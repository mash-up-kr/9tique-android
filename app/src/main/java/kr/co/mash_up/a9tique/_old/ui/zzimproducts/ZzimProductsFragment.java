package kr.co.mash_up.a9tique._old.ui.zzimproducts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique._old.base.ui.BaseFragment;
import kr.co.mash_up.a9tique._old.common.Constants;
import kr.co.mash_up.a9tique._old.common.eventbus.EventNetworkStatus;
import kr.co.mash_up.a9tique._old.data.Product;
import kr.co.mash_up.a9tique._old.ui.ConfirmationDialogFragment;
import kr.co.mash_up.a9tique._old.ui.EndlessRecyclerViewScrollListener;
import kr.co.mash_up.a9tique._old.ui.InquireSelectionDialogFragment;
import kr.co.mash_up.a9tique._old.ui.OrientationSpacingItemDecoration;
import kr.co.mash_up.a9tique._old.ui.productdetail.customer.CustomerProductDetailActivity;
import kr.co.mash_up.a9tique._old.ui.widget.RecyclerViewEmptySupport;
import kr.co.mash_up.a9tique._old.util.CheckNonNullUtil;
import kr.co.mash_up.a9tique._old.util.ProgressUtil;
import kr.co.mash_up.a9tique._old.util.SnackbarUtil;


public class ZzimProductsFragment extends BaseFragment implements ZzimProductsContract.View {

    public static final String TAG = ZzimProductsFragment.class.getSimpleName();

    @BindView(R.id.rv_zzim_products)
    RecyclerViewEmptySupport mRvZzimProducts;

    @BindView(R.id.ll_emptyView)
    LinearLayout mEmptyView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindDimen(R.dimen.zzim_product_list_item_bottom_margin)
    int itemSpacingSize;

    private ZzimlProductListAdapter mZzimlProductListAdapter;

    private ZzimProductsContract.Presenter mPresenter;

    public ZzimProductsFragment() {
    }

    public static ZzimProductsFragment newInstance() {
        ZzimProductsFragment fragment = new ZzimProductsFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
//        if(getArguments() != null){
//        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zzim_products;
    }

