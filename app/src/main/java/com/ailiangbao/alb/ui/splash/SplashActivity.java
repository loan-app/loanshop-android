package com.ailiangbao.alb.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.ui.base.BaseActivity;
import com.ailiangbao.alb.ui.login.LoginActivity;
import com.ailiangbao.alb.ui.main.MainActivity;
import com.ailiangbao.alb.ui.main.home.vm.HomeVM;
import com.ailiangbao.alb.util.AppInfoUtil;
import com.ailiangbao.provider.support.bridge.compat.RxCompat;
import com.ailiangbao.provider.support.bridge.compat.RxCompatObserver;
import com.dangbei.xlog.XLog;
import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppInstallAdapter;
import com.fm.openinstall.model.AppData;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends BaseActivity implements SplashContract.ISplashViewer, View.OnClickListener {
    @Inject
    SplashPresenter presenter;
    private boolean isLogin = true;
    private TextView tipTv;
    Disposable disposable;
    private HomeVM homeVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.cancelLoading(false);
        super.onCreate(savedInstanceState);
        //当启动页的启动模式为singleTask时，会出现按Home键后回APP时重新进入启动页，第一次安装APP时，启动APP后按Home回卓面再按图标回到APP中时会重启Activity。此方法可以解决此问题
        if (!isTaskRoot()) {
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);
        getViewerComponent().inject(this);
        presenter.bind(this);
        tipTv = findViewById(R.id.activity_splash_count_down);
        tipTv.setOnClickListener(this);
        //获取OpenInstall安装数据
        OpenInstall.getInstall(new AppInstallAdapter() {
            @Override
            public void onInstall(AppData appData) {
                //获取渠道数据
                AppInfoUtil.setChannel(appData.getChannel());
                //获取自定义数据
//                String bindData = appData.getData();
                Log.d("OpenInstall", "getInstall : installData = " + appData.toString());
                presenter.postOpenApp();
                presenter.requestHomeData();
                tipTv.setVisibility(View.VISIBLE);
                countDown();
            }
        });
    }

    private void countDown() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(aLong -> 2 - aLong)
                .compose(RxCompat.observableOnMain())
                .subscribe(new RxCompatObserver<Long>() {

                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNextCompat(Long aLong) {
                        tipTv.setText("跳过(" + aLong + "s)");
                        if (aLong <= 0) {
                            disposable.dispose();
                            startActivity(new Intent(SplashActivity.this, isLogin ? LoginActivity.class : MainActivity.class));
                            finish();
                        }
                        XLog.d("SplashActivity", "onNextCompat---每次的回调" + aLong);
                    }

                    @Override
                    public void onCompleteCompat() {
                        super.onCompleteCompat();
                    }
                });
    }

    @Override
    public void onRequestData(HomeVM homeVM) {
        this.homeVM = homeVM;
        isLogin = false;
    }

    @Override
    public void onLogin() {
        isLogin = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除所有视图
//        ViewGroup view = (ViewGroup) getWindow().getDecorView();
//        view.removeAllViews();
//        System.gc();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        startActivity(new Intent(SplashActivity.this, isLogin ? LoginActivity.class : MainActivity.class));
        finish();
    }
}
