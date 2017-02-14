package kr.co.mash_up.a9tique.ui.setting.sellerinformation.edit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.data.Seller;
import kr.co.mash_up.a9tique.data.remote.RequestSeller;
import kr.co.mash_up.a9tique.util.KeyboardUtils;
import kr.co.mash_up.a9tique.util.ProgressUtil;
import kr.co.mash_up.a9tique.util.SnackbarUtil;


public class SellerInformationEditFragment extends BaseFragment implements SellerInformationEditContract.View {

    public static final String TAG = SellerInformationEditFragment.class.getSimpleName();

    @BindView(R.id.et_seller_name)
    EditText mEtSellerName;

    @BindView(R.id.et_shop_name)
    EditText mEtShopName;

    @BindView(R.id.et_shop_info)
    EditText mEtShopInfo;

    @BindView(R.id.et_shop_phone)
    EditText mEtShopPhone;

    private Seller mSeller;
    private SellerInformationEditContract.Presenter mPresenter;

    public SellerInformationEditFragment() {
        // Required empty public constructor
    }

    public static SellerInformationEditFragment newInstance(Seller seller) {
        SellerInformationEditFragment fragment = new SellerInformationEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.SELLER, seller);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSeller = getArguments().getParcelable(Constants.SELLER);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_seller_information_edit;
    }

    @Override
    public void initView(View rootView) {
        mEtSellerName.setText(mSeller.getUser().getName());
        mEtShopName.setText(mSeller.getShop().getName());
        mEtShopInfo.setText(mSeller.getShop().getInfo());
        mEtShopPhone.setText(mSeller.getShop().getPhone());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_seller_info_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_complete:
                KeyboardUtils.hideKeyboard(getActivity(), getView());
                if (mEtSellerName.getText().toString().length() == 0) {
                    SnackbarUtil.showMessage(getActivity(), getView(), getString(R.string.seller_info_seller_name_hint), "", null);
                    return true;
                }
                if (mEtShopName.getText().toString().length() == 0) {
                    SnackbarUtil.showMessage(getActivity(), getView(), getString(R.string.seller_info_shop_name_hint), "", null);
                    return true;
                }
                if (mEtShopInfo.getText().toString().length() == 0) {
                    SnackbarUtil.showMessage(getActivity(), getView(), getString(R.string.seller_info_shop_info_hint), "", null);
                    return true;
                }
                if (mEtShopPhone.getText().toString().length() == 0) {
                    SnackbarUtil.showMessage(getActivity(), getView(), getString(R.string.seller_info_shop_phone_hint), "", null);
                    return true;
                }

                String sellerName = mEtSellerName.getText().toString();
                String shopName = mEtShopName.getText().toString();
                String shopInfo = mEtShopInfo.getText().toString();
                String sellerPhone = mEtShopPhone.getText().toString();

                RequestSeller requestSeller = new RequestSeller(sellerName, shopName, shopInfo, sellerPhone);
                mPresenter.updateSellerInformation(requestSeller);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void successfullySellerInformationUpdate() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void failureSellerInformationUpdate() {
        getActivity().finish();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(SellerInformationEditContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
