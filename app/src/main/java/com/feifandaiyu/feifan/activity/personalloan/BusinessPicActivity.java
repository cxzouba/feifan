package com.feifandaiyu.feifan.activity.personalloan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.GridImageAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CustomeReportBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.DeleteImageUtils;
import com.feifandaiyu.feifan.utils.FullyGridLayoutManager;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.QiNiuUtlis;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.feifandaiyu.feifan.utils.TimeUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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
import okhttp3.Call;

/**
 * Created by davidzhao on 2017/5/9.
 */

public class BusinessPicActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.recycler)
    RecyclerView recyclerView;
    @InjectView(R.id.skip)
    Button skip;
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
    private int upLoadCount = 0;
    private int successCount = 0;
    private int piccount;
    private ImageView iv;
    private ProgressDialog progressDialog;
    private boolean success;


    @Override
    protected int getContentView() {
        return R.layout.upload_business;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tv = (TextView) findViewById(R.id.tv_next);
        iv = (ImageView) findViewById(R.id.iv_back);
        tv.setEnabled(true);
        setTitle("经营地照片");
        ButterKnife.inject(this);
        initRecyclerView();
        tv.setOnClickListener(this);
        iv.setOnClickListener(this);
        skip.setOnClickListener(this);
    }

    private void initRecyclerView() {
        //FullyGridLayoutManager manager = new FullyGridLayoutManager(TestActivity.this, 4, GridLayoutManager.VERTICAL, false);
        LinearLayoutManager ms = new LinearLayoutManager(this);

        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        recyclerView.setLayoutManager(ms);
        adapter1 = new GridImageAdapter(BusinessPicActivity.this, onAddPicClickListener1);
        adapter1.setList(selectMedia);
        adapter1.setSelectMax(maxSelectNum);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(BusinessPicActivity.this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        PictureConfig.getInstance().externalPicturePreview(BusinessPicActivity.this, position, selectMedia);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(BusinessPicActivity.this, selectMedia.get(position - 1).getPath());
                        }
                        break;

                    default:
                }

            }
        });


    }


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

                    themeStyle = ContextCompat.getColor(BusinessPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(BusinessPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(BusinessPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(BusinessPicActivity.this, resultCallback);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(BusinessPicActivity.this, resultCallback);
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
    public void onClick(View v) {
        if (v.getId() == R.id.tv_next) {

            if (selectMedia.size() < 6) {
                MyToast.show(BusinessPicActivity.this, "请上传照片最少6张照片");
                return;
            }

            showProgressDialog();

            final List<String> keys = new ArrayList<>();

            List<List<LocalMedia>> medias = new ArrayList<>();
            medias.add(selectMedia);


            piccount = selectMedia.size();
            LogUtils.d("----------->>>>" + piccount);
            for (int j = 0; j < selectMedia.size(); j++) {
                compressPath = selectMedia.get(j).getCompressPath();


                final int finalJ = j;
                String key = UUID.randomUUID() + TimeUtils.getRandomFileName() + ".png";

                upLoadImage(key);

                if (image == null) {
                    image = new JSONArray();
                }

                try {
                    image.put(finalJ, key);

                } catch (JSONException e) {
                    e.printStackTrace();

                }
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
        } else if (v.getId() == R.id.skip) {
            new AlertDialog.Builder(this)
                    .setTitle("确定跳过经营地照片？")
                    .setMessage("稍后可在修改界面—>家访照片添加")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(BusinessPicActivity.this, TeamManager1Activity.class));
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


    private void upLoadImage(String key) {
        QiNiuUtlis.upLoad(compressPath, key, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    upLoadCount++;
                }
                if (upLoadCount == piccount) {
                    post2();
                }

            }
        });

    }


    private void post2() {
        String userId = PreferenceUtils.getString(BusinessPicActivity.this, "userId");
        LogUtils.d("--------------->>>" + image.toString());
        LogUtils.d("userId--------------->>>" + userId);
        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "UserPic/place")
                .addParams("userId", userId)
                .addParams("place", image.toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e("outdoor------------->>>>" + e);
                        upLoadCount = 0;
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("outdoor------------->>>>" + response);
                        DeleteImageUtils.DeleteImage(selectMedia, BusinessPicActivity.this);
                        DeleteImageUtils.DeleteCutImage(selectMedia, BusinessPicActivity.this);
                        String json = response;
                        Gson gson = new Gson();
                        CustomeReportBean customeReportBean = null;

                        try {
                            customeReportBean = gson.fromJson(json, CustomeReportBean.class);
                        } catch (JsonSyntaxException e) {
                            MyToast.show(BusinessPicActivity.this, "服务器连接失败");
                        }

                        if (customeReportBean.getCode() == 1) {
                            progressDialog.dismiss();
// 我的爱赤裸裸 因为 我的 2  269

                            startActivity(new Intent(BusinessPicActivity.this, TeamManager1Activity.class));
                            finish();

                            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                            overridePendingTransition(enterAnim6, exitAnim6);

                        } else {
                            upLoadCount = 0;
                            progressDialog.dismiss();
                            MyToast.show(BusinessPicActivity.this, customeReportBean.getMsg());
                        }
                        finish();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
