package com.ailiangbao.alb.config.network.interceptor.request;

import com.ailiangbao.alb.BuildConfig;
import com.ailiangbao.provider.dal.net.http.webapi.WebApi;
import com.ailiangbao.provider.dal.net.http.webapi.WebApiConstants;
import com.ailiangbao.provider.dal.util.sign.DesHelper;
import com.wangjiegulu.dal.request.core.interceptor.IRequestInterceptor;
import com.wangjiegulu.dal.request.core.request.XRequest;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class RequestSignAndEncryptInterceptor implements IRequestInterceptor {

    //    public static DesHelper DES_HELPER = new DesHelper(getSecretKey(), getSecretKey().getBytes());
    private static final String _SIGN = "_sign";
    private static final String SIGN = "sign";
    private static final String PARAM = "param";

    @Override
    public void onRequestIntercept(XRequest xRequest) throws Throwable {
        TreeMap<String, String> params = xRequest.getParameters();
        if (null != params) {
            //当没有参数时就不进行加密操作
            TreeMap<String, String> submitParams = new TreeMap();
//            Builder uriBuilder = new Builder();

            Iterator var5 = params.entrySet().iterator();

            while (var5.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) var5.next();
                String key = entry.getKey();
                String value = entry.getValue();
                submitParams.put(key, value);
//                uriBuilder.appendQueryParameter(key, value);
            }

//            String signValue = MD5Util.md5(getSecretKey() + uriBuilder.build().getQuery());
//            submitParams.put("sign", signValue);
//            uriBuilder.build().getQuery();
            xRequest.setSubmitParameters(submitParams);
        }

    }

//    public static String getSecretKey() {
//        return SKAgent.getKey(SKAgent.HaquKey.KEY_HAQU_SECRETKEY);
//    }

}
