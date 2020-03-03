package com.ailiangbao.alb.inject.user;


import com.ailiangbao.alb.HApplication;

import dagger.Module;

@Module
public class UserModule {
    private HApplication application;

    public UserModule(HApplication application) {
        this.application = application;
    }
}
