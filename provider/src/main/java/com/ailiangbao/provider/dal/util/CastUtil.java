package com.ailiangbao.provider.dal.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by $xuzhili on 2017/8/30.
 * dangbei network
 */

public final class CastUtil {

    private CastUtil() {
    }

    public static final String TAG = CastUtil.class.getSimpleName();

    /**
     * 获取指定类型
     */
    public static <T, R> T castSingleData(Class<T> tClass, R item) {
        T t = null;
        try {
            t = tClass.cast(item);
        } catch (Exception e) {
            Log.d(TAG, "cast_error:", e);
        }
        return t;
    }

    /**
     * 获取指定类型
     */
    public static <T, R> List<T> castListData(Class<T> tClass, List<R> items) {
        ArrayList<T> itemList = new ArrayList<>();
        if (items == null) {
            return itemList;
        }
        for (R item : items) {
            try {
                itemList.add(tClass.cast(item));
            } catch (Throwable throwable) {
                Log.d(TAG, "cast_error:", throwable);
            }
        }
        return itemList;
    }

}
