package com.feifandaiyu.feifan.activity.companyloan;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.CompanyLoanAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CompanyLoanURLBean;
import com.feifandaiyu.feifan.bean.ProgressBean;
import com.feifandaiyu.feifan.bean.ZuoFeiBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.dialog.SelfDialog;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.Resource2Bitmap;
import com.feifandaiyu.feifan.view.BaseViewHelper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigInput;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * Created by davidzhao on 2017/5/8.
 */

public class CompanyLoanActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.lv_companyloan)
    ListView lvCompanyloan;
    @InjectView(R.id.rl_company)
    RelativeLayout rlCompany;
    @InjectView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private TextView text;
    private ImageView ivBack;
    private TextView tvNext;
    private List<CompanyLoanURLBean.ListBean> list1;
    private CompanyLoanAdapter adapter;
    private SelfDialog selfDialog;
    private String saleID;
    private Bitmap bitmap;
    private BitmapDrawable bmdrawable;
    private KProgressHUD hud;
    private int firstVisiblePosition;
    private BaseViewHelper helper;

    @Override
    protected int getContentView() {
        return R.layout.activity_companyloan;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

//        startTranslationNoFullWindow();

        setTitle("企业贷款");

        bitmap = Resource2Bitmap.readBitMap(this, R.drawable.jiazai);
        bmdrawable = new BitmapDrawable(bitmap);
        rlCompany.setBackground(bmdrawable);

        text = (TextView) findViewById(R.id.tv_next);
        text.setText("发起贷款");
        showNext(true);
        text.setEnabled(true);
        text.setOnClickListener(this);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);

        hud = KProgressHUD.create(CompanyLoanActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setWindowColor(Color.GRAY)
                .show();


        lvCompanyloan.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                firstVisiblePosition = lvCompanyloan.getFirstVisiblePosition();

                PreferenceUtils.setString(CompanyLoanActivity.this, "userId", list1.get(position).getId());

                if (Build.VERSION.SDK_INT >= 21) {

                    startActivity(new Intent(CompanyLoanActivity.this, FullInfoActivity2.class), ActivityOptions.makeSceneTransitionAnimation(CompanyLoanActivity.this).toBundle());

                } else {

                    startActivity(new Intent(CompanyLoanActivity.this, FullInfoActivity2.class));
                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                    overridePendingTransition(enterAnim6, exitAnim6);
                }

            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                firstVisiblePosition = 0;
                initData();
            }
        });


    }

    public void startTranslationNoFullWindow() {
        helper = new BaseViewHelper
                .Builder(this)
                .isFullWindow(true)//是否全屏显示
                .isShowTransition(false)//是否显示过渡动画
                .setDimColor(Color.WHITE)//遮罩颜色
                .setDimAlpha(200)//遮罩透明度
                .setTranslationY(-200)
                .create();//开始动画
    }

    private void initData() {

        saleID = PreferenceUtils.getString(this, "saleID");

        post()
                .url(Constants.URLS.BASEURL + "Company/showcompany")
                .addParams("admin_id", saleID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("companyLoanActivity____________________>>>>>" + e);
                        hud.dismiss();
//                        refreshCompany.setRefreshing(false);
                        refreshLayout.finishRefresh();
                        MyToast.show(CompanyLoanActivity.this, "加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("companyLoanActivity----------------->>>>>>" + response);
                        String json = response;
                        Gson gson = new Gson();
                        CompanyLoanURLBean bean = null;

//                        startTranslationNoFullWindow();

                        try {
                            bean = gson.fromJson(json, CompanyLoanURLBean.class);
                            int role = bean.getRole();

                            PreferenceUtils.setInt(CompanyLoanActivity.this, "role", role);

                            list1 = bean.getList();

                            if (list1 == null || list1.size() == 0) {
                                rlCompany.setBackground(bmdrawable);
                            } else {
                                rlCompany.setBackground(null);
                            }

                            adapter = new CompanyLoanAdapter(list1, CompanyLoanActivity.this, lisetener, btnlistener3, btnlistener4, btnlistener5);
                            lvCompanyloan.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            lvCompanyloan.setSelection(firstVisiblePosition);

                            hud.dismiss();

//                        refreshCompany.setRefreshing(false);
                            refreshLayout.finishRefresh();

                        } catch (JsonSyntaxException e) {
                            hud.dismiss();
                            MyToast.show(CompanyLoanActivity.this, response);
                        }

                    }
                });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (helper != null && helper.isShowing()) {
                    helper.backActivity(this);
                }
                finish();
