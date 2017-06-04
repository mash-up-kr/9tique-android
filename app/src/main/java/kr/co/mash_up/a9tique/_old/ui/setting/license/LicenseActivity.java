package kr.co.mash_up.a9tique._old.ui.setting.license;

import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique._old.base.ui.BaseActivity;
import kr.co.mash_up.a9tique._old.common.eventbus.EventNetworkStatus;
import kr.co.mash_up.a9tique._old.common.eventbus.RxEventBus;
import kr.co.mash_up.a9tique._old.util.SnackbarUtil;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LicenseActivity extends BaseActivity {

    @BindView(R.id.cl_root)
    CoordinatorLayout mClRoot;

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

    // Todo: 프래그먼트를 만들어서 아래코드를 지우자
    private Subscription mBusSubscription;

    @Override
    public void onStart() {
        super.onStart();
        unsubscribeBus();
        mBusSubscription = RxEventBus.getInstance().getBusObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                            if (o == null) {
                                return;
                            }
                            handleEventFromBus(o);
                        },
                        this::handleError,
                        this::handleCompleted);
    }

    @Override
    public void onStop() {
        unsubscribeBus();
        super.onStop();
    }

    private void unsubscribeBus() {
        if (mBusSubscription != null && !mBusSubscription.isUnsubscribed()) {
            mBusSubscription.unsubscribe();
        }
    }

    protected void handleEventFromBus(Object event) {
        if(event instanceof EventNetworkStatus){
            SnackbarUtil.showMessage(LicenseActivity.this, mClRoot, "네트워크 상태가 불안정합니다", "" , null);
        }
    }

    protected void handleError(Throwable t) {
    }

    protected void handleCompleted() {
    }
}
