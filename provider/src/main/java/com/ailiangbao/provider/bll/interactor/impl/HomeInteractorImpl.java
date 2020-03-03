package com.ailiangbao.provider.bll.interactor.impl;

import com.ailiangbao.provider.bll.interactor.base.BaseInteractor;
import com.ailiangbao.provider.bll.interactor.contract.HomeInteractor;
import com.ailiangbao.provider.dal.dao.contract.HUserStatusDao;
import com.ailiangbao.provider.dal.net.http.entity.HomeEntity;
import com.ailiangbao.provider.dal.net.http.response.home.HomeResponse;
import com.ailiangbao.provider.dal.net.http.webapi.WebApi;
import com.ailiangbao.provider.dal.util.RequestCreator;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HomeInteractorImpl extends BaseInteractor implements HomeInteractor {
    @Inject
    RequestCreator requestCreator;

    @Inject
    HUserStatusDao userStatusDao;

    public HomeInteractorImpl() {
        getProviderUserInteractorComponent().inject(this);
    }

    @Override
    public Observable<HomeEntity> requestData() {
        return requestCreator.createRequest(WebApi.Home.DATA)
                .get()
                .observable(HomeResponse.class)
                .subscribeOn(RxCompat.getSchedulerOnNet())
                .compose(checkResponse(HomeResponse::getData));
    }

}
