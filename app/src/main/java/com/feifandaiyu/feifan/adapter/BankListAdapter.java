package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.BankListBean;
import com.feifandaiyu.feifan.bean.IncomeBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by houdaichang on 2017/6/12.
 */

public class BankListAdapter extends BaseAdapter {

    private Context context;
    private List<BankListBean.ListBean> list;

    public BankListAdapter(Context context, List<BankListBean.ListBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_brand, null);
            viewHolder = new BankListAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BankListAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.tvItemBrand.setText(list.get(position).getBankName());
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
