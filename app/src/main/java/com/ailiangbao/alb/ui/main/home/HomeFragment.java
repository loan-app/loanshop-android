package com.ailiangbao.alb.ui.main.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.ui.base.BaseFragment;
import com.ailiangbao.alb.ui.main.home.adapter.HomeAdapter;
import com.ailiangbao.alb.ui.main.home.vm.HomeVM;
import com.dangbei.xlog.XLog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment implements HomeContract.IHomeViewer {

    @Inject
    HomePresenter presenter;
    private HomeAdapter homeAdapter;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private SpinKitView loading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.startLoading(true);
        getViewerComponent().inject(this);
        presenter.bind(this);
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        loading = view.findViewById(R.id.fragment_home_view_loading);
        refreshLayout = view.findViewById(R.id.fragment_home_refresh);
        recyclerView = view.findViewById(R.id.fragment_home_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        recyclerView.setPadding(0, result, 0, 0);
        homeAdapter = new HomeAdapter(getContext());
        recyclerView.setAdapter(homeAdapter);
        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
            initData();
        });
        initData();
    }

    private void initData() {
        presenter.requestData();
    }

    @Override
    public ViewGroup getLoadingForViewGroup() {
        return view.findViewById(R.id.fragment_home_root_view);
    }

    @Override
    public void onRequestData(HomeVM homeVM) {
        homeAdapter.setData(homeVM);
        homeAdapter.notifyDataSetChanged();
        refreshLayout.finishRefresh();
    }

    @Override
    public void cancelHomeLoadingView() {
        if (loading != null) {
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //开始轮播
            startBanner();
        } else {
            //停止轮播
            stopBanner();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startBanner();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopBanner();
    }

    private void stopBanner() {
        RelativeLayout bannerParentView = getBannerParentView();
        if (bannerParentView == null) return;
        Banner banner = bannerParentView.findViewById(R.id.item_home_banner);
        banner.stopAutoPlay();
        XLog.d("HomeFragment", "停止轮播");
    }

    private void startBanner() {
        if (!getUserVisibleHint()) return;
        RelativeLayout bannerParentView = getBannerParentView();
        if (bannerParentView == null) return;
        Banner banner = bannerParentView.findViewById(R.id.item_home_banner);
        banner.startAutoPlay();
//        TextBannerView textBannerView = bannerParentView.findViewById(R.id.item_home_top_text_banner);
//        if (textBannerView == null) return;
//        textBannerView.startViewAnimator();
        XLog.d("HomeFragment", "开始轮播");
    }

    private RelativeLayout getBannerParentView() {
        if (recyclerView == null) return null;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) return null;
        View viewByPosition = layoutManager.findViewByPosition(0);
        if (viewByPosition instanceof RelativeLayout) {
            return (RelativeLayout) viewByPosition;
        }
        return null;
    }

}
