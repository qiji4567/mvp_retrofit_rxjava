package cn.mrr.mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothClass;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.mrr.mvp.bean.IPLocationBean;
import cn.mrr.mvp.block.Contract;
import cn.mrr.mvp.block.Model;
import cn.mrr.mvp.block.Presenter;
import cn.mrr.mvp.network.NetWorkManager;
import cn.mrr.mvp.network.scheduler.BaseSchedulerProvider;

public class MainActivity extends AppCompatActivity implements Contract.IView {

    private static final String TAG = "MainActivity";
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnRequest).setOnClickListener(this::btnRequest);
        presenter = new Presenter(new Model(), this, BaseSchedulerProvider.getInstance());
    }


    //    点击事件监听
    public void btnRequest(View view) {
        String tvMac = "A358D809FA23";
        String appVersion = "V1.2.0";
        String requestType = "PUSH-PUB";
        Log.e(TAG, "DDDDDDDDDD");

        presenter.getIPLocation(tvMac, appVersion, requestType);

    }

    @Override
    public void getDataSuccess(Object json) {

        Log.d(TAG, String.format("正确的 %s ", json));
    }

    @Override
    public void getDataFail(String error) {
        Log.e(TAG, "error   " + error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}