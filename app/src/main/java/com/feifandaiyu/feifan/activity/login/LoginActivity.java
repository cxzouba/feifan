package com.feifandaiyu.feifan.activity.login;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
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
import com.feifandaiyu.feifan.bean.CodeBean;
import com.feifandaiyu.feifan.bean.UserBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.SnackbarUtil;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.feifandaiyu.feifan.view.BaseViewHelper;
import com.feifandaiyu.feifan.view.DrawableTextView;
import com.feifandaiyu.feifan.view.KeyboardWatcher;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.refactor.lib.colordialog.PromptDialog;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by davidzhao on 2017/5/4.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, KeyboardWatcher.SoftKeyboardStateListener, EasyPermissions.PermissionCallbacks {
    private static final int RC_CAMERA_AND_WIFI = 100;
    private static final String TAG = "quanxian";
    @InjectView(R.id.et_username)
    EditText edUsername;
    @InjectView(R.id.et_password)
    EditText edPassword;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.iv_clean_phone)
    ImageView iv_clean_phone;
    @InjectView(R.id.clean_password)
    ImageView clean_password;
    @InjectView(R.id.iv_show_pwd)
    ImageView iv_show_pwd;
    @InjectView(R.id.send_message)
    TextView sendMessage;
    @InjectView(R.id.forget_password)
    TextView forgetPassword;
    @InjectView(R.id.logo)
    DrawableTextView logo;
    @InjectView(R.id.body)
    LinearLayout body;
    @InjectView(R.id.root)
    LinearLayout root;
    @InjectView(R.id.register)
    TextView register;
    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;
    Handler handler = new Handler();
    private Intent intent;
    private RelativeLayout rl;
    private KProgressHUD hud;
    private Bitmap bitmap;
    private BitmapDrawable bmdrawable;
    private Message m;
    private String username;
    private String Imei;
    private int screenHeight = 0;//屏幕高度
    private float scale = 0.8f; //logo缩放比例
    private KeyboardWatcher keyboardWatcher;
    private BaseViewHelper helper;
    private String versionCode;
    private String[] perms;
    /**
     * 连按两次退出程序
     */
    private long firstTime = 0;
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_title), true);
        ButterKnife.inject(this);
//        startTranslationNoShowTranslation();
        init();
        initPermission();
    }

    private void startTranslationNoShowTranslation() {
        helper = new BaseViewHelper
                .Builder(LoginActivity.this)
                .isFullWindow(true)//是否全屏显示
                .isShowTransition(true)//是否显示过渡动画
                .setDimColor(Color.WHITE)//遮罩颜色
                .setDimAlpha(200)//遮罩透明度
                .setDuration(400)
                .setScaleX(2)
                .setScaleY(2)
                .setTranslationX(-500)
                .create();//开始动画
    }

    protected void init() {
        hud = KProgressHUD.create(LoginActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在登录")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setWindowColor(Color.GRAY)
                .setDimAmount(0.5f);

        versionCode = getVersionCode(this);

//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }


//        else {
//            LinearLayout.LayoutParams manager = (LinearLayout.LayoutParams) rlTitle.getLayoutParams();
//            manager.height = 100;
//            rlTitle.setLayoutParams(manager);
//        }

        ButterKnife.inject(this);

        keyboardWatcher = new KeyboardWatcher(findViewById(Window.ID_ANDROID_CONTENT));
        keyboardWatcher.addSoftKeyboardStateListener(this);

        iv_clean_phone.setOnClickListener(this);
        clean_password.setOnClickListener(this);
        iv_show_pwd.setOnClickListener(this);


        edUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && iv_clean_phone.getVisibility() == View.GONE) {
                    iv_clean_phone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    iv_clean_phone.setVisibility(View.GONE);
                }

                if (s.toString().isEmpty()) {
                    btnLogin.setEnabled(false);
                    return;
                } else {
                    if (!StringUtils.isEmpty(edPassword.getText().toString())) {
                        btnLogin.setEnabled(true);
                    }
                }
            }
        });

        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && clean_password.getVisibility() == View.GONE) {
                    clean_password.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    clean_password.setVisibility(View.GONE);
                }

                if (s.toString().isEmpty()) {
                    btnLogin.setEnabled(false);
                    return;
                }
                if (!StringUtils.isEmpty(edUsername.getText().toString())) {
                    btnLogin.setEnabled(true);
                }

