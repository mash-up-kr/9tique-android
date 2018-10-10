package kr.co.mash_up.a9tique.ui.promotions;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique._old.ui.OnItemClickListener;
import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;

/**
 * Created by ethankim on 2017. 8. 22..
 */
public class CategoryViewHolder extends BaseViewHolder<Category> {

    @BindView(R.id.tv_name)
    TextView mTvName;

    private OnItemClickListener<Category> mListener;

    public static CategoryViewHolder newInstance(@NonNull ViewGroup parent, OnItemClickListener<Category> listener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(itemView, listener);
    }

    private CategoryViewHolder(View itemView, OnItemClickListener<Category> listener) {
        super(itemView);
        mListener = listener;
    }

    @Override
    public void bind(Category category) {
        mTvName.setText(category.getName());
        itemView.setOnClickListener(v -> mListener.onClick(category, getAdapterPosition()));
    }
}
