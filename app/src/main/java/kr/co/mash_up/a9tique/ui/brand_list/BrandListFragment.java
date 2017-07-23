package kr.co.mash_up.a9tique.ui.brand_list;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.databinding.BrandListFragmentBinding;

/**
 * Created by seokjunjeong on 2017. 7. 23..
 */

public class BrandListFragment extends BaseFragment<BrandListFragmentBinding> implements BrandListContract.View {
    private BrandListContract.Presenter mPresenter;

    public static BrandListFragment newInstance(){
        BrandListFragment fragment = new BrandListFragment();
        return fragment;
    }

    @Override
    public void setPresenter(BrandListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.brand_list_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void newPresenter() {
        new BrandListPresenter(this);
    }

    @Override
    protected void startPresenter() {
        mPresenter.start();
    }
}
