package kr.co.mash_up.a9tique._old.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique._old.base.ui.BaseFragment;
import kr.co.mash_up.a9tique._old.common.AccountManager;
import kr.co.mash_up.a9tique._old.common.Constants;
import kr.co.mash_up.a9tique._old.common.eventbus.Events;
import kr.co.mash_up.a9tique._old.data.Product;
import kr.co.mash_up.a9tique._old.data.User;
import kr.co.mash_up.a9tique._old.ui.EndlessRecyclerViewScrollListener;
import kr.co.mash_up.a9tique._old.ui.productdetail.customer.CustomerProductDetailActivity;
import kr.co.mash_up.a9tique._old.ui.productdetail.seller_mine.SellerMineProductDetailActivity;
import kr.co.mash_up.a9tique._old.ui.productdetail.seller_other.SellerOtherProductDetailActivity;
import kr.co.mash_up.a9tique._old.ui.widget.RecyclerViewEmptySupport;
import kr.co.mash_up.a9tique._old.util.SnackbarUtil;

public class SubCategoryFragment extends BaseFragment implements ProductsContract.View {

    public static final String TAG = SubCategoryFragment.class.getSimpleName();

    @BindView(R.id.rv_products)
    RecyclerViewEmptySupport mRvProducts;

    @BindView(R.id.ll_emptyView)
    LinearLayout mEmptyView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ProductListAdapter mProductListAdapter;

    private ProductsContract.Presenter mPresenter;

    private static final String ARG_MAIN_CATEGORY = "mainCategory";
    private static final String ARG_SUB_CATEGORY = "subCategory";
//    private static final String ARG_PARAM_CURRENT_PAGE_NO = "currentPageNo";
//    private static final String ARG_PARAM_PAGE_TOTAL = "pageTotal";
//    private static final String ARG_PARAM_FIRST_LOADING = "first_load";

    private String mArgMainCategory;
    private String mArgSubCategory;
//    private int mParamCurrentPageNo;
//    private int mParamPageTotal;
//    private int mLoadingItemPosition;  //로딩 푸터 추가한 위치
//    private boolean mFirstLoad;

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    public static SubCategoryFragment newInstance(String mainCategory, String subCategory) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MAIN_CATEGORY, mainCategory);
        args.putString(ARG_SUB_CATEGORY, subCategory);
//        args.putInt(ARG_PARAM_CURRENT_PAGE_NO, 0);
//        args.putInt(ARG_PARAM_PAGE_TOTAL, 0);
//        args.putBoolean(ARG_PARAM_FIRST_LOADING, true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mArgMainCategory = getArguments().getString(ARG_MAIN_CATEGORY);
            mArgSubCategory = getArguments().getString(ARG_SUB_CATEGORY);
