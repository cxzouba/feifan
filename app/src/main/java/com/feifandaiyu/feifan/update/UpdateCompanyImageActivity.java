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

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.CompanyPicarrAdapter;
import com.feifandaiyu.feifan.adapter.GridImageAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CompanyImageUploadActivityBean;
import com.feifandaiyu.feifan.bean.UpdateCompanyPicBean;
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

import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * Created by davidzhao on 2017/5/15.
 */

public class UpdateCompanyImageActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.card_three)
    RecyclerView cardThree;
    @InjectView(R.id.yanzi)
    RecyclerView yanzi;
    @InjectView(R.id.lawer_person_card)
    RecyclerView lawerPersonCard;
    @InjectView(R.id.face_check)
    RecyclerView faceCheck;
    @InjectView(R.id.company_bank)
    RecyclerView companyBank;
    @InjectView(R.id.lawer_credit)
    RecyclerView lawerCredit;
    @InjectView(R.id.company_money)
    RecyclerView companyMoney;
    @InjectView(R.id.otner_money)
    RecyclerView otnerMoney;
    @InjectView(R.id.second_card)
    RecyclerView secondCard;
    @InjectView(R.id.car_up)
    RecyclerView carUp;
    @InjectView(R.id.car_run)
    RecyclerView carRun;
    @InjectView(R.id.rv_card_three)
    RecyclerView rvCardThree;
    @InjectView(R.id.rv_yanzi)
    RecyclerView rvYanzi;
    @InjectView(R.id.rv_lawer_person_card)
    RecyclerView rvLawerPersonCard;
    @InjectView(R.id.rv_face_check)
    RecyclerView rvFaceCheck;
    @InjectView(R.id.rv_company_bank)
    RecyclerView rvCompanyBank;
    @InjectView(R.id.rv_lawer_credit)
    RecyclerView rvLawerCredit;
    @InjectView(R.id.rv_company_money)
    RecyclerView rvCompanyMoney;
    @InjectView(R.id.rv_other_money)
    RecyclerView rvOtherMoney;
    @InjectView(R.id.rv_second_card)
    RecyclerView rvSecondCard;
    @InjectView(R.id.rv_car_up)
    RecyclerView rvCarUp;
    @InjectView(R.id.rv_car_run)
    RecyclerView rvCarRun;
    @InjectView(R.id.rv_jiafang)
    RecyclerView rvJiafang;
    @InjectView(R.id.rv_jingyingdi)
    RecyclerView rvJingyingdi;
    private RecyclerView mComvispic;
    private RecyclerView mSalePic;
    private GridImageAdapter adapter12;
    private GridImageAdapter adapter11;
    private GridImageAdapter adapter10;
    private GridImageAdapter adapter9;
    private GridImageAdapter adapter8;
    private GridImageAdapter adapter7;
    private GridImageAdapter adapter6;
    private GridImageAdapter adapter5;
    private GridImageAdapter adapter4;
    private GridImageAdapter adapter3;
    private GridImageAdapter adapter2;
    private GridImageAdapter adapter1;
    private GridImageAdapter adapter;
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
    private List<String> filePaths = new ArrayList<>();
    private ImageView iv_back;
    private TextView tv_next;
    private JSONArray picarr;
    private JSONArray reportPic;
    private JSONArray cardPic;
    private JSONArray recordingPic;
    private JSONArray bankflowPic;
    private JSONArray financialPic;
    private JSONArray propertyPic;
    private JSONArray usedcarPic;
    private JSONArray vehiclePic;
    private JSONArray vehicleLicense;
    private JSONArray investigationPic;
    private JSONArray creditReporting;
    private JSONArray visitsPic;

    private JSONArray businessPic;
    private UpCompletionHandler handler;
    private UploadOptions uploadOptions;
    private int upLoadCount = 0;
    private int piccount;
    private ProgressDialog progressDialog;
    private String userId;
    private UpdateCompanyPicBean updateCompanyPicBean;

    @Override
    protected int getContentView() {
        return R.layout.update_company_image;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("图片上传");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
        tv_next.setText("保存");

        userId = PreferenceUtils.getString(this, "userId");

        tv_next.setEnabled(true);
        initRecyclerView();

//        String isOldCar = PreferenceUtils.getString(this, "isOldCar");
//
//        if (isOldCar.equals("1")) {
//            secondCard.setEnabled(false);
//        }

        initData();

    }

    private void initData() {

        post()
                .url(Constants.URLS.BASEURL + "UserInfo/companyPic")
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("updateCompanyPicShow----------------->>>>>>." + e);
//                        hud.dismiss();
                        MyToast.show(UpdateCompanyImageActivity.this, "联网失败...");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("updateCompanyPicShow----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();

                        updateCompanyPicBean = gson.fromJson(json, UpdateCompanyPicBean.class);
//
//                        hud.dismiss();
//
                        if (updateCompanyPicBean.getCode() == 1) {

                            LinearLayoutManager ms1 = new LinearLayoutManager(UpdateCompanyImageActivity.this);
                            ms1.setOrientation(LinearLayoutManager.HORIZONTAL);
                            LinearLayoutManager ms2 = new LinearLayoutManager(UpdateCompanyImageActivity.this);
                            ms2.setOrientation(LinearLayoutManager.HORIZONTAL);
                            LinearLayoutManager ms3 = new LinearLayoutManager(UpdateCompanyImageActivity.this);
                            ms3.setOrientation(LinearLayoutManager.HORIZONTAL);
                            LinearLayoutManager ms4 = new LinearLayoutManager(UpdateCompanyImageActivity.this);
                            ms4.setOrientation(LinearLayoutManager.HORIZONTAL);
                            LinearLayoutManager ms5 = new LinearLayoutManager(UpdateCompanyImageActivity.this);
                            ms5.setOrientation(LinearLayoutManager.HORIZONTAL);
                            LinearLayoutManager ms6 = new LinearLayoutManager(UpdateCompanyImageActivity.this);
                            ms6.setOrientation(LinearLayoutManager.HORIZONTAL);
                            LinearLayoutManager ms7 = new LinearLayoutManager(UpdateCompanyImageActivity.this);
                            ms7.setOrientation(LinearLayoutManager.HORIZONTAL);
                            LinearLayoutManager ms8 = new LinearLayoutManager(UpdateCompanyImageActivity.this);
                            ms8.setOrientation(LinearLayoutManager.HORIZONTAL);
                            LinearLayoutManager ms9 = new LinearLayoutManager(UpdateCompanyImageActivity.this);
                            ms9.setOrientation(LinearLayoutManager.HORIZONTAL);
                            LinearLayoutManager ms10 = new LinearLayoutManager(UpdateCompanyImageActivity.this);
                            ms10.setOrientation(LinearLayoutManager.HORIZONTAL);
                            LinearLayoutManager ms11 = new LinearLayoutManager(UpdateCompanyImageActivity.this);
                            ms11.setOrientation(LinearLayoutManager.HORIZONTAL);
                            LinearLayoutManager ms12 = new LinearLayoutManager(UpdateCompanyImageActivity.this);
                            ms12.setOrientation(LinearLayoutManager.HORIZONTAL);
                            LinearLayoutManager ms13 = new LinearLayoutManager(UpdateCompanyImageActivity.this);
                            ms13.setOrientation(LinearLayoutManager.HORIZONTAL);

                            rvCardThree.setLayoutManager(ms1);
                            rvCardThree.setAdapter(new CompanyPicarrAdapter(UpdateCompanyImageActivity.this, updateCompanyPicBean.getShow().getPicarr(), updateCompanyPicBean.getList().getPicarr()));

                            rvYanzi.setLayoutManager(ms2);
                            rvYanzi.setAdapter(new CompanyPicarrAdapter(UpdateCompanyImageActivity.this, updateCompanyPicBean.getShow().getReportPic(), updateCompanyPicBean.getList().getReportPic()));

                            rvLawerPersonCard.setLayoutManager(ms3);
                            rvLawerPersonCard.setAdapter(new CompanyPicarrAdapter(UpdateCompanyImageActivity.this, updateCompanyPicBean.getShow().getCardPic(), updateCompanyPicBean.getList().getCardPic()));

                            rvFaceCheck.setLayoutManager(ms4);
                            rvFaceCheck.setAdapter(new CompanyPicarrAdapter(UpdateCompanyImageActivity.this, updateCompanyPicBean.getShow().getRecordingPic(), updateCompanyPicBean.getList().getRecordingPic()));

                            rvCompanyBank.setLayoutManager(ms5);
                            rvCompanyBank.setAdapter(new CompanyPicarrAdapter(UpdateCompanyImageActivity.this, updateCompanyPicBean.getShow().getBankflowPic(), updateCompanyPicBean.getList().getBankflowPic()));

                            rvOtherMoney.setLayoutManager(ms6);
                            rvOtherMoney.setAdapter(new CompanyPicarrAdapter(UpdateCompanyImageActivity.this, updateCompanyPicBean.getShow().getPropertyPic(), updateCompanyPicBean.getList().getPropertyPic()));

                            rvLawerCredit.setLayoutManager(ms7);
                            rvLawerCredit.setAdapter(new CompanyPicarrAdapter(UpdateCompanyImageActivity.this, updateCompanyPicBean.getShow().getCreditReporting(), updateCompanyPicBean.getList().getCreditReporting()));

                            rvJiafang.setLayoutManager(ms8);
                            rvJiafang.setAdapter(new CompanyPicarrAdapter(UpdateCompanyImageActivity.this, updateCompanyPicBean.getShow().getVisitsPic(), updateCompanyPicBean.getList().getVisitsPic()));

                            rvJingyingdi.setLayoutManager(ms9);
                            rvJingyingdi.setAdapter(new CompanyPicarrAdapter(UpdateCompanyImageActivity.this, updateCompanyPicBean.getShow().getBusinessPic(), updateCompanyPicBean.getList().getBusinessPic()));

                            rvSecondCard.setLayoutManager(ms10);
                            rvSecondCard.setAdapter(new CompanyPicarrAdapter(UpdateCompanyImageActivity.this, updateCompanyPicBean.getShow().getUsedcarPic(), updateCompanyPicBean.getList().getUsedcarPic()));

                            rvCarRun.setLayoutManager(ms11);
                            rvCarRun.setAdapter(new CompanyPicarrAdapter(UpdateCompanyImageActivity.this, updateCompanyPicBean.getShow().getVehicleLicense(), updateCompanyPicBean.getList().getVehicleLicense()));

                            rvCompanyMoney.setLayoutManager(ms12);
                            rvCompanyMoney.setAdapter(new CompanyPicarrAdapter(UpdateCompanyImageActivity.this, updateCompanyPicBean.getShow().getFinancialPic(), updateCompanyPicBean.getList().getFinancialPic()));

                            rvCarUp.setLayoutManager(ms13);
                            rvCarUp.setAdapter(new CompanyPicarrAdapter(UpdateCompanyImageActivity.this, updateCompanyPicBean.getShow().getVehiclePic(), updateCompanyPicBean.getList().getVehiclePic()));

                        } else if (updateCompanyPicBean.getCode() == 0) {
                            new AlertDialog.Builder(UpdateCompanyImageActivity.this)

                                    .setMessage(updateCompanyPicBean.getMsg())
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

    private void initRecyclerView() {
        mComvispic = (RecyclerView) findViewById(R.id.rv_comvispic);
        mSalePic = (RecyclerView) findViewById(R.id.rv_salepic);
        LinearLayoutManager ms = new LinearLayoutManager(this);
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
        LinearLayoutManager ms12 = new LinearLayoutManager(this);

        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
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
        ms12.setOrientation(LinearLayoutManager.HORIZONTAL);

        cardThree.setLayoutManager(ms);
        yanzi.setLayoutManager(ms1);
        faceCheck.setLayoutManager(ms2);
        companyBank.setLayoutManager(ms3);
        lawerCredit.setLayoutManager(ms4);
        lawerPersonCard.setLayoutManager(ms5);
        companyMoney.setLayoutManager(ms6);
        otnerMoney.setLayoutManager(ms7);
        secondCard.setLayoutManager(ms8);
        carUp.setLayoutManager(ms9);
        carRun.setLayoutManager(ms10);
        mComvispic.setLayoutManager(ms11);
        mSalePic.setLayoutManager(ms12);


        adapter = new GridImageAdapter(UpdateCompanyImageActivity.this, onAddPicClickListener);
        adapter.setList(selectMedia);
        adapter.setSelectMax(maxSelectNum);

        adapter1 = new GridImageAdapter(UpdateCompanyImageActivity.this, onAddPicClickListener1);
        adapter1.setList(selectMedia1);
        adapter1.setSelectMax(maxSelectNum);

        adapter2 = new GridImageAdapter(UpdateCompanyImageActivity.this, onAddPicClickListener2);
        adapter2.setList(selectMedia2);
        adapter2.setSelectMax(maxSelectNum);

        adapter3 = new GridImageAdapter(UpdateCompanyImageActivity.this, onAddPicClickListener3);
        adapter3.setList(selectMedia3);
        adapter3.setSelectMax(maxSelectNum);

        adapter4 = new GridImageAdapter(UpdateCompanyImageActivity.this, onAddPicClickListener4);
        adapter4.setList(selectMedia4);
        adapter4.setSelectMax(maxSelectNum);

        adapter5 = new GridImageAdapter(UpdateCompanyImageActivity.this, onAddPicClickListener5);
        adapter5.setList(selectMedia5);
        adapter5.setSelectMax(maxSelectNum);

        adapter6 = new GridImageAdapter(UpdateCompanyImageActivity.this, onAddPicClickListener6);
        adapter6.setList(selectMedia6);
        adapter6.setSelectMax(maxSelectNum);

        adapter7 = new GridImageAdapter(UpdateCompanyImageActivity.this, onAddPicClickListener7);
        adapter7.setList(selectMedia7);
        adapter7.setSelectMax(maxSelectNum);

        adapter8 = new GridImageAdapter(UpdateCompanyImageActivity.this, onAddPicClickListener8);
        adapter8.setList(selectMedia8);
        adapter8.setSelectMax(maxSelectNum);

        adapter9 = new GridImageAdapter(UpdateCompanyImageActivity.this, onAddPicClickListener9);
        adapter9.setList(selectMedia9);
        adapter9.setSelectMax(maxSelectNum);


        adapter10 = new GridImageAdapter(UpdateCompanyImageActivity.this, onAddPicClickListener10);
        adapter10.setList(selectMedia10);
        adapter10.setSelectMax(maxSelectNum);

        adapter11 = new GridImageAdapter(UpdateCompanyImageActivity.this, onAddPicClickListener11);
        adapter11.setList(selectMedia11);
        adapter11.setSelectMax(maxSelectNum);

        adapter12 = new GridImageAdapter(UpdateCompanyImageActivity.this, onAddPicClickListener12);
        adapter12.setList(selectMedia12);
        adapter12.setSelectMax(maxSelectNum);

        cardThree.setAdapter(adapter);
        yanzi.setAdapter(adapter1);
        lawerPersonCard.setAdapter(adapter2);
        faceCheck.setAdapter(adapter3);
        companyBank.setAdapter(adapter4);
        lawerCredit.setAdapter(adapter5);
        companyMoney.setAdapter(adapter6);
        otnerMoney.setAdapter(adapter7);
        secondCard.setAdapter(adapter8);
        carUp.setAdapter(adapter9);
        carRun.setAdapter(adapter10);
        mComvispic.setAdapter(adapter11);
        mSalePic.setAdapter(adapter12);
      /*  moneyUHave.setAdapter(adapter);
        faceCheck.setAdapter(adapter);
        companyBank.setAdapter(adapter);
        lawerCredit.setAdapter(adapter);
        lawerPersonCard.setAdapter(adapter);*/
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        PictureConfig.getInstance().externalPicturePreview(UpdateCompanyImageActivity.this, position - 1, selectMedia);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateCompanyImageActivity.this, selectMedia.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });

        adapter1.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        PictureConfig.getInstance().externalPicturePreview(UpdateCompanyImageActivity.this, position - 1, selectMedia1);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateCompanyImageActivity.this, selectMedia.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateCompanyImageActivity.this, position - 1, selectMedia2);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateCompanyImageActivity.this, selectMedia2.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateCompanyImageActivity.this, position - 1, selectMedia3);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateCompanyImageActivity.this, selectMedia3.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateCompanyImageActivity.this, position - 1, selectMedia4);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateCompanyImageActivity.this, selectMedia4.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateCompanyImageActivity.this, position - 1, selectMedia5);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateCompanyImageActivity.this, selectMedia5.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateCompanyImageActivity.this, position - 1, selectMedia6);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateCompanyImageActivity.this, selectMedia6.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateCompanyImageActivity.this, position - 1, selectMedia7);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateCompanyImageActivity.this, selectMedia7.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateCompanyImageActivity.this, position - 1, selectMedia8);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateCompanyImageActivity.this, selectMedia8.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateCompanyImageActivity.this, position - 1, selectMedia9);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateCompanyImageActivity.this, selectMedia9.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateCompanyImageActivity.this, position - 1, selectMedia10);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateCompanyImageActivity.this, selectMedia10.get(position - 1).getPath());
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
                        PictureConfig.getInstance().externalPicturePreview(UpdateCompanyImageActivity.this, position - 1, selectMedia11);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateCompanyImageActivity.this, selectMedia11.get(position - 1).getPath());
                        }
                        break;
                }

            }
        });

        adapter12.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (selectType) {
                    case FunctionConfig.TYPE_IMAGE:
                        // 预览图片 可长按保存 也可自定义保存路径
                        PictureConfig.getInstance().externalPicturePreview(UpdateCompanyImageActivity.this, position - 1, selectMedia12);
                        break;
                    case FunctionConfig.TYPE_VIDEO:
                        // 预览视频
                        if (selectMedia.size() > 0) {
                            PictureConfig.getInstance().externalPictureVideo(UpdateCompanyImageActivity.this, selectMedia12.get(position - 1).getPath());
                        }
                        break;
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

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    themeStyle = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateCompanyImageActivity.this, resultCallback);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateCompanyImageActivity.this, resultCallback);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                new AlertDialog.Builder(this)

                        .setMessage("是否取消修改？")
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


                postData();

                break;
        }
    }

    private void postData() {

        List<List<LocalMedia>> medias = new ArrayList<>();

        piccount = selectMedia.size() + selectMedia1.size() + selectMedia2.size() +
                selectMedia3.size() + selectMedia4.size() + selectMedia5.size() +
                selectMedia6.size() + selectMedia7.size() + selectMedia8.size() + selectMedia9.size()
                + selectMedia10.size() + selectMedia11.size() + selectMedia12.size();

        System.out.println("======" + piccount);

        if (piccount == 0) {
            post2();
        } else {
            medias.add(selectMedia);
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
            medias.add(selectMedia12);
            for (int i = 0; i < medias.size(); i++) {
                List<LocalMedia> localMedias = medias.get(i);
                for (int j = 0; j < localMedias.size(); j++) {
                    String compressPath = localMedias.get(j).getCompressPath();

                    final int finalI = i;
                    final int finalJ = j;
                    String key = UUID.randomUUID() + TimeUtils.getRandomFileName() + ".png";
                    if (finalI == 0) {
                        if (picarr == null) {//   营业执照或三证合一图片
                            picarr = new JSONArray();
                        }
                        try {
                            picarr.put(finalJ, key);
                            updateCompanyPicBean.getList().getPicarr().add(key);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (finalI == 1) {
                        if (reportPic == null) {
                            reportPic = new JSONArray();//验资报告
                        }
                        try {
                            reportPic.put(finalJ, key);
                            updateCompanyPicBean.getList().getReportPic().add(key);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (finalI == 2) {
                        if (cardPic == null) {//法人身份证
                            cardPic = new JSONArray();
                        }
                        try {
                            cardPic.put(finalJ, key);
                            updateCompanyPicBean.getList().getCardPic().add(key);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (finalI == 3) {
                        if (recordingPic == null) {//面审表或电话核查记录
                            recordingPic = new JSONArray();
                        }
                        try {
                            recordingPic.put(finalJ, key);
                            updateCompanyPicBean.getList().getRecordingPic().add(key);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (finalI == 4) {//企业银行流水
                        if (bankflowPic == null) {
                            bankflowPic = new JSONArray();
                        }

                        try {
                            bankflowPic.put(finalJ, key);
                            updateCompanyPicBean.getList().getBankflowPic().add(key);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    } else if (finalI == 5) {

                        if (creditReporting == null) {//法人 征信报告
                            creditReporting = new JSONArray();
                        }

                        try {
                            creditReporting.put(finalJ, key);
                            updateCompanyPicBean.getList().getCreditReporting().add(key);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    } else if (finalI == 6) {

                        if (financialPic == null) {//企业财务报表
                            financialPic = new JSONArray();
                        }

                        try {
                            financialPic.put(finalJ, key);
                            updateCompanyPicBean.getList().getFinancialPic().add(key);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    } else if (finalI == 7) {// 其他财产证明

                        if (propertyPic == null) {
                            propertyPic = new JSONArray();
                        }

                        try {
                            propertyPic.put(finalJ, key);
                            updateCompanyPicBean.getList().getPropertyPic().add(key);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    } else if (finalI == 8) {//二手车评估报告

                        if (usedcarPic == null) {
                            usedcarPic = new JSONArray();
                        }

                        try {
                            usedcarPic.put(finalJ, key);
                            updateCompanyPicBean.getList().getUsedcarPic().add(key);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    } else if (finalI == 9) {//车辆登记证

                        if (vehiclePic == null) {
                            vehiclePic = new JSONArray();
                        }

                        try {
                            vehiclePic.put(finalJ, key);
                            updateCompanyPicBean.getList().getVehiclePic().add(key);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    } else if (finalI == 10) {//车辆行驶证

                        if (vehicleLicense == null) {
                            vehicleLicense = new JSONArray();
                        }

                        try {
                            vehicleLicense.put(finalJ, key);
                            updateCompanyPicBean.getList().getVehicleLicense().add(key);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    } else if (finalI == 11) {
                        if (visitsPic == null) {
                            visitsPic = new JSONArray();
                            LogUtils.d("--------------->>>" + visitsPic.toString());
                        }

                        try {
                            visitsPic.put(finalJ, key);
                            updateCompanyPicBean.getList().getVisitsPic().add(key);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    } else if (finalI == 12) {
                        if (businessPic == null) {
                            businessPic = new JSONArray();
                            LogUtils.d("--------------->>>" + businessPic.toString());
                        }

                        try {
                            businessPic.put(finalJ, key);
                            updateCompanyPicBean.getList().getBusinessPic().add(key);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }


                    QiNiuUtlis.upLoad(compressPath, key, new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject response) {

                                    if (info.isOK()) {

                                        upLoadCount++;
                                        LogUtils.i("upLoadCount", "-------------------->" + upLoadCount);
                                        LogUtils.i("upLoadCount", "-------------------->" + piccount);

                                        System.out.println(upLoadCount == piccount);
                                        if (upLoadCount == piccount) {
                                            post2();
                                        }
                                    }

                                }
                            }

                            , uploadOptions);

                    uploadOptions = new UploadOptions(null, null, false,
                            new UpProgressHandler() {
                                @Override
                                public void progress(String key, double percent) {
                                    LogUtils.i("duang " + "----------->>>" + percent);

                                }
                            }, null);


                }
            }
        }


    }

    private void post2() {
        showProgressDialog();

        Gson gson = new Gson();
        String getPicarr = gson.toJson(updateCompanyPicBean.getList().getPicarr());
        String getReportPic = gson.toJson(updateCompanyPicBean.getList().getReportPic());
        String getCardPic = gson.toJson(updateCompanyPicBean.getList().getCardPic());
        String getRecordingPic = gson.toJson(updateCompanyPicBean.getList().getRecordingPic());
        String getBankflowPic = gson.toJson(updateCompanyPicBean.getList().getBankflowPic());
        String getFinancialPic = gson.toJson(updateCompanyPicBean.getList().getFinancialPic());
        String getPropertyPic = gson.toJson(updateCompanyPicBean.getList().getPropertyPic());
        String getUsedcarPic = gson.toJson(updateCompanyPicBean.getList().getUsedcarPic());
        String getVehiclePic = gson.toJson(updateCompanyPicBean.getList().getVehiclePic());
        String getVehicleLicense = gson.toJson(updateCompanyPicBean.getList().getVehicleLicense());
        String getCreditReporting = gson.toJson(updateCompanyPicBean.getList().getCreditReporting());
        String getVisitsPic = gson.toJson(updateCompanyPicBean.getList().getVisitsPic());
        String getBusinessPic = gson.toJson(updateCompanyPicBean.getList().getBusinessPic());

        LogUtils.d("aa--------------->>>" + getPicarr.toString());
        String companyId = PreferenceUtils.getString(UpdateCompanyImageActivity.this, "userId");
        LogUtils.d("id--------------->>>" + companyId);
        post()
                .url(Constants.URLS.BASEURL + "Login/addCompanyPic")
                .addParams("companyId", companyId)
                .addParams("picarr", getPicarr.toString())
                .addParams("reportPic", getReportPic.toString())
                .addParams("cardPic", getCardPic.toString())
                .addParams("recordingPic", getRecordingPic.toString())
                .addParams("bankflowPic", getBankflowPic.toString())
                .addParams("financialPic", getFinancialPic.toString())
                .addParams("propertyPic", getPropertyPic.toString())
                .addParams("usedcarPic", getUsedcarPic == null ? "null" : getUsedcarPic.toString())
                .addParams("vehiclePic", getVehiclePic == null ? "null" : getVehiclePic.toString())
                .addParams("vehicleLicense", getVehicleLicense == null ? "null" : getVehicleLicense.toString())
                .addParams("creditReporting", getCreditReporting.toString())
                .addParams("visitsPic", getVisitsPic.toString())
                .addParams("businessPic", getBusinessPic.toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e("compangPic=========" + e);
                        MyToast.show(UpdateCompanyImageActivity.this, "联网失败");
                        progressDialog.dismiss();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("compangPic=========" + response);
                        String json = response;
                        Gson gson = new Gson();
                        CompanyImageUploadActivityBean bean = gson.fromJson(json, CompanyImageUploadActivityBean.class);

                        if (bean.getCode() == 1) {
//                                                                Intent intent = new Intent(UpdateCompanyImageActivity.this, UpLoadCompanyVideoActivity.class);
                            progressDialog.dismiss();
//                                                                startActivity(intent);

                            MyToast.show(UpdateCompanyImageActivity.this, "企业照片保存成功");

                            finish();
                            int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                            int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                            overridePendingTransition(enterAnim0, exitAnim0);
                        } else if (bean.getCode() == 0) {
                            MyToast.show(UpdateCompanyImageActivity.this, bean.getMsg());
                            progressDialog.dismiss();
                        }

                    }
                });
    }


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener1 = new GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    themeStyle = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateCompanyImageActivity.this, resultCallback1);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateCompanyImageActivity.this, resultCallback1);
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

    private PictureConfig.OnSelectResultCallback resultCallback1 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia1.addAll(resultList);
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

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener2 = new GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    themeStyle = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateCompanyImageActivity.this, resultCallback2);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateCompanyImageActivity.this, resultCallback2);
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

    private PictureConfig.OnSelectResultCallback resultCallback2 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia2.addAll(resultList);
            Log.i("callBack_result2", selectMedia2.size() + "");
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


    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener3 = new GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    themeStyle = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateCompanyImageActivity.this, resultCallback3);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateCompanyImageActivity.this, resultCallback3);
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
                    themeStyle = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateCompanyImageActivity.this, resultCallback4);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateCompanyImageActivity.this, resultCallback4);
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

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener6 = new GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    themeStyle = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateCompanyImageActivity.this, resultCallback6);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateCompanyImageActivity.this, resultCallback6);
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
                    themeStyle = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);

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
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateCompanyImageActivity.this, resultCallback7);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateCompanyImageActivity.this, resultCallback7);
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
                    themeStyle = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);

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

                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateCompanyImageActivity.this, resultCallback8);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateCompanyImageActivity.this, resultCallback8);
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

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener10 = new GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    themeStyle = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateCompanyImageActivity.this, resultCallback10);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateCompanyImageActivity.this, resultCallback10);
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
                    themeStyle = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateCompanyImageActivity.this, resultCallback11);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateCompanyImageActivity.this, resultCallback11);
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

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener9 = new GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    themeStyle = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);

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
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateCompanyImageActivity.this, resultCallback9);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateCompanyImageActivity.this, resultCallback9);
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

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener12 = new GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    themeStyle = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);

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

                            .setSelectMedia(selectMedia12) // 已选图片，传入在次进去可选中，不能传入网络图片
                            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                            .setThemeStyle(themeStyle) // 设置主题样式
                            .create();

                    if (mode) {
                        // 只拍照
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateCompanyImageActivity.this, resultCallback12);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateCompanyImageActivity.this, resultCallback12);
                    }
                    break;
                case 1:
                    // 删除图片
                    selectMedia12.remove(position);
                    adapter12.notifyItemRemoved(position + 1);
                    break;
            }


        }
    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener5 = new GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    themeStyle = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.blue);//设置主题样式
                    checkedBoxDrawable = R.drawable.select_cb;//设置图片勾选样式
                    previewColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);
                    completeColor = ContextCompat.getColor(UpdateCompanyImageActivity.this, R.color.tab_color_true);

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
                        PictureConfig.getInstance().init(options).startOpenCamera(UpdateCompanyImageActivity.this, resultCallback5);
                    } else {
                        // 先初始化参数配置，在启动相册
                        PictureConfig.getInstance().init(options).openPhoto(UpdateCompanyImageActivity.this, resultCallback5);
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

    private PictureConfig.OnSelectResultCallback resultCallback3 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia3.addAll(resultList);
            Log.i("callBack_result2", selectMedia3.size() + "");
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
            selectMedia3.add(media);
            if (selectMedia3 != null) {
                adapter3.setList(selectMedia3);
                adapter3.notifyDataSetChanged();
            }
        }
    };

    private PictureConfig.OnSelectResultCallback resultCallback11 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia11.addAll(resultList);
            Log.i("callBack_result2", selectMedia11.size() + "");
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

    private PictureConfig.OnSelectResultCallback resultCallback9 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia9.addAll(resultList);
            Log.i("callBack_result2", selectMedia9.size() + "");
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

    private PictureConfig.OnSelectResultCallback resultCallback4 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia4.addAll(resultList);
            Log.i("callBack_result2", selectMedia4.size() + "");
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
    private PictureConfig.OnSelectResultCallback resultCallback10 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia10.addAll(resultList);
            Log.i("callBack_result2", selectMedia10.size() + "");
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

    private PictureConfig.OnSelectResultCallback resultCallback5 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia5.addAll(resultList);
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


    private PictureConfig.OnSelectResultCallback resultCallback12 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia12.addAll(resultList);
            Log.i("callBack_result2", selectMedia12.size() + "");
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
            if (selectMedia12 != null) {
                adapter12.setList(selectMedia12);
                adapter12.notifyDataSetChanged();
            }
        }

        public void onSelectSuccess(LocalMedia media) {
            // 单选回调
            selectMedia12.add(media);
            if (selectMedia12 != null) {
                adapter12.setList(selectMedia12);
                adapter12.notifyDataSetChanged();
            }
        }
    };
    private PictureConfig.OnSelectResultCallback resultCallback7 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia7.addAll(resultList);
            Log.i("callBack_result2", selectMedia7.size() + "");
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

    private PictureConfig.OnSelectResultCallback resultCallback6 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia6.addAll(resultList);
            Log.i("callBack_result2", selectMedia6.size() + "");
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
    private PictureConfig.OnSelectResultCallback resultCallback8 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            // 多选回调
            selectMedia8.addAll(resultList);
            Log.i("callBack_result2", selectMedia8.size() + "");
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

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("正在上传");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}