package cn.mrr.liubei.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.mrr.liubei.R;
import cn.mrr.liubei.databinding.BaseActivityBinding;
import cn.mrr.liubei.databinding.BaseTitleBinding;
import cn.mrr.liubei.manager.AppActivityTaskManager;

public abstract class  BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private long clickTime = 0;
    protected AppCompatActivity mContext;
    protected BaseActivityBinding baseBinding;
    protected BaseTitleBinding titleBinding;
    protected View contentView;

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initialize();

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initLayout();
        onViewCreated();
        AppActivityTaskManager.getInstance().addActivity(this);
        create(savedInstanceState);
        initialize();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
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

    private void initLayout() {
        baseBinding = BaseActivityBinding.inflate(getLayoutInflater());
        setContentView(baseBinding.getRoot());
        contentView = LayoutInflater.from(this).inflate(getLayoutId(), baseBinding.getRoot(), false);
        baseBinding.getRoot().addView(contentView);
        try {
            titleBinding = BaseTitleBinding.bind(contentView);
        } catch (RuntimeException ignored) {
            titleBinding = null;
        }
    }

    protected View getContentView() {
        return contentView;
    }

    public void showReturn() {
        if (titleBinding != null) {
            titleBinding.ivBtReturn.setVisibility(View.VISIBLE);
            titleBinding.ivBtReturn.setOnClickListener(this);
        }
    }

    public void setTitle(String title) {
        if (titleBinding != null) titleBinding.tvBtTitle.setText(title);
    }

    public void showRightButton(String value, int color) {
        if (titleBinding != null) {
            titleBinding.tvBtRight.setVisibility(View.VISIBLE);
            titleBinding.tvBtRight.setText(value);
            titleBinding.tvBtRight.setTextColor(color);
            titleBinding.tvBtRight.setOnClickListener(this);
        }
    }

    public void showRightButton(boolean isShow) {
        if (titleBinding != null)
            titleBinding.tvBtRight.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void rightClick() {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_bt_return) finish();
        else if (id == R.id.tv_bt_right) rightClick();
    }

    protected void onViewCreated() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppActivityTaskManager.getInstance().removeActivity(this);
        baseBinding = null;
        titleBinding = null;
        contentView = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startActivity(AppCompatActivity from, Class<?> to, boolean isFinish) {
        Intent intent = new Intent(from, to);
        startActivity(intent);
        if (isFinish) from.finish();
    }

    protected void startActivity(AppCompatActivity from, Class<?> to, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(from, to);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
        if (isFinish) from.finish();
    }
}
