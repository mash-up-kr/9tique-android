package kr.co.mash_up.a9tique._old.ui.setting.sellerinformation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import kr.co.mash_up.a9tique._old.data.Seller;
import kr.co.mash_up.a9tique._old.data.remote.BackendHelper;
import kr.co.mash_up.a9tique._old.data.remote.ResultCallback;
import kr.co.mash_up.a9tique._old.ui.setting.sellerinformation.edit.SellerInformationEditActivity;
import kr.co.mash_up.a9tique._old.util.CheckNonNullUtil;


public class SellerInformationPresenter implements SellerInformationContract.Presenter {

    //Todo: add Model Layer
    private SellerInformationContract.View mView;

    public SellerInformationPresenter(/*Todo: add Model Layer */
                                      @NonNull SellerInformationContract.View view) {
        //Todo: add Model Layer
        mView = CheckNonNullUtil.checkNotNull(view, " seller Information view cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        // Do nothing
    }

    @Override
    public void onClickSellerInformationModify() {
        mView.showSellerInformationModify();
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
            case SellerInformationEditActivity.REQUEST_CODE_SELLER_INFORMATION_MODIFY:
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