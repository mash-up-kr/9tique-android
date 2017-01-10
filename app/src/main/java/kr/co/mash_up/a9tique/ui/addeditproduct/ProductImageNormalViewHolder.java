package kr.co.mash_up.a9tique.ui.addeditproduct;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;
import kr.co.mash_up.a9tique.data.ProductImage;
import  kr.co.mash_up.a9tique.ui.addeditproduct.ProductImageListAdapter.OnItemClickListener;


public class ProductImageNormalViewHolder extends BaseViewHolder<ProductImage> {

    @BindView(R.id.iv_product_image)
    ImageView ivProductImage;

    @BindView(R.id.iv_product_image_remove)
    ImageView ivProductImageRemove;

    private OnItemClickListener mOnItemClickListener;

    public static ProductImageNormalViewHolder newInstance(@NonNull ViewGroup parent, @NonNull OnItemClickListener listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_normal_product_image_list, parent, false);
        return new ProductImageNormalViewHolder(itemView, listener);
    }

    public ProductImageNormalViewHolder(View itemView, @NonNull OnItemClickListener listener) {
        super(itemView);

        mOnItemClickListener = listener;
    }

    @Override
    public void bind(ProductImage productImage) {
        Glide.with(itemView.getContext())
                .load(new File(productImage.getImagePath()))
                .fitCenter()
                .centerCrop()
                .into(ivProductImage);

        ivProductImageRemove.setOnClickListener(view -> {
            mOnItemClickListener.onRemove(getAdapterPosition());
        });
    }
}
