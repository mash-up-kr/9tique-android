package kr.co.mash_up.a9tique;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private RecyclerView rvSetting;
    private SettingAdapter settingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // // 툴바 (메뉴 이름, 뒤로 가기 버튼)
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // 툴바 제목 -> 메뉴 이름 (설정)
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("설정");

        // 뒤로 가기 버튼: 클릭 시 액티비티 종료
        ImageButton btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // recycler view
        rvSetting = (RecyclerView) findViewById(R.id.recycler_view);

        settingAdapter = new SettingAdapter(settingList);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvSetting.setLayoutManager(linearLayoutManager);
        rvSetting.setAdapter(settingAdapter);

        settingAdapter.setOnItemClickListener(onItemClickListener);
        prepareSettingData();
    }

    SettingAdapter.OnItemClickListener onItemClickListener = new SettingAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            final Context context = v.getContext();
            switch (position) {
                case 0:
                    // 0. 문의하기 - InquireActivity로 이동
                    Intent intent = new Intent(context, InquireActivity.class);
                    context.startActivity(intent);
                    break;
                case 2:
                    // 2. 이용약관 - AgreementActivity로 이동
                    intent = new Intent(context, AgreementActivity.class);
                    context.startActivity(intent);
                    break;
                case 3:
                    // 3. 라이센스 정보 - LicenseActivity로 이동
                    intent = new Intent(context, LicenseActivity.class);
                    context.startActivity(intent);
                    break;
                case 4:
                    // 4. 판매자 등록 - 다이얼로그 출력
                    final Dialog dlgSeller = new Dialog(context);
                    dlgSeller.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dlgSeller.setContentView(R.layout.custom_dialog);

                    // 다이얼로그 타이틀 설정
                    TextView tvSellerTitle = (TextView) dlgSeller.findViewById(R.id.dlg_title);
                    tvSellerTitle.setText("인증코드 입력");

                    // 다이얼로그 힌트 설정
                    EditText etSeller = (EditText) dlgSeller.findViewById(R.id.dlg_edittxt);
                    etSeller.setHint("발급된 인증코드를 입력해 주세요.");

                    // CANCEL 클릭 시 다이얼로그 종료
                    Button btnSellerCancel = (Button) dlgSeller.findViewById(R.id.dlg_cancel);
                    btnSellerCancel.setText("CANCEL");
                    btnSellerCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dlgSeller.dismiss();
                        }
                    });

                    // AGREE 클릭 시 sellerActivity로 이동
                    Button btnSellerAgree = (Button) dlgSeller.findViewById(R.id.dlg_agree);
                    btnSellerAgree.setText("AGREE");
                    btnSellerAgree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent seller = new Intent(context, SellerActivity.class);
                            context.startActivity(seller);
                            dlgSeller.dismiss();
                        }
                    });

                    dlgSeller.show();
                    break;
                case 5:
                    // 5. About 9tique - About9tiqueActivity로 이동
                    intent = new Intent(context, About9tiqueActivity.class);
                    context.startActivity(intent);
                    break;
                case 6:
                    // 6. 로그아웃 - 다이얼로그 출력
                    final Dialog dlgLogout = new Dialog(context);
                    dlgLogout.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dlgLogout.setContentView(R.layout.custom_dialog2);

                    // 다이얼로그 타이틀 설정
                    TextView tvLogoutTitle = (TextView) dlgLogout.findViewById(R.id.dlg_title);
                    tvLogoutTitle.setText("로그아웃");

                    // 다이얼로그 메시지 설정
                    TextView tvLogoutMessage = (TextView) dlgLogout.findViewById(R.id.dlg_message);
                    tvLogoutMessage.setText("로그아웃하시겠습니까?");

                    // CANCEL 버튼 클릭 시 다이얼로그 종료
                    Button btnLogoutCancel = (Button) dlgLogout.findViewById(R.id.dlg_cancel);
                    btnLogoutCancel.setText("CANCEL");
                    btnLogoutCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dlgLogout.dismiss();
                        }
                    });
                    // AGREE 버튼 클릭 시 FacebookLogin으로 이동
                    Button btnLogoutAgree = (Button) dlgLogout.findViewById(R.id.dlg_agree);
                    btnLogoutAgree.setText("AGREE");
                    btnLogoutAgree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent fbLogin = new Intent(context, FacebookLogin.class);
                            context.startActivity(fbLogin);
                        }
                    });

                    dlgLogout.show();
                    break;
            }
        }
    };

    // 메뉴 추가
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

        settingAdapter.notifyDataSetChanged();
    }
}
