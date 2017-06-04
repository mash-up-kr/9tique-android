package kr.co.mash_up.a9tique._old.ui.setting;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique._old.base.ui.BaseViewHolder;
import kr.co.mash_up.a9tique._old.data.Menu;
import kr.co.mash_up.a9tique._old.ui.OnItemClickListener;

public class MenuViewHolder extends BaseViewHolder<Menu> {

    @BindView(R.id.tv_toolbar_title)
    TextView mTvTitle;

    private OnItemClickListener<Menu> mOnItemClickListener;

    public static MenuViewHolder newInstance(@NonNull ViewGroup parent, OnItemClickListener<Menu> listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_setting_normal_list, parent, false);
        return new MenuViewHolder(itemView, listener);
    }

    public MenuViewHolder(View itemView, OnItemClickListener<Menu> listener) {
        super(itemView);

        mOnItemClickListener = listener;
    }

    @Override
    public void bind(Menu menu) {
        mTvTitle.setText(menu.getTitle());
        itemView.setOnClickListener(view -> {
            mOnItemClickListener.onClick(menu, getAdapterPosition());
        });
    }
}
