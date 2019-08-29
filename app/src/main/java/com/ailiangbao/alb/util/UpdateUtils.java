package com.ailiangbao.alb.util;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.ailiangbao.alb.ui.main.dialog.UpdateDialog;

public class UpdateUtils {
    @SuppressLint("StaticFieldLeak")
    private static UpdateDialog updateDialog;

    public static void checkUpdate(Activity activity, String url) {
        if (url.endsWith(".apk")) {
            showUpdateDialog(activity, url);
        }
    }

    private static void showUpdateDialog(Activity activity, String url) {
        if (updateDialog == null) {
            updateDialog = new UpdateDialog(activity, url);
            updateDialog.setOnUpdateDialogListener((UpdateDialog.OnUpdateDialogListener) activity);
        }
        updateDialog.show();
    }

    public static void download() {
        if (updateDialog != null) {
            updateDialog.download();
        }
    }
}
