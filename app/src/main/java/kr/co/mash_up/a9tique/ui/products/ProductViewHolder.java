package kr.co.mash_up.a9tique.ui.products;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;
import kr.co.mash_up.a9tique.ui.OnItemClickListener;

/**
 * Created by Dong on 2016-11-12.
 */

public class ProductViewHolder extends BaseViewHolder<Product> {

    @BindView(R.id.iv_product_thumbnail)
    ImageView ivThumbnail;

    @BindView(R.id.tv_product_name)
    TextView tvName;

    @BindView(R.id.tv_product_brand_name)
    TextView tvBrandName;

    @BindView(R.id.tv_product_size)
    TextView tvSize;

    @BindView(R.id.tv_product_price)
    TextView tvPrice;

    private OnItemClickListener<Product> mOnItemClickListener;

    public static ProductViewHolder newInstance(@NonNull ViewGroup parent, OnItemClickListener<Product> listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_list, parent, false);
        return new ProductViewHolder(itemView, listener);
    }

    public ProductViewHolder(View itemView, OnItemClickListener<Product> listener) {
        super(itemView);

        mOnItemClickListener = listener;
    }

    @Override
    public void bind(final Product product) {
        tvName.setText(product.getName());
        tvBrandName.setText(product.getBrandName());
        tvSize.setText(product.getSize());
        tvPrice.setText(NumberFormat.getInstance(Locale.KOREA).format(product.getPrice()));

        if (product.getProductImages().size() > 0) {  //Todo: remove
            Glide.with(itemView.getContext())
                    .load(Constants.END_POINT + product.getProductImages().get(0).getImageUrl())
                    .fitCenter()
                    .centerCrop()
                    .into(ivThumbnail);
        }

        itemView.setOnClickListener(view -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(product);
            }
        });
    }
}
