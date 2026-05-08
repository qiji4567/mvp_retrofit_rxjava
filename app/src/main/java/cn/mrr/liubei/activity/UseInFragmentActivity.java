package cn.mrr.liubei.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Random;

import cn.mrr.liubei.R;
import cn.mrr.liubei.base.BaseActivity;
import cn.mrr.liubei.databinding.ActivityUseInFragmentBinding;
import cn.mrr.liubei.fragment.FirstFragment;
import cn.mrr.liubei.fragment.SecondFragment;
import cn.mrr.liubei.fragment.ThirdFragment;
import cn.mrr.liubei.utils.StatusBarUtil;

/**
 * @author 53443
 */
public class UseInFragmentActivity extends BaseActivity {

    private ActivityUseInFragmentBinding binding;
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_use_in_fragment;
    }

    @Override
    protected void initialize() {
        binding = ActivityUseInFragmentBinding.bind(getContentView());

        initFragments();
        initViewPager();
        initBottomNavigation();
    }

    private void initFragments() {
        mFragmentList.clear();
        mFragmentList.add(new FirstFragment());
        mFragmentList.add(new SecondFragment());
        mFragmentList.add(new ThirdFragment());
        mFragmentList.add(new ThirdFragment());
    }

    private void initViewPager() {
        binding.vpHome.setAdapter(new FragmentPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });

        binding.vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectBottomNavigation(position);

                if (position != 0 && mFragmentList.get(position) instanceof ThirdFragment) {
                    int color = 0xff000000 | new Random().nextInt(0xffffff);
                    ((ThirdFragment) mFragmentList.get(position)).setTvTitleBackgroundColor(color);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initBottomNavigation() {
        binding.bottomNavigationBar.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_one) {
                binding.vpHome.setCurrentItem(0, false);
                return true;
            } else if (itemId == R.id.nav_two) {
                binding.vpHome.setCurrentItem(1, false);
                return true;
            } else if (itemId == R.id.nav_three) {
                binding.vpHome.setCurrentItem(2, false);
                return true;
            } else if (itemId == R.id.nav_four) {
                binding.vpHome.setCurrentItem(3, false);
                return true;
            }

            return false;
        });

        binding.bottomNavigationBar.setSelectedItemId(R.id.nav_one);
    }

    private void selectBottomNavigation(int position) {
        if (position == 0) {
            binding.bottomNavigationBar.setSelectedItemId(R.id.nav_one);
        } else if (position == 1) {
            binding.bottomNavigationBar.setSelectedItemId(R.id.nav_two);
        } else if (position == 2) {
            binding.bottomNavigationBar.setSelectedItemId(R.id.nav_three);
        } else if (position == 3) {
            binding.bottomNavigationBar.setSelectedItemId(R.id.nav_four);
        }
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(UseInFragmentActivity.this, null);
    }


}