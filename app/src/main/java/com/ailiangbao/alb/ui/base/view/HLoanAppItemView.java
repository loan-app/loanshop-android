package com.ailiangbao.alb.ui.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ailiangbao.alb.HApplication;
import com.ailiangbao.alb.R;
import com.ailiangbao.alb.ui.main.MainActivity;
import com.ailiangbao.alb.ui.web.WebActivity;
import com.ailiangbao.alb.util.AppInfoUtil;
import com.ailiangbao.alb.util.GlideUtil;
import com.ailiangbao.alb.util.JumpUtils;
import com.ailiangbao.alb.util.ResUtil;
import com.ailiangbao.alb.util.StringUtils;
import com.ailiangbao.alb.util.WebUtils;
import com.ailiangbao.provider.dal.net.http.entity.LoanEntity;

import java.util.HashMap;

public class HLoanAppItemView extends FrameLayout implements View.OnClickListener {

    private TextView nameTv, tagTv, descriptionTV, quotaTv, cycleTv;
    private HashMap<String, String> map = new HashMap<>();
    private LoanEntity loanEntity;
    private ImageView picIv;
    private FrameLayout topFl;
    private View bottomLine;

    public HLoanAppItemView(Context context) {
        this(context, null);
    }

    public HLoanAppItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HLoanAppItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public HLoanAppItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOnClickListener(this);
        View view = View.inflate(getContext(), R.layout.view_loan_app_item, this);
        TextView btTv = view.findViewById(R.id.view_loan_app_item_button_tv);
        nameTv = view.findViewById(R.id.view_loan_app_item_name_tv);
        picIv = view.findViewById(R.id.view_loan_app_item_app_icon_iv);
        tagTv = view.findViewById(R.id.view_loan_app_item_tag_tv);
        descriptionTV = view.findViewById(R.id.view_loan_app_item_description_tv);
        quotaTv = view.findViewById(R.id.view_loan_app_item_quota_tv);
        cycleTv = view.findViewById(R.id.view_loan_app_item_cycle_tv);
        topFl = view.findViewById(R.id.view_loan_app_item_top_fl);
        bottomLine = view.findViewById(R.id.view_loan_app_item_bottom_line);
        TextView moreTv = view.findViewById(R.id.view_loan_app_item_top_more_tv);
        Drawable drawable = ResUtil.getDrawable(R.mipmap.ic_home_loan_one_red_arrow);
        drawable.setBounds(0, 0, ResUtil.dip2px(6), ResUtil.dip2px(10));
        moreTv.setCompoundDrawables(null, null, drawable, null);
        btTv.setOnClickListener(this);
        moreTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_loan_app_item_top_more_tv:
                Context context = getContext();
                if (context instanceof MainActivity) {
                    ((MainActivity) context).JumpLoanFragment();
                }
                break;
            case R.id.view_loan_app_item_button_tv:
            default:
                if (loanEntity == null) return;
                if (map.size() > 0) {
                    //统计一波
                    HApplication.getInstance().userComponent.providerMainInteractor().postProduct(StringUtils.toJsonString(map));
                }
                Bundle bundle = new Bundle();
                bundle.putString(WebActivity.WEB_ACTIVITY_BUNDLE_EXTRA, loanEntity.getApplyurl());
                WebUtils.getInstance().stop();
                JumpUtils.startActivity(getContext(), WebActivity.class, bundle, WebActivity.WEB_ACTIVITY_BUNDLE);
                break;
        }
    }

    public void showTopLayout() {
        topFl.setVisibility(VISIBLE);
    }

    public void hideTopLayout() {
        topFl.setVisibility(GONE);
    }

    public void showBottomLine() {
        bottomLine.setVisibility(VISIBLE);
    }

    public void hideBottomLine() {
        bottomLine.setVisibility(GONE);
    }

    @SuppressLint("SetTextI18n")
    public void setData(LoanEntity loanEntity) {
        if (loanEntity == null) return;
        this.loanEntity = loanEntity;
        if (loanEntity.isShowTopLayout()) {
            showTopLayout();
        } else {
            hideTopLayout();
        }
        GlideUtil.loadImageRadius(getContext(), picIv, loanEntity.getLogo(), 0, 10);
        nameTv.setText(loanEntity.getTitle());
        tagTv.setText(loanEntity.getTags());
        descriptionTV.setText(loanEntity.getIntro());
        quotaTv.setText((loanEntity.getMinloan() == 0 ? "1000" : loanEntity.getMinloan()) + "~" + loanEntity.getMaxloan());
        cycleTv.setText(loanEntity.getPeriodrange());
        map.put("relId", String.valueOf(loanEntity.getId()));
        map.put("refer", "1");
        map.put("channel", AppInfoUtil.getChannel(getContext()));
        map.put("uuid", AppInfoUtil.getMac());
        map.put("mobile", String.valueOf(HApplication.getInstance().appComponent.providerGlobalInteractor().queryGlobalCurrentLoginUserIdSync()));
        WebUtils.getInstance().submit(loanEntity.getApplyurl());
    }
}
