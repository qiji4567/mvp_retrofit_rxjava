package com.htbot.coffee.ui.operation.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.htbot.coffee.MyApplication;
import com.htbot.coffee.R;
import com.htbot.coffee.adapter.CheckDeviceAdapter;
import com.htbot.coffee.api.OperationApi;
import com.htbot.coffee.base.BaseFragment;
import com.htbot.coffee.databinding.FragmentCheckBinding;
import com.htbot.coffee.dialog.CheckDeviceDialog;
import com.htbot.coffee.entity.CleanTypeListBean;
import com.htbot.coffee.entity.UserInfoBean;
import com.htbot.coffee.ui.operation.CheckLaterActivity;
import com.htbot.coffee.util.AESUtils;
import com.htbot.coffee.util.DateUtil;
import com.htbot.coffee.util.LanguageUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 每日清理签到 Fragment
 * <p>
 * 功能：
 * 1. 提示运维人员每日清理机器
 * 2. 今日签到状态展示
 * 3. 最近 3 天签到记录展示
 * 4. 本地记录自动保留 30 天
 */
public class CheckFragment extends BaseFragment<FragmentCheckBinding> {


    private OnCheckActionListener onCheckActionListener;
    /**
     * 用户信息
     */
    private UserInfoBean userInfoBean;
    private CheckDeviceAdapter checkDeviceAdapter;

    public static CheckFragment newInstance() {
        Bundle args = new Bundle();
        CheckFragment fragment = new CheckFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCheckActionListener) {
            onCheckActionListener = (OnCheckActionListener) context;
        }
    }

    @Override
    protected void initView() {
        binding.tvTitle.setText(mContext.getString(R.string.check_title));
        recycleViewCheckDevice();
    }

    private void recycleViewCheckDevice() {
        checkDeviceAdapter = new CheckDeviceAdapter(mContext, new ArrayList<>());
        GridLayoutManager checkDeviceManager = new GridLayoutManager(mContext, 3);
        binding.rvCleanTypeList.setLayoutManager(checkDeviceManager);
        binding.rvCleanTypeList.setAdapter(checkDeviceAdapter);

        binding.tvDate.setText(DateUtil.dateToYYYYMMDDHHMMss(new Date()));
        binding.tvDesc.setText(mContext.getString(R.string.check_desc_done));
    }

    @Override
    protected void initData() {
        getUserInfo();
        cleanTypeList();
    }

    @Override
    protected void initListener() {


        binding.flClose.setOnClickListener(v -> {
            if (onCheckActionListener != null) {
                onCheckActionListener.onCheckClose();
            }
        });

        checkDeviceAdapter.setOnItemCheckListener(new CheckDeviceAdapter.OnItemCheckListener() {
            @Override
            public void onCheck(View view, CleanTypeListBean data, int position) {
               showCheckDeviceDialog(data);
            }
        });
    }

    private void showCheckDeviceDialog(CleanTypeListBean data) {

        CheckDeviceDialog checkDeviceDialog = new CheckDeviceDialog(mContext);
        checkDeviceDialog.setCheckDeviceDialogListener(new CheckDeviceDialog.OnCheckDeviceDialogListener() {
            @Override
            public void onCheckSuccess(View view) {
                deviceCleaningSubmit(data);
                checkDeviceDialog.dismiss();
            }

            @Override
            public void onLookCheckDevice(View view) {
                startActivity(new Intent(mContext, CheckLaterActivity.class)
                        .putExtra("cleanType",data.getCleanType()));
                checkDeviceDialog.dismiss();
            }
        });
        showCenterDialog(checkDeviceDialog);
    }

    /**
     * 获取用户信息
     */
    @SuppressLint("CheckResult")
    private void getUserInfo() {
        OperationApi.getUserDetail(LanguageUtil.getSavedLanguage(mContext))
                .subscribe(o -> {
                    if (o.getSuccess()) {
                        userInfoBean = new Gson().fromJson(o.getData(), UserInfoBean.class);
                    }
                }, throwable -> {
                });
    }

    /**
     * 获取清洗类型列表
     */
    @SuppressLint("CheckResult")
    private void cleanTypeList() {
        HashMap<String, Object> postMap = new HashMap<>();
        postMap.put("deviceId", MyApplication.instance.getEquipmentId() + "");

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                AESUtils.createRequestOperateBody(postMap)
        );

        OperationApi.cleanTypeList(body)
                .doOnError(error -> {
                })
                .subscribe(o -> {
                    if (o.getSuccess()) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<CleanTypeListBean>>() {
                        }.getType();
                        if (!TextUtils.isEmpty(o.getData())) {
                            List<CleanTypeListBean> dataList = gson.fromJson(o.getData(), listType);
                            checkDeviceAdapter.refresh(dataList);
                        }
                    }
                }, throwable -> {
                });
    }

    /**
     * 获取清洗类型列表
     * <p>
     * "data": {
     * "deviceId": 2,
     * "serialNumber": "68415caad582f200182e4151_hcbot-test-device",
     * "cleanType": "WASTE_WATER",
     * "operatorId": "1",
     * "operatorName": "张三",
     * "washTime": "2026-04-22 12:11:11",
     * "remark":"备注"
     * }
     *
     */
    @SuppressLint("CheckResult")
    private void deviceCleaningSubmit(CleanTypeListBean data) {
        if (userInfoBean == null) {
            getUserInfo();
            return;
        }
        HashMap<String, Object> postMap = new HashMap<>();
        postMap.put("deviceId", MyApplication.instance.getEquipmentId() + "");
        postMap.put("serialNumber", MyApplication.instance.getEquipmentId() + "");
        postMap.put("cleanType", data.getCleanType());
        postMap.put("operatorId", userInfoBean.getId() + "");
        postMap.put("operatorName", userInfoBean.getUsername());
        postMap.put("washTime", DateUtil.dateToYYYYMMDDHHMMss(new Date()));
        postMap.put("remark", data.getCleanTypeDesc() + "已签到");

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                AESUtils.createRequestOperateBody(postMap)
        );

        OperationApi.deviceCleaningSubmit(body)
                .doOnError(error -> {
                })
                .subscribe(o -> {
                    if (o.getSuccess()) {
                        showToast(getString(R.string.check_check_in_success));
                        cleanTypeList();
                    }
                }, throwable -> {
                });
    }


    /**
     * 关闭弹层
     */
    private void notifyClose() {
        if (onCheckActionListener != null) {
            onCheckActionListener.onCheckClose();
        }
    }

    /**
     * 签到
     */
    public interface OnCheckActionListener {
        /**
         * 签到成功
         */
        void onCheckSuccess();

        /**
         * 关闭弹层
         */
        void onCheckClose();
    }
}