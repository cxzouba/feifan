package com.feifandaiyu.feifan.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.feifandaiyu.feifan.R;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashActivity extends Activity {

    private final long SPLASH_LENGTH = 2000;
    @InjectView(R.id.ll_splash)
    LinearLayout llSplash;
    @InjectView(R.id.iv_entry)
    ImageView mIVEntry;
    Handler handler = new Handler();
    private View view;
    private String[] perms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏模式
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);
        ButterKnife.inject(this);

//        Bitmap bitmap = Resource2Bitmap.readBitMap(this, R.drawable.splash);
//
//        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
//
//        llSplash.setBackground(bitmapDrawable);
//        initData();
        action();


    }

//    private void startAnim() {
//
//        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIVEntry, "scaleX", 1f, 1.05F);
//        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIVEntry, "scaleY", 1f, 1.05F);
//
//        AnimatorSet set = new AnimatorSet();
//        set.setDuration(2000);
//        set.setDuration(2000).play(animatorX).with(animatorY);
//        set.start();
//
//        set.addListener(new AnimatorListenerAdapter() {
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//
//                startActivity(new Intent(SplashActivity.this, WelcomeGuideActivity.class));
//                SplashActivity.this.finish();
//            }
//        });
//    }


    private void action() {

        handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, WelcomeGuideActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_LENGTH);//2秒后跳转至应用主界面MainActivity
    }

    public String getMapString(Map<String, String> map) {
        String s = null;
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
//            s = "\""+ entry.getKey() + ":" + map.get(entry.getKey()) + "\",";
            s = entry.getKey() + ":" + map.get(entry.getKey()) + ",";
            stringBuffer.append(s);

        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        Log.e("==========>", stringBuffer.toString());
        return "{" + stringBuffer.toString() + "}";
    }

}
