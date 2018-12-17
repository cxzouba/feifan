package com.feifandaiyu.feifan.activity.companyloan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.personalloan.StopHomeVisitActivity;
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
 * Created by davidzhao on 2017/5/13.
 */

public class UpLoadCompanyVideoActivity extends BaseActivity implements View.OnClickListener {
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

    @Override
    protected int getContentView() {
        return R.layout.activity_uploadvideo;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

        setTitle("上传视频");

        button = (Button) findViewById(R.id.bt_video);
        imageView = (ImageView) findViewById(R.id.iv_idvideo);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setEnabled(true);
        tv_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        button.setOnClickListener(this);
        imageView.setOnClickListener(this);

        vvVideo.setVisibility(View.GONE);

     /*   initSmallVideo();*/
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


                final BottomPopupOption bottomPopupOption = new BottomPopupOption(UpLoadCompanyVideoActivity.this);
                bottomPopupOption.setItemText("拍摄视频");
                bottomPopupOption.showPopupWindow();

                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 0) {
                            PictureConfig.getInstance().init(options).startOpenCamera(UpLoadCompanyVideoActivity.this, new ResultCa(imageView));
                            bottomPopupOption.dismiss();
                        } else if (position == 1) {

                            MyToast.show(UpLoadCompanyVideoActivity.this, "暂不支持视频从相册选择");
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
                    hud = KProgressHUD.create(UpLoadCompanyVideoActivity.this)
                            .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                            .setLabel("视频上传较耗时间，请稍后...")
                            .setCancellable(false)
                            .setDimAmount(0.5f)
                            .setMaxProgress(100)
                            .show();
                    upLoadVideo();
                } else {
                    MyToast.show(UpLoadCompanyVideoActivity.this, "请添加视频");
                }

                break;

        }
    }

    private void upLoadVideoUri() {

        String key = UUID.randomUUID() + TimeUtils.getRandomFileName() + ".mp4";
        OkHttpUtils.post()
                .url(Constants.URLS.BASEURL + "Login/video")
                .addParams("userId", PreferenceUtils.getString(UpLoadCompanyVideoActivity.this, "userId"))
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
                        startActivity(new Intent(UpLoadCompanyVideoActivity.this, StopHomeVisitActivity.class));
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
        MediaRecorderActivity.goSmallVideoRecorder(this, UpLoadCompanyVideoActivity.class.getName(), config);

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
                                        .url(Constants.URLS.BASEURL + "Login/companyVideo")
                                        .addParams("companyId", PreferenceUtils.getString(UpLoadCompanyVideoActivity.this, "userId"))
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
                                                    startActivity(new Intent(UpLoadCompanyVideoActivity.this, TeamManager2Activity.class));
                                                    finish();
                                                    // 设置过渡动画
                                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                                    overridePendingTransition(enterAnim6, exitAnim6);
                                                } else {
                                                    MyToast.show(UpLoadCompanyVideoActivity.this, bean.getMsg());
                                                }

                                            }
                                        });
                            }
                        }
                    }, null));
        } else {
            MyToast.show(UpLoadCompanyVideoActivity.this, "请拍摄视频");
        }

    }


    class ResultCa implements PictureConfig.OnSelectResultCallback {
        private ImageView view;

        ResultCa(ImageView view) {
            this.view = view;
        }

        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
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
                Glide.with(UpLoadCompanyVideoActivity.this).load(path).thumbnail(0.5f).into(imageView);


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
            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
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
