package com.feifandaiyu.feifan.activity.companyloan;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.ImageView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.update.UpdateCompanyImageActivity;
import com.feifandaiyu.feifan.update.UpdateCompanyMessageActivity;
import com.feifandaiyu.feifan.update.UpdateCompanyVideoActivity;
import com.feifandaiyu.feifan.update.UpdateContactsActivity;
import com.feifandaiyu.feifan.utils.PreferenceUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FullInfoActivity2 extends BaseActivity {


    @InjectView(R.id.iv_full1)
    ImageView ivFull1;
    @InjectView(R.id.iv_full8)
    ImageView ivFull8;
    @InjectView(R.id.iv_full12)
    ImageView ivFull12;
    @InjectView(R.id.iv_full13)
    ImageView ivFull13;
    @InjectView(R.id.iv_full9)
    ImageView ivFull9;

    @Override
    protected int getContentView() {
        return R.layout.activity_full_info2;
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


    }


    @OnClick({R.id.iv_full1, R.id.iv_full8, R.id.iv_full12, R.id.iv_full13, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_full1:
                startActivity(new Intent(FullInfoActivity2.this, UpdateCompanyMessageActivity.class));
                //设置过渡动画
                int enterAnim1 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim1 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim1, exitAnim1);
                break;
            case R.id.iv_full8:
                PreferenceUtils.setString(FullInfoActivity2.this, "status", "1");
                startActivity(new Intent(FullInfoActivity2.this, UpdateContactsActivity.class));
                //设置过渡动画
                int enterAnim8 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim8 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim8, exitAnim8);
                break;
            case R.id.iv_full12:
                startActivity(new Intent(FullInfoActivity2.this, UpdateCompanyImageActivity.class));
                //设置过渡动画
                int enterAnim12 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim12 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim12, exitAnim12);
                break;
            case R.id.iv_full13:
                startActivity(new Intent(FullInfoActivity2.this, UpdateCompanyVideoActivity.class));
                //设置过渡动画
                int enterAnim13 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim13 = R.anim.next_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim13, exitAnim13);
                break;
            case R.id.iv_back:
                finish();
                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim0, exitAnim0);
                break;

            default:
        }
    }
}
