package com.feifandaiyu.feifan.activity.carloan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.personalloan.UpLoadCarImageActivity;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CarDetailBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;


public class CarDetailActivity extends BaseActivity {

    @InjectView(R.id.tv_brand)
    TextView tvBrand;
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
    @InjectView(R.id.tv_yewu_remarks)
    TextView tvYewuRemarks;
    @InjectView(R.id.tv_pinggu_price)
    TextView tvPingguPrice;
    @InjectView(R.id.et_remarks)
    TextView etRemarks;
    @InjectView(R.id.tv_chejang)
    TextView tvChejang;
    @InjectView(R.id.tv_chepai)
    TextView tvChepai;
    @InjectView(R.id.tv_zhidao)
    TextView tvZhidao;
    @InjectView(R.id.et_remarks_yewu)
    TextView etRemarksYewu;
    private ImageView iv_back;
    private List<CarDetailBean.ListBean> list;
    private CarDetailBean bean;
    private TextView tv;
    private KProgressHUD hud;

    @OnClick({R.id.tv_car_pic, R.id.tv_yewu_remarks})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_car_pic:
                Intent intent = new Intent(CarDetailActivity.this, CarPic1Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CarDetailBean", bean);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_yewu_remarks:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        new AlertDialog.Builder(CarDetailActivity.this)
                .setMessage(list == null ? "未填写" : list.get(0).getRemarks() == null ? "无" : list.get(0).getRemarks())
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_car_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("车辆详情");
        showNext(false);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在加载")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv = (TextView) findViewById(R.id.tv_car_pic);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim0, exitAnim0);
            }
        });

        initData();

    }

    private void initData() {
        String carId = PreferenceUtils.getString(CarDetailActivity.this, "carId");

        LogUtils.e(carId);

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Login/showCar")
                .addParams("carId", carId)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("CarDetailActivity____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(CarDetailActivity.this, "加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("CarDetailActivity----------------->>>>>>" + response);
                        String json = response;
                        Gson gson = new Gson();
                        hud.dismiss();
                        try {
                            bean = gson.fromJson(json, CarDetailBean.class);
                            list = bean.getList();
                            LogUtils.i(list.size() + "---------------->>>>>>>>");

                            tvBrand.setText(list.get(0).getCar_size());
                            tvCarLocation.setText(list.get(0).getLocation() == null ? "暂未填写" : list.get(0).getLocation());
                            tvFirstTime.setText(list.get(0).getTimes() == null ? "暂未填写" : list.get(0).getTimes());
                            tvPingguPrice.setText(list.get(0).getValuation_price() == null ? "评估师暂未评估" : list.get(0).getValuation_price());
                            tvRunDis.setText(list.get(0).getMileage() == null ? "暂未填写" : list.get(0).getMileage());
                            tvVin.setText(list.get(0).getEngine_code());
                            tvChepai.setText(list.get(0).getLicense_num());
                            tvZhidao.setText(list.get(0).getGuidePrice());
                            etRemarks.setText(list.get(0).getAppraiser_remark());
                            tvChejang.setText(list.get(0).getCar_dealer());
                            etRemarksYewu.setText(list.get(0).getRemarks());

                            try {
                                tv.setText(bean.getList().get(0).getAppenhance_pic().size() + bean.getList().get(0).getChassis_pic().size() + bean.getList().get(0).getControl_pic().size() + bean.getList().get(0).getStructure_pic().size() + "张"
                                );
                            } catch (Exception e) {
                                tv.setText(0 + "张");
                            }

                        } catch (JsonSyntaxException e) {
                            new AlertDialog.Builder(CarDetailActivity.this)
                                    .setMessage("请上传车辆图片")
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    startActivity(new Intent(CarDetailActivity.this, UpLoadCarImageActivity.class));
                                                    finish();
                                                }
                                            }
                                    )
                                    .show();
                        }
                    }
                });
    }
}