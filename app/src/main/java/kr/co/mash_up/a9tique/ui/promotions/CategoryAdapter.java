package kr.co.mash_up.a9tique.ui.promotions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.co.mash_up.a9tique._old.ui.OnItemClickListener;

/**
 * Created by ethankim on 2017. 8. 22..
 */
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Category> mCategories;
    private final Context mContext;

    private OnItemClickListener<Category> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<Category> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public CategoryAdapter(@NonNull Context context) {
        this.mContext = context;
        this.mCategories = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CategoryViewHolder.newInstance(parent, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Category category = mCategories.get(position);
        ((CategoryViewHolder) holder).bind(category);
    }

    @Override
    public int getItemCount() {
        return mCategories != null ? mCategories.size() : 0;
    }
}
