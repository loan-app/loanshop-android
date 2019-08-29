package com.ailiangbao.alb.ui.cate;

import com.ailiangbao.alb.ui.base.presenter.BasePresenter;
import com.ailiangbao.alb.util.ToastUtils;
import com.ailiangbao.provider.bll.interactor.contract.CateDetailInteractor;
import com.ailiangbao.provider.dal.net.http.entity.comb.CateLoanComb;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;
import com.ailiangbao.provider.support.bridge.compat.RxCompatObserver;
import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;
import com.dangbei.mvparchitecture.viewer.Viewer;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class CateDetailPresenter extends BasePresenter implements CateDetailContract.ICateDetailPresenter {
    private final WeakReference<CateDetailContract.ICateDetailViewer> viewRef;
    @Inject
    CateDetailInteractor interactor;

    @Inject
    public CateDetailPresenter(Viewer viewer) {
        viewRef = new WeakReference<>((CateDetailContract.ICateDetailViewer) viewer);
    }

    @Override
    public void requestData(String cateId, int page, int limit) {
        interactor.requestData(cateId, page, limit)
                .observeOn(RxCompat.getSchedulerOnMain())
                .compose(RxCompat.doOnCompletedOrError(() -> viewRef.get().cancelLoadingDialog()))
                .subscribe(new RxCompatObserver<CateLoanComb>() {
                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        attachDisposable(d);
                    }

                    @Override
                    public void onNextCompat(CateLoanComb cateLoanComb) {
                        viewRef.get().onRequestData(cateLoanComb, page != 1);
                    }

                    @Override
                    public void onErrorCompat(RxCompatException compatThrowable) {
                        super.onErrorCompat(compatThrowable);
                        ToastUtils.showCustomToast("错误信息:" + compatThrowable.getMessage());
                    }
                });
    }
}
