package kr.co.mash_up.a9tique.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.data.Brand;

/**
 * Created by seokjunjeong on 2017. 7. 23..
 */

public class BrandListAdapter extends RecyclerView.Adapter<BrandListViewHolder> {
    private ArrayList<Brand> mData;
    private OnItemClickListener mListener;

    public BrandListAdapter(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public BrandListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brand_list_item, parent, false);
        BrandListViewHolder viewHolder = new BrandListViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BrandListViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }


    public void setData(ArrayList<Brand> data) {
        mData = data;
    }
}
