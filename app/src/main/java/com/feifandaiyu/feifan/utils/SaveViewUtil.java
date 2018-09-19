package com.feifandaiyu.feifan.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SaveViewUtil {

    private static final File rootDir = new File(Environment.getExternalStorageDirectory() + File.separator + "huaban/");

    public static String getPgName() {
        return pgName;
    }

    private static String pgName;

    /**
     * 保存截图的方法
     */
    public static boolean saveScreen(View view) {
        //判断sdcard是否可用
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return false;
        }
        if (!rootDir.exists()) {
            rootDir.mkdir();
        }
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        try {
            pgName = System.currentTimeMillis() + ".jpg";
            bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(new File(rootDir, pgName)));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            view.setDrawingCacheEnabled(false);
            bitmap = null;
        }
    }
}
