package com.ailiangbao.provider.bll.inject.cache;


import com.ailiangbao.provider.bll.inject.scope.Provider_Scope_User;
import com.ailiangbao.provider.bll.interactor.cache.HCurrentLoginCache;

import dagger.Module;
import dagger.Provides;

@Module
public class HProviderUserCacheModule {
    @Provides
    @Provider_Scope_User
    public HCurrentLoginCache providerCurrentLoginCache() {
        return new HCurrentLoginCache();
    }
}
