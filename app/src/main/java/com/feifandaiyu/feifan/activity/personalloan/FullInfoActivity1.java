package com.feifandaiyu.feifan.activity.personalloan;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.update.UpdateBankCardMessageActivity;
import com.feifandaiyu.feifan.update.UpdateBaseInformationActivity;
import com.feifandaiyu.feifan.update.UpdateBusinessPicActivity;
import com.feifandaiyu.feifan.update.UpdateCarEvaluateActivity2;
import com.feifandaiyu.feifan.update.UpdateCarPicActivity;
import com.feifandaiyu.feifan.update.UpdateCarTypeActivity;
import com.feifandaiyu.feifan.update.UpdateCautionerMessageActivity;
import com.feifandaiyu.feifan.update.UpdateCautionerPicActivity;
import com.feifandaiyu.feifan.update.UpdateContactsActivity;
import com.feifandaiyu.feifan.update.UpdateContractImageActivity;
import com.feifandaiyu.feifan.update.UpdateCost1Activity;
import com.feifandaiyu.feifan.update.UpdateCost2Activity;
import com.feifandaiyu.feifan.update.UpdateCost3Activity;
import com.feifandaiyu.feifan.update.UpdateCusomerVideoActivity;
import com.feifandaiyu.feifan.update.UpdateCustomeReport;
import com.feifandaiyu.feifan.update.UpdateDiyaPicActivity;
import com.feifandaiyu.feifan.update.UpdateIndoorPicActivity;
import com.feifandaiyu.feifan.update.UpdateOutdoorPicActivity;
import com.feifandaiyu.feifan.update.UpdateWifeActivity;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.feifandaiyu.feifan.utils.MyToast;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FullInfoActivity1 extends BaseActivity {

    @InjectView(R.id.iv_full1)
    ImageView ivFull1;
    @InjectView(R.id.iv_full2)
    ImageView ivFull2;
    @InjectView(R.id.iv_full3)
    ImageView ivFull3;
    @InjectView(R.id.iv_full4)
    ImageView ivFull4;
    @InjectView(R.id.iv_full5)
    ImageView ivFull5;
    @InjectView(R.id.iv_full6)
    ImageView ivFull6;
    @InjectView(R.id.iv_full7)
    ImageView ivFull7;
    @InjectView(R.id.iv_full8)
    ImageView ivFull8;
    @InjectView(R.id.iv_full9)
    ImageView ivFull9;
    @InjectView(R.id.iv_full10)
    ImageView ivFull10;
    @InjectView(R.id.iv_full11)
    ImageView ivFull11;
    @InjectView(R.id.iv_full12)
    ImageView ivFull12;
    @InjectView(R.id.iv_full13)
    ImageView ivFull13;
    @InjectView(R.id.iv_full14)
    ImageView ivFull14;
    private String isNew;
    private String flag;
    private String estate;
    private String carpicFlag;
    private String carLoan;
    private ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();
    private String[] mStringItems = {"室外照片", "室内材料照片", "合同照片", "经营地照片"};

    @Override
    protected int getContentView() {
        return R.layout.activity_full_info1;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        ButterKnife.inject(this);
        setTitle("客户信息修改");
        showNext(false);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setEnterTransition(new Explode().setDuration(300));
            getWindow().setExitTransition(new Explode().setDuration(300));
        }

        mMenuItems.add(new DialogMenuItem("室外照片", R.drawable.shiwai));
        mMenuItems.add(new DialogMenuItem("室内材料照片", R.drawable.shiwai));
        mMenuItems.add(new DialogMenuItem("合同照片", R.drawable.shinei));
        mMenuItems.add(new DialogMenuItem("经营地照片", R.drawable.shinei));

        isNew = PreferenceUtils.getString(this, "isNew");
        flag = PreferenceUtils.getString(this, "flag");
        carpicFlag = PreferenceUtils.getString(this, "carpicFlag");
        carLoan = PreferenceUtils.getString(FullInfoActivity1.this, "carLoan");

    }

    @OnClick({R.id.iv_full1, R.id.iv_full2, R.id.iv_full3, R.id.iv_full4, R.id.iv_full5, R.id.iv_full6, R.id.iv_full7, R.id.iv_full8,
            R.id.iv_full9, R.id.iv_full10, R.id.iv_full11, R.id.iv_full12, R.id.iv_full13, R.id.iv_full14, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_full1:
                startActivity(new Intent(FullInfoActivity1.this, UpdateCustomeReport.class));
                //设置过渡动画
                int enterAnim1 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim1 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim1, exitAnim1);
                break;
            case R.id.iv_full2:

                if (isNew.equals("1")) {
                    startActivity(new Intent(FullInfoActivity1.this, UpdateCarTypeActivity.class));
                    //设置过渡动画
                    int enterAnim2 = R.anim.next_enter;// 进入的activity对应的动画资源
                    int exitAnim2 = R.anim.next_exit;// 结束的activity对应的动画资源
                    overridePendingTransition(enterAnim2, exitAnim2);
                } else if (isNew.equals("2")) {
                    MyToast.show(FullInfoActivity1.this, "二手车请选择车辆评估");
                } else {
                    MyToast.show(FullInfoActivity1.this, "暂无车辆信息");
                }

                break;
            case R.id.iv_full3:

                if (isNew.equals("1")) {

                    MyToast.show(FullInfoActivity1.this, "新车请选择车型信息");

                } else if (isNew.equals("2")) {

                    startActivity(new Intent(FullInfoActivity1.this, UpdateCarEvaluateActivity2.class));
                    //设置过渡动画
                    int enterAnim3 = R.anim.next_enter;// 进入的activity对应的动画资源
                    int exitAnim3 = R.anim.next_exit;// 结束的activity对应的动画资源
                    overridePendingTransition(enterAnim3, exitAnim3);

                } else {
                    MyToast.show(FullInfoActivity1.this, "暂无车辆信息");
                }
                break;
            case R.id.iv_full4:

                if (isNew.equals("1")) {

                    MyToast.show(FullInfoActivity1.this, "新车无车辆照片");

                } else if (isNew.equals("2")) {

                    if (carpicFlag.equals("1")) {
                        startActivity(new Intent(FullInfoActivity1.this, UpdateCarPicActivity.class));
                        //设置过渡动画
                        int enterAnim4 = R.anim.next_enter;// 进入的activity对应的动画资源
                        int exitAnim4 = R.anim.next_exit;// 结束的activity对应的动画资源
                        overridePendingTransition(enterAnim4, exitAnim4);
                    } else if (carpicFlag.equals("-999")) {
                        MyToast.show(FullInfoActivity1.this, "未上传车辆照片");
                    }


                } else {
                    MyToast.show(FullInfoActivity1.this, "暂无车辆信息");
                }

                break;

            case R.id.iv_full5:

                LogUtils.e("carLoan" + carLoan + "=" + "isNew" + isNew);
                if (carLoan.equals("1") && isNew.equals("1")) {
                    startActivity(new Intent(FullInfoActivity1.this, UpdateCost1Activity.class));
                } else if (carLoan.equals("1") && isNew.equals("2")) {
                    startActivity(new Intent(FullInfoActivity1.this, UpdateCost2Activity.class));
                } else if (carLoan.equals("2") && isNew.equals("2")) {
                    startActivity(new Intent(FullInfoActivity1.this, UpdateCost3Activity.class));
                } else if (isNew.equals("0")) {
                    MyToast.show(FullInfoActivity1.this, "暂无车辆信息");
                }

                int enterAnim5 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim5 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim5, exitAnim5);
                break;
            case R.id.iv_full6:
                startActivity(new Intent(FullInfoActivity1.this, UpdateDiyaPicActivity.class));
                //设置过渡动画
                int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim6, exitAnim6);
                break;
            case R.id.iv_full7:
                startActivity(new Intent(FullInfoActivity1.this, UpdateBaseInformationActivity.class));
                //设置过渡动画
                int enterAnim7 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim7 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim7, exitAnim7);
                break;

            case R.id.iv_full8:
                PreferenceUtils.setString(FullInfoActivity1.this, "status", "0");
                startActivity(new Intent(FullInfoActivity1.this, UpdateContactsActivity.class));
                //设置过渡动画
                int enterAnim8 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim8 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim8, exitAnim8);
                break;
            case R.id.iv_full9:
