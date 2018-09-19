package com.feifandaiyu.feifan.activity.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.MsgBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.feifandaiyu.feifan.view.BaseViewHelper;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.feifandaiyu.feifan.R.id.iv_show_pwd;

/**
 * @author houdaichang
 */
public class ResetPasswordActivity extends AppCompatActivity implements TextWatcher {

    @InjectView(R.id.back_black)
    ImageView backBlack;
    @InjectView(R.id.et_new_password)
    EditText etNewPassword;
    @InjectView(R.id.iv_clean_phone)
    ImageView ivCleanPhone;
    @InjectView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @InjectView(R.id.clean_password)
    ImageView cleanPassword;
    @InjectView(iv_show_pwd)
    ImageView ivShowPwd;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    private boolean flag = false;
    private String newPassword;
    private KProgressHUD hud;
    private String username;
    private BaseViewHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
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

        startTranslationNoShowTranslation();

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        etNewPassword.addTextChangedListener(this);
        etConfirmPassword.addTextChangedListener(this);

        username = getIntent().getStringExtra("phone");
        LogUtils.e(username);
    }

    private void startTranslationNoShowTranslation() {
        helper = new BaseViewHelper
                .Builder(ResetPasswordActivity.this)
                .isFullWindow(true)//是否全屏显示
                .isShowTransition(true)//是否显示过渡动画
                .setDimColor(Color.WHITE)//遮罩颜色
                .setDimAlpha(200)//遮罩透明度
                .setDuration(400)
                .create();//开始动画
    }

    @OnClick({R.id.back_black, R.id.iv_clean_phone, R.id.clean_password, iv_show_pwd, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_black:
                if (helper!=null && helper.isShowing()){
                    helper.backActivity(this);
                }
                finish();
                break;
            case R.id.iv_clean_phone:
                etNewPassword.setText("");
                break;
            case R.id.clean_password:
                etConfirmPassword.setText("");
                break;
            case iv_show_pwd:
                if (flag == true) {
                    etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivShowPwd.setImageResource(R.drawable.pass_gone);
                    flag = false;
                } else {
                    etConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivShowPwd.setImageResource(R.drawable.pass_visuable);
                    flag = true;
                }
                String pwd = etConfirmPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    etConfirmPassword.setSelection(pwd.length());
                break;
            case R.id.btn_login:
                if (etNewPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {

                    changePassword();
                } else {
                    MyToast.show(ResetPasswordActivity.this, "两次输入的密码不一样，请重新输入");
                }
                break;
        }
    }

    private void changePassword() {
        newPassword = etNewPassword.getText().toString().trim();

        hud.show();

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Appraiser/respwd")
                .addParams("adminName", username)
                .addParams("pwd", newPassword)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.d("ResetPasswordActivity----------------->>>>" + e + "失败");
                        hud.dismiss();
                        MyToast.show(ResetPasswordActivity.this, "链接服务器失败，请联系管理员");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("ResetPasswordActivity----------------->>>>" + response + "成功");
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        MsgBean bean = gson.fromJson(json, MsgBean.class);
                        if (bean.getCode() == 1) {
                            MyToast.show(ResetPasswordActivity.this, bean.getMsg());
                            finish();
                        } else {
                            MyToast.show(ResetPasswordActivity.this, bean.getMsg());
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
        if (!StringUtils.isEmpty(etNewPassword.getText().toString()) && ivCleanPhone.getVisibility() == View.GONE) {
            ivCleanPhone.setVisibility(View.VISIBLE);
        } else if (StringUtils.isEmpty(etNewPassword.getText().toString())) {
            ivCleanPhone.setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(etConfirmPassword.getText().toString()) && cleanPassword.getVisibility() == View.GONE) {
            cleanPassword.setVisibility(View.VISIBLE);
        } else if (StringUtils.isEmpty(etConfirmPassword.getText().toString())) {
            cleanPassword.setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(etNewPassword.getText().toString())
                && !StringUtils.isEmpty(etConfirmPassword.getText().toString())) {
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
