package com.feifandaiyu.feifan.activity.personalloan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.GridImageAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.PersonalUploadImageBean;
import com.feifandaiyu.feifan.config.Constants;
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

/**
 * Created by davidzhao on 2017/5/10.
 * 落户后抵押
 */

public class DiYaAfterLuoHuPicActivity extends BaseActivity {

    @InjectView(R.id.rv_waiguan)
    RecyclerView rvWaiguan;
    @InjectView(R.id.rv_zhongkong)
    RecyclerView rvZhongkong;


    private int themeStyle;
    private int previewColor, completeColor, previewBottomBgColor, previewTopBgColor, bottomBgColor, checkedBoxDrawable;
    private int copyMode = FunctionConfig.CROP_MODEL_DEFAULT;
    private boolean enableCrop = false;

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
            .setGrade(Luban.THIRD_GEAR) // 压缩档次 默认三档
            .setCheckNumMode(false)
            .setCompressQuality(100) // 图片裁剪质量,默认无损
            .setImageSpanCount(4) // 每行个数
            .setSelectMedia(selectMedia) // 已选图片，传入在次进去可选中，不能传入网络图片
            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
            .create();
//    private List<LocalMedia> selectMedia1 = new ArrayList<>();
//    private List<LocalMedia> selectMedia2 = new ArrayList<>();
//    private List<LocalMedia> selectMedia5 = new ArrayList<>();
    private List<LocalMedia> selectMedia3 = new ArrayList<>();
    private String path;
    private boolean mode = false;//启动相册模式
    private FunctionOptions options1;
    private TextView tv_next;
    private GridImageAdapter adapter2;
    private GridImageAdapter adapter3;
    private int piccount;
    private JSONArray cardsPic;
    private JSONArray groupPhoto;
    private UploadOptions uploadOptions;
    private int upLoadCount = 0;
    private ProgressDialog progressDialog;
    private String key;
    private String saleID;
    private String describe;
    private String contractId;
    private String isNew;
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
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener2 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(DiYaAfterLuoHuPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(DiYaAfterLuoHuPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(DiYaAfterLuoHuPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(DiYaAfterLuoHuPicActivity.this, resultCallback2);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(DiYaAfterLuoHuPicActivity.this, resultCallback2);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia3.remove(position);
                    adapter2.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };

    private PictureConfig.OnSelectResultCallback resultCallback3 = new PictureConfig.OnSelectResultCallback() {
        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia.add(media);
            if (selectMedia != null) {
                adapter3.setList(selectMedia3);
                adapter3.notifyDataSetChanged();
            }
        }

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
                adapter3.setList(selectMedia);
                adapter3.notifyDataSetChanged();
            }
        }


    };
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener3 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(DiYaAfterLuoHuPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(DiYaAfterLuoHuPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(DiYaAfterLuoHuPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(DiYaAfterLuoHuPicActivity.this, resultCallback3);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(DiYaAfterLuoHuPicActivity.this, resultCallback3);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia.remove(position);
                    adapter3.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };



    @OnClick({R.id.tv_next, R.id.iv_back})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                if (selectMedia3.size() <= 0) {
                    MyToast.show(this, "合格证照片未上传");
                    return;
                }

//                if (selectMedia5.size() <= 0) {
//                    MyToast.show(this, "内部结构照片未上传");
//                    return;
//                }
//
//                if (selectMedia.size() <= 0) {
//                    MyToast.show(this, "中控内饰照片未上传");
//                    return;
//                }


                piccount = selectMedia3.size() + selectMedia.size();

//                if (piccount < 9) {
//                    MyToast.show(this, "合同照片最少9张");
//                    return;
//                }

                showProgressDialog();

                final List<String> keys = new ArrayList<>();

                List<List<LocalMedia>> medias = new ArrayList<>();

                LogUtils.i("piccount", "-------------------->" + piccount);

                medias.add(selectMedia3);
                medias.add(selectMedia );

                for (int i = 0; i < medias.size(); i++) {
                    List<LocalMedia> localMedias = medias.get(i);
                    for (int j = 0; j < localMedias.size(); j++) {
                        String compressPath = localMedias.get(j).getCompressPath();
                        System.out.println("compressPath=" + compressPath);
                        final int finalI = i;
                        final int finalJ = j;
                        key = UUID.randomUUID() + TimeUtils.getRandomFileName() + ".png";
                        if (finalI == 0) {
                            if (cardsPic == null) {
                                cardsPic = new JSONArray();
                            }
                            try {
                                cardsPic.put(finalJ, key);

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }

                        else if (finalI == 1) {
                            if (groupPhoto == null) {
                                groupPhoto = new JSONArray();
                            }
                            try {
                                groupPhoto.put(finalJ, key);
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
                                                        String carId = PreferenceUtils.getString(DiYaAfterLuoHuPicActivity.this, "carId");

//                                                        System.out.println(cardsPic.toString() + "=====" + groupPhoto.toString() + "====" + contractPic.toString() + "====" + other==null?"-1":other.toString());

                                                        OkHttpUtils
                                                                .post()
                                                                .url(Constants.URLS.BASEURL + "LiftCar/MortgageAfterSettling")
                                                                .addParams("carId", carId)
                                                                .addParams("taxProof", cardsPic.toString())//合格证（新车）必填
                                                                .addParams("afterMortgage", groupPhoto.toString())//交强险保单 非必填
                                                                .addParams("carType", isNew)
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

//                                                                            startActivity(new Intent(HegePicActivity.this, DiyaPicActivity.class));
                                                                            finish();
                                                                            // 设置过渡动画
                                                                            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                                                            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                                                            overridePendingTransition(enterAnim6, exitAnim6);
                                                                        } else {
                                                                            upLoadCount = 0;

                                                                            MyToast.show(DiYaAfterLuoHuPicActivity.this, bean.getMsg());
                                                                        }


                                                                    }
                                                                });
                                                    }
                                                }

                                            }
                                        }, null));


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

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("正在上传");

        progressDialog.show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_luohuhoudiya;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("合格证照片");
        showNext(true);
        ButterKnife.inject(this);
        initRecylerView();

        isNew = PreferenceUtils.getString(this, "isNew");

        tv_next = (TextView) findViewById(R.id.tv_next);

        tv_next.setText("上传");

        tv_next.setEnabled(true);
        /* adapter = new GridImageAdapter(this, onAddPicClickListener);*/
    }

    private void initRecylerView() {
        LinearLayoutManager ms2 = new LinearLayoutManager(this);
        LinearLayoutManager ms3 = new LinearLayoutManager(this);
        LinearLayoutManager ms4 = new LinearLayoutManager(this);

        ms2.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms3.setOrientation(LinearLayoutManager.HORIZONTAL);
        ms4.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvWaiguan.setLayoutManager(ms2);
        rvZhongkong.setLayoutManager(ms3);

        adapter3 = new GridImageAdapter(this, onAddPicClickListener3);
        adapter3.setList(selectMedia);
        adapter3.setSelectMax(maxSelectNum);
        rvZhongkong.setAdapter(adapter3);

        adapter2 = new GridImageAdapter(this, onAddPicClickListener2);
        adapter2.setList(selectMedia3);
        adapter2.setSelectMax(maxSelectNum);
        rvWaiguan.setAdapter(adapter2);

        adapter2.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(DiYaAfterLuoHuPicActivity.this, position - 1, selectMedia3);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia3.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(DiYaAfterLuoHuPicActivity.this, selectMedia3.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(DiYaAfterLuoHuPicActivity.this, position - 1, selectMedia);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(DiYaAfterLuoHuPicActivity.this, selectMedia.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });

    }
}
