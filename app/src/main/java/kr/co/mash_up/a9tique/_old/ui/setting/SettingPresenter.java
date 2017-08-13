package kr.co.mash_up.a9tique._old.ui.setting;

import android.support.annotation.NonNull;

import kr.co.mash_up.a9tique._old.data.User;
import kr.co.mash_up.a9tique.data.source.remote.BackendHelper;
import kr.co.mash_up.a9tique._old.data.remote.ResultCallback;
import kr.co.mash_up.a9tique._old.util.CheckNonNullUtil;


public class SettingPresenter implements SettingContract.Presenter {

    //Todo: add Model Layer
    private SettingContract.View mView;  // View Layer

    public SettingPresenter( /*Todo: add Model Layer */
                             @NonNull SettingContract.View view) {
        //Todo: add Model Layer
        mView = CheckNonNullUtil.checkNotNull(view, " setting view cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void onClickSellerRegisteration() {
        mView.showDialogSellerRegisteration();
    }

    @Override
    public void authenticateSeller(String authenticationCode) {
        mView.showProgressbar(true);

        BackendHelper.getInstance().registerSeller(authenticationCode, new ResultCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if (!mView.isActive()) {
                    return;
                }

                mView.saveUserAccount(user);
                mView.showProgressbar(false);
                mView.showSuccessfullySellerRegisterMessage();
                mView.redirectLoginActivity();
            }

            @Override
            public void onFailure() {
                if (!mView.isActive()) {
                    return;
                }

                mView.showProgressbar(false);
                mView.showFailureSellerRegisterMessage();
            }
        });
    }

    @Override
    public void onClickSellerInformation() {
        mView.showSellerInformation();
    }

    @Override
    public void onClickInquire() {
        mView.showInquire();
    }

    @Override
    public void onClickAgreement() {
        mView.showAgreement();
    }

    @Override
    public void onClickLicense() {
        mView.showLicense();
    }

    @Override
    public void onClickAboutUs() {
        mView.showAboutUs();
    }

    @Override
    public void onClickLogout() {
        mView.showDialogLogout();
    }

    @Override
    public void logout() {
        mView.clearUserAccount();
        mView.redirectLoginActivity();
    }

    @Override
    public void start() {
        // Do nothing
    }
}
