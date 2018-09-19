package com.feifandaiyu.feifan.activity.personalloan;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.chaychan.viewlib.PowerfulEditText;
import com.example.library.BandCardEditText;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.BankListAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.BankCardBean;
import com.feifandaiyu.feifan.bean.BankListBean;
import com.feifandaiyu.feifan.bean.MsgBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.ocr.CameraActivity;
import com.feifandaiyu.feifan.ocr.RecognizeService;
import com.feifandaiyu.feifan.utils.FileUtil;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.StringCreateUtils;
import com.feifandaiyu.feifan.utils.StringUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by houdaichang on 2017/5/10.
 */

public class BankCardMessageActivity extends BaseActivity implements TextWatcher {

    private static final int REQUEST_CODE_BANKCARD = 110;
    @InjectView(R.id.et_cardholder_name)
    TextView etCardholderName;
    @InjectView(R.id.et_bank_name)
    PowerfulEditText etBankName;
    @InjectView(R.id.tv_bank)
    TextView tvBank;
    @InjectView(R.id.et_cardholder_num)
    BandCardEditText etCardholderNum;
    @InjectView(R.id.et_tel)
    PowerfulEditText etTel;
    @InjectView(R.id.et_lianhang)
    PowerfulEditText etLianhang;
    @InjectView(R.id.cemera1)
    ImageView cemera1;
    private TextView tv_next;
    private List<BankListBean.ListBean> list_bank;
    private BankListAdapter adapter;
    private KProgressHUD hud;
    private AlertDialog.Builder alertDialog;
    private boolean hasGotToken = false;
    private boolean isOk = false;
    private String userId;

