package kr.co.mash_up.a9tique;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import butterknife.BindView;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;

/**
 * Created by Dong on 2016-11-16.
 */

public class OuterCategoryFragment extends BaseFragment {

    private static final String ARG_PARAM_TITLE = "title";

    private String mParamTitle;

    @BindView(R.id.tl_sub_categories)
    TabLayout mTlSubCategories;

    @BindView(R.id.vp_products)
    SwipeViewPager mVpProducts;

    CategoryPagerAdapter mSubCategoryPagerAdapter;

    public OuterCategoryFragment() {
        // Required empty public constructor
    }

    public static OuterCategoryFragment newInstance(String paramTitle) {
        OuterCategoryFragment fragment = new OuterCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TITLE, paramTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamTitle = getArguments().getString(ARG_PARAM_TITLE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category_with_sub;
    }

    @Override
    public void initView(View rootView) {
        setupSubCategoryViewPager();
        mVpProducts.setPagingEnabled(false);
        mTlSubCategories.setupWithViewPager(mVpProducts);
    }

    private void setupSubCategoryViewPager() {
        mSubCategoryPagerAdapter = new CategoryPagerAdapter(getChildFragmentManager());
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance("ALL"), "ALL");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance("자켓"), "자켓");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance("코트"), "코트");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance("점퍼"), "점퍼");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance("후드집업"), "후드집업");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance("베스트"), "베스트");
        mVpProducts.setAdapter(mSubCategoryPagerAdapter);
    }
}
