package com.ailiangbao.provider.support.bridge.compat.subscriber;

import android.support.annotation.VisibleForTesting;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 10/31/16.
 */
public class RxCompatException extends RuntimeException {
    public static final int CODE_FOR_TEST = 0x998899;
    public static final int CODE_DEFAULT = 0x998833;
    public static final int CODE_NETWORK = 0x998832;
    public static final int ERROR_NO_LOGIN = 0x998831;
    public static final int ERROR_NO_VIP = 0x998830;
//    public static final int CODE_LOGIN = 1001;//表示用户没有登录，1001是随便写的，主要看服务器上定义未登录的code是多少
//    public static final int CODE_VIP = 1002;
    public static final String ERROR_DEFAULT = "好像有点不对劲>_<!!";
    public static final String ERROR_NETWORK = "网络有点不对劲>_<!!";
    private int code;
    public static String JSON_STRING;

    public RxCompatException() {
        this(CODE_DEFAULT, ERROR_DEFAULT);
    }

    public RxCompatException(Throwable cause) {
        this(CODE_DEFAULT, ERROR_DEFAULT, cause);
    }

    public RxCompatException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    @VisibleForTesting
    public RxCompatException(String message) {
        this(CODE_FOR_TEST, message);
    }

    public RxCompatException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public boolean isNetWorkError() {
        return getCode() == CODE_NETWORK;
    }

    public boolean isNotLoginError() {
        return getCode() == ERROR_NO_LOGIN;
    }

    public boolean isNotVipError() {
        return getCode() == ERROR_NO_VIP;
    }

}
