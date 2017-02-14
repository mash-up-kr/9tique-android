package kr.co.mash_up.a9tique.ui.setting.sellerinformation;

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


public class SellerInfoFragment extends BaseFragment {

    public static final String TAG = SellerInfoFragment.class.getSimpleName();

    @BindView(R.id.tv_seller_name)
    TextView mTvSellerName;

    @BindView(R.id.tv_shop_name)
    TextView mTvShopName;

    @BindView(R.id.tv_shop_info)
    TextView mTvShopInfo;

    @BindView(R.id.tv_seller_phone)
    TextView mTvSellerPhone;

    private Seller mSeller;

    public SellerInfoFragment() {
        // Required empty public constructor
    }

    public static SellerInfoFragment newInstance(Seller seller) {
        SellerInfoFragment fragment = new SellerInfoFragment();
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
        return R.layout.fragment_seller_information;
    }

    @Override
    public void initView(View rootView) {
        mTvSellerName.setText(mSeller.getUser().getName());
        mTvShopName.setText(mSeller.getShop().getName());
        mTvShopInfo.setText(mSeller.getShop().getInfo());
        mTvSellerPhone.setText(mSeller.getShop().getPhone());
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
                ((SellerInformationActivity) getActivity()).showSellerInfoEdit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