//            mParamCurrentPageNo = getArguments().getInt(ARG_PARAM_CURRENT_PAGE_NO);
//            mParamPageTotal = getArguments().getInt(ARG_PARAM_PAGE_TOTAL);
//            mFirstLoad = getArguments().getBoolean(ARG_PARAM_FIRST_LOADING);
        }

        mPresenter = new ProductsPresenter(this, mArgMainCategory, mArgSubCategory);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sub_category;
    }

    @Override
    public void initView(View rootView) {
        mRvProducts.setHasFixedSize(true);
        GridLayoutManager glmProducts = new GridLayoutManager(getActivity(), 2);
        glmProducts.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mProductListAdapter.getItemViewType(position) == ProductListAdapter.VIEW_TYPE_FOOTER) {
                    return 2;
                }
                return 1;
            }
        });
        mRvProducts.setLayoutManager(glmProducts);
        mRvProducts.setEmptyView(mEmptyView);

        mProductListAdapter = new ProductListAdapter(getActivity());
        mProductListAdapter.setOnItemClickListener((product, position, shareImageView, transitionName) -> {
            if (product.isSeller()) {
                mPresenter.detailMineProductSeller(product, shareImageView, transitionName);
            } else {
                if (AccountManager.getInstance().getLevel() == User.Level.SELLER) {
                    mPresenter.detailOtherProductSeller(product, shareImageView, transitionName);
                } else {
                    mPresenter.detailProductCustomer(product, shareImageView, transitionName);
                }
            }
        });
        mRvProducts.setAdapter(mProductListAdapter);
        mRvProducts.addOnScrollListener(new EndlessRecyclerViewScrollListener(glmProducts) {
            @Override
            public int getFooterViewType(int defaultNoFooterViewType) {
                return mProductListAdapter.VIEW_TYPE_FOOTER;
            }

            @Override
            public void onLoadMore(int page, int totalItemCount) {
                Log.d(TAG, "page: " + page + "totalItemCount: " + totalItemCount);
                mPresenter.loadMoreProducts(totalItemCount);

//                mLoadingItemPosition = totalItemCount;  //로딩 푸터 추가한 위치 저장
//                // Triggered only when new data needs to be appended to the list
//                // Add whatever code is needed to append new items to the bottom of the list
//                productsLoadMoreDataFromApi(mParamCurrentPageNo + 1);
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.tulip_tree, R.color.mine_shaft);
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshProducts);
        Log.e(TAG, "initView " + mArgMainCategory + " " + mArgSubCategory);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume " + mArgMainCategory + " " + mArgSubCategory);
        mPresenter.start();
//        productsLoadMoreDataFromApi(mParamCurrentPageNo + 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.result(requestCode, resultCode, data);
    }

    @Override
    public void showLoadingIndicator(boolean active) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(active));
    }

    @Override
    public void showFooterView(boolean active, int position) {
        if (active) {
            mProductListAdapter.addFooterView(position);
        } else {
            mProductListAdapter.removeFooterView(position);
        }
    }

    @Override
    public void showLoadedProducts(List<Product> products, int elementsTotal) {
        mProductListAdapter.setProducts(products);
    }

    @Override
    public void showLoadedMoreProducts(List<Product> products) {
        mProductListAdapter.addProducts(products);
    }

    @Override
    public void showLoadingProductsError() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품로딩 실패", "", null);
    }

    @Override
    public void refreshProducts() {
        mPresenter.loadProducts(true);
    }

    @Override
    public void showNoProducts() {
        mProductListAdapter.clearProducts();
    }

    @Override
    public void showMineProductDetailForSeller(Product product, ImageView shareImageView, String transitionName) {
        Intent intent = new Intent(getActivity(), SellerMineProductDetailActivity.class);
        intent.putExtra(Constants.PRODUCT, product);
        intent.putExtra(Constants.TRANSITION_NAME, transitionName);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                shareImageView,
                transitionName);
        startActivityForResult(intent, SellerMineProductDetailActivity.REQUEST_CODE_DETAIL_RPODUCT, options.toBundle());
    }

    @Override
    public void showOtherProductDetailForSeller(Product product, ImageView shareImageView, String transitionName) {
        Intent intent = new Intent(getActivity(), SellerOtherProductDetailActivity.class);
        intent.putExtra(Constants.PRODUCT, product);
        intent.putExtra(Constants.TRANSITION_NAME, transitionName);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                shareImageView,
                transitionName);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void showProductDetailForCustomer(Product product, ImageView shareImageView, String transitionName) {
        Intent intent = new Intent(getActivity(), CustomerProductDetailActivity.class);
        intent.putExtra(Constants.PRODUCT, product);
        intent.putExtra(Constants.TRANSITION_NAME, transitionName);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                shareImageView,
                transitionName);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void showSuccessfullyRemovedMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품 삭제 완료", "", null);
    }

    @Override
    public void showFailureRemovedMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품 삭제 실패", "", null);
    }

    @Override
    public void showSuccessfullyUpdatedStatusMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품 판매상태 수정 완료", "", null);
    }

    @Override
    public void showFailureUpdatedStatusMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품 판매상태 수정 실패", "", null);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ProductsContract.Presenter presenter) {
        // Do nothing
//        mPresenter = presenter;
    }

    // EventBus subscribe
    @Override
    protected void handleEventFromBus(Object event) {
        super.handleEventFromBus(event);

        if (event instanceof Events) {
            refreshProducts();
        }
    }
}