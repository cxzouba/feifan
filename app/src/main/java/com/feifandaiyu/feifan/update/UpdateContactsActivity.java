package com.feifandaiyu.feifan.update;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.ContactsBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.popupWindow.BottomPopupOption;
import com.feifandaiyu.feifan.utils.AssetsUtils;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.NumberUtils;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.qqtheme.framework.picker.AddressPicker;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * @author houdaichang
 */
public class UpdateContactsActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    @InjectView(R.id.et_contacts_name1)
    PowerfulEditText etContactsName1;
    @InjectView(R.id.tv_contacts_home1)
    TextView tvContactsHome1;
    @InjectView(R.id.et_contacts_addr1)
    PowerfulEditText etContactsAddr1;

    @InjectView(R.id.et_contacts_phone_num1)
    PowerfulEditText etContactsPhoneNum1;
    @InjectView(R.id.et_contacts_relation1)
    TextView etContactsRelation1;
    @InjectView(R.id.et_contacts_name2)
    PowerfulEditText etContactsName2;
    @InjectView(R.id.tv_contacts_home2)
    TextView tvContactsHome2;
    @InjectView(R.id.et_contacts_addr2)
    PowerfulEditText etContactsAddr2;

    @InjectView(R.id.et_contacts_phone_num2)
    PowerfulEditText etContactsPhoneNum2;
    @InjectView(R.id.et_contacts_relation2)
    TextView etContactsRelation2;
    private ImageView iv_back;
    private TextView tv_next;
    private String province1;
    private String city1;
    private String district1;
    private String province2;
    private String city2;
    private String district2;
    private String needCautioner;
    private String isMarry;
    private BottomPopupOption bottomPopupOption;
    private InputMethodManager imm;
    private KProgressHUD hud;
    private String userId;
    private String province3;
    private String city3;
    private String area3;
    private String province4;
    private String city4;
    private String area4;
    private String id1;
    private String id2;
    private String status;

    @Override
    protected int getContentView() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("联系人信息");

        hud = KProgressHUD.create(UpdateContactsActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍候")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setText("保存");

        iv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        tvContactsHome1.setOnClickListener(this);
        tvContactsHome2.setOnClickListener(this);
        etContactsRelation1.setOnClickListener(this);
        etContactsRelation2.setOnClickListener(this);


        etContactsAddr1.addTextChangedListener(this);
        etContactsAddr2.addTextChangedListener(this);
        etContactsRelation1.addTextChangedListener(this);
        etContactsRelation2.addTextChangedListener(this);
        etContactsName1.addTextChangedListener(this);
        etContactsName2.addTextChangedListener(this);
        etContactsPhoneNum1.addTextChangedListener(this);
        etContactsPhoneNum2.addTextChangedListener(this);

        tvContactsHome1.addTextChangedListener(this);
        tvContactsHome2.addTextChangedListener(this);

//        needCautioner = PreferenceUtils.getString(this, "needCautioner");
//        isMarry = PreferenceUtils.getString(this, "isMarry");

        userId = PreferenceUtils.getString(this, "userId");
        status = PreferenceUtils.getString(this, "status");

        hud.show();

        initData();

    }

    private void initData() {

        post()
                .url(Constants.URLS.BASEURL + "UserInfo/showContact")
                .addParams("userId", userId)
                .addParams("status", status)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("updatecontactShow----------------->>>>>>." + e);
                        hud.dismiss();
                        MyToast.show(UpdateContactsActivity.this, "联网失败...");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("updatecontactShow----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();
                        ContactsBean bean = gson.fromJson(json, ContactsBean.class);

                        hud.dismiss();

                        if (bean.getCode() == 1) {

                            province3 = bean.getList().get(0).getProvince();
                            city3 = bean.getList().get(0).getCity();
                            area3 = bean.getList().get(0).getArea();

                            if (area3.equals("-1")) {
                                area3 = "";
                            } else {
                                area3 = bean.getList().get(0).getArea();
                            }

                            province4 = bean.getList().get(1).getProvince();
                            city4 = bean.getList().get(1).getCity();
                            area4 = bean.getList().get(1).getArea();

                            if (area4.equals("-1")) {
                                area4 = "";
                            } else {
                                area4 = bean.getList().get(1).getArea();
                            }

                            etContactsName1.setText(bean.getList().get(0).getUsername());
                            etContactsPhoneNum1.setText(bean.getList().get(0).getTelphone());
                            etContactsRelation1.setText(bean.getList().get(0).getRelation());
                            tvContactsHome1.setText(province3 + city3 + area3);
                            etContactsAddr1.setText(bean.getList().get(0).getAddress());
                            etContactsName2.setText(bean.getList().get(1).getUsername());
                            etContactsPhoneNum2.setText(bean.getList().get(1).getTelphone());
                            etContactsRelation2.setText(bean.getList().get(1).getRelation());
                            tvContactsHome2.setText(province4 + city4 + area4);
                            etContactsAddr2.setText(bean.getList().get(1).getAddress());

                            id1 = bean.getList().get(0).getId();
                            id2 = bean.getList().get(1).getId();


                        } else if (bean.getCode() == 0) {
                            new AlertDialog.Builder(UpdateContactsActivity.this)

                                    .setMessage(bean.getMsg())
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                            int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                            overridePendingTransition(enterAnim0, exitAnim0);
                                        }
                                    })
                                    .setCancelable(false)
                                    .show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        bottomPopupOption = new BottomPopupOption(UpdateContactsActivity.this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (v.getId()) {
            case R.id.tv_next:

//                if (province1 != null) {
//                    if (province1.equals("北京市") || province1.equals("上海市") || province1.equals("天津市") || province1.equals("重庆市")) {
//                        city1 = district1;
//                        district1 = "-1";
//                    }
//                }
//
//                if (province2 != null) {
//                    if (province2.equals("北京市") || province2.equals("上海市") || province2.equals("天津市") || province2.equals("重庆市")) {
//                        city2 = district2;
//                        district2 = "-1";
//                    }
//                }

                if (area3.equals("")) {
                    area3 = "-1";
                }

                if (area4.equals("")) {
                    area4 = "-1";
                }


                String userId = PreferenceUtils.getString(this, "userId");

                String name1 = etContactsName1.getText().toString().trim();
                String name2 = etContactsName2.getText().toString().trim();
                String phone1 = etContactsPhoneNum1.getText().toString().trim();
                String phone2 = etContactsPhoneNum2.getText().toString().trim();
                String relation1 = etContactsRelation1.getText().toString().trim();
                String relation2 = etContactsRelation2.getText().toString().trim();
                String addr1 = etContactsAddr1.getText().toString().trim();
                String addr2 = etContactsAddr2.getText().toString().trim();
                if (!NumberUtils.isMobile(phone1)) {
                    MyToast.show(this, "联系人1手机号码不是11位，请仔细检查");

                    return;
                }

                if (!NumberUtils.isMobile(phone2)) {
                    MyToast.show(this, "联系人2手机号码不是11位，请仔细检查");

                    return;
                }

                hud.show();

                JSONObject contact1 = new JSONObject();

                try {
                    contact1.put("userid", userId);
                    contact1.put("id", id1);
                    contact1.put("username", name1);
                    contact1.put("telphone", phone1);
                    contact1.put("relation", relation1);
                    contact1.put("province", province1 == null ? province3 : province1);
                    contact1.put("city", city1 == null ? city3 : city1);
                    contact1.put("area", district1 == null ? area3 : district1);
                    contact1.put("address", addr1);
                    contact1.put("status", status);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject contact2 = new JSONObject();

                try {
                    contact2.put("userid", userId);
                    contact2.put("id", id2);
                    contact2.put("username", name2);
                    contact2.put("telphone", phone2);
                    contact2.put("relation", relation2);
                    contact2.put("province", province2 == null ? province4 : province2);
                    contact2.put("city", city2 == null ? city4 : city2);
                    contact2.put("area", district2 == null ? area4 : district2);
                    contact2.put("address", addr2);
                    contact2.put("status", status);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject contacts = new JSONObject();

                try {
                    contacts.put("contact1", contact1);
                    contacts.put("contact2", contact2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println(contacts.toString().trim());

                post()

                        .url(Constants.URLS.BASEURL + "UserUpdate/editContact")

                        .addParams("contacts", contacts.toString())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                System.out.println("updatecontactEdit____________________>>>>>" + e);
                                hud.dismiss();
                                MyToast.show(UpdateContactsActivity.this, "网络连接失败");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.e("updatecontactEdit----------------->>>>>>." + response);

                                hud.dismiss();
                                String json = response;
                                Gson gson = new Gson();
                                ContactsBean bean = gson.fromJson(json, ContactsBean.class);

                                if (bean.getCode() == 1) {

                                    MyToast.show(UpdateContactsActivity.this, "联系人信息保存成功");

                                    finish();
                                    // // 设置过渡动画
                                    int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                    int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                    overridePendingTransition(enterAnim0, exitAnim0);
                                } else {
                                    MyToast.show(UpdateContactsActivity.this, bean.getMsg());
                                }

                            }
                        });


                break;
            case R.id.iv_back:
                new AlertDialog.Builder(this)

                        .setMessage("是否取消修改？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                int enterAnim0 = R.anim.pre_enter; //进入的activity对应的动画资源
                                int exitAnim0 = R.anim.pre_exit; //结束的activity对应的动画资源
                                overridePendingTransition(enterAnim0, exitAnim0);
                            }
                        })

                        .setNegativeButton("取消", null)
                        .show();

                break;

            case R.id.tv_contacts_home1:
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                showSelectHomeDialog1();
                break;
            case R.id.tv_contacts_home2:
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                showSelectHomeDialog2();
                break;
            case R.id.et_contacts_relation1:
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                chooseRelation1();
                break;
            case R.id.et_contacts_relation2:
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                chooseRelation2();
                break;

            default:
        }
    }

    private void chooseRelation2() {
        bottomPopupOption.setItemText("同事", "朋友", "父亲", "母亲", "子女", "兄弟姐妹");
        bottomPopupOption.showPopupWindow();
        bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    etContactsRelation2.setText("同事");
                    bottomPopupOption.dismiss();
                } else if (position == 1) {
                    etContactsRelation2.setText("朋友");
                    bottomPopupOption.dismiss();
                } else if (position == 2) {
                    etContactsRelation2.setText("父亲");
                    bottomPopupOption.dismiss();
                } else if (position == 3) {
                    etContactsRelation2.setText("母亲");
                    bottomPopupOption.dismiss();
                } else if (position == 4) {
                    etContactsRelation2.setText("子女");
                    bottomPopupOption.dismiss();
                } else if (position == 5) {
                    etContactsRelation2.setText("兄弟姐妹");
                    bottomPopupOption.dismiss();
                }
            }
        });
    }

    private void chooseRelation1() {
        bottomPopupOption.setItemText("同事", "朋友", "父亲", "母亲", "子女", "兄弟姐妹");
        bottomPopupOption.showPopupWindow();
        bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    etContactsRelation1.setText("同事");
                    bottomPopupOption.dismiss();
                } else if (position == 1) {
                    etContactsRelation1.setText("朋友");
                    bottomPopupOption.dismiss();
                } else if (position == 2) {
                    etContactsRelation1.setText("父亲");
                    bottomPopupOption.dismiss();
                } else if (position == 3) {
                    etContactsRelation1.setText("母亲");
                    bottomPopupOption.dismiss();
                } else if (position == 4) {
                    etContactsRelation1.setText("子女");
                    bottomPopupOption.dismiss();
                } else if (position == 5) {
                    etContactsRelation1.setText("兄弟姐妹");
                    bottomPopupOption.dismiss();
                }
            }
        });
    }

    private void showSelectHomeDialog1() {
        //选择城市颜色
//        int color1 = getResources().getColor(R.color.xuanzechengshi);
        //带选择城市颜色
//        int color2 = getResources().getColor(R.color.beixuanchengshi);
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(UpdateContactsActivity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(UpdateContactsActivity.this, data);
        picker.setSelectedItem("黑龙江省", "哈尔滨市", "道里区");
        picker.setTitleText("地址选择");
        picker.setTitleTextColor(Color.BLACK);
        picker.setLineVisible(true);
        picker.setCancelText("取消");
        picker.setSubmitText(" 确定");
        picker.setOffset(2);
//        picker.setTextColor(color1, color2);
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            //                                  省           市           区
            public void onAddressPicked(String province, String city, String county) {
                //省市区
                tvContactsHome1.setText(province + city + county);
                //省
                UpdateContactsActivity.this.province1 = province;
//                //市
                UpdateContactsActivity.this.city1 = city;
//                //区
                UpdateContactsActivity.this.district1 = county;
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
        String json = AssetsUtils.readText(UpdateContactsActivity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(UpdateContactsActivity.this, data);
        picker.setSelectedItem("黑龙江省", "哈尔滨市", "道里区");
        picker.setTitleText("地址选择");
        picker.setTitleTextColor(Color.BLACK);
        picker.setLineVisible(true);
        picker.setCancelText("取消");
        picker.setSubmitText(" 确定");
        picker.setOffset(2);
//        picker.setTextColor(color1, color2);
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            //                                  省           市           区
            public void onAddressPicked(String province, String city, String county) {
                //省市区
                tvContactsHome2.setText(province + city + county);
                //省
                UpdateContactsActivity.this.province2 = province;
//                //市
                UpdateContactsActivity.this.city2 = city;
//                //区
                UpdateContactsActivity.this.district2 = county;
            }
        });
        picker.show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!StringUtils.isEmpty(etContactsPhoneNum1.getText().toString())
                && !StringUtils.isEmpty(etContactsAddr1.getText().toString())
                && !StringUtils.isEmpty(etContactsRelation1.getText().toString())
                && !StringUtils.isEmpty(etContactsName1.getText().toString())
                && !StringUtils.isEmpty(etContactsAddr2.getText().toString())
                && !StringUtils.isEmpty(etContactsRelation2.getText().toString())

                && !StringUtils.isEmpty(etContactsName2.getText().toString())
                && !StringUtils.isEmpty(etContactsPhoneNum2.getText().toString())
                && !StringUtils.isEmpty(tvContactsHome1.getText().toString())
                && !StringUtils.isEmpty(tvContactsHome2.getText().toString())
                )

        {
            tv_next.setEnabled(true);
        } else {
            tv_next.setEnabled(false);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }
}
