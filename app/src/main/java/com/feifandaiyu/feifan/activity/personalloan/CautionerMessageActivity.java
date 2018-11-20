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
import android.widget.EditText;
import android.widget.ImageView;
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
import com.feifandaiyu.feifan.MyApplication;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CautionerMessageActivityBean;
import com.feifandaiyu.feifan.bean.MsgBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.ocr.CameraActivity;
import com.feifandaiyu.feifan.popupWindow.BottomPopupOption;
import com.feifandaiyu.feifan.testactivity.TestActivity;
import com.feifandaiyu.feifan.utils.AssetsUtils;
import com.feifandaiyu.feifan.utils.FileUtil;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
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

/**
 * @author houdaichang
 * @date 2017/5/10
 */

public class CautionerMessageActivity extends BaseActivity implements TextWatcher {
    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    private static final int REQUEST_CODE_CAMERA = 102;

    @InjectView(R.id.person_danbao)
    TextView PerssonDanbao;
    @InjectView(R.id.rb_local)
    RadioButton rbLocal;
    @InjectView(R.id.rb_not_local)
    RadioButton rbNotLocal;
    @InjectView(R.id.et_cautioner_name)
    PowerfulEditText etCautionerName;
    @InjectView(R.id.tv_sex)
    TextView tvSex;
    @InjectView(R.id.et_cautioner_phone)
    PowerfulEditText etCautionerPhone;
    @InjectView(R.id.tv_choose_certificate)
    TextView tvChooseCertificate;
    @InjectView(R.id.et_certificate_num)
    PowerfulEditText etCertificateNum;
    @InjectView(R.id.tv_home)
    TextView tvHome;
    @InjectView(R.id.et_addr)
    PowerfulEditText etAddr;
    @InjectView(R.id.et_company_name)
    PowerfulEditText etCompanyName;
    @InjectView(R.id.tv_company_home)
    TextView tvCompanyHome;
    @InjectView(R.id.et_company_addr)
    PowerfulEditText etCompanyAddr;
    @InjectView(R.id.textView13)
    TextView textView13;
    @InjectView(R.id.et_company_phone)
    PowerfulEditText etCompanyPhone;
    @InjectView(R.id.et_choose_relationship)
    TextView etChooseRelationship;
    @InjectView(R.id.tv_look)
    TextView tvLook;
    @InjectView(R.id.et_area_code)
    EditText etAreaCode;
    @InjectView(R.id.cemera1)
    ImageView cemera1;
    private BottomPopupOption bottomPopupOption;
    private TextView tv_next;
    private String province1;
    private String city1;
    private String district1;
    private String province2;
    private String city2;
    private String district2;
    private String isMarry;
    private String image;
    private KProgressHUD hud;
    private InputMethodManager imm;
    private boolean hasGotToken = false;
    private AlertDialog.Builder alertDialog;
    private boolean isOk = false;
    private String cautioner;

    @Override
    protected int getContentView() {
        return R.layout.activity_cautioner_message;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("共借人信息");

        tv_next = (TextView) findViewById(R.id.tv_next);

        alertDialog = new AlertDialog.Builder(this);

        hud = KProgressHUD.create(CautionerMessageActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍后")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        initAccessTokenWithAkSk();

        etAddr.addTextChangedListener(this);
        etCautionerName.addTextChangedListener(this);
        tvSex.addTextChangedListener(this);
        etCautionerPhone.addTextChangedListener(this);
        tvChooseCertificate.addTextChangedListener(this);
        etCertificateNum.addTextChangedListener(this);
        tvHome.addTextChangedListener(this);
        etCompanyPhone.addTextChangedListener(this);
        etCompanyName.addTextChangedListener(this);
        tvCompanyHome.addTextChangedListener(this);
        etCompanyAddr.addTextChangedListener(this);
        etChooseRelationship.addTextChangedListener(this);


        MyApplication.getInstance().addActivity(this);

        isMarry = PreferenceUtils.getString(this, "isMarry");
        cautioner = PreferenceUtils.getString(this, "cautioner");

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
                    etCautionerName.setText(result.getName().toString());
                    etCertificateNum.setText(result.getIdNumber().toString());

                }
            }

