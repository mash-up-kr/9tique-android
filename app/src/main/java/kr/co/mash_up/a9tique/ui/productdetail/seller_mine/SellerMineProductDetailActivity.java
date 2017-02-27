package kr.co.mash_up.a9tique.ui.productdetail.seller_mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.common.eventbus.EventRefreshProduct;
import kr.co.mash_up.a9tique.common.eventbus.RxEventBus;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.ui.addeditproduct.AddEditProductActivity;
import kr.co.mash_up.a9tique.util.SnackbarUtil;

/**
 * data load
 * open add edit activity
 * product remove
 * product sold out
 */
public class SellerMineProductDetailActivity extends BaseActivity {

    public static final int REQUEST_CODE_DETAIL_RPODUCT = 10;

    @BindView(R.id.cl_root)
    CoordinatorLayout mClRoot;

    @BindView(R.id.abl_detail)
    AppBarLayout mAblDetail;

    @BindView(R.id.ctl_detail)
    CollapsingToolbarLayout mCtlDetail;

    @BindView(R.id.toolbar_seller_mine_detail)
    Toolbar mToolbarDetail;

    @BindView(R.id.iv_product_thumbnail)
    ImageView mIvProductThumbnail;

    @BindView(R.id.iv_navigate_up)
    ImageView mIvNavigateUp;

    @BindView(R.id.iv_modify)
    ImageView mIvModify;

    private Product mProduct;
    private String mTransitionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().hasExtra(Constants.PRODUCT)) {
            mProduct = getIntent().getParcelableExtra(Constants.PRODUCT);
            mTransitionName = getIntent().getStringExtra(Constants.TRANSITION_NAME);
        }
        super.onCreate(savedInstanceState);

        SellerMineProductDetailFragment sellerMineProductDetailFragment =
                (SellerMineProductDetailFragment) getSupportFragmentManager().findFragmentByTag(SellerMineProductDetailFragment.TAG);

        if (null == sellerMineProductDetailFragment) {
            sellerMineProductDetailFragment = SellerMineProductDetailFragment.newInstance(mProduct);

            initFragment(sellerMineProductDetailFragment);
        }

        //Todo: Create the presenter
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_seller_mine_product_detail;
    }

    @Override
    public void initView() {
        setupToolbar();
    }

    @Override
    public void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_fragment_seller_product_detail, fragment, SellerMineProductDetailFragment.TAG)
                .commit();
    }

    @UiThread
    private void setupToolbar() {
        setSupportActionBar(mToolbarDetail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        supportPostponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mIvProductThumbnail.setTransitionName(mTransitionName);
        }

        Glide.with(SellerMineProductDetailActivity.this)
                .load(Constants.END_POINT + mProduct.getProductImages().get(0).getImageUrl())
                .placeholder(R.drawable.ic_nodata)
                .error(R.drawable.ic_nodata)
                .crossFade()
                .fitCenter()
                .centerCrop()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }
                })
                .into(mIvProductThumbnail);

        // verticalOffset은 0 ~ -xx 값을 갖는다
        mAblDetail.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (mCtlDetail.getHeight() + verticalOffset < mCtlDetail.getScrimVisibleHeightTrigger()) {
                // collapsed
                mIvNavigateUp.setImageResource(R.drawable.ic_arrow_left_white);
                mIvModify.setImageResource(R.drawable.ic_modify_white);
            } else {
                // extended
                mIvNavigateUp.setImageResource(R.drawable.ic_arrow_left_black);
                mIvModify.setImageResource(R.drawable.ic_modify_black);
            }
        });
    }

    @OnClick(R.id.iv_navigate_up)
    public void onClickNavigateUp() {
        finish();
        // Todo: 전환 애니메이션 구현
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @OnClick(R.id.iv_modify)
    public void onClickModify(View view) {
        // open seller product edit activity
        Intent intent = new Intent(SellerMineProductDetailActivity.this, AddEditProductActivity.class);
        intent.putExtra(Constants.PRODUCT_ID, mProduct.getId());
        intent.putExtra(Constants.PRODUCT, mProduct);
        startActivityForResult(intent, AddEditProductActivity.REQUEST_CODE_EDIT_PRODUCT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AddEditProductActivity.REQUEST_CODE_EDIT_PRODUCT:
                if (resultCode == Activity.RESULT_OK) {
                    SnackbarUtil.showMessage(SellerMineProductDetailActivity.this, mClRoot, "상품 수정 완료", "", null);
                    getProductDetail();
                } else {
                    SnackbarUtil.showMessage(SellerMineProductDetailActivity.this, mClRoot, "상품 수정 실패", "", null);
                }
                break;
        }
    }

    private void getProductDetail() {
        BackendHelper.getInstance().getProductDetail(mProduct.getId(), new ResultCallback<Product>() {
            @Override
            public void onSuccess(@Nullable Product product) {
                RxEventBus.getInstance().post(new EventRefreshProduct(product));
            }

            @Override
            public void onFailure() {
                // Todo: 어쩌지..
            }
        });
    }
}