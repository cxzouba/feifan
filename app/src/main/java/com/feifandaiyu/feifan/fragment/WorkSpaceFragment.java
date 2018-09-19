package com.feifandaiyu.feifan.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.settings.SettingActivity;
import com.feifandaiyu.feifan.activity.workspace.ContractListActivity;
import com.feifandaiyu.feifan.activity.workspace.FinishWorkActivity;
import com.feifandaiyu.feifan.activity.workspace.GpsListActivity;
import com.feifandaiyu.feifan.activity.workspace.WorkPriceActivity;
import com.feifandaiyu.feifan.utils.PreferenceUtils;
import com.feifandaiyu.feifan.utils.Resource2Bitmap;
import com.flyco.animation.BounceEnter.BounceBottomEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.widget.popup.base.BasePopup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by houdaichang on 2017/5/4.
 */

public class WorkSpaceFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.finish_work)
    ImageView finishWork;
    @InjectView(R.id.work_price)
    ImageView workPrice;
    @InjectView(R.id.shenqing)
    ImageView shenqing;

    private LinearLayout llBgset;
    private ImageView ivSetting;
    private Button button;
    private TextView tvPg;
    private Bitmap bitmap;
    private ImageView ivCamera;
    private ImageView ivjisuanqi;
    private ImageView ivPoint;
    private SimpleCustomPop mQuickCustomPopup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_workspace, container, false);
        llBgset = (LinearLayout) view.findViewById(R.id.ll_bgset);
        bitmap = Resource2Bitmap.readBitMap(getActivity(), R.drawable.home_background);
        BitmapDrawable bmdrawable = new BitmapDrawable(bitmap);
        llBgset.setBackground(bmdrawable);

        init();

        ButterKnife.inject(this, view);
        return view;
    }


    private void init() {
        ivSetting = (ImageView) getActivity().findViewById(R.id.iv_setting);
        ivCamera = (ImageView) getActivity().findViewById(R.id.iv_camera);
        button = (Button) getActivity().findViewById(R.id.button2);
        ivjisuanqi = (ImageView) getActivity().findViewById(R.id.iv_jisuanqi);
        ivPoint = (ImageView) getActivity().findViewById(R.id.iv_point);
        tvPg = (TextView) getActivity().findViewById(R.id.tv_next);

        TextView tvCity = (TextView) getActivity().findViewById(R.id.tv_city);

        String city = PreferenceUtils.getString(getActivity(), "city");

        tvCity.setText(city);

        mQuickCustomPopup = new SimpleCustomPop(getActivity());

        tvPg.setVisibility(View.GONE);

        ivSetting.setVisibility(View.VISIBLE);
        ivCamera.setVisibility(View.GONE);
        ivjisuanqi.setVisibility(View.GONE);
        tvCity.setVisibility(View.VISIBLE);

        ivSetting.setOnClickListener(this);
/*        llBgset = (LinearLayout) getActivity().findViewById(R.id.ll_bgset);
          llBgset.setBackgroundResource(R.drawable.home_background);*/


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_setting:
                ivPoint.setVisibility(View.GONE);

                PreferenceUtils.setInt(getActivity(), "redpoint", 0);

                startActivity(new Intent(getContext(), SettingActivity.class));
                // // 设置过渡动画
                int enterAnim6 = R.anim.next_enter;// 进入的activity对应的动画资源
                int exitAnim6 = R.anim.next_exit;// 结束的activity对应的动画资源
                getActivity().overridePendingTransition(enterAnim6, exitAnim6);
                break;
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        bitmap = Resource2Bitmap.readBitMap(getActivity(), R.drawable.home_background);
//        BitmapDrawable bmdrawable = new BitmapDrawable(bitmap);
//        llBgset.setBackground(bmdrawable);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        bitmap.recycle();
//    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.finish_work, R.id.work_price, R.id.shenqing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finish_work:
                startActivity(new Intent(getActivity(), FinishWorkActivity.class));
                getActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.work_price:
                startActivity(new Intent(getActivity(), WorkPriceActivity.class));
                getActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.shenqing:
                mQuickCustomPopup
                        .anchorView(shenqing)
                        .offset(-40, 0)
                        .gravity(Gravity.TOP)
                        .showAnim(new BounceBottomEnter())
                        .dismissAnim(new SlideBottomExit())
                        .dimEnabled(true)
                        .show();
                break;

            default:
        }
    }

    class SimpleCustomPop extends BasePopup<SimpleCustomPop> {
        @InjectView(R.id.tv_item_1)
        LinearLayout tvItem1;
        @InjectView(R.id.tv_item_2)
        LinearLayout tvItem2;

        public SimpleCustomPop(Context context) {
            super(context);
        }

        @Override
        public View onCreatePopupView() {
            View inflate = View.inflate(mContext, R.layout.popup_shenqing, null);
            ButterKnife.inject(this, inflate);
            return inflate;
        }

        @Override
        public void setUiBeforShow() {
            tvItem1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ContractListActivity.class));
                }
            });


            tvItem2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), GpsListActivity.class));
                }
            });
        }
    }
}