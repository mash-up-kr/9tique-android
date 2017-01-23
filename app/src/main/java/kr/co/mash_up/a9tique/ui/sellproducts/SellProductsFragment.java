package kr.co.mash_up.a9tique.ui.sellproducts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.ui.EndlessRecyclerViewScrollListener;
import kr.co.mash_up.a9tique.ui.addeditproduct.AddEditProductActivity;
import kr.co.mash_up.a9tique.ui.addeditproduct.ConfirmationDialogFragment;
import kr.co.mash_up.a9tique.ui.addeditproduct.OrientationSpacingItemDecoration;
import kr.co.mash_up.a9tique.ui.productdetail.SellerProductDetailActivity;
import kr.co.mash_up.a9tique.ui.widget.RecyclerViewEmptySupport;
import kr.co.mash_up.a9tique.util.CheckNonNullUtil;
import kr.co.mash_up.a9tique.util.SnackbarUtil;

public class SellProductsFragment extends BaseFragment implements SellProductsContract.View {

    public static final String TAG = SellProductsFragment.class.getSimpleName();

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_products)
    RecyclerViewEmptySupport mRvProducts;

    @BindView(R.id.ll_emptyView)
    LinearLayout mEmptyView;

    @BindDimen(R.dimen.sell_product_list_item_bottom_margin)
    int itemSpacingSize;

    ProgressDialog mPprogressDialog;

    private SellProductListAdapter mSellProductListAdapter;

    private SellProductsContract.Presenter mPresenter;

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
        mRvProducts.addItemDecoration(new OrientationSpacingItemDecoration(itemSpacingSize, OrientationSpacingItemDecoration.Orientation.BOTTOM, true));
        mRvProducts.setEmptyView(mEmptyView);
        mRvProducts.setHeader(true);

        mSellProductListAdapter = new SellProductListAdapter(getActivity());
        mSellProductListAdapter.setOnItemClickListener(new SellProductListAdapter.OnItemClickListener<Product>() {
            @Override
            public void onClick(Product product, int position) {
                if (position == 0) {
                    productRemoveAll();
                } else if (product.isSeller()) {
                    mPresenter.detailProductSeller(product);
                } else {
                    mPresenter.detailProductCustomer(product);
                }
            }

            @Override
            public void onRemove(Product product, int position) {
                ConfirmationDialogFragment dlgRemoveConfirmaation
                        = ConfirmationDialogFragment.newInstance("판매중인 상품 삭제", "선택하신 상품을 삭제 하시겠습니까?");
                dlgRemoveConfirmaation.setCallback(new ConfirmationDialogFragment.Callback() {
                    @Override
                    public void onClickOk() {
                        mPresenter.removeProduct(product, position);
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
            public void onUpdate(Product product, int position) {
                mPresenter.editProduct(product);
            }

            @Override
            public void onStatusUpdate(Product product, int position) {
                ConfirmationDialogFragment dlgStatusUpdateConfirmaation;
                if (product.getStatus().name().equals(Product.Status.SELL.name())) {
                    dlgStatusUpdateConfirmaation
                            = ConfirmationDialogFragment.newInstance("상품 판매 표기", "해당 상품을 판매중으로 표기하시겠습니까?");
                } else {
                    dlgStatusUpdateConfirmaation
                            = ConfirmationDialogFragment.newInstance("상품 판매 표기", "해당 상품을 판매완료로 표기하시겠습니까?");
                }

                dlgStatusUpdateConfirmaation.setCallback(new ConfirmationDialogFragment.Callback() {
                    @Override
                    public void onClickOk() {
                        mPresenter.updateProductStatus(product, position);
                    }

                    @Override
                    public void onClickCancel() {
                        // Do nothing
                    }
                });
                dlgStatusUpdateConfirmaation.setTargetFragment(SellProductsFragment.this, 0);
                dlgStatusUpdateConfirmaation.show(getChildFragmentManager(), ConfirmationDialogFragment.TAG);
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

        mSwipeRefreshLayout.setColorSchemeResources(R.color.my_sin, R.color.nero);
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshProducts);

        mPprogressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
        mPprogressDialog.setCancelable(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    public void productRemoveAll() {
        ConfirmationDialogFragment dlgRemoveAllConfirmaation
                = ConfirmationDialogFragment.newInstance("판매중인 상품 전체삭제", "선택하신 상품을 전체삭제 하시겠습니까?");
        dlgRemoveAllConfirmaation.setCallback(new ConfirmationDialogFragment.Callback() {
            @Override
            public void onClickOk() {
                mPresenter.removeProductAll(mSellProductListAdapter.getProducts());
            }

            @Override
            public void onClickCancel() {
                // Do nothing
            }
        });
        dlgRemoveAllConfirmaation.setTargetFragment(SellProductsFragment.this, 0);
        dlgRemoveAllConfirmaation.show(getChildFragmentManager(), ConfirmationDialogFragment.TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setLodingIndicator(boolean active) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(active));
    }

    @Override
    public void setProgressbar(boolean active) {
        if (active) {
            mPprogressDialog.show();
        } else {
            mPprogressDialog.dismiss();
        }
    }

    @Override
    public void setFooterView(boolean active, int position) {
        if (active) {
            mSellProductListAdapter.addFooterView(position);
        } else {
            mSellProductListAdapter.removeFooterView(position);
        }
    }

    @Override
    public void showProducts(List<Product> products, int elementsTotal) {
        mSellProductListAdapter.setElementsTotal(elementsTotal);
        mSellProductListAdapter.setProducts(products);
    }

    public void showAddProducts(List<Product> products){
        mSellProductListAdapter.addProducts(products);
    }

    @Override
    public void showLoadingProductsError() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품로딩 실패", "", null);
    }

    @Override
    public void showNoProducts() {
        // Do nothong
    }

    @Override
    public void showProductDetailForSeller(Product product) {
        Intent intent = new Intent(getActivity(), SellerProductDetailActivity.class);
        intent.putExtra("product", product);
        startActivityForResult(intent, SellerProductDetailActivity.REQUEST_CODE_DETAIL_RPODUCT);
    }

    @Override
    public void showProductDetailForCustomer(Product product) {
        //Todo: change customer product detail activity
        Intent intent = new Intent(getActivity(), SellerProductDetailActivity.class);
        intent.putExtra("product", product);
        startActivityForResult(intent, SellerProductDetailActivity.REQUEST_CODE_DETAIL_RPODUCT);
    }

    @Override
    public void showEditProduct(Product product) {
        Intent intent = new Intent(getActivity(), AddEditProductActivity.class);
        intent.putExtra("productId", product.getId());
        intent.putExtra("product", product);
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
    public void showSuccessfullyRemovedAllMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "전체 상품삭제 성공", "", null);
    }

    @Override
    public void showFailureRemovedAllMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "전체 상품삭제 실패", "", null);
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
    public void updateProductStatus(int position) {
        mSellProductListAdapter.notifyItemChanged(position);
    }

    @Override
    public void removeProduct(int position) {
        mSellProductListAdapter.setElementsTotal(mSellProductListAdapter.getElementsTotal() - 1);
        mSellProductListAdapter.removeItem(position - 1);
    }

    @Override
    public void refreshProducts() {
        mPresenter.loadProducts(true);
    }
}