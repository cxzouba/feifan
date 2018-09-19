package com.feifandaiyu.feifan.activity.personalloan;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
import com.feifandaiyu.feifan.bean.StopVisitBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.DistanceUtils;
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

public class StopHomeVisitActivity extends BaseActivity implements BaiduMap.OnMapStatusChangeListener, OnGetGeoCoderResultListener {


    @InjectView(R.id.bmapView)
    MapView bmapView;
    @InjectView(R.id.view_center)
    View viewCenter;
    @InjectView(R.id.img_location_point)
    ImageView imgLocationPoint;
    @InjectView(img_location_back_origin)
    ImageView imgLocationBackOrigin;
    @InjectView(R.id.iv_stop_time)
    ImageView ivStopTime;
    @InjectView(R.id.tv_stop_time)
    TextView tvStopTime;
    @InjectView(R.id.iv_location)
    ImageView ivLocation;
    @InjectView(R.id.tv_addr)
    TextView tvAddr;
    @InjectView(R.id.bt_stop_visit)
    Button btStopVisit;
    @InjectView(R.id.rl_location)
    RelativeLayout rlLocation;

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
    private String userId;
    private KProgressHUD hud;
    private double startLatitude;
    private double startLongtitude;
    private String isNew;

    @Override
    protected int getContentView() {
        return R.layout.activity_stop_home_visit;
    }


    @Override
    protected void init(Bundle savedInstanceState) {

        ButterKnife.inject(this);

        imgLocationBackOrigin.setImageResource(R.drawable.back_origin_normal);

        setTitle("结束家访");
        showBack(true);
        showNext(false);
        MyApplication.getInstance().addActivity(this);

        hud = KProgressHUD.create(StopHomeVisitActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        userId = PreferenceUtils.getString(this, "userId");
        isNew = PreferenceUtils.getString(this, "isNew");
        startLatitude = Double.parseDouble(PreferenceUtils.getString(this, "Latitude"));
        startLongtitude = Double.parseDouble(PreferenceUtils.getString(this, "Longtitude"));


        tvAddr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btStopVisit.setEnabled(true);
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

            tvStopTime.setText(year + "年" + month + "月" + date + "日" + "   " + hour + ":" + 0 + minute);

        } else {

            tvStopTime.setText(year + "年" + month + "月" + date + "日" + "   " + hour + ":" + minute);
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

            LatLng currentLatLng = new LatLng(mLatitude, mLongitude);
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

    @OnClick({R.id.bt_stop_visit, R.id.iv_back, R.id.img_location_back_origin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_stop_visit:

                hud.show();

                LogUtils.e("startLongtitude", startLongtitude + "");
                LogUtils.e("startLatitude", startLatitude + "");
                LogUtils.e("mLongitude", mLongitude + "");
                LogUtils.e("mLatitude", mLatitude + "");

                final int distance = (int) DistanceUtils.getDistance(startLongtitude, startLatitude, mLongitude, mLatitude);

                LogUtils.e("distance", distance + "");

                OkHttpUtils
                        .post()
                        .url(Constants.URLS.BASEURL + "Login/endVisit")
                        .addParams("userId", userId)
                        .addParams("distance", distance + "")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                System.out.println("stop____________________>>>>>" + e);

                                hud.dismiss();

                                MyToast.show(StopHomeVisitActivity.this, "加载失败");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.e("stop----------------->>>>>>" + response);

                                hud.dismiss();

//                                MyToast.show(StopHomeVisitActivity.this, distance + "米");

                                String json = response;
                                Gson gson = new Gson();
                                StopVisitBean bean = gson.fromJson(json, StopVisitBean.class);

                                if (bean.getCode() == 1) {

                                    startActivity(new Intent(StopHomeVisitActivity.this, BusinessPicActivity.class));

                                    mLocClient.stop();
                                    finish();
                                    // 设置过渡动画
                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                    overridePendingTransition(enterAnim6, exitAnim6);
                                } else {
                                    MyToast.show(StopHomeVisitActivity.this, bean.getMsg());
                                }

                            }
                        });


                break;

            case R.id.iv_back:
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
        System.gc();
    }


}
