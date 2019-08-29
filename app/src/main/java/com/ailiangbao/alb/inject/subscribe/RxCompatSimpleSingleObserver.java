package com.ailiangbao.alb.inject.subscribe;

import com.ailiangbao.provider.support.bridge.compat.RxCompatBaseObserver;
import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;
import com.dangbei.xlog.XLog;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public abstract class RxCompatSimpleSingleObserver<T> extends RxCompatBaseObserver implements SingleObserver<T> {

    private static final String TAG = RxCompatSimpleSingleObserver.class.getSimpleName();

    public RxCompatSimpleSingleObserver(){
    }

    @Override
    public final void onSuccess(@NonNull T t) {
        try {
            onSuccessCompat(t);
        } catch (Throwable throwable) {
            XLog.e(RXCOMPAT_OBSERVER_TAG, throwable);
        }
    }

    @Override
    public void onSubscribeCompat(Disposable d) {
    }

    @Override
    public void onErrorCompat(RxCompatException compatThrowable) {
        XLog.e(TAG, compatThrowable);
//        ToastUtils.toastErrorOnDebug(compatThrowable.getMessage());
    }

    public abstract void onSuccessCompat(T t);
}
