package kr.co.mash_up.a9tique.ui.main.contents;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.databinding.ContentsFragmentBinding;
import kr.co.mash_up.a9tique.ui.DashboardAdapter;
import kr.co.mash_up.a9tique.ui.OnItemClickListener;
import kr.co.mash_up.a9tique.ui.contents_detail.ContentsDetailActivity;

/**
 * Created by seokjunjeong on 2017. 6. 11..
 */

public class ContentsFragment extends BaseFragment<ContentsFragmentBinding>
        implements ContentsContract.View{
    private ContentsContract.Presenter mPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRvDashboard;
    private DashboardAdapter mDashboardAdapter;

    private OnItemClickListener mListener = position -> {// Sample
        startActivity(new Intent(getContext(), ContentsDetailActivity.class));
        Snackbar.make(getView(), "Sample Click " + position, Snackbar.LENGTH_LONG).show();

    };

    public static ContentsFragment newInstance() {
        ContentsFragment contentsFragment = new ContentsFragment();
        return contentsFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.contents_fragment;
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
            mDashboardAdapter.addData(String.format("Sample %d", i+5));
            mDashboardAdapter.notifyItemInserted(i);
        }
    }

    @Override
    protected void newPresenter() {
        new ContentsPresenter(this);
    }

    @Override
    protected void startPresenter() {
        mPresenter.start();
    }

    @Override
    public void setPresenter(ContentsContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
