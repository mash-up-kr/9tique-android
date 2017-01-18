package kr.co.mash_up.a9tique.ui.sellproducts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.ui.products.ProductFooterViewHolder;

public class SellProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_CONTENT = 0;
    public static final int VIEW_TYPE_FOOTER = 1;
    public static final int VIEW_TYPE_HEADER = 2;

    public interface OnItemClickListener<T> {
        void onClick(T t, int position);

        void onRemove(T t, int position);

        void onUpdate(T t, int position);

        void onStatusUpdate(T t, int position);
    }

    private ArrayList<Product> mProducts;
    private final Context mContext;

    private OnItemClickListener<Product> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<Product> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public SellProductListAdapter(@NonNull Context context) {
        this.mContext = context;
        mProducts = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_CONTENT:
                return SellProductsViewHolder.newInstance(parent, mOnItemClickListener);
            case VIEW_TYPE_FOOTER:
                return ProductFooterViewHolder.newInstance(parent);
            case VIEW_TYPE_HEADER:
                return SellProductsHeaderViewHolder.newInstance(parent, mOnItemClickListener);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_HEADER:
                ((SellProductsHeaderViewHolder) holder).bind(getItemCount() - 1);
                break;
            case VIEW_TYPE_FOOTER:
                ((ProductFooterViewHolder) holder).bind(null);
                break;
            case VIEW_TYPE_CONTENT:
                Product product = mProducts.get(position - 1);
                ((SellProductsViewHolder) holder).bind(product);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mProducts != null ? mProducts.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        }
        return mProducts.get(position - 1) == null ? VIEW_TYPE_FOOTER : VIEW_TYPE_CONTENT;
    }

    public void addItem(Product product, int position) {
        if (position < 0) {
            position = 0;
        }
        mProducts.add(position, product);
        notifyItemInserted(position + 1);
    }

    public void removeItem(int position) {
        if (position < 1) {
            position = 1;
        }
        mProducts.remove(position - 1);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void setProducts(List<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            mProducts.add(products.get(i));
        }
        notifyDataSetChanged();
    }
}
