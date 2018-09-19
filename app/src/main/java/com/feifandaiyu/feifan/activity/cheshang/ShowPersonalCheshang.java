package com.feifandaiyu.feifan.activity.cheshang;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.carloan.ImageShower;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CreditActivityBean;
import com.feifandaiyu.feifan.bean.PersonCheshangBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.ImageViewUtils;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;
import okhttp3.Call;

/**
 * Created by houdaichang on 2017/12/5.
 */

public class ShowPersonalCheshang extends BaseActivity implements OnGetGeoCoderResultListener, BaiduMap.OnMapStatusChangeListener {


    @InjectView(R.id.tv_get_type)
    TextView tvGetType;
    @InjectView(R.id.et_name)
    PowerfulEditText etName;
    @InjectView(R.id.cemera1)
    ImageView cemera1;
    @InjectView(R.id.et_certificate_num)
    PowerfulEditText etCertificateNum;
    @InjectView(R.id.et_phone_num)
    PowerfulEditText etPhoneNum;
    @InjectView(R.id.et_bankcard_num)
    PowerfulEditText etBankcardNum;
    @InjectView(R.id.cemera2)
    ImageView cemera2;
    @InjectView(R.id.tv_gps_price)
    TextView tvGpsPrice;
    @InjectView(R.id.tv_cheshang_price)
    TextView tvCheshangPrice;
    @InjectView(R.id.tv_cheshang_place)
    TextView tvCheshangPlace;
    @InjectView(R.id.et_addr)
    TextView etAddr;
    @InjectView(R.id.bmapView)
    MapView bmapView;
    @InjectView(R.id.view_center)
    View viewCenter;
    @InjectView(R.id.img_location_point)
    ImageView imgLocationPoint;
    @InjectView(R.id.img_location_back_origin)
    ImageView imgLocationBackOrigin;
    @InjectView(R.id.iv_location)
    ImageView ivLocation;
    @InjectView(R.id.tv_addr)
    TextView tvAddr;
    @InjectView(R.id.rl_location)
    RelativeLayout rlLocation;
    @InjectView(R.id.et_remark)
    PowerfulEditText etRemark;
    @InjectView(R.id.certificate_up)
    ImageView certificateUp;
    @InjectView(R.id.certificate_down)
    ImageView certificateDown;
    @InjectView(R.id.bankcard_up)
    ImageView bankcardUp;
    @InjectView(R.id.bankcard_down)
    ImageView bankcardDown;
    @InjectView(R.id.ll_image)
    LinearLayout llImage;
    @InjectView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    private String cheshangId;
    private PersonCheshangBean bean;
    private KProgressHUD hud;

    private BaiduMap mBaiduMap;
    private Point mCenterPoint = null;
    /**
     * 当前经纬度
     */
    private LatLng mLoactionLatLng;

    private LocationClient mLocClient;

    private MyLocationListener myLocationListener = new MyLocationListener();

    /**
     * 是否第一次定位
     */

    private boolean isFirstLoc = true;
    private GeoCoder mSearch;
    private Double mLatitude;
    private Double mLongitude;

    private boolean isTouch = true;
    // 地图触摸事件监听器
    BaiduMap.OnMapTouchListener touchListener = new BaiduMap.OnMapTouchListener() {
        @Override
        public void onTouch(MotionEvent event) {
            isTouch = true;
            if (event.getAction() == MotionEvent.ACTION_UP) {

                imgLocationBackOrigin.setImageResource(R.drawable.back_origin_normal);
            }
        }
    };
    private LatLng currentLatLng;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_show_personal_cheshang;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("车商详情");
        showBack(true);
        showNext(true);

        hud = KProgressHUD.create(ShowPersonalCheshang.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setLabel("加载中")
                .setDimAmount(0.5f)
                .show();


        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        TextView tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setText("保存");
        tv_next.setEnabled(true);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim0, exitAnim0);
            }
        });

