package kr.co.mash_up.a9tique.ui.products;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.ui.OnItemClickListener;

/**
 * Created by Dong on 2016-11-12.
 */

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_CONTENT = 0;
    public static final int VIEW_TYPE_FOOTER = 1;

    private ArrayList<Product> mProducts;
    private final Context mContext;

    OnItemClickListener<Product> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<Product> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public ProductListAdapter(@NonNull Context context) {
        this.mContext = context;
        this.mProducts = new ArrayList<>();

//        dummyData();
    }

    //Todo: remove
    private void dummyData() {
        Product product;
        for (int i = 0; i < 30; i++) {
            product = new Product();
            product.setName("name " + i);
            product.setBrandName("brand name " + i);
            product.setPrice(10000);
            product.setSize("size " + i);
            product.setDescription("description " + i);
            product.setMainCategory("main " + i);
            product.setSubCategory("sub " + i);
            product.setProductImages(new ArrayList<>());
            product.setCreatedAt(new Date().getTime());
            addItem(product, i);
        }
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
        return mProducts.get(position) == null ? VIEW_TYPE_FOOTER : VIEW_TYPE_CONTENT;
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
        notifyItemRangeChanged(position, mProducts.size());
    }

    public void setProducts(List<Product> products) {

        for (int i = 0; i < products.size(); i++) {
            mProducts.add(products.get(i));
        }
        notifyDataSetChanged();
    }
}
