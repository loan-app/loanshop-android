package com.ailiangbao.alb.ui.main.my.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.ui.main.my.about.AboutActivity;
import com.ailiangbao.alb.ui.main.my.setting.SettingActivity;
import com.ailiangbao.alb.util.JumpUtils;
import com.ailiangbao.alb.util.ResUtil;
import com.ailiangbao.alb.util.ToastUtils;


public class ItemView extends FrameLayout implements View.OnClickListener {

    private View line;
    private ImageView iconIv;
    private TextView titleTv;
    //    private TextView statusTv;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnClickListener(this);
//        setRippleColor(Color.parseColor("#26333333"));
//        MaterialRippleLayout.on(this)
//                .rippleColor(Color.BLACK)
//                .create();
        View view = View.inflate(getContext(), R.layout.view_fragment_my, this);
//        FrameLayout root = view.findViewById(R.id.view_fragment_my_root);
//        root.setOnClickListener(this);
        iconIv = view.findViewById(R.id.view_fragment_my_icon_iv);
        titleTv = view.findViewById(R.id.view_fragment_my_title_tv);
//        statusTv = view.findViewById(R.id.view_fragment_my_status_tv);
        line = view.findViewById(R.id.view_fragment_my_line);
    }

    public void setIconIv(int res) {
        iconIv.setImageResource(res);
    }

    public void setTitleTv(String title) {
        titleTv.setText(title);
    }

    public void hideLine() {
        line.setVisibility(GONE);
    }

    public void showLine() {
        line.setVisibility(VISIBLE);
    }

//    public void showStatus() {
//        statusTv.setVisibility(VISIBLE);
//    }
//
//    public void hideStatus() {
//        statusTv.setVisibility(GONE);
//    }

    @Override
    public void onClick(View v) {
        if (titleTv == null) return;
        switch (titleTv.getText().toString().trim()) {
            case "关于我们":
                JumpUtils.startActivity(getContext(), AboutActivity.class);
                break;
            case "联系我们(15869031617)":
//                ToastUtils.showCustomToast("开发中...");
                break;
//            case Config.CUSTOMER_SERVICE_QQ:
//                JumpUtils.jumpQQ(getContext(), Config.CUSTOMER_SERVICE_QQ);
//                break;
            case "我的设置":
                JumpUtils.startActivity(getContext(), SettingActivity.class);
                break;
            case "设置登录密码":
                ToastUtils.showCustomToast("设置登录密码");
                break;
            case "修改登录密码":
                ToastUtils.showCustomToast("修改登录密码");
                break;
            default:
                break;
        }
    }

    public void hideIcon() {
        iconIv.setVisibility(GONE);
        ((MarginLayoutParams) titleTv.getLayoutParams()).leftMargin = 0;
    }

    public void showIcon() {
        iconIv.setVisibility(VISIBLE);
        ((MarginLayoutParams) titleTv.getLayoutParams()).leftMargin = ResUtil.dip2px(35);
    }
}
