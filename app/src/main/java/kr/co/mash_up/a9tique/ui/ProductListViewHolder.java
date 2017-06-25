package kr.co.mash_up.a9tique.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.databinding.ProductListItemBinding;

/**
 * Created by seokjunjeong on 2017. 6. 24..
 */

public class ProductListViewHolder extends RecyclerView.ViewHolder {
    private ProductListItemBinding mBinding;

    public ProductListViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);
        mBinding = DataBindingUtil.bind(itemView);
        itemView.setOnClickListener(v -> {
            listener.onClick(getAdapterPosition());
        });
    }

    public void bind(Product product) {
        mBinding.setProduct(product);
    }
}
