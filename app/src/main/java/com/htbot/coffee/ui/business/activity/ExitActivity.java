package com.htbot.coffee.ui.business.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.htbot.coffee.R;
import com.htbot.coffee.base.BaseActivity;
import com.htbot.coffee.databinding.ActivityExitBinding;
import com.htbot.coffee.manager.AppActivityTaskManager;
import com.htbot.coffee.ui.operation.LoginActivity;

/**
 * @author 53443
 */
public class ExitActivity extends BaseActivity<ActivityExitBinding> {


    @Override
    protected ActivityExitBinding getViewBinding() {
        return ActivityExitBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initialize() {
        binding.tvDeExit.setOnClickListener(this::onViewClick);
        binding.tvDeLogBack.setOnClickListener(this::onViewClick);
    }

    public void onViewClick(View view) {
        if (super.onViewClick()) return;
        int id = view.getId();
        if (id == R.id.tv_de_exit) {
            AppActivityTaskManager.getInstance().removeAllActivity();
            finish();
        } else if (id == R.id.tv_de_logBack) {
            AppActivityTaskManager.getInstance().removeAllActivity();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) return false;
        return super.onKeyDown(keyCode, event);
    }
}
