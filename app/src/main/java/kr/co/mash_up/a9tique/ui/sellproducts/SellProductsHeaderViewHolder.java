package kr.co.mash_up.a9tique.ui.sellproducts;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.ui.sellproducts.SellProductListAdapter.OnItemClickListener;

/**
 * Created by Dong on 2017-01-18.
 */

public class SellProductsHeaderViewHolder extends BaseViewHolder<Integer> {

    @BindView(R.id.tv_products_count)
    TextView mTvProductsCount;

    @BindView(R.id.tv_product_remove_all)
    TextView mTvProductRemoveAll;

    private OnItemClickListener<Product> mOnItemClickListener;

    public static SellProductsHeaderViewHolder newInstance(@NonNull ViewGroup parent, OnItemClickListener<Product> listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_header_sell_product_list, parent, false);
        return new SellProductsHeaderViewHolder(itemView, listener);
    }

    public SellProductsHeaderViewHolder(View itemView, OnItemClickListener<Product> listener) {
        super(itemView);

        mOnItemClickListener = listener;
    }

    @Override
    public void bind(Integer totalItemCount) {
        mTvProductsCount.setText(String.format(Locale.KOREA, "%d개의 판매 등록 상품이 있습니다.", totalItemCount));

        mTvProductRemoveAll.setOnClickListener(view -> {
            mOnItemClickListener.onClick(null, getAdapterPosition());
        });
    }
}
