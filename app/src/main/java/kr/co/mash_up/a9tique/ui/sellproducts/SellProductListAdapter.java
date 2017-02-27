package kr.co.mash_up.a9tique.ui.sellproducts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.ui.products.ProductFooterViewHolder;

public class SellProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_CONTENT = 0;
    public static final int VIEW_TYPE_FOOTER = 1;

    public interface OnItemClickListener<T> {
        void onClick(T t, int position, ImageView shareImageView, String transitionName);

        void onRemove(T t, int position);

        void onUpdate(T t, int position);

        void onStatusUpdate(T t, int position);

        void onCheck(T t, int position);
    }

    private ArrayList<SellProduct> mSellProducts;
    private final Context mContext;

    private OnItemClickListener<SellProduct> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<SellProduct> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public SellProductListAdapter(@NonNull Context context) {
        this.mContext = context;
        mSellProducts = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_CONTENT:
                return SellProductsViewHolder.newInstance(parent, mOnItemClickListener);
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
                SellProduct sellProduct = mSellProducts.get(position);
                ((SellProductsViewHolder) holder).bind(sellProduct);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mSellProducts != null ? mSellProducts.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mSellProducts.get(position).getViewType();
    }

    public void addItem(Product product, int position) {
        if (position < 0) {
            position = 0;
        }
        mSellProducts.add(position, new SellProduct(product));
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        if (position < 0) {
            position = 0;
        }
        mSellProducts.remove(position);
        notifyItemRemoved(position);
//        notifyItemRangeChanged(0, mSellProducts.size() + 1);  // 헤더부분 refresh때문에 0부터
    }

    public void setSellProducts(List<Product> products) {
        mSellProducts.clear();
        for (Product product : products) {
            product.setViewType(VIEW_TYPE_CONTENT);
            mSellProducts.add(new SellProduct(product));
        }
        notifyDataSetChanged();
    }

    public void addProducts(List<Product> products) {
        int currentSize = mSellProducts.size();
        for (Product product : products) {
            product.setViewType(VIEW_TYPE_CONTENT);
            mSellProducts.add(new SellProduct(product));
        }
        notifyItemRangeInserted(currentSize, products.size());
//        notifyDataSetChanged();
    }

    public ArrayList<SellProduct> getSellProducts() {
        return mSellProducts;
    }

    public void addFooterView(int position) {
        addItem(new Product(VIEW_TYPE_FOOTER), position);
    }

    public void removeFooterView(int position) {
        removeItem(position);
    }

    public void setSellProductsChecked(boolean checked) {
        for (SellProduct sellProduct : mSellProducts) {
            sellProduct.setChecked(checked);
        }
        notifyDataSetChanged();
    }

    /**
     * @return check box가 check된 상품들
     */
    public ArrayList<Product> getSellProductIsSelected() {
        ArrayList<Product> products = new ArrayList<>();
        for (SellProduct sellProduct : mSellProducts) {
            if (sellProduct.isChecked()) {
                products.add(sellProduct);
            }
        }
        return products;
    }

    /**
     * @return check box가 check된 상품 수
     */
    public int getSellProductSelectedCount() {
        int selectedCount = 0;
        for (SellProduct sellProduct : mSellProducts) {
            if (sellProduct.isChecked()) {
                selectedCount += 1;
            }
        }
        return selectedCount;
    }
}