package com.ailiangbao.provider.dal.util.sign;


import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/5/16.
 */
public class DesUtil {
    //    private static byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0};
//    private static byte[] iv = "haqu66\n\n".getBytes();
    private static final byte[] KEY_VI = "dangbeiLive66666".getBytes();
    private static final byte[] SECRET_KEY = "dangbeiLive66666".getBytes();

    public static String encode(String secretKey, byte[] iv, String plainText) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(plainText.getBytes());
        return Base64.encodeToString(encryptedData, Base64.NO_WRAP);
//        IvParameterSpec zeroIv = new IvParameterSpec(KEY_VI);
//        SecretKeySpec key = new SecretKeySpec(SECRET_KEY, "AES");
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
//        return Base64.encodeToString(cipher.doFinal(plainText.getBytes()), Base64.NO_WRAP | Base64.URL_SAFE);
    }

//    public static String decode(String secretKey, byte[] iv, String encryptText) throws Exception {
////        IvParameterSpec zeroIv = new IvParameterSpec(iv);
////        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "DES");
////        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
////        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
////        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText, Base64.NO_WRAP));
//        return new String(decryptData);
//    }

    public static String decrypt(String encryptContent) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(KEY_VI);
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        return new String(cipher.doFinal(Base64.decode(encryptContent, Base64.NO_WRAP | Base64.URL_SAFE)));
    }

}
