package kr.co.mash_up.a9tique.ui.contents_detail;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.databinding.ContentsDetailFragmentBinding;
import kr.co.mash_up.a9tique.ui.CommentAdapter;
import kr.co.mash_up.a9tique.ui.ImageViewAdapter;
import kr.co.mash_up.a9tique.ui.OnItemClickListener;
import kr.co.mash_up.a9tique.ui.ProductListSemiAdapter;

/**
 * Created by seokjunjeong on 2017. 7. 7..
 */

public class ContentsDetailFragment extends BaseFragment<ContentsDetailFragmentBinding> implements ContentsDetailContract.View {
    private ContentsDetailContract.Presenter mPresenter;

    private RecyclerView mRvComment, mRvProductListSemi;
    private CommentAdapter mCommentAdapter;
    private ProductListSemiAdapter mProductListSemiAdapter;
    private ViewPager mVpEvent;
    private ImageViewAdapter mIvaEvent;

    private OnItemClickListener mListener = new OnItemClickListener() {
        @Override
        public void onClick(int position) {

        }
    };

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

        mVpEvent = mBinding.vpEvent;
        mIvaEvent = new ImageViewAdapter();
        mVpEvent.setAdapter(mIvaEvent);


        mRvComment = mBinding.rvComments;
        mCommentAdapter = new CommentAdapter();
        mRvComment.setAdapter(mCommentAdapter);

        mRvProductListSemi = mBinding.rvProductListSemi;
        mProductListSemiAdapter = new ProductListSemiAdapter(mListener);
        mRvProductListSemi.setAdapter(mProductListSemiAdapter);

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
