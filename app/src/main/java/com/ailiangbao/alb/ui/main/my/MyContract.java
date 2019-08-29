package com.ailiangbao.alb.ui.main.my;

import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;
import com.dangbei.mvparchitecture.presenter.Presenter;
import com.dangbei.mvparchitecture.viewer.Viewer;

public interface MyContract {
    interface IMyViewer extends Viewer {
        void onQueryUserInfo(UserInfoEntity userInfoEntity);

    }

    interface IMyPresenter extends Presenter {
        void queryUserInfo();
    }
}
