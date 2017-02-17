package kr.co.mash_up.a9tique.base.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import kr.co.mash_up.a9tique.common.eventbus.RxEventBus;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseFragment extends Fragment {

    private Unbinder mUnbinder;

    public BaseFragment() {
    }

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void initView(View rootView);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    private Subscription mBusSubscription;

    @Override
    public void onStart() {
        super.onStart();
        unsubscribeBus();
        mBusSubscription = RxEventBus.getInstance().getBusObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                            if (o == null) {
                                return;
                            }
                            handleEventFromBus(o);
                        },
                        this::handleError,
                        this::handleCompleted);
    }

    @Override
    public void onStop() {
        unsubscribeBus();
        super.onStop();
    }

    private void unsubscribeBus() {
        if (mBusSubscription != null && !mBusSubscription.isUnsubscribed()) {
            mBusSubscription.unsubscribe();
        }
    }

    protected void handleEventFromBus(Object event) {
    }

    protected void handleError(Throwable t) {
    }

    protected void handleCompleted() {
    }
}
