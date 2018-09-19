package com.feifandaiyu.feifan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.NoticeAdapter;
import com.feifandaiyu.feifan.bean.NoticeBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.refactor.lib.colordialog.PromptDialog;
import okhttp3.Call;

public class NoticeSettingFragment extends Fragment {

    @InjectView(R.id.lv_setting)
    ListView lvSetting;
    @InjectView(R.id.iv_bg)
    ImageView ivBg;
    private String saleID;
    private List<NoticeBean.ListBean> list;
    private NoticeAdapter adapter;
    private KProgressHUD hud;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.activity_tab_chat, container, false);

        ButterKnife.inject(this, view);

        saleID = PreferenceUtils.getString(getActivity(), "saleID");

        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        initData();

        lvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                OkHttpUtils.post()
                        .url(Constants.URLS.BASEURL + "Appraiser/showMessage")
                        .addParams("id", list.get(position).getId())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                System.out.println("msg____________________>>>>>" + e);
                                MyToast.show(getActivity(), "加载失败");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.e("msg----------------->>>>>>" + response);
                                String json = response;
                                Gson gson = new Gson();
                                DetailMsg bean = null;
                                try {

                                    bean = gson.fromJson(json, DetailMsg.class);
                                    new PromptDialog(getActivity())
                                            .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                                            .setAnimationEnable(true)
                                            .setTitleText("安全提示")
                                            .setContentText(bean.getList().getContent())
                                            .setPositiveListener("知道啦", new PromptDialog.OnPositiveListener() {
                                                @Override
                                                public void onClick(PromptDialog dialog) {
                                                    dialog.dismiss();
                                                }
                                            }).show();

                                } catch (JsonSyntaxException e) {

                                    MyToast.show(getActivity(), "联网失败");

                                }
                            }
                        });


            }
        });

        return view;

    }

    private void initData() {

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Appraiser/msglist")
                .addParams("id", saleID)
                .addParams("status", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("SettingF____________________>>>>>" + e);
//                        refreshCar.setRefreshing(false);
                        hud.dismiss();
//                        refreshLayout.finishRefresh();
                        MyToast.show(getActivity(), "加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("SettingF----------------->>>>>>" + response);
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        NoticeBean bean = null;
                        try {

                            bean = gson.fromJson(json, NoticeBean.class);

                        } catch (JsonSyntaxException e) {
                            ivBg.setVisibility(View.VISIBLE);
                            MyToast.show(getActivity(), "联网失败");

                        }

                        list = bean.getList();
//                        LogUtils.i(list1.size() + "---------------->>>>>>>>");
                        if (list.size() == 0) {
//                            llBgset.setBackground(bmdrawable);
                            ivBg.setVisibility(View.VISIBLE);
                        } else {
//                            llBgset.setBackground(null);
                            ivBg.setVisibility(View.GONE);
                        }

                        adapter = new NoticeAdapter(getActivity(), list, 0);
                        lvSetting.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

//                        lvCarLoan.setSelection(firstVisiblePosition);

//                        refreshCar.setRefreshing(false);
//                        refreshLayout.finishRefresh();

                    }
                });

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
