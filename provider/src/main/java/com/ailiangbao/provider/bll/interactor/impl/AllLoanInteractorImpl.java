package com.ailiangbao.provider.bll.interactor.impl;

import com.ailiangbao.provider.bll.interactor.base.BaseInteractor;
import com.ailiangbao.provider.bll.interactor.contract.AllLoanInteractor;
import com.ailiangbao.provider.dal.net.http.entity.LoanEntity;
import com.ailiangbao.provider.dal.net.http.entity.all.ScreeningConditionEntity;
import com.ailiangbao.provider.dal.net.http.response.all.AllLoanResponse;
import com.ailiangbao.provider.dal.net.http.response.cate.CateResponse;
import com.ailiangbao.provider.dal.net.http.webapi.WebApi;
import com.ailiangbao.provider.dal.util.RequestCreator;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AllLoanInteractorImpl extends BaseInteractor implements AllLoanInteractor {
    @Inject
    RequestCreator requestCreator;

    public AllLoanInteractorImpl() {
        getProviderUserInteractorComponent().inject(this);
    }

    @Override
    public Observable<List<LoanEntity>> requestList(int page, int limit, String label, String label2, String label3) {
        return requestCreator.createRequest(WebApi.AllLoan.LIST)
                .get()
                .addParameter("page", page)
                .addParameter("limit", limit)
                .addParameter("amount", label)
                .addParameter("tags", label2)
                .addParameter("badge", label3)
                .observable(CateResponse.class)
                .subscribeOn(RxCompat.getSchedulerOnNet())
                .compose(checkResponse(cateResponse -> cateResponse.getData().getList()));
    }

    @Override
    public Observable<ScreeningConditionEntity> requestScreeningCondition() {
        return requestCreator.createRequest(WebApi.Cate.SCREENING_CONDITION)
                .post()
                .observable(AllLoanResponse.class)
                .subscribeOn(RxCompat.getSchedulerOnNet())
                .compose(checkResponse(AllLoanResponse::getData));
    }
}
