package com.feifandaiyu.feifan.activity.personalloan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.qqtheme.framework.picker.AddressPicker;
import okhttp3.Call;

/**
 * @author houdaichang
 */
public class ContactsActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

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

    @Override
    protected int getContentView() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("联系人信息");

        hud = KProgressHUD.create(ContactsActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_next = (TextView) findViewById(R.id.tv_next);

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

        needCautioner = PreferenceUtils.getString(this, "needCautioner");
        isMarry = PreferenceUtils.getString(this, "isMarry");

    }


    @Override
    public void onClick(View v) {
        bottomPopupOption = new BottomPopupOption(ContactsActivity.this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (v.getId()) {
            case R.id.tv_next:

//                if (province1 != null) {
//                    if (province1.equals("北京市") || province1.equals("上海市") || province1.equals("天津市") || province1.equals("重庆市")) {
//                        city1 = district1;
//                        district1 = "-1";
//                    }
//                }

//                if (province2 != null) {
//                    if (province2.equals("北京市") || province2.equals("上海市") || province2.equals("天津市") || province2.equals("重庆市")) {
//                        city2 = district2;
//                        district2 = "-1";
//                    }
//                }
                if (!etContactsRelation1.getText().toString().equals("朋友")) {
                    if (tvContactsHome1.getText().toString().equals("") || tvContactsHome1.getText().toString().equals("")) {
                        MyToast.show(ContactsActivity.this, "请填写联系人1的地址");
                        return;
                    }
                }

                if (!etContactsRelation2.getText().toString().equals("朋友")) {
                    if (tvContactsHome2.getText().toString().equals("") || tvContactsHome2.getText().toString().equals("")) {
                        MyToast.show(ContactsActivity.this, "请填写联系人2的地址");
                        return;
                    }
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
                    contact1.put("username", name1);
                    contact1.put("telphone", phone1);
                    contact1.put("relation", relation1);
                    contact1.put("province", province1);
                    contact1.put("city", city1);
                    contact1.put("area", district1);
                    contact1.put("address", addr1);
                    contact1.put("status", "0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject contact2 = new JSONObject();

                try {
                    contact2.put("userid", userId);
                    contact2.put("username", name2);
                    contact2.put("telphone", phone2);
                    contact2.put("relation", relation2);
                    contact2.put("province", province2);
                    contact2.put("city", city2);
                    contact2.put("area", district2);
                    contact2.put("address", addr2);
                    contact2.put("status", "0");

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

                OkHttpUtils
                        .post()

                        .url(Constants.URLS.BASEURL + "Login/addContacts")

                        .addParams("contacts", contacts.toString())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                System.out.println("contact____________________>>>>>" + e);
                                hud.dismiss();
                                MyToast.show(ContactsActivity.this, "网络连接失败");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.e("contact----------------->>>>>>." + response);

                                hud.dismiss();
                                String json = response;
                                Gson gson = new Gson();
                                ContactsBean bean = gson.fromJson(json, ContactsBean.class);

                                if (bean.getCode() == 1) {

//                                    if (needCautioner.equals("0") && isMarry.equals("0")) {
//
//                                        startActivity(new Intent(ContactsActivity.this, WifeActivity.class));
//
//                                    } else {
                                    if (needCautioner.equals("1")) {

                                        startActivity(new Intent(ContactsActivity.this, CautionerMessageActivity.class));

                                    } else {

                                        if (isMarry.equals("2")) {
                                            startActivity(new Intent(ContactsActivity.this, WifeActivity.class));

                                        } else {
                                            startActivity(new Intent(ContactsActivity.this, BankCardMessageActivity.class));

                                        }
                                    }
//                                    }


                                    finish();
                                    // // 设置过渡动画
                                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                    overridePendingTransition(enterAnim6, exitAnim6);
                                } else {
                                    MyToast.show(ContactsActivity.this, bean.getMsg());
                                }


                            }
                        });


                break;
            case R.id.iv_back:
                new AlertDialog.Builder(this)

                        .setMessage(StringCreateUtils.createString())
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

    private void showSelectHomeDialog1() {
        //选择城市颜色
//        int color1 = getResources().getColor(R.color.xuanzechengshi);
        //带选择城市颜色
//        int color2 = getResources().getColor(R.color.beixuanchengshi);
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(ContactsActivity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(ContactsActivity.this, data);
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
                ContactsActivity.this.province1 = province;
//                //市
                ContactsActivity.this.city1 = city;
//                //区
                ContactsActivity.this.district1 = county;
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
        String json = AssetsUtils.readText(ContactsActivity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(ContactsActivity.this, data);
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
                ContactsActivity.this.province2 = province;
//                //市
                ContactsActivity.this.city2 = city;
//                //区
                ContactsActivity.this.district2 = county;
            }
        });
        picker.show();
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!StringUtils.isEmpty(etContactsPhoneNum1.getText().toString())
                && !StringUtils.isEmpty(etContactsRelation1.getText().toString())
                && !StringUtils.isEmpty(etContactsName1.getText().toString())
                && !StringUtils.isEmpty(etContactsRelation2.getText().toString())

                && !StringUtils.isEmpty(etContactsName2.getText().toString())
                && !StringUtils.isEmpty(etContactsPhoneNum2.getText().toString())
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
