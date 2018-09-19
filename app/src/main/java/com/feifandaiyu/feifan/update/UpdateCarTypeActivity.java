package com.feifandaiyu.feifan.update;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.personalloan.CarTypeMessageActivity;
import com.feifandaiyu.feifan.adapter.DialogAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CarTypeActivityBean;
import com.feifandaiyu.feifan.bean.DialogBankBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.feifandaiyu.feifan.view.OnKeyActionListener;
import com.feifandaiyu.feifan.view.VehiclePlateKeyboard;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * Created by houdaichang on 2017/5/8.
 */


public class UpdateCarTypeActivity extends BaseActivity implements TextWatcher {


    @InjectView(R.id.imageView2)
    ImageView imageView2;
    @InjectView(R.id.rb_new_car)
    RadioButton rbNewCar;
    @InjectView(R.id.imageView3)
    ImageView imageView3;
    @InjectView(R.id.rb_old_car)
    RadioButton rbOldCar;
    @InjectView(R.id.rg_isnew)
    RadioGroup rgIsnew;
    @InjectView(R.id.tv_car_bank)
    TextView tvCarBank;
    @InjectView(R.id.tv_choose_bank)
    TextView tvChooseBank;
    @InjectView(R.id.ll_car_bank)
    LinearLayout llCarBank;
    @InjectView(R.id.tv_choose_type1)
    TextView tvChooseType1;
    @InjectView(R.id.tv_choose_type)
    TextView tvChooseType;
    @InjectView(R.id.ll_choose_type)
    LinearLayout llChooseType;
    @InjectView(R.id.et_example_price)
    TextView etExamplePrice;
    @InjectView(R.id.ll_example_price)
    LinearLayout llExamplePrice;
    @InjectView(R.id.tv_real_price)
    TextView tvloanPrice;
    @InjectView(R.id.et_real_price)
    PowerfulEditText etRealPrice;
    @InjectView(R.id.ll_real_price)
    LinearLayout llloanPrice;
    @InjectView(R.id.tv_choose_color)
    TextView tvChooseColor;
    @InjectView(R.id.et_color)
    PowerfulEditText etColor;
    @InjectView(R.id.ll_choose_color)
    LinearLayout llChooseColor;
    @InjectView(R.id.imageView4)
    ImageView imageView4;
    @InjectView(R.id.rb_man_speed)
    RadioButton rbManSpeed;
    @InjectView(R.id.imageView5)
    ImageView imageView5;
    @InjectView(R.id.rb_auto_speed)
    RadioButton rbAutoSpeed;
    @InjectView(R.id.rg_isauto)
    RadioGroup rgisauto;
    @InjectView(R.id.tv_vin_num)
    TextView tvVinNum;
    @InjectView(R.id.et_vin_num)
    PowerfulEditText etVinNum;
    @InjectView(R.id.ll_vin_num)
    LinearLayout llVinNum;
    @InjectView(R.id.et_chepai_num)
    TextView etChepaiNum;
    @InjectView(R.id.ll_chepai_num)
    LinearLayout llChepaiNum;
    @InjectView(R.id.sv_cartype)
    ScrollView svCartype;
    private TextView tv_next;
    private String userId;
    private List<DialogBankBean.ListBean> list;
    private DialogAdapter adapter;
    private String id_bank;
    private ProgressDialog progressDialog;
    private String bank;
    private KProgressHUD hud;
    private String creditType;
    private String flag;
    private String showBrand;
    private String showCarSize;
    private String showCarSeries;
    private String carDealer;
    private Activity mActivity;
    private String saleID;
    private String carId;
    private String chehangId;
    private String delerId;
    //    private KeyboardUtil keyboardUtil;