            @Override
            public void onError(OCRError error) {
                alertText("", error.getMessage());
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_next, R.id.tv_sex, R.id.tv_home, R.id.tv_company_home, R.id.person_danbao,
            R.id.tv_look, R.id.et_choose_relationship, R.id.cemera1})
    public void onViewClicked(View view) {
        bottomPopupOption = new BottomPopupOption(CautionerMessageActivity.this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {

            case R.id.tv_home:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                showSelectHomeDialog1();
                break;
            case R.id.cemera1:
                Intent intent = new Intent(CautionerMessageActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
                break;
            case R.id.tv_company_home:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                showSelectHomeDialog2();
                break;
            case R.id.et_choose_relationship:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                chooseRelation();
                break;
            case R.id.tv_sex:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                bottomPopupOption.setItemText("男", "女");
                bottomPopupOption.showPopupWindow();
                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 0) {
                            tvSex.setText("男");
                            bottomPopupOption.dismiss();
                        } else if (position == 1) {
                            tvSex.setText("女");
                            bottomPopupOption.dismiss();
                        }
                    }
                });
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

            case R.id.tv_look:
                startActivity(new Intent(this, TestActivity.class));
                break;
            case R.id.tv_next:

                if (rbLocal.isChecked() || rbNotLocal.isChecked()) {
                    if (image == null) {
                        MyToast.show(this, "请上传照片");
                        return;
                    }

                    if (tvLook.getText().toString().equals("请添加照片")) {
                        MyToast.show(this, "请添加照片");
                    } else {
                        hud.show();

                        if (isOk) {
                            postData();
                        } else {
                            confirm();
                        }

                    }
                } else {
                    MyToast.show(this, "请选择户口类型");
                }

                break;
            default:
        }
    }

    private void confirm() {

        String userName = etCautionerName.getText().toString().trim();
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
                        MyToast.show(CautionerMessageActivity.this, "服务器正忙，请稍后再试。。。");
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
                            etCautionerName.setEnabled(false);
                            etCautionerName.setTextColor(Color.GRAY);

                            new AlertDialog.Builder(CautionerMessageActivity.this)
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
                            new AlertDialog.Builder(CautionerMessageActivity.this)
                                    .setMessage(MsgBean.getMsg())
                                    .setPositiveButton("知道了", null)
                                    .show();
                        }

                    }
                });


    }

    private void postData() {
        String userId = PreferenceUtils.getString(this, "userId");
        String guaranteeName = etCautionerName.getText().toString().trim();
        String telphone = etCautionerPhone.getText().toString().trim();
        String cardnum = etCertificateNum.getText().toString().trim();
        String relations = etChooseRelationship.getText().toString().trim();
        String workName = etCompanyName.getText().toString().trim();
        String workPhone = etCompanyPhone.getText().toString().trim();
        String address1 = etAddr.getText().toString().trim();
        String address2 = etCompanyAddr.getText().toString().trim();
        etCautionerPhone.getText().toString().trim();
        etCautionerPhone.getText().toString().trim();

        OkHttpUtils
                .post()
                //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                .url(Constants.URLS.BASEURL + "Login/addGuarantee")
                .addParams("userId", userId)
                .addParams("guaranteeName", guaranteeName)
                .addParams("sex", tvSex.getText().toString().equals("男") ? "0" : "1")
                .addParams("telphone", telphone)
                .addParams("cardtype", "1")
                .addParams("cardnum", cardnum)
                .addParams("relations", relations)
                .addParams("workName", workName)
                .addParams("workPhone", workPhone)
                .addParams("province", province2)
                .addParams("city", city2)
                .addParams("area", district2)
                .addParams("address", address2)
                .addParams("homeProvince", province1)
                .addParams("homeCity", city1)
                .addParams("homeArea", district1)
                .addParams("homeAddress", address1)
                .addParams("isfund", rbLocal.isChecked() ? "0" : "1")
                .addParams("flag", "0")
                .addParams("image", image)
                .addParams("areaCode", etAreaCode.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("cautioner____________________>>>>>" + e);
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("cautioner----------------->>>>>>." + response);

                        hud.dismiss();

                        String json = response;
                        Gson gson = new Gson();
                        CautionerMessageActivityBean bean = gson.fromJson(json, CautionerMessageActivityBean.class);
                        if (bean.getCode() == 0) {
                            MyToast.show(CautionerMessageActivity.this, bean.getMsg());
                            return;
                        }
                        if (bean.getCode() == 1) {

                            if (cautioner.equals("0")) {
                                if (isMarry.equals("2")) {

                                    startActivity(new Intent(CautionerMessageActivity.this, WifeActivity.class));
                                    PreferenceUtils.setString(CautionerMessageActivity.this, "imagecount", "请添加照片");

                                } else {

                                    startActivity(new Intent(CautionerMessageActivity.this, BankCardMessageActivity.class));
                                    PreferenceUtils.setString(CautionerMessageActivity.this, "imagecount", "请添加照片");

                                }

                                finish();
                                // // 设置过渡动画
                                int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                overridePendingTransition(enterAnim6, exitAnim6);
                            } else if (cautioner.equals("1")) {
                                finish();
                                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                overridePendingTransition(enterAnim0, exitAnim0);
                            }


                        }


                    }
                });
    }

    private void chooseRelation() {
        bottomPopupOption.setItemText("同事", "朋友", "父亲", "母亲", "子女", "兄弟姐妹");
        bottomPopupOption.showPopupWindow();
        bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    etChooseRelationship.setText("同事");
                    bottomPopupOption.dismiss();
                } else if (position == 1) {
                    etChooseRelationship.setText("朋友");
                    bottomPopupOption.dismiss();
                } else if (position == 2) {
                    etChooseRelationship.setText("父亲");
                    bottomPopupOption.dismiss();
                } else if (position == 3) {
                    etChooseRelationship.setText("母亲");
                    bottomPopupOption.dismiss();
                } else if (position == 4) {
                    etChooseRelationship.setText("子女");
                    bottomPopupOption.dismiss();
                } else if (position == 5) {
                    etChooseRelationship.setText("兄弟姐妹");
                    bottomPopupOption.dismiss();
                }
            }
        });
    }

    private void showSelectHomeDialog1() {
        //选择城市颜色
//        int color1 = getResources().getColor(R.color.xuanzechengshi);
        //带选择城市颜色
//        int color2 = getResources().getColor(R.color.beixuanchengshi);
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(CautionerMessageActivity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(CautionerMessageActivity.this, data);
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
                CautionerMessageActivity.this.province1 = province;
//                //市
                CautionerMessageActivity.this.city1 = city;
//                //区
                CautionerMessageActivity.this.district1 = county;
            }
        });
        picker.show();
    }

    private void showSelectHomeDialog2() {
        //选择城市颜色
//        int color1 = getResources().getColor(R.color.xuanzechengshi);
        //带选择城市颜色
//        int color2 = getResources().getColor(R.color.beixuanchengshi);
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(CautionerMessageActivity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(CautionerMessageActivity.this, data);
        picker.setSelectedItem("黑龙江省", "哈尔滨市", "道里区");
        picker.setTitleText("地址选择");
        picker.setTitleTextColor(Color.BLACK);
        picker.setLineVisible(false);
        picker.setTextSize(18);
        ;
        picker.setCancelText("取消");
        picker.setSubmitText(" 确定");
        picker.setOffset(2);
//        picker.setTextColor(color1, color2);
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            //                                  省           市           区
            public void onAddressPicked(String province, String city, String county) {
                //省市区
                tvCompanyHome.setText(province + city + county);
                //省
                CautionerMessageActivity.this.province2 = province;
//                //市
                CautionerMessageActivity.this.city2 = city;
//                //区
                CautionerMessageActivity.this.district2 = county;
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
        if (!StringUtils.isEmpty(etChooseRelationship.getText().toString())
                && !StringUtils.isEmpty(etCompanyAddr.getText().toString())
                && !StringUtils.isEmpty(etCompanyName.getText().toString())
                && !StringUtils.isEmpty(etCompanyPhone.getText().toString())
                && !StringUtils.isEmpty(etAddr.getText().toString())
                && !StringUtils.isEmpty(etCautionerName.getText().toString())
                && !StringUtils.isEmpty(etCautionerPhone.getText().toString())
                && !StringUtils.isEmpty(etCertificateNum.getText().toString())

                && !StringUtils.isEmpty(tvCompanyHome.getText().toString())
                && !StringUtils.isEmpty(tvHome.getText().toString())
                && !StringUtils.isEmpty(tvChooseCertificate.getText().toString())
                && !StringUtils.isEmpty(tvSex.getText().toString())
                )

        {
            tv_next.setEnabled(true);
        } else {
            tv_next.setEnabled(false);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceUtils.setString(CautionerMessageActivity.this, "cautioner", "0");
    }

    @Override
    protected void onResume() {


        String imagecount = PreferenceUtils.getString(CautionerMessageActivity.this, "imagecount");
        if (imagecount != null) {
            tvLook.setText(imagecount);
        }
        super.onResume();
        image = PreferenceUtils.getString(this, "imagearray");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }
}
