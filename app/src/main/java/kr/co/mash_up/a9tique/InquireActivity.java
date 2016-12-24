package kr.co.mash_up.a9tique;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
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
    private RecyclerView recyclerView;
    private SettingAdapter sAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquire);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("문의하기");

        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

    private void prepareSettingData() {
        Setting menu = new Setting("카카오톡 문의하기");
        settingList.add(menu);

        menu = new Setting("전화 문의하기");
        settingList.add(menu);

        menu = new Setting("이메일 문의하기");
        settingList.add(menu);

        sAdapter.notifyDataSetChanged();
    }


}
