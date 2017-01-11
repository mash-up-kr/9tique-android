package kr.co.mash_up.a9tique.ui.productdetail;

import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.ui.products.SellerProductListActivity;

/**
 * data load
 * open add edit activity
 * product remove
 * product sold out
 */
public class SellerProductDetailActivity extends BaseActivity {

    public static final int REQUEST_CODE_DETAIL_RPODUCT = 10;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.iv_product_thumbnail)
    ImageView ivProductThumbnail;

    private Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SellerProductDetailFragment sellerProductDetailFragment =
                (SellerProductDetailFragment) getSupportFragmentManager().findFragmentByTag(SellerProductDetailFragment.TAG);

        if (null == sellerProductDetailFragment) {
            sellerProductDetailFragment = SellerProductDetailFragment.newInstance(mProduct);

            initFragment(sellerProductDetailFragment);
        }

        //Todo: Create the presenter
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_seller_product_detail;
    }

    @Override
    public void initView() {
        setupToolbar();
    }

    @Override
    public void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_fragment_seller_product_detail, fragment, SellerProductDetailFragment.TAG)
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

        mProduct = getIntent().getParcelableExtra("product");

        Glide.with(SellerProductDetailActivity.this)
                .load(Constants.END_POINT + mProduct.getProductImages().get(0).getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .fitCenter()
                .centerCrop()
                .into(ivProductThumbnail);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_seller_product_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_seller_product_modify:
                //Todo: open seller product edit activity
                Toast.makeText(this, "open seller product edit activity", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
