package com.ailiangbao.provider.bll.interactor.impl;

import android.support.annotation.Nullable;

import com.ailiangbao.provider.bll.application.ProviderApplication;
import com.ailiangbao.provider.bll.interactor.base.BaseInteractor;
import com.ailiangbao.provider.bll.interactor.cache.HCurrentLoginCache;
import com.ailiangbao.provider.bll.interactor.contract.UserInteractor;
import com.ailiangbao.provider.dal.dao.contract.HUserDao;
import com.ailiangbao.provider.dal.dao.contract.HUserStatusDao;
import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;
import com.ailiangbao.provider.dal.net.http.entity.account.UserStatusEntity;
import com.ailiangbao.provider.dal.prefs.PrefsConstants;
import com.ailiangbao.provider.dal.prefs.PrefsHelper;
import com.ailiangbao.provider.dal.util.RequestCreator;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 10/31/16.
 */
public class UserInteractorImpl extends BaseInteractor implements UserInteractor {
    private static final String TAG = UserInteractorImpl.class.getSimpleName();

    @Inject
    HUserDao userDao;

    @Inject
    HUserStatusDao userStatusDao;

    @Inject
    HCurrentLoginCache currentLoginCache;

    @Inject
    RequestCreator requestCreator;

    //    @Inject
//    @Named(PrefsConstants.PREFS_GLOBAL)
//    PrefsHelper prefsHelper;
//
    public UserInteractorImpl() {
        getProviderUserInteractorComponent().inject(this);
    }

    @Override
    public Observable<UserInfoEntity> requestCurrentUserInfo() {
        return toObservable(() -> {
            PrefsHelper prefsHelper = ProviderApplication.getInstance().providerApplicationComponent.providerGlobalPrefsHelper();
            long userId = prefsHelper.getLong(PrefsConstants.PREFS_GLOBAL_USER_ID, UserInfoEntity.USER_NOT_LOGIN_USER_ID);
            UserInfoEntity user = null;
            if (UserInfoEntity.USER_NOT_LOGIN_USER_ID != userId) {
                user = userDao.queryUser(userId);
            }
            return null == user ? UserInfoEntity.USER_NOT_LOGIN : user;
        }).subscribeOn(RxCompat.getSchedulerOnDb());
    }


    @Override
    public Observable<UserStatusEntity> queryAuthenticationStatus() {
        return toObservable(() -> {
            PrefsHelper prefsHelper = ProviderApplication.getInstance().providerApplicationComponent.providerGlobalPrefsHelper();
            long userId = prefsHelper.getLong(PrefsConstants.PREFS_GLOBAL_USER_ID, UserInfoEntity.USER_NOT_LOGIN_USER_ID);
            UserStatusEntity userStatus = null;
            if (UserInfoEntity.USER_NOT_LOGIN_USER_ID != userId) {
                userStatus = userStatusDao.queryUserStatus(userId);
            }
            return userStatus;
        }).subscribeOn(RxCompat.getSchedulerOnDb());
    }

    @Override
    public boolean isLoginSync() {
        return currentLoginCache.isLogin();
    }

    @Override
    public void updateVipState(boolean isVip) {
//        emptyObservable(() -> {
//            PrefsHelper prefsHelper = ProviderApplication.getInstance().providerApplicationComponent.providerGlobalPrefsHelper();
//            long userId = prefsHelper.getLong(PrefsConstants.PREFS_GLOBAL_USER_ID, PersonalInfoEntity.USER_NOT_LOGIN_USER_ID);
//            if (PersonalInfoEntity.USER_NOT_LOGIN_USER_ID != userId) {
//                try {
//                    PersonalInfoEntity userInfoEntity = userDao.queryUser(userId);
//                    if (userInfoEntity != null) {
//                        userInfoEntity.setVipStatus(isVip);
//                        userDao.insertOrUpdate(userInfoEntity);
//                    }
////                    userDao.updateVipState(isVip, userId);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).subscribeOn(RxCompat.getSchedulerOnDb())
//                .subscribe();
    }

