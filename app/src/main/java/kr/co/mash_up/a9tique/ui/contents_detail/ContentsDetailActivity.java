package kr.co.mash_up.a9tique.ui.contents_detail;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.view.View;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;
import kr.co.mash_up.a9tique.databinding.ContentsDetailActivityBinding;

/**
 * Created by seokjunjeong on 2017. 7. 7..
 */

public class ContentsDetailActivity extends BaseActivity {
    private ContentsDetailActivityBinding mBinding;

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
        mBinding = DataBindingUtil.bind(findViewById(R.id.root));
        mBinding.toolbar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.toolbar.setToolbarTitle("컨텐츠 상세 화면 샘플");
    }

    @Override
    protected Fragment getFragment() {
        return ContentsDetailFragment.newInstance();
    }
}
