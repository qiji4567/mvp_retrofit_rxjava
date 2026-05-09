package com.htbot.coffee.ui.operation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.htbot.coffee.MyApplication;
import com.htbot.coffee.R;
import com.htbot.coffee.adapter.OperateMenuLeftAdapter;
import com.htbot.coffee.api.OperationApi;
import com.htbot.coffee.base.BaseActivity;
import com.htbot.coffee.databinding.ActivityOperationMainBinding;
import com.htbot.coffee.entity.DeviceInfoBean;
import com.htbot.coffee.entity.MenuGroupBean;
import com.htbot.coffee.entity.MyEvent;
import com.htbot.coffee.ui.operation.fragment.CheckFragment;
import com.htbot.coffee.ui.operation.fragment.MakingOrderFragment;
import com.htbot.coffee.ui.operation.fragment.MaterialManagementFragment;
import com.htbot.coffee.ui.operation.fragment.OperateManagerFragment;
import com.htbot.coffee.ui.operation.fragment.SystemFragment;
import com.htbot.coffee.ui.operation.fragment.ThemeSettingFragment;
import com.htbot.coffee.util.AESUtils;
import com.htbot.coffee.util.DeviceInfoUtil;
import com.htbot.coffee.util.EventManager;
import com.htbot.coffee.util.ThemeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 运维管理首页
 * <p>
 * 本次重构点：
 * 1. 修复 Fragment 切换逻辑
 * 2. 增加每日清理签到入口 rlCheck
 * 3. 增加 CheckFragment 弹层显示逻辑
 * 4. 防止 SwitchCompat 程序赋值时误触发接口
 * 5. 方法拆分，职责更清晰，便于维护
 */
public class OperationMainActivity extends BaseActivity implements CheckFragment.OnCheckActionListener {

    private static final long DEVICE_INFO_POLLING_INTERVAL = 3000L;
    private static final String TAG_MAIN_FRAGMENT = "tag_main_fragment";
    private static final String TAG_CHECK_FRAGMENT = "tag_check_fragment";

    private ActivityOperationMainBinding binding;
    private OperateMenuLeftAdapter menuLeftAdapter;
    private final List<MenuGroupBean> dataList = new ArrayList<>();

    /**
     * 当前左侧菜单下标
     */
    private int currentIndex = 0;

    /**
     * 是否来自设备接口刷新设置 Switch，避免误触发 onCheckedChanged
     */
    private boolean isUpdatingOperateSwitch = false;

    /**
     * 是否从“非运营状态”切换到“运营状态”后需要关闭当前页
     */
    private boolean isOperate = false;

    private String[] menuTitle;

    private final int[] clickResource = new int[]{
            R.mipmap.ic_ywgl_y,
            R.mipmap.ic_wlgl_y,
            R.mipmap.ic_zzdd_y,
            R.mipmap.ic_xtsz_y,
            R.mipmap.ic_ztsz_y,
            R.mipmap.ic_szms
    };

    private final int[] unClickResource = new int[]{
            R.mipmap.ic_ywgl_n,
            R.mipmap.ic_wlgl_n,
            R.mipmap.ic_zzdd_n,
            R.mipmap.ic_xtsz_n,
            R.mipmap.ic_ztsz_n,
            R.mipmap.ic_szms
    };

    /**
     * 主内容 Fragment 列表缓存
     */
    private final List<Fragment> mainFragments = new ArrayList<>();

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable;

    public static void start(Context context) {
        Intent starter = new Intent(context, OperationMainActivity.class);
        context.startActivity(starter);
    }

    @Override
    public View getLayout() {
        binding = ActivityOperationMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void initView() {
        initImmersionBar();
        initMenuTitles();
        initMenuData();
        initMenuRecyclerView();
        initLeftMenuStyle();
        initFragmentList();
        switchMainFragment(0);
        resetBottomViewLayout();
    }

    @Override
    public void getData() {
        initTheme();
        initPollingTask();
        observeGlobalEvents();
    }

    @Override
    public void initListener() {
        initMenuClickListener();
        initOperateSwitchListener();
        initTopActionListeners();

    }

    // ========================= 初始化 =========================



    /**
     * 状态栏沉浸式设置
     */
    private void initImmersionBar() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init();
    }

