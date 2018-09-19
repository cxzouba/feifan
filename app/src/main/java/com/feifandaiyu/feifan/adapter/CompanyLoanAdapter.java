package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.CompanyLoanURLBean;
import com.feifandaiyu.feifan.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by davidzhao on 2017/5/8.
 */

public class CompanyLoanAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private List<CompanyLoanURLBean.ListBean> list;
    private OnTestClickLisetener onTestClickLisetener;
    private OnButtonClickLisetrner3 onButtonClickLisetrner3;
    private OnButtonClickLisetrner4 onButtonClickLisetrner4;
    private OnButtonClickLisetrner5 onButtonClickLisetrner5;

    public CompanyLoanAdapter(List list, Context context, OnTestClickLisetener onTestClickLisetener,
                              OnButtonClickLisetrner3 onButtonClickLisetrner3, OnButtonClickLisetrner4 onButtonClickLisetrner4,
                              OnButtonClickLisetrner5 onButtonClickLisetrner5) {
        this.list = list;
        this.context = context;
        this.onTestClickLisetener = onTestClickLisetener;
        this.onButtonClickLisetrner3 = onButtonClickLisetrner3;
        this.onButtonClickLisetrner4 = onButtonClickLisetrner4;
        this.onButtonClickLisetrner5 = onButtonClickLisetrner5;
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
        if (viewHolder.needInflate) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_company_loan, null);
            viewHolder = new ViewHolder(convertView);
            viewHolder.button4.setVisibility(View.GONE);
            viewHolder.button5.setVisibility(View.GONE);
            viewHolder.button3.setVisibility(View.GONE);
            convertView.setTag(viewHolder);
        } else {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_company_loan, null);
                viewHolder = new ViewHolder(convertView);
                viewHolder.button4.setVisibility(View.GONE);
                viewHolder.button5.setVisibility(View.GONE);
                viewHolder.button3.setVisibility(View.GONE);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }

        }
        viewHolder.button4.setVisibility(View.GONE);
        viewHolder.button5.setVisibility(View.GONE);
        viewHolder.button3.setVisibility(View.GONE);
        viewHolder.textView2.setText(list.get(position).getTelphone());
        viewHolder.textView9.setText(list.get(position).getCompany_name());

        /**
         *  0-已建档，1-发起贷款，2-申请贷款中，3-初审 通过，4-贷款通过，
         *  5，放款中，6-还款中，7-已还清，8-初审未通过，拒绝
         */
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("0", "已建档");
        hashMap.put("1", "发起贷款");
        hashMap.put("2", "申请贷款中");
        hashMap.put("3", "初审通过");
        hashMap.put("4", "贷款通过");
        hashMap.put("5", "放款中");
        hashMap.put("6", "还款中");
        hashMap.put("7", "已还清");
        hashMap.put("8", "已驳回,请重新提交");
        hashMap.put("9", "作废");
        String msg = hashMap.get(list.get(position).getFlag());
        viewHolder.tvState.setText(msg);

        if (role == 1) {
            if (list.get(position).getFlag().equals("0")) {

                viewHolder.button3.setVisibility(View.INVISIBLE);

            } else {
                viewHolder.button3.setBackgroundResource(R.drawable.liucheng);
                viewHolder.button3.setVisibility(View.VISIBLE);
            }
            viewHolder.button4.setVisibility(View.INVISIBLE);
            viewHolder.button5.setVisibility(View.INVISIBLE);

        } else {

            viewHolder.button3.setVisibility(View.VISIBLE);

            if (list.get(position).getFlag().equals("0")) {

                viewHolder.button4.setVisibility(View.GONE);

                viewHolder.button5.setVisibility(View.VISIBLE);

                viewHolder.button3.setBackgroundResource(R.drawable.wanshan);

            } else {

                viewHolder.button3.setBackgroundResource(R.drawable.liucheng);

                if (list.get(position).getFlag().equals("8")) {
                    viewHolder.button3.setBackgroundResource(R.drawable.wanshan);
                    viewHolder.button4.setVisibility(View.VISIBLE);
                    viewHolder.button5.setVisibility(View.VISIBLE);
                }


            }
        }


        viewHolder.textView2.setOnClickListener(this);
        viewHolder.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner3.onButtonClick(position);
            }
        });

        viewHolder.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner4.onButtonClick(position);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView2:
                onTestClickLisetener.onTestClick(v);
                break;
        }

    }

    public interface OnTestClickLisetener {
        void onTestClick(View view);
    }

    public interface OnButtonClickLisetrner3 {
        void onButtonClick(int positon);
    }

    public interface OnButtonClickLisetrner4 {
        void onButtonClick(int positon);
    }

    public interface OnButtonClickLisetrner5 {
        void onButtonClick(int positon, View view);
    }


    public static class ViewHolder {
        public static boolean needInflate = false;
        @InjectView(R.id.textView9)
        TextView textView9;
        @InjectView(R.id.textView2)
        TextView textView2;
        @InjectView(R.id.tv_state)
        TextView tvState;
        @InjectView(R.id.button3)
        Button button3;
        @InjectView(R.id.button4)
        Button button4;
        @InjectView(R.id.button5)
        Button button5;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

