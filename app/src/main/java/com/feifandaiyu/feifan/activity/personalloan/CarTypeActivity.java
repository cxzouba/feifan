package com.feifandaiyu.feifan.activity.personalloan;

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
import android.text.format.Time;
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

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.DialogAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CarTypeActivityBean;
import com.feifandaiyu.feifan.bean.DialogBankBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.feifandaiyu.feifan.view.OnKeyActionListener;
import com.feifandaiyu.feifan.view.VehiclePlateKeyboard;
import com.google.gson.Gson;
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
 * Created by houdaichang on 2017/5/8.
 */


public class CarTypeActivity extends BaseActivity implements TextWatcher {


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
    @InjectView(R.id.tv_run_time)
    TextView tvRunTime;
    @InjectView(R.id.et_run_distance)
    PowerfulEditText etRunDistance;
    @InjectView(R.id.et_car_location)
    PowerfulEditText etCarLocation;
    @InjectView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @InjectView(R.id.ll_parent)
    LinearLayout llParent;
    private TextView tv_next;
    private List<DialogBankBean.ListBean> list;
    private DialogAdapter adapter;
    private String id_bank;
    private ProgressDialog progressDialog;
    private String bank;
    private KProgressHUD hud;
    private String creditType;
    private String realPrice;
    private String rebate;
    private Activity mActivity;
    private String saleID;
    private TimePickerView pvCustomTime;
    private int year;
    private int month;
    private int date;
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

        hud = KProgressHUD.create(CarTypeActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);


        creditType = PreferenceUtils.getString(this, "CreditType");

        saleID = PreferenceUtils.getString(this, "saleID");

        tv_next = (TextView) findViewById(R.id.tv_next);

        initTime();

//        keyboardUtil = new KeyboardUtil(mActivity, etChepaiNum);

//        rbNewCar.setChecked(true);
//        rbManSpeed.setChecked(true);

        rbNewCar.addTextChangedListener(this);
        etChepaiNum.addTextChangedListener(this);
        etColor.addTextChangedListener(this);
        etExamplePrice.addTextChangedListener(this);
        etRealPrice.addTextChangedListener(this);
        etVinNum.addTextChangedListener(this);
        tvChooseBank.addTextChangedListener(this);
        tvRunTime.addTextChangedListener(this);
        etRunDistance.addTextChangedListener(this);
        etCarLocation.addTextChangedListener(this);

