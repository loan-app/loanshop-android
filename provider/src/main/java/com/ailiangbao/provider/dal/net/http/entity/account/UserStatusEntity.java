package com.ailiangbao.provider.dal.net.http.entity.account;

import android.text.TextUtils;

import com.wangjie.rapidorm.api.annotations.Column;
import com.wangjie.rapidorm.api.annotations.Table;

import java.io.Serializable;

@Table(name = "userStatus")
public class UserStatusEntity implements Serializable {
    public static final String USER_STATUS_ID = "user_status_id";
    //表示新用户
    public static final String INIT = "init";
    //认证失效
    public static final String INVALID = "invalid";
    //认证中
    public static final String VERIFYING = "verifying";
    //认证成功
    public static final String SUBMIT = "submit";


    @Column(primaryKey = true, name = "user_status_id")
    String userId;
    @Column
    String live;//活体认证状态
    @Column
    String userInfo;//用户信息认证状态
    @Column
    String idCard;//身份证认证状态
    @Column
    String contact;//通讯录信息认证状态
    @Column
    String spCollte;//运营商认证状态
    @Column
    String bank;//银行卡认证状态
    @Column
    String zhima;//芝麻认证状态
    @Column
    String relation;//紧急联系人认证状态


    public String getUserId() {
        return TextUtils.isEmpty(userId) ? INVALID : userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLive() {
        return TextUtils.isEmpty(live) ? INVALID : live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public String getUserInfo() {
        return TextUtils.isEmpty(userInfo) ? INVALID : userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getIdCard() {
        return TextUtils.isEmpty(idCard) ? INVALID : idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getContact() {
        return TextUtils.isEmpty(contact) ? INVALID : contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSpCollte() {
        return TextUtils.isEmpty(spCollte) ? INVALID : spCollte;
    }

    public void setSpCollte(String spCollte) {
        this.spCollte = spCollte;
    }

    public String getBank() {
        return TextUtils.isEmpty(bank) ? INVALID : bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getZhima() {
        return TextUtils.isEmpty(zhima) ? INVALID : zhima;
    }

    public void setZhima(String zhima) {
        this.zhima = zhima;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getLiveName() {
        return "live";
    }

    public String getUserInfoName() {
        return "userInfo";
    }

    public String getIdCardName() {
        return "idCard";
    }

    public String getContactName() {
        return "contact";
    }

    public String getSpCollteName() {
        return "spCollte";
    }

    public String getBankName() {
        return "bank";
    }

    public String getZhimaName() {
        return "zhima";
    }

    public String getRelationName() {
        return "relation";
    }
}
