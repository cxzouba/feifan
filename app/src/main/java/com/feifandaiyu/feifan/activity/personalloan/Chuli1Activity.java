package com.feifandaiyu.feifan.activity.personalloan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.ChuLiBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

public class Chuli1Activity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.tv_chuli_who)
    TextView tvChuliWho;
    @InjectView(R.id.tv_chuli_time)
    TextView tvChuliTime;
    @InjectView(R.id.tv_chuli_result)
    TextView tvChuliResult;

    private ImageView iv_back;
    private KProgressHUD hud;


    @Override
    protected int getContentView() {
        return R.layout.activity_chuli;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

        hud = KProgressHUD.create(Chuli1Activity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        setTitle("处理意见");
        showNext(false);

        iv_back = (ImageView) findViewById(R.id.iv_back);

        iv_back.setOnClickListener(this);

        initData();


    }

    private void initData() {

        String id = PreferenceUtils.getString(Chuli1Activity.this, "chuliId1");

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Login/showTrack")
                .addParams("id", id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        System.out.println("chuliactivity____________________>>>>>" + e);

                        MyToast.show(Chuli1Activity.this, "联网失败");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("chuliactivity----------------->>>>>>" + response);

                        hud.dismiss();

                        String json = response;
                        Gson gson = new Gson();
                        ChuLiBean bean = gson.fromJson(json, ChuLiBean.class);

                        String name = PreferenceUtils.getString(Chuli1Activity.this, "chuliName1");

                        tvChuliTime.setText(bean.getList().getTimes());
                        tvChuliResult.setText(bean.getList().getRemark());
                        tvChuliWho.setText(bean.getList().getAuditors());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                startActivity(new Intent(this, GuoCheng1Activity.class));
                finish();
                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim0, exitAnim0);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
