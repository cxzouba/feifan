package com.feifandaiyu.feifan.http;

import android.net.Uri;
import android.os.Handler;

import com.feifandaiyu.feifan.MyApplication;
import com.feifandaiyu.feifan.http.cookie.PersistentCookieJar;
import com.feifandaiyu.feifan.http.cookie.cache.SetCookieCache;
import com.feifandaiyu.feifan.http.cookie.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by lxj on 2016/12/18.
 */

public class OKHttp3Helper {

    private static OKHttp3Helper mInstance = new OKHttp3Helper();
    private final OkHttpClient okhttp;
    private Handler handler = new Handler();

    private OKHttp3Helper() {
        //1.创建OkHttpClient对象
        PersistentCookieJar persistentCookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.getContext()));
        okhttp = new OkHttpClient.Builder()
                .cookieJar(persistentCookieJar)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
//                              .cache(new Cache(file,size))//设置数据缓存的
                .build();
    }

    public static OKHttp3Helper create() {

        return mInstance;
    }

    /**
     * 执行get请求
     *
     * @param url
     * @param callback
     */
    public void get(String url, HttpCallback callback) {
        get(url, null, null, callback);
    }

    /***
     * 执行get请求
     *
     * @param url
     * @param callback
     */
    public void get(String url, HashMap<String, String> headers, HashMap<String, String> params, final HttpCallback callback) {
        //创建请求对象Request
//        CacheControl: 指定请求和响应遵循的缓存机制
        CacheControl.Builder  cacheBuilder = new CacheControl.Builder();
//        缓存策略:
//        cacheBuilder.noCache() // 指请求或响应消息不被缓存,全部走网络
//        cacheBuilder.noStore() //用于防止重要的信息被无意的发布。在请求消息中发送将使得请求和响应消息都不使用缓存,也不存储
//        cacheBuilder.onlyIfCached() //只使用缓存
        cacheBuilder.maxAge(0, TimeUnit.MILLISECONDS);//设置缓存数据的有效期, 超出有效期,框架自动清空缓存
        cacheBuilder.maxStale(1, TimeUnit.DAYS);//设置缓存数据过时时间,超出有效期,框架自动清空缓存数据

        CacheControl cacheControl = cacheBuilder.build();//cacheControl

        Request.Builder builder = new Request.Builder()
                .url(url)
                .cacheControl(cacheControl)//设置网络缓存控制器
                .get();//设置请求方式是get
        //添加header
        if (headers != null && !headers.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        //请求参数: http://www.xxx.com?username=ywf&pwd=123
        if (url != null && params != null && !params.isEmpty()) {
            Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
            Set keys = params.keySet();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                uriBuilder.appendQueryParameter(key, params.get(key));
            }
        }


        Request request = builder.build();

        //执行请求
        Call call = okhttp.newCall(request);
        //执行请求，但是这个方式是同步请求的方式
//        Response response = call.execute();
        //执行异步请求的方式
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onFail(e);
                        }
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取http相应体数据
                ResponseBody body = response.body();
                //将响应体的数据转为string
                final String string = body.string();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //将数据传递给外界
                        if (callback != null) {
                            callback.onSuccess(string);
                        }
                    }
                });
            }
        });
    }

    /**
     * 提交表单发送请求
     *
     * @param url
     * @param headers
     * @param params
     * @param callback
     */
    public void post(String url, HashMap<String, String> headers, HashMap<String, String> params, final HttpCallback callback) {
        //创建请求体,formBody表单
        FormBody.Builder formBoadybuilder = new FormBody.Builder();
        if (params != null) {
            Iterator i$ = params.keySet().iterator();

            while (i$.hasNext()) {
                String key = (String) i$.next();
                formBoadybuilder.add(key, params.get(key));
            }
        }
        FormBody formBody = formBoadybuilder.build();

        //创建请求对象Request
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(formBody);//设置请求方式是get
        //添加header
        if (headers != null && !headers.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.build();

        //执行请求
        Call call = okhttp.newCall(request);
        //执行请求，但是这个方式是同步请求的方式
//        Response response = call.execute();
        //执行异步请求的方式
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onFail(e);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取http相应体数据
                ResponseBody body = response.body();
                //将响应体的数据转为string
                final String string = body.string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //将数据传递给外界
                        if (callback != null) {
                            callback.onSuccess(string);
                        }
                    }
                });
            }
        });
    }

    /**
     * 发送文件请求
     *
     * @param url
     * @param headers
     * @param params
     * @param callback
     */
    public void postfiles(String url, HashMap<String, String> headers, HashMap<String, Object> params, final HttpCallback callback) {
        //创建请求体
        MultipartBody.Builder multiPartBodyBuilder = (new MultipartBody.Builder()).setType(MultipartBody.FORM);
        ;
        if (params != null && !params.isEmpty()) {
            Iterator i$ = params.keySet().iterator();
            while (i$.hasNext()) {
                String key = (String) i$.next();
                Object o = params.get(key);
                if (o instanceof File) {
                    RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), (File) o);
                    multiPartBodyBuilder.addFormDataPart(key, key, fileBody);
                } else {
                    multiPartBodyBuilder.addPart(Headers.of(new String[]{"Content-Disposition", "form-data; name=\"" + key + "\""}), RequestBody.create((MediaType) null, (String) params.get(key)));
                }
            }
        }
        //创建请求对象Request
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(multiPartBodyBuilder.build());//设置请求方式是post
        //添加header
        if (headers != null && !headers.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.build();

        //3.执行请求
        Call call = okhttp.newCall(request);
        //执行请求，但是这个方式是同步请求的方式
//        Response response = call.execute();
        //执行异步请求的方式
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onFail(e);
                        }
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取http相应体数据
                ResponseBody body = response.body();
                //将响应体的数据转为string
                final String string = body.string();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //将数据传递给外界
                        if (callback != null) {
                            callback.onSuccess(string);
                        }
                    }
                });
            }
        });
    }

    public interface HttpCallback {
        void onSuccess(String data);

        void onFail(Exception e);
    }

}
