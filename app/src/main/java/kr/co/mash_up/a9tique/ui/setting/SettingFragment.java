package kr.co.mash_up.a9tique.ui.setting;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kakao.auth.Session;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.BuildConfig;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.data.User;
import kr.co.mash_up.a9tique.ui.addeditproduct.ConfirmationDialogFragment;
import kr.co.mash_up.a9tique.ui.login.LoginActivity;
import kr.co.mash_up.a9tique.ui.setting.aboutus.About9tiqueActivity;
import kr.co.mash_up.a9tique.ui.setting.agreement.AgreementActivity;
import kr.co.mash_up.a9tique.ui.setting.inquire.InquireActivity;
import kr.co.mash_up.a9tique.ui.setting.license.LicenseActivity;
import kr.co.mash_up.a9tique.ui.setting.sellerinformation.SellerInformationActivity;
import kr.co.mash_up.a9tique.util.PreferencesUtils;
import kr.co.mash_up.a9tique.util.ProgressUtil;
import kr.co.mash_up.a9tique.util.SnackbarUtil;

public class SettingFragment extends BaseFragment implements SettingContract.View {

    public static final String TAG = SettingFragment.class.getSimpleName();

    @BindView(R.id.tv_app_version)
    TextView mTvAppVersion;

    @BindView(R.id.rl_seller_registration)
    RelativeLayout mRlSellerRegistration;

    @BindView(R.id.rl_seller_information)
    RelativeLayout mRlSellerInformation;

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
    public void showSellerRegisteration() {
        SellerRegistrationDialogFragment dlgSellerRegistratio = SellerRegistrationDialogFragment.newInstance("인증코드 입력", "발급된 인증코드를 입력해 주세요.");
        dlgSellerRegistratio.setCallback(new SellerRegistrationDialogFragment.Callback() {
            @Override
            public void onClickOk(String authenticationCode) {
                mPresenter.authenticateSeller(authenticationCode);
            }

            @Override
            public void onClickCancel() {
                // Do nothing
            }
        });
        dlgSellerRegistratio.show(getChildFragmentManager(), SellerRegistrationDialogFragment.TAG);
    }

    @OnClick(R.id.rl_seller_information)
    public void showSellerInformation() {
        Intent intent = new Intent(getActivity(), SellerInformationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_inquire)
    public void showInquire() {
        Intent intent = new Intent(getActivity(), InquireActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_agreement)
    public void showAgreement() {
        Intent intent = new Intent(getActivity(), AgreementActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_license)
    public void showLicense() {
        Intent intent = new Intent(getActivity(), LicenseActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_about_us)
    public void showAboutUs() {
        Intent intent = new Intent(getActivity(), About9tiqueActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_logout)
    public void logout() {
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
        dlgLogout.show(getChildFragmentManager(), ConfirmationDialogFragment.TAG);
    }

    private boolean checkUserLevelBySeller() {
        if (!PreferencesUtils.getString(getActivity(), Constants.PREF_ACCESS_TOKEN, "").equals("")
                && PreferencesUtils.getString(getActivity(), Constants.PREF_USER_LEVEL, "").equals("SELLER")) {
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

    @Override
    public void saveUserAccount(User user) {
        PreferencesUtils.putString(getActivity(), Constants.PREF_ACCESS_TOKEN, user.getAccessToken());
        PreferencesUtils.putString(getActivity(), Constants.PREF_USER_LEVEL, user.getUserLevel());
    }

    @Override
    public void clearUserAccount() {
        Session.getCurrentSession().close();
        PreferencesUtils.clear(getActivity());
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
        mPresenter = presenter;
    }
}