//        initmap();

        cheshangId = PreferenceUtils.getString(this, "cheshangId");

        initData();

    }

    private void initmap() {
        // 地图初始化
        mBaiduMap = bmapView.getMap();
        // 设置为普通矢量图地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        bmapView.setPadding(10, 0, 0, 10);
        bmapView.showZoomControls(false);
        // 设置缩放比例(500米)
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);

        mBaiduMap.setOnMapTouchListener(touchListener);

        // 初始化当前 MapView 中心屏幕坐标
        mCenterPoint = mBaiduMap.getMapStatus().targetScreen;
        mLoactionLatLng = mBaiduMap.getMapStatus().target;

        // 地理编码
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);

        // 地图状态监听
        mBaiduMap.setOnMapStatusChangeListener(this);
        // 定位初始化
        mLocClient = new LocationClient(getApplicationContext());
        mLocClient.registerLocationListener(myLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);

        mLocClient.start();
        // 可定位
        mBaiduMap.setMyLocationEnabled(true);


    }

    private void initData() {

        LogUtils.e(cheshangId);

        OkHttpUtils.post()
                .url(Constants.URLS.BASEURL + "Dealer/dealerInfo")
                .addParams("Id", cheshangId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e("personalCheshang-----" + e);
                        hud.dismiss();
                        MyToast.show(ShowPersonalCheshang.this, "请求服务器失败...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("personalCheshang----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();
                        bean = gson.fromJson(json, PersonCheshangBean.class);
                        hud.dismiss();
                        if (bean.getCode() == 1) {
                            tvGetType.setText(bean.getList().getReceiving());
                            tvCheshangPrice.setText(bean.getList().getRebate());
                            tvGpsPrice.setText(bean.getList().getGps());
                            etName.setText(bean.getList().getUsername());
                            etBankcardNum.setText(bean.getList().getBankCode());
                            etCertificateNum.setText(bean.getList().getCertifCode());
                            etPhoneNum.setText(bean.getList().getTelphone());

                            etAddr.setText(bean.getList().getAddress());
                            etRemark.setText(bean.getList().getRemark());
                            tvCheshangPlace.setText(bean.getList().getProvince() + bean.getList().getCity() + bean.getList().getArea());

                            if (!bean.getList().getCardf().equals("-1")) {
                                ImageViewUtils.showNetImage(ShowPersonalCheshang.this, bean.getList().getCardf(), R.drawable.crabgnormal, certificateUp);
                                ImageViewUtils.showNetImage(ShowPersonalCheshang.this, bean.getList().getCardi(), R.drawable.crabgnormal, certificateDown);
                                ImageViewUtils.showNetImage(ShowPersonalCheshang.this, bean.getList().getBankcardf(), R.drawable.crabgnormal, bankcardUp);
                                ImageViewUtils.showNetImage(ShowPersonalCheshang.this, bean.getList().getBankcardi(), R.drawable.crabgnormal, bankcardDown);
                            } else {
                                llImage.setVisibility(View.GONE);
                            }
                        }
                    }
                });

    }

    @OnClick({R.id.certificate_up, R.id.certificate_down, R.id.bankcard_up, R.id.bankcard_down, R.id.tv_cheshang_price, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.certificate_up:
                Intent intent1 = new Intent(this, ImageShower.class);
                intent1.putExtra("image01", bean.getList().getCardf());
                startActivity(intent1);
                break;
            case R.id.certificate_down:
                Intent intent2 = new Intent(this, ImageShower.class);
                intent2.putExtra("image01", bean.getList().getCardi());
                startActivity(intent2);
                break;
            case R.id.bankcard_up:
                Intent intent3 = new Intent(this, ImageShower.class);
                intent3.putExtra("image01", bean.getList().getBankcardf());
                startActivity(intent3);
                break;
            case R.id.bankcard_down:
                Intent intent4 = new Intent(this, ImageShower.class);
                intent4.putExtra("image01", bean.getList().getBankcardi());
                startActivity(intent4);
                break;
            case R.id.tv_cheshang_price:
                LogUtils.e("click");

                OptionPicker picker2 = new OptionPicker(this, new String[]{
                        "0.0%", "0.5%", "1.0%", "1.5%", "2.0%", "2.5%", "3.0%", "3.5%", "4.0%", "4.5%", "5.0%",
                        "5.5%", "6.0%", "6.5%", "7.0%", "7.5%", "8.0%", "8.5%", "9.0%", "9.5%", "10%"
                });
                picker2.setTitleText("" + "车商返点");
                picker2.setOffset(2);
                picker2.setSelectedIndex(1);
                picker2.setTextSize(12);
                picker2.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        tvCheshangPrice.setText(option);
                    }
                });
                picker2.show();
                break;
            case R.id.tv_next:
                postData();
                break;
            default:
        }
    }

    private void postData() {

        String CheshangPrice = tvCheshangPrice.getText().toString().trim();

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "DealerUpdate/EditDealer")
                .addParams("saleid", PreferenceUtils.getString(ShowPersonalCheshang.this, "saleID"))
                .addParams("dealerid", cheshangId)
                .addParams("rebate", CheshangPrice)
                .addParams("remark", etRemark.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("PersonalCheShangActivity----------------->>>>>>." + e);
                        MyToast.show(ShowPersonalCheshang.this, "服务器正忙，请稍后再试。。。");
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("PersonalCheShangActivity----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();
                        CreditActivityBean bean = gson.fromJson(json, CreditActivityBean.class);

                        if (bean.getCode() == 1) {
                            hud.dismiss();

//                            PreferenceUtils.setString(ShowPersonalCheshang.this, "cheshangId", bean.getId());

                            finish();
                            int enterAnim6 = R.anim.pre_enter;// 进入的activity对应的动画资源
                            int exitAnim6 = R.anim.pre_exit;// 结束的activity对应的动画资源
                            overridePendingTransition(enterAnim6, exitAnim6);
                        } else if (bean.getCode() == 0) {
                            MyToast.show(ShowPersonalCheshang.this, bean.getMsg());
                            hud.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {

    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

    }

    public class MyLocationListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || bmapView == null) {
                return;
            }

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            mBaiduMap.setMyLocationData(locData);

            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

            LogUtils.e("mLatitude", mLatitude.toString());
            LogUtils.e("mLongitude", mLongitude.toString());

//System.out.println(location.getAddress()+location.getLocationDescribe() + location.getAddrStr() + location.getLocTypeDescription());

            currentLatLng = new LatLng(mLatitude, mLongitude);
            mLoactionLatLng = new LatLng(mLatitude, mLongitude);

            tvAddr.setText(location.getAddrStr());

            // 是否第一次定位
            if (isFirstLoc) {
                isFirstLoc = false;
                // 实现动画跳转
                MapStatusUpdate u = MapStatusUpdateFactory
                        .newLatLng(currentLatLng);
                mBaiduMap.animateMapStatus(u);

                return;
            }

        }

    }

    @Override
    protected void onResume() {
        bmapView.onResume();
        super.onResume();
        LogUtils.e("onResume");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)

        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            }

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        }

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    protected void onPause() {
        bmapView.onPause();
        super.onPause();
        LogUtils.e("onPause");
    }

    @Override
    protected void onDestroy() {
        bmapView.onDestroy();
        super.onDestroy();
//        mLocClient.stop();
        System.gc();
    }

}
