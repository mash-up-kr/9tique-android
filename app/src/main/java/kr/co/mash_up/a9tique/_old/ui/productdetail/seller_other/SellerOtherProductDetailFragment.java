package kr.co.mash_up.a9tique._old.ui.productdetail.seller_other;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique._old.base.ui.BaseFragment;
import kr.co.mash_up.a9tique._old.common.Constants;
import kr.co.mash_up.a9tique._old.common.eventbus.EventNetworkStatus;
import kr.co.mash_up.a9tique._old.data.Product;
import kr.co.mash_up.a9tique._old.data.ProductImage;
import kr.co.mash_up.a9tique._old.ui.InquireSelectionDialogFragment;
import kr.co.mash_up.a9tique._old.ui.addeditproduct.AddEditProductActivity;
import kr.co.mash_up.a9tique._old.util.SnackbarUtil;


public class SellerOtherProductDetailFragment extends BaseFragment {

    public static final String TAG = SellerOtherProductDetailFragment.class.getSimpleName();

    @BindView(R.id.tv_name)
    TextView mTvName;

    @BindView(R.id.tv_product_brand_name)
    TextView mTvProductBrandName;

    @BindView(R.id.tv_product_size)
    TextView mTvProductSize;

    @BindView(R.id.tv_product_price)
    TextView mTvProductPrice;

    @BindView(R.id.tv_product_description)
    TextView mTvProductDescription;

//    @BindView(R.id.rv_image)
//    RecyclerView mRvImage;

    @BindView(R.id.ll_detail_image_container)
    LinearLayout mLlDetailImageContainer;

    @BindDimen(R.dimen.product_detail_image_list_item_bottom_margin)
    int listItemBottomMargin;

    private Product mProduct;

//    private ProductImageListAdapter mProductImageListAdapter;

    public SellerOtherProductDetailFragment() {
        // Required empty public constructor
    }

    public static SellerOtherProductDetailFragment newInstance(Product product) {
        SellerOtherProductDetailFragment fragment = new SellerOtherProductDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = getArguments().getParcelable(Constants.PRODUCT);
        }
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_seller_other_product_detail;
    }

    private void initDetailProductImageList(List<ProductImage> productImages) {

//        mRvImage.setHasFixedSize(true);
//        mRvImage.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        mRvImage.addItemDecoration(new SpacingItemDecoration(listItemBottomMargin));
//
//        mProductImageListAdapter = new ProductImageListAdapter(productImages, getActivity());
//        mRvImage.setAdapter(mProductImageListAdapter);

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        for (int i = 0; i < productImages.size(); i++) {
            ProductImage productImage = productImages.get(i);
            CardView cardView = (CardView) layoutInflater.inflate(R.layout.item_detail_product_image_list, mLlDetailImageContainer, false);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardView.getLayoutParams();
            params.bottomMargin = listItemBottomMargin;
            cardView.setLayoutParams(params);

            ImageView ivImage = (ImageView) cardView.findViewById(R.id.iv_product_image);
            Glide.with(getActivity())
                    .load(Constants.END_POINT + productImage.getImageUrl())
                    .placeholder(R.drawable.ic_nodata)
                    .error(R.drawable.ic_nodata)
                    .crossFade()
                    .fitCenter()
                    .centerCrop()
                    .into(ivImage);
            mLlDetailImageContainer.addView(cardView);
        }
    }

    @Override
    public void initView(View rootView) {
        mTvName.setText(mProduct.getName());
        mTvProductBrandName.setText(mProduct.getBrandName());
        mTvProductSize.setText(mProduct.getSize());
        mTvProductPrice.setText(String.valueOf(mProduct.getPrice()));
        mTvProductDescription.setText(mProduct.getDescription());

        initDetailProductImageList(mProduct.getProductImages());
    }

    @OnClick({R.id.btn_product_inquire_top, R.id.btn_product_inquire_bottom})
    public void onClickInquire() {
        InquireSelectionDialogFragment dlgInquire = InquireSelectionDialogFragment.newInstance("문의하기");
        dlgInquire.setCallback(new InquireSelectionDialogFragment.Callback() {
            @Override
            public void onClickCallPhone() {
                Intent dialPhoneIntent = new Intent(Intent.ACTION_DIAL);
                dialPhoneIntent.setData(Uri.parse("tel:" + mProduct.getShop().getPhone()));
                if (dialPhoneIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(dialPhoneIntent);
                } else {
                    //Todo: show message - 전화 app이 없습니다.. 설치해주세요
                }
            }

            @Override
            public void onClickSendMessage() {
                Intent sendMessageIntent = new Intent(Intent.ACTION_VIEW);
                sendMessageIntent.putExtra("sms_body", "문의하기");
                sendMessageIntent.putExtra("address", mProduct.getShop().getPhone());
                sendMessageIntent.setType("vnd.android-dir/mms-sms");
                if (sendMessageIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(sendMessageIntent);
                } else {
                    //Todo: show message - 문자 app이 없습니다.. 설치해주세요
                }
            }

            @Override
            public void onClickKakaoOpenChat() {
                Intent intent = new Intent();
                intent.setData(Uri.parse(mProduct.getShop().getKakaoOpenChatUrl()));
                startActivity(intent);
            }
        });
        dlgInquire.setTargetFragment(SellerOtherProductDetailFragment.this, 0);
        dlgInquire.show(getChildFragmentManager(), InquireSelectionDialogFragment.TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
        // destroy all menu and re-call onCreateOptionsMenu
//        getActivity().invalidateOptionsMenu();
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_seller_product_detail, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_seller_product_modify:
//                // open seller product edit activity
//                Intent intent = new Intent(getActivity(), AddEditProductActivity.class);
//                intent.putExtra(Constants.PRODUCT_ID, mProduct.getId());
//                intent.putExtra(Constants.PRODUCT, mProduct);
//                startActivityForResult(intent, AddEditProductActivity.REQUEST_CODE_EDIT_PRODUCT);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AddEditProductActivity.REQUEST_CODE_EDIT_PRODUCT:
                if (resultCode == Activity.RESULT_OK) {
                    SnackbarUtil.showMessage(getActivity(), getView(), "상품 수정 완료", "", null);
                    //Todo: reloading
                } else {
                    SnackbarUtil.showMessage(getActivity(), getView(), "상품 수정 실패", "", null);
                }
                break;
        }
    }

    @Override
    protected void handleEventFromBus(Object event) {
        if (event instanceof EventNetworkStatus) {
            SnackbarUtil.showMessage(getActivity(), getView(), "네트워크 상태가 불안정합니다", "", null);
        }
    }
}