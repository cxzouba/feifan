package com.feifandaiyu.feifan.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.cheshang.CompanyCheshangPicActivity;
import com.feifandaiyu.feifan.activity.cheshang.ShowCompanyCheshang;
import com.feifandaiyu.feifan.adapter.CheshangAdapter;
import com.feifandaiyu.feifan.bean.CheshangListBean;
import com.feifandaiyu.feifan.bean.ZuoFeiBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.Resource2Bitmap;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigInput;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;
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

public class CheshangCompanyFragment extends Fragment {

    @InjectView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @InjectView(R.id.ll_cheshang)
    LinearLayout llCheshang;
    //    @InjectView(R.id.refresh_dpg)
//    SwipeRefreshLayout refreshDpg;
    private List<CheshangListBean.ListBean> list;
    @InjectView(R.id.lv_dpg)
    ListView lvDpg;
    private CheshangAdapter adapter;
    private KProgressHUD hud;
    private String usreId;
    private Bitmap bitmap;
    private BitmapDrawable bmdrawable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dpg, container, false);

        ButterKnife.inject(this, view);

        bitmap = Resource2Bitmap.readBitMap(getActivity(), R.drawable.jiazai);
        bmdrawable = new BitmapDrawable(bitmap);
        llCheshang.setBackground(bmdrawable);

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

        usreId = PreferenceUtils.getString(getActivity(), "saleID");

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Dealer/dealerList")
                .addParams("saleId", usreId)
                .addParams("type", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("cheshangList____________________>>>>>" + e);
//                        refreshDpg.setRefreshing(false);
                        refreshLayout.finishRefresh();
                        hud.dismiss();
                        MyToast.show(getActivity(), "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("cheshangList----------------->>>>>>" + response);
                        String json = response;
                        Gson gson = new Gson();
                        hud.dismiss();
                        try {
                            CheshangListBean bean = gson.fromJson(json, CheshangListBean.class);
                            list = bean.getList();
//                        LogUtils.i(list1.size() + "---------------->>>>>>>>");

                            if (list == null || list.size() == 0) {
                                llCheshang.setBackground(bmdrawable);
                            } else {
                                llCheshang.setBackground(null);
                            }

                            adapter = new CheshangAdapter(getActivity(), list, btnlistener1, btnlistener2);
                            lvDpg.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            lvDpg.setSelection(firstVisiblePosition);
//                        refreshDpg.setRefreshing(false);
                            refreshLayout.finishRefresh();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            MyToast.show(getActivity(), response);
                        }
                    }
                });

        lvDpg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PreferenceUtils.setString(getActivity(), "cheshangId", list.get(position).getId());

                startActivity(new Intent(getActivity(), ShowCompanyCheshang.class));
            }
        });

    }


    private int firstVisiblePosition;

    CheshangAdapter.OnButtonClickLisetrner1 btnlistener1 = new CheshangAdapter.OnButtonClickLisetrner1() {

        @Override
        public void onButtonClick(final int positon, final View view) {
            System.out.println("------------->>>>>" + positon);
            firstVisiblePosition = lvDpg.getFirstVisiblePosition();
//            startActivity(new Intent(getActivity(), PingGuShiPingGuActivity.class));
            final CircleDialog.Builder builder = new CircleDialog.Builder(getActivity());
            builder.configInput(new ConfigInput() {
                @Override
                public void onConfig(InputParams params) {
                    params.inputHeight = 150;
                    params.textSize = 40;
                }
            })
                    .setInputHint("请输入作废原因")
                    .setTitle("是否作废？")
                    .setPositiveInput("确定", new OnInputClickListener() {
                        @Override
                        public void onClick(String text, View v) {

                            if (text == null || text.equals("")) {
                                MyToast.show(getActivity(), "请填写作废原因！");
                                return;
                            } else {
                                hud.show();

                                OkHttpUtils.post()
                                        .url(Constants.URLS.BASEURL + "Dealer/toVoid")
                                        .addParams("remark", text)
                                        .addParams("operatorId", usreId)
                                        .addParams("Id", list.get(positon).getId())
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onError(Call call, Exception e, int id) {
                                                System.out.println("zuofei____________________>>>>>" + e);
                                                hud.dismiss();
                                                MyToast.show(getActivity(), "加载失败");
                                            }

                                            @Override
                                            public void onResponse(String response, int id) {
                                                LogUtils.e("zuofei----------------->>>>>>" + response);
                                                String json = response;
                                                Gson gson = new Gson();
                                                ZuoFeiBean bean = null;

                                                try {
                                                    bean = gson.fromJson(json, ZuoFeiBean.class);
                                                } catch (JsonSyntaxException e) {
                                                    MyToast.show(getActivity(), "服务器错误");
                                                }

                                                if (bean.getCode() == 1) {

                                                    deletePattern(view, positon);
                                                /*initData();*/

                                                } else if (bean.getCode() == 0) {
                                                    MyToast.show(getActivity(), bean.getMsg());
                                                }

                                                hud.dismiss();
                                            }
                                        });

                            }
                        }


                    })
                    .setNegative("取消", null)
                    .show();
        }
    };


    CheshangAdapter.OnButtonClickLisetrner2 btnlistener2 = new CheshangAdapter.OnButtonClickLisetrner2() {

        @Override
        public void onButtonClick(int positon) {
            firstVisiblePosition = lvDpg.getFirstVisiblePosition();
            PreferenceUtils.setString(getActivity(), "cheshangId", list.get(positon).getId());
            startActivity(new Intent(getActivity(), CompanyCheshangPicActivity.class));
            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
            getActivity().overridePendingTransition(enterAnim6, exitAnim6);
        }
    };

    private void deletePattern(final View view, final int position) {

        Animation.AnimationListener al = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                list.remove(position);
                CheshangAdapter.ViewHolder holder = (CheshangAdapter.ViewHolder) view.getTag();
                holder.needInflate = true;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        collapse(view, al);

    }

    private void collapse(final View view, Animation.AnimationListener al) {
        final int originHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1.0f) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = originHeight - (int) (originHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        if (al != null) {
            animation.setAnimationListener(al);
        }
        animation.setDuration(300);
        view.startAnimation(animation);
    }

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
