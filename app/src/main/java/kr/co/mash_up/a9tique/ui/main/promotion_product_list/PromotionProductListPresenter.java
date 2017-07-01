package kr.co.mash_up.a9tique.ui.main.promotion_product_list;

/**
 * Created by seokjunjeong on 2017. 6. 25..
 */

public class PromotionProductListPresenter implements PromotionProductListContract.Presenter {
    private PromotionProductListContract.View mView;

    public PromotionProductListPresenter(PromotionProductListContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
