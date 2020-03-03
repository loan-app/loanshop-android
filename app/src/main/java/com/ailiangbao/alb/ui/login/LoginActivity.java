package com.ailiangbao.alb.ui.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.ui.base.BaseActivity;
import com.ailiangbao.alb.ui.base.view.CustomEditText;
import com.ailiangbao.alb.ui.login.dialog.RequestPermissionDialog;
import com.ailiangbao.alb.ui.main.MainActivity;
import com.ailiangbao.alb.ui.main.dialog.UpdateDialog;
import com.ailiangbao.alb.util.ActivityManageUtils;
import com.ailiangbao.alb.util.AppInfoUtil;
import com.ailiangbao.alb.util.GlideUtil;
import com.ailiangbao.alb.util.JumpUtils;
import com.ailiangbao.alb.util.ResUtil;
import com.ailiangbao.alb.util.UpdateUtils;
import com.ailiangbao.alb.util.WebUtils;
import com.ailiangbao.provider.dal.net.http.webapi.WebApi;
import com.ailiangbao.provider.dal.net.http.webapi.WebApiConstants;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;
import com.ailiangbao.provider.support.bridge.compat.RxCompatObserver;
import com.dangbei.xlog.XLog;
import com.fm.openinstall.OpenInstall;
import com.github.lguipeng.library.animcheckbox.AnimCheckBox;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends BaseActivity implements LoginContract.ILoginViewer
        , View.OnClickListener, UpdateDialog.OnUpdateDialogListener {
    private static final String[] permissios = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    @Inject
    LoginPresenter presenter;

    private CustomEditText userIdEt;
    private CustomEditText picCodeEt;
    private CustomEditText passWordEt;
    private long lastTime;
    private boolean isCountDown;
    private Disposable disposable;
    private int radius;
    private Drawable focusDrawable;
    private TextView loginTv;
    private TextView sendTv;
    private TextView agreementTv;
    private AnimCheckBox checkBox;
    private ImageView userDeleteIv;
    private ImageView picCodeDeleteIv;
    private ImageView passWordDeleteIv;
    private ImageView picCodeIv;
    private boolean isClick = false;
    private int PERMISSION_SETTING_REQUEST = 3;
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.cancelLoading(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getViewerComponent().inject(this);
        presenter.bind(this);
        presenter.checkUpdate(AppInfoUtil.getVersionName(this), AppInfoUtil.getChannel(this));
        init();
    }

    private void init() {
        userIdEt = findViewById(R.id.activity_login_user_id_et);
        passWordEt = findViewById(R.id.activity_login_user_pass_word_et);
        picCodeEt = findViewById(R.id.activity_login_user_pic_code_et);
        picCodeIv = findViewById(R.id.activity_login_pic_code_iv);
        loginTv = findViewById(R.id.activity_login_login_tv);
        sendTv = findViewById(R.id.activity_login_send_tv);
//        agreementTv = findViewById(R.id.activity_login_agreement_tv);
//        checkBox = findViewById(R.id.activity_login_check_box);
        userDeleteIv = findViewById(R.id.activity_login_user_delete_iv);
        picCodeDeleteIv = findViewById(R.id.activity_login_pic_code_delete_iv);
        passWordDeleteIv = findViewById(R.id.activity_login_pass_word_delete_iv);
        sendTv.setOnClickListener(this);
        picCodeIv.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        userDeleteIv.setOnClickListener(this);
        picCodeDeleteIv.setOnClickListener(this);
        passWordDeleteIv.setOnClickListener(this);
        userIdEt.addTextChangedListener(new CustomEditText.CustomTextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setViewState(s, userIdEt, passWordEt, userDeleteIv);
            }

        });

        picCodeEt.addTextChangedListener(new CustomEditText.CustomTextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setViewState(s, picCodeEt, passWordEt, picCodeDeleteIv);
            }
        });

        passWordEt.addTextChangedListener(new CustomEditText.CustomTextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setViewState(s, userIdEt, passWordEt, passWordDeleteIv);
            }
        });
        loadPicCode();
    }

    private void loadPicCode() {
        uuid = UUID.randomUUID().toString();
        GlideUtil.loadImageRadius(this, picCodeIv, WebApiConstants.formatHttpWebApi(WebApi.Login.PIC_CODE)
                        + "?uuid=" + uuid + "&t=" + System.currentTimeMillis()
                , 0, 10);
    }

    private void setViewState(CharSequence s, EditText telephoneEt, EditText codeEt, ImageView imageView) {
        if (TextUtils.isEmpty(s)) {
            if (imageView.getVisibility() == View.VISIBLE) {
                imageView.setVisibility(View.GONE);
            }
        } else {
            if (imageView.getVisibility() == View.GONE) {
                imageView.setVisibility(View.VISIBLE);
            }
        }
        if (passWordEt.getText().toString().trim().matches("^\\d{6}") && userIdEt.getText().toString().trim().length() == 11
                && !TextUtils.isEmpty(picCodeEt.getText().toString().trim())) {
            isClick = true;
            loginTv.setBackground(ResUtil.getDrawable(R.drawable.shape_bg_red_5));
        } else {
            isClick = false;
            loginTv.setBackground(ResUtil.getDrawable(R.drawable.shape_bg_c9_radius_5));
        }
    }

    @Override
    public void onClick(View v) {
        String telephone = userIdEt.getText().toString().trim();
        switch (v.getId()) {
            case R.id.activity_login_login_tv:
                if (!isClick) return;
                String passWord = passWordEt.getText().toString().trim();
//                passWord = "123456";
//                telephone = "13868140535";
                if (!TextUtils.isEmpty(telephone) && telephone.length() == 11 && !TextUtils.isEmpty(passWord)) {
//                    if (checkBox.isChecked()) {
                    showLoadingDialog("登录中...");
                    presenter.requestLogin(telephone, passWord);
//                    } else {
//                        showToast("请勾选《个人信息使用协议》");
//                    }
                } else if (TextUtils.isEmpty(telephone) || telephone.length() != 11) {
                    showToast("请输入正确的手机号");
                } else if (TextUtils.isEmpty(passWord)) {
                    showToast("请输入验证码");
                }
                break;
            case R.id.activity_login_send_tv:
                if (isCountDown) return;
                if (TextUtils.isEmpty(telephone) || telephone.length() != 11) {
                    showToast("请输入正确的手机号");
                    return;
                }
                if (TextUtils.isEmpty(picCodeEt.getText().toString().trim())) {
                    showToast("请输入图形验证码");
                    return;
                }
                sendTv.setText("59s后重试");
                sendTv.setBackground(ResUtil.getDrawable(R.drawable.shape_bg_c9_radius_5));
                startCountDown();
                presenter.requestLoginCode(telephone, AppInfoUtil.getAppName(this)
                        , AppInfoUtil.getChannel(this), picCodeEt.getText().toString().trim(), uuid);
                break;
            case R.id.activity_login_pic_code_iv:
                loadPicCode();
                break;
            case R.id.activity_login_user_delete_iv:
                userIdEt.setText("");
//                userDeleteIv.setVisibility(View.GONE);
                break;
            case R.id.activity_login_pass_word_delete_iv:
                passWordEt.setText("");
//                passWordDeleteIv.setVisibility(View.GONE);
                break;
            case R.id.activity_login_pic_code_delete_iv:
                picCodeEt.setText("");
                break;
            default:
                break;
        }
    }

    private void startCountDown() {
        isCountDown = true;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(RxCompat.getSchedulerOnDb())
                .map(aLong -> 59 - aLong)
                .observeOn(RxCompat.getSchedulerOnMain())
                .subscribe(new RxCompatObserver<Long>() {

                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNextCompat(Long aLong) {
                        if (aLong <= 0) {
                            disposable.dispose();
                            resetGetCodeBt();
                            return;
                        }
                        sendTv.setText(aLong + "s后重试");
                        XLog.d("startCountDown=====>", aLong + "");
                    }
                });
    }

    private void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(userIdEt, InputMethodManager.SHOW_FORCED);
    }

    private void resetGetCodeBt() {
        sendTv.setBackground(ResUtil.getDrawable(R.drawable.shape_bg_red_5));
        sendTv.setText("获取验证码");
        isCountDown = false;
    }

    @Override
    public void onRequestLogin() {
        JumpUtils.startActivity(this, MainActivity.class);
        OpenInstall.reportRegister();
        finish();
    }

    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onRequestLoginError() {
        showToast("输入的验证码错误");
    }

    @Override
    public void onCheckUpdate(String url) {
        UpdateUtils.checkUpdate(this, url);
    }

    @Override
    public void onRequestLoginCodeError() {
        if (disposable != null) {
            disposable.dispose();
        }
        resetGetCodeBt();
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime <= 2000) {
            ActivityManageUtils.clearActivity();
            WebUtils.getInstance().onDestroy();
            super.onBackPressed();
            return;
        }
        lastTime = currentTime;

        showToast("再按一次退出");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCheckPermissions() {
        checkPermissions();
    }

    @SuppressLint("CheckResult")
    private void checkPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(permissios)
                .subscribe(granted -> {
                    if (granted) {
                        UpdateUtils.download();
                    } else {
                        showFailTipDialog(EasyPermissions.somePermissionDenied(LoginActivity.this, permissios));
                    }
                });
    }

    private void showFailTipDialog(boolean neverAsk) {
        //用户拒绝授予'读取手机'权限，弹窗提醒
        RequestPermissionDialog dialog = new RequestPermissionDialog(this);
        dialog.setOnClickListener(v -> {
            if (neverAsk) {
                checkPermissions();
            } else {
                goSystemAppSetting();
            }
        });
        dialog.show();
    }

    /**
     * 跳转到系统应用管理页面 引导用户开启权限
     */
    private void goSystemAppSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, PERMISSION_SETTING_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PERMISSION_SETTING_REQUEST == requestCode) {
            //从系统设置页面返回，重新检查是否获取相关权限
            checkPermissions();
        }
    }
}
