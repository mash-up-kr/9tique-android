package kr.co.mash_up.a9tique.ui.products;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.ui.widget.SwipeViewPager;

/**
 * Created by Dong on 2016-11-16.
 */

public class BottomCategoryFragment extends BaseFragment {

    private static final String ARG_PARAM_TITLE = "title";

    private String mParamTitle;

    @BindView(R.id.tl_sub_categories)
    TabLayout mTlSubCategories;

    @BindView(R.id.vp_products)
    SwipeViewPager mVpProducts;

    CategoryPagerAdapter mSubCategoryPagerAdapter;

    public BottomCategoryFragment() {
        // Required empty public constructor
    }

    public static BottomCategoryFragment newInstance(String paramTitle) {
        BottomCategoryFragment fragment = new BottomCategoryFragment();
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
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance("데님팬츠"), "데님팬츠");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance("팬츠"), "팬츠");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance("쇼츠"), "쇼츠");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance("스커트"), "스커트");
        mVpProducts.setAdapter(mSubCategoryPagerAdapter);
    }
}
