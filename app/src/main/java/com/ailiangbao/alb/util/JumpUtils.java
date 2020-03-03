package com.ailiangbao.alb.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class JumpUtils {
    public static void jumpQQ(Context context, String qq) {
        if (isQQClientAvailable(context)) {
            final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq + "&version=1";
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
        } else {
            Toast.makeText(context, "请安装QQ客户端", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void startActivity(Context context, Class<?> cls) {
        context.startActivity(new Intent(context, cls));
    }

    public static void startActivity(Context context, Class<?> cls, Bundle bundle, String bundleKey) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(bundleKey, bundle);
        context.startActivity(intent);
    }

}
