package kr.co.mash_up.a9tique.ui.promotions.products;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;

/**
 * Created by seokjunjeong on 2017. 6. 25..
 */

public class PromotionProductListActivity extends BaseActivity<PromotionProductsFragment> {
    @Override
    protected int getLayoutId() {
        return R.layout.promotion_products_activity;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.contentFrame;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected PromotionProductsFragment getFragment() {
        return PromotionProductsFragment.newInstance();
    }
    @Override
    public void onBackPressed() {
        if(mFragment != null && mFragment.isShowTopCategoryList()){
            mFragment.hideTopCategoryList();
            return;
        }
        if(mFragment != null && mFragment.isShowSubCategoryList()){
            mFragment.hideSubCategoryList();
            return;
        }
        super.onBackPressed();
    }
}
