package com.htbot.coffee.ui.operation.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.CheckBox;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.htbot.coffee.MyApplication;
import com.htbot.coffee.ui.operation.adapter.MakingOrderAdapter;
import com.htbot.coffee.api.OperationApi;
import com.htbot.coffee.base.BaseFragment;
import com.htbot.coffee.databinding.FragmentMakingOrderBinding;
import com.htbot.coffee.entity.MakingOrderBean;
import com.htbot.coffee.util.AESUtils;
import com.htbot.coffee.util.LanguageUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * 制作订单
 */
public class MakingOrderFragment extends BaseFragment<FragmentMakingOrderBinding> {

    private MakingOrderAdapter makingOrderAdapter;
    private List<MakingOrderBean> makingOrderBeanList = new ArrayList<>();

    @Override
    protected void initView() {


        makingOrderAdapter = new MakingOrderAdapter(getActivity(), makingOrderBeanList);
        binding.rvMakingOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvMakingOrder.setAdapter(makingOrderAdapter);
        makingOrderAdapter.setOnItemCheckListener(new MakingOrderAdapter.ItemCheckOnClick() {
            @Override
            public void onClick(int p, MakingOrderBean bean, CheckBox checkBox) {
                makingOrderBeanList.get(p).setCheck(checkBox.isChecked());

                binding.checkBoxAll.setChecked(countChecked() == makingOrderBeanList.size());
            }
        });

    }

    //计算选择的商品
    private int countChecked() {
        int count = 0;
        for (MakingOrderBean item : makingOrderBeanList) {
            if (item.isCheck()) {
                count++;
            }
        }
        return count;
    }

    @Override
    protected void initData() {
        getMakingOrder();
    }

    /**
     * 重做订单
     */
    @SuppressLint("CheckResult")
    private void remakeOrder(List<MakingOrderBean> makingOrderBeanList) {
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), AESUtils.createRequestOperateBodyList(makingOrderBeanList)
        );
        OperationApi.remakeMakingOrder(body)
                .doOnError(error -> {

                })
                .subscribe(o -> {
                    if (o.getSuccess()) {
                        getMakingOrder();
                    } else {
                        ToastUtils.showLong(o.getMessage());
                    }

                }, throwable -> {
                    ToastUtils.showLong(throwable.getMessage());
                });
    }


    /**
     * 忽略订单
     *
     * @param makingOrderBeanList
     */
    @SuppressLint("CheckResult")
    private void ignoreMakingOrder(List<MakingOrderBean> makingOrderBeanList) {


        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), AESUtils.createRequestOperateBodyList(makingOrderBeanList)
        );
        OperationApi.ignoreMakingOrder(body)
                .doOnError(error -> {

                })
                .subscribe(o -> {
                    if (o.getSuccess()) {
                        getMakingOrder();
                    } else {
                        ToastUtils.showLong(o.getMessage());
                    }


                }, throwable -> {
                    ToastUtils.showLong(throwable.getMessage());
                });
    }

    //获取订单列表
    @SuppressLint("CheckResult")
    private void getMakingOrder() {
        HashMap<String, Object> postMap = new HashMap<>();
        postMap.put("corpId", MyApplication.instance.getCorpId() + "");
        postMap.put("snId", MyApplication.instance.getSerialNumber());  // 将 data 传递进去
        postMap.put("language", LanguageUtil.getSavedLanguage(mActivity));
        postMap.put("limit", 10000);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), AESUtils.createRequestOperateBody(postMap)
        );
        OperationApi.getMakingOrder(body)
                .doOnError(error -> {

                })
                .subscribe(o -> {
                    if (o.getSuccess()) {
                        makingOrderBeanList.clear();
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<MakingOrderBean>>() {
                        }.getType();
                        List<MakingOrderBean> list = gson.fromJson(o.getData(), listType);
                        if (!list.isEmpty()) {
                            makingOrderBeanList.addAll(list);
                            makingOrderBeanList.forEach(item -> {
                                item.setCheck(false);
                            });
                        }
                        makingOrderAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showLong(o.getMessage());
                    }
                }, throwable -> {
                    ToastUtils.showLong(throwable.getMessage());
                });
    }




    @Override
    public void initListener() {

        binding.checkBoxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!makingOrderBeanList.isEmpty()) {
                    for (MakingOrderBean item : makingOrderBeanList) {
                        item.setCheck(binding.checkBoxAll.isChecked());
                    }
                }
                makingOrderAdapter.notifyDataSetChanged();
//                makingOrderBeanList.forEach(item->{
//                    item.setCheck(b);
//                    makingOrderAdapter.notifyDataSetChanged();
//                });
            }
        });
        binding.tvRemake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<MakingOrderBean> filteredList = makingOrderBeanList.stream()
                        .filter(MakingOrderBean::isCheck)  // 只选择isCheck为true的订单
                        .collect(Collectors.toList());  // 返回过滤后的列表
                if (!filteredList.isEmpty()) {
                    remakeOrder(filteredList);

                }
            }
        });
        binding.tvIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<MakingOrderBean> filteredList = makingOrderBeanList.stream()
                        .filter(MakingOrderBean::isCheck)  // 只选择isCheck为true的订单
                        .collect(Collectors.toList());  // 返回过滤后的列表
                if (!filteredList.isEmpty()) {
                    ignoreMakingOrder(filteredList);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.checkBoxAll.setChecked(false);
    }
}
