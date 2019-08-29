package com.ailiangbao.provider.bll.interactor.base;

import com.ailiangbao.provider.dal.net.http.response.BaseHttpResponse;
import com.ailiangbao.provider.bll.application.ProviderApplication;
import com.ailiangbao.provider.bll.inject.application.ProviderApplicationComponent;
import com.ailiangbao.provider.bll.inject.interactor.ProviderUserInteractorComponent;
import com.dangbei.xfunc.func.XFunc0E;
import com.dangbei.xfunc.func.XFunc0ER;
import com.dangbei.xfunc.func.XFunc1R;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/7/16.
 */
public class BaseInteractor {
    public ProviderApplicationComponent getProviderApplicationComponent() {
        return ProviderApplication.getInstance().providerApplicationComponent;
    }

    public ProviderUserInteractorComponent getProviderUserInteractorComponent() {
        return ProviderApplication.getInstance().providerUserInteractorComponent;
    }

    public <T> Observable<T> toObservable(final XFunc0ER<T> func) {
        return Observable.defer(() -> {
            try {
                return Observable.just(func.call());
            } catch (Throwable throwable) {
                return Observable.error(throwable);
            }
        }).timeout(30, TimeUnit.SECONDS);
    }

    public <T> Observable<T> fromObservable(final XFunc0ER<List<T>> func) {
        return Observable.defer(() -> {
            try {
                return Observable.fromIterable(func.call());
            } catch (Throwable throwable) {
                return Observable.error(throwable);
            }
        });
    }

    public Observable<Void> emptyObservable(final XFunc0E func) {
        return Observable.defer(() -> {
            try {
                func.call();
                return Observable.empty();
            } catch (Throwable throwable) {
                return Observable.error(throwable);
            }
        });
    }

    /**
     * 默认检查response是否成功
     */
    public static <T extends BaseHttpResponse> ObservableTransformer<T, T> checkResponseDefault() {
        return observable -> observable.map(t -> {
            if (t.isBizSucceed()) {
                return t;
            }
            throw t.toCompatException();
        });
    }

    /**
     * 检查response，并转换数据
     */
    public static <T extends BaseHttpResponse, R> ObservableTransformer<T, R> checkResponse(XFunc1R<T, R> invokeFunc) {
        return observable -> observable.map((Function<T, R>) t -> {
            if (t.isBizSucceed()) {
                return invokeFunc.call(t);
            }
            throw t.toCompatException();
        });
    }
}
