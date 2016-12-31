package kr.co.mash_up.a9tique.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 판매상품을 나타내는 Model 클래스
 */
public class Product {

    private Long id;

    private String name;

    @SerializedName("brand_name")
    private String brandName;

    private String size;

    private int price;

    private String description;

    @SerializedName("main_category")
    private String mainCategory;

    @SerializedName("sub_category")
    private String subCategory;

    @SerializedName("product_images")
    private List<ProductImage> productImages;

    private Status status;

    @SerializedName("zzim_status")
    private boolean zzimStatus;

    @SerializedName("seller_info")
    private SellerInfo sellerInfo;

    @SerializedName("created_at")
    private long createdAt;

    @SerializedName("updated_at")
    private long updatedAt;

    public enum  Status {
        SELL,  // 판매중
        SOLD_OUT  // 판매완료
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

    public SellerInfo getSellerInfo() {
        return sellerInfo;
    }

    public void setSellerInfo(SellerInfo sellerInfo) {
        this.sellerInfo = sellerInfo;
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

    @Override
    public String toString() {
        return "Product{" +
                "updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", sellerInfo=" + sellerInfo +
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
                '}';
    }
}
