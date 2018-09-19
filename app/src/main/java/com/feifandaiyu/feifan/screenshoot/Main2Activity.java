package com.feifandaiyu.feifan.screenshoot;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.ScreenShotBitmap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Main2Activity extends AppCompatActivity {

    @InjectView(R.id.textView5)
    TextView textView5;
    @InjectView(R.id.button4)
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.button4)
    public void onViewClicked() {
        Bitmap bitmap = ScreenShotBitmap.activityShot(this);
        String s = ScreenShotBitmap.saveToSD(bitmap);
        if (s==null){
            MyToast.show(Main2Activity.this,"保存失败");
        }else{
            MyToast.show(Main2Activity.this,"保存成功");
        }
    }
}
