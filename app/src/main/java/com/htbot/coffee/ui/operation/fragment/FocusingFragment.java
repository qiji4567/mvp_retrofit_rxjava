package com.htbot.coffee.ui.operation.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.htbot.coffee.base.BaseFragment;
import com.htbot.coffee.databinding.FragmentFocusingBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FocusingFragment extends BaseFragment<FragmentFocusingBinding> {
    private final List<LabelVO> labelVOList = new ArrayList<>();
    private LabelArrayAdapter adapter;

    @Override
    protected void initView() {
        adapter = new LabelArrayAdapter(requireContext(), labelVOList);
        binding.safeList.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        labelVOList.clear();
        binding.safeList.setTag(null);
        binding.biSelect.setTag(null);
    }

    @Override
    protected void initData() {
        labelVOList.add(new LabelVO("请选择", "0000"));
        labelVOList.add(new LabelVO("test", "test"));
        labelVOList.add(new LabelVO("test1", "test1"));
        adapter.notifyDataSetChanged();
        binding.locShow.setText("123123213,12312312312");
    }



    @Override
    public void initListener() {
        binding.safeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.safeList.setTag(adapterView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                binding.safeList.setTag(null);
            }
        });
        binding.biSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                binding.biSelect.setTag(radioGroup.findViewById(i).getTag());
            }
        });
        binding.connectBtn.setOnClickListener(new ClickUtils.OnDebouncingClickListener() {
            @Override
            public void onDebouncingClick(View v) {
                if (binding.biSelect.getTag() == null) {
                    return;
                }
                if (binding.biSelect.getTag() instanceof String) {
                    String value = ((String) binding.biSelect.getTag());
                    ToastUtils.showLong(value);
                }
            }
        });
        binding.resetBtn.setOnClickListener(new ClickUtils.OnDebouncingClickListener() {
            @Override
            public void onDebouncingClick(View v) {
                if (binding.biSelect.getTag() == null) {
                    return;
                }
                if (binding.biSelect.getTag() instanceof String) {
                    String value = ((String) binding.biSelect.getTag());
                    ToastUtils.showLong(value);
                }

            }
        });
        binding.shangsfBtn.setOnClickListener(new ClickUtils.OnDebouncingClickListener() {
            @Override
            public void onDebouncingClick(View v) {
                if (binding.biSelect.getTag() == null) {
                    return;
                }
                if (binding.biSelect.getTag() instanceof String) {
                    String value = ((String) binding.biSelect.getTag());
                    ToastUtils.showLong(value);
                }

            }
        });
        binding.xiasfBtn.setOnClickListener(new ClickUtils.OnDebouncingClickListener() {
            @Override
            public void onDebouncingClick(View v) {
                if (binding.biSelect.getTag() == null) {
                    return;
                }
                if (binding.biSelect.getTag() instanceof String) {
                    String value = ((String) binding.biSelect.getTag());
                    ToastUtils.showLong(value);
                }

            }
        });
        binding.safeBtn.setOnClickListener(new ClickUtils.OnDebouncingClickListener() {
            @Override
            public void onDebouncingClick(View v) {
                if (binding.safeList.getTag() != null) {
                    LabelVO vo = (LabelVO) binding.safeList.getTag();
                    ToastUtils.showLong(vo.label);
                }
            }
        });
        binding.locSetBtn.setOnClickListener(new ClickUtils.OnDebouncingClickListener() {
            @Override
            public void onDebouncingClick(View v) {
                if (binding.locShow.getText() != null) {
                    ToastUtils.showLong(binding.locShow.getText());
                }
            }
        });

    }

    class LabelArrayAdapter extends ArrayAdapter<LabelVO> {

        public LabelArrayAdapter(@NonNull Context context, @NonNull List<LabelVO> objects) {
            super(context, android.R.layout.simple_spinner_item, objects);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = super.getView(position, convertView, parent);
            }
            if (convertView instanceof TextView) {
                LabelVO labelVO = getItem(position);
                if (labelVO != null) {
                    ((TextView) convertView).setText(labelVO.label);
                }
            }
            return convertView;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = super.getDropDownView(position, convertView, parent);
            }
            if (convertView instanceof CheckedTextView) {
                LabelVO labelVO = getItem(position);
                ((CheckedTextView) convertView).setText(labelVO.label);
                Object selectObj = FocusingFragment.this.binding.safeList.getTag();
                if (selectObj != null) {
                    ((CheckedTextView) convertView).setChecked(Objects.equals(labelVO.value, ((LabelVO) selectObj).value));
                } else {
                    ((CheckedTextView) convertView).setChecked(false);
                }
            }
            return convertView;
        }
    }

    static class LabelVO {
        private String label;
        private String value;

        public LabelVO() {
        }

        public LabelVO(String label, String value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
