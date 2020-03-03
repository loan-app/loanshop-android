package com.ailiangbao.provider.dal.net.http.entity.all;

import java.io.Serializable;
import java.util.List;

public class ScreeningConditionEntity implements Serializable {

    private List<KeyValueEntity> moneyFiltrate;
    private List<KeyValueEntity> tagsFiltrate;
    private List<KeyValueEntity> typesortFiltrate;

    public List<KeyValueEntity> getMoneyFiltrate() {
        return moneyFiltrate;
    }

    public void setMoneyFiltrate(List<KeyValueEntity> moneyFiltrate) {
        this.moneyFiltrate = moneyFiltrate;
    }

    public List<KeyValueEntity> getTagsFiltrate() {
        return tagsFiltrate;
    }

    public void setTagsFiltrate(List<KeyValueEntity> tagsFiltrate) {
        this.tagsFiltrate = tagsFiltrate;
    }

    public List<KeyValueEntity> getTypesortFiltrate() {
        return typesortFiltrate;
    }

    public void setTypesortFiltrate(List<KeyValueEntity> typesortFiltrate) {
        this.typesortFiltrate = typesortFiltrate;
    }

    public static class KeyValueEntity implements Serializable, Comparable<KeyValueEntity> {
        /**
         * key : ALL
         * name : 金额不限
         * sortId : 1
         */

        private String key;
        private String name;
        private int sortId;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSortId() {
            return sortId;
        }

        public void setSortId(int sortId) {
            this.sortId = sortId;
        }

        @Override
        public int compareTo(KeyValueEntity o) {
            if (o.getSortId() - this.getSortId() > 0) {
                return -1;
            }
            return 1;
        }
    }

}
