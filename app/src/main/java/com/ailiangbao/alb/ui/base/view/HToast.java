package com.ailiangbao.alb.ui.base.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Space;
import android.widget.TextView;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.util.ResUtil;
import com.dangbei.xlog.XLog;

import java.util.Timer;
import java.util.TimerTask;

public class HToast extends Dialog {

    public final String TAG = HToast.class.getSimpleName();

    private TextView messageTv;
    private static HToast sDialog;
    private Timer mTimer;
    private TimerTask mTask;
    private ScaleAnimation mEnterAnimation;
    private int duration = 3000;
    private AlphaAnimation mEnterAlphaAnimation;

    private HToast(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        mEnterAnimation = new ScaleAnimation(0.6f, 1, 0.6f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mEnterAlphaAnimation = new AlphaAnimation(0, 1);
        mEnterAnimation.setDuration(200);
    }

    public static HToast getInstance(@NonNull Context context) {
        if (sDialog == null) {
            sDialog = new HToast(context, R.style.Toast_Dialog);
            Window window = sDialog.getWindow();
            if (window != null) {
                window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (!Settings.canDrawOverlays(LiveApplication.getInstance())) {//targetSdkVersion必须<23时检查权限是否已获取的方法
//                        window.setType(WindowManager.LayoutParams.TYPE_TOAST);
//                    } else {
//                        //设置系统级弹窗
//                        window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                    }
//                } else {
//                }
                //设置系统级弹窗
                window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
            }
            sDialog.setCanceledOnTouchOutside(true);
        }

        return sDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_custom_toast);
        messageTv = (TextView) findViewById(R.id.tv_custom_toast_message);
        messageTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResUtil.getDimensionPixelSize(R.dimen.size_15sp));
//        ScreenAdapter.scaleTxtSize(messageTv, 38);
        Space space = (Space) findViewById(R.id.view_custom_toast_space);
//        ScreenAdapter.scaleView(space, ViewGroup.LayoutParams.WRAP_CONTENT, 60);
    }

    public void showToast(String text) {
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (mTask != null) {
            mTask.cancel();
        }
        mTimer = null;
        mTask = null;

        if (!isShowing()) {
            show();
        }
        if (messageTv != null) {
            messageTv.setText(text);
        }
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                messageTv.post(() -> hideToast());
            }
        };

        mTimer.schedule(mTask, duration);
    }

    public void showToast(SpannableString text) {
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (mTask != null) {
            mTask.cancel();
        }
        mTimer = null;
        mTask = null;

        if (!isShowing()) {
            show();
        }
        if (messageTv != null) {
            messageTv.setText(text);
        }
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                messageTv.post(() -> hideToast());
            }
        };

        mTimer.schedule(mTask, duration);
    }

    @Override
    protected void onStart() {
        super.onStart();
        messageTv.startAnimation(mEnterAlphaAnimation);
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            XLog.d(TAG, e.getMessage());
        }
    }

    private void hideToast() {
        dismiss();
    }

    /**
     * 设置显示时长
     */
    public HToast setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * 设置布局
     */
    public void setView(View view) {
        setContentView(view);
    }

    /**
     * 设置布局
     */
    public void setView(int layoutId) {
        setContentView(layoutId);
    }
}
