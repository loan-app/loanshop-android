package com.ailiangbao.alb.ui.base.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by hll on 2019/3/2
 */
public class CustomEditText extends AppCompatEditText {

    private TextView textView;

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        Editable text = getText();
        if (text == null || TextUtils.isEmpty(text.toString().trim())) {
            if (textView != null) {
                textView.setVisibility(VISIBLE);
            }
        } else {
            if (textView != null && textView.getVisibility() == VISIBLE) {
                textView.setVisibility(GONE);
            }
        }
        //光标首次获取焦点是在最后面，之后操作就是按照点击的位置移动光标
        if (isEnabled() && hasFocus() && hasFocusable()) {
            setSelection(selEnd);
        } else {
            setSelection(getText().length());
        }
    }

    public void setHitView(TextView view) {
        this.textView = view;
    }

    public static class CustomTextWatcher implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    }
}
