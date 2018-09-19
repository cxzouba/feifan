/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.feifandaiyu.feifan.utils;

import android.content.Context;

import java.io.File;

public class FileUtil {
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }
}
