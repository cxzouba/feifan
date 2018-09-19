package com.feifandaiyu.feifan.activity.personalloan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CarCostBean;
import com.feifandaiyu.feifan.bean.PostCostBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.popupWindow.BottomPopupOption;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.NumberUtil;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.SmallNumberUtils;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

public class Cost3Activity extends BaseActivity implements TextWatcher {

    @InjectView(R.id.rb_zhizu)
    RadioButton rbZhizu;
    @InjectView(R.id.rb_huizu)
    RadioButton rbHuizu;
    @InjectView(R.id.tv_fapiao)
    TextView tvFapiao;
    @InjectView(R.id.tv_vprice)
    TextView tvVprice;
    @InjectView(R.id.ll_vprice)
    LinearLayout llVprice;
    @InjectView(R.id.view1)
    View view1;
    @InjectView(R.id.view2)
    View view2;
    @InjectView(R.id.et_daikuane)
    PowerfulEditText etDaikuane;
    @InjectView(R.id.tv_gps_price)
    TextView tvGpsPrice;
    @InjectView(R.id.et_shangxian)
    PowerfulEditText etShangxian;
    @InjectView(R.id.et_qiangxian)
    PowerfulEditText etQiangxian;
    @InjectView(R.id.et_gouzhishui)
    PowerfulEditText etGouzhishui;
    @InjectView(R.id.tv_qishu)
    TextView tvQishu;
    @InjectView(R.id.tv_lilv)
    TextView tvLilv;
    @InjectView(R.id.tv_lixicha)
    TextView tvLixicha;
    @InjectView(R.id.tv_yuegong)
    TextView tvYuegong;
    @InjectView(R.id.tv_daikuane)
    TextView tvDaikuane;
    private String creditType;
    private String isNew;
    private BottomPopupOption bottomPopupOption;
    private InputMethodManager imm;
    private KProgressHUD hud;
    private String carLoan;

    //    private double rebate;
    private CarCostBean bean;
    private String interest_0;
    private String interest_1;
    private String carId;
    private double d;
    private String lirun;
    private String bank_interest;
    private String credit;
    private double rebate;
    private double lixicha;

    @Override
    protected int getContentView() {
        return R.layout.activity_cost1;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("费用(车抵贷)");
        showNext(true);

        etGouzhishui.setText("0");
//        rbZhizu.setChecked(true);

        hud = KProgressHUD.create(Cost3Activity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        TextView next = (TextView) findViewById(R.id.tv_next);

        //车商返点
//        rebate = Double.parseDouble(PreferenceUtils.getString(this, "rebate","0"));
        carId = PreferenceUtils.getString(this, "carId");
        isNew = PreferenceUtils.getString(this, "isNew");
//        String realPrice = PreferenceUtils.getString(this, "realPrice");
//        credit = PreferenceUtils.getString(this, "credit");
//        tvFapiao.setText(realPrice);

        next.setEnabled(true);

        tvLilv.addTextChangedListener(this);
//        etFapiao.addTextChangedListener(this);
        etShangxian.addTextChangedListener(this);
        etQiangxian.addTextChangedListener(this);
        tvGpsPrice.addTextChangedListener(this);
        tvQishu.addTextChangedListener(this);
        etDaikuane.addTextChangedListener(this);

        initData();
    }

    private void initData() {

        System.out.println("carlon=" + carLoan);
        System.out.println("cartype=" + isNew);

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Login/interest")
                .addParams("carloan", "2")
                .addParams("cartype", "2")
                .addParams("carId", carId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("initcost____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(Cost3Activity.this, "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("initcost----------------->>>>>>" + response);
                        String json = response;
                        Gson gson = new Gson();
                        bean = null;
                        try {
                            bean = gson.fromJson(json, CarCostBean.class);

                        } catch (JsonSyntaxException e) {
                            hud.dismiss();
                            MyToast.show(Cost3Activity.this, "联网失败");
                        }


                        if (bean.getCode() == 1) {
                            if (bean.getRebate().equals("0")) {
                                rebate = 0;
                            } else {
                                rebate = NumberUtil.baifenshuToXiaoshu(bean.getRebate());
                            }
                            tvFapiao.setText(bean.getSprice());
                            tvVprice.setText(bean.getVprice() == null ? "0" : bean.getVprice());
                        } else if (bean.getCode() == 0) {

                            MyToast.show(Cost3Activity.this, bean.getMsg());

                        }

                        hud.dismiss();

                    }
                });

    }

