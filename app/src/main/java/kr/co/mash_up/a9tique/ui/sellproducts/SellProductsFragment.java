package kr.co.mash_up.a9tique.ui.sellproducts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.ui.EndlessRecyclerViewScrollListener;
import kr.co.mash_up.a9tique.ui.addeditproduct.AddEditProductActivity;
import kr.co.mash_up.a9tique.ui.addeditproduct.ConfirmationDialogFragment;
import kr.co.mash_up.a9tique.ui.addeditproduct.OrientationSpacingItemDecoration;
import kr.co.mash_up.a9tique.ui.productdetail.SellerProductDetailActivity;
import kr.co.mash_up.a9tique.util.CheckNonNullUtil;
import kr.co.mash_up.a9tique.util.ProgressUtil;
import kr.co.mash_up.a9tique.util.SnackbarUtil;

public class SellProductsFragment extends BaseFragment implements SellProductsContract.View {

    public static final String TAG = SellProductsFragment.class.getSimpleName();

    @BindView(R.id.rl_products_header)
    RelativeLayout mRlProductsHeader;

    @BindView(R.id.cb_check_all)
    CheckBox mCbCheckAll;

    @BindView(R.id.tv_products_selected_count)
    TextView mTvProductsSelectedCount;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_products)
    RecyclerView mRvProducts;

    @BindView(R.id.ll_emptyView)
    LinearLayout mEmptyView;

    @BindDimen(R.dimen.sell_product_list_item_bottom_margin)
    int itemSpacingSize;

    private SellProductListAdapter mSellProductListAdapter;

    private SellProductsContract.Presenter mPresenter;

    private int mElementsTotal = 0;

//    private static final String ARG_PARAM_CURRENT_PAGE_NO = "currentPageNo";
//    private static final String ARG_PARAM_PAGE_TOTAL = "pageTotal";
//    private static final String ARG_PARAM_FIRST_LOADING = "first_load";

//    private int mLoadingItemPosition;  //로딩 푸터 추가한 위치
//    private boolean mFirstLoad;

    public SellProductsFragment() {
        // Required empty public constructor
    }

    public static SellProductsFragment newInstance() {
        SellProductsFragment fragment = new SellProductsFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_PARAM_CURRENT_PAGE_NO, 0);
