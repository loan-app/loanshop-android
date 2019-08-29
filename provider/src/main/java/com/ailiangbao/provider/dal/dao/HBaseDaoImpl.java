package com.ailiangbao.provider.dal.dao;

import com.wangjie.rapidorm.core.dao.BaseDaoImpl;

/**
 * Created by cq on 2018/5/15
 */
public class HBaseDaoImpl<T> extends BaseDaoImpl<T> implements HBaseDao<T> {
    public HBaseDaoImpl(Class<T> clazz) {
        super(clazz);
    }
}
