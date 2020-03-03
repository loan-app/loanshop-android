package com.ailiangbao.provider.dal.net.http.entity;

import java.io.Serializable;
import java.util.List;

public class HomeEntity implements Serializable {

    private List<TypesBean> types;
    private List<LoanEntity> loans;
    private List<BannersBean> banners;

    public List<TypesBean> getTypes() {
        return types;
    }

    public void setTypes(List<TypesBean> types) {
        this.types = types;
    }

    public List<LoanEntity> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanEntity> loans) {
        this.loans = loans;
    }

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public static class TypesBean implements Serializable, Comparable<TypesBean> {
        /**
         * id : 1
         * name : 身份证贷
         * position : 1
         */

        private int id;
        private String name;
        private int position;
        private int url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getUrl() {
            return url;
        }

        public void setUrl(int url) {
            this.url = url;
        }

        @Override
        public int compareTo(TypesBean o) {
            if (o.getPosition() - this.getPosition() > 0) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public static class BannersBean implements Serializable {
        /**
         * id : 1
         * title : 广告1
         * imgurl : http://loan56.oss-cn-beijing.aliyuncs.com/32a11310-3a5c-11e9-a188-99216a3465bf.png
         * gourl : http://res.static.xiaozhoudao.net/xiaozhoudao/register-xzd.html?channelId=o_channel_01
         * intro : 123
         * seq : 100
         * byuid : 2
         * pos : 1
         * state : 2
         * createdat : 2019-02-27T06:12:46.000+0000
         * updatedat : 2019-02-28T07:36:21.000+0000
         * deletedat : null
         */

        private int id;
        private int loanId;
        private String title;
        private String imgurl;
        private String gourl;
        private String intro;
        private int seq;
        private int byuid;
        private int pos;
        private int state;
        private String createdat;
        private String updatedat;
        private Object deletedat;

        public int getId() {
            return id;
        }

        public int getLoanId() {
            return loanId;
        }

        public void setLoanId(int loanId) {
            this.loanId = loanId;
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

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getGourl() {
            return gourl;
        }

        public void setGourl(String gourl) {
            this.gourl = gourl;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public int getByuid() {
            return byuid;
        }

        public void setByuid(int byuid) {
            this.byuid = byuid;
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
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
    }
}
