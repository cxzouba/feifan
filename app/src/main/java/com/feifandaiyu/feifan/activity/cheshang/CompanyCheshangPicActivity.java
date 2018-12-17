package com.feifandaiyu.feifan.activity.cheshang;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.UploadCarIamgeBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.QiNiuUtlis;
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

public class CompanyCheshangPicActivity extends BaseActivity {

    @InjectView(R.id.certificate_up)
    ImageView certificateUp;
    @InjectView(R.id.certificate_down)
    ImageView certificateDown;
    @InjectView(R.id.bankcard_up)
    ImageView bankcardUp;
    @InjectView(R.id.bankcard_down)
    ImageView bankcardDown;
    @InjectView(R.id.business_licence)
    ImageView businessLicence;
    @InjectView(R.id.company_report)
    RecyclerView companyReport;
    private TextView tv_next;
    private String cheshangId;
    private List<LocalMedia> selectMedia1 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedia2 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedia3 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedia4 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedia5 = new ArrayList<LocalMedia>();
    private String path1;
    private String path2;
    private String path3;
    private String path4;
    private String path5;
    private List<LocalMedia> selectMediaa = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediab = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediac = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediad = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediae = new ArrayList<LocalMedia>();

    private List<LocalMedia> selectMedia = new ArrayList<>();

    private JSONArray certificate_up;
    private JSONArray certificate_down;
    private JSONArray bankcard_up;
    private JSONArray bankcard_down;
    private JSONArray business_licence;
    private JSONArray company_report;
    private GridImageAdapter adapter;
    private UploadOptions uploadOptions;

    private int piccount;
    private int upLoadCount = 1;
    private ProgressDialog progressDialog;
    private int maxSelectNum = 100;

    private int themeStyle;
    private int previewColor, completeColor, previewBottomBgColor, previewTopBgColor, bottomBgColor, checkedBoxDrawable;
    private int copyMode = FunctionConfig.CROP_MODEL_DEFAULT;
    private boolean enableCrop = false;
    private boolean mode = true;// 启动相册模式
    private int selectType = FunctionConfig.TYPE_IMAGE;

    @Override
    protected int getContentView() {
        return R.layout.activity_company_cheshang_pic;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("车商照片");
        showBack(true);
        showNext(true);

        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setEnabled(true);

        initRecycleView();

        cheshangId = PreferenceUtils.getString(this, "cheshangId");
    }

