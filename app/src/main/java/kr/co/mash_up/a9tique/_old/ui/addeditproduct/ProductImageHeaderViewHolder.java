package kr.co.mash_up.a9tique._old.ui.addeditproduct;


import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique._old.base.ui.BaseViewHolder;
import  kr.co.mash_up.a9tique._old.ui.addeditproduct.ProductImageListAdapter.OnItemClickListener;

public class ProductImageHeaderViewHolder extends BaseViewHolder {

    private OnItemClickListener mOnItemClickListener;

    public static ProductImageHeaderViewHolder newInstance(@NonNull ViewGroup parent, @NonNull OnItemClickListener listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_footer_product_image_list, parent, false);
        return new ProductImageHeaderViewHolder(itemView, listener);
    }

    public ProductImageHeaderViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);

        mOnItemClickListener = listener;
    }

    @Override
    public void bind(Object o) {
        itemView.setOnClickListener(view -> {
                mOnItemClickListener.onClick("");
        });
    }
}
