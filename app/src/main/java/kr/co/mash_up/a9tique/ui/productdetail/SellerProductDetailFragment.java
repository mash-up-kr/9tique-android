package kr.co.mash_up.a9tique.ui.productdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.data.ProductImage;


public class SellerProductDetailFragment extends BaseFragment {

    public static final String TAG = SellerProductDetailFragment.class.getSimpleName();

    @BindView(R.id.rv_image)
    RecyclerView mRvImage;

    @BindDimen(R.dimen.list_item_bottom_margin)
    int listItemBottomMargin;

    private ProductImageListAdapter mProductImageListAdapter;

    public SellerProductDetailFragment() {
        // Required empty public constructor
    }

    public static SellerProductDetailFragment newInstance() {
        SellerProductDetailFragment fragment = new SellerProductDetailFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
        setRetainInstance(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_seller_product_detail;
    }

    @Override
    public void initView(View rootView) {
        mRvImage.setHasFixedSize(true);
        mRvImage.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvImage.addItemDecoration(new SpacingItemDecoration(listItemBottomMargin));

        List<ProductImage> productImageList = new ArrayList<>();
        productImageList.add(new ProductImage());
        productImageList.add(new ProductImage());
        mProductImageListAdapter = new ProductImageListAdapter(productImageList, getActivity());
        mRvImage.setAdapter(mProductImageListAdapter);
    }


    @OnClick({R.id.btn_sold_out_top, R.id.btn_sold_out_bottom})
    public void onClickSoldout(){
        //Todo: api call
    }

    @OnClick({R.id.btn_product_remove_top, R.id.btn_product_remove_bottom})
    public void onClickProductRemove(){
        //Todo: api call
    }
}
