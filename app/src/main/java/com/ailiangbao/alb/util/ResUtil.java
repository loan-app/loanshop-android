package com.ailiangbao.alb.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.ailiangbao.alb.HApplication;


/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/5/16.
 */
public class ResUtil {
    private static Boolean isInTouchMode;

    public static int dip2px(float dpValue) {
        try {
            final float scale = HApplication.getInstance().getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } catch (Throwable throwable) {
            // ignore
        }
        return 0;
    }

    public static int sp2px(float spValue) {
        try {
            float fontScale = HApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        } catch (Throwable throwable) {
            // ignore
        }
        return 0;
    }

    public static int px2dip(float pxValue) {
        try {
            float scale = HApplication.getInstance().getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        } catch (Throwable throwable) {
            // ignore
        }
        return 0;
    }

    public static String getString(int resId) {
        try {
            return HApplication.getInstance().getString(resId);
        } catch (Throwable throwable) {
            // ignore
        }
        return "";
    }

    public static String getString(int resId, Object... formatArgs) {
        try {
            return HApplication.getInstance().getString(resId, formatArgs);
        } catch (Throwable throwable) {
            // ignore
        }
        return "";
    }

    public static String[] getArrayString(int resId) {
        try {
            return HApplication.getInstance().getResources().getStringArray(resId);
        } catch (Throwable throwable) {
            // ignore
        }
        return new String[0];
    }

    public static int getColor(int resId) {
        try {
            return HApplication.getInstance().getResources().getColor(resId);
        } catch (Throwable throwable) {
            // ignore
        }
        return 0;
    }

    public static float getDimension(int resId) {
        try {
            return HApplication.getInstance().getResources().getDimension(resId);
        } catch (Throwable throwable) {
            // ignore
        }
        return 0;
    }

    /**
     * 设置字体大小
     * setTextSize()为了适配，参数除了大小外还需要加入TypedValue.COMPLEX_UNIT_PX 参数
     */
    public static float getDimensionPixelSize(int resId) {
        try {
            return HApplication.getInstance().getResources().getDimensionPixelSize(resId);
        } catch (Throwable throwable) {
            // ignore
        }
        return 0;
    }

    public static Drawable getDrawable(int resId) {
        try {
            return ContextCompat.getDrawable(HApplication.getInstance(), resId);
        } catch (Throwable throwable) {
            // ignore
        }
        return new ColorDrawable(Color.TRANSPARENT);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getWindowWidth() {
        DisplayMetrics dm = HApplication.getInstance().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getWindowHeight() {
        DisplayMetrics dm = HApplication.getInstance().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static void recycleBitmap(Bitmap... bitmaps) {
        for (Bitmap bitmap : bitmaps) {
            if (null != bitmap && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    public static Boolean isInTouchMode() {
        return isInTouchMode;
    }

    public static void setIsInTouchMode(Boolean isInTouchMode) {
        ResUtil.isInTouchMode = isInTouchMode;
    }

    public static boolean isScrollToBottom(RecyclerView recyclerView) {
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() < recyclerView.computeVerticalScrollRange();
    }
}
