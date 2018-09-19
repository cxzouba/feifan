package com.feifandaiyu.feifan.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.carloan.CarEvaluateActivity2;
import com.feifandaiyu.feifan.activity.personalloan.BankCardMessageActivity;
import com.feifandaiyu.feifan.activity.personalloan.BaseInformationActivity;
import com.feifandaiyu.feifan.activity.personalloan.CautionerMessageActivity;
import com.feifandaiyu.feifan.activity.personalloan.ContactsActivity;
import com.feifandaiyu.feifan.activity.personalloan.ContractPicActivity;
import com.feifandaiyu.feifan.activity.personalloan.Cost1Activity;
import com.feifandaiyu.feifan.activity.personalloan.Cost3Activity;
import com.feifandaiyu.feifan.activity.personalloan.CustomeReport;
import com.feifandaiyu.feifan.activity.personalloan.CustomerDrawNameActivity;
import com.feifandaiyu.feifan.activity.personalloan.DiyaPicActivity;
import com.feifandaiyu.feifan.activity.personalloan.FacePicActivity;
import com.feifandaiyu.feifan.activity.personalloan.FullInfoActivity1;
import com.feifandaiyu.feifan.activity.personalloan.GuoCheng1Activity;
import com.feifandaiyu.feifan.activity.personalloan.IndoorPicActivity;
import com.feifandaiyu.feifan.activity.personalloan.OutdoorPicActivity;
import com.feifandaiyu.feifan.activity.personalloan.StartHomeVisitActivity;
import com.feifandaiyu.feifan.activity.personalloan.StopHomeVisitActivity;
import com.feifandaiyu.feifan.activity.personalloan.TeamManager1Activity;
import com.feifandaiyu.feifan.activity.personalloan.UpLoadCarImageActivity;
import com.feifandaiyu.feifan.activity.personalloan.UpLoadCusomerVideoActivity;
import com.feifandaiyu.feifan.activity.personalloan.WifeActivity;
import com.feifandaiyu.feifan.activity.personalloan.ZhengxinActivity;
import com.feifandaiyu.feifan.adapter.CarLoanAdapter;
import com.feifandaiyu.feifan.bean.CarLoanBean;
import com.feifandaiyu.feifan.bean.DiyaBohuiBean;
import com.feifandaiyu.feifan.bean.Fangkuan;
import com.feifandaiyu.feifan.bean.Fanyong;
import com.feifandaiyu.feifan.bean.ProgressBean;
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
 * Created by houdaichang on 2017/5/2.
 */

public class CarLoanFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    @InjectView(R.id.lv_carLoan)
    ListView lvCarLoan;
    //    @InjectView(R.id.refresh_car)
