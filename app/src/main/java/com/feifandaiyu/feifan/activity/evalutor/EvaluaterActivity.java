package com.feifandaiyu.feifan.activity.evalutor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.settings.SettingActivity;
import com.feifandaiyu.feifan.fragment.DaiPingGuFragment;
import com.feifandaiyu.feifan.fragment.YiPingGuFragment;
import com.feifandaiyu.feifan.utils.FragmentFactory;
import com.feifandaiyu.feifan.utils.SnackbarUtil;
import com.feifandaiyu.feifan.view.BaseViewHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class EvaluaterActivity extends AppCompatActivity {

    @InjectView(R.id.iv_daipinggu)
    ImageView ivDaipinggu;
    @InjectView(R.id.iv_yipinggu)
    ImageView ivYipinggu;
    @InjectView(R.id.iv_setting)
    ImageView ivSetting;
    @InjectView(R.id.fl_container)
    FrameLayout flContainer;
    @InjectView(R.id.root)
    LinearLayout root;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private MyReceiver receiver;
    private BaseViewHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluater);

        ButterKnife.inject(this);
        startTranslation1();
        registerBroadcast();
        initFrament();
    }

    public void startTranslation1() {
        helper = new BaseViewHelper
                .Builder(EvaluaterActivity.this)
                //.setEndView()//如果是两个切换的视图  这里设定最终显示的视图
                //.setTranslationView(viewById)//设置过渡视图
                .isFullWindow(true)//是否全屏显示
                .isShowTransition(true)//是否显示过渡动画
                .setDimColor(Color.WHITE)//遮罩颜色
                .setDimAlpha(200)//遮罩透明度
                .setTranslationX(0)//x轴平移
                .setRotation(360)//旋转
                .setScaleX(0)//x轴缩放
                .setScaleY(0)//y轴缩放
                .setTranslationY(0)//y轴平移
                .setDuration(500)//过渡时长
                .setInterpolator(new AccelerateDecelerateInterpolator())//设置插值器
                //设置监听
                .setOnAnimationListener(new BaseViewHelper.OnAnimationListener() {
                    @Override
                    public void onAnimationStartIn() {
                        Log.e("TAG", "onAnimationStartIn");
                    }

                    @Override
                    public void onAnimationEndIn() {
                        Log.e("TAG", "onAnimationEndIn");
                    }

                    @Override
                    public void onAnimationStartOut() {
                        Log.e("TAG", "onAnimationStartOut");
                    }

                    @Override
                    public void onAnimationEndOut() {
                        Log.e("TAG", "onAnimationEndOut");
                    }
                })
                .create();//开始动画
    }

    private void initFrament() {
        ivDaipinggu.setSelected(true);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fl_container, FragmentFactory.getInstance(fragments[0]), "0");
        ft.commit();
    }


    private void registerBroadcast() {
        // 注册广播接收者
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("exit_app");
        registerReceiver(receiver, filter);
    }

    Class[] fragments = new Class[]{
            DaiPingGuFragment.class,
            YiPingGuFragment.class,

    };

    @OnClick({R.id.iv_daipinggu, R.id.iv_yipinggu, R.id.iv_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_daipinggu:
                ivDaipinggu.setSelected(true);
                ivYipinggu.setSelected(false);

                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fl_container, FragmentFactory.getInstance(fragments[0]), "0");
                ft.commit();

                break;

            case R.id.iv_yipinggu:
                ivDaipinggu.setSelected(false);
                ivYipinggu.setSelected(true);

                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fl_container, FragmentFactory.getInstance(fragments[1]), "1");
                ft.commit();

                break;

            case R.id.iv_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }


    /**
     * 连按两次退出程序
     */
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

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

    @Override
    protected void onResume() {
        super.onResume();
    }
}
