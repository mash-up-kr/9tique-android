package kr.co.mash_up.a9tique.ui.products;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.ResponseProduct;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.ui.EndlessRecyclerViewScrollListener;

public class SubCategoryFragment extends BaseFragment {

    public static final String TAG = SubCategoryFragment.class.getSimpleName();

    @BindView(R.id.rv_products)
    RecyclerView mRvProducts;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ProductListAdapter mProductListAdapter;

    private static final String ARG_PARAM_MAIN_CATEGORY = "mainCategory";
    private static final String ARG_PARAM_SUB_CATEGORY = "subCategory";
    private static final String ARG_PARAM_CURRENT_PAGE_NO = "currentPageNo";
    private static final String ARG_PARAM_PAGE_TOTAL = "pageTotal";
    private static final String ARG_PARAM_FIRST_LOADING = "first_load";

    private String mParamMainCategory;
    private String mParamSubCategory;
    private int mParamCurrentPageNo;
    private int mParamPageTotal;
    private int mLoadingItemPosition;  //로딩 푸터 추가한 위치
    private boolean mFirstLoad;

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    public static SubCategoryFragment newInstance(String mainCategory, String subCategory) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_MAIN_CATEGORY, mainCategory);
        args.putString(ARG_PARAM_SUB_CATEGORY, subCategory);
        args.putInt(ARG_PARAM_CURRENT_PAGE_NO, 0);
        args.putInt(ARG_PARAM_PAGE_TOTAL, 0);
        args.putBoolean(ARG_PARAM_FIRST_LOADING, true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamMainCategory = getArguments().getString(ARG_PARAM_MAIN_CATEGORY);
            mParamSubCategory = getArguments().getString(ARG_PARAM_SUB_CATEGORY);
            mParamCurrentPageNo = getArguments().getInt(ARG_PARAM_CURRENT_PAGE_NO);
            mParamPageTotal = getArguments().getInt(ARG_PARAM_PAGE_TOTAL);
            mFirstLoad = getArguments().getBoolean(ARG_PARAM_FIRST_LOADING);
        }
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

        mProductListAdapter = new ProductListAdapter(getActivity());
        mProductListAdapter.setOnItemClickListener(product -> {
            //Todo: show detail product activity
            //Todo: remove snackbar
            Snackbar.make(getView(), "show detail product activity", Snackbar.LENGTH_SHORT)
                    .show();
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
                mLoadingItemPosition = totalItemCount;  //로딩 푸터 추가한 위치 저장
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                productsLoadMoreDataFromApi(mParamCurrentPageNo + 1);
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.my_sin, R.color.nero);
        mSwipeRefreshLayout.setOnRefreshListener(this::refresh);
    }

    private void refresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        productsLoadMoreDataFromApi(mParamCurrentPageNo + 1);
        mRvProducts.scrollToPosition(0);
    }

    private void productsLoadMoreDataFromApi(int currentPageNo) {

        if (mFirstLoad) {
            currentPageNo--;
        }

        // total 7, 요청은 0~6까지
        if (mFirstLoad || currentPageNo < mParamPageTotal) {
            mProductListAdapter.addItem(null, mLoadingItemPosition);

            BackendHelper.getInstance().getProducts(currentPageNo, mParamMainCategory, mParamSubCategory,
                    new ResultCallback<ResponseProduct>() {
                        @Override
                        public void onSuccess(ResponseProduct responseProduct) {

                            if (!mFirstLoad) {
                                mProductListAdapter.removeItem(mLoadingItemPosition);  // 로딩 푸터 제거
                            }

                            mParamCurrentPageNo = responseProduct.getCurrentPageNo();
                            mParamPageTotal = responseProduct.getPageTotal();
                            mProductListAdapter.setProducts(responseProduct.getProducts());
                        }

                        @Override
                        public void onFailure() {
                            //Todo: show toast
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
        Log.e(TAG, mParamCurrentPageNo + " " + mParamPageTotal);
        productsLoadMoreDataFromApi(mParamCurrentPageNo + 1);
    }
}
