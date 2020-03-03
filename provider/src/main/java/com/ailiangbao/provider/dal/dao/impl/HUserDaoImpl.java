package com.ailiangbao.provider.dal.dao.impl;

import com.ailiangbao.provider.dal.dao.contract.HUserDao;
import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;
import com.ailiangbao.provider.dal.dao.HBaseDaoImpl;
import com.wangjie.rapidorm.core.generate.builder.Where;

/**
 * Created by cq on 2018/5/15
 */
public class HUserDaoImpl extends HBaseDaoImpl<UserInfoEntity> implements HUserDao {
    public HUserDaoImpl() {
        super(UserInfoEntity.class);
    }

    @Override
    public void insertUserInfo(UserInfoEntity userInfoEntity) throws Exception {
        insert(userInfoEntity);
    }

    @Override
    public UserInfoEntity queryUser(long userId) throws Exception {
        return queryBuilder().setWhere(Where.eq(UserInfoEntity.USERID, userId))
                .setLimit(1)
                .queryFirst();
    }

    @Override
    public void updateUserInfo(UserInfoEntity userInfoEntity) throws Exception {

    }

    @Override
    public void deleteUserInfo(long userId) throws Exception {
        deleteAll();
    }

    @Override
    public void updateVipState(boolean isVip, long userId) throws Exception {
//        updateBuilder().setWhere(Where.eq(PersonalInfoEntity.USERID, userId))
//                .addUpdateColumn(UserInfoEntity_RORM.VIPSTATUS, isVip)
//                .update();
    }
}
