package kr.co.mash_up.a9tique.ui.main.shop;

/**
 * Created by seokjunjeong on 2017. 6. 22..
 */

public interface ShopContract {
    interface Presenter extends BasePresenter {
    }

    interface View extends BaseView<Presenter> {
        void showTopCategoryList();
        void showSubCategoryList();
        void hideTopCategoryList();
        void hideSubCategoryList();
        boolean isShowTopCategoryList();
        boolean isShowSubCategoryList();
    }
}
