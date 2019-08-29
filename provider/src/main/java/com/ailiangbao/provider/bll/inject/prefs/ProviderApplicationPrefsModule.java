package com.ailiangbao.provider.bll.inject.prefs;

import android.content.Context;

import com.ailiangbao.provider.bll.inject.scope.Provider_Scope_Application;
import com.ailiangbao.provider.dal.prefs.PrefsConstants;
import com.ailiangbao.provider.dal.prefs.PrefsHelper;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ProviderApplicationPrefsModule {

    @Provides
    @Named(PrefsConstants.PREFS_GLOBAL)
    @Provider_Scope_Application
    public PrefsHelper providerGlobalPrefsHelper() {
        return new PrefsHelper("h_global_prefs", getModeCompat());
    }

    private static int getModeCompat() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            return Context.MODE_MULTI_PROCESS;
//        }
        return Context.MODE_PRIVATE;
    }
}
