package com.ailiangbao.provider.bll.inject.interactor;


import com.ailiangbao.provider.bll.inject.application.ProviderApplicationComponent;
import com.ailiangbao.provider.bll.inject.cache.HProviderUserCacheModule;
import com.ailiangbao.provider.bll.inject.db.HProviderUserInfoDatabaseModule;
import com.ailiangbao.provider.bll.inject.net.ProviderUserNetworkModule;
import com.ailiangbao.provider.bll.inject.prefs.ProviderApplicationPrefsModule;
import com.ailiangbao.provider.bll.inject.scope.Provider_Scope_User;
import com.ailiangbao.provider.bll.interactor.cache.HCurrentLoginCache;
import com.ailiangbao.provider.bll.interactor.impl.AllLoanInteractorImpl;
import com.ailiangbao.provider.bll.interactor.impl.CateDetailInteractorImpl;
import com.ailiangbao.provider.bll.interactor.impl.HomeInteractorImpl;
import com.ailiangbao.provider.bll.interactor.impl.LoginInteractorImpl;
import com.ailiangbao.provider.bll.interactor.impl.MainInteractorImpl;
import com.ailiangbao.provider.bll.interactor.impl.SplashInteractorImpl;
import com.ailiangbao.provider.bll.interactor.impl.UserInteractorImpl;
import com.ailiangbao.provider.dal.dao.contract.HUserDao;
import com.ailiangbao.provider.dal.dao.contract.HUserStatusDao;

import dagger.Component;

@Component(dependencies = {ProviderApplicationComponent.class}, modules = {
        ProviderUserNetworkModule.class,
        HProviderUserInfoDatabaseModule.class,
        HProviderUserCacheModule.class,
        ProviderApplicationPrefsModule.class})
@Provider_Scope_User
public interface ProviderUserInteractorComponent {
    HUserDao providerUserDao();

    HUserStatusDao providerHUserStatusDao();

    HCurrentLoginCache providerCurrentLoginCache();

    void inject(UserInteractorImpl userInteractor);

    void inject(MainInteractorImpl mainInteractor);

    void inject(LoginInteractorImpl interactor);

    void inject(HomeInteractorImpl interactor);

    void inject(SplashInteractorImpl interactor);

    void inject(AllLoanInteractorImpl interactor);

    void inject(CateDetailInteractorImpl interactor);
}
