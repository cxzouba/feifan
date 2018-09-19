package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.DialogBankBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by houdaichang on 2017/6/12.
 */

public class DialogAdapter extends BaseAdapter {

    private Context context;
    private List<DialogBankBean.ListBean> list;

    public DialogAdapter(Context context, List<DialogBankBean.ListBean> list) {
        this.context = context;
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_brand, null);
            viewHolder = new DialogAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DialogAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.tvItemBrand.setText(list.get(position).getUsername());
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_item_brand)
        TextView tvItemBrand;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
