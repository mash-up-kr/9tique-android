package kr.co.mash_up.a9tique.ui.promotions;

/**
 * Created by seokjunjeong on 2017. 6. 25..
 */

public class PromotionProductsPresenter implements PromotionProductsContract.Presenter {
    private PromotionProductsContract.View mView;

    public PromotionProductsPresenter(PromotionProductsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
