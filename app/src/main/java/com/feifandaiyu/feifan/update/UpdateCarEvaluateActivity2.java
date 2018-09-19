package com.feifandaiyu.feifan.update;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.personalloan.CarTypeMessageActivity;
import com.feifandaiyu.feifan.adapter.DialogAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CarEvaluate2Bean;
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
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * @author houdaichang
 */
public class UpdateCarEvaluateActivity2 extends BaseActivity implements TextWatcher {

    @InjectView(R.id.bt_next)
    Button btNext;
    @InjectView(R.id.tv_car_type)
    TextView tvCarType;
    @InjectView(R.id.et_car_vin)
    PowerfulEditText etCarVin;
    @InjectView(R.id.tv_run_time)
    TextView tvRunTime;
    @InjectView(R.id.et_run_distance)
    PowerfulEditText etRunDistance;
    @InjectView(R.id.et_car_location)
    PowerfulEditText etCarLocation;
    @InjectView(R.id.et_zhidao_price)
    PowerfulEditText etZhidaoPrice;
    @InjectView(R.id.et_shiji_price)
    PowerfulEditText etShijiPrice;
    @InjectView(R.id.et_car_color)
    PowerfulEditText etCarColor;
    @InjectView(R.id.et_chepai_num)
    TextView etChepaiNum;
    @InjectView(R.id.rb_man_speed)
    RadioButton rbManSpeed;
    @InjectView(R.id.rb_auto_speed)
    RadioButton rbAutoSpeed;
    @InjectView(R.id.rg_isauto)
    RadioGroup rgIsauto;
    @InjectView(R.id.tv_choose_bank)
    TextView tvChooseBank;
    @InjectView(R.id.ll_car_bank)
    LinearLayout llCarBank;
    @InjectView(R.id.linearLayout2)
    LinearLayout linearLayout2;

    private TimePickerView pvCustomTime;
    private int year;
    private int month;
    private int date;
    private String userId;
    private InputMethodManager imm;
    private KProgressHUD hud;
    private String carLoan;
    private String flag;
    private List<DialogBankBean.ListBean> list;
    private String bank;
    private DialogAdapter adapter;
    private String showBrand;
    private String showCarSize;
    private String showCarSeries;
    private String carDealer;
    private String carId;
    private String chehangId;
    private String delerId;
    private String saleID;

