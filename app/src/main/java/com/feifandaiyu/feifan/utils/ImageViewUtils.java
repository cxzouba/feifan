package com.feifandaiyu.feifan.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Apple on 2017/5/2.
 * 加载图片工具类
 */

public final class ImageViewUtils {

    //显示图片
    public static void showNetImage(Context context, String url, int resId, ImageView iv) {
        Glide
                .with(context)
                .load(url)
                .placeholder(resId)
                .crossFade()
                .into(iv);
    }

    //显示图片
    public static void showLocalImage(Context context, int ResId, ImageView iv) {
        Glide
                .with(context)
                .load(ResId)
                .crossFade()
                .into(iv);
    }
}
