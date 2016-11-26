package kr.co.mash_up.a9tique;


import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;

public class ProductImageFooterViewHolder extends BaseViewHolder {

    private OnItemClickListener mOnItemClickListener;

    public static ProductImageFooterViewHolder newInstance(@NonNull ViewGroup parent, @NonNull OnItemClickListener listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_footer_product_image_list, parent, false);
        return new ProductImageFooterViewHolder(itemView, listener);
    }

    public ProductImageFooterViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);

        mOnItemClickListener = listener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onClick(null);
            }
        });
    }

    @Override
    public void bind(Object o) {

    }
}
