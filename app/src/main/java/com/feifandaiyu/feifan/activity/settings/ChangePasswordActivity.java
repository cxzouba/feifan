package com.feifandaiyu.feifan.activity.settings;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.ChangePasswordBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by houdaichang on 2017/5/4.
 */

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    @InjectView(R.id.et_old_password)
    PowerfulEditText etOldPassword;
    @InjectView(R.id.et_new_password)
    PowerfulEditText etNewPassword;
    @InjectView(R.id.et_confirm_password)
    PowerfulEditText etConfirmPassword;
    @InjectView(R.id.button)
    Button button;
    private ImageView ivBack;

    @Override
    protected int getContentView() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        ivBack = (ImageView) findViewById(R.id.iv_back);
        showBack(true);
        showNext(false);

        ButterKnife.inject(this);
        setTitle("修改密码");

        etOldPassword.addTextChangedListener(this);
        etNewPassword.addTextChangedListener(this);
        etConfirmPassword.addTextChangedListener(this);

        ivBack.setOnClickListener(this);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim0, exitAnim0);
                break;
            case R.id.button:
                if (etNewPassword.getText().toString().trim().equals(etConfirmPassword.getText().toString().trim())) {
                    OkHttpUtils
                            .post()
                            //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                            .url(Constants.URLS.BASEURL + "Company/editpass")
                            .addParams("admin_id", "5")
                            .addParams("newpassword", "123457")
                            .addParams("oldpassword", "123457")
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    System.out.println("____________________>>>>>" + e);
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    LogUtils.e("----------------->>>>>>." + response);
                                    String json = response;
                                    Gson gson = new Gson();
                                    ChangePasswordBean bean = gson.fromJson(json, ChangePasswordBean.class);

                                    if (bean.getCode() == 0) {
                                        MyToast.show(ChangePasswordActivity.this, bean.getMsg());
                                    } else {
                                        MyToast.show(ChangePasswordActivity.this, bean.getMsg());
                                        finish();
                                    }
                                }
                            });

                } else {
                    MyToast.show(this, "两次输入的密码不一样");
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!StringUtils.isEmpty(etConfirmPassword.getText().toString())
                && !StringUtils.isEmpty(etNewPassword.getText().toString())
                && !StringUtils.isEmpty(etOldPassword.getText().toString())
                )

        {
            button.setEnabled(true);
        } else {
            button.setEnabled(false);
        }
    }
}
