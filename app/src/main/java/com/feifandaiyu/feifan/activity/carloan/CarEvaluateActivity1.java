package com.feifandaiyu.feifan.activity.carloan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CreditActivityBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.popupWindow.BottomPopupOption;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.NumberUtils;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

public class CarEvaluateActivity1 extends BaseActivity implements TextWatcher {

    @InjectView(R.id.et_customer_name)
    PowerfulEditText etCustomerName;
    @InjectView(R.id.tv_certificate_type)
    TextView tvCertificateType;
    @InjectView(R.id.et_certificate_num)
    PowerfulEditText etCertificateNum;
    @InjectView(R.id.et_phone_num)
    PowerfulEditText etPhoneNum;
    @InjectView(R.id.tv_home)
    TextView tvHome;
    @InjectView(R.id.et_full_addr)
    PowerfulEditText etFullAddr;
    @InjectView(R.id.bt_next)
    Button btNext;
    @InjectView(R.id.rb_own)
    RadioButton rbOwn;
    @InjectView(R.id.rb_other)
    RadioButton rbOther;
    @InjectView(R.id.rg_isnew)
    RadioGroup rgIsnew;
    private BottomPopupOption bottomPopupOption;
    private String province;
    private String city;
    private String district;
    private InputMethodManager imm;
    private KProgressHUD hud;

    @Override
    protected int getContentView() {
        return R.layout.activity_car_evaluate1;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        showBack(true);
        showNext(false);
        setTitle("客户建档（车抵贷）");

        hud = KProgressHUD.create(CarEvaluateActivity1.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

//        rbOwn.setChecked(true);

        etCertificateNum.addTextChangedListener(this);
        etCustomerName.addTextChangedListener(this);
        etFullAddr.addTextChangedListener(this);
        etPhoneNum.addTextChangedListener(this);
        tvCertificateType.addTextChangedListener(this);
        tvHome.addTextChangedListener(this);

    }

    @OnClick({R.id.et_phone_num, R.id.bt_next, R.id.iv_back, R.id.tv_certificate_type, R.id.tv_home})
    public void onViewClicked(View view) {
        bottomPopupOption = new BottomPopupOption(CarEvaluateActivity1.this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        switch (view.getId()) {
            case R.id.et_phone_num:
                break;
            case R.id.tv_certificate_type:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                bottomPopupOption.setItemText("身份证", "护照", "驾驶证");
                bottomPopupOption.showPopupWindow();
                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 0) {
                            tvCertificateType.setText("身份证");
                            bottomPopupOption.dismiss();
                        } else if (position == 1) {
                            tvCertificateType.setText("护照");
                            bottomPopupOption.dismiss();
                        } else if (position == 2) {
                            tvCertificateType.setText("驾驶证");
                            bottomPopupOption.dismiss();
                        }
                    }
                });
                break;
            case R.id.bt_next:

