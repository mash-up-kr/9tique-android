package kr.co.mash_up.a9tique._old.ui.productdetail.customer;

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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.like.LikeButton;
import com.like.OnLikeListener;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique._old.base.ui.BaseActivity;
import kr.co.mash_up.a9tique._old.common.Constants;
import kr.co.mash_up.a9tique._old.data.Product;
import kr.co.mash_up.a9tique.data.source.remote.BackendHelper;
import kr.co.mash_up.a9tique._old.data.remote.ResultCallback;
import kr.co.mash_up.a9tique._old.util.SnackbarUtil;

public class CustomerProductDetailActivity extends BaseActivity {

    public static final int REQUEST_CODE_DETAIL_RPODUCT = 1110;

    @BindView(R.id.cl_root)
    CoordinatorLayout mClRoot;

    @BindView(R.id.abl_detail)
    AppBarLayout mAblDetail;

    @BindView(R.id.ctl_detail)
    CollapsingToolbarLayout mCtlDetail;

    @BindView(R.id.toolbar_customer_detail)
    Toolbar mToolbarDetail;

    @BindView(R.id.iv_product_thumbnail)
    ImageView mIvProductThumbnail;

    @BindView(R.id.iv_navigate_up)
    ImageView mIvNavigateUp;

//    @BindView(R.id.iv_zzim)
//    ImageView mIvZzim;

    @BindView(R.id.lb_zzim)
    LikeButton mLbZzim;

    private Product mProduct;
    private String mTransitionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().hasExtra(Constants.PRODUCT)) {
            mProduct = getIntent().getParcelableExtra(Constants.PRODUCT);
            mTransitionName = getIntent().getStringExtra(Constants.TRANSITION_NAME);
        }
        super.onCreate(savedInstanceState);

        CustomerProductDetailFragment customerProductDetailFragment =
                (CustomerProductDetailFragment) getSupportFragmentManager().findFragmentByTag(CustomerProductDetailFragment.TAG);

        if (null == customerProductDetailFragment) {
            customerProductDetailFragment = CustomerProductDetailFragment.newInstance(mProduct);

            initFragment(customerProductDetailFragment);
        }

        //Todo: Create the presenter
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_customer_product_detail;
    }

    @Override
    public void initView() {
        setupToolbar();
    }

    @Override
    public void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_fragment_customer_product_detail, fragment, CustomerProductDetailFragment.TAG)
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

        Glide.with(CustomerProductDetailActivity.this)
                .load(Constants.END_POINT + mProduct.getProductImages().get(0).getImageUrl())
                .placeholder(R.drawable.ic_nodata)
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

        showZzimStatus();

        // verticalOffset은 0 ~ -xx 값을 갖는다
        mAblDetail.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (mCtlDetail.getHeight() + verticalOffset < mCtlDetail.getScrimVisibleHeightTrigger()) {
                // collapsed
                mIvNavigateUp.setImageResource(R.drawable.ic_arrow_left_white);
//                mIvZzim.setBackgroundResource(R.drawable.selector_btn_zzim_white);
                mLbZzim.setUnlikeDrawableRes(R.drawable.ic_heart_empty_white);
                mLbZzim.setLikeDrawableRes(R.drawable.ic_heart_fill_white);

            } else {
                // extended
                mIvNavigateUp.setImageResource(R.drawable.ic_arrow_left_black);
//                mIvZzim.setBackgroundResource(R.drawable.selector_btn_zzim_black);
                mLbZzim.setUnlikeDrawableRes(R.drawable.ic_heart_empty_black);
                mLbZzim.setLikeDrawableRes(R.drawable.ic_heart_fill_black);

            }
        });

        mLbZzim.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                mProduct.updateZzimStatue();
                addZzimProduct();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                mProduct.updateZzimStatue();
                removeZzimProduct();
            }
        });
    }

    @OnClick(R.id.iv_navigate_up)
    public void onClickNavigateUp() {
        finish();
        // Todo: 전환 애니메이션 구현
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

//    @OnClick(R.id.iv_zzim)
//    public void onClickZzim(View view) {
//        mProduct.updateZzimStatue();
//        showZzimStatus();
//
//        if (mProduct.isZzimStatus()) {
//            addZzimProduct();
//        } else {
//            removeZzimProduct();
//        }
//    }

    private void showZzimStatus() {
//        mIvZzim.setSelected(mProduct.isZzimStatus());
        mLbZzim.setLiked(mProduct.isZzimStatus());
    }

    private void addZzimProduct() {
        BackendHelper.getInstance().addZzimProduct(mProduct.getId(), new ResultCallback() {
            @Override
            public void onSuccess(@Nullable Object o) {
                SnackbarUtil.showMessage(CustomerProductDetailActivity.this, mClRoot, "찜 완료", "", null);
            }

            @Override
            public void onFailure() {
                SnackbarUtil.showMessage(CustomerProductDetailActivity.this, mClRoot, "찜 실패", "", null);
                mProduct.updateZzimStatue();
                showZzimStatus();
            }
        });
    }

    private void removeZzimProduct() {
        BackendHelper.getInstance().deleteZzzimProduct(mProduct.getId(), new ResultCallback() {
            @Override
            public void onSuccess(@Nullable Object o) {
                SnackbarUtil.showMessage(CustomerProductDetailActivity.this, mClRoot, "찜 해제 완료", "", null);
            }

            @Override
            public void onFailure() {
                SnackbarUtil.showMessage(CustomerProductDetailActivity.this, mClRoot, "찜 해제 실패", "", null);
                mProduct.updateZzimStatue();
                showZzimStatus();
            }
        });
    }
}