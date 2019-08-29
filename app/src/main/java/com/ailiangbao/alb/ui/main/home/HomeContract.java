package com.ailiangbao.alb.ui.main.home;

import com.ailiangbao.alb.ui.main.home.vm.HomeVM;
import com.dangbei.mvparchitecture.presenter.Presenter;
import com.dangbei.mvparchitecture.viewer.Viewer;

public interface HomeContract {
    interface IHomeViewer extends Viewer {
        void onRequestData(HomeVM homeVM);

        void cancelHomeLoadingView();

    }

    interface IHomePresenter extends Presenter {
        void requestData();
    }
}
