package kr.co.mash_up.a9tique.ui.sellproducts;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.List;

import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.RequestProduct;
import kr.co.mash_up.a9tique.data.remote.ResponseProduct;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.ui.addeditproduct.AddEditProductActivity;
import kr.co.mash_up.a9tique.util.CheckNonNullUtil;

/**
 * Created by Dong on 2017-01-20.
 */

public class SellProductsPresenter implements SellProductsContract.Presenter {

    public static final String TAG = SellProductsPresenter.class.getSimpleName();

    //Todo: add Model Layer

    //Todo: WeakReference 적용
//    private WeakReference<SellProductsContract.View> mWeakView;  // View Layer
    private SellProductsContract.View mView;  // View Layer

    private boolean mFirstLoad = true;
    private int mCurrentPageNo = -1;
    private int mPageTotal = 0;

//    @UiThread
//    @Nullable
//    class SellerProductPresenter<V extends BaseView> implements SellProductsContract.Presenter<V>{
//
//    }
//    public V getView()
//    public SellProductsContract.View getView() {
//        return mWeakView == null ? null : mWeakView.get();
//    }
//
//    @UiThread
//    public boolean isViewAttached() {
//        return mWeakView != null && mWeakView.get() != null;
//    }
//
//    public void detachView() {
//        if (mWeakView != null) {
//            mWeakView.clear();
//            mWeakView = null;
//        }
//    }

    public SellProductsPresenter( /*Todo: add Model Layer */
                                  @NonNull SellProductsContract.View view) {
        //Todo: add Model Layer
//        mWeakView = new WeakReference<>(CheckNonNullUtil.checkNotNull(view, " sellProduct view cannot be null!"));
//        mWeakView.get().setPresenter(this);

        mView = CheckNonNullUtil.checkNotNull(view, " sellProduct view cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        switch (requestCode) {
            case AddEditProductActivity.REQUEST_CODE_EDIT_PRODUCT:
                if (resultCode == Activity.RESULT_OK) {
                    mView.showSuccessfullyUpdateMessage();
                    mView.refreshProducts();
                } else {
                    mView.showFailureUpdateMessage();
                }
                break;
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
            mView.showLoadingIndicator(true);  // 로딩 인디케이터 표시
        }
        if (forceUpdate) {
            mCurrentPageNo = -1;
            mPageTotal = 0;
            //Todo: Model Layer Cache clear
        }

        BackendHelper.getInstance().getSellProducts(mCurrentPageNo + 1,
                new ResultCallback<ResponseProduct>() {
                    @Override
                    public void onSuccess(ResponseProduct responseProduct) {
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

            BackendHelper.getInstance().getSellProducts(mCurrentPageNo + 1,
                    new ResultCallback<ResponseProduct>() {
                        @Override
                        public void onSuccess(ResponseProduct responseProduct) {
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
    public void editProduct(Product product) {
        mView.showEditProduct(product);
    }

    @Override
    public void detailProduct(Product product) {
        mView.showProductDetail(product);
    }

    @Override
    public void onClickRemove(Product product, int position) {
        mView.showDialogRemoveProduct(product, position);
    }

    @Override
    public void removeProduct(Product product, int position) {
        mView.showProgressbar(true);

        BackendHelper.getInstance().deleteProduct(product.getId(), new ResultCallback() {
            @Override
            public void onSuccess(Object o) {
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
    public void removeProductSelected(List<Product> products) {
        if (products.size() == 0) {
            mView.showRemoveProductSelectedErrorMessage();
            return;
        }
        mView.showProgressbar(true);

        BackendHelper.getInstance().deleteSellProducts(products, new ResultCallback() {
            @Override
            public void onSuccess(Object o) {
                if (!mView.isActive()) {
                    return;
                }
                mView.showProgressbar(false);
                mView.showSuccessfullyRemovedSelectedMessage();
                mView.refreshProducts();
            }

            @Override
            public void onFailure() {
                if (!mView.isActive()) {
                    return;
                }
                mView.showProgressbar(false);
                mView.showFailureRemovedSelectedMessage();
            }
        });
    }

    @Override
    public void updateProductStatus(Product product, int position) {
        mView.showProgressbar(true);

        RequestProduct requestProduct = new RequestProduct();
        requestProduct.setStatus(product.getStatus());

        BackendHelper.getInstance().updateProduct(product.getId(), requestProduct, new ResultCallback() {
            @Override
            public void onSuccess(Object o) {
                if (!mView.isActive()) {
                    return;
                }
                mView.showProgressbar(false);
                mView.showSuccessfullyUpdatedStatusMessage();
                mView.updateProductStatus(position);
            }

            @Override
            public void onFailure() {
                if (!mView.isActive()) {
                    return;
                }
                mView.showProgressbar(false);
                mView.showFailureUpdatedStatusMessage();
            }
        });
    }

    @Override
    public void onCheckedProductSelectAll(boolean checked) {
        mView.showProductSelectAll(checked);
    }

    @Override
    public void onClickRemoveCheckedProducts() {
        mView.showDialogRemoveCheckedProducts();
    }

    @Override
    public void start() {
//        loadProducts(false);  //Todo: Model Layer 추가하고 이걸로 사용하기
        loadProducts(true);
    }
}