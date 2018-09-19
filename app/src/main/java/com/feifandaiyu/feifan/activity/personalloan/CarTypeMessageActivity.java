package com.feifandaiyu.feifan.activity.personalloan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.CarTypeAdapter;
import com.feifandaiyu.feifan.adapter.NewCarTypeAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.BrandCarUrlBean;
import com.feifandaiyu.feifan.bean.HomeVistBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.view.QuickIndexBar;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by davidzhao on 2017/5/26.
 */

public class CarTypeMessageActivity extends BaseActivity {

    private ListView lv;

    private TextView tv_letter;
    private List<HomeVistBean.ListBean.CarTypeBean>  carType;
    private ImageView iv_back;
    private List<BrandCarUrlBean.ListBean> list;


    @Override
    protected int getContentView() {
        return R.layout.activity_cartype_message;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        showNext(false);

        setTitle("请选择车型");

        iv_back = (ImageView) findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        lv = (ListView) findViewById(R.id.lv_cartype_message);
        tv_letter = (TextView) findViewById(R.id.tv_letter);
        QuickIndexBar quickIndexBar = (QuickIndexBar) findViewById(R.id.quiclIndexBar);
        quickIndexBar.setOnLetterChangeListener(new QuickIndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                //根据letter找到列表中首字母和letter相同的条目，然后置顶
                if (carType != null){
                    for (int i = 0; i < carType.size(); i++) {
                        String firstWord = carType.get(i).getString(carType.get(i).getBrand()).charAt(0) + "";
                        if (firstWord.equalsIgnoreCase(letter)) {
                            //说明找到了和letter同样字母的条目
                            lv.setSelection(i);
                            //找到立即中断
                            break;
                        }
                    }
                }else {

                    for (int i = 0; i < list.size(); i++) {
                        String firstWord = list.get(i).getString(list.get(i).getBrand()).charAt(0) + "";
                        if (firstWord.equalsIgnoreCase(letter)) {
                            //说明找到了和letter同样字母的条目
                            lv.setSelection(i);
                            //找到立即中断
                            break;
                        }
                    }


                }

                //显示出字母
                showLetter(letter);
            }
        });
        String json = PreferenceUtils.getString(this,"json");
        if (json==null){
            OkHttpUtils
                    .post()
                    //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                    .url(Constants.URLS.BASEURL + "Login/selectCarsize")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            LogUtils.d("cartypemessage"+"------------->>>>>>>" + "失败");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            LogUtils.d("cartypemessage"+"------------->>>>" + response + "成功");
                            String json = response;

                            Gson gson = new Gson();
                            BrandCarUrlBean brandCarUrlBean = gson.fromJson(json, BrandCarUrlBean.class);
                            list = brandCarUrlBean.getList();

                            lv.setAdapter(new NewCarTypeAdapter(CarTypeMessageActivity.this, list,listener1));

                        }
                    });
        }else{
            Gson gson = new Gson();
            HomeVistBean homeVistBean = gson.fromJson(json, HomeVistBean.class);
            HomeVistBean.ListBean list = homeVistBean.getList();
            carType = list.getCarType();
            lv.setAdapter(new CarTypeAdapter(CarTypeMessageActivity.this, carType, listener));
        }


    }

    private Handler handler = new Handler();
    private void showLetter(String letter) {
        handler.removeCallbacksAndMessages(null);

        tv_letter.setText(letter);
        tv_letter.setVisibility(View.VISIBLE);
        //延时隐藏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_letter.setVisibility(View.GONE);
            }
        }, 2000);
    }

    CarTypeAdapter.OnTextclicklister listener = new CarTypeAdapter.OnTextclicklister() {


        @Override
        public void onTextClick(View view) {
            TextView tv = (TextView) view;

            Intent intent = new Intent(CarTypeMessageActivity.this, BrandChooseActivity.class);
            intent.putExtra("brand", tv.getText().toString().trim());
            PreferenceUtils.setString(CarTypeMessageActivity.this,"carBrand",tv.getText().toString().trim());
            startActivity(intent);
            finish();
        }
    };

    NewCarTypeAdapter.OnTextclicklister listener1 = new NewCarTypeAdapter.OnTextclicklister() {


        @Override
        public void onTextClick(View view) {
            TextView tv = (TextView) view;

            Intent intent = new Intent(CarTypeMessageActivity.this, BrandChooseActivity.class);
            intent.putExtra("brand", tv.getText().toString().trim());
            PreferenceUtils.setString(CarTypeMessageActivity.this,"carBrand",tv.getText().toString().trim());
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}