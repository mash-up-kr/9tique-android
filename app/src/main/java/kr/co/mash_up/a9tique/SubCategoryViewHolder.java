package kr.co.mash_up.a9tique;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubCategoryViewHolder extends ChildViewHolder<SubCategory> {

    @BindView(R.id.tv_sub_category_name)
    TextView mTvSubCategoryName;

    OnItemClickListener mOnItemClickListener;

    public static SubCategoryViewHolder newInstance(@NonNull ViewGroup parent, OnItemClickListener listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sub_category_list, parent, false);
        return new SubCategoryViewHolder(itemView, listener);
    }

    public SubCategoryViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mOnItemClickListener = listener;
    }

    public void bind(@NonNull final SubCategory subCategory) {
        mTvSubCategoryName.setText(subCategory.getName());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(subCategory.getName());
                }
            }
        });
    }
}
