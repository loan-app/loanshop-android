package com.ailiangbao.alb.ui.main.loan;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.ui.base.BaseFragment;
import com.ailiangbao.alb.ui.main.loan.adapter.DropDownMenuAdapter;
import com.ailiangbao.alb.ui.main.loan.adapter.LoanAppListAdapter;
import com.ailiangbao.alb.util.ResUtil;
import com.ailiangbao.provider.dal.net.http.entity.LoanEntity;
import com.ailiangbao.provider.dal.net.http.entity.all.ScreeningConditionEntity;
import com.ailiangbao.provider.dal.util.collection.CollectionUtil;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.sss.simpleDropMenu.SimpleDropMenu;
import com.sss.simpleDropMenu.bean.TabMenuBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AllLoanFragment extends BaseFragment implements AllLoanContract.IAllLoanViewer {
    @Inject
    AllLoanPresenter presenter;
    private List<TabMenuBean> tabText = new ArrayList<>();
    private List<View> popupView = new ArrayList<>();
    private SimpleDropMenu dropDownMenu;
    private LoanAppListAdapter loanAppListAdapter;
    private String label1 = "";
    private String label2 = "";
    private String label3 = "";
    private View line;
    private int page = 1;
    private int limit = 10;
    private SmartRefreshLayout refreshLayout;
    private DropDownMenuAdapter dropDownMenuAdapter1;
    private DropDownMenuAdapter dropDownMenuAdapter2;
    private DropDownMenuAdapter dropDownMenuAdapter3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewerComponent().inject(this);
        presenter.bind(this);
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_all_loan;
    }

    @Override
    protected void init() {
        initRefreshView();
        initData();
        intDropDownMenu();
    }

    private void initRefreshView() {
        Context context = getContext();
        if (context == null) return;
        refreshLayout = new SmartRefreshLayout(context);
        refreshLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        refreshLayout.setPrimaryColors(ResUtil.getColor(R.color.bt_red));
        refreshLayout.setRefreshHeader(new BezierCircleHeader(getContext()));
        ClassicsFooter classicsFooter = new ClassicsFooter(getContext());
        classicsFooter.setBackgroundColor(ResUtil.getColor(R.color.FFEEEEEE));
        refreshLayout.setRefreshFooter(classicsFooter);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            presenter.requestList(page, limit, label1, label2, label3);
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            presenter.requestList(page, limit, label1, label2, label3);
        });
    }

    private void initData() {
        tabText.add(new TabMenuBean("金额", true));
        tabText.add(new TabMenuBean("排序", true));
        tabText.add(new TabMenuBean("筛选", true));
        Context context = getContext();
        if (context == null) return;
        RecyclerView recyclerView1 = new RecyclerView(context);
        recyclerView1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView1.setBackgroundColor(ResUtil.getColor(R.color.FFEEEEEE));
        recyclerView1.setPadding(0, 0, 0, ResUtil.dip2px(10));
        dropDownMenuAdapter1 = new DropDownMenuAdapter();
        dropDownMenuAdapter1.setOnDropDownMenuAdapterListener((labelKey, labelValue) -> {
            label1 = labelKey;
//            dropDownMenu.setMenuTabText(0, labelValue);
            dropDownMenu.closeAllMenu();
            refreshLayout.autoRefresh();
        });
        recyclerView1.setAdapter(dropDownMenuAdapter1);

        RecyclerView recyclerView2 = new RecyclerView(context);
        recyclerView2.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView2.setBackgroundColor(ResUtil.getColor(R.color.FFEEEEEE));
        recyclerView2.setPadding(0, 0, 0, ResUtil.dip2px(10));
        dropDownMenuAdapter2 = new DropDownMenuAdapter();
        dropDownMenuAdapter2.setOnDropDownMenuAdapterListener((labelKey, labelValue) -> {
            label2 = labelKey;
//            dropDownMenu.setMenuTabText(1, labelValue);
            dropDownMenu.closeAllMenu();
            refreshLayout.autoRefresh();
        });
        recyclerView2.setAdapter(dropDownMenuAdapter2);

        RecyclerView recyclerView3 = new RecyclerView(context);
        recyclerView3.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView3.setBackgroundColor(ResUtil.getColor(R.color.FFEEEEEE));
        recyclerView3.setPadding(0, 0, 0, ResUtil.dip2px(10));
        dropDownMenuAdapter3 = new DropDownMenuAdapter();
        dropDownMenuAdapter3.setOnDropDownMenuAdapterListener((labelKey, labelValue) -> {
            label3 = labelKey;
//            dropDownMenu.setMenuTabText(2, labelValue);
            dropDownMenu.closeAllMenu();
            refreshLayout.autoRefresh();
        });

        recyclerView3.setAdapter(dropDownMenuAdapter3);

        popupView.add(recyclerView1);
        popupView.add(recyclerView2);
        popupView.add(recyclerView3);

        presenter.requestScreeningCondition();
        presenter.requestList(page, limit, label1, label2, label3);
    }

    private void intDropDownMenu() {
        Context context = getContext();
        if (context == null) return;
        line = view.findViewById(R.id.fragment_all_loan_line);
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setBackgroundColor(ResUtil.getColor(R.color.FFEEEEEE));
        recyclerView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        refreshLayout.addView(recyclerView);
        dropDownMenu = view.findViewById(R.id.fragment_all_loan_ddm);
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        dropDownMenu.setPadding(0, result, 0, 0);
//        ((ViewGroup.MarginLayoutParams) dropDownMenu.getLayoutParams()).topMargin = result;
        dropDownMenu.setDropDownMenu(tabText, popupView, refreshLayout);
        loanAppListAdapter = new LoanAppListAdapter();
        recyclerView.setAdapter(loanAppListAdapter);
    }

    @Override
    public ViewGroup getLoadingForViewGroup() {
        return view.findViewById(R.id.fragment_all_loan_root_view);
    }

    @Override
    public void onRequest(List<LoanEntity> list, boolean isLoadMore) {
        if (isLoadMore) {
            loanAppListAdapter.addMoreList(list);
            refreshLayout.finishLoadMore();
            if (CollectionUtil.isEmpty(list)) {
                refreshLayout.setNoMoreData(true);
            }
        } else {
            loanAppListAdapter.setList(list);
            refreshLayout.finishRefresh();
            line.setVisibility(View.GONE);
        }
        loanAppListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestScreeningCondition(ScreeningConditionEntity screeningConditionEntity) {
        dropDownMenuAdapter1.setData(screeningConditionEntity.getMoneyFiltrate());
        dropDownMenuAdapter2.setData(screeningConditionEntity.getTypesortFiltrate());
        dropDownMenuAdapter3.setData(screeningConditionEntity.getTagsFiltrate());
        dropDownMenuAdapter1.notifyDataSetChanged();
        dropDownMenuAdapter2.notifyDataSetChanged();
        dropDownMenuAdapter3.notifyDataSetChanged();
    }
}
