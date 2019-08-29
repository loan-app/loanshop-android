package com.ailiangbao.alb.ui.main.home.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.ui.cate.CateDetailActivity;
import com.ailiangbao.alb.util.GlideUtil;
import com.ailiangbao.alb.util.JumpUtils;
import com.ailiangbao.provider.dal.net.http.entity.HomeEntity;

import static com.ailiangbao.alb.ui.cate.CateDetailActivity.CATE_DETAIL_ACTIVITY_BUNDLE;
import static com.ailiangbao.alb.ui.cate.CateDetailActivity.CATE_DETAIL_ACTIVITY_EXTRA_ID;
import static com.ailiangbao.alb.ui.cate.CateDetailActivity.CATE_DETAIL_ACTIVITY_EXTRA_NAME;

public class HomeLoanCateItemView extends RelativeLayout implements View.OnClickListener {

    private ImageView picIv;
    private TextView nameTv;
    private HomeEntity.TypesBean data;

    public HomeLoanCateItemView(Context context) {
        this(context, null);
    }

    public HomeLoanCateItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeLoanCateItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public HomeLoanCateItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOnClickListener(this);
        View view = View.inflate(getContext(), R.layout.view_item_home_loan_cate, this);
        picIv = view.findViewById(R.id.view_item_home_loan_cate_pic_iv);
        nameTv = view.findViewById(R.id.view_item_home_loan_cate_name_iv);
    }

    public void setData(HomeEntity.TypesBean data) {
        if (data == null) return;
        this.data = data;
        nameTv.setText(data.getName());
        GlideUtil.loadImageRadius(getContext(), picIv, data.getUrl(), 0, 0);
    }

//    public void setGra

    @Override
    public void onClick(View v) {
        if (data == null) return;
        Bundle bundle = new Bundle();
        bundle.putString(CATE_DETAIL_ACTIVITY_EXTRA_NAME, data.getName());
        bundle.putString(CATE_DETAIL_ACTIVITY_EXTRA_ID, String.valueOf(data.getId()));
        JumpUtils.startActivity(getContext(), CateDetailActivity.class, bundle, CATE_DETAIL_ACTIVITY_BUNDLE);
    }
}
