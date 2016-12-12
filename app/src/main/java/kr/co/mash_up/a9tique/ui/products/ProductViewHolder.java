package kr.co.mash_up.a9tique.ui.products;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;
import kr.co.mash_up.a9tique.ui.OnItemClickListener;

/**
 * Created by Dong on 2016-11-12.
 */

public class ProductViewHolder extends BaseViewHolder<Product> {

    OnItemClickListener<Product> mOnItemClickListener;

    public static ProductViewHolder newInstance(@NonNull ViewGroup parent, OnItemClickListener<Product> listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_list, parent, false);
        return new ProductViewHolder(itemView, listener);
    }

    public ProductViewHolder(View itemView, OnItemClickListener<Product> listener) {
        super(itemView);

        mOnItemClickListener = listener;
    }

    @Override
    public void bind(final Product product) {
        //Todo: model binding

        itemView.setOnClickListener(view -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(product);
            }
        });
    }
}
