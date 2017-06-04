package kr.co.mash_up.a9tique._old.data.remote;

import com.google.gson.annotations.SerializedName;


public class RequestSeller {

    @SerializedName("seller_name")
    private String sellerName;

    @SerializedName("shop_name")
    private String shopName;

    @SerializedName("shop_info")
    private String shopInfo;

    @SerializedName("shop_phone")
    private String shopPhone;

    public RequestSeller(String sellerName, String shopName, String shopInfo, String shopPhone) {
        this.sellerName = sellerName;
        this.shopName = shopName;
        this.shopInfo = shopInfo;
        this.shopPhone = shopPhone;
    }
}
