package com.ailiangbao.alb.ui.login;

import android.os.Bundle;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.ui.base.BaseActivity;

public class LoginTkActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.cancelLoading(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tk);
    }
}
