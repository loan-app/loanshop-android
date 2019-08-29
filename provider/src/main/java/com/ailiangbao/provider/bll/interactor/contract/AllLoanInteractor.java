package com.ailiangbao.provider.bll.interactor.contract;

import com.ailiangbao.provider.dal.net.http.entity.LoanEntity;
import com.ailiangbao.provider.dal.net.http.entity.all.ScreeningConditionEntity;

import java.util.List;

import io.reactivex.Observable;

public interface AllLoanInteractor {
    Observable<List<LoanEntity>> requestList(int page, int limit, String label, String label2, String label3);

    Observable<ScreeningConditionEntity> requestScreeningCondition();
}