    /**
     * 初始化菜单标题
     */
    private void initMenuTitles() {
        menuTitle = new String[]{
                getString(R.string.content_operation_management),
                getString(R.string.content_material_management),
                getString(R.string.content_production_orders),
                getString(R.string.content_system_settings),
                getString(R.string.content_theme_settings)
        };
    }

    /**
     * 初始化左侧菜单数据
     */
    private void initMenuData() {
        dataList.clear();
        int menuType = SPUtils.getInstance().getInt("menuType", 0);

        for (int i = 0; i < menuTitle.length; i++) {
            MenuGroupBean menuGroupBean = new MenuGroupBean();
            menuGroupBean.setClick(i == 0);
            menuGroupBean.setMenuType(menuType);
            menuGroupBean.setName(menuTitle[i]);
            menuGroupBean.setGroupImages(clickResource[i] + "," + unClickResource[i]);
            menuGroupBean.setCurrentIndex(0);
            dataList.add(menuGroupBean);
        }
    }

    /**
     * 初始化左侧菜单列表
     */
    private void initMenuRecyclerView() {
        menuLeftAdapter = new OperateMenuLeftAdapter(this, dataList, binding.headView, binding.llBottomItem);
        binding.rvGroup.setLayoutManager(new LinearLayoutManager(this));
        binding.rvGroup.setAdapter(menuLeftAdapter);
    }

    /**
     * 初始化左侧菜单皮肤风格
     */
    private void initLeftMenuStyle() {
        int menuType = SPUtils.getInstance().getInt("menuType", 0);
        applyMenuStyle(menuType);
    }

    /**
     * 初始化 Fragment 列表
     */
    private void initFragmentList() {
        mainFragments.clear();
        mainFragments.add(new OperateManagerFragment());
        mainFragments.add(new MaterialManagementFragment());
        mainFragments.add(new MakingOrderFragment());
        mainFragments.add(new SystemFragment());
        mainFragments.add(new ThemeSettingFragment());
    }

