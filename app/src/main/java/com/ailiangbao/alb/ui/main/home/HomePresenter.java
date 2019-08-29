package com.ailiangbao.alb.ui.main.home;

import com.ailiangbao.alb.ui.base.presenter.BasePresenter;
import com.ailiangbao.alb.ui.main.home.vm.HomeVM;
import com.ailiangbao.alb.util.ToastUtils;
import com.ailiangbao.provider.bll.interactor.contract.HomeInteractor;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;
import com.ailiangbao.provider.support.bridge.compat.RxCompatObserver;
import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;
import com.dangbei.mvparchitecture.viewer.Viewer;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class HomePresenter extends BasePresenter implements HomeContract.IHomePresenter {
    private final WeakReference<HomeContract.IHomeViewer> viewRef;

    @Inject
    HomeInteractor interactor;

    @Inject
    public HomePresenter(Viewer viewer) {
        viewRef = new WeakReference<>((HomeContract.IHomeViewer) viewer);
    }

    @Override
    public void requestData() {
        interactor.requestData()
                .map(HomeVM::new)
                .observeOn(RxCompat.getSchedulerOnMain())
                .compose(RxCompat.doOnCompletedOrError(() -> viewRef.get().cancelHomeLoadingView()))
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
                        ToastUtils.showCustomToast("错误信息:" + compatThrowable.getMessage());
                    }
                });
    }
}
