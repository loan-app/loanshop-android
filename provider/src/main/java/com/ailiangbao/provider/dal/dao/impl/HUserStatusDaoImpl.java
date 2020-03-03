package com.ailiangbao.provider.dal.dao.impl;

import com.ailiangbao.provider.dal.net.http.entity.account.UserStatusEntity;
import com.ailiangbao.provider.dal.dao.HBaseDaoImpl;
import com.ailiangbao.provider.dal.dao.contract.HUserStatusDao;
import com.wangjie.rapidorm.core.generate.builder.Where;

public class HUserStatusDaoImpl extends HBaseDaoImpl<UserStatusEntity> implements HUserStatusDao {
    public HUserStatusDaoImpl() {
        super(UserStatusEntity.class);
    }

    @Override
    public UserStatusEntity queryUserStatus(long userId) throws Exception {
        return queryBuilder().setWhere(Where.eq(UserStatusEntity.USER_STATUS_ID, userId))
                .setLimit(1)
                .queryFirst();
    }

    @Override
    public void updateUserStatus(String column, String statusValue, long userId) throws Exception {
        updateBuilder().setWhere(Where.eq(UserStatusEntity.USER_STATUS_ID, String.valueOf(userId)))
                .addUpdateColumn(column, statusValue)
                .update();
    }
}
