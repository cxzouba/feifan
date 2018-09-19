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
import com.feifandaiyu.feifan.utils.ImageViewUtils;

import java.util.List;

/**
 * Created by houdaichang on 2017/9/1.
 */

public class CompanyPicarrAdapter extends RecyclerView.Adapter<CompanyPicarrAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<String> listShow;
    private List<String> list;

    public CompanyPicarrAdapter(Context context, List<String> listShow, List<String> list) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.listShow = listShow;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.gv_filter_image, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ImageViewUtils.showNetImage(context, listShow.get(position), R.drawable.crabgnormal, holder.mImg);

        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageShower.class);
                intent.putExtra("image01", listShow.get(position).split(",")[0]);
                context.startActivity(intent);
            }
        });

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listShow.remove(position);
                list.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listShow == null ? 0 : listShow.size();
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
