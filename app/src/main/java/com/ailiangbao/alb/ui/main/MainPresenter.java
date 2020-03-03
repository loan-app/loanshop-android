package com.ailiangbao.alb.ui.main;

import com.ailiangbao.alb.HApplication;
import com.ailiangbao.alb.ui.base.presenter.BasePresenter;
import com.ailiangbao.alb.util.AppInfoUtil;
import com.ailiangbao.alb.util.StringUtils;
import com.ailiangbao.provider.bll.interactor.contract.SplashInteractor;
import com.dangbei.mvparchitecture.viewer.Viewer;
import com.ailiangbao.provider.bll.interactor.contract.MainInteractor;
import com.ailiangbao.provider.dal.net.http.entity.ContactsEntity;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;
import com.ailiangbao.provider.support.bridge.compat.RxCompatObserver;
import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;
import com.dangbei.xlog.XLog;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MainPresenter extends BasePresenter implements MainContract.IMainPresenter {
    private final WeakReference<MainContract.IMainViewer> reference;

    @Inject
    MainInteractor interactor;

    @Inject
    SplashInteractor splashInteractor;

    @Inject
    public MainPresenter(Viewer viewer) {
        reference = new WeakReference<>((MainContract.IMainViewer) viewer);
    }

    @Override
    public void requestData() {
        interactor.requestData()
                .observeOn(RxCompat.getSchedulerOnMain())
                .subscribe(new RxCompatObserver<String>() {
                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        attachDisposable(d);
                    }

                    @Override
                    public void onNextCompat(String s) {
                        reference.get().onRequestData(s);
                    }

                    @Override
                    public void onErrorCompat(RxCompatException compatThrowable) {
                        super.onErrorCompat(compatThrowable);
                    }
                });
    }

    @Override
    public void checkUpdate(String versionName, String channel) {
        interactor.checkUpdate(versionName, channel)
                .observeOn(RxCompat.getSchedulerOnMain())
                .subscribe(new RxCompatObserver<String>() {
                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        attachDisposable(d);
                    }

                    @Override
                    public void onNextCompat(String s) {
                        reference.get().onCheckUpdate(s);
                    }

                    @Override
                    public void onErrorCompat(RxCompatException compatThrowable) {
                        super.onErrorCompat(compatThrowable);
                        XLog.d("checkUpdate", compatThrowable.getMessage());
                    }
                });
    }

    @Override
    public void postContractList(List<ContactsEntity> list) {
        interactor.postContractList(list)
                .compose(RxCompat.observableOnMain())
                .subscribe(new RxCompatObserver<Boolean>() {
                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        attachDisposable(d);
                    }

                    @Override
                    public void onNextCompat(Boolean aBoolean) {
                        if (!aBoolean) {
                            XLog.d("MainPresenter", "onNextCompat--->" + "上传失败");
                        }
                    }

                    @Override
                    public void onErrorCompat(RxCompatException compatThrowable) {
                        super.onErrorCompat(compatThrowable);
                        XLog.d("MainPresenter", "onErrorCompat--->" + "上传失败");
                    }
                });
    }

    @Override
    public void postAppChannel() {
        HashMap<String, String> map = new HashMap<>();
        map.put("channel", AppInfoUtil.getChannel(HApplication.getInstance()));
        map.put("uuid", AppInfoUtil.getMac());
        interactor.postAppChannel(StringUtils.toJsonString(map));
    }

    @Override
    public void postAppDevice() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uuid", AppInfoUtil.getMac());
//        map.put("channel", AppInfoUtil.getChannel(HApplication.getInstance()));
        interactor.postAppDevice(StringUtils.toJsonString(map));
    }

    @Override
    public void postUV() {
        HashMap<String, String> map = new HashMap<>();
        map.put("channel", AppInfoUtil.getChannel(HApplication.getInstance()));
        interactor.postUV(StringUtils.toJsonString(map));
    }

}
