package com.ailiangbao.alb.ui.splash;

import com.ailiangbao.alb.ui.main.home.vm.HomeVM;
import com.dangbei.mvparchitecture.presenter.Presenter;
import com.dangbei.mvparchitecture.viewer.Viewer;

public class SplashContract {
    interface ISplashViewer extends Viewer {

        void onRequestData(HomeVM homeVM);

        void onLogin();
    }

    interface ISplashPresenter extends Presenter {

        void requestHomeData();

        void postOpenApp();

    }
}
