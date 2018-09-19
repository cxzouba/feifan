package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.carloan.ImageShower;
import com.feifandaiyu.feifan.bean.UpdateDiyaBean;
import com.feifandaiyu.feifan.utils.ImageViewUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.media.CamcorderProfile.get;

/**
 * Created by houdaichang on 2017/8/31.
 */

public class MyDiyaAdapter extends BaseAdapter {

    private Context context;
    private UpdateDiyaBean bean;

    public MyDiyaAdapter(Context context, UpdateDiyaBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getShow() == null ? 0 : bean.getShow().getEquipmentPic().size();
    }

    @Override
    public Object getItem(int position) {
        return bean.getShow().getEquipmentPic().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.gv_filter_image, null);

            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageViewUtils.showNetImage(context, bean.getShow().getEquipmentPic().get(position), R.drawable.crabgnormal, holder.fiv);

        holder.fiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageShower.class);
                intent.putExtra("image01", bean.getShow().getEquipmentPic().get(position).split(",")[0]);
                context.startActivity(intent);
            }
        });

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.getShow().getEquipmentPic().remove(position);
                bean.getList().getEquipmentPic().remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.fiv)
        ImageView fiv;
        @InjectView(R.id.iv_del)
        ImageView ivDel;
        @InjectView(R.id.ll_del)
        LinearLayout llDel;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
