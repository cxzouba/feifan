package com.feifandaiyu.feifan.update;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.MyApplication;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.personalloan.CautionerMessageActivity;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CautionerMessageActivityBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.popupWindow.BottomPopupOption;
import com.feifandaiyu.feifan.utils.AssetsUtils;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.AddressPicker;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * Created by houdaichang on 2017/5/10.
 */

public class UpdateCautionerMessageActivity extends BaseActivity implements TextWatcher {
    @InjectView(R.id.person_danbao)
    TextView PerssonDanbao;
    @InjectView(R.id.rb_local)
    RadioButton rbLocal;
    @InjectView(R.id.rb_not_local)
    RadioButton rbNotLocal;
    @InjectView(R.id.et_cautioner_name)
    PowerfulEditText etCautionerName;
    @InjectView(R.id.tv_sex)
    TextView tvSex;
    @InjectView(R.id.et_cautioner_phone)
    PowerfulEditText etCautionerPhone;
    @InjectView(R.id.tv_choose_certificate)
    TextView tvChooseCertificate;
    @InjectView(R.id.et_certificate_num)
    PowerfulEditText etCertificateNum;
    @InjectView(R.id.tv_home)
    TextView tvHome;
    @InjectView(R.id.et_addr)
    PowerfulEditText etAddr;
    @InjectView(R.id.et_company_name)
    PowerfulEditText etCompanyName;
    @InjectView(R.id.tv_company_home)
    TextView tvCompanyHome;
    @InjectView(R.id.et_company_addr)
    PowerfulEditText etCompanyAddr;
    @InjectView(R.id.textView13)
    TextView textView13;
    @InjectView(R.id.et_company_phone)
    PowerfulEditText etCompanyPhone;
    @InjectView(R.id.et_choose_relationship)
    TextView etChooseRelationship;
    @InjectView(R.id.tv_look)
    TextView tvLook;
    @InjectView(R.id.et_area_code)
    EditText etAreaCode;
    @InjectView(R.id.ll_cautioner_pic)
    LinearLayout llCautionerPic;
    private BottomPopupOption bottomPopupOption;
    private TextView tv_next;
    private String province1;
    private String city1;
    private String district1;
    private String province2;
    private String city2;
    private String district2;
    private String isMarry;
    private String image;
    private KProgressHUD hud;
    private InputMethodManager imm;
    private String userId;
    private String province3;
    private String city3;
    private String area3;
    private String province4;
    private String city4;
    private String area4;
    private String cautionerId;


