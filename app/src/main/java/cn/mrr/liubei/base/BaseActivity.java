package cn.mrr.liubei.base;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.mrr.liubei.R;
import cn.mrr.liubei.manager.AppActivityTaskManager;
import cn.mrr.liubei.utils.StatusBarUtil;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseActivity extends SupportActivity implements View.OnClickListener {

    @Nullable
    @BindView(R.id.iv_bt_return)
    ImageView iv_bt_return;

    @Nullable
    @BindView(R.id.tv_bt_title)
    TextView tv_bt_title;

    @Nullable
    @BindView(R.id.tv_bt_right)
    TextView tv_bt_right;

    protected Activity mContext;

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initialize();

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        mContext = this;
        ButterKnife.bind(this);
        StatusBarUtil.setColor(mContext, getResources().getColor(R.color.white), 0);
        StatusBarUtil.setLightStatusBar(mContext, true);
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

    public void create(Bundle savedInstanceState){}

    protected void setStatusBar() {
        com.jaeger.library.StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    private void initLayout(){
        setContentView(R.layout.base_activity);
        LinearLayout content =  findViewById(R.id.ll_ba_main);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View v = inflater.inflate(getLayoutId(), null);
        if (null != v) {
            content.addView(v, params);
        }
    }


    public void showReturn() {
        if (null != iv_bt_return) {
            iv_bt_return.setVisibility(View.VISIBLE);
            iv_bt_return.setOnClickListener(this);
        }
    }


    public void setTitle(String title) {
        if (null != tv_bt_title) {
            tv_bt_title.setText(title);
        }
    }


    public void showRightButton(String value, int color) {
        if (null != tv_bt_right) {
            tv_bt_right.setVisibility(View.VISIBLE);
            tv_bt_right.setText(value);
            tv_bt_right.setTextColor(color);
            tv_bt_right.setOnClickListener(this);
        }
    }

    public void showRightButton(boolean isShow){
        if (null != tv_bt_right) {
            if (isShow){
                tv_bt_right.setVisibility(View.VISIBLE);
            } else {
                tv_bt_right.setVisibility(View.GONE);
            }
        }
    }


    public void rightClick() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bt_return:
                finish();
                break;
            case R.id.tv_bt_right:
                rightClick();
                break;
        }
    }


    protected void onViewCreated() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppActivityTaskManager.getInstance().removeActivity(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 回退
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startActivity(Activity from, Class<?> to, boolean isFinish) {
        Intent intent = new Intent();
        intent.setClass(from, to);
        startActivity(intent);
        if (isFinish) {
            from.finish();
        }
    }

    // 带参数的Activity
    protected void startActivity(Activity from, Class<?> to, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent();
        if (bundle != null)
            intent.putExtras(bundle);
        intent.setClass(from, to);
        startActivity(intent);
        if (isFinish) {
            from.finish();
        }
    }

}
