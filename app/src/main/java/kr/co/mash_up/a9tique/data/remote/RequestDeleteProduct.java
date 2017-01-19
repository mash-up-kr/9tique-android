package kr.co.mash_up.a9tique.data.remote;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import kr.co.mash_up.a9tique.data.Product;

/**
 * Created by Dong on 2017-01-19.
 */

public class RequestDeleteProduct {

    @SerializedName("products")
    List<RequestProduct> requestProducts;

    public RequestDeleteProduct(List<Product> products) {
        this.requestProducts = new ArrayList<>();

        RequestProduct requestProduct;
        for (int i = 0; i < products.size(); i++) {
            requestProduct = new RequestProduct(products.get(i).getId());
            requestProducts.add(requestProduct);
        }
    }
}
