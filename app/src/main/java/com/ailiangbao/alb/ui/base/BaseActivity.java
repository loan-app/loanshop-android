package com.ailiangbao.alb.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ailiangbao.alb.HApplication;
import com.ailiangbao.alb.R;
import com.ailiangbao.alb.inject.user.UserComponent;
import com.ailiangbao.alb.inject.viewer.DaggerViewerComponent;
import com.ailiangbao.alb.inject.viewer.ViewerComponent;
import com.ailiangbao.alb.inject.viewer.ViewerModule;
import com.ailiangbao.alb.util.ActivityManageUtils;
import com.dangbei.mvparchitecture.contract.OnViewerDestroyListener;
import com.dangbei.mvparchitecture.contract.OnViewerLifecycleListener;
import com.dangbei.mvparchitecture.viewer.Viewer;
import com.ailiangbao.provider.support.usage.Func0;
import com.dangbei.xlog.XLog;
import com.gyf.barlibrary.ImmersionBar;

/**
 * activity基类
 */
public abstract class BaseActivity extends AppCompatActivity implements Viewer {

    private BaseViewerDelegate viewerDelegate;
    private boolean isStartLoading = true;

//    private ActivityLifecycleManager activityLifecycleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        ScreenAdapter.init(getWindowManager());
//        ResUtil.setIsInTouchMode(getWindow().getDecorView().isInTouchMode());

        ActivityManageUtils.addActivity(this);
        viewerDelegate = new BaseViewerDelegate(this);
        initImmersion();
//        if (null != activityLifecycleManager) {
//            activityLifecycleManager.dispatchOnActivityCreated(savedInstanceState);
//        }
        //友盟推送统计
//        PushAgent.getInstance(this).onAppStart();
//        getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        if (isStartLoading) {
            showLoadingDialog("加载中...");
        }
    }

    @Override
    public void finish() {
        ActivityManageUtils.removeActivity(this);
        super.finish();
    }

    /**
     * 初始化沉浸式
     */
    private void initImmersion() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .navigationBarColor(R.color.white)
//                .transparentStatusBar()
//                .statusBarColorTransform(R.color.black)
                //                .fullScreen(true)
                .keyboardEnable(true)
                .init();
    }

    protected ViewerComponent getViewerComponent() {
        //友盟上报了userComponent为空指针，所以在此进行处理
        UserComponent userComponent = HApplication.getInstance().userComponent;
        if (null == userComponent) {
            XLog.d("BaseActivity", "UserComponent为空");
            userComponent = HApplication.getInstance().initUserComponent();
        }
        return DaggerViewerComponent.builder()
                .userComponent(userComponent)
                .viewerModule(new ViewerModule(this))
                .build();
    }

//    public void register(ActivityLifecycleListener activityLifecycleListener) {
//        if (null == activityLifecycleManager) {
//            activityLifecycleManager = new ActivityLifecycleManager();
//        }
//        activityLifecycleManager.register(activityLifecycleListener);
//    }

//    public void unregister(ActivityLifecycleListener activityLifecycleListener) {
//        if (null != activityLifecycleManager) {
//            activityLifecycleManager.unregister(activityLifecycleListener);
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (null != activityLifecycleManager) {
//            activityLifecycleManager.dispatchOnActivityStarted();
//        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        if (null != activityLifecycleManager) {
//            activityLifecycleManager.dispatchOnActivitySaveInstanceState(outState);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
//        viewerDelegate.onViewerResume();
//        if (null != activityLifecycleManager) {
//            activityLifecycleManager.dispatchOnActivityResumed();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
//        viewerDelegate.onViewerPause();
//        if (null != activityLifecycleManager) {
//            activityLifecycleManager.dispatchOnActivityPaused();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (null != activityLifecycleManager) {
//            activityLifecycleManager.dispatchOnActivityStopped();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewerDelegate.onViewerDestroy();
//        if (null != activityLifecycleManager) {
//            activityLifecycleManager.dispatchOnActivityDestroyed();
//        }
    }

    public void onRequestError(Throwable throwable) {
//        if (throwable != null && BuildConfig.DEBUG) {
//            ToastUtils.toast(throwable.getMessage());
//            XLog.e("onRequestError", getClass().getSimpleName() + "->" + throwable.getMessage(), throwable);
//        }
    }

    @Override
    public Viewer bind(OnViewerLifecycleListener onViewerLifecycleListener) {
        return viewerDelegate.bind(onViewerLifecycleListener);
    }

    @Override
    public Viewer bind(OnViewerDestroyListener onViewerDestroyListener) {
        return viewerDelegate.bind(onViewerDestroyListener);
    }

    @Override
    public Context context() {
        return viewerDelegate.context();
    }

    @Override
    public void showToast(String message) {
        if (!isFinishing()) {
            viewerDelegate.showToast(message);
        }
    }

    @Override
    public void showToast(int resStringId) {
        if (!isFinishing()) {
            viewerDelegate.showToast(resStringId);
        }
    }

    @Override
    public void showLoadingDialog(String message) {
        viewerDelegate.showLoadingDialog(message);
    }

    @Override
    public void showLoadingDialog(int resStringId) {
        viewerDelegate.showLoadingDialog(resStringId);
    }

    @Override
    public void cancelLoadingDialog() {
        viewerDelegate.cancelLoadingDialog();
    }

//    protected ErrorView errorView;

    public void showError(boolean isNetworkError, final Func0 retryFun) {
//        errorView = new ErrorView(this);
//        errorView.showError((ViewGroup) getWindow().getDecorView().getRootView(), isNetworkError);
//        errorView.setErrorLayoutListener(v -> {
//            BaseActivity.this.dismissError();
//            retryFun.call();
//        });
    }

    public void dismissError() {
//        if (null != errorView) {
//            errorView.dismissError((ViewGroup) getWindow().getDecorView().getRootView());
//            errorView = null;
//        }
    }

    protected void cancelLoading(boolean isStartLoading) {
        this.isStartLoading = isStartLoading;
    }
}
