package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.DetailsCarBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by davidzhao on 2017/5/27.
 */

public class DetailsCarbeanAdapter extends BaseAdapter {

    private Context context;
    private List<DetailsCarBean.ListBean.SeriesBean> list;
    private OnTextclickListener onTextclickListener;

    public DetailsCarbeanAdapter(Context context, List<DetailsCarBean.ListBean.SeriesBean> series,OnTextclickListener onTextclickListener) {
        this.context = context;
        list = series;
        this.onTextclickListener = onTextclickListener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = convertView.inflate(context, R.layout.item_details, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvItemDetail.setText(list.get(position).getModels());
        holder.tvItemDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTextclickListener.onTextClick(v,position);
            }
        });
        return convertView;
    }


    static class ViewHolder {

        @InjectView(R.id.tv_item_detail)
        TextView tvItemDetail;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
    public interface OnTextclickListener{
        void onTextClick(View view,int position);
    }

}
