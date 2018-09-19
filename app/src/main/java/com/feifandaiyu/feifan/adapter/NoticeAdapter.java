package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.NoticeBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by houdaichang on 2017/11/7.
 */

public class NoticeAdapter extends BaseAdapter {

    private Context context;
    private List<NoticeBean.ListBean> list;
    private int flag;

    public NoticeAdapter(Context context, List<NoticeBean.ListBean> list, int flag) {
        this.context = context;
        this.list = list;
        this.flag = flag;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notice, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.message.setText(list.get(position).getContent());
        viewHolder.time.setText(list.get(position).getRiqi());

        if (flag == 0) {
            viewHolder.ivImg.setImageResource(R.drawable.tong);
        } else if (flag == 1) {
            viewHolder.ivImg.setImageResource(R.drawable.ping);
        } else if (flag == 2) {
            viewHolder.ivImg.setImageResource(R.drawable.shen);
        }

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_img)
        ImageView ivImg;
        @InjectView(R.id.message)
        TextView message;
        @InjectView(R.id.time)
        TextView time;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
