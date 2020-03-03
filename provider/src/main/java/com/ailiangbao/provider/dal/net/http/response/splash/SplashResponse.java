package com.ailiangbao.provider.dal.net.http.response.splash;

import com.ailiangbao.provider.dal.net.http.entity.HomeEntity;
import com.ailiangbao.provider.dal.net.http.response.BaseHttpResponse;

/**
 * Created by hll on 2019/4/14
 */
public class SplashResponse extends BaseHttpResponse {
    private HomeEntity data;

    public HomeEntity getData() {
        return data;
    }

    public void setData(HomeEntity data) {
        this.data = data;
    }
}
