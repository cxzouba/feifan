package com.feifandaiyu.feifan.activity.evalutor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.ReportBean;
import com.feifandaiyu.feifan.bean.WriteBean;
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

import static com.zhy.http.okhttp.OkHttpUtils.post;

public class PingGuShiWriteActivity extends BaseActivity implements TextWatcher {


    @InjectView(R.id.et_fadongji)
    PowerfulEditText etFadongji;
    @InjectView(R.id.et_cartype)
    PowerfulEditText etCartype;
    @InjectView(R.id.textView8)
    TextView textView8;
    @InjectView(R.id.et_guochan)
    PowerfulEditText etGuochan;
    @InjectView(R.id.et_heding)
    PowerfulEditText etHeding;
    @InjectView(R.id.et_pailiang)
    PowerfulEditText etPailiang;
    @InjectView(R.id.et_gonglu)
    PowerfulEditText etGonglu;
    @InjectView(R.id.et_fire_type)
    PowerfulEditText etFireType;
    @InjectView(R.id.tv_chuchang_time)
    TextView tvChuchangTime;
    @InjectView(R.id.tv_dengji_time)
    TextView tvDengjiTime;
    @InjectView(R.id.et_use_year)
    PowerfulEditText etUseYear;
    @InjectView(R.id.et_weixiu)
    PowerfulEditText etWeixiu;
    @InjectView(R.id.et_zhuangtai)
    PowerfulEditText etZhuangtai;
    @InjectView(R.id.et_chongzhi)
    PowerfulEditText etChongzhi;
    @InjectView(R.id.et_chengxinlv)
    PowerfulEditText etChengxinlv;
    @InjectView(R.id.et_shichang)
    PowerfulEditText etShichang;
    @InjectView(R.id.et_mudi)
    PowerfulEditText etMudi;
    @InjectView(R.id.et_jiegou)
    EditText etJiegou;
    @InjectView(R.id.et_jishu)
    EditText etJishu;
    @InjectView(R.id.et_remarks)
    EditText etRemarks;
    private TimePickerView pvCustomTime;
    private TextView tv_next;
    private ReportBean.ListBean.CarBean carList;
    private ReportBean bean;
    private String car_size;
    private String car_color;
    private String license_num;
    private String mileage;
    private String newcar_price;
    private String vin_code;
    private String ypgCarId;
    private String pingGuShiId;
    private KProgressHUD hud;
    private int year;
    private int month;
    private int date;

    @Override
    protected int getContentView() {
        return R.layout.activity_pinggushi_write;

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

        setTitle("评估报告");

        tv_next = (TextView) findViewById(R.id.tv_next);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在加载")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        etFireType.addTextChangedListener(this);
        etZhuangtai.addTextChangedListener(this);
        etCartype.addTextChangedListener(this);
        etChengxinlv.addTextChangedListener(this);
        etFadongji.addTextChangedListener(this);
        etHeding.addTextChangedListener(this);
        etGuochan.addTextChangedListener(this);
        etRemarks.addTextChangedListener(this);
        etChongzhi.addTextChangedListener(this);
        etUseYear.addTextChangedListener(this);
        etGonglu.addTextChangedListener(this);
        etJiegou.addTextChangedListener(this);
        etShichang.addTextChangedListener(this);
        etWeixiu.addTextChangedListener(this);
        etJishu.addTextChangedListener(this);
        etMudi.addTextChangedListener(this);
        etPailiang.addTextChangedListener(this);

        tvChuchangTime.addTextChangedListener(this);
        tvDengjiTime.addTextChangedListener(this);

        ypgCarId = PreferenceUtils.getString(PingGuShiWriteActivity.this, "YPGCarId");
        pingGuShiId = PreferenceUtils.getString(PingGuShiWriteActivity.this, "saleID");


        initData();

        Time t = new Time("GMT+8");
        t.setToNow(); // 取得系统时间。
        year = t.year;
        month = t.month;
        date = t.monthDay;


    }

