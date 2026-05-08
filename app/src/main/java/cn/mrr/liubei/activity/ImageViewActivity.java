package cn.mrr.liubei.activity;

import android.view.MenuItem;
import android.widget.SeekBar;

import cn.mrr.liubei.R;
import cn.mrr.liubei.base.BaseActivity;
import cn.mrr.liubei.databinding.ActivityImageViewBinding;
import cn.mrr.liubei.utils.StatusBarUtil;

public class ImageViewActivity extends BaseActivity {
    private ActivityImageViewBinding binding;
    private int mAlpha;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_view;
    }

    @Override
    protected void initialize() {
        binding = ActivityImageViewBinding.bind(getContentView());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.sbChangeAlpha.setMax(255);
        binding.sbChangeAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAlpha = progress;
                StatusBarUtil.setTranslucentForImageView(ImageViewActivity.this, mAlpha, binding.viewNeedOffset);
                binding.tvStatusAlpha.setText(String.valueOf(mAlpha));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        binding.sbChangeAlpha.setProgress(StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }


}
