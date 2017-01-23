package kr.co.mash_up.a9tique.ui.products;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rd.PageIndicatorView;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.ui.sellproducts.SellProductsActivity;
import kr.co.mash_up.a9tique.ui.setting.SettingActivity;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;
import kr.co.mash_up.a9tique.data.MainCategory;
import kr.co.mash_up.a9tique.ui.addeditproduct.AddEditProductActivity;
import kr.co.mash_up.a9tique.util.SnackbarUtil;

//Todo: EventPage 무한 스크롤
//Todo: EventPage 오토 스크롤
public class SellerProductListActivity extends BaseActivity {

    public static final String TAG = SellerProductListActivity.class.getSimpleName();
    public static final String CURRENT_PAGER_POSITION = "current_pager_position";

    @BindView(R.id.cl_root)
    CoordinatorLayout mClRoot;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.abl_seller_product_list)
    AppBarLayout mAblSellerProductList;

    @BindView(R.id.ctl_seller_product_list)
    CollapsingToolbarLayout mCtlSellerProductList;

    @BindView(R.id.vp_event)
    ViewPager mVpEvents;

    @BindView(R.id.piv_event)
    PageIndicatorView mPivEvents;

    @BindView(R.id.tl_main_categories)
    TabLayout mTlMainCategories;

    @BindView(R.id.vp_main_categories)
    ViewPager mVpMainCategories;

    CategoryPagerAdapter mMainCategoryPagerAdapter;

    SellerProductListEventPagerAdapter mEventPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_seller_product_list;
    }

    @Override
    public void initView() {
        setupToolbar();
        setupMainCategoryViewPager();
        setupEventViewPager();

        mTlMainCategories.setupWithViewPager(mVpMainCategories);
        mTlMainCategories.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mVpMainCategories.setCurrentItem(mTlMainCategories.getSelectedTabPosition());
                mAblSellerProductList.setExpanded(false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mAblSellerProductList.setExpanded(true);

        // https://developer.android.com/reference/android/support/v7/graphics/Palette.html
        // 색 바꿀 때 사용
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//
//            @SuppressWarnings("ResourceType")
//            @Override
//            public void onGenerated(Palette palette) {
//                int vibrantColor = palette.getVibrantColor(R.color.colorPrimary);
//                int vibrantDarkColor = palette.getDarkVibrantColor(R.color.colorPrimaryDark);
//                mCtlSellerProductList.setContentScrimColor(vibrantColor);
//                mCtlSellerProductList.setStatusBarScrimColor(vibrantDarkColor);
//            }
//        });
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_setting_white);
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }
        mCtlSellerProductList.setTitleEnabled(false);
    }

    private void setupMainCategoryViewPager() {
        mMainCategoryPagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager());
        mMainCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(MainCategory.List.NEW.name(), ""), "New");
        mMainCategoryPagerAdapter.addFragment(OuterCategoryFragment.newInstance(MainCategory.List.OUTER.name()), "아우터");
        mMainCategoryPagerAdapter.addFragment(TopCategoryFragment.newInstance(MainCategory.List.TOP.name()), "상의");
        mMainCategoryPagerAdapter.addFragment(BottomCategoryFragment.newInstance(MainCategory.List.BOTTOM.name()), "하의");
        mMainCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(MainCategory.List.SHOSE.name(), ""), "신발");
        mMainCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance(MainCategory.List.CAP.name(), ""), "모자");
        mVpMainCategories.setAdapter(mMainCategoryPagerAdapter);
        mVpMainCategories.setOffscreenPageLimit(mMainCategoryPagerAdapter.getCount());
    }

    private void setupEventViewPager() {
        mEventPagerAdapter = new SellerProductListEventPagerAdapter(getSupportFragmentManager());
        mEventPagerAdapter.addFragment(SellerProductListEventFragment.newInstance());
        mEventPagerAdapter.addFragment(SellerProductListEventFragment.newInstance());
        mEventPagerAdapter.addFragment(SellerProductListEventFragment.newInstance());
        mEventPagerAdapter.addFragment(SellerProductListEventFragment.newInstance());
        mEventPagerAdapter.addFragment(SellerProductListEventFragment.newInstance());
        mVpEvents.setAdapter(mEventPagerAdapter);
    }

    @Override
    public void initFragment(Fragment fragment) {
        // Do nothing
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seller_product_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                startActivity(new Intent(SellerProductListActivity.this, SettingActivity.class));
                return true;
            case R.id.action_seller_products:
                startActivity(new Intent(SellerProductListActivity.this, SellProductsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // open seller add product
    @OnClick(R.id.fab_add_product)
    public void addProduct(View view) {
        Intent intent = new Intent(SellerProductListActivity.this, AddEditProductActivity.class);
        startActivityForResult(intent, AddEditProductActivity.REQUEST_CODE_ADD_RPODUCT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case AddEditProductActivity.REQUEST_CODE_ADD_RPODUCT:
                if (resultCode == Activity.RESULT_OK) {
                    SnackbarUtil.showMessage(SellerProductListActivity.this, mClRoot, "상품 등록 완료", "", null);
                    //Todo: reloading

                } else {
                    SnackbarUtil.showMessage(SellerProductListActivity.this, mClRoot, "상품 등록 실패. 다시 등록해주세요", "RETRY", view -> {
                        Intent intent = new Intent(SellerProductListActivity.this, AddEditProductActivity.class);
                        startActivityForResult(intent, AddEditProductActivity.REQUEST_CODE_ADD_RPODUCT);
                    });
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_PAGER_POSITION, mTlMainCategories.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVpMainCategories.setCurrentItem(savedInstanceState.getInt(CURRENT_PAGER_POSITION));
    }
}
