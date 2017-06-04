package kr.co.mash_up.a9tique._old.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 판매점 정보
 */
public class Shop implements Parcelable {

    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("info")
    private String info;

    @SerializedName("phone")
    private String phone;

    @SerializedName("kakao_open_chat_url")
    private String kakaoOpenChatUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKakaoOpenChatUrl() {
        return kakaoOpenChatUrl;
    }

    public void setKakaoOpenChatUrl(String kakaoOpenChatUrl) {
        this.kakaoOpenChatUrl = kakaoOpenChatUrl;
    }

    public Shop() {
    }

    public Shop(String name, String info, String phone) {
        this.name = name;
        this.info = info;
        this.phone = phone;
    }

    protected Shop(Parcel in) {
        id = in.readLong();
        name = in.readString();
        info = in.readString();
        phone = in.readString();
        kakaoOpenChatUrl = in.readString();
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(info);
        parcel.writeString(phone);
        parcel.writeString(kakaoOpenChatUrl);
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", phone='" + phone + '\'' +
                ", kakaoOpenChatUrl='" + kakaoOpenChatUrl + '\'' +
                '}';
    }
}