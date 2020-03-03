package com.ailiangbao.provider.bll.interactor.contract;

import com.ailiangbao.provider.dal.net.http.entity.HomeEntity;

import io.reactivex.Observable;

public interface HomeInteractor {

    Observable<HomeEntity> requestData();
}
