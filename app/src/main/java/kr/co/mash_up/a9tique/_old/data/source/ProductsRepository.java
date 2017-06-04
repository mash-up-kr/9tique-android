package kr.co.mash_up.a9tique._old.data.source;

import android.support.annotation.NonNull;

import kr.co.mash_up.a9tique._old.data.Product;
import kr.co.mash_up.a9tique._old.data.remote.BackendHelper;
import kr.co.mash_up.a9tique._old.util.CheckNonNullUtil;

/**
 * Created by Dong on 2017-01-20.
 * Concrete implementation to load tasks from the data sources into a cache.
 * 인메모리 캐시, 디스크 캐시(Local DB) 이용
 */
//Todo: Local DB사용할 때 구현하기
public class ProductsRepository implements ProductsDataSource {

    private static ProductsRepository instance = null;

    private final BackendHelper mBackendHelper;

    private boolean mCacheIsDirty = false;  // false면 캐시된 상태, true면 server request한다.

    public static ProductsRepository getInstance() {
        if (instance == null) {
            return new ProductsRepository();
        }
        return instance;
    }

    private ProductsRepository() {
        mBackendHelper = BackendHelper.getInstance();
    }

    public static void destroyInstance() {
        instance = null;
    }

    @Override
    public void refreshProducts() {
        mCacheIsDirty = true;
    }

    @Override
    public void getProducts(@NonNull LoadProductsCallback callback) {
        CheckNonNullUtil.checkNotNull(callback);
    }

    @Override
    public void getProduct(@NonNull Long productId, @NonNull GetProductCallback callback) {

    }

    @Override
    public void createProduct(@NonNull Product product, @NonNull ProductTransactionCallback callback) {

    }

    @Override
    public void updateProduct(@NonNull Product product, @NonNull ProductTransactionCallback callback) {

    }

    @Override
    public void deleteProduct(@NonNull Long productId, @NonNull ProductTransactionCallback callback) {

    }
}
