package com.ailiangbao.alb.inject.viewer;


import com.ailiangbao.alb.inject.scope.Scope_Viewer;
import com.ailiangbao.alb.inject.user.UserComponent;
import com.ailiangbao.alb.ui.cate.CateDetailActivity;
import com.ailiangbao.alb.ui.login.LoginActivity;
import com.ailiangbao.alb.ui.main.MainActivity;
import com.ailiangbao.alb.ui.main.home.HomeFragment;
import com.ailiangbao.alb.ui.main.loan.AllLoanFragment;
import com.ailiangbao.alb.ui.main.my.MyFragment;
import com.ailiangbao.alb.ui.splash.SplashActivity;

import dagger.Component;

@Component(dependencies = UserComponent.class, modules = ViewerModule.class)
@Scope_Viewer
public interface ViewerComponent {

    void inject(MainActivity viewer);

    void inject(LoginActivity viewer);

    void inject(HomeFragment viewer);

    void inject(MyFragment viewer);

    void inject(SplashActivity viewer);

    void inject(AllLoanFragment viewer);

    void inject(CateDetailActivity viewer);
}
