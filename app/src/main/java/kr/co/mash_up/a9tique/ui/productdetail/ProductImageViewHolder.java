package kr.co.mash_up.a9tique.ui.productdetail;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;
import kr.co.mash_up.a9tique.data.ProductImage;


public class ProductImageViewHolder extends BaseViewHolder<ProductImage> {

    @BindView(R.id.iv_product_image)
    ImageView ivProductImage;

    public static ProductImageViewHolder newInstance(@NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detail_product_image_list, parent, false);
        return new ProductImageViewHolder(itemView);
    }

    public ProductImageViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(ProductImage productImage) {
        Glide.with(itemView.getContext())
                .load(productImage.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .fitCenter()
                .centerCrop()
                .into(ivProductImage);
    }
}
