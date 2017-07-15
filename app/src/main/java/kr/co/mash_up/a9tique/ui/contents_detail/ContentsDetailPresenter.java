package kr.co.mash_up.a9tique.ui.contents_detail;

/**
 * Created by seokjunjeong on 2017. 7. 7..
 */

public class ContentsDetailPresenter implements ContentsDetailContract.Presenter {
    private ContentsDetailContract.View mView;

    public ContentsDetailPresenter(ContentsDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
