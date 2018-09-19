package com.feifandaiyu.feifan.activity.evalutor;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CarDetailBean;
import com.feifandaiyu.feifan.bean.WriteBean;
import com.feifandaiyu.feifan.bean.ZuoFeiBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigInput;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

public class PingGuShiPingGuActivity extends BaseActivity implements View.OnClickListener, TextWatcher {


    @InjectView(R.id.tv_brand)
    TextView tvBrand;
    @InjectView(R.id.tv_chejang)
    TextView tvChejang;
    @InjectView(R.id.tv_zhidao)
    TextView tvZhidao;
    @InjectView(R.id.tv_chepai)
    TextView tvChepai;
    @InjectView(R.id.tv_vin)
    TextView tvVin;
    @InjectView(R.id.tv_first_time)
    TextView tvFirstTime;
    @InjectView(R.id.tv_run_dis)
    TextView tvRunDis;
    @InjectView(R.id.tv_car_location)
    TextView tvCarLocation;
    @InjectView(R.id.tv_car_pic)
    TextView tvCarPic;
    @InjectView(R.id.et_remarks_yewu)
    TextView etRemarksYewu;
    @InjectView(R.id.et_pinggu_price)
    PowerfulEditText etPingguPrice;
    @InjectView(R.id.et_remarks)
    EditText etRemarks;
    @InjectView(R.id.bt_bohui)
    Button btBohui;
    @InjectView(R.id.bt_commit)
    Button btCommit;
    @InjectView(R.id.tv_color)
    TextView tvColor;
    @InjectView(R.id.tv_gearbox)
    TextView tvGearbox;
    private TextView tv_commit;
    private ImageView iv_back;
    private List<CarDetailBean.ListBean> list;
    private String dpgCarId;
    private CarDetailBean bean;
    private KProgressHUD hud;
    private String saleID;

    @Override
    protected int getContentView() {
        return R.layout.activity_pinggushi_pinggu;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

        setTitle("车辆评估");
//        tv_commit = (TextView) findViewById(tv_next);
//        tv_commit.setText("提交");
        showNext(false);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        iv_back = (ImageView) findViewById(R.id.iv_back);

        dpgCarId = PreferenceUtils.getString(PingGuShiPingGuActivity.this, "DpgCarId");
        saleID = PreferenceUtils.getString(this, "saleID");

        etPingguPrice.addTextChangedListener(this);
        etRemarks.addTextChangedListener(this);

        iv_back.setOnClickListener(this);
        btBohui.setOnClickListener(this);
        btCommit.setOnClickListener(this);
        tvCarPic.setOnClickListener(this);

        initData();


    }


    private void initData() {

//        LogUtils.d(dpgCarId);

        post()
                .url(Constants.URLS.BASEURL + "Login/showCar")
                .addParams("carId", dpgCarId)
                .build()
                .execute(new StringCallback() {

                    private String areaname;

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("pinggushipinggu____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(PingGuShiPingGuActivity.this, "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("pinggushipinggu----------------->>>>>>" + response);
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();

                        bean = gson.fromJson(json, CarDetailBean.class);

                        list = bean.getList();

                        tvBrand.setText(list.get(0).getCar_size());
                        tvVin.setText(list.get(0).getEngine_code());
                        tvFirstTime.setText(list.get(0).getTimes());
                        tvRunDis.setText(list.get(0).getMileage());
                        tvCarLocation.setText(list.get(0).getLocation());
                        tvChejang.setText(list.get(0).getCar_dealer());
                        tvChepai.setText(list.get(0).getLicense_num());
                        tvZhidao.setText(list.get(0).getGuidePrice());

                        tvColor.setText(list.get(0).getCar_color());
                        tvGearbox.setText(list.get(0).getGearbox().equals("0") ? "自动" : "手动");

                        etRemarksYewu.setText(list.get(0).getRemarks());

                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_commit:
                hud.show();
                postData();
                break;
            case R.id.bt_bohui:
                final CircleDialog.Builder builder = new CircleDialog.Builder(PingGuShiPingGuActivity.this);
                builder.configInput(new ConfigInput() {
                    @Override
                    public void onConfig(InputParams params) {
                        params.inputHeight = 150;
                        params.textSize = 40;
                    }
                })
                        .setInputHint("请输入驳回原因")
                        .setTitle("是否驳回？")
                        .setPositiveInput("确定", new OnInputClickListener() {
                            @Override
                            public void onClick(String text, View v) {

                                hud.show();

                                OkHttpUtils.post()
                                        .url(Constants.URLS.BASEURL + "Appraiser/reject")
                                        .addParams("remark", text)
                                        .addParams("appraiserId", saleID)
                                        .addParams("carId", dpgCarId)
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onError(Call call, Exception e, int id) {
                                                System.out.println("bohui____________________>>>>>" + e);
                                                hud.dismiss();
                                                MyToast.show(PingGuShiPingGuActivity.this, "加载失败");
                                            }

                                            @Override
                                            public void onResponse(String response, int id) {
                                                LogUtils.e("bohui----------------->>>>>>" + response);
                                                String json = response;
                                                Gson gson = new Gson();
                                                ZuoFeiBean bean = null;

                                                try {
                                                    bean = gson.fromJson(json, ZuoFeiBean.class);
                                                } catch (JsonSyntaxException e) {
                                                    MyToast.show(PingGuShiPingGuActivity.this, "服务器错误");
                                                }

                                                if (bean.getCode() == 1) {

                                                    finish();

                                                } else if (bean.getCode() == 0) {
                                                    MyToast.show(PingGuShiPingGuActivity.this, bean.getMsg());
                                                }

                                                hud.dismiss();
                                            }
                                        });

                            }
                        })
                        .setNegative("取消", null)
                        .show();
                break;
            case R.id.iv_back:
                finish();
                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim0, exitAnim0);
                break;
            case R.id.tv_car_pic:
                Intent intent = new Intent(PingGuShiPingGuActivity.this, CarPic2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CarDetailBean", bean);
                intent.putExtras(bundle);
                startActivity(intent);

                finish();
                break;
            case R.id.tv_yewu_remarks:
                new AlertDialog.Builder(PingGuShiPingGuActivity.this)
                        .setMessage(list == null ? "未填写" : list.get(0).getRemarks())
                        .show();
                break;

        }

    }

    private void postData() {

        System.out.println("carid=" + dpgCarId + "appraiser_remark=" + etRemarks.getText().toString().trim()
                + "valuation_price=" + etPingguPrice.getText().toString().trim());

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Company/editusedcar")
                .addParams("carid", dpgCarId)
                .addParams("appraiser_remark", etRemarks.getText().toString().trim())
                .addParams("valuation_price", etPingguPrice.getText().toString().trim())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("pinggushipingguPost____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(PingGuShiPingGuActivity.this, "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("pinggushipingguPost----------------->>>>>>" + response);
                        hud.dismiss();
                        String json = response;

                        Gson gson = new Gson();

                        WriteBean bean = gson.fromJson(json, WriteBean.class);

                        if (bean.getCode() == 1) {
                            finish();
                        } else {
                            MyToast.show(PingGuShiPingGuActivity.this, bean.getMsg());
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
        if (!StringUtils.isEmpty(etRemarks.getText().toString())
                && !StringUtils.isEmpty(etPingguPrice.getText().toString())
                )

        {
            btCommit.setEnabled(true);
        } else {
            btCommit.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }
}
