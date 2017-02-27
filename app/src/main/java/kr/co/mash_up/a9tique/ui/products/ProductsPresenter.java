package kr.co.mash_up.a9tique.ui.products;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.ResponseProduct;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.ui.productdetail.seller_mine.SellerMineProductDetailActivity;
import kr.co.mash_up.a9tique.util.CheckNonNullUtil;

public class ProductsPresenter implements ProductsContract.Presenter {

    //Todo: add Model Layer
    private ProductsContract.View mView;  // View Layer

    private boolean mFirstLoad = true;
    private int mCurrentPageNo = -1;
    private int mPageTotal = 0;
    private String mMainCategory;
    private String mSubCategory;

    public ProductsPresenter( /*Todo: add Model Layer */
                              @NonNull ProductsContract.View view,
                              String mainCategory, String subCategory) {
        //Todo: add Model Layer
        mView = CheckNonNullUtil.checkNotNull(view, " sellProduct view cannot be null!");
//        mView.setPresenter(this);
        mMainCategory = mainCategory;
        mSubCategory = subCategory;
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SellerMineProductDetailActivity.REQUEST_CODE_DETAIL_RPODUCT:
                if (resultCode == Activity.RESULT_OK) {
                    int result = data.getIntExtra(Constants.API_RESULT, 0);
                    switch (result) {
                        case Constants.PRODUCT_UPDATE_STATUS_SUCCESS:
                            mView.showSuccessfullyUpdatedStatusMessage();
                            mView.refreshProducts();
                            break;
                        case Constants.PRODUCT_UPDATE_STATUS_FAILURE:
                            mView.showFailureUpdatedStatusMessage();
                            break;
                        case Constants.PRODUCT_DELETE_SUCCESS:
                            mView.showSuccessfullyRemovedMessage();
                            mView.refreshProducts();
                            break;
                        case Constants.PRODUCT_DELETE_FAILURE:
                            mView.showFailureRemovedMessage();
                            break;
                    }
                }
        }
    }

    @Override
    public void loadProducts(boolean forceUpdate) {
        loadProducts(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    /**
     * 데이터를 처음 불러오거나 Refresh할 때 호출
     *
     * @param forceUpdate   업데이트 여부
     * @param showLoadingUI Loading UI 표시 여부
     */
    private void loadProducts(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mView.showLoadingIndicator(true);
        }
        if (forceUpdate) {
            mCurrentPageNo = -1;
            mPageTotal = 0;
            //Todo: Model Layer Cache clear
        }

       /*  데이터를 보존시켜서 재로딩안하게
            1. offset limit을 건다. -> 간단하지만 메모리에 문제가 있을 수도 있다. 이걸로 해결함
            2. life cycle안에서 store, restore한다.
        */
        BackendHelper.getInstance().getProducts(mCurrentPageNo + 1, mMainCategory, mSubCategory,
                new ResultCallback<ResponseProduct>() {
                    @Override
                    public void onSuccess(@Nullable ResponseProduct responseProduct) {
                        if (!mView.isActive()) {
                            return;
                        }
                        if (showLoadingUI) {
                            mView.showLoadingIndicator(false);
                        }

                        mCurrentPageNo = responseProduct.getCurrentPageNo();
                        mPageTotal = responseProduct.getPageTotal();

                        mView.showLoadedProducts(responseProduct.getProducts(), responseProduct.getElementsTotal());
                    }

                    @Override
                    public void onFailure() {
                        if (!mView.isActive()) {
                            return;
                        }
                        if (showLoadingUI) {
                            mView.showLoadingIndicator(false);
                        }
                        mView.showNoProducts();
                        mView.showLoadingProductsError();
                    }
                });
    }

    /**
     * 무한 스크롤로 데이터를 더 불러올 때 호출
     *
     * @param loadingFooterPosition Loading FootView를 추가할 리스트 포지션
     */
    @Override
    public void loadMoreProducts(int loadingFooterPosition) {
        if (mCurrentPageNo + 1 < mPageTotal) {
            mView.showFooterView(true, loadingFooterPosition);

            BackendHelper.getInstance().getProducts(mCurrentPageNo + 1, mMainCategory, mSubCategory,
                    new ResultCallback<ResponseProduct>() {
                        @Override
                        public void onSuccess(@Nullable ResponseProduct responseProduct) {
                            if (!mView.isActive()) {
                                return;
                            }
                            mView.showFooterView(false, loadingFooterPosition);

                            mCurrentPageNo = responseProduct.getCurrentPageNo();
                            mPageTotal = responseProduct.getPageTotal();

                            mView.showLoadedMoreProducts(responseProduct.getProducts());
                        }

                        @Override
                        public void onFailure() {
                            if (!mView.isActive()) {
                                return;
                            }
                            mView.showFooterView(false, loadingFooterPosition);
                            mView.showLoadingProductsError();
                        }
                    });
        }
    }

    @Override
    public void detailMineProductSeller(Product product, ImageView shareImageView, String transitionName) {
        mView.showMineProductDetailForSeller(product, shareImageView, transitionName);
    }

    @Override
    public void detailOtherProductSeller(Product product, ImageView shareImageView, String transitionName) {
        mView.showOtherProductDetailForSeller(product, shareImageView, transitionName);
    }

    @Override
    public void detailProductCustomer(Product product, ImageView shareImageView, String transitionName) {
        mView.showProductDetailForCustomer(product, shareImageView, transitionName);
    }

    @Override
    public void start() {
//        loadProducts(false);  //Todo: Model Layer 추가하고 이걸로 사용하기
        loadProducts(true);
    }
}