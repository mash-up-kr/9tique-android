package kr.co.mash_up.a9tique.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import kr.co.mash_up.a9tique.databinding.DashboardItemBinding;

/**
 * Created by seokjunjeong on 2017. 6. 6..
 */

public class DashboardViewHolder extends RecyclerView.ViewHolder {
    private DashboardItemBinding mBinding;

    public DashboardViewHolder(View itemView, OnItemClickListener l) {
        super(itemView);
        mBinding = DataBindingUtil.bind(itemView);
        itemView.setOnClickListener(v -> {
            l.onClick(getAdapterPosition());
        });
    }

    public void bind(String str) {
        mBinding.setSample(str);
    }
}
