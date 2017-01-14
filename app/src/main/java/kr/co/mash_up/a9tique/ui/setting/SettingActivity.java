package kr.co.mash_up.a9tique.ui.setting;

import android.content.Intent;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.kakao.auth.Session;

import java.util.ArrayList;

import butterknife.BindDimen;
import butterknife.BindView;
import kr.co.mash_up.a9tique.About9tiqueActivity;
import kr.co.mash_up.a9tique.AgreementActivity;
import kr.co.mash_up.a9tique.BuildConfig;
import kr.co.mash_up.a9tique.InquireActivity;
import kr.co.mash_up.a9tique.LicenseActivity;
import kr.co.mash_up.a9tique.LoginActivity;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.SellerRegistrationDialogFragment;
import kr.co.mash_up.a9tique.base.ui.BaseActivity;
import kr.co.mash_up.a9tique.ui.addeditproduct.ConfirmationDialogFragment;
import kr.co.mash_up.a9tique.ui.addeditproduct.OrientationSpacingItemDecoration;
import kr.co.mash_up.a9tique.util.PreferencesUtils;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @BindView(R.id.rv_setting)
    RecyclerView mRvSetting;

    @BindDimen(R.dimen.setting_list_item_margin)
    int itemSpacingSize;

    private MenuListAdapter mMenuListAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        setupToolbar();
        setupList();
    }

    @Override
    public void initFragment(Fragment fragment) {
        // Do nothing
    }

    @UiThread
    private void setupList() {
        mMenuListAdapter = new MenuListAdapter(SettingActivity.this);
        mMenuListAdapter.setOnItemClickListener((setting, position) -> {
            switch (position) {
                case 0: {
                    Intent intent = new Intent(SettingActivity.this, InquireActivity.class);
                    startActivity(intent);
                }
                break;
                case 2: {
                    Intent intent = new Intent(SettingActivity.this, AgreementActivity.class);
                    startActivity(intent);
                }
                break;
                case 3: {
                    Intent intent = new Intent(SettingActivity.this, LicenseActivity.class);
                    startActivity(intent);
                }
                break;
                case 4: {
                    SellerRegistrationDialogFragment dlgSellerRegistratio = SellerRegistrationDialogFragment.newInstance("인증코드 입력","발급된 인증코드를 입력해 주세요.");
                    dlgSellerRegistratio.setCallback(new SellerRegistrationDialogFragment.Callback() {
                        @Override
                        public void onClickOk() {
                            //Todo: seller registration api call
                            // success - set intent seller data and show seller activity
                            // fali - show error message - ???
                            Intent intent = new Intent(SettingActivity.this, SellerRegistrationActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onClickCancel() {
                            // Do nothing
                        }
                    });
                    dlgSellerRegistratio.show(getSupportFragmentManager(), SellerRegistrationDialogFragment.TAG);
                }
                break;
                case 5: {
                    Intent intent = new Intent(SettingActivity.this, About9tiqueActivity.class);
                    startActivity(intent);
                }
                break;
                case 6: {
                    ConfirmationDialogFragment dlgLogout = ConfirmationDialogFragment.newInstance("로그아웃", "로그아웃하시겠습니까?");
                    dlgLogout.setCallback(new ConfirmationDialogFragment.Callback() {
                        @Override
                        public void onClickOk() {
                            Session.getCurrentSession().close();
                            PreferencesUtils.clear(SettingActivity.this);
                            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                        @Override
                        public void onClickCancel() {
                            // Do nothing
                        }
                    });
                    dlgLogout.show(getSupportFragmentManager(), ConfirmationDialogFragment.TAG);
                }
                break;
            }
        });
        mRvSetting.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
        mRvSetting.addItemDecoration(new OrientationSpacingItemDecoration(itemSpacingSize, OrientationSpacingItemDecoration.Orientation.BOTTOM));
        mRvSetting.setAdapter(mMenuListAdapter);
        prepareMenuData();
    }

    private void prepareMenuData() {
        ArrayList<Menu> menus = new ArrayList<>(7);
        menus.add(new Menu("문의하기", MenuListAdapter.VIEW_TYPE_WITH_IMAGE));
        menus.add(new Menu("버전정보", "V " + BuildConfig.VERSION_NAME, MenuListAdapter.VIEW_TYPE_WITH_TEXT));
        menus.add(new Menu("이용약관", MenuListAdapter.VIEW_TYPE_WITH_IMAGE));
        menus.add(new Menu("라이센스 정보", MenuListAdapter.VIEW_TYPE_WITH_IMAGE));
        menus.add(new Menu("판매자 등록", MenuListAdapter.VIEW_TYPE_WITH_IMAGE));
        menus.add(new Menu("About 9tique", MenuListAdapter.VIEW_TYPE_WITH_IMAGE));
        menus.add(new Menu("로그아웃", MenuListAdapter.VIEW_TYPE_NORMAL));
        mMenuListAdapter.setData(menus);
    }

    @UiThread
    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left_white);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTvTitle.setText("설정");
    }
}