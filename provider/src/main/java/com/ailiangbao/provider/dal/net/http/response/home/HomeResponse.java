package com.ailiangbao.provider.dal.net.http.response.home;

import com.ailiangbao.provider.dal.net.http.entity.HomeEntity;
import com.ailiangbao.provider.dal.net.http.response.BaseHttpResponse;

public class HomeResponse extends BaseHttpResponse {
    private HomeEntity data;

    public HomeEntity getData() {
        return data;
    }

    public void setData(HomeEntity data) {
        this.data = data;
    }
}
