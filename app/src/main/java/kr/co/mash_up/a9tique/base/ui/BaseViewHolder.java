package kr.co.mash_up.a9tique.base.ui;


import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**
     * 뷰홀더에 데이터 바인딩
     *
     * @param t 데이터를 담는 POJO 객체
     */
    @UiThread
    public abstract void bind(T t);
}
