package kr.co.mash_up.a9tique;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ProductImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_ADD = 1;

    private List<ProductImage> mProductImageList;
    private Context mContext;

    public ProductImageListAdapter(@NonNull Context context) {
        this.mContext = context;
        mProductImageList = new ArrayList<>();

        initData();
    }

    private void initData() {
        ProductImage productImage = new ProductImage();
        productImage.setImageType(VIEW_TYPE_ADD);
        addItem(0, productImage);
    }

    @Override
    public int getItemViewType(int position) {
        return mProductImageList.get(position).getImageType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ADD) {
            return ProductImageAddViewHolder.newInstance(parent);
        }
        return ProductImageNormalViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductImage productImage = mProductImageList.get(position);

        switch (productImage.getImageType()) {
            case VIEW_TYPE_ADD:
                ((ProductImageAddViewHolder) holder).bind(productImage);
                break;
            case VIEW_TYPE_NORMAL:
                ((ProductImageNormalViewHolder) holder).bind(productImage);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mProductImageList != null ? mProductImageList.size() : 0;
    }

    public void addItem(int position, ProductImage productImage) {
        if (position < 0) {
            position = 0;
        }
        mProductImageList.add(position, productImage);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mProductImageList.remove(position);
        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, mProductImageList.size());
    }
}
