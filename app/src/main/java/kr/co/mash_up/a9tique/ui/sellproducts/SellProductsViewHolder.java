package kr.co.mash_up.a9tique.ui.sellproducts;

import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import kr.co.mash_up.a9tique.ui.sellproducts.SellProductListAdapter.OnItemClickListener;

/**
 * Created by Dong on 2017-01-16.
 */

public class SellProductsViewHolder extends BaseViewHolder<Product> {

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

    @BindView(R.id.tv_product_remove)
    TextView mTvProductRemove;

    @BindView(R.id.tv_product_modify)
    TextView mTvProductModify;

    @BindView(R.id.tv_product_status)
    TextView mTvProductStatus;

    @BindView(R.id.switch_product_status_update)
    SwitchCompat mSwitchProductStatusUpdate;

    private OnItemClickListener<Product> mOnItemClickListener;

    public static SellProductsViewHolder newInstance(@NonNull ViewGroup parent, OnItemClickListener<Product> listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_content_sell_product_list, parent, false);
        return new SellProductsViewHolder(itemView, listener);
    }

    public SellProductsViewHolder(View itemView, OnItemClickListener<Product> listener) {
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
                    .fitCenter()
                    .centerCrop()
                    .into(mIvProductThumbnail);
        }

        itemView.setOnClickListener(view -> mOnItemClickListener.onClick(product, getAdapterPosition()));
        mTvProductRemove.setOnClickListener(view -> mOnItemClickListener.onRemove(product, getAdapterPosition()));
        mTvProductModify.setOnClickListener(view -> mOnItemClickListener.onUpdate(product, getAdapterPosition()));

        mSwitchProductStatusUpdate.setOnCheckedChangeListener(null);
        if (product.getStatus().equals(Product.Status.SELL)) {
            mRlSoldoutFilter.setVisibility(View.GONE);
            mTvProductStatus.setText("판매중");
            mSwitchProductStatusUpdate.setChecked(true);
        } else {
            mRlSoldoutFilter.setVisibility(View.VISIBLE);
            mTvProductStatus.setText("판매완료");
            mSwitchProductStatusUpdate.setChecked(false);
        }

        mSwitchProductStatusUpdate.setOnCheckedChangeListener((compoundButton, checked) -> {
            if (checked) {
                product.setStatus(Product.Status.SELL);
            } else {
                product.setStatus(Product.Status.SOLD_OUT);
            }
            mOnItemClickListener.onStatusUpdate(product, getAdapterPosition());
        });
    }
}