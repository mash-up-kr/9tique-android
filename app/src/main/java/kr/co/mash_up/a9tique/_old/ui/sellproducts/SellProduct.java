package kr.co.mash_up.a9tique._old.ui.sellproducts;

import kr.co.mash_up.a9tique._old.data.Product;

/**
 * Created by Dong on 2017-02-15.
 */

public class SellProduct extends Product {

    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public SellProduct(){
    }

    public SellProduct(Product product) {
        super(product.getId(), product.getName(), product.getBrandName(), product.getSize(), product.getPrice(),
                product.getDescription(), product.getMainCategory(), product.getSubCategory(),
                product.getProductImages(), product.getStatus(), product.isZzimStatus(), product.getShop(),
                product.getCreatedAt(), product.getUpdatedAt(), product.isSeller());
        this.checked = false;
    }
}
