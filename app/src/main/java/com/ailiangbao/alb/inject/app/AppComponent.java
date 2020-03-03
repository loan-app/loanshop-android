package com.ailiangbao.alb.inject.app;


import com.ailiangbao.alb.HApplication;
import com.ailiangbao.alb.inject.scope.Scope_Application;
import com.ailiangbao.provider.bll.interactor.contract.GlobalInteractor;

import dagger.Component;


@Component(modules = AppModule.class)
@Scope_Application
public interface AppComponent {

    void inject(HApplication application);

    GlobalInteractor providerGlobalInteractor();
}
