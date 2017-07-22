package kr.co.mash_up.a9tique.ui.product_detail;

import android.support.v4.app.Fragment;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;

/**
 * Created by seokjunjeong on 2017. 7. 22..
 */

public class ProductDetailActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.product_detail_activity;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.contentFrame;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected Fragment getFragment() {
        return null;
    }
}
