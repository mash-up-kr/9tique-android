package kr.co.mash_up.a9tique._old.ui.addeditproduct.categorysleleciton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique._old.base.ui.BaseFragment;
import kr.co.mash_up.a9tique._old.common.eventbus.EventNetworkStatus;
import kr.co.mash_up.a9tique.data.MainCategory;
import kr.co.mash_up.a9tique.data.SubCategory;
import kr.co.mash_up.a9tique._old.ui.OnItemClickListener;
import kr.co.mash_up.a9tique._old.util.SnackbarUtil;


public class CategorySelectionFragment extends BaseFragment {

    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;

    CategoryListAdapter mCategoryListAdapter;

    public static final String TAG = CategorySelectionFragment.class.getSimpleName();

    public static final String[] mainCategories = {"아우터", "상의", "하의", "신발", "모자"};

    public static final String[] outerSubCategories = {"자켓", "코트", "점퍼", " 후드집업", "패딩점퍼",
            "베스트"};

    public static final String[] topSubCategories = {"티셔츠", "후드티셔츠", "슬리브리스", " 셔츠",
            "니트", "블라우스", "원피스"};

    public static final String[] bottomSubCategories = {"데님팬츠", "팬츠", "쇼츠", " 스커트"};


    public CategorySelectionFragment() {
        // Required empty public constructor
    }

    public static CategorySelectionFragment newInstance() {
        CategorySelectionFragment fragment = new CategorySelectionFragment();
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
        return R.layout.fragment_category_selection;
    }

    @Override
    public void initView(View rootView) {

        SubCategory subCategory;
        List<SubCategory> subCategories = new ArrayList<>();

        for (String outerSubCategory : outerSubCategories) {
            subCategory = new SubCategory(outerSubCategory);
            subCategories.add(subCategory);
        }
        MainCategory mainCategory1 = new MainCategory(mainCategories[0], subCategories);

        subCategories = new ArrayList<>();
        for (String topSubCategory : topSubCategories) {
            subCategory = new SubCategory(topSubCategory);
            subCategories.add(subCategory);
        }
        MainCategory mainCategory2 = new MainCategory(mainCategories[1], subCategories);

        subCategories = new ArrayList<>();
        for (String bottomCategory : bottomSubCategories) {
            subCategory = new SubCategory(bottomCategory);
            subCategories.add(subCategory);
        }
        MainCategory mainCategory3 = new MainCategory(mainCategories[2], subCategories);
        MainCategory mainCategory4 = new MainCategory(mainCategories[3], new ArrayList<SubCategory>());
        MainCategory mainCategory5 = new MainCategory(mainCategories[4], new ArrayList<SubCategory>());

        final List<MainCategory> mainCategoryList = Arrays.asList(mainCategory1, mainCategory2, mainCategory3,
                mainCategory4, mainCategory5);

        mCategoryListAdapter = new CategoryListAdapter(getActivity(), mainCategoryList);
        mCategoryListAdapter.setOnItemClickListener(new OnItemClickListener<String>() {

            @Override
            public void onClick(String str, int position) {
                Intent intent = new Intent();

                // sub 카테고리가 왔을 때 main 카테고리 채우기
                for (int i = 0; i < outerSubCategories.length; i++) {
                    if (str.equals(outerSubCategories[i])) {
                        intent.putExtra("mainCategory", mainCategories[0]);
                        intent.putExtra("subCategory", str);
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();
                    }
                }

                for (int i = 0; i < topSubCategories.length; i++) {
                    if (str.equals(topSubCategories[i])) {
                        intent.putExtra("mainCategory", mainCategories[1]);
                        intent.putExtra("subCategory", str);
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();
                    }
                }

                for (int i = 0; i < bottomSubCategories.length; i++) {
                    if (str.equals(bottomSubCategories[i])) {
                        intent.putExtra("mainCategory", mainCategories[2]);
                        intent.putExtra("subCategory", str);
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();
                    }
                }

                if (str.equals(mainCategories[3]) || str.equals(mainCategories[4])) {
                    intent.putExtra("mainCategory", str);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
            }
        });
        mRvCategory.setHasFixedSize(true);
        mRvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvCategory.setAdapter(mCategoryListAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCategoryListAdapter.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mCategoryListAdapter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void handleEventFromBus(Object event) {
        if(event instanceof EventNetworkStatus){
            SnackbarUtil.showMessage(getActivity(), getView(), "네트워크 상태가 불안정합니다", "" , null);
        }
    }
}
