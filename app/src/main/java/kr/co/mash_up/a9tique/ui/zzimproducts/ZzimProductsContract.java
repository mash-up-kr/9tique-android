package kr.co.mash_up.a9tique.ui.zzimproducts;


import android.content.Intent;
import android.widget.ImageView;

import java.util.List;

import kr.co.mash_up.a9tique.base.BasePresenter;
import kr.co.mash_up.a9tique.base.BaseView;
import kr.co.mash_up.a9tique.data.Product;


/**
 * This specifies the contract between the view and the presenter.
 */
public interface ZzimProductsContract {

    /**
     * View -> Presenter
     */
    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode, Intent data);  // 찜목록에서 제거 했을 경우

        void loadProducts(boolean forceUpdate);

        void loadMoreProducts(int loadingFooterPosition);

        void detailProduct(Product product, ImageView shareImageView, String transitionName);

        void onClickRemoveProduct(Product product, int position);

        void removeProduct(Product product, int position);

        void onClickInquireProduct(Product product, int position);

        void callPhone(Product product);

        void sendMessage(Product product);
    }

    /**
     * Presenter -> View
     */
    interface View extends BaseView<ZzimProductsContract.Presenter> {

        boolean isActive();

        void showLoadingIndicator(boolean active);

        void showProgressbar(boolean active);

        void showFooterView(boolean active, int position);

        void showLoadedProducts(List<Product> products);

        void showLoadedMoreProducts(List<Product> products);

        void showLoadingProductsError();

        void refreshProducts();

        void showNoProducts();

        void showProductDetail(Product product, ImageView shareImageView, String transitionName);

        void showDialogRemoveProduct(Product product, int position);

        void removeProduct(int position);

        void showSuccessfullyRemovedMessage();

        void showFailureRemovedMessage();

        void showDialogInquire(Product product, int position);

        void showCallPhone(String phoneNumber);

        void showSendMessage(String phoneNumber);
    }
}