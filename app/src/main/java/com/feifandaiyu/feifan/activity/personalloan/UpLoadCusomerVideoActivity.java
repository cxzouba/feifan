package com.feifandaiyu.feifan.activity.personalloan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.UploadVideoBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.popupWindow.BottomPopupOption;
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
import com.mabeijianxi.smallvideorecord2.DeviceUtils;
import com.mabeijianxi.smallvideorecord2.JianXiCamera;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadOptions;
import com.yalantis.ucrop.entity.LocalMedia;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;


/**
 * Created by hdc on 2017/5/13.
 */

public class UpLoadCusomerVideoActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.vv_video)
    VideoView vvVideo;
    private Button button;
    private ImageView imageView;
    private List<LocalMedia> selectMedia = new ArrayList<LocalMedia>();
    private String path;
    private ImageView iv_back;
    private TextView tv_next;
    private String videoPath;
    private ProgressDialog mProgressDialog;

    private String videoUri;
    private TextView tv_send;
    private TextView tv_cancel;
    private String videoScreenshot;

    private KProgressHUD hud;
    private UploadOptions uploadOptions;
    private int windowHeight;
    private WindowManager wm;
    private int windowWidth;
    private int screenDensity;
    private LinearLayout mCaptureLl;
    private TextView mCaptureIv;
    private String rentType;
    private String carLoan;


    @Override
    protected int getContentView() {
        return R.layout.activity_uploadvideo;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        createEnvironment();
        setTitle("上传视频");
        /*initSmallVideo(this);*/
        button = (Button) findViewById(R.id.bt_video);
        imageView = (ImageView) findViewById(R.id.iv_idvideo);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setEnabled(true);
        tv_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        button.setOnClickListener(this);
        imageView.setOnClickListener(this);

        rentType = PreferenceUtils.getString(UpLoadCusomerVideoActivity.this, "rentType", "haha");

        carLoan = PreferenceUtils.getString(UpLoadCusomerVideoActivity.this, "carLoan", "1");

        vvVideo.setVisibility(View.GONE);
     /*   initSmallVideo();*/
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createEnvironment() {
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        windowWidth = metric.widthPixels;
        windowHeight = metric.heightPixels;
        screenDensity = metric.densityDpi;
//        mImageReader = ImageReader.newInstance(windowWidth, windowHeight, 0x1, 2);
    }

    public static void initSmallVideo() {
        // 设置拍摄视频缓存路径
        File dcim = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                JianXiCamera.setVideoCachePath(dcim + "/mabeijianxi/");
            } else {
                JianXiCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
                        "/sdcard-ext/")
                        + "/mabeijianxi/");
            }
        } else {
            JianXiCamera.setVideoCachePath(dcim + "/mabeijianxi/");
        }
        // 初始化拍摄
        JianXiCamera.initialize(true, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_video:

                final BottomPopupOption bottomPopupOption = new BottomPopupOption(UpLoadCusomerVideoActivity.this);
                bottomPopupOption.setItemText("拍摄视频");
                bottomPopupOption.showPopupWindow();

                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 0) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (!Settings.canDrawOverlays(getApplicationContext())) {
                                    //启动Activity让用户授权
                                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                                    intent.setData(Uri.parse("package:" + getPackageName()));
                                    startActivityForResult(intent, 100);
                                } else {
                                    PictureConfig.getInstance().init(options).startOpenCamera(UpLoadCusomerVideoActivity.this, new ResultCa(imageView));
                                    initText();
                                }
                            } else {
                                PictureConfig.getInstance().init(options).startOpenCamera(UpLoadCusomerVideoActivity.this, new ResultCa(imageView));
                                initText();
                            }

                            bottomPopupOption.dismiss();
                        } else if (position == 1) {

                            MyToast.show(UpLoadCusomerVideoActivity.this, "暂不支持视频从相册选择");
                            finish();
                            bottomPopupOption.dismiss();
                        }
                    }
                });
                break;

            case R.id.iv_idvideo:
                if (videoUri != null) {
                    PictureConfig.getInstance().externalPictureVideo(this, videoUri);
                } else {
                    if (selectMedia.size() <= 0) {
                        MyToast.show(this, "当前无可预览的视频，请点击拍摄");
                    } else {
                        PictureConfig.getInstance().externalPictureVideo(this, selectMedia.get(0).getPath());
                    }
                }
                //
                break;

            case R.id.iv_back:
                new AlertDialog.Builder(this)
                        .setMessage(StringCreateUtils.createString())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动    画资源
                                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                overridePendingTransition(enterAnim0, exitAnim0);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;

            case R.id.tv_next:


                if (selectMedia.size() > 0) {
                    hud = KProgressHUD.create(UpLoadCusomerVideoActivity.this)
                            .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                            .setLabel("视频上传较耗时间，请稍后...")
                            .setCancellable(false)
                            .setDimAmount(0.5f)
                            .setMaxProgress(100)
                            .show();
                    upLoadVideo();
                } else {
                    MyToast.show(UpLoadCusomerVideoActivity.this, "请添加视频");
                }

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PictureConfig.getInstance().init(options).startOpenCamera(UpLoadCusomerVideoActivity.this, new ResultCa(imageView));
                initText();
            } else {
                Toast.makeText(this, "请打开权限以获取视频话术", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initText() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.RGBA_8888);
        params.x = 0;
        params.y = windowHeight / 10;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        mCaptureLl = (LinearLayout) inflater.inflate(R.layout.float_capture, null);
        mCaptureIv = (TextView) mCaptureLl.findViewById(R.id.iv_capture);

        if (rentType.equals("以租代购")) {
            mCaptureIv.setText("        1.请问您是XXX吗？" +
                    "   2.您的身份证号是XXX吗？您在非凡贷遇（杭州）融资租赁有限公司黑龙江分公司自愿申请新车租赁业务，您确定么？" +
                    "   3.该笔租赁业务，租赁车型为XXX ，月租金为XXX，租期为XX期，您确定么？" +
                    "   4.在租赁业务期间如发生二次抵押，变卖，逾期，非法营运，发生重大交通事故，所产生的一切后果，由您本人承担全部的法律责任您约定么？");
        } else {

            if (carLoan.equals("1")) {

                mCaptureIv.setText("        请问您是XXX（先生/女士）吗？  您的身份证号码为XXX吗，您在泰隆银行自愿申请交易贷业务，您确定吗？" +
                        "XXX（先生/女士），您在泰隆银行自愿申请汽车分期付款业务，您确定吗？" +
                        "XXX（先生/女士），您在非凡贷遇（杭州）融资租赁有限公司黑龙江分公司公司分期付款购买一辆XXX品牌，车价为XXX元的汽车，并向泰隆银行申请购车分期业务和接受非凡贷遇（杭州）融资租赁有限公司黑龙江分公司公司的担保，您确定吗？ 该笔分期付款业务贷款额为XXX，月还款为XXX元，共计还款期限为XXX期， 您确定吗？" +
                        "XXX（先生/女士）， 以上面谈是您的真实意愿表达，并愿意承担相应的法律责任， 您确定吗？");

            } else if (carLoan.equals("2")) {

                mCaptureIv.setText("        车抵贷问答视频话术:" +
                        "请问您是XXX先生/女士）吗？  您的身份证号码为XXXXXXXXXXXX，您在泰隆银行自愿申请车抵贷业务，您确定吗？" +
                        "XXX（先生/女士），您在非凡贷遇（杭州）融资租赁有限公司黑龙江分公司公司抵押一辆XXXXX汽车，贷款额是XXXXX万元， 贷款期限XX期， 利息共计XXXX元，您确定吗？" +
                        "XXX（先生/女士）， 以上面谈是您的真实意愿表达，并愿意承担相应的法律责任， 您确定吗？");

            }
        }

        mCaptureIv.setSelected(true);
        mCaptureIv.setFocusable(true);
        wm.addView(mCaptureLl, params);
    }

    /**
     * 车抵贷问答视频话术
     * 请问您是      （先生/女士）吗？  您的身份证号码为             ，您在泰隆银行自愿申请车抵贷业务，您确定吗？
     * （先生/女士），您在非凡贷遇（杭州）融资租赁有限公司黑龙江分公司公司抵押一辆                     汽车，贷款额是    万元， 贷款期限    期， 利息共计       元，您确定吗？
     * （先生/女士）， 以上面谈是您的真实意愿表达，并愿意承担相应的法律责任， 您确定吗？
     * <p>
     * <p>
     * 交易贷问答视频话术
     * 请问您是      （先生/女士）吗？  您的身份证号码为             ，您在泰隆银行自愿申请交易贷业务，您确定吗？
     * （先生/女士），您在泰隆银行自愿申请汽车分期付款业务，您确定吗？
     * （先生/女士），您在非凡贷遇（杭州）融资租赁有限公司黑龙江分公司公司分期付款购买一辆      品牌，车价为    元的汽车，并向泰隆银行申请购车分期业务和接受非凡贷遇（杭州）融资租赁有限公司黑龙江分公司公司的担保，您确定吗？ 该笔分期付款业务贷款额为     ，月还款为      元，共计还款期限为   期， 您确定吗？
     * （先生/女士）， 以上面谈是您的真实意愿表达，并愿意承担相应的法律责任， 您确定吗？
     * 非
     * <p>
     * <p>
     * 以租代购问答视频话术
     * 1.请问您是            ？
     * 2.您的身份证号是    ？您在非凡贷遇（杭州）融资租赁有限公司黑龙江分公司自愿申请新车租赁业务，您确定么？
     * 3.该笔租赁业务，租赁车型为      ，月租金为      ，租期为     期，您确定么？
     * 4.在租赁业务期间如发生二次抵押，变卖，逾期，非法营运，发生重大交通事故，所产生的一切后果，由您本人承担全部的法律责任您约定么？
     */


    private void upLoadVideoUri() {

        String key = UUID.randomUUID() + TimeUtils.getRandomFileName() + "hdc" + ".mp4";
        OkHttpUtils.post()
                .url(Constants.URLS.BASEURL + "Login/video")
                .addParams("userId", PreferenceUtils.getString(UpLoadCusomerVideoActivity.this, "userId"))
                .addParams("video", key)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.d("video-------------->>>>" + e);
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d("video-------------->>>>" + response);
                        hud.dismiss();
                        startActivity(new Intent(UpLoadCusomerVideoActivity.this, StopHomeVisitActivity.class));
                        finish();
                        // 设置过渡动画
                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                        overridePendingTransition(enterAnim6, exitAnim6);
                    }
                });

        QiNiuUtlis.upLoad(videoUri, key, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                LogUtils.i(info.toString());
            }
        });
    }

    private void CpVideo() {
        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                .fullScreen(true)
                .recordTimeMax(6000 * 10 * 3)
                .recordTimeMin(1500)
                .maxFrameRate(30)
                .videoBitrate(4000000)
                .captureThumbnailsTime(1)
                .build();
        MediaRecorderActivity.goSmallVideoRecorder(this, UpLoadCusomerVideoActivity.class.getName(), config);

    }

    private void upLoadVideo() {

        String key = UUID.randomUUID() + TimeUtils.getRandomFileName() + ".mp4";

        if (selectMedia.size() > 0) {
            QiNiuUtlis.upLoad(selectMedia.get(0).getPath(), key, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            LogUtils.i(info.toString());
                        }
                    }
                    , uploadOptions = new UploadOptions(null, null, false, new UpProgressHandler() {
                        @Override
                        public void progress(String key, double percent) {
                            double progerss = percent * 100;
                            int showprogess = (int) progerss;
                            LogUtils.d(percent * 100 + "...." + showprogess);
                            hud.setProgress(showprogess + 1);
                            if (showprogess >= 99) {
                                OkHttpUtils.post()
                                        .url(Constants.URLS.BASEURL + "Login/video")
                                        .addParams("userId", PreferenceUtils.getString(UpLoadCusomerVideoActivity.this, "userId"))
                                        .addParams("video", key)
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onError(Call call, Exception e, int id) {
                                                LogUtils.d("video-------------->>>>" + e);
                                                hud.dismiss();
                                                return;
                                            }

                                            @Override
                                            public void onResponse(String response, int id) {
                                                LogUtils.d("video-------------->>>>" + response);
                                                hud.dismiss();

                                                String json = response;
                                                Gson gson = new Gson();
                                                UploadVideoBean bean = gson.fromJson(json, UploadVideoBean.class);

                                                if (bean.getCode() == 1) {
                                                    startActivity(new Intent(UpLoadCusomerVideoActivity.this, FacePicActivity.class));
                                                    finish();
                                                    // 设置过渡动画
                                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                                    overridePendingTransition(enterAnim6, exitAnim6);
                                                } else {
                                                    MyToast.show(UpLoadCusomerVideoActivity.this, bean.getMsg());
                                                }

                                            }
                                        });
                            }
                        }
                    }, null));
        } else {
            MyToast.show(UpLoadCusomerVideoActivity.this, "请拍摄视频");
        }

    }

    class ResultCa implements PictureConfig.OnSelectResultCallback {
        private ImageView view;

        ResultCa(ImageView view) {
            this.view = view;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {

            if (mCaptureLl != null) {
                if (mCaptureLl.isAttachedToWindow()) {
                    wm.removeView(mCaptureLl);
                }
            }

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
                path = media.getPath();
                Glide.with(UpLoadCusomerVideoActivity.this).load(path).thumbnail(0.5f).into(imageView);


            }
        }
    }

    FunctionOptions options = new FunctionOptions.Builder()
            .setType(FunctionConfig.TYPE_VIDEO) // 图片or视频 FunctionConfig.TYPE_IMAGE  TYPE_VIDEO
            .setCropMode(FunctionConfig.CROP_MODEL_DEFAULT) // 裁剪模式 默认、1:1、3:4、3:2、16:9
            .setCompress(true) //是否压缩
            .setEnablePixelCompress(true) //是否启用像素压缩
            .setEnableQualityCompress(true) //是否启质量压缩
            .setMaxSelectNum(9) // 可选择图片的数量
            .setSelectMode(FunctionConfig.MODE_MULTIPLE) // 单选 or 多选
            .setShowCamera(true) //是否显示拍照选项 这里自动根据type 启动拍照或录视频
            .setEnablePreview(true) // 是否打开预览选项
            .setEnableCrop(false) // 是否打开剪切选项
            .setPreviewVideo(true) // 是否预览视频(播放) mode or 多选有效
            .setRecordVideoDefinition(FunctionConfig.HIGH) // 视频清晰度
            .setRecordVideoSecond(60 * 3) // 视频秒数
            .setGif(false)// 是否显示gif图片，默认不显示
            .setCropW(200) // cropW-->裁剪宽度 值不能小于100  如果值大于图片原始宽高 将返回原图大小
            .setCropH(200) // cropH-->裁剪高度 值不能小于100 如果值大于图片原始宽高 将返回原图大小
            .setMaxB(1024 * 1024 * 3) // 压缩最大值 例如:200kb  就设置202400，202400 / 1024 = 200kb
            .setEnablePixelCompress(true)
            .setGrade(Luban.THIRD_GEAR) // 压缩档次 默认三档
            .setCheckNumMode(false)
            .setCompressQuality(100) // 图片裁剪质量,默认无损
            .setImageSpanCount(4) // 每行个数
            .setSelectMedia(selectMedia) // 已选图片，传入在次进去可选中，不能传入网络图片
            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
            .create();

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }


    @Override
    protected void onResume() {

        if (mCaptureLl != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (mCaptureLl.isAttachedToWindow()) {
                    wm.removeView(mCaptureLl);
                }
            }else {
                wm.removeView(mCaptureLl);
            }
        }

        if (selectMedia.size() > 0) {
            Glide.with(this).load(path).thumbnail(0.5f).into(imageView);

        } else {

            Intent intent = getIntent();
            videoUri = intent.getStringExtra(MediaRecorderActivity.VIDEO_URI);
            System.out.print("------->>>>" + videoUri);
            videoScreenshot = intent.getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT);
            if (videoUri != null) {
                LogUtils.d("______________" + videoUri);
                Glide.with(this).load(videoUri).thumbnail(0.5f).into(imageView);
            }
        }
        super.onResume();
    }


}
