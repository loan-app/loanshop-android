package com.ailiangbao.alb.ui.main;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.ailiangbao.alb.R;
import com.ailiangbao.alb.receiver.NetStateReceiver;
import com.ailiangbao.alb.ui.base.BaseActivity;
import com.ailiangbao.alb.ui.base.BaseFragment;
import com.ailiangbao.alb.ui.base.view.NotScrollViewPager;
import com.ailiangbao.alb.ui.main.adapter.ViewPagerAdapter;
import com.ailiangbao.alb.ui.main.dialog.UpdateDialog;
import com.ailiangbao.alb.ui.main.home.HomeFragment;
import com.ailiangbao.alb.ui.main.loan.AllLoanFragment;
import com.ailiangbao.alb.ui.main.my.MyFragment;
import com.ailiangbao.alb.util.ActivityManageUtils;
import com.ailiangbao.alb.util.AppInfoUtil;
import com.ailiangbao.alb.util.ContactsUtils;
import com.ailiangbao.alb.util.WebUtils;
import com.ailiangbao.provider.dal.net.http.entity.ContactsEntity;
import com.ailiangbao.provider.dal.util.collection.CollectionUtil;
import com.chaychan.library.BottomBarLayout;
import com.dangbei.xlog.XLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements MainContract.IMainViewer
        , View.OnClickListener
        , EasyPermissions.PermissionCallbacks, UpdateDialog.OnUpdateDialogListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String[] permissios = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    @Inject
    MainPresenter presenter;
    private int PERMISSIO_REQUEST_CODE = 111;
    private BaseFragment currentFragment;
    private long lastTime;
    private List<BaseFragment> list;
    private NotScrollViewPager viewPager;
    private BottomBarLayout bottomBar;
    private NetStateReceiver netStateReceiver;
    private UpdateDialog updateDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.cancelLoading(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViewerComponent().inject(this);
        presenter.bind(this);
        initFragment();
        init();
//        checkPermissions();
//        initData();
        initNetStateReceiver();
        initLoadWebQueue();
    }

    private void initLoadWebQueue() {
        WebUtils.getInstance().initWebView(this, findViewById(R.id.activity_tab_tv_root_view));
    }

    private void initNetStateReceiver() {
        presenter.checkUpdate(AppInfoUtil.getVersionName(this), AppInfoUtil.getChannel(this));
        presenter.postAppChannel();
        presenter.postAppDevice();
        presenter.postUV();
//        if (netStateReceiver == null) {
//            netStateReceiver = new NetStateReceiver();
//            netStateReceiver.setOnNetStateListener(() -> presenter.postAppChannel());
//        }
//        IntentFilter timeFilter = new IntentFilter();
//        timeFilter.addAction("android.net.ethernet.ETHERNET_STATE_CHANGED");
//        timeFilter.addAction("android.net.ethernet.STATE_CHANGE");
//        timeFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        timeFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
//        timeFilter.addAction("android.net.wifi.STATE_CHANGE");
//        timeFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//        registerReceiver(netStateReceiver, timeFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (netStateReceiver != null) {
//            unregisterReceiver(netStateReceiver);
//            netStateReceiver = null;
//        }
    }

    private boolean checkPermissions() {
        if (!EasyPermissions.hasPermissions(this, permissios)) {
            EasyPermissions.requestPermissions(this, "需要权限完成更新", PERMISSIO_REQUEST_CODE, permissios);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (String permission : permissions) {
            if (EasyPermissions.hasPermissions(this, permissios)) {
                if (updateDialog != null) {
                    updateDialog.download();
                }
//                getContractList();
            }
            XLog.i(TAG, "onRequestPermissionsResult--->结果" + permission);
        }
    }

    /**
     * 权限授权时的回调
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        for (String permission : perms) {
            XLog.i(TAG, "onPermissionsGranted--->允许" + permission);
        }
    }

    /**
     * 权限被拒时的回调
     */
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //判断权限被拒时，用户是否钩选了不在询问
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setRationale("需要权限完成更新")
                    .setTitle("更新权限")
                    .build()
                    .show();
        }
        for (String permission : perms) {
            XLog.i(TAG, "onPermissionsDenied--->被拒" + permission);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (!EasyPermissions.hasPermissions(this, permissios)) {
                //这里响应的是AppSettingsDialog点击取消按钮的效果
//                finish();
                showToast("权限没有允许");
            }
        }
    }

    private void init() {
//        TextView tabTv = findViewById(R.id.activity_tab_tv);
//        View line = findViewById(R.id.activity_tab_line);
        bottomBar = findViewById(R.id.activity_main_bottom_bar);
        viewPager = findViewById(R.id.activity_main_view_pager);
        viewPager.setOffscreenPageLimit(2);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.setList(list);
        viewPager.setAdapter(viewPagerAdapter);
        bottomBar.setViewPager(viewPager);
        bottomBar.setOnItemSelectedListener((bottomBarItem, position, i1) -> {
//                Log.d("home--->", tabId + "");
            if (list == null || list.size() <= 0) return;
            switch (bottomBarItem.getId()) {
                case R.id.activity_main_bottom_bar_item_01:
                    MainActivity.this.setFragment(0);
//                    tabTv.setText("提现");
//                    tabTv.setTextColor(ResUtil.getColor(R.color.tvPrimary));
//                    tabTv.setBackgroundColor(ResUtil.getColor(R.color.white));
//                    line.setVisibility(View.VISIBLE);
                    break;
                case R.id.activity_main_bottom_bar_item_02:
                    MainActivity.this.setFragment(1);
//                    tabTv.setText("认证");
//                    tabTv.setTextColor(ResUtil.getColor(R.color.tvPrimary));
//                    tabTv.setBackgroundColor(ResUtil.getColor(R.color.white));
//                    line.setVisibility(View.VISIBLE);
                    break;
                case R.id.activity_main_bottom_bar_item_03:
//                    tabTv.setText("我的");
//                    line.setVisibility(View.GONE);
//                    tabTv.setTextColor(ResUtil.getColor(R.color.white));
                    MainActivity.this.setFragment(2);
//                    tabTv.setBackgroundDrawable(DrawableUtils.getGradientDrawable(
//                            GradientDrawable.Orientation.TL_BR, 0, 0, 0, 0, 0xFFC7A060, 0xFFDFC188));
                    break;
            }
        });
    }

    private void setFragment(int position) {
        viewPager.setCurrentItem(position, false);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if (!fragment.isAdded()) {
//            transaction.add(R.id.activity_main_fl, fragment);
//        }
//        if (currentFragment != null) {
//            getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
//        }
//        currentFragment = fragment;
//        transaction.show(fragment).commit();
    }

    private void initFragment() {
        list = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        AllLoanFragment allLoanFragment = new AllLoanFragment();
        MyFragment myFragment = new MyFragment();
        list.add(homeFragment);
        list.add(allLoanFragment);
        list.add(myFragment);
    }

    private void initData() {
        presenter.requestData();
    }

    @Override
    public void onRequestData(String message) {
        showToast(message);
        XLog.i(TAG, message);
    }

    @Override
    public void onCheckUpdate(String s) {
        if (s.endsWith(".apk")) {
            showUpdateDialog(s);
        }
    }

    private void showUpdateDialog(String url) {
        if (updateDialog == null) {
            updateDialog = new UpdateDialog(this, url);
            updateDialog.setOnUpdateDialogListener(this);
        }
        updateDialog.show();
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.test) {
//            initData();
//            startActivity(new Intent(this, IDCardActivity.class));
//            getContractList();
//            startQQActivity();
//        }

    }

    private void getContractList() {
        List<ContactsEntity> contactList = ContactsUtils.getInstance(this).getContactList();
        if (!CollectionUtil.isEmpty(contactList)) {
            presenter.postContractList(contactList);
        }
        for (int i = 0; i < contactList.size(); i++) {
            ContactsEntity contactsEntity = contactList.get(i);
            if (contactsEntity != null) {
                XLog.d(TAG, "名字：" + contactsEntity.getName() + "------号码：" + contactsEntity.getNum());
            }
        }
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

    public void backHomeFragment() {
        if (bottomBar != null) {
            bottomBar.setCurrentItem(0);
        }
    }

    public void JumpLoanFragment() {
        if (bottomBar != null) {
            bottomBar.setCurrentItem(1);
        }
    }

    @Override
    public void onCheckPermissions() {
        if (checkPermissions() && updateDialog != null) {
            updateDialog.download();
        }
    }
}
