package com.ailiangbao.provider.dal.util;


import com.ailiangbao.provider.support.usage.XPair;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 02/08/2017.
 */
public class TextUtil {
    public static boolean isEquals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return null == charSequence || 0 == charSequence.length();
    }

    public static boolean isBlank(CharSequence charSequence) {
        return null == charSequence || 0 == charSequence.toString().trim().length();
    }

    public static String convertCount(int count) {
        if (count < 10000) {
            return String.valueOf(count);
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(0);
        numberFormat.setRoundingMode(RoundingMode.UP);

        if (count < 100000000) {
            if (count % 10000 == 0) {
                return numberFormat.format(count / 10000).replace(",", "") + "万";
            } else {
                int i = count % 10000 / 1000;//表示千位
                if (i != 0) {
                    return numberFormat.format(count / 10000).replace(",", "") + "." + i + "万";
                } else {
                    return numberFormat.format(count / 10000).replace(",", "") + "万";
                }
            }
        }

        return numberFormat.format(count / 100000000).replace(",", "") + "亿";
    }

    public static XPair<String, String> convertCountXPair(int count) {
        if (count < 10000) {
            return new XPair<>(String.valueOf(count), "");
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(0);
        numberFormat.setRoundingMode(RoundingMode.UP);

        if (count < 100000000) {
            if (count % 10000 == 0) {
                return new XPair<>(numberFormat.format(count / 10000).replace(",", ""), "万");
            } else {
                return new XPair<>(numberFormat.format(count / 10000).replace(",", ""), "万+");
            }
        }
        return new XPair<>(numberFormat.format(count / 100000000).replace(",", ""), "亿");
    }
}
