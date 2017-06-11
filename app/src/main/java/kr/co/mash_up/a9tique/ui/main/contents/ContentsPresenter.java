package kr.co.mash_up.a9tique.ui.main.contents;

/**
 * Created by seokjunjeong on 2017. 6. 11..
 */

public class ContentsPresenter implements ContentsContract.Presenter {
    private ContentsContract.View mView;

    public ContentsPresenter(ContentsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void start() {

    }
}
