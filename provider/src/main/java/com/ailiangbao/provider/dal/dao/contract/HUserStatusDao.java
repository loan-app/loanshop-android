package com.ailiangbao.provider.dal.dao.contract;

import com.ailiangbao.provider.dal.dao.HBaseDao;
import com.ailiangbao.provider.dal.net.http.entity.account.UserStatusEntity;

public interface HUserStatusDao extends HBaseDao<UserStatusEntity> {
    UserStatusEntity queryUserStatus(long userId) throws Exception;

    void updateUserStatus(String column, String statusValue, long userId) throws Exception;
}
