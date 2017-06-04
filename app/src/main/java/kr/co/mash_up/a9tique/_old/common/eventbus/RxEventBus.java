package kr.co.mash_up.a9tique._old.common.eventbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;


public class RxEventBus {

    private static RxEventBus sInstance;
    private final Subject<Object, Object> mBus = new SerializedSubject<>(PublishSubject.create());

    public static RxEventBus getInstance() {
        if (sInstance == null) {
            sInstance = new RxEventBus();
        }
        return sInstance;
    }

    private RxEventBus() {
    }

    public void post(Object o) {
        mBus.onNext(o);
    }

    public Observable<Object> getBusObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }
}