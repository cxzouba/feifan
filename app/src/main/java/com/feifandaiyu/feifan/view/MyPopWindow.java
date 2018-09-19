package com.feifandaiyu.feifan.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.feifandaiyu.feifan.R;

import butterknife.InjectView;

public class MyPopWindow extends PopupWindow {

    @InjectView(R.id.rl_pop)
    RelativeLayout rlPop;

    @InjectView(R.id.rl1)
    RelativeLayout rl1;
    @InjectView(R.id.rl2)
    View rl2;

    private Context mContext;

    private View view;
    private Button btOk;
    private WebView wvXieyi;

    public MyPopWindow(final Context mContext) {

        this.view = LayoutInflater.from(mContext).inflate(R.layout.pop_layout, null);

        btOk = (Button) view.findViewById(R.id.bt_ok);
        wvXieyi = (WebView) view.findViewById(R.id.wv_xieyi);

        btOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        wvXieyi.getSettings().setJavaScriptEnabled(true);
        wvXieyi.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        wvXieyi.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        wvXieyi.loadUrl("http://specaildata.hrbffdy.com/web.html");
        wvXieyi.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // 设置按钮监听
//        btOk.setOnClickListener(itemsOnClick);
//        payType.setOnClickListener(itemsOnClick);
//        btn_take_photo.setOnClickListener(itemsOnClick);
        // 设置外部可点击
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//        this.view.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int height = view.findViewById(R.id.rl_pop).getTop();
//
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                    }
//                }
//                return false;
//            }
//        });

    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isOutsideTouchable()) {
                    View mView = getContentView();
                    if (null != mView)
                        mView.dispatchTouchEvent(event);
                }
                return isFocusable() && !isOutsideTouchable();
            }
        });
        this.setTouchable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.AnimationPreview);

    }
}