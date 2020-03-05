package com.ailiangbao.alb.ui.main.my.about;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.ui.base.BaseActivity;
import com.ailiangbao.alb.ui.base.view.TabBarView;
import com.ailiangbao.alb.util.AppInfoUtil;
import com.ailiangbao.alb.util.GlideUtil;

public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.cancelLoading(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();
    }

    private void init() {
        TabBarView tabBar = findViewById(R.id.activity_about_tab_bar);
        tabBar.setActivity(this);
        tabBar.setNameTv("关于我们");
//        Button loginOutBt = findViewById(R.id.activity_setting_login_out);
        ImageView logoIv = findViewById(R.id.activity_about_iv);
        GlideUtil.loadLocalImageRadius(this, logoIv, R.mipmap.ic_logo_yicai, 0, 10);
        TextView versionTv = findViewById(R.id.activity_about_version_tv);
        versionTv.setText("版本号 " + AppInfoUtil.getVersionName(this));
//        loginOutBt.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View v) {
//        HApplication.getInstance().switchUser(UserInfoEntity.USER_NOT_LOGIN_USER_ID, null);
//        ActivityManageUtils.clearActivity();
//        JumpUtils.startActivity(this, LoginActivity.class);
//        finish();
//    }
}
