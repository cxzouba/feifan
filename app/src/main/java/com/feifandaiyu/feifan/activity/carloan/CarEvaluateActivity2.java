package com.feifandaiyu.feifan.activity.carloan;

import android.app.Activity;
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
import android.view.KeyEvent;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.personalloan.CarTypeMessageActivity;
import com.feifandaiyu.feifan.activity.personalloan.UpLoadCarImageActivity;
import com.feifandaiyu.feifan.adapter.DialogAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CarEvaluate2Bean;
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
public class CarEvaluateActivity2 extends BaseActivity implements TextWatcher {

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
    @InjectView(R.id.ll_car_bank)
    LinearLayout llCarBank;
    @InjectView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @InjectView(R.id.tv_choose_bank)
    TextView tvChooseBank;
    @InjectView(R.id.sv_cartype)
    ScrollView svCartype;

    private TimePickerView pvCustomTime;
    private int year;
    private int month;
    private int date;
    private String userId;
    private InputMethodManager imm;
    private KProgressHUD hud;
    private Activity mActivity;
    //    private KeyboardUtil keyboardUtil;
    private int top;
    private String saleID;
    private List<DialogBankBean.ListBean> list;
    private DialogAdapter adapter;
    private String bank;
    private String rebate;

    @Override
    protected int getContentView() {
        return R.layout.activity_car_evaluate2;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        mActivity = this;

        showBack(true);
        showNext(false);
        setTitle("车辆评估");

        hud = KProgressHUD.create(CarEvaluateActivity2.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

//        keyboardUtil = new KeyboardUtil(mActivity, etChepaiNum);

        saleID = PreferenceUtils.getString(this, "saleID");

//        rbManSpeed.setChecked(true);

        etCarLocation.addTextChangedListener(this);
        etCarVin.addTextChangedListener(this);
        etRunDistance.addTextChangedListener(this);
        tvRunTime.addTextChangedListener(this);
        etZhidaoPrice.addTextChangedListener(this);
        etShijiPrice.addTextChangedListener(this);
        etCarColor.addTextChangedListener(this);
        etChepaiNum.addTextChangedListener(this);

        top = rgIsauto.getTop();

//        etChepaiNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    keyboardUtil.showKeyboard();
//                } else {
//                    keyboardUtil.hideKeyboard();
//                }
//            }
//        });
//
//        etChepaiNum.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                keyboardUtil.hideSoftInputMethod();
//                keyboardUtil.showKeyboard();
//                svCartype.fullScroll(ScrollView.FOCUS_DOWN);
//                return false;
//            }
//        });


        Time t = new Time("GMT+8");
        t.setToNow(); // 取得系统时间。
        year = t.year;
        month = t.month;
        date = t.monthDay;
    }


    @OnClick({R.id.iv_back, R.id.bt_next, R.id.tv_run_time, R.id.et_chepai_num, R.id.tv_choose_bank})
    public void onViewClicked(View view) {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {
            case R.id.iv_back:

                PreferenceUtils.setString(CarEvaluateActivity2.this, "details", "");
                PreferenceUtils.setString(CarEvaluateActivity2.this, "price", "");
                PreferenceUtils.setString(CarEvaluateActivity2.this, "cars", "");
                PreferenceUtils.setString(CarEvaluateActivity2.this, "carBrand", "");

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

            case R.id.bt_next:

                if (tvCarType.getText().toString().equals("") || tvCarType.getText() == null) {
                    MyToast.show(this, "请选择车型");
                    return;
                }

                if (etChepaiNum.getText() != null && !etChepaiNum.getText().toString().equals("") && etChepaiNum.getText().toString().length() < 7) {
                    MyToast.show(this, "车牌号格式不正确");
                    return;
                }

                if (rbAutoSpeed.isChecked() || rbManSpeed.isChecked()) {

                    postData();
                } else {
                    MyToast.show(this, "请选择变速类型");
                }

                break;

            case R.id.tv_run_time:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                showCarTimeDialog();
                break;
            case R.id.tv_choose_bank:
                showDialog();
                break;

            case R.id.et_chepai_num:

                VehiclePlateKeyboard keyboard = new VehiclePlateKeyboard(CarEvaluateActivity2.this, new OnKeyActionListener() {
                    @Override
                    public void onFinish(String input) {
                        if (input.length() == 7) {
                            etChepaiNum.setText(input);
                        } else {
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

            default:
        }

    }

    private void showDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);//将xml布局转换为view
        View view = inflater.inflate(R.layout.dialog_bank, null);//将xml布局转换为view,里面有listview
        final ListView listView = (ListView) view.findViewById(R.id.pop_bank);

        final Dialog builder = new Dialog(CarEvaluateActivity2.this);
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

                        adapter = new DialogAdapter(CarEvaluateActivity2.this, list);
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

    private void postData() {
        //                if (Double.parseDouble(etZhidaoPrice.getText().toString()) > Double.parseDouble(etShijiPrice.getText().toString())) {

        hud.show();

        userId = PreferenceUtils.getString(CarEvaluateActivity2.this, "userId");

        PreferenceUtils.setString(CarEvaluateActivity2.this, "isNew", "2");

        String brand = PreferenceUtils.getString(CarEvaluateActivity2.this, "carBrand");
        String carSeries = PreferenceUtils.getString(CarEvaluateActivity2.this, "cars");
        String carSize = PreferenceUtils.getString(CarEvaluateActivity2.this, "details");

        PreferenceUtils.setString(CarEvaluateActivity2.this, "realPrice", etShijiPrice.getText().toString().trim());

        post()
                //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                .url(Constants.URLS.BASEURL + "Login/vehicleEvaluation")
                .addParams("carDealer", bank == null ? "-1" : bank) // 车行id
                .addParams("brand", brand)
                .addParams("carSeries", carSeries)
                .addParams("carSize", carSize)
                .addParams("engineCode", etCarVin.getText().toString())
                .addParams("fcardTime", tvRunTime.getText().toString())
                .addParams("mileage", etRunDistance.getText().toString())
                .addParams("saleid", saleID)
                .addParams("location", etCarLocation.getText().toString())
                .addParams("guidePrice", etZhidaoPrice.getText().toString())
                .addParams("salePrice", etShijiPrice.getText().toString())
                .addParams("carColor", etCarColor.getText().toString())
                .addParams("licenseNum", etChepaiNum.getText().toString())
                .addParams("gearbox", rbManSpeed.isChecked() ? "1" : "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("carEv2____________________>>>>>" + e);
                        MyToast.show(CarEvaluateActivity2.this, "联网失败");
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("carEv2----------------->>>>>>." + response);

                        hud.dismiss();

                        String json = response;
                        Gson gson = new Gson();

                        CarEvaluate2Bean bean = null;
                        try {
                            bean = gson.fromJson(json, CarEvaluate2Bean.class);
                        } catch (JsonSyntaxException e) {
                            MyToast.show(CarEvaluateActivity2.this, "联网失败");
                        }

                        if (bean != null) {
                            if (bean.getCode() == 1) {

//                                        PreferenceUtils.setString(CarEvaluateActivity2.this, "loanPrice", etShijiPrice.getText().toString().trim());

                                String carId = bean.getList();
                                PreferenceUtils.setBoolean(CarEvaluateActivity2.this, userId, true);
                                PreferenceUtils.setString(CarEvaluateActivity2.this, "carId", carId);
                                PreferenceUtils.setString(CarEvaluateActivity2.this, "details", "");
                                PreferenceUtils.setString(CarEvaluateActivity2.this, "price", "");
                                PreferenceUtils.setString(CarEvaluateActivity2.this, "cars", "");
                                PreferenceUtils.setString(CarEvaluateActivity2.this, "carBrand", "");
                                PreferenceUtils.setString(CarEvaluateActivity2.this, "isNew", "2");
                                startActivity(new Intent(CarEvaluateActivity2.this, UpLoadCarImageActivity.class));
                                finish();
                                int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                overridePendingTransition(enterAnim6, exitAnim6);
                            } else if (bean.getCode() == 0) {

                                MyToast.show(CarEvaluateActivity2.this, bean.getMsg());

                            }
                        }

                    }
                });
//                } else {
//                    MyToast.show(this, "发票价应该小于指导价");
//                }
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

//        if (etChepaiNum.getText().toString().length() == 7) {
//            keyboardUtil.hideKeyboard();
//        }

        if (!StringUtils.isEmpty(etRunDistance.getText().toString())
//                && !StringUtils.isEmpty(etRemarks.getText().toString())
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

        String details = PreferenceUtils.getString(CarEvaluateActivity2.this, "details");

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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (keyboardUtil.isShow()) {
//                keyboardUtil.hideKeyboard();
//            } else {
//                finish();
//            }
//        }
        return false;
    }
}
