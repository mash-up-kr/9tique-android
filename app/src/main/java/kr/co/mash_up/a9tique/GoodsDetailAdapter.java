package kr.co.mash_up.a9tique;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by CY on 2016. 12. 25..
 */

public class GoodsDetailAdapter extends RecyclerView.Adapter<GoodsDetailAdapter.ViewHolder> {

    private List<GoodsDetail> goodsDetailList;

    public GoodsDetailAdapter(List<GoodsDetail> goodsDetailList) {
        this.goodsDetailList = goodsDetailList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivGoodsImage; // 상품 상세 사진 이미지 뷰
        public TextView tvGoodsTitle; // 상품 타이틀 텍스트 뷰
        public TextView tvGoodsSubTitle; // 상품 서브 타이틀 텍스트 뷰
        public TextView tvGoodsSize; // 상품 사이즈 텍스트 뷰
        public TextView tvGoodsPrice; // 상품 가격 텍스트 뷰
        public TextView tvGoodsInfo; // 상품 상세 정보 텍스트 뷰

        public ViewHolder(View itemView) {
            super(itemView);
            ivGoodsImage = (ImageView) itemView.findViewById(R.id.goodsImage);
            tvGoodsTitle = (TextView) itemView.findViewById(R.id.goodsTitle);
            tvGoodsSubTitle = (TextView) itemView.findViewById(R.id.goodsSubTitle);
            tvGoodsSize = (TextView) itemView.findViewById(R.id.goodsSize);
            tvGoodsPrice = (TextView) itemView.findViewById(R.id.goodsPrice);
            tvGoodsInfo = (TextView) itemView.findViewById(R.id.goodsInfo);
        }
    }

    @Override
    public int getItemCount() {
        return goodsDetailList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_detail_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GoodsDetail goodsDetail = goodsDetailList.get(position);
        holder.ivGoodsImage.setImageResource(goodsDetail.getImage());
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