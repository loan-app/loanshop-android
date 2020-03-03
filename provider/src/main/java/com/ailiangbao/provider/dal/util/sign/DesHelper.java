package com.ailiangbao.provider.dal.util.sign;

import android.support.annotation.NonNull;

/**
 * http://www.jianshu.com/p/4eb3e6d07b78
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/5/16.
 */
public class DesHelper {

    public static final String SECRET = "dangbeiLive66666";
//    private static final byte[] SECRET_KEY = "dangbeiLive66666".getBytes();

    private String secretKey = "dangbeiLive66666";
    ;
    private byte[] iv = "dangbeiLive66666".getBytes();

    private static DesHelper desHelper;

    public static DesHelper getInstance() {
        if (desHelper == null) {
            desHelper = new DesHelper(SECRET, SECRET.getBytes());
        }
        return desHelper;
    }

    private DesHelper(@NonNull String secretKey, @NonNull byte[] iv) {
        this.secretKey = secretKey;
        this.iv = iv;
    }

    public String encode(String plainText, String key, byte[] iv) throws Exception {
        return DesUtil.encode(key, iv, plainText);
    }

//    public String decode(String encryptText) throws Exception {
//        return DesUtil.decode(secretKey, iv, encryptText);
//    }

    public String decrypt(String encryptText) throws Exception {
        return DesUtil.decrypt(encryptText);
    }

    public String getSecretKey() {
        return SECRET;
    }
}
