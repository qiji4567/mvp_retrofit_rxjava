package com.htbot.coffee.ui.operation.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.htbot.coffee.MyApplication;
import com.htbot.coffee.R;
import com.htbot.coffee.adapter.MaterialManagerAdapter;
import com.htbot.coffee.api.OperationApi;
import com.htbot.coffee.base.BaseTaskQueueFragment;
import com.htbot.coffee.databinding.FragmentMaterialManagementBinding;
import com.htbot.coffee.dialog.AddBeansDialog;
import com.htbot.coffee.dialog.CommonDialog;
import com.htbot.coffee.dialog.UpdatePzDialog;
import com.htbot.coffee.entity.AddBeansBean;
import com.htbot.coffee.entity.MaterialOrderBean;
import com.htbot.coffee.util.AESUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 物料管理页面
 *
 * 轮询优化点：
 * 1. 防止接口重复请求
 * 2. 数据无变化时不刷新列表
 * 3. 网络断开时暂停轮询
 * 4. 连续无变化时自动延长轮询间隔
 *
 * @author 53443
 */
public class MaterialManagementFragment extends BaseTaskQueueFragment<FragmentMaterialManagementBinding> {

    /**
     * 默认前台轮询：3秒
     */
    private static final long POLLING_FAST = 3000L;

    /**
     * 中速轮询：6秒
     */
    private static final long POLLING_MEDIUM = 6000L;

    /**
     * 慢速轮询：10秒
     */
    private static final long POLLING_SLOW = 10000L;

    /**
     * 后台轮询：15秒
     */
    private static final long POLLING_BACKGROUND = 15000L;

    /**
     * 物料管理适配器
     */
    private MaterialManagerAdapter materialManagerAdapter;

    /**
     * 当前展示的数据
     */
    private final List<MaterialOrderBean> materialOrderBeanList = new ArrayList<>();

    /**
     * 当前是否正在请求接口，防止重复请求
     */
    private volatile boolean isRequesting = false;

    /**
     * 上一次数据指纹
     */
    private String lastDataFingerprint = "";

    /**
     * 连续无变化次数
     */
    private int noChangeCount = 0;

    @Override
    protected void initView() {
        initRecyclerView();
        initAdapterListener();

        // 开启轮询：前台3秒，后台15秒，首次立即执行
        enablePolling(true, POLLING_FAST, POLLING_BACKGROUND, true);
    }

