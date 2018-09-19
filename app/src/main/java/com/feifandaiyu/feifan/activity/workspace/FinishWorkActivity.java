package com.feifandaiyu.feifan.activity.workspace;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.DemoBase;
import com.feifandaiyu.feifan.bean.FinishWorkBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

public class FinishWorkActivity extends DemoBase {

    @InjectView(R.id.back)
    ImageView back;
    private CombinedChart mChart;
    private final int itemcount = 12;
    private String saleID;
    private KProgressHUD hud;
    private FinishWorkBean bean;
    private CombinedData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_finish_work);
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

    private void initChart() {
        data = new CombinedData();
        mChart = (CombinedChart) findViewById(R.id.chart1);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.TRANSPARENT);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);

        mChart.animateXY(1500, 1500);

        // draw bars behind lines
        mChart.setDrawOrder(new DrawOrder[]{
                DrawOrder.BAR
        });

        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setAxisMaximum(1000000f);
        rightAxis.setTextColor(Color.WHITE);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setAxisMaximum(1000000f);
        leftAxis.setTextColor(Color.WHITE);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mMonths[(int) value % mMonths.length];
            }
        });

        data.setData(generateLineData());
        data.setData(generateBarData());

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();
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
                        System.out.println("FinishWorkActivity____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(FinishWorkActivity.this, "加载失败");
//                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("FinishWorkActivity----------------->>>>>>" + response);
                        String json = response;
                        Gson gson = new Gson();

                        try {
                            bean = gson.fromJson(json, FinishWorkBean.class);
                        } catch (JsonSyntaxException e) {
                            MyToast.show(FinishWorkActivity.this, "没有数据");
                        }
                        if (bean != null) {

                            System.out.println(bean.getList().get_$06().get(0).getSummoney());

                            initChart();
//
                            hud.dismiss();

                        } else {
                            hud.dismiss();
                            System.out.println("bean == null");
                        }
                    }
                });

    }


    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

//        for (int index = 0; index < itemcount; index++)
//            entries.add(new Entry(index + 0.5f, getRandom(15, 5)));

        entries.add(0, new BarEntry(0, 80));
        entries.add(1, new BarEntry(1, 70));
        entries.add(2, new BarEntry(2, 60));
        entries.add(3, new BarEntry(3, 80));
        entries.add(4, new BarEntry(4, 90));
        entries.add(5, new BarEntry(5, 60));
        entries.add(6, new BarEntry(6, 70));
        entries.add(7, new BarEntry(7, 90));
        entries.add(8, new BarEntry(8, 40));
        entries.add(9, new BarEntry(9, 50));
        entries.add(10, new BarEntry(10, 70));
        entries.add(11, new BarEntry(11, 80));

        return d;
    }

    private BarData generateBarData() {

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();

        entries1.add(0, new BarEntry(0, bean.getList().get_$01().get(0).getSummoney()));
        entries1.add(1, new BarEntry(1, bean.getList().get_$02().get(0).getSummoney()));
        entries1.add(2, new BarEntry(2, bean.getList().get_$03().get(0).getSummoney()));
        entries1.add(3, new BarEntry(3, bean.getList().get_$04().get(0).getSummoney()));
        entries1.add(4, new BarEntry(4, bean.getList().get_$05().get(0).getSummoney()));
        entries1.add(5, new BarEntry(5, bean.getList().get_$06().get(0).getSummoney()));
        entries1.add(6, new BarEntry(6, bean.getList().get_$07().get(0).getSummoney()));
        entries1.add(7, new BarEntry(7, bean.getList().get_$08().get(0).getSummoney()));
        entries1.add(8, new BarEntry(8, bean.getList().get_$09().get(0).getSummoney()));
        entries1.add(9, new BarEntry(9, bean.getList().get_$10().get(0).getSummoney()));
        entries1.add(10, new BarEntry(10, bean.getList().get_$11().get(0).getSummoney()));
        entries1.add(11, new BarEntry(11, bean.getList().get_$12().get(0).getSummoney()));

        // stacked
        entries2.add(0, new BarEntry(0, bean.getList().get_$01().get(0).getMax()));
        entries2.add(1, new BarEntry(1, bean.getList().get_$02().get(0).getMax()));
        entries2.add(2, new BarEntry(2, bean.getList().get_$03().get(0).getMax()));
        entries2.add(3, new BarEntry(3, bean.getList().get_$04().get(0).getMax()));
        entries2.add(4, new BarEntry(4, bean.getList().get_$05().get(0).getMax()));
        entries2.add(5, new BarEntry(5, bean.getList().get_$06().get(0).getMax()));
        entries2.add(6, new BarEntry(6, bean.getList().get_$07().get(0).getMax()));
        entries2.add(7, new BarEntry(7, bean.getList().get_$08().get(0).getMax()));
        entries2.add(8, new BarEntry(8, bean.getList().get_$09().get(0).getMax()));
        entries2.add(9, new BarEntry(9, bean.getList().get_$10().get(0).getMax()));
        entries2.add(10, new BarEntry(10, bean.getList().get_$11().get(0).getMax()));
        entries2.add(11, new BarEntry(11, bean.getList().get_$12().get(0).getMax()));


        BarDataSet set1 = new BarDataSet(entries1, "我的");
        set1.setColor(getResources().getColor(R.color.myGreen));
        set1.setValueTextColor(getResources().getColor(R.color.myGreen));
        set1.setValueTextSize(8f);

        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, "最高");
        set2.setColors(getResources().getColor(R.color.myRed));
        set2.setValueTextColor(getResources().getColor(R.color.myRed));
        set2.setValueTextSize(8f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setDrawValues(false);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.42f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1, set2);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
        int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
        int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
        overridePendingTransition(enterAnim0, exitAnim0);
    }
}
