package kr.co.mash_up.a9tique._old.data;

/**
 * Created by CY on 2016. 11. 15..
 */

public class Menu {

    private String title;
    private String label;
    private int viewType;

    public Menu() {
    }

    public Menu(String title, int viewType) {
        this.title = title;
        this.viewType = viewType;
    }

    public Menu(String title, String label, int viewType) {
        this.title = title;
        this.label = label;
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
