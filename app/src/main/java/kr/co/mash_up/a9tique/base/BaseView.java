package kr.co.mash_up.a9tique.base;

/**
 * 사용자 입력, 화면 갱신 -> Activity, Fragment
 * View를 담당하는 interface
 * View 갱신 로직만 담고 있어야 한다.
 *
 * @param <T> 데이터를 다룰 Presenter 객체]
 */
public interface BaseView<T> {
    void setPresenter(T presenter);
}
