package com.ailiangbao.provider.bll.interactor.contract;


import android.support.annotation.Nullable;

import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;
import com.ailiangbao.provider.dal.net.http.entity.account.UserStatusEntity;

import io.reactivex.Observable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 10/31/16.
 */
public interface UserInteractor {

//    /**
//     * 获取authorId、微信二维码地址
//     */
//    Observable<ZenithQRCodeComb> requestQrCode(String authid, String devid);
//
//    Observable<ZenithNetQrComb> requestNetQr(String uuid);

    /**
     * 获取当前用户信息
     */
    Observable<UserInfoEntity> requestCurrentUserInfo();

    Observable<UserStatusEntity> queryAuthenticationStatus();
//
//
//    Observable<PersonalInfoEntity> requestCurrentUserInfoFromNet();

    /**
     * 同步保存用户信息
     *
     * @param user
     */
//    boolean requestSaveLoginInfoSync(PersonalInfoEntity user);

    /**
     * 获取用户信息
     *
     * @param type 0 TV端微信登录 1 手机端微信授权登录 2 游客登录
     *             <p>
     *             0的时候前面参数名为 authid且 值为authorId
     *             1的时候前面参数名为 code  且 值为authorId
     *             2的时候前面参数名为 visit 且 值为1
     */
//    Observable<PersonalInfoEntity> requestLogin(int type, String authorId);

    boolean isLoginSync();

    void updateVipState(boolean isVip);

    UserInfoEntity requestCurrentUserInfoSyn();

    void deleteCurrentLoginUserInfo();

    /**
     * 获取当前登录用户的token，如果是未登录状态，则返回null
     *
     * @return
     */
    @Nullable
    String getUserToken();

    @Nullable
    String getUserID();

    void saveUserInfo(UserInfoEntity userInfoEntity);


//    Observable<Bitmap> requestShareQRCode(int wdith, int height);

//    Observable<PersonalInfoEntity> postInputInviteCode(String inviteCode);

//    Observable<Boolean> requestLoginOut();

    /**
     * 当前战队模式，是否开启，0关闭，1开启
     */
//    Observable<Integer> requestCurrentTeamMode();

    /**
     * 同步用户信息
     *
     * @return
     */
//    Observable<BaseHttpResponse> requestSyncUserInfo();
//
//    Observable<Integer> requestCurrentTeamModeFromLocal();
//
//    Observable<Float> requestVisitorAward();

}
