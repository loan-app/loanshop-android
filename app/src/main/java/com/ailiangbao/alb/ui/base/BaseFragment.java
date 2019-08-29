package com.ailiangbao.alb.ui.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ailiangbao.alb.HApplication;
import com.ailiangbao.alb.inject.viewer.DaggerViewerComponent;
import com.ailiangbao.alb.inject.viewer.ViewerComponent;
import com.ailiangbao.alb.inject.viewer.ViewerModule;
import com.dangbei.mvparchitecture.contract.OnViewerDestroyListener;
import com.dangbei.mvparchitecture.contract.OnViewerLifecycleListener;
import com.dangbei.mvparchitecture.viewer.Viewer;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


/**
 * Created by yhzhao
 * 2017/7/28
 */

public abstract class BaseFragment extends Fragment implements Viewer {
    private boolean isPrepared = false;
    /**
     * 第一次onResume中的调用onUserVisible避免操作与onFirstUserVisible操作重复
     */
    private boolean isFirstResume = true;

    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;

    private BaseViewerDelegate viewerDelegate;
    private LoadingView loadingView;
    private BaseFragment fragment;
    //    private CSNoDataView csNoDataView;
//    private Flowable<MainKeyEvent> mainKeyEventFlowable;
    private boolean isVisibleToUser = true;
    private boolean isPause = false;
    public View view;
    private boolean isLoading = false;

//    public BaseFragment() {
//        viewerDelegate = new BaseViewerDelegate(getActivity());
//    }

    public boolean isLoading() {
        return isLoading;
    }

    public void startLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Context context = getContext();
//        if (context instanceof Activity) {
//            ResUtil.setIsInTouchMode(((Activity) context).getWindow().getDecorView().isInTouchMode());
//        }
        viewerDelegate = new BaseViewerDelegate(getActivity());
        initLoadingAndNoDataTip();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutResources(), container, false);
        }
        return view;
    }

    protected abstract int getLayoutResources();

    private void  initLoadingAndNoDataTip() {
//        if (isLoading) {
//            loadingView = new LoadingView(getContext());
//        }
//        loadingView.setLayoutParams(100, 100, 380);
//        loadingView.setText("数据加载中...");
//        loadingView.setTextSize(24);
//        loadingView.setTextColor(R.color.white);
//        LNoDataView lNoDataView = new LNoDataView(getContext());
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.setMargins();
//        lNoDataView.setLayoutParams(params);
//        getLoadingForViewGroup().addView(lNoDataView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(null);
        startLoadingView();
        init();
        initPrepare();
    }

    protected abstract void init();

    private void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isPause = false;
        viewerDelegate.onViewerResume();

        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isPause = true;
        viewerDelegate.onViewerPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    @Override
    public void onDestroy() {
        unRegisterRxBus();
        super.onDestroy();
        if (viewerDelegate != null) {
            viewerDelegate.onViewerDestroy();
        }
    }

    @Override
    public boolean getUserVisibleHint() {
        return isVisibleToUser;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //todo:屏蔽此方法，为了解决一super里的putBoolean出现未知的运行时异常 暂时没有办法处理（先注掉，不知道会出现其他什么问题），等待后续内测反馈问题
        this.isVisibleToUser = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    protected ViewerComponent getViewerComponent() {
        return DaggerViewerComponent.builder()
                .userComponent(HApplication.getInstance().userComponent)
                .viewerModule(new ViewerModule(this))
                .build();
    }

    /**
     * 第一次fragment可见（进行初始化工作）
     */
    public void onFirstUserVisible() {
        startLoadingView();
//        registerRxBus();
    }

    /**
     * fragment可见（切换回来或者onResume）
     */
    public void onUserVisible() {
        registerRxBus();
    }

    /**
     * 第一次fragment不可见（暂时无需在此处理事件）
     */
    public void onFirstUserInvisible() {
        cancelLoadingDialog();
        unRegisterRxBus();
    }

    /**
     * fragment不可见（切换掉或者onPause）
     */
    public void onUserInvisible() {
        cancelLoadingDialog();
        if (!isPause) {
            unRegisterRxBus();
        }
    }

    public void registerRxBus() {
    }

    public void unRegisterRxBus() {
//        _callIfNotNull(mainKeyEventFlowable, mainKeyEventFlowable -> RxBus.get().unregister(MainKeyEvent.class, mainKeyEventFlowable));
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
        Context context = getContext();
        if (context != null && !((BaseActivity) context).isFinishing()) {
            viewerDelegate.showToast(message);
        }
    }

    @Override
    public void showToast(int resStringId) {
        Context context = getContext();
        if (context != null && !((BaseActivity) context).isFinishing()) {
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
        if (viewerDelegate == null) return;
        viewerDelegate.cancelLoadingDialog();
//        stopLoadingView();
    }

    public void focusEnter() {

    }

    public void onTabFocused() {

    }

    public void startLoadingView() {
        if (loadingView != null) {
            loadingView.startLoading(getLoadingForViewGroup());
        }
    }

    public void stopLoadingView() {
        if (loadingView != null) {
            loadingView.stopLoading(getLoadingForViewGroup());
        }
    }

    public abstract ViewGroup getLoadingForViewGroup();

    public void refresh() {
//        startLoadingView();
    }

    public void setUserVisibleHint(BaseFragment fragment) {
        this.fragment = fragment;
    }

    public boolean getUserVisibleHint(BaseFragment fragment) {
        return this.fragment == fragment;
    }

    public void showNotDataTip() {
//        if (csNoDataView == null) {
//            csNoDataView = new CSNoDataView(getContext());
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
//                    , RelativeLayout.LayoutParams.WRAP_CONTENT);
//            params.addRule(RelativeLayout.CENTER_IN_PARENT);
//            csNoDataView.setLayoutParams(params);
//            getLoadingForViewGroup().addView(csNoDataView);
//        } else {
//            csNoDataView.setVisibility(View.VISIBLE);
//        }
    }

    public void hideNotDataTip() {
//        if (csNoDataView != null) {
//            csNoDataView.setVisibility(View.GONE);
//        }
    }

    @SuppressLint("CheckResult")
    public void delayedInit(Consumer<Long> consumer) {
        Observable.timer(250, TimeUnit.MILLISECONDS)
                .observeOn(RxCompat.getSchedulerOnMain())
                .subscribe(consumer);
    }

    public RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            scrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            scrolled(recyclerView, dx, dy);
        }
    };

    public void scrolled(RecyclerView recyclerView, int dx, int dy) {
        //
    }

    public void scrollStateChanged(RecyclerView recyclerView, int newState) {
        //
    }

//    @SuppressLint("CheckResult")
//    public void registerBlackEvent(Consumer<MainKeyEvent> consumer) {
//        mainKeyEventFlowable = RxBus.get().register(MainKeyEvent.class);
//        mainKeyEventFlowable.observeOn(RxCompat.getSchedulerOnMain())
//                .subscribe(consumer);
//    }
}
