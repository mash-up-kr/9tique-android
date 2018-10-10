package kr.co.mash_up.a9tique._old.ui.addeditproduct;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;
import kr.co.mash_up.a9tique._old.common.Constants;
import kr.co.mash_up.a9tique._old.data.ProductImage;
import kr.co.mash_up.a9tique._old.ui.addeditproduct.ProductImageListAdapter.OnItemClickListener;


public class ProductImageNormalViewHolder extends BaseViewHolder<ProductImage> {

    @BindView(R.id.iv_product_image)
    ImageView ivProductImage;

    @BindView(R.id.iv_product_image_remove)
    ImageView ivProductImageRemove;

    private RequestManager mRequestManager;
    private OnItemClickListener mOnItemClickListener;

    public static ProductImageNormalViewHolder newInstance(@NonNull ViewGroup parent, @NonNull OnItemClickListener listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_normal_product_image_list, parent, false);
        return new ProductImageNormalViewHolder(itemView, listener);
    }

    public ProductImageNormalViewHolder(View itemView, @NonNull OnItemClickListener listener) {
        super(itemView);

        mRequestManager = Glide.with(itemView.getContext());
        mOnItemClickListener = listener;
    }

    @Override
    public void bind(ProductImage productImage) {
        DrawableTypeRequest<String> request;

        if (TextUtils.isEmpty(productImage.getImageUrl())) {
            request = mRequestManager
                    .load(productImage.getImagePath());
        } else {
            request = mRequestManager
                    .load(Constants.END_POINT + productImage.getImageUrl());
        }

        request
                .placeholder(R.drawable.ic_nodata)
                .error(R.drawable.ic_nodata)
                .crossFade()
                .fitCenter()
                .centerCrop()
                .into(ivProductImage);

        ivProductImageRemove.setOnClickListener(view -> {
            mOnItemClickListener.onRemove(getAdapterPosition());
        });
    }
}
