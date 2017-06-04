package kr.co.mash_up.a9tique._old.ui.setting.sellerinformation;

import kr.co.mash_up.a9tique._old.base.BasePresenter;
import kr.co.mash_up.a9tique._old.base.BaseView;
import kr.co.mash_up.a9tique._old.data.Seller;


public interface SellerInformationContract {

    /**
     * View -> Presenter
     */
    interface Presenter extends BasePresenter {

        void onClickSellerInformationModify();

        void loadSellerInformation();

        void result(int requestCode, int resultCode);
    }

    /**
     * Presenter -> View
     */
    interface View extends BaseView<Presenter> {

        void showProgressbar(boolean active);

        void showFailureLoadingSellerInformationMessage();

        void showFailureUpdateSellerInformationMessage();

        void showSuccessfullyUpdateSellerInformationMessage();

        void showSellerInformation(Seller seller);

        boolean isActive();

        void showSellerInformationModify();
    }
}
