package kr.co.mash_up.a9tique;

/**
 * Created by CY on 2016. 12. 25..
 */

public class GoodsDetail {
    private String text;
    private int image;

    public GoodsDetail() {

    }

    public GoodsDetail(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
