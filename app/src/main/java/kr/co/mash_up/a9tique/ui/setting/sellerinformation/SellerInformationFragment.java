package kr.co.mash_up.a9tique.ui.setting.sellerinformation;

import android.app.Activity;
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
import kr.co.mash_up.a9tique.data.Seller;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.ui.setting.sellerinformation.edit.SellerInformationEditActivity;
import kr.co.mash_up.a9tique.util.ProgressUtil;
import kr.co.mash_up.a9tique.util.SnackbarUtil;


public class SellerInformationFragment extends BaseFragment {

    public static final String TAG = SellerInformationFragment.class.getSimpleName();
    public static final int REQUEST_CODE_SELLER_INFORMATION_MODIFY = 3000;

    @BindView(R.id.tv_seller_name)
    TextView mTvSellerName;

    @BindView(R.id.tv_shop_name)
    TextView mTvShopName;

    @BindView(R.id.tv_shop_info)
    TextView mTvShopInfo;

    @BindView(R.id.tv_seller_phone)
    TextView mTvSellerPhone;

    private Seller mSeller;

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
        getSellerInformation();
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
                showSellerInformationModify();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SELLER_INFORMATION_MODIFY:
                if (resultCode == Activity.RESULT_OK) {
                    SnackbarUtil.showMessage(getActivity(), getView(), "판매자 정보 수정 성공", "", null);
                    getSellerInformation();
                } else {
                    SnackbarUtil.showMessage(getActivity(), getView(), "판매자 정보 수정 실패",
                            "Retry", view -> showSellerInformationModify());
                }
                break;
        }
    }

    private void getSellerInformation() {
        ProgressUtil.showProgressDialog(getActivity());

        BackendHelper.getInstance().getSellerInfo(new ResultCallback<Seller>() {
            @Override
            public void onSuccess(@Nullable Seller seller) {
                mSeller = seller;
                setSellerInfo();
                ProgressUtil.hideProgressDialog();
            }

            @Override
            public void onFailure() {
                ProgressUtil.hideProgressDialog();
                SnackbarUtil.showMessage(getActivity(), getView(), "판매자 정보 로딩 실패",
                        "Retry", view -> getSellerInformation());
            }
        });
    }

    private void setSellerInfo() {
        mTvSellerName.setText(mSeller.getUser().getName());
        mTvShopName.setText(mSeller.getShop().getName());
        mTvShopInfo.setText(mSeller.getShop().getInfo());
        mTvSellerPhone.setText(mSeller.getShop().getPhone());
    }


    private void showSellerInformationModify() {
        Intent intent = new Intent(getActivity(), SellerInformationEditActivity.class);
        intent.putExtra(Constants.SELLER, mSeller);
        startActivityForResult(intent, REQUEST_CODE_SELLER_INFORMATION_MODIFY);
    }
}
