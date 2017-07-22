package kr.co.mash_up.a9tique.ui.product_detail;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.databinding.ProductDetailFragmentBinding;

/**
 * Created by seokjunjeong on 2017. 7. 22..
 */

public class ProductDetailFragment extends BaseFragment<ProductDetailFragmentBinding> implements ProductDetailContract.View {
    private ProductDetailContract.Presenter mPresenter;

    public static ProductDetailFragment newInstance(){
        ProductDetailFragment fragment = new ProductDetailFragment();
        return fragment;
    }

    @Override
    public void setPresenter(ProductDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.product_detail_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void newPresenter() {
        new ProductDetailPresenter(this);
    }

    @Override
    protected void startPresenter() {
        mPresenter.start();
    }
}
