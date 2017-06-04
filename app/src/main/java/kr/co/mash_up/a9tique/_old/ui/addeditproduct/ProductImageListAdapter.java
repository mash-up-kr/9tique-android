package kr.co.mash_up.a9tique._old.ui.addeditproduct;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.co.mash_up.a9tique._old.data.ProductImage;

public class ProductImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_HEADER = 1;

    public interface OnItemClickListener<T> {
        void onClick(T t);

        void onRemove(int position);
    }

    private List<ProductImage> mProductImages;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public ProductImageListAdapter(@NonNull Context context) {
        this.mContext = context;
        mProductImages = new ArrayList<>();
        setHeadViewHolder();
    }

    @Override
    public int getItemViewType(int position) {
        return mProductImages.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return ProductImageHeaderViewHolder.newInstance(parent, mOnItemClickListener);
        }
        return ProductImageNormalViewHolder.newInstance(parent, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_HEADER:
                ((ProductImageHeaderViewHolder) holder).bind("");
                break;
            case VIEW_TYPE_NORMAL:
                ProductImage productImage = mProductImages.get(position);
                ((ProductImageNormalViewHolder) holder).bind(productImage);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mProductImages.size();
    }

    public void addItem(int position, ProductImage productImage) {
        mProductImages.add(position, productImage);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mProductImages.remove(position);
        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, mProductImages.size());
    }

    public void setData(List<String> pathList) {
        mProductImages.clear();
        setHeadViewHolder();

        for (String path : pathList) {
            mProductImages.add(new ProductImage(path, VIEW_TYPE_NORMAL));
        }
        notifyDataSetChanged();
    }

    public void setProductImages(List<ProductImage> productImageList) {
        mProductImages.clear();
        setHeadViewHolder();

        for(ProductImage image : productImageList){
            image.setViewType(VIEW_TYPE_NORMAL);
            mProductImages.add(image);
        }
        notifyDataSetChanged();
    }

    private void setHeadViewHolder(){
        mProductImages.add(new ProductImage(VIEW_TYPE_HEADER));
    }

    public List<ProductImage> getProductImages() {
        List<ProductImage> productImages = new ArrayList<>();
        for(ProductImage productImage : mProductImages){
            if(productImage.getViewType() == VIEW_TYPE_NORMAL){
                productImages.add(productImage);
            }
        }
        return productImages;
    }
}
