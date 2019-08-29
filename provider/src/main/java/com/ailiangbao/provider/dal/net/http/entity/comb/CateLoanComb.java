package com.ailiangbao.provider.dal.net.http.entity.comb;

import com.ailiangbao.provider.dal.net.http.entity.LoanEntity;

import java.io.Serializable;
import java.util.List;

public class CateLoanComb implements Serializable {
    private String banner;
    private List<LoanEntity> list;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public List<LoanEntity> getList() {
        return list;
    }

    public void setList(List<LoanEntity> list) {
        this.list = list;
    }
}