    @Override
    public void initView(View rootView) {
        initZzimList();

        mSwipeRefreshLayout.setColorSchemeResources(R.color.tulip_tree, R.color.mine_shaft);
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshProducts);
    }

    private void initZzimList() {
        mRvZzimProducts.setHasFixedSize(true);
        LinearLayoutManager llmZzimProducts = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvZzimProducts.setLayoutManager(llmZzimProducts);
        mRvZzimProducts.addItemDecoration(new OrientationSpacingItemDecoration(itemSpacingSize, OrientationSpacingItemDecoration.Orientation.BOTTOM));
        mRvZzimProducts.setEmptyView(mEmptyView);

        mZzimlProductListAdapter = new ZzimlProductListAdapter(getActivity());
        mRvZzimProducts.setAdapter(mZzimlProductListAdapter);
               mZzimlProductListAdapter.setOnItemClickListener(new ZzimlProductListAdapter.OnItemClickListener<Product>() {

            @Override
            public void onClick(Product product, int position, ImageView shareImageView, String transitionName) {
                mPresenter.detailProduct(product, shareImageView, transitionName);
            }

            @Override
            public void onRemove(Product product, int position) {
                mPresenter.onClickRemoveProduct(product, position);
            }

            @Override
            public void onInquire(Product product, int position) {
                mPresenter.onClickInquireProduct(product, position);
            }
        });

        mRvZzimProducts.addOnScrollListener(new EndlessRecyclerViewScrollListener(llmZzimProducts) {
            @Override
            public int getFooterViewType(int defaultNoFooterViewType) {
                return mZzimlProductListAdapter.VIEW_TYPE_FOOTER;
            }

            @Override
            public void onLoadMore(int page, int totalItemCount) {
                Log.d(TAG, "page: " + page + " totalItemCount: " + totalItemCount);
                mPresenter.loadMoreProducts(totalItemCount);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.result(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showLoadingIndicator(boolean active) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(active));
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
    public void showFooterView(boolean active, int position) {
        if (active) {
            mZzimlProductListAdapter.addFooterView(position);
        } else {
            mZzimlProductListAdapter.removeFooterView(position);
        }
    }

    @Override
    public void showLoadedProducts(List<Product> products) {
        mZzimlProductListAdapter.setProducts(products);
    }

    @Override
    public void showLoadedMoreProducts(List<Product> products) {
        mZzimlProductListAdapter.addProducts(products);
    }

    @Override
    public void showLoadingProductsError() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품로딩 실패", "", null);
    }

    @Override
    public void refreshProducts() {
        mPresenter.loadProducts(true);
    }

    @Override
    public void showNoProducts() {
        mZzimlProductListAdapter.clearProducts();
    }

    @Override
    public void showProductDetail(Product product, ImageView shareImageView, String transitionName) {
        Intent intent = new Intent(getActivity(), CustomerProductDetailActivity.class);
        intent.putExtra(Constants.PRODUCT, product);
        intent.putExtra(Constants.TRANSITION_NAME, transitionName);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                shareImageView,
                transitionName);
        startActivityForResult(intent, CustomerProductDetailActivity.REQUEST_CODE_DETAIL_RPODUCT, options.toBundle());
    }

    @Override
    public void removeProduct(int position) {
        mZzimlProductListAdapter.removeItem(position);
    }

    @Override
    public void showDialogInquire(Product product, int position) {
        InquireSelectionDialogFragment dlgInquire = InquireSelectionDialogFragment.newInstance("문의하기");
        dlgInquire.setCallback(new InquireSelectionDialogFragment.Callback() {
            @Override
            public void onClickCallPhone() {
                mPresenter.callPhone(product);
            }

            @Override
            public void onClickSendMessage() {
                mPresenter.sendMessage(product);
            }

            @Override
            public void onClickKakaoOpenChat() {
                mPresenter.kakaoOpenChat(product);
            }
        });
        dlgInquire.setTargetFragment(ZzimProductsFragment.this, 0);
        dlgInquire.show(getChildFragmentManager(), InquireSelectionDialogFragment.TAG);
    }

    @Override
    public void showSuccessfullyRemovedMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품 삭제 완료", "", null);
    }

    @Override
    public void showFailureRemovedMessage() {
        SnackbarUtil.showMessage(getActivity(), getView(), "상품 삭제 실패", "", null);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showCallPhone(String phoneNumber) {
        Intent dialPhoneIntent = new Intent(Intent.ACTION_DIAL);
        dialPhoneIntent.setData(Uri.parse("tel:" + phoneNumber));
        if (dialPhoneIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(dialPhoneIntent);
        } else {
            //Todo: show message - 전화 app이 없습니다.. 설치해주세요
        }
    }

    @Override
    public void showSendMessage(String phoneNumber) {
        Intent sendMessageIntent = new Intent(Intent.ACTION_VIEW);
        sendMessageIntent.putExtra("sms_body", "문의하기");
        sendMessageIntent.putExtra("address", phoneNumber);
        sendMessageIntent.setType("vnd.android-dir/mms-sms");
        if (sendMessageIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(sendMessageIntent);
        } else {
            //Todo: show message - 문자 app이 없습니다.. 설치해주세요
        }
    }

    @Override
    public void showKakaoOpenChat(String kakaoOpenChatUrl) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(kakaoOpenChatUrl));
        startActivity(intent);
    }

    @Override
    public void showDialogRemoveProduct(Product product, int position) {
        ConfirmationDialogFragment dlgRemoveConfirmation
                = ConfirmationDialogFragment.newInstance("찜 상품 삭제", "선택하신 상품을 삭제 하시겠습니까?");
        dlgRemoveConfirmation.setCallback(new ConfirmationDialogFragment.Callback() {
            @Override
            public void onClickOk() {
                mPresenter.removeProduct(product, position);
            }

            @Override
            public void onClickCancel() {
                // Do nothing
            }
        });
        dlgRemoveConfirmation.setTargetFragment(ZzimProductsFragment.this, 0);
        dlgRemoveConfirmation.show(getChildFragmentManager(), ConfirmationDialogFragment.TAG);
    }

    @Override
    public void setPresenter(ZzimProductsContract.Presenter presenter) {
        mPresenter = CheckNonNullUtil.checkNotNull(presenter);
    }

    @Override
    protected void handleEventFromBus(Object event) {
        if(event instanceof EventNetworkStatus){
            SnackbarUtil.showMessage(getActivity(), getView(), "네트워크 상태가 불안정합니다", "" , null);
        }
    }
}