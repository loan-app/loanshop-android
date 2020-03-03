package com.ailiangbao.provider.dal.net.http.response;

import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;

public class UserInfoResponse extends BaseHttpResponse {
    private UserInfoEntity data;

    public UserInfoEntity getUserInfo() {
        return data;
    }

    public void setUserInfo(UserInfoEntity data) {
        this.data = data;
    }
}
