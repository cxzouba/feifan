package com.feifandaiyu.feifan.update;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CreditActivityBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.popupWindow.BottomPopupOption;
import com.feifandaiyu.feifan.utils.AssetsUtils;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.NumberUtils;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.AddressPicker;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * @author houdaichang
 */
public class UpdateInputCreditActivity extends BaseActivity implements TextWatcher {


    @InjectView(R.id.et_customer_name)
    PowerfulEditText etCustomerName;
    @InjectView(R.id.tv_certificate_type)
    TextView tvCertificateType;
    @InjectView(R.id.et_certificate_num)
    PowerfulEditText etCertificateNum;
    @InjectView(R.id.et_phone_num)
    PowerfulEditText etPhoneNum;
    @InjectView(R.id.ll_about_us)
    LinearLayout llAboutUs;
    @InjectView(R.id.imageView7)
    ImageView imageView7;
    @InjectView(R.id.tv_home)
    TextView tvHome;
    @InjectView(R.id.ll_home)
    LinearLayout llHome;
    @InjectView(R.id.et_full_addr)
    EditText etFullAddr;
    @InjectView(R.id.ll_car_addr)
    LinearLayout llCarAddr;
    @InjectView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @InjectView(R.id.bt_start_visit)
    Button btStartVisit;
    @InjectView(R.id.rb_own)
    RadioButton rbOwn;
    @InjectView(R.id.rb_other)
    RadioButton rbOther;
    @InjectView(R.id.rb_two)
    RadioButton rbTwo;
    @InjectView(R.id.rb_base)
    RadioButton rbBase;
    @InjectView(R.id.et_shenfenzheng_addr)
    PowerfulEditText etShenfenzhengAddr;
    private BottomPopupOption bottomPopupOption;
    private String province;
    private String city;
    private String district;
    private KProgressHUD hud;
    private String userId;
    private String province1;
    private String city1;
    private String area1;
    private String carLoan;

//    private GeoCoder geoCoder;
//    private Geocoder gc;
//    private String fullAddr;
//    private double latitude;
//    private double longitude;

    @Override
    protected int getContentView() {
        return R.layout.activity_input_credit;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

//        gc = new Geocoder(InputCreditActivity.this, Locale.CHINA);

//        geoCoder = GeoCoder.newInstance();

        setTitle("客户建档");
        showNext(false);
        btStartVisit.setText("保存");

        hud = KProgressHUD.create(UpdateInputCreditActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍候")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        userId = PreferenceUtils.getString(this, "userId");
        carLoan = PreferenceUtils.getString(UpdateInputCreditActivity.this, "carLoan");

        etCustomerName.addTextChangedListener(this);
        etCertificateNum.addTextChangedListener(this);
        etPhoneNum.addTextChangedListener(this);
        etFullAddr.addTextChangedListener(this);
        tvHome.addTextChangedListener(this);
        tvCertificateType.addTextChangedListener(this);

        hud.show();

        initData();

    }

    private void initData() {

        post()
                .url(Constants.URLS.BASEURL + "UserInfo/index")
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("updateInputShow----------------->>>>>>." + e);
                        hud.dismiss();
                        MyToast.show(UpdateInputCreditActivity.this, "联网失败...");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("updateInputShow----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();
                        CreditActivityBean bean = gson.fromJson(json, CreditActivityBean.class);

                        hud.dismiss();

                        if (bean.getCode() == 1) {
                            etCustomerName.setText(bean.getList().getUserName());
                            etCustomerName.setTextColor(Color.GRAY);
                            etCustomerName.setEnabled(false);

                            tvCertificateType.setText(bean.getList().getCardType());
                            tvCertificateType.setTextColor(Color.GRAY);
                            tvCertificateType.setEnabled(false);

                            etCertificateNum.setText(bean.getList().getCardNum());
                            etCertificateNum.setTextColor(Color.GRAY);
                            etCertificateNum.setEnabled(false);

                            etPhoneNum.setText(bean.getList().getTelphone());

                            province1 = bean.getList().getProvince();
                            city1 = bean.getList().getCity();
                            area1 = bean.getList().getArea();

                            if (area1.equals("-1")) {
                                area1 = "";
                            } else {
                                area1 = bean.getList().getArea();
                            }

                            tvHome.setText(province1 + city1 + area1);

                            etShenfenzhengAddr.setText(bean.getList().getCardAddress());
                            etShenfenzhengAddr.setTextColor(Color.GRAY);
                            etShenfenzhengAddr.setEnabled(false);

                            etFullAddr.setText(bean.getList().getAddress());

                            if (bean.getList().getLoanType().equals("1")) {
                                rbTwo.setChecked(true);
                                rbBase.setEnabled(false);
                            } else if (bean.getList().getLoanType().equals("2")) {
                                rbBase.setChecked(true);
                                rbTwo.setEnabled(false);
                            }

                            if (bean.getList().getCredit().equals("0")) {
                                rbOwn.setChecked(true);
                                rbOther.setEnabled(false);
                            } else if (bean.getList().getCredit().equals("1")) {
                                rbOther.setChecked(true);
                                rbOwn.setEnabled(false);
                            }


                        } else if (bean.getCode() == 0) {
                            new AlertDialog.Builder(UpdateInputCreditActivity.this)
                                    .setMessage(bean.getMsg())
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                            int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                            overridePendingTransition(enterAnim0, exitAnim0);
                                        }
                                    })
                                    .setCancelable(false)
                                    .show();
                        }
                    }
                });
    }


    @OnClick({R.id.iv_back, R.id.bt_start_visit, R.id.tv_certificate_type,
            R.id.tv_home})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.tv_home:

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                showSelectHomeDialog();
                break;

            case R.id.tv_certificate_type:

                InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                bottomPopupOption = new BottomPopupOption(UpdateInputCreditActivity.this);
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
            case R.id.bt_start_visit:
                hud.show();

