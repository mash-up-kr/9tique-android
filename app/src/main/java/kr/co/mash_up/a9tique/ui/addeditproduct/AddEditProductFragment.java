package kr.co.mash_up.a9tique.ui.addeditproduct;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;
import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.data.ProductImage;
import kr.co.mash_up.a9tique.data.remote.BackendHelper;
import kr.co.mash_up.a9tique.data.remote.ResultCallback;
import kr.co.mash_up.a9tique.ui.addeditproduct.categorysleleciton.CategorySelectionActivity;
import kr.co.mash_up.a9tique.util.SnackbarUtil;
import kr.co.mash_up.a9tique.util.TranslationUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class AddEditProductFragment extends BaseFragment implements ConfirmationDialogFragment.Callback,
        PictureSelectionDialogFragment.Callback {

    public static final String TAG = AddEditProductFragment.class.getSimpleName();
    public static final String PARAM_PRODUCT_ID = "productId";
    public static final int REQUEST_CODE_CATEGORY_SELECTION = 1000;
    public static final int REQUEST_CODE_CAMERA_CAPTURE = 1001;

    private Integer mParamProductId;
    private String mMainCategory;
    private String mSubCategory;

    @BindView(R.id.tv_image_count)
    TextView mTvImageCount;

    @BindView(R.id.rv_image)
    RecyclerView mRvImage;

    @BindView(R.id.et_brand_description)
    EditText mEtBrandDescription;

    @BindView(R.id.et_name_description)
    EditText mEtNameDescription;

    @BindView(R.id.et_size_description)
    EditText mEtSizeDescription;

    @BindView(R.id.et_price_description)
    EditText mEtPriceDescription;

    @BindView(R.id.tv_category_description)
    TextView mTvCategoryDescription;

    @BindView(R.id.et_detail_description)
    EditText mEtDetailDescription;

    @BindDimen(R.dimen.product_image_list_margin)
    int itemSpacingSize;

    private ProductImageListAdapter mProductImageListAdapter;

    public AddEditProductFragment() {
        // Required empty public constructor
    }

    public static AddEditProductFragment newInstance() {
        AddEditProductFragment fragment = new AddEditProductFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamProductId = getArguments().getInt(PARAM_PRODUCT_ID);
        }
        setRetainInstance(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_edit_product;
    }

    @Override
    public void initView(View rootView) {
        mRvImage.setHasFixedSize(true);
        mRvImage.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRvImage.addItemDecoration(new SpacingItemDecoration(itemSpacingSize));

        mProductImageListAdapter = new ProductImageListAdapter(getActivity());
        mProductImageListAdapter.setOnItemClickListener(new ProductImageListAdapter.OnItemClickListener() {
            @Override
            public void onClick(Object o) {
                // show dialog select camera or gallery
                PictureSelectionDialogFragment dialog = PictureSelectionDialogFragment.newInstance("사진 업로드");
                dialog.setTargetFragment(AddEditProductFragment.this, 0);
                dialog.show(getChildFragmentManager(), PictureSelectionDialogFragment.TAG);
            }

            @Override
            public void onRemove(int position) {
                mProductImageListAdapter.removeItem(position);
                setProductImageCount();
            }
        });
        mRvImage.setAdapter(mProductImageListAdapter);

        setProductImageCount();
    }

    @OnClick(R.id.iv_category_select)
    public void categorySelect() {
        startActivityForResult(new Intent(getActivity(), CategorySelectionActivity.class), REQUEST_CODE_CATEGORY_SELECTION);
        // Todo: 전환 애니메이션 구현
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @OnClick(R.id.btn_complete)
    public void productRegister() {
        ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstance("상품 등록하기", "해당 상품을 등록하시겠습니까?");
        dialog.setTargetFragment(AddEditProductFragment.this, 0);
        dialog.show(getChildFragmentManager(), ConfirmationDialogFragment.TAG);
    }

    /**
     * 이미지 갯수 표시
     */
    private void setProductImageCount() {
        mTvImageCount.setText(String.format(Locale.KOREA, "(%d/4)", mProductImageListAdapter.getItemCount() - 1));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CATEGORY_SELECTION:
                    mMainCategory = data.getStringExtra("mainCategory");
                    mSubCategory = data.getStringExtra("subCategory");
                    if (mSubCategory != null) {
                        mTvCategoryDescription.setText(mMainCategory + " > " + mSubCategory);
                    } else {
                        mTvCategoryDescription.setText(mMainCategory);
                        mSubCategory = "";
                    }
                    break;
                case Define.ALBUM_REQUEST_CODE:  // gallery result receive
                    ArrayList<String> path = data.getStringArrayListExtra(Define.INTENT_PATH);
                    mProductImageListAdapter.setData(path);
                    setProductImageCount();
                    break;
                case REQUEST_CODE_CAMERA_CAPTURE:
                    ProductImage productImage = new ProductImage();
                    productImage.setImagePath(getPathFromUri(data.getData()));
                    mProductImageListAdapter.addItem(mProductImageListAdapter.getItemCount(), productImage);
                    setProductImageCount();
                    break;
            }
        }
    }

    private String getPathFromUri(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        String path = "";
        if (cursor != null) {
            cursor.moveToNext();
            path = cursor.getString(cursor.getColumnIndex("_data"));
            cursor.close();
        }
        return path;
    }

    /**
     * 상품 정보 업로드
     */
    @Override
    public void onClickOk() {

        List<ProductImage> productImageList = mProductImageListAdapter.getProductImageList();
        if (productImageList.size() == 0) {
            SnackbarUtil.showMessage(getActivity(), getView(), "사진을 선택해주세요", "", null);
            return;
        }
        if (mEtBrandDescription.getText().toString().length() == 0) {
            SnackbarUtil.showMessage(getActivity(), getView(), "브랜드명을 입력해주세요", "", null);
            return;
        }
        if (mEtNameDescription.getText().toString().length() == 0) {
            SnackbarUtil.showMessage(getActivity(), getView(), "상품명을 입력해주세요", "", null);
            return;
        }
        if (mEtSizeDescription.getText().toString().length() == 0) {
            SnackbarUtil.showMessage(getActivity(), getView(), "사이즈를 입력해주세요", "", null);
            return;
        }
        if (mEtPriceDescription.getText().toString().length() == 0) {
            SnackbarUtil.showMessage(getActivity(), getView(), "가격을 입력해주세요", "", null);
            return;
        }
        if (mMainCategory == null || "".equals(mMainCategory)) {
            SnackbarUtil.showMessage(getActivity(), getView(), "카테고리를 선택해주세요", "", null);
            return;
        }
        if (mEtDetailDescription.getText().toString().length() == 0) {
            SnackbarUtil.showMessage(getActivity(), getView(), "상품 상세설명을 입력해주세요", "", null);
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("업로드 중...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Map<String, RequestBody> requestProduct = new HashMap<>();
        TranslationUtil translationUtil = TranslationUtil.getInstance();
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), mEtNameDescription.getText().toString());
        RequestBody brandName = RequestBody.create(MediaType.parse("multipart/form-data"), mEtBrandDescription.getText().toString());
        RequestBody size = RequestBody.create(MediaType.parse("multipart/form-data"), mEtSizeDescription.getText().toString());
        RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"), mEtPriceDescription.getText().toString());
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), mEtDetailDescription.getText().toString());
        RequestBody mainCategory = RequestBody.create(MediaType.parse("multipart/form-data"), translationUtil.koreanToEnglish(mMainCategory));
        RequestBody subCategory;
        if (!"".equals(mSubCategory)) {
            subCategory = RequestBody.create(MediaType.parse("multipart/form-data"), translationUtil.koreanToEnglish(mSubCategory));
        } else {
            subCategory = RequestBody.create(MediaType.parse("multipart/form-data"), mSubCategory);
        }

        requestProduct.put("name", name);
        requestProduct.put("brand_name", brandName);
        requestProduct.put("size", size);
        requestProduct.put("price", price);
        requestProduct.put("description", description);
        requestProduct.put("main_category", mainCategory);
        requestProduct.put("sub_category", subCategory);

        // 멀티파트로 만들기
        List<MultipartBody.Part> imageMultiparts = new ArrayList<>(productImageList.size());
        for (int i = 0; i < productImageList.size(); i++) {
            File file = new File(productImageList.get(i).getImagePath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
            imageMultiparts.add(part);
        }

        BackendHelper.getInstance().addProduct(requestProduct, imageMultiparts, new ResultCallback() {
            @Override
            public void onSuccess(Object o) {
                progressDialog.dismiss();
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                getActivity().finish();
            }
        });
    }

    @Override
    public void onClickCancel() {
        // do noting
    }

    @Override
    public void onClickGalleryStart() {
        FishBun.with(AddEditProductFragment.this)
                .setPickerCount(4)
                .startAlbum();
    }

    @Override
    public void onClickCameraStart() {
        if (mProductImageListAdapter.getProductImageList().size() == 4) {
            SnackbarUtil.showMessage(getActivity(), getView(), "사진은 4장까지만 선택할 수 있습니다.", "", null);
            return;
        }
        Intent intentCameraCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCameraCapture, REQUEST_CODE_CAMERA_CAPTURE);
    }
}
