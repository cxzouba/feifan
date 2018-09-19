package com.feifandaiyu.feifan.activity.evalutor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.carloan.ImageShower;
import com.feifandaiyu.feifan.activity.personalloan.UpLoadCarImageActivity;
import com.feifandaiyu.feifan.adapter.CarPicAdapter;
import com.feifandaiyu.feifan.base.BaseActivity;
import com.feifandaiyu.feifan.bean.CarDetailBean;
import com.feifandaiyu.feifan.utils.ImageViewUtils;
import com.feifandaiyu.feifan.view.CustomGridView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CarPic2Activity extends BaseActivity implements View.OnClickListener {


    @InjectView(R.id.number_progress_bar)
    NumberProgressBar numberProgressBar;
    @InjectView(R.id.waiguan_zhengqian)
    ImageView waiguanZhengqian;
    @InjectView(R.id.waiguan_zhenghou)
    ImageView waiguanZhenghou;
    @InjectView(R.id.waiguan_zuoqian)
    ImageView waiguanZuoqian;
    @InjectView(R.id.waiguan_youqian)
    ImageView waiguanYouqian;
    @InjectView(R.id.waiguan_zuohou)
    ImageView waiguanZuohou;
    @InjectView(R.id.waiguan_youhou)
    ImageView waiguanYouhou;
    @InjectView(R.id.waiguan_fengdang)
    ImageView waiguanFengdang;
    @InjectView(R.id.zhongkong_licheng)
    ImageView zhongkongLicheng;
    @InjectView(R.id.neibu_mingpai)
    ImageView neibuMingpai;
    @InjectView(R.id.neibu_fadongji)
    ImageView neibuFadongji;
    @InjectView(R.id.neibu_houbeixing)
    ImageView neibuHoubeixing;
    @InjectView(R.id.fujia_dengji_1)
    ImageView fujiaDengji1;
    @InjectView(R.id.fujia_dengji_2)
    ImageView fujiaDengji2;
    @InjectView(R.id.fujia_dengji_3)
    ImageView fujiaDengji3;
    @InjectView(R.id.fujia_dengji_4)
    ImageView fujiaDengji4;
    @InjectView(R.id.fujia_dengji_5)
    ImageView fujiaDengji5;
    @InjectView(R.id.fujia_dengji_6)
    ImageView fujiaDengji6;
    @InjectView(R.id.fujia_dengji_7)
    ImageView fujiaDengji7;
    @InjectView(R.id.fujia_dengji_8)
    ImageView fujiaDengji8;
    @InjectView(R.id.fujia_dengji_9)
    ImageView fujiaDengji9;
    @InjectView(R.id.fujia_xinshi_zheng)
    ImageView fujiaXinshiZheng;
    @InjectView(R.id.fujia_xinshi_fu)
    ImageView fujiaXinshiFu;
    @InjectView(R.id.fujia_xinshi_jian)
    ImageView fujiaXinshiJian;
    @InjectView(R.id.fujia_shangxian)
    ImageView fujiaShangxian;
    @InjectView(R.id.fujia_qiangxian)
    ImageView fujiaQiangxian;
    @InjectView(R.id.other_pic)
    CustomGridView otherPic;
    private ImageView iv_back;
    private CarDetailBean bean;

    @Override
    protected int getContentView() {
        return R.layout.activity_car_pic_show;

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("车辆照片");
        showNext(false);
        ButterKnife.inject(this);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        Intent intent = this.getIntent();

        bean = (CarDetailBean) intent.getSerializableExtra("CarDetailBean");


        showImage();    /* https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498552710&di=907cdb67a4c6acd5759622b61a32f2be&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2F1011%2F06041G22505%2F1F604122505-3.jpg*/

    }

    private void showImage() {

        if (bean.getList().get(0).getAppenhance_pic() == null) {
            new AlertDialog.Builder(CarPic2Activity.this)
                    .setMessage("是否上传照片")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CarPic2Activity.this.finish();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(CarPic2Activity.this, UpLoadCarImageActivity.class));
                            CarPic2Activity.this.finish();
                        }
                    }).setCancelable(false)
                    .show();

            return;
        } else {

            try {
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getAppenhance_pic().get(0).split(",")[0], R.drawable.crabgnormal, waiguanZhengqian);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getAppenhance_pic().get(1).split(",")[0], R.drawable.crabgnormal, waiguanZhenghou);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getAppenhance_pic().get(2).split(",")[0], R.drawable.crabgnormal, waiguanZuoqian);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getAppenhance_pic().get(3).split(",")[0], R.drawable.crabgnormal, waiguanYouqian);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getAppenhance_pic().get(4).split(",")[0], R.drawable.crabgnormal, waiguanZuohou);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getAppenhance_pic().get(5).split(",")[0], R.drawable.crabgnormal, waiguanYouhou);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getAppenhance_pic().get(6).split(",")[0], R.drawable.crabgnormal, waiguanFengdang);

                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getControl_pic().get(0).split(",")[0], R.drawable.crabgnormal, zhongkongLicheng);

                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getStructure_pic().get(0).split(",")[0], R.drawable.crabgnormal, neibuMingpai);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getStructure_pic().get(1).split(",")[0], R.drawable.crabgnormal, neibuFadongji);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getStructure_pic().get(2).split(",")[0], R.drawable.crabgnormal, neibuHoubeixing);

                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getChassis_pic().get(0).split(",")[0], R.drawable.crabgnormal, fujiaDengji1);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getChassis_pic().get(1).split(",")[0], R.drawable.crabgnormal, fujiaDengji2);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getChassis_pic().get(2).split(",")[0], R.drawable.crabgnormal, fujiaDengji3);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getChassis_pic().get(3).split(",")[0], R.drawable.crabgnormal, fujiaDengji4);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getChassis_pic().get(4).split(",")[0], R.drawable.crabgnormal, fujiaDengji5);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getChassis_pic().get(5).split(",")[0], R.drawable.crabgnormal, fujiaDengji6);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getChassis_pic().get(6).split(",")[0], R.drawable.crabgnormal, fujiaDengji7);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getChassis_pic().get(7).split(",")[0], R.drawable.crabgnormal, fujiaDengji8);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getChassis_pic().get(8).split(",")[0], R.drawable.crabgnormal, fujiaDengji9);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getChassis_pic().get(9).split(",")[0], R.drawable.crabgnormal, fujiaXinshiZheng);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getChassis_pic().get(10).split(",")[0], R.drawable.crabgnormal, fujiaXinshiFu);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getChassis_pic().get(11).split(",")[0], R.drawable.crabgnormal, fujiaXinshiJian);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getChassis_pic().get(12).split(",")[0], R.drawable.crabgnormal, fujiaShangxian);
                ImageViewUtils.showNetImage(CarPic2Activity.this, bean.getList().get(0).getChassis_pic().get(13).split(",")[0], R.drawable.crabgnormal, fujiaQiangxian);

                otherPic.setAdapter(new CarPicAdapter(CarPic2Activity.this, bean));

            } catch (Exception e) {

            }

