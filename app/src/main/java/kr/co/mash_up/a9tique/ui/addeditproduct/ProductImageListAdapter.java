package kr.co.mash_up.a9tique.ui.addeditproduct;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.co.mash_up.a9tique.data.ProductImage;

public class ProductImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_FOOTER = 1;

    public interface OnItemClickListener<T> {
        void onClick(T t);
        void onRemove(int position);
    }

    private List<ProductImage> mProductImages;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public List<ProductImage> getProductImages() {
        return mProductImages;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public ProductImageListAdapter(@NonNull Context context) {
        this.mContext = context;
        mProductImages = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return position == mProductImages.size() ? VIEW_TYPE_FOOTER : VIEW_TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FOOTER) {
            return ProductImageFooterViewHolder.newInstance(parent, mOnItemClickListener);
        }
        return ProductImageNormalViewHolder.newInstance(parent, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ProductImageNormalViewHolder) {
            ProductImage productImage = mProductImages.get(position);
            ((ProductImageNormalViewHolder) holder).bind(productImage);
        } else if (holder instanceof ProductImageFooterViewHolder) {
            ((ProductImageFooterViewHolder) holder).bind("");
        }
    }

    @Override
    public int getItemCount() {
        return mProductImages.size() + 1;
    }

    public void addItem(int position, ProductImage productImage) {
        mProductImages.add(position - 1, productImage);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mProductImages.remove(position);
        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, mProductImages.size());
    }

    public void setData(List<String> pathList) {
        mProductImages.clear();

        for (String path : pathList) {
            mProductImages.add(new ProductImage(path));
        }
        notifyDataSetChanged();
    }

    public void setProductImages(List<ProductImage> productImageList) {
        mProductImages.clear();
        mProductImages = productImageList;
        notifyDataSetChanged();
    }
}
