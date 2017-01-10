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

public class SettingActivity extends AppCompatActivity {
    private List<Setting> settingList = new ArrayList<>();
    private SettingAdapter settingAdapter;

    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.recycler_view)
    RecyclerView rvSetting;

    @OnClick(R.id.ibtn_toolbar_back)
    public void ibtnToolbarBackClick(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvToolbarTitle.setText("설정");

        settingAdapter = new SettingAdapter(settingList);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvSetting.setLayoutManager(linearLayoutManager);
        rvSetting.setAdapter(settingAdapter);

        prepareSettingData();
    }


    private void prepareSettingData() {
        Setting menu = new Setting("문의하기", R.drawable.icn_next); // inquire
        settingList.add(menu);

        menu = new Setting("버전정보", "V 0.0.1"); // version info
        settingList.add(menu);

        menu = new Setting("이용약관", R.drawable.icn_next); // agreement
        settingList.add(menu);

        menu = new Setting("라이센스 정보", R.drawable.icn_next); // license
        settingList.add(menu);

        menu = new Setting("판매자 등록", R.drawable.icn_next); // seller
        settingList.add(menu);

        menu = new Setting("About 9tique", R.drawable.icn_next); // About 9tique
        settingList.add(menu);

        menu = new Setting("로그아웃");
        settingList.add(menu);

        settingAdapter.notifyDataSetChanged();
    }
}