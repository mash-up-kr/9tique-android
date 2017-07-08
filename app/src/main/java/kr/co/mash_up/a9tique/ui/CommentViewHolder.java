package kr.co.mash_up.a9tique.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import kr.co.mash_up.a9tique.data.Comment;
import kr.co.mash_up.a9tique.databinding.CommentItemBinding;

/**
 * Created by seokjunjeong on 2017. 7. 8..
 */

public class CommentViewHolder extends RecyclerView.ViewHolder {
    private CommentItemBinding mBinding;

    public CommentViewHolder(View itemView) {
        super(itemView);
        mBinding = DataBindingUtil.bind(itemView);
    }

    public void bind(Comment comment) {
        mBinding.setComment(comment);
    }
}
