package com.feifandaiyu.feifan.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.model.LatLng;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.FaceConfirmActivity;
import com.feifandaiyu.feifan.activity.JisuanqiActivity;
import com.feifandaiyu.feifan.activity.cheshang.CheShangListActivity;
import com.feifandaiyu.feifan.activity.companyloan.CompanyLoanActivity;
import com.feifandaiyu.feifan.activity.personalloan.PersonalLoanActivity;
import com.feifandaiyu.feifan.camera.CameraUtil;
import com.feifandaiyu.feifan.utils.Resource2Bitmap;
import com.feifandaiyu.feifan.view.BaseViewHelper;
import com.flyco.animation.BounceEnter.BounceBottomEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.widget.popup.base.BasePopup;
import com.google.zxing.activity.CaptureActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by houdaichang on 2017/5/2.
 */

public class TransactionLoanFragment extends Fragment {

    private static final int REQ_CODE = 110;
    public ImageView ivSetting;
    @InjectView(R.id.personal)
    ImageView personal;
    @InjectView(R.id.company)
    ImageView company;
    @InjectView(R.id.cehshang)
    ImageView cehshang;
    private LinearLayout rl;
    private TextView tvPg;
    private Bitmap bitmap;
    private ImageView ivCamera;
    private String path;
    private Bitmap pic;
    private ImageView image;
    private String str;
    /**
     * 当前经纬度
     */

    private LatLng mLoactionLatLng;
    private LocationClient mLocClient;
    private double mLatitude;
    private double mLongitude;
    private String addr;
    private ImageView ivjisuanqi;
    private ImageView ivPoint;
    private SimpleCustomPop mQuickCustomPopup;

    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {

            }

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

            }

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        } else {

            CameraUtil.getInstance().camera(getActivity());

        }
    }

    @OnClick({R.id.personal, R.id.company, R.id.cehshang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personal:

//                startActivity(new Intent(getActivity(), PersonalLoanActivity.class));
//                getActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                Intent intent = new Intent(getActivity(), PersonalLoanActivity.class);
                new BaseViewHelper
                        .Builder(getActivity(), view)
                        .startActivity(intent);
                break;
            case R.id.company:

//                startActivity(new Intent(getActivity(), CompanyLoanActivity.class));
//                getActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                Intent intent1 = new Intent(getActivity(), CompanyLoanActivity.class);
                new BaseViewHelper
                        .Builder(getActivity(), view)
                        .startActivity(intent1);
                break;

            case R.id.cehshang:

//                startActivity(new Intent(getActivity(), CompanyLoanActivity.class));
//                getActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                Intent intent2 = new Intent(getActivity(), CheShangListActivity.class);
                new BaseViewHelper
                        .Builder(getActivity(), view)
                        .startActivity(intent2);
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {

            String result = "";

            if (data != null) {
                result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
                new AlertDialog.Builder(getActivity())
                        .setTitle("合同")
                        .setMessage(result)
                        .setPositiveButton("确定", null)
                        .show();
            } else {
                new AlertDialog.Builder(getActivity())
                        .setTitle("合同")
                        .setMessage("扫描无结果")
                        .setPositiveButton("确定", null)
                        .show();
            }

//            MyToast.show(getContext(), result);
//            if(bitmap != null){
//                mImageCallback.setImageBitmap(bitmap);//现实扫码图片
//            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_loan, container, false);
        bitmap = Resource2Bitmap.readBitMap(getActivity(), R.drawable.trans_bg);
        BitmapDrawable bmdrawable = new BitmapDrawable(bitmap);
        rl = (LinearLayout) view.findViewById(R.id.rl_setbg);
        rl.setBackground(bmdrawable);
        init();
        ButterKnife.inject(this, view);
        return view;
    }

    private void init() {

        ivSetting = (ImageView) getActivity().findViewById(R.id.iv_setting);

        ivCamera = (ImageView) getActivity().findViewById(R.id.iv_camera);
        ivPoint = (ImageView) getActivity().findViewById(R.id.iv_point);
        ivPoint.setVisibility(View.GONE);
        ivjisuanqi = (ImageView) getActivity().findViewById(R.id.iv_jisuanqi);

        TextView tvCity = (TextView) getActivity().findViewById(R.id.tv_city);

        ivSetting.setVisibility(View.GONE);
        ivCamera.setVisibility(View.VISIBLE);
        ivjisuanqi.setVisibility(View.VISIBLE);
        tvCity.setVisibility(View.GONE);

        mQuickCustomPopup = new SimpleCustomPop(getActivity());

        tvPg = (TextView) getActivity().findViewById(R.id.tv_next);

        tvPg.setVisibility(View.GONE);

        ivjisuanqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JisuanqiActivity.class));

            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                PictureConfig.getInstance().init(options).startOpenCamera(getActivity(), new ResultCa((ImageView) v, selectMedia, 0));

                mQuickCustomPopup
                        .anchorView(ivCamera)
                        .offset(-10, 5)
                        .gravity(Gravity.BOTTOM)
                        .showAnim(new BounceBottomEnter())
                        .dismissAnim(new SlideBottomExit())
                        .dimEnabled(true)
                        .show();

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    class SimpleCustomPop extends BasePopup<SimpleCustomPop> {
        @InjectView(R.id.tv_item_1)
        TextView tvItem1;
        @InjectView(R.id.tv_item_2)
        TextView tvItem2;
        @InjectView(R.id.ll_content)
        LinearLayout llContent;
        @InjectView(R.id.tv_scran)
        TextView tvScran;


        public SimpleCustomPop(Context context) {
            super(context);
        }

        @Override
        public View onCreatePopupView() {
            View inflate = View.inflate(mContext, R.layout.popup_custom, null);
            ButterKnife.inject(this, inflate);
            return inflate;
        }

        @Override
        public void setUiBeforShow() {
            tvItem1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takePhoto();
                }
            });

            tvItem2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), FaceConfirmActivity.class));
                }
            });

            tvScran.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    startActivityForResult(intent, REQ_CODE);
                }
            });

        }
    }
}