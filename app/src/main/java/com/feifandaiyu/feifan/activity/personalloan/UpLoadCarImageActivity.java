package com.feifandaiyu.feifan.activity.personalloan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.GridImageAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.UploadCarIamgeBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.DeleteImageUtils;
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
import java.util.Timer;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

public class UpLoadCarImageActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.number_progress_bar)
    NumberProgressBar numberProgressBar;
    @InjectView(R.id.waiguan_zhengqian)
    ImageView waiguanZhengqian;
    @InjectView(R.id.waiguan_zhenghou)
    ImageView waiguanZhenghou;
    @InjectView(R.id.waiguan_zuoqian)
    ImageView waiguanZuoqian;
    @InjectView(R.id.waiguan_youqian)
    ImageView waiguanYouqian;
    @InjectView(R.id.waiguan_zuohou)
    ImageView waiguanZuohou;
    @InjectView(R.id.waiguan_youhou)
    ImageView waiguanYouhou;
    @InjectView(R.id.waiguan_fengdang)
    ImageView waiguanFengdang;
    @InjectView(R.id.zhongkong_licheng)
    ImageView zhongkongLicheng;
    @InjectView(R.id.neibu_mingpai)
    ImageView neibuMingpai;
    @InjectView(R.id.neibu_fadongji)
    ImageView neibuFadongji;
    @InjectView(R.id.neibu_houbeixing)
    ImageView neibuHoubeixing;
    @InjectView(R.id.fujia_dengji_1)
    ImageView fujiaDengji1;
    @InjectView(R.id.fujia_dengji_2)
    ImageView fujiaDengji2;
    @InjectView(R.id.fujia_dengji_3)
    ImageView fujiaDengji3;
    @InjectView(R.id.fujia_dengji_4)
    ImageView fujiaDengji4;
    @InjectView(R.id.fujia_dengji_5)
    ImageView fujiaDengji5;
    @InjectView(R.id.fujia_dengji_6)
    ImageView fujiaDengji6;
    @InjectView(R.id.fujia_dengji_7)
    ImageView fujiaDengji7;
    @InjectView(R.id.fujia_dengji_8)
    ImageView fujiaDengji8;
    @InjectView(R.id.fujia_dengji_9)
    ImageView fujiaDengji9;
    @InjectView(R.id.fujia_xinshi_zheng)
    ImageView fujiaXinshiZheng;
    @InjectView(R.id.fujia_xinshi_fu)
    ImageView fujiaXinshiFu;
    @InjectView(R.id.fujia_xinshi_jian)
    ImageView fujiaXinshiJian;
    @InjectView(R.id.fujia_shangxian)
    ImageView fujiaShangxian;
    @InjectView(R.id.fujia_qiangxian)
    ImageView fujiaQiangxian;
    @InjectView(R.id.company_report)
    RecyclerView companyReport;
    @InjectView(R.id.et_remark)
    EditText etRemark;
    private List<LocalMedia> selectMedia = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedia1 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedia2 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedia3 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedia5 = new ArrayList<LocalMedia>();

    private List<LocalMedia> selectMediaa = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediab = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediac = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediaaA = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediaaB = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediaaC = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediaaD = new ArrayList<LocalMedia>();

    private List<LocalMedia> selectMediad = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedial = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediam = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedian = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedias = new ArrayList<>();
    private List<LocalMedia> selectMediat = new ArrayList<>();
    private List<LocalMedia> selectMediau = new ArrayList<>();
    private List<LocalMedia> selectMediav = new ArrayList<>();
    private List<LocalMedia> selectMediaw = new ArrayList<>();
    private List<LocalMedia> selectMediax = new ArrayList<>();

    private List<LocalMedia> selectMediaa1 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediab2 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediac3 = new ArrayList<LocalMedia>();

    private List<LocalMedia> selectMediad4 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediae5 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediaf6 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediag7 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediah8 = new ArrayList<LocalMedia>();

    private int piccount;
    private String path;
    private int upLoadCount = 1;
    private Timer timer;


    private ImageView iv_back;
    private TextView tv_next;
    private JSONArray appenhance_pic;
    private JSONArray appenhance_pictext;
    private JSONArray control_pic;
    private JSONArray control_pictext;
    private JSONArray chassis_pic;
    private JSONArray chassis_pictext;
    private JSONArray structure_pic;
    private JSONArray structure_pictext;
    private JSONArray other_pic;

    private UploadOptions uploadOptions;
    private String carLoan;
    private Handler mHandler = new Handler();
    private ProgressDialog progressDialog;
    private String carId;
    private RecyclerView theOther;
    private GridImageAdapter adapter;
    private boolean enablePreview = true;
    private boolean isPreviewVideo = true;
    private boolean enableCrop = false;
    private boolean theme = false;
    private boolean selectImageType = false;
    private boolean mode = true;// 启动相册模式
    private boolean clickVideo = false;
    private int themeStyle;
    private int previewColor, completeColor, previewBottomBgColor, previewTopBgColor, bottomBgColor, checkedBoxDrawable;
    private int selectType = FunctionConfig.TYPE_IMAGE;
    private int copyMode = FunctionConfig.CROP_MODEL_DEFAULT;
    private int maxSelectNum = 100;

    @Override
    protected int getContentView() {
        return R.layout.activity_up_load_car_image;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        setTitle("上传车辆照片");
        ButterKnife.inject(this);
        /*theOther = (RecyclerView) findViewById(R.id.rv_theother);*/

        /*initRecyclerView();*/
        carId = PreferenceUtils.getString(UpLoadCarImageActivity.this, "carId");
        carLoan = PreferenceUtils.getString(this, "carLoan");

        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_next = (TextView) findViewById(R.id.tv_next);

        initRecycleView();

        tv_next.setText("提交评估");

        tv_next.setEnabled(true);

        iv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);

        LogUtils.e("carId" + "=" + carId + "===============");


    }

    private void initRecycleView() {
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        companyReport.setLayoutManager(ms);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectMedia5);
        adapter.setSelectMax(maxSelectNum);
        companyReport.setAdapter(adapter);

        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(UpLoadCarImageActivity.this, position - 1, selectMedia5);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia5.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpLoadCarImageActivity.this, selectMedia5.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });

    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    themeStyle = ContextCompat.getColor(UpLoadCarImageActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpLoadCarImageActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpLoadCarImageActivity.this, R.color.tab_color_true);

                    FunctionOptions options = new FunctionOptions.Builder()
                            .setType(FunctionConfig.TYPE_IMAGE) // 图片or视频 FunctionConfig.TYPE_IMAGE  TYPE_VIDEO
                            .setCropMode(copyMode) // 裁剪模式 默认、1:1、3:4、3:2、16:9
                            .setCompress(true) //是否压缩
                            .setEnablePixelCompress(true) //是否启用像素压缩
                            .setEnableQualityCompress(true) //是否启质量压缩
                            .setMaxSelectNum(100) // 可选择图片的数量
                            .setSelectMode(FunctionConfig.MODE_MULTIPLE) // 单选 or 多选
                            .setShowCamera(false) //是否显示拍照选项 这里自动根据type 启动拍照或录视频
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

                    if (false) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(UpLoadCarImageActivity.this, resultCallback);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpLoadCarImageActivity.this, resultCallback);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia5.remove(position);
                    adapter.notifyItemRemoved(position + 1);
                    break;
                default:
            }
        }
    };

    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia5 = resultList;
            Log.i("callBack_result2", selectMedia5.size() + "");
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
                adapter.setList(selectMedia5);
                adapter.notifyDataSetChanged();
            }
        }

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia5.add(media);
            if (selectMedia5 != null) {
                adapter.setList(selectMedia5);
                adapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.tv_next:

                if (selectMedia.size() < 7) {
                    MyToast.show(this, "车身外观有未上传的照片");
                    return;
                }
                if (selectMedia1.size() < 1) {
                    MyToast.show(this, "中控内饰有未上传的照片");
                    return;
                }
                if (selectMedia2.size() < 3) {
                    MyToast.show(this, "内部结构有未上传的图片");
                    return;
                }

                if (selectMedia3.size() < 14) {
                    MyToast.show(this, "附加或者大本有未上传照片");
                    return;
                }


                showProgressDialog();
                appenhance_pictext = new JSONArray();
                try {
                    appenhance_pictext.put(0, "正前方");
                    appenhance_pictext.put(1, "正后方");
                    appenhance_pictext.put(2, "左前45度");
                    appenhance_pictext.put(3, "右前45度");
                    appenhance_pictext.put(4, "左后45度");
                    appenhance_pictext.put(5, "右后45度");
                    appenhance_pictext.put(6, "风挡17位串号");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                control_pictext = new JSONArray();
                try {
                    control_pictext.put(0, "里程");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //  谁办呗 就说买

                chassis_pictext = new JSONArray();
                try {
                    chassis_pictext.put(0, "大本1");
                    chassis_pictext.put(1, "大本2");
                    chassis_pictext.put(2, "大本3");
                    chassis_pictext.put(3, "大本4");
                    chassis_pictext.put(4, "大本5");
                    chassis_pictext.put(5, "大本6");
                    chassis_pictext.put(6, "大本7");
                    chassis_pictext.put(7, "大本8");
                    chassis_pictext.put(8, "大本9");
                    chassis_pictext.put(9, "行驶证正本");
                    chassis_pictext.put(10, "行驶证副本");
                    chassis_pictext.put(11, "机动车检车页");
                    chassis_pictext.put(12, "商险");
                    chassis_pictext.put(13, "强险");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                structure_pictext = new JSONArray();
                try {
                    structure_pictext.put(0, "出厂铭牌");
                    structure_pictext.put(1, "发动机");
                    structure_pictext.put(2, "后备箱");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                LogUtils.i("size", "selectmedia----->>>>" + selectMedia.size() + "   selectmedia1------------->" + selectMedia1.size()
                        + "selectmeida2-------->" + selectMedia2.size() + "selectmedia3--------->" + selectMedia3.size() + "selectmedia5--------->" + selectMedia5.size());


                List<List<LocalMedia>> medias = new ArrayList<>();
                piccount = selectMedia.size() + selectMedia1.size() + selectMedia2.size() +
                        selectMedia3.size() + selectMedia5.size();
                medias.add(selectMedia);
                medias.add(selectMedia1);
                medias.add(selectMedia2);
                medias.add(selectMedia3);
                medias.add(selectMedia5);

                for (int i = 0; i < medias.size(); i++) {
                    List<LocalMedia> localMedias = medias.get(i);
                    for (int j = 0; j < localMedias.size(); j++) {
                        String compressPath = localMedias.get(j).getCompressPath();
                        final int finalI = i;
                        final int finalJ = j;
                        String key = UUID.randomUUID() + TimeUtils.getRandomFileName() + ".png";

                        if (finalI == 0) {
                            if (appenhance_pic == null) {
                                appenhance_pic = new JSONArray();
                            }
                            try {

                                appenhance_pic.put(finalJ, key);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else if (finalI == 1) {

                            if (control_pic == null) {
                                control_pic = new JSONArray();
                            }
                            try {
                                control_pic.put(finalJ, key);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (finalI == 2) {
                            if (structure_pic == null) {
                                structure_pic = new JSONArray();
                            }
                            try {
                                structure_pic.put(finalJ, key);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (finalI == 3) {


                            if (chassis_pic == null) {
                                chassis_pic = new JSONArray();
                            }
                            try {
                                chassis_pic.put(finalJ, key);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (finalI == 4) {


                            if (other_pic == null) {
                                other_pic = new JSONArray();
                            }
                            try {
                                other_pic.put(finalJ, key);

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
                                                LogUtils.d(carId + "," + appenhance_pic.toString() + "," + appenhance_pictext.toString()
                                                        + "," + control_pic.toString() + "," + control_pictext.toString() + "," +
                                                        chassis_pic.toString() + "," + chassis_pictext.toString() + "," + structure_pic.toString()
                                                        + "," + structure_pictext.toString() + chassis_pic.toString() + chassis_pictext.toString());

                                                LogUtils.d(other_pic == null ? "-1" : other_pic.toString());

                                                OkHttpUtils
                                                        .post()
                                                        //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                                                        .url(Constants.URLS.BASEURL + "Login/carPicarr")
                                                        .addParams("carId", carId)
                                                        .addParams("appenhance_pic", appenhance_pic.toString())
                                                        .addParams("appenhance_pictext", appenhance_pictext.toString())
                                                        .addParams("control_pic", control_pic.toString())
                                                        .addParams("control_pictext", control_pictext.toString())
                                                        .addParams("structure_pic", structure_pic.toString())
                                                        .addParams("structure_pictext", structure_pictext.toString())
                                                        .addParams("chassis_pic", chassis_pic.toString())
                                                        .addParams("chassis_pictext", chassis_pictext.toString())
                                                        .addParams("other_pic", other_pic == null ? "-1" : other_pic.toString())
                                                        .addParams("remark", etRemark.getText() == null ? "-1" : etRemark.getText().toString())
                                                        .build()
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onError(Call call, Exception e, int id) {
                                                                progressDialog.dismiss();
                                                                upLoadCount = 0;
                                                                System.out.println("upload____________________>>>>>" + e);
                                                            }

                                                            @Override
                                                            public void onResponse(String response, int id) {
                                                                LogUtils.e("upload----------------->>>>>>." + response);
                                                                DeleteImageUtils.DeleteImage(selectMedia, UpLoadCarImageActivity.this);

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
                                                                    MyToast.show(UpLoadCarImageActivity.this, "车辆评估已提交，请等待!");
                                                                    finish();
                                                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                                                    overridePendingTransition(enterAnim6, exitAnim6);
                                                                } else {
                                                                    upLoadCount =0;
                                                                    MyToast.show(UpLoadCarImageActivity.this, bean.getMsg());
                                                                }


                                                            }
                                                        });
                                            }

                                        }

                                    }
                                }, null);


                    }
                }
                break;
            default:

        }
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

    @OnClick({R.id.waiguan_zhengqian, R.id.waiguan_zhenghou, R.id.waiguan_zuoqian, R.id.waiguan_youqian, R.id.waiguan_zuohou, R.id.waiguan_youhou, R.id.waiguan_fengdang,
            R.id.zhongkong_licheng,
            R.id.neibu_mingpai, R.id.neibu_fadongji, R.id.neibu_houbeixing,
            R.id.fujia_dengji_1, R.id.fujia_dengji_2, R.id.fujia_dengji_3, R.id.fujia_dengji_4, R.id.fujia_dengji_5, R.id.fujia_dengji_6, R.id.fujia_dengji_7, R.id.fujia_dengji_8, R.id.fujia_dengji_9,
            R.id.fujia_xinshi_zheng, R.id.fujia_xinshi_fu, R.id.fujia_xinshi_jian, R.id.fujia_shangxian, R.id.fujia_qiangxian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.waiguan_zhengqian:
                //                PictureConfig.getInstance().init(options).startOpenCamera(this, new ResultCa((ImageView) view, selectMediaa, 0));
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa((ImageView) view, selectMediaa, 0));
                break;
            case R.id.waiguan_zhenghou:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa((ImageView) view, selectMediab, 0));
                break;
            case R.id.waiguan_zuoqian:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa((ImageView) view, selectMediac, 0));
                break;
            case R.id.waiguan_youqian:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa((ImageView) view, selectMediaaA, 0));
                break;
            case R.id.waiguan_zuohou:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa((ImageView) view, selectMediaaB, 0));
                break;
            case R.id.waiguan_youhou:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa((ImageView) view, selectMediaaC, 0));
                break;
            case R.id.waiguan_fengdang:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa((ImageView) view, selectMediaaD, 0));
                break;
            case R.id.zhongkong_licheng:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa1((ImageView) view, selectMediad, 0));
                break;
            case R.id.neibu_mingpai:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa2((ImageView) view, selectMedial, 0));
                break;
            case R.id.neibu_fadongji:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa2((ImageView) view, selectMediam, 0));
                break;
            case R.id.neibu_houbeixing:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa2((ImageView) view, selectMedian, 0));
                break;
            case R.id.fujia_dengji_1:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMedias, 0));
                break;
            case R.id.fujia_dengji_2:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediat, 0));
                break;
            case R.id.fujia_dengji_3:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediau, 0));
                break;
            case R.id.fujia_dengji_4:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediav, 0));
                break;
            case R.id.fujia_dengji_5:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediaw, 0));
                break;
            case R.id.fujia_dengji_6:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediax, 0));
                break;
            case R.id.fujia_dengji_7:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediaa1, 0));
                break;
            case R.id.fujia_dengji_8:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediab2, 0));
                break;
            case R.id.fujia_dengji_9:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediac3, 0));
                break;
            case R.id.fujia_xinshi_zheng:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediad4, 0));
                break;
            case R.id.fujia_xinshi_fu:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediae5, 0));
                break;
            case R.id.fujia_xinshi_jian:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediaf6, 0));
                break;
            case R.id.fujia_shangxian:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediag7, 0));
                break;
            case R.id.fujia_qiangxian:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediah8, 0));
                break;
            default:
        }
    }


    class ResultCa implements PictureConfig.OnSelectResultCallback {
        private ImageView view;
        private List<LocalMedia> list;
        private int position;

        ResultCa(ImageView view, List<LocalMedia> list, int position) {
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
                if (selectMedia.contains(list.get(list.size() - 2))) {

                    selectMedia.remove(list.get(list.size() - 2));
                }
            }
            selectMedia.add(list.get(list.size() - 1));

            LocalMedia media = resultList.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();

            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
                Glide.with(UpLoadCarImageActivity.this)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(UpLoadCarImageActivity.this)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }
        }

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

            if (list.size() >= 2) {
                if (selectMedia1.contains(list.get(list.size() - 2))) {

                    selectMedia1.remove(list.get(list.size() - 2));
                }
            }
            selectMedia1.add(list.get(list.size() - 1));
            LocalMedia media = resultList.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();

            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
                Glide.with(UpLoadCarImageActivity.this)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(UpLoadCarImageActivity.this)
                        .load(path)
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

            if (list.size() >= 2) {
                if (selectMedia2.contains(list.get(list.size() - 2))) {

                    selectMedia2.remove(list.get(list.size() - 2));
                }
            }
            selectMedia2.add(list.get(list.size() - 1));


            LocalMedia media = resultList.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();

            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
                Glide.with(UpLoadCarImageActivity.this)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地

                Glide.with(UpLoadCarImageActivity.this)
                        .load(path)
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
            if (list.size() >= 2) {
                if (selectMedia3.contains(list.get(list.size() - 2))) {

                    selectMedia3.remove(list.get(list.size() - 2));
                }
            }
            selectMedia3.add(list.get(list.size() - 1));
            LocalMedia media = resultList.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();

            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
                Glide.with(UpLoadCarImageActivity.this)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(UpLoadCarImageActivity.this)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }
        }


    } // 企业家访照片，企业经营地照片

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
            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
            .create();

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }
}


