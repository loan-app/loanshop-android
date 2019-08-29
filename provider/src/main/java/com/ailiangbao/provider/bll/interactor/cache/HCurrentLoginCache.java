package com.ailiangbao.provider.bll.interactor.cache;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ailiangbao.provider.bll.application.ProviderApplication;
import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;
import com.ailiangbao.provider.dal.prefs.PrefsConstants;


/**
 * Created by cq on 2018/5/15
 */
public class HCurrentLoginCache {
    private static final String TAG = HCurrentLoginCache.class.getSimpleName();
    /**
     * 当前登录的用户缓存
     */
    private UserInfoEntity user;

    /**
     * 同步查询当前登录的用户
     */
    @NonNull
    @SuppressWarnings("PMD.AvoidDeeplyNestedIfStmts")
    public UserInfoEntity getCurrentUser() {
        if (null == user) {
            synchronized (this) {
                if (null == user) {
                    try {
                        ProviderApplication providerApplication = ProviderApplication.getInstance();
                        long userId = providerApplication.providerApplicationComponent.providerGlobalPrefsHelper()
                                .getLong(PrefsConstants.PREFS_GLOBAL_USER_ID, UserInfoEntity.USER_NOT_LOGIN_USER_ID);
                        if (UserInfoEntity.USER_NOT_LOGIN_USER_ID != userId) {
                            user = providerApplication.providerUserInteractorComponent.providerUserDao().queryUser(userId);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                    if (null == user) {
                        user = UserInfoEntity.USER_NOT_LOGIN;
                    }
                }
            }
        }
        return user;
    }

    /**
     * 是否是登录状态
     */
    public boolean isLogin() {
        return UserInfoEntity.USER_NOT_LOGIN != getCurrentUser();
    }

    /**
     * 清除缓存
     */
    public void clear() {
        user = null;
    }
}
