package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.BrandCarBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by davidzhao on 2017/5/26.
 */

public class BrandChooseAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<BrandCarBean.ListBean.SeriesBean> list;
    private OnTextClickLister onTextClickLister;

    public BrandChooseAdapter(Context context, List<BrandCarBean.ListBean.SeriesBean> series, OnTextClickLister onTextClickLister) {
        this.context = context;
        this.onTextClickLister = onTextClickLister;
        list = series;
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = convertView.inflate(context, R.layout.item_brand, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvItemBrand.setText(list.get(position).getCars());
        holder.tvItemBrand.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        onTextClickLister.onTextClick(v);
    }

    static class ViewHolder {
        @InjectView(R.id.tv_item_brand)
        TextView tvItemBrand;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public interface OnTextClickLister {
        void onTextClick(View view);
    }
}
