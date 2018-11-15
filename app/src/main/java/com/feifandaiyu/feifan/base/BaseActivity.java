package com.feifandaiyu.feifan.base;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.service.ScreenRecordService;

/**
 * Created by houdaichang on 2017/5/2.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private FrameLayout viewContent;
    private TextView tvTitle;
    private ImageView ivBack;
    private TextView tvNxt;
    private boolean isRecord = false;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mScreenDensity;
    private MediaScannerConnection mediaScannerConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        viewContent = (FrameLayout) findViewById(R.id.viewContent);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        setSupportActionBar(toolBar);

        //设置不显示自带的title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //将继承BaseActivity的布局解析到Framelayout里
        LayoutInflater.from(this).inflate(getContentView(), viewContent);

        init(savedInstanceState);

        getScreenBaseInfo();

//        startScreenRecord();

        mediaScannerConnection = new MediaScannerConnection(getApplicationContext(), null);
        mediaScannerConnection.connect();
    }

    protected abstract int getContentView();

    protected abstract void init(Bundle savedInstanceState);

    /**
     * 获取屏幕基本信息
     */
    private void getScreenBaseInfo() {
        //A structure describing general information about a display, such as its size, density, and font scaling.
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
        mScreenDensity = metrics.densityDpi;
    }

    //start screen record
    protected void startScreenRecord() {
        //Manages the retrieval of certain types of MediaProjection tokens.
        MediaProjectionManager mediaProjectionManager =
                (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        //Returns an Intent that must passed to startActivityForResult() in order to start screen capture.
        Intent permissionIntent = mediaProjectionManager.createScreenCaptureIntent();
        startActivityForResult(permissionIntent, 1000);

        isRecord = true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopScreenRecord();
        scan();
        if (mediaScannerConnection != null) {
            //释放连接
            mediaScannerConnection.disconnect();
        }
    }



    private void scan() {
        mediaScannerConnection.scanFile(Environment.getExternalStorageDirectory().getPath(), "video/mp4");
    }

    //stop screen record.
    private void stopScreenRecord() {
        Intent service = new Intent(this, ScreenRecordService.class);
        stopService(service);
        isRecord = false;
        Toast.makeText(this, "录屏成功", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                //获得录屏权限，启动Service进行录制
                Intent intent = new Intent(BaseActivity.this, ScreenRecordService.class);
                intent.putExtra("resultCode", resultCode);
                intent.putExtra("resultData", data);
                intent.putExtra("mScreenWidth", mScreenWidth);
                intent.putExtra("mScreenHeight", mScreenHeight);
                intent.putExtra("mScreenDensity", mScreenDensity);
                startService(intent);
                Toast.makeText(this, "录屏开始", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "录屏失败", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //设置标题
    protected void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
    }

    protected void showNext(boolean next) {
        tvNxt = (TextView) findViewById(R.id.tv_next);

        if (next) {
            tvNxt.setVisibility(View.VISIBLE);
        } else {

            tvNxt.setVisibility(View.GONE);
        }
    }

    protected void showBack(boolean show) {

        ivBack = (ImageView) findViewById(R.id.iv_back);
        if (show) {

            ivBack.setVisibility(View.VISIBLE);
        } else {
            ivBack.setVisibility(View.GONE);
        }

    }
}