//    SwipeRefreshLayout refreshCar;
    @InjectView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private ImageView ivSetting;
    private TextView tvPg;
    private LinearLayout llBgset;
    private Bitmap bitmap;
    private BitmapDrawable bmdrawable;
    private List<CarLoanBean.ListBean> list;
    private CarLoanAdapter adapter;
    private CarLoanAdapter.OnButtonClickLisetrner btnlistener;
    private String saleID;
    private ImageView ivCamera;
    private int firstVisiblePosition;
    private KProgressHUD hud;
    private ImageView ivjisuanqi;
    private ImageView ivPoint;
    private String carId;
    private CarLoanBean bean;

    private Fangkuan fangkuanBean;
    private Fanyong fanYongBean;
    private MyFangkuanDialog myDialog;
    private MyFanyongDialog myFanyongDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_loan, container, false);
        llBgset = (LinearLayout) view.findViewById(R.id.ll_carloan);
        bitmap = Resource2Bitmap.readBitMap(getActivity(), R.drawable.jiazai);
        bmdrawable = new BitmapDrawable(bitmap);
        llBgset.setBackground(bmdrawable);
        ButterKnife.inject(this, view);

        init();
        lvCarLoan.setOnItemClickListener(this);
        return view;
    }

    private void init() {
        saleID = PreferenceUtils.getString(getActivity(), "saleID");

        ivSetting = (ImageView) getActivity().findViewById(R.id.iv_setting);

        ivCamera = (ImageView) getActivity().findViewById(R.id.iv_camera);
        ivjisuanqi = (ImageView) getActivity().findViewById(R.id.iv_jisuanqi);
        TextView tvCity = (TextView) getActivity().findViewById(R.id.tv_city);
        ivPoint = (ImageView) getActivity().findViewById(R.id.iv_point);
        ivPoint.setVisibility(View.GONE);
        ivSetting.setVisibility(View.GONE);
        ivCamera.setVisibility(View.GONE);
        ivjisuanqi.setVisibility(View.GONE);
        tvCity.setVisibility(View.GONE);

        tvPg = (TextView) getActivity().findViewById(R.id.tv_next);

        tvPg.setText("车辆评估");

        tvPg.setEnabled(true);

        tvPg.setVisibility(View.VISIBLE);

        tvPg.setOnClickListener(this);

        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                firstVisiblePosition = 0;
                initData();
            }
        });

        myDialog = new MyFangkuanDialog(getActivity());
        myFanyongDialog = new MyFanyongDialog(getActivity());

    }

    private void initData() {

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Login/carLoan")
                .addParams("saleId", saleID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("carLoanActivity____________________>>>>>" + e);
//                        refreshCar.
// (false);
                        hud.dismiss();
                        refreshLayout.finishRefresh();
                        MyToast.show(getActivity(), "加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("carLoanActivity----------------->>>>>>" + response);
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        bean = null;
                        try {

                            bean = gson.fromJson(json, CarLoanBean.class);

                        } catch (JsonSyntaxException e) {

                            MyToast.show(getActivity(), "联网失败");
                            refreshLayout.finishRefresh();

                        }

                        if (bean.getCode() == 1) {
                            list = bean.getList();
//                        LogUtils.i(list.size() + "---------------->>>>>>>>");
                            if (list.size() == 0) {
                                llBgset.setBackground(bmdrawable);
                            } else {
                                llBgset.setBackground(null);
                            }

                            adapter = new CarLoanAdapter(getActivity(), list, btnlistener, btnlistener3, btnlistener4, btnlistener5, btnlistener6, btnlistener7, btnlistener8, btnlistener9);
                            lvCarLoan.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            lvCarLoan.setSelection(firstVisiblePosition);

//                        refreshCar.setRefreshing(false);
                            refreshLayout.finishRefresh();
                        } else if (bean.getCode() == 0) {
                            MyToast.show(getActivity(), bean.getMsg());
                            refreshLayout.finishRefresh();
                        }

                    }
                });
    }

    CarLoanAdapter.OnButtonClickLisetrner7 btnlistener7 = new CarLoanAdapter.OnButtonClickLisetrner7() {

        @Override
        public void onButtonClick(final int positon) {
            if (list.get(positon).getReconsider().equals("0")) {
                firstVisiblePosition = lvCarLoan.getFirstVisiblePosition();
                carId = list.get(positon).getCarId();
                final CircleDialog.Builder builder = new CircleDialog.Builder(getActivity());
                builder.configInput(new ConfigInput() {
                    @Override
                    public void onConfig(InputParams params) {
                        params.inputHeight = 150;
                        params.textSize = 40;
                    }
                })
                        .setInputHint("请输入复议原因")
                        .setTitle("是否复议？")
                        .setPositiveInput("确定", new OnInputClickListener() {
                            @Override
                            public void onClick(String text, View v) {

//                            LogUtils.e(text);

                                if (text == null || text.equals("")) {
                                    MyToast.show(getActivity(), "请填写复议原因！");
                                    return;
                                } else {
                                    hud.show();

                                    OkHttpUtils.post()
                                            .url(Constants.URLS.BASEURL + "Dealer/apply")
                                            .addParams("remark", text)
                                            .addParams("carId", carId)
                                            .build()
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onError(Call call, Exception e, int id) {
                                                    System.out.println("fuyi____________________>>>>>" + e);
                                                    hud.dismiss();
                                                    MyToast.show(getActivity(), "加载失败");
                                                }

                                                @Override
                                                public void onResponse(String response, int id) {
                                                    LogUtils.e("fuyi----------------->>>>>>" + response);
                                                    String json = response;
                                                    Gson gson = new Gson();
                                                    ZuoFeiBean bean = null;

                                                    try {
                                                        bean = gson.fromJson(json, ZuoFeiBean.class);
                                                    } catch (JsonSyntaxException e) {
                                                        MyToast.show(getActivity(), "服务器错误");
                                                    }

                                                    if (bean.getCode() == 1) {
                                                        adapter.notifyDataSetChanged();
                                                        MyToast.show(getActivity(), "复议成功");

                                                        initData();

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
            } else if (list.get(positon).getReconsider().equals("-1")) {
                MyToast.show(getActivity(), "复议被驳回！");
                new AlertDialog.Builder(getActivity())
                        .setTitle("复议驳回原因")
                        .setMessage(list.get(positon).getDismissal())
                        .setNegativeButton("知道了", null)
                        .show()
                        .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
            }
        }
    };

    CarLoanAdapter.OnButtonClickLisetrner8 btnlistener8 = new CarLoanAdapter.OnButtonClickLisetrner8() {

        @Override
        public void onButtonClick(final int positon) {
            firstVisiblePosition = lvCarLoan.getFirstVisiblePosition();

            OkHttpUtils.post()
                    .url(Constants.URLS.BASEURL + "Loan/loanShow")
                    .addParams("userId", list.get(positon).getId())
                    .addParams("carId", list.get(positon).getCarId())
                    .addParams("carType", list.get(positon).getIsnew())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            System.out.println("fangkuan____________________>>>>>" + e);
                            hud.dismiss();
                            MyToast.show(getActivity(), "加载失败");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            LogUtils.e("fangkuan----------------->>>>>>" + response);
                            String json = response;
                            Gson gson = new Gson();
                            fangkuanBean = null;

                            try {
                                fangkuanBean = gson.fromJson(json, Fangkuan.class);
                                if (fangkuanBean.getCode() == 1) {

                                    if (fangkuanBean.getRejects().getDismissal().equals("0")) {
                                        myDialog.showDialog();
                                    } else {
                                        new AlertDialog.Builder(getActivity())
                                                .setTitle(fangkuanBean.getRejects().getTitle())
                                                .setMessage("驳回原因：" + fangkuanBean.getRejects().getReason() + "\n" + "\n" + "提示：" + fangkuanBean.getRejects().getDescription())
                                                .setPositiveButton("申请放款", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        myDialog.showDialog();
                                                    }
                                                })
                                                .setNegativeButton("取消", null)
                                                .show()
                                                .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));

                                    }

                                } else if (fangkuanBean.getCode() == 0) {
                                    MyToast.show(getActivity(), fangkuanBean.getMsg());
                                }
                            } catch (JsonSyntaxException e) {
                                MyToast.show(getActivity(), "服务器错误");
                            }


                        }
                    });


        }
    };

    CarLoanAdapter.OnButtonClickLisetrner9 btnlistener9 = new CarLoanAdapter.OnButtonClickLisetrner9() {

        @Override
        public void onButtonClick(final int positon) {
            firstVisiblePosition = lvCarLoan.getFirstVisiblePosition();

            OkHttpUtils.post()
                    .url(Constants.URLS.BASEURL + "Loan/applyDealerRebate")
                    .addParams("userId", list.get(positon).getId())
                    .addParams("carId", list.get(positon).getCarId())
                    .addParams("carType", list.get(positon).getIsnew())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            System.out.println("fanyong____________________>>>>>" + e);
                            hud.dismiss();
                            MyToast.show(getActivity(), "加载失败");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            LogUtils.e("fanyong----------------->>>>>>" + response);
                            String json = response;
                            Gson gson = new Gson();
                            fanYongBean = null;

                            try {
                                fanYongBean = gson.fromJson(json, Fanyong.class);
                                if (fanYongBean.getCode() == 1) {

                                    myFanyongDialog.showDialog();

                                } else if (fanYongBean.getCode() == 0) {
                                    MyToast.show(getActivity(), fanYongBean.getMsg());
                                }
                            } catch (JsonSyntaxException e) {
                                MyToast.show(getActivity(), "服务器错误");
                            }


                        }
                    });


        }
    };

    private int stage;
    CarLoanAdapter.OnButtonClickLisetrner3 btnlistener3 = new CarLoanAdapter.OnButtonClickLisetrner3() {

        @Override
        public void onButtonClick(final int positon) {


            firstVisiblePosition = lvCarLoan.getFirstVisiblePosition();

            PreferenceUtils.setString(getActivity(), "carId", list.get(positon).getCarId());
            PreferenceUtils.setString(getActivity(), "isNew", list.get(positon).getIsnew());

            if (list.get(positon).getId().equals("-999")) {

                if (list.get(positon).getCarpicFlag().equals("-999")) {

                    startActivity(new Intent(getActivity(), UpLoadCarImageActivity.class));
                    // // 设置过渡动画
                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                    getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                } else if (list.get(positon).getCarpicFlag().equals("1")) {

                    if (list.get(positon).getCostFlag().equals("-999")) {

                        if (list.get(positon).getIsnew().equals("1")) {
                            startActivity(new Intent(getActivity(), Cost1Activity.class));
                            // // 设置过渡动画
                            int enterAnim6 = R.anim.next_enter;
                            int exitAnim6 = R.anim.next_exit;
                            getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                        } else if (list.get(positon).getIsnew().equals("2")) {

                            if (list.get(positon).getVprice().equals("-999")) {
                                hud.dismiss();
                                if (list.get(positon).getEstate().equals("-1")) {
                                    if (list.get(positon).getOrf() < 3) {
                                        new AlertDialog.Builder(getActivity())
                                                .setTitle("评估师第" + list.get(positon).getOrf() + "次驳回")
                                                .setMessage("原因：" + list.get(positon).getReasons() + "\n" + "\n" + "提示：您还能修改" + (3 - list.get(positon).getOrf()) + "次")
                                                .setPositiveButton("去修改", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        finish();
                                                        firstVisiblePosition = lvCarLoan.getFirstVisiblePosition();
                                                        saveData(positon);
                                                        startActivity(new Intent(getActivity(), FullInfoActivity1.class));
//                                                        overridePendingTransition(enterAnim0, exitAnim0);
                                                    }
                                                })
                                                .setNegativeButton("暂不修改", null)
                                                .show()
                                                .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
                                    } else {
                                        new AlertDialog.Builder(getActivity())
                                                .setTitle("提示")
                                                .setMessage("该客户已被评估师驳回3次，不能再发起贷款，请自行作废！")
                                                .setPositiveButton("知道了", null)
                                                .show()
                                                .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
                                    }
                                } else {

                                    MyToast.show(getActivity(), "评估师暂未评估");
                                }
                            } else {

                                if (list.get(positon).getReconsider().equals("-1")) {
                                    MyToast.show(getActivity(), "复议被驳回，请自行作废");
                                } else if (list.get(positon).getReconsider().equals("3")) {
                                    MyToast.show(getActivity(), "复议正在进行中，请等待结果");
                                } else if (list.get(positon).getReconsider().equals("1")) {
                                    MyToast.show(getActivity(), "不可复议，请自行作废");
                                } else {
                                    startActivity(new Intent(getActivity(), Cost3Activity.class));
                                    // // 设置过渡动画
                                    int enterAnim6 = R.anim.next_enter;
                                    int exitAnim6 = R.anim.next_exit;
                                    getActivity().overridePendingTransition(enterAnim6, exitAnim6);
                                }

                            }

                        }

                    } else if (list.get(positon).getCostFlag().equals("1")) {

                        startActivity(new Intent(getActivity(), CustomeReport.class));
                        int enterAnim6 = R.anim.next_enter;
                        int exitAnim6 = R.anim.next_exit;
                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                    }
                }
            } else {

                LogUtils.e("userId=" + list.get(positon).getId());

                PreferenceUtils.setString(getActivity(), "userId", list.get(positon).getId());

//            final String carLoan = list.get(positon).getCarloan();
//
//                final String credit = list.get(positon).getCredit();
//
//                System.out.println("credit" + credit);
//
//                PreferenceUtils.setString(getActivity(), "carLoan", carLoan);

//                if (bean.getRole() != 1) {
//
//                    if (!list.get(positon).getCarId().equals("0")) {
//                        PreferenceUtils.setString(getActivity(), "carId", list.get(positon).getCarId());
//                    }
//
//                }

                if (list.get(positon).getFlag().equals("0") || list.get(positon).getFlag().equals("-1")) {

                    OkHttpUtils.post()
                            .url(Constants.URLS.BASEURL + "Login/perfectInfo")
                            .addParams("userId", list.get(positon).getId())
                            .build()
                            .execute(new StringCallback() {

                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    System.out.println("Progress____________________>>>>>" + e);
                                    hud.dismiss();
                                }

                                @Override
                                public void onResponse(String response, int id) {

                                    LogUtils.e("Progress----------------->>>>>>" + response);
                                    String json = response;
                                    Gson gson = new Gson();

                                    ProgressBean bean = gson.fromJson(json, ProgressBean.class);

                                    PreferenceUtils.setString(getActivity(), "userName", bean.getUserName());

                                    stage = bean.getStage();

                                    String price = list.get(positon).getPrice();

                                    PreferenceUtils.setString(getActivity(), "loanPrice", price); //贷款额

                                    PreferenceUtils.setString(getActivity(), "carId", list.get(positon).getCarId());

//                                PreferenceUtils.setString(getActivity(), "carcarLoan", carLoan);

                                    PreferenceUtils.setString(getActivity(), "isNew", list.get(positon).getIsnew());

//                                    PreferenceUtils.setString(getActivity(), "rebate", list.get(positon).getRebate());
//
                                    PreferenceUtils.setString(getActivity(), "Latitude", list.get(positon).getVisits_lat());

                                    PreferenceUtils.setString(getActivity(), "Longtitude", list.get(positon).getVisits_lng());
//
//                                    PreferenceUtils.setString(getActivity(), "credit", list.get(positon).getCredit());

                                    PreferenceUtils.setString(getActivity(), "vprice", list.get(positon).getVprice());

                                    PreferenceUtils.setString(getActivity(), "flag", list.get(positon).getFlag());

                                    PreferenceUtils.setString(getActivity(), "contractId", list.get(positon).getContract());

                                    PreferenceUtils.setString(getActivity(), "rentType", list.get(positon).getRentType());

                                    PreferenceUtils.setString(getActivity(), "carLoan", "2");

                                    if (list.get(positon).getIsmarry() != null) {

                                        PreferenceUtils.setString(getActivity(), "isMarry", list.get(positon).getIsmarry());
                                    }

                                    if (list.get(positon).getIsguarantee() != null) {

                                        PreferenceUtils.setString(getActivity(), "needCautioner", list.get(positon).getIsguarantee());
                                    }


                                    /**
                                     0 信息已全部添加完成
                                     1 开始家访
                                     2 添加车辆信息
                                     3 添加客户基本信息
                                     4 添加联系人信息
                                     5 添加共借人信息
                                     6 添加配偶信息
                                     7 添加银行卡信息
                                     8 添加身份证信息
                                     9 添加管理团队
                                     10 结束家访
                                     11 添加二手车信息
                                     12 二手车车辆照片
                                     13 视频
                                     14 征信报告
                                     15 银行授权书
                                     16 新车费用
                                     17 二手车费用
                                     18 上传抵押证件照片
                                     19 修改费用
                                     20 修改车辆信息
                                     21 客户征信正在审核
                                     22 征信审核未通过，增加字段：reason，审核未通过原因
                                     23 添加室外照片
                                     24 添加合同照片
                                     24 添加合同照片
                                     25 添加电子合同签名照片
                                     26 添加人脸识别
                                     */

//                                if (carLoan.equals("1")) {

                                    hud.dismiss();

                                    if (stage == 1) {

                                        startActivity(new Intent(getActivity(), StartHomeVisitActivity.class));
                                        // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 3) {

                                        startActivity(new Intent(getActivity(), BaseInformationActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 4) {

                                        startActivity(new Intent(getActivity(), ContactsActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 5) {

                                        startActivity(new Intent(getActivity(), CautionerMessageActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 6) {

                                        startActivity(new Intent(getActivity(), WifeActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 7) {

                                        startActivity(new Intent(getActivity(), BankCardMessageActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 8) {

                                        startActivity(new Intent(getActivity(), IndoorPicActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 9) {

                                        startActivity(new Intent(getActivity(), TeamManager1Activity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 10) {

                                        startActivity(new Intent(getActivity(), StopHomeVisitActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 13) {
                                        startActivity(new Intent(getActivity(), UpLoadCusomerVideoActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 20) {
                                        if (list.get(positon).getOrf() < 3) {
                                            new AlertDialog.Builder(getActivity())
                                                    .setTitle("评估师第" + list.get(positon).getOrf() + "次驳回")
                                                    .setMessage("原因：" + bean.getReasons() + "\n" + "\n" + "提示：您还能修改" + (3 - list.get(positon).getOrf()) + "次")
                                                    .setPositiveButton("去修改", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
//                                                        finish();
                                                            firstVisiblePosition = lvCarLoan.getFirstVisiblePosition();
                                                            saveData(positon);
                                                            startActivity(new Intent(getActivity(), FullInfoActivity1.class));
//                                                        getActivity().overridePendingTransition(enterAnim0, exitAnim0);
                                                        }
                                                    })
                                                    .setNegativeButton("暂不修改", null)
                                                    .show()
                                                    .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
                                        } else {
                                            new AlertDialog.Builder(getActivity())
                                                    .setTitle("提示")
                                                    .setMessage("该客户已被评估师驳回3次，不能再发起贷款，请自行作废！")
                                                    .setPositiveButton("知道了", null)
                                                    .show()
                                                    .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
                                        }

                                    } else if (stage == 21) {

                                        LogUtils.e("shiwoma");

                                        MyToast.show(getActivity(), bean.getMsg());

                                    } else if (stage == 22) {

                                        MyToast.show(getActivity(), bean.getMsg());

                                    } else if (stage == 23) {

                                        LogUtils.e("添加室外照片");

                                        startActivity(new Intent(getActivity(), OutdoorPicActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 24) {

                                        LogUtils.e("添加合同照片");
                                        startActivity(new Intent(getActivity(), ContractPicActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 25) {

                                        LogUtils.e("添加签名照片");
                                        startActivity(new Intent(getActivity(), CustomerDrawNameActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 26) {

                                        LogUtils.e("添加签名照片");
                                        startActivity(new Intent(getActivity(), FacePicActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    }else if (stage == 27) {

                                        LogUtils.e("征信查询");
                                        startActivity(new Intent(getActivity(), ZhengxinActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);

                                    }  else if (stage == 0) {
                                        MyToast.show(getActivity(), bean.getMsg());
                                    }

                                }
                            });

                } else if (list.get(positon).getFlag().equals("8")) {

                    hud.dismiss();

                    if (list.get(positon).getRf() < 3) {

                        firstVisiblePosition = lvCarLoan.getFirstVisiblePosition();
                        PreferenceUtils.setString(getActivity(), "userId", list.get(positon).getId());

                        new AlertDialog.Builder(getActivity())
                                .setTitle("风控第" + list.get(positon).getRf() + "次驳回")
                                .setMessage("\n" + "提示：您还能修改" + (3 - list.get(positon).getRf()) + "次")
                                .setPositiveButton("去提交", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(getActivity(), TeamManager1Activity.class));
                                        // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        getActivity().overridePendingTransition(enterAnim6, exitAnim6);
                                    }
                                })
                                .setNegativeButton("去修改", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        firstVisiblePosition = lvCarLoan.getFirstVisiblePosition();
                                        saveData(positon);

                                        startActivity(new Intent(getActivity(), FullInfoActivity1.class));

                                    }
                                })
                                .show()
                                .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));

                    } else {

                        new AlertDialog.Builder(getActivity())
                                .setTitle("提示")
                                .setMessage("该客户已被风控驳回3次，不能再发起贷款，请自行作废！")
                                .setPositiveButton("作废", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        firstVisiblePosition = lvCarLoan.getFirstVisiblePosition();
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
                                                    public void onClick(String text, final View v) {

                                                        hud.show();

                                                        OkHttpUtils.post()
                                                                .url(Constants.URLS.BASEURL + "login/toVoid")
                                                                .addParams("remark", text)
                                                                .addParams("operatorId", saleID)
                                                                .addParams("id", list.get(positon).getId())
                                                                .addParams("tableName", "user")
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

                                                                            deletePattern(v, positon);
                                                /*initData();*/
                                                                        } else if (bean.getCode() == 0) {
                                                                            MyToast.show(getActivity(), bean.getMsg());
                                                                        }

                                                                        hud.dismiss();
                                                                    }
                                                                });

                                                    }
                                                })
                                                .setNegative("取消", null)
                                                .show();
                                    }
                                })
                                .setNegativeButton("知道了", null)
                                .show()
                                .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));

                    }

                } else {

                    startActivity(new Intent(getActivity(), GuoCheng1Activity.class));
                    // // 设置过渡动画
                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                    getActivity().overridePendingTransition(enterAnim6, exitAnim6);
                }
            }

        }
    };


    CarLoanAdapter.OnButtonClickLisetrner4 btnlistener4 = new CarLoanAdapter.OnButtonClickLisetrner4() {

        @Override
        public void onButtonClick(final int positon, final View view) {

            PreferenceUtils.setString(getActivity(), "userId", list.get(positon).getId());

            firstVisiblePosition = lvCarLoan.getFirstVisiblePosition();

            startActivity(new Intent(getActivity(), GuoCheng1Activity.class));
            // // 设置过渡动画
            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
            getActivity().overridePendingTransition(enterAnim6, exitAnim6);

        }
    };

    CarLoanAdapter.OnButtonClickLisetrner5 btnlistener5 = new CarLoanAdapter.OnButtonClickLisetrner5() {

        @Override
        public void onButtonClick(final int positon, final View view) {
            carId = list.get(positon).getCarId();
            firstVisiblePosition = lvCarLoan.getFirstVisiblePosition();
//            PreferenceUtils.setString(getActivity(), "userId", list.get(positon).getId());
//            startActivity(new Intent(getActivity(), TeamManager1Activity.class));
//            // // 设置过渡动画
//            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
//            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
//            getActivity().overridePendingTransition(enterAnim6, exitAnim6);
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

//                            LogUtils.e(text);

                            if (text == null || text.equals("")) {
                                MyToast.show(getActivity(), "请填写作废原因！");
                                return;
                            } else {
                                hud.show();

                                OkHttpUtils.post()
                                        .url(Constants.URLS.BASEURL + "Dealer/tovoidCar")
                                        .addParams("remark", text)
                                        .addParams("carId", carId)
                                        .addParams("status", list.get(positon).getIsnew())
                                        .addParams("saleId", saleID)
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

    CarLoanAdapter.OnButtonClickLisetrner6 btnlistener6 = new CarLoanAdapter.OnButtonClickLisetrner6() {

        @Override
        public void onButtonClick(final int positon) {
            hud.show();
            firstVisiblePosition = lvCarLoan.getFirstVisiblePosition();

            OkHttpUtils.post()
                    .url(Constants.URLS.BASEURL + "Loan/mortgageRejects")
                    .addParams("carId", list.get(positon).getCarId())
                    .addParams("userId", list.get(positon).getId())
                    .addParams("carType", list.get(positon).getIsnew())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            System.out.println("fanyong____________________>>>>>" + e);
                            hud.dismiss();
                            MyToast.show(getActivity(), "加载失败");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            LogUtils.e("fanyong----------------->>>>>>" + response);
                            String json = response;
                            Gson gson = new Gson();
                            DiyaBohuiBean bean = null;

                            try {
                                bean = gson.fromJson(json, DiyaBohuiBean.class);
                            } catch (JsonSyntaxException e) {
                                MyToast.show(getActivity(), "服务器错误");
                            }

                            if (bean.getCode() == 1) {

                                if (bean.getRejects().getDismissal().equals("0")) {
                                    PreferenceUtils.setString(getActivity(), "carId", list.get(positon).getCarId());
                                    PreferenceUtils.setString(getActivity(), "isNew", list.get(positon).getIsnew());
                                    PreferenceUtils.setString(getActivity(), "userId", list.get(positon).getId());
                                    PreferenceUtils.setString(getActivity(), "mid", list.get(positon).getMid());
                                    startActivity(new Intent(getActivity(), DiyaPicActivity.class));
                                    // // 设置过渡动画
                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                    getActivity().overridePendingTransition(enterAnim6, exitAnim6);
                                } else {
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle(bean.getRejects().getTitle())
                                            .setMessage("驳回原因：" + bean.getRejects().getReason() + "\n" + "\n" + "提示：" + bean.getRejects().getDescription())
                                            .setPositiveButton("去填写", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    PreferenceUtils.setString(getActivity(), "carId", list.get(positon).getCarId());
                                                    PreferenceUtils.setString(getActivity(), "isNew", list.get(positon).getIsnew());
                                                    PreferenceUtils.setString(getActivity(), "userId", list.get(positon).getId());
                                                    PreferenceUtils.setString(getActivity(), "mid", list.get(positon).getMid());
                                                    startActivity(new Intent(getActivity(), DiyaPicActivity.class));
                                                    // // 设置过渡动画
                                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                                    getActivity().overridePendingTransition(enterAnim6, exitAnim6);
                                                }
                                            })
                                            .setNegativeButton("取消", null)
                                            .show()
                                            .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));

                                }

                            } else if (bean.getCode() == 0) {
                                MyToast.show(getActivity(), bean.getMsg());
                            }

                            hud.dismiss();
                        }
                    });
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
                CarLoanAdapter.ViewHolder holder = (CarLoanAdapter.ViewHolder) view.getTag();
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
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_next:
                PreferenceUtils.setString(getActivity(), "lala", "2");
                startActivity(new Intent(getContext(), CarEvaluateActivity2.class));
                int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                getActivity().overridePendingTransition(enterAnim6, exitAnim6);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (list.get(position).getFlag().equals("0") || list.get(position).getFlag().equals("8")
                || list.get(position).getFlag().equals("-1") || list.get(position).getFlag().equals("-999")) {

            firstVisiblePosition = lvCarLoan.getFirstVisiblePosition();
            PreferenceUtils.setString(getActivity(), "isNew", "2");
            saveData(position);
//        startActivity(new Intent(getActivity(), CarDetailActivity.class));
            startActivity(new Intent(getActivity(), FullInfoActivity1.class));
            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
            getActivity().overridePendingTransition(enterAnim6, exitAnim6);
        } else {
            MyToast.show(getActivity(), "该用户已发起贷款，不能修改");
        }
    }

    private void saveData(int position) {
        PreferenceUtils.setString(getActivity(), "carLoan", "2");
        PreferenceUtils.setString(getActivity(), "carId", list.get(position).getCarId());
        PreferenceUtils.setString(getActivity(), "isNew", "2");
        PreferenceUtils.setString(getActivity(), "flag", list.get(position).getFlag());
//        PreferenceUtils.setString(getActivity(), "vprice", list.get(position).getVprice());
//        PreferenceUtils.setInt(getActivity(), "carpicchange", list.get(position).getCarpicchange());
//        PreferenceUtils.setInt(getActivity(), "carinfochange", list.get(position).getCarinfochange());
        PreferenceUtils.setString(getActivity(), "estate", list.get(position).getEstate());
        PreferenceUtils.setString(getActivity(), "carpicFlag", list.get(position).getCarpicFlag());
        PreferenceUtils.setString(getActivity(), "costFlag", list.get(position).getCostFlag());
        PreferenceUtils.setString(getActivity(), "userId", list.get(position).getUserId());

        PreferenceUtils.setString(getActivity(), "contractId", list.get(position).getContract());
        PreferenceUtils.setString(getActivity(), "mid", list.get(position).getMid());

        PreferenceUtils.setString(getActivity(), "rentType", list.get(position).getRentType());

        PreferenceUtils.setString(getActivity(), "carLoan", "2");


    }

    public class MyFangkuanDialog {

        private Context context;
        private TextView tvName;
        private TextView tvChooseType;
        private TextView tvDaikuane;
        private TextView tvGpsPrice;
        private TextView tvLixicha;
        private TextView tvFangkuane;
        private EditText etShoukuanName;
        private EditText etShoukuanNum;
        private EditText etDianfuNum;
        private RadioButton rbYes;
        private RadioButton rbNo;
        private RadioGroup rgDianfu;
        private LinearLayout llZhouqi;

        public MyFangkuanDialog(Context context) {
            this.context = context;
        }

        public void showDialog() {

//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View layout = inflater.inflate(R.layout.dialog_fangkuan, null);//获取自定义布局

//            builder.setView(layout);

            tvName = (TextView) layout.findViewById(R.id.tv_name);
            tvChooseType = (TextView) layout.findViewById(R.id.tv_choose_type);
            tvDaikuane = (TextView) layout.findViewById(R.id.tv_daikuane);
            tvGpsPrice = (TextView) layout.findViewById(R.id.tv_gps_price);
            tvLixicha = (TextView) layout.findViewById(R.id.tv_lixicha);
            tvFangkuane = (TextView) layout.findViewById(R.id.tv_fangkuane);

            etShoukuanName = (EditText) layout.findViewById(R.id.et_shoukuan_name);
            etShoukuanNum = (EditText) layout.findViewById(R.id.et_shoukuan_num);
            etDianfuNum = (EditText) layout.findViewById(R.id.et_dianfu_num);

            rbYes = (RadioButton) layout.findViewById(R.id.rb_yes);
            rbNo = (RadioButton) layout.findViewById(R.id.rb_no);

            rgDianfu = (RadioGroup) layout.findViewById(R.id.rg_dianfu);

            llZhouqi = (LinearLayout) layout.findViewById(R.id.ll_zhouqi);

            rgDianfu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == rbYes.getId()) {
                        llZhouqi.setVisibility(View.VISIBLE);
                    } else if (checkedId == rbNo.getId()) {
                        llZhouqi.setVisibility(View.GONE);
                    }
                }
            });

            final AlertDialog dialog = new AlertDialog.Builder(context).setView(layout).setPositiveButton("确定", null)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
