package com.feifandaiyu.feifan.activity.personalloan;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.MyApplication;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.IncomeAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.BaseInformationBean;
import com.feifandaiyu.feifan.bean.IncomeBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.popupWindow.BottomPopupOption;
import com.feifandaiyu.feifan.utils.AssetsUtils;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.AddressPicker;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * Created by houdaichang on 2017/5/8.
 */

public class BaseInformationActivity extends BaseActivity implements TextWatcher {


    @InjectView(R.id.rb_yes)
    RadioButton rbYes;
    @InjectView(R.id.rb_no)
    RadioButton rbNo;
    @InjectView(R.id.tv_birthday)
    TextView tvBirthday;
    @InjectView(R.id.ll_birthday)
    LinearLayout llBirthday;
    @InjectView(R.id.tv_sex)
    TextView tvSex;
    @InjectView(R.id.ll_sex)
    LinearLayout llSex;
    @InjectView(R.id.tv_marriage)
    TextView tvMarriage;
    @InjectView(R.id.ll_marriage)
    LinearLayout llMarriage;
    @InjectView(R.id.tv_education)
    TextView tvEducation;
    @InjectView(R.id.ll_education)
    LinearLayout llEducation;
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.et_profession)
    PowerfulEditText etProfession;
    @InjectView(R.id.ll_profession)
    LinearLayout llProfession;
    @InjectView(R.id.et_month_income)
    TextView etMonthIncome;
    @InjectView(R.id.et_year_income)
    TextView etYearIncome;
    @InjectView(R.id.tv_house_type)
    TextView tvHouseType;
    @InjectView(R.id.et_company_name)
    PowerfulEditText etCompanyName;
    @InjectView(R.id.et_company_phone)
    PowerfulEditText etCompanyPhone;
    @InjectView(R.id.tv_company_home)
    TextView tvCompanyHome;
    @InjectView(R.id.et_company_addr)
    PowerfulEditText etCompanyAddr;
    @InjectView(R.id.imageView10)
    ImageView imageView10;
    @InjectView(R.id.tv_work_time)
    TextView tvWorkTime;
    @InjectView(R.id.et_company_type)
    TextView etCompanyType;
    @InjectView(R.id.et_remarks)
    EditText etRemarks;
    @InjectView(R.id.et_area_code)
    EditText etAreaCode;
    @InjectView(R.id.rb_two)
    RadioButton rbTwo;
    @InjectView(R.id.rb_base)
    RadioButton rbBase;
    @InjectView(R.id.et_phone_num)
    PowerfulEditText etPhoneNum;
    @InjectView(R.id.et_shenfenzheng_addr)
    PowerfulEditText etShenfenzhengAddr;
    @InjectView(R.id.tv_home)
    TextView tvHome;
    @InjectView(R.id.et_full_addr)
    EditText etFullAddr;
    private TimePickerView pvCustomTime;
    private BottomPopupOption bottomPopupOption;
    private TextView tv_next;
    private String userId;
    private String province;
    private String city;
    private String district;
    private String province1;
    private String city1;
    private String district1;
    private ArrayList<String> list = new ArrayList<String>();
    private int year;
    private int month;
    private int date;
    private List<IncomeBean.ListBean> list_income;
    private IncomeAdapter adapter;
    private InputMethodManager imm;
    private KProgressHUD hud;
    private double price;

    @Override
    protected int getContentView() {
        return R.layout.activity_base_information;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("基本信息");
        showBack(true);
        showNext(true);

//        rbYes.setChecked(true);

        hud = KProgressHUD.create(BaseInformationActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        // TODO: 2018/8/29 获取
        String loanPrice = PreferenceUtils.getString(this, "loanPrice");

        System.out.println(loanPrice);

//        price = 0.00;
        try {
            price = Double.parseDouble(loanPrice);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            price = 0;
        }

//        if (price > 200000) {
//            rbYes.setChecked(true);
//            rbNo.setEnabled(false);
//        }

        tv_next = (TextView) findViewById(R.id.tv_next);

        etCompanyAddr.addTextChangedListener(this);
        etCompanyName.addTextChangedListener(this);
        etCompanyPhone.addTextChangedListener(this);
        etCompanyType.addTextChangedListener(this);
        etMonthIncome.addTextChangedListener(this);
        etProfession.addTextChangedListener(this);
        etRemarks.addTextChangedListener(this);
        etYearIncome.addTextChangedListener(this);

        tvCompanyHome.addTextChangedListener(this);
        tvBirthday.addTextChangedListener(this);
        tvHouseType.addTextChangedListener(this);
        tvEducation.addTextChangedListener(this);
        tvMarriage.addTextChangedListener(this);
        tvSex.addTextChangedListener(this);
        tvWorkTime.addTextChangedListener(this);

        tvHome.addTextChangedListener(this);
        etShenfenzhengAddr.addTextChangedListener(this);
        etFullAddr.addTextChangedListener(this);
        etPhoneNum.addTextChangedListener(this);

        MyApplication.getInstance().addActivity(this);

        Time t = new Time("GMT+8");
        t.setToNow(); // 取得系统时间。
        year = t.year;
        month = t.month;
        date = t.monthDay;
    }


    @OnClick({R.id.tv_birthday, R.id.tv_sex, R.id.tv_marriage, R.id.tv_education, R.id.textView,
            R.id.tv_house_type, R.id.tv_company_home, R.id.tv_work_time, R.id.tv_next, R.id.iv_back,
            R.id.et_company_type, R.id.et_month_income, R.id.et_year_income, R.id.tv_home})
    public void onViewClicked(View view) {
        bottomPopupOption = new BottomPopupOption(BaseInformationActivity.this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {
            case R.id.tv_marriage:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                bottomPopupOption.setItemText("单身", "已婚", "离异", "丧偶");
                bottomPopupOption.showPopupWindow();
                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 0) {
                            tvMarriage.setText("单身");
                            bottomPopupOption.dismiss();
                        } else if (position == 1) {
                            tvMarriage.setText("已婚");
                            bottomPopupOption.dismiss();
                        } else if (position == 2) {
                            tvMarriage.setText("离异");
                            bottomPopupOption.dismiss();
                        } else if (position == 3) {
                            tvMarriage.setText("丧偶");
                            bottomPopupOption.dismiss();
                        }
                    }
                });
                break;

            case R.id.tv_education:

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                bottomPopupOption.setItemText("本科以上", "本科", "专科", "高中、中专、技校", "高中以下");
                bottomPopupOption.showPopupWindow();
                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 0) {
                            tvEducation.setText("本科以上");
                            bottomPopupOption.dismiss();
                        } else if (position == 1) {
                            tvEducation.setText("本科");
                            bottomPopupOption.dismiss();
                        } else if (position == 2) {
                            tvEducation.setText("专科");
                            bottomPopupOption.dismiss();
                        } else if (position == 3) {
                            tvEducation.setText("高中、中专、技校");
                            bottomPopupOption.dismiss();
                        } else if (position == 4) {
                            tvEducation.setText("高中以下");
                            bottomPopupOption.dismiss();
                        }
                    }
                });
                break;

            case R.id.tv_work_time:

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                showWorkTimeDialog();

                break;

            case R.id.et_month_income:

                showIncome();

                break;

            case R.id.et_year_income:

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                bottomPopupOption.setItemText("2万以下", "2万-5万", "5万-10万", "10万-20万", "20万-50万", "50万以上");
                bottomPopupOption.showPopupWindow();
                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 0) {
                            etYearIncome.setText("2万以下");
                            bottomPopupOption.dismiss();
                        } else if (position == 1) {
                            etYearIncome.setText("2万-5万");
                            bottomPopupOption.dismiss();
                        } else if (position == 2) {
                            etYearIncome.setText("5万-10万");
                            bottomPopupOption.dismiss();
                        } else if (position == 3) {
                            etYearIncome.setText("10万-20万");
                            bottomPopupOption.dismiss();
                        } else if (position == 4) {
                            etYearIncome.setText("20万-50万");
                            bottomPopupOption.dismiss();
                        } else if (position == 5) {
                            etYearIncome.setText("50万以上");
                            bottomPopupOption.dismiss();
                        }
                    }
                });

                break;

            case R.id.tv_company_home:

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                showSelectHomeDialog();

                break;
            case R.id.tv_home:

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                showSelectHomeDialog1();

                break;

            case R.id.tv_house_type:

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                bottomPopupOption.setItemText("自有无贷", "贷款买房", "借助直系亲属", "租房", "单位房产", "公房租赁", "自建房");
                bottomPopupOption.showPopupWindow();
                bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position == 0) {
                            tvHouseType.setText("自有无贷");
                            bottomPopupOption.dismiss();
                        } else if (position == 1) {
                            tvHouseType.setText("贷款买房");
                            bottomPopupOption.dismiss();
                        } else if (position == 2) {
                            tvHouseType.setText("借助直系亲属");
                            bottomPopupOption.dismiss();
                        } else if (position == 3) {
                            tvHouseType.setText("租房");
                            bottomPopupOption.dismiss();
                        } else if (position == 4) {
                            tvHouseType.setText("单位房产");
                            bottomPopupOption.dismiss();
                        } else if (position == 5) {
                            tvHouseType.setText("公房租赁");
                            bottomPopupOption.dismiss();
                        } else if (position == 6) {
                            tvHouseType.setText("自建房");
                            bottomPopupOption.dismiss();
                        }
                    }
                });
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
            case R.id.tv_birthday:

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

                showSelectDateDialog();
                break;
            case R.id.et_company_type:
                showCompanyType();
                break;
            case R.id.iv_back:
                new AlertDialog.Builder(this)

                        .setMessage(StringCreateUtils.createString())
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

                if (rbYes.isChecked() || rbNo.isChecked()) {
                    if (!tvMarriage.getText().toString().equals("已婚") && price > 200000 && rbNo.isChecked()) {
                        LogUtils.d("--------------->>>" + (!tvMarriage.getText().toString().equals("已婚")));
                        LogUtils.d("--------------->>>" + rbNo.isChecked());
                        LogUtils.d("--------------->>>" + !(!tvMarriage.getText().toString().equals("已婚") && price > 200000 && rbNo.isChecked()));
                        MyToast.show(BaseInformationActivity.this, "如果贷款额大于20万，且客户没有配偶，必须选择共借人");
                    } else {
                        postMessage();
                    }
                } else {
                    MyToast.show(this, "请选择是否需要共借人");
                }

                break;

            default:
        }
    }

    private void showIncome() {

        LayoutInflater inflater = LayoutInflater.from(this);//将xml布局转换为view
        View view = inflater.inflate(R.layout.dialog_bank, null);//将xml布局转换为view,里面有listview
        final ListView listView = (ListView) view.findViewById(R.id.pop_bank);

        final Dialog builder = new Dialog(BaseInformationActivity.this);
        builder.setContentView(view);
        builder.setTitle("请选择月收入");
        builder.show();

        post()
                .url(Constants.URLS.BASEURL + "Login//wagesList")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("income------------------>>>" + e);

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("income------------------>>>" + response);
                        String json = response;
                        Gson gson = new Gson();
                        IncomeBean bean = gson.fromJson(json, IncomeBean.class);
                        list_income = bean.getList();
//                        LogUtils.i(list1.size() + "---------------->>>>>>>>");
                        adapter = new IncomeAdapter(BaseInformationActivity.this, list_income);
                        listView.setAdapter(adapter);//给listview设置适配器，adapter
                        adapter.notifyDataSetChanged();
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                id_bank = list.get(position).getId();
                String income = list_income.get(position).getWagesName().toString();
                etMonthIncome.setText(income);
                builder.dismiss();
            }
        });

    }

    private void showCompanyType() {
        LayoutInflater inflater = LayoutInflater.from(this);//将xml布局转换为view
        View view = inflater.inflate(R.layout.dialog_type, null);//将xml布局转换为view,里面有listview
        ListView listView = (ListView) view.findViewById(R.id.lv_type);

        list = new ArrayList<String>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData());
        listView.setAdapter(adapter);


        final Dialog builder = new Dialog(BaseInformationActivity.this);
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

    // 是通过 io 读写一个文件，然后通过tcp 传？
    private void postMessage() {

//        if (province != null) {
//            if (province.equals("北京市") || province.equals("上海市") || province.equals("天津市") || province.equals("重庆市")) {
//                city = district;
//                district = "-1";
//            }
//        }

        userId = PreferenceUtils.getString(this, "userId");
        String birthday = tvBirthday.getText().toString().trim();
        String education = tvEducation.getText().toString().trim();
        String occupation = etProfession.getText().toString().trim();
        String monthlyIncome = etMonthIncome.getText().toString().trim();
        String annualncome = etYearIncome.getText().toString().trim();
        String housing = tvHouseType.getText().toString().trim();
        String corporateName = etCompanyName.getText().toString().trim();
        String corporateMobile = etCompanyPhone.getText().toString().trim();
        String workAddress = etCompanyAddr.getText().toString().trim();
        String inductionTime = tvWorkTime.getText().toString().trim();
        String unitProperty = etCompanyType.getText().toString().trim();
        String remarks = etRemarks.getText().toString().trim();
        String isMarry = tvMarriage.getText().toString().trim();
        String tel = etPhoneNum.getText().toString().trim();
        String addrs = etFullAddr.getText().toString().trim();
        String shenfenzhengAddr = etShenfenzhengAddr.getText().toString().trim();

        HashMap<String, String> educationMap = new HashMap<>();
        educationMap.put("本科以上", "1");
        educationMap.put("本科", "2");
        educationMap.put("专科", "3");
        educationMap.put("高中、中专、技校", "4");
        educationMap.put("高中以下", "5");

        HashMap<String, String> housingMap = new HashMap<>();
        housingMap.put("自有无贷", "1");
        housingMap.put("贷款买房", "2");
        housingMap.put("借助直系亲属", "3");
        housingMap.put("租房", "4");
        housingMap.put("单位房产", "5");
        housingMap.put("公房租赁", "6");
        housingMap.put("自建房", "7");

        HashMap<String, String> isMarryMap = new HashMap<>();
        isMarryMap.put("单身", "1");
        isMarryMap.put("已婚", "2");
        isMarryMap.put("离异", "3");
        isMarryMap.put("丧偶", "4");


        hud.show();

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Login/addUserInfo")
                .addParams("userId", userId)
                .addParams("birthday", birthday)
                .addParams("sex", tvSex.getText().toString().trim().equals("男") ? "0" : "1")
                .addParams("education", educationMap.get(education))
                .addParams("occupation", occupation)
                .addParams("monthlyIncome", monthlyIncome)
                .addParams("annualncome", annualncome)
                .addParams("housing", housingMap.get(housing))
                .addParams("corporateName", corporateName)
                .addParams("corporateMobile", corporateMobile)
                .addParams("workProvince", province)
                .addParams("workCity", city)
                .addParams("workArea", district)
                .addParams("workAddress", workAddress)
                .addParams("inductionTime", inductionTime)
                .addParams("unitProperty", unitProperty)
                .addParams("remarks", remarks)
                .addParams("isMarry", isMarryMap.get(isMarry))
                .addParams("areaCode", etAreaCode.getText().toString())
                .addParams("isguarantee", rbYes.isChecked() ? "1" : "2")
                .addParams("telphone", tel)
                .addParams("province", province1)
                .addParams("city", city1)
                .addParams("area", district1)
                .addParams("address", addrs)
                .addParams("loanType", rbTwo.isChecked() ? "1" : "2")
                .addParams("cardAddress", shenfenzhengAddr)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.i("baseInfo-------------->>>>>>>>" + e);
                hud.dismiss();
                MyToast.show(BaseInformationActivity.this, "网络连接失败");
            }

            @Override
            public void onResponse(String response, int id) {
                // TODO: 2017/6/20 页面的跳转。。。
                LogUtils.i("baseInfo-------------->>>>>>>>" + response);

                hud.dismiss();

                String json = response;
                Gson gson = new Gson();
                BaseInformationBean baseInformationBean = gson.fromJson(json, BaseInformationBean.class);
                if (baseInformationBean.getCode() == 0) {
                    MyToast.show(BaseInformationActivity.this, baseInformationBean.getMsg());
                    return;
                }

                if (baseInformationBean.getCode() == 1) {

                    System.out.println(rbYes.isChecked() + "_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_->>>>>>");

                    PreferenceUtils.setString(BaseInformationActivity.this, "needCautioner", rbYes.isChecked() ? "1" : "2");

                    PreferenceUtils.setString(BaseInformationActivity.this, "isMarry", tvMarriage.getText().toString().equals("已婚") ? "2" : "1");

                    startActivity(new Intent(BaseInformationActivity.this, ContactsActivity.class));

                    finish();

                    // // 设置过渡动画
                    int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                    int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                    overridePendingTransition(enterAnim6, exitAnim6);
                }


            }
        });

    }


    private void showWorkTimeDialog() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1950, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year, month, date);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvWorkTime.setText(getTime(date));
            }
        })
                .setLineSpacingMultiplier(1.5f)//设置两横线之间的间隔倍数
                .setDate(selectedDate)
                .setSubmitColor(getResources().getColor(R.color.activecolor))
                .setCancelColor(R.color.activecolor)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);

                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })


                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.DKGRAY)
                .build();

        pvCustomTime.show();
    }

    private void showSelectDateDialog() {
        /**
         * @description
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1950, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year, month, date);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvBirthday.setText(getTime(date));
            }
        })
                /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
               /*.gravity(Gravity.RIGHT)// default is center*/

                .setLineSpacingMultiplier(1.5f)//设置两横线之间的间隔倍数
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);

                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });


                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })


                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.DKGRAY)
                .build();

        pvCustomTime.show();

    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    private void showSelectHomeDialog() {
        //选择城市颜色
//        int color1 = getResources().getColor(R.color.xuanzechengshi);
        //带选择城市颜色
//        int color2 = getResources().getColor(R.color.beixuanchengshi);
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(BaseInformationActivity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(BaseInformationActivity.this, data);
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
                tvCompanyHome.setText(province + city + county);
                //省
                BaseInformationActivity.this.province = province;
//                //市
                BaseInformationActivity.this.city = city;
//                //区
                BaseInformationActivity.this.district = county;
            }
        });
        picker.show();
    }

    private void showSelectHomeDialog1() {
        //选择城市颜色
//        int color1 = getResources().getColor(R.color.xuanzechengshi);
        //带选择城市颜色
//        int color2 = getResources().getColor(R.color.beixuanchengshi);
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(BaseInformationActivity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(BaseInformationActivity.this, data);
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
                tvHome.setText(province + city + county);
                //省
                BaseInformationActivity.this.province1 = province;
//                //市
                BaseInformationActivity.this.city1 = city;
//                //区
                BaseInformationActivity.this.district1 = county;
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

//        if (!tvMarriage.getText().toString().equals("已婚") && price > 200000) {
//            rbYes.setChecked(true);
//            rbNo.setClickable(false);
//        }else {
//            rbNo.setClickable(true);
//        }

        if (!StringUtils.isEmpty(etYearIncome.getText().toString())
                && !StringUtils.isEmpty(etRemarks.getText().toString())
                && !StringUtils.isEmpty(etProfession.getText().toString())
                && !StringUtils.isEmpty(etMonthIncome.getText().toString())
                && !StringUtils.isEmpty(etCompanyType.getText().toString())
                && !StringUtils.isEmpty(etCompanyAddr.getText().toString())
                && !StringUtils.isEmpty(etCompanyName.getText().toString())
                && !StringUtils.isEmpty(etCompanyPhone.getText().toString())

                && !StringUtils.isEmpty(tvWorkTime.getText().toString())
                && !StringUtils.isEmpty(tvSex.getText().toString())
                && !StringUtils.isEmpty(tvMarriage.getText().toString())
                && !StringUtils.isEmpty(tvEducation.getText().toString())
                && !StringUtils.isEmpty(tvBirthday.getText().toString())
                && !StringUtils.isEmpty(tvCompanyHome.getText().toString())
                && !StringUtils.isEmpty(tvHouseType.getText().toString())

                && !StringUtils.isEmpty(tvHome.getText().toString())
                && !StringUtils.isEmpty(etPhoneNum.getText().toString())
                && !StringUtils.isEmpty(etFullAddr.getText().toString())
                && !StringUtils.isEmpty(etShenfenzhengAddr.getText().toString())
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