    @Override
    public UserInfoEntity requestCurrentUserInfoSyn() {
        PrefsHelper prefsHelper = ProviderApplication.getInstance().providerApplicationComponent.providerGlobalPrefsHelper();
        long userId = prefsHelper.getLong(PrefsConstants.PREFS_GLOBAL_USER_ID, UserInfoEntity.USER_NOT_LOGIN_USER_ID);
        UserInfoEntity user = null;
        if (UserInfoEntity.USER_NOT_LOGIN_USER_ID != userId) {
            try {
                user = userDao.queryUser(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null == user ? UserInfoEntity.USER_NOT_LOGIN : user;
    }

    @Override
    public void deleteCurrentLoginUserInfo() {
        PrefsHelper prefsHelper = ProviderApplication.getInstance().providerApplicationComponent.providerGlobalPrefsHelper();
        long userId = prefsHelper.getLong(PrefsConstants.PREFS_GLOBAL_USER_ID, UserInfoEntity.USER_NOT_LOGIN_USER_ID);
        UserInfoEntity user = null;
        if (UserInfoEntity.USER_NOT_LOGIN_USER_ID != userId) {
            try {
                userDao.deleteUserInfo(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public String getUserToken() {
        PrefsHelper prefsHelper = ProviderApplication.getInstance().providerApplicationComponent.providerGlobalPrefsHelper();
        return prefsHelper.getString(PrefsConstants.PREFS_GLOBAL_USER_TOKEN);
    }

    @Nullable
    @Override
    public String getUserID() {
        PrefsHelper prefsHelper = ProviderApplication.getInstance().providerApplicationComponent.providerGlobalPrefsHelper();
        long userId = prefsHelper.getLong(PrefsConstants.PREFS_GLOBAL_USER_ID, UserInfoEntity.USER_NOT_LOGIN_USER_ID);
        if (UserInfoEntity.USER_NOT_LOGIN_USER_ID != userId) {
            return String.valueOf(userId);
        }
        return null;
    }

    @Override
    public void saveUserInfo(UserInfoEntity userInfoEntity) {
        PrefsHelper prefsHelper = ProviderApplication.getInstance().providerApplicationComponent.providerGlobalPrefsHelper();
//        prefsHelper.putString(PrefsConstants.PREFS_GLOBAL_USER_ID, userInfoEntity.getUserId());
//        prefsHelper.putString(PrefsConstants.PREFS_GLOBAL_USER_TOKEN, userInfoEntity.getSessionToken());
    }

    private String getUserInfoTest() {
        return "{\n" +
                "\t\"userInfo\": {\n" +
                "\t\t\"userId\": \"4815\",\n" +
                "\t\t\"token\": \"539cfbd0c3aa07c4eee63a64b45098e9\",\n" +
                "\t\t\"logo\": \"http:\\/\\/wx.qlogo.cn\\/mmopen\\/vi_32\\/picoxSBicnZfJ9icgTCWoAnKEy213uQMyfpKnTjUISmhXM3Sf92sCGT6pZooAVnusdgrXMyMT1TkpYlgM6EtbC7ibA\\/132\",\n" +
                "\t\t\"nickname\": \"ChenNel_Yuu\",\n" +
                "\t\t\"mobile\": \"\",\n" +
                "\t\t\"alipay\": \"\",\n" +
                "\t\t\"aliname\": \"\",\n" +
                "\t\t\"account\": null,\n" +
                "\t\t\"invitecode\": \"27B4E\",\n" +
                "\t\t\"card\": 2,\n" +
                "\t\t\"inviteUsed\": false,\n" +
                "\t\t\"rank\": 0,\n" +
                "\t\t\"correctRank\": 0,\n" +
                "\t\t\"allRank\": 0\n" +
                "\t},\n" +
                "\t\"code\": 0,\n" +
                "\t\"msg\": \"success\",\n" +
                "\t\"nowTime\": 1516160083000\n" +
                "}";
    }

}
