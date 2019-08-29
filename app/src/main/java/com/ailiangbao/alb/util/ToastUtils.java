package com.ailiangbao.alb.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.SpannableString;
import android.view.View;
import android.widget.Toast;

import com.ailiangbao.alb.BuildConfig;
import com.ailiangbao.alb.HApplication;
import com.ailiangbao.alb.ui.base.view.HToast;
import com.ailiangbao.provider.dal.util.TextUtil;


/**
 * Created by admin
 * 2017/5/2
 */
public class ToastUtils {

    private static Toast toast;

    public static void toast(String msg) {
        if (!TextUtil.isEmpty(msg)) {
            showCustomToast(msg);
        }
    }

    public static void toast(int msgResId) {
        showCustomToast(HApplication.getInstance().getResources().getText(msgResId).toString());
    }

    public static void toastErrorOnDebug(String msg) {
        if (BuildConfig.DEBUG && !TextUtil.isEmpty(msg)) {
            Toast.makeText(HApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void toastErrorOnDebug(int msgResId) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(HApplication.getInstance(), msgResId, Toast.LENGTH_SHORT).show();
        }
    }

    public static void toastTemp(String msg) {
        Toast.makeText(HApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showCustomToast(String msg) {
        showCustomToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showCustomToast(SpannableString msg) {
        Context context = HApplication.getInstance();
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(context)) {
                //需要SYSTEM_ALERT_WINDOW权限时，使用系统默认toast
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            } else {
                HToast.getInstance(context).showToast(msg);
            }
        } else {
            HToast.getInstance(context).showToast(msg);
        }
    }

    public static void showCustomToast(String msg, int duration) {
        Context context = HApplication.getInstance();
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(context)) {
                //需要SYSTEM_ALERT_WINDOW权限时，使用系统默认toast
                if (toast == null) {
                    toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                        @Override
                        public void onViewAttachedToWindow(View v) {

                        }

                        @Override
                        public void onViewDetachedFromWindow(View v) {
                            toast = null;
                        }
                    });
                    toast.setGravity(17, 0, 0);
                }
                if (duration > 0) {
                    toast.setDuration(duration);
                }
                toast.setText(msg);
                toast.show();
            } else {
                HToast.getInstance(context).showToast(msg);
            }
        } else {
            HToast.getInstance(context).showToast(msg);
        }
    }
}
