package com.feifandaiyu.feifan.popupWindow;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.dialog.SelfDialog;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.FunctionOptions;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;


/**
 * Created by David 17
 * 从底部滑上来的popup
 */
public class SlideFromBottomPopup extends BasePopupWindow implements View.OnClickListener {

    public static List<LocalMedia> list = new ArrayList<LocalMedia>();

    private String path;

    private View popupView;
    private ImageView imageView;
    private Activity personalUpLoad;
    private SelfDialog selfDialog;

    public SlideFromBottomPopup(Activity context, View imageView, List<LocalMedia> selectMedia) {
        super(context);
        personalUpLoad = context;
        this.imageView = (ImageView) imageView;
        bindEvent();
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateAnimation(250 * 2, 0, 300);
    }

    @Override
    public View getClickToDismissView() {
        return popupView.findViewById(R.id.click_to_dismiss);
    }

    @Override
    public View onCreatePopupView() {
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_slide_from_bottom, null);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return popupView.findViewById(R.id.popup_anima);
    }

    private void bindEvent() {
        if (popupView != null) {

            popupView.findViewById(R.id.tx_2).setOnClickListener(this);
            popupView.findViewById(R.id.tx_3).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tx_2:
                PictureConfig.getInstance().init(options).startOpenCamera(personalUpLoad, new ResultCa(imageView));
                dismiss();
                break;
            case R.id.tx_3:
                selfDialog = new SelfDialog(getContext());
                selfDialog.setTitle("提示");
                selfDialog.setMessage("当前照片暂不支持从相册中选择哦 嘿嘿嘿！");
                selfDialog.setYesOnclickListener("那就拍照吧！！！", new SelfDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        PictureConfig.getInstance().init(options).startOpenCamera(personalUpLoad, new ResultCa(imageView));
                        selfDialog.dismiss();
                    }
                });
                selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {

                        selfDialog.dismiss();
                    }
                });
                selfDialog.show();

                dismiss();


                break;
            default:
                break;
        }

    }


    class ResultCa implements PictureConfig.OnSelectResultCallback {
        private ImageView view;

        ResultCa(ImageView view) {
            this.view = view;
        }

        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            list = resultList;
            Log.i("callBack_result", list.size() + "");
            LocalMedia media = list.get(0);
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                String path = media.getCutPath();

            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                String path = media.getCompressPath();
            } else {
                // 原图地址
                path = media.getPath();
                Glide.with(personalUpLoad)
                        .load(path)
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            }
        }
    }

    FunctionOptions options = new FunctionOptions.Builder()
            .setType(FunctionConfig.TYPE_IMAGE) // 图片or视频 FunctionConfig.TYPE_IMAGE  TYPE_VIDEO
            .setCropMode(FunctionConfig.CROP_MODEL_DEFAULT) // 裁剪模式 默认、1:1、3:4、3:2、16:9
            .setCompress(false) //是否压缩
            .setEnablePixelCompress(true) //是否启用像素压缩
            .setEnableQualityCompress(true) //是否启质量压缩
            .setMaxSelectNum(9) // 可选择图片的数量
            .setSelectMode(FunctionConfig.MODE_MULTIPLE) // 单选 or 多选
            .setShowCamera(true) //是否显示拍照选项 这里自动根据type 启动拍照或录视频
            .setEnablePreview(true) // 是否打开预览选项
            .setEnableCrop(false) // 是否打开剪切选项
            .setPreviewVideo(true) // 是否预览视频(播放) mode or 多选有效

            .setRecordVideoDefinition(FunctionConfig.HIGH) // 视频清晰度
            .setRecordVideoSecond(60) // 视频秒数
            .setGif(false)// 是否显示gif图片，默认不显示
            .setCropW(200) // cropW-->裁剪宽度 值不能小于100  如果值大于图片原始宽高 将返回原图大小
            .setCropH(200) // cropH-->裁剪高度 值不能小于100 如果值大于图片原始宽高 将返回原图大小
            .setMaxB(202400) // 压缩最大值 例如:200kb  就设置202400，202400 / 1024 = 200kb


            .setEnablePixelCompress(true)

            .setGrade(Luban.THIRD_GEAR) // 压缩档次 默认三档
            .setCheckNumMode(false)
            .setCompressQuality(100) // 图片裁剪质量,默认无损
            .setImageSpanCount(4) // 每行个数

            .setSelectMedia(list) // 已选图片，传入在次进去可选中，不能传入网络图片
            .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
            .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
            .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效

            // 0/9 完成  样式
//                            .setPicture_title_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题字体颜色
//                            .setPicture_right_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题右边字体颜色
//                            .setLeftBackDrawable(R.mipmap.back2) // 设置返回键图标
//                            .setStatusBar(ContextCompat.getColor(MainActivity.this, R.color.white)) // 设置状态栏颜色，默认是和标题栏一致
//                            .setImmersive(false)// 是否改变状态栏字体颜色(黑色) 今天总还有我哦】
            .create();
}
