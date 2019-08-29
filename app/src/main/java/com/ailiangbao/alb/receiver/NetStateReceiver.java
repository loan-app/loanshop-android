package com.ailiangbao.alb.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.ailiangbao.alb.HApplication;
import com.ailiangbao.alb.util.ToastUtils;

/**
 * Created by hll on 2019/4/14
 */
public class NetStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            ConnectivityManager connectivityManager = (ConnectivityManager) HApplication.getInstance().getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                int type2 = networkInfo.getType();
                String typeName = networkInfo.getTypeName();
//                tv.setText(type2 + "--" + typeName);
                switch (type2) {
                    case 0://移动网络    2G 3G 4G 都是一样的 实测 mix2s 联通卡
                        break;
                    case 1: //wifi网络
                        break;
                    case 9:  //网线连接
                        break;
                }
                if (onNetStateListener != null) {
                    onNetStateListener.onUpLoad();
                }
//                ToastUtils.showCustomToast(type2 + "");
            } else {// 无网络
//                tv.setText("not connect");
            }
        }
    }

    private OnNetStateListener onNetStateListener;

    public void setOnNetStateListener(OnNetStateListener onNetStateListener) {
        this.onNetStateListener = onNetStateListener;
    }

    public interface OnNetStateListener {
        void onUpLoad();
    }
}
