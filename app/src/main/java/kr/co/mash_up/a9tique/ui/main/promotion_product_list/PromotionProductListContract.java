package kr.co.mash_up.a9tique.ui.main.promotion_product_list;

import kr.co.mash_up.a9tique.base.BasePresenter;
import kr.co.mash_up.a9tique.base.BaseView;

/**
 * Created by seokjunjeong on 2017. 6. 25..
 */

public interface PromotionProductListContract {

    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView<Presenter>{
        void showTopCategoryList();
        void showSubCategoryList();
        void hideTopCategoryList();
        void hideSubCategoryList();
        boolean isShowTopCategoryList();
        boolean isShowSubCategoryList();
    }
}
