package kr.co.mash_up.a9tique.ui.contents_detail;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.databinding.ContentsDetailFragmentBinding;

/**
 * Created by seokjunjeong on 2017. 7. 7..
 */

public class ContentsDetailFragment extends BaseFragment<ContentsDetailFragmentBinding> implements ContentsDetailContract.View {
    private ContentsDetailContract.Presenter mPresenter;

    public static ContentsDetailFragment newInstance() {
        ContentsDetailFragment fragment = new ContentsDetailFragment();
        return fragment;
    }

    @Override
    public void setPresenter(ContentsDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.contents_detail_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void newPresenter() {
        new ContentsDetailPresenter(this);
    }

    @Override
    protected void startPresenter() {
        mPresenter.start();
    }
}
