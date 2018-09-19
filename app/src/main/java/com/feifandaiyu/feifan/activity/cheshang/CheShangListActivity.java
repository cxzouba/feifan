package com.feifandaiyu.feifan.activity.cheshang;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.fragment.CheshangCompanyFragment;
import com.feifandaiyu.feifan.fragment.CheshangPersonalFragment;
import com.feifandaiyu.feifan.utils.FragmentFactory;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by davidzhao on 2017/5/8.
 */

public class CheShangListActivity extends AppCompatActivity {

    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.iv_daipinggu)
    ImageView ivDaipinggu;
    @InjectView(R.id.iv_yipinggu)
    ImageView ivYipinggu;
    @InjectView(R.id.add_cheshang)
    TextView addCheshang;
    @InjectView(R.id.imageView)
    RelativeLayout imageView;
    @InjectView(R.id.sliding_tabs)
    Toolbar slidingTabs;
    @InjectView(R.id.fl_container)
    FrameLayout flContainer;
    @InjectView(R.id.root)
    LinearLayout root;
    Class[] fragments = new Class[]{
            CheshangPersonalFragment.class,
            CheshangCompanyFragment.class,

    };
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheshang_list);
        ButterKnife.inject(this);

        initFrament();

    }

    private void initFrament() {
        ivDaipinggu.setSelected(true);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fl_container, FragmentFactory.getInstance(fragments[0]), "0");
        ft.commit();
    }

    @OnClick({R.id.iv_back, R.id.iv_daipinggu, R.id.iv_yipinggu, R.id.add_cheshang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_daipinggu:
                ivDaipinggu.setSelected(true);
                ivYipinggu.setSelected(false);

                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fl_container, FragmentFactory.getInstance(fragments[0]), "0");
                ft.commit();
                break;
            case R.id.iv_yipinggu:
                ivDaipinggu.setSelected(false);
                ivYipinggu.setSelected(true);

                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fl_container, FragmentFactory.getInstance(fragments[1]), "1");
                ft.commit();
                break;
            case R.id.add_cheshang:
                new AlertDialog.Builder(this)

                        .setMessage("请选择车商类型")
                        .setPositiveButton("个人", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(CheShangListActivity.this, PersonalCheShangActivity.class));
                            }
                        })
                        .setNegativeButton("企业", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(CheShangListActivity.this, CompanyCheShangActivity.class));
                            }
                        })
                        .show();
                break;

            default:
        }
    }
}
