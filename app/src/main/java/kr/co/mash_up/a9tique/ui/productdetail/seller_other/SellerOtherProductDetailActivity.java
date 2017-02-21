package kr.co.mash_up.a9tique.ui.productdetail.seller_other;

import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.data.Product;

/**
 * data load
 * open add edit activity
 * product remove
 * product sold out
 */
public class SellerOtherProductDetailActivity extends BaseActivity {

    @BindView(R.id.abl_detail)
    AppBarLayout mAblDetail;

    @BindView(R.id.ctl_detail)
    CollapsingToolbarLayout mCtlDetail;

    @BindView(R.id.toolbar_seller_other_detail)
    Toolbar mToolbarDetail;

    @BindView(R.id.iv_product_thumbnail)
    ImageView ivProductThumbnail;

    @BindView(R.id.iv_navigate_up)
    ImageView mIvNavigateUp;

    private Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().hasExtra(Constants.PRODUCT)) {
            mProduct = getIntent().getParcelableExtra(Constants.PRODUCT);
        }
        super.onCreate(savedInstanceState);

        SellerOtherProductDetailFragment sellerOtherProductDetailFragment =
                (SellerOtherProductDetailFragment) getSupportFragmentManager().findFragmentByTag(SellerOtherProductDetailFragment.TAG);

        if (null == sellerOtherProductDetailFragment) {
            sellerOtherProductDetailFragment = SellerOtherProductDetailFragment.newInstance(mProduct);

            initFragment(sellerOtherProductDetailFragment);
        }

        //Todo: Create the presenter
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_seller_other_product_detail;
    }

    @Override
    public void initView() {
        setupToolbar();
    }

    @Override
    public void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_fragment_seller_product_detail, fragment, SellerOtherProductDetailFragment.TAG)
                .commit();
    }

    @UiThread
    private void setupToolbar() {
        setSupportActionBar(mToolbarDetail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        Glide.with(SellerOtherProductDetailActivity.this)
                .load(Constants.END_POINT + mProduct.getProductImages().get(0).getImageUrl())
                .placeholder(R.drawable.ic_nodata)
                .error(R.drawable.ic_nodata)
                .crossFade()
                .fitCenter()
                .centerCrop()
                .into(ivProductThumbnail);

        // verticalOffset은 0 ~ -xx 값을 갖는다
        mAblDetail.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (mCtlDetail.getHeight() + verticalOffset < mCtlDetail.getScrimVisibleHeightTrigger()) {
                // collapsed
                mIvNavigateUp.setImageResource(R.drawable.ic_arrow_left_white);
            } else {
                // extended
                mIvNavigateUp.setImageResource(R.drawable.ic_arrow_left_black);
            }
        });
    }

    @OnClick(R.id.iv_navigate_up)
    public void onClickNavigateUp() {
        finish();
        // Todo: 전환 애니메이션 구현
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}