package kr.co.mash_up.a9tique.ui.main.home;

/**
 * Created by seokjunjeong on 2017. 6. 4..
 */

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;

    public HomePresenter(HomeContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
