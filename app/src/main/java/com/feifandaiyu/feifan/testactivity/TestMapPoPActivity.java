package com.feifandaiyu.feifan.testactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.popupWindow.MapPopWindow;

public class TestMapPoPActivity extends AppCompatActivity {
    private Button button;
    private LinearLayout relativeLayout;
    private PopupWindow mPopWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testmap);
        button  = (Button) findViewById(R.id.button5);
       relativeLayout = (LinearLayout) findViewById(R.id.rl_te);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
    }

    public void  showPopupWindow(){
        View contentView =  LayoutInflater.from(this).inflate(R.layout.view_visit_time, null);
        mPopWindow  = new PopupWindow(contentView,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);

        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_testmap, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }




/*    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.popuplayout, null);
        mPopWindow = new PopupWindow(contentView,
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        TextView tv1 = (TextView)contentView.findViewById(R.id.pop_computer);
        TextView tv2 = (TextView)contentView.findViewById(R.id.pop_financial);
        TextView tv3 = (TextView)contentView.findViewById(R.id.pop_manage);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        //显示PopupWindow
        View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.main, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }*/
}
