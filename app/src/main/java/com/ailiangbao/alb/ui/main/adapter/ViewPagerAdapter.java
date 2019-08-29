package com.ailiangbao.alb.ui.main.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ailiangbao.alb.ui.base.BaseFragment;
import com.ailiangbao.provider.dal.util.collection.CollectionUtil;

import java.util.List;

/**
 * author:hll
 * time:2018/7/3
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> list;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public BaseFragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return CollectionUtil.isEmpty(list) ? 0 : list.size();
    }

    public void setList(List<BaseFragment> list) {
        this.list = list;
    }
}
