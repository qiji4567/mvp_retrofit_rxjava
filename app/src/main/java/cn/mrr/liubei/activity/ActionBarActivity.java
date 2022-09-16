package cn.mrr.liubei.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.jaeger.library.StatusBarUtil;

import cn.mrr.liubei.R;
import cn.mrr.liubei.adapter.FragmentAdapter;
import cn.mrr.liubei.base.BaseMVPActivity;
import cn.mrr.liubei.fragment.FirstFragment;
import cn.mrr.liubei.fragment.SecondFragment;
import cn.mrr.liubei.fragment.ThirdFragment;

public class ActionBarActivity extends BaseMVPActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_action_bar;
    }

    @Override
    protected void initialize() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());

        FragmentAdapter fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        viewPager.setAdapter(fragmentAdapter);
    }

}