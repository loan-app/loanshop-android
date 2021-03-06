package com.ailiangbao.provider.support.bridge.compat;

import android.util.Log;

import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 10/31/16.
 */
public abstract class RxCompatSingleObserver<T> extends RxCompatBaseObserver implements SingleObserver<T> {

    @Override
    public final void onSuccess(@NonNull T t) {
        try {
            onSuccessCompat(t);
        } catch (Throwable throwable) {
            Log.e(RXCOMPAT_OBSERVER_TAG, throwable.getMessage());
        }
    }

    public abstract void onSubscribeCompat(Disposable d);

    public void onErrorCompat(RxCompatException compatThrowable) {
        // ignore
    }

    public abstract void onSuccessCompat(T t);

}
