package com.ailiangbao.provider.dal.net.http.entity;

import java.io.Serializable;

public class ContactsEntity implements Serializable {
    private String label;//名字
    private String value;//号码

    public ContactsEntity(String name, String num) {
        this.label = name;
        this.value = num;
    }

    public String getNum() {
        return value;
    }

    public void setNum(String num) {
        this.value = num;
    }

    public String getName() {
        return label;
    }

    public void setName(String name) {
        this.label = name;
    }
}
