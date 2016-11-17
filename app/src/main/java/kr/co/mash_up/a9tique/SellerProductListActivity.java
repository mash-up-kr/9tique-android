package kr.co.mash_up.a9tique;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rd.PageIndicatorView;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;

//Todo: EventPage 무한 스크롤
//Todo: EventPage 오토 스크롤
public class SellerProductListActivity extends BaseActivity {

    public static final String TAG = SellerProductListActivity.class.getSimpleName();

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
        setSupportActionBar(mToolbar);

        final ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setHomeAsUpIndicator(android.R.drawable.ic_menu_day);
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }
        mCtlSellerProductList.setTitleEnabled(false);

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

    private void setupMainCategoryViewPager() {
        mMainCategoryPagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager());
        mMainCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance("New"), "New");
        mMainCategoryPagerAdapter.addFragment(OuterCategoryFragment.newInstance("아우터"), "아우터");
        mMainCategoryPagerAdapter.addFragment(TopCategoryFragment.newInstance("상의"), "상의");
        mMainCategoryPagerAdapter.addFragment(BottomCategoryFragment.newInstance("하의"), "하의");
        mMainCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance("신발"), "신발");
        mMainCategoryPagerAdapter.addFragment(SubCategoryFragment.newInstance("모자"), "모자");
        mVpMainCategories.setAdapter(mMainCategoryPagerAdapter);
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
        switch (id){
            case android.R.id.home:
                //Todo: open setting activity, remove toast
                Toast.makeText(this, "open setting activity", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_seller_products:
                //Todo: open seller products activity, remove toast
                Toast.makeText(this, "open seller products activity", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab_add_product)
    public void addProduct(View view){
        //Todo: open seller add product
        Snackbar.make(view, "Seller Add Product", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
