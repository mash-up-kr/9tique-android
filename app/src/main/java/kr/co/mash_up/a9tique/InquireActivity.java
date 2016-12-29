package kr.co.mash_up.a9tique;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InquireActivity extends AppCompatActivity {
    private List<Setting> settingList = new ArrayList<>();
    private RecyclerView rvInquire;
    private SettingAdapter settingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquire);

        // 툴바 (메뉴 이름, 뒤로 가기 버튼)
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // 툴바 제목: 메뉴 이름 (문의하기)
        TextView tvToolbarTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        tvToolbarTitle.setText("문의하기");

        // 뒤로 가기 버튼: 클릭 시 액티비티 종료
        ImageButton ibtnToolbarBack = (ImageButton) findViewById(R.id.ibtn_toolbar_back);
        ibtnToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // recycler view
        rvInquire = (RecyclerView) findViewById(R.id.recycler_view);

        // setting adapter
        settingAdapter = new SettingAdapter(settingList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvInquire.setLayoutManager(layoutManager);
        rvInquire.setAdapter(settingAdapter);

        settingAdapter.setOnItemClickListener(onItemClickListener);

        prepareSettingData();
    }

    // 메뉴 추가
    private void prepareSettingData() {
        Setting menu = new Setting("카카오톡 문의하기");
        settingList.add(menu);

        menu = new Setting("전화 문의하기");
        settingList.add(menu);

        menu = new Setting("이메일 문의하기");
        settingList.add(menu);

        settingAdapter.notifyDataSetChanged();
    }

    // 각 메뉴마다 카카오톡 문의하기, 전화 문의하기, 이메일 문의하기 Toast 띄우기
    SettingAdapter.OnItemClickListener onItemClickListener = new SettingAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            Context context = v.getContext();
            switch (position) {
                case 0:
                    Toast.makeText(context, "카카오톡 문의하기", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(context, "전화 문의하기", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(context, "이메일 문의하기", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