                if (rbOwn.isChecked() || rbOther.isChecked()) {
                    hud.show();

                    if (province.equals("北京市") || province.equals("上海市") || province.equals("天津市") || province.equals("重庆市")) {
                        city = district;
                        district = "-1";
                    }

                    PreferenceUtils.setInt(CarEvaluateActivity1.this, "credit", 2);

                    String userName = etCustomerName.getText().toString().trim();
                    String cardNum = etCertificateNum.getText().toString().trim();
                    String telphone = etPhoneNum.getText().toString().trim();
                    if (!NumberUtils.isMobile(telphone)) {
                        MyToast.show(this, "手机号码不是11位，请仔细检查");
                        return;
                    }
                    String cardTypeMessage = tvCertificateType.getText().toString();

                    String cardType = "1";
                    if (cardTypeMessage.equals("身份证")) {
                        cardType = "1";
                    } else if (cardTypeMessage.equals("护照")) {
                        cardType = "2";
                    } else {
                        cardType = "3";
                    }

                    OkHttpUtils
                            .post()
                            .url(Constants.URLS.BASEURL + "Login/createArchives")
                            .addParams("userName", userName)
                            .addParams("cardType", cardType)
                            .addParams("cardNum", cardNum)
                            .addParams("telphone", telphone)
                            .addParams("address", etFullAddr.getText().toString().trim())
                            .addParams("lat", "333")
                            .addParams("lng", "222")
                            .addParams("province", province == null ? "黑龙江" : province)
                            .addParams("city", city == null ? "哈尔滨" : city)
                            .addParams("area", district == null ? "道里区" : district)
                            .addParams("salesman_id", PreferenceUtils.getString(CarEvaluateActivity1.this, "saleID"))
                            .addParams("carLoan", "2")
                            .addParams("credit", rbOwn.isChecked() ? "0" : "1")
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    LogUtils.i("CarEvaluateActivity1----------------->>>>>>." + e);
                                    MyToast.show(CarEvaluateActivity1.this, "服务器正忙，请稍后再试。。。");
                                    hud.dismiss();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    // TODO: 2017/6/7 数据提交中用户交互界面....
                                    LogUtils.i("CarEvaluateActivity1----------------->>>>>>." + response);
                                    hud.dismiss();
                                    String json = response;
                                    Gson gson = new Gson();
                                    CreditActivityBean bean = gson.fromJson(json, CreditActivityBean.class);
                                    if (bean.getCode() == 1) {
                                        String userId = bean.getList().getUserId();
                                        PreferenceUtils.setString(CarEvaluateActivity1.this, "userId", userId);
                                        PreferenceUtils.setString(CarEvaluateActivity1.this, "carLoan", "2");

//                                    if (rbOwn.isChecked()) {
//                                        Intent intent = new Intent(CarEvaluateActivity1.this, CustomeReport.class);
//                                        startActivity(intent);
//                                    } else if (rbOther.isChecked()) {
//                                        Intent intent = new Intent(CarEvaluateActivity1.this, BankOkActivity.class);
//
//                                        startActivity(intent);
//                                    }
                                        Intent intent = new Intent(CarEvaluateActivity1.this, CarEvaluateActivity2.class);
                                        startActivity(intent);
                                        finish();
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);
                                    } else if (bean.getCode() == 0) {
                                        MyToast.show(CarEvaluateActivity1.this, bean.getMsg());
                                    }

                                }
                            });

                } else {
                    MyToast.show(this, "请选择征信类型");
                }


                break;

            case R.id.tv_home:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                showSelectHomeDialog();
                break;

            case R.id.iv_back:
                new AlertDialog.Builder(this)

                        .setMessage(StringCreateUtils.createString())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();

                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
    }

    private void showSelectHomeDialog() {
        CityPicker cityPicker = new CityPicker.Builder(CarEvaluateActivity1.this)
                .textSize(18)
                .title(" ")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#efefef")
                .titleTextColor("#807f7f")
                .confirTextColor("#49a4f4")
                .cancelTextColor("#807f7f")
                .province("黑龙江")
                .city("哈尔滨")
                .district("道里区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();

        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                province = citySelected[0];
                //城市
                city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                district = citySelected[2];
                //邮编
                String code = citySelected[3];

                tvHome.setText(province + city + district);
            }

            @Override
            public void onCancel() {
//                Toast.makeText(InputCreditActivity.this, "已取消", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!StringUtils.isEmpty(etPhoneNum.getText().toString())
                && !StringUtils.isEmpty(etCustomerName.getText().toString())
                && !StringUtils.isEmpty(etFullAddr.getText().toString())
                && !StringUtils.isEmpty(etCertificateNum.getText().toString())
                && !StringUtils.isEmpty(tvHome.getText().toString())
                && !StringUtils.isEmpty(tvCertificateType.getText().toString()
        )
                )

        {
            btNext.setEnabled(true);
        } else {
            btNext.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
