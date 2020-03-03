package com.ailiangbao.provider.bll.interactor.impl;

import com.ailiangbao.provider.bll.interactor.base.BaseInteractor;
import com.ailiangbao.provider.bll.interactor.contract.CateDetailInteractor;
import com.ailiangbao.provider.dal.net.http.entity.cate.CateLoanEntity;
import com.ailiangbao.provider.dal.net.http.entity.comb.CateLoanComb;
import com.ailiangbao.provider.dal.net.http.response.cate.CateResponse;
import com.ailiangbao.provider.dal.net.http.webapi.WebApi;
import com.ailiangbao.provider.dal.util.RequestCreator;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CateDetailInteractorImpl extends BaseInteractor implements CateDetailInteractor {
    @Inject
    RequestCreator requestCreator;

    public CateDetailInteractorImpl() {
        getProviderUserInteractorComponent().inject(this);
    }

    @Override
    public Observable<CateLoanComb> requestData(String cateId, int page, int limit) {
        return requestCreator.createRequest(WebApi.Cate.LIST)
                .get()
                .addParameter("type", cateId)
                .addParameter("page", page)
                .addParameter("limit", limit)
                .observable(CateResponse.class)
                .subscribeOn(RxCompat.getSchedulerOnNet())
                .compose(checkResponse(cateResponse -> {
                    CateLoanEntity data = cateResponse.getData();
                    CateLoanComb cateLoanComb = new CateLoanComb();
                    cateLoanComb.setBanner(data.getTypeBanner());
                    cateLoanComb.setList(data.getList());
                    return cateLoanComb;
                }));
    }
}
