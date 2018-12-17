package com.feifandaiyu.feifan.activity.personalloan;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.GridImageAdapter;
import com.feifandaiyu.feifan.adapter.OfficeAdappter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.OcrBean;
import com.feifandaiyu.feifan.bean.OfficeBean;
import com.feifandaiyu.feifan.bean.PersonalUploadImageBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.ocr.RecognizeService;
import com.feifandaiyu.feifan.utils.FileUtil;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.QiNiuUtlis;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.feifandaiyu.feifan.utils.TimeUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.FunctionOptions;
import com.luck.picture.lib.model.PictureConfig;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
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

import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * Created by davidzhao on 2017/5/10.
 */

public class DaiAfterActivity extends BaseActivity implements BaiduMap.OnMapStatusChangeListener, OnGetGeoCoderResultListener {

    private static final int REQUEST_CODE_ACCURATE_BASIC = 101;
    @InjectView(R.id.et_gps_number1)
    EditText etGpsNumber;
    @InjectView(R.id.rv_daben1)
    RecyclerView rvDaben;
    @InjectView(R.id.iv_jiaoqiangxian1)
    ImageView ivJiaoqiangxian;
    @InjectView(R.id.iv_del_jiaoqiangxian1)
    ImageView ivDelJiaoqiangxian;
    @InjectView(R.id.rl_jiaoqiangxianpic1)
    RelativeLayout rlJiaoqiangxianpic;
    @InjectView(R.id.custom_qiangxian1)
    RecyclerView customQiangxian;
    @InjectView(R.id.iv_fapiao1)
    ImageView ivFapiao;
    @InjectView(R.id.iv_del_fapiao1)
    ImageView ivDelFapiao;
    @InjectView(R.id.rl_fapiaopic1)
    RelativeLayout rlFapiaopic;
    @InjectView(R.id.ll_fapiao1)
    LinearLayout llFapiao;
    @InjectView(R.id.rl_fapiao1)
    RelativeLayout rlFapiao;
    @InjectView(R.id.custom_shangxian1)
    RecyclerView customShangxian;
    @InjectView(R.id.custom_fengdang1)
    RecyclerView customFengdang;
    @InjectView(R.id.custom_chepaihao1)
    RecyclerView customChepaihao;
    @InjectView(R.id.ll_xieyi1)
    LinearLayout llXieyi;
    @InjectView(R.id.rl_xieyi1)
    RelativeLayout rlXieyi;
    @InjectView(R.id.custom_anzhuang1)
    RecyclerView customAnzhuang;
    @InjectView(R.id.custom_kehu1)
    RecyclerView customKehu;
    @InjectView(R.id.custom_ershou1)
    RecyclerView customErshou;
    @InjectView(R.id.custom_xingshizheng1)
    RecyclerView customXingshizheng;
    @InjectView(R.id.custom_baoxianfapiao1)
    RecyclerView customBaoxianfapiao;
    @InjectView(R.id.custom_fapiao1)
    RecyclerView customFapiao;
    @InjectView(R.id.tv_choose_office1)
    TextView tvChooseOffice;
    @InjectView(R.id.bmapView1)
    MapView bmapView;
    @InjectView(R.id.view_center1)
    View viewCenter;
    @InjectView(R.id.img_location_point1)
    ImageView imgLocationPoint;
    @InjectView(R.id.img_location_back_origin1)
    ImageView imgLocationBackOrigin;
    @InjectView(R.id.iv_location1)
    ImageView ivLocation;
    @InjectView(R.id.tv_addr1)
    TextView tvAddr;
    @InjectView(R.id.rl_location1)
    RelativeLayout rlLocation;

    private boolean isTouch = true;
    private boolean isFirstLoc = true;
    private GeoCoder mSearch;
    private Double mLatitude;
    private Double mLongitude;

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

    private int themeStyle;
    private int previewColor, completeColor, previewBottomBgColor, previewTopBgColor, bottomBgColor, checkedBoxDrawable;
    private int copyMode = FunctionConfig.CROP_MODEL_DEFAULT;
    private boolean enableCrop = false;
    private KProgressHUD hud;
    private boolean hasGotToken = false;
    private AlertDialog.Builder alertDialog;
    private WindowManager.LayoutParams params;
    private int maxSelectNum = 100;
    private int selectType = FunctionConfig.TYPE_IMAGE;
    private List<LocalMedia> selectMedia = new ArrayList<>();
    FunctionOptions options = new FunctionOptions.Builder()
            .setType(FunctionConfig.TYPE_IMAGE) // 图片or视频 FunctionConfig.TYPE_IMAGE  TYPE_VIDEO
            .setCropMode(FunctionConfig.CROP_MODEL_DEFAULT) // 裁剪模式 默认、1:1、3:4、3:2、16:9
            .setCompress(true) //是否压缩
            .setEnablePixelCompress(true) //是否启用像素压缩
            .setEnableQualityCompress(true) //是否启质量压缩
            .setMaxSelectNum(1) // 可选择图片的数量
            .setSelectMode(FunctionConfig.MODE_MULTIPLE) // 单选 or 多选
            .setShowCamera(true) //是否显示拍照选项 这里自动根据type 启动拍照或录视频
            .setEnablePreview(true) // 是否打开预览选项
            .setEnableCrop(false) // 是否打开剪切选项
            .setPreviewVideo(true) // 是否预览视频(播放) mode or 多选有效

