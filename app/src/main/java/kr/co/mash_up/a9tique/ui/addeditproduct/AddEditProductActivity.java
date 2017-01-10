package kr.co.mash_up.a9tique.ui.addeditproduct;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;

public class AddEditProductActivity extends BaseActivity {

    public static final int REQUEST_CODE_ADD_EDIT_RPODUCT = 10;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Integer productId = null;

        AddEditProductFragment addEditProductFragment =
                (AddEditProductFragment) getSupportFragmentManager().findFragmentByTag(AddEditProductFragment.TAG);

        if(null == addEditProductFragment){
            addEditProductFragment = AddEditProductFragment.newInstance();

            if(getIntent().hasExtra(AddEditProductFragment.PARAM_PRODUCT_ID)){
                // 상품정보 수정
                productId = getIntent().getIntExtra(AddEditProductFragment.PARAM_PRODUCT_ID, 0);
                Bundle bundle = new Bundle();
                bundle.putInt(AddEditProductFragment.PARAM_PRODUCT_ID, productId);
                addEditProductFragment.setArguments(bundle);
            }else{
                // 상품정보 등록
            }
            initFragment(addEditProductFragment);
        }

        //Todo: Create the presenter
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_edit_product;
    }

    @Override
    public void initView() {
        setupToolbar();
    }

    @Override
    public void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_fragment_add_edit_product, fragment, AddEditProductFragment.TAG)
                .commit();
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

        mTvTitle.setText("상품 판매 등록");
        //Todo: toolbar title toggle, mTvTitle.setText("상품 판매 수정");
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
