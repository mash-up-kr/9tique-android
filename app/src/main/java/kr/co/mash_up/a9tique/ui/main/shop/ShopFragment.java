package kr.co.mash_up.a9tique.ui.main.shop;

import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.databinding.ProductListLayoutBinding;
import kr.co.mash_up.a9tique.databinding.ShopFragmentBinding;
import kr.co.mash_up.a9tique.ui.OnItemClickListener;
import kr.co.mash_up.a9tique.ui.ProductListAdapter;

/**
 * Created by seokjunjeong on 2017. 6. 22..
 */

public class ShopFragment extends BaseFragment<ShopFragmentBinding> implements ShopContract.View {
    private ShopContract.Presenter mPresenter;
    private ProductListLayoutBinding mLayoutBinding;
    private RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;


    private OnItemClickListener mListener = position -> {
        // Sample
        Snackbar.make(getView(), "Sample Click " + position, Snackbar.LENGTH_LONG).show();
    };

    @Override
    public void setPresenter(ShopContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.shop_fragment;
    }

    @Override
    protected void initView() {
//        mLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
//                R.layout.product_list_layout, (ViewGroup) mBinding.getRoot(), false);
//
//        mRecyclerView = mLayoutBinding.rvProductList;
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.rv_product_list);
        mAdapter = new ProductListAdapter(mListener);
        mRecyclerView.setAdapter(mAdapter);


        // Sample
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("", "brandName", 1000, "category", "size", true, 100));
        products.add(new Product("", "brandName", 1000, "category", "size", true, 100));
        products.add(new Product("", "brandName", 1000, "category", "size", true, 100));
        products.add(new Product("", "brandName", 1000, "category", "size", true, 100));
        products.add(new Product("", "brandName", 1000, "category", "size", true, 100));
        products.add(new Product("", "brandName", 1000, "category", "size", true, 100));
        products.add(new Product("", "brandName", 1000, "category", "size", true, 100));
        products.add(new Product("", "brandName", 1000, "category", "size", true, 100));
        mAdapter.setProducts(products);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void newPresenter() {
        new ShopPresenter(this);
    }

    @Override
    protected void startPresenter() {
        mPresenter.start();
    }
}
