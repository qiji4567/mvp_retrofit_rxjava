package com.htbot.coffee.ui.operation.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.htbot.coffee.MyApplication;
import com.htbot.coffee.R;
import com.htbot.coffee.api.OperationApi;
import com.htbot.coffee.base.BaseFragment;
import com.htbot.coffee.databinding.FragmentSystemSettingBinding;
import com.htbot.coffee.entity.DeviceInfoBean;
import com.htbot.coffee.entity.VersionItem;
import com.htbot.coffee.util.AESUtils;
import com.htbot.coffee.util.DeviceInfoUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 系统设置
 */
public class SystemFragment extends BaseFragment<FragmentSystemSettingBinding> {

    private List<VersionItem> versions1 = new ArrayList<>();
    private List<VersionItem> versions2 = new ArrayList<>();

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        DeviceInfoBean deviceInfoBean = DeviceInfoUtil.getDeviceInfo();
//        binding.tvOrgName.setText(deviceInfoBean.getOrgName());
//        binding.tvDeviceName.setText(deviceInfoBean.getName());
//        binding.tvAppVersion.setText("1.0.0");
//        binding.tvDeviceSn.setText(deviceInfoBean.getSerialNumber()+"");
//        binding.tvLunBanVersion.setText(deviceInfoBean.getSoftwareVersion());

        // 初始化版本列表
        initVersionData(deviceInfoBean);
        // 动态添加版本条目
        addVersionItems();
    }

    private void initVersionData(DeviceInfoBean deviceInfo) {
        versions1.clear();

        versions1.add(new VersionItem(R.string.zzmc, deviceInfo.getOrgName()));
        versions1.add(new VersionItem(R.string.sbmc, deviceInfo.getName()));
        versions1.add(new VersionItem(R.string.sbsn, deviceInfo.getSerialNumber()));
        versions1.add(new VersionItem(R.string.appbb, deviceInfo.getPadVersion()));
        versions1.add(new VersionItem(R.string.lubanbb, deviceInfo.getOs()));

        versions1.add(new VersionItem(R.string.coffee_app_bb, deviceInfo.getCoffeeApp()));
        versions1.add(new VersionItem(R.string.mcu_bb, deviceInfo.getMcuVersion()));
        versions1.add(new VersionItem(R.string.arm_bb, deviceInfo.getArm()));
        versions1.add(new VersionItem(R.string.display_bb, deviceInfo.getDisplay()));

        versions2.clear();
    }

    private void addVersionItems() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout container1 = binding.llVersionContainer1;
        container1.removeAllViews();

        for (VersionItem item : versions1) {
            View itemView = inflater.inflate(R.layout.item_version, container1, false);

            TextView tvModule = itemView.findViewById(R.id.tvModuleName);
            TextView tvVersion = itemView.findViewById(R.id.tvVersion);

            tvModule.setText(getString(item.getId()));
            tvVersion.setText(item.getVersion());

            container1.addView(itemView);
        }

        // TODO: container2
    }



    @Override
    public void initListener() {
        binding.rlCheckVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                command("update");
            }
        });
        binding.rlRestartLuBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                command("reboot");
            }
        });
    }
//    /**
//     * 重启LUBAN
//     */
//    @SuppressLint("CheckResult")
//    private void deviceRestart(){
//        HashMap<String,Object> postMap = new HashMap<>();
//        postMap.put("id",MyApplication.deviceId+"");
//
//        RequestBody body = RequestBody.create(
//                MediaType.parse("application/json; charset=utf-8"), AESUtils.createRequestOperateBodyNoData(postMap)
//        );
//        OperationApi.deviceRestart(body)
//                .doOnError(error->{
//
//                })
//                .subscribe(o -> {
//                    if(o.getSuccess()){
//
//                    }
//                    ToastUtils.showLong(o.getMessage());
//
//                },throwable -> {
//                    ToastUtils.showLong(throwable.getMessage());
//                });
//    }

//    /**
//     * 版本更新
//     */
//    @SuppressLint("CheckResult")
//    private void deviceUpdate(){
//        OperationApi.deviceUpdate( MyApplication.deviceId+"")
//                .doOnError(error->{
//
//                })
//                .subscribe(o -> {
//                    if(o.getSuccess()){
//
//                    }
//
//                },throwable -> {
//                    ToastUtils.showLong(throwable.getMessage());
//                });
//    }


    @SuppressLint("CheckResult")
    private void command(String method) {
        HashMap<String, Object> postMap = new HashMap<>();
        postMap.put("method", method);
        postMap.put("data", new JSONObject());  // 将 data 传递进去
        postMap.put("id", MyApplication.instance.getEquipmentId() + "");

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), AESUtils.createRequestOperateBody(postMap)
        );
        OperationApi.command(body)
                .doOnError(error -> {

                })
                .subscribe(o -> {
                    //ToastUtils.showLong(o.getMessage());
                }, throwable -> {
                    ToastUtils.showLong(throwable.getMessage());
                });
    }


}
