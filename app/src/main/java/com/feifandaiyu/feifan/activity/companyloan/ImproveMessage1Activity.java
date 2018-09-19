package com.feifandaiyu.feifan.activity.companyloan;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.MsgBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.ocr.CameraActivity;
import com.feifandaiyu.feifan.utils.FileUtil;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.NumberUtils;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.feifandaiyu.feifan.R.id.tv_next;

/**
 *
 * @author houdaichang
 * @date 2017/5/9
 */

public class ImproveMessage1Activity extends BaseActivity implements TextWatcher {

    private static final int REQUEST_CODE_CAMERA = 102;
    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    @InjectView(R.id.et_legal_name)
    PowerfulEditText etLegalName;
    @InjectView(R.id.et_certificate_num)
    PowerfulEditText etCertificateNum;
    @InjectView(R.id.et_legal_phone_num)
    PowerfulEditText etLegalPhoneNum;
    @InjectView(R.id.rb_new_car)
    RadioButton rbNewCar;
    @InjectView(R.id.rb_old_car)
    RadioButton rbOldCar;
    @InjectView(R.id.rg_isnew)
    RadioGroup rgIsnew;
    @InjectView(R.id.cemera1)
    ImageView cemera1;
    private TextView tvNext;
    private KProgressHUD hud;
    private boolean hasGotToken = false;
    private AlertDialog.Builder alertDialog;
    private boolean isOk = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_improve_message1;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

        setTitle("企业建档");
        showBack(true);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        alertDialog = new AlertDialog.Builder(this);

        initAccessTokenWithAkSk();

        tvNext = (TextView) findViewById(tv_next);


        etCertificateNum.addTextChangedListener(this);
        etLegalName.addTextChangedListener(this);
        etLegalPhoneNum.addTextChangedListener(this);

        rgIsnew.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (rbNewCar.getId() == checkedId) {
                    PreferenceUtils.setString(ImproveMessage1Activity.this, "cartypeim", "0");
                } else if (rbOldCar.getId() == checkedId) {
                    PreferenceUtils.setString(ImproveMessage1Activity.this, "cartypeim", "1");
                }
            }
        });


    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {

            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hud.dismiss();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                hud.dismiss();
                alertText("AK，SK方式获取token失败", error.getMessage());
            }
        }, getApplicationContext(), "T8tx2AW8ZGGKUWNZitHsXRuc", "C8SR9G6ucTDLge1q0c6Be4HgGlYoQR6y");
    }

    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE_FRONT && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String filePath = getRealPathFromURI(uri);
            recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
        }

//        if (requestCode == REQUEST_CODE_PICK_IMAGE_BACK && resultCode == Activity.RESULT_OK) {
//            Uri uri = data.getData();
//            String filePath = getRealPathFromURI(uri);
//            recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
//        }

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
//                    alertText("", result.toString());
//                    infoTextView.setText(result.toString());
//                    String json = response;
//                    Gson gson = new Gson();
//                    CreditActivityBean bean = gson.fromJson(json, CreditActivityBean.class);
                    hud.dismiss();
                    etLegalName.setText(result.getName().toString());
                    etCertificateNum.setText(result.getIdNumber().toString());

                }
            }

            @Override
            public void onError(OCRError error) {
                alertText("", error.getMessage());
            }
        });
    }


    @OnClick({R.id.et_legal_name, R.id.et_certificate_num, R.id.et_legal_phone_num, R.id.iv_back, R.id.tv_next, R.id.cemera1})
    public void onViewClicked(View view) {
        switch (view.getId()) {

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

            case R.id.cemera1:

                Intent intent = new Intent(ImproveMessage1Activity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);

                break;

            case tv_next:

                if (rbNewCar.isChecked() || rbOldCar.isChecked()) {
                    String isOldCar = rbOldCar.isChecked() ? "1" : "0";
                    PreferenceUtils.setString(ImproveMessage1Activity.this, "isOldCar", isOldCar);

                    String isEmpy = etLegalName.getText().toString().trim();
                    if (isEmpy.equals("") || isEmpy == null) {
//                    hud.dismiss();
                        MyToast.show(this, "当前有未填写内容，请完善");
                        return;
                    }

                    if (!NumberUtils.isMobile(etLegalPhoneNum.getText().toString().trim())) {
//                    hud.dismiss();

                        MyToast.show(this, "手机号不是11位，请检查");
                        return;
                    }


                    if (isOk) {
                        postNext();
                    } else {

                        hud.show();

                        confirm();
                    }

                } else {
                    MyToast.show(this, "请选择购车类型");
                }


                break;

            default:
        }
    }

    private void confirm() {

        String userName = etLegalName.getText().toString().trim();
        String cardNum = etCertificateNum.getText().toString().trim();

        OkHttpUtils.post()
                .url(Constants.URLS.BASEURL + "Appraiser/VerifyIdcardv")
                .addParams("userName", userName)
                .addParams("cardNum", cardNum)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("confirm----------------->>>>>>." + e);
                        MyToast.show(ImproveMessage1Activity.this, "服务器正忙，请稍后再试。。。");
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("confirm----------------->>>>>>." + response);
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        MsgBean MsgBean = gson.fromJson(json, MsgBean.class);
                        if (MsgBean.getCode() == 1) {
                            isOk = true;
                            etCertificateNum.setEnabled(false);
                            etCertificateNum.setTextColor(Color.GRAY);
                            etLegalName.setEnabled(false);
                            etLegalName.setTextColor(Color.GRAY);

                            new AlertDialog.Builder(ImproveMessage1Activity.this)
                                    .setMessage(MsgBean.getMsg() + "，是否保存用户信息？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            hud.show();
                                            postNext();
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        } else {
                            isOk = false;
                            new AlertDialog.Builder(ImproveMessage1Activity.this)
                                    .setMessage(MsgBean.getMsg())
                                    .setPositiveButton("知道了", null)
                                    .show();
                        }

                    }
                });


    }



    private void postNext() {
        PreferenceUtils.setString(ImproveMessage1Activity.this, "LegalName", etLegalName.getText().toString().trim());
        PreferenceUtils.setString(ImproveMessage1Activity.this, "CertificateNum", etCertificateNum.getText().toString().trim());
        PreferenceUtils.setString(ImproveMessage1Activity.this, "LegalPhoneNum", etLegalPhoneNum.getText().toString().trim());
        PreferenceUtils.setString(ImproveMessage1Activity.this, "CompanyCartype", rbNewCar.isChecked() ? "0" : "1");

        startActivity(new Intent(ImproveMessage1Activity.this, ImproveMessage2Activity.class));
        finish();
        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
        overridePendingTransition(enterAnim6, exitAnim6);
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!StringUtils.isEmpty(etLegalPhoneNum.getText().toString())
                && !StringUtils.isEmpty(etLegalName.getText().toString())
                && !StringUtils.isEmpty(etCertificateNum.getText().toString())
                )

        {
            tvNext.setEnabled(true);
        } else {
            tvNext.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
