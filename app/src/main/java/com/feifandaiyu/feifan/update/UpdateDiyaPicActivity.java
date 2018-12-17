package com.feifandaiyu.feifan.update;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.GridImageAdapter;
import com.feifandaiyu.feifan.adapter.MyAnzhuangAdapter;
import com.feifandaiyu.feifan.adapter.MyChepaihaoAdapter;
import com.feifandaiyu.feifan.adapter.MyDabenAdapter;
import com.feifandaiyu.feifan.adapter.MyFapiaoAdapter;
import com.feifandaiyu.feifan.adapter.MyFengdangAdapter;
import com.feifandaiyu.feifan.adapter.MyHegeAdapter;
import com.feifandaiyu.feifan.adapter.MyHeyingAdapter;
import com.feifandaiyu.feifan.adapter.MyQiangxianAdapter;
import com.feifandaiyu.feifan.adapter.MyShangxianAdapter;
import com.feifandaiyu.feifan.adapter.MyXieyiAdapter;
import com.feifandaiyu.feifan.adapter.MyXingshizhengAdapter;
import com.feifandaiyu.feifan.adapter.OfficeAdappter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.DialogBankBean;
import com.feifandaiyu.feifan.bean.DiyaBean;
import com.feifandaiyu.feifan.bean.OfficeBean;
import com.feifandaiyu.feifan.bean.PersonalUploadImageBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.QiNiuUtlis;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.feifandaiyu.feifan.utils.TimeUtils;
import com.feifandaiyu.feifan.view.OnKeyActionListener;
import com.feifandaiyu.feifan.view.VehiclePlateKeyboard;
import com.google.gson.Gson;
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


public class UpdateDiyaPicActivity extends BaseActivity {

    @InjectView(R.id.et_gps_number)
    EditText etGpsNumber;
    @InjectView(R.id.custom_daben)
    RecyclerView customDaben;
    @InjectView(R.id.custom_qiangxian)
    RecyclerView customQiangxian;
    @InjectView(R.id.custom_shangxian)
    RecyclerView customShangxian;
    @InjectView(R.id.custom_xingshizheng)
    RecyclerView customXingshizheng;
    @InjectView(R.id.custom_fapiao)
    RecyclerView customFapiao;
    @InjectView(R.id.custom_hegezheng)
    RecyclerView customHegezheng;
    @InjectView(R.id.custom_xieyi)
    RecyclerView customXieyi;
    @InjectView(R.id.custom_fengdang)
    RecyclerView customFengdang;
    @InjectView(R.id.custom_chepaihao)
    RecyclerView customChepaihao;
    @InjectView(R.id.custom_anzhuang)
    RecyclerView customAnzhuang;
    @InjectView(R.id.custom_heying)
    RecyclerView customHeying;
    @InjectView(R.id.tv_choose_office)
    TextView tvChooseOffice;
    @InjectView(R.id.ll_xingshizheng)
    LinearLayout llXingshizheng;
    @InjectView(R.id.rl_xingshizheng)
    RelativeLayout rlXingshizheng;
    @InjectView(R.id.ll_fapiao)
    LinearLayout llFapiao;
    @InjectView(R.id.rl_fapiao)
    RelativeLayout rlFapiao;
    @InjectView(R.id.ll_hegezheng)
    LinearLayout llHegezheng;
    @InjectView(R.id.rl_hegezheng)
    RelativeLayout rlHegezheng;
    @InjectView(R.id.ll_xieyi)
    LinearLayout llXieyi;
    @InjectView(R.id.rl_xieyi)
    RelativeLayout rlXieyi;
    @InjectView(R.id.rv_daben)
    RecyclerView rvDaben;
    @InjectView(R.id.rv_qiangxian)
    RecyclerView rvQiangxian;
    @InjectView(R.id.rv_shangxian)
    RecyclerView rvShangxian;
    @InjectView(R.id.rv_xingshizheng)
    RecyclerView rvXingshizheng;
    @InjectView(R.id.rv_fapiao)
    RecyclerView rvFapiao;
    @InjectView(R.id.rv_hegezheng)
    RecyclerView rvHegezheng;
    @InjectView(R.id.rv_xieyi)
    RecyclerView rvXieyi;
    @InjectView(R.id.rv_fengdang)
    RecyclerView rvFengdang;
    @InjectView(R.id.rv_chepaihao)
    RecyclerView rvChepaihao;
    @InjectView(R.id.rv_anzhuang)
    RecyclerView rvAnzhuang;
    @InjectView(R.id.rv_heying)
    RecyclerView rvHeying;
    @InjectView(R.id.et_chepai_num)
    TextView etChepaiNum;
    @InjectView(R.id.ll_chepai)
    LinearLayout llChepai;
    @InjectView(R.id.et_chejia_num)
    PowerfulEditText etChejiaNum;
    @InjectView(R.id.ll_chejiahao)
    LinearLayout llChejiahao;

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
    private JSONArray XingshizhengPic;
    private JSONArray FapiaoPic;
    private JSONArray HegezhengPic;
    private JSONArray FengdangPic;
    private JSONArray ChepaiPic;
    private JSONArray AnzhuangPic;
    private JSONArray HeyingPic;
    private JSONArray XieyiPic;
    private UploadOptions uploadOptions;
    private int upLoadCount = 0;
    private ProgressDialog progressDialog;
    private String key;
    private String saleID;
    private String userId;
    private String isNew;
    private OfficeAdappter adapter;
    private List<OfficeBean.ListBean> list;
    private String rid;
    private String carId;
    private String mid;
    private DiyaBean diyaBean;
    private KProgressHUD hud;

