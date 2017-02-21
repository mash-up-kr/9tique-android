package kr.co.mash_up.a9tique.ui.zzimproducts;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.ui.zzimproducts.ZzimlProductListAdapter.OnItemClickListener;


public class ZzimProductsViewHolder extends BaseViewHolder<Product> {

    @BindView(R.id.iv_product_thumbnail)
    ImageView mIvProductThumbnail;

    @BindView(R.id.rl_sold_out_filter)
    RelativeLayout mRlSoldoutFilter;

    @BindView(R.id.tv_product_name)
    TextView mTvProductName;

    @BindView(R.id.tv_product_brand_name)
    TextView mTvProductBrandName;

    @BindView(R.id.tv_product_size)
    TextView mTvProductSize;

    @BindView(R.id.tv_product_price)
    TextView mTvProductPrice;

    @BindView(R.id.btn_product_inquire)
    Button mBtnProductInquire;

    @BindView(R.id.btn_product_remove)
    Button mBtnProductRemove;

    private OnItemClickListener<Product> mOnItemClickListener;

    public static ZzimProductsViewHolder newInstance(@NonNull ViewGroup parent, OnItemClickListener<Product> listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_content_zzim_product_list, parent, false);
        return new ZzimProductsViewHolder(itemView, listener);
    }

    public ZzimProductsViewHolder(View itemView, OnItemClickListener<Product> listener) {
        super(itemView);

        mOnItemClickListener = listener;
    }

    @Override
    public void bind(Product product) {
        mTvProductName.setText(product.getName());
        mTvProductBrandName.setText(product.getBrandName());
        mTvProductSize.setText(product.getSize());
        mTvProductPrice.setText(String.format("￦ %s", NumberFormat.getInstance(Locale.KOREA).format(product.getPrice())));

        if (product.getProductImages().size() > 0) {  //Todo: remove
            Glide.with(itemView.getContext())
                    .load(Constants.END_POINT + product.getProductImages().get(0).getImageUrl())
                    .placeholder(R.drawable.ic_nodata)
                    .error(R.drawable.ic_nodata)
                    .crossFade()
                    .fitCenter()
                    .centerCrop()
                    .into(mIvProductThumbnail);
        }

        itemView.setOnClickListener(view -> mOnItemClickListener.onClick(product, getAdapterPosition()));
        mBtnProductRemove.setOnClickListener(view -> mOnItemClickListener.onRemove(product, getAdapterPosition()));
        mBtnProductInquire.setOnClickListener(view -> mOnItemClickListener.onInquire(product, getAdapterPosition()));

        if (product.getStatus().equals(Product.Status.SELL)) {
            mRlSoldoutFilter.setVisibility(View.GONE);
        } else {
            mRlSoldoutFilter.setVisibility(View.VISIBLE);
        }
    }
}