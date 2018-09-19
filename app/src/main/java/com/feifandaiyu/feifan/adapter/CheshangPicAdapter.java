package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.carloan.ImageShower;
import com.feifandaiyu.feifan.bean.CompanyCheshangBean;
import com.feifandaiyu.feifan.utils.ImageViewUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by houdaichang on 2017/8/31.
 */

public class CheshangPicAdapter extends BaseAdapter {

    private Context context;
    private CompanyCheshangBean bean;

    public CheshangPicAdapter(Context context, CompanyCheshangBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getList().getPlacePic() == null ? 0 : bean.getList().getPlacePic().size();
    }

    @Override
    public Object getItem(int position) {
        return bean.getList().getPlacePic().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView ==null){

            convertView = LayoutInflater.from(context).inflate(R.layout.item_cheshang_image, null);

            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageViewUtils.showNetImage(context,bean.getList().getPlacePic().get(position),R.drawable.crabgnormal,holder.fiv);

        holder.fiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageShower.class);
                intent.putExtra("image01", bean.getList().getPlacePic().get(position).split(",")[0]);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.fiv)
        ImageView fiv;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
