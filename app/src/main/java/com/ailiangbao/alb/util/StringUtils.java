package com.ailiangbao.alb.util;

import java.util.Map;

/**
 * Created by hll on 2019/4/13
 */
public class StringUtils {
    public static String toJsonString(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.append("\"").append(entry.getKey()).append("\":").append("\"").append(entry.getValue()).append("\",");
        }
        String jsonString = builder.toString();
        String substring = jsonString.substring(0, jsonString.length() - 1);
        return substring + "}";
    }
}
