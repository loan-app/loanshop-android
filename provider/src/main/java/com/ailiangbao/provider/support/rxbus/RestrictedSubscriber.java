package com.ailiangbao.provider.support.rxbus;


import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 02/08/2017.
 */
public abstract class RestrictedSubscriber<T> implements Subscriber<T> {
    private static final String TAG = RestrictedSubscriber.class.getSimpleName();
    private int onSubscribeRequest;
    private int onNextRequest;
    private Subscription subscription;


    public RestrictedSubscriber() {
        this(1, 1);
    }

    public RestrictedSubscriber(int request) {
        this(request, request);
    }

    public RestrictedSubscriber(int onSubscribeRequest, int onNextRequest) {
        this.onSubscribeRequest = onSubscribeRequest;
        this.onNextRequest = onNextRequest;
    }

    @Override
    public final void onSubscribe(Subscription s) {
        subscription = s;
        subscription.request(onSubscribeRequest);
        try {
            onSubscribeCompat(s);
        } catch (Throwable throwable) {
            Log.e(TAG, throwable.getMessage());
        }
    }

    @Override
    public final void onNext(T t) {
        try {
            onNextCompat(t);
        } catch (Throwable throwable) {
            Log.e(TAG, throwable.getMessage());
        }
        subscription.request(onNextRequest);
    }

    @Override
    public final void onComplete() {
        try {
            onCompleteCompat();
        } catch (Throwable throwable) {
            Log.e(TAG, throwable.getMessage());
        }
    }

    @Override
    public final void onError(Throwable t) {
        try {
            onErrorCompat(t);
        } catch (Throwable throwable) {
            Log.e(TAG, throwable.getMessage());
        }
    }

    public void onSubscribeCompat(Subscription subscription) {
        // ignore
    }

    public void onErrorCompat(Throwable throwable) {
        // ignore
    }

    public abstract void onNextCompat(T t);

    public void onCompleteCompat() {
        // ignore
    }
}
