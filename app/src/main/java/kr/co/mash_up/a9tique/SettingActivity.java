package kr.co.mash_up.a9tique;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
    private List<Setting> settingList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SettingAdapter sAdapter;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        activity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("설정");

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
            final Context context = v.getContext();
            switch (position) {
                case 0:
                    Intent intent = new Intent(context, InquireActivity.class);
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
                    final Dialog dlg_seller = new Dialog(context);
                    dlg_seller.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dlg_seller.setContentView(R.layout.custom_dialog);

                    TextView seller_title = (TextView) dlg_seller.findViewById(R.id.dlg_title);
                    seller_title.setText("인증코드 입력");

                    EditText seller_edittxt = (EditText) dlg_seller.findViewById(R.id.dlg_edittxt);
                    seller_edittxt.setHint("발급된 인증코드를 입력해 주세요.");

                    Button seller_cancel = (Button) dlg_seller.findViewById(R.id.dlg_cancel);
                    seller_cancel.setText("CANCEL");
                    seller_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dlg_seller.dismiss();
                        }
                    });
                    Button seller_agree = (Button) dlg_seller.findViewById(R.id.dlg_agree);
                    seller_agree.setText("AGREE");
                    seller_agree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent seller = new Intent(context, SellerActivity.class);
                            context.startActivity(seller);
                            dlg_seller.dismiss();
                        }
                    });
                    dlg_seller.show();
                    break;
                case 5:
                    intent = new Intent(context, About9tiqueActivity.class);
                    context.startActivity(intent);
                    break;
                case 6:
                    final Dialog dlg_logout = new Dialog(context);
                    dlg_logout.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dlg_logout.setContentView(R.layout.custom_dialog2);

                    TextView logout_title = (TextView) dlg_logout.findViewById(R.id.dlg_title);
                    logout_title.setText("로그아웃");

                    TextView logout_message = (TextView) dlg_logout.findViewById(R.id.dlg_message);
                    logout_message.setText("로그아웃하시겠습니까?");

                    Button logout_cancel = (Button) dlg_logout.findViewById(R.id.dlg_cancel);
                    logout_cancel.setText("CANCEL");
                    logout_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dlg_logout.dismiss();
                        }
                    });
                    Button logout_agree = (Button) dlg_logout.findViewById(R.id.dlg_agree);
                    logout_agree.setText("AGREE");
                    logout_agree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent fblogin = new Intent(context, FacebookLogin.class);
                            context.startActivity(fblogin);
                        }
                    });

                    dlg_logout.show();
                    break;
            }
        }
    };

    private void prepareSettingData() {
        Setting menu = new Setting("문의하기", R.drawable.icn_next); // inquire
        settingList.add(menu);

        menu = new Setting("버전정보", "V 0.0.1"); // version info
        settingList.add(menu);

        menu = new Setting("이용약관", R.drawable.icn_next); // agreement
        settingList.add(menu);

        menu = new Setting("라이센스 정보", R.drawable.icn_next); // license
        settingList.add(menu);

        menu = new Setting("판매자 등록", R.drawable.icn_next);
        settingList.add(menu);

        menu = new Setting("About 9tique", R.drawable.icn_next); // About 9tique
        settingList.add(menu);

        menu = new Setting("로그아웃");
        settingList.add(menu);

        sAdapter.notifyDataSetChanged();
    }
}
