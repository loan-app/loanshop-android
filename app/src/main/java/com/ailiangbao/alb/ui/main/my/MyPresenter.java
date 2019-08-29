package com.ailiangbao.alb.ui.main.my;

import com.ailiangbao.alb.ui.base.presenter.BasePresenter;
import com.ailiangbao.provider.bll.interactor.contract.UserInteractor;
import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;
import com.ailiangbao.provider.support.bridge.compat.RxCompatObserver;
import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;
import com.dangbei.mvparchitecture.viewer.Viewer;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MyPresenter extends BasePresenter implements MyContract.IMyPresenter {

    private final WeakReference<MyContract.IMyViewer> viewRef;

    @Inject
    UserInteractor userInteractor;

    @Inject
    public MyPresenter(Viewer viewer) {
        viewRef = new WeakReference<>((MyContract.IMyViewer) viewer);
    }

    @Override
    public void queryUserInfo() {
        userInteractor.requestCurrentUserInfo()
                .observeOn(RxCompat.getSchedulerOnMain())
                .subscribe(new RxCompatObserver<UserInfoEntity>() {
                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        attachDisposable(d);
                    }

                    @Override
                    public void onNextCompat(UserInfoEntity userInfoEntity) {
                        viewRef.get().onQueryUserInfo(userInfoEntity);
                    }

                    @Override
                    public void onErrorCompat(RxCompatException compatThrowable) {
                        super.onErrorCompat(compatThrowable);
                    }
                });
    }
}
