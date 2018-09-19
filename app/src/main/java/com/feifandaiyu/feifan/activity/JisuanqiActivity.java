package com.feifandaiyu.feifan.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CarCostBean;
import com.feifandaiyu.feifan.bean.CostBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.popupWindow.BottomPopupOption;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.NumberUtil;
import com.feifandaiyu.feifan.utils.SmallNumberUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

public class JisuanqiActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, TextWatcher {


    @InjectView(R.id.rb_new)
    RadioButton rbNew;
    @InjectView(R.id.rb_old)
    RadioButton rbOld;
    @InjectView(R.id.rb_jiaoyi)
    RadioButton rbJiaoyi;
    @InjectView(R.id.rb_chedi)
    RadioButton rbChedi;
    @InjectView(R.id.et_fapiao)
    PowerfulEditText etDaikuane;
    @InjectView(R.id.tv_gps_price)
    TextView tvGpsPrice;
    @InjectView(R.id.et_shangxian)
    PowerfulEditText etShangxian;
    @InjectView(R.id.et_qiangxian)
    PowerfulEditText etQiangxian;
    @InjectView(R.id.et_gouzhishui)
    PowerfulEditText tvGouzhishui;
    @InjectView(R.id.tv_qishu)
    TextView tvQishu;
    @InjectView(R.id.tv_lilv)
    TextView tvLilv;
    @InjectView(R.id.tv_daikuane)
    TextView tvDaikuane;
    @InjectView(R.id.tv_lixicha)
    TextView tvLixicha;
    @InjectView(R.id.tv_yuegong)
    TextView tvYuegong;
    private String cartype;
    private String carloan;
    private CarCostBean carloanBean;
    private KProgressHUD hud;
    private CostBean bean;
    private BottomPopupOption bottomPopupOption;
    private InputMethodManager imm;
    private String interest_0;
    private String bank_interest;
    private String interest_1;
    private double d;
    private double lixicha;

