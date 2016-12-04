package kr.co.mash_up.a9tique;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;

/**
 * Created by Dong on 2016-11-12.
 */

public class ProductViewHolder extends BaseViewHolder<Product> {

    public static ProductViewHolder newInstance(@NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_list, parent, false);
        return new ProductViewHolder(itemView);
    }

    public ProductViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(final Product product) {
        //Todo: model binding
    }
}
