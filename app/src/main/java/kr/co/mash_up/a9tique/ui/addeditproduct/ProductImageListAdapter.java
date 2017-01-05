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

    private List<ProductImage> mProductImageList;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public List<ProductImage> getProductImageList() {
        return mProductImageList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public ProductImageListAdapter(@NonNull Context context) {
        this.mContext = context;
        mProductImageList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return position == mProductImageList.size() ? VIEW_TYPE_FOOTER : VIEW_TYPE_NORMAL;
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
            ProductImage productImage = mProductImageList.get(position);
            ((ProductImageNormalViewHolder) holder).bind(productImage);
        } else if (holder instanceof ProductImageFooterViewHolder) {
            ((ProductImageFooterViewHolder) holder).bind("");
        }
    }

    @Override
    public int getItemCount() {
        return mProductImageList.size() + 1;
    }

    public void addItem(int position, ProductImage productImage) {
        mProductImageList.add(position - 1, productImage);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mProductImageList.remove(position);
        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, mProductImageList.size());
    }

    public void setData(List<String> pathList) {
        mProductImageList.clear();

        for (String path : pathList) {
            mProductImageList.add(new ProductImage(path));
        }
        notifyDataSetChanged();
    }
}
