package com.ailiangbao.provider.bll.interactor.impl;

import com.ailiangbao.provider.bll.interactor.base.BaseInteractor;
import com.ailiangbao.provider.bll.interactor.contract.GlobalInteractor;
import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;
import com.ailiangbao.provider.dal.prefs.PrefsConstants;
import com.ailiangbao.provider.dal.prefs.PrefsHelper;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by cq on 2018/5/16
 */
public class GlobalInteractorImpl extends BaseInteractor implements GlobalInteractor {

    @Inject
    @Named(PrefsConstants.PREFS_GLOBAL)
    PrefsHelper globalPrefsHelper;

    public GlobalInteractorImpl() {
        getProviderApplicationComponent().inject(this);
    }

    @Override
    public long queryGlobalCurrentLoginUserIdSync() {
        return globalPrefsHelper.getLong(PrefsConstants.PREFS_GLOBAL_USER_ID, UserInfoEntity.USER_NOT_LOGIN_USER_ID);
    }

    @Override
    public void saveGlobalCurrentLoginUserIdSync(long userId) {
        globalPrefsHelper.putLong(PrefsConstants.PREFS_GLOBAL_USER_ID, userId).commit();
    }

    @Override
    public String queryGlobalCurrentLoginUserTokenSync() {
        return globalPrefsHelper.getString(PrefsConstants.PREFS_GLOBAL_USER_TOKEN);
    }

    @Override
    public void saveGlobalCurrentLoginUserTokenSync(String token) {
        globalPrefsHelper.putString(PrefsConstants.PREFS_GLOBAL_USER_TOKEN, "");
    }

    //
//    @Override
//    public boolean queryIfAppFirstLaunch() {
////        boolean isFirstLaunch = globalPrefsHelper.getBoolean(PrefsConstants.PREFS_GLOBAL_IS_FIRST_LAUNCH, true);
////        if (isFirstLaunch) {
//             是首次则设置改值为false
////            globalPrefsHelper.putBoolean(PrefsConstants.PREFS_GLOBAL_IS_FIRST_LAUNCH, false).commit();
////        }
////        return isFirstLaunch;
//        return false;
//    }
//
//    @Override
//    public String queryPlayModelSyn() {
////        String defaultPlayer;
////        if (Build.VERSION.SDK_INT >= 16) {//exoPlayer最低支持4.1版本
////            defaultPlayer = PrefsConstants.PLAY_MODEL_HARD;
////        } else {
////            defaultPlayer = PrefsConstants.PLAY_MODEL_SYSTEM;
////        }
//        return globalPrefsHelper.getString(PrefsConstants.PLAY_MODEL, PrefsConstants.PLAY_MODEL_HARD);
////        return globalPrefsHelper.getString(PrefsConstants.PLAY_MODEL, PrefsConstants.PLAY_MODEL_HARD);
//    }
//
//    @Override
//    public void savePlayMode(String model) {
////        if (Build.VERSION.SDK_INT < 16 && model.equals(PrefsConstants.PLAY_MODEL_HARD)) {
////            model = PrefsConstants.PLAY_MODEL_SYSTEM;
////        }
//        globalPrefsHelper.putString(PrefsConstants.PLAY_MODEL, model).commit();
//    }
//
//    @Override
//    public int queryDefinitionSyn() {
//        int anInt = globalPrefsHelper.getInt(PrefsConstants.DEFINITION, PrefsConstants.TS);
//        if (anInt == 0) {//不知为何，出现过值等于0的情况
//            anInt = 1;
//        }
//        return anInt;
//    }
//
//    @Override
//    public void saveDefinitionSyn(int definition) {
//        globalPrefsHelper.putInt(PrefsConstants.DEFINITION, definition).commit();
//    }
//
//    @Override
//    public boolean queryAutoPlayStateSyn() {
//        return globalPrefsHelper.getBoolean(PrefsConstants.AUTO_PLAY, true);
//    }
//
//    @Override
//    public void saveAutoPlayState(boolean state) {
//        globalPrefsHelper.putBoolean(PrefsConstants.AUTO_PLAY, state).commit();
//    }
//
    @Override
    public String queryUUIDSyn() {
        return globalPrefsHelper.getString(PrefsConstants.UUID, "");
    }

