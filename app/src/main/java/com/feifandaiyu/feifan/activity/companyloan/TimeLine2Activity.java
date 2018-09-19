package com.feifandaiyu.feifan.activity.companyloan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.companyloan.Chuli2Activity;
import com.feifandaiyu.feifan.activity.companyloan.CompanyLoanActivity;
import com.feifandaiyu.feifan.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by houdaichang on 2017/5/13.
 */

public class TimeLine2Activity extends BaseActivity {
    @InjectView(R.id.tv_date_pinggushi)
    TextView tvDatePinggushi;
    @InjectView(R.id.tv_time_pinggushi)
    TextView tvTimePinggushi;
    @InjectView(R.id.iv_see_pinggushi)
    ImageView ivSeePinggushi;
    @InjectView(R.id.tv_date_xiaoshou)
    TextView tvDateXiaoshou;
    @InjectView(R.id.tv_time_xiaoshou)
    TextView tvTimeXiaoshou;
    @InjectView(R.id.iv_see_xiaoshou)
    ImageView ivSeeXiaoshou;
    @InjectView(R.id.tv_date_fengkong)
    TextView tvDateFengkong;
    @InjectView(R.id.tv_time_fengkong)
    TextView tvTimeFengkong;
    @InjectView(R.id.iv_see_fengkong)
    ImageView ivSeeFengkong;
    @InjectView(R.id.tv_date_caiwu)
    TextView tvDateCaiwu;
    @InjectView(R.id.tv_time_csiwu)
    TextView tvTimeCsiwu;
    @InjectView(R.id.iv_see_caiwu)
    ImageView ivSeeCaiwu;
    @InjectView(R.id.tv_date_daihou)
    TextView tvDateDaihou;
    @InjectView(R.id.tv_time_daihou)
    TextView tvTimeDaihou;
    @InjectView(R.id.iv_see_daihou)
    ImageView ivSeeDaihou;

    @Override
    protected int getContentView() {
        return R.layout.activity_timeline;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTitle("过程意见");
        showNext(false);
    }


    @OnClick({R.id.iv_see_pinggushi, R.id.iv_see_xiaoshou, R.id.iv_see_fengkong, R.id.iv_see_caiwu,
            R.id.iv_see_daihou, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_see_pinggushi:
                startActivity(new Intent(this, Chuli2Activity.class));
                finish();
                break;
            case R.id.iv_see_xiaoshou:
                startActivity(new Intent(this, Chuli2Activity.class));
                finish();
                break;
            case R.id.iv_see_fengkong:
                startActivity(new Intent(this, Chuli2Activity.class));
                finish();
                break;
            case R.id.iv_see_caiwu:
                startActivity(new Intent(this, Chuli2Activity.class));
                finish();
                break;
            case R.id.iv_see_daihou:
                startActivity(new Intent(this, Chuli2Activity.class));
                finish();
                break;
            case R.id.iv_back:
                startActivity(new Intent(this, CompanyLoanActivity.class));
                finish();
                break;
        }
    }
}
