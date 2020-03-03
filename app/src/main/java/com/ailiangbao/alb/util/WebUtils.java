package com.ailiangbao.alb.util;

import android.content.Context;
import android.net.http.SslError;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ailiangbao.alb.HApplication;
import com.ailiangbao.alb.ui.main.MainActivity;
import com.ailiangbao.provider.dal.util.collection.CollectionUtil;
import com.dangbei.xlog.XLog;

import java.util.ArrayList;
import java.util.List;

import static com.dangbei.xfunc.usage.Usage._callIfNotNull;

public class WebUtils {
    private static WebUtils instance;
    private List<String> list = new ArrayList<>();
    private WebView webView;
    private boolean isRunning = false;
    private Thread downloadThread;
    private RelativeLayout rootView;
    private Context context;
    private boolean isLoading;
    private long currentTime;
    private boolean isAddWebView;

    public static WebUtils getInstance() {
        if (instance == null) {
            instance = new WebUtils();
        }
        return instance;
    }

    public void initWebView(Context context, RelativeLayout view) {
        this.context = context;
        if (webView == null) {
            webView = new WebView(context);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -1);
            webView.setLayoutParams(params);
            webView.setVisibility(View.INVISIBLE);
            rootView = view;
            view.addView(webView, 0);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setBlockNetworkImage(true);
            webSettings.setDefaultTextEncodingName("utf-8");
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            webSettings.setAllowContentAccess(true);
            String cacheDirPath = Environment.getExternalStorageDirectory().getPath() + "/webcache/";
            webSettings.setAppCachePath(cacheDirPath);
            webSettings.setDatabasePath(cacheDirPath);
            webSettings.setAllowFileAccess(true);
            webSettings.setDatabaseEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setAppCacheEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//关闭WebView中缓存
            webSettings.setUseWideViewPort(true);//true支持h5 的meta标签
            webSettings.setLoadWithOverviewMode(true);//适配屏幕大小(内容将自动缩放)
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕，内容将自动缩放 版本4.2后失效
//          webSettings.setTextZoom(100);//WebView里的字体就不会随系统字体大小设置发生变化了
            webView.setWebContentsDebuggingEnabled(true);
            webSettings.setMediaPlaybackRequiresUserGesture(false);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                    handler.proceed();
                }
            });
            setWebViewChromeClient();
        }
    }

    private void setWebViewChromeClient() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                XLog.d("WebActivity---->Progress:", newProgress + "");
                if (newProgress == 100) {
                    webView.getSettings().setBlockNetworkImage(false);
                    isLoading = false;
                    //当一个网页加载完成后，开始加载另一个网页
//                    loadNextUrl();
                }
            }
        });
    }

    //    private PriorityBlockingQueue<String> waitingQueue = new PriorityBlockingQueue<>(5);
    private List<String> waitingQueue = new ArrayList<>();
    private Runnable submittingRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    if (!AppInfoUtil.isNetWorkAvailable(HApplication.getInstance())) {
//                        isRunning = false;
                        stop("submitting thread... top");
                    }
                    if (isLoading) {
                        if (System.currentTimeMillis() - currentTime >= 3000) {//超过三秒取消加载
                            isLoading = false;
                            continue;
                        }
                        if (waitingQueue.size() == 0) {
                            isRunning = false;
                            break;
                        }
                        XLog.d("webUtil", "加载中上。。。");
                        continue;
                    }
                    if (!isRunning) {
                        break;
                    }
                    String url = waitingQueue.get(0);
                    waitingQueue.remove(0);
                    XLog.i("webUtil", "VideoCache take : " + url);
                    if (!isRunning) {
                        break;
                    }
                    isLoading = true;
                    currentTime = System.currentTimeMillis();
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).runOnUiThread(() -> {
                            if (webView != null) {
                                webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                                webView.loadUrl(url);
                            } else {
                                stop();
                            }
                        });
                    }
                    if (isLoading) {
                        XLog.d("webUtils", "加载中下。。。");
                        continue;
                    }
                    if (!AppInfoUtil.isNetWorkAvailable(HApplication.getInstance())) {
//                        isRunning = false;
                        stop("submitting thread... after download");
                    }
                    if (!isRunning) {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    stop();
                }
            }
        }
    };

    public void submit(String url) {
        if (!list.contains(url)) {
            list.add(url);
            waitingQueue.add(url);
            if (!isRunning) {
                start(false);
            }
        }
    }

    public void stop() {
        stop("default");
    }

    public void stop(String message) {
        if (isRunning) {
            XLog.i("webUtil", "stop...message: " + message);
            isLoading = false;
            this.isRunning = false;
            _callIfNotNull(downloadThread, Thread::interrupt);
//            waitAll();
        }
    }

    public void start(boolean cellularNotify) {
        if (!isRunning) {
            if (CollectionUtil.isEmpty(waitingQueue)) return;
            XLog.i("webUtil", "Controller start download...");
            isRunning = true;
            downloadThread = new Thread(submittingRunnable);
            downloadThread.start();
        }
    }

    public void onDestroy() {
        _callIfNotNull(downloadThread, Thread::interrupt);
        if (rootView != null) {
            rootView.removeView(webView);
            rootView = null;
        }
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
    }

    protected void onPause() {
        if (webView != null) {
            webView.onPause();
        }
    }

    protected void onResume() {
        if (webView != null) {
            webView.onResume();
        }
    }

    public void isAddWebView(boolean isAddWebView) {
        this.isAddWebView = isAddWebView;
    }

    public WebView getWebView() {
        rootView.removeView(webView);
        return webView;
    }

    public void addWebView() {
        if (rootView != null && isAddWebView) {
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            rootView.addView(webView, 0);
            webView.setVisibility(View.INVISIBLE);
            setWebViewChromeClient();
        }
    }
}
