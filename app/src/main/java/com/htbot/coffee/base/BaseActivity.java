package com.htbot.coffee.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.htbot.coffee.manager.AppActivityTaskManager;

/**
 * @author 53443
 */
public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity implements View.OnClickListener {

    private long clickTime = 0;

    protected AppCompatActivity mContext;
    protected VB binding;

    protected abstract VB getViewBinding();

    protected abstract void initialize();

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        initLayout();

        onViewCreated();

        create(savedInstanceState);
        initialize();
    }

    private void initLayout() {
        binding = getViewBinding();
        setContentView(binding.getRoot());
        AppActivityTaskManager.getInstance().addActivity(this);
        setStatusBar();
    }

    protected View getContentView() {
        return binding == null ? null : binding.getRoot();
    }

    public VB getBinding() {
        return binding;
    }

    public boolean onViewClick() {
        if (System.currentTimeMillis() - 1000 < clickTime) {
            return true;
        }
        clickTime = System.currentTimeMillis();
        return false;
    }

    public void create(Bundle savedInstanceState) {
    }

    protected void setStatusBar() {
    }


    public void rightClick() {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

    }

    protected void onViewCreated() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppActivityTaskManager.getInstance().removeActivity(this);

        binding = null;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void startActivity(AppCompatActivity from, Class<?> to, boolean isFinish) {
        Intent intent = new Intent(from, to);
        startActivity(intent);
        if (isFinish) {
            from.finish();
        }
    }

    protected void startActivity(AppCompatActivity from, Class<?> to, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(from, to);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (isFinish) {
            from.finish();
        }
    }
}