    @Override
    protected int getContentView() {
        return R.layout.activity_car_evaluate2;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

        showBack(true);

        showNext(false);

        setTitle("车辆评估");
        btNext.setText("保存");

        hud = KProgressHUD.create(UpdateCarEvaluateActivity2.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍候")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        String estate = PreferenceUtils.getString(this, "estate");

        if (estate.equals("0") || estate.equals("-1")) {
            btNext.setVisibility(View.VISIBLE);
        } else {
            btNext.setVisibility(View.GONE);
            MyToast.showLong(this, "当前车辆正在评估或已评估完成，不可修改车辆信息");
        }

        etCarLocation.addTextChangedListener(this);
        etCarVin.addTextChangedListener(this);
        etRunDistance.addTextChangedListener(this);
        tvRunTime.addTextChangedListener(this);
        etZhidaoPrice.addTextChangedListener(this);
        etShijiPrice.addTextChangedListener(this);
        etCarColor.addTextChangedListener(this);
        etChepaiNum.addTextChangedListener(this);

        userId = PreferenceUtils.getString(UpdateCarEvaluateActivity2.this, "userId");
        carLoan = PreferenceUtils.getString(UpdateCarEvaluateActivity2.this, "carcarLoan");
        flag = PreferenceUtils.getString(UpdateCarEvaluateActivity2.this, "flag");
        carId = PreferenceUtils.getString(this, "carId");
        saleID = PreferenceUtils.getString(this, "saleID");

        Time t = new Time("GMT+8");
        t.setToNow(); // 取得系统时间。
        year = t.year;
        month = t.month;
        date = t.monthDay;
        hud.show();

        initData();

    }

    private void initData() {

        LogUtils.e(carId);

        post()
                .url(Constants.URLS.BASEURL + "UserInfo/carInfo")
                .addParams("carId", carId)
                .addParams("status", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("updateCarEv2Show----------------->>>>>>." + e);
                        hud.dismiss();
                        MyToast.show(UpdateCarEvaluateActivity2.this, "联网失败...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("updateCarEv2Show----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();
                        CarTypeActivityBean bean = gson.fromJson(json, CarTypeActivityBean.class);

                        hud.dismiss();

                        if (bean.getCode() == 1) {

                            showBrand = bean.getList().getBrand();
                            showCarSize = bean.getList().getCarSize();
                            showCarSeries = bean.getList().getCarSeries();
                            tvCarType.setText(showCarSize);

                            carDealer = bean.getList().getCarDealer();
                            if (carDealer.equals("-1")) {
                                tvChooseBank.setText("未选择");
                            } else {
                                tvChooseBank.setText(carDealer);
                            }

                            delerId = bean.getList().getDelerId();

                            etZhidaoPrice.setText(bean.getList().getGuidePrice());

//                            String realPrice = PreferenceUtils.getString(UpdateCarEvaluateActivity2.this, "loanPrice");

                            etShijiPrice.setText(bean.getList().getSalePrice());

                            etCarColor.setText(bean.getList().getCarColor());

                            if (bean.getList().getFcardTime().equals("-1")) {
                                tvRunTime.setHint("请选择");
                            } else {
                                tvRunTime.setText(bean.getList().getFcardTime());
                            }

                            if (bean.getList().getMileage().equals("-1")) {
                                etRunDistance.setHint("请选择");
                            } else {
                                etRunDistance.setText(bean.getList().getMileage());
                            }

                            if (bean.getList().getLocation().equals("-1")) {
                                etCarLocation.setHint("请输入");
                            } else {
                                etCarLocation.setText(bean.getList().getLocation());
                            }

                            if (bean.getList().getGearbox().equals("0")) {
                                rbAutoSpeed.setChecked(true);
                            } else if (bean.getList().getGearbox().equals("1")) {
                                rbManSpeed.setChecked(true);
                            }

                            etCarVin.setText(bean.getList().getVinCode());
                            etChepaiNum.setText(bean.getList().getLicenseNum());

                                tvCarType.setTextColor(Color.GRAY);
                                tvCarType.setEnabled(false);
                                etZhidaoPrice.setTextColor(Color.GRAY);
                                etZhidaoPrice.setEnabled(false);
                                etShijiPrice.setTextColor(Color.GRAY);
                                etShijiPrice.setEnabled(false);
                                tvChooseBank.setTextColor(Color.GRAY);
                                tvChooseBank.setEnabled(false);

                        } else if (bean.getCode() == 0) {
                            new AlertDialog.Builder(UpdateCarEvaluateActivity2.this)

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


    @OnClick({R.id.iv_back, R.id.bt_next, R.id.tv_run_time, R.id.tv_choose_bank, R.id.et_chepai_num})
    public void onViewClicked(View view) {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {
            case R.id.iv_back:

                PreferenceUtils.setString(UpdateCarEvaluateActivity2.this, "details", "");
                PreferenceUtils.setString(UpdateCarEvaluateActivity2.this, "cars", "");
                PreferenceUtils.setString(UpdateCarEvaluateActivity2.this, "carBrand", "");
                PreferenceUtils.setString(UpdateCarEvaluateActivity2.this, "price", "");

                new AlertDialog.Builder(this)
                        .setMessage("是否返回？")
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

            case R.id.tv_choose_bank:
                showDialog();
                break;

            case R.id.et_chepai_num:
                VehiclePlateKeyboard keyboard = new VehiclePlateKeyboard(UpdateCarEvaluateActivity2.this, new OnKeyActionListener() {
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
            case R.id.bt_next:

                if (tvCarType.getText().toString().equals("") || tvCarType.getText() == null) {
                    MyToast.show(this, "请选择车型");
                    return;
                }

//                if (Double.parseDouble(etZhidaoPrice.getText().toString()) > Double.parseDouble(etShijiPrice.getText().toString())) {
                postData();
//                } else {
//                    MyToast.show(this, "发票价应该小于指导价");
//                }

                break;

            case R.id.tv_run_time:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                showCarTimeDialog();
                break;
            default:
        }
    }

    private void postData() {
        hud.show();

//                PreferenceUtils.setString(UpdateCarEvaluateActivity2.this, "isNew", "1");

        String brand = PreferenceUtils.getString(UpdateCarEvaluateActivity2.this, "carBrand");
        String carSeries = PreferenceUtils.getString(UpdateCarEvaluateActivity2.this, "cars");
        String carSize = PreferenceUtils.getString(UpdateCarEvaluateActivity2.this, "details");

        PreferenceUtils.setString(UpdateCarEvaluateActivity2.this, "realPrice", etShijiPrice.getText().toString().trim());

        System.out.println("bank" + bank);

        post()
                //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                .url(Constants.URLS.BASEURL + "userUpdate/editCar")
                .addParams("carId", carId)
                .addParams("status", "1")
                .addParams("carDealer", chehangId == null ? delerId : chehangId.toString()) // 车行id
                .addParams("brand", brand.equals("") || brand == null ? showBrand : brand)
                .addParams("carSeries", carSeries.equals("") || carSeries == null ? showCarSeries : carSeries)
                .addParams("carSize", carSize.equals("") || carSize == null ? showCarSize : carSize)
                .addParams("vinCode", etCarVin.getText().toString())
                .addParams("fcardTime", tvRunTime.getText().toString())
                .addParams("mileage", etRunDistance.getText().toString())
                .addParams("location", etCarLocation.getText().toString())
                .addParams("guidePrice", etZhidaoPrice.getText().toString())
                .addParams("salePrice", etShijiPrice.getText().toString())
                .addParams("carColor", etCarColor.getText().toString())
                .addParams("licenseNum", etChepaiNum.getText().toString())
                .addParams("gearbox", rbManSpeed.isChecked() ? "1" : "0")
//                        .addParams("carLoan", "2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("updateCarEv2Edit____________________>>>>>" + e);
                        MyToast.show(UpdateCarEvaluateActivity2.this, "联网失败");
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("updateCarEv2Edit----------------->>>>>>." + response);

                        hud.dismiss();

                        String json = response;
                        Gson gson = new Gson();

                        CarEvaluate2Bean bean = null;
                        try {
                            bean = gson.fromJson(json, CarEvaluate2Bean.class);
                        } catch (JsonSyntaxException e) {
                            MyToast.show(UpdateCarEvaluateActivity2.this, "联网失败");
                        }

                        if (bean != null) {
                            if (bean.getCode() == 1) {

//                                        PreferenceUtils.setString(CarEvaluateActivity2.this, "loanPrice", etShijiPrice.getText().toString().trim());
//                                        String carId = bean.getList();
//                                        PreferenceUtils.setBoolean(UpdateCarEvaluateActivity2.this, userId, true);
//                                        PreferenceUtils.setString(UpdateCarEvaluateActivity2.this, "carId", carId);
//                                        PreferenceUtils.setString(UpdateCarEvaluateActivity2.this, "isNew", "2");
//                                        startActivity(new Intent(UpdateCarEvaluateActivity2.this, Cost3Activity.class));

                                PreferenceUtils.setString(UpdateCarEvaluateActivity2.this, "details", "");
                                PreferenceUtils.setString(UpdateCarEvaluateActivity2.this, "cars", "");
                                PreferenceUtils.setString(UpdateCarEvaluateActivity2.this, "carBrand", "");
                                PreferenceUtils.setString(UpdateCarEvaluateActivity2.this, "price", "");

                                new AlertDialog.Builder(UpdateCarEvaluateActivity2.this)
                                        .setTitle("车辆信息保存成功")
                                        .setMessage("\n" + "是否提交给评估师？")
                                        .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                hud.show();
                                                toEvaluate();
                                            }
                                        })
                                        .setNegativeButton("仅保存", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                                // // 设置过渡动画
                                                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                                overridePendingTransition(enterAnim0, exitAnim0);
                                            }
                                        })
                                        .show()
                                        .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
                            } else if (bean.getCode() == 0) {

                                MyToast.show(UpdateCarEvaluateActivity2.this, bean.getMsg());

                            }
                        }
                    }
                });
    }

    private void toEvaluate() {
        LogUtils.i("toEvaluate------------------>>>" + carId);
        post().url(Constants.URLS.BASEURL + "Appraiser/resubmit")
                .addParams("id", carId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("toEvaluate------------------>>>" + e);
                        hud.dismiss();
                        MyToast.show(UpdateCarEvaluateActivity2.this, "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("toEvaluate------------------>>>" + response);
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        DialogBankBean bean = gson.fromJson(json, DialogBankBean.class);
                        if (bean.getCode() == 1) {
                            MyToast.show(UpdateCarEvaluateActivity2.this, bean.getMsg());
                            finish();
                            // // 设置过渡动画
                            int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                            int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                            overridePendingTransition(enterAnim0, exitAnim0);
                        } else {
                            MyToast.show(UpdateCarEvaluateActivity2.this, bean.getMsg());
                        }
                    }
                });

    }


    private void showDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);//将xml布局转换为view
        View view = inflater.inflate(R.layout.dialog_bank, null);//将xml布局转换为view,里面有listview
        final ListView listView = (ListView) view.findViewById(R.id.pop_bank);

        final Dialog builder = new Dialog(UpdateCarEvaluateActivity2.this);
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

                        PreferenceUtils.setString(UpdateCarEvaluateActivity2.this, "rebate", rebate);

                        adapter = new DialogAdapter(UpdateCarEvaluateActivity2.this, list);
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


    private void showCarTimeDialog() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1950, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year, month, date);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvRunTime.setText(getTime(date));
            }
        })
                .setLineSpacingMultiplier(1.5f)//设置两横线之间的间隔倍数
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);

                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });


                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })


                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.DKGRAY)
                .build();

        pvCustomTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
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

        if (!StringUtils.isEmpty(etRunDistance.getText().toString())
                && !StringUtils.isEmpty(etCarVin.getText().toString())
                && !StringUtils.isEmpty(etCarLocation.getText().toString())
                && !StringUtils.isEmpty(tvRunTime.getText().toString())
                && !StringUtils.isEmpty(etChepaiNum.getText().toString())
                && !StringUtils.isEmpty(etCarColor.getText().toString())
                && !StringUtils.isEmpty(etZhidaoPrice.getText().toString())
                && !StringUtils.isEmpty(etShijiPrice.getText().toString())
                )

        {
            btNext.setEnabled(true);
        } else {
            btNext.setEnabled(false);
        }
    }


    @OnClick(R.id.tv_car_type)
    public void onViewClicked() {

        startActivity(new Intent(this, CarTypeMessageActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();

        String details = PreferenceUtils.getString(UpdateCarEvaluateActivity2.this, "details");

        tvCarType.setText(details);

        String price = PreferenceUtils.getString(this, "price");

        System.out.println(price + "================");

        if (price != null) {
            if (!price.equals("")) {
                String newPrice = price.replace("万", "");
                double v = 0;
                try {
                    v = Double.parseDouble(newPrice);
                } catch (NumberFormatException e) {
                    v = Double.parseDouble(newPrice.split("~")[0]);
                }
                double v1 = v * 10000;
                int a = (int) v1;
                String y = a + "";
                etZhidaoPrice.setText(y);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
