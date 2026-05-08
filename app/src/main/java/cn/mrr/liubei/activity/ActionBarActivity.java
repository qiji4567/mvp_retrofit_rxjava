package cn.mrr.liubei.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import cn.mrr.liubei.R;
import cn.mrr.liubei.adapter.FragmentAdapter;
import cn.mrr.liubei.base.BaseActivity;
import cn.mrr.liubei.databinding.ActivityActionBarBinding;
import cn.mrr.liubei.fragment.FirstFragment;
import cn.mrr.liubei.fragment.SecondFragment;
import cn.mrr.liubei.fragment.ThirdFragment;

public class ActionBarActivity extends BaseActivity {
    private ActivityActionBarBinding binding;

    @Override protected int getLayoutId() { return R.layout.activity_action_bar; }

    @Override protected void initialize() {
        binding = ActivityActionBarBinding.bind(getContentView());
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());
        binding.viewpager.setAdapter(new FragmentAdapter(fragmentManager, fragments));
    }
}
