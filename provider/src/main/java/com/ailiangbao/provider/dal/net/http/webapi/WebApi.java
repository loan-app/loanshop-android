package com.ailiangbao.provider.dal.net.http.webapi;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/5/16.
 */
public final class WebApi {
    public static final String CONTRACT = "/loan/meta/contact-info/upload";

    public static class Login {
        /**
         * 发送验证码
         */
        public static final String SEND = "/api/v2/getVcode";

        public static final String LOGIN = "/api/mobileLogin";

        public static final String PIC_CODE = "/api/captcha.jpg";
    }

    public static class Authentication {

        /**
         * 上传身份证正面
         */
        public static final String ID_CARD_FRONT = "/loam/id-card/face/ocr";

        public static final String ID_CARD_BACK = "/image/upload";

        public static final String FACE = "/live-package/upload";

        public static final String ID_CARD_INFO = "/loan/id-card/bind";

        public static final String FACE_INFO = "/loan/live-package/submit";
    }

    public static class Home {

        public static final String DATA = "/api/index";
        public static final String UPDATE = "/api/get_new_version";
    }

    public static class Bank {
        public static final String TELEPHONE_CODE = "/loan/bank-card/bind";

        public static final String POST_INFO = "/loan/bank-card/add";
    }

    public static class Amount {
        public static final String QUOTA_LIST = "/mall/amount/list";

        public static final String SUBMIT_QUOTA = "/order/submit";

        public static final String LOAN_BANK_CARD = "/loan/bank-card/get";

        //订单款信息
        public static final String PAY_BACK_MONEY_INFO = "/me/order/credit/detail";

        //展期订单信息
        public static final String DELAY_PAY_BACK_MONEY_INFO = "/me/settle/extent/detail";
    }

    public static class Operator {
        public static final String URL = "/sp/collect/redirect";
    }

    public static class Personal {

        public static final String INFO = "/loan/basic-info/update";
    }

    public static class PayType {
        public static final String PAY = "/settle/confirm/submit/yeebao";

        public static final String PAY_STATUS = "/settle/income/item/status";

        public static final String SUBMIT_PAY_ORDER = "/settle/repay/submit/yeebao";
        //展期
        public static final String SUBMIT_IS_DELAY_PAY_ORDER = "/settle/extent/submit/yeebao";
    }

    public static class BangManage {

        public static final String BANK_CARD_LIST = "/loan/bank-card/list";

        public static final String BANK_CARD_CHANGE = "/loan/bank-card/to-default";
    }

    public static class Cate {

        public static final String LIST = "/api/getTypeLoanList";

        public static final String SCREENING_CONDITION = "/api/getFiltrate";
    }

    public static class AllLoan {
        public static final String LIST = "/api/getLoanList";
    }

    public static class Post {
        public static final String PRODUCT = "/api/traceProduct";
        public static final String APP_DEVICE = "/api/traceMdevice";
        public static final String APP_CHANNEL = "/api/traceChannel";
        public static final String OPEN_APP = "/api/traceChannelAppOpenNum";
        public static final String UV = "/api/traceChannelUV";
        public static final String LOGIN = "/api/tal";
    }
}
