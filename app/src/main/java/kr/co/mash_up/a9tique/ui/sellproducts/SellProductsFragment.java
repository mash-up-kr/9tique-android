package kr.co.mash_up.a9tique.ui.sellproducts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindDimen;
import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.RequestProduct;
import kr.co.mash_up.a9tique.data.remote.ResponseProduct;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.ui.EndlessRecyclerViewScrollListener;
import kr.co.mash_up.a9tique.ui.addeditproduct.ConfirmationDialogFragment;
import kr.co.mash_up.a9tique.ui.addeditproduct.OrientationSpacingItemDecoration;
import kr.co.mash_up.a9tique.ui.productdetail.SellerProductDetailActivity;
import kr.co.mash_up.a9tique.ui.widget.RecyclerViewEmptySupport;
import kr.co.mash_up.a9tique.util.SnackbarUtil;

public class SellProductsFragment extends BaseFragment {

    public static final String TAG = SellProductsFragment.class.getSimpleName();

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_products)
    RecyclerViewEmptySupport mRvProducts;

    @BindView(R.id.ll_emptyView)
    LinearLayout mEmptyView;

    @BindDimen(R.dimen.sell_product_list_item_bottom_margin)
    int itemSpacingSize;

    private SellProductListAdapter mSellProductListAdapter;

    private static final String ARG_PARAM_CURRENT_PAGE_NO = "currentPageNo";
    private static final String ARG_PARAM_PAGE_TOTAL = "pageTotal";
    private static final String ARG_PARAM_FIRST_LOADING = "first_load";

    private int mParamCurrentPageNo;
    private int mParamPageTotal;
    private int mLoadingItemPosition;  //로딩 푸터 추가한 위치
    private boolean mFirstLoad;

    public SellProductsFragment() {
        // Required empty public constructor
    }

    public static SellProductsFragment newInstance() {
        SellProductsFragment fragment = new SellProductsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_CURRENT_PAGE_NO, 0);
        args.putInt(ARG_PARAM_PAGE_TOTAL, 0);
        args.putBoolean(ARG_PARAM_FIRST_LOADING, true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mParamCurrentPageNo = getArguments().getInt(ARG_PARAM_CURRENT_PAGE_NO);
            mParamPageTotal = getArguments().getInt(ARG_PARAM_PAGE_TOTAL);
            mFirstLoad = getArguments().getBoolean(ARG_PARAM_FIRST_LOADING);
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
                    Intent intent = new Intent(getActivity(), SellerProductDetailActivity.class);
                    intent.putExtra("product", product);
                    startActivityForResult(intent, SellerProductDetailActivity.REQUEST_CODE_DETAIL_RPODUCT);
                } else {
                    //Todo: show customer product detail activity
                    Intent intent = new Intent(getActivity(), SellerProductDetailActivity.class);
                    intent.putExtra("product", product);
                    startActivityForResult(intent, SellerProductDetailActivity.REQUEST_CODE_DETAIL_RPODUCT);
                }
            }

            @Override
            public void onRemove(Product product, int position) {
                ConfirmationDialogFragment dlgRemoveConfirmaation
                        = ConfirmationDialogFragment.newInstance("판매중인 상품 삭제", "선택하신 상품을 삭제 하시겠습니까?");
                dlgRemoveConfirmaation.setCallback(new ConfirmationDialogFragment.Callback() {
                    @Override
                    public void onClickOk() {
                        //Todo: show progress bar

                        //Todo: api call -> remove product
                        BackendHelper.getInstance().deleteProduct(product.getId(), new ResultCallback() {
                            @Override
                            public void onSuccess(Object o) {
                                //Todo: reloading
                                SnackbarUtil.showMessage(getActivity(), getView(), "상품 삭제 성공", "", null);
                            }

                            @Override
                            public void onFailure() {
                                SnackbarUtil.showMessage(getActivity(), getView(), "상품 삭제 실패", "", null);
                            }
                        });
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
                //Todo: show detail activity. update mode
            }

            @Override
            public void onStatusUpdate(Product product, int position) {
                RequestProduct requestProduct = new RequestProduct();
                requestProduct.setStatus(product.getStatus());

                ConfirmationDialogFragment dlgStatusUpdateConfirmaation;
                if (requestProduct.getStatus().name().equals(Product.Status.SELL.name())) {
                    dlgStatusUpdateConfirmaation
                            = ConfirmationDialogFragment.newInstance("상품 판매 표기", "해당 상품을 판매중으로 표기하시겠습니까?");
                } else {
                    dlgStatusUpdateConfirmaation
                            = ConfirmationDialogFragment.newInstance("상품 판매 표기", "해당 상품을 판매완료로 표기하시겠습니까?");
                }

                dlgStatusUpdateConfirmaation.setCallback(new ConfirmationDialogFragment.Callback() {
                    @Override
                    public void onClickOk() {
                        //Todo: show progress bar

                        BackendHelper.getInstance().updateProduct(product.getId(), requestProduct, new ResultCallback() {
                            @Override
                            public void onSuccess(Object o) {
                                //Todo: reloading??
                                mSellProductListAdapter.notifyItemChanged(position);
                                SnackbarUtil.showMessage(getActivity(), getView(), "상품 판매 상태 변경 성공", "", null);
                            }

                            @Override
                            public void onFailure() {
                                SnackbarUtil.showMessage(getActivity(), getView(), "상품 판매 상태 변경 실페", "", null);
                            }
                        });
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

        //Todo: 무한 스크롤
        mRvProducts.addOnScrollListener(new EndlessRecyclerViewScrollListener(llmProducts) {
            @Override
            public int getFooterViewType(int defaultNoFooterViewType) {
                return mSellProductListAdapter.VIEW_TYPE_FOOTER;
            }

            @Override
            public void onLoadMore(int page, int totalItemCount) {
                Log.d(TAG, "page: " + page + "totalItemCount: " + totalItemCount);
                mLoadingItemPosition = totalItemCount;  //로딩 푸터 추가한 위치 저장
                sellProductsLoadMoreDataFromApi(mParamCurrentPageNo + 1);
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.my_sin, R.color.nero);
        mSwipeRefreshLayout.setOnRefreshListener(this::refresh);
    }

    private void refresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        sellProductsLoadMoreDataFromApi(mParamCurrentPageNo + 1);
        mRvProducts.scrollToPosition(0);
    }

    public void productRemoveAll() {
        ConfirmationDialogFragment dlgRemoveAllConfirmaation
                = ConfirmationDialogFragment.newInstance("판매중인 상품 전체삭제", "선택하신 상품을 전체삭제 하시겠습니까?");
        dlgRemoveAllConfirmaation.setCallback(new ConfirmationDialogFragment.Callback() {
            @Override
            public void onClickOk() {
                BackendHelper.getInstance().deleteSellProducts(mSellProductListAdapter.getProducts(), new ResultCallback() {
                    @Override
                    public void onSuccess(Object o) {
                        SnackbarUtil.showMessage(getActivity(), getView(), "전체 상품삭제 성공", "", null);
                        //Todo: reloading
                    }

                    @Override
                    public void onFailure() {
                        SnackbarUtil.showMessage(getActivity(), getView(), "전체 상품삭제 실패", "", null);
                    }
                });
            }

            @Override
            public void onClickCancel() {
                // Do nothing
            }
        });
        dlgRemoveAllConfirmaation.setTargetFragment(SellProductsFragment.this, 0);
        dlgRemoveAllConfirmaation.show(getChildFragmentManager(), ConfirmationDialogFragment.TAG);
    }

    private void sellProductsLoadMoreDataFromApi(int currentPageNo) {
        if (mFirstLoad) {
            currentPageNo--;
        }

         /*Todo: 아래로 안들어가서 화면이 안보이는 것
         1. mFirstLoad, currentPageNo, mParamPageTotal 초기화해서 이부분에 들어가게 해야한다.
         2. 데이터를 보존시켜서 재로딩안하게 - life cycle안에서 store, restore한다.
        */
        if (mFirstLoad || currentPageNo < mParamPageTotal) {
            Log.e(TAG, " " + mLoadingItemPosition);
            mSellProductListAdapter.addItem(null, mLoadingItemPosition);

            BackendHelper.getInstance().getSellProducts(currentPageNo,
                    new ResultCallback<ResponseProduct>() {
                        @Override
                        public void onSuccess(ResponseProduct responseProduct) {
                            if (!mFirstLoad) {
                                Log.e(TAG, " " + mLoadingItemPosition);
                                mSellProductListAdapter.removeItem(mLoadingItemPosition);  // 로딩 푸터 제거
                            }

                            mParamCurrentPageNo = responseProduct.getCurrentPageNo();
                            mParamPageTotal = responseProduct.getPageTotal();
                            Log.e(TAG, "mParamCurrentPageNo " + mParamCurrentPageNo + " " + mParamPageTotal);

                            mSellProductListAdapter.setProducts(responseProduct.getProducts());
                        }

                        @Override
                        public void onFailure() {
                            SnackbarUtil.showMessage(getActivity(), getView(), "상품로딩 실패", "", null);
                        }
                    });
        }
        mFirstLoad = false;
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sellProductsLoadMoreDataFromApi(mParamCurrentPageNo + 1);
    }
}
