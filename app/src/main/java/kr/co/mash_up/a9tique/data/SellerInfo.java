package kr.co.mash_up.a9tique.data;

import com.google.gson.annotations.SerializedName;

/**
 *  판매자 정보
 */
public class SellerInfo {

    @SerializedName("shop_name")
    private String shopName;

    @SerializedName("shop_info")
    private String shopInfo;

    private String phone;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(String shopInfo) {
        this.shopInfo = shopInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
