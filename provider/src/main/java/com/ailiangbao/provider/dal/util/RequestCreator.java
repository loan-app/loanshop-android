package com.ailiangbao.provider.dal.util;

import com.ailiangbao.provider.bll.application.ProviderApplication;
import com.ailiangbao.provider.dal.net.http.webapi.WebApiConstants;
import com.wangjiegulu.dal.request.core.request.XRequest;

/**
 * Created by zhanghongjie on 2018/4/10.
 */

public class RequestCreator {

    private GsonResponseConverter converter = new GsonResponseConverter();

    public XRequest createRequest(String httpUrl) {
        return createRequest(httpUrl, false);
    }

    public XRequest createRequest(String httpUrl, boolean isChuShouHost) {
        return XRequest.create(isChuShouHost ? WebApiConstants.formatHttpsWebApiForChuShou(httpUrl)
                : (ProviderApplication.getInstance().isTestServer() ? WebApiConstants.formatHttpWebApi(httpUrl)
                : WebApiConstants.formatHttpsWebApi(httpUrl)))
                .setResponseConverter(converter);
    }

    public XRequest createAuthRequest(String httpUrl) {
        return XRequest.create(WebApiConstants.formatHttpAuthWebApi(httpUrl)).setResponseConverter(converter);
    }

    public XRequest createXMallRequest(String httpUrl) {
        return XRequest.create(WebApiConstants.formatHttpXMallWebApi(httpUrl)).setResponseConverter(converter);
    }

//    public XRequest createRequestForDBMarket(String httpUrl) {
//        return XRequest.create(WebApiConstants.formatHttpWebApiForDBMarket(httpUrl)).setResponseConverter(converter);
//    }
}
