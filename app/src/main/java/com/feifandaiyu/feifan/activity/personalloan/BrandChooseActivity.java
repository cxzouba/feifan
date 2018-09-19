package com.feifandaiyu.feifan.activity.personalloan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.BrandChooseAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.BrandCarBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;

import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by davidzhao on 2017/5/26.
 */

public class BrandChooseActivity extends BaseActivity {
    private ListView listView;
    private ImageView iv_back;
    private String brand;

    @Override
    protected int getContentView() {
        return R.layout.activity_brandchoise;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("选择品牌");
        showNext(false);

        iv_back = (ImageView) findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              finish();

            }
        });

        brand = getIntent().getStringExtra("brand");
        listView = (ListView) findViewById(R.id.lv_brandactivity);

            OkHttpUtils.post()
                    .url(Constants.URLS.BASEURL + "Login/cars")
                    .addParams("brand", brand)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            LogUtils.i("----------------------->>>>>>" + response);
                            String json = response;
                            Gson gson = new Gson();
                            BrandCarBean brandCarBean = gson.fromJson(json, BrandCarBean.class);
                            List<BrandCarBean.ListBean.SeriesBean> series = brandCarBean.getList().getSeries();

                            listView.setAdapter(new BrandChooseAdapter(BrandChooseActivity.this, series, lister));
                        }
                    });
    }

    BrandChooseAdapter.OnTextClickLister lister = new BrandChooseAdapter.OnTextClickLister() {
        @Override
        public void onTextClick(View view) {
            TextView tv = (TextView) view;
            Intent intent = new Intent(BrandChooseActivity.this, DetailsCarMessageActivity.class);
            intent.putExtra("cars", tv.getText().toString().trim());
            PreferenceUtils.setString(BrandChooseActivity.this,"cars",tv.getText().toString().trim());
            startActivity(intent);
            finish();
        }
    };


}
