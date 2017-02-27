package kr.co.mash_up.a9tique.ui.zzimproducts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.ui.products.ProductFooterViewHolder;

public class ZzimlProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_CONTENT = 0;
    public static final int VIEW_TYPE_FOOTER = 1;

    public interface OnItemClickListener<T> {
        void onClick(T t, int position, ImageView shareImageView, String transitionName);

        void onRemove(T t, int position);

        void onInquire(T t, int position);
    }

    private ArrayList<Product> mProducts;
    private final Context mContext;

    private OnItemClickListener<Product> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<Product> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public ZzimlProductListAdapter(@NonNull Context context) {
        this.mContext = context;
        mProducts = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_CONTENT:
                return ZzimProductsViewHolder.newInstance(parent, mOnItemClickListener);
            case VIEW_TYPE_FOOTER:
                return ProductFooterViewHolder.newInstance(parent);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_FOOTER:
                ((ProductFooterViewHolder) holder).bind(null);
                break;
            case VIEW_TYPE_CONTENT:
                Product product = mProducts.get(position);
                ((ZzimProductsViewHolder) holder).bind(product);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mProducts != null ? mProducts.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mProducts.get(position).getViewType();
    }

    public void addItem(Product product, int position) {
        if (position < 0) {
            position = 0;
        }
        mProducts.add(position, product);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        if (position < 0) {
            position = 0;
        }
        mProducts.remove(position);
        notifyItemRemoved(position);
    }

    public void setProducts(List<Product> products) {
        mProducts.clear();
        for (Product product : products) {
            product.setViewType(VIEW_TYPE_CONTENT);
            mProducts.add(product);
        }
        notifyDataSetChanged();
    }

    public void addProducts(List<Product> products) {
        int currentSize = mProducts.size();
        for (Product product : products) {
            product.setViewType(VIEW_TYPE_CONTENT);
            mProducts.add(product);
        }
        notifyItemRangeInserted(currentSize, products.size());
//        notifyDataSetChanged();
    }

    public void addFooterView(int position) {
        addItem(new Product(VIEW_TYPE_FOOTER), position);
    }

    public void removeFooterView(int position) {
        removeItem(position);
    }

    public void clearProducts(){
        mProducts.clear();
        //Todo: call notifyDataSetChanged();
    }
}