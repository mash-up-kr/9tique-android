package kr.co.mash_up.a9tique.ui.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.databinding.MainActivityBinding;
import kr.co.mash_up.a9tique.ui.brand_list.BrandListActivity;
import kr.co.mash_up.a9tique.ui.main.contents.ContentsFragment;
import kr.co.mash_up.a9tique.ui.main.home.HomeFragment;
import kr.co.mash_up.a9tique.ui.main.shop.ShopFragment;
import kr.co.mash_up.a9tique.util.ui.FragmentUtil;

/**
 * Created by seokjunjeong on 2017. 6. 4..
 */

public class MainActivity extends AppCompatActivity {
    private MainActivityBinding mBinding;
    private int mFragmentContentId;
    private HomeFragment mHomeFragment;
    private ContentsFragment mContentsFragment;
    private ShopFragment mShopFragment;
    private SlidingUpPanelLayout mSlidingUpPanelLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        mBinding.setActivity(this);

        mFragmentContentId = R.id.contentFrame;
        mHomeFragment = new HomeFragment();
        mContentsFragment = new ContentsFragment();
        mShopFragment = new ShopFragment();

        FragmentUtil.addFragment(this, mFragmentContentId, mHomeFragment);

        mSlidingUpPanelLayout = mBinding.slidingLayout;
        hideSlidingMenu();
        mSlidingUpPanelLayout.setFadeOnClickListener(view -> hideSlidingMenu());
    }

    public void replaceFragment(Fragment fragment) {
        FragmentUtil.replaceFragment(this, mFragmentContentId, fragment);
    }

    // call databinding
    public void onClickToolbarItem(View v) {
        switch (v.getId()) {
            case R.id.tv_home: {
                replaceFragment(mHomeFragment);
            }
            break;
            case R.id.tv_contents: {
                replaceFragment(mContentsFragment);
            }
            break;
            case R.id.tv_shop: {
                replaceFragment(mShopFragment);
            }
            break;
        }
    }

    // call databinding
    public void onClickShowSlidMenu(View v) {
        showSlidingMenu();
    }

    // call dataBinding
    public void onMenuItemClick(View v){
        switch (v.getId()){
            case R.id.iv_home:{
                replaceFragment(mHomeFragment);
            }
            break;
            case R.id.iv_exit:{
            }
            break;
            case R.id.tv_brand:{
                startActivity(new Intent(getApplicationContext(), BrandListActivity.class));
            }
        }

        hideSlidingMenu();

    }

    @Override
    public void onBackPressed() {
        if (mSlidingUpPanelLayout != null &&
                (mSlidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)) {
            hideSlidingMenu();
            return;
        }
        if(mShopFragment != null && mShopFragment.isShowTopCategoryList()){
            mShopFragment.hideTopCategoryList();
            return;
        }
        if(mShopFragment != null && mShopFragment.isShowSubCategoryList()){
            mShopFragment.hideSubCategoryList();
            return;
        }
        super.onBackPressed();
    }

    private void hideSlidingMenu() {
        mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    private void showSlidingMenu() {
        mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

    }
}
