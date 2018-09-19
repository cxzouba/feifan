package com.feifandaiyu.feifan.reciever;

import android.content.Context;
import android.content.Intent;

import com.feifandaiyu.feifan.activity.settings.NoticeActivity;
import com.feifandaiyu.feifan.utils.LogUtils;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

/**
 * Created by houdaichang on 2017/11/7.
 */

public class MyReciever extends XGPushBaseReceiver {
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {

    }

    @Override
    public void onUnregisterResult(Context context, int i) {

    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {

    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
        LogUtils.e("onTextMessage" + xgPushTextMessage);
//        context.startActivity(new Intent(context,NoticeActivity.class));
    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {
        LogUtils.e("onNotifactionClickedResult" + xgPushClickedResult);
        context.startActivity(new Intent(context,NoticeActivity.class));

    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
        LogUtils.e("onNotifactionShowedResult" + xgPushShowedResult);
        Intent intent = new Intent();
        intent.setAction("update");
        intent.putExtra("msg","更新");
        context.sendBroadcast(intent);
    }
}
