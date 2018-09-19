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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.ContractInfoAdapter;
import com.feifandaiyu.feifan.adapter.ContractListAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.ContractInfoBean;
import com.feifandaiyu.feifan.bean.ContractListBean;
import com.feifandaiyu.feifan.bean.Fangkuan;
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

public class ContractListActivity extends BaseActivity {


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
    private List<ContractListBean.ListBean> list;
    private ContractListBean bean;
    private Bitmap bitmap;
    private BitmapDrawable bmdrawable;
    private int firstVisiblePosition;
    private ContractListAdapter adapper;
    private MyContractDialog myContractDialog;
    private Fangkuan fangkuanBean;
    private List<ContractInfoBean.ListBean> contractInfo;
    private ContractInfoAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_apply_contract;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("合同列表");

        bitmap = Resource2Bitmap.readBitMap(this, R.drawable.jiazai);
        bmdrawable = new BitmapDrawable(bitmap);
        rlContract.setBackground(bmdrawable);

        next = (TextView) findViewById(R.id.tv_next);
        back = (ImageView) findViewById(R.id.iv_back);

        next.setText("申请合同");
        next.setEnabled(true);


        hud = KProgressHUD.create(ContractListActivity.this)
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
                LayoutInflater inflater = LayoutInflater.from(ContractListActivity.this);//将xml布局转换为view
                View item = inflater.inflate(R.layout.dialog_bank, null);//将xml布局转换为view,里面有listview
                final ListView listView = (ListView) item.findViewById(R.id.pop_bank);

                final Dialog builder = new Dialog(ContractListActivity.this);
                builder.setContentView(item);
                builder.setTitle("合同编号");

                post()
                        .url(Constants.URLS.BASEURL + "Loan/contractShow")
                        .addParams("applyId", bean.getList().get(position).getId())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                LogUtils.i("contractShow------------------>>>" + e);

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.i("contractShow------------------>>>" + response);
                                String json = response;
                                Gson gson = new Gson();
                                ContractInfoBean bean = gson.fromJson(json, ContractInfoBean.class);

                                if (bean.getCode() == 1) {
                                    contractInfo = bean.getList();
//                        LogUtils.i(list1.size() + "---------------->>>>>>>>");
                                    adapter = new ContractInfoAdapter(ContractListActivity.this, contractInfo);
                                    listView.setAdapter(adapter);//给listview设置适配器，adapter
                                    adapter.notifyDataSetChanged();
                                    builder.show();

                                } else if (bean.getCode() == 0) {
                                    MyToast.show(ContractListActivity.this, bean.getMsg());
                                }


                            }
                        });

            }
        });
    }

    private void initData() {
        post()
                .url(Constants.URLS.BASEURL + "UserPic/appliList")
                .addParams("saleId", saleID)
                .build()
                .execute(new StringCallback() {

                    private String areaname;

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("contractList____________________>>>>>" + e);
                        hud.dismiss();
                        MyToast.show(ContractListActivity.this, "联网失败");
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.finishRefresh();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("contractList----------------->>>>>>" + response);
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();

                        bean = null;
                        try {
                            bean = gson.fromJson(json, ContractListBean.class);
                        } catch (JsonSyntaxException e) {
                            if (refreshLayout.isRefreshing()) {
                                refreshLayout.finishRefresh();
                            }
                        }

                        if (bean != null) {

//                            LogUtils.i(list1.size() + "---------------->>>>>>>>");
                            list = bean.getList();

                            if (bean.getCode() == 1) {


                                if (list == null || list.size() == 0) {
                                    rlContract.setBackground(bmdrawable);
                                } else {
                                    rlContract.setBackground(null);
                                }
                                adapper = new ContractListAdapter(ContractListActivity.this, list);
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

                                MyToast.show(ContractListActivity.this, "获取失败，code=0");
                            }
                        }


                    }
                });
    }

    @OnClick({R.id.tv_next, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                myContractDialog.showDialog();
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
        private RadioButton rbPersonal;
        private RadioButton rbCompany;
        private RadioGroup rgType;
        private PowerfulEditText etNum;

        public MyContractDialog(Context context) {
            this.context = context;
        }

        public void showDialog() {

//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = getLayoutInflater();
            final View layout = inflater.inflate(R.layout.dialog_contract, null);//获取自定义布局

            rbPersonal = (RadioButton) layout.findViewById(R.id.rb_personal);
            rbCompany = (RadioButton) layout.findViewById(R.id.rb_company);
            rgType = (RadioGroup) layout.findViewById(R.id.rg_type);
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
                        MyToast.show(ContractListActivity.this, "请填写数量");
                        return;
                    }

                    if (rbPersonal.isChecked() || rbCompany.isChecked()) {

                    } else {
                        MyToast.show(ContractListActivity.this, "请选择合同类型");
                        return;
                    }

                    OkHttpUtils.post()
                            .url(Constants.URLS.BASEURL + "UserPic/salesApplyContract")
                            .addParams("saleId", saleID)
                            .addParams("number", etNum.getText().toString())
                            .addParams("contractType", rbPersonal.isChecked() ? "1" : "2")

                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    System.out.println("fangkuan____________________>>>>>" + e);
                                    hud.dismiss();
                                    MyToast.show(ContractListActivity.this, "加载失败");
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    LogUtils.e("fangkuan----------------->>>>>>" + response);
                                    String json = response;
                                    Gson gson = new Gson();
                                    fangkuanBean = null;

                                    try {
                                        fangkuanBean = gson.fromJson(json, Fangkuan.class);
                                        if (fangkuanBean.getCode() == 1) {

                                            dialog.dismiss();
                                            adapper.notifyDataSetChanged();
                                            MyToast.show(ContractListActivity.this, "提交成功");

                                        } else if (fangkuanBean.getCode() == 0) {
                                            MyToast.show(ContractListActivity.this, fangkuanBean.getMsg());
                                        }
                                    } catch (JsonSyntaxException e) {
                                        MyToast.show(ContractListActivity.this, "服务器错误");
                                    }


                                }
                            });

                }
            });

            initUI();
        }

        private void initUI() {
        }
    }
}
