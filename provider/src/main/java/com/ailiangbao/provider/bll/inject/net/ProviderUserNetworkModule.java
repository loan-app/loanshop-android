package com.ailiangbao.provider.bll.inject.net;


import com.ailiangbao.provider.bll.inject.scope.Provider_Scope_User;
import com.ailiangbao.provider.dal.util.RequestCreator;

import dagger.Module;
import dagger.Provides;

@Module
public class ProviderUserNetworkModule {

    @Provides
    @Provider_Scope_User
    public RequestCreator providerXRequestCreator() {
        return new RequestCreator();
    }

}
