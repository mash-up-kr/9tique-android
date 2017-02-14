package kr.co.mash_up.a9tique.ui.setting.sellerinformation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.data.Seller;


public class SellerInformationActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_title)
    TextView mTvToolbarTitle;

    private Seller mSeller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getIntent().hasExtra(Constants.SELLER)) {
            mSeller = getIntent().getParcelableExtra(Constants.SELLER);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_seller_information;
    }

    @Override
    public void initView() {
        setupToolbar();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_fragment_seller_info);
        if (null == fragment) {
            showSellerInfo();
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left_white);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_fragment_seller_info, fragment, SellerInfoFragment.TAG)
                .commit();
    }

    /**
     * 뒤로가기 네비게이션 버튼 클릭시 호출되는 콜백 메소드
     *
     * @return 이벤트 처리 여부
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        // Todo: 전환 애니메이션 구현
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        return true;
    }

    private void showSellerInfo() {
        mTvToolbarTitle.setText("판매자 정보 확인");
        SellerInfoFragment sellerInfoFragment = SellerInfoFragment.newInstance(mSeller);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragment_seller_info, sellerInfoFragment, SellerInfoFragment.TAG)
                .commit();
    }

    public void showSellerInfoEdit() {
        mTvToolbarTitle.setText("판매자 정보 수정");
        SellerInfoEditFragment sellerInfoEditFragment = SellerInfoEditFragment.newInstance(mSeller);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragment_seller_info, sellerInfoEditFragment, SellerInfoEditFragment.TAG)
                .commit();
    }
}