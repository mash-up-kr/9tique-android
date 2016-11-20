package kr.co.mash_up.a9tique;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;


public class ProductImageNormalViewHolder extends BaseViewHolder<ProductImage>{

    public static ProductImageNormalViewHolder newInstance(@NonNull ViewGroup parent){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_normal_product_image_list, parent, false);
        return new ProductImageNormalViewHolder(itemView);
    }

    public ProductImageNormalViewHolder(View itemView) {
        super(itemView);

        //Todo: add click listener
    }

    @Override
    public void bind(ProductImage productImage) {
        //Todo: data bind
    }
}
