package com.ailiangbao.provider.dal.net.http.entity;

import java.io.Serializable;

public class LoanEntity implements Serializable {
    private int id;
    private String title;
    private int state;
    private String tags;
    private int maxloan;
    private int minloan;
    private String raterange;
    private String periodrange;
    private String applyurl;
    private int applynum;
    private String badge;
    private Object applyurl2;
    private String intro;
    private String applyinfo;
    private String logo;
    private int byuid;
    private String createdat;
    private String updatedat;
    private Object deletedat;
    private int weights;
    private boolean isShowTopLayout = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getMaxloan() {
        return maxloan;
    }

    public void setMaxloan(int maxloan) {
        this.maxloan = maxloan;
    }

    public int getMinloan() {
        return minloan;
    }

    public void setMinloan(int minloan) {
        this.minloan = minloan;
    }

    public String getRaterange() {
        return raterange;
    }

    public void setRaterange(String raterange) {
        this.raterange = raterange;
    }

    public String getPeriodrange() {
        return periodrange;
    }

    public void setPeriodrange(String periodrange) {
        this.periodrange = periodrange;
    }

    public String getApplyurl() {
        return applyurl;
    }

    public void setApplyurl(String applyurl) {
        this.applyurl = applyurl;
    }

    public int getApplynum() {
        return applynum;
    }

    public void setApplynum(int applynum) {
        this.applynum = applynum;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public Object getApplyurl2() {
        return applyurl2;
    }

    public void setApplyurl2(Object applyurl2) {
        this.applyurl2 = applyurl2;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getApplyinfo() {
        return applyinfo;
    }

    public void setApplyinfo(String applyinfo) {
        this.applyinfo = applyinfo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getByuid() {
        return byuid;
    }

    public void setByuid(int byuid) {
        this.byuid = byuid;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public String getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(String updatedat) {
        this.updatedat = updatedat;
    }

    public Object getDeletedat() {
        return deletedat;
    }

    public void setDeletedat(Object deletedat) {
        this.deletedat = deletedat;
    }

    public int getWeights() {
        return weights;
    }

    public void setWeights(int weights) {
        this.weights = weights;
    }

    public boolean isShowTopLayout() {
        return isShowTopLayout;
    }

    public void setShowTopLayout(boolean showTopLayout) {
        isShowTopLayout = showTopLayout;
    }
}
