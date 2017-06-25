package kr.co.mash_up.a9tique.ui.main.shop;

/**
 * Created by seokjunjeong on 2017. 6. 24..
 */

public class ShopPresenter implements ShopContract.Presenter {
    private ShopContract.View mView;

    public ShopPresenter(ShopContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
