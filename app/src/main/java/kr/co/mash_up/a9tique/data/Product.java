package kr.co.mash_up.a9tique.data;

/**
 * Created by seokjunjeong on 2017. 6. 24..
 */

public class Product {
    public String imgUrl;
    public String brandName;
    public int price;
    public String category;
    public String size;
    public boolean myWishItem;
    public int wishCount;

    public Product(String imgUrl, String brandName, int price, String category, String size, boolean myWishItem, int wishCount) {
        this.imgUrl = imgUrl;
        this.brandName = brandName;
        this.price = price;
        this.category = category;
        this.size = size;
        this.myWishItem = myWishItem;
        this.wishCount = wishCount;
    }
}
