package kr.co.mash_up.a9tique.ui.setting;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseViewHolder;
import kr.co.mash_up.a9tique.ui.OnItemClickListener;

public class SettingWithImageViewHolder extends BaseViewHolder<Menu> {

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @BindView(R.id.iv_indicator)
    ImageView mIvIndicator;

    private OnItemClickListener<Menu> mOnItemClickListener;

    public static SettingWithImageViewHolder newInstance(@NonNull ViewGroup parent, OnItemClickListener<Menu> listener) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_setting_with_img_list, parent, false);
        return new SettingWithImageViewHolder(itemView, listener);
    }

    public SettingWithImageViewHolder(View itemView, OnItemClickListener<Menu> listener) {
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
