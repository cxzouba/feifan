package com.feifandaiyu.feifan.activity.carloan;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.utils.ImageViewUtils;
import com.github.chrisbanes.photoview.PhotoView;


public class ImageShower extends Activity implements View.OnClickListener {


    private PhotoView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageshower);
        Intent intent = getIntent();
        String image = intent.getStringExtra("image01");
        iv = (PhotoView) findViewById(R.id.iv_showpic);



        ImageViewUtils.showNetImage(this,image,0,iv);
        iv.setOnClickListener(this);
     /*   final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
        dialog.show();
        // 两秒后关闭后dialog
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000 );*/
    }


    @Override
    public void onClick(View v) {
        finish();
    }
}