    @Override
    protected int getContentView() {
        return R.layout.activity_cautioner_message;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("共借人信息");

        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setText("保存");

        hud = KProgressHUD.create(UpdateCautionerMessageActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍候")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        llCautionerPic.setVisibility(View.GONE);

        etAddr.addTextChangedListener(this);
        etCautionerName.addTextChangedListener(this);
        tvSex.addTextChangedListener(this);
        etCautionerPhone.addTextChangedListener(this);
        tvChooseCertificate.addTextChangedListener(this);
        etCertificateNum.addTextChangedListener(this);
        tvHome.addTextChangedListener(this);
        etCompanyPhone.addTextChangedListener(this);
        etCompanyName.addTextChangedListener(this);
        tvCompanyHome.addTextChangedListener(this);
        etCompanyAddr.addTextChangedListener(this);
        etChooseRelationship.addTextChangedListener(this);


        MyApplication.getInstance().addActivity(this);

        userId = PreferenceUtils.getString(this, "userId");
        isMarry = PreferenceUtils.getString(this, "isMarry");

        hud.show();

        initData();
    }

    private void initData() {

        post()
                .url(Constants.URLS.BASEURL + "UserInfo/guarantee")
                .addParams("userId", userId)
                .addParams("flag", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("updateCautionerShow----------------->>>>>>." + e);
                        hud.dismiss();
                        MyToast.show(UpdateCautionerMessageActivity.this, "联网失败...");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("updateCautionerShow----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();
                        CautionerMessageActivityBean bean = gson.fromJson(json, CautionerMessageActivityBean.class);

                        hud.dismiss();

                        if (bean.getCode() == 1) {

                            if (bean.getList().getIsfund().equals("本地")) {
                                rbLocal.setChecked(true);
                            } else if (bean.getList().getIsfund().equals("非本地")) {
                                rbNotLocal.setChecked(true);
                            }

                            etCautionerName.setText(bean.getList().getGuaranteeName());
                            etCautionerName.setTextColor(Color.GRAY);
                            etCautionerName.setEnabled(false);

                            tvSex.setText(bean.getList().getSex());
                            etCautionerPhone.setText(bean.getList().getTelphone());
                            tvChooseCertificate.setText(bean.getList().getCardType());

                            etCertificateNum.setText(bean.getList().getCardnum());
                            etCertificateNum.setTextColor(Color.GRAY);
                            etCertificateNum.setEnabled(false);

                            province3 = bean.getList().getHomeProvince();
                            city3 = bean.getList().getHomeCity();
                            area3 = bean.getList().getHomeArea();

                            if (area3.equals("-1")) {
                                area3 = "";
                            } else {
                                area3 = bean.getList().getHomeArea();
                            }

                            tvHome.setText(province3 + city3 + area3);
                            etAddr.setText(bean.getList().getHomeAddress());

                            province4 = bean.getList().getProvince();
                            city4 = bean.getList().getCity();
                            area4 = bean.getList().getArea();

                            if (area4.equals("-1")) {
                                area4 = "";
                            } else {
                                area4 = bean.getList().getArea();
                            }

                            tvCompanyHome.setText(province4 + city4 + area4);
                            etCompanyAddr.setText(bean.getList().getAddress());

                            etCompanyName.setText(bean.getList().getWorkName());
                            etCompanyPhone.setText(bean.getList().getWorkPhone());

                            if (!bean.getList().getAreaCode().equals("-1")) {

                                etAreaCode.setText(bean.getList().getAreaCode());
                            } else {
                                etAreaCode.setText("");
                            }

                            etChooseRelationship.setText(bean.getList().getRelations());

                            cautionerId = bean.getList().getId();

                        } else if (bean.getCode() == 0) {
                            new AlertDialog.Builder(UpdateCautionerMessageActivity.this)

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
                        }else if (bean.getCode()==2){
                            new AlertDialog.Builder(UpdateCautionerMessageActivity.this)

                                    .setMessage("您尚未添加过共借人，是否去添加？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(UpdateCautionerMessageActivity.this, CautionerMessageActivity.class));
                                            PreferenceUtils.setString(UpdateCautionerMessageActivity.this,"cautioner","1");
                                            finish();
                                            int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                            int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                            overridePendingTransition(enterAnim6, exitAnim6);
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                            int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                            overridePendingTransition(enterAnim0, exitAnim0);
                                        }
                                    })
                                    .setCancelable(false)
                                    .show()
                                    .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));

                        }
                    }
                });

    }

    @OnClick({R.id.iv_back, R.id.tv_next, R.id.tv_sex, R.id.tv_home,
            R.id.tv_company_home, R.id.person_danbao, R.id.tv_look, R.id.et_choose_relationship})
    public void onViewClicked(View view) {
        bottomPopupOption = new BottomPopupOption(UpdateCautionerMessageActivity.this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {

            case R.id.tv_home:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                showSelectHomeDialog1();
                break;
            case R.id.tv_company_home:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                showSelectHomeDialog2();
                break;
            case R.id.et_choose_relationship:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                chooseRelation();
                break;
            case R.id.tv_sex:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                bottomPopupOption.setItemText("男", "女");
                bottomPopupOption.showPopupWindow();
                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 0) {
                            tvSex.setText("男");
                            bottomPopupOption.dismiss();
                        } else if (position == 1) {
                            tvSex.setText("女");
                            bottomPopupOption.dismiss();
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
                                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                overridePendingTransition(enterAnim0, exitAnim0);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;

//            case R.id.tv_look:
//                startActivity(new Intent(this, UpdateTestActivity.class));
//                break;
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

                hud.show();

                String guaranteeName = etCautionerName.getText().toString().trim();
                String telphone = etCautionerPhone.getText().toString().trim();
                String cardnum = etCertificateNum.getText().toString().trim();
                String relations = etChooseRelationship.getText().toString().trim();
                String workName = etCompanyName.getText().toString().trim();
                String workPhone = etCompanyPhone.getText().toString().trim();
                String address1 = etAddr.getText().toString().trim();
                String address2 = etCompanyAddr.getText().toString().trim();


                String cardTypeMessage = tvChooseCertificate.getText().toString();

                post()
                        //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                        .url(Constants.URLS.BASEURL + "UserUpdate/editGuarantee")
                        .addParams("userId", userId)
                        .addParams("sex", tvSex.getText().toString().equals("男") ? "0" : "1")
                        .addParams("guaranteeName", guaranteeName)
                        .addParams("telphone", telphone)
                        .addParams("cardType", "1")
                        .addParams("cardnum", cardnum)
                        .addParams("relations", relations)
                        .addParams("workName", workName)
                        .addParams("workPhone", workPhone)
                        .addParams("province", province2 == null ? province4 : province2)
                        .addParams("city", city2 == null ? city4 : city2)
                        .addParams("area", district2 == null ? area4 : district2)
                        .addParams("address", address2)
                        .addParams("homeProvince", province1 == null ? province3 : province1)
                        .addParams("homeCity", city1 == null ? city3 : city1)
                        .addParams("homeArea", district1 == null ? area3 : district1)
                        .addParams("homeAddress", address1)
                        .addParams("isfund", rbLocal.isChecked() ? "0" : "1")
                        .addParams("flag", "0")
                        .addParams("id", cautionerId)
//                            .addParams("image", image)
                        .addParams("areaCode", etAreaCode.getText().toString())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                System.out.println("updateCautionerEdit____________________>>>>>" + e);
                                hud.dismiss();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.e("updateCautionerEdit----------------->>>>>>." + response);

                                hud.dismiss();

                                String json = response;
                                Gson gson = new Gson();
                                CautionerMessageActivityBean bean = gson.fromJson(json, CautionerMessageActivityBean.class);
                                if (bean.getCode() == 0) {
                                    MyToast.show(UpdateCautionerMessageActivity.this, bean.getMsg());
                                    return;
                                }
                                if (bean.getCode() == 1) {

//                                        PreferenceUtils.setString(UpdateCautionerMessageActivity.this, "imagecount", "请添加照片");

                                    MyToast.show(UpdateCautionerMessageActivity.this, "共借人信息保存成功");

                                    finish();
                                    int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                    int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                    overridePendingTransition(enterAnim0, exitAnim0);
                                }


                            }
                        });


                break;

                default:

        }
    }

    private void chooseRelation() {
        bottomPopupOption.setItemText("同事", "朋友", "父亲", "母亲", "子女","兄弟姐妹");
        bottomPopupOption.showPopupWindow();
        bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    etChooseRelationship.setText("同事");
                    bottomPopupOption.dismiss();
                } else if (position == 1) {
                    etChooseRelationship.setText("朋友");
                    bottomPopupOption.dismiss();
                } else if (position == 2) {
                    etChooseRelationship.setText("父亲");
                    bottomPopupOption.dismiss();
                } else if (position == 3) {
                    etChooseRelationship.setText("母亲");
                    bottomPopupOption.dismiss();
                } else if (position == 4) {
                    etChooseRelationship.setText("子女");
                    bottomPopupOption.dismiss();
                }else if (position == 5) {
                    etChooseRelationship.setText("兄弟姐妹");
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
        String json = AssetsUtils.readText(UpdateCautionerMessageActivity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(UpdateCautionerMessageActivity.this, data);
        picker.setSelectedItem("黑龙江省", "哈尔滨市", "道里区");
        picker.setTitleText("地址选择");
        picker.setTitleTextColor(Color.BLACK);
picker.setLineVisible(false);        picker.setTextSize(18);;
        picker.setCancelText("取消");
        picker.setSubmitText(" 确定");
        picker.setOffset(2);
//        picker.setTextColor(color1, color2);
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            //                                  省           市           区
            public void onAddressPicked(String province, String city, String county) {
                //省市区
                tvHome.setText(province + city + county);
                //省
                UpdateCautionerMessageActivity.this.province1 = province;
//                //市
                UpdateCautionerMessageActivity.this.city1 = city;
//                //区
                UpdateCautionerMessageActivity.this.district1 = county;
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
        String json = AssetsUtils.readText(UpdateCautionerMessageActivity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(UpdateCautionerMessageActivity.this, data);
        picker.setSelectedItem("黑龙江省", "哈尔滨市", "道里区");
        picker.setTitleText("地址选择");
        picker.setTitleTextColor(Color.BLACK);
picker.setLineVisible(false);        picker.setTextSize(18);;
        picker.setCancelText("取消");
        picker.setSubmitText(" 确定");
        picker.setOffset(2);
//        picker.setTextColor(color1, color2);
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            //                                  省           市           区
            public void onAddressPicked(String province, String city, String county) {
                //省市区
                tvCompanyHome.setText(province + city + county);
                //省
                UpdateCautionerMessageActivity.this.province2 = province;
//                //市
                UpdateCautionerMessageActivity.this.city2 = city;
//                //区
                UpdateCautionerMessageActivity.this.district2 = county;
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
        if (!StringUtils.isEmpty(etChooseRelationship.getText().toString())
                && !StringUtils.isEmpty(etCompanyAddr.getText().toString())
                && !StringUtils.isEmpty(etCompanyName.getText().toString())
                && !StringUtils.isEmpty(etCompanyPhone.getText().toString())
                && !StringUtils.isEmpty(etAddr.getText().toString())
                && !StringUtils.isEmpty(etCautionerName.getText().toString())
                && !StringUtils.isEmpty(etCautionerPhone.getText().toString())
                && !StringUtils.isEmpty(etCertificateNum.getText().toString())

                && !StringUtils.isEmpty(tvCompanyHome.getText().toString())
                && !StringUtils.isEmpty(tvHome.getText().toString())
                && !StringUtils.isEmpty(tvChooseCertificate.getText().toString())
                && !StringUtils.isEmpty(tvSex.getText().toString())
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

    @Override
    protected void onResume() {


        String imagecount = PreferenceUtils.getString(UpdateCautionerMessageActivity.this, "imagecount");
        if (imagecount != null) {
            tvLook.setText(imagecount);
        }
        super.onResume();
        image = PreferenceUtils.getString(this, "imagearray");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }
}