    /**
     * 初始化列表
     */
    private void initRecyclerView() {
        materialManagerAdapter = new MaterialManagerAdapter(requireActivity(), materialOrderBeanList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        binding.rvMaterial.setLayoutManager(layoutManager);
        binding.rvMaterial.setAdapter(materialManagerAdapter);
    }

    /**
     * 初始化适配器统一点击事件
     */
    private void initAdapterListener() {
        materialManagerAdapter.setOnMaterialActionListener((actionType, position, materialOrderBean) -> {
            switch (actionType) {
                case ADD_BEANS:
                    showAddBeansDialog(materialOrderBean);
                    break;
                case UPDATE_BEANS:
                    showUpdateBeansDialog(materialOrderBean);
                    break;
                case EDIT_TARE:
                    showUpdateTareDialog(materialOrderBean);
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * 当前是否允许轮询
     * 断网或正在请求中时，直接跳过本次轮询
     */
    @Override
    protected boolean canRunPolling() {
        return isNetworkAvailable() && !isRequesting;
    }

    /**
     * 轮询执行逻辑
     */
    @Override
    protected void onPollingTask() {
        getMaterialInfo(false);
    }

    @Override
    protected void initData() {
        getMaterialInfo(true);
    }

    @Override
    public void initListener() {

    }

    /**
     * 显示加豆弹窗
     */
    private void showAddBeansDialog(@NonNull MaterialOrderBean materialOrderBean) {
        AddBeansDialog addBeansDialog = new AddBeansDialog(
                requireActivity(),
                getString(R.string.add),
                new AddBeansDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm(AddBeansBean addBeansBean) {
                        showAddBeansConfirmDialog(materialOrderBean, addBeansBean);
                    }

                    @Override
                    public void onCancel() {

                    }
                }
        );
        addBeansDialog.show();
    }

    /**
     * 显示加豆确认弹窗
     */
    private void showAddBeansConfirmDialog(@NonNull MaterialOrderBean materialOrderBean,
                                           @NonNull AddBeansBean addBeansBean) {
        String content = String.format(getString(R.string.confirm_add_beans), materialOrderBean.getName())
                + addBeansBean.getCount()
                + addBeansBean.getUnit()
                + "?";

        CommonDialog commonDialog = new CommonDialog(
                requireActivity(),
                getString(R.string.add),
                content,
                new CommonDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        addMaterial(materialOrderBean.getId(), String.valueOf(addBeansBean.getCount()));
                    }

                    @Override
                    public void onCancel() {

                    }
                }
        );
        commonDialog.show();
    }

    /**
     * 显示修改余量弹窗
     */
    private void showUpdateBeansDialog(@NonNull MaterialOrderBean materialOrderBean) {
        AddBeansDialog updateDialog = new AddBeansDialog(
                requireActivity(),
                getString(R.string.add),
                new AddBeansDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm(AddBeansBean addBeansBean) {
                        showUpdateBeansConfirmDialog(materialOrderBean, addBeansBean);
                    }

                    @Override
                    public void onCancel() {

                    }
                }
        );
        updateDialog.show();
    }

    /**
     * 显示修改余量确认弹窗
     */
    private void showUpdateBeansConfirmDialog(@NonNull MaterialOrderBean materialOrderBean,
                                              @NonNull AddBeansBean addBeansBean) {
        String content = String.format(getString(R.string.confirm_add_beans2), materialOrderBean.getName())
                + addBeansBean.getCount()
                + addBeansBean.getUnit()
                + "?";

        CommonDialog commonDialog = new CommonDialog(
                requireActivity(),
                getString(R.string.update_beans),
                content,
                new CommonDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        updateMaterial(materialOrderBean.getId(), String.valueOf(addBeansBean.getCount()));
                    }

                    @Override
                    public void onCancel() {

                    }
                }
        );
        commonDialog.show();
    }

    /**
     * 显示修改皮重弹窗
     */
    private void showUpdateTareDialog(@NonNull MaterialOrderBean materialOrderBean) {
        UpdatePzDialog updatePzDialog = new UpdatePzDialog(
                requireActivity(),
                getString(R.string.update_pz),
                new UpdatePzDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm(String content) {
                        updateTare(materialOrderBean.getId(), content);
                    }

                    @Override
                    public void onCancel() {

                    }
                }
        );
        updatePzDialog.show();
    }

    /**
     * 加豆
     */
    @SuppressLint("CheckResult")
    private void addMaterial(int orderId, @NonNull String quantity) {
        HashMap<String, Object> postMap = new HashMap<>();
        postMap.put("id", String.valueOf(orderId));
        postMap.put("quantity", quantity);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                AESUtils.createRequestOperateBody(postMap)
        );

        OperationApi.addMaterial(body)
                .doOnError(error -> isRequesting = false)
                .subscribe(o -> {
                    if (o.getSuccess()) {
                        // 手动操作后立即强制刷新
                        resetPollingSpeed();
                        getMaterialInfo(true);
                    }
                    ToastUtils.showLong(o.getMessage());
                }, throwable -> {
                    isRequesting = false;
                    ToastUtils.showLong(throwable.getMessage());
                });
    }

    /**
     * 修改余量
     */
    @SuppressLint("CheckResult")
    private void updateMaterial(int orderId, @NonNull String remain) {
        HashMap<String, Object> postMap = new HashMap<>();
        postMap.put("id", String.valueOf(orderId));
        postMap.put("remain", remain);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                AESUtils.createRequestOperateBody(postMap)
        );

        OperationApi.updateMaterial(body)
                .doOnError(error -> isRequesting = false)
                .subscribe(o -> {
                    if (o.getSuccess()) {
                        resetPollingSpeed();
                        getMaterialInfo(true);
                    }
                    ToastUtils.showLong(o.getMessage());
                }, throwable -> {
                    isRequesting = false;
                    ToastUtils.showLong(throwable.getMessage());
                });
    }

    /**
     * 修改皮重
     */
    @SuppressLint("CheckResult")
    private void updateTare(int orderId, @NonNull String tare) {
        HashMap<String, Object> postMap = new HashMap<>();
        postMap.put("id", String.valueOf(orderId));
        postMap.put("tare", tare);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                AESUtils.createRequestOperateBody(postMap)
        );

        OperationApi.updateTare(body)
                .doOnError(error -> isRequesting = false)
                .subscribe(o -> {
                    if (o.getSuccess()) {
                        resetPollingSpeed();
                        getMaterialInfo(true);
                    }
                    ToastUtils.showLong(o.getMessage());
                }, throwable -> {
                    isRequesting = false;
                    ToastUtils.showLong(throwable.getMessage());
                });
    }

    /**
     * 获取物料列表
     *
     * @param forceRefresh true=强制刷新UI；false=仅数据变化时刷新
     */
    @SuppressLint("CheckResult")
    private void getMaterialInfo(boolean forceRefresh) {
        if (isRequesting) {
            return;
        }

        if (!isNetworkAvailable()) {
            stopPollingNow();
            return;
        }

        isRequesting = true;

        OperationApi.getEquipmentBin(String.valueOf(MyApplication.instance.getEquipmentId()))
                .doFinally(() -> isRequesting = false)
                .subscribe(o -> {
                    if (!o.getSuccess()) {
                        ToastUtils.showLong(o.getMessage());
                        return;
                    }

                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<MaterialOrderBean>>() {
                    }.getType();

                    List<MaterialOrderBean> newDataList = gson.fromJson(o.getData(), listType);
                    if (newDataList == null) {
                        newDataList = new ArrayList<>();
                    }

                    boolean changed = isDataChanged(newDataList);

                    if (forceRefresh || changed) {
                        materialOrderBeanList.clear();
                        materialOrderBeanList.addAll(newDataList);

                        if (materialManagerAdapter != null) {
                            materialManagerAdapter.refresh(materialOrderBeanList);
                        }
                    }

                    if (changed) {
                        lastDataFingerprint = buildDataFingerprint(newDataList);
                        noChangeCount = 0;
                        updateCurrentPollingInterval(POLLING_FAST);
                    } else {
                        noChangeCount++;
                        adjustPollingIntervalByStability();
                    }

                    // 断网恢复后，确保轮询正常启动
                    startPollingNow(false);

                }, throwable -> {
                    ToastUtils.showLong(throwable.getMessage());

                    // 请求失败时适当放慢一点，避免疯狂打接口
                    updateCurrentPollingInterval(POLLING_MEDIUM);
                });
    }

    /**
     * 判断数据是否变化
     */
    private boolean isDataChanged(@NonNull List<MaterialOrderBean> newDataList) {
        String newFingerprint = buildDataFingerprint(newDataList);
        return !Objects.equals(lastDataFingerprint, newFingerprint);
    }

    /**
     * 构建数据指纹
     *
     * 只拼接会影响界面展示的核心字段
     */
    @NonNull
    private String buildDataFingerprint(@NonNull List<MaterialOrderBean> list) {
        StringBuilder sb = new StringBuilder();

        for (MaterialOrderBean bean : list) {
            sb.append(bean.getId()).append("_")
                    .append(bean.getName()).append("_")
                    .append(bean.getRemain()).append("_")
                    .append(bean.getCapacity()).append("_")
                    .append(bean.getTare()).append("_")
                    .append(bean.getImg()).append("_")
                    .append(bean.getCountWay()).append("_")
                    .append(bean.getUnit()).append(";");
        }

        return sb.toString();
    }

    /**
     * 根据连续无变化次数，自动调整轮询速度
     */
    private void adjustPollingIntervalByStability() {
        if (noChangeCount >= 10) {
            updateCurrentPollingInterval(POLLING_SLOW);
        } else if (noChangeCount >= 5) {
            updateCurrentPollingInterval(POLLING_MEDIUM);
        } else {
            updateCurrentPollingInterval(POLLING_FAST);
        }
    }

    /**
     * 恢复到快速轮询
     */
    private void resetPollingSpeed() {
        noChangeCount = 0;
        updateCurrentPollingInterval(POLLING_FAST);
    }

    /**
     * 判断当前网络是否可用
     */
    private boolean isNetworkAvailable() {
        Context context = getContext();
        if (context == null) {
            return false;
        }

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        }

        Network network = connectivityManager.getActiveNetwork();
        if (network == null) {
            return false;
        }

        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        if (capabilities == null) {
            return false;
        }

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }

    @Override
    public void onResume() {
        super.onResume();

        // 页面重新可见时，如果网络恢复了，保证轮询能继续
        if (isNetworkAvailable()) {
            startPollingNow(false);
        }
    }
}