//                if (province != null) {
//                    if (province.equals("北京市") || province.equals("上海市") || province.equals("天津市") || province.equals("重庆市")) {
//                        city = district;
//                        district = "-1";
//                    }
//                }


                String telphone = etPhoneNum.getText().toString().trim();

                if (!NumberUtils.isMobile(telphone)) {
                    MyToast.show(this, "手机号码不是11位，请仔细检查");
                    hud.dismiss();
                    return;
                }
                PreferenceUtils.setInt(UpdateInputCreditActivity.this, "credit", 1);

                String userName = etCustomerName.getText().toString().trim();

                PreferenceUtils.setString(UpdateInputCreditActivity.this, "userName", userName);

                String cardTypeMessage = tvCertificateType.getText().toString();
                String cardType = "1";
                if (cardTypeMessage.equals("身份证")) {
                    cardType = "1";
                } else if (cardTypeMessage.equals("护照")) {
                    cardType = "2";
                } else {
                    cardType = "3";
                }
                String cardNum = etCertificateNum.getText().toString().trim();

                if (area1.equals("")) {
                    area1 = "-1";
                }

                System.out.println(province1 + city1 + area1);

                OkHttpUtils
                        .post()
                        .url(Constants.URLS.BASEURL + "UserUpdate/index")
                        .addParams("userId", userId)
                        .addParams("userName", userName)
                        .addParams("cardType", cardType)
                        .addParams("cardNum", cardNum)
                        .addParams("telphone", telphone)
                        .addParams("address", etFullAddr.getText().toString())
                        .addParams("lat", "333")
                        .addParams("lng", "222")
                        .addParams("province", province == null ? province1 : province)
                        .addParams("city", city == null ? city1 : city)
                        .addParams("area", district == null ? area1 : district)
                        .addParams("salesman_id", PreferenceUtils.getString(UpdateInputCreditActivity.this, "saleID"))
//                        .addParams("carLoan", carLoan)
//                        .addParams("credit", rbOwn.isChecked() ? "0" : "1")
//                        .addParams("loanType", rbTwo.isChecked() ? "1" : "2")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                LogUtils.i("updateInputChange----------------->>>>>>." + e);
                                MyToast.show(UpdateInputCreditActivity.this, "服务器正忙，请稍后再试。。。");
                                hud.dismiss();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.i("updateInputChange----------------->>>>>>." + response);
                                String json = response;
                                Gson gson = new Gson();
                                CreditActivityBean bean = gson.fromJson(json, CreditActivityBean.class);

                                PreferenceUtils.setString(UpdateInputCreditActivity.this, "carLoan", "1");

                                if (bean.getCode() == 1) {

                                    System.out.println(province == null ? province1 : province + city == null ? city1 : city + district == null ? area1 : district);

//                                    String userId = bean.getList().getUserId();
//                                    PreferenceUtils.setString(UpdateInputCreditActivity.this, "userId", userId);
//                                    if (rbOwn.isChecked()) {
//                                        PreferenceUtils.setString(UpdateInputCreditActivity.this, "CreditType", "0");
//                                    } else if (rbOther.isChecked()) {
//                                        PreferenceUtils.setString(UpdateInputCreditActivity.this, "CreditType", "1");
//                                    }
                                    hud.dismiss();

                                    MyToast.show(UpdateInputCreditActivity.this, bean.getMsg());
//
//                                    startActivity(new Intent(UpdateInputCreditActivity.this, CarTypeActivity.class));
                                    finish();
                                    int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                    int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                    overridePendingTransition(enterAnim0, exitAnim0);
                                } else if (bean.getCode() == 0) {
                                    MyToast.show(UpdateInputCreditActivity.this, bean.getMsg());
                                    hud.dismiss();
                                }

                            }
                        });

                break;
            case R.id.iv_back:

                new AlertDialog.Builder(this)

                        .setMessage("是否取消修改？")
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
            default:
        }
    }


    private void showSelectHomeDialog() {
        //选择城市颜色
//        int color1 = getResources().getColor(R.color.xuanzechengshi);
        //带选择城市颜色
//        int color2 = getResources().getColor(R.color.beixuanchengshi);
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(UpdateInputCreditActivity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(UpdateInputCreditActivity.this, data);
        picker.setSelectedItem("黑龙江省", "哈尔滨市", "道里区");
        picker.setTitleText("地址选择");
        picker.setTitleTextColor(Color.BLACK);
        picker.setLineVisible(false);
        picker.setTextSize(18);
        ;
        picker.setOffset(2);
//        picker.setTextColor(color1, color2);
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            //                                  省           市           区
            public void onAddressPicked(String province, String city, String county) {
                //省市区
                tvHome.setText(province + city + county);
                //省
                UpdateInputCreditActivity.this.province = province;
//                //市
                UpdateInputCreditActivity.this.city = city;
//                //区
                UpdateInputCreditActivity.this.district = county;
            }
        });
        picker.show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!StringUtils.isEmpty(etCustomerName.getText().toString())
                && !StringUtils.isEmpty(etCertificateNum.getText().toString())
                && !StringUtils.isEmpty(etPhoneNum.getText().toString())
                && !StringUtils.isEmpty(etFullAddr.getText().toString())
                && !StringUtils.isEmpty(tvCertificateType.getText().toString())
                && !StringUtils.isEmpty(tvHome.getText().toString()
        )
                )

        {
            btStartVisit.setEnabled(true);
        } else {
            btStartVisit.setEnabled(false);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }
}