//                if (!s.toString().matches("[A-Za-z0-9]+")) {
//                    String temp = s.toString();
//                    Toast.makeText(LoginActivity.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
//                    s.delete(temp.length() - 1, temp.length());
//                    edPassword.setSelection(s.length());
//                }
            }
        });


        initVersonCode();
        login();
    }

    private void initPermission() {
        perms = new String[]{Manifest.permission.CAMERA, Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE)) {
            //...
            LogUtils.e("1");
//            startAnim();
//            action();

        } else {
            //...
            LogUtils.e("2");

            EasyPermissions.requestPermissions(this, "请允许权限申请", RC_CAMERA_AND_WIFI, perms);

        }
    }

    public String getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    private void initVersonCode() {
        OkHttpUtils.post()
                .url(Constants.URLS.BASEURL + "Appraiser/version")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("versonCode----------------->>>>>>." + e);
                        MyToast.show(LoginActivity.this, "服务器正忙，获取VersinCode失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("versonCode----------------->>>>>>." + response);
                        try {
                            String json = response;
                            Gson gson = new Gson();
                            CodeBean bean = gson.fromJson(json, CodeBean.class);
                            if (bean.getCode() == 1) {
                                if (bean.getVersion() > Integer.parseInt(versionCode)) {
                                    PromptDialog promptDialog = new PromptDialog(LoginActivity.this);
//                                    promptDialog.setCanceledOnTouchOutside(false);
                                    promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                                            .setAnimationEnable(true)
                                            .setTitleText("更新提示")
                                            .setContentText("您有新的版本需要更新")
                                            .setPositiveListener("去更新", new PromptDialog.OnPositiveListener() {
                                                        @Override
                                                        public void onClick(PromptDialog dialog) {
                                                            Intent intent = new Intent();
                                                            intent.setAction("android.intent.action.VIEW");
                                                            Uri content_url = Uri.parse("http://app.qq.com/#id=detail&appid=1106267582");
                                                            intent.setData(content_url);
                                                            startActivity(intent);
                                                            dialog.dismiss();
                                                        }
                                                    }
                                            ).show();
                                }
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void login() {


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘

                username = edUsername.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                if (username.equals("") || password.equals("")) {

                    MyToast.show(LoginActivity.this, "用户名或者密码不能为空");
                    return;

                } else {

                    if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                        new AppSettingsDialog.Builder(LoginActivity.this)
//                                .setRationale("没有电话权限，此应用程序无法正常工作。请点击确定修改权限")
//                                .setTitle("必需权限")
//                                .build()
//                                .show();
                        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 198);

                        return;

                    }

                    Imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();

                    LogUtils.e("imei=" + Imei);

                    if (Imei != null) {
                        hud.show();

                        OkHttpUtils
                                .post()
                                .url(Constants.URLS.BASEURL + "Login/index")
                                .addParams("userName", username)
                                .addParams("password", password)
                                .addParams("imei", Imei)
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        LogUtils.d("login----------------->>>>" + e + "失败");
                                        hud.dismiss();
                                        MyToast.show(LoginActivity.this, "链接服务器失败，请联系管理员");
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
                                            MyToast.show(LoginActivity.this, "当前网络链接失败");
                                        }

                                        if (userBean != null) {
                                            if (userBean.getCode().equals("0")) {

                                                hud.dismiss();

                                                MyToast.show(LoginActivity.this, userBean.getMsg());

                                            } else if (userBean.getCode().equals("1")) {
                                                getXGPushReceiver(username);

                                                PreferenceUtils.setString(LoginActivity.this, "loginName", username);

                                                PreferenceUtils.setString(LoginActivity.this, "city", userBean.getList().getCity());

                                                PreferenceUtils.setString(LoginActivity.this, "settingName", userBean.getList().getUserName());

                                                PreferenceUtils.setInt(LoginActivity.this, "chijiu", userBean.getList().getRole());

                                                if (userBean.getList().getRole() == 2) {

                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    PreferenceUtils.setString(LoginActivity.this, "saleID", userBean.getList().getSaleID() + "");
                                                    PreferenceUtils.setBoolean(LoginActivity.this, "islogin", true);
                                                    hud.dismiss();
                                                    new BaseViewHelper
                                                            .Builder(LoginActivity.this, v)
                                                            .startActivity(intent);
                                                    finish();
                                                } else if (userBean.getList().getRole() == 99) {

                                                    Intent intent = new Intent(LoginActivity.this, EvaluaterActivity.class);
                                                    PreferenceUtils.setString(LoginActivity.this, "saleID", userBean.getList().getSaleID() + "");
                                                    PreferenceUtils.setBoolean(LoginActivity.this, "islogin", true);
                                                    hud.dismiss();
                                                    new BaseViewHelper
                                                            .Builder(LoginActivity.this, v)
                                                            .startActivity(intent);
                                                    finish();

                                                } else if (userBean.getList().getRole() == -1) {

                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                                    PreferenceUtils.setString(LoginActivity.this, "saleID", userBean.getList().getSaleID() + "");
                                                    PreferenceUtils.setBoolean(LoginActivity.this, "islogin", true);
                                                    hud.dismiss();
                                                    new BaseViewHelper
                                                            .Builder(LoginActivity.this, v)
                                                            .startActivity(intent);
                                                    finish();
                                                } else {
                                                    MyToast.show(LoginActivity.this, "未识别的用户");
                                                    hud.dismiss();
                                                }
                                            } else if (userBean.getCode().equals("2")) {
                                                hud.dismiss();

                                                new AlertDialog.Builder(LoginActivity.this)

                                                        .setMessage("检测到您的登录设备发生改变，请进行短信登录！")
                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Intent intent = new Intent(LoginActivity.this, MsgLoginActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                                                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                                                overridePendingTransition(enterAnim0, exitAnim0);
                                                            }
                                                        })
                                                        .setNegativeButton("取消", null)
                                                        .show()
                                                        .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));


                                            }
                                        }
                                    }
                                });
                    } else {
                        MyToast.show(LoginActivity.this, "为获取到手机IMEI码，请打开手机权限");
                    }
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

    @Override
    protected void onStop() {
        super.onStop();
//        bitmap.recycle();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
        Log.e(TAG, "获取成功的权限" + list);

//        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE)) {
//            LogUtils.e("3");
//            //已经获取到权限
////            Toast.makeText(MustPermissionActivity.this, "获取到权限，正常进入", Toast.LENGTH_LONG).show();
//            action();
//
//        } else {
//            LogUtils.e("4");
//            action();
////            MyToast.show(this, "未获取到全部权限");
//        }
//        action();
//        startAnim();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
        Log.e(TAG, "获取失败的权限" + list);
//        action();
//        startAnim();
//        new AppSettingsDialog.Builder(this)
//                .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置屏幕以修改应用权限")
//                .setTitle("必需权限")
//                .build()
//                .show();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

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
                    XGPushManager.deleteTag(LoginActivity.this, username);
                    //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;

            default:
        }
        return super.onKeyUp(keyCode, event);
    }

