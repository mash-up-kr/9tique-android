package kr.co.mash_up.a9tique.ui.setting.aboutus;

import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;

public class About9tiqueActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_toolbar_title)
    TextView mTvTitle;

    @BindView(R.id.tv_about_title)
    TextView mTvAboutTitle;

    @BindView(R.id.tv_about_content)
    TextView mTvAboutContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about9tique;
    }

    @Override
    public void initView() {
        setupToolbar();
        //Todo: 내용 수정
        //Todo: 줄바꿈 모든 디바이스에서 알맞게 동작하도록 수정하기
        mTvAboutTitle.setText("9TIQUE");
        mTvAboutContent.setText("‘9tique’는 오프라인 및 온라인 빈티지 의류 쇼핑몰 기반(의류 브랜드가 있는 빈티지의류를 칭함, 브랜드가 없는 보세 빈티지 제외.) 모바일 커머스 플랫폼입니다. \n" +
                "\n" +
                "오프라인 및 온라인 빈티지 쇼핑몰과의 협업을 통해 빈티지의류 상품 정보를 제공받고, 제공받은 빈티지의류 정보를 9tique 서비스를 통해 고객에게 제공합니다. \n" +
                "고객은 9tique를 통해 여러 쇼핑몰(온라인 및 오프라인)을 둘러보지 않고 명품 빈티지 의류를 쉽고 간편하게 구입할 수 있습니다.\n" +
                "\n" +
                "문의사항이 있으시면 아래의 연락처로 연락해주세요.\n" +
                "\n" +
                "박도준\n" +
                "모바일 앱 서비스 기획자(프로젝트 기획 / 매니저)\n" +
                "이메일 : dojun8778@gmail.com\n" +
                "연락처 : 010-5582-8778");
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
        mTvTitle.setText("About 9tique");
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
