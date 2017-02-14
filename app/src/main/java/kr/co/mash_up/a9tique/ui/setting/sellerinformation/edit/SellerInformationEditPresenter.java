package kr.co.mash_up.a9tique.ui.setting.sellerinformation.edit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.RequestSeller;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;


public class SellerInformationEditPresenter implements SellerInformationEditContract.Presenter {

    //Todo: add Model Layer
    private SellerInformationEditContract.View mView;

    public SellerInformationEditPresenter(/*Todo: add Model Layer */
                                          @NonNull SellerInformationEditContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        // Do nothing
    }

    @Override
    public void updateSellerInformation(RequestSeller requestSeller) {
        mView.showProgressbar(true);

        BackendHelper.getInstance().updateSellerInfo(requestSeller, new ResultCallback() {
            @Override
            public void onSuccess(@Nullable Object o) {
                if (!mView.isActive()) {
                    return;
                }
                mView.showProgressbar(false);
                mView.successfullySellerInformationUpdate();
            }

            @Override
            public void onFailure() {
                if (!mView.isActive()) {
                    return;
                }
                mView.showProgressbar(false);
                mView.failureSellerInformationUpdate();
            }
        });
    }
}
