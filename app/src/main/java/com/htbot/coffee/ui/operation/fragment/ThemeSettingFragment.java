package com.htbot.coffee.ui.operation.fragment;

import android.view.View;
import android.widget.SeekBar;

import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.htbot.coffee.MyApplication;
import com.htbot.coffee.R;
import com.htbot.coffee.adapter.BackgroundAdapter;
import com.htbot.coffee.base.BaseFragment;
import com.htbot.coffee.databinding.FragmentThemeSettingBinding;
import com.htbot.coffee.entity.BackgroundBean;
import com.htbot.coffee.entity.MyEvent;
import com.htbot.coffee.util.ThemeUtils;
import com.htbot.coffee.util.ViewsUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * 主题设置
 */
public class ThemeSettingFragment extends BaseFragment<FragmentThemeSettingBinding> {
    private BackgroundAdapter backgroundAdapter;
    private List<BackgroundBean> backgroundBeanList = new ArrayList<>();

    @Override
    protected void initView( ) {
        int round = ViewsUtil.dpToPxInt(getActivity(), 10);
        Glide.with(getActivity())
                .applyDefaultRequestOptions(new RequestOptions()
                        .transform(new RoundedCorners(round)))  // 设置圆角效果
                .load(R.mipmap.ic_menu_style1)
                .into(binding.ivMenuStyle1);  // 设置加载的目标视图
        Glide.with(getActivity())
                .applyDefaultRequestOptions(new RequestOptions()
                        .transform(new RoundedCorners(round)))  // 设置圆角效果
                .load(R.mipmap.ic_menu_style_2)
                .into(binding.ivMenuStyle2);  // 设置加载的目标视
        backgroundBeanList.clear();
        backgroundBeanList.add(new BackgroundBean(0, R.mipmap.ic_theme_1, true));
        backgroundBeanList.add(new BackgroundBean(1, R.mipmap.ic_theme_2, false));
        backgroundBeanList.add(new BackgroundBean(2, R.mipmap.ic_theme_3, false));
        backgroundBeanList.add(new BackgroundBean(3, R.mipmap.ic_theme_4, false));
        int p = SPUtils.getInstance().getInt("themeType", 0);
        for (int i = 0; i < backgroundBeanList.size(); i++) {
            backgroundBeanList.get(i).setSelect(p == i);
        }
        int p2 = SPUtils.getInstance().getInt("menuType", 0);
        if (p2 == 0) {
            binding.ivMenuSelect1.setVisibility(View.VISIBLE);
            binding.ivMenuSelect2.setVisibility(View.GONE);
        } else {
            binding.ivMenuSelect1.setVisibility(View.GONE);
            binding.ivMenuSelect2.setVisibility(View.VISIBLE);
        }
        backgroundAdapter = new BackgroundAdapter(getActivity(), backgroundBeanList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        binding.rvTheme.setLayoutManager(gridLayoutManager);
        binding.rvTheme.setAdapter(backgroundAdapter);
        backgroundAdapter.setOnItemClick(new BackgroundAdapter.ItemOnClick() {
            @Override
            public void onClick(int p, BackgroundBean bean) {
                for (int i = 0; i < backgroundBeanList.size(); i++) {
                    backgroundBeanList.get(i).setSelect(p == i);
                    if (p == i) {
                        ThemeUtils.themeBitmap = null;
                        SPUtils.getInstance().put("themeType", p);
                        EventBus.getDefault().post(new MyEvent(MyApplication.themeEvent, p + ""));
                    }
                }
                backgroundAdapter.notifyDataSetChanged();
            }
        });
        binding.rlMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p = SPUtils.getInstance().getInt("menuType", 0);
                if (p != 0) {
                    SPUtils.getInstance().put("menuType", 0);
                    binding.ivMenuSelect1.setVisibility(View.VISIBLE);
                    binding.ivMenuSelect2.setVisibility(View.GONE);
                    EventBus.getDefault().post(new MyEvent(MyApplication.menuEvent, "0"));
                }
            }
        });
        binding.rlMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p = SPUtils.getInstance().getInt("menuType", 0);
                if (p != 1) {
                    SPUtils.getInstance().put("menuType", 1);
                    binding.ivMenuSelect1.setVisibility(View.GONE);
                    binding.ivMenuSelect2.setVisibility(View.VISIBLE);
                    EventBus.getDefault().post(new MyEvent(MyApplication.menuEvent, "1"));
                }
            }
        });
    }

    @Override
    protected void initData() {
        int r = SPUtils.getInstance().getInt("color1", 0);
        int g = SPUtils.getInstance().getInt("color2", 0);
        int b = SPUtils.getInstance().getInt("color3", 0);
        int a = SPUtils.getInstance().getInt("color4", 100);
        binding.seekBar1.setProgress(r);
        binding.seekBar2.setProgress(g);
        binding.seekBar3.setProgress(b);
        binding.seekBar4.setProgress(a);
    }




    @Override
    protected void initListener() {
        binding.seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ThemeUtils.themeBitmap = null;
                int endProgress = seekBar.getProgress();
                SPUtils.getInstance().put("color1", endProgress);
                int p = SPUtils.getInstance().getInt("themeType", 0);
                EventBus.getDefault().post(new MyEvent(MyApplication.themeEvent, p + ""));
            }
        });
        binding.seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ThemeUtils.themeBitmap = null;
                int endProgress = seekBar.getProgress();
                SPUtils.getInstance().put("color2", endProgress);
                int p = SPUtils.getInstance().getInt("themeType", 0);
                EventBus.getDefault().post(new MyEvent(MyApplication.themeEvent, p + ""));
            }
        });
        binding.seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ThemeUtils.themeBitmap = null;
                int endProgress = seekBar.getProgress();
                SPUtils.getInstance().put("color3", endProgress);
                int p = SPUtils.getInstance().getInt("themeType", 0);
                EventBus.getDefault().post(new MyEvent(MyApplication.themeEvent, p + ""));
            }
        });
        binding.seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int endProgress = seekBar.getProgress();
                SPUtils.getInstance().put("color4", endProgress);
                ThemeUtils.themeBitmap = null;
                int p = SPUtils.getInstance().getInt("themeType", 0);
                EventBus.getDefault().post(new MyEvent(MyApplication.themeEvent, p + ""));
            }
        });

        binding.tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.getInstance().put("color1", 0);
                SPUtils.getInstance().put("color2", 0);
                SPUtils.getInstance().put("color3", 0);
                SPUtils.getInstance().put("color4", 100);
                binding.seekBar1.setProgress(0);
                binding.seekBar2.setProgress(0);
                binding.seekBar3.setProgress(0);
                binding.seekBar4.setProgress(100);
                ThemeUtils.themeBitmap = null;
                int p = SPUtils.getInstance().getInt("themeType", 0);
                EventBus.getDefault().post(new MyEvent(MyApplication.themeEvent, p + ""));
            }
        });
    }
}
