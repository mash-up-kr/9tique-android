package kr.co.mash_up.a9tique.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import kr.co.mash_up.a9tique.data.Brand;
import kr.co.mash_up.a9tique.databinding.BrandListItemBinding;

/**
 * Created by seokjunjeong on 2017. 7. 23..
 */

public class BrandListViewHolder extends RecyclerView.ViewHolder {
    private BrandListItemBinding mBinding;


    public BrandListViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);
        itemView.setOnClickListener(v -> listener.onClick(getAdapterPosition()));
        mBinding = DataBindingUtil.bind(itemView);
    }

    public void bind(Brand brand){
        mBinding.setBrand(brand);
    }
}
