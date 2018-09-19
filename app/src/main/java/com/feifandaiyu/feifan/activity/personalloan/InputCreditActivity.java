package com.feifandaiyu.feifan.activity.personalloan;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.carloan.CarEvaluateActivity2;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CreditActivityBean;
import com.feifandaiyu.feifan.bean.MsgBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.ocr.CameraActivity;
import com.feifandaiyu.feifan.popupWindow.BottomPopupOption;
import com.feifandaiyu.feifan.utils.AssetsUtils;
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
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.AddressPicker;
import okhttp3.Call;

public class InputCreditActivity extends BaseActivity implements TextWatcher {

    @InjectView(R.id.et_customer_name)
    PowerfulEditText etCustomerName;
    @InjectView(R.id.tv_certificate_type)
    TextView tvCertificateType;
    @InjectView(R.id.et_certificate_num)
    PowerfulEditText etCertificateNum;
    @InjectView(R.id.et_phone_num)
    PowerfulEditText etPhoneNum;
    @InjectView(R.id.ll_about_us)
    LinearLayout llAboutUs;
    @InjectView(R.id.imageView7)
    ImageView imageView7;
    @InjectView(R.id.tv_home)
    TextView tvHome;
    @InjectView(R.id.ll_home)
    LinearLayout llHome;
    @InjectView(R.id.et_full_addr)
    EditText etFullAddr;
    @InjectView(R.id.ll_car_addr)
    LinearLayout llCarAddr;
    @InjectView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @InjectView(R.id.bt_start_visit)
    Button btStartVisit;
    @InjectView(R.id.rb_own)
    RadioButton rbOwn;
    @InjectView(R.id.rb_other)
    RadioButton rbOther;
    @InjectView(R.id.rb_two)
    RadioButton rbTwo;
    @InjectView(R.id.rb_base)
    RadioButton rbBase;
    @InjectView(R.id.cemera1)
    ImageView cemera;
    @InjectView(R.id.et_shenfenzheng_addr)
    PowerfulEditText etShenfenzhengAddr;
    @InjectView(R.id.fangan)
    LinearLayout fangan;
    private BottomPopupOption bottomPopupOption;
    private String province;
    private String city;
    private String district;
    private KProgressHUD hud;
    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    private static final int REQUEST_CODE_CAMERA = 102;
    private AlertDialog.Builder alertDialog;
    private boolean hasGotToken = false;
    private boolean isOk = false;
    private String type;
    private String carloan;

//    private GeoCoder geoCoder;
//    private Geocoder gc;
//    private String fullAddr;
//    private double latitude;
//    private double longitude;