    @OnClick({R.id.tv_gps_price, R.id.tv_next, R.id.iv_back, R.id.tv_qishu, R.id.tv_lilv})
    public void onViewClicked(View view) {
        bottomPopupOption = new BottomPopupOption(Cost3Activity.this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {
            case R.id.tv_gps_price:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                bottomPopupOption.setItemText("1988", "2988");
                bottomPopupOption.showPopupWindow();
                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                                                           @Override
                                                           public void onItemClick(int position) {
                                                               if (position == 0) {
                                                                   tvGpsPrice.setText("1988");
                                                                   bottomPopupOption.dismiss();
                                                               } else if (position == 1) {
                                                                   tvGpsPrice.setText("2988");
                                                                   bottomPopupOption.dismiss();
                                                               }

                                                           }
                                                       }

                );
                break;
            case R.id.tv_next:

//                double v = Double.parseDouble(tvDaikuane.getText().toString()) * (Double.parseDouble(tvLilv.getText().toString()) - Double.parseDouble(bank_interest) - rebate);
//                (Double.parseDouble(tvLilv.getText().toString()) - Double.parseDouble(bank_interest) - rebate)
//                System.out.println("daikuane=" + Double.parseDouble(tvDaikuane.getText().toString()));
//                System.out.println("lilv=" + SmallNumberUtils.toMinus(Double.parseDouble(tvLilv.getText().toString()), Double.parseDouble(bank_interest), rebate));
//                System.out.println("lirun=" + s);

                if (!StringUtils.isEmpty(tvFapiao.getText().toString())
                        && !StringUtils.isEmpty(etQiangxian.getText().toString())
                        && !StringUtils.isEmpty(etShangxian.getText().toString())
                        && !StringUtils.isEmpty(tvLilv.getText().toString())
                        && !StringUtils.isEmpty(tvDaikuane.getText().toString())
                        && !StringUtils.isEmpty(tvQishu.getText().toString())
                        && !StringUtils.isEmpty(etGouzhishui.getText().toString())
                        && !StringUtils.isEmpty(tvGpsPrice.getText().toString())
                        && !StringUtils.isEmpty(etDaikuane.getText().toString())
                        ) {

                    if (rbZhizu.isChecked()) {
                        if (d <= Double.parseDouble(tvVprice.getText().toString()) * 0.9) {
                            hud.show();
                            postData();
                        } else {
                            MyToast.show(Cost3Activity.this, "选择直租时，贷款额不得大于发票价的90%");
                        }
                    } else if (rbHuizu.isChecked()) {
                        if (d <= Double.parseDouble(tvVprice.getText().toString()) * 0.85) {
                            hud.show();
                            postData();
                        } else {
                            MyToast.show(Cost3Activity.this, "选择回租时，贷款额不得大于发票价的85%");
                        }
                    } else {
                        MyToast.show(Cost3Activity.this, "请选择直租或回租");
                    }

                } else {
                    MyToast.show(Cost3Activity.this, "请填写全部信息后点击下一步");
                }
                break;

            case R.id.iv_back:
                new AlertDialog.Builder(this)
                        .setMessage(StringCreateUtils.createString())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                overridePendingTransition(enterAnim0, exitAnim0);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;

            case R.id.tv_qishu:

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                bottomPopupOption.setItemText("3", "12", "24", "36");
                bottomPopupOption.showPopupWindow();
                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 0) {
//                            interest_0 = bean.getPeriods3().get(0).getInterest();
//                            interest_1 = bean.getPeriods3().get(0).getInterest();
//                            bank_interest = bean.getPeriods3().get(0).getBank_interest();
//                            tvLilv.setText(interest_0);
                            tvQishu.setText("3");
                            tvLilv.setText("");
                            bottomPopupOption.dismiss();
                        } else if (position == 1) {
//                            interest_0 = bean.getPeriods12().get(0).getInterest();
//                            interest_1 = bean.getPeriods12().get(0).getInterest();
//                            bank_interest = bean.getPeriods12().get(0).getBank_interest();
//                            tvLilv.setText(interest_0);
                            tvQishu.setText("12");
                            tvLilv.setText("");
                            bottomPopupOption.dismiss();
                        } else if (position == 2) {
//                            interest_0 = bean.getPeriods24().get(0).getInterest();
//                            interest_1 = bean.getPeriods24().get(1).getInterest();
//                            bank_interest = bean.getPeriods24().get(0).getBank_interest();
//                            tvLilv.setText(interest_0);
                            tvQishu.setText("24");
                            tvLilv.setText("");
                            bottomPopupOption.dismiss();
                        } else if (position == 3) {
//                            interest_0 = bean.getPeriods36().get(0).getInterest();
//                            interest_1 = bean.getPeriods36().get(1).getInterest();
//                            bank_interest = bean.getPeriods36().get(0).getBank_interest();
//                            tvLilv.setText(interest_0);
                            tvQishu.setText("36");
                            tvLilv.setText("");
                            bottomPopupOption.dismiss();
                        }
                    }
                });

                break;

            case R.id.tv_lilv:
                if (tvQishu.getText().toString() != "") {

                    if (tvQishu.getText().toString().equals("3")) {
                        interest_0 = bean.getPeriods3().get(0).getInterest();
                        interest_1 = bean.getPeriods3().get(1).getInterest();
//                        bank_interest = bean.getPeriods12().get(0).getBank_interest();
                    } else if (tvQishu.getText().toString().equals("12")) {
                        interest_0 = bean.getPeriods12().get(0).getInterest();
                        interest_1 = bean.getPeriods12().get(1).getInterest();
//                        bank_interest = bean.getPeriods12().get(0).getBank_interest();
                    } else if (tvQishu.getText().toString().equals("24")) {
                        interest_0 = bean.getPeriods24().get(0).getInterest();
                        interest_1 = bean.getPeriods24().get(1).getInterest();
//                        bank_interest = bean.getPeriods24().get(0).getBank_interest();
                    } else if (tvQishu.getText().toString().equals("36")) {
                        interest_0 = bean.getPeriods36().get(0).getInterest();
                        interest_1 = bean.getPeriods36().get(1).getInterest();
//                        bank_interest = bean.getPeriods36().get(0).getBank_interest();
                    }

                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                    bottomPopupOption.setItemText(interest_0, interest_1);
                    bottomPopupOption.showPopupWindow();
                    bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {

                        @Override
                        public void onItemClick(int position) {
                            if (position == 0) {
                                if (tvQishu.getText().toString().equals("3")) {
                                    bank_interest = bean.getPeriods3().get(0).getBank_interest();
                                } else if (tvQishu.getText().toString().equals("12")) {
                                    bank_interest = bean.getPeriods12().get(0).getBank_interest();
                                } else if (tvQishu.getText().toString().equals("24")) {
                                    bank_interest = bean.getPeriods24().get(0).getBank_interest();
                                } else if (tvQishu.getText().toString().equals("36")) {
                                    bank_interest = bean.getPeriods36().get(0).getBank_interest();
                                }
                                tvLilv.setText(interest_0);
                                LogUtils.e("yinhang1", bank_interest + "=");
                                bottomPopupOption.dismiss();
                            } else if (position == 1) {
                                if (tvQishu.getText().toString().equals("3")) {
                                    bank_interest = bean.getPeriods3().get(1).getBank_interest();
                                } else if (tvQishu.getText().toString().equals("12")) {
                                    bank_interest = bean.getPeriods12().get(1).getBank_interest();
                                } else if (tvQishu.getText().toString().equals("24")) {
                                    bank_interest = bean.getPeriods24().get(1).getBank_interest();
                                } else if (tvQishu.getText().toString().equals("36")) {
                                    bank_interest = bean.getPeriods36().get(1).getBank_interest();
                                }
                                tvLilv.setText(interest_1);
                                LogUtils.e("yinhang2", bank_interest + "=");
                                bottomPopupOption.dismiss();
                            }
                        }
                    });
                } else {

                    MyToast.show(Cost3Activity.this, "请先选择贷款期数");

                }
                break;

            default:

        }
    }

    private void postData() {

//        String liru = SmallNumberUtils.toReal(Double.parseDouble(etDaikuane.getText().toString()),
//                SmallNumberUtils.toMinus(Double.parseDouble(interest_0), Double.parseDouble(bank_interest))
//                        * Double.parseDouble(tvQishu.getText().toString().equals("") ? "0" : tvQishu.getText().toString()));

        double lirun = SmallNumberUtils.toMinus(lixicha, Double.parseDouble(bean.getGps()), Double.parseDouble(SmallNumberUtils.toReal(Double.parseDouble(etDaikuane.getText().toString()), rebate)));

        System.out.println("liren=" + lirun);
        System.out.println("Daikuane=" + Double.parseDouble(etDaikuane.getText().toString()));
        System.out.println("tvLilv=" + interest_0);
        System.out.println("bank_interest=" + Double.parseDouble(bank_interest));
//        System.out.println("rebate=" + rebate);

        post()
                .url(Constants.URLS.BASEURL + "Login/usedInterest")
                .addParams("carId", carId)
                .addParams("loanPrice", tvDaikuane.getText().toString().trim())
                .addParams("invoicePrice", etDaikuane.getText().toString().trim())
                .addParams("gpsCost", tvGpsPrice.getText().toString().trim())
                .addParams("compulsory", etQiangxian.getText().toString().trim())
                .addParams("commercial", etShangxian.getText().toString().trim())
                .addParams("interest", tvLilv.getText().toString())
                .addParams("periods", tvQishu.getText().toString().trim())
                .addParams("profit", NumberUtil.toReal(String.valueOf(lirun)))
                .addParams("bankInterest", bank_interest)
                .addParams("rentType", rbZhizu.isChecked() ? "直租" : "回租")
                .addParams("emtotal", tvYuegong.getText().toString())
                .addParams("interest_total", tvLixicha.getText().toString().trim())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        System.out.println("postcost____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(Cost3Activity.this, "联网失败");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("postcost----------------->>>>>>" + response);
                        String json = response;
                        Gson gson = new Gson();

                        PostCostBean postCostBean = null;

                        try {

                            postCostBean = gson.fromJson(json, PostCostBean.class);

                        } catch (JsonSyntaxException e) {
                            hud.dismiss();
                            MyToast.show(Cost3Activity.this, "联网失败");
                            return;
                        }

                        if (postCostBean.getCode() == 1) {

                            PreferenceUtils.setString(Cost3Activity.this, "loanPrice", tvDaikuane.getText().toString());

                            startActivity(new Intent(Cost3Activity.this, CustomeReport.class));

                            finish();
                            // // 设置过渡动画
                            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                            overridePendingTransition(enterAnim6, exitAnim6);
                        } else if (postCostBean.getCode() == 0) {
                            MyToast.show(Cost3Activity.this, postCostBean.getMsg());
                        }

                        hud.dismiss();

                    }
                });

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        try {

//            LogUtils.e(etFapiao.getText().toString().equals("") ? "0" : etFapiao.getText().toString());
//            LogUtils.e(Double.parseDouble(etFapiao.getText().toString().equals("") ? "0" : etFapiao.getText().toString()) * 0.09945 + "");
            LogUtils.e("yinhang3", bank_interest + "=");

//            tvGouzhishui.setText(String.valueOf((Double.parseDouble(etFapiao.getText().toString().equals("") ? "0" : etFapiao.getText().toString()) * 9945) / 100000));
//            lixicha = NumberUtil.getNumber("" + (SmallNumberUtils.toMinus(Double.parseDouble(tvLilv.getText().toString().equals("") ? "0" : tvLilv.getText().toString().equals(SmallNumberUtils.toReal(Double.parseDouble(interest_0), 100) + "%") ? interest_0 : interest_1),
//                    Double.parseDouble(bank_interest == null ? "0" : bank_interest)))
//                    * Double.parseDouble(etDaikuane.getText().toString().equals("") ? "0" : etDaikuane.getText().toString())
//                    * Double.parseDouble(tvQishu.getText().toString().equals("") ? "0" : tvQishu.getText().toString()));

//            lixicha = NumberUtil.getNumber("" + SmallNumberUtils.toReal((SmallNumberUtils.toMinus(Double.parseDouble(tvLilv.getText().toString().equals("") ? "0" : tvLilv.getText().toString().equals(SmallNumberUtils.toReal(Double.parseDouble(interest_0), 100) + "%") ? interest_0 : interest_1),
//                    Double.parseDouble(bank_interest == null ? "0" : bank_interest))),Double.parseDouble(etDaikuane.getText().toString().equals("") ? "0" : etDaikuane.getText().toString()),
//                    Double.parseDouble(tvQishu.getText().toString().equals("") ? "0" : tvQishu.getText().toString())));

            lixicha = NumberUtil.getNumber("" + SmallNumberUtils.toReal((SmallNumberUtils.toMinus(Double.parseDouble(tvLilv.getText().toString().equals("") ? "0" : tvLilv.getText().toString()),
                    Double.parseDouble(bank_interest == null ? "0" : bank_interest))),
                    Double.parseDouble(etDaikuane.getText().toString().equals("") ? "0" : etDaikuane.getText().toString())
                    , Double.parseDouble(tvQishu.getText().toString().equals("") ? "0" : tvQishu.getText().toString())));

            tvLixicha.setText(NumberUtil.toReal(lixicha + ""));

//            LogUtils.e("haha=" + "" +);

            double shiji = SmallNumberUtils.toMinus(Double.parseDouble(etDaikuane.getText().toString().equals("") ? "0" : etDaikuane.getText().toString()),
                    lixicha,
                    Double.parseDouble(tvGpsPrice.getText().toString().equals("") ? "0" : tvGpsPrice.getText().toString()),
                    Double.parseDouble(etShangxian.getText().toString().equals("") ? "0" : etShangxian.getText().toString()),
                    Double.parseDouble(etQiangxian.getText().toString().equals("") ? "0" : etQiangxian.getText().toString()));

            tvDaikuane.setText(NumberUtil.toReal(String.valueOf(shiji)));

            LogUtils.e("shiji" + shiji);
            LogUtils.e("lixicha" + lixicha);

            if (!etDaikuane.getText().toString().equals("") && !tvQishu.getText().toString().equals("")) {

                tvYuegong.setText(NumberUtil.getPerMonthPrincipalInterest(Double.parseDouble(etDaikuane.getText().toString()), 0.14, Integer.parseInt(tvQishu.getText().toString())) + "");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            MyToast.show(Cost3Activity.this, "输入有误");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

}