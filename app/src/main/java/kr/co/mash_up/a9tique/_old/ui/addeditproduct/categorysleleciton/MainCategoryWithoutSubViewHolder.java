package kr.co.mash_up.a9tique._old.ui.addeditproduct.categorysleleciton;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.data.MainCategory;
import kr.co.mash_up.a9tique.data.SubCategory;
import kr.co.mash_up.a9tique._old.ui.OnItemClickListener;

/**
 * Created by Dong on 2016-11-20.
 */

public class MainCategoryWithoutSubViewHolder extends ParentViewHolder<MainCategory, SubCategory> {

    @BindView(R.id.tv_main_category_name)
    TextView mTvMainCategoryName;

    @Nullable
    OnItemClickListener mOnItemClickListener;

    public static MainCategoryWithoutSubViewHolder newInstance(@NonNull ViewGroup parent, OnItemClickListener listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_without_sub_category_list, parent, false);
        return new MainCategoryWithoutSubViewHolder(itemView, listener);
    }

    public MainCategoryWithoutSubViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mOnItemClickListener = listener;
    }

    public MainCategoryWithoutSubViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull final MainCategory mainCategory) {
        mTvMainCategoryName.setText(mainCategory.getName());

        itemView.setOnClickListener(view -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(mainCategory.getName(), getAdapterPosition());
            }
        });
    }
}
