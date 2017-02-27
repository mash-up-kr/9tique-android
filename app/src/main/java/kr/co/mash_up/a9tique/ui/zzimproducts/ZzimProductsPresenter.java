package kr.co.mash_up.a9tique.ui.zzimproducts;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.ResponseProduct;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.ui.productdetail.customer.CustomerProductDetailActivity;
import kr.co.mash_up.a9tique.util.CheckNonNullUtil;


public class ZzimProductsPresenter implements ZzimProductsContract.Presenter {

    //Todo: add Model Layer
    private ZzimProductsContract.View mView;  // View Layer

    private boolean mFirstLoad = true;
    private int mCurrentPageNo = -1;
    private int mPageTotal = 0;

    public ZzimProductsPresenter( /*Todo: add Model Layer */
                                  @NonNull ZzimProductsContract.View view) {

        //Todo: add Model Layer
        mView = CheckNonNullUtil.checkNotNull(view, " zzimProducts view cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void start() {
//        loadProducts(false);  //Todo: Model Layer 추가하고 이걸로 사용하기
        loadProducts(true);
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CustomerProductDetailActivity.REQUEST_CODE_DETAIL_RPODUCT:
                if(resultCode == Activity.RESULT_OK){
                    //Todo: 상세정보에서 찜 제거 했을 경우
                    int position = data.getIntExtra("position", 0);
                    mView.removeProduct(position);
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

        BackendHelper.getInstance().getZzimProducts(mCurrentPageNo + 1,
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

                        mView.showLoadedProducts(responseProduct.getProducts());
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

            BackendHelper.getInstance().getZzimProducts(mCurrentPageNo + 1,
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
    public void detailProduct(Product product, ImageView shareImageView, String transitionName) {
        mView.showProductDetail(product, shareImageView, transitionName);
    }

    @Override
    public void onClickRemoveProduct(Product product, int position) {
        mView.showDialogRemoveProduct(product, position);
    }

    @Override
    public void removeProduct(Product product, int position) {
        mView.showProgressbar(true);

        BackendHelper.getInstance().deleteZzzimProduct(product.getId(), new ResultCallback() {
            @Override
            public void onSuccess(@Nullable Object o) {
                if (!mView.isActive()) {
                    return;
                }
                mView.showProgressbar(false);
                mView.showSuccessfullyRemovedMessage();
                mView.removeProduct(position);
            }

            @Override
            public void onFailure() {
                if (!mView.isActive()) {
                    return;
                }
                mView.showProgressbar(false);
                mView.showFailureRemovedMessage();
            }
        });
    }

    @Override
    public void onClickInquireProduct(Product product, int position) {
        mView.showDialogInquire(product, position);
    }

    @Override
    public void callPhone(Product product) {
        mView.showCallPhone(product.getShop().getPhone());

    }

    @Override
    public void sendMessage(Product product) {
        mView.showSendMessage(product.getShop().getPhone());
    }
}