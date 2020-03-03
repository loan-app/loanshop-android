package com.ailiangbao.provider.bll.interactor.impl;

import android.annotation.SuppressLint;

import com.ailiangbao.provider.bll.application.ProviderApplication;
import com.ailiangbao.provider.bll.interactor.base.BaseInteractor;
import com.ailiangbao.provider.bll.interactor.contract.MainInteractor;
import com.ailiangbao.provider.dal.net.http.entity.ContactsEntity;
import com.ailiangbao.provider.dal.net.http.response.BaseHttpResponse;
import com.ailiangbao.provider.dal.net.http.response.home.UpdateResponse;
import com.ailiangbao.provider.dal.net.http.webapi.WebApi;
import com.ailiangbao.provider.dal.util.GsonUtil;
import com.ailiangbao.provider.dal.util.RequestCreator;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;
import com.ailiangbao.provider.support.bridge.compat.RxCompatObserver;
import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

import static com.wangjiegulu.dal.request.core.request.XRequest.JSON;

public class MainInteractorImpl extends BaseInteractor implements MainInteractor {
    @Inject
    RequestCreator requestCreator;

    public MainInteractorImpl() {
        getProviderUserInteractorComponent().inject(this);
    }

    @Override
    public Observable<String> requestData() {
        return toObservable(() -> "数据层测试")
                .subscribeOn(RxCompat.getSchedulerOnNet());
    }

    @Override
    public Observable<Boolean> postContractList(List<ContactsEntity> list) {
        String json = GsonUtil.getGson().toJson(list);
        return requestCreator.createRequest(WebApi.CONTRACT)
                .post()
                .addParameter("content", json)
                .addParameter("json", "1")
                .observable(BaseHttpResponse.class)
                .subscribeOn(RxCompat.getSchedulerOnNet())
                .compose(checkResponse(BaseHttpResponse::isSuccess));
    }

    @SuppressLint("CheckResult")
    @Override
    public void postProduct(String jsonString) {
        if (ProviderApplication.getInstance().isBuildConfigDebug()) return;
        requestCreator.createRequest(WebApi.Post.PRODUCT)
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

    @SuppressLint("CheckResult")
    @Override
    public void postAppChannel(String jsonString) {
        if (ProviderApplication.getInstance().isBuildConfigDebug()) return;
        requestCreator.createRequest(WebApi.Post.APP_CHANNEL)
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

    @SuppressLint("CheckResult")
    @Override
    public void postAppDevice(String jsonString) {
        if (ProviderApplication.getInstance().isBuildConfigDebug()) return;
        requestCreator.createRequest(WebApi.Post.APP_DEVICE)
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

    @SuppressLint("CheckResult")
    @Override
    public void postUV(String jsonString) {
        if (ProviderApplication.getInstance().isBuildConfigDebug()) return;
        requestCreator.createRequest(WebApi.Post.UV)
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

    @Override
    public Observable<String> checkUpdate(String versionName, String channel) {
        return requestCreator.createRequest(WebApi.Home.UPDATE)
                .get()
                .addParameter("type", "2")
                .addParameter("currentVersion", versionName)
                .addParameter("chCode", channel)
                .observable(UpdateResponse.class)
                .subscribeOn(RxCompat.getSchedulerOnNet())
                .compose(checkResponse(updateResponse -> updateResponse.getData().getLink()));
    }
}
