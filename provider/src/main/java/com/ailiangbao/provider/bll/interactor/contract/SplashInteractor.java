package com.ailiangbao.provider.bll.interactor.contract;

import com.ailiangbao.provider.dal.net.http.entity.HomeEntity;

import io.reactivex.Observable;

public interface SplashInteractor {
    Observable<HomeEntity> requestHomeData();

    void postOpenApp(String toJsonString);

}
