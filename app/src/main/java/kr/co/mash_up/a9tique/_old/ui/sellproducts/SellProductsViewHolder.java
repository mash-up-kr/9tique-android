package kr.co.mash_up.a9tique._old.ui.sellproducts;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique._old.base.ui.BaseViewHolder;
import kr.co.mash_up.a9tique._old.common.Constants;
import kr.co.mash_up.a9tique._old.data.Product;
import kr.co.mash_up.a9tique._old.ui.sellproducts.SellProductListAdapter.OnItemClickListener;

/**
 * Created by Dong on 2017-01-16.
 */

public class SellProductsViewHolder extends BaseViewHolder<SellProduct> {

    @BindView(R.id.cb_product)
    CheckBox mCbProduct;

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

    @BindView(R.id.btn_product_remove)
    Button mBtnProductRemove;

    @BindView(R.id.btn_product_modify)
    Button mBtnProductModify;

    @BindView(R.id.tv_product_status)
    TextView mTvProductStatus;

    @BindView(R.id.switch_product_status_update)
    SwitchCompat mSwitchProductStatusUpdate;

    private OnItemClickListener<SellProduct> mOnItemClickListener;

    public static SellProductsViewHolder newInstance(@NonNull ViewGroup parent, OnItemClickListener<SellProduct> listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_content_sell_product_list, parent, false);
        return new SellProductsViewHolder(itemView, listener);
    }

    public SellProductsViewHolder(View itemView, OnItemClickListener<SellProduct> listener) {
        super(itemView);

        mOnItemClickListener = listener;
    }

    @Override
    public void bind(SellProduct sellProduct) {
        mTvProductName.setText(sellProduct.getName());
        mTvProductBrandName.setText(sellProduct.getBrandName());
        mTvProductSize.setText(sellProduct.getSize());
        mTvProductPrice.setText(String.format("￦ %s", NumberFormat.getInstance(Locale.KOREA).format(sellProduct.getPrice())));

        if (sellProduct.getProductImages().size() > 0) {  //Todo: remove
            Glide.with(itemView.getContext())
                    .load(Constants.END_POINT + sellProduct.getProductImages().get(0).getImageUrl())
                    .placeholder(R.drawable.ic_nodata)
                    .error(R.drawable.ic_nodata)
                    .crossFade()
                    .fitCenter()
                    .centerCrop()
                    .into(mIvProductThumbnail);
        }

        String transitionName = Constants.PRODUCT_IMAGE_TRANSITION + String.valueOf(getAdapterPosition());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mIvProductThumbnail.setTransitionName(transitionName);
        }

        itemView.setOnClickListener(view -> mOnItemClickListener.onClick(sellProduct, getAdapterPosition(), mIvProductThumbnail, transitionName));
        mBtnProductRemove.setOnClickListener(view -> mOnItemClickListener.onRemove(sellProduct, getAdapterPosition()));
        mBtnProductModify.setOnClickListener(view -> mOnItemClickListener.onUpdate(sellProduct, getAdapterPosition()));

        mSwitchProductStatusUpdate.setOnCheckedChangeListener(null);
        if (sellProduct.getStatus().equals(Product.Status.SELL)) {
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
                sellProduct.setStatus(Product.Status.SELL);
            } else {
                sellProduct.setStatus(Product.Status.SOLD_OUT);
            }
            mOnItemClickListener.onStatusUpdate(sellProduct, getAdapterPosition());
        });

        mCbProduct.setOnCheckedChangeListener(null);
//        mCbProduct.post(() -> mCbProduct.setChecked(sellProduct.isChecked()));  // animation 작동하는데 살짝 느리다..
        mCbProduct.setChecked(sellProduct.isChecked());
        mCbProduct.setOnCheckedChangeListener((compoundButton, b) -> {
            sellProduct.setChecked(b);
            mOnItemClickListener.onCheck(sellProduct, getAdapterPosition());
        });
    }
}