    /**
     * 底部占位布局适配
     */
    private void resetBottomViewLayout() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.weight = 1;
        binding.bottomView.setLayoutParams(layoutParams);
    }

    /**
     * 初始化主题
     */
    private void initTheme() {
        int themeType = SPUtils.getInstance().getInt("themeType", 0);
        ThemeUtils.setThemeBg(binding.rlMain, themeType);
    }

    /**
     * 初始化轮询任务
     */
    private void initPollingTask() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (MyApplication.instance.getEquipmentId() != null) {
                    getDeviceInfo();
                }
                handler.postDelayed(this, DEVICE_INFO_POLLING_INTERVAL);
            }
        };
    }

    /**
     * 监听全局事件
     */
    private void observeGlobalEvents() {
        EventManager.getInstance().getTokenExpiredLiveData()
                .observe(this, expired -> {
                    if (expired != null && expired) {
                        goToLoginPage();
                        EventManager.getInstance().resetTokenExpired();
                    }
                });
    }

    // ========================= Listener =========================

    /**
     * 左侧菜单点击
     */
    private void initMenuClickListener() {
        menuLeftAdapter.setOnItemClick((position, bean) -> {
            if (currentIndex == position) {
                return;
            }
            updateMenuSelection(position);
            switchMainFragment(position);
        });
    }

    /**
     * 运维开关监听
     */
    private void initOperateSwitchListener() {
        binding.switchOperate.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isUpdatingOperateSwitch) {
                return;
            }
            deviceOperationStatusUpdate(isChecked ? "1" : "0");
        });
    }

    /**
     * 顶部操作按钮监听
     */
    private void initTopActionListeners() {
        binding.ivBackPage.setOnClickListener(v -> handleBackAction());

        binding.rlExitLogin.setOnClickListener(v -> logout());

        binding.rlCheck.setOnClickListener(v -> openCheckDialog());
    }

    // ========================= 菜单 / Fragment =========================

    /**
     * 更新左侧菜单选中态
     */
    private void updateMenuSelection(int targetIndex) {
        currentIndex = targetIndex;
        for (int i = 0; i < dataList.size(); i++) {
            MenuGroupBean item = dataList.get(i);
            item.setCurrentIndex(targetIndex);
            item.setClick(i == targetIndex);
        }
        menuLeftAdapter.notifyDataSetChanged();
    }

    /**
     * 主内容 Fragment 切换
     * <p>
     * 修复点：
     * 原代码 fragment.isAdded() 后没有 show/hide 或 replace，切换不稳定。
     * 这里统一使用 show/hide，首次 add，后续 show。
     */
    private void switchMainFragment(int position) {
        if (position < 0 || position >= mainFragments.size()) {
            return;
        }

        Fragment targetFragment = mainFragments.get(position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // 隐藏所有主 Fragment
        for (Fragment fragment : mainFragments) {
            if (fragment.isAdded()) {
                transaction.hide(fragment);
            }
        }

        if (targetFragment.isAdded()) {
            transaction.show(targetFragment);
        } else {
            transaction.add(R.id.fragment_container, targetFragment, TAG_MAIN_FRAGMENT + "_" + position);
        }

        transaction.commitAllowingStateLoss();

        getDeviceInfo();
    }

    /**
     * 打开签到弹层
     */
    private void openCheckDialog() {




        Fragment existFragment = getSupportFragmentManager().findFragmentByTag(TAG_CHECK_FRAGMENT);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (existFragment == null) {
            transaction.add(R.id.flCheckContainer, CheckFragment.newInstance(), TAG_CHECK_FRAGMENT);
        } else {
            transaction.show(existFragment);
        }

        binding.flCheckContainer.setVisibility(View.VISIBLE);
        transaction.commitAllowingStateLoss();

    }

    /**
     * 关闭签到弹层
     */
    private void closeCheckFragment() {
        Fragment existFragment = getSupportFragmentManager().findFragmentByTag(TAG_CHECK_FRAGMENT);
        if (existFragment != null && existFragment.isAdded()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(existFragment);
            transaction.commitAllowingStateLoss();
        }
        binding.flCheckContainer.setVisibility(View.GONE);
    }

    // ========================= CheckFragment 回调 =========================

    /**
     * 签到成功
     */
    @Override
    public void onCheckSuccess() {
        getDeviceInfo();
        ToastUtils.showShort(getString(R.string.check_success_tip));
        closeCheckFragment();
    }

    @Override
    public void onCheckClose() {
        closeCheckFragment();
    }



    // ========================= 设备信息 =========================

    /**
     * 获取设备信息
     */
    @SuppressLint("CheckResult")
    private void getDeviceInfo() {
        OperationApi.getDeviceDetail(MyApplication.instance.getEquipmentId() + "")
                .doOnError(error -> {
                })
                .subscribe(o -> {
                    if (o.getSuccess()) {
                        DeviceInfoBean deviceInfoBean = new Gson().fromJson(o.getData(), DeviceInfoBean.class);
                        DeviceInfoUtil.setDeviceInfo(deviceInfoBean);
                        updateOperateSwitchState(deviceInfoBean);
                        updateTrialMode(deviceInfoBean);
                    }
                }, throwable -> ToastUtils.showLong(throwable.getMessage()));
    }

    /**
     * 更新运维开关显示状态
     */
    private void updateOperateSwitchState(@NonNull DeviceInfoBean deviceInfoBean) {
        boolean checked = deviceInfoBean.getOperationStatus() == 1;

        isUpdatingOperateSwitch = true;
        binding.switchOperate.setChecked(checked);
        isUpdatingOperateSwitch = false;

        if (!checked) {
            isOperate = true;
        }
    }

    /**
     * 更新试做模式文案
     */
    private void updateTrialMode(@NonNull DeviceInfoBean deviceInfoBean) {
        if (deviceInfoBean.getTrial() == 1) {
            binding.tvAppName.setText(R.string.trial_mode);
        } else {
            binding.tvAppName.setText(R.string.app_name);
        }
    }

    /**
     * 更新设备运维状态
     */
    @SuppressLint("CheckResult")
    private void deviceOperationStatusUpdate(String operationStatus) {
        HashMap<String, Object> postMap = new HashMap<>();
        postMap.put("id", MyApplication.instance.getEquipmentId() + "");
        postMap.put("operationStatus", operationStatus);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                AESUtils.createRequestOperateBody(postMap)
        );

        OperationApi.deviceOperationStatusUpdate(body)
                .doOnError(error -> getDeviceInfo())
                .subscribe(o -> {
                    if (o.getSuccess() && "1".equals(operationStatus) && isOperate) {
                        finish();
                    }
                }, throwable -> ToastUtils.showLong(throwable.getMessage()));
    }

    // ========================= 登录 / 返回 =========================

    /**
     * 返回逻辑处理
     */
    private void handleBackAction() {
        String passwordless = SPUtils.getInstance().getString("passwordless", "0");
        if (Objects.equals(passwordless, "1")) {
            logout();
        }
        finish();
    }

    /**
     * 跳转登录页
     */
    private void goToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * 退出登录
     */
    @SuppressLint("CheckResult")
    private void logout() {
        HashMap<String, Object> postMap = new HashMap<>();
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                AESUtils.createRequestOperateBody(postMap)
        );

        OperationApi.logout(body)
                .doOnError(error -> {
                })
                .subscribe(o -> {
                    if (o.getSuccess()) {
                        SPUtils.getInstance().put("adminToken", "");
                        SPUtils.getInstance().put("passwordless", "");
                        finish();
                    }
                }, throwable -> ToastUtils.showLong(throwable.getMessage()));
    }

    // ========================= UI 风格 =========================

    /**
     * 应用菜单风格
     */
    private void applyMenuStyle(int menuType) {
        if (menuType == 1) {
            binding.bottomView.setVisibility(View.GONE);
            binding.llLeft.setBackgroundResource(R.drawable.shape_1a000_25dp);
        } else {
            binding.llLeft.setBackgroundResource(0);
            binding.bottomView.setVisibility(View.VISIBLE);
        }
    }

    // ========================= 生命周期 =========================

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThemeUtils.setTheme(this, binding.ivMain);
        startDeviceInfoPolling();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopDeviceInfoPolling();
    }

    @Override
    public void onBackPressed() {
        // 如果签到弹层打开了，优先关闭弹层
        if (binding.flCheckContainer.getVisibility() == View.VISIBLE) {
            closeCheckFragment();
            return;
        }

        String passwordless = SPUtils.getInstance().getString("passwordless", "0");
        if (Objects.equals(passwordless, "1")) {
            logout();
        }
        super.onBackPressed();
    }

    /**
     * 启动设备信息轮询
     */
    private void startDeviceInfoPolling() {
        if (timerRunnable != null) {
            handler.removeCallbacks(timerRunnable);
            handler.postDelayed(timerRunnable, 1000);
        }
    }

    /**
     * 停止设备信息轮询
     */
    private void stopDeviceInfoPolling() {
        if (timerRunnable != null) {
            handler.removeCallbacks(timerRunnable);
        }
    }

    // ========================= EventBus =========================

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MyEvent event) {
        if (event.getType() == MyApplication.themeEvent) {
            int themeType = Integer.parseInt(event.getMessage());
            ThemeUtils.setThemeBg(binding.rlMain, themeType);
            ThemeUtils.setTheme(this, binding.ivMain);
        } else if (event.getType() == MyApplication.menuEvent) {
            int menuType = Integer.parseInt(event.getMessage());
            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).setMenuType(menuType);
                dataList.get(i).setClick(i == currentIndex);
            }
            menuLeftAdapter.notifyDataSetChanged();
            applyMenuStyle(menuType);
        }
    }
}