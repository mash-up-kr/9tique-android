package kr.co.mash_up.a9tique.ui.setting.sellerinformation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.common.eventbus.EventNetworkStatus;
import kr.co.mash_up.a9tique.data.Seller;
import kr.co.mash_up.a9tique.ui.setting.sellerinformation.edit.SellerInformationEditActivity;
import kr.co.mash_up.a9tique.util.CheckNonNullUtil;
import kr.co.mash_up.a9tique.util.ProgressUtil;
import kr.co.mash_up.a9tique.util.SnackbarUtil;


public class SellerInformationFragment extends BaseFragment implements SellerInformationContract.View {

    public static final String TAG = SellerInformationFragment.class.getSimpleName();

    @BindView(R.id.tv_seller_name)
    TextView mTvSellerName;

    @BindView(R.id.tv_shop_name)
    TextView mTvShopName;

    @BindView(R.id.tv_shop_info)
    TextView mTvShopInfo;

    @BindView(R.id.tv_seller_phone)
    TextView mTvSellerPhone;

    private Seller mSeller;
    private SellerInformationContract.Presenter mPresenter;

    public SellerInformationFragment() {
        // Required empty public constructor
    }

    public static SellerInformationFragment newInstance() {
        SellerInformationFragment fragment = new SellerInformationFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mSeller = getArguments().getParcelable(Constants.SELLER);
//        }
        setHasOptionsMenu(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_seller_information;
    }

    @Override
    public void initView(View rootView) {
        mPresenter.loadSellerInformation();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_seller_info, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_seller_information_modify:
                mPresenter.onClickSellerInformationModify();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void showSellerInformationModify() {
        Intent intent = new Intent(getActivity(), SellerInformationEditActivity.class);
        intent.putExtra(Constants.SELLER, mSeller);
        startActivityForResult(intent, SellerInformationEditActivity.REQUEST_CODE_SELLER_INFORMATION_MODIFY);
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
    public void showFailureLoadingSellerInformationMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "판매자 정보 로딩 실패",
                "Retry", view -> mPresenter.loadSellerInformation());
    }

    @Override
    public void showFailureUpdateSellerInformationMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "판매자 정보 수정 실패",
                "Retry", view -> showSellerInformationModify());
    }

    @Override
    public void showSuccessfullyUpdateSellerInformationMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "판매자 정보 수정 성공", "", null);
    }

    @Override
    public void showSellerInformation(Seller seller) {
        mSeller = seller;
        mTvSellerName.setText(mSeller.getUser().getName());
        mTvShopName.setText(mSeller.getShop().getName());
        mTvShopInfo.setText(mSeller.getShop().getInfo());
        mTvSellerPhone.setText(mSeller.getShop().getPhone());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(SellerInformationContract.Presenter presenter) {
        mPresenter = CheckNonNullUtil.checkNotNull(presenter);
    }

    @Override
    protected void handleEventFromBus(Object event) {
        if(event instanceof EventNetworkStatus){
            SnackbarUtil.showMessage(getActivity(), getView(), "네트워크 상태가 불안정합니다", "" , null);
        }
    }
}
