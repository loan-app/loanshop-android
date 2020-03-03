package com.ailiangbao.alb.ui.main.home.vm;

import android.support.annotation.NonNull;

import com.ailiangbao.alb.R;
import com.ailiangbao.provider.bll.vm.VM;
import com.ailiangbao.provider.dal.net.http.entity.HomeEntity;
import com.ailiangbao.provider.dal.net.http.entity.LoanEntity;
import com.ailiangbao.provider.dal.util.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeVM extends VM<HomeEntity> {
    public static final int TYPE_BANNER = 0;
    public static final int TYPE_CATE = 1;
    public static final int TYPE_RECOMMEND_LOAN = 2;
    public static final int TYPE_LIST = 3;
    private List<HomeEntity.TypesBean> cateList;
    private List<HomeEntity.BannersBean> bannerList;
    private List<LoanEntity> loansBeanList;
    private LoanEntity recommendLoan;
    private List<String> bannerUrlList;
    private List<Integer> viewTypeList;
    private int itemCount;

    public HomeVM(@NonNull HomeEntity model) {
        super(model);
        init();
    }

    private void init() {
        HomeEntity model = getModel();
        viewTypeList = new ArrayList<>();
        List<HomeEntity.BannersBean> banners = model.getBanners();
        if (!CollectionUtil.isEmpty(banners)) {
            itemCount++;
            viewTypeList.add(TYPE_BANNER);
            bannerList = banners;
            bannerUrlList = new ArrayList<>();
            for (HomeEntity.BannersBean banner : banners) {
                bannerUrlList.add(banner.getImgurl());
            }
        }
        List<HomeEntity.TypesBean> types = model.getTypes();
        if (!CollectionUtil.isEmpty(types)) {
            itemCount++;
            viewTypeList.add(TYPE_CATE);
            cateList = types;
            Collections.sort(cateList);
            for (int i = 0; i < cateList.size(); i++) {
                HomeEntity.TypesBean typesBean = cateList.get(i);
                switch (typesBean.getId()) {
                    case 1:
                        typesBean.setUrl(R.mipmap.ic_home_cate_01);
                        break;
                    case 2:
                        typesBean.setUrl(R.mipmap.ic_home_cate_02);
                        break;
                    case 3:
                        typesBean.setUrl(R.mipmap.ic_home_cate_03);
                        break;
                    case 4:
                        typesBean.setUrl(R.mipmap.ic_home_cate_04);
                        break;
                }
            }
        }
        List<LoanEntity> loans = model.getLoans();
        if (!CollectionUtil.isEmpty(loans)) {
            itemCount++;
            viewTypeList.add(TYPE_RECOMMEND_LOAN);
            recommendLoan = loans.get(0);
            loans.remove(0);
            loansBeanList = loans;
            if (!CollectionUtil.isEmpty(loansBeanList)) {
                loansBeanList.get(0).setShowTopLayout(true);
                itemCount = itemCount + loansBeanList.size();
            }
        }
    }

    public int getItemCount() {
        return itemCount;
    }

    public int getViewType(int position) {
        if (position < viewTypeList.size()) {
            return viewTypeList.get(position);
        }
        return TYPE_LIST;
    }

    public LoanEntity getLoanData(int position) {
        position = position - (CollectionUtil.isEmpty(bannerList) ? 0 : 1) - (CollectionUtil.isEmpty(cateList) ? 0 : 1) - (recommendLoan == null ? 0 : 1);
        return loansBeanList.get(position);
    }

    public List<HomeEntity.BannersBean> getBannerList() {
        return bannerList;
    }

    public List<String> getBannerUrl() {
        return bannerUrlList;
    }

    public List<HomeEntity.TypesBean> getCateList() {
        return cateList;
    }

    public LoanEntity getRecommendLoan() {
        return recommendLoan;
    }
}
