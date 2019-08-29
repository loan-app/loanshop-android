package com.ailiangbao.alb.inject.app;


import com.ailiangbao.alb.HApplication;
import com.ailiangbao.alb.inject.scope.Scope_Application;
import com.ailiangbao.provider.bll.interactor.contract.GlobalInteractor;
import com.ailiangbao.provider.bll.interactor.impl.GlobalInteractorImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private HApplication application;

    public AppModule(HApplication application) {
        this.application = application;
    }

    @Provides
    @Scope_Application
    GlobalInteractor providerGlobalInteractor() {
        return new GlobalInteractorImpl();
    }

}
