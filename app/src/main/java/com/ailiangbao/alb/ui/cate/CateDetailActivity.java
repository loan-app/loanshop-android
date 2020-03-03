package com.ailiangbao.alb.ui.cate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.ui.base.BaseActivity;
import com.ailiangbao.alb.ui.base.view.TabBarView;
import com.ailiangbao.alb.ui.main.loan.adapter.LoanAppListAdapter;
import com.ailiangbao.alb.util.GlideUtil;
import com.ailiangbao.alb.util.ResUtil;
import com.ailiangbao.provider.dal.net.http.entity.comb.CateLoanComb;
import com.ailiangbao.provider.dal.util.collection.CollectionUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import javax.inject.Inject;

public class CateDetailActivity extends BaseActivity implements CateDetailContract.ICateDetailViewer {
    @Inject
    CateDetailPresenter presenter;

    public static final String CATE_DETAIL_ACTIVITY_BUNDLE = "cate_detail_activity_bundle";
    public static final String CATE_DETAIL_ACTIVITY_EXTRA_NAME = "cate_detail_activity_extra_name";
    public static final String CATE_DETAIL_ACTIVITY_EXTRA_ID = "cate_detail_activity_extra_id";
    private LoanAppListAdapter loanAppListAdapter;
    private SmartRefreshLayout refreshLayout;
    private String tabBarName = "";
    private ImageView picIv;
    private String cateId;
    private int limit = 10;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cate_detail);
        getViewerComponent().inject(this);
        presenter.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(CATE_DETAIL_ACTIVITY_BUNDLE);
            if (bundle != null) {
                tabBarName = bundle.getString(CATE_DETAIL_ACTIVITY_EXTRA_NAME);
                cateId = bundle.getString(CATE_DETAIL_ACTIVITY_EXTRA_ID);
            }
        }
        initView();
        initData();
    }

    private void initView() {
        TabBarView tabBar = findViewById(R.id.activity_cate_detail_tab_bar);
        tabBar.setNameTv(tabBarName);
        tabBar.setActivity(this);
        refreshLayout = findViewById(R.id.activity_cate_detail_refresh);
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
            page = 1;
            CateDetailActivity.this.initData();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            initData();
        });

        picIv = new ImageView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, ResUtil.dip2px(156));
        params.bottomMargin = ResUtil.dip2px(14);
        picIv.setLayoutParams(params);
        picIv.setScaleType(ImageView.ScaleType.FIT_XY);
        RecyclerView recyclerView = findViewById(R.id.activity_cate_detail_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        loanAppListAdapter = new LoanAppListAdapter();
        loanAppListAdapter.setHeadView(picIv);
//        loanAppListAdapter.setOnHeadClickListener(() -> showToast("头布局"));
        recyclerView.setAdapter(loanAppListAdapter);
    }

    private void initData() {
        presenter.requestData(cateId, page, limit);
    }

    @Override
    public void onRequestData(CateLoanComb cateLoanComb, boolean isLoadMore) {
        if (!isLoadMore) {
            GlideUtil.loadImageRadius(this, picIv, cateLoanComb.getBanner(), 0);
            loanAppListAdapter.setList(cateLoanComb.getList());
            refreshLayout.finishRefresh();
        } else {
            loanAppListAdapter.addMoreList(cateLoanComb.getList());
            refreshLayout.finishLoadMore();
            if (CollectionUtil.isEmpty(cateLoanComb.getList())) {
                refreshLayout.setNoMoreData(true);
            }
        }
        loanAppListAdapter.notifyDataSetChanged();
    }
}