//这里必须要先调show()方法，后面的getButton才有效
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.red));


            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rbYes.isChecked() || rbNo.isChecked()) {

                        if (rbYes.isChecked()) {
                            if (etShoukuanName.getText().toString().equals("")) {
                                MyToast.show(context, "请填写收款人");
                                return;
                            }
                            if (etShoukuanNum.getText().toString().equals("")) {
                                MyToast.show(context, "请填写收款账号");
                                return;
                            }
                            if (etDianfuNum.getText().equals("")) {
                                MyToast.show(context, "请填写垫付周期");
                                return;
                            }
                            if (Integer.parseInt(etDianfuNum.getText().toString()) <= 0) {
                                MyToast.show(context, "垫付周期必须大于等于1");
                                return;
                            }
                        } else if (rbNo.isChecked()) {
                            if (etShoukuanName.getText().toString().equals("")) {
                                MyToast.show(context, "请填写收款人");
                                return;
                            }
                            if (etShoukuanNum.getText().toString().equals("")) {
                                MyToast.show(context, "请填写收款账号");
                                return;
                            }
                            etDianfuNum.setText("0");
                        }

                        OkHttpUtils.post()
                                .url(Constants.URLS.BASEURL + "Loan/applyLoan")
                                .addParams("saleId", saleID)
                                .addParams("loanId", fangkuanBean.getList().getLoanId())
                                .addParams("advance", rbYes.isChecked() ? "1" : "0")
                                .addParams("cycle", etDianfuNum.getText().toString())
                                .addParams("payname", etShoukuanName.getText().toString())
                                .addParams("loanMoney", tvFangkuane.getText().toString())
                                .addParams("paycode", etShoukuanNum.getText().toString())
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        System.out.println("fangkuan____________________>>>>>" + e);
                                        hud.dismiss();
                                        MyToast.show(getActivity(), "加载失败");
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        LogUtils.e("fangkuan----------------->>>>>>" + response);
                                        String json = response;
                                        Gson gson = new Gson();
                                        fangkuanBean = null;

                                        try {
                                            fangkuanBean = gson.fromJson(json, Fangkuan.class);
                                            if (fangkuanBean.getCode() == 1) {

                                                dialog.dismiss();
                                                adapter.notifyDataSetChanged();
                                                MyToast.show(getActivity(), "提交成功");

                                            } else if (fangkuanBean.getCode() == 0) {
                                                MyToast.show(getActivity(), fangkuanBean.getMsg());
                                            }
                                        } catch (JsonSyntaxException e) {
                                            MyToast.show(getActivity(), "服务器错误");
                                        }
                                    }
                                });

                    } else {
                        MyToast.show(context, "请选择是否垫付");
                        return;
                    }
                }
            });

            initUI();
        }

        private void initUI() {
            tvName.setText(fangkuanBean.getList().getUsername());
            tvChooseType.setText(fangkuanBean.getList().getCarSize());
            tvDaikuane.setText(fangkuanBean.getList().getLoanPrice());
            tvGpsPrice.setText(fangkuanBean.getList().getGps());
            tvLixicha.setText(fangkuanBean.getList().getInterestTotal());
            tvFangkuane.setText(fangkuanBean.getList().getLoanMoney());
        }
    }

    public class MyFanyongDialog {

        private Context context;
        private TextView ff_bili;
        private TextView ff_daikuane;
        private TextView ff_fanyong;
        private TextView ff_name;
        private TextView ff_zhanghao;
        private TextView tv_gps_price;

        public MyFanyongDialog(Context context) {
            this.context = context;
        }

        public void showDialog() {

//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View layout = inflater.inflate(R.layout.dialog_fanyong, null);//获取自定义布局

//            builder.setView(layout);

            ff_bili = (TextView) layout.findViewById(R.id.ff_bili);
            ff_daikuane = (TextView) layout.findViewById(R.id.ff_daikuane);
            ff_fanyong = (TextView) layout.findViewById(R.id.ff_fanyong);
            ff_name = (TextView) layout.findViewById(R.id.ff_name);
            ff_zhanghao = (TextView) layout.findViewById(R.id.ff_zhanghao);
            tv_gps_price = (TextView) layout.findViewById(R.id.tv_gps_price);

            final AlertDialog dialog = new AlertDialog.Builder(context).setView(layout).setPositiveButton("确定", null)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
//这里必须要先调show()方法，后面的getButton才有效
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.red));


            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    OkHttpUtils.post()
                            .url(Constants.URLS.BASEURL + "Loan/applyDealerLoan")
                            .addParams("saleId", saleID)
                            .addParams("loanId", fanYongBean.getList().getLoanId())
                            .addParams("loanAmount", fanYongBean.getList().getLoanAmount())
                            .addParams("rebate", fanYongBean.getList().getRebate())
                            .addParams("money", fanYongBean.getList().getMoney())
                            .addParams("flag", "0")
                            .addParams("dealerId", fanYongBean.getList().getDealerId())
                            .addParams("gps", fanYongBean.getList().getGps())

                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    System.out.println("fangkuan____________________>>>>>" + e);
                                    hud.dismiss();
                                    MyToast.show(getActivity(), "加载失败");
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    LogUtils.e("fangkuan----------------->>>>>>" + response);
                                    String json = response;
                                    Gson gson = new Gson();
                                    fangkuanBean = null;

                                    try {
                                        fangkuanBean = gson.fromJson(json, Fangkuan.class);
                                        if (fangkuanBean.getCode() == 1) {

                                            dialog.dismiss();
                                            adapter.notifyDataSetChanged();
                                            MyToast.show(getActivity(), "提交成功");

                                        } else if (fangkuanBean.getCode() == 0) {
                                            MyToast.show(getActivity(), fangkuanBean.getMsg());
                                        }
                                    } catch (JsonSyntaxException e) {
                                        MyToast.show(getActivity(), "服务器错误");
                                    }


                                }
                            });

                }
            });

            initUI();
        }

        private void initUI() {
            ff_bili.setText(fanYongBean.getList().getRebate());
            ff_daikuane.setText(fanYongBean.getList().getLoanAmount());
            ff_fanyong.setText(fanYongBean.getList().getMoney());
            ff_name.setText(fanYongBean.getList().getUsername());
            ff_zhanghao.setText(fanYongBean.getList().getBankcode());
            tv_gps_price.setText(fanYongBean.getList().getGps());
        }
    }
}
