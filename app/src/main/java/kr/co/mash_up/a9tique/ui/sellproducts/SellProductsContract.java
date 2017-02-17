package kr.co.mash_up.a9tique.ui.sellproducts;

import java.util.List;

import kr.co.mash_up.a9tique.base.BasePresenter;
import kr.co.mash_up.a9tique.base.BaseView;
import kr.co.mash_up.a9tique.data.Product;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface SellProductsContract {

    /**
     * View -> Presenter
     */
    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadProducts(boolean forceUpdate);

        void loadMoreProducts(int loadingFooterPosition);

        void editProduct(Product product);

        void detailProductSeller(Product product);

        void detailProductCustomer(Product product);

        void removeProduct(Product product, int position);

        void removeProductSelected(List<Product> products);

        void updateProductStatus(Product product, int position);
    }

    /**
     * Presenter -> View
     */
    interface View extends BaseView<Presenter> {

        void showLoadingIndicator(boolean active);

        void showProgressbar(boolean active);

        void showFooterView(boolean active, int position);

        void showLoadedProducts(List<Product> products, int elementsTotal);

        void showLoadedMoreProducts(List<Product> products);

        void showLoadingProductsError();

        void refreshProducts();

        void showNoProducts();

        void showProductDetailForSeller(Product product);

        void showProductDetailForCustomer(Product product);

        void removeProduct(int position);

        void updateProductStatus(int position);

        void showEditProduct(Product product);

        void showSuccessfullyUpdateMessage();

        void showFailureUpdateMessage();

        void showSuccessfullyRemovedMessage();

        void showFailureRemovedMessage();

        void showSuccessfullyRemovedSelectedMessage();

        void showFailureRemovedSelectedMessage();

        void showSuccessfullyUpdatedStatusMessage();

        void showFailureUpdatedStatusMessage();

        void showRemoveProductSelectedErrorMessage();

        boolean isActive();
    }
}