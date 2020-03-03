package com.ailiangbao.alb.ui.main;

import com.dangbei.mvparchitecture.presenter.Presenter;
import com.dangbei.mvparchitecture.viewer.Viewer;
import com.ailiangbao.provider.dal.net.http.entity.ContactsEntity;

import java.util.List;

public interface MainContract {
    interface IMainViewer extends Viewer {
        void onRequestData(String message);

        void onCheckUpdate(String s);
    }

    interface IMainPresenter extends Presenter {

        void requestData();

        void checkUpdate(String versionName, String channel);

        void postContractList(List<ContactsEntity> contactList);

        void postAppChannel();

        void postAppDevice();

        void postUV();
    }
}
