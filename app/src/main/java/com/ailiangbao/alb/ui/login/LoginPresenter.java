package com.ailiangbao.alb.ui.login;

import android.widget.Toast;

import com.ailiangbao.alb.HApplication;
import com.ailiangbao.alb.ui.base.presenter.BasePresenter;
import com.ailiangbao.alb.util.AppInfoUtil;
import com.ailiangbao.alb.util.StringUtils;
import com.ailiangbao.alb.util.ToastUtils;
import com.ailiangbao.provider.bll.interactor.contract.LoginInteractor;
import com.ailiangbao.provider.bll.interactor.contract.MainInteractor;
import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;
import com.ailiangbao.provider.support.bridge.compat.RxCompatObserver;
import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;
import com.dangbei.mvparchitecture.viewer.Viewer;
import com.dangbei.xlog.XLog;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class LoginPresenter extends BasePresenter implements LoginContract.ILoginPresenter {
    private static final String TAG = LoginPresenter.class.getSimpleName();
    private final WeakReference<LoginContract.ILoginViewer> viewRef;

    @Inject
    LoginInteractor interactor;

    @Inject
    MainInteractor mainInteractor;

    @Inject
    public LoginPresenter(Viewer viewer) {
        viewRef = new WeakReference<>((LoginContract.ILoginViewer) viewer);
    }

    @Override
    public void requestLoginCode(String telephone, String appName, String channel, String picCode, String uuid) {
        interactor.requestLoginCode(telephone, appName, channel, picCode, uuid)
                .observeOn(RxCompat.getSchedulerOnMain())
                .subscribe(new RxCompatObserver<String>() {
                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        attachDisposable(d);
                    }

                    @Override
                    public void onNextCompat(String message) {
                        ToastUtils.showCustomToast(message);
                    }

                    @Override
                    public void onErrorCompat(RxCompatException compatThrowable) {
                        super.onErrorCompat(compatThrowable);
                        viewRef.get().onRequestLoginCodeError();
                        XLog.d(TAG, compatThrowable.getMessage());
                        ToastUtils.showCustomToast("错误信息:" + compatThrowable.getMessage(), Toast.LENGTH_LONG);
                    }
                });
    }

    @Override
    public void requestLogin(String telephone, String code) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", telephone);
        map.put("vcode", code);
        interactor.requestLogin(StringUtils.toJsonString(map))
                .observeOn(RxCompat.getSchedulerOnMain())
                .compose(RxCompat.doOnCompletedOrError(() -> viewRef.get().cancelLoadingDialog()))
                .subscribe(new RxCompatObserver<UserInfoEntity>() {
                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        attachDisposable(d);
                    }

                    @Override
                    public void onNextCompat(UserInfoEntity userInfoEntity) {
                        userInfoEntity.setUserId(telephone);
                        HApplication.getInstance().switchUser(Long.valueOf(userInfoEntity.getUserId()), userInfoEntity);
                        map.clear();
                        map.put("mac", AppInfoUtil.getMac());
                        map.put("type", "2");
                        map.put("channel", AppInfoUtil.getChannel(HApplication.getInstance()));
                        String jsonString = StringUtils.toJsonString(map);
                        postLoginNum(jsonString);
                        viewRef.get().onRequestLogin();
                    }

                    @Override
                    public void onErrorCompat(RxCompatException compatThrowable) {
                        super.onErrorCompat(compatThrowable);
                        viewRef.get().onRequestLoginError();
                        XLog.d(TAG, compatThrowable.getMessage());
                        ToastUtils.showCustomToast("错误信息:" + compatThrowable.getMessage(), Toast.LENGTH_LONG);
                    }
                });
    }

    @Override
    public void checkUpdate(String versionName, String channel) {
        mainInteractor.checkUpdate(versionName, channel)
                .observeOn(RxCompat.getSchedulerOnMain())
                .subscribe(new RxCompatObserver<String>() {
                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        attachDisposable(d);
                    }

                    @Override
                    public void onNextCompat(String s) {
                        viewRef.get().onCheckUpdate(s);
                    }

                    @Override
                    public void onErrorCompat(RxCompatException compatThrowable) {
                        super.onErrorCompat(compatThrowable);
                    }
                });
    }

    private void postLoginNum(String jsonString) {
        interactor.postLoginNum(jsonString);
    }

}
