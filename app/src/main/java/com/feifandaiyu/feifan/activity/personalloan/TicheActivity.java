package com.feifandaiyu.feifan.activity.personalloan;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.feifandaiyu.feifan.MyApplication;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.StartHomeVistiBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.feifandaiyu.feifan.R.id.img_location_back_origin;

/**
 * Created by houdaichang on 2017/5/8.
 */

public class TicheActivity extends BaseActivity implements BaiduMap.OnMapStatusChangeListener, OnGetGeoCoderResultListener {

    @InjectView(R.id.tv_addr)
    TextView tvAddr;
    @InjectView(R.id.bmapView)
    MapView bmapView;

    @InjectView(R.id.iv_location)
    ImageView ivLocation;
    @InjectView(R.id.view_center)
    View viewCenter;
    @InjectView(R.id.img_location_point)
    ImageView imgLocationPoint;
    @InjectView(img_location_back_origin)
    ImageView imgLocationBackOrigin;
    @InjectView(R.id.rl_location)
    RelativeLayout rlLocation;
    @InjectView(R.id.et_remarks)
    EditText etRemarks;

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
    private KProgressHUD hud;
    private TextView tv_next;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_tiche;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void init(Bundle savedInstanceState) {

        ButterKnife.inject(this);

        imgLocationBackOrigin.setImageResource(R.drawable.back_origin_normal);

        hud = KProgressHUD.create(TicheActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        setTitle("提车");
        showBack(true);
        tv_next = (TextView) findViewById(R.id.tv_next);

        tv_next.setEnabled(true);

        tv_next.setText("上传");
        MyApplication.getInstance().addActivity(this);

        tvAddr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tvAddr.getText() != null && !tvAddr.getText().toString().equals("")) {
//                    btStartVisit.setEnabled(true);
                }
            }
        });

        Time t = new Time("GMT+8");
        t.setToNow(); // 取得系统时间。
        int year = t.year;
        int month = t.month + 1;
        int date = t.monthDay;
        int hour = t.hour + 8; // 0-23
        int minute = t.minute;
        int second = t.second;

        if (minute < 10) {

//            tvStartTime.setText(year + "年" + month + "月" + date + "日" + "   " + hour + ":" + 0 + minute);

        } else {
//            tvStartTime.setText(year + "年" + month + "月" + date + "日" + "   " + hour + ":" + minute);
        }

        initmap();

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

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)

        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }
    }

    @OnClick({R.id.iv_back, R.id.img_location_back_origin, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:

                hud.show();

                System.out.println(mLatitude + "=====" + mLongitude);

                OkHttpUtils
                        .post()
                        //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                        .url(Constants.URLS.BASEURL + "LiftCar/index")
                        .addParams("carId", PreferenceUtils.getString(this, "carId"))
                        .addParams("lat", mLatitude.toString())
                        .addParams("lng", mLongitude.toString())
                        .addParams("carType", "0")
                        .addParams("remarks", etRemarks.getText().toString())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                LogUtils.d("startHomeActivity" + "------------->>>>>>>" + e);
                                MyToast.show(TicheActivity.this, "提交失败");
                                hud.dismiss();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.d("startHomeActivity" + "------------->>>>" + response + "成功");
                                String json = response;
                                Gson gson = new Gson();
                                hud.dismiss();

                                StartHomeVistiBean bean = gson.fromJson(json, StartHomeVistiBean.class);

                                if (bean.getCode() == 1) {
//                                    PreferenceUtils.setString(TicheActivity.this, "userName", bean.getList().getUserName());
//
//                                    String carLoan = bean.getList().getCarLoan();
//                                    PreferenceUtils.setString(TicheActivity.this, "carLoan", carLoan);
//
//
//                                    PreferenceUtils.setString(TicheActivity.this, "Latitude", mLatitude.toString());
//                                    PreferenceUtils.setString(TicheActivity.this, "Longtitude", mLongitude.toString());
//
//                                    Intent intent = new Intent(TicheActivity.this, BaseInformationActivity.class);
//                                    startActivity(intent);
                                    MyToast.show(TicheActivity.this, "上传成功");

                                    finish();
                                    // 设置过渡动画
//                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
//                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
//                                    overridePendingTransition(enterAnim6, exitAnim6);

//                                    LogUtils.e("carloan" + "=" + carLoan + "===============");

//                                    PreferenceUtils.setString(StartHomeVisitActivity.this, "json", json);

                                } else if (bean.getCode() == 0) {
                                    MyToast.show(TicheActivity.this, bean.getMsg());
                                }


                            }
                        });

                break;

            case R.id.iv_back:

                mLocClient.stop();

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

            case img_location_back_origin:
                if (mLoactionLatLng != null) {
                    // 实现动画跳转
                    imgLocationBackOrigin.setImageResource(R.drawable.back_origin_select);
                    MapStatusUpdate u = MapStatusUpdateFactory
                            .newLatLng(mLoactionLatLng);
                    mBaiduMap.animateMapStatus(u);
                    mSearch.reverseGeoCode((new ReverseGeoCodeOption())
                            .location(mLoactionLatLng));
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocClient.stop();
        System.gc();
    }

    /**
     * 定位SDK监听函数
     */
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


}