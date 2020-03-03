package com.ailiangbao.alb.ui.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.lguipeng.library.animcheckbox.AnimCheckBox;

/**
 * Created by hll on 2019/3/9
 */
public class HCheckeBox extends AnimCheckBox {
    private boolean isOnTouch;

    public HCheckeBox(Context context) {
        super(context, null);
    }

    public HCheckeBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 是否拦载点击事件
     * @param isOnTouch
     */
    public void setDisPatchOnTouch(boolean isOnTouch) {
        this.isOnTouch = isOnTouch;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!isOnTouch) {
            return super.dispatchTouchEvent(event);
        } else {
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
