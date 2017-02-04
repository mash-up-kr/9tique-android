package kr.co.mash_up.a9tique.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 판매상품을 나타내는 Model 클래스
 */
public class Product implements Parcelable {

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("brand_name")
    private String brandName;

    @Expose
    @SerializedName("size")
    private String size;

    @Expose
    @SerializedName("price")
    private int price;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("main_category")
    private String mainCategory;

    @Expose
    @SerializedName("sub_category")
    private String subCategory;

    @Expose
    @SerializedName("product_images")
    private List<ProductImage> productImages;

    @Expose
    @SerializedName("status")
    private Status status;

    @Expose(serialize = false)
    @SerializedName("zzim_status")
    private boolean zzimStatus;

    @Expose(serialize = false)
    @SerializedName("shop")
    private Shop shop;

    @Expose(serialize = false)
    @SerializedName("created_at")
    private long createdAt;

    @Expose(serialize = false)
    @SerializedName("updated_at")
    private long updatedAt;

    @Expose(serialize = false)
    @SerializedName("seller")
    private boolean seller;

    public enum Status {
        SELL,  // 판매중
        SOLD_OUT  // 판매완료
    }

    private int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isZzimStatus() {
        return zzimStatus;
    }

    public void setZzimStatus(boolean zzimStatus) {
        this.zzimStatus = zzimStatus;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isSeller() {
        return seller;
    }

    public void setSeller(boolean seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "Product{" +
                "updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", shop=" + shop +
                ", zzimStatus=" + zzimStatus +
                ", status=" + status +
                ", productImages=" + productImages +
                ", subCategory='" + subCategory + '\'' +
                ", mainCategory='" + mainCategory + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", size='" + size + '\'' +
                ", brandName='" + brandName + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", seller=" + seller +
                '}';
    }

    public Product() {
    }

    public Product(int viewType) {
        this.viewType = viewType;
    }

    public Product(Long id, String name, String brandName, String size, int price, String description, String mainCategory, String subCategory, List<ProductImage> productImages, Status status, boolean zzimStatus, Shop shop, long createdAt, long updatedAt, boolean seller) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.size = size;
        this.price = price;
        this.description = description;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.productImages = productImages;
        this.status = status;
        this.zzimStatus = zzimStatus;
        this.shop = shop;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.seller = seller;
    }

    protected Product(Parcel in) {
        id = in.readLong();
        name = in.readString();
        brandName = in.readString();
        size = in.readString();
        price = in.readInt();
        description = in.readString();
        mainCategory = in.readString();
        subCategory = in.readString();
        productImages = in.createTypedArrayList(ProductImage.CREATOR);
        if (in.readString().equals(Status.SELL.name())) {
            status = Status.SELL;
        } else {
            status = Status.SOLD_OUT;
        }
        zzimStatus = in.readByte() != 0;
        shop = in.readParcelable(Shop.class.getClassLoader());
        createdAt = in.readLong();
        updatedAt = in.readLong();
        seller = in.readByte() != 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
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
        parcel.writeString(brandName);
        parcel.writeString(size);
        parcel.writeInt(price);
        parcel.writeString(description);
        parcel.writeString(mainCategory);
        parcel.writeString(subCategory);
        parcel.writeTypedList(productImages);
        parcel.writeString(status.name());
        parcel.writeByte((byte) (zzimStatus ? 1 : 0));
        parcel.writeParcelable(shop, i);
        parcel.writeLong(createdAt);
        parcel.writeLong(updatedAt);
        parcel.writeByte((byte) (seller ? 1 : 0));
    }
}
