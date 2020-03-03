package com.ailiangbao.alb.ui.login;

import com.dangbei.mvparchitecture.presenter.Presenter;
import com.dangbei.mvparchitecture.viewer.Viewer;

public interface LoginContract {
    interface ILoginViewer extends Viewer {

        void onRequestLogin();

        void onRequestLoginError();

        void onCheckUpdate(String url);

        void onRequestLoginCodeError();
    }

    interface ILoginPresenter extends Presenter {

        void requestLoginCode(String telephone, String appName, String channel, String picCode, String uuid);

        void requestLogin(String telephone, String code);

        void checkUpdate(String versionName, String channel);
    }
}
