package kr.co.mash_up.a9tique.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.co.mash_up.a9tique.R;

/**
 * Created by seokjunjeong on 2017. 6. 6..
 */

public class DashboardAdapter extends RecyclerView.Adapter<DashboardViewHolder> {
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<String> mData;

    public DashboardAdapter(@NonNull OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        mData = new ArrayList<>();
    }

    public void setData(ArrayList<String> data) {
        mData = data;
    }

    public void addData(String str) {
        mData.add(str);
    }


    @Override
    public DashboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_item, parent, false);
        DashboardViewHolder dashboardViewHolder =
                new DashboardViewHolder(view, mOnItemClickListener);
        return dashboardViewHolder;
    }

    @Override
    public void onBindViewHolder(DashboardViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
