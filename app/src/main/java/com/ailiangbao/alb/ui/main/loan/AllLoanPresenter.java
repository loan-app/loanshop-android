package com.ailiangbao.alb.ui.main.loan;

import com.ailiangbao.alb.ui.base.presenter.BasePresenter;
import com.ailiangbao.alb.util.ToastUtils;
import com.ailiangbao.provider.bll.interactor.contract.AllLoanInteractor;
import com.ailiangbao.provider.dal.net.http.entity.LoanEntity;
import com.ailiangbao.provider.dal.net.http.entity.all.ScreeningConditionEntity;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;
import com.ailiangbao.provider.support.bridge.compat.RxCompatObserver;
import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;
import com.dangbei.mvparchitecture.viewer.Viewer;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class AllLoanPresenter extends BasePresenter implements AllLoanContract.IAllLoanPresenter {
    private final WeakReference<AllLoanContract.IAllLoanViewer> viewRef;
    @Inject
    AllLoanInteractor interactor;

    @Inject
    public AllLoanPresenter(Viewer viewer) {
        viewRef = new WeakReference<>((AllLoanContract.IAllLoanViewer) viewer);
    }

    @Override
    public void requestList(int page, int limit, String label, String label2, String label3) {
        interactor.requestList(page, limit, label, label2, label3)
                .observeOn(RxCompat.getSchedulerOnMain())
                .subscribe(new RxCompatObserver<List<LoanEntity>>() {
                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        attachDisposable(d);
                    }

                    @Override
                    public void onNextCompat(List<LoanEntity> list) {
                        viewRef.get().onRequest(list, page != 1);
                    }

                    @Override
                    public void onErrorCompat(RxCompatException compatThrowable) {
                        super.onErrorCompat(compatThrowable);
                        if (page != 1) {
                            ToastUtils.showCustomToast("错误信息:" + compatThrowable.getMessage());
                        }
                    }
                });
    }

    @Override
    public void requestScreeningCondition() {
        interactor.requestScreeningCondition()
                .observeOn(RxCompat.getSchedulerOnMain())
                .subscribe(new RxCompatObserver<ScreeningConditionEntity>() {
                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        attachDisposable(d);
                    }

                    @Override
                    public void onNextCompat(ScreeningConditionEntity screeningConditionEntity) {
                        viewRef.get().onRequestScreeningCondition(screeningConditionEntity);
                    }

                    @Override
                    public void onErrorCompat(RxCompatException compatThrowable) {
                        super.onErrorCompat(compatThrowable);
                    }
                });
    }
}
