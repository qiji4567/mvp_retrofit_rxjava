package com.htbot.coffee.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import com.htbot.coffee.adapter.FragmentAdapter;
import com.htbot.coffee.base.BaseActivity;
import com.htbot.coffee.databinding.ActivityActionBarBinding;
import com.htbot.coffee.fragment.FirstFragment;
import com.htbot.coffee.fragment.SecondFragment;
import com.htbot.coffee.fragment.ThirdFragment;

/**
 * @author 53443
 */
public class ActionBarActivity extends BaseActivity<ActivityActionBarBinding> {


    @Override
    protected ActivityActionBarBinding getViewBinding() {
        return ActivityActionBarBinding.inflate(getLayoutInflater());
    }

    @Override protected void initialize() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());
        binding.viewpager.setAdapter(new FragmentAdapter(fragmentManager, fragments));
    }
}
