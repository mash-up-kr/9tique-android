package kr.co.mash_up.a9tique.ui.brand_list;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.Toolbar;
import android.view.View;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;
import kr.co.mash_up.a9tique.databinding.BrandListActivityBinding;

/**
 * Created by seokjunjeong on 2017. 7. 23..
 */

public class BrandListActivity extends BaseActivity<BrandListFragment> {
    private BrandListActivityBinding mBinding;

    @Override
    protected int getLayoutId() {
        return R.layout.brand_list_activity;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.contentFrame;
    }

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.bind(findViewById(R.id.root));
        mBinding.setToolbarTitle("브랜드 리스트");
        mBinding.toolbar.ivBack.setOnClickListener(v -> finish());
    }

    @Override
    protected BrandListFragment getFragment() {
        return BrandListFragment.newInstance();
    }
}