//    @Override
//    public void onSoftKeyboardOpened(int keyboardSize) {
//        Log.e("wenzhihao", "----->show" + keyboardSize);
//        int[] location = new int[2];
//        body.getLocationOnScreen(location); //获取body在屏幕中的坐标,控件左上角
//        int x = 0;
//        int y = 629;
//        Log.e("wenzhihao", "y = " + y + ",x=" + x);
//        int bottom = 770;
//        Log.e("wenzhihao", "bottom = " + bottom);
//        Log.e("wenzhihao", "con-h = " + body.getHeight());
//        if (keyboardSize > bottom) {
//            ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(body, "translationY", 0.0f, -(keyboardSize - bottom));
//            mAnimatorTranslateY.setDuration(300);
//            mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
//            mAnimatorTranslateY.start();
//            zoomIn(logo, keyboardSize - bottom);
//
//        }
//    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_clean_phone:
                edUsername.setText("");
                break;
            case R.id.clean_password:
                edPassword.setText("");
                break;
            case R.id.iv_show_pwd:
                if (flag == true) {
                    edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    iv_show_pwd.setImageResource(R.drawable.pass_gone);
                    flag = false;
                } else {
                    edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    iv_show_pwd.setImageResource(R.drawable.pass_visuable);
                    flag = true;
                }
                String pwd = edPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    edPassword.setSelection(pwd.length());
                break;
        }
    }

//    @Override
//    public void onSoftKeyboardClosed() {
//        Log.e("wenzhihao", "----->hide");
//        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(body, "translationY", body.getTranslationY(), 0);
//        mAnimatorTranslateY.setDuration(300);
//        mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
//        mAnimatorTranslateY.start();
//        zoomOut(logo);
//    }

    /**
     * f放大
     *
     * @param view
     */
    public void zoomOut(final View view) {
        if (view.getTranslationY() == 0) {
            return;
        }
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();

    }

    /**
     * 缩小
     *
     * @param view
     */
    public void zoomIn(final View view, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX).with(mAnimatorScaleY);

        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();

    }

    @OnClick({R.id.register, R.id.send_message, R.id.forget_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                new BaseViewHelper
                        .Builder(LoginActivity.this, view)
                        .startActivity(intent);
//                finish();
                break;
            case R.id.send_message:
                Intent intent1 = new Intent(LoginActivity.this, MsgLoginActivity.class);
                new BaseViewHelper
                        .Builder(LoginActivity.this, view)
                        .startActivity(intent1);
                finish();
                break;
            case R.id.forget_password:
                Intent intent2 = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                new BaseViewHelper
                        .Builder(LoginActivity.this, view)
                        .startActivity(intent2);
                break;
        }
    }


    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {

    }

    @Override
    public void onSoftKeyboardClosed() {

    }
}

