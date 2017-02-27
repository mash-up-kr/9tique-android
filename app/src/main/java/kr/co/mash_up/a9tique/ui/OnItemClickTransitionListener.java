package kr.co.mash_up.a9tique.ui;


import android.widget.ImageView;

public interface OnItemClickTransitionListener<T> {
    void onClick(T t, int position, ImageView shareImageView, String transitionName);
}
