package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.GpsListBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by houdaichang on 2017/6/12.
 */

public class GpsListAdapter extends BaseAdapter {

    private Context context;
    private List<GpsListBean.ListBean> list;

    public GpsListAdapter(Context context, List<GpsListBean.ListBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gps, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.tvItemBrand.setText(list.get(position).getBankName());
        viewHolder.tvNum.setText(list.get(position).getNumber());
        viewHolder.tvTime.setText(list.get(position).getCreatetime());
        viewHolder.tvState.setText(list.get(position).getState());
        viewHolder.tvPaifa.setText(list.get(position).getDistribute()+"");
        viewHolder.tvQianding.setText(list.get(position).getSign()+"");

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_num)
        TextView tvNum;
        @InjectView(R.id.tv_time)
        TextView tvTime;
        @InjectView(R.id.tv_state)
        TextView tvState;
        @InjectView(R.id.tv_paifa1)
        TextView tvPaifa;
        @InjectView(R.id.tv_qianding1)
        TextView tvQianding;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
