package cn.mrr.liubei.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;

import com.zackratos.ultimatebarx.library.UltimateBarX;

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
        UltimateBarX.create(UltimateBarX.STATUS_BAR)        // 设置状态栏
                .fitWindow(true)                                // 布局是否侵入状态栏（true 不侵入，false 侵入）
                .bgColor(Color.BLACK)                           // 状态栏背景颜色（色值）
                .bgColorRes(R.color.colorPrimary)                // 状态栏背景颜色（资源id）
                .bgRes(R.drawable.bg_home_iv)                  // 状态栏背景 drawable
                .light(false)                                   // light模式（状态栏字体灰色 Android 6.0 以上支持）
                .apply(this);
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
                //清除记录存储
//                Manager.setUserToken(this, "");
                AppActivityTaskManager.getInstance().removeAllActivity();

//                自定义跳转登录
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
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
