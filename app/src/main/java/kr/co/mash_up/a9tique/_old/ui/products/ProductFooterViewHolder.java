package kr.co.mash_up.a9tique._old.ui.products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class ProductFooterViewHolder extends BaseViewHolder {

    @BindView(R.id.progressBar)
    MaterialProgressBar mProgressBar;

    public static ProductFooterViewHolder newInstance(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_footer_product_list, parent, false);
        return new ProductFooterViewHolder(itemView);
    }

    public ProductFooterViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Object o) {
        mProgressBar.setIndeterminate(true);
    }
}
