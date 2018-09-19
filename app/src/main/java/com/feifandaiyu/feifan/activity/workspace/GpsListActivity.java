package com.feifandaiyu.feifan.activity.workspace;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.GpsInfoAdapter;
import com.feifandaiyu.feifan.adapter.GpsListAdappter;
import com.feifandaiyu.feifan.adapter.GpsListAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.Fangkuan;
import com.feifandaiyu.feifan.bean.GpsInfoBean;
import com.feifandaiyu.feifan.bean.GpsListBean;
import com.feifandaiyu.feifan.bean.GpsType;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.Resource2Bitmap;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

public class GpsListActivity extends BaseActivity {


    @InjectView(R.id.lv_contract_list)
    ListView lvContractList;
    @InjectView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @InjectView(R.id.rl_contract)
    RelativeLayout rlContract;
    private TextView next;
    private ImageView back;
    private String saleID;
    private KProgressHUD hud;
    private List<GpsListBean.ListBean> list;
    private GpsListBean bean;
    private Bitmap bitmap;
    private BitmapDrawable bmdrawable;
    private int firstVisiblePosition;
    private GpsListAdapter adapper;
    private MyContractDialog myContractDialog;
    private Fangkuan fangkuanBean;
    private GpsType Bean;
    private List<GpsType.ListBean> gpsList;
    private List<GpsInfoBean.ListBean> gpsInfo;
    private GpsInfoAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_apply_contract;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

        bitmap = Resource2Bitmap.readBitMap(this, R.drawable.jiazai);
        bmdrawable = new BitmapDrawable(bitmap);
        rlContract.setBackground(bmdrawable);

        setTitle("GPS列表");

        next = (TextView) findViewById(R.id.tv_next);
        back = (ImageView) findViewById(R.id.iv_back);

        next.setText("申请GPS");
        next.setEnabled(true);


