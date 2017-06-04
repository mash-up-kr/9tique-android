package kr.co.mash_up.a9tique._old.ui.addeditproduct.categorysleleciton;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.List;

import kr.co.mash_up.a9tique._old.data.MainCategory;
import kr.co.mash_up.a9tique._old.data.SubCategory;
import kr.co.mash_up.a9tique._old.ui.OnItemClickListener;


public class CategoryListAdapter extends ExpandableRecyclerAdapter<MainCategory, SubCategory, MainCategoryWithoutSubViewHolder, SubCategoryViewHolder> {

    public static final int PARENT_WITHOUT_CHILD = 0;
    public static final int PARENT_WITH_CHILD = 1;
    public static final int CHILD = 2;

    LayoutInflater mLayoutInflater;
    List<MainCategory> mMainCategories;

    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public CategoryListAdapter(Context context, @NonNull List<MainCategory> parentList) {
        super(parentList);
        mMainCategories = parentList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @UiThread
    @NonNull
    @Override
    public MainCategoryWithoutSubViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
//        View view;
        switch (viewType) {
            default:
            case PARENT_WITHOUT_CHILD:
//                view = mLayoutInflater.inflate(R.layout.item_main_without_sub_category_list, parentViewGroup, false);
                return MainCategoryWithoutSubViewHolder.newInstance(parentViewGroup, mOnItemClickListener);
            case PARENT_WITH_CHILD:
//                view = mLayoutInflater.inflate(R.layout.item_main_with_sub_category_list, parentViewGroup, false);
                return MainCategoryWithSubViewHolder.newInstance(parentViewGroup);
        }
    }

    @UiThread
    @NonNull
    @Override
    public SubCategoryViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
//        View view;
        switch (viewType) {
            default:
            case CHILD:
//                view = mLayoutInflater.inflate(R.layout.item_sub_category_list, childViewGroup, false);
                return SubCategoryViewHolder.newInstance(childViewGroup, mOnItemClickListener);
        }
    }

    @UiThread
    @Override
    public void onBindParentViewHolder(@NonNull MainCategoryWithoutSubViewHolder parentViewHolder, int parentPosition, @NonNull MainCategory parent) {
        parentViewHolder.bind(parent);
    }

    @UiThread
    @Override
    public void onBindChildViewHolder(@NonNull SubCategoryViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull SubCategory child) {
        childViewHolder.bind(child);
    }

    @Override
    public int getParentViewType(int parentPosition) {
        if (mMainCategories.get(parentPosition).getChildList().size() == 0) {
            return PARENT_WITHOUT_CHILD;
        }
        return PARENT_WITH_CHILD;
    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
        return CHILD;
    }

    @Override
    public boolean isParentViewType(int viewType) {
        return viewType == PARENT_WITH_CHILD || viewType == PARENT_WITHOUT_CHILD;
    }
}
