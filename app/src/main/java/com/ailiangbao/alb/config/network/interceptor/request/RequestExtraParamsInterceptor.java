package com.ailiangbao.alb.config.network.interceptor.request;


import com.wangjiegulu.dal.request.core.interceptor.IRequestInterceptor;
import com.wangjiegulu.dal.request.core.request.XRequest;

public class RequestExtraParamsInterceptor implements IRequestInterceptor {

    @Override
    public void onRequestIntercept(XRequest xRequest) throws Throwable {
//        xRequest.addParameter("X-Parse-Cid", "1")
//                .addParameter("imei", "imei");
//        UserInteractor userInteractor = HApplication.getInstance().userComponent.providerUserInteractor();
//        if (null != userInteractor) {
//            String userToken = userInteractor.getUserToken();
//            String userID = userInteractor.getUserID();
//            if (!TextUtils.isEmpty(userID) && !TextUtils.isEmpty(userToken)) {
//                xRequest.addParameter("X-Parse-Session-Token", userToken)
//                        .addParameter("X-Parse-User-Id", userID);
//            } else {
//                XLog.d("RequestExtraParamsInterceptor", "公参数----->userID与Token为空");
//            }
//        }
//                .addParameter("version", appInfoUtil.getVersionName(CSTvApplication.getInstance()))
//                .addParameter("vcode", appInfoUtil.getVersionCode(CSTvApplication.getInstance()))
//                .addParameter("os", appInfoUtil.getOs())
//                .addParameter("cpu", appInfoUtil.getCpu())
//                .addParameter("deviceId", appInfoUtil.getUuid())//设备唯一id"8c1ad9d17b3c40059dea5fe7457f698b"
//                .addParameter("devid", appInfoUtil.getDeviceId())//设备id,统计使用
//                .addParameter("umengDeviceToken", AppInfoUtil.getUmengDeviceToken())
////                .addParameter("network", NetUtil.getNetWorkSimpleStatus(LiveApplication.getInstance()))
//                .addParameter("channel", ChannelUtil.getChannel())
//                .addParameter("deviceName", appInfoUtil.getDeviceName())
//                .addParameter("isencrypt", /*true*/1)
//                .addParameter("times", TimeUtil.getCurrentTime())//时间参数不能当公参，因为要准确的北京时间，防止别人刷接口
//                .addParameter("nonce", StringUtils.randomString(6))
//                .addParameter("_xappkey", Config._XAPPKEY)
//                .addParameter("_t", System.currentTimeMillis() + "");
//        XLog.i("hll", "deviceId----->" + appInfoUtil.getUuid());
//        if (xRequest.isSign()) {
//            xRequest.addParameter("times", System.currentTimeMillis());
//            xRequest.addParameter("nonce", StringUtils.randomString(6));
//        }
//
//        // TODO: 2/28/17 wangjie 校验是否是自己服务器的请求
//        // 如果是登录状态，则每个请求加入token校验
//        UserInteractor userInteractor = HApplication.getInstance().userComponent.providerUserInteractor();
//        if (null != userInteractor && userInteractor.isLoginSync()) {
//            xRequest.addParameter("utoken", userInteractor.getUserToken());
//        }

    }

}
