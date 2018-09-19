package com.feifandaiyu.feifan.activity.personalloan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
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
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chaychan.viewlib.PowerfulEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.IncomeAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.IncomeBean;
import com.feifandaiyu.feifan.bean.MsgBean;
import com.feifandaiyu.feifan.bean.WifeActivityBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.ocr.CameraActivity;
import com.feifandaiyu.feifan.popupWindow.BottomPopupOption;
import com.feifandaiyu.feifan.utils.AssetsUtils;
import com.feifandaiyu.feifan.utils.BirthdayUtils;
import com.feifandaiyu.feifan.utils.FileUtil;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.AddressPicker;
import okhttp3.Call;

public class WifeActivity extends BaseActivity implements TextWatcher {


    private static final int REQUEST_CODE_CAMERA = 102;
    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    @InjectView(R.id.rb_local)
    RadioButton rbLocal;
    @InjectView(R.id.rb_not_local)
    RadioButton rbNotLocal;
    @InjectView(R.id.et_wife_name)
    PowerfulEditText etWifeName;
    @InjectView(R.id.tv_wife_certificate_type)
    TextView tvWifeCertificateType;
    @InjectView(R.id.ll_certificate_type)
    LinearLayout llCertificateType;
    @InjectView(R.id.et_wife_certificate_num)
    PowerfulEditText etWifeCertificateNum;
    @InjectView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @InjectView(R.id.et_wife_phone_num)
    PowerfulEditText etWifePhoneNum;
    @InjectView(R.id.tv_wife_birthday)
    TextView tvWifeBirthday;
    @InjectView(R.id.ll_birthday)
    LinearLayout llBirthday;
    @InjectView(R.id.et_wife_month_income)
    TextView etWifeMonthIncome;
    @InjectView(R.id.et_wife_month_pay)
    TextView etWifeMonthPay;
    @InjectView(R.id.et_wife_company_name)
    PowerfulEditText etWifeCompanyName;
    @InjectView(R.id.tv_wife_company_home)
    TextView tvWifeCompanyHome;
    @InjectView(R.id.et_wife_company_addr)
    PowerfulEditText etWifeCompanyAddr;
    @InjectView(R.id.textView13)
    TextView textView13;
    @InjectView(R.id.et_wife_company_phone)
    PowerfulEditText etWifeCompanyPhone;
    @InjectView(R.id.tv_wife_work_time)
    TextView tvWifeWorkTime;
    @InjectView(R.id.et_wife_company_type)
    TextView etCompanyType;
    @InjectView(R.id.et_wife_remarks)
    EditText etWifeRemarks;
    @InjectView(R.id.et_area_code)
    EditText etAreaCode;
    @InjectView(R.id.cemera1)
    ImageView cemera1;
    private BottomPopupOption bottomPopupOption;
    private TimePickerView pvCustomTime;
    private TextView tv_next;
    private String province;
    private String city;
    private String district;
    private ArrayList<String> list = new ArrayList<>();
    private int year;
    private int month;
    private int date;
    private IncomeAdapter adapter;
    private List<IncomeBean.ListBean> list_income;
    private InputMethodManager imm;
    private KProgressHUD hud;
    private String birhtday;
    private boolean hasGotToken = false;
    private AlertDialog.Builder alertDialog;
    private boolean isOk = false;



    @Override
    protected int getContentView() {
        return R.layout.activity_wife;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("配偶信息");

        tv_next = (TextView) findViewById(R.id.tv_next);

        alertDialog = new AlertDialog.Builder(this);

        hud = KProgressHUD.create(WifeActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在提交")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
        .show();


//        rbLocal.setChecked(true);
        initAccessTokenWithAkSk();

        etWifeCertificateNum.addTextChangedListener(this);
        etWifeCompanyAddr.addTextChangedListener(this);
        etWifeCompanyName.addTextChangedListener(this);
        etWifeCompanyPhone.addTextChangedListener(this);
        etCompanyType.addTextChangedListener(this);
        etWifeMonthIncome.addTextChangedListener(this);
        etWifeName.addTextChangedListener(this);
        etWifePhoneNum.addTextChangedListener(this);
        etWifeRemarks.addTextChangedListener(this);
        etWifeMonthPay.addTextChangedListener(this);

        tvWifeCertificateType.addTextChangedListener(this);
        tvWifeCompanyHome.addTextChangedListener(this);
        tvWifeWorkTime.addTextChangedListener(this);

        Time t = new Time("GMT+8");
        t.setToNow(); // 取得系统时间。
        year = t.year;
        month = t.month;
        date = t.monthDay;
    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {

            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hud.dismiss();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                hud.dismiss();
                alertText("AK，SK方式获取token失败", error.getMessage());
            }
        }, getApplicationContext(), "T8tx2AW8ZGGKUWNZitHsXRuc", "C8SR9G6ucTDLge1q0c6Be4HgGlYoQR6y");
    }

    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE_FRONT && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String filePath = getRealPathFromURI(uri);
            recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
        }

