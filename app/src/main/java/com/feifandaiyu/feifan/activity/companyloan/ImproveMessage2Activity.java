package com.feifandaiyu.feifan.activity.companyloan;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.Improve2Bean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.AssetsUtils;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.AddressPicker;
import okhttp3.Call;

/**
 * Created by houdaichang on 2017/5/9.
 */

public class ImproveMessage2Activity extends BaseActivity implements TextWatcher {
    @InjectView(R.id.et_company_name)
    PowerfulEditText etCompanyName;
    @InjectView(R.id.et_code_name)
    PowerfulEditText etCodeName;
    @InjectView(R.id.ll_team_certificate)
    LinearLayout llTeamCertificate;
    @InjectView(R.id.tv_belong_home)
    TextView tvBelongHome;
    @InjectView(R.id.et_belong_addr)
    PowerfulEditText etBelongAddr;
    @InjectView(R.id.tv_location_home)
    TextView tvLocationHome;
    @InjectView(R.id.et_location_addr)
    PowerfulEditText etLocationAddr;
    @InjectView(R.id.et_do_what)
    PowerfulEditText etDoWhat;
    @InjectView(R.id.et_company_type)
    TextView etCompanyType;
    @InjectView(R.id.et_area_code)
    EditText etAreaCode;
    @InjectView(R.id.et_company_num)
    PowerfulEditText etCompanyNum;

    private TextView tvNext;
    private String blong_province;
    private String belong_city;
    private String belong_district;
    private String location_province;
    private String location_city;
    private String location_district;
    private ArrayList<String> list = new ArrayList<>();
    private InputMethodManager imm;
    private String userId;
    private KProgressHUD hud;

    @Override
    protected int getContentView() {
        return R.layout.activity_improve_message2;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("企业信息");
        showBack(true);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        tvNext = (TextView) findViewById(R.id.tv_next);
        etBelongAddr.addTextChangedListener(this);
        etCodeName.addTextChangedListener(this);
        etCompanyName.addTextChangedListener(this);
        etCompanyNum.addTextChangedListener(this);
        etDoWhat.addTextChangedListener(this);
        etLocationAddr.addTextChangedListener(this);
        tvLocationHome.addTextChangedListener(this);
        tvBelongHome.addTextChangedListener(this);
        etCompanyType.addTextChangedListener(this);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

    }


