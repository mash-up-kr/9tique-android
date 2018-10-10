package kr.co.mash_up.a9tique.ui.product_detail;

/**
 * Created by seokjunjeong on 2017. 7. 22..
 */

public interface ProductDetailContract {
    interface Presenter extends BasePresenter{
        void loadProduct(long productId);
    }

    interface View extends BaseView<Presenter>{
        void addProduct(final String imgUrl);
    }
}