    @Override
    public void saveUUID(String uuid) {
        globalPrefsHelper.putString(PrefsConstants.UUID, uuid).commit();
    }
//
//    @Override
//    public boolean queryUpdateState() {
//        return globalPrefsHelper.getBoolean(PrefsConstants.SETTING_VERSION_UPDATE, false);
//    }
//
//    @Override
//    public void saveUpdateState(boolean isUpdate) {
//        globalPrefsHelper.putBoolean(PrefsConstants.SETTING_VERSION_UPDATE, isUpdate).commit();
//    }
//
//    @Override
//    public boolean queryBulletScreenStateSyn() {
//        return globalPrefsHelper.getBoolean(PrefsConstants.BULLET_SCREEN_STATE, true);
//    }
//
//    @Override
//    public void saveBulletScreenStateSyn(boolean isOnBullet) {
//        globalPrefsHelper.putBoolean(PrefsConstants.BULLET_SCREEN_STATE, isOnBullet).commit();
//    }
//
//    @Override
//    public int queryBarrageLocation() {
//        return globalPrefsHelper.getInt(PrefsConstants.BARRAGE_LOCATION, 2);
//    }
//
//    @Override
//    public void saveBarrageLocation(int location) {
//        globalPrefsHelper.putInt(PrefsConstants.BARRAGE_LOCATION, location).commit();
//    }
//
//    @Override
//    public int queryBarrageTextSize() {
//        return globalPrefsHelper.getInt(PrefsConstants.BARRAGE_TEXT_SIZE, 2);
//    }
//
//    @Override
//    public void saveBarrageTextSize(int size) {
//        globalPrefsHelper.putInt(PrefsConstants.BARRAGE_TEXT_SIZE, size).commit();
//    }
//
//    @Override
//    public int queryBarrageTextTrans() {
//        return globalPrefsHelper.getInt(PrefsConstants.BARRAGE_TEXT_TRANS, 0);
//    }
//
//    @Override
//    public void saveBarrageTextTrans(int trans) {
//        globalPrefsHelper.putInt(PrefsConstants.BARRAGE_TEXT_TRANS, trans).commit();
//    }
//
//    @Override
//    public boolean queryActionState() {
//        return globalPrefsHelper.getBoolean(PrefsConstants.ACTION_STATE, true);//活动默认开启
//    }
//
//    @Override
//    public void saveActionState(boolean isAction) {
//        globalPrefsHelper.putBoolean(PrefsConstants.ACTION_STATE, isAction).commit();
//    }
//
//    @Override
//    public void saveSplashLogoPicUrl(String logoPicUrl) {
//        globalPrefsHelper.putString(PrefsConstants.SPLASH_LOGO_PIC_URL, logoPicUrl).commit();
//    }
//
//    @Override
//    public String getSplashLogoPicUrl() {
//        return globalPrefsHelper.getString(PrefsConstants.SPLASH_LOGO_PIC_URL, "");
//    }
//
//    @Override
//    public void saveSplashAdPicUrl(String adPicUrl) {
//        globalPrefsHelper.putString(PrefsConstants.SPLASH_AD_PIC_URL, adPicUrl).commit();
//    }
//
//    @Override
//    public String getSplashAdPicUrl() {
//        return globalPrefsHelper.getString(PrefsConstants.SPLASH_AD_PIC_URL, "");
//    }
//
//    @Override
//    public void saveSplashLogoPicMd5(String logoPicMd5) {
//        globalPrefsHelper.putString(PrefsConstants.SPLASH_LOGO_PIC_MD5, logoPicMd5).commit();
//    }
//
//    @Override
//    public String getSplashLogoPicMd5() {
//        return globalPrefsHelper.getString(PrefsConstants.SPLASH_LOGO_PIC_MD5, "");
//    }
//
//    @Override
//    public void saveSplashAdPicMd5(String adPicMd5) {
//        globalPrefsHelper.putString(PrefsConstants.SPLASH_AD_PIC_MD5, adPicMd5).commit();
//    }
//
//    @Override
//    public String getSplashAdPicMd5() {
//        return globalPrefsHelper.getString(PrefsConstants.SPLASH_AD_PIC_MD5, "");
//    }
//
//    @Override
//    public void saveSplashLogoLocalPath(String logoLocalPath) {
//        globalPrefsHelper.putString(PrefsConstants.SPLASH_LOGO_LOCAL_PATH, logoLocalPath).commit();
//    }
//
//    @Override
//    public String getSplashLogoLocalPath() {
//        return globalPrefsHelper.getString(PrefsConstants.SPLASH_LOGO_LOCAL_PATH, "");
//    }
//
//    @Override
//    public void saveSplashAdLocalPath(String adLocalPath) {
//        globalPrefsHelper.putString(PrefsConstants.SPLASH_AD_LOCAL_PATH, adLocalPath).commit();
//    }
//
//    @Override
//    public String getSplashAdLocalPath() {
//        return globalPrefsHelper.getString(PrefsConstants.SPLASH_AD_LOCAL_PATH, "");
//    }
//
//    @Override
//    public void saveUmengDeviceToken(String deviceToken) {
//        globalPrefsHelper.putString(PrefsConstants.UMENG_DEVICE_TOKEN, deviceToken).commit();
//    }
//
//    @Override
//    public String queryUmengDeviceToken() {
//        return globalPrefsHelper.getString(PrefsConstants.UMENG_DEVICE_TOKEN, "");
//    }
}
