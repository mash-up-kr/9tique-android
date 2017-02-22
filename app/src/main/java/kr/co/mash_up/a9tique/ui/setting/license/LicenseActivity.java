package kr.co.mash_up.a9tique.ui.setting.license;

import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;

public class LicenseActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_toolbar_title)
    TextView mTvTitle;

    @BindView(R.id.tv_license_title)
    TextView mTvLicenseTitle;

    @BindView(R.id.tv_license_content)
    TextView mTvLicenseContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_license;
    }

    @Override
    public void initView() {
        setupToolbar();

        //Todo: 내용 넣기
        mTvLicenseTitle.setText("fffff");
        mTvLicenseContent.setText("fffff");
    }

    @UiThread
    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left_white);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTvTitle.setText("라이센스 정보");
    }

    @Override
    public void initFragment(Fragment fragment) {
        // Do nothing
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
