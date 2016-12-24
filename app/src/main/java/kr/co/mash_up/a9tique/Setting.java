package kr.co.mash_up.a9tique;

/**
 * Created by CY on 2016. 11. 15..
 */

public class Setting {
    private String menu;
    private int imgId;
    private String label;

    public Setting() {
    }

    public Setting(String menu, int imgId)
    {
        this.menu = menu;
        this.imgId = imgId;
    }

    public Setting(String menu, String label) {
        this.menu = menu;
        this.label = label;
    }

    public Setting(String menu) {
        this.menu = menu;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
