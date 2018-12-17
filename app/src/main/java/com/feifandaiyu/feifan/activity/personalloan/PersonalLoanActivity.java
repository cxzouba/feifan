package com.feifandaiyu.feifan.activity.personalloan;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.PersonalLoanAdapper;
import com.feifandaiyu.feifan.bean.DiyaBohuiBean;
import com.feifandaiyu.feifan.bean.Fangkuan;
import com.feifandaiyu.feifan.bean.Fanyong;
import com.feifandaiyu.feifan.bean.PersonalLoanURLBean;
import com.feifandaiyu.feifan.bean.ProgressBean;
import com.feifandaiyu.feifan.bean.ZuoFeiBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.dialog.SelfDialog;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.Resource2Bitmap;
import com.feifandaiyu.feifan.view.BaseViewHelper;
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
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author houdaichang
 */

public class PersonalLoanActivity extends AppCompatActivity {

    @InjectView(R.id.rl_person)
    LinearLayout rlPerson;
    @InjectView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.iv_daipinggu)
    ImageView ivDaipinggu;
    @InjectView(R.id.iv_yipinggu)
    ImageView ivYipinggu;
    @InjectView(R.id.add_car)
    TextView addCar;
    @InjectView(R.id.imageView)
    RelativeLayout imageView;
    @InjectView(R.id.sliding_tabs)
    Toolbar slidingTabs;
    PersonalLoanAdapper.OnTestClickLisetener lisetener = new PersonalLoanAdapper.OnTestClickLisetener() {
        @Override
        public void onTestClick(View view) {
            final TextView tv = (TextView) view;

            new AlertDialog.Builder(PersonalLoanActivity.this)

                    .setMessage("将要拨打电话给" + tv.getText())
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (ActivityCompat.checkSelfPermission(PersonalLoanActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(PersonalLoanActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                            } else {

                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tv.getText()));
                                PersonalLoanActivity.this.startActivity(intent);

                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();

        }
    };
    private ListView lvPersonaloan;
    private TextView text;
    private TextView tvNext;
    private SelfDialog selfDialog;
    private List<PersonalLoanURLBean.ListBean> list1;
    private PersonalLoanAdapper adapper;
    private int stage;
    private String saleID;
    private Bitmap bitmap;
    private BitmapDrawable bmdrawable;
    private KProgressHUD hud;
    private int firstVisiblePosition;
    private int i= 1;
    PersonalLoanAdapper.OnButtonClickLisetrner4 btnlistener4 = new PersonalLoanAdapper.OnButtonClickLisetrner4() {

        @Override
        public void onButtonClick(final int positon, final View view) {

            PreferenceUtils.setString(PersonalLoanActivity.this, "userId", list1.get(positon).getId());

            firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();

            startActivity(new Intent(PersonalLoanActivity.this, GuoCheng1Activity.class));
            // // 设置过渡动画
            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
            overridePendingTransition(enterAnim6, exitAnim6);

        }
    };
    PersonalLoanAdapper.OnButtonClickLisetrner10 btnlistener10 = new PersonalLoanAdapper.OnButtonClickLisetrner10() {

        @Override
        public void onButtonClick(final int positon, final View view) {
            firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();

            PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(positon).getCarid());
            PreferenceUtils.setString(PersonalLoanActivity.this,  "isNew", list1.get(positon).getIsnew());

            startActivity(new Intent(PersonalLoanActivity.this, HegePicActivity.class));

        }
    };
    PersonalLoanAdapper.OnButtonClickLisetrner11 btnlistener11 = new PersonalLoanAdapper.OnButtonClickLisetrner11() {

        @Override
        public void onButtonClick(final int positon) {
            firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();

            PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(positon).getCarid());
            PreferenceUtils.setString(PersonalLoanActivity.this, "isNew", list1.get(positon).getIsnew());
            startActivity(new Intent(PersonalLoanActivity.this, TicheActivity.class));

        }
    };

    PersonalLoanAdapper.OnButtonClickLisetrner12 btnlistener12 = new PersonalLoanAdapper.OnButtonClickLisetrner12() {

        @Override
        public void onButtonClick(final int positon) {
            firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();

            PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(positon).getCarid());
            PreferenceUtils.setString(PersonalLoanActivity.this, "isNew", list1.get(positon).getIsnew());
            if (list1.get(positon).getIsnew().equals("1")) {
                startActivity(new Intent(PersonalLoanActivity.this, LuohuActivity.class));
            }else{
                startActivity(new Intent(PersonalLoanActivity.this, UpNewCarImageActivity.class));
            }

        }
    };

    PersonalLoanAdapper.OnButtonClickLisetrner13 btnlistener13 = new PersonalLoanAdapper.OnButtonClickLisetrner13() {

        @Override
        public void onButtonClick(final int positon) {
            firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();

            PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(positon).getCarid());
            PreferenceUtils.setString(PersonalLoanActivity.this, "isNew", list1.get(positon).getIsnew());

            startActivity(new Intent(PersonalLoanActivity.this, DiYaAfterLuoHuPicActivity.class));

        }
    };


    PersonalLoanAdapper.OnButtonClickLisetrner6 btnlistener6 = new PersonalLoanAdapper.OnButtonClickLisetrner6() {

        @Override
        public void onButtonClick(final int positon, final View view) {

            hud.show();
            firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();

            OkHttpUtils.post()
                    .url(Constants.URLS.BASEURL + "Loan/mortgageRejects")
                    .addParams("carId", list1.get(positon).getCarid())
                    .addParams("userId", list1.get(positon).getId())
                    .addParams("carType", list1.get(positon).getIsnew())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            System.out.println("fanyong____________________>>>>>" + e);
                            hud.dismiss();
                            MyToast.show(PersonalLoanActivity.this, "加载失败");
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
                                MyToast.show(PersonalLoanActivity.this, "服务器错误");
                            }

                            if (bean.getCode() == 1) {

                                if (bean.getRejects().getDismissal().equals("0")) {
                        //复议驳回
                                if (list1.get(positon).getCarpic().equals("1")) {
                                    if(list1.get(positon).getIsnew().equals("1")){
                                        //是新车
                                        PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(positon).getCarid());
                                        PreferenceUtils.setString(PersonalLoanActivity.this, "isNew", list1.get(positon).getIsnew());
                                        PreferenceUtils.setString(PersonalLoanActivity.this, "userId", list1.get(positon).getId());
                                        PreferenceUtils.setString(PersonalLoanActivity.this, "mid", list1.get(positon).getMid());
                                        PreferenceUtils.setString(PersonalLoanActivity.this, "jiaoqiangxian", list1.get(positon).getInsurance().size() == 0 ? "" : list1.get(positon).getInsurance().get(0));
                                        PreferenceUtils.setString(PersonalLoanActivity.this, "jiaoqiangxianShow", list1.get(positon).getInsuranceshow().size() == 0 ? "" : list1.get(positon).getInsuranceshow().get(0));
                                        PreferenceUtils.setString(PersonalLoanActivity.this, "fapiao", list1.get(positon).getInvoice().size() == 0 ? "" : list1.get(positon).getInvoice().get(0));
                                        PreferenceUtils.setString(PersonalLoanActivity.this, "fapiaoShow", list1.get(positon).getInvoiceshow().size() == 0 ? "" : list1.get(positon).getInvoiceshow().get(0));

                                        startActivity(new Intent(PersonalLoanActivity.this, DiyaPicNewActivity.class));
                                    }else{
                                        if ( list1.get(positon).getCarloan()==0){
                                            //是交易贷二手车
                                            PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(positon).getCarid());
                                            Intent intent = new Intent(PersonalLoanActivity.this,DaiAfterActivity.class);
                                            startActivity(intent);
                                        }else   if ( list1.get(positon).getCarloan()==1){
                                            //车抵贷二手车
                                            //++++++++++++++++++++++++
                                            PreferenceUtils.setString(PersonalLoanActivity.this, "carType", list1.get(positon).getIsnew());
                                            PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(positon).getCarid());
                                            PreferenceUtils.setString(PersonalLoanActivity.this, "mid", list1.get(positon).getMid());
                                            Intent intent = new Intent(PersonalLoanActivity.this,CarDiYaActivity.class);
                                            startActivity(intent);
                                        }

                                    }


                                } else if (list1.get(positon).getCarpic().equals("-999")) {

                                    PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(positon).getCarid());

                                    startActivity(new Intent(PersonalLoanActivity.this, NewCarPicActivity.class));

                                }

                                // // 设置过渡动画、
                                int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                overridePendingTransition(enterAnim6, exitAnim6);
                                }