            .setRecordVideoDefinition(FunctionConfig.HIGH) // 视频清晰度
            .setRecordVideoSecond(60) // 视频秒数
            .setGif(false)// 是否显示gif图片，默认不显示
            .setCropW(200) // cropW-->裁剪宽度 值不能小于100  如果值大于图片原始宽高 将返回原图大小
            .setCropH(200) // cropH-->裁剪高度 值不能小于100 如果值大于图片原始宽高 将返回原图大小
            .setMaxB(102400) // 压缩最大值 例如:200kb  就设置202400，202400 / 1024 = 200kb
            .setEnablePixelCompress(true)
            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
            .setCheckNumMode(false)
            .setCompressQuality(100) // 图片裁剪质量,默认无损
            .setImageSpanCount(4) // 每行个数
            .setSelectMedia(selectMedia) // 已选图片，传入在次进去可选中，不能传入网络图片
            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
            .create();
    private List<LocalMedia> selectMedia1 = new ArrayList<>();
    private List<LocalMedia> selectMedia2 = new ArrayList<>();
    private List<LocalMedia> selectMedia3 = new ArrayList<>();
    private List<LocalMedia> selectMedia4 = new ArrayList<>();
    private List<LocalMedia> selectMedia5 = new ArrayList<>();
    private List<LocalMedia> selectMedia6 = new ArrayList<>();
    private List<LocalMedia> selectMedia7 = new ArrayList<>();
    private List<LocalMedia> selectMedia8 = new ArrayList<>();
    private List<LocalMedia> selectMedia9 = new ArrayList<>();
    private List<LocalMedia> selectMedia10 = new ArrayList<>();
    private List<LocalMedia> selectMedia11 = new ArrayList<>();
    private String path;
    private boolean mode = false;// 启动相册模式
    private FunctionOptions options1;
    private TextView tv_next;
    private GridImageAdapter adapter1;
    private GridImageAdapter adapter2;
    private GridImageAdapter adapter3;
    private GridImageAdapter adapter4;
    private GridImageAdapter adapter5;
    private GridImageAdapter adapter6;
    private GridImageAdapter adapter7;
    private GridImageAdapter adapter8;
    private GridImageAdapter adapter9;
    private GridImageAdapter adapter10;
    private GridImageAdapter adapter11;
    private int piccount;
    private JSONArray DabenPic;
    private JSONArray QiangxianPic;
    private JSONArray ShangxianPic;
    private JSONArray FengdangPic;
    private JSONArray ChepaihaoPic;
    private JSONArray AnzhuangPic;
    private JSONArray KehuPic;
    private JSONArray ErshouPic;
    private JSONArray XingshizhengPic;
    private JSONArray BXFapiaoPic;
    private JSONArray FapiaoPic;
    private BaiduMap mBaiduMap;
    private Point mCenterPoint = null;
    /**
     * 当前经纬度
     */
    private LatLng mLoactionLatLng;

    private LocationClient mLocClient;

    private MyLocationListener myLocationListener = new MyLocationListener();

    private UploadOptions uploadOptions;
    private int upLoadCount = 1;
    private ProgressDialog progressDialog;
    private String key;

    private OfficeAdappter adapter;
    private List<OfficeBean.ListBean> list;

    private PictureConfig.OnSelectResultCallback resultCallback1 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia1 = resultList;
            Log.i("callBack_result", selectMedia1.size() + "");
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
            if (selectMedia1 != null) {
                adapter1.setList(selectMedia1);
                adapter1.notifyDataSetChanged();
            }
        }

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia1.add(media);
            if (selectMedia1 != null) {
                adapter1.setList(selectMedia1);
                adapter1.notifyDataSetChanged();
            }
        }
    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener1 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:

                    themeStyle = ContextCompat.getColor(DaiAfterActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);

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
                            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
                            .setCheckNumMode(false)
                            .setCompressQuality(100) // 图片裁剪质量,默认无损
                            .setImageSpanCount(4) // 每行个数

                            .setSelectMedia(selectMedia1) // 已选图片，传入在次进去可选中，不能传入网络图片
                            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                            .setThemeStyle(themeStyle) // 设置主题样式
                            // 0/9 完成  样式
//                            .setPicture_title_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题字体颜色
//                            .setPicture_right_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题右边字体颜色
//                            .setLeftBackDrawable(R.mipmap.back2) // 设置返回键图标
//                            .setStatusBar(ContextCompat.getColor(MainActivity.this, R.color.white)) // 设置状态栏颜色，默认是和标题栏一致
//                            .setImmersive(false)// 是否改变状态栏字体颜色(黑色)
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(DaiAfterActivity.this, resultCallback1);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(DaiAfterActivity.this, resultCallback1);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia1.remove(position);
                    adapter1.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };

    private PictureConfig.OnSelectResultCallback resultCallback2 = new PictureConfig.OnSelectResultCallback() {
        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia2.add(media);
            if (selectMedia2 != null) {
                adapter2.setList(selectMedia2);
                adapter2.notifyDataSetChanged();
            }
        }

        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia2 = resultList;
            Log.i("callBack_result", selectMedia2.size() + "");
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
            if (selectMedia2 != null) {
                adapter2.setList(selectMedia2);
                adapter2.notifyDataSetChanged();
            }
        }


    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener2 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    themeStyle = ContextCompat.getColor(DaiAfterActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);

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
                            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
                            .setCheckNumMode(false)
                            .setCompressQuality(100) // 图片裁剪质量,默认无损
                            .setImageSpanCount(4) // 每行个数

                            .setSelectMedia(selectMedia2) // 已选图片，传入在次进去可选中，不能传入网络图片
                            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                            .setThemeStyle(themeStyle) // 设置主题样式
                            // 0/9 完成  样式
//                            .setPicture_title_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题字体颜色
//                            .setPicture_right_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题右边字体颜色
//                            .setLeftBackDrawable(R.mipmap.back2) // 设置返回键图标
//                            .setStatusBar(ContextCompat.getColor(MainActivity.this, R.color.white)) // 设置状态栏颜色，默认是和标题栏一致
//                            .setImmersive(false)// 是否改变状态栏字体颜色(黑色)
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(DaiAfterActivity.this, resultCallback2);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(DaiAfterActivity.this, resultCallback2);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia2.remove(position);
                    adapter2.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };

    private PictureConfig.OnSelectResultCallback resultCallback3 = new PictureConfig.OnSelectResultCallback() {
        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia.add(media);
            if (selectMedia3 != null) {
                adapter3.setList(selectMedia3);
                adapter3.notifyDataSetChanged();
            }
        }

        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia3 = resultList;
            Log.i("callBack_result", selectMedia3.size() + "");
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
            if (selectMedia3 != null) {
                adapter3.setList(selectMedia3);
                adapter3.notifyDataSetChanged();
            }
        }


    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener3 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(DaiAfterActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);

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
                            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
                            .setCheckNumMode(false)
                            .setCompressQuality(100) // 图片裁剪质量,默认无损
                            .setImageSpanCount(4) // 每行个数

                            .setSelectMedia(selectMedia3) // 已选图片，传入在次进去可选中，不能传入网络图片
                            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                            .setThemeStyle(themeStyle) // 设置主题样式
                            // 0/9 完成  样式
