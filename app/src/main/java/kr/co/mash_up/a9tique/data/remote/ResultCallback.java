package kr.co.mash_up.a9tique.data.remote;


public interface ResultCallback<T> {
    void onSuccess(T t);
    void onFailure();
}
