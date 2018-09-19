package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.CarLoanBean;
import com.feifandaiyu.feifan.utils.ImageViewUtils;
import com.feifandaiyu.feifan.utils.PreferenceUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by houdaichang on 2017/6/2.
 */

public class CarLoanAdapter extends BaseAdapter {

    private Context context;
    private List<CarLoanBean.ListBean> list;
    private OnButtonClickLisetrner onButtonClickLisetrner;
    private OnButtonClickLisetrner3 onButtonClickLisetrner3;
    private OnButtonClickLisetrner4 onButtonClickLisetrner4;
    private OnButtonClickLisetrner5 onButtonClickLisetrner5;
    private OnButtonClickLisetrner6 onButtonClickLisetrner6;
    private OnButtonClickLisetrner7 onButtonClickLisetrner7;
    private OnButtonClickLisetrner8 onButtonClickLisetrner8;
    private OnButtonClickLisetrner9 onButtonClickLisetrner9;

    public CarLoanAdapter(Context context, List<CarLoanBean.ListBean> list, OnButtonClickLisetrner onButtonClickLisetrner, OnButtonClickLisetrner3 onButtonClickLisetrner3
            , OnButtonClickLisetrner4 onButtonClickLisetrner4, OnButtonClickLisetrner5 onButtonClickLisetrner5, OnButtonClickLisetrner6 onButtonClickLisetrner6
            , OnButtonClickLisetrner7 onButtonClickLisetrner7, OnButtonClickLisetrner8 onButtonClickLisetrner8, OnButtonClickLisetrner9 onButtonClickLisetrner9) {
        this.context = context;
        this.list = list;
        this.onButtonClickLisetrner = onButtonClickLisetrner;
        this.onButtonClickLisetrner3 = onButtonClickLisetrner3;
        this.onButtonClickLisetrner4 = onButtonClickLisetrner4;
        this.onButtonClickLisetrner5 = onButtonClickLisetrner5;
        this.onButtonClickLisetrner6 = onButtonClickLisetrner6;
        this.onButtonClickLisetrner7 = onButtonClickLisetrner7;
        this.onButtonClickLisetrner8 = onButtonClickLisetrner8;
        this.onButtonClickLisetrner9 = onButtonClickLisetrner9;

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
        int role = PreferenceUtils.getInt(context, "role");

        ViewHolder viewHolder = null;
        if (ViewHolder.needInflate) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_car_loan, null);
            viewHolder = new ViewHolder(convertView);
            viewHolder.button4.setVisibility(View.GONE);
            viewHolder.button5.setVisibility(View.GONE);
            viewHolder.button6.setVisibility(View.GONE);
            viewHolder.button3.setVisibility(View.GONE);
            viewHolder.tvFuyi.setVisibility(View.GONE);
            convertView.setTag(viewHolder);

        } else {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_car_loan, null);
                viewHolder = new ViewHolder(convertView);
                viewHolder.button4.setVisibility(View.GONE);
                viewHolder.button5.setVisibility(View.GONE);
                viewHolder.button6.setVisibility(View.GONE);
                viewHolder.button3.setVisibility(View.GONE);
                viewHolder.tvFuyi.setVisibility(View.GONE);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
        }

        viewHolder.button4.setVisibility(View.GONE);
        viewHolder.button6.setVisibility(View.GONE);
        viewHolder.button5.setVisibility(View.GONE);
        viewHolder.button3.setVisibility(View.GONE);
        viewHolder.tvFuyi.setVisibility(View.GONE);
        viewHolder.carHostName.setText(list.get(position).getUsername());
        viewHolder.carName.setText(list.get(position).getCar_size());
        viewHolder.tv_chepai.setText(list.get(position).getLicenseNum());
        viewHolder.carName.setSelected(true);

        if (list.get(position).getVprice().equals("-999")) {

            viewHolder.tvPingguPrice.setText("暂无评估价");
            viewHolder.tvPingguPrice.setTextColor(context.getResources().getColor(R.color.red));

        } else {

            viewHolder.tvPingguPrice.setText(list.get(position).getValuation_price() + "元");
            viewHolder.tvPingguPrice.setTextColor(context.getResources().getColor(R.color.green));

        }

        ImageViewUtils.showNetImage(context, list.get(position).getAppenhance_pic(), R.drawable.item_pinggushi_01, viewHolder.ivCar);

        if (role == 1) {
            viewHolder.button6.setVisibility(View.INVISIBLE);


            if (list.get(position).getFlag().equals("0")) {

                viewHolder.button3.setVisibility(View.INVISIBLE);

            } else {

                viewHolder.button3.setBackgroundResource(R.drawable.liucheng);
                viewHolder.button3.setVisibility(View.VISIBLE);

            }

            viewHolder.button4.setVisibility(View.INVISIBLE);
            viewHolder.button5.setVisibility(View.INVISIBLE);

        } else {

            if (list.get(position).getIsnew().equals("2")) {
                if (list.get(position).getReconsider().equals("0")) {
                    viewHolder.tvFuyi.setVisibility(View.VISIBLE);
                    viewHolder.tvFuyi.setText("复");

                } else if (list.get(position).getReconsider().equals("-1")) {
                    viewHolder.tvFuyi.setVisibility(View.VISIBLE);
                    viewHolder.tvFuyi.setText("驳");
                }

            } else if (list.get(position).getIsnew().equals("1")) {
                viewHolder.tvFuyi.setVisibility(View.GONE);
            }

            if (list.get(position).getPay().equals("1")) {
                viewHolder.tvFangkuan.setVisibility(View.GONE);
            } else if (list.get(position).getPay().equals("2")) {
                viewHolder.tvFangkuan.setVisibility(View.VISIBLE);
            }

            if (list.get(position).getDealer().equals("1")) {
                viewHolder.tvFanyong.setVisibility(View.GONE);
            } else if (list.get(position).getDealer().equals("2")) {
                viewHolder.tvFanyong.setVisibility(View.VISIBLE);
            }

            viewHolder.button3.setVisibility(View.VISIBLE);

            if (!(list.get(position).getFlag().equals("0") || list.get(position).getFlag().equals("-1") || list.get(position).getFlag().equals("8") || list.get(position).getFlag().equals("-999"))) {

                viewHolder.button3.setBackgroundResource(R.drawable.liucheng);

                viewHolder.button6.setVisibility(View.GONE);

                viewHolder.button4.setVisibility(View.GONE);

                viewHolder.button5.setVisibility(View.GONE);

            } else {

//                viewHolder.button4.setVisibility(View.VISIBLE);
                viewHolder.button3.setBackgroundResource(R.drawable.wanshan);
                viewHolder.button5.setVisibility(View.VISIBLE);

            }

            if (list.get(position).getFlag().equals("0") || list.get(position).getFlag().equals("-1") || list.get(position).getFlag().equals("-999")) {

                viewHolder.button6.setVisibility(View.GONE);

                viewHolder.button4.setVisibility(View.GONE);

                viewHolder.button5.setVisibility(View.VISIBLE);

//                viewHolder.button3.setBackgroundResource(R.drawable.wanshan);

            } else {

//                viewHolder.button4.setVisibility(View.VISIBLE);

                if (list.get(position).getFlag().equals("8")) {
                    viewHolder.button3.setBackgroundResource(R.drawable.wanshan);
                    viewHolder.button5.setVisibility(View.VISIBLE);
                    viewHolder.button3.setVisibility(View.VISIBLE);
                    viewHolder.button4.setVisibility(View.VISIBLE);
                }

                if (list.get(position).getIsnew().equals("1")) {
                    if (list.get(position).getFlag().equals("5") && list.get(position).getGps().equals("2")) {
                        viewHolder.button6.setVisibility(View.VISIBLE);
                        viewHolder.button5.setVisibility(View.GONE);
                    }
                } else if (list.get(position).getIsnew().equals("2")) {
                    if (list.get(position).getFlag().equals("5") && list.get(position).getGps().equals("2")) {
                        viewHolder.button6.setVisibility(View.VISIBLE);
                        viewHolder.button5.setVisibility(View.GONE);
                    }
                }

//                viewHolder.button3.setBackgroundResource(R.drawable.liucheng);

            }

//            if (list.get(position).getFlag().equals("0")) {
//
//                if (list.get(position).getIsnew().equals("2") && list.get(position).getAssessment().equals("1")) {
//                    viewHolder.tvState.setText("待评估");
//                } else if (list.get(position).getIsnew().equals("2") && list.get(position).getAssessment().equals("2")) {
//                    viewHolder.tvState.setText("已评估");
//                }
//            }
        }


        viewHolder.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner3.onButtonClick(position);
            }
        });

        final View finalConvertView1 = convertView;
        viewHolder.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner4.onButtonClick(position, finalConvertView1);
            }
        });

        viewHolder.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner6.onButtonClick(position);
            }
        });

        viewHolder.tvFuyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner7.onButtonClick(position);
            }
        });

        viewHolder.tvFangkuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner8.onButtonClick(position);
            }
        });

        viewHolder.tvFanyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner9.onButtonClick(position);
            }
        });

        final View finalConvertView = convertView;
        viewHolder.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner5.onButtonClick(position, finalConvertView);
            }
        });


        return convertView;
    }

    public interface OnButtonClickLisetrner {
        void onButtonClick(int positon);
    }

    public interface OnButtonClickLisetrner3 {
        void onButtonClick(int positon);
    }

    public interface OnButtonClickLisetrner4 {
        void onButtonClick(int positon, View view);
    }

    public interface OnButtonClickLisetrner5 {
        void onButtonClick(int positon, View view);
    }

    public interface OnButtonClickLisetrner6 {
        void onButtonClick(int positon);
    }

    public interface OnButtonClickLisetrner7 {
        void onButtonClick(int positon);
    }

    public interface OnButtonClickLisetrner8 {
        void onButtonClick(int positon);
    }

    public interface OnButtonClickLisetrner9 {
        void onButtonClick(int positon);
    }

    public static class ViewHolder {
        public static boolean needInflate = false;
        @InjectView(R.id.car_name)
        TextView carName;
        @InjectView(R.id.iv_car)
        ImageView ivCar;
        @InjectView(R.id.car_host_name)
        TextView carHostName;
        @InjectView(R.id.tv_pinggu_price)
        TextView tvPingguPrice;
        @InjectView(R.id.fuyi)
        TextView tvFuyi;
        @InjectView(R.id.fangkuan)
        TextView tvFangkuan;
        @InjectView(R.id.fanyong)
        TextView tvFanyong;
        @InjectView(R.id.tv_chepai)
        TextView tv_chepai;
        @InjectView(R.id.button3)
        Button button3;
        @InjectView(R.id.button4)
        Button button4;
        @InjectView(R.id.button5)
        Button button5;
        @InjectView(R.id.button6)
        Button button6;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
