package kr.co.mash_up.a9tique.ui.sellproducts;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.RequestProduct;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.ui.EndlessRecyclerViewScrollListener;
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

    @BindView(R.id.tv_products_count)
    TextView mTvProductsCount;

    @BindView(R.id.tv_product_remove_all)
    TextView mTvProductRemoveAll;

    @BindDimen(R.dimen.sell_product_list_item_bottom_margin)
    int itemSpacingSize;

    private SellProductListAdapter mSellProductListAdapter;

    private int mLoadingItemPosition;  //로딩 푸터 추가한 위치

    public SellProductsFragment() {
        // Required empty public constructor
    }

    public static SellProductsFragment newInstance() {
        SellProductsFragment fragment = new SellProductsFragment();
        return fragment;
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
        mRvProducts.addItemDecoration(new OrientationSpacingItemDecoration(itemSpacingSize, OrientationSpacingItemDecoration.Orientation.BOTTOM));
        mRvProducts.setEmptyView(mEmptyView);

        mSellProductListAdapter = new SellProductListAdapter(getActivity());
        mSellProductListAdapter.setOnItemClickListener(new SellProductListAdapter.OnItemClickListener<Product>() {
            @Override
            public void onClick(Product product, int position) {
                if (product.isSeller()) {
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
                //Todo: show progress bar
                SnackbarUtil.showMessage(getActivity(), getView(), "상품 삭제 성공", "", null);

//                BackendHelper.getInstance().deleteProduct(product.getId(), new ResultCallback() {
//                    @Override
//                    public void onSuccess(Object o) {
//                        //Todo: call adapter notifyDataSetChange()
//                        SnackbarUtil.showMessage(getActivity(), getView(), "상품 삭제 성공", "", null);
//                    }
//
//                    @Override
//                    public void onFailure() {
//                        SnackbarUtil.showMessage(getActivity(), getView(), "상품 삭제 실패", "", null);
//                    }
//                });
            }

            @Override
            public void onUpdate(Product product, int position) {
                //Todo: show detail activity. update mode
            }

            @Override
            public void onStatusUpdate(Product product, int position) {
                //Todo: show progress bar
                RequestProduct requestProduct = new RequestProduct();
                requestProduct.setStatus(product.getStatus());

//                BackendHelper.getInstance().updateProduct(product.getId(), requestProduct, new ResultCallback() {
//                    @Override
//                    public void onSuccess(Object o) {
//                mSellProductListAdapter.notifyItemChanged(position);
//                        SnackbarUtil.showMessage(getActivity(), getView(), "상품 판매 상태 변경 성공", "" , null);
//                    }
//
//                    @Override
//                    public void onFailure() {
//                        SnackbarUtil.showMessage(getActivity(), getView(), "상품 판매 상태 변경 실페", "" , null);
//                    }
//                });
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
//                productsLoadMoreDataFromApi(mParamCurrentPageNo + 1);
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.my_sin, R.color.nero);
        mSwipeRefreshLayout.setOnRefreshListener(this::refresh);

        setProductsCount();
    }

    private void refresh() {
        mSwipeRefreshLayout.setRefreshing(true);
//        productsLoadMoreDataFromApi(mParamCurrentPageNo + 1);
        mRvProducts.scrollToPosition(0);
    }

    /**
     * 상품 갯수 표시
     */
    private void setProductsCount() {
        mTvProductsCount.setText(String.format(Locale.KOREA, "%d개의 판매 등록 상품이 있습니다.", mSellProductListAdapter.getItemCount()));

        if (mSellProductListAdapter.getItemCount() == 0) {
            mTvProductsCount.setVisibility(View.GONE);
            mTvProductRemoveAll.setVisibility(View.GONE);
        } else {
            mTvProductsCount.setVisibility(View.VISIBLE);
            mTvProductRemoveAll.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.tv_product_remove_all)
    public void onClickProductRemoveAll() {
        //Todo: api call -> remove product all

        //Todo: move in callback
        SnackbarUtil.showMessage(getActivity(), getView(), "전체 상품삭제 성공", "", null);
        SnackbarUtil.showMessage(getActivity(), getView(), "전체 상품삭제 실패", "", null);
    }
}
