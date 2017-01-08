package kr.co.mash_up.a9tique.data.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kr.co.mash_up.a9tique.data.Product;
import okhttp3.MultipartBody;

public class RequestProduct {

    @SerializedName("name")
    String name;

    @SerializedName("brand_name")
    String brandName;

    @SerializedName("size")
    String size;

    @SerializedName("price")
    Integer price;

    @SerializedName("description")
    String description;

    @SerializedName("status")
    Product.Status status;

    @SerializedName("main_category")
    String mainCategory;

    @SerializedName("sub_category")
    String subCategory;

    @SerializedName("files")
    List<MultipartBody.Part> files;

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

    public List<MultipartBody.Part> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartBody.Part> files) {
        this.files = files;
    }
}
