package com.feifandaiyu.feifan.activity.personalloan;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
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
import com.feifandaiyu.feifan.adapter.GridImageAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.StartHomeVistiBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.FullyGridLayoutManager;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.QiNiuUtlis;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.feifandaiyu.feifan.utils.TimeUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.FunctionOptions;
import com.luck.picture.lib.model.PictureConfig;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadOptions;
import com.yalantis.ucrop.entity.LocalMedia;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.feifandaiyu.feifan.R.id.img_location_back_origin;

/**
 * Created by houdaichang on 2017/5/8.
 */

public class LuohuActivity extends BaseActivity implements BaiduMap.OnMapStatusChangeListener, OnGetGeoCoderResultListener {

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

    @InjectView(R.id.recycler)
    RecyclerView recyclerView;

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


    private GridImageAdapter adapter1;
    private int themeStyle;
    private int previewColor, completeColor, previewBottomBgColor, previewTopBgColor, bottomBgColor, checkedBoxDrawable;
    private int selectType = FunctionConfig.TYPE_IMAGE;
    private int copyMode = FunctionConfig.CROP_MODEL_DEFAULT;
    private boolean enablePreview = true;
    private boolean isPreviewVideo = true;
    private boolean enableCrop = false;
    private boolean theme = false;
    private boolean selectImageType = false;
    private boolean mode = false;// 启动相册模式
    private boolean clickVideo = false;
    private int maxSelectNum = 100;
    private List<LocalMedia> selectMedia = new ArrayList<>();
    private TextView tv;
    private String compressPath;
    private String key;
    private JSONArray image;
    private UploadOptions uploadOptions;
    private int upLoadCount = 1;
    private int successCount = 0;
    private int piccount;
    private ImageView iv;
    private ProgressDialog progressDialog;
    private boolean success;
    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia = resultList;
            Log.i("callBack_result", selectMedia.size() + "");
            LocalMedia media = resultList.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                String path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                String path = media.getCompressPath();
            } else {
                // 原图地址
                String path = media.getPath();
            }
            if (selectMedia != null) {
                adapter1.setList(selectMedia);
                adapter1.notifyDataSetChanged();
            }
        }

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia.add(media);
            if (selectMedia != null) {
                adapter1.setList(selectMedia);
                adapter1.notifyDataSetChanged();
            }
        }
    };
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener1 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:

                    themeStyle = ContextCompat.getColor(LuohuActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(LuohuActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(LuohuActivity.this, R.color.tab_color_true);

                    FunctionOptions options = new FunctionOptions.Builder()
                            .setType(FunctionConfig.TYPE_IMAGE) // 图片or视频 FunctionConfig.TYPE_IMAGE  TYPE_VIDEO
                            .setCropMode(copyMode) // 裁剪模式 默认、1:1、3:4、3:2、16:9
                            .setCompress(true) //是否压缩
                            .setEnablePixelCompress(true) //是否启用像素压缩
                            .setEnableQualityCompress(true) //是否启质量压缩
                            .setMaxSelectNum(maxSelectNum) // 可选择图片的数量
                            .setSelectMode(FunctionConfig.MODE_MULTIPLE) // 单选 or 多选
                            .setShowCamera(true) //是否显示拍照选项 这里自动根据type 启动拍照或录视频
                            .setEnablePreview(true) // 是否打开预览选项
                            .setEnableCrop(enableCrop) // 是否打开剪切选项
                            .setPreviewVideo(true) // 是否预览视频(播放) mode or 多选有效
                            .setCheckedBoxDrawable(checkedBoxDrawable)
                            .setRecordVideoDefinition(FunctionConfig.HIGH) // 视频清晰度
                            .setRecordVideoSecond(60) // 视频秒数
                            .setGif(false)// 是否显示gif图片，默认不显示
                            .setCropW(200) // cropW-->裁剪宽度 值不能小于100  如果值大于图片原始宽高 将返回原图大小
                            .setCropH(200) // cropH-->裁剪高度 值不能小于100 如果值大于图片原始宽高 将返回原图大小
                            .setMaxB(202400) // 压缩最大值 例如:200kb  就设置202400，202400 / 1024 = 200kb
                            .setPreviewColor(previewColor) //预览字体颜色
                            .setCompleteColor(completeColor) //已完成字体颜色
                            .setPreviewBottomBgColor(previewBottomBgColor) //预览图片底部背景色
                            .setEnablePixelCompress(true)
                            .setBottomBgColor(bottomBgColor) //图片列表底部背景色
                            .setGrade(Luban.THIRD_GEAR) // 压缩档次 默认三档
                            .setCheckNumMode(false)
                            .setCompressQuality(100) // 图片裁剪质量,默认无损
                            .setImageSpanCount(4) // 每行个数

                            .setSelectMedia(selectMedia) // 已选图片，传入在次进去可选中，不能传入网络图片
                            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                            .setThemeStyle(themeStyle) // 设置主题样式
                            .create();
                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(LuohuActivity.this, resultCallback);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(LuohuActivity.this, resultCallback);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia.remove(position);
                    adapter1.notifyItemRemoved(position + 1);
                    break;
                default:
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_luohu;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void init(Bundle savedInstanceState) {

        ButterKnife.inject(this);

        imgLocationBackOrigin.setImageResource(R.drawable.back_origin_normal);

        hud = KProgressHUD.create(LuohuActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        setTitle("落户");
        showBack(true);
        tv_next = (TextView) findViewById(R.id.tv_next);

        tv_next.setEnabled(true);

        tv_next.setText("上传");
        MyApplication.getInstance().addActivity(this);

        initRecyclerView();

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

    private void initRecyclerView() {
        //FullyGridLayoutManager manager = new FullyGridLayoutManager(TestActivity.this, 4, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager ms = new LinearLayoutManager(this);

        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        recyclerView.setLayoutManager(ms);
        adapter1 = new GridImageAdapter(LuohuActivity.this, onAddPicClickListener1);
        adapter1.setList(selectMedia);
        adapter1.setSelectMax(maxSelectNum);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(LuohuActivity.this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        PictureConfig.getInstance().externalPicturePreview(LuohuActivity.this, position, selectMedia);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(LuohuActivity.this, selectMedia.get(position - 1).getPath());
                        }
                        break;

                    default:
                }

            }
        });

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
                updateToQiniu();

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

    private void updateToQiniu() {
        List<List<LocalMedia>> medias = new ArrayList<>();

        medias.add(selectMedia);
        piccount = selectMedia.size();
        LogUtils.e("piccount=" + piccount);

        if (piccount < 1) {
            MyToast.show(LuohuActivity.this, "请上传面部照片");
            return;
        }

        for (int i = 0; i < medias.size(); i++) {
            List<LocalMedia> localMedias = medias.get(i);
            for (int j = 0; j < localMedias.size(); j++) {
                String compressPath = localMedias.get(j).getCompressPath();
                final int finalI = i;
                final int finalJ = j;
                String key = UUID.randomUUID() + TimeUtils.getRandomFileName() + ".png";


                if (finalI == 0) {
                    if (image == null) {
                        image = new JSONArray();
                    }
                    try {

                        image.put(finalJ, key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                QiNiuUtlis.upLoad(compressPath, key, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                LogUtils.i("1");
                                if (!info.isOK()) {
                                    hud.dismiss();
                                }

                                LogUtils.i("upLoadCount" + upLoadCount);

                                if (upLoadCount == piccount) {
                                    post2();
                                }
                            }
                        }
                );

            }
        }
    }


    private void post2() {

        System.out.println(mLatitude + "=====" + mLongitude);

        OkHttpUtils
                .post()
                //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                .url(Constants.URLS.BASEURL + "LiftCar/settle")
                .addParams("carId", PreferenceUtils.getString(this, "carId"))
                .addParams("lat", mLatitude.toString())
                .addParams("lng", mLongitude.toString())
                .addParams("carType", "0")
                .addParams("remarks", etRemarks.getText().toString())
                .addParams("drivingLicense", image.toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.d("startHomeActivity" + "------------->>>>>>>" + e);
                        MyToast.show(LuohuActivity.this, "提交失败");
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
                            MyToast.show(LuohuActivity.this, "上传成功");

                            finish();
                            // 设置过渡动画
//                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
//                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
//                                    overridePendingTransition(enterAnim6, exitAnim6);

//                                    LogUtils.e("carloan" + "=" + carLoan + "===============");

//                                    PreferenceUtils.setString(StartHomeVisitActivity.this, "json", json);

                        } else if (bean.getCode() == 0) {
                            MyToast.show(LuohuActivity.this, bean.getMsg());
                        }


                    }
                });
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