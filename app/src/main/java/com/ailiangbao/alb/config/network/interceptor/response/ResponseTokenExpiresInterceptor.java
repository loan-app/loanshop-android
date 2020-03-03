package com.ailiangbao.alb.config.network.interceptor.response;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.ailiangbao.alb.HApplication;
import com.ailiangbao.alb.ui.login.LoginActivity;
import com.ailiangbao.alb.util.ActivityManageUtils;
import com.ailiangbao.provider.dal.net.http.response.BaseHttpResponse;
import com.ailiangbao.provider.dal.net.http.response.splash.SplashResponse;
import com.wangjiegulu.dal.request.core.interceptor.IResponseInterceptor;
import com.wangjiegulu.dal.request.core.request.XRequest;


public class ResponseTokenExpiresInterceptor implements IResponseInterceptor {

    @Override
    public void onResponseIntercept(XRequest xRequest, Object response) throws Throwable {
        BaseHttpResponse baseHttpResponse = null;
        if (response instanceof BaseHttpResponse) {
            baseHttpResponse = (BaseHttpResponse) response;
        }
        if (baseHttpResponse == null) {
            return;
        }
        if (baseHttpResponse.isBizSucceed()) {
            return;
        }
        if (baseHttpResponse instanceof SplashResponse) {
            return;
        }

        if (baseHttpResponse.isLoginCode()) {
            startLogin();
        }
//        String message = baseHttpResponse.getMessage();
//        XLog.i("hll", "服务器返回的异常" + message);
////        if (ResponseCode.ERROR_TOKEN_EXPIRES.equals(message)) {
////            //当用户在其他设备上登陆后，对当前设备的一些处理
////            if (CSTvApplication.getInstance().userComponent.providerUserInteractor().isLoginSync()) {
////                CSTvApplication.getInstance().logoutEvent("该账户已在其它设备登录");
////            }
////        }
//        if (baseHttpResponse instanceof OrderNumResponse || baseHttpResponse instanceof LiveUrlResponse) {
////            if (BuildConfig.DEBUG) {
////                PersonalInfoEntity userInfoEntity = CSTvApplication.getInstance().userComponent.providerUserInteractor().requestCurrentUserInfoSyn();
////                if (!userInfoEntity.isLoginState() && RoomActivity.class.getName().equals(ActivityLifecycleManager.get().getTopActivityClassName())) {
////                    if (baseHttpResponse instanceof LiveUrlResponse) {
////                        ((LiveUrlResponse) baseHttpResponse).setUrl(null);
////                        message = ResponseCode.ERROR_TOKEN_EXPIRES;
////                    }
////                } else if (!userInfoEntity.isVipStatus() && !MainActivity.class.getName().equals(ActivityLifecycleManager.get().getTopActivityClassName())) {
////                    message = ResponseCode.ERROR_NO_VIP;
////                    if (baseHttpResponse instanceof LiveUrlResponse) {
////                        ((LiveUrlResponse) baseHttpResponse).setUrl(null);
////                    }
////                }
////                baseHttpResponse.setMessage(message);
////            }
//            switch (message) {
//                case ResponseCode.ERROR_TOKEN_EXPIRES:
//                    startLogin();
//                    break;
//                case ResponseCode.ERROR_NO_VIP:
//                    startVip();
//                    break;
////                case ResponseCode.ERROR_PARAM_TIME_OUT:
////                    againUpDateNetTime();
////                    break;
//                default:
//                    break;
//            }
//        }
    }

    //    private void againUpDateNetTime() {
//        //重新初始化时间,并重新请求当前数据
//        HApplication.getInstance().initNetTime();
//    }
//
//    private void startVip() {
//        if (!VIPActivity.class.getName().equals(ActivityLifecycleManager.get().getTopActivityClassName())) {
//            Context context = HApplication.getInstance();
//            Intent intent = new Intent(context, VIPActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        }
//    }
//
    private void startLogin() {
        // 如果Response 返回Token过期，则判断当前是否在登录界面，如果不是，则跳转过去
        AppCompatActivity topActivity = ActivityManageUtils.getTopActivity();
        if (topActivity == null || !LoginActivity.class.getName().equals(topActivity.getClass().getName())) {
            Context context = HApplication.getInstance();
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        if (HApplication.getInstance().userComponent.providerUserInteractor().isLoginSync()) {
            HApplication.getInstance().logoutEvent("该账户已在其它设备登录");
        }
    }
}
