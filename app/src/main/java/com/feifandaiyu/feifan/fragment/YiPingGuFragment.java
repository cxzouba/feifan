package com.feifandaiyu.feifan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.evalutor.EvaluaterDrawNameActivity;
import com.feifandaiyu.feifan.activity.evalutor.PingGuShiWriteActivity;
import com.feifandaiyu.feifan.adapter.YPGAdapter;
import com.feifandaiyu.feifan.bean.DPGBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by houdaichang on 2017/5/15.
 */

public class YiPingGuFragment extends Fragment {

    @InjectView(R.id.lv_ypg)
    ListView lvYpg;
    @InjectView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    //    @InjectView(R.id.refresh_ypg)
//    SwipeRefreshLayout refreshYpg;
    private List<DPGBean.ListEndBean> list;
    private YPGAdapter adapter;
    private ImageView imageView;
    private KProgressHUD hud;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ypg, container, false);

        ButterKnife.inject(this, view);

        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在加载")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

//        initData();

//        refreshYpg.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                initData();
//            }
//        });
//        refreshYpg.setColorSchemeColors(getResources().getColor(R.color.activecolor));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                firstVisiblePosition = 0;
                initData();
            }
        });

        return view;

    }

    private void initData() {


        String usreId = PreferenceUtils.getString(getActivity(), "saleID");

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Login/appraiserList")
                .addParams("userId", usreId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("yipinggu____________________>>>>>" + e);
                        refreshLayout.finishRefresh();
                        hud.dismiss();
                        MyToast.show(getActivity(), "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("yipinggu----------------->>>>>>" + response);
                        String json = response;
                        hud.dismiss();
                        Gson gson = new GsonBuilder()
                                .setPrettyPrinting()
                                .create();
                        DPGBean bean = gson.fromJson(json, DPGBean.class);
                        LogUtils.e("yipinggu----------------->>>>>>" + gson.toJson(bean));
                        list = bean.getList_end();
                        adapter = new YPGAdapter(getActivity(), list, btnlistener);
                        lvYpg.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        lvYpg.setSelection(firstVisiblePosition);
//                        refreshYpg.setRefreshing(false);
                        refreshLayout.finishRefresh();
                    }
                });

    }

    //    private int isOver;
    private int firstVisiblePosition;
    YPGAdapter.OnButtonClickLisetrner btnlistener = new YPGAdapter.OnButtonClickLisetrner() {

        @Override
        public void onButtonClick(int positon) {

//            isOver = list.get(positon).getIsover();

//            System.out.println("isOver" + isOver);

            firstVisiblePosition = lvYpg.getFirstVisiblePosition();

//            System.out.println("report" + list.get(positon).getReport());

            if (list.get(positon).getUserId().equals("-999")) {

                MyToast.show(getActivity(), "请等待业务员完善客户信息");

            } else {
                if (list.get(positon).getApic().equals("未签名")) {

                    if (list.get(positon).getPid().equals("无报告")) {


                        startActivity(new Intent(getActivity(), PingGuShiWriteActivity.class));

                    } else {

                        PreferenceUtils.setString(getActivity(), "ReportId", list.get(positon).getPid());
                        PreferenceUtils.setString(getActivity(), "YPGCarId", list.get(positon).getId());

                        startActivity(new Intent(getActivity(), EvaluaterDrawNameActivity.class));

                    }

                } else {

                    MyToast.show(getActivity(), "评估报告已完成");

                }
            }

            // 设置过渡动画
            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
            getActivity().overridePendingTransition(enterAnim6, exitAnim6);

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
