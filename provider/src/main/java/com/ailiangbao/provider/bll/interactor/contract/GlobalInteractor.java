package com.ailiangbao.provider.bll.interactor.contract;

/**
 * Created by cq on 2018/5/16
 */
public interface GlobalInteractor {
    //    /**
//     * 获取保存的登录ID
//     * <p>
//     * 一般只在第一次打开app时通过该方法获取上次登录的userId，如果不是-1，说明上次已登陆，则自动登录
//     *
//     * @return -1 如果没有登录
//     */
    long queryGlobalCurrentLoginUserIdSync();

    //
//    /**
//     * 保存登录ID
//     */
    void saveGlobalCurrentLoginUserIdSync(long userId);

    String queryGlobalCurrentLoginUserTokenSync();

    void saveGlobalCurrentLoginUserTokenSync(String token);
//
//    /**
//     * 判断app是否是首次安装启动
//     *
//     * @return
//     */
//    boolean queryIfAppFirstLaunch();
//
//    String queryPlayModelSyn();
//
//    void savePlayMode(String model);
//
//    int queryDefinitionSyn();
//
//    void saveDefinitionSyn(int definition);
//
//    boolean queryAutoPlayStateSyn();
//
//    void saveAutoPlayState(boolean state);
//

    /**
     * 查询UUID
     */
    String queryUUIDSyn();

    /**
     * 新安装软件是保存UUID
     */
    void saveUUID(String uuid);
//
//    boolean queryUpdateState();
//
//    void saveUpdateState(boolean isUpdate);
//
//    boolean queryBulletScreenStateSyn();
//
//    void saveBulletScreenStateSyn(boolean isOnBullet);
//
//    int queryBarrageLocation();
//
//    void saveBarrageLocation(int location);
//
//    int queryBarrageTextSize();
//
//    void saveBarrageTextSize(int size);
//
//    int queryBarrageTextTrans();
//
//    void saveBarrageTextTrans(int trans);
//
//    boolean queryActionState();
//
//    void saveActionState(boolean isAction);
//
//    //******* splash start ********//
//    void saveSplashLogoPicUrl(String logoPicUrl);
//
//    String getSplashLogoPicUrl();
//
//    void saveSplashAdPicUrl(String adPicUrl);
//
//    String getSplashAdPicUrl();
//
//    void saveSplashLogoPicMd5(String logoPicMd5);
//
//    String getSplashLogoPicMd5();
//
//    void saveSplashAdPicMd5(String adPicMd5);
//
//    String getSplashAdPicMd5();
//
//    void saveSplashLogoLocalPath(String logoLocalPath);
//
//    String getSplashLogoLocalPath();
//
//    void saveSplashAdLocalPath(String adLocalPath);
//
//    String getSplashAdLocalPath();
//    //******* splash end   ********//
//
//    //***********umeng***********//
//    void saveUmengDeviceToken(String deviceToken);
//
//    String queryUmengDeviceToken();
}
