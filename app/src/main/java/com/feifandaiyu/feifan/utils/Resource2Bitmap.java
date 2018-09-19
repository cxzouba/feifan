package com.feifandaiyu.feifan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.SystemClock;

import com.feifandaiyu.feifan.MyApplication;

import java.io.File;
import java.io.InputStream;

/**
 * Created by davidzhao on 2017/5/9.
 */

public class Resource2Bitmap {

    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        Bitmap bm = BitmapFactory.decodeStream(is,null,opt);
        return bm;
        ///data/data/youPackageName/files
    }

}
