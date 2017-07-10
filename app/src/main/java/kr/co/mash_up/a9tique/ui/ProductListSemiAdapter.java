package kr.co.mash_up.a9tique.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.data.Product;

/**
 * Created by seokjunjeong on 2017. 6. 24..
 */

public class ProductListSemiAdapter extends RecyclerView.Adapter<ProductListViewHolder> {
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<Product> mProducts;

    public ProductListSemiAdapter(@NonNull OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        mProducts = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(ArrayList<Product> products) {
        mProducts = products;
    }

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        ProductListViewHolder productListViewHolder =
                new ProductListViewHolder(view, mOnItemClickListener);
        return productListViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, int position) {
        holder.bind(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }
}
