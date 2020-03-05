package com.ailiangbao.provider.dal.net.http.webapi;

import android.net.Uri;
import android.util.Log;

import com.ailiangbao.provider.bll.application.ProviderApplication;
import com.ailiangbao.provider.dal.util.TextUtil;


/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/5/16.
 */
public final class WebApiConstants {
    private WebApiConstants() {

    }
    //线上测试服47.111.165.27
    private static final String HOST_STUFF = ProviderApplication.getInstance().isTestServer() ? "47.114.163.8:8081/ycqb-api" : "api.whwen.cn/ycqb-api";

    // http ITG (test)
    private static final String HOST_ITG = getITGHost();
    private static final String HTTP_URL_ITG = "http://" + HOST_ITG;
    private static final String HTTPS_URL_ITG = "http://" + HOST_ITG;

    // http STG
    private static final String HTTP_URL_STG = "http://" + HOST_STUFF;
    private static final String HTTPS_URL_STG = "http://" + HOST_STUFF;

    public static String getITGHost() {
        return ProviderApplication.getInstance().isTestServer() ? "47.114.163.8:8081/ycqb-api" : "api.whwen.cn/ycqb-api";
    }

    public static String getHttpHost() {
        boolean isDebug = ProviderApplication.getInstance().isBuildConfigDebug();
        return isDebug ? HTTP_URL_ITG : HTTP_URL_STG;
    }

    public static String getHttpsHost() {
        boolean isDebug = ProviderApplication.getInstance().isBuildConfigDebug();
        return isDebug ? HTTPS_URL_ITG : HTTPS_URL_STG;
    }

    public static String formatHttpWebApi(String httpWebApi) {
        String httpHost = getHttpHost();
        if (!httpWebApi.startsWith("http://") && !httpWebApi.contains(httpHost)) {
            httpWebApi = httpHost + httpWebApi;
        }
        Log.d("cq", "httpWebApi:" + httpWebApi);
        return httpWebApi;
    }

    public static String formatHttpsWebApi(String httpWebApi) {
        String httpsHost = getHttpsHost();
        if (!httpWebApi.contains(httpsHost)) {
            httpWebApi = httpsHost + httpWebApi;
        }
        return httpWebApi;
    }

    /**
     * 是否是自己服务器的请求
     */
    public static boolean isInternalUrl(String url) {
        if (null == url || url.startsWith("/")) {
            return false;
        }
        Uri uri = Uri.parse(url);
        String host = uri.getHost();
        return !TextUtil.isEmpty(host) && host.contains(HOST_STUFF);
    }

    //******************触手域名拼接start**************

    public static String getChuShouHttpHost() {
//        return "open.vchushou.com";
        return "openchat.chushou.tv";
    }

    public static String formatHttpWebApiForChuShou(String httpWebApi) {
        String httpHost = getChuShouHttpHost();
        if (!httpWebApi.startsWith("http://") && !httpWebApi.contains(httpHost)) {
            httpWebApi = "http://" + httpHost + httpWebApi;
        }
        return httpWebApi;
    }

    public static String formatHttpsWebApiForChuShou(String httpWebApi) {
        String httpHost = getChuShouHttpHost();
        if (!httpWebApi.startsWith("https://") && !httpWebApi.contains(httpHost)) {
            httpWebApi = "https://" + httpHost + httpWebApi;
        }
        Log.d("cq", "httpWebApi:" + httpWebApi);
        return httpWebApi;
    }
    //******************触手域名拼接end**************


    //******************当贝域名拼接start**************
    public static String getDBMarketHttpHost() {
//        http://api.downbei.com/dbapiwel/tasklistall.php
        return "api.downbei.com";
    }

    public static String formatHttpWebApiForDBMarket(String httpWebApi) {
        String httpHost = getDBMarketHttpHost();
        if (!httpWebApi.startsWith("http://") && !httpWebApi.contains(httpHost)) {
            httpWebApi = "http://" + httpHost + httpWebApi;
        }
        Log.d("cq", "httpWebApi:" + httpWebApi);
        return httpWebApi;
    }

    public static boolean isDBHost(String url) {
        String host = getDBMarketHttpHost();
        return url.contains(host);
    }
    //******************当贝域名拼接end**************

    public static String formatHttpAuthWebApi(String httpWebApi) {
        String httpHost = getAuthHttpHost();
        if (!httpWebApi.startsWith("http://") && !httpWebApi.contains(httpHost)) {
            httpWebApi = "http://" + httpHost + httpWebApi;
        }
        return httpWebApi;
    }

    private static String getAuthHttpHost() {
        return ProviderApplication.getInstance().isTestServer() ? "api.mobile.auth.huadle.com" : "api.mobile.auth.xiaozhoudao.net";
    }

    public static String formatHttpXMallWebApi(String httpWebApi) {
        String httpHost = getXMallHttpHost();
        if (!httpWebApi.startsWith("http://") && !httpWebApi.contains(httpHost)) {
            httpWebApi = "http://" + httpHost + httpWebApi;
        }
        return httpWebApi;
    }

    private static String getXMallHttpHost() {
        return ProviderApplication.getInstance().isTestServer() ? "api.mobile.xmall.huadle.com" : "api.mobile.xmall.xiaozhoudao.net";
    }
}
