package kr.co.mash_up.a9tique.ui.product_detail;

/**
 * Created by seokjunjeong on 2017. 7. 22..
 */

public class ProductDetailPresenter implements ProductDetailContract.Presenter {
    private ProductDetailContract.View mView;

    public ProductDetailPresenter(ProductDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadProduct(long productId) {

    }
}
