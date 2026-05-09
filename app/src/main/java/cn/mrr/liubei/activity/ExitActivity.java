package cn.mrr.liubei.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import androidx.viewbinding.ViewBinding;

import cn.mrr.liubei.R;
import cn.mrr.liubei.base.BaseActivity;
import cn.mrr.liubei.databinding.ActivityExitBinding;
import cn.mrr.liubei.manager.AppActivityTaskManager;

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
