package kr.co.mash_up.a9tique.ui.addeditproduct;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import kr.co.mash_up.a9tique.ui.ConfirmationDialogFragment;
import kr.co.mash_up.a9tique.ui.OrientationSpacingItemDecoration;
import kr.co.mash_up.a9tique.ui.addeditproduct.categorysleleciton.CategorySelectionActivity;
import kr.co.mash_up.a9tique.util.SnackbarUtil;
import kr.co.mash_up.a9tique.util.TranslationUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class AddEditProductFragment extends BaseFragment implements ConfirmationDialogFragment.Callback,
        PictureSelectionDialogFragment.Callback {

    public static final String TAG = AddEditProductFragment.class.getSimpleName();
    public static final int REQUEST_CODE_CATEGORY_SELECTION = 1000;
    public static final int REQUEST_CODE_CAMERA_CAPTURE = 1001;

    private Long mProductId;
    private Product mProduct;
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

    @BindView(R.id.btn_complete)
    Button mBtnComplete;

    @BindDimen(R.dimen.add_edit_product_image_list_item_margin)
    int itemSpacingSize;

    private ProductImageListAdapter mProductImageListAdapter;

    private ProgressDialog mProgressDialog;

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
            mProductId = getArguments().getLong(Constants.PRODUCT_ID);
            mProduct = getArguments().getParcelable(Constants.PRODUCT);
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
        mRvImage.addItemDecoration(new OrientationSpacingItemDecoration(itemSpacingSize, OrientationSpacingItemDecoration.Orientation.RIGHT));

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

        if (mProductId != null && mProductId > 0) {
            mProductImageListAdapter.setProductImages(mProduct.getProductImages());
            mEtBrandDescription.setText(mProduct.getBrandName());
            mEtNameDescription.setText(mProduct.getName());
            mEtSizeDescription.setText(mProduct.getSize());
            mEtPriceDescription.setText(String.valueOf(mProduct.getPrice()));
            setCategoryData(mProduct.getMainCategory(), mProduct.getSubCategory());  // 영어로 온다
            mEtDetailDescription.setText(mProduct.getDescription());
            mBtnComplete.setText("수정 완료");
        } else {
            mBtnComplete.setText("등록 완료");
        }

        setProductImageCount();

        mProgressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("업로드 중...");
        mProgressDialog.setCancelable(false);
    }

    @OnClick(R.id.cardView_category_select)
    public void categorySelect() {
        startActivityForResult(new Intent(getActivity(), CategorySelectionActivity.class), REQUEST_CODE_CATEGORY_SELECTION);
        // Todo: 전환 애니메이션 구현
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @OnClick(R.id.btn_complete)
    public void productRegister() {
        ConfirmationDialogFragment dialogProductRegister;

        if (mProductId != null && mProductId > 0) {
            dialogProductRegister = ConfirmationDialogFragment.newInstance("상품 수정하기", "해당 상품을 수정하시겠습니까?");
        } else {
            dialogProductRegister = ConfirmationDialogFragment.newInstance("상품 등록하기", "해당 상품을 등록하시겠습니까?");
        }
        dialogProductRegister.setTargetFragment(AddEditProductFragment.this, 0);
        dialogProductRegister.show(getChildFragmentManager(), ConfirmationDialogFragment.TAG);
    }

    /**
     * 이미지 갯수 표시
     */
    private void setProductImageCount() {
        mTvImageCount.setText(String.format(Locale.KOREA, "(%d/8)", mProductImageListAdapter.getItemCount() - 1));
    }

    /**
     * 카테고리 정보 셋팅
     *
     * @param mainCategory 메인 카테고리
     * @param subCategory  서브 카테고리
     */
    private void setCategoryData(String mainCategory, String subCategory) {
        TranslationUtil translationUtil = TranslationUtil.getInstance();
        if (!TextUtils.isEmpty(subCategory)) {
            mTvCategoryDescription.setText(translationUtil.englishToKorean(mainCategory) + " > " + translationUtil.englishToKorean(subCategory));
            mMainCategory = mainCategory;
            mSubCategory = subCategory;
        } else {
            mTvCategoryDescription.setText(translationUtil.englishToKorean(mainCategory));
            mMainCategory = mainCategory;
            mSubCategory = "";
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CATEGORY_SELECTION:
                    TranslationUtil translationUtil = TranslationUtil.getInstance();
                    setCategoryData(translationUtil.koreanToEnglish(data.getStringExtra("mainCategory")), translationUtil.koreanToEnglish(data.getStringExtra("subCategory")));  // 한국어로 오니까 영어로 변환
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

        List<ProductImage> productImageList = mProductImageListAdapter.getProductImages();
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

        List<MultipartBody.Part> imageMultiparts = makeMultiparts(productImageList);

        RequestProduct requestProduct = new RequestProduct();
        requestProduct.setName(mEtNameDescription.getText().toString());
        requestProduct.setBrandName(mEtBrandDescription.getText().toString());
        requestProduct.setSize(mEtSizeDescription.getText().toString());
        requestProduct.setPrice(Integer.valueOf(mEtPriceDescription.getText().toString()));
        requestProduct.setDescription(mEtDetailDescription.getText().toString());
        requestProduct.setMainCategory(mMainCategory);

        if (!"".equals(mSubCategory)) {
            requestProduct.setSubCategory(mSubCategory);
        } else {
            requestProduct.setSubCategory(mSubCategory);
        }

        if (mProductId == null) {
            productCreate(requestProduct, imageMultiparts);
        } else {
            productUpdate(requestProduct, imageMultiparts);
        }
    }

    @Override
    public void onClickCancel() {
        // do noting
    }

    @Override
    public void onClickGalleryStart() {
        FishBun.with(AddEditProductFragment.this)
                .setPickerCount(8)
                .startAlbum();
    }

    @Override
    public void onClickCameraStart() {
        if (mProductImageListAdapter.getProductImages().size() == 8) {
            SnackbarUtil.showMessage(getActivity(), getView(), "사진은 8장까지만 선택할 수 있습니다.", "", null);
            return;
        }
        Intent intentCameraCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCameraCapture, REQUEST_CODE_CAMERA_CAPTURE);
    }

    //Todo: remove callback hell - use Rx
    private void productCreate(RequestProduct requestProduct, List<MultipartBody.Part> imageMultiparts) {
        mProgressDialog.show();

        BackendHelper.getInstance().addProductImage(imageMultiparts, new ResultCallback<List<ProductImage>>() {
            @Override
            public void onSuccess(List<ProductImage> productImages) {
                requestProduct.setProductImages(productImages);

                BackendHelper.getInstance().addProduct(requestProduct, new ResultCallback() {
                    @Override
                    public void onSuccess(Object o) {
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }

                        getActivity().setResult(Activity.RESULT_OK);
                        getActivity().finish();
                    }

                    @Override
                    public void onFailure() {
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        getActivity().finish();
                    }
                });
            }

            @Override
            public void onFailure() {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                getActivity().finish();
            }
        });
    }

    //Todo: remove callback hell - use Rx
    private void productUpdate(RequestProduct requestProduct, List<MultipartBody.Part> imageMultiparts) {
        mProgressDialog.show();

        if (imageMultiparts.size() == 0) {  // 이미지 수정이 안되었을 경우
            requestProduct.setProductImages(mProductImageListAdapter.getProductImages());

            BackendHelper.getInstance().updateProduct(mProductId, requestProduct, new ResultCallback() {
                @Override
                public void onSuccess(Object o) {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }

                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }

                @Override
                public void onFailure() {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    getActivity().finish();
                }
            });
        } else {  // 이미지 수정 됬을 경우
            BackendHelper.getInstance().addProductImage(imageMultiparts, new ResultCallback<List<ProductImage>>() {
                @Override
                public void onSuccess(List<ProductImage> productImages) {
                    requestProduct.setProductImages(productImages);

                    BackendHelper.getInstance().updateProduct(mProductId, requestProduct, new ResultCallback() {
                        @Override
                        public void onSuccess(Object o) {
                            if (mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }

                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure() {
                            if (mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }
                            getActivity().finish();
                        }
                    });
                }

                @Override
                public void onFailure() {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    getActivity().finish();
                }
            });
        }
    }

    /**
     * multipart list로 만들기
     *
     * @param productImages multipart로 만들 이미지 파일
     * @return multipart list
     */
    private List<MultipartBody.Part> makeMultiparts(List<ProductImage> productImages) {
        List<MultipartBody.Part> imageMultiparts = new ArrayList<>();
        for (int i = 0; i < productImages.size(); i++) {
            if (productImages.get(i).getImagePath() != null && !"".equals(productImages.get(i).getImagePath())) {
                File file = new File(productImages.get(i).getImagePath());
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                imageMultiparts.add(part);
            }
        }

        return imageMultiparts;
    }
}