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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.GridImageAdapter;
import com.feifandaiyu.feifan.adapter.MyCarPicAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CarDetailBean;
import com.feifandaiyu.feifan.bean.CarPicBean;
import com.feifandaiyu.feifan.bean.DialogBankBean;
import com.feifandaiyu.feifan.bean.UploadCarIamgeBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.DeleteImageUtils;
import com.feifandaiyu.feifan.utils.ImageViewUtils;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.QiNiuUtlis;
import com.feifandaiyu.feifan.utils.TimeUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
 * @author houdaichang
 */
public class UpdateCarPicActivity extends BaseActivity implements View.OnClickListener {


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
    @InjectView(R.id.rv_other_show)
    RecyclerView rvOtherShow;
    private ImageView iv_back;
    private CarDetailBean bean;
    private String userId;
    private List<LocalMedia> selectMedia = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedia1 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedia2 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedia3 = new ArrayList<LocalMedia>();

    private List<LocalMedia> selectMediaa = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediab = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediac = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediaaA = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediaaB = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediaaC = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediaaD = new ArrayList<LocalMedia>();

    private List<LocalMedia> selectMediad = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediae = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediaf = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediag = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediah = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediai = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediaj = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediak = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedial = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediam = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMedian = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediao = new ArrayList<>();
    private List<LocalMedia> selectMediap = new ArrayList<>();
    private List<LocalMedia> selectMediaq = new ArrayList<>();
    private List<LocalMedia> selectMediar = new ArrayList<>();
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
    private List<LocalMedia> selectMediai9 = new ArrayList<LocalMedia>();

    private List<LocalMedia> selectMediax1 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediax2 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediax3 = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectMediax4 = new ArrayList<LocalMedia>();

    private String path;
    private List<String> appenhance_pic;
    private List<String> chassis_pic;
    private List<String> control_pic;
    private List<String> structure_pic;
    private String carId;
    private JSONArray appenhance_pictext;
    private JSONArray control_pictext;
    private JSONArray chassis_pictext;
    private JSONArray structure_pictext;
    private JSONArray other2;
    private String appenhance;
    private String chassis;
    private String control;
    private String structure;
    private String house;
    private TextView tv_next;
    private KProgressHUD hud;
    private GridImageAdapter adapter;
    private ProgressDialog progressDialog;

    private List<LocalMedia> selectMedia4 = new ArrayList<>();
    private int themeStyle;
    private int previewColor, completeColor, previewBottomBgColor, previewTopBgColor, bottomBgColor, checkedBoxDrawable;
    private int copyMode = FunctionConfig.CROP_MODEL_DEFAULT;
    private boolean enableCrop = false;

    private int maxSelectNum = 100;
    private int selectType = FunctionConfig.TYPE_IMAGE;
    private boolean mode = false;// 启动相册模式
    private FunctionOptions options1;
    private CarPicBean carPicBean;
    private String key;
    private UploadOptions uploadOptions;
    private int upLoadCount = 0;


