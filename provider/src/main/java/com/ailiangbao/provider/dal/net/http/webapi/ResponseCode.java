package com.ailiangbao.provider.dal.net.http.webapi;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/21/16.
 */
public class ResponseCode {
    /**
     * 登录状态过期（token过期）
     */
    public static final String ERROR_TOKEN_EXPIRES = "nologin";

    public static final String ERROR_NO_VIP = "noVip";
    /**
     * 表示当前的时间参数与服务器的时间相差超过了5分钟
     */
    public static final String ERROR_PARAM_TIME_OUT = "paramTimeOut";

    public static final String ERROR_UN_KNOWN = "unknown";

//    public static final int SUCCEED_LOGIN_SELECT_CATE = 6214;

}
