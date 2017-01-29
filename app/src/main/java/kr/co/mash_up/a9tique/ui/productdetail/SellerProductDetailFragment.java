package kr.co.mash_up.a9tique.ui.productdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.common.Constants;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.data.ProductImage;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.RequestProduct;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.ui.addeditproduct.AddEditProductActivity;
import kr.co.mash_up.a9tique.ui.addeditproduct.ConfirmationDialogFragment;
import kr.co.mash_up.a9tique.util.SnackbarUtil;

import static kr.co.mash_up.a9tique.common.Constants.PRODUCT_ID;


public class SellerProductDetailFragment extends BaseFragment {

    public static final String TAG = SellerProductDetailFragment.class.getSimpleName();
    private static final String ARG_PARAM_PRODUCT = "product";

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

    @BindView(R.id.btn_product_status_update_top)
    Button mBtnSoldOutTop;

    @BindView(R.id.btn_product_status_update_bottom)
    Button mBtnSoldOutBottom;

    @BindDimen(R.dimen.product_detail_image_list_item_bottom_margin)
    int listItemBottomMargin;

    private Product mProduct;

//    private ProductImageListAdapter mProductImageListAdapter;

    public SellerProductDetailFragment() {
        // Required empty public constructor
    }

    public static SellerProductDetailFragment newInstance(Product product) {
        SellerProductDetailFragment fragment = new SellerProductDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = getArguments().getParcelable(ARG_PARAM_PRODUCT);
        }
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_seller_product_detail;
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
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade()
                    .fitCenter()
                    .centerCrop()
                    .into(ivImage);
            mLlDetailImageContainer.addView(cardView);
        }
    }

    private void initBtnSoldout(Product.Status status) {
        if (status.name().equals(Product.Status.SELL.name())) {
            mBtnSoldOutTop.setText("판매완료");
            mBtnSoldOutBottom.setText("판매완료");
        } else {
            mBtnSoldOutTop.setText("판매중");
            mBtnSoldOutBottom.setText("판매중");
        }
    }

    @Override
    public void initView(View rootView) {
        mTvName.setText(mProduct.getName());
        mTvProductBrandName.setText(mProduct.getBrandName());
        mTvProductSize.setText(mProduct.getSize());
        mTvProductPrice.setText(String.valueOf(mProduct.getPrice()));
        mTvProductDescription.setText(mProduct.getDescription());

        initBtnSoldout(mProduct.getStatus());
        initDetailProductImageList(mProduct.getProductImages());
    }

    @OnClick({R.id.btn_product_status_update_top, R.id.btn_product_status_update_bottom})
    public void onClickUpdateProductStatus() {
        ConfirmationDialogFragment dlgConfirmation;
        RequestProduct requestProduct = new RequestProduct();

        if (mProduct.getStatus().name().equals(Product.Status.SELL.name())) {
            dlgConfirmation = ConfirmationDialogFragment.newInstance("상품 판매 표기", "해당 상품을 판매완료로 표기하시겠습니까?");
            requestProduct.setStatus(Product.Status.SOLD_OUT);
        } else {
            dlgConfirmation = ConfirmationDialogFragment.newInstance("상품 판매 표기", "해당 상품을 판매중으로 표기하시겠습니까?");
            requestProduct.setStatus(Product.Status.SELL);
        }

        dlgConfirmation.setCallback(new ConfirmationDialogFragment.Callback() {
            @Override
            public void onClickOk() {
                // api call. product sell <-> sold out
                BackendHelper.getInstance().updateProduct(mProduct.getId(), requestProduct, new ResultCallback() {
                    @Override
                    public void onSuccess(Object o) {
                        Intent receiveIntent = getActivity().getIntent();
                        receiveIntent.putExtra(Constants.API_RESULT, Constants.PRODUCT_UPDATE_STATUS_SUCCESS);
                        getActivity().setResult(Activity.RESULT_OK, receiveIntent);
                        getActivity().finish();
                    }

                    @Override
                    public void onFailure() {
                        Intent receiveIntent = getActivity().getIntent();
                        receiveIntent.putExtra(Constants.API_RESULT, Constants.PRODUCT_UPDATE_STATUS_FAILURE);
                        getActivity().setResult(Activity.RESULT_OK, receiveIntent);
                        getActivity().finish();
                    }
                });
            }

            @Override
            public void onClickCancel() {
                // Do noting
            }
        });

        dlgConfirmation.setTargetFragment(SellerProductDetailFragment.this, 0);
        dlgConfirmation.show(getChildFragmentManager(), ConfirmationDialogFragment.TAG);
    }

    @OnClick({R.id.btn_product_remove_top, R.id.btn_product_remove_bottom})
    public void onClickProductRemove() {
        ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstance("판매중인 상품 삭제", "선택하신 상품을 삭제 하시겠습니까?");
        dialog.setCallback(new ConfirmationDialogFragment.Callback() {
            @Override
            public void onClickOk() {
                // api call. product delete
                BackendHelper.getInstance().deleteProduct(mProduct.getId(), new ResultCallback() {
                    @Override
                    public void onSuccess(Object o) {
                        Intent receiveIntent = getActivity().getIntent();
                        receiveIntent.putExtra(Constants.API_RESULT, Constants.PRODUCT_DELETE_SUCCESS);
                        getActivity().setResult(Activity.RESULT_OK, receiveIntent);
                        getActivity().finish();
                    }

                    @Override
                    public void onFailure() {
                        Intent receiveIntent = getActivity().getIntent();
                        receiveIntent.putExtra(Constants.API_RESULT, Constants.PRODUCT_DELETE_FAILURE);
                        getActivity().setResult(Activity.RESULT_OK, receiveIntent);
                        getActivity().finish();
                    }
                });
            }

            @Override
            public void onClickCancel() {
                // do noting
            }
        });
        dialog.setTargetFragment(SellerProductDetailFragment.this, 0);
        dialog.show(getChildFragmentManager(), ConfirmationDialogFragment.TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
        // destroy all menu and re-call onCreateOptionsMenu
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_seller_product_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_seller_product_modify:
                // open seller product edit activity
                Intent intent = new Intent(getActivity(), AddEditProductActivity.class);
                intent.putExtra(Constants.PRODUCT_ID, mProduct.getId());
                intent.putExtra(Constants.PRODUCT, mProduct);
                startActivityForResult(intent, AddEditProductActivity.REQUEST_CODE_EDIT_PRODUCT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
}
