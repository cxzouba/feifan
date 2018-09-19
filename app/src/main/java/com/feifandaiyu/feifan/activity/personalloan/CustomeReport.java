package com.feifandaiyu.feifan.activity.personalloan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.GridImageAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CustomeReportBean;
import com.feifandaiyu.feifan.bean.MsgBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.ocr.CameraActivity;
import com.feifandaiyu.feifan.ocr.RecognizeService;
import com.feifandaiyu.feifan.utils.FileUtil;
import com.feifandaiyu.feifan.utils.FullyGridLayoutManager;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.QiNiuUtlis;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.feifandaiyu.feifan.utils.TimeUtils;
import com.feifandaiyu.feifan.view.MyPopWindow;
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
import com.tuyenmonkey.mkloader.MKLoader;
import com.yalantis.ucrop.entity.LocalMedia;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;


public class CustomeReport extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    private static final int REQUEST_CODE_CAMERA = 102;
    private static final int REQUEST_CODE_ACCURATE_BASIC = 103;
    @InjectView(R.id.recycler)
    RecyclerView recyclerView;
    @InjectView(R.id.loading)
    MKLoader loading;
    @InjectView(R.id.rb_own)
    RadioButton rbOwn;
    @InjectView(R.id.rb_other)
    RadioButton rbOther;
    @InjectView(R.id.rg_isnew)
    RadioGroup rgIsnew;
    @InjectView(R.id.et_customer_name)
    PowerfulEditText etCustomerName;
    @InjectView(R.id.cemera1)
    ImageView cemera1;
    @InjectView(R.id.et_certificate_num)
    PowerfulEditText etCertificateNum;
    @InjectView(R.id.rv_yuqi)
    RecyclerView rvYuqi;
    @InjectView(R.id.remark)
    EditText remark;
    @InjectView(R.id.tv_baogao)
    TextView tvBaogao;
    @InjectView(R.id.ll_zhengxin)
    LinearLayout llZhengxin;
    @InjectView(R.id.ll_yuqi)
    LinearLayout llYuqi;
    @InjectView(R.id.scroll)
    ScrollView scroll;
    @InjectView(R.id.tv_certificate_date)
    TextView tvCertificateDate;
    @InjectView(R.id.cemera2)
    ImageView cemera2;
    @InjectView(R.id.et_jiashi_num)
    PowerfulEditText etJiashiNum;
    private GridImageAdapter adapter1;
    private GridImageAdapter adapter2;
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
    private List<LocalMedia> selectMedia1 = new ArrayList<>();
    private TextView tv;
    private String compressPath;
    private ImageView ivBack;
    private JSONArray image;
    private JSONArray fujia;
    private UploadOptions uploadOptions;
    private int piccount;
    private int upLoadCount = 0;
    private int credit;
    private ProgressDialog progressDialog;
    private KProgressHUD hud;
    private String carId;
    private String isNew;
    private String saleID;
    private AlertDialog.Builder alertDialog;
    private boolean hasGotToken = false;
    private boolean isOk = false;
    private boolean isJiashi = false;

    private WindowManager.LayoutParams params;
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
    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback1 = new PictureConfig.OnSelectResultCallback() {
        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia1.add(media);
            if (selectMedia1 != null) {
                adapter2.setList(selectMedia1);
                adapter2.notifyDataSetChanged();
            }
        }

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
                adapter2.setList(selectMedia1);
                adapter2.notifyDataSetChanged();
            }
        }


    };
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener1 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    // 进入相册
                    /**
                     * type --> 1图片 or 2视频
                     * copyMode -->裁剪比例，默认、1:1、3:4、3:2、16:9
                     * maxSelectNum --> 可选择图片的数量
                     * selectMode         --> 单选 or 多选
                     * isShow       --> 是否显示拍照选项 这里自动根据type 启动拍照或录视频
                     * isPreview    --> 是否打开预览选项
                     * isCrop       --> 是否打开剪切选项
                     * isPreviewVideo -->是否预览视频(播放) mode or 多选有效
                     * ThemeStyle -->主题颜色
                     * CheckedBoxDrawable -->图片勾选样式
                     * cropW-->裁剪宽度 值不能小于100  如果值大于图片原始宽高 将返回原图大小
                     * cropH-->裁剪高度 值不能小于100
                     * isCompress -->是否压缩图片
                     * setEnablePixelCompress 是否启用像素压缩
                     * setEnableQualityCompress 是否启用质量压缩
                     * setRecordVideoSecond 录视频的秒数，默认不限制
                     * setRecordVideoDefinition 视频清晰度  Constants.HIGH 清晰  Constants.ORDINARY 低质量
                     * setImageSpanCount -->每行显示个数
                     * setCheckNumMode 是否显示QQ选择风格(带数字效果)
                     * setPreviewColor 预览文字颜色
                     * setCompleteColor 完成文字颜色
                     * setPreviewBottomBgColor 预览界面底部背景色
                     * setBottomBgColor 选择图片页面底部背景色
                     * setCompressQuality 设置裁剪质量，默认无损裁剪
                     * setSelectMedia 已选择的图片
                     * setCompressFlag 1为系统自带压缩  2为第三方luban压缩
                     * 注意-->type为2时 设置isPreview or isCrop 无效
                     * 注意：Options可以为空，默认标准模式
                     */

                    themeStyle = ContextCompat.getColor(CustomeReport.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(CustomeReport.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(CustomeReport.this, R.color.tab_color_true);

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
                            // 0/9 完成  样式
//                            .setPicture_title_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题字体颜色
//                            .setPicture_right_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题右边字体颜色
//                            .setLeftBackDrawable(R.mipmap.back2) // 设置返回键图标
//                            .setStatusBar(ContextCompat.getColor(MainActivity.this, R.color.white)) // 设置状态栏颜色，默认是和标题栏一致
//                            .setImmersive(false)// 是否改变状态栏字体颜色(黑色)
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(CustomeReport.this, resultCallback);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(CustomeReport.this, resultCallback);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia.remove(position);
                    adapter1.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };


    //    private void initWebView() {
//        wvXieyi.getSettings().setJavaScriptEnabled(true);
//        wvXieyi.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
//        wvXieyi.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//        wvXieyi.loadUrl("http://specaildata.hrbffdy.com/web.html");
//        wvXieyi.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//    }
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener2 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    // 进入相册
                    /**
                     * type --> 1图片 or 2视频
                     * copyMode -->裁剪比例，默认、1:1、3:4、3:2、16:9
                     * maxSelectNum --> 可选择图片的数量
                     * selectMode         --> 单选 or 多选
                     * isShow       --> 是否显示拍照选项 这里自动根据type 启动拍照或录视频
                     * isPreview    --> 是否打开预览选项
                     * isCrop       --> 是否打开剪切选项
                     * isPreviewVideo -->是否预览视频(播放) mode or 多选有效
                     * ThemeStyle -->主题颜色
                     * CheckedBoxDrawable -->图片勾选样式
                     * cropW-->裁剪宽度 值不能小于100  如果值大于图片原始宽高 将返回原图大小
                     * cropH-->裁剪高度 值不能小于100
                     * isCompress -->是否压缩图片
                     * setEnablePixelCompress 是否启用像素压缩
                     * setEnableQualityCompress 是否启用质量压缩
                     * setRecordVideoSecond 录视频的秒数，默认不限制
                     * setRecordVideoDefinition 视频清晰度  Constants.HIGH 清晰  Constants.ORDINARY 低质量
                     * setImageSpanCount -->每行显示个数
                     * setCheckNumMode 是否显示QQ选择风格(带数字效果)
                     * setPreviewColor 预览文字颜色
                     * setCompleteColor 完成文字颜色
                     * setPreviewBottomBgColor 预览界面底部背景色
                     * setBottomBgColor 选择图片页面底部背景色
                     * setCompressQuality 设置裁剪质量，默认无损裁剪
                     * setSelectMedia 已选择的图片
                     * setCompressFlag 1为系统自带压缩  2为第三方luban压缩
                     * 注意-->type为2时 设置isPreview or isCrop 无效
                     * 注意：Options可以为空，默认标准模式
                     */

                    themeStyle = ContextCompat.getColor(CustomeReport.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(CustomeReport.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(CustomeReport.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(CustomeReport.this, resultCallback1);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(CustomeReport.this, resultCallback1);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia1.remove(position);
                    adapter2.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };
    private MsgBean msgBean;

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
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

                    etCustomerName.setText(result.getName().toString());
                    etCertificateNum.setText(result.getIdNumber().toString());

                }
            }

            @Override
            public void onError(OCRError error) {
                alertText("", error.getMessage());
            }
        });
    }

    private void recIDCard2(String idCardSide, String filePath) {
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

                    tvCertificateDate.setText(result.getExpiryDate().toString());
                }
            }

            @Override
            public void onError(OCRError error) {
                alertText("", error.getMessage());
            }
        });
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_next) {

            if (etCustomerName.getText().toString().equals("") || etCustomerName.getText() == null) {
                MyToast.show(CustomeReport.this, "请填写姓名");
                return;
            }

            if (etCertificateNum.getText().toString().equals("") || etCertificateNum.getText() == null) {
                MyToast.show(CustomeReport.this, "请填写身份证号");
                return;
            }

            if (tvCertificateDate.getText().toString().equals("") || tvCertificateDate.getText() == null) {
                MyToast.show(CustomeReport.this, "请填写身份证有效期");
                return;
            }

            if (etJiashiNum.getText().toString().equals("") || etJiashiNum.getText() == null) {
                MyToast.show(CustomeReport.this, "请填写驾驶证档案编号");
                return;
            }

            if (selectMedia.size() <= 0) {
                MyToast.show(this, "请上传征信相关照片");
                return;
            }


            if (isOk) {

                if (isJiashi) {

                    saveImage();

                } else {

                    confirmJiashi();

                }

            } else {

                confirmCertificate();

            }

        } else if (v.getId() == R.id.iv_back) {
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
        }

    }

    private void saveImage() {
        showProgressDialog();

        final List<String> keys = new ArrayList<>();

        List<List<LocalMedia>> medias = new ArrayList<>();
        medias.add(selectMedia);
        medias.add(selectMedia1);


        piccount = selectMedia.size() + selectMedia1.size();
        LogUtils.d("----------->>>>" + piccount);
        for (int j = 0; j < medias.size(); j++) {
            List<LocalMedia> localMedias = medias.get(j);
            for (int i = 0; i < localMedias.size(); i++) {
                compressPath = localMedias.get(i).getCompressPath();
                final int finalI = i;
                final int finalJ = j;
                String key = UUID.randomUUID() + TimeUtils.getRandomFileName() + ".png";

                if (finalJ == 0) {
                    if (image == null) {
                        image = new JSONArray();
                    }

                    try {
                        image.put(finalI, key);

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                } else if (finalJ == 1) {
                    if (fujia == null) {
                        fujia = new JSONArray();
                    }

                    try {
                        fujia.put(finalI, key);

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
                upLoadImage(key);
            }

        }
    }

    private void confirmJiashi() {
        OkHttpUtils.post()
                .url(Constants.URLS.BASEURL + "Common/jszhc")
                .addParams("salesid", saleID)
                .addParams("name", etCustomerName.getText().toString())
                .addParams("cardNo", etCertificateNum.getText().toString())
                .addParams("archviesNo", etJiashiNum.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("jiashi----------------->>>>>>." + e);
                        MyToast.show(CustomeReport.this, "服务器正忙，请稍后再试。。。");
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("jiashi----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();
                        hud.dismiss();
                        msgBean = gson.fromJson(json, MsgBean.class);
                        if (msgBean.getCode() == 1) {

                            MyToast.show(CustomeReport.this, "驾驶证档案编号认证通过");

                            isJiashi = true;
                            etCustomerName.setEnabled(false);
                            etCustomerName.setTextColor(Color.GRAY);
                            etCertificateNum.setEnabled(false);
                            etCertificateNum.setTextColor(Color.GRAY);
                            etJiashiNum.setEnabled(false);
                            etJiashiNum.setTextColor(Color.GRAY);

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
                            saveImage();
                        } else {
                            hud.dismiss();
                            isJiashi = false;
                            new AlertDialog.Builder(CustomeReport.this)
                                    .setTitle("驾驶证档案编号认证")
                                    .setMessage(msgBean.getMsg())
                                    .setPositiveButton("知道了", null)
                                    .show();
                        }

                    }
                });
    }

    private void confirmCertificate() {

        String userName = etCustomerName.getText().toString().trim();
        String cardNum = etCertificateNum.getText().toString().trim();

        OkHttpUtils.post()
                .url(Constants.URLS.BASEURL + "Appraiser/VerifyIdcardv")
                .addParams("userName", userName)
                .addParams("cardNum", cardNum)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("confirm----------------->>>>>>." + e);
                        MyToast.show(CustomeReport.this, "服务器正忙，请稍后再试。。。");
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("confirm----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();
                        hud.dismiss();
                        MsgBean MsgBean = gson.fromJson(json, MsgBean.class);
                        if (MsgBean.getCode() == 1) {

                            MyToast.show(CustomeReport.this, "身份证实名认证通过");

                            isOk = true;
                            etCustomerName.setEnabled(false);
                            etCustomerName.setTextColor(Color.GRAY);
                            etCertificateNum.setEnabled(false);
                            etCertificateNum.setTextColor(Color.GRAY);

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
                            confirmJiashi();
                        } else {
                            hud.dismiss();
                            isOk = false;
                            new AlertDialog.Builder(CustomeReport.this)
                                    .setTitle("身份证验证")
                                    .setMessage(MsgBean.getMsg())
                                    .setPositiveButton("知道了", null)
                                    .show();
                        }

                    }
                });

    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在上传");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                upLoadCount = 0;
            }
        });
    }

    private void upLoadImage(String key) {
        QiNiuUtlis.upLoad(compressPath, key, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                upLoadCount++;
                LogUtils.i("upLoadCount", "-------------------->" + upLoadCount);
                LogUtils.i("piccount", "-------------------->" + piccount);

                System.out.println(upLoadCount == piccount);
                if (upLoadCount == piccount) {
                    post2();
                }
            }
        }, uploadOptions = new UploadOptions(null, null, false,
                new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {
                        LogUtils.i("percent " + "----------->>>" + percent);

                    }
                }, null));
    }

    private void post2() {

        LogUtils.w("image---->" + image.toString());
        LogUtils.w("fujia---->" + (fujia == null ? "-1" : fujia.toString()));

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Login/createArchives")
                .addParams("userName", etCustomerName.getText().toString())
                .addParams("cardNum", etCertificateNum.getText().toString())
                .addParams("credit", rbOwn.isChecked() ? "0" : "1")
                .addParams("carType", isNew)
                .addParams("overduePic", fujia == null ? "-1" : fujia.toString())
                .addParams("overdue", remark.getText() == null ? "-1" : remark.getText().toString())
                .addParams("carId", carId)
                .addParams("creditPic", image.toString())
                .addParams("salesman_id", saleID)
                .addParams("drivingLicence", etJiashiNum.getText().toString())
                .addParams("termValidity", tvCertificateDate.getText().toString())
                .addParams("token", msgBean.getToken())

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e("customerReport------------->>>>" + e);
                        progressDialog.dismiss();
                        upLoadCount = 0;
                        MyToast.show(CustomeReport.this, "服务器链接失败，请联系管理员");
                    }


                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("customerReport------------->>>>" + response);
                        progressDialog.dismiss();
                        String json = response;
                        Gson gson = new Gson();

                        CustomeReportBean customeReportBean = null;

                        try {
                            customeReportBean = gson.fromJson(json, CustomeReportBean.class);
                        } catch (JsonSyntaxException e) {
                            MyToast.show(CustomeReport.this, "服务器连接失败");
                        }

                        if (customeReportBean.getCode() == 1) {
//                            credit = PreferenceUtils.getInt(CustomeReport.this, "credit");
//                            if (credit == 1) {
//                                startActivity(new Intent(CustomeReport.this, StartHomeVisitActivity.class));
//                                finish();
//                            } else if (credit == 2) {
//                                startActivity(new Intent(CustomeReport.this, CarEvaluateActivity2.class));
//                                finish();
//                            } else {
//                                MyToast.show(CustomeReport.this, "未能成功获取到credit");
//                            }

                            // TODO: 2018/8/29 id price
                            startActivity(new Intent(CustomeReport.this, StartHomeVisitActivity.class));
                            finish();
//                            MyToast.show(CustomeReport.this, "请等待征信审核...");
                            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                            overridePendingTransition(enterAnim6, exitAnim6);


                        } else {
                            upLoadCount = 0;
                            MyToast.show(CustomeReport.this, customeReportBean.getMsg());
                        }


                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.test_imageloader;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        ButterKnife.inject(this);

        hud = KProgressHUD.create(CustomeReport.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍后")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        tv = (TextView) findViewById(R.id.tv_next);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tv.setEnabled(true);
        setTitle("客户建档");
        ButterKnife.inject(this);

        initRecyclerView();

        tv.setOnClickListener(this);
        ivBack.setOnClickListener(this);

//        rbOwn.setSelected(true);
        rbOwn.setChecked(true);

        alertDialog = new AlertDialog.Builder(this);

        initAccessTokenWithAkSk();

        carId = PreferenceUtils.getString(this, "carId");
        isNew = PreferenceUtils.getString(this, "isNew");
        saleID = PreferenceUtils.getString(this, "saleID");

        rgIsnew.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                LogUtils.e(checkedId+"");
                if (checkedId == rbOwn.getId()) {
                    tvBaogao.setText("征信报告");
                    llYuqi.setVisibility(View.VISIBLE);
                    llZhengxin.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    rvYuqi.setVisibility(View.VISIBLE);
                    remark.setVisibility(View.VISIBLE);
                } else if (checkedId == rbOther.getId()) {
                    tvBaogao.setText("银行授权书");
                    llYuqi.setVisibility(View.GONE);
                    llZhengxin.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    rvYuqi.setVisibility(View.GONE);
                    remark.setVisibility(View.GONE);
                }
            }
        });

