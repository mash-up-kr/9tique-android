package kr.co.mash_up.a9tique;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by CY on 2016. 12. 25..
 */

public class CustomerGoodsDetailAdapter extends RecyclerView.Adapter<CustomerGoodsDetailAdapter.ViewHolder> {

    private List<CustomerGoodsDetail> customerGoodsDetailList;
    private Context mContext;

    public CustomerGoodsDetailAdapter(List<CustomerGoodsDetail> customerGoodsDetailList) {
        this.customerGoodsDetailList = customerGoodsDetailList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_goods_title)
        TextView tvGoodsTitle;
        @BindView(R.id.tv_goods_subtitle)
        TextView tvGoodsSubTitle;
        @BindView(R.id.tv_goods_size)
        TextView tvGoodsSize;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_goods_info)
        TextView tvGoodsInfo;

        public ViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.btn_inquire, R.id.btn_inquire_long})
        void btnInquireClick(View v) {
            final InquireDialog dlgInquire = new InquireDialog(mContext);
            dlgInquire.show();
        }
    }

    @Override
    public int getItemCount() {
        return customerGoodsDetailList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_detail_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvGoodsTitle.setText("Title Title");
        holder.tvGoodsSubTitle.setText("Brand name");
        holder.tvGoodsSize.setText("Size");
        holder.tvGoodsPrice.setText("￦ 105,000");
        holder.tvGoodsInfo.setText("text text text text text text text text text texttext text text text text text text text. \n" +
                "\n" +
                "text text text text text text text text text text text\n" +
                "text text text text text");
    }
}