package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.CheshangListBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by davidzhao on 2017/5/15.
 */

public class CheshangAdapter extends BaseAdapter {
    private Context context;
    private List<CheshangListBean.ListBean> list;
    private OnButtonClickLisetrner1 onButtonClickLisetrner1;
    private OnButtonClickLisetrner2 onButtonClickLisetrner2;

    public CheshangAdapter(Context context, List<CheshangListBean.ListBean> list, OnButtonClickLisetrner1 onButtonClickLisetrner1, OnButtonClickLisetrner2 onButtonClickLisetrner2) {
        this.context = context;
        this.list = list;
        this.onButtonClickLisetrner1 = onButtonClickLisetrner1;
        this.onButtonClickLisetrner2 = onButtonClickLisetrner2;
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

        if (PersonalLoanAdapper.ViewHolder.needInflate) {

            convertView = LayoutInflater.from(context).inflate(R.layout.item_cheshang_list, null);
            holder = new CheshangAdapter.ViewHolder(convertView);
            holder.btWanshan.setVisibility(View.GONE);
            convertView.setTag(holder);

        } else {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_cheshang_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
        }

//        holder.tvName.setText(list.get(position).getUsername());
//        holder.tvBrand.setText(list.get(position).getCar_series());
//        holder.tvState.setText(list.get(position).getFlag());
//        ImageViewUtils.showImage(context, list.get(position).getAppenhance_pic(), R.drawable.item_pinggushi_01, holder.ivCar);
        holder.cheshangName.setText(list.get(position).getUsername());
        holder.cheshangRebate.setText(list.get(position).getRebate());
        holder.phone.setText(list.get(position).getTelphone());

        if (list.get(position).getStatus() == 0) {
            holder.btWanshan.setVisibility(View.VISIBLE);
        } else if (list.get(position).getStatus() == 1) {
            holder.btWanshan.setVisibility(View.GONE);
        }

        holder.btZuofei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner1.onButtonClick(position, v);
            }
        });

        holder.btWanshan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner2.onButtonClick(position);
            }
        });

        return convertView;
    }


    public interface OnButtonClickLisetrner1 {
        void onButtonClick(int positon, View view);
    }

    public interface OnButtonClickLisetrner2 {
        void onButtonClick(int positon);
    }


    public static class ViewHolder {
        public static boolean needInflate = false;
        @InjectView(R.id.cheshang_name)
        TextView cheshangName;
        @InjectView(R.id.phone)
        TextView phone;
        @InjectView(R.id.cheshang_rebate)
        TextView cheshangRebate;
        @InjectView(R.id.bt_zuofei)
        Button btZuofei;
        @InjectView(R.id.bt_wanshan)
        Button btWanshan;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