        rgIsnew.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                LogUtils.e(checkedId+"");
                if (checkedId == rbNewCar.getId()) {
                    linearLayout2.setVisibility(View.GONE);
                } else if (checkedId == rbOldCar.getId()) {
                    linearLayout2.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initTime() {
        Time t = new Time("GMT+8");
        t.setToNow(); // 取得系统时间。
        year = t.year;
        month = t.month;
        date = t.monthDay;
    }


    @OnClick({R.id.tv_choose_bank, R.id.iv_back, R.id.tv_next, R.id.tv_choose_type, R.id.et_chepai_num, R.id.tv_run_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_bank:
                showDialog();
                break;
            case R.id.tv_run_time:
                showCarTimeDialog();
                break;
            case R.id.et_chepai_num:
                VehiclePlateKeyboard keyboard = new VehiclePlateKeyboard(CarTypeActivity.this, new OnKeyActionListener() {
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

                PreferenceUtils.setString(this, "details", "");
                PreferenceUtils.setString(this, "price", "");
                PreferenceUtils.setString(CarTypeActivity.this, "cars", "");
                PreferenceUtils.setString(CarTypeActivity.this, "carBrand", "");

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

            case R.id.tv_next:// TODO: 2017/6/7

                if (tvChooseType.getText().toString().equals("") || tvChooseType.getText() == null) {
                    MyToast.show(this, "请选择车型");
                    return;
                }

                if (etChepaiNum.getText() != null && !etChepaiNum.getText().toString().equals("") && etChepaiNum.getText().toString().length() < 7) {
                    MyToast.show(this, "车牌号格式不正确");
                    return;
                }

                if (rbAutoSpeed.isChecked() || rbManSpeed.isChecked()) {

                } else {
                    MyToast.show(this, "请选择变速类型");
                    return;
                }

                if (rbNewCar.isChecked() || rbOldCar.isChecked()) {
                    if (rbNewCar.isChecked()) {
                        postData();
                    } else {
                        if (rbAutoSpeed.isChecked() || rbManSpeed.isChecked()) {
                            postData();
                        } else {
                            MyToast.show(this, "您有未选择项");
                        }
                    }
                } else {
                    MyToast.show(this, "您有未选择项");
                }


                break;
            case R.id.tv_choose_type:
                startActivity(new Intent(CarTypeActivity.this, CarTypeMessageActivity.class));
                break;

            default:
        }
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

    private void postData() {
//        if (Double.parseDouble(etExamplePrice.getText().toString()) > Double.parseDouble(etRealPrice.getText().toString())) {
        hud.show();

        realPrice = etRealPrice.getText().toString();

        postMessage();
//        } else {
//            MyToast.show(this, "发票价应该小于指导价");
//        }
    }

    private void showDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);//将xml布局转换为view
        View view = inflater.inflate(R.layout.dialog_bank, null);//将xml布局转换为view,里面有listview
        final ListView listView = (ListView) view.findViewById(R.id.pop_bank);

        final Dialog builder = new Dialog(CarTypeActivity.this);
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

                        adapter = new DialogAdapter(CarTypeActivity.this, list);
                        listView.setAdapter(adapter);//给listview设置适配器，adapter
                        adapter.notifyDataSetChanged();
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                bank = list.get(position).getId();

                rebate = list.get(position).getRebate();

                tvChooseBank.setText(list.get(position).getUsername());
                builder.dismiss();
            }
        });

    }

    private void postMessage() {

        System.out.println("rebate=" + PreferenceUtils.getString(CarTypeActivity.this, "rebate"));

        final String brand = PreferenceUtils.getString(CarTypeActivity.this, "carBrand");
        final String carSeries = PreferenceUtils.getString(CarTypeActivity.this, "cars");
        final String carSize = PreferenceUtils.getString(CarTypeActivity.this, "details");

        String guidePrice = etExamplePrice.getText().toString().trim();
        String salePrice = etRealPrice.getText().toString().trim();
        String vinCode = etVinNum.getText().toString().trim();
        String carColor = etColor.getText().toString().trim();
        String licenseNum = etChepaiNum.getText().toString().trim();

        post()
                .url(Constants.URLS.BASEURL + "Login/addCar")
                .addParams("carType", rbNewCar.isChecked() ? "0" : "1")
                .addParams("carDealer", bank == null ? "-1" : bank) // 车行id
                .addParams("brand", brand)
                .addParams("carSize", carSize)
                .addParams("carSeries", carSeries)
                .addParams("guidePrice", guidePrice)
                .addParams("salePrice", salePrice)
                .addParams("vinCode", vinCode)
                .addParams("carColor", carColor)
                .addParams("gearbox", rbManSpeed.isChecked() ? "1" : "0")
                .addParams("saleid", saleID)
                .addParams("licenseNum", licenseNum)
                .addParams("carLoan", "1")
                .addParams("fcardTime", tvRunTime.getText().toString())
                .addParams("mileage", etRunDistance.getText().toString())
                .addParams("location", etCarLocation.getText().toString())
                .addParams("carLoan", "1")
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                        LogUtils.i("cartype------------------>>>" + e);
                        hud.dismiss();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("cartype------------------>>>" + response);

                        hud.dismiss();

                        String json = response;
                        Gson gson = new Gson();
                        CarTypeActivityBean carTypeActivityBean = gson.fromJson(json, CarTypeActivityBean.class);

                        if (carTypeActivityBean.getCode() == 0) {
                            MyToast.show(CarTypeActivity.this, carTypeActivityBean.getMsg());
                            return;
                        }
                        if (carTypeActivityBean.getCode() == 1) {
//                            PreferenceUtils.setString(CarTypeActivity.this, "loanPrice", etloanPrice.getText().toString().trim());
//                            PreferenceUtils.setString(CarTypeActivity.this, "carId", carTypeActivityBean.getCarId());

//                            PreferenceUtils.setString(CarTypeActivity.this, "realPrice", realPrice);
//                            PreferenceUtils.setString(CarTypeActivity.this, "vin", etVinNum.getText().toString());
//                            PreferenceUtils.setString(CarTypeActivity.this, "chepai", etChepaiNum.getText().toString());
//                            PreferenceUtils.setString(CarTypeActivity.this, "zhidaoPrice", etExamplePrice.getText().toString());
//                            PreferenceUtils.setString(CarTypeActivity.this, "shijiPrice", etRealPrice.getText().toString());
//                            PreferenceUtils.setString(CarTypeActivity.this, "color", etColor.getText().toString());
//                            PreferenceUtils.setString(CarTypeActivity.this, "gearbox", rbAutoSpeed.isChecked() ? "0" : "1");

                            PreferenceUtils.setString(CarTypeActivity.this, "price", "");
                            PreferenceUtils.setString(CarTypeActivity.this, "details", "");
                            PreferenceUtils.setString(CarTypeActivity.this, "cars", "");
                            PreferenceUtils.setString(CarTypeActivity.this, "carBrand", "");

//                            PreferenceUtils.setString(CarTypeActivity.this, "Mdetails", carSize);
//                            PreferenceUtils.setString(CarTypeActivity.this, "Mcars", carSeries);
//                            PreferenceUtils.setString(CarTypeActivity.this, "McarBrand", brand);

                            System.out.println("rabit=" + rebate);
                            if (rebate != null) {

                                PreferenceUtils.setString(CarTypeActivity.this, "rebate", rebate);

                            } else {

                                PreferenceUtils.setString(CarTypeActivity.this, "rebate", "0");

                            }

                            System.out.println("rebate" + PreferenceUtils.getString(CarTypeActivity.this, "rebate"));

                            PreferenceUtils.setString(CarTypeActivity.this, "carId", carTypeActivityBean.getCarId());

                            if (rbOldCar.isChecked()) {

                                PreferenceUtils.setString(CarTypeActivity.this, "carLoan", "1");
                                PreferenceUtils.setString(CarTypeActivity.this, "isNew", "2");
                                startActivity(new Intent(CarTypeActivity.this, UpLoadCarImageActivity.class));

                            } else if (rbNewCar.isChecked()) {

                                PreferenceUtils.setString(CarTypeActivity.this, "isNew", "1");
                                startActivity(new Intent(CarTypeActivity.this, Cost1Activity.class));

                            }

                            finish();
                            // // 设置过渡动画
                            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                            overridePendingTransition(enterAnim6, exitAnim6);

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

//        System.out.println("rebate=" + PreferenceUtils.getString(CarTypeActivity.this, "rebate"));

//        if (etChepaiNum.getText().toString().length() == 7) {
//            keyboardUtil.hideKeyboard();
//        }

        if (rbOldCar.isChecked()) {
            if (!StringUtils.isEmpty(etVinNum.getText().toString())
                    && !StringUtils.isEmpty(etRealPrice.getText().toString())
                    && !StringUtils.isEmpty(tvChooseType.getText().toString())
                    && !StringUtils.isEmpty(etChepaiNum.getText().toString())
                    && !StringUtils.isEmpty(etExamplePrice.getText().toString())
                    && !StringUtils.isEmpty(etColor.getText().toString())
                    && !StringUtils.isEmpty(tvRunTime.getText().toString())
                    && !StringUtils.isEmpty(etRunDistance.getText().toString())
                    && !StringUtils.isEmpty(etCarLocation.getText().toString())
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
                            && !StringUtils.isEmpty(tvChooseType.getText().toString())
                            && !StringUtils.isEmpty(etColor.getText().toString())
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
