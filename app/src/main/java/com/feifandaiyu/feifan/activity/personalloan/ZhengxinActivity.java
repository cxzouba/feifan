package com.feifandaiyu.feifan.activity.personalloan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.FuwuBean;
import com.feifandaiyu.feifan.bean.MsgBean;
import com.feifandaiyu.feifan.bean.ZhengxinBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

public class ZhengxinActivity extends BaseActivity {

    @InjectView(R.id.tv_customer_name)
    TextView tvCustomerName;
    @InjectView(R.id.tv_certificate_nun)
    TextView tvCertificateNun;
    @InjectView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @InjectView(R.id.tv_contact1_name)
    TextView tvContact1Name;
    @InjectView(R.id.tv_contact1_nun)
    TextView tvContact1Nun;
    @InjectView(R.id.tv_contact1_relation)
    TextView tvContact1Relation;
    @InjectView(R.id.tv_contact2_name)
    TextView tvContact2Name;
    @InjectView(R.id.tv_contact2_nun)
    TextView tvContact2Nun;
    @InjectView(R.id.tv_contact2_relation)
    TextView tvContact2Relation;
    @InjectView(R.id.et_service_num)
    EditText etServiceNum;
    @InjectView(R.id.bt_confirm_setvice)
    Button btConfirmSetvice;
    @InjectView(R.id.et_msg_num)
    EditText etMsgNum;
    @InjectView(R.id.bt_confirm_msg)
    Button btConfirmMsg;
    private String userId;
    private KProgressHUD hud;
    private String customer_id;
    private FuwuBean fuwuBean;

    @Override
    protected int getContentView() {
        return R.layout.activity_zhengxin;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        ButterKnife.inject(this);
        setTitle("征信查询");
        showNext(false);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍候")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        userId = PreferenceUtils.getString(this, "userId");

        initData();

    }

    private void initData() {

        OkHttpUtils.post()
                .url(Constants.URLS.BASEURL + "Credit/juxinli")
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("zhengxin----------------->>>>>>." + e);
                        hud.dismiss();
                        MyToast.show(ZhengxinActivity.this, "联网失败...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("zhengxin----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();
                        ZhengxinBean bean = gson.fromJson(json, ZhengxinBean.class);

                        hud.dismiss();

                        if (bean.getCode() == 1) {

                            tvCustomerName.setText(bean.getUser().get(0).getUsername());
                            tvCertificateNun.setText(bean.getUser().get(0).getIdCode());
                            tvPhoneNum.setText(bean.getUser().get(0).getMobile());
                            tvContact1Name.setText(bean.getContact().get(0).getUsername());
                            tvContact1Nun.setText(bean.getContact().get(0).getMobile());
                            tvContact1Relation.setText(bean.getContact().get(0).getRelation());
                            tvContact2Name.setText(bean.getContact().get(1).getUsername());
                            tvContact2Nun.setText(bean.getContact().get(1).getMobile());
                            tvContact2Relation.setText(bean.getContact().get(1).getRelation());

                            customer_id = bean.getUser().get(0).getUserId();

                        } else if (bean.getCode() == 0) {

                            new AlertDialog.Builder(ZhengxinActivity.this)

                                    .setMessage(bean.getMsg())
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                            int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                            overridePendingTransition(enterAnim0, exitAnim0);
                                        }
                                    })
                                    .setCancelable(false)
                                    .show();
                        }
                    }
                });

    }

    @OnClick({R.id.bt_confirm_setvice, R.id.bt_confirm_msg, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_confirm_setvice:

                if (etServiceNum.getText().toString().equals("")) {
                    MyToast.show(ZhengxinActivity.this, "请填写服务密码");
                    return;
                }

                hud.show();

                OkHttpUtils.post()
                        .url(Constants.URLS.BASEURL + "Credit/juxinliSave")
                        .addParams("userId", customer_id)
                        .addParams("mobileCode", etServiceNum.getText().toString().trim())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                LogUtils.i("fuwu----------------->>>>>>." + e);
                                hud.dismiss();
                                MyToast.show(ZhengxinActivity.this, "联网失败...");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.i("fuwu----------------->>>>>>." + response);
                                String json = response;
                                Gson gson = new Gson();
                                fuwuBean = gson.fromJson(json, FuwuBean.class);

                                hud.dismiss();

                                if (fuwuBean.getCode() == 1) {

                                    if (fuwuBean.getProcess_code() == 10002) {
                                        MyToast.show(ZhengxinActivity.this, "请填写短信验证码进行验证");

                                    } else if (fuwuBean.getProcess_code() == 10008) {

                                        new AlertDialog.Builder(ZhengxinActivity.this)

                                                .setMessage("征信查询成功")
                                                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        startActivity(new Intent(ZhengxinActivity.this, StopHomeVisitActivity.class));
                                                        finish();
                                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                                        overridePendingTransition(enterAnim6, exitAnim6);
                                                    }
                                                })
                                                .setCancelable(false)
                                                .show();

                                    } else {

                                        new AlertDialog.Builder(ZhengxinActivity.this)

                                                .setMessage(fuwuBean.getMsg())
                                                .setPositiveButton("知道了", null)
                                                .setCancelable(false)
                                                .show();
                                    }

                                } else if (fuwuBean.getCode() == 0) {

                                    new AlertDialog.Builder(ZhengxinActivity.this)
                                            .setMessage(fuwuBean.getMsg())
                                            .setPositiveButton("知道了", null)
                                            .setCancelable(false)
                                            .show();

                                }
                            }
                        });
                break;

            case R.id.bt_confirm_msg:

                if (etMsgNum.getText().toString().equals("")) {
                    MyToast.show(ZhengxinActivity.this, "请填写短信验证码");
                    return;
                }

                if (fuwuBean == null) {
                    MyToast.show(ZhengxinActivity.this, "请先验证服务密码");
                    return;
                }

                hud.show();

                OkHttpUtils.post()
                        .url(Constants.URLS.BASEURL + "Credit/mobileCode")
                        .addParams("userId", customer_id)
                        .addParams("code", etMsgNum.getText().toString())
                        .addParams("token", fuwuBean.getToken())
                        .addParams("account", fuwuBean.getAccount())
                        .addParams("password", fuwuBean.getPassword())
                        .addParams("website", fuwuBean.getWebsite())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                LogUtils.i("msg----------------->>>>>>." + e);
                                hud.dismiss();
                                MyToast.show(ZhengxinActivity.this, "联网失败...");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.i("msg----------------->>>>>>." + response);
                                String json = response;
                                Gson gson = new Gson();
                                MsgBean bean = gson.fromJson(json, MsgBean.class);

                                hud.dismiss();

                                if (bean.getCode() == 1) {

                                    new AlertDialog.Builder(ZhengxinActivity.this)

                                            .setMessage("征信查询成功")
                                            .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    startActivity(new Intent(ZhengxinActivity.this, StopHomeVisitActivity.class));
                                                    finish();
                                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                                    overridePendingTransition(enterAnim6, exitAnim6);
                                                }
                                            })
                                            .setCancelable(false)
                                            .show();

                                } else if (bean.getCode() == 0) {
                                    new AlertDialog.Builder(ZhengxinActivity.this)
                                            .setMessage(bean.getMsg())
                                            .setPositiveButton("知道了", null)
                                            .setCancelable(false)
                                            .show();
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

            default:
        }
    }
}
