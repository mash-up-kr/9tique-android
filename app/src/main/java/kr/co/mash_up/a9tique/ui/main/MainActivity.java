package kr.co.mash_up.a9tique.ui.main;

import android.support.v4.app.Fragment;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;
import kr.co.mash_up.a9tique.ui.main.home.HomeFragment;
import kr.co.mash_up.a9tique.util.ui.FragmentUtil;

/**
 * Created by seokjunjeong on 2017. 6. 4..
 */

public class MainActivity extends BaseActivity<Fragment> {
    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.contentFrame;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected Fragment getFragment() {
        return HomeFragment.newInstance();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentUtil.replaceFragment(this, getFragmentContentId(), fragment);
    }
}
