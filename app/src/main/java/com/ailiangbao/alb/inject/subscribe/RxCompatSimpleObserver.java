package com.ailiangbao.alb.inject.subscribe;

import com.ailiangbao.provider.support.bridge.compat.RxCompatObserver;
import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;
import com.dangbei.xlog.XLog;

import io.reactivex.disposables.Disposable;

public class RxCompatSimpleObserver<T> extends RxCompatObserver<T> {

    private static final String TAG = RxCompatSimpleObserver.class.getSimpleName();

    public RxCompatSimpleObserver() {
    }

    @Override
    public void onSubscribeCompat(Disposable d) {

    }

    @Override
    public void onNextCompat(T o) {

    }

    @Override
    public void onErrorCompat(RxCompatException compatThrowable) {
        super.onErrorCompat(compatThrowable);
        XLog.e(TAG, compatThrowable);
//        ToastUtils.toastErrorOnDebug(compatThrowable.getMessage());
    }

    @Override
    public void onCompleteCompat() {
        super.onCompleteCompat();
    }

}
