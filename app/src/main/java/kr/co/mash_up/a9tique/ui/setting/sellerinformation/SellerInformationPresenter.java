package kr.co.mash_up.a9tique.ui.setting.sellerinformation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import kr.co.mash_up.a9tique.data.Seller;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;


public class SellerInformationPresenter implements SellerInformationContract.Presenter {

    //Todo: add Model Layer
    private SellerInformationContract.View mView;

    public SellerInformationPresenter(/*Todo: add Model Layer */
                                      @NonNull SellerInformationContract.View view) {
        //Todo: add Model Layer
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        // Do nothing
    }

    @Override
    public void loadSellerInformation() {
        mView.showProgressbar(true);

        BackendHelper.getInstance().getSellerInfo(new ResultCallback<Seller>() {
            @Override
            public void onSuccess(@Nullable Seller seller) {
                if (!mView.isActive()) {
                    return;
                }
                mView.showSellerInformation(seller);
                mView.showProgressbar(false);
            }

            @Override
            public void onFailure() {
                if (!mView.isActive()) {
                    return;
                }
                mView.showProgressbar(false);
                mView.showFailureLoadingSellerInformationMessage();
            }
        });
    }

    @Override
    public void result(int requestCode, int resultCode) {
        switch (requestCode) {
            case SellerInformationFragment.REQUEST_CODE_SELLER_INFORMATION_MODIFY:
                if (resultCode == Activity.RESULT_OK) {
                    mView.showSuccessfullyUpdateSellerInformationMessage();
                    loadSellerInformation();
                } else {
                    mView.showFailureUpdateSellerInformationMessage();
                }
                break;
        }
    }
}