    @Override
    protected int getContentView() {
        return R.layout.activity_input_credit;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

//        gc = new Geocoder(InputCreditActivity.this, Locale.CHINA);

//        geoCoder = GeoCoder.newInstance();

        setTitle("客户建档（个人）");
        showNext(false);

        type = PreferenceUtils.getString(this, "lala");

        if (type.equals("2")) {
            fangan.setVisibility(View.GONE);
            carloan = "2";
            setTitle("客户建档（车抵）");
        } else {
            carloan = "1";
            setTitle("客户建档（个人）");
        }

//        initAccessTokenWithAkSk();

        alertDialog = new AlertDialog.Builder(this);

        hud = KProgressHUD.create(InputCreditActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

//        rbOwn.setChecked(true);
//        rbTwo.setChecked(true);

        etCustomerName.addTextChangedListener(this);
        etCertificateNum.addTextChangedListener(this);
        etPhoneNum.addTextChangedListener(this);
        etFullAddr.addTextChangedListener(this);
        tvHome.addTextChangedListener(this);
        tvCertificateType.addTextChangedListener(this);
        etShenfenzhengAddr.addTextChangedListener(this);
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


    @OnClick({R.id.iv_back, R.id.bt_start_visit, R.id.tv_home, R.id.cemera1})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.cemera1:
                LogUtils.e("pai");
                Intent intent = new Intent(InputCreditActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
                break;

            case R.id.tv_home:

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                showSelectHomeDialog();
                break;

            case R.id.bt_start_visit:

                if (type.equals("1")) {
                    if ((rbOwn.isChecked() || rbOther.isChecked()) && (rbTwo.isChecked() || rbBase.isChecked())) {

                        hud.show();

                        if (isOk) {

                            postData();

                        } else {

                            confirm();

                        }


                    } else {
                        MyToast.show(this, "您有未选择项");
                    }
                } else if (type.equals("2")) {
                    if ((rbOwn.isChecked() || rbOther.isChecked())) {

                        hud.show();

                        confirm();

                    } else {
                        MyToast.show(this, "您有未选择项");
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

    private void confirm() {

        String userName = etCustomerName.getText().toString().trim();
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
                        MyToast.show(InputCreditActivity.this, "服务器正忙，请稍后再试。。。");
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
                            etCustomerName.setEnabled(false);
                            etCustomerName.setTextColor(Color.GRAY);
                            etShenfenzhengAddr.setEnabled(false);
                            etShenfenzhengAddr.setTextColor(Color.GRAY);
                            etCertificateNum.setEnabled(false);
                            etCertificateNum.setTextColor(Color.GRAY);
                            new AlertDialog.Builder(InputCreditActivity.this)
                                    .setMessage(MsgBean.getMsg() + "，是否保存用户信息？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            hud.show();
                                            postData();
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        } else {
                            isOk = false;
                            new AlertDialog.Builder(InputCreditActivity.this)
                                    .setMessage(MsgBean.getMsg())
                                    .setPositiveButton("知道了", null)
                                    .show();
                        }

                    }
                });

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
                    etCustomerName.setText(result.getName().toString());
                    etCertificateNum.setText(result.getIdNumber().toString());
                    etShenfenzhengAddr.setText(result.getAddress().toString());

                }
            }

            @Override
            public void onError(OCRError error) {
                alertText("", error.getMessage());
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

    private void postData() {

//        if (province.equals("北京市") || province.equals("上海市") || province.equals("天津市") || province.equals("重庆市")) {
//            city = district;
//            district = "-1";
//        }

        String telphone = etPhoneNum.getText().toString().trim();

        if (!NumberUtils.isMobile(telphone)) {
            MyToast.show(this, "手机号码不是11位，请仔细检查");
            hud.dismiss();
            return;
        }

        String userName = etCustomerName.getText().toString().trim();

        PreferenceUtils.setString(InputCreditActivity.this, "userName", userName);

        String cardTypeMessage = tvCertificateType.getText().toString();

        String cardNum = etCertificateNum.getText().toString().trim();

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Login/createArchives")
                .addParams("userName", userName)
                .addParams("cardType", "1")
                .addParams("cardNum", cardNum)
                .addParams("telphone", telphone)
                .addParams("address", etFullAddr.getText().toString())
                .addParams("lat", "333")
                .addParams("lng", "222")
                .addParams("province", province == null ? "黑龙江" : province)
                .addParams("city", city == null ? "哈尔滨" : city)
                .addParams("area", district == null ? "道里区" : district)
                .addParams("salesman_id", PreferenceUtils.getString(InputCreditActivity.this, "saleID"))
                .addParams("carLoan", carloan)
                .addParams("credit", rbOwn.isChecked() ? "0" : "1")
                .addParams("loanType", rbTwo == null ? "0" : rbTwo.isChecked() ? "1" : "2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("input----------------->>>>>>." + e);
                        MyToast.show(InputCreditActivity.this, "服务器正忙，请稍后再试。。。");
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("input----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();
                        CreditActivityBean bean = gson.fromJson(json, CreditActivityBean.class);

                        if (bean.getCode() == 1) {
                            String userId = bean.getList().getUserId();
                            PreferenceUtils.setString(InputCreditActivity.this, "userId", userId);
                            if (rbOwn.isChecked()) {
                                PreferenceUtils.setString(InputCreditActivity.this, "CreditType", "0");
                            } else if (rbOther.isChecked()) {
                                PreferenceUtils.setString(InputCreditActivity.this, "CreditType", "1");
                            }
                            hud.dismiss();

                            if (type.equals("1")) {
                                PreferenceUtils.setString(InputCreditActivity.this, "carLoan", "1");
                                PreferenceUtils.setInt(InputCreditActivity.this, "credit", 1);
                                startActivity(new Intent(InputCreditActivity.this, CarTypeActivity.class));
                            } else if (type.equals("2")) {
                                PreferenceUtils.setString(InputCreditActivity.this, "carLoan", "2");
                                PreferenceUtils.setInt(InputCreditActivity.this, "credit", 2);
                                startActivity(new Intent(InputCreditActivity.this, CarEvaluateActivity2.class));
                            }

                            finish();
                            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                            overridePendingTransition(enterAnim6, exitAnim6);
                        } else if (bean.getCode() == 0) {
                            MyToast.show(InputCreditActivity.this, bean.getMsg());
                            hud.dismiss();
                        }
                    }
                });
    }


    private void showSelectHomeDialog() {

        //选择城市颜色
//        int color1 = getResources().getColor(R.color.xuanzechengshi);
        //带选择城市颜色
//        int color2 = getResources().getColor(R.color.beixuanchengshi);

        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(InputCreditActivity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(InputCreditActivity.this, data);
        picker.setSelectedItem("黑龙江省", "哈尔滨市", "道里区");
        picker.setTitleText("地址选择");
        picker.setTitleTextColor(Color.BLACK);
        picker.setLineVisible(false);
        picker.setTextSize(18);
        picker.setCancelText("取消");
        picker.setSubmitText(" 确定");
        picker.setOffset(2);
//        picker.setTextColor(color1, color2);
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {

            @Override
            //                                  省           市           区
            public void onAddressPicked(String province, String city, String county) {
                //省市区
                tvHome.setText(province + city + county);
                //省
                InputCreditActivity.this.province = province;
//                //市
                InputCreditActivity.this.city = city;
//                //区
                InputCreditActivity.this.district = county;
            }
        });
        picker.show();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!StringUtils.isEmpty(etCustomerName.getText().toString())
                && !StringUtils.isEmpty(etCertificateNum.getText().toString())
                && !StringUtils.isEmpty(etPhoneNum.getText().toString())
                && !StringUtils.isEmpty(etFullAddr.getText().toString())
                && !StringUtils.isEmpty(tvCertificateType.getText().toString())
                && !StringUtils.isEmpty(tvHome.getText().toString())
                && !StringUtils.isEmpty(etShenfenzhengAddr.getText().toString())
                )

        {
            btStartVisit.setEnabled(true);
        } else {
            btStartVisit.setEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hud.show();
        initAccessTokenWithAkSk();
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }
}
