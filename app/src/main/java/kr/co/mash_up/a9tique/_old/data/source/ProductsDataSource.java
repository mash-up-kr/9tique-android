package kr.co.mash_up.a9tique._old.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import kr.co.mash_up.a9tique._old.data.Product;

/**
 * Created by Dong on 2017-01-20.
 * Main entry point for accessing products data
 * Model Layer
 */
//Todo: Local DB사용할 때 구현하기
public interface ProductsDataSource {

    /**
     * 상품 리스트 로딩 Callback
     */
    interface LoadProductsCallback {
        void onProductsLoaded(@NonNull List<Product> results);

        void onDataNotAvailable();
    }

    /**
     * 상품 로딩 Callback
     */
    interface GetProductCallback {
        void onProductLoaded(@NonNull Product product);

        void onDataNotAvailable();
    }

    /**
     * Transaction 결과 Callback
     */
    interface ProductTransactionCallback {
        void onSuccess();

        void onFailure();
    }

    /**
     * 상품 리로딩
     */
    void refreshProducts();

    /**
     * 상품 리스트 로딩
     *
     * @param callback 리스트 로딩 결과 Callback
     */
    void getProducts(@NonNull LoadProductsCallback callback);

    /**
     * 상품 상세정보 로딩
     *
     * @param productId 로딩할 상품 id
     * @param callback  로딩 결과 Callback
     */
    void getProduct(@NonNull Long productId, @NonNull GetProductCallback callback);

    /**
     * 상품 생성
     *
     * @param product  생성할 상품 모델
     * @param callback 생성 결과 Callback
     */
    void createProduct(@NonNull Product product, @NonNull ProductTransactionCallback callback);

    /**
     * 상품 수정
     *
     * @param product  수정할 상품 모델
     * @param callback 수정 결과 Callback
     */
    void updateProduct(@NonNull Product product, @NonNull ProductTransactionCallback callback);

    /**
     * 상품 삭제
     *
     * @param productId 삭제할 상품 id
     * @param callback  삭제 결과 Callback
     */
    void deleteProduct(@NonNull Long productId, @NonNull ProductTransactionCallback callback);
}
