package com.feifandaiyu.feifan.ocr;

import android.os.Looper;

/**
 * Created by houdaichang on 2017/10/13.
 */

public class UIThread {
    public UIThread() {
    }

    public static boolean isUITread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
