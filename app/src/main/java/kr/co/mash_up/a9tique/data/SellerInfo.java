package kr.co.mash_up.a9tique.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 *  판매자 정보
 */
public class SellerInfo implements Parcelable {

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

    public SellerInfo(){}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(shopName);
        parcel.writeString(shopInfo);
        parcel.writeString(phone);
    }

    protected SellerInfo(Parcel in) {
        shopName = in.readString();
        shopInfo = in.readString();
        phone = in.readString();
    }

    public static final Creator<SellerInfo> CREATOR = new Creator<SellerInfo>() {
        @Override
        public SellerInfo createFromParcel(Parcel in) {
            return new SellerInfo(in);
        }

        @Override
        public SellerInfo[] newArray(int size) {
            return new SellerInfo[size];
        }
    };
}
