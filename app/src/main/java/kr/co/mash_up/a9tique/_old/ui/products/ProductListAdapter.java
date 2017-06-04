package kr.co.mash_up.a9tique._old.ui.products;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.co.mash_up.a9tique._old.data.Product;
import kr.co.mash_up.a9tique._old.ui.OnItemClickTransitionListener;

/**
 * Created by Dong on 2016-11-12.
 */

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_CONTENT = 0;
    public static final int VIEW_TYPE_FOOTER = 1;

    private ArrayList<Product> mProducts;
    private final Context mContext;

    private OnItemClickTransitionListener<Product> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickTransitionListener<Product> listener) {
        mOnItemClickListener = listener;
    }

    public ProductListAdapter(@NonNull Context context) {
        this.mContext = context;
        this.mProducts = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_CONTENT:
                return ProductViewHolder.newInstance(parent, mOnItemClickListener);
            case VIEW_TYPE_FOOTER:
                return ProductFooterViewHolder.newInstance(parent);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductViewHolder) {
            Product product = mProducts.get(position);
            ((ProductViewHolder) holder).bind(product);
        } else {
            ((ProductFooterViewHolder) holder).bind(null);
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
        mProducts.remove(position);
        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, mProducts.size());
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
    }

    public void clearProducts() {
        mProducts.clear();
        //Todo: call notifyDataSetChanged();
    }

    public void addFooterView(int position) {
        addItem(new Product(VIEW_TYPE_FOOTER), position);
    }

    public void removeFooterView(int position) {
        removeItem(position);
    }
}