    @Override
    protected int getContentView() {
        return R.layout.activity_cost2;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

        setTitle("计算器");
        showNext(false);
        showBack(true);

        rbNew.setChecked(true);
        rbJiaoyi.setChecked(true);

        hud = KProgressHUD.create(JisuanqiActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        rbNew.setOnCheckedChangeListener(this);
//        rbOld.setOnCheckedChangeListener(this);
        rbJiaoyi.setOnCheckedChangeListener(this);
//        rbChedi.setOnCheckedChangeListener(this);

        tvLilv.addTextChangedListener(this);
        etDaikuane.addTextChangedListener(this);
        etShangxian.addTextChangedListener(this);
        etQiangxian.addTextChangedListener(this);
        tvGpsPrice.addTextChangedListener(this);
        tvGouzhishui.addTextChangedListener(this);
        tvQishu.addTextChangedListener(this);

        ImageView back = (ImageView) findViewById(R.id.iv_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        initData();

    }


    @OnClick({R.id.tv_gps_price, R.id.tv_qishu, R.id.tv_lilv})
    public void onViewClicked(View view) {
        bottomPopupOption = new BottomPopupOption(JisuanqiActivity.this);
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
                });
                break;
            case R.id.tv_qishu:
                if (rbJiaoyi.isChecked() || rbNew.isChecked()) {
                    tvLilv.setText("");

                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                    bottomPopupOption.setItemText("12", "24", "36");
                    bottomPopupOption.showPopupWindow();
                    bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            if (position == 0) {
                                tvQishu.setText("12");
                                bottomPopupOption.dismiss();
                            } else if (position == 1) {
                                tvQishu.setText("24");
                                bottomPopupOption.dismiss();
                            } else if (position == 2) {
                                tvQishu.setText("36");
                                bottomPopupOption.dismiss();
                            }
                        }
                    });

                } else {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                    bottomPopupOption.setItemText("3", "12", "24", "36");
                    bottomPopupOption.showPopupWindow();
                    bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            if (position == 0) {
                                tvQishu.setText("3");
                                tvLilv.setText("");
                                bottomPopupOption.dismiss();
                            } else if (position == 1) {
                                tvQishu.setText("12");
                                tvLilv.setText("");
                                bottomPopupOption.dismiss();
                            } else if (position == 2) {
                                tvQishu.setText("24");
                                tvLilv.setText("");
                                bottomPopupOption.dismiss();
                            } else if (position == 3) {
                                tvQishu.setText("36");
                                tvLilv.setText("");
                                bottomPopupOption.dismiss();
                            }
                        }
                    });
                }
                break;
            case R.id.tv_lilv:

                if (!tvQishu.getText().toString().equals("")){
                    if (rbJiaoyi.isChecked() || rbNew.isChecked()) {
                        if (tvQishu.getText().toString() != "") {
                            if (tvQishu.getText().toString().equals("12")) {
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

                            if (interest_0 != null && interest_1 != null) {
                                bottomPopupOption.setItemText(SmallNumberUtils.toReal(Double.parseDouble(interest_0), 100) + "%", SmallNumberUtils.toReal(Double.parseDouble(interest_1), 100) + "%");
                                bottomPopupOption.showPopupWindow();
                            }

                            bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {

                                @Override
                                public void onItemClick(int position) {
                                    if (position == 0) {
                                        if (tvQishu.getText().toString().equals("12")) {
                                            bank_interest = bean.getPeriods12().get(0).getBank_interest();
                                        } else if (tvQishu.getText().toString().equals("24")) {
                                            bank_interest = bean.getPeriods24().get(0).getBank_interest();
                                        } else if (tvQishu.getText().toString().equals("36")) {
                                            bank_interest = bean.getPeriods36().get(0).getBank_interest();
                                        }
                                        tvLilv.setText(SmallNumberUtils.toReal(Double.parseDouble(interest_0), 100) + "%");
                                        LogUtils.e("yinhang1", bank_interest + "=");
                                        bottomPopupOption.dismiss();
                                    } else if (position == 1) {
                                        if (tvQishu.getText().toString().equals("12")) {
                                            bank_interest = bean.getPeriods12().get(1).getBank_interest();
                                        } else if (tvQishu.getText().toString().equals("24")) {
                                            bank_interest = bean.getPeriods24().get(1).getBank_interest();
                                        } else if (tvQishu.getText().toString().equals("36")) {
                                            bank_interest = bean.getPeriods36().get(1).getBank_interest();
                                        }
                                        tvLilv.setText(SmallNumberUtils.toReal(Double.parseDouble(interest_1), 100) + "%");
                                        LogUtils.e("yinhang2", bank_interest + "=");
                                        bottomPopupOption.dismiss();
                                    }
                                }
                            });
                        } else {

                            MyToast.show(JisuanqiActivity.this, "请先选择贷款期数");

                        }

                    } else if (rbChedi.isChecked()) {

                        if (tvQishu.getText().toString() != "") {

                            if (tvQishu.getText().toString().equals("3")) {
                                interest_0 = carloanBean.getPeriods3().get(0).getInterest();
                                interest_1 = carloanBean.getPeriods3().get(1).getInterest();
//                        bank_interest = bean.getPeriods12().get(0).getBank_interest();
                            } else if (tvQishu.getText().toString().equals("12")) {
                                interest_0 = carloanBean.getPeriods12().get(0).getInterest();
                                interest_1 = carloanBean.getPeriods12().get(1).getInterest();
//                        bank_interest = bean.getPeriods12().get(0).getBank_interest();
                            } else if (tvQishu.getText().toString().equals("24")) {
                                interest_0 = carloanBean.getPeriods24().get(0).getInterest();
                                interest_1 = carloanBean.getPeriods24().get(1).getInterest();
//                        bank_interest = bean.getPeriods24().get(0).getBank_interest();
                            } else if (tvQishu.getText().toString().equals("36")) {
                                interest_0 = carloanBean.getPeriods36().get(0).getInterest();
                                interest_1 = carloanBean.getPeriods36().get(1).getInterest();
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
                                            bank_interest = carloanBean.getPeriods3().get(0).getBank_interest();
                                        } else if (tvQishu.getText().toString().equals("12")) {
                                            bank_interest = carloanBean.getPeriods12().get(0).getBank_interest();
                                        } else if (tvQishu.getText().toString().equals("24")) {
                                            bank_interest = carloanBean.getPeriods24().get(0).getBank_interest();
                                        } else if (tvQishu.getText().toString().equals("36")) {
                                            bank_interest = carloanBean.getPeriods36().get(0).getBank_interest();
                                        }
                                        tvLilv.setText(interest_0);
                                        LogUtils.e("yinhang1", bank_interest + "=");
                                        bottomPopupOption.dismiss();
                                    } else if (position == 1) {
                                        if (tvQishu.getText().toString().equals("3")) {
                                            bank_interest = carloanBean.getPeriods3().get(1).getBank_interest();
                                        } else if (tvQishu.getText().toString().equals("12")) {
                                            bank_interest = carloanBean.getPeriods12().get(1).getBank_interest();
                                        } else if (tvQishu.getText().toString().equals("24")) {
                                            bank_interest = carloanBean.getPeriods24().get(1).getBank_interest();
                                        } else if (tvQishu.getText().toString().equals("36")) {
                                            bank_interest = carloanBean.getPeriods36().get(1).getBank_interest();
                                        }
                                        tvLilv.setText(interest_1);
                                        LogUtils.e("yinhang2", bank_interest + "=");
                                        bottomPopupOption.dismiss();
                                    }
                                }
                            });
                        } else {

                            MyToast.show(JisuanqiActivity.this, "请先选择贷款期数");

                        }
                    }
                } else {

                    MyToast.show(JisuanqiActivity.this, "请先选择贷款期数");

                }

                break;

            default:
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        rbJiaoyi.setChecked(false);
        rbChedi.setEnabled(true);
