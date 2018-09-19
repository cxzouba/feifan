package com.feifandaiyu.feifan.activity.cheshang;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
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
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CreditActivityBean;
import com.feifandaiyu.feifan.bean.MsgBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.ocr.CameraActivity;
import com.feifandaiyu.feifan.ocr.RecognizeService;
import com.feifandaiyu.feifan.popupWindow.BottomPopupOption;
import com.feifandaiyu.feifan.utils.AssetsUtils;
import com.feifandaiyu.feifan.utils.FileUtil;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.NumberUtils;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.OptionPicker;
import okhttp3.Call;

public class CompanyCheShangActivity extends BaseActivity implements TextWatcher, OnGetGeoCoderResultListener, BaiduMap.OnMapStatusChangeListener {

    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    private static final int REQUEST_CODE_CAMERA = 102;
    private static final int REQUEST_CODE_BANKCARD = 110;
    @InjectView(R.id.tv_get_type)
    TextView tvGetType;
    @InjectView(R.id.et_full_name)
    PowerfulEditText etFullName;
    @InjectView(R.id.et_contact_name)
    PowerfulEditText etContactName;
    @InjectView(R.id.et_phone_num)
    PowerfulEditText etPhoneNum;
    @InjectView(R.id.et_leagle_name)
    PowerfulEditText etLeagleName;
    @InjectView(R.id.et_leagle_num)
    PowerfulEditText etLeagleNum;
    @InjectView(R.id.et_duigong_num)
    PowerfulEditText etDuigongNum;
    @InjectView(R.id.et_bankcard_num)
    PowerfulEditText etBankcardNum;
    @InjectView(R.id.et_zuzhi_num)
    PowerfulEditText etZuzhiNum;
    @InjectView(R.id.tv_start_time)
    TextView tvStartTime;
    @InjectView(R.id.tv_stop_time)
    TextView tvStopTime;
    @InjectView(R.id.et_sale_num)
    PowerfulEditText etSaleNum;
    @InjectView(R.id.tv_gps_price)
    TextView tvGpsPrice;
    @InjectView(R.id.tv_cheshang_price)
    TextView tvCheshangPrice;
    @InjectView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @InjectView(R.id.cemera1)
    ImageView cemera1;
    @InjectView(R.id.cemera2)
    ImageView cemera2;
    @InjectView(R.id.tv_cheshang_place)
    TextView tvCheshangPlace;
    @InjectView(R.id.et_addr)
    PowerfulEditText etAddr;
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
    private BottomPopupOption bottomPopupOption;
    private TimePickerView pvCustomTime;
    private int year;
    private int month;
    private int date;
    private TextView tv_next;
    private KProgressHUD hud;
    private boolean hasGotToken = false;
    private AlertDialog.Builder alertDialog;
    private boolean isOk = false;
    private String province;
    private String city;
    private String district;

    private BaiduMap mBaiduMap;
    private android.graphics.Point mCenterPoint = null;
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
    protected int getContentView() {
        return R.layout.activity_company_che_shang;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("企业车商");
        showNext(true);
        showBack(true);

        tv_next = (TextView) findViewById(R.id.tv_next);

        hud = KProgressHUD.create(CompanyCheShangActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        initTime();

        initTextChangeListener();

        initAccessTokenWithAkSk();

        initmap();

        alertDialog = new AlertDialog.Builder(this);

    }

    private void initTime() {
        Time t = new Time("GMT+8");
        t.setToNow(); // 取得系统时间。
        year = t.year;
        month = t.month;
        date = t.monthDay;
    }

    private void initTextChangeListener() {
        tvGetType.addTextChangedListener(this);
        etFullName.addTextChangedListener(this);
        etContactName.addTextChangedListener(this);
        etPhoneNum.addTextChangedListener(this);
        etLeagleName.addTextChangedListener(this);
        etLeagleNum.addTextChangedListener(this);
        etDuigongNum.addTextChangedListener(this);
        etBankcardNum.addTextChangedListener(this);
        etZuzhiNum.addTextChangedListener(this);
        tvStartTime.addTextChangedListener(this);
        tvStopTime.addTextChangedListener(this);
        etSaleNum.addTextChangedListener(this);
        tvGpsPrice.addTextChangedListener(this);
        tvCheshangPrice.addTextChangedListener(this);
        etRemark.addTextChangedListener(this);
        etAddr.addTextChangedListener(this);
        tvCheshangPlace.addTextChangedListener(this);
    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {

            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hud.dismiss();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                hud.dismiss();
                alertText("AK，SK方式获取token失败", error.getMessage());
            }
        }, getApplicationContext(), "T8tx2AW8ZGGKUWNZitHsXRuc", "C8SR9G6ucTDLge1q0c6Be4HgGlYoQR6y");
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

    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        bmapView.onDestroy();
        super.onDestroy();
        mLocClient.stop();
        System.gc();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE_FRONT && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String filePath = getRealPathFromURI(uri);
            recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
        }