//        args.putInt(ARG_PARAM_PAGE_TOTAL, 0);
//        args.putBoolean(ARG_PARAM_FIRST_LOADING, true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
//            mParamCurrentPageNo = getArguments().getInt(ARG_PARAM_CURRENT_PAGE_NO);
//            mParamPageTotal = getArguments().getInt(ARG_PARAM_PAGE_TOTAL);
//            mFirstLoad = getArguments().getBoolean(ARG_PARAM_FIRST_LOADING);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sell_products;
    }

    @Override
    public void initView(View rootView) {
        mRvProducts.setHasFixedSize(true);
        LinearLayoutManager llmProducts = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvProducts.setLayoutManager(llmProducts);
        mRvProducts.addItemDecoration(new OrientationSpacingItemDecoration(itemSpacingSize, OrientationSpacingItemDecoration.Orientation.BOTTOM, false));

        mSellProductListAdapter = new SellProductListAdapter(getActivity());
        mSellProductListAdapter.setOnItemClickListener(new SellProductListAdapter.OnItemClickListener<SellProduct>() {
            @Override
            public void onClick(SellProduct sellProduct, int position) {
                if (sellProduct.isSeller()) {
                    mPresenter.detailProductSeller(sellProduct);
                } else {
                    mPresenter.detailProductCustomer(sellProduct);
                }
            }

            @Override
            public void onRemove(SellProduct sellProduct, int position) {
                ConfirmationDialogFragment dlgRemoveConfirmaation
                        = ConfirmationDialogFragment.newInstance("판매중인 상품 삭제", "선택하신 상품을 삭제 하시겠습니까?");
                dlgRemoveConfirmaation.setCallback(new ConfirmationDialogFragment.Callback() {
                    @Override
                    public void onClickOk() {
                        mPresenter.removeProduct(sellProduct, position);
                    }

                    @Override
                    public void onClickCancel() {
                        // Do nothing
                    }
                });
                dlgRemoveConfirmaation.setTargetFragment(SellProductsFragment.this, 0);
                dlgRemoveConfirmaation.show(getChildFragmentManager(), ConfirmationDialogFragment.TAG);
            }

            @Override
            public void onUpdate(SellProduct sellProduct, int position) {
                mPresenter.editProduct(sellProduct);
            }

            @Override
            public void onStatusUpdate(SellProduct sellProduct, int position) {
                //Todo: 바로 api call하지말고 시간을 두고 최종 변경사항으로 api 호출. 스위치는 상태를 바꾸기 쉬우니까!!
                mPresenter.updateProductStatus(sellProduct, position);
            }

            @Override
            public void onCheck(SellProduct sellProduct, int position) {
                setProductsSelectedCount();
            }
        });
        mRvProducts.setAdapter(mSellProductListAdapter);

        mRvProducts.addOnScrollListener(new EndlessRecyclerViewScrollListener(llmProducts) {
            @Override
            public int getFooterViewType(int defaultNoFooterViewType) {
                return mSellProductListAdapter.VIEW_TYPE_FOOTER;
            }

            @Override
            public void onLoadMore(int page, int totalItemCount) {
                Log.d(TAG, "page: " + page + " totalItemCount: " + totalItemCount);
                mPresenter.loadMoreProducts(totalItemCount - 1);  // 로딩 푸터 추가할 위치 전달. 헤더 때문에 -1
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.tulip_tree, R.color.mine_shaft);
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshProducts);
    }

    @OnCheckedChanged(R.id.cb_check_all)
    public void onCheckedAll(boolean checked) {
        mSellProductListAdapter.setSellProductsChecked(checked);
        setProductsSelectedCount();
    }

    @OnClick(R.id.tv_product_checked_remove)
    public void removeCheckedProducts() {
        ConfirmationDialogFragment dlgRemoveCheckedConfirmaation
                = ConfirmationDialogFragment.newInstance("판매중인 상품 선택삭제", "선택하신 상품을 삭제 하시겠습니까?");
        dlgRemoveCheckedConfirmaation.setCallback(new ConfirmationDialogFragment.Callback() {
            @Override
            public void onClickOk() {
                mPresenter.removeProductSelected(mSellProductListAdapter.getSellProductIsSelected());
            }

            @Override
            public void onClickCancel() {
                // Do nothing
            }
        });
        dlgRemoveCheckedConfirmaation.setTargetFragment(SellProductsFragment.this, 0);
        dlgRemoveCheckedConfirmaation.show(getChildFragmentManager(), ConfirmationDialogFragment.TAG);
    }

    private void setProductsSelectedCount() {
        int checkedItemCount = mSellProductListAdapter.getSellProductSelectedCount();
        mTvProductsSelectedCount.setText(String.format(Locale.KOREA, "%d/%d", checkedItemCount, mElementsTotal));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showLoadingIndicator(boolean active) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(active));
    }

    @Override
    public void showProgressbar(boolean active) {
        if (active) {
            ProgressUtil.showProgressDialog(getActivity());
        } else {
            ProgressUtil.hideProgressDialog();
        }
    }

    @Override
    public void showFooterView(boolean active, int position) {
        if (active) {
            mSellProductListAdapter.addFooterView(position);
        } else {
            mSellProductListAdapter.removeFooterView(position);
        }
    }

    private void showEmptyView(boolean active) {
        if (active) {
            mEmptyView.setVisibility(View.VISIBLE);
            mRvProducts.setVisibility(View.GONE);
            mRlProductsHeader.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mRvProducts.setVisibility(View.VISIBLE);
            mRlProductsHeader.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoadedProducts(List<Product> products, int elementsTotal) {
        mSellProductListAdapter.setSellProducts(products);
        showEmptyView(mSellProductListAdapter.getItemCount() == 0);
        mElementsTotal = elementsTotal;
        setProductsSelectedCount();
    }

    public void showLoadedMoreProducts(List<Product> products) {
        mSellProductListAdapter.addProducts(products);
    }

    @Override
    public void showLoadingProductsError() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품로딩 실패", "", null);
    }

    @Override
    public void showNoProducts() {
        showEmptyView(true);
    }

    @Override
    public void showProductDetailForSeller(Product product) {
        Intent intent = new Intent(getActivity(), SellerProductDetailActivity.class);
        intent.putExtra(Constants.PRODUCT, product);
        startActivityForResult(intent, SellerProductDetailActivity.REQUEST_CODE_DETAIL_RPODUCT);
    }

    @Override
    public void showProductDetailForCustomer(Product product) {
        //Todo: change customer product detail activity
        Intent intent = new Intent(getActivity(), SellerProductDetailActivity.class);
        intent.putExtra(Constants.PRODUCT, product);
        startActivityForResult(intent, SellerProductDetailActivity.REQUEST_CODE_DETAIL_RPODUCT);
    }

    @Override
    public void showEditProduct(Product product) {
        Intent intent = new Intent(getActivity(), AddEditProductActivity.class);
        intent.putExtra(Constants.PRODUCT_ID, product.getId());
        intent.putExtra(Constants.PRODUCT, product);
        startActivityForResult(intent, AddEditProductActivity.REQUEST_CODE_EDIT_PRODUCT);
    }

    @Override
    public boolean isActive() {
        return isAdded();  // fragment가 acitivity에 의해 hosting 여부
    }

    @Override
    public void setPresenter(SellProductsContract.Presenter presenter) {
        mPresenter = CheckNonNullUtil.checkNotNull(presenter);
    }

    @Override
    public void showSuccessfullyRemovedMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품 삭제 성공", "", null);
    }

    @Override
    public void showFailureRemovedMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품 삭제 실패", "", null);
    }

    @Override
    public void showSuccessfullyUpdateMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품 수정 성공", "", null);
    }

    @Override
    public void showFailureUpdateMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품 수정 실패", "", null);
    }

    @Override
    public void showSuccessfullyRemovedSelectedMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "선택 상품삭제 성공", "", null);
    }

    @Override
    public void showFailureRemovedSelectedMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "선택 상품삭제 실패", "", null);
    }

    @Override
    public void showSuccessfullyUpdatedStatusMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품 판매 상태 변경 성공", "", null);
    }

    @Override
    public void showFailureUpdatedStatusMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품 판매 상태 변경 실페", "", null);
    }

    @Override
    public void showRemoveProductSelectedErrorMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "선택하신 상품이 없습니다.", "", null);
    }

    @Override
    public void updateProductStatus(int position) {
        mSellProductListAdapter.notifyItemChanged(position);
    }

    @Override
    public void removeProduct(int position) {
        mSellProductListAdapter.removeItem(position - 1);
        mElementsTotal -= 1;
        setProductsSelectedCount();
    }

    @Override
    public void refreshProducts() {
        mPresenter.loadProducts(true);
    }
}