    @Override
    protected int getContentView() {
        return R.layout.activity_update_diya_pic;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("");
        showNext(true);
        ButterKnife.inject(this);
        initRecylerView();

        initData();

        initPicData();

        if (isNew.equals("1")) {
            rlXieyi.setVisibility(View.GONE);
            llXieyi.setVisibility(View.GONE);
        } else if (isNew.equals("2")) {
            rlXingshizheng.setVisibility(View.GONE);
            llXingshizheng.setVisibility(View.GONE);
            rlFapiao.setVisibility(View.GONE);
            llFapiao.setVisibility(View.GONE);
            rlHegezheng.setVisibility(View.GONE);
            llHegezheng.setVisibility(View.GONE);
            llChepai.setVisibility(View.GONE);
            llChejiahao.setVisibility(View.GONE);
        }

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍候")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        tv_next = (TextView) findViewById(R.id.tv_next);

        tv_next.setEnabled(true);

        tv_next.setText("上传");
       /* adapter = new GridImageAdapter(this, onAddPicClickListener);*/
    }

    private void initPicData() {

        LogUtils.e("mid=" + mid);
        LogUtils.e("saleID" + saleID);

        post()
                .url(Constants.URLS.BASEURL + "UserPic/mortgageShow")
                .addParams("mid", mid)
                .addParams("saleId", saleID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("updateDiyaPicShow----------------->>>>>>." + e);
//                        hud.dismiss();
                        MyToast.show(UpdateDiyaPicActivity.this, "联网失败...");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("updateDiyaPicShow----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();

                        LinearLayoutManager ms1 = new LinearLayoutManager(UpdateDiyaPicActivity.this);
                        ms1.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms2 = new LinearLayoutManager(UpdateDiyaPicActivity.this);
                        ms2.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms3 = new LinearLayoutManager(UpdateDiyaPicActivity.this);
                        ms3.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms4 = new LinearLayoutManager(UpdateDiyaPicActivity.this);
                        ms4.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms5 = new LinearLayoutManager(UpdateDiyaPicActivity.this);
                        ms5.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms6 = new LinearLayoutManager(UpdateDiyaPicActivity.this);
                        ms6.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms7 = new LinearLayoutManager(UpdateDiyaPicActivity.this);
                        ms7.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms8 = new LinearLayoutManager(UpdateDiyaPicActivity.this);
                        ms8.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms9 = new LinearLayoutManager(UpdateDiyaPicActivity.this);
                        ms9.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms10 = new LinearLayoutManager(UpdateDiyaPicActivity.this);
                        ms10.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearLayoutManager ms11 = new LinearLayoutManager(UpdateDiyaPicActivity.this);
                        ms11.setOrientation(LinearLayoutManager.HORIZONTAL);

                        diyaBean = gson.fromJson(json, DiyaBean.class);

//                        hud.dismiss();

                        if (diyaBean.getCode() == 1) {

                            if (isNew.equals("1")) {

                                etChejiaNum.setText(diyaBean.getChejiahao());
                                etChepaiNum.setText(diyaBean.getChepaihao());

                                rvDaben.setLayoutManager(ms1);
                                rvDaben.setAdapter(new MyDabenAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvQiangxian.setLayoutManager(ms2);
                                rvQiangxian.setAdapter(new MyQiangxianAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvShangxian.setLayoutManager(ms3);
                                rvShangxian.setAdapter(new MyShangxianAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvFengdang.setLayoutManager(ms6);
                                rvFengdang.setAdapter(new MyFengdangAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvChepaihao.setLayoutManager(ms7);
                                rvChepaihao.setAdapter(new MyChepaihaoAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvAnzhuang.setLayoutManager(ms8);
                                rvAnzhuang.setAdapter(new MyAnzhuangAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvHeying.setLayoutManager(ms4);
                                rvHeying.setAdapter(new MyHeyingAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvFapiao.setLayoutManager(ms9);
                                rvFapiao.setAdapter(new MyFapiaoAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvHegezheng.setLayoutManager(ms10);
                                rvHegezheng.setAdapter(new MyHegeAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvXingshizheng.setLayoutManager(ms11);
                                rvXingshizheng.setAdapter(new MyXingshizhengAdapter(diyaBean, UpdateDiyaPicActivity.this));
                            } else if (isNew.equals("2")) {
                                rvDaben.setLayoutManager(ms1);
                                rvDaben.setAdapter(new MyDabenAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvQiangxian.setLayoutManager(ms2);
                                rvQiangxian.setAdapter(new MyQiangxianAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvShangxian.setLayoutManager(ms3);
                                rvShangxian.setAdapter(new MyShangxianAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvXieyi.setLayoutManager(ms5);
                                rvXieyi.setAdapter(new MyXieyiAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvFengdang.setLayoutManager(ms6);
                                rvFengdang.setAdapter(new MyFengdangAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvChepaihao.setLayoutManager(ms7);
                                rvChepaihao.setAdapter(new MyChepaihaoAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvAnzhuang.setLayoutManager(ms8);
                                rvAnzhuang.setAdapter(new MyAnzhuangAdapter(diyaBean, UpdateDiyaPicActivity.this));
                                rvHeying.setLayoutManager(ms4);
                                rvHeying.setAdapter(new MyHeyingAdapter(diyaBean, UpdateDiyaPicActivity.this));
                            }

                            etGpsNumber.setText(diyaBean.getImei());
                            tvChooseOffice.setText(diyaBean.getRname());

                        } else if (diyaBean.getCode() == 0) {
                            new AlertDialog.Builder(UpdateDiyaPicActivity.this)

                                    .setMessage(diyaBean.getMsg())
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

    private void initData() {
        saleID = PreferenceUtils.getString(this, "saleID");
        userId = PreferenceUtils.getString(UpdateDiyaPicActivity.this, "userId");
        carId = PreferenceUtils.getString(UpdateDiyaPicActivity.this, "carId");
        mid = PreferenceUtils.getString(UpdateDiyaPicActivity.this, "mid");

        isNew = PreferenceUtils.getString(UpdateDiyaPicActivity.this, "isNew");
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

        customDaben.setLayoutManager(ms1);
        customQiangxian.setLayoutManager(ms2);
        customShangxian.setLayoutManager(ms3);
        customXingshizheng.setLayoutManager(ms4);
        customFapiao.setLayoutManager(ms5);
        customHegezheng.setLayoutManager(ms6);
        customFengdang.setLayoutManager(ms7);
        customChepaihao.setLayoutManager(ms8);
        customAnzhuang.setLayoutManager(ms9);
        customHeying.setLayoutManager(ms10);
        customXieyi.setLayoutManager(ms11);

        adapter1 = new GridImageAdapter(this, onAddPicClickListener1);
        adapter1.setList(selectMedia1);
        adapter1.setSelectMax(maxSelectNum);
        customDaben.setAdapter(adapter1);

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
        customXingshizheng.setAdapter(adapter4);

        adapter5 = new GridImageAdapter(this, onAddPicClickListener5);
        adapter5.setList(selectMedia5);
        adapter5.setSelectMax(maxSelectNum);
        customFapiao.setAdapter(adapter5);

        adapter6 = new GridImageAdapter(this, onAddPicClickListener6);
        adapter6.setList(selectMedia6);
        adapter6.setSelectMax(maxSelectNum);
        customHegezheng.setAdapter(adapter6);

        adapter7 = new GridImageAdapter(this, onAddPicClickListener7);
        adapter7.setList(selectMedia7);
        adapter7.setSelectMax(maxSelectNum);
        customFengdang.setAdapter(adapter7);

        adapter8 = new GridImageAdapter(this, onAddPicClickListener8);
        adapter8.setList(selectMedia8);
        adapter8.setSelectMax(maxSelectNum);
        customChepaihao.setAdapter(adapter8);

        adapter9 = new GridImageAdapter(this, onAddPicClickListener9);
        adapter9.setList(selectMedia9);
        adapter9.setSelectMax(maxSelectNum);
        customAnzhuang.setAdapter(adapter9);

        adapter10 = new GridImageAdapter(this, onAddPicClickListener10);
        adapter10.setList(selectMedia10);
        adapter10.setSelectMax(maxSelectNum);
        customHeying.setAdapter(adapter10);

        adapter11 = new GridImageAdapter(this, onAddPicClickListener11);
        adapter11.setList(selectMedia11);
        adapter11.setSelectMax(maxSelectNum);
        customXieyi.setAdapter(adapter11);


        adapter1.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        //PictureConfig.getInstance().externalPicturePreview(MainActivity.this, "/custom_file", position, selectMedia);
                        PictureConfig.getInstance().externalPicturePreview(UpdateDiyaPicActivity.this, position - 1, selectMedia1);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia1.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateDiyaPicActivity.this, selectMedia1.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateDiyaPicActivity.this, position - 1, selectMedia2);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia2.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateDiyaPicActivity.this, selectMedia2.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateDiyaPicActivity.this, position - 1, selectMedia3);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia3.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateDiyaPicActivity.this, selectMedia3.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateDiyaPicActivity.this, position - 1, selectMedia4);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia4.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateDiyaPicActivity.this, selectMedia4.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateDiyaPicActivity.this, position - 1, selectMedia5);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia5.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateDiyaPicActivity.this, selectMedia5.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateDiyaPicActivity.this, position - 1, selectMedia6);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia6.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateDiyaPicActivity.this, selectMedia6.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateDiyaPicActivity.this, position - 1, selectMedia7);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia7.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateDiyaPicActivity.this, selectMedia7.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateDiyaPicActivity.this, position - 1, selectMedia8);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia8.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateDiyaPicActivity.this, selectMedia8.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateDiyaPicActivity.this, position - 1, selectMedia9);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia9.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateDiyaPicActivity.this, selectMedia9.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateDiyaPicActivity.this, position - 1, selectMedia10);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia10.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateDiyaPicActivity.this, selectMedia10.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateDiyaPicActivity.this, position - 1, selectMedia11);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia11.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateDiyaPicActivity.this, selectMedia11.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });

    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener1 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateDiyaPicActivity.this, resultCallback1);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateDiyaPicActivity.this, resultCallback1);
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

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener2 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    themeStyle = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateDiyaPicActivity.this, resultCallback2);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateDiyaPicActivity.this, resultCallback2);
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

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener3 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateDiyaPicActivity.this, resultCallback3);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateDiyaPicActivity.this, resultCallback3);
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

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener4 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateDiyaPicActivity.this, resultCallback4);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateDiyaPicActivity.this, resultCallback4);
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
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener5 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateDiyaPicActivity.this, resultCallback5);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateDiyaPicActivity.this, resultCallback5);
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
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener6 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateDiyaPicActivity.this, resultCallback6);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateDiyaPicActivity.this, resultCallback6);
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
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener7 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateDiyaPicActivity.this, resultCallback7);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateDiyaPicActivity.this, resultCallback7);
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
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener8 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateDiyaPicActivity.this, resultCallback8);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateDiyaPicActivity.this, resultCallback8);
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
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener9 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateDiyaPicActivity.this, resultCallback9);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateDiyaPicActivity.this, resultCallback9);
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
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener10 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:


                    themeStyle = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateDiyaPicActivity.this, resultCallback10);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateDiyaPicActivity.this, resultCallback10);
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
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener11 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:

                    themeStyle = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateDiyaPicActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateDiyaPicActivity.this, resultCallback11);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateDiyaPicActivity.this, resultCallback11);
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

    @OnClick({R.id.tv_next, R.id.iv_back, R.id.et_chepai_num})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                if(rid==null||rid.equals("")){
                    MyToast.show(this, "内勤人员不能为空");
                    return;
                }