//                            .setPicture_title_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题字体颜色
//                            .setPicture_right_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题右边字体颜色
//                            .setLeftBackDrawable(R.mipmap.back2) // 设置返回键图标
//                            .setStatusBar(ContextCompat.getColor(MainActivity.this, R.color.white)) // 设置状态栏颜色，默认是和标题栏一致
//                            .setImmersive(false)// 是否改变状态栏字体颜色(黑色)
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(DaiAfterActivity.this, resultCallback3);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(DaiAfterActivity.this, resultCallback3);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia3.remove(position);
                    adapter3.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };

    private PictureConfig.OnSelectResultCallback resultCallback4 = new PictureConfig.OnSelectResultCallback() {
        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia4.add(media);
            if (selectMedia4 != null) {
                adapter4.setList(selectMedia4);
                adapter4.notifyDataSetChanged();
            }
        }

        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia4 = resultList;
            Log.i("callBack_result", selectMedia4.size() + "");
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
            if (selectMedia4 != null) {
                adapter4.setList(selectMedia4);
                adapter4.notifyDataSetChanged();
            }
        }


    };
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener4 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(DaiAfterActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);

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
                            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
                            .setCheckNumMode(false)
                            .setCompressQuality(100) // 图片裁剪质量,默认无损
                            .setImageSpanCount(4) // 每行个数

                            .setSelectMedia(selectMedia4) // 已选图片，传入在次进去可选中，不能传入网络图片
                            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                            .setThemeStyle(themeStyle) // 设置主题样式
                            // 0/9 完成  样式
//                            .setPicture_title_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题字体颜色
//                            .setPicture_right_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题右边字体颜色
//                            .setLeftBackDrawable(R.mipmap.back2) // 设置返回键图标
//                            .setStatusBar(ContextCompat.getColor(MainActivity.this, R.color.white)) // 设置状态栏颜色，默认是和标题栏一致
//                            .setImmersive(false)// 是否改变状态栏字体颜色(黑色)
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(DaiAfterActivity.this, resultCallback4);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(DaiAfterActivity.this, resultCallback4);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia4.remove(position);
                    adapter4.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };
    private PictureConfig.OnSelectResultCallback resultCallback5 = new PictureConfig.OnSelectResultCallback() {
        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia5.add(media);
            if (selectMedia5 != null) {
                adapter5.setList(selectMedia5);
                adapter5.notifyDataSetChanged();
            }
        }

        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia5 = resultList;
            Log.i("callBack_result", selectMedia5.size() + "");
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
            if (selectMedia5 != null) {
                adapter5.setList(selectMedia5);
                adapter5.notifyDataSetChanged();
            }
        }


    };
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener5 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(DaiAfterActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);

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
                            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
                            .setCheckNumMode(false)
                            .setCompressQuality(100) // 图片裁剪质量,默认无损
                            .setImageSpanCount(4) // 每行个数

                            .setSelectMedia(selectMedia5) // 已选图片，传入在次进去可选中，不能传入网络图片
                            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                            .setThemeStyle(themeStyle) // 设置主题样式
                            // 0/9 完成  样式
//                            .setPicture_title_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题字体颜色
//                            .setPicture_right_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题右边字体颜色
//                            .setLeftBackDrawable(R.mipmap.back2) // 设置返回键图标
//                            .setStatusBar(ContextCompat.getColor(MainActivity.this, R.color.white)) // 设置状态栏颜色，默认是和标题栏一致
//                            .setImmersive(false)// 是否改变状态栏字体颜色(黑色)
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(DaiAfterActivity.this, resultCallback5);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(DaiAfterActivity.this, resultCallback5);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia5.remove(position);
                    adapter5.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };
    private PictureConfig.OnSelectResultCallback resultCallback6 = new PictureConfig.OnSelectResultCallback() {
        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia6.add(media);
            if (selectMedia6 != null) {
                adapter6.setList(selectMedia6);
                adapter6.notifyDataSetChanged();
            }
        }

        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia6 = resultList;
            Log.i("callBack_result", selectMedia6.size() + "");
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
            if (selectMedia6 != null) {
                adapter6.setList(selectMedia6);
                adapter6.notifyDataSetChanged();
            }
        }


    };
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener6 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(DaiAfterActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);

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
                            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
                            .setCheckNumMode(false)
                            .setCompressQuality(100) // 图片裁剪质量,默认无损
                            .setImageSpanCount(4) // 每行个数

                            .setSelectMedia(selectMedia6) // 已选图片，传入在次进去可选中，不能传入网络图片
                            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                            .setThemeStyle(themeStyle) // 设置主题样式
                            // 0/9 完成  样式
