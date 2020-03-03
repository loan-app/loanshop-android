package com.ailiangbao.provider.bll.application;

import android.annotation.SuppressLint;
import android.app.Application;

import com.ailiangbao.provider.bll.inject.application.ProviderApplicationComponent;
import com.ailiangbao.provider.bll.inject.db.HProviderUserInfoDatabaseModule;
import com.ailiangbao.provider.bll.inject.interactor.ProviderUserInteractorComponent;
import com.ailiangbao.provider.bll.inject.prefs.ProviderApplicationPrefsModule;
import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;
import com.ailiangbao.provider.bll.inject.application.DaggerProviderApplicationComponent;
import com.ailiangbao.provider.bll.inject.interactor.DaggerProviderUserInteractorComponent;


/**
 * 与主项目之间上下文环境的同步
 * <p>
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 10/27/16.
 */
public class ProviderApplication {
    private boolean isTestServer;

    private static class Holder {
        @SuppressLint("StaticFieldLeak")
        private static ProviderApplication instance = new ProviderApplication();
    }

    private Application application;
    private boolean buildConfigDebug;
    private String channel;

    public ProviderApplicationComponent providerApplicationComponent;
    public ProviderUserInteractorComponent providerUserInteractorComponent;

    public static ProviderApplication getInstance() {
        return Holder.instance;
    }

    private ProviderApplication() {
    }

    public ProviderApplication setApplication(Application application) {
        this.application = application;

        providerApplicationComponent = DaggerProviderApplicationComponent.builder()
                .providerApplicationPrefsModule(new ProviderApplicationPrefsModule())
                .build();

        return this;
    }

    public ProviderApplication setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public ProviderApplication setTestServer(boolean isTestServer) {
        this.isTestServer = isTestServer;
        return this;
    }

    public boolean isTestServer() {
        return isTestServer;
    }

    /**
     * 每次切换用户都需要调用此方法来使用新的UserInteractor
     */
    public void onSwitchUser() {
        providerUserInteractorComponent = DaggerProviderUserInteractorComponent.builder()
                .providerApplicationComponent(providerApplicationComponent)
                .hProviderUserInfoDatabaseModule(new HProviderUserInfoDatabaseModule())
                .build();
    }

    public Application getApplication() {
        return application;
    }

    public ProviderApplication setBuildConfigDebug(boolean buildConfigDebug) {
        this.buildConfigDebug = buildConfigDebug;
        return this;
    }

    public boolean isBuildConfigDebug() {
        return buildConfigDebug;
    }

    public String getChannel() {
        return channel;
    }

    /**
     * 当前登录用户
     */
    public UserInfoEntity getCurrentUser() {
        return providerUserInteractorComponent.providerCurrentLoginCache().getCurrentUser();
    }

    /**
     * 是否登录状态
     */
    public boolean isLogin() {
        return providerUserInteractorComponent.providerCurrentLoginCache().isLogin();
    }

    public String getCurrentUserCode() {
        return getCurrentUserCode(getCurrentUser().getUserId(UserInfoEntity.USER_NOT_LOGIN_USER_ID));
    }

    /**
     * 获取用户code
     */
    public String getCurrentUserCode(long userId) {
        return userId + "";
    }

    /**
     * 获取用户数据库文件
     */
    public String getCurrentUserDataBase() {
        return getCurrentUserCode() + ".db";
    }
}
