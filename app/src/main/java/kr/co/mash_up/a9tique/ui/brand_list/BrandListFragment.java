package kr.co.mash_up.a9tique.ui.brand_list;

import android.support.v7.widget.RecyclerView;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.databinding.BrandListFragmentBinding;
import kr.co.mash_up.a9tique.ui.BrandListAdapter;
import kr.co.mash_up.a9tique.ui.OnItemClickListener;

/**
 * Created by seokjunjeong on 2017. 7. 23..
 */

public class BrandListFragment extends BaseFragment<BrandListFragmentBinding> implements BrandListContract.View {
    private BrandListContract.Presenter mPresenter;

    private RecyclerView mRvBlandList;
    private BrandListAdapter mBrandListAdapter;
    private OnItemClickListener mListener = position -> {

    };

    public static BrandListFragment newInstance() {
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
        mRvBlandList = mBinding.rvBrandList;
        mBrandListAdapter = new BrandListAdapter(mListener);
        mRvBlandList.setAdapter(mBrandListAdapter);
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
