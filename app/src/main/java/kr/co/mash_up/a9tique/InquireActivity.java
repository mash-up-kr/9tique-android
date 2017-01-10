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

public class InquireActivity extends AppCompatActivity {
    private List<Setting> settingList = new ArrayList<>();
    private SettingAdapter settingAdapter;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.recycler_view)
    RecyclerView rvInquire;

    @OnClick(R.id.ibtn_toolbar_back)
    public void click(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquire);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvToolbarTitle.setText("문의하기");

        settingAdapter = new SettingAdapter(settingList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvInquire.setLayoutManager(layoutManager);
        rvInquire.setAdapter(settingAdapter);

        prepareSettingData();
    }

    private void prepareSettingData() {
        Setting menu = new Setting("카카오톡 문의하기");
        settingList.add(menu);

        menu = new Setting("전화 문의하기");
        settingList.add(menu);

        menu = new Setting("이메일 문의하기");
        settingList.add(menu);

        settingAdapter.notifyDataSetChanged();
    }
}