//        tvDaikuane.setText("");
        tvLilv.setText("");
        tvQishu.setText("");
        if (rbNew.isChecked()) {
            cartype = "1";
            carloan = "1";
            rbChedi.setEnabled(false);
//            rbJiaoyi.setChecked(true);
        } else if (rbOld.isChecked() && rbJiaoyi.isChecked()) {
            cartype = "2";
            carloan = "1";
        } else if (rbOld.isChecked() && rbChedi.isChecked()) {
            cartype = "2";
            carloan = "2";
        }

        if (rbChedi.isChecked()) {
            rbOld.setChecked(true);
        }

        System.out.println("cartype = " + cartype + "========" + "carloan = " + carloan);

        if (cartype.equals("2") && carloan.equals("2")) {
            hud.show();
            initCarloan();
        } else {
            hud.show();
            initData();
        }
    }

    private void initData() {

        post()
                .url(Constants.URLS.BASEURL + "Loan/outInterest")
                .addParams("carloan", carloan == null ? "1" : carloan)
                .addParams("cartype", cartype == null ? "1" : cartype)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("initcost____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(JisuanqiActivity.this, "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("initcost----------------->>>>>>" + response);
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        bean = null;
                        try {
                            bean = gson.fromJson(json, CostBean.class);

                        } catch (JsonSyntaxException e) {
                            MyToast.show(JisuanqiActivity.this, "联网失败");
                        }

                        if (bean.getCode() == 0) {
                            MyToast.show(JisuanqiActivity.this, bean.getMsg());
                        }

                    }
                });

    }

    private void initCarloan() {

//        System.out.println("carlon=" + carLoan);
//        System.out.println("cartype=" + isNew);

        post()
                .url(Constants.URLS.BASEURL + "Loan/outInterest")
                .addParams("carloan", carloan)
                .addParams("cartype", cartype)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("initCarloancost____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(JisuanqiActivity.this, "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("initcost----------------->>>>>>" + response);
                        String json = response;
                        Gson gson = new Gson();
                        carloanBean = null;
                        try {
                            carloanBean = gson.fromJson(json, CarCostBean.class);

                        } catch (JsonSyntaxException e) {
                            hud.dismiss();
                            MyToast.show(JisuanqiActivity.this, "联网失败");
                        }

                        if (carloanBean.getCode() == 0) {
                            MyToast.show(JisuanqiActivity.this, carloanBean.getMsg());
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

            if (rbJiaoyi.isChecked()) {
                if (rbNew.isChecked()) {
                    lixicha = NumberUtil.getNumber("" + SmallNumberUtils.toReal((SmallNumberUtils.toMinus(Double.parseDouble(tvLilv.getText().toString().equals("") ? "0" : tvLilv.getText().toString().equals(SmallNumberUtils.toReal(Double.parseDouble(interest_0), 100) + "%") ? interest_0 : interest_1),
                            Double.parseDouble(bank_interest == null ? "0" : bank_interest))), Double.parseDouble(etDaikuane.getText().toString().equals("") ? "0" : etDaikuane.getText().toString())));

                    tvLixicha.setText(NumberUtil.toReal(lixicha + ""));

                    double shiji = SmallNumberUtils.toMinus(Double.parseDouble(etDaikuane.getText().toString().equals("") ? "0" : etDaikuane.getText().toString()),
                            lixicha,
                            Double.parseDouble(tvGpsPrice.getText().toString().equals("") ? "0" : tvGpsPrice.getText().toString()),
                            Double.parseDouble(etShangxian.getText().toString().equals("") ? "0" : etShangxian.getText().toString()),
                            Double.parseDouble(etQiangxian.getText().toString().equals("") ? "0" : etQiangxian.getText().toString()),
                            Double.parseDouble(tvGouzhishui.getText().toString().equals("") ? "0" : tvGouzhishui.getText().toString()));

                    tvDaikuane.setText(NumberUtil.toReal(String.valueOf(shiji)));

                    LogUtils.e("shiji" + shiji);
                    LogUtils.e("lixicha" + lixicha);

                    if (!etDaikuane.getText().toString().equals("") && !tvQishu.getText().toString().equals("")) {

                        tvYuegong.setText(NumberUtil.getPerMonthPrincipalInterest(Double.parseDouble(etDaikuane.getText().toString()), 0.1, Integer.parseInt(tvQishu.getText().toString())) + "");
                    }
                } else if (rbOld.isChecked()) {

                    lixicha = NumberUtil.getNumber("" + SmallNumberUtils.toReal((SmallNumberUtils.toMinus(Double.parseDouble(tvLilv.getText().toString().equals("") ? "0" : tvLilv.getText().toString().equals(SmallNumberUtils.toReal(Double.parseDouble(interest_0), 100) + "%") ? interest_0 : interest_1),
                            Double.parseDouble(bank_interest == null ? "0" : bank_interest))), Double.parseDouble(etDaikuane.getText().toString().equals("") ? "0" : etDaikuane.getText().toString())));
                    tvLixicha.setText(NumberUtil.toReal(lixicha + ""));

                    double shiji = SmallNumberUtils.toMinus(Double.parseDouble(etDaikuane.getText().toString().equals("") ? "0" : etDaikuane.getText().toString()),
                            lixicha,
                            Double.parseDouble(tvGpsPrice.getText().toString().equals("") ? "0" : tvGpsPrice.getText().toString()),
                            Double.parseDouble(etShangxian.getText().toString().equals("") ? "0" : etShangxian.getText().toString()),
                            Double.parseDouble(etQiangxian.getText().toString().equals("") ? "0" : etQiangxian.getText().toString()));

                    tvDaikuane.setText(NumberUtil.toReal(String.valueOf(shiji)));

                    LogUtils.e("shiji" + shiji);
                    LogUtils.e("lixicha" + lixicha);

                    if (!etDaikuane.getText().toString().equals("") && !tvQishu.getText().toString().equals("")) {

                        tvYuegong.setText(NumberUtil.getPerMonthPrincipalInterest(Double.parseDouble(etDaikuane.getText().toString()), 0.12, Integer.parseInt(tvQishu.getText().toString())) + "");
                    }
                }

//                System.out.println(d);
//
//                int daikuane = (int) d;
//
//                tvDaikuane.setText(String.valueOf(daikuane));

            } else if (rbChedi.isChecked()) {
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
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            MyToast.show(JisuanqiActivity.this, "输入有误");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