//        if (requestCode == REQUEST_CODE_PICK_IMAGE_BACK && resultCode == Activity.RESULT_OK) {
//            Uri uri = data.getData();
//            String filePath = getRealPathFromURI(uri);
//            recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
//        }

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }
        }

        if (requestCode == REQUEST_CODE_BANKCARD && resultCode == Activity.RESULT_OK) {
            RecognizeService.recBankCard(this,FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
//                            infoPopText(result);
                            LogUtils.e(result);
                            String regEx = "[^0-9]";
                            Pattern p = Pattern.compile(regEx);
                            Matcher m = p.matcher(result);
                            System.out.println(m.replaceAll("").trim());
                            etBankcardNum.setText(m.replaceAll("").trim());
                        }

                        @Override
                        public void onResult2(GeneralResult result) {

                        }
                    });
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
//                    alertText("", result.toString());
//                    infoTextView.setText(result.toString());
//                    String json = response;
//                    Gson gson = new Gson();
//                    CreditActivityBean bean = gson.fromJson(json, CreditActivityBean.class);
                    hud.dismiss();
                    etLeagleName.setText(result.getName().toString());
                    etLeagleNum.setText(result.getIdNumber().toString());

                }
            }

            @Override
            public void onError(OCRError error) {
                alertText("", error.getMessage());
            }
        });
    }

    @OnClick({R.id.tv_get_type, R.id.tv_gps_price, R.id.tv_cheshang_price, R.id.iv_back, R.id.tv_next,
            R.id.tv_start_time, R.id.tv_stop_time, R.id.cemera1, R.id.cemera2, R.id.tv_cheshang_place})
    public void onViewClicked(View view) {
        bottomPopupOption = new BottomPopupOption(CompanyCheShangActivity.this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {
            case R.id.tv_get_type:
                showSelectQudaoDialog();
                break;
            case R.id.tv_cheshang_place:
                showSelectHomeDialog();
                break;
            case R.id.tv_start_time:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                showStartTimeDialog();
                break;
            case R.id.tv_stop_time:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                showStopTimeDialog();
                break;
            case R.id.tv_gps_price:
                OptionPicker picker = new OptionPicker(this, new String[]{
                        "1000", "2000"
                });
                picker.setTitleText("" +
                        "GPS返点");
                picker.setOffset(2);
                picker.setSelectedIndex(0);
                picker.setTextSize(12);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        tvGpsPrice.setText(option);
                    }
                });
                picker.show();
                break;
            case R.id.tv_cheshang_price:
                OptionPicker picker2 = new OptionPicker(this, new String[]{
                        "0.0%", "0.5%", "1.0%", "1.5%", "2.0%", "2.5%", "3.0%", "3.5%", "4.0%", "4.5%", "5.0%",
                        "5.5%", "6.0%", "6.5%", "7.0%", "7.5%", "8.0%", "8.5%", "9.0%", "9.5%", "10%"
                });
                picker2.setTitleText("" +
                        "车商返点");
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
            case R.id.cemera1:
                Intent intent = new Intent(CompanyCheShangActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
                break;
            case R.id.cemera2:
                if (!checkTokenStatus()) {
                    return;
                }

                Intent intent2 = new Intent(CompanyCheShangActivity.this, CameraActivity.class);
                intent2.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent2.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent2, REQUEST_CODE_BANKCARD);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_next:
                hud.show();

                if (isOk) {

                    postData();

                } else {

                    confirmCertificate();

                }
                break;
            default:
        }
    }

    private void showSelectQudaoDialog() {

        //选择城市颜色
//        int color1 = getResources().getColor(R.color.xuanzechengshi);
        //带选择城市颜色
//        int color2 = getResources().getColor(R.color.beixuanchengshi);

        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(CompanyCheShangActivity.this, "cheshang.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(CompanyCheShangActivity.this, data);
        picker.setSelectedItem("车商", "新车", "4s店");
        picker.setTitleText("渠道选择");
        picker.setTitleTextColor(Color.BLACK);
        picker.setLineVisible(false);
        picker.setTextSize(18);
        picker.setCancelText("取消");
        picker.setSubmitText(" 确定");
        picker.setOffset(2);
//        picker.setTextColor(color1, color2);
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {

            @Override
            //                                  省           市           区
            public void onAddressPicked(String province, String city, String county) {
                if (province.equals(city)) {
                    city = "";
                }

                if (city.equals(county)) {
                    county = "";
                }
                //省市区
                tvGetType.setText(province + city + county);
//                //省
                CompanyCheShangActivity.this.province = province;
//                //市
                CompanyCheShangActivity.this.city = city;
//                //区
                CompanyCheShangActivity.this.district = county;
            }
        });
        picker.show();
    }

    private void showSelectHomeDialog() {
        //选择城市颜色
//        int color1 = getResources().getColor(R.color.xuanzechengshi);
        //带选择城市颜色
//        int color2 = getResources().getColor(R.color.beixuanchengshi);
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(CompanyCheShangActivity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(CompanyCheShangActivity.this, data);
        picker.setSelectedItem("黑龙江省", "哈尔滨市", "道里区");
        picker.setTitleText("地址选择");
        picker.setTitleTextColor(Color.BLACK);
        picker.setLineVisible(false);
        picker.setTextSize(18);

        picker.setCancelText("取消");
        picker.setSubmitText(" 确定");
        picker.setOffset(2);
//        picker.setTextColor(color1, color2);
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            //                                  省           市           区
            public void onAddressPicked(String province, String city, String county) {
                //省市区
                tvCheshangPlace.setText(province + city + county);
                //省
                CompanyCheShangActivity.this.province = province;
//                //市
                CompanyCheShangActivity.this.city = city;
//                //区
                CompanyCheShangActivity.this.district = county;
            }
        });
        picker.show();
    }

    private void showStartTimeDialog() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1950, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year, month, date);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvStartTime.setText(getTime(date));
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

    private void showStopTimeDialog() {
        /**
         * @description
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1950, 1, 1);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvStopTime.setText(getTime(date));
            }
        })
                /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
               /*.gravity(Gravity.RIGHT)// default is center*/

                .setLineSpacingMultiplier(1.5f)//设置两横线之间的间隔倍数
                .setDate(selectedDate)
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

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    private void postData() {

        String telphone = etPhoneNum.getText().toString().trim();

        if (!NumberUtils.isMobile(telphone)) {
            MyToast.show(this, "手机号码不是11位，请仔细检查");
            hud.dismiss();
            return;
        }

        String userName = etFullName.getText().toString().trim();

        PreferenceUtils.setString(CompanyCheShangActivity.this, "userName", userName);

        String cardNum = etLeagleNum.getText().toString().trim();

        String CheshangPrice = tvCheshangPrice.getText().toString().trim();

        String BankcardNum = etBankcardNum.getText().toString().trim();

        String GpsPrice = this.tvGpsPrice.getText().toString().trim();

        String ContactName = etContactName.getText().toString().trim();
        String StartTime = tvStartTime.getText().toString().trim();
        String StopTime = tvStopTime.getText().toString().trim();
        String LeagleName = etLeagleName.getText().toString().trim();
        String SaleNum = etSaleNum.getText().toString().trim();
        String DuigongNum = etDuigongNum.getText().toString().trim();
        String ZuzhiNum = etZuzhiNum.getText().toString().trim();

        LogUtils.e("province" + province + "city" + city + "district" + district);

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Dealer/addDealer")
                .addParams("saleId", PreferenceUtils.getString(CompanyCheShangActivity.this, "saleID"))
                .addParams("username", userName)
                .addParams("receiving", province)
                .addParams("channel", city)
                .addParams("threeChannel", district)
                .addParams("telphone", telphone)
                .addParams("certifCode", cardNum)
                .addParams("rebate", CheshangPrice)
                .addParams("bankCode", BankcardNum)
                .addParams("gps", GpsPrice)
                .addParams("contacts", ContactName)
                .addParams("openTime", StartTime)
                .addParams("endTime", StopTime)
                .addParams("legalName", LeagleName)
                .addParams("sales", SaleNum)
                .addParams("account", DuigongNum)
                .addParams("oCode", ZuzhiNum)

                .addParams("province", province)
                .addParams("city", city)
                .addParams("area", district)
                .addParams("address", etAddr.getText().toString())
                .addParams("lng", mLongitude.toString())
                .addParams("lat", mLatitude.toString())
                .addParams("remark", etRemark.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("PersonalCheShangActivity----------------->>>>>>." + e);
                        MyToast.show(CompanyCheShangActivity.this, "服务器正忙，请稍后再试。。。");
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

                            PreferenceUtils.setString(CompanyCheShangActivity.this, "cheshangId", bean.getId());

                            startActivity(new Intent(CompanyCheShangActivity.this, CompanyCheshangPicActivity.class));

                            finish();
                            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                            overridePendingTransition(enterAnim6, exitAnim6);
                        } else if (bean.getCode() == 0) {
                            MyToast.show(CompanyCheShangActivity.this, bean.getMsg());
                            hud.dismiss();
                        }
                    }
                });
    }

    private void confirmCertificate() {

        String userName = etLeagleName.getText().toString().trim();
        String cardNum = etLeagleNum.getText().toString().trim();

        OkHttpUtils.post()
                .url(Constants.URLS.BASEURL + "Appraiser/VerifyIdcardv")
                .addParams("userName", userName)
                .addParams("cardNum", cardNum)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("confirm----------------->>>>>>." + e);
                        MyToast.show(CompanyCheShangActivity.this, "服务器正忙，请稍后再试。。。");
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("confirm----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();
                        MsgBean MsgBean = gson.fromJson(json, MsgBean.class);
                        if (MsgBean.getCode() == 1) {

                            MyToast.show(CompanyCheShangActivity.this, "身份证实名认证通过");

                            isOk = true;
                            etLeagleName.setEnabled(false);
                            etLeagleName.setTextColor(Color.GRAY);
                            etLeagleNum.setEnabled(false);
                            etLeagleNum.setTextColor(Color.GRAY);

//                            new AlertDialog.Builder(PersonalCheShangActivity.this)
//                                    .setMessage(MsgBean.getMsg() + "，是否保存用户信息？")
//                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            hud.show();
//                                            postData();
//                                        }
//                                    })
//                                    .setNegativeButton("取消", null)
//                                    .show();

                            confirmBankCard();

                        } else {
                            hud.dismiss();
                            isOk = false;
                            new AlertDialog.Builder(CompanyCheShangActivity.this)
                                    .setTitle("身份证验证")
                                    .setMessage(MsgBean.getMsg())
                                    .setPositiveButton("知道了", null)
                                    .show();
                        }

                    }
                });

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void confirmBankCard() {
        String userName = etLeagleName.getText().toString().trim();
        String cardNum = etBankcardNum.getText().toString().trim();
        String idCard = etLeagleNum.getText().toString().trim();

        OkHttpUtils.post()
                .url(Constants.URLS.BASEURL + "Appraiser/bankCard")
                .addParams("userName", userName)
                .addParams("cardNum", cardNum)
                .addParams("idcard", idCard)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("confirm----------------->>>>>>." + e);
                        MyToast.show(CompanyCheShangActivity.this, "服务器正忙，请稍后再试。。。");
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("confirm----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();
                        MsgBean MsgBean = gson.fromJson(json, MsgBean.class);
                        if (MsgBean.getCode() == 1) {
                            MyToast.show(CompanyCheShangActivity.this, "银行卡验证通过");
                            isOk = true;
                            etLeagleName.setEnabled(false);
                            etLeagleName.setTextColor(Color.GRAY);
                            etBankcardNum.setEnabled(false);
                            etBankcardNum.setTextColor(Color.GRAY);

//                            new AlertDialog.Builder(PersonalCheShangActivity.this)
//                                    .setMessage(MsgBean.getMsg() + "，是否保存用户信息？")
//                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            hud.show();
//                                            postData();
//                                        }
//                                    })
//                                    .setNegativeButton("取消", null)
//                                    .show();

                            postData();

                        } else {
                            hud.dismiss();
                            isOk = false;
                            new AlertDialog.Builder(CompanyCheShangActivity.this)
                                    .setTitle("银行卡验证")
                                    .setMessage(MsgBean.getMsg())
                                    .setPositiveButton("知道了", null)
                                    .show();
                        }

                    }
                });
    }

    @Override
    protected void onPause() {
        bmapView.onPause();
        super.onPause();
        LogUtils.e("onPause");
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (!StringUtils.isEmpty(tvCheshangPrice.getText().toString())
                && !StringUtils.isEmpty(tvGpsPrice.getText().toString())
                && !StringUtils.isEmpty(tvStopTime.getText().toString())
                && !StringUtils.isEmpty(tvStartTime.getText().toString())
                && !StringUtils.isEmpty(tvGetType.getText().toString())
                && !StringUtils.isEmpty(etSaleNum.getText().toString())
                && !StringUtils.isEmpty(etZuzhiNum.getText().toString())
                && !StringUtils.isEmpty(etBankcardNum.getText().toString())
                && !StringUtils.isEmpty(etDuigongNum.getText().toString())
                && !StringUtils.isEmpty(etLeagleNum.getText().toString())
                && !StringUtils.isEmpty(etLeagleName.getText().toString())
                && !StringUtils.isEmpty(etPhoneNum.getText().toString())
                && !StringUtils.isEmpty(etContactName.getText().toString())
                && !StringUtils.isEmpty(etFullName.getText().toString())
                && !StringUtils.isEmpty(etAddr.getText().toString())
                && !StringUtils.isEmpty(etRemark.getText().toString())
                && !StringUtils.isEmpty(tvCheshangPlace.getText().toString())
                )

        {
            tv_next.setEnabled(true);
        } else {
            tv_next.setEnabled(false);
        }
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
