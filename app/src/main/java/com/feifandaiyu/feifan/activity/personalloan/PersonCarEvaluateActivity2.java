package com.feifandaiyu.feifan.activity.personalloan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CarEvaluate2Bean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

public class PersonCarEvaluateActivity2 extends BaseActivity implements TextWatcher {


    @InjectView(R.id.tv_run_time)
    TextView tvRunTime;
    @InjectView(R.id.et_run_distance)
    PowerfulEditText etRunDistance;
    @InjectView(R.id.et_car_location)
    PowerfulEditText etCarLocation;
    @InjectView(R.id.bt_next)
    Button btNext;
    @InjectView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    private TimePickerView pvCustomTime;
    private String userId;
    private int year;
    private int month;
    private int date;
    private KProgressHUD hud;

    @Override
    protected int getContentView() {
        return R.layout.activity_personal_car_evaluate2;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

        showBack(true);
        showNext(false);
        setTitle("车辆评估");

        hud = KProgressHUD.create(PersonCarEvaluateActivity2.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        userId = PreferenceUtils.getString(this, "userId");

        etCarLocation.addTextChangedListener(this);
        etRunDistance.addTextChangedListener(this);
        tvRunTime.addTextChangedListener(this);
//        tvCarType.addTextChangedListener(this);

        Time t = new Time("GMT+8");
        t.setToNow(); // 取得系统时间。
        year = t.year;
        month = t.month;
        date = t.monthDay;
    }

    @OnClick({R.id.iv_back, R.id.bt_next, R.id.tv_run_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim0, exitAnim0);
                break;
            case R.id.bt_next:

                hud.show();

//                PreferenceUtils.setString(PersonCarEvaluateActivity2.this,"isNew","1");

                String brand = PreferenceUtils.getString(PersonCarEvaluateActivity2.this, "McarBrand");
                String carSeries = PreferenceUtils.getString(PersonCarEvaluateActivity2.this, "Mcars");
                String carSize = PreferenceUtils.getString(PersonCarEvaluateActivity2.this, "Mdetails");

                String vin = PreferenceUtils.getString(PersonCarEvaluateActivity2.this, "vin");
                String chepai = PreferenceUtils.getString(PersonCarEvaluateActivity2.this, "chepai");
                String zhidaoPrice = PreferenceUtils.getString(PersonCarEvaluateActivity2.this, "zhidaoPrice");
                String shijiPrice = PreferenceUtils.getString(PersonCarEvaluateActivity2.this, "shijiPrice");
                String color = PreferenceUtils.getString(PersonCarEvaluateActivity2.this, "color");
                String gearbox = PreferenceUtils.getString(PersonCarEvaluateActivity2.this, "gearbox");

                OkHttpUtils
                        .post()
                        //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                        .url(Constants.URLS.BASEURL + "Login/vehicleEvaluation")
                        .addParams("userId", userId)
                        .addParams("brand", brand)
                        .addParams("carSeries", carSeries)
                        .addParams("carSize", carSize)
                        .addParams("engineCode", vin)
                        .addParams("fcardTime", tvRunTime.getText().toString())
                        .addParams("mileage", etRunDistance.getText().toString())
                        .addParams("location", etCarLocation.getText().toString())
                        .addParams("guidePrice", zhidaoPrice)
                        .addParams("salePrice", shijiPrice)
                        .addParams("carColor", color)
                        .addParams("licenseNum", chepai)
                        .addParams("carLoan", "1")
                        .addParams("gearbox", gearbox)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                System.out.println("PersonCarEvaluateActivity2____________________>>>>>" + e);
                                hud.dismiss();
                                MyToast.show(PersonCarEvaluateActivity2.this, "网络连接失败");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.e("PersonCarEvaluateActivity2----------------->>>>>>." + response);

                                hud.dismiss();

                                String json = response;
                                Gson gson = new Gson();
                                CarEvaluate2Bean bean = gson.fromJson(json, CarEvaluate2Bean.class);

                                if (bean.getCode() == 1) {

                                    String carId = bean.getList();

                                    PreferenceUtils.setString(PersonCarEvaluateActivity2.this, "carId", carId);

                                    PreferenceUtils.setString(PersonCarEvaluateActivity2.this, "carLoan", "1");

                                    startActivity(new Intent(PersonCarEvaluateActivity2.this, UpLoadCarImageActivity.class));

                                    finish();
                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                    overridePendingTransition(enterAnim6, exitAnim6);
                                } else {
                                    MyToast.show(PersonCarEvaluateActivity2.this, bean.getMsg());
                                }
                            }
                        });

                break;
            case R.id.tv_run_time:
                showCarTimeDialog();
                break;
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
                && !StringUtils.isEmpty(etCarLocation.getText().toString())
                && !StringUtils.isEmpty(tvRunTime.getText().toString())
                ) {
            btNext.setEnabled(true);
        } else {
            btNext.setEnabled(false);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        String details = PreferenceUtils.getString(PersonCarEvaluateActivity2.this, "details");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
