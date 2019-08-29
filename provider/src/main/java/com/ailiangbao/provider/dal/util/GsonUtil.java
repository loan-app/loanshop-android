package com.ailiangbao.provider.dal.util;

import com.google.gson.Gson;

/**
 * 统一gson来源，方便以后统一修改。
 * Created by zhanghongjie on 2018/4/10.
 */
public class GsonUtil {

    public static Gson getGson(){
        return new Gson();
    }
}
