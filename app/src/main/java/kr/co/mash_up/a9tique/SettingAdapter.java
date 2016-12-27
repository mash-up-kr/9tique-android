package kr.co.mash_up.a9tique;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by CY on 2016. 11. 15..
 */


public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.MyViewHolder> {

    private List<Setting> settingList;
    OnItemClickListener mItemClickListener;

    // setting adapter
    public SettingAdapter(List<Setting> settingList) {
        this.settingList = settingList;
    }

    // view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView menu;
        public ImageView imageView;
        public TextView label;

        public MyViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.image_id);
            this.label = (TextView) view.findViewById(R.id.label);
            menu = (TextView) view.findViewById(R.id.menu);
            menu.setOnClickListener(this);
        }

        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_list_row, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Setting setting = settingList.get(position);
        holder.menu.setText(setting.getMenu());
        holder.label.setText(setting.getLabel());
        holder.imageView.setImageResource(setting.getImgId());
    }

    @Override
    public int getItemCount() {
        return settingList.size();
    }

    // item click listener
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}

