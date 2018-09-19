package com.feifandaiyu.feifan.activity.settings;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.login.LoginActivity;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.dialog.SelfDialog;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.tencent.android.tpush.XGPushManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.tencent.android.tpush.XGPushManager.registerPush;

/**
 * Created by admin on 2017/5/4.
 */

public class SettingActivity extends BaseActivity {
    @InjectView(R.id.tv_change_password)
    TextView mTvChangePassword;
    @InjectView(R.id.ll_change_password)
    LinearLayout mLlChangePassword;
    @InjectView(R.id.tv_about_us)
    TextView mTvAboutUs;
    @InjectView(R.id.ll_about_us)
    LinearLayout mLlAboutUs;
    @InjectView(R.id.tv_clean_cache)
    TextView mTvCleanCache;
    @InjectView(R.id.ll_clean_cache)
    LinearLayout mLlCleanCache;
    @InjectView(R.id.tv_current_version)
    TextView mTvCurrentVersion;
    @InjectView(R.id.ll_current_version)
    LinearLayout mLlCurrentVersion;
    @InjectView(R.id.bt_exit)
    Button btExit;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.ll_notice)
    LinearLayout llNotice;
    @InjectView(R.id.iv_point_setting)
    ImageView ivPoint;
    @InjectView(R.id.textView15)
    TextView textView15;

    private SelfDialog selfDialog;
    private Handler mHandler = new Handler();
    private MyXGReceiver xgreceiver;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

        String name = PreferenceUtils.getString(this, "settingName");



        tvName.setText(name);

        if ( PreferenceUtils.getInt(this,"redsetting")==1) {

            ivPoint.setVisibility(View.VISIBLE);

        } else if (PreferenceUtils.getInt(this,"redsetting")==0){

            ivPoint.setVisibility(View.GONE);

        }

        showNext(false);

        showBack(true);

        setTitle("设置");

        registerXGBroadcast();
    }


    @OnClick({R.id.ll_change_password, R.id.ll_about_us, R.id.ll_clean_cache, R.id.ll_current_version, R.id.bt_exit, R.id.iv_back, R.id.ll_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_change_password:
                startActivity(new Intent(this, ChangePasswordActivity.class));

                int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim6, exitAnim6);
                break;
            case R.id.ll_about_us:

                break;
            case R.id.ll_notice:
                ivPoint.setVisibility(View.GONE);
                PreferenceUtils.setInt(SettingActivity.this,"redsetting",0);
                startActivity(new Intent(this, NoticeActivity.class));
                break;
            case R.id.ll_clean_cache:
                showAlertExitDialog();
                break;
            case R.id.ll_current_version:

                break;
            case R.id.bt_exit:
                showExitDialog();
                break;
            case R.id.iv_back:
                unregisterReceiver(xgreceiver);
                finish();
                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim0, exitAnim0);
                break;

            default:
        }
    }

    private void showExitDialog() {
//        new AlertDialog.Builder(this)
//
//                .setMessage("是否退出登录？")
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        int role = PreferenceUtils.getInt(SettingActivity.this, "chijiu");
//                        PreferenceUtils.setBoolean(SettingActivity.this, "islogin", false);
//
//
//                        Intent intent = new Intent();
//                        intent.setAction("exit_app");
//                        sendBroadcast(intent);
//
//
//                        Intent intent1 = new Intent(SettingActivity.this, LoginActivity.class);
//                        startActivity(intent1);
//                        finish();
//                    }
//                })
//                .setNegativeButton("取消", null)
//                .show();

        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);

        dialogBuilder
                .withTitle("提示")
                .withMessage("是否退出登录？")
                .withEffect(Effectstype.Shake)
                .withButton1Text("确定")
                .withButton2Text("取消")
                .isCancelableOnTouchOutside(false)
                .setCustomView(R.layout.dialog_layout, this)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        XGPushManager.deleteTag(SettingActivity.this, PreferenceUtils.getString(SettingActivity.this, "loginName"));
                        int role = PreferenceUtils.getInt(SettingActivity.this, "chijiu");
                        registerPush(SettingActivity.this, "*");
                        PreferenceUtils.setBoolean(SettingActivity.this, "islogin", false);
                        Intent intent = new Intent();
                        intent.setAction("exit_app");
                        sendBroadcast(intent);
                        Intent intent1 = new Intent(SettingActivity.this, LoginActivity.class);
                        startActivity(intent1);
                        unregisterReceiver(xgreceiver);
                        finish();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }


    private void showAlertExitDialog() {
        new AlertDialog.Builder(this)
                .setMessage("是否清理缓存？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showProgressDialog();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void showProgressDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("正在清理缓存");

        progressDialog.show();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);//让他显示2秒后，取消ProgressDialog
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }

        });
        t.start();

        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MyToast.show(getApplicationContext(), "缓存清理成功");
                    }
                }, 300);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    private void registerXGBroadcast() {
        // 注册广播接收者
        xgreceiver = new MyXGReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("update");
        registerReceiver(xgreceiver, filter);
    }

    class MyXGReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("update")) {
//                MyToast.show(SettingActivity.this, "您有新的通知");
                ivPoint.setVisibility(View.VISIBLE);
            }
        }
    }

}