    private void initRecycleView() {
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        companyReport.setLayoutManager(ms);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectMedia);
        adapter.setSelectMax(maxSelectNum);
        companyReport.setAdapter(adapter);

        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(CompanyCheshangPicActivity.this, position - 1, selectMedia);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(CompanyCheshangPicActivity.this, selectMedia.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });

    }

    @OnClick({R.id.certificate_up, R.id.certificate_down, R.id.bankcard_up, R.id.bankcard_down, R.id.business_licence, R.id.tv_next, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.certificate_up:
                PictureConfig.getInstance().init(options).startOpenCamera(this, new ResultCa1((ImageView) view, selectMediaa, 0));
                break;
            case R.id.certificate_down:
                PictureConfig.getInstance().init(options).startOpenCamera(this, new ResultCa2((ImageView) view, selectMediab, 0));
                break;
            case R.id.bankcard_up:
                PictureConfig.getInstance().init(options).startOpenCamera(this, new ResultCa3((ImageView) view, selectMediac, 0));
                break;
            case R.id.bankcard_down:
                PictureConfig.getInstance().init(options).startOpenCamera(this, new ResultCa4((ImageView) view, selectMediad, 0));
                break;
            case R.id.business_licence:
                PictureConfig.getInstance().init(options).startOpenCamera(this, new ResultCa5((ImageView) view, selectMediae, 0));
                break;
//            case R.id.company_report:
//                finish();
//                break;
            case R.id.tv_next:
                updateToQiniu();
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
        }
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(CompanyCheshangPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(CompanyCheshangPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(CompanyCheshangPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(CompanyCheshangPicActivity.this, resultCallback6);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(CompanyCheshangPicActivity.this, resultCallback6);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia.remove(position);
                    adapter.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };

    private void updateToQiniu() {
        List<List<LocalMedia>> medias = new ArrayList<>();
        piccount = selectMedia1.size() + selectMedia2.size() + selectMedia3.size() +
                selectMedia4.size() + selectMedia5.size() + selectMedia.size();
        medias.add(selectMedia1);
        medias.add(selectMedia2);
        medias.add(selectMedia3);
        medias.add(selectMedia4);
        medias.add(selectMedia5);
        medias.add(selectMedia);

        if (piccount < 6) {
            MyToast.show(CompanyCheshangPicActivity.this, "您有照片未上传");
            return;
        }

        showProgressDialog();

        for (int i = 0; i < medias.size(); i++) {
            List<LocalMedia> localMedias = medias.get(i);
            for (int j = 0; j < localMedias.size(); j++) {
                String compressPath = localMedias.get(j).getCompressPath();
                final int finalI = i;
                final int finalJ = j;
                String key = UUID.randomUUID() + TimeUtils.getRandomFileName() + ".png";

                if (finalI == 0) {
                    if (certificate_up == null) {
                        certificate_up = new JSONArray();
                    }
                    try {

                        certificate_up.put(finalJ, key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else if (finalI == 1) {

                    if (certificate_down == null) {
                        certificate_down = new JSONArray();
                    }
                    try {
                        certificate_down.put(finalJ, key);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (finalI == 2) {
                    if (bankcard_up == null) {
                        bankcard_up = new JSONArray();
                    }
                    try {
                        bankcard_up.put(finalJ, key);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (finalI == 3) {


                    if (bankcard_down == null) {
                        bankcard_down = new JSONArray();
                    }
                    try {
                        bankcard_down.put(finalJ, key);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (finalI == 4) {


                    if (business_licence == null) {
                        business_licence = new JSONArray();
                    }
                    try {
                        business_licence.put(finalJ, key);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (finalI == 5) {


                    if (company_report == null) {
                        company_report = new JSONArray();
                    }
                    try {
                        company_report.put(finalJ, key);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                QiNiuUtlis.upLoad(compressPath, key, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {

                            }
                        }
                        , uploadOptions);


                uploadOptions = new UploadOptions(null, null, false,
                        new UpProgressHandler() {
                            @Override
                            public void progress(String key, final double percent) {

                                LogUtils.i("duang " + "----------->>>" + percent);
                                if (percent == 1.0) {
                                    LogUtils.i("ceshi", "-------------------->" + upLoadCount++);
                                    LogUtils.i("ceshi", "-------------------->" + piccount);
                                    if (upLoadCount == piccount) {

                                        upLoadCount = 1;
                                        LogUtils.d(cheshangId + "," + certificate_up.toString() + "," + certificate_down.toString()
                                                + "," + bankcard_up.toString() + "," + bankcard_down.toString() + "," + business_licence.toString() + "," + company_report.toString());

                                        OkHttpUtils
                                                .post()
                                                //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                                                .url(Constants.URLS.BASEURL + "Dealer/addPic")
                                                .addParams("Id", cheshangId)
                                                .addParams("cardf", certificate_up.toString())
                                                .addParams("cardi", certificate_down.toString())
                                                .addParams("bankcardf", bankcard_up.toString())
                                                .addParams("bankcardi", bankcard_down.toString())
                                                .addParams("licensePic", business_licence.toString())
                                                .addParams("placePic", company_report.toString())
                                                .build()
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onError(Call call, Exception e, int id) {
                                                        progressDialog.dismiss();
                                                        System.out.println("upload____________________>>>>>" + e);
                                                    }

                                                    @Override
                                                    public void onResponse(String response, int id) {
                                                        LogUtils.e("upload----------------->>>>>>." + response);
//                                                        DeleteImageUtils.DeleteImage(selectMedia, UpLoadCarImageActivity.this);

                                                        progressDialog.dismiss();

                                                        String json = response;
                                                        Gson gson = new Gson();
                                                        UploadCarIamgeBean bean = gson.fromJson(json, UploadCarIamgeBean.class);

                                                        if (bean.getCode() == 1) {
//                                                                    if (carLoan.equals("1")) {
//                                                                        startActivity(new Intent(UpLoadCarImageActivity.this, BaseInformationActivity.class));
//                                                                        finish();
//                                                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
//                                                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
//                                                                        overridePendingTransition(enterAnim6, exitAnim6);
//                                                                    } else {
//                                                                        startActivity(new Intent(UpLoadCarImageActivity.this, StartHomeVisitActivity.class));
//                                                                        finish();
//                                                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
//                                                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
//                                                                        overridePendingTransition(enterAnim6, exitAnim6);
//                                                                    }
                                                            MyToast.show(CompanyCheshangPicActivity.this, "车商添加成功");
                                                            finish();
                                                            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                                            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                                            overridePendingTransition(enterAnim6, exitAnim6);
                                                        } else {
                                                            MyToast.show(CompanyCheshangPicActivity.this, bean.getMsg());
                                                        }


                                                    }
                                                });
                                    }

                                }

                            }
                        }, null);


            }
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("正在上传");

        progressDialog.setCancelable(false);

        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.show();
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
            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
            .setCheckNumMode(false)
            .setCompressQuality(100) // 图片裁剪质量,默认无损
            .setImageSpanCount(4) // 每行个数
            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
            .create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    class ResultCa1 implements PictureConfig.OnSelectResultCallback {
        private ImageView view;
        private List<LocalMedia> list;
        private int position;

        ResultCa1(ImageView view, List<LocalMedia> list, int position) {
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
                if (selectMedia1.contains(list.get(list.size() - 2))) {

                    selectMedia1.remove(list.get(list.size() - 2));
                }
            }
            selectMedia1.add(list.get(list.size() - 1));

            LocalMedia media = resultList.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path1 = media.getCutPath();

            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path1 = media.getCompressPath();
                Glide.with(CompanyCheshangPicActivity.this)
                        .load(path1)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(CompanyCheshangPicActivity.this)
                        .load(path1)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }
        }


    }

    class ResultCa2 implements PictureConfig.OnSelectResultCallback {
        private ImageView view;
        private List<LocalMedia> list;
        private int position;

        ResultCa2(ImageView view, List<LocalMedia> list, int position) {
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
                if (selectMedia2.contains(list.get(list.size() - 2))) {

                    selectMedia2.remove(list.get(list.size() - 2));
                }
            }
            selectMedia2.add(list.get(list.size() - 1));

            LocalMedia media = resultList.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path2 = media.getCutPath();

            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path2 = media.getCompressPath();
                Glide.with(CompanyCheshangPicActivity.this)
                        .load(path2)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(CompanyCheshangPicActivity.this)
                        .load(path2)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }
        }


    }

    class ResultCa3 implements PictureConfig.OnSelectResultCallback {
        private ImageView view;
        private List<LocalMedia> list;
        private int position;

        ResultCa3(ImageView view, List<LocalMedia> list, int position) {
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
                if (selectMedia3.contains(list.get(list.size() - 2))) {

                    selectMedia3.remove(list.get(list.size() - 2));
                }
            }
            selectMedia3.add(list.get(list.size() - 1));

            LocalMedia media = resultList.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path3 = media.getCutPath();

            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path3 = media.getCompressPath();
                Glide.with(CompanyCheshangPicActivity.this)
                        .load(path3)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(CompanyCheshangPicActivity.this)
                        .load(path3)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }
        }


    }

    class ResultCa4 implements PictureConfig.OnSelectResultCallback {
        private ImageView view;
        private List<LocalMedia> list;
        private int position;

        ResultCa4(ImageView view, List<LocalMedia> list, int position) {
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
                if (selectMedia4.contains(list.get(list.size() - 2))) {

                    selectMedia4.remove(list.get(list.size() - 2));
                }
            }
            selectMedia4.add(list.get(list.size() - 1));

            LocalMedia media = resultList.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path4 = media.getCutPath();

            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path4 = media.getCompressPath();
                Glide.with(CompanyCheshangPicActivity.this)
                        .load(path4)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(CompanyCheshangPicActivity.this)
                        .load(path4)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }
        }


    }

    class ResultCa5 implements PictureConfig.OnSelectResultCallback {
        private ImageView view;
        private List<LocalMedia> list;
        private int position;

        ResultCa5(ImageView view, List<LocalMedia> list, int position) {
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
                if (selectMedia5.contains(list.get(list.size() - 2))) {

                    selectMedia5.remove(list.get(list.size() - 2));
                }
            }
            selectMedia5.add(list.get(list.size() - 1));

            LocalMedia media = resultList.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path5 = media.getCutPath();

            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path5 = media.getCompressPath();
                Glide.with(CompanyCheshangPicActivity.this)
                        .load(path5)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(CompanyCheshangPicActivity.this)
                        .load(path5)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }
        }


    }

    private PictureConfig.OnSelectResultCallback resultCallback6 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            /*selectMedia4 = resultList;*/
           /* resultList.addAll(selectMedia4);*/
            selectMedia.addAll(resultList);
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
                adapter.setList(selectMedia);
                adapter.notifyDataSetChanged();
            }
        }

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia.add(media);
            if (selectMedia != null) {
                adapter.setList(selectMedia);
                adapter.notifyDataSetChanged();
            }
        }
    };
}
