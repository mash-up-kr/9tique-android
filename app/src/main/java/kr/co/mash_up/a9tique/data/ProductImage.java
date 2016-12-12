package kr.co.mash_up.a9tique.data;

public class ProductImage {

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
}
