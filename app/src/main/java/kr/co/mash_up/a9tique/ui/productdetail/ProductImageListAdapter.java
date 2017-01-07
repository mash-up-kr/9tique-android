package kr.co.mash_up.a9tique.ui.productdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import kr.co.mash_up.a9tique.data.ProductImage;

public class ProductImageListAdapter extends RecyclerView.Adapter<ProductImageViewHolder> {

    private List<ProductImage> mProductImageList;
    private Context mContext;

    public ProductImageListAdapter(List<ProductImage> productImageList, Context context) {
        mProductImageList = productImageList;
        mContext = context;
    }

    @Override
    public ProductImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ProductImageViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(ProductImageViewHolder holder, int position) {
        ProductImage productImage = mProductImageList.get(position);
        holder.bind(productImage);
    }

    @Override
    public int getItemCount() {
        return mProductImageList.size();
    }
}