//                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.tv_next:
                startActivity(new Intent(this, ImproveMessage1Activity.class));
                int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim6, exitAnim6);
                break;
        }
    }

    CompanyLoanAdapter.OnTestClickLisetener lisetener = new CompanyLoanAdapter.OnTestClickLisetener() {
        @Override
        public void onTestClick(View view) {
            final TextView tv = (TextView) view;
            new AlertDialog.Builder(CompanyLoanActivity.this)

                    .setMessage("将要拨打电话给" + tv.getText())
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (ActivityCompat.checkSelfPermission(CompanyLoanActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(CompanyLoanActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                            } else {

                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tv.getText()));
                                CompanyLoanActivity.this.startActivity(intent);

                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();

        }
    };

    CompanyLoanAdapter.OnButtonClickLisetrner4 btnlistener4 = new CompanyLoanAdapter.OnButtonClickLisetrner4() {

        @Override
        public void onButtonClick(int positon) {
            PreferenceUtils.setString(CompanyLoanActivity.this, "userId", list1.get(positon).getId());
            firstVisiblePosition = lvCompanyloan.getFirstVisiblePosition();
            startActivity(new Intent(CompanyLoanActivity.this, GuoCheng2Activity.class));
            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
            overridePendingTransition(enterAnim6, exitAnim6);
        }
    };


    CompanyLoanAdapter.OnButtonClickLisetrner5 btnlistener5 = new CompanyLoanAdapter.OnButtonClickLisetrner5() {

        @Override
        public void onButtonClick(final int positon, final View view) {
//            firstVisiblePosition = lvCompanyloan.getFirstVisiblePosition();
//            PreferenceUtils.setString(PersonalLoanActivity.this, "userId", list1.get(positon).getId());
//            startActivity(new Intent(PersonalLoanActivity.this, TeamManager1Activity.class));
//            // // 设置过渡动画
//            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
//            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
//            overridePendingTransition(enterAnim6, exitAnim6);
            final CircleDialog.Builder builder = new CircleDialog.Builder(CompanyLoanActivity.this);
            builder.configInput(new ConfigInput() {
                @Override
                public void onConfig(InputParams params) {
                    params.inputHeight = 150;
                    params.textSize = 40;
                }
            })
                    .setInputHint("请输入作废原因")
                    .setTitle("是否作废？")
                    .setPositiveInput("确定", new OnInputClickListener() {
                        @Override
                        public void onClick(String text, View v) {
//                            Toast.makeText(PersonalLoanActivity.this,text,Toast.LENGTH_SHORT).show();
                            // TODO: 2017/7/27   点击确定 就在这  text 就是 输入的信息

                            hud.show();

                            OkHttpUtils.post()
                                    .url(Constants.URLS.BASEURL + "login/toVoid")
                                    .addParams("remark", text)
                                    .addParams("operatorId", saleID)
                                    .addParams("id", list1.get(positon).getId())
                                    .addParams("tableName", "company")
                                    .build()
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e, int id) {
                                            System.out.println("zuofei____________________>>>>>" + e);
                                            hud.dismiss();
                                            MyToast.show(CompanyLoanActivity.this, "加载失败");
                                        }

                                        @Override
                                        public void onResponse(String response, int id) {
                                            LogUtils.e("zuofei----------------->>>>>>" + response);
                                            String json = response;
                                            Gson gson = new Gson();
                                            ZuoFeiBean bean = null;
                                            try {
                                                bean = gson.fromJson(json, ZuoFeiBean.class);
                                            } catch (JsonSyntaxException e) {
                                                MyToast.show(CompanyLoanActivity.this, "服务器错误");
                                            }

                                            if (bean.getCode() == 1) {

                                                deletePattern(view, positon);

                                                /*initData();*/

                                            } else if (bean.getCode() == 0) {
                                                MyToast.show(CompanyLoanActivity.this, bean.getMsg());
                                            }

                                            hud.dismiss();
                                        }
                                    });

                        }
                    })
                    .setNegative("取消", null)
                    .show();
        }
    };

    private int stage;
    CompanyLoanAdapter.OnButtonClickLisetrner3 btnlistener3 = new CompanyLoanAdapter.OnButtonClickLisetrner3() {

        @Override
        public void onButtonClick(final int positon) {
            firstVisiblePosition = lvCompanyloan.getFirstVisiblePosition();

            System.out.println("companyloan------------->>>>>" + positon);

            PreferenceUtils.setString(CompanyLoanActivity.this, "userId", list1.get(positon).getId());

            if (list1.get(positon).getFlag().equals("0")) {
                hud.show();

                OkHttpUtils
                        .post()
                        .url(Constants.URLS.BASEURL + "Company/perfectInfo")
                        .addParams("id", list1.get(positon).getId())
                        .build()
                        .execute(new StringCallback() {

                            @Override
                            public void onError(Call call, Exception e, int id) {
                                System.out.println("CompanyProgress____________________>>>>>" + e);
                                hud.dismiss();
                            }

                            @Override
                            public void onResponse(String response, int id) {

                                hud.dismiss();
                                LogUtils.e("companyProgress----------------->>>>>>" + response);
                                String json = response;
                                Gson gson = new Gson();
                                ProgressBean bean = gson.fromJson(json, ProgressBean.class);

                                PreferenceUtils.setString(CompanyLoanActivity.this, "userName", bean.getUserName());

                                stage = bean.getStage();

                                /**
                                 * stage
                                 1 企业信息
                                 2 联系人信息
                                 3 企业照片
                                 4 风控
                                 5 上传视频
                                 */

                                if (stage == 2) {

                                    startActivity(new Intent(CompanyLoanActivity.this, CompanyContactsActivity.class));
                                    // // 设置过渡动画
                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                    overridePendingTransition(enterAnim6, exitAnim6);
//

                                } else if (stage == 3) {
                                    startActivity(new Intent(CompanyLoanActivity.this, CompanyImageUpLoadActivity.class));
                                    // // 设置过渡动画
                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                    overridePendingTransition(enterAnim6, exitAnim6);
                                } else if (stage == 4) {
                                    startActivity(new Intent(CompanyLoanActivity.this, TeamManager2Activity.class));
                                    // // 设置过渡动画
                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                    overridePendingTransition(enterAnim6, exitAnim6);
                                } else if (stage == 5) {
                                    startActivity(new Intent(CompanyLoanActivity.this, UpLoadCompanyVideoActivity.class));
                                    // // 设置过渡动画
                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                    overridePendingTransition(enterAnim6, exitAnim6);
                                }
                            }
                        });
            } else if (list1.get(positon).getFlag().equals("8")) {
                startActivity(new Intent(CompanyLoanActivity.this, TeamManager2Activity.class));
                // // 设置过渡动画
                int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim6, exitAnim6);
            } else {
                startActivity(new Intent(CompanyLoanActivity.this, GuoCheng2Activity.class));
                int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim6, exitAnim6);
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        initData();
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

    private void deletePattern(final View view, final int position) {

        Animation.AnimationListener al = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                list1.remove(position);
                CompanyLoanAdapter.ViewHolder holder = (CompanyLoanAdapter.ViewHolder) view.getTag();
                holder.needInflate = true;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        collapse(view, al);

    }

    private void collapse(final View view, Animation.AnimationListener al) {
        final int originHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1.0f) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = originHeight - (int) (originHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        if (al != null) {
            animation.setAnimationListener(al);
        }
        animation.setDuration(300);
        view.startAnimation(animation);
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
