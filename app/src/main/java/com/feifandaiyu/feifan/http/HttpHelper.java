package com.feifandaiyu.feifan.http;

import android.os.Handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by lxj on 2016/12/18.
 */

public class HttpHelper {

    private static HttpHelper mInstance = new HttpHelper();
    private final OkHttpClient okhttp;
    private Handler handler = new Handler();
    private HttpHelper(){
        //1.创建OkHttpClient对象
        //                              .cache(new Cache(file,size))//设置数据缓存的
        okhttp = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
//                              .cache(new Cache(file,size))//设置数据缓存的
                .build();
    }
    public static HttpHelper create(){

         return mInstance;
    }

    /**
     * 执行get请求
     * @param url
     * @param callback
     */
    public void execGet(String url, HttpCallback callback){
        execGet(url,null,callback);
    }

    /***
     * 执行get请求
     * @param url
     * @param callback
     */
    public void execGet(String url, HashMap<String,String> headers, final HttpCallback callback){
        //2.创建请求对象Request
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();//设置请求方式是get
        //添加header
        if(headers!=null && !headers.isEmpty()){
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
            while(iterator.hasNext()){
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
                        if(callback!=null){
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
                        if(callback!=null){
                            callback.onSuccess(string);
                        }
                    }
                });
            }
        });



    }
    public interface HttpCallback{
        void onSuccess(String data);
        void onFail(Exception e);
    }

}
