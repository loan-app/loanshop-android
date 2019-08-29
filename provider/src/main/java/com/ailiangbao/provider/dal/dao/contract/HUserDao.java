package com.ailiangbao.provider.dal.dao.contract;


import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;
import com.ailiangbao.provider.dal.dao.HBaseDao;

/**
 * Created by cq on 2018/5/15
 */
public interface HUserDao extends HBaseDao<UserInfoEntity> {
    void insertUserInfo(UserInfoEntity userInfoEntity) throws Exception;

    UserInfoEntity queryUser(long userId) throws Exception;

    void updateUserInfo(UserInfoEntity userInfoEntity) throws Exception;

    void deleteUserInfo(long userId) throws Exception;

    void updateVipState(boolean isVip, long userId) throws Exception;
}
