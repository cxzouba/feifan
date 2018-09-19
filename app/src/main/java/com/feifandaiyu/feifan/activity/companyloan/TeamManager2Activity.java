package com.feifandaiyu.feifan.activity.companyloan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.TeamManager2Adappter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.TeamMannerBean;
import com.feifandaiyu.feifan.bean.TeamMeanager2ActivityBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by davidzhao on 2017/5/16.
 */

public class TeamManager2Activity extends BaseActivity {

    @InjectView(R.id.lv_manger)
    ListView lvManger;
    @InjectView(R.id.bt_start_loan)
    Button btStartLoan;
    @InjectView(R.id.et_remarks)
    EditText etRemarks;
    private ListView lv;
    private String saleID;
    private String companyId;
    private String admin_id;
    private KProgressHUD hud;
    private String loginName;

    @Override
    protected int getContentView() {
        return R.layout.activity_teammanger;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        ButterKnife.inject(this);
        setTitle("发起贷款");
        showNext(false);

        hud = KProgressHUD.create(TeamManager2Activity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);


        saleID = PreferenceUtils.getString(this, "saleID");

        companyId = PreferenceUtils.getString(this, "userId");

        loginName = PreferenceUtils.getString(this, "loginName");


        etRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!StringUtils.isEmpty(etRemarks.getText().toString())) {
                    btStartLoan.setEnabled(true);
                } else {
                    btStartLoan.setEnabled(false);
                }

            }
        });

        initData();
    }

    private void initData() {
        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Company/adminrole")
                .addParams("salename", loginName)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("guanli------------->>>>>>>" + e);
                        MyToast.show(TeamManager2Activity.this, "风控列表获取失败");
                    }


                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("------------->>>>>>>" + response);
                        String json = response;
                        Gson gson = new Gson();
                        TeamMannerBean bean = gson.fromJson(json, TeamMannerBean.class);
                        final List<TeamMannerBean.ListBean> list = bean.getList();
                        lv = (ListView) findViewById(R.id.lv_manger);
                        lv.setAdapter(new TeamManager2Adappter(TeamManager2Activity.this, list, new TeamManager2Adappter.OnRadiobuttonclick() {
                            @Override
                            public void radioButtonck(int position) {
                                admin_id = list.get(position).getAdmin_id();
                            }
                        }));
                    }
                });
    }


    @OnClick({R.id.iv_back, R.id.bt_start_loan})
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

            case R.id.bt_start_loan:
                if (admin_id != null) {
                    hud.show();

                    uploadMessage();

                    finish();
                } else {
                    MyToast.show(this, "当前风控未选择");
                }
                break;

        }
    }

    private void uploadMessage() {
        OkHttpUtils
                .post()
                //http://byu2763230001.my3w.com/public/index.php/info/Company/trackuser
                .url(Constants.URLS.BASEURL + "Company/track")
                .addParams("company_id", companyId)
                .addParams("next_opreatorid", admin_id)
                .addParams("operatorid", saleID)
                .addParams("remark", etRemarks.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("teamManager2----------->>>>>" + e);
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("teamManager2----------->>>>>～～～～～" + response);

                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        TeamMeanager2ActivityBean bean = gson.fromJson(json, TeamMeanager2ActivityBean.class);
                        if (bean.getCode() == 1) {
                            startActivity(new Intent(TeamManager2Activity.this, CompanyLoanActivity.class));
                        } else {
                            MyToast.show(TeamManager2Activity.this, bean.getMsg());
                        }


                    }

                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    public interface OnRadiobuttonclick {
        void radioButtonck(int position);

    }
}
