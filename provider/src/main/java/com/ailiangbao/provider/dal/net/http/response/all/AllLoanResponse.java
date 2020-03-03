package com.ailiangbao.provider.dal.net.http.response.all;

import com.ailiangbao.provider.dal.net.http.entity.all.ScreeningConditionEntity;
import com.ailiangbao.provider.dal.net.http.response.BaseHttpResponse;

public class AllLoanResponse extends BaseHttpResponse {
    private ScreeningConditionEntity data;

    public ScreeningConditionEntity getData() {
        return data;
    }

    public void setData(ScreeningConditionEntity data) {
        this.data = data;
    }
}
