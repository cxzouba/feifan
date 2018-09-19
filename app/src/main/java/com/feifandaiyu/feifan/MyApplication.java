package com.feifandaiyu.feifan;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.baidu.mapapi.SDKInitializer;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.FunctionOptions;
import com.luck.picture.lib.model.PictureConfig;
import com.tencent.bugly.Bugly;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by houdaichang on 2017/5/2.
 * 全局单例
 */

public class MyApplication extends MultiDexApplication {
    //list结合集合保存没一个activity
    private List<Activity> mList = new LinkedList<Activity>();
    //为了实现每次使用该类时不创建新的对象而创建的静态对象
    private static MyApplication instance;
    private static Context mContext;
    private static Handler mMainThreadHandler;
    private static int mMainThreadId;
    private Map<String, String> mMemProtocolCacheMap = new HashMap<>();

    public Map<String, String> getMemProtocolCacheMap() {
        return mMemProtocolCacheMap;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    //实力化
    public synchronized static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }


    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }


    //杀进程
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    /**
     * 得到上下文
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 得到主线程里面的创建的一个hanlder
     *
     * @return
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 得到主线程的线程id
     *
     * @return
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    @Override
    public void onCreate() {//程序的入口方法
        //上下文
        mContext = getApplicationContext();

        //主线程的Handler
        mMainThreadHandler = new Handler();

        //主线程的线程id
        mMainThreadId = android.os.Process.myTid();
        /**
         myTid:Thread
         myPid:Process
         myUid:User
         */
        super.onCreate();

        PreferenceUtils.setString(this, "details", "");
        PreferenceUtils.setString(this, "cars", "");
        PreferenceUtils.setString(this, "carBrand", "");
        PreferenceUtils.setString(this, "cautioner", "0");

        SDKInitializer.initialize(this);

//        ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("OkHttpClient"))
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

//        CrashReport.initCrashReport(getApplicationContext(), "93994ae976", true);
        Bugly.init(getApplicationContext(), "93994ae976", false);

        // 配置初始化图片
        FunctionOptions options = new FunctionOptions.Builder().create();
        options.setType(FunctionConfig.TYPE_IMAGE);
        options.setCompress(true);
        options.setGrade(Luban.THIRD_GEAR);
        PictureConfig.getInstance().init(options);

    }

}
