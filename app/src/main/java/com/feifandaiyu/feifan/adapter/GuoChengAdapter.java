package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.GuoChengBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by houdaichang on 2017/6/2.
 */

public class GuoChengAdapter extends BaseAdapter {

    private Context context;
    private List<GuoChengBean.ListBean> list;
    private OnButtonClickLisetrner onButtonClickLisetrner;

    public GuoChengAdapter(Context context, List<GuoChengBean.ListBean> list, OnButtonClickLisetrner onButtonClickLisetrner) {
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_timeline, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTime.setText(list.get(position).getTimes());
        viewHolder.tvWho.setText(list.get(position).getName());
        viewHolder.ivSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner.onButtonClick(position);
            }
        });

        return convertView;
    }

    public interface OnButtonClickLisetrner {
        void onButtonClick(int positon);
    }

    static class ViewHolder {
        @InjectView(R.id.tv_time)
        TextView tvTime;
        @InjectView(R.id.tv_who)
        TextView tvWho;
        @InjectView(R.id.iv_see)
        ImageView ivSee;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
