package kr.co.mash_up.a9tique._old.ui.setting;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.BuildConfig;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique._old.base.ui.BaseFragment;
import kr.co.mash_up.a9tique._old.common.AccountManager;
import kr.co.mash_up.a9tique._old.common.eventbus.EventNetworkStatus;
import kr.co.mash_up.a9tique._old.data.User;
import kr.co.mash_up.a9tique._old.ui.ConfirmationDialogFragment;
import kr.co.mash_up.a9tique._old.ui.login.LoginActivity;
import kr.co.mash_up.a9tique._old.ui.setting.aboutus.About9tiqueActivity;
import kr.co.mash_up.a9tique._old.ui.setting.agreement.AgreementActivity;
import kr.co.mash_up.a9tique._old.ui.setting.inquire.InquireActivity;
import kr.co.mash_up.a9tique._old.ui.setting.license.LicenseActivity;
import kr.co.mash_up.a9tique._old.ui.setting.sellerinformation.SellerInformationActivity;
import kr.co.mash_up.a9tique._old.util.CheckNonNullUtil;
import kr.co.mash_up.a9tique._old.util.PreferencesUtils;
import kr.co.mash_up.a9tique._old.util.ProgressUtil;
import kr.co.mash_up.a9tique._old.util.SnackbarUtil;


public class SettingFragment extends BaseFragment implements SettingContract.View {

    public static final String TAG = SettingFragment.class.getSimpleName();

    @BindView(R.id.tv_app_version)
    TextView mTvAppVersion;

    @BindView(R.id.rl_seller_registration)
    RelativeLayout mRlSellerRegistration;

    @BindView(R.id.rl_seller_information)
    RelativeLayout mRlSellerInformation;

    private AccountManager mAccountManager;
    private SettingContract.Presenter mPresenter;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initView(View rootView) {
        mTvAppVersion.setText(String.format("V %s", BuildConfig.VERSION_NAME));

        if (checkUserLevelBySeller()) {
            mRlSellerRegistration.setVisibility(View.GONE);
            mRlSellerInformation.setVisibility(View.VISIBLE);
        } else {
            mRlSellerRegistration.setVisibility(View.VISIBLE);
            mRlSellerInformation.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.rl_seller_registration)
    public void onClickSellerRegisteration() {
        mPresenter.onClickSellerRegisteration();
    }

    @Override
    public void showDialogSellerRegisteration() {
        SellerRegistrationDialogFragment dlgSellerRegistration = SellerRegistrationDialogFragment.newInstance("인증코드 입력", "발급된 인증코드를 입력해 주세요.");
        dlgSellerRegistration.setCallback(new SellerRegistrationDialogFragment.Callback() {
            @Override
            public void onClickOk(String authenticationCode) {
                mPresenter.authenticateSeller(authenticationCode);
            }

            @Override
            public void onClickCancel() {
                // Do nothing
            }
        });
        dlgSellerRegistration.setTargetFragment(SettingFragment.this, 0);
        dlgSellerRegistration.show(getChildFragmentManager(), SellerRegistrationDialogFragment.TAG);
    }

    @OnClick(R.id.rl_seller_information)
    public void onClickSellerInformation() {
        mPresenter.onClickSellerInformation();
    }

    @Override
    public void showSellerInformation() {
        Intent intent = new Intent(getActivity(), SellerInformationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_inquire)
    public void onClickInquire() {
        mPresenter.onClickInquire();
    }

    @Override
    public void showInquire() {
        Intent intent = new Intent(getActivity(), InquireActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_agreement)
    public void onClickAgreement() {
        mPresenter.onClickAgreement();
    }

    @Override
    public void showAgreement() {
        Intent intent = new Intent(getActivity(), AgreementActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_license)
    public void onClickLicense() {
        mPresenter.onClickLicense();
    }

    @Override
    public void showLicense() {
        Intent intent = new Intent(getActivity(), LicenseActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_about_us)
    public void onClickAboutUs() {
        mPresenter.onClickAboutUs();
    }

    @Override
    public void showAboutUs() {
        Intent intent = new Intent(getActivity(), About9tiqueActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_logout)
    public void onClickLogout() {
        mPresenter.onClickLogout();
    }

    @Override
    public void showDialogLogout() {
        ConfirmationDialogFragment dlgLogout = ConfirmationDialogFragment.newInstance("로그아웃", "로그아웃하시겠습니까?");
        dlgLogout.setCallback(new ConfirmationDialogFragment.Callback() {
            @Override
            public void onClickOk() {
                mPresenter.logout();
            }

            @Override
            public void onClickCancel() {
                // Do nothing
            }
        });
        dlgLogout.setTargetFragment(SettingFragment.this, 0);
        dlgLogout.show(getChildFragmentManager(), ConfirmationDialogFragment.TAG);
    }

    private boolean checkUserLevelBySeller() {
        mAccountManager = AccountManager.getInstance();
        if (mAccountManager.getLevel().equals(User.Level.SELLER)) {
            return true;
        }
        return false;
    }

    @Override
    public void redirectLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showFailureSellerRegisterMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "판매자 등록 실패", "", null);
    }

    @Override
    public void showSuccessfullySellerRegisterMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "판매자 등록 성공", "", null);
    }

    /**
     * 판매자 인증후 access token, user level 저장
     */
    @Override
    public void saveUserAccount(User user) {
        mAccountManager.updateAccountInformation(getActivity(), user);
    }

    @Override
    public void clearUserAccount() {
//        Session.getCurrentSession().close();
        LoginManager.getInstance().logOut();
        PreferencesUtils.clear(getActivity());
        mAccountManager.clearAccountInformation();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showProgressbar(boolean active) {
        if (active) {
            ProgressUtil.showProgressDialog(getActivity());
        } else {
            ProgressUtil.hideProgressDialog();
        }
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        mPresenter = CheckNonNullUtil.checkNotNull(presenter);
    }

    @Override
    protected void handleEventFromBus(Object event) {
        if(event instanceof EventNetworkStatus){
            SnackbarUtil.showMessage(getActivity(), getView(), "네트워크 상태가 불안정합니다", "" , null);
        }
    }
}
