package kr.co.mash_up.a9tique.ui.setting;

import android.support.annotation.NonNull;

import kr.co.mash_up.a9tique.data.User;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;


public class SettingPresenter implements SettingContract.Presenter {

    //Todo: add Model Layer
    private SettingContract.View mView;  // View Layer

    public SettingPresenter( /*Todo: add Model Layer */
                             @NonNull SettingContract.View view) {
        //Todo: add Model Layer
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void authenticateSeller(String authenticationCode) {
        mView.showProgressbar(true);

        BackendHelper.getInstance().registerSeller(authenticationCode, new ResultCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if(!mView.isActive()){
                    return;
                }

                mView.saveUserAccount(user);
                mView.showProgressbar(false);
                mView.showSuccessfullySellerRegisterMessage();
                mView.redirectLoginActivity();
            }

            @Override
            public void onFailure() {
                if(!mView.isActive()){
                    return;
                }

                mView.showProgressbar(false);
                mView.showFailureSellerRegisterMessage();
            }
        });
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
