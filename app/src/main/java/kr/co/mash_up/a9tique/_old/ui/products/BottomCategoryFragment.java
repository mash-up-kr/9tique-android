package kr.co.mash_up.a9tique._old.ui.products;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique._old.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.data.SubCategory;

/**
 * Created by Dong on 2016-11-16.
 */

public class BottomCategoryFragment extends BaseFragment {

    private static final String ARG_PARAM_MAIN_CATEGORY = "mainCategory";

    private String mParamMainCategory;

    @BindView(R.id.tl_sub_categories)
    TabLayout mTlSubCategories;

    private CategoryPagerAdapter mSubCategoryPagerAdapter;

    public BottomCategoryFragment() {
        // Required empty public constructor
    }

    public static BottomCategoryFragment newInstance(String mainCategory) {
        BottomCategoryFragment fragment = new BottomCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_MAIN_CATEGORY, mainCategory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
    }

    private void setupSubCategoryViewPager() {
        mSubCategoryPagerAdapter = new CategoryPagerAdapter(getChildFragmentManager());
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(mParamMainCategory, SubCategory.List.ALL.name()), "ALL");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(mParamMainCategory, SubCategory.List.DENIM_PANTS.name()), "데님팬츠");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(mParamMainCategory, SubCategory.List.PANTS.name()), "팬츠");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(mParamMainCategory, SubCategory.List.SHORTS.name()), "쇼츠");
        mSubCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(mParamMainCategory, SubCategory.List.SKIRT.name()), "스커트");

        for (int i = 0; i < mSubCategoryPagerAdapter.getCount(); i++) {
            mTlSubCategories.addTab(mTlSubCategories.newTab().setText(mSubCategoryPagerAdapter.getPageTitle(i)));
        }

        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.fl_products_fragment_container);
        if (fragment == null) {
            fragment = mSubCategoryPagerAdapter.getItem(0);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.fl_products_fragment_container, fragment, fragment.getTag())
                    .commit();
        }

        mTlSubCategories.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int currentPosition = tab.getPosition();
                Fragment fragment = mSubCategoryPagerAdapter.getItem(currentPosition);

                getChildFragmentManager().beginTransaction()
                        .replace(R.id.fl_products_fragment_container, fragment, fragment.getTag())
                        .commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
