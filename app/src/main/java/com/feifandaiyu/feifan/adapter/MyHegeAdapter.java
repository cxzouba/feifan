package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.activity.carloan.ImageShower;
import com.feifandaiyu.feifan.bean.DiyaBean;
import com.feifandaiyu.feifan.utils.ImageViewUtils;

/**
 * Created by houdaichang on 2017/9/1.
 */

public class MyHegeAdapter extends RecyclerView.Adapter<MyHegeAdapter.ViewHolder> {

    private DiyaBean bean;
    private Context context;
    private LayoutInflater mInflater;

    public MyHegeAdapter(DiyaBean bean, Context context) {
        this.bean = bean;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.gv_filter_image, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ImageViewUtils.showNetImage(context, bean.getShow().getCertificate().get(position), R.drawable.crabgnormal, holder.mImg);

        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageShower.class);
                intent.putExtra("image01", bean.getShow().getCertificate().get(position).split(",")[0]);
                context.startActivity(intent);
            }
        });

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.getShow().getCertificate().remove(position);
                bean.getList().getCertificate().remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.getShow().getCertificate() == null ? 0 : bean.getShow().getCertificate().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        ImageView ivDel;

        public ViewHolder(View view) {
            super(view);
            mImg = (ImageView) view.findViewById(R.id.fiv);
            ivDel = (ImageView) view.findViewById(R.id.iv_del);
        }
    }
}
