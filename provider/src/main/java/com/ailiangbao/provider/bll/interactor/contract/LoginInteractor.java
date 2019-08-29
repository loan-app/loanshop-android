package com.ailiangbao.provider.bll.interactor.contract;


import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;

import io.reactivex.Observable;

public interface LoginInteractor {
    Observable<String> requestLoginCode(String telephone, String appName, String channel, String picCode, String uuid);

    Observable<UserInfoEntity> requestLogin(String jsonString);

    void saveUserInfo(UserInfoEntity userInfoEntity);

    void postLoginNum(String jsonString);
}
