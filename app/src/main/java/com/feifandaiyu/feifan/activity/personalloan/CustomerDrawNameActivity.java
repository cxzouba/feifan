package com.feifandaiyu.feifan.activity.personalloan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.PersonalUploadImageBean;
import com.feifandaiyu.feifan.config.Constants;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.QiNiuUtlis;
import com.feifandaiyu.feifan.utils.SaveViewUtil;
import com.feifandaiyu.feifan.view.HuaBanView;
import com.feifandaiyu.feifan.view.MyContractWindow;
import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;

/**
 * Created by davidzhao on 2017/5/18.
 */

public class CustomerDrawNameActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    /**
     * 画画的内容区
     */
    private HuaBanView hbView;
    /**
     * 设置粗细的Dialog
     */
    private AlertDialog dialog;
    private View dialogView;
    private TextView shouWidth;
    private SeekBar widthSb;
    private int paintWidth;
    private String reportId;
    private String userId;
    private WindowManager.LayoutParams params;

    @Override
    protected int getContentView() {
        return R.layout.activity_drawname;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("签名");
        showNext(false);

//        reportId = PreferenceUtils.getString(this, "ReportId");

        userId = PreferenceUtils.getString(CustomerDrawNameActivity.this, "userId");

//        System.out.println("ReportId=" + reportId);

//        LogUtils.d("ahhahahhahahhahahahhahahahhah" + reportId);

        initView();

    }

    private void initPopWindow() {
        MyContractWindow takePhotoPopWin = new MyContractWindow(this);
//        设置Popupwindow显示位置（从底部弹出）
        takePhotoPopWin.showAtLocation(findViewById(R.id.ll_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        takePhotoPopWin.setOutsideTouchable(false);
        //当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
        getWindow().setAttributes(params);

        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        takePhotoPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });

    }


    private void initView() {
        dialogView = getLayoutInflater().inflate(R.layout.dialog_width_set, null);
        shouWidth = (TextView) dialogView.findViewById(R.id.textView1);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        widthSb = (SeekBar) dialogView.findViewById(R.id.seekBar1);
        widthSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                shouWidth.setText("当前选中宽度：" + (progress + 1));
                paintWidth = progress + 1;
            }
        });
        hbView = (HuaBanView) findViewById(R.id.huaBanView1);
        dialog = new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_info).setTitle("设置画笔宽度").
                setView(dialogView).setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                hbView.setPaintWidth(paintWidth);
            }
        }).setNegativeButton("取消", null).create();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        SubMenu colorSm = menu.addSubMenu(1, 1, 1, "选择画笔颜色");
//        colorSm.add(2, 200, 200, "红色");
//        colorSm.add(2, 210, 210, "绿色");
//        colorSm.add(2, 220, 220, "蓝色");
//        colorSm.add(2, 230, 230, "紫色");
//        colorSm.add(2, 240, 240, "黄色");
//        colorSm.add(2, 250, 250, "黑色");
//        menu.add(1, 2, 2, "设置画笔粗细");
//        SubMenu widthSm = menu.addSubMenu(1, 3, 3, "设置画笔样式");
//        widthSm.add(3, 300, 300, "线状画笔");
//        widthSm.add(3, 301, 301, "填充画笔");
        menu.add(1, 4, 4, "清空画布");
//        menu.add(1, 5, 5, "保存画布");
        menu.add(1, 6, 6, "提交评估");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int index = item.getItemId();
        switch (index) {

            case 4:

                hbView.clearScreen();

                break;

            case 6:

                if (SaveViewUtil.saveScreen(hbView)) {

                    Toast.makeText(this, "截图成功", Toast.LENGTH_SHORT).show();

                    String path = Environment.getExternalStorageDirectory() + File.separator + "huaban/" + SaveViewUtil.getPgName();

                    LogUtils.d("hahha", SaveViewUtil.getPgName());

                    QiNiuUtlis.upLoad(path, SaveViewUtil.getPgName(), new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {

                        }
                    });

                    OkHttpUtils.post()
                            .url(Constants.URLS.BASEURL + "UserPic/autograph")
                            .addParams("userId", userId)
                            .addParams("autograph", SaveViewUtil.getPgName().toString())
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    LogUtils.d("CustomerDrawNameActivity-----------" + e);
                                    MyToast.show(CustomerDrawNameActivity.this, "服务器忙，请稍后再试");
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    LogUtils.d("CustomerDrawNameActivity-----------" + response);
                                    System.out.println(SaveViewUtil.getPgName().toString());
                                    String json = response;
                                    Gson gson = new Gson();
                                    PersonalUploadImageBean bean = gson.fromJson(json, PersonalUploadImageBean.class);
                                    if (bean.getCode() == 1) {

                                        startActivity(new Intent(CustomerDrawNameActivity.this, UpLoadCusomerVideoActivity.class));
                                        finish();
                                        // 设置过渡动画
                                        int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                                        int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                                        overridePendingTransition(enterAnim6, exitAnim6);

                                    } else if (bean.getCode() == 0) {

                                        MyToast.show(CustomerDrawNameActivity.this, bean.getMsg());

                                    }
                                }
                            });

                } else {
                    Toast.makeText(this, "截图失败，请检查sdcard是否可用", Toast.LENGTH_SHORT).show();
                }


                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim0, exitAnim0);
                break;
        }
    }

    private int i=0;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && i==0) {
            i++;
            initPopWindow();
        }
    }
}
