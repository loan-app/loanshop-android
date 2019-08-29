package com.ailiangbao.provider.dal.net.http.response.cate;

import com.ailiangbao.provider.dal.net.http.entity.cate.CateLoanEntity;
import com.ailiangbao.provider.dal.net.http.response.BaseHttpResponse;

public class CateResponse extends BaseHttpResponse {
    private CateLoanEntity data;

    public CateLoanEntity getData() {
        return data;
    }

    public void setData(CateLoanEntity data) {
        this.data = data;
    }
}
