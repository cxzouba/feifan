package com.feifandaiyu.feifan.activity.personalloan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.GuoChengAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.GuoChengBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;

public class GuoCheng1Activity extends BaseActivity {

    private ListView lvGuocheng;
    private List<GuoChengBean.ListBean> list;
    private GuoChengAdapter adapter;
    private ImageView iv_back;
    private KProgressHUD hud;

    @Override
    protected int getContentView() {
        return R.layout.activity_guo_cheng;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

        setTitle("过程意见");
        showNext(false);

        hud = KProgressHUD.create(GuoCheng1Activity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        lvGuocheng = (ListView) findViewById(R.id.lv_guocheng);

        iv_back = (ImageView) findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim0, exitAnim0);
            }
        });

        initData();
    }

    private void initData() {

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Login/trackList")
                .addParams("flag", "0")
                .addParams("taskId", PreferenceUtils.getString(GuoCheng1Activity.this, "userId"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("guochengactivity____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(GuoCheng1Activity.this, "联网失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("guochengactivity----------------->>>>>>" + response);
                        String json = response;
                        Gson gson = new Gson();
                        GuoChengBean bean = null;
                        try {
                            bean = gson.fromJson(json, GuoChengBean.class);
                        } catch (JsonSyntaxException e) {
                            hud.dismiss();
                            MyToast.show(GuoCheng1Activity.this, "联网失败");
                        }

                        list = bean.getList();
                        LogUtils.i(list.size() + "---------------->>>>>>>>");

                        adapter = new GuoChengAdapter(GuoCheng1Activity.this, list, btnlistener);
                        lvGuocheng.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        hud.dismiss();

                    }
                });

    }

    GuoChengAdapter.OnButtonClickLisetrner btnlistener = new GuoChengAdapter.OnButtonClickLisetrner() {

        @Override
        public void onButtonClick(int positon) {
            System.out.println("------------->>>>>" + positon);

            PreferenceUtils.setString(GuoCheng1Activity.this, "chuliName1", list.get(positon).getName());

            PreferenceUtils.setString(GuoCheng1Activity.this, "chuliId1", list.get(positon).getId());

            startActivity(new Intent(GuoCheng1Activity.this, Chuli1Activity.class));
            finish();
            // // 设置过渡动画
            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
            overridePendingTransition(enterAnim6, exitAnim6);


        }
    };

}