    @OnClick({R.id.iv_back, R.id.tv_next, R.id.tv_belong_home, R.id.tv_location_home, R.id.et_company_type})
    public void onViewClicked(View view) {

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        switch (view.getId()) {

            case R.id.tv_belong_home:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                showSelectHomeDialog1();
                break;

            case R.id.tv_location_home:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                showSelectHomeDialog2();
                break;

            case R.id.iv_back:
                new AlertDialog.Builder(this)
                        .setMessage("如果返回,将重新建档")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                overridePendingTransition(enterAnim0, exitAnim0);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;

            case R.id.tv_next:
                hud.show();

//                if (blong_province != null) {
//                    if (blong_province.equals("北京市") || blong_province.equals("上海市") || blong_province.equals("天津市") || blong_province.equals("重庆市")) {
//                        belong_city = belong_district;
//                        belong_district = "-1";
//                    }
//                }
//
//                if (location_province != null) {
//                    if (location_province.equals("北京市") || location_province.equals("上海市") || location_province.equals("天津市") || location_province.equals("重庆市")) {
//                        location_city = location_district;
//                        location_district = "-1";
//                    }
//                }

                String legalName = PreferenceUtils.getString(ImproveMessage2Activity.this, "LegalName");
                String certificateNum = PreferenceUtils.getString(ImproveMessage2Activity.this, "CertificateNum");
                String legalPhoneNum = PreferenceUtils.getString(ImproveMessage2Activity.this, "LegalPhoneNum");
                String companyCartype = PreferenceUtils.getString(ImproveMessage2Activity.this, "CompanyCartype");

                String saleID = PreferenceUtils.getString(ImproveMessage2Activity.this, "saleID");
                String company_name = etCompanyName.getText().toString().trim();
                String code = etCodeName.getText().toString().trim();
                String blong_addr = etBelongAddr.getText().toString().trim();
                String location_addr = etLocationAddr.getText().toString().trim();
                String CompanyNum = etCompanyNum.getText().toString().trim();
                String DoWhat = etDoWhat.getText().toString().trim();
                String CompanyType = etCompanyType.getText().toString().trim();

                System.out.println(company_name +
                        code + blong_province + belong_city + belong_district + blong_addr + location_province
                        + location_city + location_district + location_addr + CompanyNum + CompanyType + DoWhat);

                OkHttpUtils
                        .post()
                        .url(Constants.URLS.BASEURL + "Company/registerCompany")
                        .addParams("legperson_name", legalName)
                        .addParams("legperson_code", certificateNum)
                        .addParams("telphone", legalPhoneNum)
                        .addParams("cartype", companyCartype)
                        .addParams("salesman_id", saleID)
                        .addParams("company_name", company_name)
                        .addParams("organize_code", code)
                        .addParams("province", blong_province)
                        .addParams("city", belong_city)
                        .addParams("area", belong_district)
                        .addParams("address", blong_addr)
                        .addParams("ope_province", location_province)
                        .addParams("ope_city", location_city)
                        .addParams("ope_area", location_district)
                        .addParams("ope_address", location_addr)
                        .addParams("mobile", CompanyNum)
                        .addParams("com_nature", CompanyType)
                        .addParams("com_industry", DoWhat)
                        .addParams("area_code", etAreaCode.getText().toString())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                System.out.println("improve2____________________>>>>>" + e);
                                hud.dismiss();
                                MyToast.show(ImproveMessage2Activity.this, "服务器正忙，请稍后再试...");
                                return;
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.e("improve2----------------->>>>>>." + response);
                                String json = response;
                                Gson gson = new Gson();
                                Improve2Bean bean = gson.fromJson(json, Improve2Bean.class);

                                System.out.println("companyId=" + bean.getList().getId());

                                PreferenceUtils.setString(ImproveMessage2Activity.this, "userId", bean.getList().getUserId());

                                if (bean.getCode() == 1) {
                                    hud.dismiss();
                                    startActivity(new Intent(ImproveMessage2Activity.this, CompanyContactsActivity.class));
                                    finish();
                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                    overridePendingTransition(enterAnim6, exitAnim6);
                                } else if (bean.getCode() == 0) {
                                    hud.dismiss();
                                    MyToast.show(ImproveMessage2Activity.this, bean.getMsg());
                                }

                            }
                        });

                break;
            case R.id.et_company_type:
                showCompanyType();
                break;
        }
    }

    private void showCompanyType() {
        LayoutInflater inflater = LayoutInflater.from(this);//将xml布局转换为view
        View view = inflater.inflate(R.layout.dialog_type, null);//将xml布局转换为view,里面有listview
        ListView listView = (ListView) view.findViewById(R.id.lv_type);

        list = new ArrayList<String>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData());
        listView.setAdapter(adapter);


        final Dialog builder = new Dialog(ImproveMessage2Activity.this);
        builder.setTitle("请选择公司类型");
        builder.setContentView(view);
        builder.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String type = list.get(position);
                etCompanyType.setText(type);
                builder.dismiss();
            }
        });

    }

    private ArrayList<String> getData() {
        list.add("外资(欧美)");
        list.add("外资(非欧美)");
        list.add("合资");
        list.add("国企");
        list.add("民营公司");
        list.add("上市公司");
        list.add("创业公司");
        list.add("外企代表处");
        list.add("政府机关");
        list.add("事业单位");
        list.add("非营利机构");
        list.add("个体");
        list.add("私营");
        list.add("股份制有限公司");
        return list;
    }


    private void showSelectHomeDialog1() {
        //选择城市颜色
//        int color1 = getResources().getColor(R.color.xuanzechengshi);
        //带选择城市颜色
//        int color2 = getResources().getColor(R.color.beixuanchengshi);
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(ImproveMessage2Activity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(ImproveMessage2Activity.this, data);
        picker.setSelectedItem("黑龙江省", "哈尔滨市", "道里区");
        picker.setTitleText("地址选择");
        picker.setTitleTextColor(Color.BLACK);
        picker.setLineVisible(false);
        picker.setTextSize(18);
        ;
        picker.setCancelText("取消");
        picker.setSubmitText(" 确定");
        picker.setOffset(2);
//        picker.setTextColor(color1, color2);
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            //                                  省           市           区
            public void onAddressPicked(String province, String city, String county) {
                //省市区
                tvBelongHome.setText(province + city + county);
                //省
                ImproveMessage2Activity.this.blong_province = province;
//                //市
                ImproveMessage2Activity.this.belong_city = city;
//                //区
                ImproveMessage2Activity.this.belong_district = county;
            }
        });
        picker.show();
    }

    private void showSelectHomeDialog2() {
        //选择城市颜色
//        int color1 = getResources().getColor(R.color.xuanzechengshi);
        //带选择城市颜色
//        int color2 = getResources().getColor(R.color.beixuanchengshi);
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(ImproveMessage2Activity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(ImproveMessage2Activity.this, data);
        picker.setSelectedItem("黑龙江省", "哈尔滨市", "道里区");
        picker.setTitleText("地址选择");
        picker.setTitleTextColor(Color.BLACK);
        picker.setLineVisible(false);
        picker.setTextSize(18);
        picker.setCancelText("取消");
        picker.setSubmitText(" 确定");
        picker.setOffset(2);
//        picker.setTextColor(color1, color2);
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            //                                  省           市           区
            public void onAddressPicked(String province, String city, String county) {
                //省市区
                tvLocationHome.setText(province + city + county);
                //省
                ImproveMessage2Activity.this.location_province = province;
//                //市
                ImproveMessage2Activity.this.location_city = city;
//                //区
                ImproveMessage2Activity.this.location_district = county;
            }
        });
        picker.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!StringUtils.isEmpty(etCompanyType.getText().toString())
                && !StringUtils.isEmpty(etLocationAddr.getText().toString())
                && !StringUtils.isEmpty(etDoWhat.getText().toString())
                && !StringUtils.isEmpty(etCompanyNum.getText().toString())
                && !StringUtils.isEmpty(etBelongAddr.getText().toString())
                && !StringUtils.isEmpty(etCodeName.getText().toString())
                && !StringUtils.isEmpty(etCompanyName.getText().toString())
                && !StringUtils.isEmpty(tvBelongHome.getText().toString())
                && !StringUtils.isEmpty(tvLocationHome.getText().toString())
                )

        {
            tvNext.setEnabled(true);
        } else {
            tvNext.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }
}
