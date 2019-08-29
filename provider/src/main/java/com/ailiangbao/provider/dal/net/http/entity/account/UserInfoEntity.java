package com.ailiangbao.provider.dal.net.http.entity.account;

import android.text.TextUtils;

import com.ailiangbao.provider.dal.net.http.response.BaseHttpResponse;
import com.wangjie.rapidorm.api.annotations.Column;
import com.wangjie.rapidorm.api.annotations.Table;

import java.io.Serializable;

/**
 * Created by cq on 2018/5/8
 */
@Table(name = "userInfo")
public class UserInfoEntity extends BaseHttpResponse {

    public static final String USERID = "user_id";

    /* 未登录用户user id */
    public static final long USER_NOT_LOGIN_USER_ID = -0x338933;
    /* 未登录用户 */
    public static final UserInfoEntity USER_NOT_LOGIN = new UserInfoEntity(String.valueOf(USER_NOT_LOGIN_USER_ID));

    public static final int USER_TYPE_NULL = 0;
    public static final int USER_TYPE_USER = 1;

    /**
     * sessionToken : 6087a785-00e4-4382-bce2-461b8efdda5a
     * telephone : XXXXXXXX
     * comId : 1
     * loginType : telephone
     * stamp : 2019-02-17 11:07:01
     */

    public UserInfoEntity() {

    }

    public UserInfoEntity(String id) {
        this.userId = id;
    }

    /**
     "deviceId": "8c1ad9d17b3c40059dea5fe7457f698b",
     "id": 1,
     "accountNo": 300187976,
     "openid": "oTO6rxMsSePGDToh0gRMcd_27Crk",
     "nickname": null,
     "headimgurl": "http://thirdwx.qlogo.cn/mmopen/vi_32/3aJj6vPsj8ACq4oPRibQVTFXJJ15vktAHeKZkMYHxEibqFOLxr1jTuydv5IDlz3pwEZicMhgSB14ltawQ98zLoHZw/132",
     "createDate": "2018-07-25 13:33:06",
     "vipId": null,
     "vipBeginDate": null,
     "vipEndDate": null,
     "vipStatus": null
     */

    /**
     * 用户id,唯一标识
     */
    @Column(primaryKey = true, name = "user_id")
    String userId;
    @Column
    String sessionToken;
    @Column
    String telephone;
    @Column
    String comId;
    @Column
    String loginType;
    @Column
    String stamp;

    @Column
    String token;

    String expire;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public long getUserId(long Id) {
        if (TextUtils.isEmpty(userId)) {
            return Id;
        }
        return Long.valueOf(userId);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public long getIdDefaultNotLogin() {
        return TextUtils.isEmpty(userId) ? USER_NOT_LOGIN_USER_ID : Long.valueOf(userId);
    }
}
