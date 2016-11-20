package kr.co.mash_up.a9tique;


import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;

public class ProductImageAddViewHolder extends BaseViewHolder {

    public static ProductImageAddViewHolder newInstance(@NonNull ViewGroup parent){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_product_image_list, parent, false);
        return new ProductImageAddViewHolder(itemView);
    }

    public ProductImageAddViewHolder(View itemView) {
        super(itemView);

        //Todo: add click listener
    }

    @Override
    public void bind(Object o) {

    }
}
