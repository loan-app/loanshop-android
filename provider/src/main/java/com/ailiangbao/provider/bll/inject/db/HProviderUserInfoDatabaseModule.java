package com.ailiangbao.provider.bll.inject.db;


import com.ailiangbao.provider.bll.inject.scope.Provider_Scope_User;
import com.ailiangbao.provider.dal.dao.contract.HUserDao;
import com.ailiangbao.provider.dal.dao.contract.HUserStatusDao;
import com.ailiangbao.provider.dal.dao.impl.HUserDaoImpl;
import com.ailiangbao.provider.dal.dao.impl.HUserStatusDaoImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class HProviderUserInfoDatabaseModule {
    @Provides
    @Provider_Scope_User
    public HUserDao providerUserDao() {
        return new HUserDaoImpl();
    }
    @Provides
    @Provider_Scope_User
    public HUserStatusDao providerHUserStatusDao() {
        return new HUserStatusDaoImpl();
    }

}
