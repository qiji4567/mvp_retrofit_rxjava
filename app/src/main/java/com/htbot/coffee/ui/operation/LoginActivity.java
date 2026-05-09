package com.htbot.coffee.ui.operation;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.htbot.coffee.R;
import com.htbot.coffee.application.MyApplication;
import com.htbot.coffee.base.BaseActivity;
import com.htbot.coffee.databinding.ActivityLoginBinding;
import com.htbot.coffee.net.api.OperationApi;
import com.htbot.coffee.utils.AESUtils;
import com.htbot.coffee.utils.KeyboardUtil;
import com.htbot.coffee.utils.ThemeUtils;

import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * 登录
 */

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;

    @Override
    public void initView() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init();
    }

    @Override
    public void getData() {
        int p = SPUtils.getInstance().getInt("themeType", 0);
        ThemeUtils.setThemeBg(binding.rlMain, p);
    }

    @Override
    public View getLayout() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void initListener() {
        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.etName.getText().toString();
                String pwd = binding.etPwd.getText().toString();
                if (StringUtils.isEmpty(name)) {
                    ToastUtils.showLong(getString(R.string.login_username_hint));
                    return;
                }
                if (StringUtils.isEmpty(pwd)) {
                    ToastUtils.showLong(getString(R.string.login_password_hint));
                    return;
                }
                doLogin(name, pwd);
            }
        });
        binding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.ivMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtil.hideKeyboard(LoginActivity.this);
            }
        });
    }

    @SuppressLint("CheckResult")
    private void doLogin(String account, String password) {
        OperationApi.getPublicKey()
                .subscribeOn(Schedulers.io())
                .flatMap(pkResponse -> {
                            String pk = pkResponse.getData();

                            HashMap<String, Object> postMap = new HashMap<>();
                            postMap.put("account", account);
                            postMap.put("password", AESUtils.encryptUserAndPwd(password, pk));
                            postMap.put("serialNumber", MyApplication.instance.getSerialNumber());
                            postMap.put("platform", "android");
                            RequestBody body = RequestBody.create(
                                    MediaType.parse("application/json; charset=utf-8"), AESUtils.createRequestOperateBody(postMap)
                            );
                            return OperationApi.login(body);
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                            if (o.getSuccess() && !StringUtils.isEmpty(o.getData())) {
                                SPUtils.getInstance().put("adminToken", o.getData());
                                Log.v("LoginActivity", "登陆成功");
                                startActivity(new Intent(LoginActivity.this, OperationMainActivity.class));
                                finish();
                            } else {
                                ToastUtils.showLong(o.getMessage());
                            }
                        }, e -> {
                            ToastUtils.showLong(e.getMessage());
                        }
                );
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThemeUtils.setTheme(this, binding.ivMain);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null && isTouchOutsideView(view, ev)) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public static boolean isTouchOutsideView(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        return x < location[0] || x > location[0] + view.getWidth() || y < location[1] || y > location[1] + view.getHeight();
    }
}
