package kr.co.mash_up.a9tique.ui.setting.sellerinformation;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;


public class SellerInformationActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;

    private SellerInformationContract.Presenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_seller_information;
    }

    @Override
    public void initView() {
        setupToolbar();
        SellerInformationFragment sellerInformationFragment =
                (SellerInformationFragment) getSupportFragmentManager().findFragmentById(R.id.fl_fragment_seller_info);
        if (null == sellerInformationFragment) {
            sellerInformationFragment = SellerInformationFragment.newInstance();
            initFragment(sellerInformationFragment);
        }

        // Create the presenter
        mPresenter = new SellerInformationPresenter(sellerInformationFragment);
    }

    @Override
    public void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_fragment_seller_info, fragment, SellerInformationFragment.TAG)
                .commit();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left_white);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTvToolbarTitle.setText("판매자 정보 확인");
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