package com.ailiangbao.alb.ui.base;


import android.content.Context;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.util.ResUtil;
import com.dangbei.mvparchitecture.viewer.ViewerDelegateDefault;

/**
 * 暂时默认继承ViewerDelegateDefault，便于之后的样式扩展重写
 * <p>
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/2/16.
 */
public class BaseViewerDelegate extends ViewerDelegateDefault {

    private LiveLoadingView loadingDialog;

    public BaseViewerDelegate(Context context) {
        super(context);
    }

    /**
     * *************************LOADING_DIALOG-START****************************
     */

    @Override
    public void showLoadingDialog(String message) {
        showLoadingMessageDialog(message);
    }

    @Override
    public void showLoadingDialog(int resStringId) {
        showLoadingMessageDialog(ResUtil.getString(resStringId));
    }

    public void showLoadingMessageDialog(String message) {
        if (mContextRef == null || mContextRef.get() == null) {
            return;
        }
        if (null == loadingDialog) {
            loadingDialog = new LiveLoadingView(mContextRef.get(), R.style.FullDialog);
        }
        loadingDialog.showLoading(message);
    }

    @Override
    public void cancelLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.dismissLoading();
        }
    }

    @Override
    public void showToast(String message) {
        super.showToast(message);
//        ToastUtils.toast(message);
    }

    @Override
    public void showToast(int resStringId) {
        super.showToast(resStringId);
//        ToastUtils.toast(resStringId);
    }
}
