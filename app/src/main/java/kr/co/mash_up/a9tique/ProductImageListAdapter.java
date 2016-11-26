package kr.co.mash_up.a9tique;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ProductImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_FOOTER = 1;

    private List<ProductImage> mProductImageList;
    private Context mContext;

    OnItemClickListener mOnItemClickListener;

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
        return ProductImageNormalViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ProductImageNormalViewHolder) {
            ProductImage productImage = mProductImageList.get(position);
            ((ProductImageNormalViewHolder) holder).bind(productImage);
        }
    }

    @Override
    public int getItemCount() {
        return mProductImageList.size() + 1;
    }

    public void addItem(int position, ProductImage productImage) {
        mProductImageList.add(position, productImage);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mProductImageList.remove(position);
        notifyItemRemoved(position - 1);
//        notifyItemRangeChanged(position, mProductImageList.size());
    }

    public void setData(List<String> pathList) {
        mProductImageList.clear();

        for(String path : pathList){
            mProductImageList.add(new ProductImage(path));
        }
        notifyDataSetChanged();
    }
}
