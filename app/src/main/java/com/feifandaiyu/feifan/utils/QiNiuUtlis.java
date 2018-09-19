package com.feifandaiyu.feifan.utils;

import com.feifandaiyu.feifan.config.Constants;
import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import java.io.File;

/**
 * Created by davidzhao on 2017/6/9.
 */

public class QiNiuUtlis {
   public static void upLoad(String filePath, String key, UpCompletionHandler handler){
       Configuration config = new Configuration.Builder().zone(Zone.zone1).build();
       UploadManager uploadManager = new UploadManager(config);
       uploadManager.put(filePath,key, Constants.token,handler,null);
   }

    public static void upLoad(String filePath, String key, UpCompletionHandler handler, UploadOptions uploadOptions){
        Configuration config = new Configuration.Builder().zone(Zone.zone1).build();
        UploadManager uploadManager = new UploadManager(config);
        uploadManager.put(filePath,key, Constants.token,handler,uploadOptions);
    }

    public static void upLoad(File file, String key, UpCompletionHandler handler){
        Configuration config = new Configuration.Builder().zone(Zone.zone1).build();
        UploadManager uploadManager = new UploadManager(config);
        uploadManager.put(file,key, Constants.token,handler,null);
    }

    public static void upLoad(byte[] filePath, String key, UpCompletionHandler handler){
        Configuration config = new Configuration.Builder().zone(Zone.zone1).build();
        UploadManager uploadManager = new UploadManager(config);
        uploadManager.put(filePath,key, Constants.token,handler,null);
    }

}
