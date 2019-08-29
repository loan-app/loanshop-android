package com.ailiangbao.alb.ui.main.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ailiangbao.alb.R;
import com.dangbei.xlog.XLog;
import com.dpuntu.downloader.DownloadManager;
import com.dpuntu.downloader.Downloader;
import com.dpuntu.downloader.Observer;

import java.io.File;
import java.util.UUID;

/**
 * author:hll
 * time:2017/12/27
 */

public class UpdateDialog extends Dialog implements View.OnClickListener {
    private final String url;
    private ProgressBar progressBar;
    private OnUpdateDialogListener onUpdateDialogListener;
    private TextView tv1, tv2;
    private TextView updateTv;
    private View line;
    private Downloader mDownloader;

    public UpdateDialog(Context context, String url) {
        super(context, R.style.update_app);
        setCancelable(false);
        this.url = url;
    }

    public interface OnUpdateDialogListener {
        void onCheckPermissions();
    }

    public void setOnUpdateDialogListener(OnUpdateDialogListener onUpdateDialogListener) {
        this.onUpdateDialogListener = onUpdateDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (null != window) {
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            window.setWindowAnimations(R.style.Exit_app_animation);
        }
        setContentView(R.layout.dialog_update_app);
        initView();
    }

    private void initView() {
        updateTv = findViewById(R.id.dialog_update_app_update);
        progressBar = findViewById(R.id.dialog_update_app_progress_bar);
        tv1 = findViewById(R.id.dialog_update_app_tv1);
        tv2 = findViewById(R.id.dialog_update_app_tv2);
        line = findViewById(R.id.dialog_update_app_line);
        updateTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_update_app_update:
                String trim = updateTv.getText().toString().trim();
                if ("马上更新".equals(trim)) {
                    if (onUpdateDialogListener != null) {
                        onUpdateDialogListener.onCheckPermissions();
                    }
                } else if ("安装".equals(trim)) {
                    startInstallApk(mDownloader);
                }
                break;
            default:
                break;
        }
    }

    public void download() {
        tv1.setText("下载中...");
        tv2.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);
        updateTv.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        startDownloadApk();
    }

    private void startDownloadApk() {
        String downloadId = UUID.randomUUID().toString().replaceAll("-", "");
        //                .client(client) //这就是第二部配置的OkHttpClient，你也可以不配置，下载器内部有个默认的OkHttpClient
// 这是你下载的文件需要存储的磁盘上的名字，必须设置项
//                .filePath("xxx") // 设置文件存储的路径，可省略，默认为根目录下 Android/data/你的app applicationId/files
//                .loadedSize(mLoadedSize)  // 这是下砸文件已经下载的大小，可以不设置，默认是0，如果是断点的话就必须设置，否则无法断点
//                .totalSize(mTotalSize) // 这是下载文件的文件总大小，可以不设置，默认是0，如果是断点的话就必须设置，否则无法断点
//下载文件的任务对应的id，用于标识单一任务，不可重复，必须设置
//下载文件的下载地址，必须配置
        mDownloader = new Downloader.Builder()
//                .client(client) //这就是第二部配置的OkHttpClient，你也可以不配置，下载器内部有个默认的OkHttpClient
                .fileName(downloadId + ".apk")  // 这是你下载的文件需要存储的磁盘上的名字，必须设置项
//                .filePath("xxx") // 设置文件存储的路径，可省略，默认为根目录下 Android/data/你的app applicationId/files
//                .loadedSize(mLoadedSize)  // 这是下砸文件已经下载的大小，可以不设置，默认是0，如果是断点的话就必须设置，否则无法断点
//                .totalSize(mTotalSize) // 这是下载文件的文件总大小，可以不设置，默认是0，如果是断点的话就必须设置，否则无法断点
                .taskId(downloadId) //下载文件的任务对应的id，用于标识单一任务，不可重复，必须设置
                .url(url) //下载文件的下载地址，必须配置
                .build();
        DownloadManager.addDownloader(mDownloader);
        DownloadManager.start(downloadId);
        DownloadManager.subjectTask(downloadId, new Observer() {
            @Override
            public void onCreate(String s) {
                XLog.d("onCreate-->", s);
            }

            @Override
            public void onReady(String s) {
                XLog.d("onReady-->", s);
            }

            @Override
            public void onLoading(String s, String s1, long l, long l1) {
                if (progressBar.getMax() != (int) l) {
                    progressBar.setMax((int) l);
                }
                progressBar.post(() -> progressBar.setProgress((int) l1));
//                XLog.d("onLoading-->", "s:" + s + ",s1" + s1 + ",L" + l + "L1" + l1);
            }

            @Override
            public void onPause(String s, long l, long l1) {
                XLog.d("onPause-->", "s:" + s + ",L" + l + "L1" + l1);
            }

            @Override
            public void onFinish(String s) {
                XLog.d("onFinish-->", s);
                updateTv.post(() -> {
                    updateTv.setText("安装");
                    updateTv.setVisibility(View.VISIBLE);
                });
                startInstallApk(mDownloader);
            }

            @Override
            public void onError(String s, String s1, long l, long l1) {
                XLog.d("onError-->", "s:" + s + ",s1" + s1 + ",L" + l + "L1" + l1);
            }
        });
    }

    private void startInstallApk(Downloader mDownloader) {
        Intent intent = new Intent();
        File apkFile = new File(mDownloader.getFilePath() + "/" + mDownloader.getFileName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".provider", apkFile);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        getContext().startActivity(intent);
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        if (blackHomeParentView.getVisibility() == View.VISIBLE) {
//            setNormalUI();
//        }else {
//
//        }
//        hideUI();
    }

    @Override
    public void show() {
        super.show();
    }

//
//    @Override
//    public boolean onUnhandledKey(KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
//            requestFocus();
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            this.dismiss();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    public void requestFocus() {
//        if (exitButtonTv != null) {
//            exitButtonTv.requestFocus();
//        }
//    }

    //
//    public void setData(List<HomeLiveItemEntity> list) {
//        if (CollectionUtil.isEmpty(list)) {
//            loadingView.stopLoading(view);
//            noDataTipTv.setVisibility(View.VISIBLE);
//            return;
//        }
//        this.list = list;
//        if (noDataTipTv != null) {
//            noDataTipTv.setVisibility(View.GONE);
//        }
//        if (horizontalRecyclerView != null) {
//            horizontalRecyclerView.setVisibility(View.VISIBLE);
//        }
//        if (exitAppAdapter != null) {
//            loadingView.stopLoading(view);
//            exitAppAdapter.setList(list);
//            exitAppAdapter.notifyDataSetChanged();
//        }
//    }

//    public void showNoDataTip() {
//        if (noDataTipTv != null) {
//            noDataTipTv.setVisibility(View.VISIBLE);
//        }
//        if (horizontalRecyclerView != null) {
//            horizontalRecyclerView.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public void show() {
//        super.show();
////        if (CollectionUtil.isEmpty(list)) {
////            loadingView.startLoading(view);
////            noDataTipTv.setVisibility(View.GONE);
////        }
//    }
}