//                if (etGpsNumber.getText() == null || etGpsNumber.getText().toString().equals("")) {
//                    MyToast.show(this, "请填写GPS设备号");
//                    return;
//                }
//
//                if (selectMedia1.size() <= 0) {
//                    MyToast.show(this, "大本照片未上传");
//                    return;
//                }
//
//                if (selectMedia2.size() <= 0) {
//                    MyToast.show(this, "交强险未上传");
//                    return;
//                }
//
//                if (selectMedia3.size() <= 0) {
//                    MyToast.show(this, "商业险未上传");
//                    return;
//                }
//
//                if (isNew.equals("1")) {
//                    if (selectMedia4.size() <= 0) {
//                        MyToast.show(this, "行驶证未上传");
//                        return;
//                    }
//
//                    if (selectMedia5.size() <= 0) {
//                        MyToast.show(this, "发票未上传");
//                        return;
//                    }
//
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
//
//                if (selectMedia7.size() <= 0) {
//                    MyToast.show(this, "风挡vin码和设备合影未上传");
//                    return;
//                }
//
//                if (selectMedia8.size() <= 0) {
//                    MyToast.show(this, "车牌号和设备合影未上传");
//                    return;
//                }
//
//                if (selectMedia9.size() <= 0) {
//                    MyToast.show(this, "安装位置未上传");
//                    return;
//                }
//
//                if (selectMedia10.size() <= 0) {
//                    MyToast.show(this, "客户合影未上传");
//                    return;
//                }


                if (rid == null || rid.equals("") || rid.equals("null")) {
                    rid = diyaBean.getRid();
//                    MyToast.show(this, "请选择内勤人员");
//                    return;
                }

                showProgressDialog();

                final List<String> keys = new ArrayList<>();

                List<List<LocalMedia>> medias = new ArrayList<>();
                piccount = selectMedia1.size() + selectMedia2.size() + selectMedia3.size() + selectMedia4.size() + selectMedia5.size() + selectMedia6.size()
                        + selectMedia7.size() + selectMedia8.size() + selectMedia9.size() + selectMedia10.size() + selectMedia11.size();
                LogUtils.i("piccount", "-------------------->" + piccount);

                if (piccount == 0) {
                    post2();
                } else {

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
                                    diyaBean.getList().getAfterMortgage().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            } else if (finalI == 1) {
                                if (QiangxianPic == null) {
                                    QiangxianPic = new JSONArray();
                                }

                                try {
                                    QiangxianPic.put(finalJ, key);
                                    diyaBean.getList().getInsurance().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 2) {
                                if (ShangxianPic == null) {
                                    ShangxianPic = new JSONArray();
                                }
                                try {
                                    ShangxianPic.put(finalJ, key);
                                    diyaBean.getList().getCommercial().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 3) {
                                if (XingshizhengPic == null) {
                                    XingshizhengPic = new JSONArray();
                                }
                                try {
                                    XingshizhengPic.put(finalJ, key);
                                    diyaBean.getList().getDrivingLicense().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 4) {
                                if (FapiaoPic == null) {
                                    FapiaoPic = new JSONArray();
                                }
                                try {
                                    FapiaoPic.put(finalJ, key);
                                    diyaBean.getList().getInvoice().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 5) {
                                if (HegezhengPic == null) {
                                    HegezhengPic = new JSONArray();
                                }
                                try {
                                    HegezhengPic.put(finalJ, key);
                                    diyaBean.getList().getCertificate().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 6) {
                                if (FengdangPic == null) {
                                    FengdangPic = new JSONArray();
                                }
                                try {
                                    FengdangPic.put(finalJ, key);
                                    diyaBean.getList().getVincode().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 7) {
                                if (ChepaiPic == null) {
                                    ChepaiPic = new JSONArray();
                                }
                                try {
                                    ChepaiPic.put(finalJ, key);
                                    diyaBean.getList().getLicensenum().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 8) {
                                if (AnzhuangPic == null) {
                                    AnzhuangPic = new JSONArray();
                                }
                                try {
                                    AnzhuangPic.put(finalJ, key);
                                    diyaBean.getList().getPosition().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 9) {
                                if (HeyingPic == null) {
                                    HeyingPic = new JSONArray();
                                }
                                try {
                                    HeyingPic.put(finalJ, key);
                                    diyaBean.getList().getGroupPhoto().add(key);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (finalI == 10) {
                                if (XieyiPic == null) {
                                    XieyiPic = new JSONArray();
                                }
                                try {
                                    XieyiPic.put(finalJ, key);
                                    diyaBean.getList().getAfterMortgage().add(key);
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

            case R.id.et_chepai_num:

                VehiclePlateKeyboard keyboard = new VehiclePlateKeyboard(UpdateDiyaPicActivity.this, new OnKeyActionListener() {
                    @Override
                    public void onFinish(String input) {
                        if (input.length() == 7) {
                            etChepaiNum.setText(input);
                        } else {
                            etChepaiNum.setText("");

                        }
                    }

                    @Override
                    public void onProcess(String input) {
                        etChepaiNum.setText("");
                    }
                });
                keyboard.setDefaultPlateNumber("");
                keyboard.show(getWindow().getDecorView().getRootView());
                break;

            default:
        }
    }

    private void post2() {

        if (isNew.equals("1")) {

            if (diyaBean.getList().getDrivingLicense().size() == 0) {
                MyToast.show(UpdateDiyaPicActivity.this, "请上传行驶证照片");
                progressDialog.dismiss();
                return;
            }

            if (diyaBean.getList().getInvoice().size() == 0) {
                MyToast.show(UpdateDiyaPicActivity.this, "请上传发票照片");
                progressDialog.dismiss();
                return;
            }

            if (diyaBean.getList().getCertificate().size() == 0) {
                MyToast.show(UpdateDiyaPicActivity.this, "请上传合格证照片");
                progressDialog.dismiss();
                return;
            }

        } else if (isNew.equals("2")) {
            if (diyaBean.getList().getSaleAgreement().size() == 0) {
                MyToast.show(UpdateDiyaPicActivity.this, "请上传二手车买卖协议");
                progressDialog.dismiss();
                return;
            }
        }

        LogUtils.e("DabenPic=" + DabenPic);
        LogUtils.e("QiangxianPic=" + QiangxianPic);
        LogUtils.e("ShangxianPic=" + ShangxianPic);
        LogUtils.e("XingshizhengPic=" + XingshizhengPic);
        LogUtils.e("FapiaoPic=" + FapiaoPic);
        LogUtils.e("HegezhengPic=" + HegezhengPic);
        LogUtils.e("FengdangPic=" + FengdangPic);
        LogUtils.e("ChepaiPic=" + ChepaiPic);
        LogUtils.e("AnzhuangPic=" + AnzhuangPic);
        LogUtils.e("HeyingPic=" + HeyingPic);
        LogUtils.e("XieyiPic=" + XieyiPic);

        Gson gson = new Gson();
        String AfterMortgage = gson.toJson(diyaBean.getList().getAfterMortgage());
        String Insurance = gson.toJson(diyaBean.getList().getInsurance());
        String Commercial = gson.toJson(diyaBean.getList().getCommercial());
        String Vincode = gson.toJson(diyaBean.getList().getVincode());
        String Licensenum = gson.toJson(diyaBean.getList().getLicensenum());
        String Position = gson.toJson(diyaBean.getList().getPosition());
        String GroupPhoto = gson.toJson(diyaBean.getList().getGroupPhoto());
        String DrivingLicense = gson.toJson(diyaBean.getList().getDrivingLicense());
        String Invoice = gson.toJson(diyaBean.getList().getInvoice());
        String Certificate = gson.toJson(diyaBean.getList().getCertificate());
        String SaleAgreement = gson.toJson(diyaBean.getList().getSaleAgreement());

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Login/addImei")
                .addParams("carId", carId)
                .addParams("flag", "1")
                .addParams("carType", isNew)
                .addParams("imei", etGpsNumber.getText().toString())
                .addParams("afterMortgage", AfterMortgage)
                .addParams("insurance", Insurance)
                .addParams("commercial", Commercial)
                .addParams("vincode", Vincode)
                .addParams("licensenum", Licensenum)
                .addParams("position", Position)
                .addParams("groupPhoto", GroupPhoto)

                .addParams("drivingLicense", diyaBean.getList().getDrivingLicense().get(0).equals("-1") ? "-1" : DrivingLicense)
                .addParams("invoice", diyaBean.getList().getInvoice().get(0).equals("-1") ? "-1" : Invoice)
                .addParams("certificate", diyaBean.getList().getCertificate().get(0).equals("-1") ? "-1" : Certificate)

                .addParams("saleAgreement", diyaBean.getList().getSaleAgreement().get(0).equals("-1") ? "-1" : SaleAgreement)

                .addParams("mid", mid)
                .addParams("rid", rid)

                .addParams("licenseNum", etChepaiNum.getText().toString())
                .addParams("vinCode", etChejiaNum.getText().toString())

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

                            if (isNew.equals("1")) {

                                new AlertDialog.Builder(UpdateDiyaPicActivity.this)
                                        .setTitle("证件片保存成功")
                                        .setMessage("\n" + "是否提交给内勤？")
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

                            } else if (isNew.equals("2")) {
                                finish();
                                MyToast.show(UpdateDiyaPicActivity.this, "上传成功");
                            }

                        } else {
                            upLoadCount = 0;

                            MyToast.show(UpdateDiyaPicActivity.this, bean.getMsg());
                        }

                    }
                });
    }


    private void toEvaluate() {
        post().url(Constants.URLS.BASEURL + "Dealer/remortgage")
                .addParams("mid", mid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("toEvaluate------------------>>>" + e);
                        hud.dismiss();
                        MyToast.show(UpdateDiyaPicActivity.this, "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("toEvaluate------------------>>>" + response);
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        DialogBankBean bean = gson.fromJson(json, DialogBankBean.class);
                        if (bean.getCode() == 1) {
                            MyToast.show(UpdateDiyaPicActivity.this, bean.getMsg());
                            finish();
                            // 设置过渡动画
                            int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                            int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                            overridePendingTransition(enterAnim0, exitAnim0);
                        } else {
                            MyToast.show(UpdateDiyaPicActivity.this, bean.getMsg());
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
            .setGrade(Luban.FIRST_GEAR) // 压缩档次 默认三档
            .setCheckNumMode(false)
            .setCompressQuality(100) // 图片裁剪质量,默认无损
            .setImageSpanCount(4) // 每行个数
            .setSelectMedia(selectMedia) // 已选图片，传入在次进去可选中，不能传入网络图片
            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
            .create();

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

    private PictureConfig.OnSelectResultCallback resultCallback2 = new PictureConfig.OnSelectResultCallback() {
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

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia2.add(media);
            if (selectMedia2 != null) {
                adapter2.setList(selectMedia2);
                adapter2.notifyDataSetChanged();
            }
        }
    };


    private PictureConfig.OnSelectResultCallback resultCallback3 = new PictureConfig.OnSelectResultCallback() {
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

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia.add(media);
            if (selectMedia3 != null) {
                adapter3.setList(selectMedia3);
                adapter3.notifyDataSetChanged();
            }
        }
    };


    private PictureConfig.OnSelectResultCallback resultCallback4 = new PictureConfig.OnSelectResultCallback() {
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

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia4.add(media);
            if (selectMedia4 != null) {
                adapter4.setList(selectMedia4);
                adapter4.notifyDataSetChanged();
            }
        }
    };


    private PictureConfig.OnSelectResultCallback resultCallback5 = new PictureConfig.OnSelectResultCallback() {
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

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia5.add(media);
            if (selectMedia5 != null) {
                adapter5.setList(selectMedia5);
                adapter5.notifyDataSetChanged();
            }
        }
    };


    private PictureConfig.OnSelectResultCallback resultCallback6 = new PictureConfig.OnSelectResultCallback() {
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

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia6.add(media);
            if (selectMedia6 != null) {
                adapter6.setList(selectMedia6);
                adapter6.notifyDataSetChanged();
            }
        }
    };


    private PictureConfig.OnSelectResultCallback resultCallback7 = new PictureConfig.OnSelectResultCallback() {
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

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia7.add(media);
            if (selectMedia7 != null) {
                adapter7.setList(selectMedia7);
                adapter7.notifyDataSetChanged();
            }
        }
    };


    private PictureConfig.OnSelectResultCallback resultCallback8 = new PictureConfig.OnSelectResultCallback() {
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


        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia8.add(media);
            if (selectMedia8 != null) {
                adapter8.setList(selectMedia8);
                adapter8.notifyDataSetChanged();
            }
        }
    };


    private PictureConfig.OnSelectResultCallback resultCallback9 = new PictureConfig.OnSelectResultCallback() {
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

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia9.add(media);
            if (selectMedia9 != null) {
                adapter9.setList(selectMedia9);
                adapter9.notifyDataSetChanged();
            }
        }
    };


    private PictureConfig.OnSelectResultCallback resultCallback10 = new PictureConfig.OnSelectResultCallback() {
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

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia10.add(media);
            if (selectMedia10 != null) {
                adapter10.setList(selectMedia10);
                adapter10.notifyDataSetChanged();
            }
        }
    };


    private PictureConfig.OnSelectResultCallback resultCallback11 = new PictureConfig.OnSelectResultCallback() {
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

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia11.add(media);
            if (selectMedia11 != null) {
                adapter11.setList(selectMedia11);
                adapter11.notifyDataSetChanged();
            }
        }
    };

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

    @OnClick(R.id.tv_choose_office)
    public void onViewClicked() {
        LayoutInflater inflater = LayoutInflater.from(this);//将xml布局转换为view
        View view = inflater.inflate(R.layout.dialog_bank, null);//将xml布局转换为view,里面有listview
        final ListView listView = (ListView) view.findViewById(R.id.pop_bank);

        final Dialog builder = new Dialog(UpdateDiyaPicActivity.this);
        builder.setContentView(view);
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
                            adapter = new OfficeAdappter(UpdateDiyaPicActivity.this, list, new OfficeAdappter.OnRadiobuttonclick() {
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
    }
}




