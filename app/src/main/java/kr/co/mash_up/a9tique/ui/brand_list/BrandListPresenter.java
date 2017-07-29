package kr.co.mash_up.a9tique.ui.brand_list;

/**
 * Created by seokjunjeong on 2017. 7. 23..
 */

public class BrandListPresenter implements BrandListContract.Presenter {
    private BrandListContract.View mView;

    public BrandListPresenter(BrandListContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
