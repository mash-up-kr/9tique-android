package kr.co.mash_up.a9tique._old.ui.products;

import android.content.Intent;
import android.widget.ImageView;

import java.util.List;

import kr.co.mash_up.a9tique._old.base.BasePresenter;
import kr.co.mash_up.a9tique._old.base.BaseView;
import kr.co.mash_up.a9tique._old.data.Product;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface ProductsContract {

    /*
    상품 클릭했을 때 -> detail
    load, loadMore

    fab 클릭했을 때 -> activity에서하고 지금은 분리 안하는 걸로
    셋팅 -> activity에서하고 지금은 분리 안하는 걸로
    찜리스트 -> activity에서하고 지금은 분리 안하는 걸로
     */

    /**
     * View -> Presenter
     */
    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode, Intent data);

        void loadProducts(boolean forceUpdate);

        void loadMoreProducts(int loadingFooterPosition);

        void detailMineProductSeller(Product product, ImageView shareImageView, String transitionName);

        void detailOtherProductSeller(Product product, ImageView shareImageView, String transitionName);

        void detailProductCustomer(Product product, ImageView shareImageView, String transitionName);
    }

    /**
     * Presenter -> View
     */
    interface View extends BaseView<ProductsContract.Presenter> {

        void showLoadingIndicator(boolean active);

        void showFooterView(boolean active, int position);

        void showLoadedProducts(List<Product> products, int elementsTotal);

        void showLoadedMoreProducts(List<Product> products);

        void showLoadingProductsError();

        void refreshProducts();

        void showNoProducts();

        void showMineProductDetailForSeller(Product product, ImageView shareImageView, String transitionName);

        void showOtherProductDetailForSeller(Product product, ImageView shareImageView, String transitionName);

        void showProductDetailForCustomer(Product product, ImageView shareImageView, String transitionName);

        void showSuccessfullyRemovedMessage();

        void showFailureRemovedMessage();

        void showSuccessfullyUpdatedStatusMessage();

        void showFailureUpdatedStatusMessage();

        boolean isActive();
    }
}