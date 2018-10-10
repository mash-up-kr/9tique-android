package kr.co.mash_up.a9tique._old.ui.setting;

import kr.co.mash_up.a9tique.base.ui.BasePresenter;
import kr.co.mash_up.a9tique.base.ui.BaseView;
import kr.co.mash_up.a9tique._old.data.User;


public interface SettingContract {

    /**
     * View -> Presenter
     */
    interface Presenter extends BasePresenter {

        void onClickSellerRegisteration();

        void authenticateSeller(String authenticationCode);

        void onClickSellerInformation();

        void onClickInquire();

        void onClickAgreement();

        void onClickLicense();

        void onClickAboutUs();

        void onClickLogout();

        void logout();
    }

    /**
     * Presenter -> View
     */
    interface View extends BaseView<Presenter> {

        boolean isActive();

        void showProgressbar(boolean active);

        void redirectLoginActivity();

        void showFailureSellerRegisterMessage();

        void showSuccessfullySellerRegisterMessage();

        void saveUserAccount(User user);

        void clearUserAccount();

        void showDialogSellerRegisteration();

        void showSellerInformation();

        void showInquire();

        void showAgreement();

        void showLicense();

        void showAboutUs();

        void showDialogLogout();
    }
}