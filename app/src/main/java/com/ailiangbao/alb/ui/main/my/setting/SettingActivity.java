package com.ailiangbao.alb.ui.main.my.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ailiangbao.alb.HApplication;
import com.ailiangbao.alb.R;
import com.ailiangbao.alb.ui.base.BaseActivity;
import com.ailiangbao.alb.ui.base.view.TabBarView;
import com.ailiangbao.alb.ui.login.LoginActivity;
import com.ailiangbao.alb.util.ActivityManageUtils;
import com.ailiangbao.alb.util.GlideUtil;
import com.ailiangbao.alb.util.JumpUtils;
import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.cancelLoading(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init() {
        TabBarView tabBar = findViewById(R.id.activity_setting_tab_bar);
        ImageView logoIv = findViewById(R.id.activity_setting_logo_iv);
        tabBar.setActivity(this);
        tabBar.setNameTv("设置");
        GlideUtil.loadLocalImageRadius(this, logoIv, R.mipmap.ic_logo_miao, 0, 10);
//        ItemView item01 = findViewById(R.id.activity_setting_item_01);
//        item01.hideIcon();
//        item01.hideLine();
//        item01.setTitleTv("设置登录密码");
//        ItemView item02 = findViewById(R.id.activity_setting_item_02);
//        item02.hideIcon();
//        item02.hideLine();
//        item02.setTitleTv("修改登录密码");
        TextView outTv = findViewById(R.id.activity_setting_out_tv);
        outTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        HApplication.getInstance().switchUser(UserInfoEntity.USER_NOT_LOGIN_USER_ID, null);
        ActivityManageUtils.clearActivity();
        JumpUtils.startActivity(this, LoginActivity.class);
        finish();
    }
}
