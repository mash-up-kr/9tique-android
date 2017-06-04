package kr.co.mash_up.a9tique._old.data.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kr.co.mash_up.a9tique._old.data.Product;
import kr.co.mash_up.a9tique._old.data.ProductImage;

public class RequestProduct {

    @SerializedName("id")
    Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("brand_name")
    private String brandName;

    @SerializedName("size")
    private String size;

    @SerializedName("price")
    private Integer price;

    @SerializedName("description")
    private String description;

    @SerializedName("status")
    private Product.Status status;

    @SerializedName("main_category")
    private String mainCategory;

    @SerializedName("sub_category")
    private String subCategory;

    @SerializedName("product_images")
    private List<ProductImage> productImages;

    public RequestProduct() {
    }

    public RequestProduct(Long id) {
        this.id = id;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product.Status getStatus() {
        return status;
    }

    public void setStatus(Product.Status status) {
        this.status = status;
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
}
