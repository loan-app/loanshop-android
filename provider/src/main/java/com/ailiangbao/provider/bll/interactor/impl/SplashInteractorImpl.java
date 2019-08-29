package com.ailiangbao.provider.bll.interactor.impl;

import android.annotation.SuppressLint;

import com.ailiangbao.provider.bll.application.ProviderApplication;
import com.ailiangbao.provider.bll.interactor.base.BaseInteractor;
import com.ailiangbao.provider.bll.interactor.contract.SplashInteractor;
import com.ailiangbao.provider.dal.dao.contract.HUserStatusDao;
import com.ailiangbao.provider.dal.net.http.entity.HomeEntity;
import com.ailiangbao.provider.dal.net.http.response.BaseHttpResponse;
import com.ailiangbao.provider.dal.net.http.response.splash.SplashResponse;
import com.ailiangbao.provider.dal.net.http.webapi.WebApi;
import com.ailiangbao.provider.dal.util.RequestCreator;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;
import com.ailiangbao.provider.support.bridge.compat.RxCompatObserver;
import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

import static com.wangjiegulu.dal.request.core.request.XRequest.JSON;

public class SplashInteractorImpl extends BaseInteractor implements SplashInteractor {
    @Inject
    RequestCreator requestCreator;

    @Inject
    HUserStatusDao userStatusDao;

    public SplashInteractorImpl() {
        getProviderUserInteractorComponent().inject(this);
    }

    @Override
    public Observable<HomeEntity> requestHomeData() {
        return requestCreator.createRequest(WebApi.Home.DATA)
                .get()
                .observable(SplashResponse.class)
                .subscribeOn(RxCompat.getSchedulerOnNet())
                .compose(checkResponse(SplashResponse::getData));
    }

    @SuppressLint("CheckResult")
    @Override
    public void postOpenApp(String jsonString) {
        if (ProviderApplication.getInstance().isBuildConfigDebug()) return;
        requestCreator.createRequest(WebApi.Post.OPEN_APP)
                .post()
                .addHeader("Content-Type", "application/json")
                .addRequestBody(RequestBody.create(JSON, jsonString))
                .observable(BaseHttpResponse.class)
                .subscribeOn(RxCompat.getSchedulerOnNet())
                .subscribe(new RxCompatObserver<BaseHttpResponse>() {
                    private Disposable d;

                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        this.d = d;
                    }

                    @Override
                    public void onNextCompat(BaseHttpResponse baseHttpResponse) {
                        d.dispose();
                    }

                    @Override
                    public void onErrorCompat(RxCompatException compatThrowable) {
                        super.onErrorCompat(compatThrowable);
                        d.dispose();
                    }
                });
    }
}
