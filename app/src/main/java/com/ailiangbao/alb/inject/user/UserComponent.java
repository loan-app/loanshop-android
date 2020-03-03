package com.ailiangbao.alb.inject.user;


import com.ailiangbao.alb.inject.app.AppComponent;
import com.ailiangbao.alb.inject.modules.InteractorModule;
import com.ailiangbao.alb.inject.scope.Scope_User;
import com.ailiangbao.provider.bll.interactor.contract.AllLoanInteractor;
import com.ailiangbao.provider.bll.interactor.contract.CateDetailInteractor;
import com.ailiangbao.provider.bll.interactor.contract.HomeInteractor;
import com.ailiangbao.provider.bll.interactor.contract.LoginInteractor;
import com.ailiangbao.provider.bll.interactor.contract.MainInteractor;
import com.ailiangbao.provider.bll.interactor.contract.SplashInteractor;
import com.ailiangbao.provider.bll.interactor.contract.UserInteractor;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = {UserModule.class, InteractorModule.class})
@Scope_User
public interface UserComponent {
    MainInteractor providerMainInteractor();

    UserInteractor providerUserInteractor();

    LoginInteractor providerLoginInteractor();

    HomeInteractor providerHomeInteractor();

    SplashInteractor providerSplashInteractor();

    AllLoanInteractor providerAllLoanInteractor();

    CateDetailInteractor providerCateDetailInteractor();

}
