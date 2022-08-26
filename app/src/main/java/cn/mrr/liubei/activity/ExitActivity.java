package cn.mrr.liubei.activity;

import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;

import butterknife.OnClick;
import cn.mrr.liubei.R;
import cn.mrr.liubei.base.BaseMVPActivity;
import cn.mrr.liubei.manager.AppActivityTaskManager;
import cn.mrr.liubei.utils.StatusBarUtil;


/**
 * Created by 王鑫
 * 重新登录Activity
 */
public class ExitActivity extends BaseMVPActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_exit;
    }

    @Override
    protected void initialize() {
        StatusBarUtil.setColor(this, Color.parseColor("#00000000"), 0);
        StatusBarUtil.setLightStatusBar(this, true);
    }

    @OnClick({R.id.tv_de_exit, R.id.tv_de_logBack})
    public void onViewClick(View view) {
        if (super.onViewClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_de_exit:
                //清除记录存储
//                Manager.setUserToken(this, "");
                AppActivityTaskManager.getInstance().removeAllActivity();
                finish();
                break;
            case R.id.tv_de_logBack:
                finish();
                //清除记录存储
//                Manager.setUserToken(this, "");
                AppActivityTaskManager.getInstance().removeAllActivity();
                //自定义跳转登录
//                Intent intent = new Intent(this, LoginActivity.class);
//                startActivity(intent);
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
