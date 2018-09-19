package com.feifandaiyu.feifan.update;

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

import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * Created by houdaichang on 2017/5/10.
 */

public class UpdateBankCardMessageActivity extends BaseActivity implements TextWatcher {

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
    private String userId;
    private String bankCardId;
    private boolean hasGotToken = false;
    private boolean isOk = false;
    private AlertDialog.Builder alertDialog;
    private String cardNum;

    @Override
    protected int getContentView() {
        return R.layout.activity_bank_card_message;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("银行卡信息");

        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setText("保存");

        hud = KProgressHUD.create(UpdateBankCardMessageActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍候")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        alertDialog = new AlertDialog.Builder(this);

        etBankName.addTextChangedListener(this);
        etCardholderName.addTextChangedListener(this);
        etCardholderNum.addTextChangedListener(this);
        etTel.addTextChangedListener(this);

//        String userName = PreferenceUtils.getString(this, "userName");

//        etCardholderName.setText(userName);
        userId = PreferenceUtils.getString(this, "userId");

        hud.show();

        initData();
        initAccessTokenWithAkSk();
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

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    private void initData() {

        post()
                .url(Constants.URLS.BASEURL + "UserInfo/card")
                .addParams("userId", userId)
                .addParams("flag", "0")
                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("updateBankShow----------------->>>>>>." + e);
                        MyToast.show(UpdateBankCardMessageActivity.this, "联网失败...");
                        hud.dismiss();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i("updateBankShow----------------->>>>>>." + response);
                        String json = response;
                        Gson gson = new Gson();
                        BankCardBean bean = gson.fromJson(json, BankCardBean.class);
                        hud.dismiss();
                        if (bean.getCode() == 1) {
                            etCardholderName.setText(bean.getList().getCardHolder());
                            cardNum = bean.getList().getCardNum().trim();
                            etCardholderNum.setText(cardNum);
                            tvBank.setText(bean.getList().getBankName());
                            etBankName.setText(bean.getList().getOpeningBank());
                            etTel.setText(bean.getList().getMobile());

                            if (!bean.getList().getUnionPay().equals("-1")) {
                                etLianhang.setText(bean.getList().getUnionPay());
                            }

                            bankCardId = bean.getList().getId();

                        } else if (bean.getCode() == 0) {
                            new AlertDialog.Builder(UpdateBankCardMessageActivity.this)

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

    @OnClick({R.id.iv_back, R.id.tv_next, R.id.tv_bank,R.id.cemera1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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

            case R.id.tv_bank:

                showBank();

                break;
            case R.id.cemera1:

                if (!checkTokenStatus()) {
                    return;
                }

                Intent intent = new Intent(UpdateBankCardMessageActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent, REQUEST_CODE_BANKCARD);

                break;

            case R.id.tv_next:

                hud.show();

                if (cardNum.equals(etCardholderNum.getText().toString().replace(" ",""))){
                    postData();
                }else {
                    confirm();
                }

                break;

            default:
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
                        MyToast.show(UpdateBankCardMessageActivity.this, "服务器正忙，请稍后再试。。。");
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
                            new AlertDialog.Builder(UpdateBankCardMessageActivity.this)
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
                            new AlertDialog.Builder(UpdateBankCardMessageActivity.this)
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

        post()
                //http://byu2763230001.my3w.com/pcreateArchivesublic/info/Login/
                .url(Constants.URLS.BASEURL + "UserUpdate/editCard")
                .addParams("userId", userId)
                .addParams("bankName", tvBank.getText().toString().trim())
                .addParams("cardNum", cardNum)
                .addParams("cardHolder", cardHolder)
                .addParams("openingBank", bankName)
                .addParams("mobile", etTel.getText().toString().trim())
                .addParams("unionPay", etLianhang.getText().toString().trim())
                .addParams("id", bankCardId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("updateBankEdit____________________>>>>>" + e);
                        MyToast.show(UpdateBankCardMessageActivity.this, "服务器链接失败");
                        hud.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        LogUtils.e("updateBankEdit----------------->>>>>>." + response);

                        hud.dismiss();

                        String json = response;
                        Gson gson = new Gson();
                        BankCardBean bean = gson.fromJson(json, BankCardBean.class);

                        if (bean != null) {
                            if (bean.getCode() == 1) {
//                                        startActivity(new Intent(UpdateBankCardMessageActivity.this, PersonalUpLoadImageActivity.class));

                                MyToast.show(UpdateBankCardMessageActivity.this, "银行卡信息保存成功");

                                finish();

                                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                                overridePendingTransition(enterAnim0, exitAnim0);
                            } else {
                                MyToast.show(UpdateBankCardMessageActivity.this, bean.getMsg());
                            }
                        }
                    }
                });
    }

    private void showBank() {

        LayoutInflater inflater = LayoutInflater.from(this);//将xml布局转换为view
        View view = inflater.inflate(R.layout.dialog_bank, null);//将xml布局转换为view,里面有listview
        final ListView listView = (ListView) view.findViewById(R.id.pop_bank);

        final Dialog builder = new Dialog(UpdateBankCardMessageActivity.this);
        builder.setContentView(view);
        builder.setTitle("请选择银行");
        builder.show();

        post()
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
                        adapter = new BankListAdapter(UpdateBankCardMessageActivity.this, list_bank);
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

