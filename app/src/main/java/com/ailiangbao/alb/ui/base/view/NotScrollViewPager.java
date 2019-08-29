package com.ailiangbao.alb.ui.base.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * author: hll
 * 内部包含左右滑动的控件的ViewPager，防止左右切换时切换了viewPager
 */

public class NotScrollViewPager extends ViewPager {

    public NotScrollViewPager(Context context) {
        super(context);
    }

    public NotScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean executeKeyEvent(KeyEvent event) {
        return false;
    }

    /**
     * return false 禁止触摸时左右滑动
     */
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        return false;
    }

    /**
     * return false 禁止按键时左右滑动
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }

    //去除页面切换时的滑动翻页效果
    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item, smoothScroll);
    }

//    @Override
//    public void setCurrentItem(int item) {
//        // TODO Auto-generated method stub
//        super.setCurrentItem(item, false);
//    }

}