    @Override
    protected int getContentView() {
        return R.layout.activity_car_pic;

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("车辆照片");
        showNext(true);
        ButterKnife.inject(this);

        hud = KProgressHUD.create(UpdateCarPicActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍候")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        tv_next = (TextView) findViewById(R.id.tv_next);

        tv_next.setText("保存");

        tv_next.setEnabled(true);

        String estate = PreferenceUtils.getString(this, "estate");

        if (estate.equals("0") || estate.equals("-1")) {
            tv_next.setVisibility(View.VISIBLE);
        } else {
            tv_next.setVisibility(View.GONE);
            MyToast.showLong(this, "当前车辆正在评估或已评估完成，不可修改车辆照片");
        }

        int carpicchange = PreferenceUtils.getInt(this, "carpicchange");

//        if (carpicchange == 0) {
//            tv_next.setVisibility(View.GONE);
//        }

        userId = PreferenceUtils.getString(this, "userId");
        carId = PreferenceUtils.getString(this, "carId");

//        Intent intent = this.getIntent();

//        bean = (CarDetailBean) intent.getSerializableExtra("CarDetailBean");

        showImage();    /* https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498552710&di=907cdb67a4c6acd5759622b61a32f2be&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2F1011%2F06041G22505%2F1F604122505-3.jpg*/
        initRecylerView();

    }

    private void initRecylerView() {
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        companyReport.setLayoutManager(ms);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectMedia4);
        adapter.setSelectMax(100);
        companyReport.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        PictureConfig.getInstance().externalPicturePreview(UpdateCarPicActivity.this, position - 1, selectMedia4);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia4.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateCarPicActivity.this, selectMedia4.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });//客户照片上传
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    themeStyle = ContextCompat.getColor(UpdateCarPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateCarPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateCarPicActivity.this, R.color.tab_color_true);

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
                            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
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
                        PictureConfig.getInstance().init(options1).startOpenCamera(UpdateCarPicActivity.this, resultCallback5);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options1).openPhoto(UpdateCarPicActivity.this, resultCallback5);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia4.remove(position);
                    adapter.notifyItemRemoved(position + 1);
                    break;
            }
        }
    };

    private PictureConfig.OnSelectResultCallback resultCallback5 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            /*selectMedia4 = resultList;*/
           /* resultList.addAll(selectMedia4);*/
            selectMedia4.addAll(resultList);
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

    private void showImage() {

        LogUtils.e("carid=" + carId);

        post()
                .url(Constants.URLS.BASEURL + "UserInfo/carPic")
                .addParams("carId", carId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        LogUtils.i("updatCarpicShow----------------->>>>>>." + e);

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("updateCarpicShow----------------->>>>>>." + response);

                        String json = response;
                        Gson gson = new GsonBuilder()
                                .setPrettyPrinting()
                                .create();
                        carPicBean = gson.fromJson(json, CarPicBean.class);
                        LogUtils.i("updateCarpicShow----------------->>>>>>." + gson.toJson(carPicBean));
                        LinearLayoutManager ms = new LinearLayoutManager(UpdateCarPicActivity.this);
                        ms.setOrientation(LinearLayoutManager.HORIZONTAL);

                        if (carPicBean.getCode() == 1) {

                            appenhance_pic = carPicBean.getShow().getAppenhance_pic();
                            chassis_pic = carPicBean.getShow().getChassis_pic();
                            control_pic = carPicBean.getShow().getControl_pic();
                            structure_pic = carPicBean.getShow().getStructure_pic();

                            rvOtherShow.setLayoutManager(ms);
                            rvOtherShow.setAdapter(new MyCarPicAdapter(carPicBean, UpdateCarPicActivity.this));

                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getAppenhance_pic().get(0).split(",")[0], R.drawable.crabgnormal, waiguanZhengqian);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getAppenhance_pic().get(1).split(",")[0], R.drawable.crabgnormal, waiguanZhenghou);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getAppenhance_pic().get(2).split(",")[0], R.drawable.crabgnormal, waiguanZuoqian);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getAppenhance_pic().get(3).split(",")[0], R.drawable.crabgnormal, waiguanYouqian);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getAppenhance_pic().get(4).split(",")[0], R.drawable.crabgnormal, waiguanZuohou);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getAppenhance_pic().get(5).split(",")[0], R.drawable.crabgnormal, waiguanYouhou);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getAppenhance_pic().get(6).split(",")[0], R.drawable.crabgnormal, waiguanFengdang);

                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getControl_pic().get(0).split(",")[0], R.drawable.crabgnormal, zhongkongLicheng);

                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getStructure_pic().get(0).split(",")[0], R.drawable.crabgnormal, neibuMingpai);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getStructure_pic().get(1).split(",")[0], R.drawable.crabgnormal, neibuFadongji);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getStructure_pic().get(2).split(",")[0], R.drawable.crabgnormal, neibuHoubeixing);

                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getChassis_pic().get(0).split(",")[0], R.drawable.crabgnormal, fujiaDengji1);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getChassis_pic().get(1).split(",")[0], R.drawable.crabgnormal, fujiaDengji2);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getChassis_pic().get(2).split(",")[0], R.drawable.crabgnormal, fujiaDengji3);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getChassis_pic().get(3).split(",")[0], R.drawable.crabgnormal, fujiaDengji4);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getChassis_pic().get(4).split(",")[0], R.drawable.crabgnormal, fujiaDengji5);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getChassis_pic().get(5).split(",")[0], R.drawable.crabgnormal, fujiaDengji6);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getChassis_pic().get(6).split(",")[0], R.drawable.crabgnormal, fujiaDengji7);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getChassis_pic().get(7).split(",")[0], R.drawable.crabgnormal, fujiaDengji8);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getChassis_pic().get(8).split(",")[0], R.drawable.crabgnormal, fujiaDengji9);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getChassis_pic().get(9).split(",")[0], R.drawable.crabgnormal, fujiaXinshiZheng);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getChassis_pic().get(10).split(",")[0], R.drawable.crabgnormal, fujiaXinshiFu);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getChassis_pic().get(11).split(",")[0], R.drawable.crabgnormal, fujiaXinshiJian);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getChassis_pic().get(12).split(",")[0], R.drawable.crabgnormal, fujiaShangxian);
                            ImageViewUtils.showNetImage(UpdateCarPicActivity.this, carPicBean.getList().getChassis_pic().get(13).split(",")[0], R.drawable.crabgnormal, fujiaQiangxian);

                            etRemark.setText(carPicBean.getRemark());
                        } else if (carPicBean.getCode() == 0) {
                            new AlertDialog.Builder(UpdateCarPicActivity.this)
                                    .setMessage(carPicBean.getMsg())
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                new AlertDialog.Builder(this)
                        .setMessage("是否返回?")
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


    @OnClick({R.id.waiguan_zhengqian, R.id.waiguan_zhenghou, R.id.waiguan_zuoqian, R.id.waiguan_youqian, R.id.waiguan_zuohou, R.id.waiguan_youhou, R.id.waiguan_fengdang,
            R.id.zhongkong_licheng,
            R.id.neibu_mingpai, R.id.neibu_fadongji, R.id.neibu_houbeixing,
            R.id.fujia_dengji_1, R.id.fujia_dengji_2, R.id.fujia_dengji_3, R.id.fujia_dengji_4, R.id.fujia_dengji_5, R.id.fujia_dengji_6, R.id.fujia_dengji_7, R.id.fujia_dengji_8, R.id.fujia_dengji_9, R.id.fujia_xinshi_zheng, R.id.fujia_xinshi_fu, R.id.fujia_xinshi_jian, R.id.fujia_shangxian, R.id.fujia_qiangxian,
            R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.waiguan_zhengqian:
                //                PictureConfig.getInstance().init(options).startOpenCamera(this, new ResultCa((ImageView) view, selectMediaa, 0));
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa((ImageView) view, selectMediaa, 0, 0));
                break;
            case R.id.waiguan_zhenghou:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa((ImageView) view, selectMediab, 0, 1));
                break;
            case R.id.waiguan_zuoqian:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa((ImageView) view, selectMediac, 0, 2));
                break;
            case R.id.waiguan_youqian:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa((ImageView) view, selectMediaaA, 0, 3));
                break;
            case R.id.waiguan_zuohou:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa((ImageView) view, selectMediaaB, 0, 4));
                break;
            case R.id.waiguan_youhou:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa((ImageView) view, selectMediaaC, 0, 5));
                break;
            case R.id.waiguan_fengdang:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa((ImageView) view, selectMediaaD, 0, 6));
                break;
            case R.id.zhongkong_licheng:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa1((ImageView) view, selectMediad, 0, 0));
                break;
            case R.id.neibu_mingpai:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa2((ImageView) view, selectMedial, 0, 0));
                break;
            case R.id.neibu_fadongji:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa2((ImageView) view, selectMediam, 0, 1));
                break;
            case R.id.neibu_houbeixing:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa2((ImageView) view, selectMedian, 0, 2));
                break;
            case R.id.fujia_dengji_1:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMedias, 0, 0));
                break;
            case R.id.fujia_dengji_2:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediat, 0, 1));
                break;
            case R.id.fujia_dengji_3:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediau, 0, 2));
                break;
            case R.id.fujia_dengji_4:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediav, 0, 3));
                break;
            case R.id.fujia_dengji_5:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediaw, 0, 4));
                break;
            case R.id.fujia_dengji_6:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediax, 0, 5));
                break;
            case R.id.fujia_dengji_7:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediaa1, 0, 6));
                break;
            case R.id.fujia_dengji_8:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediab2, 0, 7));
                break;
            case R.id.fujia_dengji_9:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediac3, 0, 8));
                break;
            case R.id.fujia_xinshi_zheng:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediad4, 0, 9));
                break;
            case R.id.fujia_xinshi_fu:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediae5, 0, 10));
                break;
            case R.id.fujia_xinshi_jian:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediaf6, 0, 11));
                break;
            case R.id.fujia_shangxian:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediag7, 0, 12));
                break;
            case R.id.fujia_qiangxian:
                PictureConfig.getInstance().init(options).openPhoto(this, new ResultCa3((ImageView) view, selectMediah8, 0, 13));
                break;
            case R.id.tv_next:

                showProgressDialog();

                final List<String> keys = new ArrayList<>();

                List<List<LocalMedia>> medias = new ArrayList<>();


                if (selectMedia4.size()>0){

                    medias.add(selectMedia4);

                    for (int i = 0; i < medias.size(); i++) {
                        List<LocalMedia> localMedias = medias.get(i);
                        for (int j = 0; j < localMedias.size(); j++) {
                            String compressPath = localMedias.get(j).getCompressPath();

                            final int finalI = i;
                            final int finalJ = j;
                            key = UUID.randomUUID() + TimeUtils.getRandomFileName() + ".png";
                            if (finalI == 0) {
                                if (other2 == null) {
                                    other2 = new JSONArray();
                                }
                                try {
                                    other2.put(finalJ, key);
                                    carPicBean.getShow().getOther_pic().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }

                            LogUtils.i("ceshi---------------------------------" + key);

                            QiNiuUtlis.upLoad(compressPath, key, new UpCompletionHandler() {
                                        @Override
                                        public void complete(String key, ResponseInfo info, JSONObject response) {

                                            System.out.println("i======" + info.toString());

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

                                                        System.out.println(upLoadCount == selectMedia4.size());
                                                        if (upLoadCount == selectMedia4.size()) {

                                                            post2();
                                                        }
                                                    }

                                                }
                                            }, null));


                        }
                    }

                }else {
                    post2();
                }

                break;
            default:
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("正在上传");

        progressDialog.show();

    }

    private void post2(){
        Gson gson = new Gson();
        appenhance = gson.toJson(appenhance_pic);
        control = gson.toJson(control_pic);
        structure = gson.toJson(structure_pic);
        chassis = gson.toJson(chassis_pic);
        house = gson.toJson(carPicBean.getShow().getOther_pic());

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

        OkHttpUtils
                .post()
                //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                .url(Constants.URLS.BASEURL + "Login/carPicarr")
                .addParams("carId", carId)
                .addParams("appenhance_pic", appenhance.toString())
                .addParams("appenhance_pictext", appenhance_pictext.toString())
                .addParams("control_pic", control.toString())
                .addParams("control_pictext", control_pictext.toString())
                .addParams("structure_pic", structure.toString())
                .addParams("structure_pictext", structure_pictext.toString())
                .addParams("chassis_pic", chassis.toString())
                .addParams("chassis_pictext", chassis_pictext.toString())
                .addParams("other_pic", house == null ? "-1" : house.toString())
                .addParams("remark", etRemark.getText() == null ? "-1" : etRemark.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("upload____________________>>>>>" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("upload----------------->>>>>>." + response);
                        DeleteImageUtils.DeleteImage(selectMedia, UpdateCarPicActivity.this);


                        String json = response;
                        Gson gson = new Gson();
                        UploadCarIamgeBean bean = gson.fromJson(json, UploadCarIamgeBean.class);

                        if (bean.getCode() == 1) {

                            new AlertDialog.Builder(UpdateCarPicActivity.this)
                                    .setTitle("车辆照片保存成功")
                                    .setMessage("\n" + "是否提交给评估师？")
                                    .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            hud.show();
                                            toEvaluate();
                                        }
                                    })
                                    .setNegativeButton("仅保存", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            // // 设置过渡动画
                                            int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                            int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                            overridePendingTransition(enterAnim0, exitAnim0);
                                        }
                                    })
                                    .show()
                                    .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));

                        } else {
                            MyToast.show(UpdateCarPicActivity.this, bean.getMsg());
                        }
                    }
                });
    }

    private void toEvaluate() {
        LogUtils.i("toEvaluate------------------>>>" + carId);
        post().url(Constants.URLS.BASEURL + "Appraiser/resubmit")
                .addParams("id", carId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("toEvaluate------------------>>>" + e);
                        hud.dismiss();
                        MyToast.show(UpdateCarPicActivity.this, "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("toEvaluate------------------>>>" + response);
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        DialogBankBean bean = gson.fromJson(json, DialogBankBean.class);
                        if (bean.getCode() == 1) {
                            MyToast.show(UpdateCarPicActivity.this, bean.getMsg());
                            finish();
                            // // 设置过渡动画
                            int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                            int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                            overridePendingTransition(enterAnim0, exitAnim0);
                        } else {
                            MyToast.show(UpdateCarPicActivity.this, bean.getMsg());
                        }
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }


    class ResultCa implements PictureConfig.OnSelectResultCallback {
        private ImageView view;
        private List<LocalMedia> list;
        private int position;
        private int listPosition;

        ResultCa(ImageView view, List<LocalMedia> list, int position, int listPosition) {
            this.list = list;
            this.position = position;
            this.view = view;
            this.listPosition = listPosition;
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
                Glide.with(UpdateCarPicActivity.this)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(UpdateCarPicActivity.this)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }
            String key = UUID.randomUUID() + TimeUtils.getRandomFileName() + "hdc" + ".png";

            QiNiuUtlis.upLoad(path, key, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    if (info.isOK()) {
                        LogUtils.e("完成" + key);

                        appenhance_pic.remove(listPosition);
                        appenhance_pic.add(listPosition, key);


//                        chassis = gson.toJson(chassis_pic);
//                        control = gson.toJson(control_pic);
//                        structure = gson.toJson(structure_pic);

//                        System.out.println(appenhance);
                    }


                }
            });
        }

    }

    class ResultCa1 implements PictureConfig.OnSelectResultCallback {
        private ImageView view;
        private List<LocalMedia> list;
        private int position;
        private int listPosition;


        ResultCa1(ImageView view, List<LocalMedia> list, int position, int listPosition) {
            this.list = list;
            this.position = position;
            this.view = view;
            this.listPosition = listPosition;
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
                Glide.with(UpdateCarPicActivity.this)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(UpdateCarPicActivity.this)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }

            String key = UUID.randomUUID() + TimeUtils.getRandomFileName() + "hdc" + ".png";

            QiNiuUtlis.upLoad(path, key, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    if (info.isOK()) {
                        LogUtils.e("完成" + key);

                        control_pic.remove(listPosition);
                        control_pic.add(listPosition, key);

//                        chassis = gson.toJson(chassis_pic);
//                        control = gson.toJson(control_pic);
//                        structure = gson.toJson(structure_pic);

//                        System.out.println(control);
                    }


                }
            });
        }


    }

    class ResultCa2 implements PictureConfig.OnSelectResultCallback {
        private ImageView view;
        private List<LocalMedia> list;
        private int position;
        private int listPosition;

        ResultCa2(ImageView view, List<LocalMedia> list, int position, int listPosition) {
            this.list = list;
            this.position = position;
            this.view = view;
            this.listPosition = listPosition;
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
                Glide.with(UpdateCarPicActivity.this)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地

                Glide.with(UpdateCarPicActivity.this)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }
            String key = UUID.randomUUID() + TimeUtils.getRandomFileName() + "hdc" + ".png";

            QiNiuUtlis.upLoad(path, key, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    if (info.isOK()) {
                        LogUtils.e("完成" + key);

                        structure_pic.remove(listPosition);
                        structure_pic.add(listPosition, key);

//                        chassis = gson.toJson(chassis_pic);
//                        control = gson.toJson(control_pic);
//                        structure = gson.toJson(structure_pic);

//                        System.out.println(control);
                    }


                }
            });
        }


    }

    class ResultCa3 implements PictureConfig.OnSelectResultCallback {
        private ImageView view;
        private List<LocalMedia> list;
        private int position;
        private int listPosition;

        ResultCa3(ImageView view, List<LocalMedia> list, int position, int listPosition) {
            this.list = list;
            this.position = position;
            this.view = view;
            this.listPosition = listPosition;
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
                Glide.with(UpdateCarPicActivity.this)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(UpdateCarPicActivity.this)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }

            String key = UUID.randomUUID() + TimeUtils.getRandomFileName() + "hdc" + ".png";

            QiNiuUtlis.upLoad(path, key, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    if (info.isOK()) {
                        LogUtils.e("完成" + key);

                        chassis_pic.remove(listPosition);
                        chassis_pic.add(listPosition, key);

//                        chassis = gson.toJson(chassis_pic);
//                        control = gson.toJson(control_pic);
//                        structure = gson.toJson(structure_pic);

//                        System.out.println(control);
                    }


                }
            });
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
            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
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

