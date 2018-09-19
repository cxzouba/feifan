package com.feifandaiyu.feifan.activity.login;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.view.BaseViewHelper;
import com.feifandaiyu.feifan.view.CountDownTimerButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.feifandaiyu.feifan.R.id.iv_show_pwd;


public class RegisterActivity extends AppCompatActivity {

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
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.clean_password)
    ImageView cleanPassword;
    @InjectView(R.id.iv_show_pwd)
    ImageView ivShowPwd;
    @InjectView(R.id.back_black)
    ImageView backBlack;
    private BaseViewHelper helper;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        ButterKnife.inject(this);
        WindowManager wm = this.getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        startTranslationNoShowTranslation();
    }

    private void startTranslationNoShowTranslation() {
        helper = new BaseViewHelper
                .Builder(RegisterActivity.this)
                .isFullWindow(true)//是否全屏显示
                .isShowTransition(true)//是否显示过渡动画
                .setDimColor(Color.WHITE)//遮罩颜色
                .setDimAlpha(200)//遮罩透明度
                .setDuration(400)
                .setScaleX(2)
                .setScaleY(2)
                .setTranslationX(-(width/2))
                .create();//开始动画
    }

    private boolean flag = false;

    @OnClick({R.id.tv_login, R.id.iv_clean_phone, R.id.tv_timmer, R.id.clean_password, R.id.iv_show_pwd, R.id.back_black})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
//                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                new BaseViewHelper
//                        .Builder(RegisterActivity.this, view)
//                        .startActivity(intent);
                if (helper != null && helper.isShowing()) {
                    helper.backActivity(this);
                }
                finish();
                break;
            case R.id.iv_clean_phone:
                etUsername.setText("");
                break;
            case R.id.tv_timmer:
                if (etUsername.getText().toString().equals("")) {
                    MyToast.show(this, "请填写手机号");
                } else {

                    tvTimmer.startCountDown();
                }
                break;
            case R.id.clean_password:
                etPassword.setText("");
                break;
            case iv_show_pwd:
                if (flag == true) {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivShowPwd.setImageResource(R.drawable.pass_gone);
                    flag = false;
                } else {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivShowPwd.setImageResource(R.drawable.pass_visuable);
                    flag = true;
                }
                String pwd = etPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd)) {
                    etPassword.setSelection(pwd.length());
                }
                break;
            case R.id.back_black:
//                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(intent1);
                if (helper != null && helper.isShowing()) {
                    helper.backActivity(this);
                }
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {

        if (helper != null && helper.isShowing()) {
            helper.backActivity(this);
        } else {
            super.onBackPressed();
        }
    }
}
