package com.ailiangbao.provider.bll.interactor.contract;

import com.ailiangbao.provider.dal.net.http.entity.ContactsEntity;

import java.util.List;

import io.reactivex.Observable;

public interface MainInteractor {

    Observable<String> requestData();

    Observable<Boolean> postContractList(List<ContactsEntity> list);

    void postProduct(String jsonString);

    void postAppChannel(String toJsonString);

    void postAppDevice(String toJsonString);

    void postUV(String jsonString);

    Observable<String> checkUpdate(String versionName, String channel);
}
