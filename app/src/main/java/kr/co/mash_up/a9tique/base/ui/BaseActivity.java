package kr.co.mash_up.a9tique.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import kr.co.mash_up.a9tique.util.ui.FragmentUtil;


/**
 * Created by seokjunjeong on 2017. 5. 27..
 */

public abstract class BaseActivity<F extends Fragment> extends AppCompatActivity {
    protected F mFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mFragment = getFragment();
        FragmentUtil.addFragment(this, getFragmentContentId(), mFragment);
        initView();
        initToolbar();
        initPresenter();
    }

    protected abstract int getLayoutId();

    protected abstract int getFragmentContentId();

    protected abstract void initView();

    protected abstract void initToolbar();

    protected abstract void initPresenter();

    protected abstract F getFragment();
}
