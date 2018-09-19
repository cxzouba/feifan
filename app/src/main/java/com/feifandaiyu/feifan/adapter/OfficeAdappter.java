package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.OfficeBean;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by davidzhao on 2017/5/16.
 */

public class OfficeAdappter extends BaseAdapter {
    private List<OfficeBean.ListBean> list;
    private Context context;
    private String[] beans;
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();
    private int newPosition;
    private ViewHolder holder = null;
    private OnRadiobuttonclick radiobuttonclick;

    public OfficeAdappter(Context context, List<OfficeBean.ListBean> list, OnRadiobuttonclick radiobuttonclick) {
        this.list = list;
        this.context = context;
        this.radiobuttonclick = radiobuttonclick;
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

        if (convertView == null) {
            convertView = convertView.inflate(context, R.layout.item_office, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvLeftnaem.setText(list.get(position).getAdmin_name());
//            holder.textView10.setText("张三" + position);
        holder.radioButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                radiobuttonclick.radioButtonck(position);
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                states.put(String.valueOf(position), true);
                OfficeAdappter.this.notifyDataSetChanged();
            }
        });
        boolean res = false;
        if (states.get(String.valueOf(position)) == null
                || states.get(String.valueOf(position)) == false) {
            res = false;
            states.put(String.valueOf(position), false);
        } else {
            res = true;
        }

        holder.radioButton2.setChecked(res);

        for (String key : states.keySet()) {
            System.out.println(key + "----------" + states.get(key));
        }

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.radioButton2)
        RadioButton radioButton2;
        @InjectView(R.id.tv_leftnaem)
        TextView tvLeftnaem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public interface OnRadiobuttonclick {
        void radioButtonck(int position);

    }
}