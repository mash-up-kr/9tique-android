package kr.co.mash_up.a9tique.data;

import com.bignerdranch.expandablerecyclerview.model.Parent;

/**
 * Created by Dong on 2016-11-20.
 */
public class MainCategory implements Parent<SubCategory> {

    private String mName;
    private java.util.List<SubCategory> mSubCategories;

    public MainCategory(String name, java.util.List<SubCategory> subCategories) {
        mName = name;
        mSubCategories = subCategories;
    }

    public String getName() {
        return mName;
    }

    @Override
    public java.util.List<SubCategory> getChildList() {
        return mSubCategories;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public SubCategory getSubCategory(int position) {
        return mSubCategories.get(position);
    }

    // Todo: DB에서 find
    public enum List {
        NEW,  // 신상품
        OUTER,  // 아우터
        TOP,  // 상의
        BOTTOM,  // 하의
        CAP,  //모자
        SHOSE  //신발
    }
}
