package kr.co.mash_up.a9tique._old.common.eventbus;

import kr.co.mash_up.a9tique._old.data.Product;


public class EventRefreshProduct {

    private Product mProduct;

    public EventRefreshProduct(Product product) {
        mProduct = product;
    }

    public Product getProduct() {
        return mProduct;
    }
}
