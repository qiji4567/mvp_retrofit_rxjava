package com.htbot.coffee.ui.operation.fragment;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.htbot.coffee.MyApplication;
import com.htbot.coffee.R;
import com.htbot.coffee.api.OperationApi;
import com.htbot.coffee.base.BaseFragment;
import com.htbot.coffee.databinding.FragmentOperateManagerBinding;
import com.htbot.coffee.entity.DeviceInfoBean;
import com.htbot.coffee.util.AESUtils;
import com.htbot.coffee.util.DeviceInfoUtil;
import com.htbot.coffee.util.LanguageUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 运维管理
 */
public class OperateManagerFragment extends BaseFragment<FragmentOperateManagerBinding> {


    @Override
    protected void initView() {
    }


    @Override
    protected void initData() {
        DeviceInfoBean deviceInfoBean = DeviceInfoUtil.getDeviceInfo();
        if (deviceInfoBean != null) {
            String modelName = !TextUtils.isEmpty(deviceInfoBean.getModelName()) ? deviceInfoBean.getModelName() : "";

            boolean isModelName = modelName.toLowerCase().contains("a2");
            binding.llYbhw.setVisibility(isModelName ? View.GONE : View.VISIBLE);
        }
    }



    @Override
    public void initListener() {
        binding.llSzms.setOnClickListener(new ClickUtils.OnDebouncingClickListener() {
            @Override
            public void onDebouncingClick(View view) {
                DeviceInfoBean deviceInfoBean = DeviceInfoUtil.getDeviceInfo();
                if (Objects.equals(deviceInfoBean.getTrial(), -1)) {
                    ToastUtils.showLong(mActivity.getString(R.string.content_current_device_cannot_set_trial_make));
                } else {
                    command("trial", new JSONObject());
                    ToastUtils.showLong(mActivity.getString(R.string.content_success));

                }
            }
        });
        binding.llDczd.setOnClickListener(new ClickUtils.OnDebouncingClickListener() {
            @Override
            public void onDebouncingClick(View view) {
                command("vibration", new JSONObject());
                ToastUtils.showLong(mActivity.getString(R.string.content_success));
            }
        });
        binding.llZjQX.setOnClickListener(new ClickUtils.OnDebouncingClickListener() {
            @Override
            public void onDebouncingClick(View view) {
                try {
                    JSONObject data = new JSONObject();
                    data.put("type", "all");
                    data.put("index", 0);

                    command("clean", data);
                    ToastUtils.showLong(mActivity.getString(R.string.content_success));

                } catch (Exception e) {
                    ToastUtils.showLong(mActivity.getString(R.string.content_pump_batch_operation_error) + e.getMessage());
                }
            }
        });
        binding.llZbhw.setOnClickListener(new ClickUtils.OnDebouncingClickListener() {
            @Override
            public void onDebouncingClick(View view) {
                try {
                    JSONObject data = new JSONObject();
                    data.put("arm", "left");

                    command("toReadyPos", data);
                    ToastUtils.showLong(mActivity.getString(R.string.content_success));

                } catch (Exception e) {
                    ToastUtils.showLong(mActivity.getString(R.string.content_left_arm_reset_error) + e.getMessage());
                }
            }
        });
        binding.llYbhw.setOnClickListener(new ClickUtils.OnDebouncingClickListener() {
            @Override
            public void onDebouncingClick(View view) {
                try {
                    JSONObject data = new JSONObject();
                    data.put("arm", "right");

                    command("toReadyPos", data);
                    ToastUtils.showLong(mActivity.getString(R.string.content_success));

                } catch (Exception e) {
                    ToastUtils.showLong(mActivity.getString(R.string.content_right_arm_reset_error)+ e.getMessage());
                }

            }
        });
        binding.llYjhf.setOnClickListener(new ClickUtils.OnDebouncingClickListener() {
            @Override
            public void onDebouncingClick(View view) {
                command("recovery", new JSONObject());
                ToastUtils.showLong(mActivity.getString(R.string.content_success));

            }
        });
    }

    //操作指令
    @SuppressLint("CheckResult")
    private void command(String method, JSONObject data) {
        HashMap<String, Object> postMap = new HashMap<>();
        postMap.put("method", method);
        postMap.put("data", data);  // 将 data 传递进去
        postMap.put("id", MyApplication.instance.getEquipmentId() + "");
        postMap.put("language", LanguageUtil.getSavedLanguage(mActivity));


        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), AESUtils.createRequestOperateBody(postMap)
        );
        OperationApi.command(body)
                .doOnError(error -> {

                })
                .subscribe(o -> {
                    ToastUtils.showLong(mActivity.getString(R.string.content_execute_success));
                }, throwable -> {
                    ToastUtils.showLong(throwable.getMessage());
                });
    }
}
