package kr.co.mash_up.a9tique.ui.main.promotion_product_list;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.databinding.PromotionProductListFragmentBinding;
import kr.co.mash_up.a9tique.ui.OnItemClickListener;
import kr.co.mash_up.a9tique.ui.ProductListAdapter;

/**
 * Created by seokjunjeong on 2017. 6. 25..
 */

public class PromotionProductListFragment
        extends BaseFragment<PromotionProductListFragmentBinding>
        implements PromotionProductListContract.View {
    private PromotionProductListContract.Presenter mPresenter;
    private RecyclerView mRvProductList;
    private ProductListAdapter mProductListAdapter;
    private LinearLayout mLlTopCategory, mLlSubCategory;
    private TextView mTvTopCategory, mTvSubCategory;
    private OnItemClickListener mListener = position -> {
        // Sample
        Snackbar.make(getView(), "Sample Click " + position, Snackbar.LENGTH_LONG).show();
    };
    private View.OnClickListener mOnTopCategoryItemClickListener = view -> {
        mTvTopCategory.setText(((TextView) view).getText().toString());
        hideTopCategoryList();
        // Todo 하위 카테고리 어떻게 지정해야할지 고민해야함;;;;
        switch (view.getId()) {
            case R.id.tv_all: {

            }
            break;
            case R.id.tv_top: {

            }
            break;
            case R.id.tv_outer: {

            }
            break;
            case R.id.tv_onepiece: {

            }
            break;
            case R.id.tv_bottom: {

            }
            break;
            case R.id.tv_headwear: {

            }
            break;
            case R.id.tv_shoes: {

            }
            break;
        }

    };
    private View.OnClickListener mOnSortClickListener = view -> {
        mBinding.include.tvLastest.setSelected(false);
        mBinding.include.tvPopularity.setSelected(false);
        mBinding.include.tvLowPrice.setSelected(false);
        mBinding.include.tvHighPrice.setSelected(false);
        view.setSelected(true);

        // Todo 샵 상품 리스트 정렬 처리
        switch (view.getId()) {
            case R.id.tv_lastest: {

            }
            break;
            case R.id.tv_popularity: {

            }
            break;
            case R.id.tv_low_price: {

            }
            break;
            case R.id.tv_high_price: {

            }
            break;
        }

    };

    public static PromotionProductListFragment newInstance() {
        PromotionProductListFragment fragment = new PromotionProductListFragment();
        return fragment;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.promotion_product_list_fragment;
    }

    @Override
    protected void initView() {
        mRvProductList = mBinding.include.rvProductList;
        mProductListAdapter = new ProductListAdapter(mListener);
        mRvProductList.setAdapter(mProductListAdapter);

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

        mProductListAdapter.setProducts(products);
        mProductListAdapter.notifyDataSetChanged();

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
            if (mTvTopCategory.getText().toString().equals(getString(R.string.title_all)) ||
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

        mBinding.include.tvLastest.setOnClickListener(mOnSortClickListener);
        mBinding.include.tvPopularity.setOnClickListener(mOnSortClickListener);
        mBinding.include.tvLowPrice.setOnClickListener(mOnSortClickListener);
        mBinding.include.tvHighPrice.setOnClickListener(mOnSortClickListener);

        mBinding.include.tvLastest.setSelected(true);
    }

    @Override
    protected void newPresenter() {
        new PromotionProductListPresenter(this);
    }

    @Override
    protected void startPresenter() {
        mPresenter.start();
    }

    @Override
    public void setPresenter(PromotionProductListContract.Presenter presenter) {
        mPresenter = presenter;
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
