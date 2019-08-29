package com.ailiangbao.provider.bll.interactor.impl;

import android.util.Log;

import com.ailiangbao.provider.bll.application.ProviderApplication;
import com.ailiangbao.provider.bll.interactor.base.BaseInteractor;
import com.ailiangbao.provider.bll.interactor.contract.LoginInteractor;
import com.ailiangbao.provider.dal.dao.contract.HUserDao;
import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;
import com.ailiangbao.provider.dal.net.http.response.BaseHttpResponse;
import com.ailiangbao.provider.dal.net.http.webapi.WebApi;
import com.ailiangbao.provider.dal.util.RequestCreator;
import com.ailiangbao.provider.dal.util.sign.DesHelper;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;
import com.ailiangbao.provider.support.bridge.compat.RxCompatObserver;
import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;
import com.dangbei.provider.BuildConfig;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

import static com.wangjiegulu.dal.request.core.request.XRequest.JSON;


public class LoginInteractorImpl extends BaseInteractor implements LoginInteractor {
    @Inject
    RequestCreator requestCreator;

    @Inject
    HUserDao userDao;

    public LoginInteractorImpl() {
        getProviderUserInteractorComponent().inject(this);
    }


    @Override
    public Observable<String> requestLoginCode(String telephone, String appName, String channel, String picCode, String uuid) {
        try {
            telephone = DesHelper.getInstance().encode(telephone, BuildConfig.APP_KEY, BuildConfig.APP_KEY.getBytes());
        } catch (Exception ignored) {
        }
        return requestCreator.createRequest(WebApi.Login.SEND)
                .post()
                .addHeader("Content-Type", "application/json")
                .addParameter("mobile", telephone)
                .addParameter("chName", appName)
                .addParameter("chCode", channel)
                .addParameter("imgCode", picCode)
                .addParameter("uuid", uuid)
                .observable(BaseHttpResponse.class)
                .subscribeOn(RxCompat.getSchedulerOnNet())
                .compose(checkResponse(baseHttpResponse -> {
                    if ("success".equals(baseHttpResponse.getMessage())) {
                        return "验证码已发送至您的手机";
                    }
                    return baseHttpResponse.getMessage();
                }));
    }

    @Override
    public Observable<UserInfoEntity> requestLogin(String jsonString) {
        return requestCreator.createRequest(WebApi.Login.LOGIN)
                .post()
                .addHeader("Content-Type", "application/json")
                .addRequestBody(RequestBody.create(JSON, jsonString))
//                .addFileParameter("dd", "dfdf", "application/json; charset=utf-8", jsonStrings.getBytes(Util.UTF_8))
                .observable(UserInfoEntity.class)
                .subscribeOn(RxCompat.getSchedulerOnNet())
                .compose(checkResponse(userInfoEntity -> userInfoEntity));
    }

    @Override
    public void saveUserInfo(UserInfoEntity userInfoEntity) {
//        PrefsHelper prefsHelper = ProviderApplication.getInstance().providerApplicationComponent.providerGlobalPrefsHelper();
//        prefsHelper.putLong(PrefsConstants.PREFS_GLOBAL_USER_ID, Long.valueOf(userInfoEntity.getUserId()));
//        prefsHelper.putString(PrefsConstants.PREFS_GLOBAL_USER_TOKEN, userInfoEntity.getSessionToken());
        try {
//            if (BuildConfig.DEBUG) {
//                user.setVipStatus(true);
//            }
            userDao.insertOrUpdate(userInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LoginInteractorImpl", "saveUserInfo:" + e);
        }
    }

    @Override
    public void postLoginNum(String jsonString) {
        if (ProviderApplication.getInstance().isBuildConfigDebug()) return;
        requestCreator.createRequest(WebApi.Post.LOGIN)
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
                        Log.d("postLoginNum", "上传完成--->:");
                    }

                    @Override
                    public void onErrorCompat(RxCompatException compatThrowable) {
                        super.onErrorCompat(compatThrowable);
                        d.dispose();
                        Log.d("postLoginNum", "上传失败--->:" + compatThrowable.getMessage());
                    }
                });
    }
}
