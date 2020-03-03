package com.ailiangbao.alb.ui.login.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ailiangbao.alb.R;


/**
 * ================================================
 * <p>
 * Created by NIRVANA on 2018/8/23
 * Contact with <zwq651406441@gmail.com>
 * ================================================
 */
public class RequestPermissionDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private View.OnClickListener mOnClickListener;

    public RequestPermissionDialog(@NonNull Context context) {
        super(context, R.style.Update_dialog);
        mContext = context;
        initView();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @SuppressLint("StringFormatInvalid")
    private void initView() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.dialog_request_permission, null);
        TextView tvAllow = rootView.findViewById(R.id.tv_allow);
        TextView tvRequestPermissionTips = rootView.findViewById(R.id.tv_request_permission_tips);
        TextView tvDinyPermissionTips = rootView.findViewById(R.id.tv_diny_permission_tips);
        setContentView(rootView);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        tvAllow.setOnClickListener(this);
        tvRequestPermissionTips.setText(getContext().getString(R.string.placeholder_request_permission_desc, getContext().getString(R.string.app_name)));
        tvDinyPermissionTips.setText(getContext().getString(R.string.placeholder_refuse_permission_desc, getContext().getString(R.string.app_name)));
    }

    @Override
    public void onClick(View view) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(view);
            dismiss();
        }
    }
}