//                PreferenceUtils.setString(FullInfoActivity1.this,"status","0");
                startActivity(new Intent(FullInfoActivity1.this, UpdateCautionerMessageActivity.class));
                //设置过渡动画
                int enterAnim9 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim9 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim9, exitAnim9);
                break;
            case R.id.iv_full10:
                startActivity(new Intent(FullInfoActivity1.this, UpdateWifeActivity.class));
                //设置过渡动画
                int enterAnim10 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim10 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim10, exitAnim10);
                break;
            case R.id.iv_full11:
                startActivity(new Intent(FullInfoActivity1.this, UpdateBankCardMessageActivity.class));
                //设置过渡动画
                int enterAnim11 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim11 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim11, exitAnim11);
                break;
            case R.id.iv_full12:
                NormalListDialogStringArr();
//                startActivity(new Intent(FullInfoActivity1.this, UpdatePersonalImageActivity.class));
                //设置过渡动画
//                int enterAnim12 = R.anim.next_enter;// 进入的activity对应的动画资源
//                int exitAnim12 = R.anim.next_exit;// 结束的activity对应的动画资源
//                overridePendingTransition(enterAnim12, exitAnim12);
                break;
            case R.id.iv_full13:
                startActivity(new Intent(FullInfoActivity1.this, UpdateCusomerVideoActivity.class));
                //设置过渡动画
                int enterAnim13 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim13 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim13, exitAnim13);
                break;
            case R.id.iv_full14:
                startActivity(new Intent(FullInfoActivity1.this, UpdateCautionerPicActivity.class));
                //设置过渡动画
                int enterAnim14 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim14 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim14, exitAnim14);
                break;
