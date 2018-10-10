package kr.co.mash_up.a9tique.ui.promotions;

import kr.co.mash_up.a9tique.base.BasePresenter;
import kr.co.mash_up.a9tique.base.BaseView;

/**
 * Created by ethankim on 2017. 8. 22..
 */

public interface PromotionProductsContract {

    interface Presenter extends BasePresenter {
        // Todo: 정의

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
