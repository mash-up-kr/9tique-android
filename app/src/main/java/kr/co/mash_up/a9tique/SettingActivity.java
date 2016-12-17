package kr.co.mash_up.a9tique;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
    private List<Setting> settingList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SettingAdapter sAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar app_bar = (Toolbar) findViewById(R.id.app_bar);
        app_bar.setTitle("설정");
        setSupportActionBar(app_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        sAdapter = new SettingAdapter(settingList);
        RecyclerView.LayoutManager sLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(sLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sAdapter);

        sAdapter.setOnItemClickListener(onItemClickListener);

        prepareSettingData();
    }

    SettingAdapter.OnItemClickListener onItemClickListener = new SettingAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            Context context = v.getContext();
            switch (position) {
                case 0:
                    Intent intent = new Intent(context, InquireActivity.class);
                    context.startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(context, VersionInfoActivity.class);
                    context.startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(context, AgreementActivity.class);
                    context.startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(context, LicenseActivity.class);
                    context.startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(context, SellerActivity.class);
                    context.startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(context, About9tiqueActivity.class);
                    context.startActivity(intent);
                    break;
                case 6:
                    Toast.makeText(context, "로그아웃", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void prepareSettingData() {
        Setting menu = new Setting("문의하기"); // inquire
        settingList.add(menu);

        menu = new Setting("버전정보"); // version info
        settingList.add(menu);

        menu = new Setting("이용약관"); // agreement
        settingList.add(menu);

        menu = new Setting("라이센스 정보"); // license
        settingList.add(menu);

        menu = new Setting("판매자 등록"); // seller
        settingList.add(menu);

        menu = new Setting("About 9tique"); // About 9tique
        settingList.add(menu);

        menu = new Setting("로그아웃");
        settingList.add(menu);

        sAdapter.notifyDataSetChanged();
    }
}
