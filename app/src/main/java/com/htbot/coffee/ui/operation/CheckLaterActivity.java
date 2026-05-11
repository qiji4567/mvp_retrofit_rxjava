package com.htbot.coffee.ui.operation;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import com.htbot.coffee.R;
import com.htbot.coffee.application.MyApplication;
import com.htbot.coffee.ui.operation.adapter.CategorySpinnerAdapter;
import com.htbot.coffee.ui.operation.adapter.CheckHistoryAdapter;
import com.htbot.coffee.api.OperationApi;
import com.htbot.coffee.base.BaseActivity;
import com.htbot.coffee.databinding.ActivityCheckBinding;
import com.htbot.coffee.entity.CheckRecordBean;
import com.htbot.coffee.entity.CleanTypeListBean;
import com.htbot.coffee.entity.UserInfoBean;
import com.htbot.coffee.utils.AESUtils;
import com.htbot.coffee.utils.DateUtil;
import com.htbot.coffee.utils.LanguageUtil;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CheckLaterActivity extends BaseActivity {

    /**
     * ViewBinding
     */
    private ActivityCheckBinding binding;

    /**
     * 清洗类型 ID 集合
     */
    private final List<String> categoryIdList = new ArrayList<>();

    /**
     * 清洗类型名称集合
     */
    private final List<String> categoryNameList = new ArrayList<>();

    /**
     * 当前选中的清洗类型
     */
    private String cleanType;

    /**
     * 当前页码
     */
    private int pageIndex = 1;

    /**
     * 每页数量
     */
    private static final int PAGE_SIZE = 20;

    /**
     * 默认查询最近几天
     * 3 = 今天、昨天、前天
     */
    private static final int DEFAULT_SEARCH_DAY = 3;

    /**
     * 时间格式
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 历史记录适配器
     */
    private CheckHistoryAdapter historyAdapter;

    /**
     * 历史记录数据
     */
    private final List<CheckRecordBean.DataBean> historyList = new ArrayList<>();

    /**
     * 用户信息
     */
    private UserInfoBean userInfoBean;

    /**
     * 查询开始时间
     */
    private String startDate;

    /**
     * 查询结束时间
     */
    private String endDate;

    /**
     * 日期格式化工具
     */
    private final SimpleDateFormat sdf =
            new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    @Override
    public View getLayout() {
        binding = ActivityCheckBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init();
        getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        cleanType = getIntent().getStringExtra("cleanType");

        initDefaultTime();
        initRecyclerView();
        refreshPageData();
    }

    @Override
    public void getData() {
        getUserInfo();
    }

    @Override
    public void initListener() {
        binding.rlBack.setOnClickListener(v -> notifyClose());

        binding.btnStartTime.setOnClickListener(v -> showTimePicker(true));
        binding.btnEndTime.setOnClickListener(v -> showTimePicker(false));
    }

    /**
     * 初始化默认查询时间
     * 默认最近 3 天：
     * 开始时间 = 前天 00:00:00
     * 结束时间 = 今天 23:59:59
     */
    private void initDefaultTime() {
        setDefaultDateRange(DEFAULT_SEARCH_DAY);
    }

    /**
     * 设置默认查询时间范围
     *
     * @param day 最近几天
     *            1 = 今天
     *            3 = 最近 3 天，包含今天
     *            7 = 最近 7 天，包含今天
     */
    private void setDefaultDateRange(int day) {
        startDate = getBeforeDayStartTime(day);
        endDate = DateUtil.getTodayEndTime();

        binding.btnStartTime.setText(startDate);
        binding.btnEndTime.setText(endDate);
    }

    /**
     * 获取最近 day 天的开始时间
     *
     * @param day 最近几天，包含今天
     * @return yyyy-MM-dd HH:mm:ss
     */
    private String getBeforeDayStartTime(int day) {
        Calendar calendar = Calendar.getInstance();

        int offsetDay = Math.max(day, 1) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -offsetDay);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return sdf.format(calendar.getTime());
    }

    /**
     * 显示日期选择器
     *
     * @param isStartTime true = 选择开始时间，false = 选择结束时间
     */
    private void showTimePicker(boolean isStartTime) {
        Calendar selectedDate = Calendar.getInstance();

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(2020, 0, 1);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(2035, 11, 31);

        TimePickerView pickerView = new TimePickerBuilder(this, (date, v) -> {
            String time = formatPickerDate(date, isStartTime);

            if (isStartTime) {
                if (!TextUtils.isEmpty(endDate) && dateAfter(time, endDate)) {
                    ToastUtils.showShort(getString(R.string.check_start_time_greater_error));
                    return;
                }

                startDate = time;
                binding.btnStartTime.setText(time);
            } else {
                if (!TextUtils.isEmpty(startDate) && dateBefore(time, startDate)) {
                    ToastUtils.showShort(getString(R.string.check_end_time_less_error));
                    return;
                }

                endDate = time;
                binding.btnEndTime.setText(time);
            }

            refreshListByCondition();

        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setTitleText(getString(isStartTime ? R.string.check_select_start_time : R.string.check_select_end_time))
                .setDate(selectedDate)
                .setRangDate(startCalendar, endCalendar)
                .setSubmitText(getString(R.string.confirm))
                .setCancelText(getString(R.string.cancel))
                .setOutSideCancelable(true)
                .isCyclic(false)
                .build();

        pickerView.show();
    }

    /**
     * 格式化选择器返回的日期
     * 开始时间补 00:00:00
     * 结束时间补 23:59:59
     */
    private String formatPickerDate(Date date, boolean isStartTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (isStartTime) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
        }

        calendar.set(Calendar.MILLISECOND, 0);
        return sdf.format(calendar.getTime());
    }

    /**
     * 判断 time1 是否晚于 time2
     */
    private boolean dateAfter(String time1, String time2) {
        Date date1 = parseDate(time1);
        Date date2 = parseDate(time2);
        return date1 != null && date2 != null && date1.after(date2);
    }

    /**
     * 判断 time1 是否早于 time2
     */
    private boolean dateBefore(String time1, String time2) {
        Date date1 = parseDate(time1);
        Date date2 = parseDate(time2);
        return date1 != null && date2 != null && date1.before(date2);
    }

    /**
     * 字符串时间转 Date
     */
    private Date parseDate(String time) {
        try {
            return sdf.parse(time);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据筛选条件刷新列表
     */
    private void refreshListByCondition() {
        pageIndex = 1;
        historyList.clear();
        historyAdapter.refresh(historyList);
        deviceCleaningPage();
    }

    /**
     * 更新分类下拉框
     */
    private void updateCategorySpinner() {
        if (categoryNameList.isEmpty() || categoryIdList.isEmpty()) {
            return;
        }

        CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(this, categoryNameList);
        binding.spCategory.setAdapter(adapter);

        binding.spCategory.setPopupBackgroundDrawable(
                new android.graphics.drawable.ColorDrawable(android.graphics.Color.WHITE)
        );

        cleanType = categoryIdList.get(0);
        adapter.setSelectedPosition(0);
        binding.spCategory.setSelection(0, false);

        binding.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cleanType = categoryIdList.get(position);
                adapter.setSelectedPosition(position);
                refreshListByCondition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * 获取清洗类型列表
     */
    @SuppressLint("CheckResult")
    private void cleanTypeList() {
        HashMap<String, Object> postMap = new HashMap<>();
        postMap.put("deviceId", MyApplication.instance.getEquipmentId() + "");

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                AESUtils.createRequestOperateBody(postMap)
        );

        OperationApi.cleanTypeList(body)
                .subscribe(o -> {
                    if (o.getSuccess()) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<CleanTypeListBean>>() {
                        }.getType();

                        if (!TextUtils.isEmpty(o.getData())) {
                            List<CleanTypeListBean> dataList = gson.fromJson(o.getData(), listType);

                            if (dataList != null && !dataList.isEmpty()) {
                                categoryIdList.clear();
                                categoryNameList.clear();

                                for (CleanTypeListBean bean : dataList) {
                                    categoryIdList.add(bean.getCleanType());
                                    categoryNameList.add(bean.getCleanTypeDesc());
                                }

                                updateCategorySpinner();
                            }
                        }
                    }
                }, throwable -> ToastUtils.showLong(throwable.getMessage()));
    }

    /**
     * 请求清洗历史列表
     */
    @SuppressLint("CheckResult")
    private void deviceCleaningPage() {
        if (TextUtils.isEmpty(cleanType)) {
            return;
        }

        HashMap<String, Object> postMap = new HashMap<>();
        postMap.put("deviceId", MyApplication.instance.getEquipmentId());
        postMap.put("cleanType", cleanType);
        postMap.put("startDate", startDate);
        postMap.put("endDate", endDate);
        postMap.put("pageIndex", pageIndex);
        postMap.put("pageSize", PAGE_SIZE);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                AESUtils.createRequestOperateBody(postMap)
        );

        OperationApi.deviceCleaningPage(body)
                .subscribe(o -> {
                    if (o.getSuccess()) {
                        Gson gson = new Gson();
                        CheckRecordBean checkRecordBean =
                                gson.fromJson(o.getData(), CheckRecordBean.class);

                        historyList.clear();

                        if (checkRecordBean != null && checkRecordBean.getData() != null) {
                            historyList.addAll(checkRecordBean.getData());
                        }

                        refreshHistoryList();
                    }
                }, throwable -> ToastUtils.showLong(throwable.getMessage()));
    }

    /**
     * 获取用户信息
     */
    @SuppressLint("CheckResult")
    private void getUserInfo() {
        OperationApi.getUserDetail(LanguageUtil.getSavedLanguage(mContext))
                .subscribe(o -> {
                    if (o.getSuccess()) {
                        userInfoBean = new Gson().fromJson(o.getData(), UserInfoBean.class);

                        if (userInfoBean != null) {
                            cleanTypeList();
                        }
                    }
                }, throwable -> ToastUtils.showLong(throwable.getMessage()));
    }

    /**
     * 初始化历史记录列表
     */
    private void initRecyclerView() {
        historyAdapter = new CheckHistoryAdapter(mContext, historyList);
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvHistory.setAdapter(historyAdapter);
    }

    /**
     * 刷新页面数据
     */
    private void refreshPageData() {
        refreshHistoryList();
    }

    /**
     * 刷新历史列表 UI
     */
    private void refreshHistoryList() {
        historyAdapter.refresh(historyList);
        binding.tvHistoryTitle.setText(getString(R.string.check_history_title));
        binding.rvHistory.setVisibility(historyList.isEmpty() ? View.GONE : View.VISIBLE);
        binding.tvHistoryEmpty.setVisibility(historyList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    /**
     * 关闭页面
     */
    private void notifyClose() {
        finish();
    }
}