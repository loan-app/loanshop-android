package com.ailiangbao.provider.bll.inject.application;


import com.ailiangbao.provider.bll.interactor.impl.GlobalInteractorImpl;
import com.ailiangbao.provider.bll.inject.prefs.ProviderApplicationPrefsModule;
import com.ailiangbao.provider.bll.inject.scope.Provider_Scope_Application;
import com.ailiangbao.provider.dal.prefs.PrefsConstants;
import com.ailiangbao.provider.dal.prefs.PrefsHelper;

import javax.inject.Named;

import dagger.Component;

@Component(modules = {ProviderApplicationPrefsModule.class})
@Provider_Scope_Application
public interface ProviderApplicationComponent {
    @Named(PrefsConstants.PREFS_GLOBAL)
    PrefsHelper providerGlobalPrefsHelper();

    void inject(GlobalInteractorImpl interactor);
}