//                            .setPicture_title_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题字体颜色
//                            .setPicture_right_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题右边字体颜色
//                            .setLeftBackDrawable(R.mipmap.back2) // 设置返回键图标
//                            .setStatusBar(ContextCompat.getColor(MainActivity.this, R.color.white)) // 设置状态栏颜色，默认是和标题栏一致
//                            .setImmersive(false)// 是否改变状态栏字体颜色(黑色)
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(DaiAfterActivity.this, resultCallback6);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(DaiAfterActivity.this, resultCallback6);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia6.remove(position);
                    adapter6.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };
    private PictureConfig.OnSelectResultCallback resultCallback7 = new PictureConfig.OnSelectResultCallback() {
        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia7.add(media);
            if (selectMedia7 != null) {
                adapter7.setList(selectMedia7);
                adapter7.notifyDataSetChanged();
            }
        }

        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia7 = resultList;
            Log.i("callBack_result", selectMedia7.size() + "");
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
            if (selectMedia7 != null) {
                adapter7.setList(selectMedia7);
                adapter7.notifyDataSetChanged();
            }
        }


    };
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener7 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(DaiAfterActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);

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
                            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
                            .setCheckNumMode(false)
                            .setCompressQuality(100) // 图片裁剪质量,默认无损
                            .setImageSpanCount(4) // 每行个数

                            .setSelectMedia(selectMedia7) // 已选图片，传入在次进去可选中，不能传入网络图片
                            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                            .setThemeStyle(themeStyle) // 设置主题样式
                            // 0/9 完成  样式
//                            .setPicture_title_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题字体颜色
//                            .setPicture_right_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题右边字体颜色
//                            .setLeftBackDrawable(R.mipmap.back2) // 设置返回键图标
//                            .setStatusBar(ContextCompat.getColor(MainActivity.this, R.color.white)) // 设置状态栏颜色，默认是和标题栏一致
//                            .setImmersive(false)// 是否改变状态栏字体颜色(黑色)
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(DaiAfterActivity.this, resultCallback7);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(DaiAfterActivity.this, resultCallback7);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia7.remove(position);
                    adapter7.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };
    private PictureConfig.OnSelectResultCallback resultCallback8 = new PictureConfig.OnSelectResultCallback() {
        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia8.add(media);
            if (selectMedia8 != null) {
                adapter8.setList(selectMedia8);
                adapter8.notifyDataSetChanged();
            }
        }

        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia8 = resultList;
            Log.i("callBack_result", selectMedia8.size() + "");
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
            if (selectMedia8 != null) {
                adapter8.setList(selectMedia8);
                adapter8.notifyDataSetChanged();
            }
        }


    };
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener8 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(DaiAfterActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);

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
                            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
                            .setCheckNumMode(false)
                            .setCompressQuality(100) // 图片裁剪质量,默认无损
                            .setImageSpanCount(4) // 每行个数

                            .setSelectMedia(selectMedia8) // 已选图片，传入在次进去可选中，不能传入网络图片
                            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                            .setThemeStyle(themeStyle) // 设置主题样式
                            // 0/9 完成  样式
//                            .setPicture_title_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题字体颜色
//                            .setPicture_right_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题右边字体颜色
//                            .setLeftBackDrawable(R.mipmap.back2) // 设置返回键图标
//                            .setStatusBar(ContextCompat.getColor(MainActivity.this, R.color.white)) // 设置状态栏颜色，默认是和标题栏一致
//                            .setImmersive(false)// 是否改变状态栏字体颜色(黑色)
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(DaiAfterActivity.this, resultCallback8);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(DaiAfterActivity.this, resultCallback8);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia8.remove(position);
                    adapter8.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };
    private PictureConfig.OnSelectResultCallback resultCallback9 = new PictureConfig.OnSelectResultCallback() {
        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia9.add(media);
            if (selectMedia9 != null) {
                adapter9.setList(selectMedia9);
                adapter9.notifyDataSetChanged();
            }
        }

        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia9 = resultList;
            Log.i("callBack_result", selectMedia9.size() + "");
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
            if (selectMedia9 != null) {
                adapter9.setList(selectMedia9);
                adapter9.notifyDataSetChanged();
            }
        }


    };
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener9 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(DaiAfterActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);

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
                            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
                            .setCheckNumMode(false)
                            .setCompressQuality(100) // 图片裁剪质量,默认无损
                            .setImageSpanCount(4) // 每行个数

                            .setSelectMedia(selectMedia9) // 已选图片，传入在次进去可选中，不能传入网络图片
                            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                            .setThemeStyle(themeStyle) // 设置主题样式
                            // 0/9 完成  样式
//                            .setPicture_title_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题字体颜色
//                            .setPicture_right_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题右边字体颜色
//                            .setLeftBackDrawable(R.mipmap.back2) // 设置返回键图标
//                            .setStatusBar(ContextCompat.getColor(MainActivity.this, R.color.white)) // 设置状态栏颜色，默认是和标题栏一致
//                            .setImmersive(false)// 是否改变状态栏字体颜色(黑色)
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(DaiAfterActivity.this, resultCallback9);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(DaiAfterActivity.this, resultCallback9);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia9.remove(position);
                    adapter9.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };
    private PictureConfig.OnSelectResultCallback resultCallback10 = new PictureConfig.OnSelectResultCallback() {
        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia10.add(media);
            if (selectMedia10 != null) {
                adapter10.setList(selectMedia10);
                adapter10.notifyDataSetChanged();
            }
        }

        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia10 = resultList;
            Log.i("callBack_result", selectMedia10.size() + "");
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
            if (selectMedia10 != null) {
                adapter10.setList(selectMedia10);
                adapter10.notifyDataSetChanged();
            }
        }


    };
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener10 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(DaiAfterActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);

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
                            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
                            .setCheckNumMode(false)
                            .setCompressQuality(100) // 图片裁剪质量,默认无损
                            .setImageSpanCount(4) // 每行个数

                            .setSelectMedia(selectMedia10) // 已选图片，传入在次进去可选中，不能传入网络图片
                            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                            .setThemeStyle(themeStyle) // 设置主题样式
                            // 0/9 完成  样式
