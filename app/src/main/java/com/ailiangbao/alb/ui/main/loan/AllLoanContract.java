package com.ailiangbao.alb.ui.main.loan;

import com.ailiangbao.provider.dal.net.http.entity.LoanEntity;
import com.ailiangbao.provider.dal.net.http.entity.all.ScreeningConditionEntity;
import com.dangbei.mvparchitecture.presenter.Presenter;
import com.dangbei.mvparchitecture.viewer.Viewer;

import java.util.List;

public interface AllLoanContract {
    interface IAllLoanViewer extends Viewer {
        void onRequest(List<LoanEntity> list, boolean isLoadMore);

        void onRequestScreeningCondition(ScreeningConditionEntity screeningConditionEntity);
    }

    interface IAllLoanPresenter extends Presenter {
        void requestList(int page, int limit, String label, String label2, String label3);

        void requestScreeningCondition();
    }
}
