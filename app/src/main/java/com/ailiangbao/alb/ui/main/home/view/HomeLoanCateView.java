package com.ailiangbao.alb.ui.main.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.ailiangbao.provider.dal.net.http.entity.HomeEntity;
import com.ailiangbao.provider.dal.util.collection.CollectionUtil;

import java.util.List;

public class HomeLoanCateView extends LinearLayout {
    public HomeLoanCateView(Context context) {
        this(context, null);
    }

    public HomeLoanCateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeLoanCateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public HomeLoanCateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
//        params.leftMargin = ResUtil.dip2px(14);
//        params.rightMargin = ResUtil.dip2px(14);
        setLayoutParams(params);
        setGravity(Gravity.CENTER_VERTICAL);
        setWeightSum(4);
    }

    public void setList(List<HomeEntity.TypesBean> list) {
        if (CollectionUtil.isEmpty(list)) return;
        for (int i = 0; i < list.size(); i++) {
            HomeLoanCateItemView homeLoanCateItemView = new HomeLoanCateItemView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, -2, 1);
//            if (i == list.size() - 1) {
//                homeLoanCateItemView.setGravity(Gravity.END);
//            } else if (i == 0) {
//                homeLoanCateItemView.setGravity(Gravity.START);
//            } else {
            homeLoanCateItemView.setGravity(Gravity.CENTER_HORIZONTAL);
//            }
            addView(homeLoanCateItemView, params);
            homeLoanCateItemView.setData(list.get(i));
        }
    }
}
