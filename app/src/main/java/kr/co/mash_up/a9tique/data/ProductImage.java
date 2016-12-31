package kr.co.mash_up.a9tique.data;

import com.google.gson.annotations.SerializedName;

public class ProductImage {

    @SerializedName("url")
    private String imageUrl;

    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ProductImage(String imagePath) {
        this.imagePath = imagePath;
    }

    public ProductImage() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return imageUrl;
    }
}
