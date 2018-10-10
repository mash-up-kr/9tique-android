package kr.co.mash_up.a9tique.base.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by seokjunjeong on 2017. 5. 27..
 */

public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment {
    protected B mBinding;  // Todo: remove databinding

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        newPresenter();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        startPresenter();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void newPresenter();

    protected abstract void startPresenter();

}
