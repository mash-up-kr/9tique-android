package kr.co.mash_up.a9tique;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SellerActivity extends AppCompatActivity {
    private List<Setting> settingList = new ArrayList<>();
    private RecyclerView rvSeller;
    private SellerAdapter sellerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        // 툴바 (메뉴 이름, 뒤로 가기 버튼, 등록 버튼)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_seller_identify);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // 툴바 제목: 메뉴 이름 (판매자 정보 확인)
        TextView toolbarTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText("판매자 정보 확인");

        // 뒤로 가기 버튼: 클릭 시 액티비티 종료
        ImageButton ibtnToolbarBack = (ImageButton) findViewById(R.id.ibtn_toolbar_back);
        ibtnToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 등록하기 버튼
        Button btnToolbarSignUp = (Button) findViewById(R.id.btn_toolbar_sign_up);
        btnToolbarSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // recycler view
        rvSeller = (RecyclerView) findViewById(R.id.recycler_view);

        sellerAdapter = new SellerAdapter(settingList);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvSeller.setLayoutManager(linearLayoutManager);
        rvSeller.setItemAnimator(new DefaultItemAnimator());
        rvSeller.setAdapter(sellerAdapter);

        prepareSettingData();
    }

    // 메뉴 추가
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
