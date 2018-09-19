package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.DPGBean;
import com.feifandaiyu.feifan.utils.ImageViewUtils;
import com.feifandaiyu.feifan.utils.PreferenceUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by davidzhao on 2017/5/15.
 */

public class DPGAdapter extends BaseAdapter {
    @InjectView(R.id.iv_start)
    ImageView ivStart;
    private Context context;
    private List<DPGBean.ListBean> list;
    private DPGAdapter.OnButtonClickLisetrner onButtonClickLisetrner;

    public DPGAdapter(Context context, List<DPGBean.ListBean> list, DPGAdapter.OnButtonClickLisetrner onButtonClickLisetrner) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = convertView.inflate(context, R.layout.item_evaluater1, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(list.get(position).getLicenseNum());
        holder.tvBrand.setText(list.get(position).getCar_size());
        holder.tvBrand.setSelected(true);
        holder.tvState.setText("待评估");
        ImageViewUtils.showNetImage(context, list.get(position).getAppenhance_pic(), R.drawable.item_pinggushi_01, holder.ivCar);

        holder.ivStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.setString(context, "DpgCarId", list.get(position).getId());
                onButtonClickLisetrner.onButtonClick(position);
            }
        });

        return convertView;
    }


    public interface OnButtonClickLisetrner {
        void onButtonClick(int positon);
    }


    static class ViewHolder {
        @InjectView(R.id.tv_brand)
        TextView tvBrand;
        @InjectView(R.id.iv_car)
        ImageView ivCar;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_state)
        TextView tvState;
        @InjectView(R.id.iv_start)
        ImageView ivStart;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
