package kr.co.mash_up.a9tique;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class About9tiqueActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_about_title)
    TextView tvAboutTitle;
    @BindView(R.id.tv_about_text)
    TextView tvAboutText;
    @BindView(R.id.tv_about_address)
    TextView tvAboutAddress;

    @OnClick(R.id.ibtn_toolbar_back)
    public void click(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about9tique);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvToolbarTitle.setText("About 9tique");

        tvAboutTitle.setText("9TIQUE");

        tvAboutText.setText("9tique는 광장시장 내 수입 구제시장을\n" +
                "기반으로 한 의류 소개 플랫폼입니다.\n" +
                "200개의 수입 구제 매장과 함께하고 있으며,\n" +
                "보다 편리한 수입 구제 상가\n" +
                "이용 경험을 제공하고 있습니다.");

        tvAboutAddress.setText("000@gmail.com\n" +
        "010.000.0000\n" + "주소지");
    }
}
