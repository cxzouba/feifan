package com.feifandaiyu.feifan.activity.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.MainActivity;
import com.feifandaiyu.feifan.activity.evalutor.EvaluaterActivity;
import com.feifandaiyu.feifan.bean.MsgBean;
import com.feifandaiyu.feifan.bean.UserBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.SnackbarUtil;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.feifandaiyu.feifan.view.BaseViewHelper;
import com.feifandaiyu.feifan.view.CountDownTimerButton;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

public class MsgLoginActivity extends AppCompatActivity implements TextWatcher {

    @InjectView(R.id.back_black)
    ImageView backBlack;
    @InjectView(R.id.tv_login)
    TextView tvLogin;
    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.iv_clean_phone)
    ImageView ivCleanPhone;
    @InjectView(R.id.et_identify_code)
    EditText etIdentifyCode;
    @InjectView(R.id.tv_timmer)
    CountDownTimerButton tvTimmer;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;
    @InjectView(R.id.root)
    LinearLayout root;
    private String username;
    private Message m;
    //    private String Imei;
    private KProgressHUD hud;
    private BaseViewHelper helper;
    private int height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_title), true);

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

        hud = KProgressHUD.create(MsgLoginActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        etUsername.addTextChangedListener(this);
        etIdentifyCode.addTextChangedListener(this);

    }

    private void startTranslationNoShowTranslation() {
        helper = new BaseViewHelper
                .Builder(MsgLoginActivity.this)
                .isFullWindow(true)//是否全屏显示
                .isShowTransition(true)//是否显示过渡动画
                .setDimColor(Color.WHITE)//遮罩颜色
                .setDimAlpha(200)//遮罩透明度
                .setDuration(400)
                .setScaleX(1.5f)
                .setScaleY(1.5f)
                .setTranslationY(-(height / 2))
                .setTranslationX(100)
                .create();//开始动画
    }

    @OnClick({R.id.back_black, R.id.tv_login, R.id.iv_clean_phone, R.id.tv_timmer, R.id.btn_login})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.back_black:
                Intent intent1 = new Intent(MsgLoginActivity.this, LoginActivity.class);
                new BaseViewHelper
                        .Builder(MsgLoginActivity.this, view)
                        .startActivity(intent1);
                finish();
                break;
            case R.id.tv_login:
                Intent intent = new Intent(MsgLoginActivity.this, RegisterActivity.class);
                new BaseViewHelper
                        .Builder(MsgLoginActivity.this, view)
                        .startActivity(intent);
                break;
            case R.id.iv_clean_phone:
                etUsername.setText("");
                break;
            case R.id.tv_timmer:
                if (etUsername.getText().toString().equals("")) {
                    MyToast.show(MsgLoginActivity.this, "请填写手机号");
                } else {
                    tvTimmer.startCountDown();
                    sendMsg();
                }
                break;
            case R.id.btn_login:

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                username = etUsername.getText().toString().trim();
                String password = etIdentifyCode.getText().toString().trim();

                if (username.equals("") || password.equals("")) {
                    MyToast.show(MsgLoginActivity.this, "用户名或者密码不能为空");
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
//                            .addParams("imei", Imei)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    LogUtils.d("login----------------->>>>" + e + "失败");
                                    hud.dismiss();
                                    MyToast.show(MsgLoginActivity.this, "链接服务器失败，请联系管理员");
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
                                        MyToast.show(MsgLoginActivity.this, "当前网络链接失败");
                                    }

                                    if (userBean != null) {
                                        if (userBean.getCode().equals("0")) {

                                            hud.dismiss();

                                            MyToast.show(MsgLoginActivity.this, userBean.getMsg());

                                        } else {
                                            getXGPushReceiver(username);

                                            PreferenceUtils.setString(MsgLoginActivity.this, "loginName", username);

                                            PreferenceUtils.setString(MsgLoginActivity.this, "city", userBean.getList().getCity());

                                            PreferenceUtils.setString(MsgLoginActivity.this, "settingName", userBean.getList().getUserName());

                                            PreferenceUtils.setInt(MsgLoginActivity.this, "chijiu", userBean.getList().getRole());

                                            if (userBean.getList().getRole() == 2) {

                                                Intent intent = new Intent(MsgLoginActivity.this, MainActivity.class);
                                                PreferenceUtils.setString(MsgLoginActivity.this, "saleID", userBean.getList().getSaleID() + "");
                                                PreferenceUtils.setBoolean(MsgLoginActivity.this, "islogin", true);
                                                hud.dismiss();
                                                new BaseViewHelper
                                                        .Builder(MsgLoginActivity.this, view)
                                                        .startActivity(intent);
                                                finish();

                                            } else if (userBean.getList().getRole() == 99) {

                                                Intent intent = new Intent(MsgLoginActivity.this, EvaluaterActivity.class);
                                                PreferenceUtils.setString(MsgLoginActivity.this, "saleID", userBean.getList().getSaleID() + "");
                                                PreferenceUtils.setBoolean(MsgLoginActivity.this, "islogin", true);
                                                hud.dismiss();
                                                new BaseViewHelper
                                                        .Builder(MsgLoginActivity.this, view)
                                                        .startActivity(intent);
                                                finish();

                                            } else if (userBean.getList().getRole() == -1) {

                                                Intent intent = new Intent(MsgLoginActivity.this, MainActivity.class);

                                                PreferenceUtils.setString(MsgLoginActivity.this, "saleID", userBean.getList().getSaleID() + "");
                                                PreferenceUtils.setBoolean(MsgLoginActivity.this, "islogin", true);
                                                hud.dismiss();
                                                new BaseViewHelper
                                                        .Builder(MsgLoginActivity.this, view)
                                                        .startActivity(intent);
                                                finish();
                                            } else {
                                                MyToast.show(MsgLoginActivity.this, "未识别的用户");
                                                hud.dismiss();
                                            }
                                        }
                                    }
                                }
                            });
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
                        MyToast.show(MsgLoginActivity.this, "链接服务器失败，请联系管理员");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("msg----------------->>>>" + response + "成功");
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        MsgBean MsgBean = gson.fromJson(json, MsgBean.class);
                        if (MsgBean.getCode() == 1) {
                            MyToast.show(MsgLoginActivity.this, "短信已发送，请注意查收");
                        } else {
                            MyToast.show(MsgLoginActivity.this, MsgBean.getMsg());
                        }

                    }

                });
    }

    private void getXGPushReceiver(String tag) {

        XGPushManager.setTag(this, tag);
        XGPushManager.registerPush(getApplicationContext(),
                new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
                        Log.w(com.tencent.android.tpush.common.Constants.LogTag, "+++ register push sucess. token:" + data + "flag" + flag);

                        m.obj = "+++ register push sucess. token:" + data;
                        m.sendToTarget();
                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                        Log.w(com.tencent.android.tpush.common.Constants.LogTag,
                                "+++ register push fail. token:" + data
                                        + ", errCode:" + errCode + ",msg:"
                                        + msg);
                        m.obj = "+++ register push fail. token:" + data
                                + ", errCode:" + errCode + ",msg:" + msg;
                        m.sendToTarget();
                    }
                });

//        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
//        if(click!=null){
//            startActivity(new Intent(LoginActivity.this,PersonalLoanActivity.class));
//            return;
//        }

    }

    /**
     * 连按两次退出程序
     */
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
//                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    SnackbarUtil.ShortSnackbar(root, "再按一次退出", SnackbarUtil.Normal).show();

                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {
                    XGPushManager.deleteTag(MsgLoginActivity.this, username);
                    //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
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
                && !StringUtils.isEmpty(etIdentifyCode.getText().toString())) {
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setEnabled(false);
        }
    }

}
