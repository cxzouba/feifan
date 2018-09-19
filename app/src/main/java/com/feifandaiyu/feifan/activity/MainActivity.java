package com.feifandaiyu.feifan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.fragment.CarLoanFragment;
import com.feifandaiyu.feifan.fragment.TransactionLoanFragment;
import com.feifandaiyu.feifan.fragment.WorkSpaceFragment;
import com.feifandaiyu.feifan.utils.FragmentFactory;
import com.feifandaiyu.feifan.utils.JsonSP;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.SnackbarUtil;
import com.feifandaiyu.feifan.view.BaseViewHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.refactor.lib.colordialog.PromptDialog;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.contaier)
    FrameLayout contaier;
    @InjectView(R.id.bottomNavigationBar)
    BottomNavigationBar bottomNavigationBar;
    @InjectView(R.id.root)
    LinearLayout root;
    private BadgeItem badgeItem;
    private String[] barLabels;
    private ImageView ivSetting;
    private TextView tvPg;
    private String saleID;
    private ImageView ivCamera;
    private MyReceiver receiver;
    private MyXGReceiver xgreceiver;
    private Message m;
    private ImageView ivjisuanqi;
    private TextView tvCity;
    private BaseViewHelper helper;
    private ImageView ivPoint;
    public int redPoint = 0;
    private int po = 0;
    private Toolbar toolBar;
    private PromptDialog promptDialog;
    //

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
//        startTranslationShowTranslationY();
        Intent intent = getIntent();
        saleID = intent.getStringExtra("saleID");
        JsonSP.setString(this, "saleID", saleID);
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        ivCamera = (ImageView) findViewById(R.id.iv_camera);
        ivPoint = (ImageView) findViewById(R.id.iv_point);
        ivSetting = (ImageView) findViewById(R.id.iv_setting);
        tvPg = (TextView) findViewById(R.id.tv_next);
        ivjisuanqi = (ImageView) findViewById(R.id.iv_jisuanqi);
        tvCity = (TextView) findViewById(R.id.tv_city);
        PreferenceUtils.setInt(this, "redsetting", 0);
        showBack(false);
        showNext(false);
        setTitle("非凡车贷");
        registerBroadcast();
        registerXGBroadcast();
        initBottomNavigationBar();
        initFragment();
        initNotice();

    }

    private void initNotice() {
        promptDialog = new PromptDialog(MainActivity.this);
        promptDialog.setCanceledOnTouchOutside(false);
        promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_INFO)
                .setAnimationEnable(true)
                .setTitleText("通知")
                .setContentText("您有新的消息，请去 设置-我的消息 中查看")
                .setPositiveListener("知道啦", new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                });
    }


    private void startTranslationShowTranslationY() {
        helper = new BaseViewHelper
                .Builder(MainActivity.this)
                .isFullWindow(true)//是否全屏显示
                .isShowTransition(true)//是否显示过渡动画
                .setDimColor(Color.WHITE)//遮罩颜色
                .setDimAlpha(200)//遮罩透明度
                .setTranslationY(-600)
                .setInterpolator(new AnticipateOvershootInterpolator())
                .create();//开始动画
    }

    private void registerBroadcast() {
        // 注册广播接收者
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("exit_app");
        registerReceiver(receiver, filter);

    }

    private void registerXGBroadcast() {
        // 注册广播接收者
        xgreceiver = new MyXGReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("update");
        registerReceiver(xgreceiver, filter);
    }

    Class[] fragments = new Class[]{
            WorkSpaceFragment.class,
            TransactionLoanFragment.class,
            CarLoanFragment.class,
    };

    //初始化Fragment
    private void initFragment() {
        //把TransactionLoanFragment显示在FrameLayout里面
        FragmentManager fm = getSupportFragmentManager();//获取Fragment管理器
        FragmentTransaction ft = fm.beginTransaction();//开启事务
        ft.add(R.id.contaier, FragmentFactory.getInstance(fragments[0]), "0");//把交易贷的Fragment添加到Container
        ft.commit();//提交
    }

    //底部导航条图片资源
    int[] barIcons = new int[]{
            R.drawable.home,
            R.drawable.trans,
            R.drawable.car,
    };

    //初始化底部导航条
    private void initBottomNavigationBar() {
        //底部导航的标签
        barLabels = getResources().getStringArray(R.array.bottombarlabel);

        //未读消息提醒
       /* BadgeItem badgeItem = new BadgeItem();
        badgeItem.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
        badgeItem.setText("10");
        badgeItem.show();*/


        badgeItem = new BadgeItem()
                .setGravity(Gravity.TOP | Gravity.RIGHT)
                .setText("1")
        ;//设置位置（靠顶部|水平居中）

        bottomNavigationBar.setPadding(2, 2, 2, 2);

        bottomNavigationBar
                .setBarBackgroundColor(R.color.bottombarcolor)//设置底部导航条的颜色
                .addItem(new BottomNavigationItem(barIcons[0], barLabels[0]).setBadgeItem(badgeItem))//添加tab
                .addItem(new BottomNavigationItem(barIcons[1], barLabels[1]))
                .addItem(new BottomNavigationItem(barIcons[2], barLabels[2]))
//                .addItem(new BottomNavigationItem(barIcons[3], barLabels[3]).setBadgeItem(badgeItem))
                .setActiveColor(R.color.activecolor)//设置选中的颜色
                .setInActiveColor(R.color.inactivecolor)//设置未选中该的颜色
                .setFirstSelectedPosition(0)//让第一个item选中
                .setMode(BottomNavigationBar.MODE_FIXED)//设置为混合模式
                .initialise();//初始化


        badgeItem.hide();
        //给Tab设置点击监听
        bottomNavigationBar.setTabSelectedListener(new MyTabSelectedListener());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    private class MyTabSelectedListener implements BottomNavigationBar.OnTabSelectedListener {

        //tab选中调用（显示Fragment）
        @Override
        public void onTabSelected(int position) {
//            MyLogger.i("onTabSelected:"+position);
            //1 通过FragmentManager获取Fragment 2 如果不存在，就通过FragmentFactory创建 3 如果存在，显示
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag(position + "");
            FragmentTransaction ft = fm.beginTransaction();

            if (fragment == null) {
                //添加
                ft.add(R.id.contaier, FragmentFactory.getInstance(fragments[position]), position + "");
            } else {

                if (position == 0) {

                    po = 0;
                    LogUtils.e("0");
                    if (redPoint == 1) {
                        ivPoint.setVisibility(View.VISIBLE);
                    } else {
                        ivPoint.setVisibility(View.GONE);
                    }

                    ivCamera.setVisibility(View.GONE);
                    ivSetting.setVisibility(View.VISIBLE);
                    tvPg.setVisibility(View.GONE);
                    ivjisuanqi.setVisibility(View.GONE);
                    tvCity.setVisibility(View.VISIBLE);

                } else if (position == 1) {
                    po = 1;
                    LogUtils.e("1");
                    ivPoint.setVisibility(View.GONE);
                    ivCamera.setVisibility(View.VISIBLE);
                    ivSetting.setVisibility(View.GONE);
                    tvPg.setVisibility(View.GONE);
                    tvCity.setVisibility(View.GONE);
                    ivjisuanqi.setVisibility(View.VISIBLE);

                } else if (position == 2) {
                    po = 2;
                    LogUtils.e("2");
                    ivPoint.setVisibility(View.GONE);
                    ivCamera.setVisibility(View.GONE);
                    ivSetting.setVisibility(View.GONE);
                    tvCity.setVisibility(View.GONE);
                    tvPg.setText("车辆评估");
                    tvPg.setVisibility(View.VISIBLE);
                    ivjisuanqi.setVisibility(View.GONE);
                }
                //显示
                ft.show(fragment);
            }
            ft.commit();

            setTitle(barLabels[position]);

        }

        //之前选中的tab未被选中调用(隐藏Fragment)
        @Override
        public void onTabUnselected(int position) {
//            MyLogger.i("onTabUnselected:"+position);
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag(position + "");
            FragmentTransaction ft = fm.beginTransaction();
            ft.hide(fragment);
            ft.commit();

        }

        @Override
        public void onTabReselected(int position) {
//            MyLogger.i("onTabReselected:"+position);
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            //把当前的Activity移至到后台
//            //是否为任务栈的根   false:只能够处理点击App图标进入的Activity(Splash)  true:根对所有的Activity
//            moveTaskToBack(false);
//            return true;//自己处理后退键的行为
//        }
//        return super.onKeyDown(keyCode, event);
//
//    }


    @Override
    protected void onPause() {
        super.onPause();
        System.gc();
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    /**
     * 连按两次退出程序
     */
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出

//                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    SnackbarUtil.ShortSnackbar(root, "再按一次退出", SnackbarUtil.Normal).show();


                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                                                    //两次按键小于2秒时，退出应用
                    System.exit(0);
                    unregisterReceiver(receiver);
                    unregisterReceiver(xgreceiver);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("exit_app")) {
                finish();
            }
        }
    }

    class MyXGReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("update")) {

//                MyToast.show(MainActivity.this, "您有新的通知");

                if (!promptDialog.isShowing()) {
                    promptDialog.show();
                }

                if (po == 0) {
                    ivPoint.setVisibility(View.VISIBLE);
                } else {
                    ivPoint.setVisibility(View.GONE);
                }

                redPoint = 1;

                PreferenceUtils.setInt(MainActivity.this, "redsetting", 1);

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        int red = PreferenceUtils.getInt(MainActivity.this, "redpoint", 1);

        if (red == 0) {

            redPoint = red;

        }
    }
}