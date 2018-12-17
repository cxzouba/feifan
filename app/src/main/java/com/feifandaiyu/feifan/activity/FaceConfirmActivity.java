package com.feifandaiyu.feifan.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.UploadCarIamgeBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.QiNiuUtlis;
import com.feifandaiyu.feifan.utils.TimeUtils;
import com.google.gson.Gson;
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
import butterknife.OnClick;
import okhttp3.Call;

public class FaceConfirmActivity extends BaseActivity {


    @InjectView(R.id.takephoto)
    ImageView takephoto;
    @InjectView(R.id.et_name)
    PowerfulEditText etName;
    @InjectView(R.id.ll_name)
    LinearLayout llName;
    @InjectView(R.id.et_certificate_num)
    PowerfulEditText etCertificateNum;
    private List<LocalMedia> selectMedia1 = new ArrayList<LocalMedia>();
    private String path1;
    private List<LocalMedia> selectMediaa = new ArrayList<LocalMedia>();
    private JSONArray certificate_up;
    private UploadOptions uploadOptions;
    private TextView tv_next;
    private int piccount;
    private int upLoadCount = 1;
    private ProgressDialog progressDialog;
    private String userId;



    @Override
    protected int getContentView() {
        return R.layout.activity_face_confirm;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("人脸识别");
        showBack(true);
        showNext(true);

        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setEnabled(true);

        tv_next.setText("提交");


    }

    @OnClick({R.id.takephoto, R.id.tv_next, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.takephoto:
                PictureConfig.getInstance().init(options).startOpenCamera(this, new ResultCa1((ImageView) view, selectMediaa, 0));
                break;
            case R.id.tv_next:

                if (etName.getText().toString().equals("")) {
                    MyToast.show(FaceConfirmActivity.this, "请填写姓名");
                    return;
                }
                if (etCertificateNum.getText().toString().equals("")) {
                    MyToast.show(FaceConfirmActivity.this, "请填写身份证号");
                    return;
                }

                updateToQiniu();
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
        }
    }

    private void updateToQiniu() {
        List<List<LocalMedia>> medias = new ArrayList<>();

        medias.add(selectMedia1);
        piccount = selectMedia1.size();
        LogUtils.e("piccount=" + piccount);

        if (piccount < 1) {
            MyToast.show(FaceConfirmActivity.this, "请上传面部照片");
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


                }

                QiNiuUtlis.upLoad(compressPath, key, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                LogUtils.i("1");
//                                if (info.isOK()) {
//                                    upLoadCount++;
//                                }
                                LogUtils.i("upLoadCount" + upLoadCount);

                                if (upLoadCount == piccount) {
                                    post2();
                                }
                            }
                        }
                );

            }
        }
    }

    private void post2() {
        LogUtils.d(certificate_up.toString());
        OkHttpUtils
                .post()
                //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                .url(Constants.URLS.BASEURL + "Appraiser/verifyface")
                .addParams("idcard", etCertificateNum.getText().toString())
                .addParams("image", certificate_up.toString())
                .addParams("realname", etName.getText().toString())
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
                            MyToast.show(FaceConfirmActivity.this, bean.getMsg());
                        } else {
                            MyToast.show(FaceConfirmActivity.this, bean.getMsg());
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
                Glide.with(FaceConfirmActivity.this)
                        .load(path1)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                // 原图地址

                Glide.with(FaceConfirmActivity.this)
                        .load(path1)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);

            }
        }


    }

}
