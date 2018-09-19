package com.feifandaiyu.feifan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.evalutor.PingGuShiPingGuActivity;
import com.feifandaiyu.feifan.adapter.DPGAdapter;
import com.feifandaiyu.feifan.bean.DPGBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.google.gson.Gson;
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

public class DaiPingGuFragment extends Fragment {

    @InjectView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    //    @InjectView(R.id.refresh_dpg)
//    SwipeRefreshLayout refreshDpg;
    private List<DPGBean.ListBean> list;
    @InjectView(R.id.lv_dpg)
    ListView lvDpg;
    private DPGAdapter adapter;
    private KProgressHUD hud;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dpg, container, false);

        ButterKnife.inject(this, view);

        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在加载")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

//        initData();

//        refreshDpg.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                initData();
//            }
//        });
//        refreshDpg.setColorSchemeColors(getResources().getColor(R.color.activecolor));

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
                        System.out.println("daipinggu____________________>>>>>" + e);
//                        refreshDpg.setRefreshing(false);
                        refreshLayout.finishRefresh();
                        hud.dismiss();
                        MyToast.show(getActivity(), "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("daipinggu----------------->>>>>>" + response);
                        String json = response;
                        Gson gson = new Gson();
                        hud.dismiss();
                        DPGBean bean = gson.fromJson(json, DPGBean.class);
                        list = bean.getList();
//                        LogUtils.i(list1.size() + "---------------->>>>>>>>");

                        adapter = new DPGAdapter(getActivity(), list, btnlistener);
                        lvDpg.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        lvDpg.setSelection(firstVisiblePosition);
//                        refreshDpg.setRefreshing(false);
                        refreshLayout.finishRefresh();
                    }
                });

    }

    private int firstVisiblePosition;
    DPGAdapter.OnButtonClickLisetrner btnlistener = new DPGAdapter.OnButtonClickLisetrner() {

        @Override
        public void onButtonClick(int positon) {
            System.out.println("------------->>>>>" + positon);
            firstVisiblePosition = lvDpg.getFirstVisiblePosition();
            startActivity(new Intent(getActivity(), PingGuShiPingGuActivity.class));
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
