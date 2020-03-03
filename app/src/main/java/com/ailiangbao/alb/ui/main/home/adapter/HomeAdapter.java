package com.ailiangbao.alb.ui.main.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ailiangbao.alb.HApplication;
import com.ailiangbao.alb.R;
import com.ailiangbao.alb.ui.base.view.HLoanAppItemView;
import com.ailiangbao.alb.ui.main.home.view.HomeLoanCateView;
import com.ailiangbao.alb.ui.main.home.vm.HomeVM;
import com.ailiangbao.alb.ui.web.WebActivity;
import com.ailiangbao.alb.util.AppInfoUtil;
import com.ailiangbao.alb.util.GlideUtil;
import com.ailiangbao.alb.util.JumpUtils;
import com.ailiangbao.alb.util.StringUtils;
import com.ailiangbao.alb.util.WebUtils;
import com.ailiangbao.provider.dal.net.http.entity.HomeEntity;
import com.ailiangbao.provider.dal.net.http.entity.LoanEntity;
import com.ailiangbao.provider.dal.util.collection.CollectionUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final Context context;
    private HomeVM homeVM;
    private Map<String, String> map;
    private Map<String, String> bannerMap;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    public void setData(HomeVM homeVM) {
        this.homeVM = homeVM;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        switch (viewType) {
            case HomeVM.TYPE_BANNER:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_branner, viewGroup, false);
                break;
            case HomeVM.TYPE_CATE:
                view = new HomeLoanCateView(viewGroup.getContext());
                break;
            case HomeVM.TYPE_RECOMMEND_LOAN:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_recommend_loan, viewGroup, false);
                break;
            case HomeVM.TYPE_LIST:
                view = new HLoanAppItemView(viewGroup.getContext());
                break;
            default:
                break;
        }
        return new ViewHolder(view, viewType);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case HomeVM.TYPE_BANNER:
                List<String> bannerUrl = homeVM.getBannerUrl();
                if (!CollectionUtil.isEmpty(bannerUrl)) {
                    viewHolder.banner.setImages(bannerUrl);
                    viewHolder.banner.setOnBannerListener(position1 -> {
                        List<HomeEntity.BannersBean> bannerList = homeVM.getBannerList();
                        if (CollectionUtil.isEmpty(bannerList)) return;
                        HomeEntity.BannersBean bannersBean = bannerList.get(position1);
                        if (bannersBean == null) return;
                        Bundle bundle = new Bundle();
                        bundle.putString(WebActivity.WEB_ACTIVITY_BUNDLE_EXTRA, bannersBean.getGourl());
                        WebUtils.getInstance().stop();
                        JumpUtils.startActivity(context, WebActivity.class, bundle, WebActivity.WEB_ACTIVITY_BUNDLE);
                        if (bannerMap == null) {
                            bannerMap = new HashMap<>();
                        } else {
                            bannerMap.clear();
                        }
                        bannerMap.put("relId", String.valueOf(bannersBean.getLoanId()));
                        bannerMap.put("refer", "1");
                        bannerMap.put("channel", AppInfoUtil.getChannel(context));
                        bannerMap.put("uuid", AppInfoUtil.getMac());
                        bannerMap.put("mobile", String.valueOf(HApplication.getInstance().appComponent.providerGlobalInteractor().queryGlobalCurrentLoginUserIdSync()));
                        HApplication.getInstance().userComponent.providerMainInteractor().postProduct(StringUtils.toJsonString(bannerMap));
                    });
                    viewHolder.banner.start();
                }
                break;
            case HomeVM.TYPE_CATE:
                List<HomeEntity.TypesBean> cateList = homeVM.getCateList();
                ((HomeLoanCateView) viewHolder.itemView).setList(cateList);
                break;
            case HomeVM.TYPE_RECOMMEND_LOAN:
                LoanEntity recommendLoan = homeVM.getRecommendLoan();
                if (recommendLoan != null) {
                    viewHolder.quotaTv.setText((recommendLoan.getMinloan() == 0 ? "1000" : recommendLoan.getMinloan()) + "~" + recommendLoan.getMaxloan());
                    viewHolder.loanNameTv.setText(recommendLoan.getTitle());
                    WebUtils.getInstance().submit(recommendLoan.getApplyurl());
                }
                break;
            case HomeVM.TYPE_LIST:
                LoanEntity loanData = homeVM.getLoanData(position);
                ((HLoanAppItemView) viewHolder.itemView).setData(loanData);
                if (position == getItemCount() - 1) {
                    ((HLoanAppItemView) viewHolder.itemView).showBottomLine();
                } else {
                    ((HLoanAppItemView) viewHolder.itemView).hideBottomLine();
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return homeVM == null ? 0 : homeVM.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return homeVM.getViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView loanNameTv;
        private TextView quotaTv;
        private Banner banner;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            switch (viewType) {
                case HomeVM.TYPE_BANNER:
                    banner = itemView.findViewById(R.id.item_home_banner);
                    banner.setImageLoader(new GlideImageLoader());
                    banner.isAutoPlay(true);
                    banner.setDelayTime(3000);
                    banner.setIndicatorGravity(BannerConfig.CENTER);
                    banner.setBannerAnimation(Transformer.Default);
                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                    break;
                case HomeVM.TYPE_CATE:
                    break;
                case HomeVM.TYPE_RECOMMEND_LOAN:
                    loanNameTv = itemView.findViewById(R.id.item_home_recommend_loan_name_tv);
                    quotaTv = itemView.findViewById(R.id.item_home_recommend_loan_quota_tv);
                    TextView submitTv = itemView.findViewById(R.id.item_home_recommend_loan_bt_tv);
                    submitTv.setOnClickListener(this);
                    break;
                case HomeVM.TYPE_LIST:
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_home_recommend_loan_bt_tv:
                    LoanEntity recommendLoan = homeVM.getRecommendLoan();
                    if (recommendLoan == null) return;
                    Bundle bundle = new Bundle();
                    bundle.putString(WebActivity.WEB_ACTIVITY_BUNDLE_EXTRA, homeVM.getRecommendLoan().getApplyurl());
                    WebUtils.getInstance().stop();
                    JumpUtils.startActivity(context, WebActivity.class, bundle, WebActivity.WEB_ACTIVITY_BUNDLE);
                    if (map == null) {
                        map = new HashMap<>();
                    } else {
                        map.clear();
                    }
                    map.put("relId", String.valueOf(recommendLoan.getId()));
                    map.put("refer", "1");
                    map.put("channel", AppInfoUtil.getChannel(context));
                    map.put("uuid", AppInfoUtil.getMac());
                    map.put("mobile", String.valueOf(HApplication.getInstance().appComponent.providerGlobalInteractor().queryGlobalCurrentLoginUserIdSync()));
                    HApplication.getInstance().userComponent.providerMainInteractor().postProduct(StringUtils.toJsonString(map));
                    break;
            }
        }

    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Glide 加载图片简单用法
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            GlideUtil.loadImageRadius(context, imageView, path, 0, 10);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
//        @Override
//        public ImageView createImageView(Context context) {
//            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//            return simpleDraweeView;
//        }
    }
}
