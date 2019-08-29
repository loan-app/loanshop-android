package com.ailiangbao.alb.ui.splash;

import com.ailiangbao.alb.HApplication;
import com.ailiangbao.alb.ui.base.presenter.BasePresenter;
import com.ailiangbao.alb.ui.main.home.vm.HomeVM;
import com.ailiangbao.alb.util.AppInfoUtil;
import com.ailiangbao.alb.util.StringUtils;
import com.ailiangbao.provider.bll.interactor.contract.SplashInteractor;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;
import com.ailiangbao.provider.support.bridge.compat.RxCompatObserver;
import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;
import com.dangbei.mvparchitecture.viewer.Viewer;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

import static com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException.ERROR_NO_LOGIN;

public class SplashPresenter extends BasePresenter implements SplashContract.ISplashPresenter {

    private final WeakReference<SplashContract.ISplashViewer> viewRef;

    @Inject
    SplashInteractor interactor;

    @Inject
    public SplashPresenter(Viewer viewer) {
        viewRef = new WeakReference<>((SplashContract.ISplashViewer) viewer);
    }

    @Override
    public void requestHomeData() {
        interactor.requestHomeData()
                .map(HomeVM::new)
                .observeOn(RxCompat.getSchedulerOnMain())
                .compose(RxCompat.doOnCompletedOrError(() -> viewRef.get().cancelLoadingDialog()))
                .subscribe(new RxCompatObserver<HomeVM>() {
                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        attachDisposable(d);
                    }

                    @Override
                    public void onNextCompat(HomeVM homeVM) {
                        viewRef.get().onRequestData(homeVM);
                    }

                    @Override
                    public void onErrorCompat(RxCompatException compatThrowable) {
                        super.onErrorCompat(compatThrowable);
                        if (compatThrowable.getCode() == ERROR_NO_LOGIN) {
                            viewRef.get().onLogin();
                        }
                    }
                });
    }

    @Override
    public void postOpenApp() {
        HashMap<String, String> map = new HashMap<>();
        map.put("channel", AppInfoUtil.getChannel(HApplication.getInstance()));
        interactor.postOpenApp(StringUtils.toJsonString(map));
    }

}
