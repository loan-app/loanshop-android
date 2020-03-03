package com.ailiangbao.alb.ui.main.my;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.ui.base.BaseFragment;
import com.ailiangbao.alb.ui.main.my.view.ItemView;
import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;

import javax.inject.Inject;

public class MyFragment extends BaseFragment implements MyContract.IMyViewer {

    @Inject
    MyPresenter presenter;
    private TextView telephoneNumTv;

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewerComponent().inject(this);
        presenter.bind(this);
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_my;
    }

    @Override
    protected void init() {
        telephoneNumTv = view.findViewById(R.id.fragment_my_telephone_num);
        ItemView item01 = view.findViewById(R.id.fragment_my_item_01);
        ItemView item03 = view.findViewById(R.id.fragment_my_item_03);
        ItemView item04 = view.findViewById(R.id.fragment_my_item_04);
        item01.setTitleTv("关于我们");
        item03.setTitleTv("联系我们");
        item04.setTitleTv("我的设置");
        item04.hideLine();
        item01.setIconIv(R.mipmap.ic_my_about);
        item03.setIconIv(R.mipmap.ic_my_telephone);
        item04.setIconIv(R.mipmap.ic_my_setting);
        initData();
    }

    @Override
    public ViewGroup getLoadingForViewGroup() {
        return null;
    }

    private void initData() {
        presenter.queryUserInfo();
    }

    @Override
    public void onQueryUserInfo(UserInfoEntity userInfoEntity) {
        if (userInfoEntity == null) return;
        telephoneNumTv.setText(userInfoEntity.getUserId());
    }
}
