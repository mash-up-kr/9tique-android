package kr.co.mash_up.a9tique.ui.brand_list;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;

/**
 * Created by seokjunjeong on 2017. 7. 23..
 */

public class BrandListActivity extends BaseActivity<BrandListFragment> {
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

    }

    @Override
    protected BrandListFragment getFragment() {
        return BrandListFragment.newInstance();
    }
}
