package kr.co.mash_up.a9tique.ui.sellproducts;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.RequestProduct;
import kr.co.mash_up.a9tique.data.remote.ResponseProduct;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.util.CheckNonNullUtil;

/**
 * Created by Dong on 2017-01-20.
 */

public class SellProductsPresenter implements SellProductsContract.Presenter {

    public static final String TAG = SellProductsPresenter.class.getSimpleName();

    //Todo: add Model

    @NonNull
    private final SellProductsContract.View mView;  // View

    private boolean mFirstLoad = true;
    private int mCurrentPageNo = -1;
    private int mPageTotal = 0;

    public SellProductsPresenter( /*Todo: add Model */
                                  @NonNull SellProductsContract.View view) {
        //Todo: add Model
        mView = CheckNonNullUtil.checkNotNull(view, "sellProduct view cannot be null!!");

        mView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadMoreProducts(int loadingFooterPosition) {
        if (mCurrentPageNo + 1 < mPageTotal) {
            mView.setFooterView(true, loadingFooterPosition);

            BackendHelper.getInstance().getSellProducts(mCurrentPageNo + 1,
                    new ResultCallback<ResponseProduct>() {
                        @Override
                        public void onSuccess(ResponseProduct responseProduct) {
                            if (!mView.isActive()) {
                                return;
                            }
                            mView.setFooterView(false, loadingFooterPosition);

                            Log.e(TAG, "before mCurrentPageNo " + mCurrentPageNo + " mPageTotal " + mPageTotal);
                            mCurrentPageNo = responseProduct.getCurrentPageNo();
                            mPageTotal = responseProduct.getPageTotal();
                            Log.e(TAG, "after mCurrentPageNo " + mCurrentPageNo + " mPageTotal " + mPageTotal);

                            processProducts(responseProduct.getProducts());
                        }

                        @Override
                        public void onFailure() {
                            if (!mView.isActive()) {
                                return;
                            }
                            mView.setFooterView(false, loadingFooterPosition);
                            mView.showLoadingProductsError();
                        }
                    });
        }
    }

    @Override
    public void loadProducts(boolean forceUpdate) {
        loadProducts(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadProducts(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mView.setLodingIndicator(true);  // 로딩 인디케이터 표시
        }
        if (forceUpdate) {
            mCurrentPageNo = -1;
            mPageTotal = 0;
            //Todo: 강제 업데이트
        }

        BackendHelper.getInstance().getSellProducts(mCurrentPageNo + 1,
                new ResultCallback<ResponseProduct>() {
                    @Override
                    public void onSuccess(ResponseProduct responseProduct) {
                        if (!mView.isActive()) {
                            return;
                        }
                        if (showLoadingUI) {
                            mView.setLodingIndicator(false);
                        }

                        Log.e(TAG, "before mCurrentPageNo " + mCurrentPageNo + " mPageTotal " + mPageTotal);
                        mCurrentPageNo = responseProduct.getCurrentPageNo();
                        mPageTotal = responseProduct.getPageTotal();
                        Log.e(TAG, "after mCurrentPageNo " + mCurrentPageNo + " mPageTotal " + mPageTotal);

                        processProducts(responseProduct.getProducts());
                    }

                    @Override
                    public void onFailure() {
                        if (!mView.isActive()) {
                            return;
                        }
                        if (showLoadingUI) {
                            mView.setLodingIndicator(false);
                        }
                        mView.showLoadingProductsError();
                    }
                });
    }

    private void processProducts(List<Product> products) {
//        if (products.isEmpty()) {
//            mView.showNoProducts();
//        } else {
        mView.showProducts(products);
//        }
    }

    @Override
    public void editProduct() {
        mView.showEditProduct();
    }

    @Override
    public void detailProductSeller(Product product) {
        mView.showProductDetailForSeller(product);
    }

    @Override
    public void detailProductCustomer(Product product) {
        mView.showProductDetailForCustomer(product);
    }

    @Override
    public void removeProduct(Product product) {
        mView.setProgressbar(true);

        BackendHelper.getInstance().deleteProduct(product.getId(), new ResultCallback() {
            @Override
            public void onSuccess(Object o) {
                mView.setProgressbar(false);
                mView.showSuccessfullyRemovedMessage();
                //Todo: reloading
                mView.refreshProducts();
            }

            @Override
            public void onFailure() {
                mView.setProgressbar(false);
                mView.showFailureRemovedMessage();
            }
        });
    }

    @Override
    public void removeProductAll(List<Product> products) {
        mView.setProgressbar(true);

        BackendHelper.getInstance().deleteSellProducts(products, new ResultCallback() {
            @Override
            public void onSuccess(Object o) {
                mView.setProgressbar(false);
                mView.showSuccessfullyRemovedAllMessage();
                //Todo: reloading
                mView.refreshProducts();
            }

            @Override
            public void onFailure() {
                mView.setProgressbar(false);
                mView.showFailureRemovedAllMessage();
            }
        });
    }

    @Override
    public void updateProductStatus(Product product, int position) {
        mView.setProgressbar(true);

        RequestProduct requestProduct = new RequestProduct();
        requestProduct.setStatus(product.getStatus());

        BackendHelper.getInstance().updateProduct(product.getId(), requestProduct, new ResultCallback() {
            @Override
            public void onSuccess(Object o) {
                mView.setProgressbar(false);
                mView.showSuccessfullyUpdatedStatusMessage();
                //Todo: reloading??
                mView.updateProductStatus(position);
            }

            @Override
            public void onFailure() {
                mView.setProgressbar(false);
                mView.showFailureUpdatedStatusMessage();
            }
        });
    }

    @Override
    public void start() {
        loadProducts(false);
    }
}