        hud = KProgressHUD.create(GpsListActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setWindowColor(Color.GRAY)
                .show();

        saleID = PreferenceUtils.getString(this, "saleID");

        myContractDialog = new MyContractDialog(this);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                firstVisiblePosition = 0;
                initData();
            }
        });

        initData();

        lvContractList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = LayoutInflater.from(GpsListActivity.this);//将xml布局转换为view
                View item = inflater.inflate(R.layout.dialog_bank, null);//将xml布局转换为view,里面有listview
                final ListView listView = (ListView) item.findViewById(R.id.pop_bank);

                final Dialog builder = new Dialog(GpsListActivity.this);
                builder.setContentView(item);
                builder.setTitle("GPS编号");

                post()
                        .url(Constants.URLS.BASEURL + "Loan/applyGpsShow")
                        .addParams("applyId", bean.getList().get(position).getId())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                LogUtils.i("gpsinfo------------------>>>" + e);

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.i("gpsinfo------------------>>>" + response);
                                String json = response;
                                Gson gson = new Gson();
                                GpsInfoBean bean = gson.fromJson(json, GpsInfoBean.class);

                                if (bean.getCode() == 1) {
                                    gpsInfo = bean.getList();
//                        LogUtils.i(list1.size() + "---------------->>>>>>>>");
                                    adapter = new GpsInfoAdapter(GpsListActivity.this, gpsInfo);
                                    listView.setAdapter(adapter);//给listview设置适配器，adapter
                                    adapter.notifyDataSetChanged();
                                    builder.show();

                                } else if (bean.getCode() == 0) {
                                    MyToast.show(GpsListActivity.this, bean.getMsg());
                                }


                            }
                        });

            }
        });
    }


    private void initData() {

        LogUtils.e("saleID=" + saleID);

        post()
                .url(Constants.URLS.BASEURL + "Loan/applyGpsList")
                .addParams("saleId", saleID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("gpsList____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(GpsListActivity.this, "联网失败");
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.finishRefresh();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("gpsList----------------->>>>>>" + response);
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();

                        bean = null;
                        try {
                            bean = gson.fromJson(json, GpsListBean.class);
                        } catch (JsonSyntaxException e) {
                            if (refreshLayout.isRefreshing()) {
                                refreshLayout.finishRefresh();
                            }
                        }

                        if (bean != null) {

                            list = bean.getList();

                            if (bean.getCode() == 1) {

                                if (list == null || list.size() == 0) {
                                    rlContract.setBackground(bmdrawable);
                                } else {
                                    rlContract.setBackground(null);
                                }
                                adapper = new GpsListAdapter(GpsListActivity.this, list);
                                lvContractList.setAdapter(adapper);
                                adapper.notifyDataSetChanged();

                                lvContractList.setSelection(firstVisiblePosition);

                                if (refreshLayout.isRefreshing()) {

                                    refreshLayout.finishRefresh();

                                }

                            } else {

                                if (refreshLayout.isRefreshing()) {

                                    refreshLayout.finishRefresh();

                                }

                                MyToast.show(GpsListActivity.this, "获取失败，code=0");
                            }
                        }


                    }
                });
    }

    @OnClick({R.id.tv_next, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:

                OkHttpUtils.post()
                        .url(Constants.URLS.BASEURL + "Loan/applyGps")
                        .addParams("saleId", saleID)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                System.out.println("fangkuan____________________>>>>>" + e);
                                hud.dismiss();
                                MyToast.show(GpsListActivity.this, "加载失败");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.e("fangkuan----------------->>>>>>" + response);
                                String json = response;
                                Gson gson = new Gson();
                                Bean = null;

                                try {
                                    Bean = gson.fromJson(json, GpsType.class);
                                    if (Bean.getCode() == 1) {

                                        gpsList = Bean.getList();

                                        myContractDialog.showDialog();

                                    } else if (Bean.getCode() == 0) {
                                        MyToast.show(GpsListActivity.this, Bean.getMsg());
                                    }
                                } catch (JsonSyntaxException e) {
                                    MyToast.show(GpsListActivity.this, "服务器错误");
                                }


                            }
                        });

                break;
            case R.id.iv_back:
                finish();
                break;
            default:
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    public class MyContractDialog {

        private Context context;
        private ListView lvGps;
        private PowerfulEditText etNum;
        private String admin_id;
        private String type;

        public MyContractDialog(Context context) {
            this.context = context;
        }

        public void showDialog() {

//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = getLayoutInflater();
            final View layout = inflater.inflate(R.layout.dialog_gps, null);//获取自定义布局

            lvGps = (ListView) layout.findViewById(R.id.lv_gps);
            etNum = (PowerfulEditText) layout.findViewById(R.id.et_num);

            final AlertDialog dialog = new AlertDialog.Builder(context).setView(layout).setPositiveButton("申请", null)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
//这里必须要先调show()方法，后面的getButton才有效
            dialog.show();

            dialog.setCanceledOnTouchOutside(false);

            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.red));

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (etNum.getText().toString().equals("") || etNum.getText() == null) {
                        MyToast.show(GpsListActivity.this, "请填写数量");
                        return;
                    }

                    if (type == null || admin_id == null) {
                        MyToast.show(GpsListActivity.this, "请选择GPS型号");
                        return;
                    }

                    OkHttpUtils.post()
                            .url(Constants.URLS.BASEURL + "Loan/applyGpsSave")
                            .addParams("saleId", saleID)
                            .addParams("number", etNum.getText().toString())
                            .addParams("model", type)
                            .addParams("cityId", admin_id)

                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    System.out.println("gps____________________>>>>>" + e);
                                    hud.dismiss();
                                    MyToast.show(GpsListActivity.this, "加载失败");
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    LogUtils.e("gps----------------->>>>>>" + response);
                                    String json = response;
                                    Gson gson = new Gson();
                                    fangkuanBean = null;

                                    try {
                                        fangkuanBean = gson.fromJson(json, Fangkuan.class);
                                        if (fangkuanBean.getCode() == 1) {

                                            dialog.dismiss();
                                            adapper.notifyDataSetChanged();
                                            MyToast.show(GpsListActivity.this, "提交成功");

                                        } else if (fangkuanBean.getCode() == 0) {
                                            MyToast.show(GpsListActivity.this, fangkuanBean.getMsg());
                                        }
                                    } catch (JsonSyntaxException e) {
                                        MyToast.show(GpsListActivity.this, "服务器错误");
                                    }


                                }
                            });

                }
            });

            initUI();
        }

        private void initUI() {
            lvGps.setAdapter(new GpsListAdappter(GpsListActivity.this, gpsList, new GpsListAdappter.OnRadiobuttonclick() {
                        @Override
                        public void radioButtonck(int position) {
                            admin_id = gpsList.get(position).getUser_id();
                            type = gpsList.get(position).getModel();
                        }
                    }
                    )
            );
        }
    }
}
