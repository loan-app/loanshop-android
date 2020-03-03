package com.ailiangbao.provider.dal.net.http.entity.cate;

import com.ailiangbao.provider.dal.net.http.entity.LoanEntity;

import java.io.Serializable;
import java.util.List;

public class CateLoanEntity implements Serializable {

    /**
     * totalCount : 1
     * pageSize : 10
     * totalPage : 1
     * currPage : 1
     * clickNum : null
     * regNum : null
     * pvNum : null
     * uvNum : null
     * list : [{"id":18,"title":"中安信业","state":1,"tags":"FAST","maxloan":50000,"minloan":null,"raterange":"0.01%","periodrange":"3/6/12月","applyurl":"http://bd.zac.cn/bd/appweb/action/simpleApply?systemId=1yhkj2019&extendTab=01","applynum":16213,"badge":"OPTIMIZATION","applyurl2":null,"intro":"低息大额极速5分钟放款","applyinfo":"申请条件 1、   申请材料 1、   审核说明 审核方式： 放款时间： 还款方式： ","logo":"http://loan56.oss-cn-beijing.aliyuncs.com/5e1cb150-4954-11e9-8088-d5e55211333a.jpg","byuid":2,"createdat":"2019-03-18T08:03:53.000+0000","updatedat":"2019-03-18T08:51:04.000+0000","deletedat":null,"weights":3,"type":1}]
     */

    private int totalCount;
    private int pageSize;
    private int totalPage;
    private int currPage;
    private String typeBanner;
    private List<LoanEntity> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public String getTypeBanner() {
        return typeBanner;
    }

    public void setTypeBanner(String typeBanner) {
        this.typeBanner = typeBanner;
    }

    public List<LoanEntity> getList() {
        return list;
    }

    public void setList(List<LoanEntity> list) {
        this.list = list;
    }

}
