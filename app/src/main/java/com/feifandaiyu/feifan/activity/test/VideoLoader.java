package com.feifandaiyu.feifan.activity.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.feifandaiyu.feifan.R;
import com.mabeijianxi.smallvideorecord2.DeviceUtils;
import com.mabeijianxi.smallvideorecord2.JianXiCamera;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;

import java.io.File;


public class VideoLoader extends AppCompatActivity implements View.OnClickListener {

    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viedocompress);
        initSmallVideo();

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }
    public static void initSmallVideo() {
        // 设置拍摄视频缓存路径
        File dcim = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                JianXiCamera.setVideoCachePath(dcim + "/mabeijianxi/");
            } else {
                JianXiCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
                        "/sdcard-ext/")
                        + "/mabeijianxi/");
            }
        } else {
            JianXiCamera.setVideoCachePath(dcim + "/mabeijianxi/");
        }
        // 初始化拍摄
        JianXiCamera.initialize(false,null);
    }
    @Override
    public void onClick(View v) {
        CpVideo();
    }

   public void CpVideo() {
        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                .smallVideoWidth(360)
                .smallVideoHeight(480)
                .recordTimeMax(6*1000*10*3)
                .maxFrameRate(20)
                .videoBitrate(600*1000)
                .captureThumbnailsTime(1)
                .build();
        MediaRecorderActivity.goSmallVideoRecorder(this, VideoLoader.class.getName(), config);
    }
}
