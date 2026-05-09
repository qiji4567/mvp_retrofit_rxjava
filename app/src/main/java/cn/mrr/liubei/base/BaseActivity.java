package cn.mrr.liubei.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import cn.mrr.liubei.R;
import cn.mrr.liubei.databinding.BaseActivityBinding;
import cn.mrr.liubei.databinding.BaseTitleBinding;
import cn.mrr.liubei.manager.AppActivityTaskManager;

/**
 * @author 53443
 */
public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity implements View.OnClickListener {

    private long clickTime = 0;

    protected AppCompatActivity mContext;
    protected BaseActivityBinding baseBinding;
    protected BaseTitleBinding titleBinding;
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
        AppActivityTaskManager.getInstance().addActivity(this);
        create(savedInstanceState);
        initialize();
    }

    private void initLayout() {
        baseBinding = BaseActivityBinding.inflate(getLayoutInflater());
        setContentView(baseBinding.getRoot());

        binding = getViewBinding();
        baseBinding.getRoot().addView(binding.getRoot());

        try {
            titleBinding = BaseTitleBinding.bind(binding.getRoot());
        } catch (RuntimeException ignored) {
            titleBinding = null;
        }

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

    public void showReturn() {
        if (titleBinding != null) {
            titleBinding.ivBtReturn.setVisibility(View.VISIBLE);
            titleBinding.ivBtReturn.setOnClickListener(this);
        }
    }

    public void setTitle(String title) {
        if (titleBinding != null) {
            titleBinding.tvBtTitle.setText(title);
        }
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
        if (titleBinding != null) {
            titleBinding.tvBtRight.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    public void rightClick() {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.iv_bt_return) {
            finish();
        } else if (id == R.id.tv_bt_right) {
            rightClick();
        }
    }

    protected void onViewCreated() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppActivityTaskManager.getInstance().removeActivity(this);

        binding = null;
        baseBinding = null;
        titleBinding = null;
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