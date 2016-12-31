package kr.co.mash_up.a9tique.ui.products;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.data.SubCategory;
import kr.co.mash_up.a9tique.ui.widget.SwipeViewPager;

/**
 * Created by Dong on 2016-11-16.
 */

public class TopCategoryFragment extends BaseFragment {

    private static final String ARG_PARAM_MAIN_CATEGORY = "mainCategory";

    private String mParamMainCategory;

    @BindView(R.id.tl_sub_categories)
    TabLayout mTlSubCategories;

    @BindView(R.id.vp_products)
    SwipeViewPager mVpProducts;

    CategoryPagerAdapter mSubCategoryPagerAdapter;

    public TopCategoryFragment() {
        // Required empty public constructor
    }

    public static TopCategoryFragment newInstance(String mainCategory) {
        TopCategoryFragment fragment = new TopCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_MAIN_CATEGORY, mainCategory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamMainCategory = getArguments().getString(ARG_PARAM_MAIN_CATEGORY);
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
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(mParamMainCategory, SubCategory.List.ALL.name()), "ALL");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(mParamMainCategory, SubCategory.List.TSHIRT.name()), "티셔츠");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(mParamMainCategory, SubCategory.List.HOODS_TSHIRT.name()), "후드티셔츠");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(mParamMainCategory, SubCategory.List.SLEEVELESS.name()), "슬리브리스");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(mParamMainCategory, SubCategory.List.SHIRT.name()), "셔츠");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(mParamMainCategory, SubCategory.List.KNIT.name()), "니트");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(mParamMainCategory, SubCategory.List.BLOUSE.name()), "블라우스");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(mParamMainCategory, SubCategory.List.ONE_PIECE.name()), "원피스");
        mVpProducts.setAdapter(mSubCategoryPagerAdapter);
    }
}
