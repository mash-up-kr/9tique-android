package kr.co.mash_up.a9tique.ui.products;

import android.content.Intent;

import java.util.List;

import kr.co.mash_up.a9tique.base.BasePresenter;
import kr.co.mash_up.a9tique.base.BaseView;
import kr.co.mash_up.a9tique.data.Product;

/**
 * This specifies the contract between the view and the presenter.
 */
public class ProductsContract {

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

        void detailMineProductSeller(Product product);

        void detailOtherProductSeller(Product product);

        void detailProductCustomer(Product product);
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

        void showMineProductDetailForSeller(Product product);

        void showOtherProductDetailForSeller(Product product);

        void showProductDetailForCustomer(Product product);

        void showSuccessfullyRemovedMessage();

        void showFailureRemovedMessage();

        void showSuccessfullyUpdatedStatusMessage();

        void showFailureUpdatedStatusMessage();

        boolean isActive();
    }
}