//                            .setPicture_title_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题字体颜色
//                            .setPicture_right_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题右边字体颜色
//                            .setLeftBackDrawable(R.mipmap.back2) // 设置返回键图标
//                            .setStatusBar(ContextCompat.getColor(MainActivity.this, R.color.white)) // 设置状态栏颜色，默认是和标题栏一致
//                            .setImmersive(false)// 是否改变状态栏字体颜色(黑色)
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(DaiAfterActivity.this, resultCallback10);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(DaiAfterActivity.this, resultCallback10);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia10.remove(position);
                    adapter10.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };
    private PictureConfig.OnSelectResultCallback resultCallback11 = new PictureConfig.OnSelectResultCallback() {
        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia11.add(media);
            if (selectMedia11 != null) {
                adapter11.setList(selectMedia11);
                adapter11.notifyDataSetChanged();
            }
        }

        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia11 = resultList;
            Log.i("callBack_result", selectMedia11.size() + "");
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
            if (selectMedia11 != null) {
                adapter11.setList(selectMedia11);
                adapter11.notifyDataSetChanged();
            }
        }


    };
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener11 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(DaiAfterActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(DaiAfterActivity.this, R.color.tab_color_true);

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
                            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
                            .setCheckNumMode(false)
                            .setCompressQuality(100) // 图片裁剪质量,默认无损
                            .setImageSpanCount(4) // 每行个数

                            .setSelectMedia(selectMedia11) // 已选图片，传入在次进去可选中，不能传入网络图片
                            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                            .setThemeStyle(themeStyle) // 设置主题样式
                            // 0/9 完成  样式
//                            .setPicture_title_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题字体颜色
//                            .setPicture_right_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题右边字体颜色
//                            .setLeftBackDrawable(R.mipmap.back2) // 设置返回键图标
//                            .setStatusBar(ContextCompat.getColor(MainActivity.this, R.color.white)) // 设置状态栏颜色，默认是和标题栏一致
//                            .setImmersive(false)// 是否改变状态栏字体颜色(黑色)
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(DaiAfterActivity.this, resultCallback11);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(DaiAfterActivity.this, resultCallback11);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia11.remove(position);
                    adapter11.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };
    private String jiaoqiangxian;
    private String jiaoqiangxianShow;
    private String fapiao;
    //    private String fapiaoShow;
    private OcrBean bean;
    private String words;
    private String rid;
    private String userId;
    private String carId;
    private String mid;
    private String isNew;


    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("正在上传");

        progressDialog.show();

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                upLoadCount = 0;
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.inject(this);
//    }

    @Override
    protected int getContentView() {
        return R.layout.activity_daiafter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
        setTitle("");
        showNext(true);
        initRecylerView();
        initmap();
        userId = PreferenceUtils.getString(DaiAfterActivity.this, "userId");
        carId = PreferenceUtils.getString(DaiAfterActivity.this, "carId");
        mid = PreferenceUtils.getString(DaiAfterActivity.this, "mid");

        isNew = PreferenceUtils.getString(DaiAfterActivity.this, "isNew");
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍后")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        alertDialog = new AlertDialog.Builder(this);

        initData();

        initAccessTokenWithAkSk();
//
//        if (isNew.equals("1")) {
//            rlXieyi.setVisibility(View.GONE);
//            llXieyi.setVisibility(View.GONE);
//            llChepai.setVisibility(View.VISIBLE);
//            llChejiahao.setVisibility(View.VISIBLE);
//        } else if (isNew.equals("2")) {
////            rlXingshizheng.setVisibility(View.GONE);
////            llXingshizheng.setVisibility(View.GONE);
//            rlFapiao.setVisibility(View.GONE);
//            llFapiao.setVisibility(View.GONE);
////            rlHegezheng.setVisibility(View.GONE);
////            llHegezheng.setVisibility(View.GONE);
//            llChepai.setVisibility(View.GONE);
//            llChejiahao.setVisibility(View.GONE);
//        }

        tv_next = (TextView) findViewById(R.id.tv_next);

        tv_next.setEnabled(true);

        tv_next.setText("上传");
        /* adapter = new GridImageAdapter(this, onAddPicClickListener);*/
    }

    private void initRecylerView() {
        LinearLayoutManager ms1 = new LinearLayoutManager(this);
        LinearLayoutManager ms2 = new LinearLayoutManager(this);
        LinearLayoutManager ms3 = new LinearLayoutManager(this);
        LinearLayoutManager ms4 = new LinearLayoutManager(this);
        LinearLayoutManager ms5 = new LinearLayoutManager(this);
        LinearLayoutManager ms6 = new LinearLayoutManager(this);
        LinearLayoutManager ms7 = new LinearLayoutManager(this);
        LinearLayoutManager ms8 = new LinearLayoutManager(this);
        LinearLayoutManager ms9 = new LinearLayoutManager(this);
        LinearLayoutManager ms10 = new LinearLayoutManager(this);
        LinearLayoutManager ms11 = new LinearLayoutManager(this);

        ms1.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms2.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms3.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms4.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms5.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms6.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms7.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms8.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms9.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms10.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms11.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvDaben.setLayoutManager(ms1);
        customQiangxian.setLayoutManager(ms2);
        customShangxian.setLayoutManager(ms3);
        customFengdang.setLayoutManager(ms4);
        customChepaihao.setLayoutManager(ms5);
        customAnzhuang.setLayoutManager(ms6);
        customKehu.setLayoutManager(ms7);
        customErshou.setLayoutManager(ms8);
        customXingshizheng.setLayoutManager(ms9);
        customBaoxianfapiao.setLayoutManager(ms10);
        customFapiao.setLayoutManager(ms11);

        adapter1 = new GridImageAdapter(this, onAddPicClickListener1);
        adapter1.setList(selectMedia1);
        adapter1.setSelectMax(maxSelectNum);
        rvDaben.setAdapter(adapter1);

        adapter2 = new GridImageAdapter(this, onAddPicClickListener2);
        adapter2.setList(selectMedia2);
        adapter2.setSelectMax(maxSelectNum);
        customQiangxian.setAdapter(adapter2);

        adapter3 = new GridImageAdapter(this, onAddPicClickListener3);
        adapter3.setList(selectMedia3);
        adapter3.setSelectMax(maxSelectNum);
        customShangxian.setAdapter(adapter3);

        adapter4 = new GridImageAdapter(this, onAddPicClickListener4);
        adapter4.setList(selectMedia4);
        adapter4.setSelectMax(maxSelectNum);
        customFengdang.setAdapter(adapter4);

        adapter5 = new GridImageAdapter(this, onAddPicClickListener5);
        adapter5.setList(selectMedia5);
        adapter5.setSelectMax(maxSelectNum);
        customChepaihao.setAdapter(adapter5);

        adapter6 = new GridImageAdapter(this, onAddPicClickListener6);
        adapter6.setList(selectMedia6);
        adapter6.setSelectMax(maxSelectNum);
        customAnzhuang.setAdapter(adapter6);

        adapter7 = new GridImageAdapter(this, onAddPicClickListener7);
        adapter7.setList(selectMedia7);
        adapter7.setSelectMax(maxSelectNum);
        customKehu.setAdapter(adapter7);

        adapter8 = new GridImageAdapter(this, onAddPicClickListener8);
        adapter8.setList(selectMedia8);
        adapter8.setSelectMax(maxSelectNum);
        customErshou.setAdapter(adapter8);

        adapter9 = new GridImageAdapter(this, onAddPicClickListener9);
        adapter9.setList(selectMedia9);
        adapter9.setSelectMax(maxSelectNum);
        customXingshizheng.setAdapter(adapter9);

        adapter10 = new GridImageAdapter(this, onAddPicClickListener10);
        adapter10.setList(selectMedia10);
        adapter10.setSelectMax(maxSelectNum);
        customBaoxianfapiao.setAdapter(adapter10);

        adapter11 = new GridImageAdapter(this, onAddPicClickListener11);
        adapter11.setList(selectMedia11);
        adapter11.setSelectMax(maxSelectNum);
        customFapiao.setAdapter(adapter11);


        adapter1.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(DaiAfterActivity.this, position - 1, selectMedia1);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia1.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(DaiAfterActivity.this, selectMedia1.get(position - 1).getPath());
                        }
                        break;
                }
            }
        });

        adapter2.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(DaiAfterActivity.this, position - 1, selectMedia2);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia2.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(DaiAfterActivity.this, selectMedia2.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });


        adapter3.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(DaiAfterActivity.this, position - 1, selectMedia3);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia3.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(DaiAfterActivity.this, selectMedia3.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });
        adapter4.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(DaiAfterActivity.this, position - 1, selectMedia4);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia4.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(DaiAfterActivity.this, selectMedia4.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });
        adapter5.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(DaiAfterActivity.this, position - 1, selectMedia5);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia5.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(DaiAfterActivity.this, selectMedia5.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });
        adapter6.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(DaiAfterActivity.this, position - 1, selectMedia6);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia6.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(DaiAfterActivity.this, selectMedia6.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });
        adapter7.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(DaiAfterActivity.this, position - 1, selectMedia7);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia7.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(DaiAfterActivity.this, selectMedia7.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });
        adapter8.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(DaiAfterActivity.this, position - 1, selectMedia8);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia8.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(DaiAfterActivity.this, selectMedia8.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });
        adapter9.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(DaiAfterActivity.this, position - 1, selectMedia9);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia9.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(DaiAfterActivity.this, selectMedia9.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });
        adapter10.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(DaiAfterActivity.this, position - 1, selectMedia10);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia10.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(DaiAfterActivity.this, selectMedia10.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });
        adapter11.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(DaiAfterActivity.this, position - 1, selectMedia11);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia11.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(DaiAfterActivity.this, selectMedia11.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });

    }

    private void initData() {


        jiaoqiangxian = PreferenceUtils.getString(DaiAfterActivity.this, "jiaoqiangxian");
        jiaoqiangxianShow = PreferenceUtils.getString(DaiAfterActivity.this, "jiaoqiangxianShow");
        fapiao = PreferenceUtils.getString(DaiAfterActivity.this, "fapiao");
//        fapiaoShow = PreferenceUtils.getString(DaiAfterActivity.this, "fapiaoShow");
//        LogUtils.e(fapiaoShow);
//
//        if (!fapiaoShow.equals("")) {
//            rlFapiaopic.setVisibility(View.VISIBLE);
//            ImageViewUtils.showNetImage(DaiAfterActivity.this, fapiaoShow, R.drawable.crabgnormal, ivFapiao);
//
//        }

//        if (!jiaoqiangxianShow.equals("")) {
//            rlJiaoqiangxianpic.setVisibility(View.VISIBLE);
//            ImageViewUtils.showNetImage(DaiAfterActivity.this, jiaoqiangxianShow, R.drawable.crabgnormal, ivJiaoqiangxian);
//
//        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ACCURATE_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recAccurateBasic(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
//                            infoPopText(result);
                            Log.e("hshsh", result);
                            String json = result;
                            Gson gson = new Gson();
                            try {
                                bean = gson.fromJson(json, OcrBean.class);
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                                MyToast.show(DaiAfterActivity.this, "识别失败，请重试");
                            }

                            if (bean != null) {
                                words = bean.getWords_result().toString();
                                MyToast.show(DaiAfterActivity.this, words);
                                LogUtils.e(bean.toString());
                                LogUtils.e(words);
                            }
                            try {
                                JSONObject joResult = new JSONObject(result);
                                words = joResult.getString("words_result");
                                LogUtils.e(words);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onResult2(GeneralResult result) {
                            Log.e("erereererer", result.getWordList().toString());
                        }
                    });
        }
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
    public void onPointerCaptureChanged(boolean hasCapture) {

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


    @OnClick({R.id.iv_del_jiaoqiangxian1, R.id.iv_del_fapiao1, R.id.tv_choose_office1,R.id.tv_next,R.id.iv_back})
    public void onViewClicked(View view) {

            switch (view.getId()) {
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

                case R.id.tv_choose_office1:
                    LayoutInflater inflater = LayoutInflater.from(this);//将xml布局转换为view
                    View view1 = inflater.inflate(R.layout.dialog_bank, null);//将xml布局转换为view,里面有listview
                    final ListView listView = (ListView) view1.findViewById(R.id.pop_bank);

                    final Dialog builder = new Dialog(DaiAfterActivity.this);
                    builder.setContentView(view1);
                    builder.setTitle("请选择内勤");
                    builder.show();

                    post()
                            .url(Constants.URLS.BASEURL + "UserPic/officeList")
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    LogUtils.i("officeList------------------>>>" + e);

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    LogUtils.i("officeList------------------>>>" + response);
                                    String json = response;
                                    Gson gson = new Gson();
                                    OfficeBean bean = gson.fromJson(json, OfficeBean.class);

                                    if (bean.getCode() == 1) {
                                        list = bean.getList();
                                        adapter = new OfficeAdappter(DaiAfterActivity.this, list, new OfficeAdappter.OnRadiobuttonclick() {
                                            @Override
                                            public void radioButtonck(int position) {
                                                rid = list.get(position).getRid();
                                                tvChooseOffice.setText(list.get(position).getAdmin_name());
                                                builder.dismiss();
                                            }
                                        });
                                        listView.setAdapter(adapter);//给listview设置适配器，adapter
                                    }
                                }
                            });

                    break;
                case R.id.iv_del_jiaoqiangxian1:
                    rlJiaoqiangxianpic.setVisibility(View.GONE);
                    jiaoqiangxian = "";
                    break;
                case R.id.iv_del_fapiao1:
                    rlFapiaopic.setVisibility(View.GONE);
                    fapiao = "";
                    break;


                case R.id.tv_next:
                    if(rid==null||rid.equals("")){
                        MyToast.show(this, "内勤人员不能为空");
                        return;
                    }
                    if (etGpsNumber.getText() == null || etGpsNumber.getText().toString().equals("")) {
                        MyToast.show(this, "请填写GPS设备号");
                        return;
                    }

                    if (selectMedia1.size() <= 0) {
                        MyToast.show(this, "保险发票照片未上传");
                        return;
                    }

                    if (selectMedia2.size() <= 0 && jiaoqiangxian.equals("")) {
                        MyToast.show(this, "交强险未上传");
                        return;
                    }

                    if (selectMedia3.size() <= 0) {
                        MyToast.show(this, "商业险未上传");
                        return;
                    }

