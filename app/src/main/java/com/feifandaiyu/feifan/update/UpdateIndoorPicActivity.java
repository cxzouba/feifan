package com.feifandaiyu.feifan.update;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.GridImageAdapter;
import com.feifandaiyu.feifan.adapter.MyDaikouAdapter;
import com.feifandaiyu.feifan.adapter.MyFangchanAdapter;
import com.feifandaiyu.feifan.adapter.MyGongzuoAdapter;
import com.feifandaiyu.feifan.adapter.MyHukouAdapter;
import com.feifandaiyu.feifan.adapter.MyJiashizhengAdapter;
import com.feifandaiyu.feifan.adapter.MyJiehunAdapter;
import com.feifandaiyu.feifan.adapter.MyShineiAdapter;
import com.feifandaiyu.feifan.adapter.MyYinhangAdapter;
import com.feifandaiyu.feifan.adapter.MyZhuceAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.IndoorPicBean;
import com.feifandaiyu.feifan.bean.PersonalUploadImageBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.ImageViewUtils;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.QiNiuUtlis;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.feifandaiyu.feifan.utils.TimeUtils;
import com.google.gson.Gson;
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

public class UpdateIndoorPicActivity extends BaseActivity {
    @InjectView(R.id.custom_jiashizheng)
    RecyclerView customJiashizheng;
    @InjectView(R.id.custom_live)
    RecyclerView customLive;
    @InjectView(R.id.certificate_up)
    ImageView certificateUp;
    @InjectView(R.id.certificate_down)
    ImageView certificateDown;
    @InjectView(R.id.bankcard_up)
    ImageView bankcardUp;
    @InjectView(R.id.bankcard_down)
    ImageView bankcardDown;
    @InjectView(R.id.custom_bank)
    RecyclerView customBank;
    @InjectView(R.id.custom_app_zhece)
    RecyclerView customAppZhece;
    @InjectView(R.id.custom_app_daikou)
    RecyclerView customAppDaikou;
    @InjectView(R.id.custom_jiehun)
    RecyclerView customJiehun;
    @InjectView(R.id.custom_app_gongzuo)
    RecyclerView customGongzuo;
    @InjectView(R.id.rv_custom_live)
    RecyclerView rvCustomLive;
    @InjectView(R.id.rv_custom_jiashizheng)
    RecyclerView rvCustomJiashizheng;
    @InjectView(R.id.rv_hukou)
    RecyclerView rvHukou;
    @InjectView(R.id.rv_custom_fangchan)
    RecyclerView rvCustomFangchan;
    @InjectView(R.id.rv_custom_bank)
    RecyclerView rvCustomBank;
    @InjectView(R.id.rv_custom_app_zhece)
    RecyclerView rvCustomAppZhece;
    @InjectView(R.id.rv_custom_app_daikou)
    RecyclerView rvCustomAppDaikou;
    @InjectView(R.id.rv_custom_jiehun)
    RecyclerView rvCustomJiehun;
    @InjectView(R.id.rv_custom_app_gongzuo)
    RecyclerView rvCustomAppGongzuo;

    private int themeStyle;
    private int previewColor, completeColor, previewBottomBgColor, previewTopBgColor, bottomBgColor, checkedBoxDrawable;
    private int copyMode = FunctionConfig.CROP_MODEL_DEFAULT;
    private boolean enableCrop = false;

    private int maxSelectNum = 100;
    private int selectType = FunctionConfig.TYPE_IMAGE;
    private List<LocalMedia> selectMedia = new ArrayList<>();
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
    private List<LocalMedia> selectMedia12 = new ArrayList<>();
    private List<LocalMedia> selectMedia13 = new ArrayList<>();
    private List<LocalMedia> selectMedia14 = new ArrayList<>();
    private String path1;
    private String path2;
    private String path3;
    private String path4;
    private List<LocalMedia> selectMediaa = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediab = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediac = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediad = new ArrayList<LocalMedia>();
    private JSONArray certificate_up;
    private JSONArray certificate_down;
    private JSONArray bankcard_up;
    private JSONArray bankcard_down;

    private String path;
    private boolean mode = false;// 启动相册模式
    private FunctionOptions options1;
    private TextView tv_next;

    private GridImageAdapter adapter;
    private GridImageAdapter adapter1;
    private GridImageAdapter adapter2;
    private GridImageAdapter adapter3;
    private GridImageAdapter adapter4;
    private GridImageAdapter adapter5;
    private GridImageAdapter adapter6;
    private GridImageAdapter adapter7;
    private GridImageAdapter adapter8;

