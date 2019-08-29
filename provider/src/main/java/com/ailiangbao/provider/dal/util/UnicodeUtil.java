package com.ailiangbao.provider.dal.util;

/**
 * unicode转换工具。
 * Created by zhanghongjie on 2017/8/9.
 */
public class UnicodeUtil {

    private UnicodeUtil(){}

    /**
     * unicode 转utf8
     * @param src Unicode文字。
     * @return utf8
     */
    public static String unicodeToUTF_8(String src) {
        if (null == src) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < src.length();) {
            char c = src.charAt(i);
            if (i + 6 < src.length() && c == '\\' && src.charAt(i + 1) == 'u') {
                String hex = src.substring(i + 2, i + 6);
                try {
                    out.append((char) Integer.parseInt(hex, 16));
                } catch (NumberFormatException nfe) {
                    nfe.fillInStackTrace();
                }
                i = i + 6;
            } else {
                out.append(src.charAt(i));
                ++i;
            }
        }
        return out.toString();

    }
}
