package kr.co.mash_up.a9tique.ui.products;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.data.SubCategory;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.ResponseProduct;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;

//Todo: 리스트 무한 스크롤
public class SubCategoryFragment extends BaseFragment {

    public static final String TAG = SubCategoryFragment.class.getSimpleName();

    @BindView(R.id.rv_products)
    RecyclerView mRvProducts;

    private ProductListAdapter mProductListAdapter;

    private static final String ARG_PARAM_MAIN_CATEGORY = "mainCategory";
    private static final String ARG_PARAM_SUB_CATEGORY = "subCategory";
    private static final String ARG_PARAM_CURRENT_PAGE_NO = "currentPageNo";
    private static final String ARG_PARAM_PAGE_TOTAL = "pageTotal";

    private String mParamMainCategory;
    private String mParamSubCategory;
    private int mParamCurrentPageNo;
    private int mParamPageTotal;

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    public static SubCategoryFragment newInstance(String mainCategory, String subCategory) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_MAIN_CATEGORY, mainCategory);
        args.putString(ARG_PARAM_SUB_CATEGORY, subCategory);
        args.putInt(ARG_PARAM_CURRENT_PAGE_NO, 0);
        args.putInt(ARG_PARAM_PAGE_TOTAL, 0);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamMainCategory = getArguments().getString(ARG_PARAM_MAIN_CATEGORY);
            mParamSubCategory = getArguments().getString(ARG_PARAM_SUB_CATEGORY);
            mParamCurrentPageNo = getArguments().getInt(ARG_PARAM_CURRENT_PAGE_NO);
            mParamPageTotal = getArguments().getInt(ARG_PARAM_PAGE_TOTAL);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sub_category;
    }

    @Override
    public void initView(View rootView) {
        mRvProducts.setHasFixedSize(true);
        mRvProducts.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mProductListAdapter = new ProductListAdapter(getActivity());
        mProductListAdapter.setOnItemClickListener(product -> {
            //Todo: show detail product activity
            //Todo: remove snackbar
            Snackbar.make(getView(), "show detail product activity", Snackbar.LENGTH_SHORT)
                    .show();
        });
        mRvProducts.setAdapter(mProductListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        // total 7, 요청은 0~6까지
//        if(mParamCurrentPageNo == mParamPageTotal - 1)

        BackendHelper.getInstance().getProducts(mParamCurrentPageNo, mParamMainCategory, mParamSubCategory,
                new ResultCallback<ResponseProduct>() {
                    @Override
                    public void onSuccess(ResponseProduct responseProduct) {
                        mParamCurrentPageNo = responseProduct.getCurrentPageNo();
                        mParamPageTotal = responseProduct.getPageTotal();

                        mProductListAdapter.setProducts(responseProduct.getProducts());
                        Log.d(TAG, "1");
                    }

                    @Override
                    public void onFailure() {
                        //Todo: show toast
                    }
                });
    }
}
