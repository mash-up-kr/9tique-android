package kr.co.mash_up.a9tique._old.data.remote;


import android.support.annotation.Nullable;

public interface ResultCallback<T> {
    void onSuccess(@Nullable T t);
    void onFailure();
}