package com.feifandaiyu.feifan.activity.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.MainActivity;
import com.feifandaiyu.feifan.bean.MsgBean;
import com.feifandaiyu.feifan.bean.UserBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.feifandaiyu.feifan.view.BaseViewHelper;
import com.feifandaiyu.feifan.view.Code;
import com.feifandaiyu.feifan.view.CountDownTimerButton;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.feifandaiyu.feifan.R.id.iv_showCode;


public class ForgetPasswordActivity extends AppCompatActivity implements TextWatcher {

    @InjectView(R.id.back_black)
    ImageView backBlack;
    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.iv_clean_phone)
    ImageView ivCleanPhone;
    @InjectView(R.id.et_identify_code)
    EditText etIdentifyCode;
    @InjectView(iv_showCode)
    ImageView ivShowCode;
    @InjectView(R.id.et_msg_code)
    EditText etMsgCode;
    @InjectView(R.id.tv_timmer)
    CountDownTimerButton tvTimmer;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    private String realCode;
    private boolean flag = false;
    public static final String TAG = MainActivity.class.getName();
    private String username;
    private KProgressHUD hud;
    private BaseViewHelper helper;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_title) , true);

//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
////            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }

        ButterKnife.inject(this);

        WindowManager wm = this.getWindowManager();
        height = wm.getDefaultDisplay().getHeight();

        startTranslationNoShowTranslation();

        hud = KProgressHUD.create(ForgetPasswordActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        ivShowCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();

        etUsername.addTextChangedListener(this);
        etIdentifyCode.addTextChangedListener(this);
        etMsgCode.addTextChangedListener(this);
    }

    private void startTranslationNoShowTranslation() {
        helper = new BaseViewHelper
                .Builder(ForgetPasswordActivity.this)
                .isFullWindow(true)//是否全屏显示
                .isShowTransition(true)//是否显示过渡动画
                .setDimColor(Color.WHITE)//遮罩颜色
                .setDimAlpha(200)//遮罩透明度
                .setDuration(400)
                .setScaleX(1.5f)
                .setScaleY(1.5f)
                .setTranslationY(-(height/2))
                .setTranslationX(-100)
                .create();//开始动画
    }

    @OnClick({R.id.back_black, R.id.iv_clean_phone, R.id.iv_showCode, R.id.btn_login, R.id.tv_timmer})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.back_black:
                if (helper!=null && helper.isShowing()){
                    helper.backActivity(this);
                }
                finish();
                break;
            case R.id.iv_clean_phone:
                etUsername.setText("");
                break;
            case iv_showCode:
                ivShowCode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                Log.v(TAG, "realCode" + realCode);
                break;
            case R.id.tv_timmer:
                if (etUsername.getText().toString().equals("")) {
                    MyToast.show(ForgetPasswordActivity.this, "请填写手机号");
                } else {
                    if (realCode.equals(etIdentifyCode.getText().toString())) {
                        tvTimmer.startCountDown();
                        sendMsg();
                    } else {
                        MyToast.show(ForgetPasswordActivity.this, "图形验证码输入有误");
                    }

                }
                break;
            case R.id.btn_login:
                if (realCode.equals(etIdentifyCode.getText().toString())) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                    username = etUsername.getText().toString().trim();
                    String password = etMsgCode.getText().toString().trim();

                    if (username.equals("") || password.equals("")) {
                        MyToast.show(ForgetPasswordActivity.this, "用户名或者密码不能为空");
                        return;

                    } else {

//                    if (ActivityCompat.checkSelfPermission(MsgLoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                        new AppSettingsDialog.Builder(MsgLoginActivity.this)
//                                .setRationale("没有电话权限，此应用程序无法正常工作。请点击确定修改权限")
//                                .setTitle("必需权限")
//                                .build()
//                                .show();
//                        return;
//                    }

//                    Imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();

//                    LogUtils.e("imei=" + Imei);

                        hud.show();

                        OkHttpUtils
                                .post()
                                .url(Constants.URLS.BASEURL + "Appraiser/smsLogin")
                                .addParams("mobile", username)
                                .addParams("vcode", password)
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        LogUtils.d("login----------------->>>>" + e + "失败");
                                        hud.dismiss();
                                        MyToast.show(ForgetPasswordActivity.this, "链接服务器失败，请联系管理员");
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        LogUtils.i("login----------------->>>>" + response + "成功");
                                        String json = response;
                                        Gson gson = new Gson();
                                        UserBean userBean = null;
                                        try {

                                            userBean = gson.fromJson(json, UserBean.class);

                                        } catch (Exception e) {
                                            hud.dismiss();
                                            MyToast.show(ForgetPasswordActivity.this, "当前网络链接失败");
                                        }

                                        if (userBean != null) {
                                            if (userBean.getCode().equals("0")) {

                                                hud.dismiss();

                                                MyToast.show(ForgetPasswordActivity.this, userBean.getMsg());

                                            } else {

                                                Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
                                                intent.putExtra("phone", username);


                                                if (userBean.getList().getRole() == 2) {

                                                    hud.dismiss();
                                                    new BaseViewHelper
                                                            .Builder(ForgetPasswordActivity.this, view)
                                                            .startActivity(intent);
                                                    finish();

                                                } else if (userBean.getList().getRole() == 99) {

                                                    hud.dismiss();
                                                    new BaseViewHelper
                                                            .Builder(ForgetPasswordActivity.this, view)
                                                            .startActivity(intent);
                                                    finish();

                                                } else if (userBean.getList().getRole() == -1) {

                                                    hud.dismiss();
                                                    new BaseViewHelper
                                                            .Builder(ForgetPasswordActivity.this, view)
                                                            .startActivity(intent);
                                                    finish();

                                                } else {
                                                    MyToast.show(ForgetPasswordActivity.this, "未识别的用户");
                                                    hud.dismiss();
                                                }
                                            }
                                        }
                                    }
                                });
                    }

                    break;

                } else {
                    MyToast.show(ForgetPasswordActivity.this, "图形验证码输入有误");
                }
                break;

        }
    }

    private void sendMsg() {
        username = etUsername.getText().toString().trim();

        hud.show();

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Appraiser/sendSms")
                .addParams("mobile", username)
                .addParams("second", "60")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.d("msg----------------->>>>" + e + "失败");
                        hud.dismiss();
                        MyToast.show(ForgetPasswordActivity.this, "链接服务器失败，请联系管理员");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("msg----------------->>>>" + response + "成功");
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        MsgBean MsgBean = gson.fromJson(json, MsgBean.class);
                        if (MsgBean.getCode() == 1) {

                            MyToast.show(ForgetPasswordActivity.this, "短信已发送，请注意查收");
                        } else {
                            MyToast.show(ForgetPasswordActivity.this, MsgBean.getMsg());
                        }
                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!StringUtils.isEmpty(etUsername.getText().toString()) && ivCleanPhone.getVisibility() == View.GONE) {
            ivCleanPhone.setVisibility(View.VISIBLE);
        } else if (StringUtils.isEmpty(etUsername.getText().toString())) {
            ivCleanPhone.setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(etUsername.getText().toString())
                && !StringUtils.isEmpty(etIdentifyCode.getText().toString())
                && !StringUtils.isEmpty(etMsgCode.getText().toString())) {
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {

        if (helper!=null && helper.isShowing()){
            helper.backActivity(this);
        }else {
            super.onBackPressed();
        }
    }
}
