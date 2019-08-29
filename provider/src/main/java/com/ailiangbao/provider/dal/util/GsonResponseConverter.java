package com.ailiangbao.provider.dal.util;

import android.util.Log;

import com.ailiangbao.provider.support.bridge.compat.subscriber.RxCompatException;
import com.wangjiegulu.dal.request.core.converter.ResponseConverter;
import com.wangjiegulu.dal.request.core.request.XRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Created by zhanghongjie on 2018/4/10.
 */

public class GsonResponseConverter implements ResponseConverter {
    @Override
    public <T> T convert(XRequest xRequest, byte[] responseBytes, Type responseType) {
        ByteArrayInputStream byteArrayInputStream = null;
//        try {
        try {
            byteArrayInputStream = new ByteArrayInputStream(responseBytes);
            String jsonString = getStringFromInputStream(byteArrayInputStream);
            Log.d("test--->返回的字符串", xRequest.get().getUrl() + "--->" + jsonString);
            RxCompatException.JSON_STRING = jsonString;
//            if (responseType instanceof BaseHttpResponse) {
//                ((BaseHttpResponse) responseType).setJSON_STRING(jsonString);
//            }
//            if (xRequest.get().getUrl().equals(WebApiConstants.formatHttpWebApi(WebApi.Other.NET_TIME))) {//因为服务器返回的时间是一个纯流，所以在此做特殊得理
//                jsonString = "{\"netTime\":" + "\"" + jsonString + "\",\"msg\":" + "\"success\"" + "}";
//                XLog.i("hll", jsonString);
//            }
//            jsonString = DesHelper.getInstance().decrypt(jsonString);
            return GsonUtil.getGson().fromJson(jsonString, responseType);
        } catch (Exception e) {
            e.printStackTrace();
            if (null != byteArrayInputStream) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
//        return GsonUtil.getGson().fromJson(reader = new InputStreamReader(new ByteArrayInputStream(responseBytes)), responseType);
//        } finally {
//            if (null != reader) {
//                try {
//                    reader.close();
//                } catch (Throwable ignore) {}
//            }
//        }
        return null;
    }

    private String getStringFromInputStream(InputStream inputStream) throws Exception {
        String resultData = null;      //需要返回的结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(data)) != -1) {
            byteArrayOutputStream.write(data, 0, len);
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }
}
