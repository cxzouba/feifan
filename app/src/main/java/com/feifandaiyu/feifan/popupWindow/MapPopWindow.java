package com.feifandaiyu.feifan.popupWindow;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import com.feifandaiyu.feifan.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by davidzhao on 2017/5/17.
 */

public class MapPopWindow extends BasePopupWindow {
    private Context context;
    private View view;
    public MapPopWindow(Activity context,View view) {
        super(context);
        this.context = context;
    }

    public MapPopWindow(Activity context, int w, int h) {
        super(context, w, h);
    }

    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return null;
    }

    @Override
    public View onCreatePopupView() {
        return view.inflate(context,R.layout.view_visit_time,null);
    }

    @Override
    public View initAnimaView() {
        return null;
    }
}
