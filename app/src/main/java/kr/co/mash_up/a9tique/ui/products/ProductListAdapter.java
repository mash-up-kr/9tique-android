package kr.co.mash_up.a9tique.ui.products;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.ui.OnItemClickListener;

/**
 * Created by Dong on 2016-11-12.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private ArrayList<Product> mProducts;
    private final Context mContext;

    OnItemClickListener<Product> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<Product> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public ProductListAdapter(@NonNull Context context) {
        this.mContext = context;
        this.mProducts = new ArrayList<>();

        dummyData();
    }

    //Todo: remove
    private void dummyData(){
        Product product;
        for(int i=0; i<30; i++){
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
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ProductViewHolder.newInstance(parent, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        if (mProducts.size() == 0) {
            return;
        }
        Product product = mProducts.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return mProducts != null ? mProducts.size() : 0;
    }

    public void addItem(Product product, int position) {
        mProducts.add(position, product);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mProducts.remove(position);
        notifyItemRemoved(position);
    }

    public void setProducts(List<Product> products) {
        mProducts = (ArrayList<Product>) products;
        notifyDataSetChanged();
    }
}
