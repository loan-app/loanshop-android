package com.ailiangbao.alb.ui.base.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.util.JumpUtils;

/**
 * Created by hll on 2019/3/2
 */
public class TabBarView extends FrameLayout implements View.OnClickListener {

    private TextView nameTv;
    private Activity activity;
    private Class<?> clss;

    public TabBarView(Context context) {
        this(context, null);
    }

    public TabBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.view_tab_bar, this);
        nameTv = view.findViewById(R.id.view_tab_bar_name_tv);
        FrameLayout backFl = view.findViewById(R.id.view_tab_bar_back);
        backFl.setOnClickListener(this);
    }

    public void setNameTv(String name) {
        nameTv.setText(name);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * 返回的activity应该设置为singleTask
     */
    public void setBackToActivity(Class<?> clss) {
        this.clss = clss;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_tab_bar_back:
                if (activity != null) {
                    hintKeyBoard();
                    activity.finish();
                } else if (clss != null) {
                    JumpUtils.startActivity(getContext(), clss);
                }
                break;
        }
    }

    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && activity.getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (activity.getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
//        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
