package com.feifandaiyu.feifan.activity.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feifandaiyu.feifan.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author houdaichang
 */
public class H5Activity extends AppCompatActivity {

    @InjectView(R.id.web)
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        ButterKnife.inject(this);

        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        web.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        web.loadUrl("http://shop.lxtx.org.cn/mobile");
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }
}