//                if (isNew.equals("1")) {
//
//                    if (etChepaiNum.getText() == null || etChepaiNum.getText().toString().equals("")) {
//                        MyToast.show(this, "请填写车辆牌照");
//                        return;
//                    }
//
//                    if (etChejiaNum.getText() == null || etChejiaNum.getText().toString().equals("")) {
//                        MyToast.show(this, "请填写车架号");
//                        return;
//                    }

//                    if (selectMedia4.size() <= 0) {
//                        MyToast.show(this, "行驶证未上传");
//                        return;
//                    }

                    if (selectMedia5.size() <= 0 && fapiao.equals("")) {
                        MyToast.show(this, "车发票未上传");
                        return;
                    }

//                    if (selectMedia6.size() <= 0) {
//                        MyToast.show(this, "合格证未上传");
//                        return;
//                    }
//                } else if (isNew.equals("2")) {
//                    if (selectMedia11.size() <= 0) {
//                        MyToast.show(this, "二手车买卖协议未上传");
//                        return;
//                    }
//                }

                    if (selectMedia7.size() <= 0) {
                        MyToast.show(this, "风挡vin码和设备合影未上传");
                        return;
                    }

                    if (selectMedia8.size() <= 0) {
                        MyToast.show(this, "车牌号和设备合影未上传");
                        return;
                    }

                    if (selectMedia9.size() <= 0) {
                        MyToast.show(this, "安装位置未上传");
                        return;
                    }

                    if (selectMedia10.size() <= 0) {
                        MyToast.show(this, "客户合影未上传");
                        return;
                    }


