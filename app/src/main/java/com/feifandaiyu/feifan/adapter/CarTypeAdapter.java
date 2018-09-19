package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.HomeVistBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by davidzhao on 2017/5/26.
 */

public class CarTypeAdapter extends BaseAdapter implements View.OnClickListener {
    private List<HomeVistBean.ListBean.CarTypeBean> list;
    private Context context;
    OnTextclicklister onTextclicklister;

    public CarTypeAdapter(Context context, List list, OnTextclicklister onTextclicklister) {
        this.list = list;
        this.context = context;
        this.onTextclicklister = onTextclicklister;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String brand = list.get(position).getBrand();
        String firstLetter = list.get(position).getString(brand).charAt(0) + "";

        if (position > 0) {
            //上一个条目的首字母
            String lastLetter = list.get(position - 1).getString(list.get(position - 1).getBrand()).charAt(0) + "";
            //如果当前首字母和上一个相同，则隐藏首字母
            if (firstLetter.equalsIgnoreCase(lastLetter)) {
                holder.letter.setVisibility(View.GONE);
            } else {
                //说明不相等，直接设置
                //由于是复用的，所以当需要显示的时候，要设置为可见
                holder.letter.setVisibility(View.VISIBLE);
                holder.letter.setText(firstLetter);
            }
        } else {
            holder.letter.setVisibility(View.VISIBLE);
            holder.letter.setText(firstLetter);
        }


        holder.name.setText(brand);
        holder.name.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        onTextclicklister.onTextClick(v);
    }


    static class ViewHolder {
        @InjectView(R.id.letter)
        TextView letter;
        @InjectView(R.id.name)
        TextView name;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public interface OnTextclicklister {
        void onTextClick(View view);
    }

}