//        initWebView();
//
//        wvXieyi.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    scroll.requestDisallowInterceptTouchEvent(false);
//                } else {
//                    scroll.requestDisallowInterceptTouchEvent(true);
//                }
//                return false;
//            }
//        });
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
                        recIDCard2(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }
        }

        if (requestCode == REQUEST_CODE_ACCURATE_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recAccurateBasic(this, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
//                            infoPopText(result);
                            Log.e("hshsh", result);
                        }

                        @Override
                        public void onResult2(GeneralResult result) {

                        }
                    });
        }

    }

    private void initRecyclerView() {
        //FullyGridLayoutManager manager = new FullyGridLayoutManager(TestActivity.this, 4, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager ms = new LinearLayoutManager(this);

        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        recyclerView.setLayoutManager(ms);
        adapter1 = new GridImageAdapter(CustomeReport.this, onAddPicClickListener1);
        adapter1.setList(selectMedia);
        adapter1.setSelectMax(maxSelectNum);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(CustomeReport.this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(CustomeReport.this, position, selectMedia);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(CustomeReport.this, selectMedia.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });

        LinearLayoutManager ms1 = new LinearLayoutManager(this);

        ms1.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        rvYuqi.setLayoutManager(ms);
        adapter2 = new GridImageAdapter(CustomeReport.this, onAddPicClickListener2);
        adapter2.setList(selectMedia1);
        adapter2.setSelectMax(maxSelectNum);
        FullyGridLayoutManager manager1 = new FullyGridLayoutManager(CustomeReport.this, 3, GridLayoutManager.VERTICAL, false);
        rvYuqi.setLayoutManager(manager1);
        rvYuqi.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(CustomeReport.this, position, selectMedia1);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia1.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(CustomeReport.this, selectMedia1.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });


    }

    private void initAccessTokenWithAkSk() {

        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {

            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hud.dismiss();
                hasGotToken = true;

                show();

            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                hud.dismiss();
                alertText("AK，SK方式获取token失败", error.getMessage());

            }
        }, getApplicationContext(), "T8tx2AW8ZGGKUWNZitHsXRuc", "C8SR9G6ucTDLge1q0c6Be4HgGlYoQR6y");
    }

    private void show() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initPopWindow();
            }
        });
    }

    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();

                show();
            }
        });
    }

    private void initPopWindow() {
        MyPopWindow takePhotoPopWin = new MyPopWindow(this);
//        设置Popupwindow显示位置（从底部弹出）
        takePhotoPopWin.showAtLocation(findViewById(R.id.scroll), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        takePhotoPopWin.setOutsideTouchable(false);
        //当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
        getWindow().setAttributes(params);

        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        takePhotoPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    @OnClick({R.id.cemera1, R.id.cemera2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cemera1:
                Intent intent = new Intent(CustomeReport.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
//                Intent intent = new Intent(CustomeReport.this, CameraActivity.class);
//                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
//                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
//                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
//                        CameraActivity.CONTENT_TYPE_GENERAL);
//                startActivityForResult(intent, REQUEST_CODE_ACCURATE_BASIC);
                break;
            case R.id.cemera2:
                Intent intent1 = new Intent(CustomeReport.this, CameraActivity.class);
                intent1.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent1.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                startActivityForResult(intent1, REQUEST_CODE_CAMERA);
                break;

        }
    }


}
