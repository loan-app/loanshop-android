package com.ailiangbao.alb.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import com.ailiangbao.alb.HApplication;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/5/16.
 */
public class AppInfoUtil {

    private static AppInfoUtil instance;
    private String appVersion;
    private String appCode;
    private String cpu;
    private static String channel;
    private String deviceId;
    private String deviceName;
    private String uuid;

//    private AppInfoUtil(Context context) {
//
//        appVersion = getVersionName(context);
//        appCode = String.valueOf(getVersionCode(context));
//        cpu = getCPU();
//        channel = getChannel(context);
////        deviceId = getDeviceId(context);
//        uuid = getUUID();
//        deviceName = Build.MODEL;
//
//    }

//    public static AppInfoUtil getInstance() {

//        if (instance == null) {
//            instance = new AppInfoUtil(HApplication.getInstance());
//        }
//        return instance;
//    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getAppCode() {
        return appCode;
    }

    public String getCpu() {
        return cpu;
    }

    public String getOs() {
        return Build.VERSION.RELEASE;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前应用程序的版本号
     *
     * @author wangjie
     */
    public static String getVersionName(Context context) {
        String version = "1.0.0";
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取当前应用程序的版本号
     *
     * @author wangjie
     */
    public int getVersionCode(Context context) {
        int version = 0;
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getCPU() {
        String abi = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            abi = Build.CPU_ABI;
        } else {
            abi = Build.SUPPORTED_ABIS[0];
        }
        return abi;
    }

    public static void setChannel(String channelCode) {
        channel = channelCode;
    }

    public static String getChannel() {
        return TextUtils.isEmpty(channel) ? "UnKnow" : channel;
    }

    public static String getChannel(Context context) {
        return getChannel();
//        try {
//            PackageManager pm = context.getPackageManager();
//            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
//            return appInfo.metaData.getString("UMENG_CHANNEL");
//        } catch (PackageManager.NameNotFoundException ignored) {
//        }
//        return "";
    }

    @SuppressLint("HardwareIds")
    public static String getMac() {
        String mac;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(HApplication.getInstance());
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacAddress();
        } else {
            mac = getMacFromHardware();
        }
        if (TextUtils.isEmpty(mac)) {
            mac = HApplication.getInstance().appComponent.providerGlobalInteractor().queryUUIDSyn();
            if (TextUtils.isEmpty(mac)) {
                String uuid = UUID.randomUUID().toString();
                HApplication.getInstance().appComponent.providerGlobalInteractor().saveUUID(uuid);
                mac = uuid;
            }
        }
        return mac;
    }

    /**
     * Android 7.0 获取mac地址
     *
     * @return
     */
    private static String getMacFromHardware() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return UUID.randomUUID().toString();
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UUID.randomUUID().toString();
    }

    /**
     * Android 6.0-Android 7.0 获取mac地址
     */
    public static String getMacAddress() {
        String macSerial = null;
        String str = "";

        try {
            Process pp = Runtime.getRuntime().exec("cat/sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            while (null != str) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();//去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }

        return macSerial;
    }

    /**
     * Android 6.0 之前（不包括6.0）获取mac地址
     * 必须的权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     *
     * @param context * @return
     */
    public static String getMacDefault(Context context) {
        String mac = "";
        if (context == null) {
            return mac;
        }
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }

    public static String getUUID() {
//        GlobalInteractor globalInteractor = HApplication.getInstance().appComponent.providerGlobalInteractor();
//        String uuid = globalInteractor.queryUUIDSyn();
//        if (TextUtils.isEmpty(uuid)) {
        //            globalInteractor.saveUUID(uuid);
//        }
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

//    public static String getUmengDeviceToken() {
//        return HApplication.getInstance().appComponent.providerGlobalInteractor().queryUmengDeviceToken();
//    }

//    public static String getDeviceId(Context context) {
//        String deviceId = null;
//        try {
//            TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            deviceId = telephony.getDeviceId();
//            if (deviceId == null || deviceId.length() <= 1) {
//                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//            }
//        } catch (Throwable throwable) {
//        }
//        if (null == deviceId) {
//            deviceId = "Unknown";
//        }
//        return deviceId;
//    }

    /**
     * 获得限定条件下的包名应用
     *
     * @param isSystemFilter 是否过滤系统应用
     * @param filterList     需要过滤的列表
     * @return 经过过滤条件下的包名列表
     */
    public List<String> getAllInstalledPackage(Context context, boolean isSystemFilter, List<String> filterList) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfo = packageManager.getInstalledPackages(0);
        List<String> packages = new ArrayList<>();
        List<String> defaultFilter = getDefaultList();
        if (packageInfo != null) {
            for (int i = 0, len = packageInfo.size(); i < len; i++) {
                if (isSystemFilter && ((ApplicationInfo.FLAG_SYSTEM & packageInfo.get(i).applicationInfo.flags) != 0)) {
                    //过滤掉系统app
                    continue;
                }
                String pn = packageInfo.get(i).packageName;
                //过滤掉需要过滤的列表
                if (filterList != null && filterList.contains(pn)) {
                    continue;
                }
                if (defaultFilter != null && defaultFilter.contains(pn)) {
                    continue;
                }
                packages.add(pn);
            }
        }
        return packages;
    }

    public static boolean isNetWorkAvailable(Context c) {
        ConnectivityManager cm;
        if (c != null) {
            cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo netWorkInfo = cm.getActiveNetworkInfo();
                if (netWorkInfo != null) {
                    return netWorkInfo.isAvailable();
                }
            }
        }

        return false;
    }

    private List<String> getDefaultList() {
        List<String> list = new ArrayList<>();
        list.add("com.dangbeimarket.lite");
        list.add("com.dangbeimarket");
        return list;
    }
}
