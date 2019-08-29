package com.ailiangbao.alb.ui.cate;

import com.ailiangbao.provider.dal.net.http.entity.comb.CateLoanComb;
import com.dangbei.mvparchitecture.presenter.Presenter;
import com.dangbei.mvparchitecture.viewer.Viewer;

public interface CateDetailContract {
    interface ICateDetailViewer extends Viewer {
        void onRequestData(CateLoanComb cateLoanComb, boolean isLoadMore);
    }

    interface ICateDetailPresenter extends Presenter {
        void requestData(String cateId, int page, int limit);
    }
}