    @Override
    protected int getContentView() {
        return R.layout.activity_bank_card_message;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("银行卡信息");
        System.out.print("---------------->" + "");

        tv_next = (TextView) findViewById(R.id.tv_next);
        hud = KProgressHUD.create(BankCardMessageActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍后")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        alertDialog = new AlertDialog.Builder(this);

        etBankName.addTextChangedListener(this);
        etCardholderName.addTextChangedListener(this);
        etCardholderNum.addTextChangedListener(this);
        etTel.addTextChangedListener(this);

        String userName = PreferenceUtils.getString(this, "userName");
        userId = PreferenceUtils.getString(this, "userId");

        etCardholderName.setText(userName);
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

    @OnClick({R.id.iv_back, R.id.tv_next, R.id.tv_bank, R.id.cemera1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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

            case R.id.tv_bank:

                showBank();

                break;
            case R.id.cemera1:

                if (!checkTokenStatus()) {
                    return;
                }

                Intent intent = new Intent(BankCardMessageActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent, REQUEST_CODE_BANKCARD);

                break;

            case R.id.tv_next:

                hud.show();

                if (isOk) {
                    postData();
                } else {

                    confirm();
                }

                break;
        }
    }

    private void confirm() {

        String userName = etCardholderName.getText().toString().trim();

        String replace = etCardholderNum.getText().toString().trim();
        String cardNum = replace.replace(" ", "");

        OkHttpUtils.post()
                .url(Constants.URLS.BASEURL + "Appraiser/VerifyBankcard")
                .addParams("userName", userName)
                .addParams("cardNum", cardNum)
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("confirm----------------->>>>>>." + e);
                        MyToast.show(BankCardMessageActivity.this, "服务器正忙，请稍后再试。。。");
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
                            etCardholderNum.setEnabled(false);
                            etCardholderNum.setTextColor(Color.GRAY);
                            etCardholderName.setEnabled(false);
                            etCardholderName.setTextColor(Color.GRAY);
                            new AlertDialog.Builder(BankCardMessageActivity.this)
                                    .setMessage(MsgBean.getMsg() + "，是否保存银行卡信息？")
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
                            new AlertDialog.Builder(BankCardMessageActivity.this)
                                    .setMessage(MsgBean.getMsg())
                                    .setPositiveButton("知道了", null)
                                    .show();
                        }

                    }
                });


    }

    private void postData() {
        String bankName = etBankName.getText().toString().trim();
        String replace = etCardholderNum.getText().toString().trim();
        String cardNum = replace.replace(" ", "");
        String cardHolder = etCardholderName.getText().toString().trim();

        OkHttpUtils
                .post()
                //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                .url(Constants.URLS.BASEURL + "Login/addCard")
                .addParams("userId", userId)
                .addParams("bankName", tvBank.getText().toString().trim())
                .addParams("cardNum", cardNum)
                .addParams("cardHolder", cardHolder)
                .addParams("flag", "0")
                .addParams("openingBank", bankName)
                .addParams("mobile", etTel.getText().toString().trim())
                .addParams("unionPay", etLianhang.getText().toString().trim())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("bankCard____________________>>>>>" + e);
                        MyToast.show(BankCardMessageActivity.this, "服务器链接失败");
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        LogUtils.e("bankCard----------------->>>>>>." + response);

                        hud.dismiss();

                        String json = response;
                        Gson gson = new Gson();
                        BankCardBean bean = gson.fromJson(json, BankCardBean.class);

                        if (bean != null) {
                            if (bean.getCode() == 1) {
                                startActivity(new Intent(BankCardMessageActivity.this, OutdoorPicActivity.class));
                                finish();

                                // 设置过渡动画
                                int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                overridePendingTransition(enterAnim6, exitAnim6);
                            } else {
                                MyToast.show(BankCardMessageActivity.this, bean.getMsg());
                            }
                        }
                    }
                });
    }

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    private void showBank() {

        LayoutInflater inflater = LayoutInflater.from(this);//将xml布局转换为view
        View view = inflater.inflate(R.layout.dialog_bank, null);//将xml布局转换为view,里面有listview
        final ListView listView = (ListView) view.findViewById(R.id.pop_bank);

        final Dialog builder = new Dialog(BankCardMessageActivity.this);
        builder.setContentView(view);
        builder.setTitle("请选择银行");
        builder.show();

        OkHttpUtils
                .post()
                .url(Constants.URLS.BASEURL + "Login//bankList")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("bankList------------------>>>" + e);

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("bankList------------------>>>" + response);
                        String json = response;
                        Gson gson = new Gson();
                        BankListBean bean = gson.fromJson(json, BankListBean.class);
                        list_bank = bean.getList();
//                        LogUtils.i(list1.size() + "---------------->>>>>>>>");
                        adapter = new BankListAdapter(BankCardMessageActivity.this, list_bank);
                        listView.setAdapter(adapter);//给listview设置适配器，adapter
                        adapter.notifyDataSetChanged();
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                id_bank = list.get(position).getId();
                String income = list_bank.get(position).getBankName().toString();
                tvBank.setText(income);
                builder.dismiss();
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

        if (!StringUtils.isEmpty(etCardholderName.getText().toString())
                && !StringUtils.isEmpty(etCardholderNum.getText().toString())
                && !StringUtils.isEmpty(etBankName.getText().toString())
                && !StringUtils.isEmpty(etTel.getText().toString())
                )

        {
            tv_next.setEnabled(true);
        } else {
            tv_next.setEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hud.show();
        initAccessTokenWithAkSk();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BANKCARD && resultCode == Activity.RESULT_OK) {
            RecognizeService.recBankCard(this,FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
//                            infoPopText(result);
                            LogUtils.e(result);
                            String regEx = "[^0-9]";
                            Pattern p = Pattern.compile(regEx);
                            Matcher m = p.matcher(result);
                            System.out.println(m.replaceAll("").trim());
                            etCardholderNum.setText(m.replaceAll("").trim());
                        }

                        @Override
                        public void onResult2(GeneralResult result) {

                        }
                    });
        }
    }

    private void infoPopText(final String result) {
        alertText("", result);
    }
}

