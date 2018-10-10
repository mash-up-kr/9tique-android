package kr.co.mash_up.a9tique.ui.main.home;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.databinding.HomeFragmentBinding;
import kr.co.mash_up.a9tique.ui.DashboardAdapter;
import kr.co.mash_up.a9tique.ui.OnItemClickListener;
import kr.co.mash_up.a9tique.ui.promotions.products.PromotionProductListActivity;

/**
 * Created by seokjunjeong on 2017. 6. 4..
 */

public class HomeFragment extends BaseFragment<HomeFragmentBinding> implements HomeContract.View {
    private HomeContract.Presenter mPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRvDashboard;
    private DashboardAdapter mDashboardAdapter;

    private OnItemClickListener mListener = position -> {// Sample
        Snackbar.make(getView(), "Sample Click " + position, Snackbar.LENGTH_LONG).show();
        if(position == 0){
            startActivity(new Intent(getContext(), PromotionProductListActivity.class));
        }

    };

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout = mBinding.swipeRefreshLayout;
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            // Sample
            new Handler().postDelayed(() -> {

                Snackbar.make(getView(), "Sample Refresh", Snackbar.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }, 1000);
        });

        mRvDashboard = mBinding.rvDashboard;
        mDashboardAdapter = new DashboardAdapter(mListener);
        mRvDashboard.setAdapter(mDashboardAdapter);

        // Sample
        for (int i = 0; i < 5; i++) {
            mDashboardAdapter.addData(String.format("Sample %d", i));
            mDashboardAdapter.notifyItemInserted(i);
        }
    }

    @Override
    protected void newPresenter() {
        new HomePresenter(this);
    }

    @Override
    protected void startPresenter() {
        mPresenter.start();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
