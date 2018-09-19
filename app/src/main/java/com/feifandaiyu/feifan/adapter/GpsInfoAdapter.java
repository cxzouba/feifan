package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.GpsInfoBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by houdaichang on 2017/6/12.
 */

public class GpsInfoAdapter extends BaseAdapter {

    private Context context;
    private List<GpsInfoBean.ListBean> list;

    public GpsInfoAdapter(Context context, List<GpsInfoBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

//    public IncomeAdapter(BaseInformationActivity context, ArrayList<String> list) {
//        this.context = context;
//        this.list = list;
//    }

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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_info, null);
            viewHolder = new GpsInfoAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GpsInfoAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.tvItemBrand.setText("编号:"+list.get(position).getNumber());
        viewHolder.tvName.setText("姓名:"+list.get(position).getUserName());
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_item_brand)
        TextView tvItemBrand;
        @InjectView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
