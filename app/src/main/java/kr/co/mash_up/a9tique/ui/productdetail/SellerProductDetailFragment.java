package kr.co.mash_up.a9tique.ui.productdetail;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.data.Product;
import kr.co.mash_up.a9tique.data.ProductImage;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.RequestProduct;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.ui.addeditproduct.AddEditProductFragment;
import kr.co.mash_up.a9tique.ui.addeditproduct.ConfirmationDialogFragment;
import rx.Observable;


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

    @BindDimen(R.dimen.list_item_bottom_margin)
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
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_seller_product_detail;
    }

    @Override
    public void initView(View rootView) {
        mTvName.setText(mProduct.getName());
        mTvProductBrandName.setText(mProduct.getBrandName());
        mTvProductSize.setText(mProduct.getSize());
        mTvProductPrice.setText(String.valueOf(mProduct.getPrice()));
        mTvProductDescription.setText(mProduct.getDescription());

//        mRvImage.setHasFixedSize(true);
//        mRvImage.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        mRvImage.addItemDecoration(new SpacingItemDecoration(listItemBottomMargin));
//
//        mProductImageListAdapter = new ProductImageListAdapter(mProduct.getProductImages(), getActivity());
//        mRvImage.setAdapter(mProductImageListAdapter);

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        List<ProductImage> productImages = mProduct.getProductImages();
        for(int i=0; i<productImages.size(); i++){
            ProductImage productImage = productImages.get(i);
            CardView cardView = (CardView) layoutInflater.inflate(R.layout.item_detail_product_image_list, mLlDetailImageContainer, false);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardView.getLayoutParams();
            params.bottomMargin = listItemBottomMargin;
            cardView.setLayoutParams(params);

            ImageView ivImage = (ImageView) cardView.findViewById(R.id.iv_product_image);
            Glide.with(getActivity())
                    .load(productImage.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade()
                    .fitCenter()
                    .centerCrop()
                    .into(ivImage);
            mLlDetailImageContainer.addView(cardView);
        }
    }

    @OnClick({R.id.btn_sold_out_top, R.id.btn_sold_out_bottom})
    public void onClickSoldout() {
        ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstance("상품 판매 표기", "해당 상품을 판매완료로 표기하시겠습니까?");
        dialog.setCallback(new ConfirmationDialogFragment.Callback() {
            @Override
            public void onClickOk() {
                //Todo: api call. product sell -> sold out
                RequestProduct requestProduct = new RequestProduct();
                requestProduct.setStatus(Product.Status.SOLD_OUT);
                BackendHelper.getInstance().updateProduct(mProduct.getId(), requestProduct, new ResultCallback() {
                    @Override
                    public void onSuccess(Object o) {
                        getActivity().setResult(Activity.RESULT_OK);
                        //Todo: show update success message
                        getActivity().finish();
                    }

                    @Override
                    public void onFailure() {
                        //Todo: show update fail message
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

    @OnClick({R.id.btn_product_remove_top, R.id.btn_product_remove_bottom})
    public void onClickProductRemove() {
        ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstance("판매중인 상품 삭제", "선택하신 상품을 삭제 하시겠습니까?");
        dialog.setCallback(new ConfirmationDialogFragment.Callback() {
            @Override
            public void onClickOk() {
                //Todo: api call. product delete
                BackendHelper.getInstance().deleteProduct(mProduct.getId(), new ResultCallback() {
                    @Override
                    public void onSuccess(Object o) {
                        getActivity().setResult(Activity.RESULT_OK);
                        getActivity().finish();
                        //Todo: show delete success message
                    }

                    @Override
                    public void onFailure() {
                        //Todo: show delete fail message
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
}
