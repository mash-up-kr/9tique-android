package kr.co.mash_up.a9tique.ui.product_detail;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.mash_up.a9tique.R;
import kr.co.mash_up.a9tique.base.ui.BaseFragment;
import kr.co.mash_up.a9tique.databinding.ProductDetailFragmentBinding;
import kr.co.mash_up.a9tique.ui.CommentAdapter;
import kr.co.mash_up.a9tique.ui.ImageViewAdapter;
import kr.co.mash_up.a9tique.ui.OnItemClickListener;
import kr.co.mash_up.a9tique.ui.ProductListSemiAdapter;

/**
 * Created by seokjunjeong on 2017. 7. 22..
 */

public class ProductDetailFragment extends BaseFragment<ProductDetailFragmentBinding> implements ProductDetailContract.View {
    private ProductDetailContract.Presenter mPresenter;

    private RecyclerView mRvComment, mRvProductListSemi;
    private CommentAdapter mCommentAdapter;
    private ProductListSemiAdapter mProductListSemiAdapter;
    private ViewPager mVpProduct;
    private ImageViewAdapter mIvaProduct;

    private ArrayList<String> mProductsUrl;

    private OnItemClickListener mListener = new OnItemClickListener() {
        @Override
        public void onClick(int position) {

        }
    };

    public static ProductDetailFragment newInstance() {
        ProductDetailFragment fragment = new ProductDetailFragment();
        return fragment;
    }

    @Override
    public void setPresenter(ProductDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.product_detail_fragment;
    }

    @Override
    protected void initView() {
        mProductsUrl = new ArrayList<>();

        mVpProduct = mBinding.vpProducts;
        mIvaProduct = new ImageViewAdapter();
        mVpProduct.setAdapter(mIvaProduct);
        mVpProduct.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mRvComment = mBinding.rvComments;
        mCommentAdapter = new CommentAdapter();
        mRvComment.setAdapter(mCommentAdapter);

        mRvProductListSemi = mBinding.rvProductListSemi;
        mProductListSemiAdapter = new ProductListSemiAdapter(mListener);
        mRvProductListSemi.setAdapter(mProductListSemiAdapter);
    }

    @Override
    protected void newPresenter() {
        new ProductDetailPresenter(this);
    }

    @Override
    protected void startPresenter() {
        mPresenter.start();
    }

    @Override
    public void addProduct(final String imgUrl) {
        mProductsUrl.add(imgUrl);
        ImageView imageView = new ImageView(getContext());
        imageView.getLayoutParams().width = (int) getResources().getDimension(R.dimen.product_thumbnail_width);
        imageView.getLayoutParams().height = (int) getResources().getDimension(R.dimen.product_thumbnail_height);
        Glide.with(getContext()).load(imgUrl).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVpProduct.setCurrentItem(mProductsUrl.indexOf(imgUrl));
            }
        });
        mBinding.llThumbnailProductItem.addView(imageView);

    }
}
