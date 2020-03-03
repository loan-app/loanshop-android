package com.ailiangbao.provider.bll.interactor.contract;

import com.ailiangbao.provider.dal.net.http.entity.comb.CateLoanComb;

import io.reactivex.Observable;

public interface CateDetailInteractor {
    Observable<CateLoanComb> requestData(String cateId, int page, int limit);
}