    @Override
    protected int getContentView() {
        return R.layout.activity_car_type;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("车型信息");
        showBack(true);
        showNext(true);
        mActivity = this;

        hud = KProgressHUD.create(UpdateCarTypeActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍候")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

//        keyboardUtil = new KeyboardUtil(mActivity, etChepaiNum);

        userId = PreferenceUtils.getString(this, "userId");
        flag = PreferenceUtils.getString(this, "flag");
        creditType = PreferenceUtils.getString(this, "CreditType");
        saleID = PreferenceUtils.getString(this, "saleID");
        carId = PreferenceUtils.getString(this, "carId");

        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setText("保存");

        if (flag.equals("0") || flag.equals("-999")) {
            tv_next.setVisibility(View.VISIBLE);
        } else {
            tv_next.setVisibility(View.GONE);
            MyToast.showLong(this,"车辆已发起贷款，不能修改");
        }

        etChepaiNum.addTextChangedListener(this);
        etColor.addTextChangedListener(this);
        etExamplePrice.addTextChangedListener(this);
        etRealPrice.addTextChangedListener(this);
        etVinNum.addTextChangedListener(this);
        tvChooseBank.addTextChangedListener(this);
        String userName = PreferenceUtils.getString(this, "userName");

        hud.show();

        initData();

    }


    private void initData() {

        post()
                .url(Constants.URLS.BASEURL + "UserInfo/carInfo")
                .addParams("carId", carId)
                .addParams("status", "0")
                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("updateCartypeShow----------------->>>>>>." + e);
                        hud.dismiss();
                        MyToast.show(UpdateCarTypeActivity.this, "联网失败...");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("updateCartypeShow----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();
                        CarTypeActivityBean bean = gson.fromJson(json, CarTypeActivityBean.class);

                        hud.dismiss();

                        if (bean.getCode() == 1) {

                            if (bean.getList().getCarType() == 0) {
                                rbNewCar.setChecked(true);
                                rbOldCar.setEnabled(false);
                            } else if (bean.getList().getCarType() == 1) {
                                rbOldCar.setChecked(true);
                                rbNewCar.setEnabled(false);
                            }


                            carDealer = bean.getList().getCarDealer();
                            if (carDealer.equals("-1")) {
                                tvChooseBank.setText("未选择");
                            } else {
                                tvChooseBank.setText(carDealer);
                            }

                            delerId = bean.getList().getDelerId();

                            showBrand = bean.getList().getBrand();
                            showCarSize = bean.getList().getCarSize();
                            showCarSeries = bean.getList().getCarSeries();

                            tvChooseType.setText(showCarSize);

                            etExamplePrice.setText(bean.getList().getGuidePrice());


//                            String realPrice = PreferenceUtils.getString(UpdateCarTypeActivity.this, "loanPrice");

                            etRealPrice.setText(bean.getList().getSalePrice());

                            if (bean.getList().getCarColor().equals("-1")) {
                                etColor.setHint("未填写");
                            } else {

                                etColor.setText(bean.getList().getCarColor());

                            }

                            if (bean.getList().getGearbox().equals("0")) {
                                rbAutoSpeed.setChecked(true);
                            } else if (bean.getList().getGearbox().equals("1")) {
                                rbManSpeed.setChecked(true);
                            }

                            if (bean.getList().getVinCode().equals("-1")) {
                                etVinNum.setHint("未填写");

                            } else {

                                etVinNum.setText(bean.getList().getVinCode());
                            }

                            if (bean.getList().getLicenseNum().equals("-1")) {
                                etChepaiNum.setHint("未填写");

                            } else {

                                etChepaiNum.setText(bean.getList().getLicenseNum());
                            }


                                tvChooseBank.setTextColor(Color.GRAY);
                                tvChooseBank.setEnabled(false);
                                tvChooseType.setTextColor(Color.GRAY);
                                tvChooseType.setEnabled(false);
                                etExamplePrice.setTextColor(Color.GRAY);
                                etExamplePrice.setEnabled(false);
                                etRealPrice.setTextColor(Color.GRAY);
                                etRealPrice.setEnabled(false);



                        } else if (bean.getCode() == 0) {
                            new AlertDialog.Builder(UpdateCarTypeActivity.this)

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


    @OnClick({R.id.tv_choose_bank, R.id.iv_back, R.id.tv_next, R.id.tv_choose_type, R.id.et_chepai_num})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_bank:
                showDialog();
                break;
            case R.id.et_chepai_num:
                VehiclePlateKeyboard keyboard = new VehiclePlateKeyboard(UpdateCarTypeActivity.this, new OnKeyActionListener() {
                    @Override
                    public void onFinish(String input) {
                        if (input.length()==7){
                            etChepaiNum.setText(input);
                        }else {
                            etChepaiNum.setText("");

                        }
                    }

                    @Override
                    public void onProcess(String input) {
                        etChepaiNum.setText("");
                    }
                });

                keyboard.setDefaultPlateNumber("");
                keyboard.show(getWindow().getDecorView().getRootView());
                break;
            case R.id.iv_back:

                PreferenceUtils.setString(UpdateCarTypeActivity.this, "details", "");
                PreferenceUtils.setString(UpdateCarTypeActivity.this, "price", "");
                PreferenceUtils.setString(UpdateCarTypeActivity.this, "cars", "");
                PreferenceUtils.setString(UpdateCarTypeActivity.this, "carBrand", "");

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
            case R.id.tv_next:// TODO: 2017/6/7
                if (tvChooseType.getText().toString().equals("") || tvChooseType.getText() == null) {
                    MyToast.show(this, "请选择车型");
                    return;
                }
                postMessage();
                break;
            case R.id.tv_choose_type:
                startActivity(new Intent(UpdateCarTypeActivity.this, CarTypeMessageActivity.class));
                break;
        }
    }


    private void showDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);//将xml布局转换为view
        View view = inflater.inflate(R.layout.dialog_bank, null);//将xml布局转换为view,里面有listview
        final ListView listView = (ListView) view.findViewById(R.id.pop_bank);

        final Dialog builder = new Dialog(UpdateCarTypeActivity.this);
        builder.setTitle("选择车行");
        builder.setContentView(view);
        builder.show();

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Login/carDealer")
                .addParams("id", saleID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("dialogBamk------------------>>>" + e);
                        MyToast.show(UpdateCarTypeActivity.this, "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("dialogBank------------------>>>" + response);
                        String json = response;
                        Gson gson = new Gson();
                        DialogBankBean bean = gson.fromJson(json, DialogBankBean.class);
                        list = bean.getList();
//                        LogUtils.i(list1.size() + "---------------->>>>>>>>");

                        //车商返点
                        String rebate = null;
                        try {
                            rebate = list.get(0).getRebate();
                        } catch (Exception e) {
                            e.printStackTrace();
                            rebate = "0";
                        }

                        PreferenceUtils.setString(UpdateCarTypeActivity.this, "rebate", rebate);

                        adapter = new DialogAdapter(UpdateCarTypeActivity.this, list);
                        listView.setAdapter(adapter);//给listview设置适配器，adapter
                        adapter.notifyDataSetChanged();
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chehangId = list.get(position).getId();
                bank = list.get(position).getUsername();
                tvChooseBank.setText(bank.toString());
                builder.dismiss();
            }
        });

    }


    private void postMessage() {
        hud.show();

        String brand = PreferenceUtils.getString(UpdateCarTypeActivity.this, "carBrand");
        String carSeries = PreferenceUtils.getString(UpdateCarTypeActivity.this, "cars");
        String carSize = PreferenceUtils.getString(UpdateCarTypeActivity.this, "details");

        String guidePrice = etExamplePrice.getText().toString().trim();
        String salePrice = etRealPrice.getText().toString().trim();
        String vinCode = etVinNum.getText().toString().trim();
        String carColor = etColor.getText().toString().trim();
        String licenseNum = etChepaiNum.getText().toString().trim();

        if (brand != null) {

            System.out.println("brand=" + brand);
            System.out.println("carSize=" + carSize);
            System.out.println("carSeries=" + carSeries);
            System.out.println("chehangId=" + chehangId);
            System.out.println("delerId=" + delerId);
            System.out.println("showBrand=" + showBrand);
        }

        post()
                .url(Constants.URLS.BASEURL + "userUpdate/editCar")
                .addParams("carType", rbNewCar.isChecked() ? "0" : "1")
                .addParams("carDealer", chehangId == null ? delerId : chehangId.toString()) // 车行id
                .addParams("brand", brand == null || brand.equals("") ? showBrand : brand)
                .addParams("carSize", carSize == null || carSize.equals("") ? showCarSize : carSize)
                .addParams("carSeries", carSeries == null || carSeries.equals("") ? showCarSeries : carSeries)
                .addParams("guidePrice", guidePrice)
                .addParams("salePrice", salePrice)
                .addParams("vinCode", vinCode)
                .addParams("carColor", carColor)
                .addParams("gearbox", rbManSpeed.isChecked() ? "1" : "0")
                .addParams("carId", carId)
                .addParams("status", "0")
                .addParams("licenseNum", licenseNum)
//                .addParams("carLoan", rbOldCar.isChecked() ? "2" : "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("updateCartypeEdit------------------>>>" + e);
                        hud.dismiss();
                        MyToast.show(UpdateCarTypeActivity.this, "联网失败");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("updateCartypeEdit------------------>>>" + response);

                        hud.dismiss();

                        String json = response;
                        Gson gson = new Gson();
                        CarTypeActivityBean carTypeActivityBean = gson.fromJson(json, CarTypeActivityBean.class);

                        if (carTypeActivityBean.getCode() == 0) {
                            MyToast.show(UpdateCarTypeActivity.this, carTypeActivityBean.getMsg());
                            return;
                        }
                        if (carTypeActivityBean.getCode() == 1) {

                            PreferenceUtils.setString(UpdateCarTypeActivity.this, "details", "");
                            PreferenceUtils.setString(UpdateCarTypeActivity.this, "price", "");
                            PreferenceUtils.setString(UpdateCarTypeActivity.this, "cars", "");
                            PreferenceUtils.setString(UpdateCarTypeActivity.this, "carBrand", "");

                            MyToast.show(UpdateCarTypeActivity.this, "车型信息保存成功");

                            finish();
                            // // 设置过渡动画
                            int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                            int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                            overridePendingTransition(enterAnim0, exitAnim0);

                        }

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

//        if (etChepaiNum.getText().toString().length() == 7) {
//            if (keyboardUtil != null) {
//                keyboardUtil.hideKeyboard();
//            }
//        }

        if (rbOldCar.isChecked()) {
            if (!StringUtils.isEmpty(etVinNum.getText().toString())
                    && !StringUtils.isEmpty(etRealPrice.getText().toString())
                    && !StringUtils.isEmpty(etChepaiNum.getText().toString())
                    && !StringUtils.isEmpty(etExamplePrice.getText().toString())
                    && !StringUtils.isEmpty(etColor.getText().toString())
//                    && !StringUtils.isEmpty(tvChooseBank.getText().toString())
                    ) {
                tv_next.setEnabled(true);
            } else {
                tv_next.setEnabled(false);
            }
        } else if (rbNewCar.isChecked()) {
            if (
                    !StringUtils.isEmpty(etRealPrice.getText().toString())
                            && !StringUtils.isEmpty(etExamplePrice.getText().toString())
//                    && !StringUtils.isEmpty(tvChooseBank.getText().toString())
                    ) {
                tv_next.setEnabled(true);
            } else {
                tv_next.setEnabled(false);
            }
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    @Override
    protected void onResume() {
        super.onResume();

        tvChooseType.setText(PreferenceUtils.getString(this, "details"));

        try {
            String price = PreferenceUtils.getString(this, "price");
            if (price != null) {
                if (!price.equals("")) {
                    String newPrice = price.replace("万", "");
                    double v = 0;
                    try {
                        v = Float.parseFloat(newPrice);
                    } catch (NumberFormatException e) {
                        v = Float.parseFloat(newPrice.split("~")[0]);
                    }
                    double v1 = v * 10000;
                    int a = (int) v1;
                    String y = a + "";
                    etExamplePrice.setText(y);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            etExamplePrice.setHint("请输入");
        }

    }

}
