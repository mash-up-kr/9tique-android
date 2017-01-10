package kr.co.mash_up.a9tique;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by CY on 2016. 11. 15..
 */


public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.MyViewHolder> {

    private List<Setting> settingList;
    private Context mContext;

    public SettingAdapter(List<Setting> settingList) {
        this.settingList = settingList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.menu)
        TextView menu;
        @BindView(R.id.image_id)
        ImageView imageView;
        @BindView(R.id.label)
        TextView label;

        public MyViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.menu)
        void onClick(View v) {
            switch (getAdapterPosition()) {
                case 0:
                    Intent intent = new Intent(mContext, InquireActivity.class);
                    mContext.startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(mContext, AgreementActivity.class);
                    mContext.startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(mContext, LicenseActivity.class);
                    mContext.startActivity(intent);
                    break;
                case 4:
                    final SellerDialog dlgSeller = new SellerDialog(mContext);
                    dlgSeller.show();
                    break;
                case 5:
                    intent = new Intent(mContext, About9tiqueActivity.class);
                    mContext.startActivity(intent);
                    break;
                case 6:
                    final LogoutDialog dlgLogout = new LogoutDialog(mContext);
                    dlgLogout.show();
                    break;
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

}

