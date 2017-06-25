package kr.co.mash_up.a9tique.ui.main.shop;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;

    private LinearLayout mLlTopCategory, mLlSubCategory;
    private TextView mTvTopCategory, mTvSubCategory;


    private OnItemClickListener mListener = position -> {
        // Sample
        Snackbar.make(getView(), "Sample Click " + position, Snackbar.LENGTH_LONG).show();
    };

    private View.OnClickListener mOnTopCategoryItemClickListener = view -> {
        mTvTopCategory.setText(((TextView)view).getText().toString());
        hideTopCategoryList();
        // Todo 하위 카테고리 어떻게 지정해야할지 고민해야함;;;;
        switch (view.getId()){
            case R.id.tv_all:{

            }
            break;
            case R.id.tv_top:{

            }
            break;
            case R.id.tv_outer:{

            }
            break;
            case R.id.tv_onepiece:{

            }
            break;
            case R.id.tv_bottom:{

            }
            break;
            case R.id.tv_headwear:{

            }
            break;
            case R.id.tv_shoes:{

            }
            break;
        }

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
        mRecyclerView = mBinding.include.rvProductList;
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

        mLlTopCategory = mBinding.include.llTopCategory;
        mLlSubCategory = mBinding.include.llSubCategory;

        mTvTopCategory = mBinding.include.tvTopCategory;
        mTvSubCategory = mBinding.include.tvSubCategory;

        mTvTopCategory.setOnClickListener(view -> {
            hideSubCategoryList();
            if (mLlTopCategory.getVisibility() == View.VISIBLE) {
                hideTopCategoryList();
            } else {
                showTopCategoryList();
            }
        });
        mTvSubCategory.setOnClickListener(view -> {
            if (mTvTopCategory.getText().toString().equals(getString(R.string.title_all))||
                    mTvTopCategory.getText().toString().equals(getString(R.string.top_category)))
                return;
            hideTopCategoryList();
            if (mLlSubCategory.getVisibility() == View.VISIBLE) {
                hideSubCategoryList();
            } else {
                showSubCategoryList();
            }
        });

        mBinding.include.tvAll.setOnClickListener(mOnTopCategoryItemClickListener);
        mBinding.include.tvTop.setOnClickListener(mOnTopCategoryItemClickListener);
        mBinding.include.tvOuter.setOnClickListener(mOnTopCategoryItemClickListener);
        mBinding.include.tvOnepiece.setOnClickListener(mOnTopCategoryItemClickListener);
        mBinding.include.tvBottom.setOnClickListener(mOnTopCategoryItemClickListener);
        mBinding.include.tvHeadwear.setOnClickListener(mOnTopCategoryItemClickListener);
        mBinding.include.tvShoes.setOnClickListener(mOnTopCategoryItemClickListener);
    }

    @Override
    protected void newPresenter() {
        new ShopPresenter(this);
    }

    @Override
    protected void startPresenter() {
        mPresenter.start();
    }


    @Override
    public void showTopCategoryList() {
        mLlTopCategory.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSubCategoryList() {
        mLlSubCategory.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTopCategoryList() {
        mLlTopCategory.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideSubCategoryList() {
        mLlSubCategory.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean isShowTopCategoryList() {
        return mLlTopCategory.getVisibility() == View.VISIBLE;
    }

    @Override
    public boolean isShowSubCategoryList() {
        return mLlSubCategory.getVisibility() == View.VISIBLE;
    }
}
