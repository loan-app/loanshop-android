package com.ailiangbao.alb;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ailiangbao.alb.config.network.interceptor.request.RequestExtraParamsInterceptor;
import com.ailiangbao.alb.config.network.interceptor.request.RequestHeaderInterceptor;
import com.ailiangbao.alb.config.network.interceptor.request.RequestSignAndEncryptInterceptor;
import com.ailiangbao.alb.config.network.interceptor.response.ResponseTokenExpiresInterceptor;
import com.ailiangbao.alb.inject.app.AppComponent;
import com.ailiangbao.alb.inject.app.AppModule;
import com.ailiangbao.alb.inject.app.DaggerAppComponent;
import com.ailiangbao.alb.inject.modules.InteractorModule;
import com.ailiangbao.alb.inject.user.DaggerUserComponent;
import com.ailiangbao.alb.inject.user.UserComponent;
import com.ailiangbao.alb.inject.user.UserModule;
import com.ailiangbao.alb.util.AppInfoUtil;
import com.ailiangbao.provider.bll.application.ProviderApplication;
import com.ailiangbao.provider.bll.interactor.contract.GlobalInteractor;
import com.ailiangbao.provider.bll.interactor.contract.LoginInteractor;
import com.ailiangbao.provider.dal.db.HDatabaseFactory;
import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;
import com.ailiangbao.provider.support.bridge.testable.rx.scheduler.SchedulerBridge;
import com.dangbei.xlog.XLog;
import com.dangbei.xlog.XLogDelegateAndroid;
import com.dpuntu.downloader.DownloadManager;
import com.fm.openinstall.OpenInstall;
import com.tencent.bugly.crashreport.CrashReport;
import com.wangjiegulu.dal.request.XHttpManager;

import cn.screen.adaptation.annotation.IdentificationEnum;
import cn.screen.adaptation.screen_lib.ScreenAdaptation;

public class HApplication extends Application {
    private final static String TAG = HApplication.class.getSimpleName();
    private static HApplication instance;

    public AppComponent appComponent;
    public UserComponent userComponent;

    public static HApplication getInstance() {
        return instance;
    }

    /**
     * 判断当前进程是否为主进程
     */
    private boolean isUIProcess(Context context) {
        try {
            int pid = android.os.Process.myPid();
            String processName = "";
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    processName = appProcess.processName;
                    break;
                }
            }
            String packageName = context.getPackageName();
            return processName.equals(packageName);
        } catch (Throwable throwable) {
            XLog.e(TAG, throwable);
        }
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        XLog.initialize(new XLogDelegateAndroid());
        if (isUIProcess(this)) {
            initBugly();
            initChannel();
            initUIProcess();
            initScreenAdaptive();
            DownloadManager.initDownloader(this);
        }
    }

    private void initChannel() {
        OpenInstall.init(this);
    }

    private void initBugly() {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setAppChannel(AppInfoUtil.getChannel(this));
//        strategy.setAppPackageName(AppUtils.getAppPackageName())
        strategy.setAppVersion(AppInfoUtil.getVersionName(this));
//        strategy.setDeviceID(UmengUtils.getMac(this))
        CrashReport.initCrashReport(this, BuildConfig.BUGLY_APP_ID, BuildConfig.DEBUG, strategy);
    }

    private void initScreenAdaptive() {
        ScreenAdaptation.instance()
                .setBaseWidth(360)  // 设计图的宽度 单位dp  必填.
                .setBaseHeight(640)  // 设计图的高度 单位dp  必填.
                .setBase(IdentificationEnum.HEIGHT) //全局以哪个纬度开始适配 取值有IdentificationEnum.WIDTH,和IdentificationEnum.HEIGHT.
                .setAutoScreenAdaptation(true) //开启全局适配
                .create(this);
    }

    private void initUIProcess() {

        String channelString = AppInfoUtil.getChannel(this);

        ProviderApplication.getInstance()
                .setApplication(this)
                .setChannel(TextUtils.isEmpty(channelString) ? "UnKnow" : channelString)
                .setTestServer(false)
                .setBuildConfigDebug(BuildConfig.DEBUG);
//
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();

        // 记录当前的Activity类名
//        registerActivityLifecycleCallbacks(ActivityLifecycleManager.get());
//
//        // 初始化线程池
        SchedulerBridge.initialize();
//        /* 注意 以下添加 RequestInterceptor 的顺序不能改变 */
        XHttpManager.getInstance()
////                // 添加请求默认header
                .addRequestInterceptor(new RequestHeaderInterceptor())
////                // 添加请求默认参数
                .addRequestInterceptor(new RequestExtraParamsInterceptor())
//                // 签名&加密
                .addRequestInterceptor(new RequestSignAndEncryptInterceptor())
//////                // Token过期处理
                .addResponseInterceptor(new ResponseTokenExpiresInterceptor());
//
////        CrashHandler.getInstance(this).init();
//
        autoSwitchUser();
    }

    private void autoSwitchUser() {
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        GlobalInteractor globalInteractor = appComponent.providerGlobalInteractor();
        long userId = globalInteractor.queryGlobalCurrentLoginUserIdSync();
        XLog.i(TAG, "-----------> autoSwitchUser, userId: " + userId);
        switchUser(userId, null);
    }

    public void switchUser(long userId, @Nullable UserInfoEntity user) {

        XLog.d(TAG, "-----------> switchUser, userId: " + userId + ", PersonalInfoEntity: " + user);

        // 如果用户id和传入的userId不一致，则默认使用未登录状态
        if (null != user && user.getIdDefaultNotLogin() != userId) {
            user = null;
            userId = UserInfoEntity.USER_NOT_LOGIN_USER_ID;
        }

        /* 切换本地保存的当前用户userId */
        appComponent.providerGlobalInteractor().saveGlobalCurrentLoginUserIdSync(userId);
        if (null != ProviderApplication.getInstance().providerUserInteractorComponent) {
            ProviderApplication.getInstance().providerUserInteractorComponent.
                    providerCurrentLoginCache().clear();
        }
        userComponent = initUserComponent();

        /* 重置数据库 */
        // TODO: 4/17/17 wangjie optim databaseFactory scope
        HDatabaseFactory.getInstance().resetDatabase(
                ProviderApplication.getInstance().getCurrentUserCode(userId) + ".db");

//        /* 通知数据层切换用户 */
        ProviderApplication.getInstance().onSwitchUser();
        XLog.i(TAG, "切换用户后的数据层操作");

        /* 保存登录信息 */
        if (null != user) {
            LoginInteractor loginInteractor = userComponent.providerLoginInteractor();
            loginInteractor.saveUserInfo(user);
        }
    }

    public UserComponent initUserComponent() {
        return DaggerUserComponent
                .builder()
                .userModule(new UserModule(this))
                .interactorModule(new InteractorModule())
                .appComponent(appComponent)
                .build();
    }

    public void logoutEvent(String tipMessage) {
//        ToastUtils.showCustomToast(tipMessage);
    }
}