    private RecyclerView fangchan;
    private int piccount;
    private JSONArray customLivePic;
    private JSONArray fangchanPic;
    private JSONArray customJiashizhengPic;
    private JSONArray hukouPic;

    private JSONArray customBankPic;
    private JSONArray customAppZhecePic;
    private JSONArray customAppDaikouPic;
    private JSONArray customJiehunPic;
    private JSONArray customGongzuoPic;

    private UploadOptions uploadOptions;
    private int upLoadCount = 0;
    private ProgressDialog progressDialog;
    private String key;
    private RecyclerView hukou;
    private String userId;
    private IndoorPicBean indoorPicBean;

    @Override
    protected int getContentView() {
        return R.layout.activity_update_indoor_pic;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("室内材料照片");
        showNext(true);
        ButterKnife.inject(this);
        initRecylerView();

        tv_next = (TextView) findViewById(R.id.tv_next);

        tv_next.setEnabled(true);

        tv_next.setText("保存");

        userId = PreferenceUtils.getString(this, "userId");
        initData();
    }

    private void initData() {

        post()
                .url(Constants.URLS.BASEURL + "UserInfo/userinfoPic")
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("updateIndoorPicShow----------------->>>>>>." + e);
//                        hud.dismiss();
                        MyToast.show(UpdateIndoorPicActivity.this, "联网失败...");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("updateIndoorPicShow----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();

                        LinearLayoutManager ms1 = new LinearLayoutManager(UpdateIndoorPicActivity.this);
                        ms1.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms2 = new LinearLayoutManager(UpdateIndoorPicActivity.this);
                        ms2.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms3 = new LinearLayoutManager(UpdateIndoorPicActivity.this);
                        ms3.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms4 = new LinearLayoutManager(UpdateIndoorPicActivity.this);
                        ms4.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms5 = new LinearLayoutManager(UpdateIndoorPicActivity.this);
                        ms5.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms6 = new LinearLayoutManager(UpdateIndoorPicActivity.this);
                        ms6.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms7 = new LinearLayoutManager(UpdateIndoorPicActivity.this);
                        ms7.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms8 = new LinearLayoutManager(UpdateIndoorPicActivity.this);
                        ms8.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms9 = new LinearLayoutManager(UpdateIndoorPicActivity.this);
                        ms9.setOrientation(LinearLayoutManager.HORIZONTAL);

                        indoorPicBean = gson.fromJson(json, IndoorPicBean.class);
//
//                        hud.dismiss();
//
                        if (indoorPicBean.getCode() == 1) {

                            ImageViewUtils.showNetImage(UpdateIndoorPicActivity.this, indoorPicBean.getShow().getCards().get(0), R.drawable.crabgnormal, certificateUp);
                            ImageViewUtils.showNetImage(UpdateIndoorPicActivity.this, indoorPicBean.getShow().getCardi().get(0), R.drawable.crabgnormal, certificateDown);
                            ImageViewUtils.showNetImage(UpdateIndoorPicActivity.this, indoorPicBean.getShow().getBankcardFront().get(0), R.drawable.crabgnormal, bankcardUp);
                            ImageViewUtils.showNetImage(UpdateIndoorPicActivity.this, indoorPicBean.getShow().getBankcardCounter().get(0), R.drawable.crabgnormal, bankcardDown);

                            rvCustomLive.setLayoutManager(ms1);
                            rvCustomLive.setAdapter(new MyShineiAdapter(indoorPicBean, UpdateIndoorPicActivity.this));

                            rvCustomJiashizheng.setLayoutManager(ms2);
                            rvCustomJiashizheng.setAdapter(new MyJiashizhengAdapter(indoorPicBean, UpdateIndoorPicActivity.this));
//
                            rvHukou.setLayoutManager(ms3);
                            rvHukou.setAdapter(new MyHukouAdapter(indoorPicBean, UpdateIndoorPicActivity.this));
//
                            rvCustomFangchan.setLayoutManager(ms4);
                            rvCustomFangchan.setAdapter(new MyFangchanAdapter(indoorPicBean, UpdateIndoorPicActivity.this));
//
                            rvCustomBank.setLayoutManager(ms5);
                            rvCustomBank.setAdapter(new MyYinhangAdapter(indoorPicBean, UpdateIndoorPicActivity.this));
//
                            rvCustomAppZhece.setLayoutManager(ms6);
                            rvCustomAppZhece.setAdapter(new MyZhuceAdapter(indoorPicBean, UpdateIndoorPicActivity.this));
//
                            rvCustomAppDaikou.setLayoutManager(ms7);
                            rvCustomAppDaikou.setAdapter(new MyDaikouAdapter(indoorPicBean, UpdateIndoorPicActivity.this));
//
                            rvCustomJiehun.setLayoutManager(ms8);
                            rvCustomJiehun.setAdapter(new MyJiehunAdapter(indoorPicBean, UpdateIndoorPicActivity.this));
//
                            rvCustomAppGongzuo.setLayoutManager(ms9);
                            rvCustomAppGongzuo.setAdapter(new MyGongzuoAdapter(indoorPicBean, UpdateIndoorPicActivity.this));


                        } else if (indoorPicBean.getCode() == 0) {
                            new AlertDialog.Builder(UpdateIndoorPicActivity.this)

                                    .setMessage(indoorPicBean.getMsg())
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                            int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                            overridePendingTransition(enterAnim0, exitAnim0);
                                        }
                                    })
                                    .setCancelable(false)
                                    .show();
                        }
                    }
                });

    }

    private void initRecylerView() {
        fangchan = (RecyclerView) findViewById(R.id.custom_fangchan);
        hukou = (RecyclerView) findViewById(R.id.hukou);
        LinearLayoutManager ms = new LinearLayoutManager(this);
        LinearLayoutManager ms1 = new LinearLayoutManager(this);
        LinearLayoutManager ms2 = new LinearLayoutManager(this);
        LinearLayoutManager ms3 = new LinearLayoutManager(this);
        LinearLayoutManager ms4 = new LinearLayoutManager(this);
        LinearLayoutManager ms5 = new LinearLayoutManager(this);
        LinearLayoutManager ms6 = new LinearLayoutManager(this);
        LinearLayoutManager ms7 = new LinearLayoutManager(this);
        LinearLayoutManager ms8 = new LinearLayoutManager(this);

        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        ms1.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms2.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms3.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms4.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms5.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms6.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms7.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms8.setOrientation(LinearLayoutManager.HORIZONTAL);

        customLive.setLayoutManager(ms);
        fangchan.setLayoutManager(ms1);
        customJiashizheng.setLayoutManager(ms2);
        hukou.setLayoutManager(ms3);
        customBank.setLayoutManager(ms4);
        customAppZhece.setLayoutManager(ms5);
        customAppDaikou.setLayoutManager(ms6);
        customJiehun.setLayoutManager(ms7);
        customGongzuo.setLayoutManager(ms8);

        adapter3 = new GridImageAdapter(this, onAddPicClickListener3);
        adapter3.setList(selectMedia);
        adapter3.setSelectMax(maxSelectNum);
        hukou.setAdapter(adapter3);

        adapter2 = new GridImageAdapter(this, onAddPicClickListener2);
        adapter2.setList(selectMedia3);
        adapter2.setSelectMax(maxSelectNum);
        customJiashizheng.setAdapter(adapter2);

        adapter1 = new GridImageAdapter(this, onAddPicClickListener1);
        adapter1.setList(selectMedia5);
        adapter1.setSelectMax(maxSelectNum);
        fangchan.setAdapter(adapter1);

        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectMedia4);
        adapter.setSelectMax(100);
        customLive.setAdapter(adapter);

        adapter4 = new GridImageAdapter(this, onAddPicClickListener4);
        adapter4.setList(selectMedia6);
        adapter4.setSelectMax(100);
        customBank.setAdapter(adapter4);

        adapter5 = new GridImageAdapter(this, onAddPicClickListener5);
        adapter5.setList(selectMedia7);
        adapter5.setSelectMax(100);
        customAppZhece.setAdapter(adapter5);

        adapter6 = new GridImageAdapter(this, onAddPicClickListener6);
        adapter6.setList(selectMedia8);
        adapter6.setSelectMax(100);
        customAppDaikou.setAdapter(adapter6);

        adapter7 = new GridImageAdapter(this, onAddPicClickListener7);
        adapter7.setList(selectMedia9);
        adapter7.setSelectMax(100);
        customJiehun.setAdapter(adapter7);

        adapter8 = new GridImageAdapter(this, onAddPicClickListener8);
        adapter8.setList(selectMedia10);
        adapter8.setSelectMax(100);
        customGongzuo.setAdapter(adapter8);

        adapter1.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(UpdateIndoorPicActivity.this, position - 1, selectMedia5);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia5.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateIndoorPicActivity.this, selectMedia5.get(position - 1).getPath());
                        }
                        break;

                    default:
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateIndoorPicActivity.this, position - 1, selectMedia3);
                        break;

                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia3.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateIndoorPicActivity.this, selectMedia3.get(position - 1).getPath());
                        }
                        break;

                    default:

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
                        PictureConfig.getInstance().externalPicturePreview(UpdateIndoorPicActivity.this, position - 1, selectMedia);
                        break;

                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateIndoorPicActivity.this, selectMedia.get(position - 1).getPath());
                        }
                        break;

                    default:

                }

            }
        });

        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {

                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        PictureConfig.getInstance().externalPicturePreview(UpdateIndoorPicActivity.this, position - 1, selectMedia4);
                        break;

                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia4.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateIndoorPicActivity.this, selectMedia4.get(position - 1).getPath());
                        }
                        break;

                    default:

                }

            }
        });

        adapter4.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {

                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        PictureConfig.getInstance().externalPicturePreview(UpdateIndoorPicActivity.this, position - 1, selectMedia6);
                        break;

                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia6.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateIndoorPicActivity.this, selectMedia6.get(position - 1).getPath());
                        }
                        break;

                    default:

                }

            }
        });

        adapter5.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {

                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        PictureConfig.getInstance().externalPicturePreview(UpdateIndoorPicActivity.this, position - 1, selectMedia7);
                        break;

                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia7.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateIndoorPicActivity.this, selectMedia7.get(position - 1).getPath());
                        }
                        break;

                    default:

                }

            }
        });

        adapter6.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {

                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        PictureConfig.getInstance().externalPicturePreview(UpdateIndoorPicActivity.this, position - 1, selectMedia8);
                        break;

                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia8.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateIndoorPicActivity.this, selectMedia8.get(position - 1).getPath());
                        }
                        break;

                    default:

                }

            }
        });

        adapter7.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {

                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        PictureConfig.getInstance().externalPicturePreview(UpdateIndoorPicActivity.this, position - 1, selectMedia9);
                        break;

                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia9.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateIndoorPicActivity.this, selectMedia9.get(position - 1).getPath());
                        }
                        break;

                    default:

                }

            }
        });

        adapter8.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {

                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        PictureConfig.getInstance().externalPicturePreview(UpdateIndoorPicActivity.this, position - 1, selectMedia10);
                        break;

                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia10.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateIndoorPicActivity.this, selectMedia10.get(position - 1).getPath());
                        }
                        break;

                    default:

                }

            }
        });

    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    themeStyle = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);

                    options1 = new FunctionOptions.Builder()
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

                            .setSelectMedia(selectMedia4) // 已选图片，传入在次进去可选中，不能传入网络图片
                            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                            .setThemeStyle(themeStyle) // 设置主题样式
                            .create();
                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options1).startOpenCamera(UpdateIndoorPicActivity.this, resultCallback);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options1).openPhoto(UpdateIndoorPicActivity.this, resultCallback);
                    }
                    break;

                case 1:
                    // 删除图片
                    selectMedia4.remove(position);
                    adapter.notifyItemRemoved(position + 1);
                    break;

                default:

            }
        }
    };


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener1 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.blue);
                    checkedBoxDrawable = R.drawable.select_cb;
                    previewColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateIndoorPicActivity.this, resultCallback1);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateIndoorPicActivity.this, resultCallback1);
                    }
                    break;

                case 1:
                    // 删除图片
                    selectMedia5.remove(position);
                    adapter1.notifyItemRemoved(position + 1);
                    break;

                default:
            }
        }
    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener2 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:

                    themeStyle = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateIndoorPicActivity.this, resultCallback2);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateIndoorPicActivity.this, resultCallback2);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia3.remove(position);
                    adapter2.notifyItemRemoved(position + 1);
                    break;

                default:
            }
        }
    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener3 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:

                    themeStyle = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateIndoorPicActivity.this, resultCallback3);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateIndoorPicActivity.this, resultCallback3);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia.remove(position);
                    adapter3.notifyItemRemoved(position + 1);
                    break;

                default:
            }
        }
    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener6 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateIndoorPicActivity.this, resultCallback6);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateIndoorPicActivity.this, resultCallback6);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia8.remove(position);
                    adapter6.notifyItemRemoved(position + 1);
                    break;

                default:
            }
        }
    };
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener7 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateIndoorPicActivity.this, resultCallback7);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateIndoorPicActivity.this, resultCallback7);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia9.remove(position);
                    adapter7.notifyItemRemoved(position + 1);
                    break;

                default:
            }
        }
    };


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener8 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateIndoorPicActivity.this, resultCallback8);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateIndoorPicActivity.this, resultCallback8);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia10.remove(position);
                    adapter8.notifyItemRemoved(position + 1);
                    break;

                default:
            }
        }
    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener5 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateIndoorPicActivity.this, resultCallback5);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateIndoorPicActivity.this, resultCallback5);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia7.remove(position);
                    adapter5.notifyItemRemoved(position + 1);
                    break;

                default:
            }
        }
    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener4 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateIndoorPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateIndoorPicActivity.this, resultCallback4);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateIndoorPicActivity.this, resultCallback4);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia6.remove(position);
                    adapter4.notifyItemRemoved(position + 1);
                    break;

                default:
            }
        }
    };


    @OnClick({R.id.tv_next, R.id.iv_back, R.id.certificate_up, R.id.certificate_down, R.id.bankcard_up, R.id.bankcard_down})
    public void onViewClicked(final View view) {
        switch (view.getId()) {

            case R.id.certificate_up:
                PictureConfig.getInstance().init(options).startOpenCamera(this, new ResultCa9((ImageView) view, selectMediaa, 0));
                break;
            case R.id.certificate_down:
                PictureConfig.getInstance().init(options).startOpenCamera(this, new ResultCa10((ImageView) view, selectMediab, 0));
                break;
            case R.id.bankcard_up:
                PictureConfig.getInstance().init(options).startOpenCamera(this, new ResultCa11((ImageView) view, selectMediac, 0));
                break;
            case R.id.bankcard_down:
                PictureConfig.getInstance().init(options).startOpenCamera(this, new ResultCa12((ImageView) view, selectMediad, 0));
                break;
            case R.id.tv_next:

                showProgressDialog();

                final List<String> keys = new ArrayList<>();

                List<List<LocalMedia>> medias = new ArrayList<>();
                piccount = selectMedia.size() + selectMedia3.size() + selectMedia4.size() + selectMedia5.size()
                        + selectMedia6.size() + selectMedia7.size() + selectMedia8.size() + selectMedia9.size() + selectMedia10.size()
                        + selectMedia11.size() + selectMedia12.size() + selectMedia13.size() + selectMedia14.size();

                LogUtils.i("piccount", "-------------------->" + piccount);

                if (piccount == 0) {
                    post2();
                } else {

                    medias.add(selectMedia3);
                    medias.add(selectMedia4);
                    medias.add(selectMedia5);
                    medias.add(selectMedia);

                    medias.add(selectMedia6);
                    medias.add(selectMedia7);
                    medias.add(selectMedia8);
                    medias.add(selectMedia9);
                    medias.add(selectMedia10);

                    medias.add(selectMedia11);
                    medias.add(selectMedia12);
                    medias.add(selectMedia13);
                    medias.add(selectMedia14);

                    for (int i = 0; i < medias.size(); i++) {
                        List<LocalMedia> localMedias = medias.get(i);
                        for (int j = 0; j < localMedias.size(); j++) {
                            String compressPath = localMedias.get(j).getCompressPath();
                            System.out.println("compressPath=" + compressPath);
                            final int finalI = i;
                            final int finalJ = j;
                            key = UUID.randomUUID() + TimeUtils.getRandomFileName() + ".png";
                            if (finalI == 0) {
                                if (customJiashizhengPic == null) {
                                    customJiashizhengPic = new JSONArray();
                                }
                                try {
                                    customJiashizhengPic.put(finalJ, key);
                                    indoorPicBean.getList().getDrivers().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            } else if (finalI == 1) {
                                if (customLivePic == null) {
                                    customLivePic = new JSONArray();
                                }

                                try {
                                    customLivePic.put(finalJ, key);
                                    indoorPicBean.getList().getIndoor().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            } else if (finalI == 2) {
                                if (fangchanPic == null) {
                                    fangchanPic = new JSONArray();
                                }

                                try {
                                    fangchanPic.put(finalJ, key);
                                    indoorPicBean.getList().getHousePic().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            } else if (finalI == 3) {
                                if (hukouPic == null) {
                                    hukouPic = new JSONArray();
                                }
                                try {

                                    hukouPic.put(finalJ, key);
                                    indoorPicBean.getList().getHousehold().add(key);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 4) {
                                if (customBankPic == null) {
                                    customBankPic = new JSONArray();
                                }
                                try {
                                    customBankPic.put(finalJ, key);
                                    indoorPicBean.getList().getBankFlow().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 5) {
                                if (customAppZhecePic == null) {
                                    customAppZhecePic = new JSONArray();
                                }
                                try {
                                    customAppZhecePic.put(finalJ, key);
                                    indoorPicBean.getList().getScreenshot().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 6) {
                                if (customAppDaikouPic == null) {
                                    customAppDaikouPic = new JSONArray();
                                }
                                try {
                                    customAppDaikouPic.put(finalJ, key);
                                    indoorPicBean.getList().getAppAgreement().add(key);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 7) {
                                if (customJiehunPic == null) {
                                    customJiehunPic = new JSONArray();
                                }
                                try {
                                    customJiehunPic.put(finalJ, key);
                                    indoorPicBean.getList().getMarriage().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 8) {
                                if (customGongzuoPic == null) {
                                    customGongzuoPic = new JSONArray();
                                }
                                try {
                                    customGongzuoPic.put(finalJ, key);
                                    indoorPicBean.getList().getWorkProof().add(key);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 9) {
                                if (certificate_up == null) {
                                    certificate_up = new JSONArray();

                                }
                                try {
                                    certificate_up.put(finalJ, key);
                                    indoorPicBean.getList().getCards().remove(0);
                                    indoorPicBean.getList().getCards().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            } else if (finalI == 10) {

                                if (certificate_down == null) {
                                    certificate_down = new JSONArray();
                                }
                                try {
                                    certificate_down.put(finalJ, key);
                                    indoorPicBean.getList().getCardi().remove(0);
                                    indoorPicBean.getList().getCardi().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 11) {
                                if (bankcard_up == null) {
                                    bankcard_up = new JSONArray();
                                }
                                try {
                                    bankcard_up.put(finalJ, key);
                                    indoorPicBean.getList().getBankcardFront().remove(0);

                                    indoorPicBean.getList().getBankcardFront().add(key);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 12) {


                                if (bankcard_down == null) {
                                    bankcard_down = new JSONArray();
                                }
                                try {
                                    bankcard_down.put(finalJ, key);
                                    indoorPicBean.getList().getBankcardCounter().remove(0);

                                    indoorPicBean.getList().getBankcardCounter().add(key);

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
                                                            post2();
                                                        }
                                                    }

                                                }
                                            }, null));


                        }
                    }
                }


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

            default:
        }
    }

    private void post2() {
        String userId = PreferenceUtils.getString(UpdateIndoorPicActivity.this, "userId");

        Gson gson = new Gson();
        String CustomLivePic = gson.toJson(indoorPicBean.getList().getIndoor());
        String FangchanPic = gson.toJson(indoorPicBean.getList().getHousePic());
        String Jiashi = gson.toJson(indoorPicBean.getList().getDrivers());
        String Hukou = gson.toJson(indoorPicBean.getList().getHousehold());
        String Jiehun = gson.toJson(indoorPicBean.getList().getMarriage());
        String Yinhang = gson.toJson(indoorPicBean.getList().getBankFlow());
        String Jietu = gson.toJson(indoorPicBean.getList().getScreenshot());
        String Daikou = gson.toJson(indoorPicBean.getList().getAppAgreement());
        String Gongzuo = gson.toJson(indoorPicBean.getList().getWorkProof());
        String Cards = gson.toJson(indoorPicBean.getList().getCards());
        String Cardi = gson.toJson(indoorPicBean.getList().getCardi());
        String BankcardFront = gson.toJson(indoorPicBean.getList().getBankcardFront());
        String getBankcardCounter = gson.toJson(indoorPicBean.getList().getBankcardCounter());

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Login/addPic")
                .addParams("userId", userId)
                .addParams("housePic", FangchanPic)
                .addParams("indoor", CustomLivePic)
                .addParams("drivers", Jiashi)
                .addParams("household", Hukou == null ? "-1" : Hukou)
                .addParams("cards", Cards)
                .addParams("cardi", Cardi)
                .addParams("marriage", Jiehun == null ? "-1" : Jiehun)
                .addParams("bankFlow", Yinhang)
                .addParams("bankcardFront", BankcardFront)
                .addParams("bankcardCounter", getBankcardCounter)
                .addParams("screenshot", Jietu)
                .addParams("appAgreement", Daikou)
                .addParams("workProof", Gongzuo == null ? "-1" : Gongzuo)
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
//                            startActivity(new Intent(UpdateIndoorPicActivity.this, ContractPicActivity.class));
                            finish();
                            // 设置过渡动画
//                            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
//                            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
//                            overridePendingTransition(enterAnim6, exitAnim6);
                            MyToast.show(UpdateIndoorPicActivity.this, "室内材料照片上传成功");

                        } else {
                            upLoadCount = 0;
                            MyToast.show(UpdateIndoorPicActivity.this, bean.getMsg());
                        }


                    }
                });
    }

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
            .setGrade(Luban.THIRD_GEAR) // 压缩档次 默认三档
            .setCheckNumMode(false)
            .setCompressQuality(100) // 图片裁剪质量,默认无损
            .setImageSpanCount(4) // 每行个数
            .setSelectMedia(selectMedia) // 已选图片，传入在次进去可选中，不能传入网络图片
            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
            .create();


    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            /*selectMedia4 = resultList;*/
           /* resultList.addAll(selectMedia4);*/
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
                adapter.setList(selectMedia4);
                adapter.notifyDataSetChanged();
            }
        }

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia.add(media);
            if (selectMedia4 != null) {
                adapter.setList(selectMedia4);
                adapter.notifyDataSetChanged();
            }
        }
    };

    private PictureConfig.OnSelectResultCallback resultCallback2 = new PictureConfig.OnSelectResultCallback() {
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
                adapter2.setList(selectMedia3);
                adapter2.notifyDataSetChanged();
            }
        }

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia.add(media);
            if (selectMedia3 != null) {
                adapter2.setList(selectMedia3);
                adapter2.notifyDataSetChanged();
            }
        }
    };

    private PictureConfig.OnSelectResultCallback resultCallback3 = new PictureConfig.OnSelectResultCallback() {
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
            if (selectMedia3 != null) {
                adapter3.setList(selectMedia);
                adapter3.notifyDataSetChanged();
            }
        }

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia.add(media);
            if (selectMedia3 != null) {
                adapter2.setList(selectMedia3);
                adapter2.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    private PictureConfig.OnSelectResultCallback resultCallback1 = new PictureConfig.OnSelectResultCallback() {
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
                adapter1.setList(selectMedia5);
                adapter1.notifyDataSetChanged();
            }
        }

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia5.add(media);
            if (selectMedia5 != null) {
                adapter1.setList(selectMedia5);
                adapter1.notifyDataSetChanged();
            }
        }
    };

    private PictureConfig.OnSelectResultCallback resultCallback4 = new PictureConfig.OnSelectResultCallback() {
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
                adapter4.setList(selectMedia6);
                adapter4.notifyDataSetChanged();
            }
        }

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia6.add(media);
            if (selectMedia6 != null) {
                adapter4.setList(selectMedia6);
                adapter4.notifyDataSetChanged();
            }
        }
    };

    private PictureConfig.OnSelectResultCallback resultCallback5 = new PictureConfig.OnSelectResultCallback() {
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
                adapter5.setList(selectMedia7);
                adapter5.notifyDataSetChanged();
            }
        }

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia7.add(media);
            if (selectMedia7 != null) {
                adapter5.setList(selectMedia7);
                adapter5.notifyDataSetChanged();
            }
        }
    };

    private PictureConfig.OnSelectResultCallback resultCallback6 = new PictureConfig.OnSelectResultCallback() {
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
                adapter6.setList(selectMedia8);
                adapter6.notifyDataSetChanged();
            }
        }

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia8.add(media);
            if (selectMedia8 != null) {
                adapter6.setList(selectMedia8);
                adapter6.notifyDataSetChanged();
            }
        }
    };

    private PictureConfig.OnSelectResultCallback resultCallback7 = new PictureConfig.OnSelectResultCallback() {
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
                adapter7.setList(selectMedia9);
                adapter7.notifyDataSetChanged();
            }
        }

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia9.add(media);
            if (selectMedia9 != null) {
                adapter7.setList(selectMedia9);
                adapter7.notifyDataSetChanged();
            }
        }
    };

    private PictureConfig.OnSelectResultCallback resultCallback8 = new PictureConfig.OnSelectResultCallback() {
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
                adapter8.setList(selectMedia10);
                adapter8.notifyDataSetChanged();
            }
        }

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia10.add(media);
            if (selectMedia10 != null) {
                adapter8.setList(selectMedia10);
                adapter8.notifyDataSetChanged();
            }
        }
    };

    class ResultCa9 implements PictureConfig.OnSelectResultCallback {
        private ImageView view;
        private List<LocalMedia> list;
        private int position;

        ResultCa9(ImageView view, List<LocalMedia> list, int position) {
            this.list = list;
            this.position = position;
            this.view = view;
        }


        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {

            if (list == null) {
                list = resultList;
            } else {
                list.addAll(resultList);
            }
            LogUtils.d("UpLoadCarImageActivity------>>>", list.size() + "张");
            if (list.size() >= 2) {
                if (selectMedia11.contains(list.get(list.size() - 2))) {

                    selectMedia11.remove(list.get(list.size() - 2));
                }
            }
            selectMedia11.add(list.get(list.size() - 1));

            LocalMedia media = resultList.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path1 = media.getCutPath();

            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path1 = media.getCompressPath();
                Glide.with(UpdateIndoorPicActivity.this)
                        .load(path1)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(UpdateIndoorPicActivity.this)
                        .load(path1)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }
        }


    }

    class ResultCa10 implements PictureConfig.OnSelectResultCallback {
        private ImageView view;
        private List<LocalMedia> list;
        private int position;

        ResultCa10(ImageView view, List<LocalMedia> list, int position) {
            this.list = list;
            this.position = position;
            this.view = view;
        }


        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {

            if (list == null) {
                list = resultList;
            } else {
                list.addAll(resultList);
            }
            LogUtils.d("UpLoadCarImageActivity------>>>", list.size() + "张");
            if (list.size() >= 2) {
                if (selectMedia12.contains(list.get(list.size() - 2))) {

                    selectMedia12.remove(list.get(list.size() - 2));
                }
            }
            selectMedia12.add(list.get(list.size() - 1));

            LocalMedia media = resultList.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path2 = media.getCutPath();

            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path2 = media.getCompressPath();
                Glide.with(UpdateIndoorPicActivity.this)
                        .load(path2)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(UpdateIndoorPicActivity.this)
                        .load(path2)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }
        }


    }

    class ResultCa11 implements PictureConfig.OnSelectResultCallback {
        private ImageView view;
        private List<LocalMedia> list;
        private int position;

        ResultCa11(ImageView view, List<LocalMedia> list, int position) {
            this.list = list;
            this.position = position;
            this.view = view;
        }


        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {

            if (list == null) {
                list = resultList;
            } else {
                list.addAll(resultList);
            }
            LogUtils.d("UpLoadCarImageActivity------>>>", list.size() + "张");
            if (list.size() >= 2) {
                if (selectMedia13.contains(list.get(list.size() - 2))) {

                    selectMedia13.remove(list.get(list.size() - 2));
                }
            }
            selectMedia13.add(list.get(list.size() - 1));

            LocalMedia media = resultList.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path3 = media.getCutPath();

            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path3 = media.getCompressPath();
                Glide.with(UpdateIndoorPicActivity.this)
                        .load(path3)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(UpdateIndoorPicActivity.this)
                        .load(path3)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }
        }


    }

    class ResultCa12 implements PictureConfig.OnSelectResultCallback {
        private ImageView view;
        private List<LocalMedia> list;
        private int position;

        ResultCa12(ImageView view, List<LocalMedia> list, int position) {
            this.list = list;
            this.position = position;
            this.view = view;
        }


        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {

            if (list == null) {
                list = resultList;
            } else {
                list.addAll(resultList);
            }
            LogUtils.d("UpLoadCarImageActivity------>>>", list.size() + "张");
            if (list.size() >= 2) {
                if (selectMedia14.contains(list.get(list.size() - 2))) {

                    selectMedia14.remove(list.get(list.size() - 2));
                }
            }
            selectMedia14.add(list.get(list.size() - 1));

            LocalMedia media = resultList.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path4 = media.getCutPath();

            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path4 = media.getCompressPath();
                Glide.with(UpdateIndoorPicActivity.this)
                        .load(path4)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(UpdateIndoorPicActivity.this)
                        .load(path4)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

}