//            case R.id.iv_full15:
//                startActivity(new Intent(FullInfoActivity1.this, UpdateCautionerPicActivity.class));
//                //设置过渡动画
//                int enterAnim15 = R.anim.next_enter;// 进入的activity对应的动画资源
//                int exitAnim15 = R.anim.next_exit;// 结束的activity对应的动画资源
//                overridePendingTransition(enterAnim15, exitAnim15);
//                break;
            case R.id.iv_back:
                finish();
                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim0, exitAnim0);
                break;

            default:
        }
    }

    private void NormalListDialogStringArr() {
        final NormalListDialog dialog = new NormalListDialog(this, mStringItems);
        dialog.title("请选择")//
                .layoutAnimation(null)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(FullInfoActivity1.this, UpdateOutdoorPicActivity.class));
                    //设置过渡动画
                    int enterAnim14 = R.anim.next_enter;// 进入的activity对应的动画资源
                    int exitAnim14 = R.anim.next_exit;// 结束的activity对应的动画资源
                    overridePendingTransition(enterAnim14, exitAnim14);
                    LogUtils.e(position + "");
                } else if (position == 1) {
                    LogUtils.e(position + "");
                    startActivity(new Intent(FullInfoActivity1.this, UpdateIndoorPicActivity.class));
                    //设置过渡动画
                    int enterAnim14 = R.anim.next_enter;// 进入的activity对应的动画资源
                    int exitAnim14 = R.anim.next_exit;// 结束的activity对应的动画资源
                    overridePendingTransition(enterAnim14, exitAnim14);
                } else if (position == 2) {
                    LogUtils.e(position + "");
                    startActivity(new Intent(FullInfoActivity1.this, UpdateContractImageActivity.class));
                    //设置过渡动画
                    int enterAnim14 = R.anim.next_enter;// 进入的activity对应的动画资源
                    int exitAnim14 = R.anim.next_exit;// 结束的activity对应的动画资源
                    overridePendingTransition(enterAnim14, exitAnim14);
                } else if (position == 3) {
                    startActivity(new Intent(FullInfoActivity1.this, UpdateBusinessPicActivity.class));
                    //设置过渡动画
                    int enterAnim14 = R.anim.next_enter;// 进入的activity对应的动画资源
                    int exitAnim14 = R.anim.next_exit;// 结束的activity对应的动画资源
                    overridePendingTransition(enterAnim14, exitAnim14);
                    LogUtils.e(position + "");

                }

                dialog.dismiss();
            }
        });
    }


}
