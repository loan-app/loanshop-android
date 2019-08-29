package com.ailiangbao.alb.config.network.interceptor.request;

import com.ailiangbao.alb.HApplication;
import com.ailiangbao.provider.bll.interactor.contract.UserInteractor;
import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;
import com.dangbei.xlog.XLog;
import com.wangjiegulu.dal.request.core.interceptor.IRequestInterceptor;
import com.wangjiegulu.dal.request.core.request.XRequest;

public class RequestHeaderInterceptor implements IRequestInterceptor {
    @Override
    public void onRequestIntercept(XRequest xRequest) throws Throwable {
//        xRequest.addHeader("Accept-Encoding", "gzip");
//        xRequest.addHeader("Content-Type", "application/json;charset=utf-8");
//        xRequest.addHeader("X-Parse-Cid", "1")
//                .addHeader("imei", "imei");
        UserInteractor userInteractor = HApplication.getInstance().userComponent.providerUserInteractor();
        if (null != userInteractor) {
            UserInfoEntity userInfoEntity = userInteractor.requestCurrentUserInfoSyn();
            if (userInfoEntity != null) {
                String userToken = userInfoEntity.getToken();
                xRequest.addHeader("TOKEN", userToken);
            } else {
                XLog.d("RequestExtraParamsInterceptor", "公参数----->Token为空");
            }
        }
    }
}
