package kr.co.mash_up.a9tique.ui.zzimproducts;

import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import kr.co.mash_up.a9tique.NinetiqueApplication;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;

public class ZzimProductsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    private ZzimProductsContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ZzimProductsFragment zzimProductsFragment
                = (ZzimProductsFragment) getSupportFragmentManager().findFragmentByTag(ZzimProductsFragment.TAG);

        if(null == zzimProductsFragment){
            zzimProductsFragment = ZzimProductsFragment.newInstance();

            initFragment(zzimProductsFragment);
        }

        // Create the presenter
        mPresenter = new ZzimProductsPresenter(zzimProductsFragment);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zzim_products;
    }

    @Override
    public void initView() {
        setupToolbar();
        setupFont();
    }

    @Override
    public void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_fragment_zzim_products, fragment, ZzimProductsFragment.TAG)
                .commit();
    }

    @UiThread
    private void setupFont(){
        NinetiqueApplication.getNinetiqueApplication(ZzimProductsActivity.this)
                .setNotoSansMedium(mTvTitle);
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
        mTvTitle.setText("내가 찜한 상품");
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
