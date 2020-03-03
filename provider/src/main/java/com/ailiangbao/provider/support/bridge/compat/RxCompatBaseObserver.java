package com.ailiangbao.provider.support.bridge.compat;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;
import com.ailiangbao.provider.support.exception.ExceptionUtil;

import io.reactivex.disposables.Disposable;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 10/31/16.
 */
public abstract class RxCompatBaseObserver<T> {
    public static final String RXCOMPAT_OBSERVER_TAG = RxCompatBaseObserver.class.getSimpleName();

    public final void onSubscribe(@NonNull Disposable d) {
        try {
            onSubscribeCompat(d);
        } catch (Throwable throwable) {
            Log.e(RXCOMPAT_OBSERVER_TAG, throwable.getMessage());
        }
    }

    public final void onError(Throwable e) {
        RxCompatException rxCompatException;
        if (e instanceof RxCompatException) {
            rxCompatException = (RxCompatException) e;
        } else {
            if (ExceptionUtil.isNetworkError(e)) {
                rxCompatException = new RxCompatException(RxCompatException.CODE_NETWORK, RxCompatException.ERROR_NETWORK, e);
            } else {
                // default error
                rxCompatException = new RxCompatException(RxCompatException.CODE_DEFAULT, RxCompatException.JSON_STRING, e);
            }
        }
        try {
            onErrorCompat(rxCompatException);
        } catch (Throwable throwable) {
            Log.e(RXCOMPAT_OBSERVER_TAG, throwable.getMessage());
        }

//        XLog.e(RXCOMPAT_OBSERVER_TAG, rxCompatException.getMessage()); // 单元测试失败依据，勿删除！！
//
//        XLog.e(RXCOMPAT_OBSERVER_TAG, rxCompatException.getCause());
    }

    public abstract void onSubscribeCompat(Disposable d);

    public abstract void onErrorCompat(RxCompatException compatThrowable);

}
