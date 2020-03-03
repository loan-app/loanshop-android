package com.ailiangbao.provider.dal.net.http.response;


import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;

import java.io.Serializable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/4/16.
 */
public class BaseHttpResponse implements Serializable {
    protected Integer code;
    protected String msg;
    protected boolean success;
    protected Integer cost;
    public static final int NO_LOGIN_CODE = 499;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public int getCode(int defaultValue) {
        return null == code ? defaultValue : code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public RxCompatException toCompatException() {
        RxCompatException rxCompatException;
        Integer code = getCode();
        if (code == NO_LOGIN_CODE) {
            rxCompatException = new RxCompatException(RxCompatException.ERROR_NO_LOGIN, getMessage());
        } else {
            rxCompatException = new RxCompatException(RxCompatException.CODE_DEFAULT, getMessage());
        }
//        String message = getMessage();
//        switch (message) {
//            case ResponseCode.ERROR_TOKEN_EXPIRES:
//                rxCompatException = new RxCompatException(RxCompatException.ERROR_NO_LOGIN, message);
//                break;
//            case ResponseCode.ERROR_NO_VIP:
//                rxCompatException = new RxCompatException(RxCompatException.ERROR_NO_VIP, message);
//                break;
//            default:
//                rxCompatException = new RxCompatException(RxCompatException.CODE_DEFAULT, message);
//                break;
//        }
        return rxCompatException;
    }

    /**
     * 判断是否是未登录CODE
     */
    public boolean isLoginCode() {
        return getCode() == NO_LOGIN_CODE;
    }

    /**
     * 业务是否成功
     */
    public boolean isBizSucceed() {
        return (getCode() != null && getCode() == 0);
    }

    @Override
    public String toString() {
        return "BaseHttpResponse{" +
                "code=" + code +
                ", message='" + msg + '\'' +
                '}';
    }
}