    private void initData() {
        post()
                .url(Constants.URLS.BASEURL + "Login/carReport")
                .addParams("carId", ypgCarId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("Report------------------>>>" + e);
                        hud.dismiss();
                        MyToast.show(PingGuShiWriteActivity.this, "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("Report------------------>>>" + response);
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        bean = gson.fromJson(json, ReportBean.class);

                        if (bean.getCode() == 1) {

                            carList = bean.getList().getCar();

                            car_size = carList.getCar_size();
                            car_color = carList.getCar_color();
                            license_num = carList.getLicense_num();
                            mileage = carList.getMileage();
                            newcar_price = carList.getNewcar_price();
                            vin_code = carList.getVin_code();

                        } else {
                            MyToast.show(PingGuShiWriteActivity.this, bean.getMsg());
                        }

                    }
                });
    }

    @OnClick({R.id.tv_next, R.id.iv_back, R.id.tv_chuchang_time, R.id.tv_dengji_time,
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                hud.show();
                postData();

                break;
            case R.id.tv_chuchang_time:
                showChuChangTimeDialog();
                break;
            case R.id.tv_dengji_time:
                showDengJiTimeDialog();
                break;
            case R.id.iv_back:
                finish();
                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim0, exitAnim0);
                break;
        }
    }

    private void postData() {
        String faDongji = etFadongji.getText().toString();
        String carType = etCartype.getText().toString();
        String guoChan = etGuochan.getText().toString();
        String heDing = etHeding.getText().toString();
        String paiLiang = etPailiang.getText().toString();
        String gongLv = etGonglu.getText().toString();
        String ranLiao = etFireType.getText().toString();
        String chuChang = tvChuchangTime.getText().toString();
        String dengJi = tvDengjiTime.getText().toString();
        String jieGou = etJiegou.getText().toString();
        String jiShu = etJishu.getText().toString();
        String weiXiu = etWeixiu.getText().toString();
        String zhuangTai = etZhuangtai.getText().toString();
        String chengXinLv = etChengxinlv.getText().toString();
        String shiChang = etShichang.getText().toString();
        String muDi = etMudi.getText().toString();
        String remarks = etRemarks.getText().toString();
        String useYear = etUseYear.getText().toString().trim();

        System.out.println(ypgCarId + "========" + pingGuShiId + "========" + car_size + "========" + vin_code + "========"
                + license_num + "========" + faDongji + "========" + carType + "========" + car_color + "========" + guoChan
                + "========" + heDing + "========" + paiLiang + "========" + gongLv + "========" + ranLiao + "========" + chuChang
                + "========" + dengJi + "========" + useYear + "========" + mileage + "========" + jieGou + "========" + jiShu
                + "========" + weiXiu + "========" + zhuangTai + "========" + newcar_price + "========" + chengXinLv + "========"
                + shiChang + "========" + muDi + "========" + remarks);

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Login/reportSave")
                .addParams("carInfo", car_size)
                .addParams("vinCode", vin_code)
                .addParams("carId", ypgCarId)
                .addParams("registCode", license_num)
                .addParams("engineCode", faDongji)
                .addParams("carType", carType)
                .addParams("carColor", car_color)
                .addParams("source", guoChan)
                .addParams("passengers", heDing)
                .addParams("displacement", paiLiang)
                .addParams("power", gongLv)
                .addParams("fuel", ranLiao)
                .addParams("pTime", chuChang)
                .addParams("regTime", dengJi)
                .addParams("serviceLife", useYear)
                .addParams("mileage", mileage)
                .addParams("design", jieGou)
                .addParams("tecSituation", jiShu)
                .addParams("repair", weiXiu)
                .addParams("nowStatus", zhuangTai)
                .addParams("newcarPrice", newcar_price)
                .addParams("newRate", chengXinLv)
                .addParams("marketPrice", shiChang)
                .addParams("objective", muDi)
                .addParams("remarks", remarks)
                .addParams("appraiserId", pingGuShiId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("write____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(PingGuShiWriteActivity.this, "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        LogUtils.e("write----------------->>>>>>." + response);

                        hud.dismiss();

                        String json = response;

                        Gson gson = new Gson();

                        WriteBean bean = gson.fromJson(json, WriteBean.class);

                        String ReportId = bean.getList();

                        PreferenceUtils.setString(PingGuShiWriteActivity.this, "ReportId", ReportId);

                        System.out.println("ReportId=" + ReportId);

                        if (bean.getCode() == 1) {
                            startActivity(new Intent(PingGuShiWriteActivity.this, EvaluaterDrawNameActivity.class));
                            finish();
                            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                            overridePendingTransition(enterAnim6, exitAnim6);
                        } else {
                            MyToast.show(PingGuShiWriteActivity.this, bean.getMsg());
                        }
                    }
                });
    }

    private void showChuChangTimeDialog() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1950, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year, month, date);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvChuchangTime.setText(getTime(date));
            }
        })
                .setLineSpacingMultiplier(1.5f)//设置两横线之间的间隔倍数
                .setDate(selectedDate)
                .setSubmitColor(getResources().getColor(R.color.activecolor))
                .setCancelColor(R.color.activecolor)
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

    private void showDengJiTimeDialog() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1950, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year, month, date);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvDengjiTime.setText(getTime(date));
            }
        })
                .setLineSpacingMultiplier(1.5f)//设置两横线之间的间隔倍数
                .setDate(selectedDate)
                .setSubmitColor(getResources().getColor(R.color.activecolor))
                .setCancelColor(R.color.activecolor)
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!StringUtils.isEmpty(tvDengjiTime.getText().toString())
                && !StringUtils.isEmpty(tvChuchangTime.getText().toString())
                && !StringUtils.isEmpty(etUseYear.getText().toString())
                && !StringUtils.isEmpty(etPailiang.getText().toString())
                && !StringUtils.isEmpty(etCartype.getText().toString())
                && !StringUtils.isEmpty(etFadongji.getText().toString())
                && !StringUtils.isEmpty(etFireType.getText().toString())
                && !StringUtils.isEmpty(etHeding.getText().toString())
                && !StringUtils.isEmpty(etGuochan.getText().toString())
                && !StringUtils.isEmpty(etRemarks.getText().toString())
                && !StringUtils.isEmpty(etMudi.getText().toString())
                && !StringUtils.isEmpty(etWeixiu.getText().toString())
                && !StringUtils.isEmpty(etChengxinlv.getText().toString())
                && !StringUtils.isEmpty(etChongzhi.getText().toString())
                && !StringUtils.isEmpty(etGonglu.getText().toString())
                && !StringUtils.isEmpty(etZhuangtai.getText().toString())
                && !StringUtils.isEmpty(etJiegou.getText().toString())
                && !StringUtils.isEmpty(etJishu.getText().toString())
                && !StringUtils.isEmpty(etShichang.getText().toString())
                )

        {
            tv_next.setEnabled(true);
        } else {
            tv_next.setEnabled(false);
        }
    }


}
