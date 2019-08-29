package com.ailiangbao.alb.inject.modules;


import com.ailiangbao.alb.inject.scope.Scope_User;
import com.ailiangbao.provider.bll.interactor.contract.AllLoanInteractor;
import com.ailiangbao.provider.bll.interactor.contract.CateDetailInteractor;
import com.ailiangbao.provider.bll.interactor.contract.HomeInteractor;
import com.ailiangbao.provider.bll.interactor.contract.LoginInteractor;
import com.ailiangbao.provider.bll.interactor.contract.MainInteractor;
import com.ailiangbao.provider.bll.interactor.contract.SplashInteractor;
import com.ailiangbao.provider.bll.interactor.contract.UserInteractor;
import com.ailiangbao.provider.bll.interactor.impl.AllLoanInteractorImpl;
import com.ailiangbao.provider.bll.interactor.impl.CateDetailInteractorImpl;
import com.ailiangbao.provider.bll.interactor.impl.HomeInteractorImpl;
import com.ailiangbao.provider.bll.interactor.impl.LoginInteractorImpl;
import com.ailiangbao.provider.bll.interactor.impl.MainInteractorImpl;
import com.ailiangbao.provider.bll.interactor.impl.SplashInteractorImpl;
import com.ailiangbao.provider.bll.interactor.impl.UserInteractorImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorModule {
    @Provides
    @Scope_User
    public MainInteractor providerMainInteractor() {
        return new MainInteractorImpl();
    }

    @Provides
    @Scope_User
    public UserInteractor providerUserInteractor() {
        return new UserInteractorImpl();
    }

    @Provides
    @Scope_User
    public LoginInteractor providerLoginInteractor() {
        return new LoginInteractorImpl();
    }

    @Provides
    @Scope_User
    public HomeInteractor providerHomeInteractor() {
        return new HomeInteractorImpl();
    }

    @Provides
    @Scope_User
    public SplashInteractor providerSplashInteractor() {
        return new SplashInteractorImpl();
    }

    @Provides
    @Scope_User
    public AllLoanInteractor providerAllLoanInteractor() {
        return new AllLoanInteractorImpl();
    }

    @Provides
    @Scope_User
    public CateDetailInteractor providerCateDetailInteractor() {
        return new CateDetailInteractorImpl();
    }
}
