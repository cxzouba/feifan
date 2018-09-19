package com.feifandaiyu.feifan.activity.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.adapter.FragmentAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.fragment.NoticeEvaluteFragment;
import com.feifandaiyu.feifan.fragment.NoticeSettingFragment;
import com.feifandaiyu.feifan.fragment.NoticeShenheFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author houdaichang
 */

public class NoticeActivity extends BaseActivity {
    @InjectView(R.id.tv_notice_setting)
    TextView tvNoticeSetting;
    @InjectView(R.id.ll_notice_setting)
    LinearLayout llNoticeSetting;
    @InjectView(R.id.tv_notice_evalute)
    TextView tvNoticeEvalute;
    @InjectView(R.id.ll_notice_evalute)
    LinearLayout llNoticeEvalute;
    @InjectView(R.id.tv_notice_shenhe)
    TextView tvNoticeShenhe;
    @InjectView(R.id.ll_notice_shenhe)
    LinearLayout llNoticeShenhe;
    @InjectView(R.id.id_switch_tab_ll)
    LinearLayout idSwitchTabLl;
    @InjectView(R.id.id_tab_line_iv)
    ImageView idTabLineIv;
    @InjectView(R.id.id_page_vp)
    ViewPager idPageVp;

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;

    /**
     * Fragment
     */
    private NoticeSettingFragment noticeSettingFragment;
    private NoticeEvaluteFragment noticeEvaluteFragment;
    private NoticeShenheFragment noticeShenheFragment;

    /**
     * ViewPager的当前选中页
     */
    private int currentIndex;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;

    @Override
    protected int getContentView() {
        return R.layout.activity_notice;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);

        setTitle("我的消息");
        showBack(true);
        showNext(false);
        initView();
        initTabLineWidth();
    }


    private void initView() {
        noticeEvaluteFragment = new NoticeEvaluteFragment();
        noticeShenheFragment = new NoticeShenheFragment();
        noticeSettingFragment = new NoticeSettingFragment();
        mFragmentList.add(noticeSettingFragment);
        mFragmentList.add(noticeEvaluteFragment);
        mFragmentList.add(noticeShenheFragment);

        mFragmentAdapter = new FragmentAdapter(
                this.getSupportFragmentManager(), mFragmentList);

        idPageVp.setAdapter(mFragmentAdapter);

        idPageVp.setCurrentItem(0);
        idPageVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }

            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) idTabLineIv
                        .getLayoutParams();

                Log.e("offset:", offset + "");
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景： 记3个页面, 从左到右分别为0,1,2 0->1; 1->2; 2->1;
                 * 1->0
                 */

                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3)) + screenWidth / 6;

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3)) + screenWidth / 7;

                } else if (currentIndex == 1 && position == 1) // 1->2

                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3)) + screenWidth / 8;

                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3)) + screenWidth / 11;
                }
                idTabLineIv.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
//                resetTextView();
                switch (position) {
                    case 0:
//                        tvNoticeSetting.setTextColor(Color.BLUE);
                        break;
                    case 1:
//                        tvNoticeEvalute.setTextColor(Color.BLUE);
                        break;
                    case 2:
//                        tvNoticeShenhe.setTextColor(Color.BLUE);
                        break;
                }
                currentIndex = position;
            }
        });

    }

    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) idTabLineIv
                .getLayoutParams();

        lp.width = screenWidth / 12;
        idTabLineIv.setLayoutParams(lp);
    }

    /**
     * 重置颜色
     */
//    private void resetTextView() {
//        tvNoticeSetting.setTextColor(getResources().getColor(R.color.notice_title));
//        tvNoticeEvalute.setTextColor(getResources().getColor(R.color.notice_title));
//        tvNoticeShenhe.setTextColor(getResources().getColor(R.color.notice_title));
//    }
    @OnClick({R.id.ll_notice_setting, R.id.ll_notice_evalute, R.id.ll_notice_shenhe, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_notice_setting:
                idPageVp.setCurrentItem(0);
//                tvNoticeSetting.setTextColor(Color.BLUE);
                break;
            case R.id.ll_notice_evalute:
                idPageVp.setCurrentItem(1);
//                tvNoticeEvalute.setTextColor(Color.BLUE);
                break;
            case R.id.ll_notice_shenhe:
                idPageVp.setCurrentItem(2);
//                tvNoticeShenhe.setTextColor(Color.BLUE);
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