/*        ImageViewUtils.showNetImage(this,bean.getList().get(0).getStructure_pic().get(2),R.id.id_face_cancel,carImage22);
        ImageViewUtils.showNetImage(this,bean.getList().get(0).getStructure_pic().get(3),R.id.id_face_cancel,carImage23);*/


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                int enterAnim0 = R.anim.pre_enter;// 进入的activity对应的动画资源
                int exitAnim0 = R.anim.pre_exit;// 结束的activity对应的动画资源
                overridePendingTransition(enterAnim0, exitAnim0);
        }
    }


    @OnClick({R.id.waiguan_zhengqian, R.id.waiguan_zhenghou, R.id.waiguan_zuoqian, R.id.waiguan_youqian, R.id.waiguan_zuohou, R.id.waiguan_youhou, R.id.waiguan_fengdang,
            R.id.zhongkong_licheng,
            R.id.neibu_mingpai, R.id.neibu_fadongji, R.id.neibu_houbeixing,
            R.id.fujia_dengji_1, R.id.fujia_dengji_2, R.id.fujia_dengji_3, R.id.fujia_dengji_4, R.id.fujia_dengji_5, R.id.fujia_dengji_6, R.id.fujia_dengji_7, R.id.fujia_dengji_8, R.id.fujia_dengji_9, R.id.fujia_xinshi_zheng, R.id.fujia_xinshi_fu, R.id.fujia_xinshi_jian, R.id.fujia_shangxian, R.id.fujia_qiangxian,
            R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.waiguan_zhengqian:
                Intent intent = new Intent(this, ImageShower.class);
                intent.putExtra("image01", bean.getList().get(0).getAppenhance_pic().get(0).split(",")[0]);
                startActivity(intent);
                break;
            case R.id.waiguan_zhenghou:
                Intent intent1 = new Intent(this, ImageShower.class);
                intent1.putExtra("image01", bean.getList().get(0).getAppenhance_pic().get(1).split(",")[0]);
                startActivity(intent1);
                break;
            case R.id.waiguan_zuoqian:
                Intent intent2 = new Intent(this, ImageShower.class);
                intent2.putExtra("image01", bean.getList().get(0).getAppenhance_pic().get(2).split(",")[0]);
                startActivity(intent2);
                break;
            case R.id.waiguan_youqian:
                Intent intent23 = new Intent(this, ImageShower.class);
                intent23.putExtra("image01", bean.getList().get(0).getAppenhance_pic().get(2).split(",")[0]);
                startActivity(intent23);
                break;
            case R.id.waiguan_zuohou:
                Intent intent24 = new Intent(this, ImageShower.class);
                intent24.putExtra("image01", bean.getList().get(0).getAppenhance_pic().get(2).split(",")[0]);
                startActivity(intent24);
                break;
            case R.id.waiguan_youhou:
                Intent intent25 = new Intent(this, ImageShower.class);
                intent25.putExtra("image01", bean.getList().get(0).getAppenhance_pic().get(2).split(",")[0]);
                startActivity(intent25);
                break;
            case R.id.waiguan_fengdang:
                Intent intent26 = new Intent(this, ImageShower.class);
                intent26.putExtra("image01", bean.getList().get(0).getAppenhance_pic().get(2).split(",")[0]);
                startActivity(intent26);
                break;
            case R.id.zhongkong_licheng:
                Intent intent3 = new Intent(this, ImageShower.class);
                intent3.putExtra("image01", bean.getList().get(0).getControl_pic().get(0).split(",")[0]);
                startActivity(intent3);

                break;

            case R.id.neibu_mingpai:
                Intent intent13 = new Intent(this, ImageShower.class);
                intent13.putExtra("image01", bean.getList().get(0).getStructure_pic().get(0).split(",")[0]);
                startActivity(intent13);
                break;

            case R.id.neibu_fadongji:
                Intent intent11 = new Intent(this, ImageShower.class);
                intent11.putExtra("image01", bean.getList().get(0).getStructure_pic().get(1).split(",")[0]);
                startActivity(intent11);
                break;
            case R.id.neibu_houbeixing:
                Intent intent12 = new Intent(this, ImageShower.class);
                intent12.putExtra("image01", bean.getList().get(0).getStructure_pic().get(2).split(",")[0]);
                startActivity(intent12);
                break;

            case R.id.fujia_dengji_1:
                if (bean.getList().get(0).getChassis_pic().size() >= 1) {
                    Intent intent18 = new Intent(this, ImageShower.class);
                    intent18.putExtra("image01", bean.getList().get(0).getChassis_pic().get(0).split(",")[0]);
                    startActivity(intent18);
                }
                break;

            case R.id.fujia_dengji_2:
                if (bean.getList().get(0).getChassis_pic().size() >= 2) {
                    Intent intent19 = new Intent(this, ImageShower.class);
                    intent19.putExtra("image01", bean.getList().get(0).getChassis_pic().get(1).split(",")[0]);
                    startActivity(intent19);
                }
                break;

            case R.id.fujia_dengji_3:
                if (bean.getList().get(0).getChassis_pic().size() >= 3) {
                    Intent intent20 = new Intent(this, ImageShower.class);
                    intent20.putExtra("image01", bean.getList().get(0).getChassis_pic().get(2).split(",")[0]);
                    startActivity(intent20);
                }
                break;

            case R.id.fujia_dengji_4:
                if (bean.getList().get(0).getChassis_pic().size() >= 4) {
                    Intent intent21 = new Intent(this, ImageShower.class);
                    intent21.putExtra("image01", bean.getList().get(0).getChassis_pic().get(3).split(",")[0]);
                    startActivity(intent21);
                }
                break;
            case R.id.fujia_dengji_5:
                if (bean.getList().get(0).getChassis_pic().size() >= 5) {
                    Intent intent22 = new Intent(this, ImageShower.class);
                    intent22.putExtra("image01", bean.getList().get(0).getChassis_pic().get(4).split(",")[0]);
                    startActivity(intent22);
                }
                break;

            case R.id.fujia_dengji_6:
                if (bean.getList().get(0).getChassis_pic().size() >= 6) {
                    Intent intent29 = new Intent(this, ImageShower.class);
                    intent29.putExtra("image01", bean.getList().get(0).getChassis_pic().get(5).split(",")[0]);
                    startActivity(intent29);
                }
                break;

            case R.id.fujia_dengji_7:

                if (bean.getList().get(0).getChassis_pic().size() >= 7) {
                    Intent intent22 = new Intent(this, ImageShower.class);
                    intent22.putExtra("image01", bean.getList().get(0).getChassis_pic().get(6).split(",")[0]);
                    startActivity(intent22);
                }
                break;

            case R.id.fujia_dengji_8:
                if (bean.getList().get(0).getChassis_pic().size() >= 8) {
                    Intent intent22 = new Intent(this, ImageShower.class);
                    intent22.putExtra("image01", bean.getList().get(0).getChassis_pic().get(7).split(",")[0]);
                    startActivity(intent22);
                }
                break;
            case R.id.fujia_dengji_9:
                if (bean.getList().get(0).getChassis_pic().size() >= 9) {
                    Intent intent22 = new Intent(this, ImageShower.class);
                    intent22.putExtra("image01", bean.getList().get(0).getChassis_pic().get(8).split(",")[0]);
                    startActivity(intent22);
                }
                break;
            case R.id.fujia_xinshi_zheng:
                if (bean.getList().get(0).getChassis_pic().size() >= 10) {
                    Intent intent22 = new Intent(this, ImageShower.class);
                    intent22.putExtra("image01", bean.getList().get(0).getChassis_pic().get(9).split(",")[0]);
                    startActivity(intent22);
                }
                break;
            case R.id.fujia_xinshi_fu:
                if (bean.getList().get(0).getChassis_pic().size() >= 11) {
                    Intent intent22 = new Intent(this, ImageShower.class);
                    intent22.putExtra("image01", bean.getList().get(0).getChassis_pic().get(10).split(",")[0]);
                    startActivity(intent22);
                }
                break;
            case R.id.fujia_xinshi_jian:
                if (bean.getList().get(0).getChassis_pic().size() >= 12) {
                    Intent intent22 = new Intent(this, ImageShower.class);
                    intent22.putExtra("image01", bean.getList().get(0).getChassis_pic().get(11).split(",")[0]);
                    startActivity(intent22);
                }
                break;
            case R.id.fujia_shangxian:
                if (bean.getList().get(0).getChassis_pic().size() >= 13) {
                    Intent intent22 = new Intent(this, ImageShower.class);
                    intent22.putExtra("image01", bean.getList().get(0).getChassis_pic().get(12).split(",")[0]);
                    startActivity(intent22);
                }
                break;
            case R.id.fujia_qiangxian:
                if (bean.getList().get(0).getChassis_pic().size() >= 14) {
                    Intent intent22 = new Intent(this, ImageShower.class);
                    intent22.putExtra("image01", bean.getList().get(0).getChassis_pic().get(13).split(",")[0]);
                    startActivity(intent22);
                }
                break;
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
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
