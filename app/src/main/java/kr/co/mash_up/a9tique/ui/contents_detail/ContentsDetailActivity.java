package kr.co.mash_up.a9tique.ui.contents_detail;

import android.support.v4.app.Fragment;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;

/**
 * Created by seokjunjeong on 2017. 7. 7..
 */

public class ContentsDetailActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.contents_detail_activity;
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
        return ContentsDetailFragment.newInstance();
    }
}
