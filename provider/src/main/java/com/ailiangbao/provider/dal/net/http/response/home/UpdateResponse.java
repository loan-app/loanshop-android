package com.ailiangbao.provider.dal.net.http.response.home;

import com.ailiangbao.provider.dal.net.http.entity.cate.UpdateEntity;
import com.ailiangbao.provider.dal.net.http.response.BaseHttpResponse;

public class UpdateResponse extends BaseHttpResponse {
    private UpdateEntity data;

    public UpdateEntity getData() {
        return data;
    }

    public void setData(UpdateEntity data) {
        this.data = data;
    }
}
