package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.DPGBean;
import com.feifandaiyu.feifan.utils.ImageViewUtils;
import com.feifandaiyu.feifan.utils.PreferenceUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by davidzhao on 2017/5/15.
 */

public class YPGAdapter extends BaseAdapter {
    @InjectView(R.id.iv_start)
    ImageView ivStart;
    private Context context;
    private List<DPGBean.ListEndBean> list;
    private YPGAdapter.OnButtonClickLisetrner onButtonClickLisetrner;

    public YPGAdapter(Context context, List<DPGBean.ListEndBean> list, YPGAdapter.OnButtonClickLisetrner onButtonClickLisetrner) {
        this.context = context;
        this.list = list;
        this.onButtonClickLisetrner = onButtonClickLisetrner;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = convertView.inflate(context, R.layout.item_evaluater2, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PreferenceUtils.setString(context, "YpgCarId", list.get(position).getId());
        holder.tvBrand.setText(list.get(position).getCar_size());
        holder.tvBrand.setSelected(true);
        holder.tvName.setText(list.get(position).getLicenseNum());
        holder.tvState.setText(list.get(position).getValuation_price());
        ImageViewUtils.showNetImage(context, list.get(position).getAppenhance_pic(), R.drawable.item_pinggushi_01, holder.ivCar);

//        if (list.get(position).getIsover() == 1) {
//            holder.ivStart.setBackgroundResource(R.drawable.button_bg_blue);
//            holder.ivStart.setText("已完成");
//            holder.tvState.setTextColor(context.getResources().getColor(R.color.activecolor));
//
//        } else {
//            holder.ivStart.setBackgroundResource(R.drawable.button_bg_green);
//            holder.ivStart.setText("写评估报告");
//            holder.tvState.setTextColor(context.getResources().getColor(R.color.green));
//        }

        if (list.get(position).getUserId().equals("-999")) {
            holder.tvState.setTextColor(context.getResources().getColor(R.color.green));
            holder.ivStart.setBackgroundResource(R.drawable.button_bg_green);
            holder.ivStart.setText("写评估报告");

        } else {

            if (list.get(position).getApic().equals("未签名")) {

                if (list.get(position).getPid().equals("无报告")) {
                    holder.tvState.setTextColor(context.getResources().getColor(R.color.green));
                    holder.ivStart.setBackgroundResource(R.drawable.button_bg_green);
                    holder.ivStart.setText("写评估报告");
                } else {
                    holder.tvState.setTextColor(context.getResources().getColor(R.color.red));
                    holder.ivStart.setBackgroundResource(R.drawable.button_bg_pressed);
                    holder.ivStart.setText("签名");
                }

            } else {
                holder.tvState.setTextColor(context.getResources().getColor(R.color.activecolor));
                holder.ivStart.setBackgroundResource(R.drawable.button_bg_blue);
                holder.ivStart.setText("已完成");
            }
        }

        holder.ivStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.setString(context, "YPGCarId", list.get(position).getId());
                onButtonClickLisetrner.onButtonClick(position);
            }
        });
        return convertView;
    }

    public interface OnButtonClickLisetrner {
        void onButtonClick(int positon);
    }

    static class ViewHolder {
        @InjectView(R.id.tv_brand)
        TextView tvBrand;
        @InjectView(R.id.iv_car)
        ImageView ivCar;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_state)
        TextView tvState;
        @InjectView(R.id.iv_start)
        Button ivStart;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
