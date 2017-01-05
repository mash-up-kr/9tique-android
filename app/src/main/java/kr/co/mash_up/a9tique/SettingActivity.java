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

        settingAdapter.setOnItemClickListener(onItemClickListener);
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
                    final Dialog dlgSeller = new Dialog(context);
                    dlgSeller.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dlgSeller.setContentView(R.layout.seller_dialog);

                    TextView tvDlgSellerTitle = (TextView) dlgSeller.findViewById(R.id.dlg_title);
                    tvDlgSellerTitle.setText("인증코드 입력");

                    EditText etDlgSeller = (EditText) dlgSeller.findViewById(R.id.dlg_edittxt);
                    etDlgSeller.setHint("발급된 인증코드를 입력해 주세요.");

                    Button btnDlgSellerCancel = (Button) dlgSeller.findViewById(R.id.dlg_cancel);
                    btnDlgSellerCancel.setText("CANCEL");
                    btnDlgSellerCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dlgSeller.dismiss();
                        }
                    });

                    Button btnDlgSellerAgree = (Button) dlgSeller.findViewById(R.id.dlg_agree);
                    btnDlgSellerAgree.setText("AGREE");
                    btnDlgSellerAgree.setOnClickListener(new View.OnClickListener() {
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
                    intent = new Intent(context, About9tiqueActivity.class);
                    context.startActivity(intent);
                    break;
                case 6:
                    final Dialog dlgLogout = new Dialog(context);
                    dlgLogout.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dlgLogout.setContentView(R.layout.logout_dialog);

                    TextView tvDlgLogoutTitle = (TextView) dlgLogout.findViewById(R.id.dlg_title);
                    tvDlgLogoutTitle.setText("로그아웃");

                    TextView tvDlgLogoutMessage = (TextView) dlgLogout.findViewById(R.id.dlg_message);
                    tvDlgLogoutMessage.setText("로그아웃하시겠습니까?");

                    Button btnDlgLogoutCancel = (Button) dlgLogout.findViewById(R.id.dlg_cancel);
                    btnDlgLogoutCancel.setText("CANCEL");
                    btnDlgLogoutCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dlgLogout.dismiss();
                        }
                    });
                    Button btnDlgLogoutAgree = (Button) dlgLogout.findViewById(R.id.dlg_agree);
                    btnDlgLogoutAgree.setText("AGREE");
                    btnDlgLogoutAgree.setOnClickListener(new View.OnClickListener() {
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