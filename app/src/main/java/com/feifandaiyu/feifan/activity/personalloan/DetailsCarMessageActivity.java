package com.feifandaiyu.feifan.activity.personalloan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.DetailsCarbeanAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.DetailsCarBean;
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

public class DetailsCarMessageActivity extends BaseActivity {
    private ListView lv;
    private ImageView iv_back;
    private String cars;
    private DetailsCarBean detailsCarBean;

    @Override
    protected int getContentView() {
        return R.layout.activity_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("车辆详细信息");
        showNext(false);
        cars = getIntent().getStringExtra("cars");

        iv_back = (ImageView) findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lv = (ListView) findViewById(R.id.lv_detail);

        OkHttpUtils.post()
                //http://byu2763230001.my3w.com/pcreateArchivesublic/info/
                .url(Constants.URLS.BASEURL + "Login/series")
                .addParams("cars", cars)
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
                        detailsCarBean = gson.fromJson(json, DetailsCarBean.class);
                        List<DetailsCarBean.ListBean.SeriesBean> series = detailsCarBean.getList().getSeries();
                        lv.setAdapter(new DetailsCarbeanAdapter(DetailsCarMessageActivity.this, series, lister));

                    }
                });

    }

    DetailsCarbeanAdapter.OnTextclickListener lister = new DetailsCarbeanAdapter.OnTextclickListener() {
        @Override
        public void onTextClick(View view, int position) {
            TextView textView = (TextView) view;
            Intent mIntent = new Intent();
            mIntent.putExtra("details", textView.getText().toString().trim());
            PreferenceUtils.setString(DetailsCarMessageActivity.this, "details", textView.getText().toString().trim());

            PreferenceUtils.setString(DetailsCarMessageActivity.this, "price", detailsCarBean.getList().getSeries().get(position).getPrice());

            finish();

        }
    };
}