//                if (rid == null || tvChooseOffice.getText().toString().equals("")) {
//                    MyToast.show(this, "请选择内勤人员");
//                    return;
//                }


                    showProgressDialog();

                    final List<String> keys = new ArrayList<>();

                    List<List<LocalMedia>> medias = new ArrayList<>();
                    piccount = selectMedia1.size() + selectMedia2.size() + selectMedia3.size() + selectMedia4.size() + selectMedia5.size() + selectMedia6.size()
                            + selectMedia7.size() + selectMedia8.size() + selectMedia9.size() + selectMedia10.size() + selectMedia11.size();

                    LogUtils.i("piccount", "-------------------->" + piccount);

                    medias.add(selectMedia1);
                    medias.add(selectMedia2);
                    medias.add(selectMedia3);
                    medias.add(selectMedia4);
                    medias.add(selectMedia5);
                    medias.add(selectMedia6);
                    medias.add(selectMedia7);
                    medias.add(selectMedia8);
                    medias.add(selectMedia9);
                    medias.add(selectMedia10);
                    medias.add(selectMedia11);

                    for (int i = 0; i < medias.size(); i++) {
                        List<LocalMedia> localMedias = medias.get(i);
                        for (int j = 0; j < localMedias.size(); j++) {
                            String compressPath = localMedias.get(j).getCompressPath();
                            System.out.println("compressPath=" + compressPath);
                            final int finalI = i;
                            final int finalJ = j;
                            key = UUID.randomUUID() + TimeUtils.getRandomFileName() + ".png";
                            if (finalI == 0) {
                                if (DabenPic == null) {
                                    DabenPic = new JSONArray();
                                }
                                try {
                                    DabenPic.put(finalJ, key);

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            } else if (finalI == 1) {
                                if (QiangxianPic == null) {
                                    QiangxianPic = new JSONArray();
                                }

                                try {
                                    QiangxianPic.put(finalJ, key);

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            } else if (finalI == 2) {
                                if (ShangxianPic == null) {
                                    ShangxianPic = new JSONArray();
                                }
                                try {
                                    ShangxianPic.put(finalJ, key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 3) {
                                if (FengdangPic == null) {
                                    FengdangPic = new JSONArray();
                                }
                                try {
                                    FengdangPic.put(finalJ, key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 4) {
                                if (ChepaihaoPic == null) {
                                    ChepaihaoPic = new JSONArray();
                                }
                                try {
                                    ChepaihaoPic.put(finalJ, key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 5) {
                                if (AnzhuangPic == null) {
                                    AnzhuangPic = new JSONArray();
                                }
                                try {
                                    AnzhuangPic.put(finalJ, key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 6) {
                                if (KehuPic == null) {
                                    KehuPic = new JSONArray();
                                }
                                try {
                                    KehuPic.put(finalJ, key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 7) {
                                if (ErshouPic == null) {
                                    ErshouPic = new JSONArray();
                                }
                                try {
                                    ErshouPic.put(finalJ, key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 8) {
                                if (XingshizhengPic == null) {
                                    XingshizhengPic = new JSONArray();
                                }
                                try {
                                    XingshizhengPic.put(finalJ, key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 9) {
                                if (BXFapiaoPic == null) {
                                    BXFapiaoPic = new JSONArray();
                                }
                                try {
                                    BXFapiaoPic.put(finalJ, key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 10) {
                                if (FapiaoPic == null) {
                                    FapiaoPic = new JSONArray();
                                }
                                try {
                                    FapiaoPic.put(finalJ, key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                            LogUtils.i("ceshi---------------------------------" + key);

                            QiNiuUtlis.upLoad(compressPath, key, new UpCompletionHandler() {
                                        @Override
                                        public void complete(String key, ResponseInfo info, JSONObject response) {

                                        }
                                    }

                                    , uploadOptions = new UploadOptions(null, null, false,
                                            new UpProgressHandler() {
                                                @Override
                                                public void progress(String key, double percent) {
                                                    LogUtils.i("percent " + "----------->>>" + percent);
                                                    if (percent == 1.0) {
                                                        upLoadCount++;
                                                        LogUtils.i("upLoadCount", "-------------------->" + upLoadCount);

                                                        System.out.println(upLoadCount == piccount);
                                                        if (upLoadCount == piccount) {

                                                            LogUtils.e("DabenPic=" + DabenPic);
                                                            LogUtils.e("QiangxianPic=" + QiangxianPic);
                                                            LogUtils.e("ShangxianPic=" + ShangxianPic);
                                                            LogUtils.e("XingshizhengPic=" + XingshizhengPic);
                                                            LogUtils.e("FapiaoPic=" + FapiaoPic);
//                                                        LogUtils.e("HegezhengPic=" + HegezhengPic);
//                                                        LogUtils.e("FengdangPic=" + FengdangPic);
//                                                        LogUtils.e("ChepaiPic=" + ChepaiPic);
//                                                        LogUtils.e("AnzhuangPic=" + AnzhuangPic);
//                                                        LogUtils.e("HeyingPic=" + HeyingPic);
//                                                        LogUtils.e("XieyiPic=" + XieyiPic);

                                                            OkHttpUtils
                                                                    .post()
                                                                    .url(Constants.URLS.BASEURL + "Login/addImei")
                                                                    .addParams("carId",carId)
                                                                    .addParams("imei", etGpsNumber.getText().toString())
                                                                    .addParams("flag", "0")
                                                                    .addParams("carType", "2")
                                                                    .addParams("afterMortgage", DabenPic.toString())
                                                                    .addParams("insurance", QiangxianPic.toString())
                                                                    .addParams("commercial", ShangxianPic.toString())
                                                                    .addParams("vincode", FengdangPic.toString())
                                                                    .addParams("position", AnzhuangPic.toString())
                                                                    .addParams("licensenum", ChepaihaoPic.toString())
                                                                    .addParams("groupPhoto", KehuPic.toString())
                                                                    .addParams("drivingLicense", XingshizhengPic.toString())
                                                                    .addParams("lnsuranceInvoice", BXFapiaoPic.toString())
                                                                    .addParams("invoice", FapiaoPic == null ? "-1" : FapiaoPic.toString())//
                                                                    .addParams("saleAgreement", ErshouPic.toString())
                                                                    .addParams("rid", rid)
                                                                    .addParams("mid", mid)
                                                                    .addParams("lat", mLatitude.toString())
                                                                    .addParams("lng", mLongitude.toString())
                                                                    .build()
                                                                    .execute(new StringCallback() {
                                                                        @Override
                                                                        public void onError(Call call, Exception e, int id) {
                                                                            progressDialog.dismiss();
                                                                            upLoadCount = 0;
                                                                            LogUtils.e("personalUploadImage------------->>>>" + e);
                                                                        }

                                                                        @Override
                                                                        public void onResponse(String response, int id) {
                                                                            LogUtils.e("personalUploadImage------------->>>>" + response);
                                                                            progressDialog.dismiss();

                                                                            String json = response;
                                                                            Gson gson = new Gson();
                                                                            PersonalUploadImageBean bean = gson.fromJson(json, PersonalUploadImageBean.class);

                                                                            if (bean.getCode() == 1) {

//                                                                            startActivity(new Intent(DiyaPicActivity.this, CustomerDrawNameActivity.class));
                                                                                // 设置过渡动画
//                                                                            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
//                                                                            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
//                                                                            overridePendingTransition(enterAnim6, exitAnim6);
                                                                                MyToast.show(DaiAfterActivity.this, "上传成功");

                                                                                finish();

                                                                            } else {
                                                                                upLoadCount = 0;

                                                                                MyToast.show(DaiAfterActivity.this, bean.getMsg());
                                                                            }


                                                                        }
                                                                    });
                                                        }
                                                    }

                                                }
                                            }, null));


                                          break;

                        }}}}




//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

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




