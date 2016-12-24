package kr.co.mash_up.a9tique;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by CY on 2016. 11. 15..
 */


public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.MyViewHolder> {

    private List<Setting> settingList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView menu;
        public EditText edittxt;

        public MyViewHolder(View view) {
            super(view);
            menu = (TextView) view.findViewById(R.id.menu);
            edittxt = (EditText) view.findViewById(R.id.edittxt);
        }
    }

    public SellerAdapter(List<Setting> settingList) {
        this.settingList = settingList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_list_row2, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Setting setting = settingList.get(position);
        holder.menu.setText(setting.getMenu());
        switch (position) {
            case 0:
                holder.edittxt.setHint("판매자 성함을 입력해 주세요.");
                break;
            case 1:
                holder.edittxt.setHint("등록하시는 매장명을 입력해 주세요.");
                break;
            case 2:
                holder.edittxt.setHint("매장에 대한 정보를 입력해 주세요.");
                break;
            case 3:
                holder.edittxt.setHint("상품 문의가 가능한 연락처를 남겨 주세요.");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return settingList.size();
    }
}
