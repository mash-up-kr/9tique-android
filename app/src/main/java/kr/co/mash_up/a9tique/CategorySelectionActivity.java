package kr.co.mash_up.a9tique;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import kr.co.mash_up.a9tique.base.ui.BaseActivity;

public class CategorySelectionActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CategorySelectionFragment categorySelectionFragment =
                (CategorySelectionFragment) getSupportFragmentManager().findFragmentByTag(CategorySelectionFragment.TAG);

        if(null == categorySelectionFragment){
            categorySelectionFragment = CategorySelectionFragment.newInstance();
            initFragment(categorySelectionFragment);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_category_selection;
    }

    @Override
    public void initView() {
        setupToolbar();
    }

    @Override
    public void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_fragment_category_selection, fragment, CategorySelectionFragment.TAG)
                .commit();
    }

    @UiThread
    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTvTitle.setText("카테고리 선택");
    }

    /**
     * 뒤로가기 네비게이션 버튼 클릭시 호출되는 콜백 메소드
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
