package com.feifandaiyu.feifan.activity.workspace;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.DemoBase;
import com.feifandaiyu.feifan.bean.FinishWorkBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

public class WorkPriceActivity extends DemoBase {

    @InjectView(R.id.back)
    ImageView back;
    private String saleID;
    private KProgressHUD hud;
    private FinishWorkBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_listview_chart);
        ButterKnife.inject(this);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        initData();

    }

    private void initData() {
        saleID = PreferenceUtils.getString(this, "saleID");

        LogUtils.e(saleID + "==========");

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Company/statistics")
                .addParams("admin_id", saleID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("WorkPriceActivity____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(WorkPriceActivity.this, "加载失败");
//                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("WorkPriceActivity----------------->>>>>>" + response);
                        String json = response;
                        Gson gson = new Gson();

                        try {
                            bean = gson.fromJson(json, FinishWorkBean.class);
                        } catch (JsonSyntaxException e) {
                            MyToast.show(WorkPriceActivity.this, "没有数据");
                        }
                        if (bean != null) {

                            System.out.println(bean.getList().get_$06().get(0).getTc());

                            hud.dismiss();

                            ListView lv = (ListView) findViewById(R.id.listView1);

                            ArrayList<BarData> list = new ArrayList<BarData>();

                            // 20 items
                            for (int i = 0; i < 1; i++) {
                                list.add(generateData(i + 1));
                            }

                            ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
                            lv.setAdapter(cda);

                        } else {
                            hud.dismiss();
                            System.out.println("bean == null");
                        }
                    }
                });

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
        int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
        int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
        overridePendingTransition(enterAnim0, exitAnim0);
    }

    private class ChartDataAdapter extends ArrayAdapter<BarData> {

        public ChartDataAdapter(Context context, List<BarData> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            BarData data = getItem(position);
            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();

                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_barchart, null);
                holder.chart = (BarChart) convertView.findViewById(R.id.chart);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            // apply styling
            data.setValueTypeface(mTfLight);
            data.setValueTextColor(Color.WHITE);
            holder.chart.getDescription().setEnabled(false);
            holder.chart.setDrawGridBackground(false);
            data.setDrawValues(true);

            XAxis xAxis = holder.chart.getXAxis();
            xAxis.setPosition(XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setLabelCount(12);
            xAxis.setTextColor(Color.WHITE);
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return mMonths[(int) value % mMonths.length];
                }
            });

            YAxis leftAxis = holder.chart.getAxisLeft();
            leftAxis.setTypeface(mTfLight);
            leftAxis.setLabelCount(6, true);
            leftAxis.setSpaceTop(15f);
            leftAxis.setAxisMaximum(10000);
            leftAxis.setAxisMinimum(0);
            leftAxis.setTextColor(Color.WHITE);

            YAxis rightAxis = holder.chart.getAxisRight();
            rightAxis.setTypeface(mTfLight);
            rightAxis.setLabelCount(6, true);
            rightAxis.setSpaceTop(15f);
            rightAxis.setAxisMaximum(10000);
            rightAxis.setAxisMinimum(0);
            rightAxis.setTextColor(Color.WHITE);


            // set data
            holder.chart.setData(data);
            holder.chart.setFitBars(true);

            // do not forget to refresh the chart
//            holder.chart.invalidate();
            holder.chart.animateY(700);

            return convertView;
        }

        private class ViewHolder {
            BarChart chart;
        }
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateData(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 12; i++) {
            entries.add(0, new BarEntry(0, bean.getList().get_$01().get(0).getTc()));
            entries.add(1, new BarEntry(1, bean.getList().get_$02().get(0).getTc()));
            entries.add(2, new BarEntry(2, bean.getList().get_$03().get(0).getTc()));
            entries.add(3, new BarEntry(3, bean.getList().get_$04().get(0).getTc()));
            entries.add(4, new BarEntry(4, bean.getList().get_$05().get(0).getTc()));
            entries.add(5, new BarEntry(5, bean.getList().get_$06().get(0).getTc()));
            entries.add(6, new BarEntry(6, bean.getList().get_$07().get(0).getTc()));
            entries.add(7, new BarEntry(7, bean.getList().get_$08().get(0).getTc()));
            entries.add(8, new BarEntry(8, bean.getList().get_$09().get(0).getTc()));
            entries.add(9, new BarEntry(9, bean.getList().get_$10().get(0).getTc()));
            entries.add(10, new BarEntry(10, bean.getList().get_$11().get(0).getTc()));
            entries.add(11, new BarEntry(11, bean.getList().get_$12().get(0).getTc()));
        }

        BarDataSet d = new BarDataSet(entries, "我的提成");
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setBarBorderColor(Color.WHITE);
        d.setBarShadowColor(Color.rgb(203, 203, 203));
        d.setDrawValues(true);

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.8f);
        cd.setDrawValues(true);
        return cd;
    }
}