//        if (requestCode == REQUEST_CODE_PICK_IMAGE_BACK && resultCode == Activity.RESULT_OK) {
//            Uri uri = data.getData();
//            String filePath = getRealPathFromURI(uri);
//            recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
//        }

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
//                    alertText("", result.toString());
//                    infoTextView.setText(result.toString());
//                    String json = response;
//                    Gson gson = new Gson();
//                    CreditActivityBean bean = gson.fromJson(json, CreditActivityBean.class);
                    hud.dismiss();
                    etWifeName.setText(result.getName().toString());
                    etWifeCertificateNum.setText(result.getIdNumber().toString());

                }
            }

            @Override
            public void onError(OCRError error) {
                alertText("", error.getMessage());
            }
        });
    }


    @OnClick({R.id.tv_wife_birthday, R.id.iv_back, R.id.tv_wife_company_home, R.id.tv_wife_work_time, R.id.tv_next, R.id.et_wife_company_type
            , R.id.et_wife_month_income,R.id.cemera1,R.id.et_wife_month_pay})
    public void onViewClicked(View view) {
        bottomPopupOption = new BottomPopupOption(WifeActivity.this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {

            case R.id.tv_wife_birthday:

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                showSelectDateDialog();

                break;

            case R.id.tv_wife_work_time:

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                showSelectDateDialog1();

                break;

            case R.id.cemera1:

                Intent intent = new Intent(WifeActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);

                break;

            case R.id.et_wife_month_income:
                showIncome();
                break;

            case R.id.et_wife_month_pay:
                showPay();
                break;

            case R.id.tv_wife_company_home:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                showSelectHomeDialog();
                break;

            case R.id.et_wife_company_type:
                showCompanyType();
                break;

            case R.id.tv_next:

                if (rbLocal.isChecked() || rbNotLocal.isChecked()) {

                    hud.show();

                    if (isOk) {
                        postData();
                    } else {
                        confirm();
                    }
                } else {
                    MyToast.show(this, "请选择户口类型");
                }

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

                default:
        }
    }

    private void showPay() {
        LayoutInflater inflater = LayoutInflater.from(this);//将xml布局转换为view
        View view = inflater.inflate(R.layout.dialog_bank, null);//将xml布局转换为view,里面有listview
        final ListView listView = (ListView) view.findViewById(R.id.pop_bank);

        final Dialog builder = new Dialog(WifeActivity.this);
        builder.setContentView(view);
        builder.setTitle("请选择月支出");
        builder.show();

        OkHttpUtils
                .post()
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
                        adapter = new IncomeAdapter(WifeActivity.this, list_income);
                        listView.setAdapter(adapter);//给listview设置适配器，adapter
                        adapter.notifyDataSetChanged();
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                id_bank = list.get(position).getId();
                String income = list_income.get(position).getWagesName().toString();
                etWifeMonthPay.setText(income);
                builder.dismiss();
            }
        });

    }

    private void confirm() {

        String userName = etWifeName.getText().toString().trim();
        String cardNum = etWifeCertificateNum.getText().toString().trim();

        OkHttpUtils.post()
                .url(Constants.URLS.BASEURL + "Appraiser/VerifyIdcardv")
                .addParams("userName", userName)
                .addParams("cardNum", cardNum)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("confirm----------------->>>>>>." + e);
                        MyToast.show(WifeActivity.this, "服务器正忙，请稍后再试。。。");
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("confirm----------------->>>>>>." + response);
                        hud.dismiss();
                        String json = response;
                        Gson gson = new Gson();
                        MsgBean MsgBean = gson.fromJson(json, MsgBean.class);
                        if (MsgBean.getCode() == 1) {
                            isOk = true;
                            etWifeCertificateNum.setEnabled(false);
                            etWifeCertificateNum.setTextColor(Color.GRAY);
                            etWifeName.setEnabled(false);
                            etWifeName.setTextColor(Color.GRAY);

                            new AlertDialog.Builder(WifeActivity.this)
                                    .setMessage(MsgBean.getMsg() + "，是否保存用户信息？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            hud.show();
                                            postData();
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        } else {
                            isOk = false;
                            new AlertDialog.Builder(WifeActivity.this)
                                    .setMessage(MsgBean.getMsg())
                                    .setPositiveButton("知道了", null)
                                    .show();
                        }

                    }
                });


    }


    private void postData() {
        String userId = PreferenceUtils.getString(this, "userId");
        String userName = etWifeName.getText().toString().trim();
        String telphone = etWifePhoneNum.getText().toString().trim();
        String cardnum = etWifeCertificateNum.getText().toString().trim();
        String birthday = tvWifeBirthday.getText().toString().trim();
        String corporateName = etWifeCompanyName.getText().toString().trim();
        String corporateMobile = etWifeCompanyPhone.getText().toString().trim();
        String workAddress = etWifeCompanyAddr.getText().toString().trim();
        String monthlyIncome = etWifeMonthIncome.getText().toString().trim();
        String defray = etWifeMonthPay.getText().toString().trim();
        String inductionTime = tvWifeWorkTime.getText().toString().trim();
        String unitProperty = etCompanyType.getText().toString().trim();
        String remarks = etWifeRemarks.getText().toString().trim();

        String cardTypeMessage = tvWifeCertificateType.getText().toString();

        if (!NumberUtils.isMobile(telphone)) {
            MyToast.show(this, "手机号码不是11位，请仔细检查");
            return;
        }

        OkHttpUtils
                .post()
                //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                .url(Constants.URLS.BASEURL + "Login/addSpouse")
                .addParams("userId", userId)
                .addParams("userName", userName)
                .addParams("telphone", telphone)
                .addParams("cardtype", "1")
                .addParams("cardnum", cardnum)
                .addParams("birthday", birthday)
                .addParams("corporateName", corporateName)
                .addParams("corporateMobile", corporateMobile)
                .addParams("workProvince", province)
                .addParams("workCity", city)
                .addParams("workArea", district)
                .addParams("workAddress", workAddress)
                .addParams("monthlyIncome", monthlyIncome)
                .addParams("defray", defray)
                .addParams("inductionTime", inductionTime)
                .addParams("isfund", rbLocal.isChecked() ? "0" : "1")
                .addParams("unitProperty", unitProperty)
                .addParams("flag", "0")
                .addParams("remarks", remarks)
                .addParams("areaCode", etAreaCode.getText().toString())

//                        .addParams("image[]", "pics")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("wife____________________>>>>>" + e);
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("wife----------------->>>>>>" + response);

                        hud.dismiss();

                        String json = response;
                        Gson gson = new Gson();
                        WifeActivityBean wifeActivityBean = gson.fromJson(json, WifeActivityBean.class);

                        if (wifeActivityBean != null) {
                            if (wifeActivityBean.getCode() == 1) {
                                startActivity(new Intent(WifeActivity.this, BankCardMessageActivity.class));
                                finish();
                                // // 设置过渡动画
                                int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                overridePendingTransition(enterAnim6, exitAnim6);
                            } else if (wifeActivityBean.getCode() == 0) {
                                MyToast.show(WifeActivity.this, wifeActivityBean.getMsg());
                            }
                        }
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


        final Dialog builder = new Dialog(WifeActivity.this);
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

    private void showSelectDateDialog() {

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1950, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year, month, date);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvWifeBirthday.setText(getTime(date));
            }
        })

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

    private void showSelectDateDialog1() {

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1950, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year, month, date);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvWifeWorkTime.setText(getTime(date));
            }
        })

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

    private void showIncome() {

        LayoutInflater inflater = LayoutInflater.from(this);//将xml布局转换为view
        View view = inflater.inflate(R.layout.dialog_bank, null);//将xml布局转换为view,里面有listview
        final ListView listView = (ListView) view.findViewById(R.id.pop_bank);

        final Dialog builder = new Dialog(WifeActivity.this);
        builder.setContentView(view);
        builder.setTitle("请选择月收入");
        builder.show();

        OkHttpUtils
                .post()
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
                        adapter = new IncomeAdapter(WifeActivity.this, list_income);
                        listView.setAdapter(adapter);//给listview设置适配器，adapter
                        adapter.notifyDataSetChanged();
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                id_bank = list.get(position).getId();
                String income = list_income.get(position).getWagesName().toString();
                etWifeMonthIncome.setText(income);
                builder.dismiss();
            }
        });

    }


    private void showSelectHomeDialog() {
        //选择城市颜色
//        int color1 = getResources().getColor(R.color.xuanzechengshi);
        //带选择城市颜色
//        int color2 = getResources().getColor(R.color.beixuanchengshi);
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtils.readText(WifeActivity.this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(WifeActivity.this, data);
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
                tvWifeCompanyHome.setText(province + city + county);
                //省
                WifeActivity.this.province = province;
//                //市
                WifeActivity.this.city = city;
//                //区
                WifeActivity.this.district = county;
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
        String idnumber = etWifeCertificateNum.getText().toString();
        if (idnumber.length() == 18) {
            birhtday = BirthdayUtils.getBirhtday(idnumber);
            tvWifeBirthday.setText(birhtday);


        }
        if (!StringUtils.isEmpty(etWifeMonthPay.getText().toString())
                && !StringUtils.isEmpty(etWifeRemarks.getText().toString())
                && !StringUtils.isEmpty(etWifePhoneNum.getText().toString())
                && !StringUtils.isEmpty(etWifeName.getText().toString())
                && !StringUtils.isEmpty(etWifeCertificateNum.getText().toString())
                && !StringUtils.isEmpty(etWifeCompanyAddr.getText().toString())
                && !StringUtils.isEmpty(etWifeCompanyName.getText().toString())
                && !StringUtils.isEmpty(etWifeCompanyPhone.getText().toString())
                && !StringUtils.isEmpty(etCompanyType.getText().toString())
                && !StringUtils.isEmpty(etWifeMonthIncome.getText().toString())
                && !StringUtils.isEmpty(tvWifeWorkTime.getText().toString())
                && !StringUtils.isEmpty(tvWifeCompanyHome.getText().toString())
                && !StringUtils.isEmpty(tvWifeCertificateType.getText().toString())
                && !StringUtils.isEmpty(tvWifeBirthday.getText().toString())

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
        ButterKnife.inject(this);
    }
}
