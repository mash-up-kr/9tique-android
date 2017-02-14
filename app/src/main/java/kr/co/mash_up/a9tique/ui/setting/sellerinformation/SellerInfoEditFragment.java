package kr.co.mash_up.a9tique.ui.setting.sellerinformation;

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
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.RequestSeller;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.util.SnackbarUtil;


public class SellerInfoEditFragment extends BaseFragment {

    public static final String TAG = SellerInfoEditFragment.class.getSimpleName();

    @BindView(R.id.et_seller_name)
    EditText mEtSellerName;

    @BindView(R.id.et_shop_name)
    EditText mEtShopName;

    @BindView(R.id.et_shop_info)
    EditText mEtShopInfo;

    @BindView(R.id.et_shop_phone)
    EditText mEtShopPhone;

    private Seller mSeller;

    public SellerInfoEditFragment() {
        // Required empty public constructor
    }

    public static SellerInfoEditFragment newInstance(Seller seller) {
        SellerInfoEditFragment fragment = new SellerInfoEditFragment();
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
                BackendHelper.getInstance().updateSellerInfo(requestSeller, new ResultCallback() {
                    @Override
                    public void onSuccess(@Nullable Object o) {
                        //Todo: show success message
                        getActivity().finish();
                    }

                    @Override
                    public void onFailure() {
                        //Todo: show failure message
                        getActivity().finish();
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
