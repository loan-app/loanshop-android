package com.ailiangbao.alb.ui.web;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.ui.base.BaseActivity;
import com.ailiangbao.alb.util.ToastUtils;
import com.ailiangbao.alb.util.WebUtils;
import com.dangbei.xlog.XLog;

public class WebActivity extends BaseActivity {
    public static final String WEB_ACTIVITY_BUNDLE = "web_activity_bundle";
    public static final String WEB_ACTIVITY_BUNDLE_EXTRA = "web_activity_bundle_extra";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.cancelLoading(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        init();
    }

    private void init() {
        webView = initWeb();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(WEB_ACTIVITY_BUNDLE);
            if (bundle != null) {
                String url = bundle.getString(WEB_ACTIVITY_BUNDLE_EXTRA);
                if (TextUtils.isEmpty(url)) return;
                webView.loadUrl(url);
            }
        }
    }

    @NonNull
    private WebView initWeb() {
        ProgressBar progressBar = findViewById(R.id.activity_web_progress_bar);
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        ((ViewGroup.MarginLayoutParams) progressBar.getLayoutParams()).topMargin = result;
        WebView webView = findViewById(R.id.activity_web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setBlockNetworkImage(true);
//        webSettings.setUseWideViewPort(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
//        webSettings.setLoadWithOverviewMode(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//关闭WebView中缓存
        webSettings.setUseWideViewPort(true);//true支持h5 的meta标签
        webSettings.setLoadWithOverviewMode(true);//适配屏幕大小(内容将自动缩放)
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕，内容将自动缩放 版本4.2后失效
//        webSettings.setTextZoom(100);//WebView里的字体就不会随系统字体大小设置发生变化了
        webView.setWebContentsDebuggingEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowContentAccess(true);
        String cacheDirPath = Environment.getExternalStorageDirectory().getPath() + "/webcache/";
        webSettings.setAppCachePath(cacheDirPath);
        webSettings.setDatabasePath(cacheDirPath);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });
        webView.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> {
            // TODO: 2017-5-6 处理下载事件
            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(url));
            if (intent.resolveActivity(getPackageManager()) != null) {
                final ComponentName componentName = intent.resolveActivity(getPackageManager());
                // 打印Log   ComponentName到底是什么
                XLog.d("web", "componentName = " + componentName.getClassName());
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
            } else {
                ToastUtils.showCustomToast("请先给手机安装浏览器", Toast.LENGTH_LONG);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                XLog.d("WebActivity---->Progress:", newProgress + "");
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);//progressBar.setProgress(newProgress);
                    webView.getSettings().setBlockNetworkImage(false);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);//设置加载进度
                }
            }
        });
        return webView;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            WebUtils.getInstance().start(false);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }
}
