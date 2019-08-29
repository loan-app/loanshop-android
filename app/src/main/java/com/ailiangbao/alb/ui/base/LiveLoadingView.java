package com.ailiangbao.alb.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.config.Config;
import com.ailiangbao.alb.util.ResUtil;
import com.github.ybq.android.spinkit.SpinKitView;


/**
 * Created by yhzhao
 * 2017/11/21
 */

public class LiveLoadingView extends Dialog {
    private LoadingView loadingView;
    private FrameLayout rootLayout;
    private SpinKitView loading;
    private TextView messageTv;

    public LiveLoadingView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LiveLoadingView(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected LiveLoadingView(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(ResUtil.getDrawable(R.color.black_55));
            window.setGravity(Gravity.CENTER);
        }
        setCancelable(false);
//        loadingView = new LoadingView(context);
//        loadingView.setLayoutParams(-2, -2, 480);
//        loadingView.setTextColor(R.color.white);
//        loadingView.setTextSize(24);
//        rootLayout = new FrameLayout(context);
//        rootLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        loading = findViewById(R.id.dialog_loading);
        messageTv = findViewById(R.id.dialog_loading_message);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Config.LOADING_BACK && keyCode == KeyEvent.KEYCODE_BACK) {
            dismissLoading();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showLoading(String message) {
        show();
//        loadingView.startLoading(rootLayout);
        loading.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(message)) {
            messageTv.setText(message);
        }
    }

    public void dismissLoading() {
//        loadingView.stopLoading(rootLayout);
        loading.setVisibility(View.INVISIBLE);
        dismiss();
    }
}
