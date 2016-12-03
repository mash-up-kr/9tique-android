package kr.co.mash_up.a9tique;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

/**
 * Created by Dong on 2016-11-20.
 */

public class MainCategory implements Parent<SubCategory> {

    private String mName;
    private List<SubCategory> mSubCategories;

    public MainCategory(String name, List<SubCategory> subCategories) {
        mName = name;
        mSubCategories = subCategories;
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<SubCategory> getChildList() {
        return mSubCategories;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public SubCategory getSubCategory(int position) {
        return mSubCategories.get(position);
    }
}