package kr.co.mash_up.a9tique._old.ui.setting.sellerinformation.edit;

import kr.co.mash_up.a9tique._old.base.BasePresenter;
import kr.co.mash_up.a9tique._old.base.BaseView;
import kr.co.mash_up.a9tique._old.data.remote.RequestSeller;

/**
 * Created by Dong on 2017-02-15.
 */

public interface SellerInformationEditContract {

    /**
     * View -> Presenter
     */
    interface Presenter extends BasePresenter {
        void updateSellerInformation(RequestSeller requestSeller);
    }

    /**
     * Presenter -> View
     */
    interface View extends BaseView<SellerInformationEditContract.Presenter> {
        void showProgressbar(boolean active);

        void successfullySellerInformationUpdate();

        void failureSellerInformationUpdate();

        boolean isActive();
    }
}
