package com.feifandaiyu.feifan.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.icu.text.SimpleDateFormat;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by davidzhao on 2017/5/11.
 */

public class ScreenShotBitmap {
    private static String TAG ="ScreenShotBitmap";

    private static final String SCREENSHOTS_DIR_NAME = "screenShots";
    private static final String SCREENSHOT_FILE_NAME_TEMPLATE = "Screenshot.jpg";
    private static final String SCREENSHOT_FILE_PATH_TEMPLATE = "%s/%s/%s";

    /**
     * 存储到sdcard
     *
     * @param bmp
     * @return
     */
    public static String saveToSD(Bitmap bmp) {
        //判断sd卡是否存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //文件名
            long systemTime = System.currentTimeMillis();
           /* String imageDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date(systemTime));*/

            String mFileName = "Screenshot.jpg";

            File dir = new File(SCREENSHOTS_DIR_NAME);
            //判断文件是否存在，不存在则创建
            if (!dir.exists()) {
                dir.mkdirs();
            }

            //文件全名
            String mstrRootPath = Environment.getExternalStorageDirectory().toString();
            String mFilePath = String.format(SCREENSHOT_FILE_PATH_TEMPLATE, mstrRootPath,
                    SCREENSHOTS_DIR_NAME, mFileName);

            Log.i(TAG, "file path:" + mFilePath);
            File file = new File(mFilePath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.i(TAG, "file path:" + file.getAbsolutePath());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                if (fos != null) {
                    //第一参数是图片格式，第二参数是图片质量，第三参数是输出流
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return mFilePath;
        }
        return null;
    }

    /*
        获取当前的bitmap图片
     */
    public static Bitmap activityShot(Activity activity) {
         /*获取windows中最顶层的view*/
        View view = activity.getWindow().getDecorView();

        //允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        //获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;

        WindowManager windowManager = activity.getWindowManager();

        //获取屏幕宽和高
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        //去掉状态栏
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeight, width,
                height-statusBarHeight);

        //销毁缓存信息
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }
}
