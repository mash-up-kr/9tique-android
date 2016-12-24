package kr.co.mash_up.a9tique;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class About9tiqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about9tique);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("About 9tique");

        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView about_tv1 = (TextView)findViewById(R.id.about_tv1);
        about_tv1.setText("9TIQUE");

        TextView about_tv2 = (TextView)findViewById(R.id.about_tv2);
        about_tv2.setText("9tique는 광장시장 내 수입 구제시장을\n" +
                "기반으로 한 의류 소개 플랫폼입니다.\n" +
                "200개의 수입 구제 매장과 함께하고 있으며,\n" +
                "보다 편리한 수입 구제 상가\n" +
                "이용 경험을 제공하고 있습니다.");

        TextView about_tv3 = (TextView)findViewById(R.id.about_tv3);
        about_tv3.setText("000@gmail.com\n" +
        "010.000.0000\n" + "주소지");
    }
}