//                                else {
//
//                                    if (list1.get(positon).getIsnew().equals("2")) {
//                                        new AlertDialog.Builder(PersonalLoanActivity.this)
//                                                .setTitle(bean.getRejects().getTitle())
//                                                .setMessage("驳回原因：" + bean.getRejects().getReason() + "\n" + "\n" + "提示：" + bean.getRejects().getDescription())
//                                                .setPositiveButton("去修改", new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(positon).getCarid());
//                                                        PreferenceUtils.setString(PersonalLoanActivity.this, "isNew", list1.get(positon).getIsnew());
//                                                        PreferenceUtils.setString(PersonalLoanActivity.this, "userId", list1.get(positon).getId());
//                                                        PreferenceUtils.setString(PersonalLoanActivity.this, "mid", list1.get(positon).getMid());
//                                                        startActivity(new Intent(PersonalLoanActivity.this, UpdateDiyaPicActivity.class));
//                                                        // // 设置过渡动画
//                                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
//                                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
//                                                        overridePendingTransition(enterAnim6, exitAnim6);
//                                                    }
//                                                })
//                                                .setNegativeButton("取消", null)
//                                                .setNeutralButton("作废", new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        ShowZuofei(positon, view);
//                                                    }
//                                                })
//                                                .show()
//                                                .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
//
//                                    } else if (list1.get(positon).getIsnew().equals("1")) {
//                                        new AlertDialog.Builder(PersonalLoanActivity.this)
//                                                .setTitle(bean.getRejects().getTitle())
//                                                .setMessage("驳回原因：" + bean.getRejects().getReason() + "\n" + "\n" + "提示：" + bean.getRejects().getDescription())
//                                                .setPositiveButton("修改证件照片", new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(positon).getCarid());
//                                                        PreferenceUtils.setString(PersonalLoanActivity.this, "isNew", list1.get(positon).getIsnew());
//                                                        PreferenceUtils.setString(PersonalLoanActivity.this, "userId", list1.get(positon).getId());
//                                                        PreferenceUtils.setString(PersonalLoanActivity.this, "mid", list1.get(positon).getMid());
//                                                        startActivity(new Intent(PersonalLoanActivity.this, UpdateDiyaPicActivity.class));
//                                                        // // 设置过渡动画
//                                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
//                                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
//                                                        overridePendingTransition(enterAnim6, exitAnim6);
//                                                    }
//                                                })
//                                                .setNegativeButton("修改车辆照片", new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(positon).getCarid());
//                                                        startActivity(new Intent(PersonalLoanActivity.this, UpdateNewCarImageActivity.class));
//                                                        // // 设置过渡动画
//                                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
//                                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
//                                                        overridePendingTransition(enterAnim6, exitAnim6);
//                                                    }
//                                                })
//                                                .show()
//                                                .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
//
//                                    }
//                                }


                            } else if (bean.getCode() == 0) {
                                MyToast.show(PersonalLoanActivity.this, bean.getMsg());
                            }

                            hud.dismiss();
                        }
                    });


        }
    };
    private PersonalLoanURLBean bean;
    private BaseViewHelper helper;
    private String isNew;
    private String carId;
    PersonalLoanAdapper.OnButtonClickLisetrner5 btnlistener5 = new PersonalLoanAdapper.OnButtonClickLisetrner5() {

        @Override
        public void onButtonClick(final int positon, final View view) {
            firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();
            ShowZuofei(positon, view);
        }
    };
    private ProgressBean progressBean;
    PersonalLoanAdapper.OnButtonClickLisetrner3 btnlistener3 = new PersonalLoanAdapper.OnButtonClickLisetrner3() {

        @Override
        public void onButtonClick(final int positon) {
            hud.show();

            firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();

            PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(positon).getCarid());
            PreferenceUtils.setString(PersonalLoanActivity.this, "isNew", list1.get(positon).getIsnew());

            if (list1.get(positon).getId().equals("-999")) {

                if (list1.get(positon).getCarpicFlag().equals("-999")) {
                    hud.dismiss();
                    startActivity(new Intent(PersonalLoanActivity.this, UpLoadCarImageActivity.class));
                    //设置过渡动画
                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                    overridePendingTransition(enterAnim6, exitAnim6);

                } else if (list1.get(positon).getCarpicFlag().equals("1")) {

                    if (list1.get(positon).getCostFlag().equals("-999")) {

                        if (list1.get(positon).getIsnew().equals("1")) {
                            hud.dismiss();

                            startActivity(new Intent(PersonalLoanActivity.this, Cost1Activity.class));
                            // // 设置过渡动画
                            int enterAnim6 = R.anim.next_enter;
                            int exitAnim6 = R.anim.next_exit;
                            overridePendingTransition(enterAnim6, exitAnim6);

                        } else if (list1.get(positon).getIsnew().equals("2")) {

                            if (list1.get(positon).getVprice().equals("-999")) {
                                hud.dismiss();
                                if (list1.get(positon).getEstate().equals("-1")) {
                                    if (list1.get(positon).getOrf() < 3) {
                                        new AlertDialog.Builder(PersonalLoanActivity.this)
                                                .setTitle("评估师第" + list1.get(positon).getOrf() + "次驳回")
                                                .setMessage("原因：" + list1.get(positon).getReasons() + "\n" + "\n" + "提示：您还能修改" + (3 - list1.get(positon).getOrf()) + "次")
                                                .setPositiveButton("去修改", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        finish();
                                                        firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();
                                                        saveData(positon);
                                                        startActivity(new Intent(PersonalLoanActivity.this, FullInfoActivity1.class));
//                                                        overridePendingTransition(enterAnim0, exitAnim0);
                                                    }
                                                })
                                                .setNegativeButton("暂不修改", null)
                                                .show()
                                                .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
                                    } else {
                                        new AlertDialog.Builder(PersonalLoanActivity.this)
                                                .setTitle("提示")
                                                .setMessage("该客户已被评估师驳回3次，不能再发起贷款，请自行作废！")
                                                .setPositiveButton("知道了", null)
                                                .show()
                                                .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
                                    }

                                } else {

                                    MyToast.show(PersonalLoanActivity.this, "评估师暂未评估");
                                }

                            } else {

                                hud.dismiss();

                                if (list1.get(positon).getReconsider().equals("-1")) {
                                    MyToast.show(PersonalLoanActivity.this, "复议被驳回，请自行作废");
                                } else if (list1.get(positon).getReconsider().equals("3")) {
                                    MyToast.show(PersonalLoanActivity.this, "复议正在进行中，请等待结果");
                                } else if (list1.get(positon).getReconsider().equals("1")) {
                                    MyToast.show(PersonalLoanActivity.this, "不可复议，请自行作废");
                                } else {
                                    startActivity(new Intent(PersonalLoanActivity.this, Cost2Activity.class));
                                    // // 设置过渡动画
                                    int enterAnim6 = R.anim.next_enter;
                                    int exitAnim6 = R.anim.next_exit;
                                    overridePendingTransition(enterAnim6, exitAnim6);
                                }
                            }
                        }
                    } else if (list1.get(positon).getCostFlag().equals("1")) {
                        hud.dismiss();
                        startActivity(new Intent(PersonalLoanActivity.this, CustomeReport.class));
                        // // 设置过渡动画
                        int enterAnim6 = R.anim.next_enter;
                        int exitAnim6 = R.anim.next_exit;
                        overridePendingTransition(enterAnim6, exitAnim6);
                    }
                }
            } else {

                LogUtils.e("userId=" + list1.get(positon).getId());

                PreferenceUtils.setString(PersonalLoanActivity.this, "userId", list1.get(positon).getId());

//            final String carLoan = list1.get(positon).getCarloan();
//
//                final String credit = list1.get(positon).getCredit();
//
//                System.out.println("credit" + credit);
//
//                PreferenceUtils.setString(PersonalLoanActivity.this, "carLoan", carLoan);

                if (bean.getRole() != 1) {

                    if (!list1.get(positon).getCarid().equals("0")) {
                        PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(positon).getCarid());
                    }

                }

                if (list1.get(positon).getFlag().equals("0") || list1.get(positon).getFlag().equals("-1")) {

                    OkHttpUtils.post()
                            .url(Constants.URLS.BASEURL + "Login/perfectInfo")
                            .addParams("userId", list1.get(positon).getId())
                            .addParams("carId", list1.get(positon).getCarid())
                            .addParams("isnew", list1.get(positon).getIsnew())
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

                                    progressBean = gson.fromJson(json, ProgressBean.class);

                                    PreferenceUtils.setString(PersonalLoanActivity.this, "userName", progressBean.getUserName());

                                    stage = progressBean.getStage();

                                    String price = list1.get(positon).getPrice();

                                    PreferenceUtils.setString(PersonalLoanActivity.this, "loanPrice", price); //贷款额

                                    PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(positon).getCarid());

                                    PreferenceUtils.setString(PersonalLoanActivity.this, "isNew", list1.get(positon).getIsnew());

                                    PreferenceUtils.setString(PersonalLoanActivity.this, "rebate", list1.get(positon).getRebate());

                                    PreferenceUtils.setString(PersonalLoanActivity.this, "Latitude", list1.get(positon).getVisits_lat());

                                    PreferenceUtils.setString(PersonalLoanActivity.this, "Longtitude", list1.get(positon).getVisits_lng());

                                    PreferenceUtils.setString(PersonalLoanActivity.this, "credit", list1.get(positon).getCredit());

                                    PreferenceUtils.setString(PersonalLoanActivity.this, "vprice", list1.get(positon).getVprice());

                                    PreferenceUtils.setString(PersonalLoanActivity.this, "flag", list1.get(positon).getFlag());

                                    PreferenceUtils.setString(PersonalLoanActivity.this, "contractId", list1.get(positon).getContract());

                                    PreferenceUtils.setString(PersonalLoanActivity.this, "rentType", list1.get(positon).getRentType());

                                    PreferenceUtils.setString(PersonalLoanActivity.this, "carLoan", "1");

                                    if (list1.get(positon).getIsmarry() != null) {

                                        PreferenceUtils.setString(PersonalLoanActivity.this, "isMarry", list1.get(positon).getIsmarry());
                                    }

                                    if (list1.get(positon).getIsguarantee() != null) {

                                        PreferenceUtils.setString(PersonalLoanActivity.this, "needCautioner", list1.get(positon).getIsguarantee());
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
                                     8 添加室内，材料
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
                                     25 添加电子合同签名照片
                                     26 添加人脸识别
                                     27 征信查询
                                     */

//                                if (carLoan.equals("1")) {

                                    hud.dismiss();

                                    if (stage == 1) {

                                        startActivity(new Intent(PersonalLoanActivity.this, StartHomeVisitActivity.class));
                                        // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 3) {

                                        startActivity(new Intent(PersonalLoanActivity.this, BaseInformationActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 4) {

                                        startActivity(new Intent(PersonalLoanActivity.this, ContactsActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 5) {

                                        startActivity(new Intent(PersonalLoanActivity.this, CautionerMessageActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 6) {

                                        startActivity(new Intent(PersonalLoanActivity.this, WifeActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 7) {

                                        startActivity(new Intent(PersonalLoanActivity.this, BankCardMessageActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 8) {

                                        startActivity(new Intent(PersonalLoanActivity.this, IndoorPicActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 9) {

                                        startActivity(new Intent(PersonalLoanActivity.this, TeamManager1Activity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 10) {

                                        startActivity(new Intent(PersonalLoanActivity.this, StopHomeVisitActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 13) {
                                        startActivity(new Intent(PersonalLoanActivity.this, UpLoadCusomerVideoActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 20) {
                                        if (list1.get(positon).getOrf() < 3) {
                                            new AlertDialog.Builder(PersonalLoanActivity.this)
                                                    .setTitle("评估师第" + list1.get(positon).getOrf() + "次驳回")
                                                    .setMessage("原因：" + progressBean.getReasons() + "\n" + "\n" + "提示：您还能修改" + (3 - list1.get(positon).getOrf()) + "次")
                                                    .setPositiveButton("去修改", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
//                                                        finish();
                                                            firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();
                                                            saveData(positon);
                                                            startActivity(new Intent(PersonalLoanActivity.this, FullInfoActivity1.class));
//                                                        overridePendingTransition(enterAnim0, exitAnim0);
                                                        }
                                                    })
                                                    .setNegativeButton("暂不修改", null)
                                                    .show()
                                                    .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
                                        } else {
                                            new AlertDialog.Builder(PersonalLoanActivity.this)
                                                    .setTitle("提示")
                                                    .setMessage("该客户已被评估师驳回3次，不能再发起贷款，请自行作废！")
                                                    .setPositiveButton("知道了", null)
                                                    .show()
                                                    .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
                                        }

                                    } else if (stage == 21) {

                                        LogUtils.e("shiwoma");

                                        MyToast.show(PersonalLoanActivity.this, progressBean.getMsg());

                                    } else if (stage == 22) {

                                        MyToast.show(PersonalLoanActivity.this, progressBean.getMsg());

                                    } else if (stage == 23) {

                                        LogUtils.e("添加室外照片");

                                        startActivity(new Intent(PersonalLoanActivity.this, OutdoorPicActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 24) {

                                        LogUtils.e("添加合同照片");
                                        startActivity(new Intent(PersonalLoanActivity.this, ContractPicActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 25) {

                                        LogUtils.e("添加签名照片");
                                        startActivity(new Intent(PersonalLoanActivity.this, CustomerDrawNameActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 26) {

                                        LogUtils.e("添加人脸识别");
                                        startActivity(new Intent(PersonalLoanActivity.this, FacePicActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 27) {

                                        LogUtils.e("征信查询");
                                        startActivity(new Intent(PersonalLoanActivity.this, ZhengxinActivity.class));
                                        // // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (stage == 0) {
                                        MyToast.show(PersonalLoanActivity.this, progressBean.getMsg());
                                    }

//                                } else {
//
//                                    hud.dismiss();
//
//                                    if (stage == 2) {
//
//                                        startActivity(new Intent(PersonalLoanActivity.this, CarEvaluateActivity2.class));
//                                        // // 设置过渡动画
//                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
//                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
//                                        overridePendingTransition(enterAnim6, exitAnim6);
//
//                                    } else if (stage == 12) {
//
//                                        startActivity(new Intent(PersonalLoanActivity.this, UpLoadCarImageActivity.class));
//                                        // // 设置过渡动画
//                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
//                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
//                                        overridePendingTransition(enterAnim6, exitAnim6);
//
//                                    }
////                                    else if (stage == 17) {
////
////                                        if (list1.get(positon).getVprice().equals("0")) {
////                                            MyToast.show(PersonalLoanActivity.this, "请等待评估师评估");
////                                        } else {
////
////                                            PreferenceUtils.setString(PersonalLoanActivity.this, "isNew", list1.get(positon).getIsnew());
////                                            PreferenceUtils.setString(PersonalLoanActivity.this, "realPrice", list1.get(positon).getSprice());
////                                            PreferenceUtils.setString(PersonalLoanActivity.this, "rebate", list1.get(positon).getRebate());
////
////                                            startActivity(new Intent(PersonalLoanActivity.this, Cost3Activity.class));
////                                            // // 设置过渡动画
////                                            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
////                                            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
////                                            overridePendingTransition(enterAnim6, exitAnim6);
////                                        }
////                                    }
//                                    else if (stage == 19) {
//
//                                        if (list1.get(positon).getVprice().equals("0")) {
//
//                                            MyToast.show(PersonalLoanActivity.this, "请等待评估师评估");
//
//                                        } else {
//
//                                            startActivity(new Intent(PersonalLoanActivity.this, UpdateCost3Activity.class));
//                                            // // 设置过渡动画
//                                            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
//                                            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
//                                            overridePendingTransition(enterAnim6, exitAnim6);
//
//                                        }
//
//                                    }
//
//                                }
                                }
                            });

                } else if (list1.get(positon).getFlag().equals("8")) {

                    hud.dismiss();

                    if (list1.get(positon).getRf() < 3) {

                        firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();
                        PreferenceUtils.setString(PersonalLoanActivity.this, "userId", list1.get(positon).getId());

                        new AlertDialog.Builder(PersonalLoanActivity.this)
                                .setTitle("风控第" + list1.get(positon).getRf() + "次驳回")
                                .setMessage("\n" + "提示：您还能修改" + (3 - list1.get(positon).getRf()) + "次")
                                .setPositiveButton("去提交", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(PersonalLoanActivity.this, TeamManager1Activity.class));
                                        // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);
                                    }
                                })
                                .setNegativeButton("去修改", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();
                                        saveData(positon);

                                        startActivity(new Intent(PersonalLoanActivity.this, FullInfoActivity1.class));

                                    }
                                })
                                .show()
                                .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));

                    } else {

                        new AlertDialog.Builder(PersonalLoanActivity.this)
                                .setTitle("提示")
                                .setMessage("该客户已被风控驳回3次，不能再发起贷款，请自行作废！")
                                .setPositiveButton("作废", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();
                                        final CircleDialog.Builder builder = new CircleDialog.Builder(PersonalLoanActivity.this);
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
                                                                .addParams("id", list1.get(positon).getId())
                                                                .addParams("tableName", "user")
                                                                .build()
                                                                .execute(new StringCallback() {
                                                                    @Override
                                                                    public void onError(Call call, Exception e, int id) {
                                                                        System.out.println("zuofei____________________>>>>>" + e);
                                                                        hud.dismiss();
                                                                        MyToast.show(PersonalLoanActivity.this, "加载失败");
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
                                                                            MyToast.show(PersonalLoanActivity.this, "服务器错误");
                                                                        }

                                                                        if (bean.getCode() == 1) {

                                                                            deletePattern(v, positon);
                                                /*initData();*/
                                                                        } else if (bean.getCode() == 0) {
                                                                            MyToast.show(PersonalLoanActivity.this, bean.getMsg());
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

                    startActivity(new Intent(PersonalLoanActivity.this, GuoCheng1Activity.class));
                    // // 设置过渡动画
                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                    overridePendingTransition(enterAnim6, exitAnim6);
                }
            }

        }
    };
    private Fangkuan fangkuanBean;
    private Fanyong fanYongBean;
    private MyFangkuanDialog myDialog;
    private MyFanyongDialog myFanyongDialog;
    PersonalLoanAdapper.OnButtonClickLisetrner9 btnlistener9 = new PersonalLoanAdapper.OnButtonClickLisetrner9() {

        @Override
        public void onButtonClick(final int positon) {
            firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();

            OkHttpUtils.post()
                    .url(Constants.URLS.BASEURL + "Loan/applyDealerRebate")
                    .addParams("userId", list1.get(positon).getId())
                    .addParams("carId", list1.get(positon).getCarid())
                    .addParams("carType", list1.get(positon).getIsnew())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            System.out.println("fanyong____________________>>>>>" + e);
                            hud.dismiss();
                            MyToast.show(PersonalLoanActivity.this, "加载失败");
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
                                    MyToast.show(PersonalLoanActivity.this, fanYongBean.getMsg());
                                }
                            } catch (JsonSyntaxException e) {
                                MyToast.show(PersonalLoanActivity.this, "服务器错误");
                            }


                        }
                    });


        }
    };
    PersonalLoanAdapper.OnButtonClickLisetrner8 btnlistener8 = new PersonalLoanAdapper.OnButtonClickLisetrner8() {

        @Override
        public void onButtonClick(final int positon) {
            firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();

            OkHttpUtils.post()
                    .url(Constants.URLS.BASEURL + "Loan/loanShow")
                    .addParams("userId", list1.get(positon).getId())
                    .addParams("carId", list1.get(positon).getCarid())
                    .addParams("carType", list1.get(positon).getIsnew())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            System.out.println("fangkuan____________________>>>>>" + e);
                            hud.dismiss();
                            MyToast.show(PersonalLoanActivity.this, "加载失败");
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
                                        new AlertDialog.Builder(PersonalLoanActivity.this)
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
                                    MyToast.show(PersonalLoanActivity.this, fangkuanBean.getMsg());
                                }
                            } catch (JsonSyntaxException e) {
                                MyToast.show(PersonalLoanActivity.this, "服务器错误");
                            }


                        }
                    });


        }
    };
    PersonalLoanAdapper.OnButtonClickLisetrner7 btnlistener7 = new PersonalLoanAdapper.OnButtonClickLisetrner7() {

        @Override
        public void onButtonClick(final int positon) {
            if (list1.get(positon).getReconsider().equals("0")) {
                firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();
                carId = list1.get(positon).getCarid();
                final CircleDialog.Builder builder = new CircleDialog.Builder(PersonalLoanActivity.this);
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
                                    MyToast.show(PersonalLoanActivity.this, "请填写复议原因！");
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
                                                    MyToast.show(PersonalLoanActivity.this, "加载失败");
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
                                                        MyToast.show(PersonalLoanActivity.this, "服务器错误");
                                                    }

                                                    if (bean.getCode() == 1) {
                                                        adapper.notifyDataSetChanged();
                                                        MyToast.show(PersonalLoanActivity.this, "复议成功");
                                                        initData();

                                                    } else if (bean.getCode() == 0) {
                                                        MyToast.show(PersonalLoanActivity.this, bean.getMsg());
                                                    }

                                                    hud.dismiss();
                                                }
                                            });
                                }


                            }
                        })

                        .setNegative("取消", null)
                        .show();
            } else if (list1.get(positon).getReconsider().equals("-1")) {
                MyToast.show(PersonalLoanActivity.this, "复议被驳回！");
                new AlertDialog.Builder(PersonalLoanActivity.this)
                        .setTitle("复议驳回原因")
                        .setMessage(list1.get(positon).getDismissal())
                        .setNegativeButton("知道了", null)
                        .show()
                        .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_loan);
        ButterKnife.inject(this);
        isNew = "0";
        init();
    }

    protected void init() {

        bitmap = Resource2Bitmap.readBitMap(this, R.drawable.jiazai);
        bmdrawable = new BitmapDrawable(bitmap);
        rlPerson.setBackground(bmdrawable);

        ivDaipinggu.setSelected(true);

        addCar.setText("添加车辆");

        lvPersonaloan = (ListView) findViewById(R.id.lv_personaloan);

        hud = KProgressHUD.create(PersonalLoanActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setWindowColor(Color.GRAY)
                .show();

        saleID = PreferenceUtils.getString(this, "saleID");

        lvPersonaloan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                MyToast.show(PersonalLoanActivity.this, "haha");

                LogUtils.e("haha");

                if (list1.get(position).getFlag().equals("0") || list1.get(position).getFlag().equals("8")
                        || list1.get(position).getFlag().equals("-1") || list1.get(position).getFlag().equals("-999")) {

                    firstVisiblePosition = lvPersonaloan.getFirstVisiblePosition();

                    saveData(position);


                    startActivity(new Intent(PersonalLoanActivity.this, FullInfoActivity1.class));
                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                    overridePendingTransition(enterAnim6, exitAnim6);

                } else {
                    MyToast.show(PersonalLoanActivity.this, "该用户已发起贷款，不能修改");
                }

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                i++;
                initData1(i+"");
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                firstVisiblePosition = 0;
                initData();
            }
        });
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//
//            }
//        });

        myDialog = new MyFangkuanDialog(this);
        myFanyongDialog = new MyFanyongDialog(this);
    }

    private void saveData(int position) {
        PreferenceUtils.setString(PersonalLoanActivity.this, "carLoan", "1");
        PreferenceUtils.setString(PersonalLoanActivity.this, "loanPrice", list1.get(position).getPrice()); //贷款额
        PreferenceUtils.setString(PersonalLoanActivity.this, "userId", list1.get(position).getId());
        PreferenceUtils.setString(PersonalLoanActivity.this, "carId", list1.get(position).getCarid());
        PreferenceUtils.setString(PersonalLoanActivity.this, "isNew", list1.get(position).getIsnew());
        PreferenceUtils.setString(PersonalLoanActivity.this, "flag", list1.get(position).getFlag());
        PreferenceUtils.setString(PersonalLoanActivity.this, "vprice", list1.get(position).getVprice());
        PreferenceUtils.setInt(PersonalLoanActivity.this, "carpicchange", list1.get(position).getCarpicchange());
        PreferenceUtils.setInt(PersonalLoanActivity.this, "carinfochange", list1.get(position).getCarinfochange());
        PreferenceUtils.setString(PersonalLoanActivity.this, "estate", list1.get(position).getEstate());
        PreferenceUtils.setString(PersonalLoanActivity.this, "carpicFlag", list1.get(position).getCarpicFlag());
        PreferenceUtils.setString(PersonalLoanActivity.this, "costFlag", list1.get(position).getCostFlag());
        PreferenceUtils.setString(PersonalLoanActivity.this, "contractId", list1.get(position).getContract());
        PreferenceUtils.setString(PersonalLoanActivity.this, "mid", list1.get(position).getMid());
        PreferenceUtils.setString(PersonalLoanActivity.this, "rentType", list1.get(position).getRentType());
        PreferenceUtils.setString(PersonalLoanActivity.this, "carLoan", "1");

    }

    private void initData1(String x) {
        LogUtils.e(saleID + "==========");

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Dealer/carlist")
                .addParams("Id", saleID)
                .addParams("status", isNew)
                .addParams("page", x)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("PersonalLoanActivity____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(PersonalLoanActivity.this, "加载失败");
//                        refreshLayout.setRefreshing(false)
                        if (refreshLayout.isLoading()) {
                            refreshLayout.finishLoadmore();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("PersonalLoanActivity----------------->>>>>>" + response+i+"");
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        bean = null;
                        try {
                            bean = gson.fromJson(json, PersonalLoanURLBean.class);
                        } catch (JsonSyntaxException e) {
                            LogUtils.e("PersonalLoanActivity----------------->>>>>>" + e.toString());


                            if (refreshLayout.isLoading()) {
                                refreshLayout.finishLoadmore();
                            }
                        }
                        if (bean.getList().size()==0||bean.getList()!=null) {

                            int role = bean.getRole();

                            PreferenceUtils.setInt(PersonalLoanActivity.this, "role", role);
                                    list1.addAll(bean.getList());
                            if (bean.getCode() == 1) {

                                adapper = new PersonalLoanAdapper(list1, PersonalLoanActivity.this, lisetener, btnlistener3, btnlistener4, btnlistener5, btnlistener6,
                                        btnlistener7, btnlistener8, btnlistener9, btnlistener10, btnlistener11, btnlistener12, btnlistener13);
                                lvPersonaloan.setAdapter(adapper);
                                adapper.notifyDataSetChanged();

//                                lvPersonaloan.setSelection(firstVisiblePosition);


                                if (refreshLayout.isLoading()) {
                                    refreshLayout.finishLoadmore();
                                }
                            } else {

                                if (refreshLayout.isLoading()) {
                                    refreshLayout.finishLoadmore();
                                }

                                MyToast.show(PersonalLoanActivity.this, "获取失败，code=0");
                            }
                        }else
                        {
                            i=1;
                            MyToast.show(PersonalLoanActivity.this, "没有加载更多" );
                        }
                    }
                });
    }
    private void initData() {
        LogUtils.e(saleID + "==========");

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Dealer/carlist")
                .addParams("Id", saleID)
                .addParams("status", isNew)
                .addParams("page", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("PersonalLoanActivity____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(PersonalLoanActivity.this, "加载失败");
//                        refreshLayout.setRefreshing(false);
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.finishRefresh();
                        }
                        if (refreshLayout.isLoading()) {
                            refreshLayout.finishLoadmore();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("PersonalLoanActivity----------------->>>>>>" + response+i+"");
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        bean = null;
                        try {
                            bean = gson.fromJson(json, PersonalLoanURLBean.class);
                        } catch (JsonSyntaxException e) {
                            LogUtils.e("PersonalLoanActivity----------------->>>>>>" + e.toString());

                            if (refreshLayout.isRefreshing()) {
                                refreshLayout.finishRefresh();
                            }
                            if (refreshLayout.isLoading()) {
                                refreshLayout.finishLoadmore();
                            }
                        }

                        if (bean != null) {

                            int role = bean.getRole();

                            PreferenceUtils.setInt(PersonalLoanActivity.this, "role", role);
                            if (i!=1){
                                //如果不是第一次刷新
                                for (int j = 0; j < bean.getList().size(); j++) {
                                    list1.add(bean.getList().get(j));
                                }

                            }
                            list1 = bean.getList();
//                            LogUtils.i(list1.size() + "---------------->>>>>>>>");

                            if (bean.getCode() == 1) {

//                                startTranslationNoFullWindow();

                                if (list1 == null || list1.size() == 0) {
                                    rlPerson.setBackground(bmdrawable);
                                } else {
                                    rlPerson.setBackground(null);
                                }
                                adapper = new PersonalLoanAdapper(list1, PersonalLoanActivity.this, lisetener, btnlistener3, btnlistener4, btnlistener5, btnlistener6,
                                        btnlistener7, btnlistener8, btnlistener9, btnlistener10, btnlistener11, btnlistener12, btnlistener13);
                                lvPersonaloan.setAdapter(adapper);
                                adapper.notifyDataSetChanged();

                                lvPersonaloan.setSelection(firstVisiblePosition);

                                if (refreshLayout.isRefreshing()) {

                                    refreshLayout.finishRefresh();

                                }
                                if (refreshLayout.isLoading()) {
                                    refreshLayout.finishLoadmore();
                                }
                            } else {

                                if (refreshLayout.isRefreshing()) {

                                    refreshLayout.finishRefresh();

                                }
                                if (refreshLayout.isLoading()) {
                                    refreshLayout.finishLoadmore();
                                }

                                MyToast.show(PersonalLoanActivity.this, "获取失败，code=0");
                            }
                        }
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    public void startTranslationNoFullWindow() {
        helper = new BaseViewHelper
                .Builder(this)
                .isFullWindow(true)//是否全屏显示
                .isShowTransition(false)//是否显示过渡动画
                .setDimColor(Color.WHITE)//遮罩颜色
                .setDimAlpha(150)//遮罩透明度
                .setTranslationY(200)
                .create();//开始动画
    }

    @OnClick({R.id.iv_back, R.id.iv_daipinggu, R.id.iv_yipinggu, R.id.add_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (helper != null && helper.isShowing()) {
                    helper.backActivity(this);
                }
                finish();
//                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.iv_daipinggu:
                hud.show();
                isNew = "0";
                ivDaipinggu.setSelected(true);
                ivYipinggu.setSelected(false);
                firstVisiblePosition = 0;
                initData();
                break;
            case R.id.iv_yipinggu:
                hud.show();
                isNew = "1";
                ivDaipinggu.setSelected(false);
                ivYipinggu.setSelected(true);
                firstVisiblePosition = 0;
                initData();
                break;
            case R.id.add_car:
                PreferenceUtils.setString(this, "lala", "1");
                Intent intent = new Intent(this, CarTypeActivity.class);
                startActivity(intent);
                // 设置过渡动画
                int enterAnim6 = R.anim.next_enter;// 进入// 的activity对应的动画资源
                int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim6, exitAnim6);
                break;
        }
    }

    private void ShowZuofei(final int positon, final View view) {
        carId = list1.get(positon).getCarid();
        final CircleDialog.Builder builder = new CircleDialog.Builder(PersonalLoanActivity.this);
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
                            MyToast.show(PersonalLoanActivity.this, "请填写作废原因！");
                            return;
                        } else {
                            hud.show();

                            OkHttpUtils.post()
                                    .url(Constants.URLS.BASEURL + "Dealer/tovoidCar")
                                    .addParams("remark", text)
                                    .addParams("carId", carId)
                                    .addParams("status", list1.get(positon).getIsnew())
                                    .addParams("saleId", saleID)
                                    .build()
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e, int id) {
                                            System.out.println("zuofei____________________>>>>>" + e);
                                            hud.dismiss();
                                            MyToast.show(PersonalLoanActivity.this, "加载失败");
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
                                                MyToast.show(PersonalLoanActivity.this, "服务器错误");
                                            }

                                            if (bean.getCode() == 1) {

                                                deletePattern(view, positon);
                                            /*initData();*/

                                            } else if (bean.getCode() == 0) {
                                                MyToast.show(PersonalLoanActivity.this, bean.getMsg());
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

    private void deletePattern(final View view, final int position) {

        Animation.AnimationListener al = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                list1.remove(position);
                PersonalLoanAdapper.ViewHolder holder = (PersonalLoanAdapper.ViewHolder) view.getTag();
                holder.needInflate = true;
                adapper.notifyDataSetChanged();
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
            public boolean willChangeBounds() {
                return true;
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1.0f) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = originHeight - (int) (originHeight * interpolatedTime);
                    view.requestLayout();
                }
            }
        };
        if (al != null) {
            animation.setAnimationListener(al);
        }
        animation.setDuration(300);
        view.startAnimation(animation);
    }

    @Override
    public void onBackPressed() {

        if (helper != null && helper.isShowing()) {
            helper.backActivity(this);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        loading.setVisibility(View.GONE);
        hud.show();
        initData();
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
            LayoutInflater inflater = getLayoutInflater();
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

            final AlertDialog dialog = new AlertDialog.Builder(context).setView(layout).setPositiveButton("放款", null)
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
                            if (etDianfuNum.getText().toString().equals("")) {
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
                                        MyToast.show(PersonalLoanActivity.this, "加载失败");
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
                                                initData();
                                                MyToast.show(PersonalLoanActivity.this, "提交成功");

                                            } else if (fangkuanBean.getCode() == 0) {
                                                dialog.dismiss();
                                                MyToast.show(PersonalLoanActivity.this, fangkuanBean.getMsg());
                                            }
                                        } catch (JsonSyntaxException e) {
                                            dialog.dismiss();
                                            MyToast.show(PersonalLoanActivity.this, "服务器错误");
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
            LayoutInflater inflater = getLayoutInflater();
            final View layout = inflater.inflate(R.layout.dialog_fanyong, null);//获取自定义布局

//            builder.setView(layout);


            ff_zhanghao = (TextView) layout.findViewById(R.id.ff_zhanghao);
            ff_bili = (TextView) layout.findViewById(R.id.ff_bili);
            ff_daikuane = (TextView) layout.findViewById(R.id.ff_daikuane);
            ff_fanyong = (TextView) layout.findViewById(R.id.ff_fanyong);
            ff_name = (TextView) layout.findViewById(R.id.ff_name);
            tv_gps_price = (TextView) layout.findViewById(R.id.tv_gps_price);

            final AlertDialog dialog = new AlertDialog.Builder(context).setView(layout).setPositiveButton("返佣", null)
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
                                    MyToast.show(PersonalLoanActivity.this, "加载失败");
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    LogUtils.e("fangkuan----------------->>>>>>" + response);
                                    String json = response;
                                    Gson gson = new Gson();
                                    fanYongBean = null;

                                    try {
                                        fanYongBean = gson.fromJson(json, Fanyong.class);
                                        if (fanYongBean.getCode() == 1) {

                                            dialog.dismiss();
                                            initData();
                                            MyToast.show(PersonalLoanActivity.this, "提交成功");

                                        } else if (fanYongBean.getCode() == 0) {
                                            MyToast.show(PersonalLoanActivity.this, fanYongBean.getMsg());
                                        }
                                    } catch (JsonSyntaxException e) {
                                        MyToast.show(PersonalLoanActivity.this, "服务器错误");
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