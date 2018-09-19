package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.carloan.ImageShower;
import com.feifandaiyu.feifan.bean.UpdateCreatBean;
import com.feifandaiyu.feifan.utils.ImageViewUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by houdaichang on 2017/8/31.
 */

public class MyCreatAdapter extends BaseAdapter {

    private Context context;
    private UpdateCreatBean bean;

    public MyCreatAdapter(Context context, UpdateCreatBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getList().getPic() == null ? 0 : bean.getList().getPic().size();
    }

    @Override
    public Object getItem(int position) {
        return bean.getList().getPic().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView ==null){

            convertView = LayoutInflater.from(context).inflate(R.layout.gv_filter_image, null);

            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageViewUtils.showNetImage(context,bean.getList().getPic().get(position),R.drawable.crabgnormal,holder.fiv);

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.getList().getPic().remove(position);
                notifyDataSetChanged();
            }
        });

        holder.fiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageShower.class);
                intent.putExtra("image01", bean.getList().getPic().get(position).split(",")[0]);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.fiv)
        ImageView fiv;
        @InjectView(R.id.iv_del)
        ImageView ivDel;
        @InjectView(R.id.ll_del)
        LinearLayout llDel;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
