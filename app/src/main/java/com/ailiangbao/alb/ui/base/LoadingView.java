package com.ailiangbao.alb.ui.base;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ailiangbao.alb.R;
import com.github.ybq.android.spinkit.SpinKitView;

public class LoadingView extends LinearLayout {
    private TextView loadingTv;
    private SpinKitView loading;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
//        setVisibility(GONE);
        View.inflate(getContext(), R.layout.view_loading, this);
        loading = findViewById(R.id.view_loading);
        loadingTv = findViewById(R.id.view_loading_tip_tv);
//        ScreenAdapter.scaleView(loadingPb, 100, 100, 0, 350);
//        loadingTv = findViewById(R.id.view_loading_tv);
//        ScreenAdapter.scaleView(loadingTv, -2, -2, 0, 15);
//        loadingTv.setTextSize(Axis.scaleTextSize(24));
    }

    public void startLoading(ViewGroup viewGroup) {
        if (null != loading && getParent() == null) {
//            viewGroup.addView(this);
//            ScreenAdapter.scaleView(this, ScreenAdapter.MATCH, ScreenAdapter.MATCH);
//            loadingPb.setVisibility(VISIBLE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            this.setLayoutParams(params);
            setVisibility(VISIBLE);
        }
    }

    public void stopLoading(ViewGroup viewGroup) {
        if (null != loading && getParent() != null) {
//            loadingPb.setVisibility(GONE);
//            viewGroup.removeView(this);
            setVisibility(VISIBLE);
        }
    }

    public boolean isLoading() {
        return getParent() != null;
    }

    public void setLayoutParams(int width, int height, int marginTop) {
//        ScreenAdapter.scaleView(loadingPb, width, height, 0, marginTop);
    }


    public void setCustomLayoutParams(ViewGroup.LayoutParams params) {
        loading.setLayoutParams(params);
    }

    public void setTextMarginTop(int marginTop) {
//        ScreenAdapter.scaleView(loadingTv, ScreenAdapter.WRAP, ScreenAdapter.WRAP, 0, marginTop);
    }

    public void setText(String loadingTxt) {
        loadingTv.setText(loadingTxt);
    }

    public void setTextSize(float size) {
//        ScreenAdapter.scaleTxtSize(loadingTv, size);
    }

    public void setTextColor(@ColorRes int color) {
        loadingTv.setTextColor(ContextCompat.getColor(getContext(), color));
    }

    public void setLoadingVisibility(boolean isVisibility) {
        loading.setVisibility(isVisibility ? VISIBLE : GONE);
    }

    public static LoadingView getInstance(Context context) {
        return new LoadingView(context);
    }
}