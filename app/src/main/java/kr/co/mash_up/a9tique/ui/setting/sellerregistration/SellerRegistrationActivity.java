package kr.co.mash_up.a9tique.ui.setting.sellerregistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;

public class SellerRegistrationActivity extends BaseActivity {

    @BindView(R.id.toolbar_seller_registration)
    Toolbar mToolbarSellerRegistration;

    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;

    @BindView(R.id.et_seller_name)
    EditText mEtSellerName;

    @BindView(R.id.et_shop_name)
    EditText mEtShopName;

    @BindView(R.id.et_shop_info)
    EditText mEtShopInfo;

    @BindView(R.id.et_seller_phone)
    EditText mEtSellerPhone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_seller_registration;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent receiveIntent = getIntent();
    }

    @Override
    public void initView() {
        setupToolbar();
        //Todo: EditText data set
//        mEtSellerName.setText();
//        mEtShopName.setText();
//        mEtShopInfo.setText();
//        mEtSellerPhone.setText();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbarSellerRegistration);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left_white);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTvToolbarTitle.setText("판매자 정보 확인");
    }

    @Override
    public void initFragment(Fragment fragment) {
        // Do nothing
    }

    @OnClick(R.id.btn_toolbar_registration)
    public void btnToolbarSignUpClick(View view) {
        //Todo: seller registration api call
        //Todo: seller token save
        finish();
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
}
