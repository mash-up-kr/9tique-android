package kr.co.mash_up.a9tique;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SellerActivity extends AppCompatActivity {
    private List<Setting> settingList = new ArrayList<>();
    private SellerAdapter sellerAdapter;

    @BindView(R.id.toolbar_seller_identify)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.recycler_view)
    RecyclerView rvSeller;

    @OnClick(R.id.ibtn_toolbar_back)
    public void ibtnToolbarBackClick(View view) {
        finish();
    }
    @OnClick(R.id.btn_toolbar_sign_up)
    public void btnToolbarSignUpClick(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvToolbarTitle.setText("판매자 정보 확인");

        sellerAdapter = new SellerAdapter(settingList);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvSeller.setLayoutManager(linearLayoutManager);
        rvSeller.setAdapter(sellerAdapter);

        prepareSettingData();
    }

    private void prepareSettingData() {
        Setting menu = new Setting("판매자 이름");
        settingList.add(menu);

        menu = new Setting("매장명");
        settingList.add(menu);

        menu = new Setting("매장 정보");
        settingList.add(menu);

        menu = new Setting("연락처");
        settingList.add(menu);

        sellerAdapter.notifyDataSetChanged();
    }
}
