package com.feifandaiyu.feifan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feifandaiyu.feifan.R;
import com.feifandaiyu.feifan.bean.PersonalLoanURLBean;
import com.feifandaiyu.feifan.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by davidzhao on 2017/5/11.
 */

public class PersonalLoanAdapper extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private List<PersonalLoanURLBean.ListBean> list;
    private OnTestClickLisetener onTestClickLisetener;
    private OnButtonClickLisetrner3 onButtonClickLisetrner3;
    private OnButtonClickLisetrner4 onButtonClickLisetrner4;
    private OnButtonClickLisetrner5 onButtonClickLisetrner5;
    private OnButtonClickLisetrner6 onButtonClickLisetrner6;
    private OnButtonClickLisetrner7 onButtonClickLisetrner7;
    private OnButtonClickLisetrner8 onButtonClickLisetrner8;
    private OnButtonClickLisetrner9 onButtonClickLisetrner9;
    private OnButtonClickLisetrner10 onButtonClickLisetrner10;
    private OnButtonClickLisetrner11 onButtonClickLisetrner11;
    private OnButtonClickLisetrner12 onButtonClickLisetrner12;
    private OnButtonClickLisetrner13 onButtonClickLisetrner13;

    public PersonalLoanAdapper(List list, Context context, OnTestClickLisetener onTestClickLisetener, OnButtonClickLisetrner3 onButtonClickLisetrner3, OnButtonClickLisetrner4 onButtonClickLisetrner4,
                               OnButtonClickLisetrner5 onButtonClickLisetrner5, OnButtonClickLisetrner6 onButtonClickLisetrner6, OnButtonClickLisetrner7 onButtonClickLisetrner7,
                               OnButtonClickLisetrner8 onButtonClickLisetrner8, OnButtonClickLisetrner9 onButtonClickLisetrner9, OnButtonClickLisetrner10 onButtonClickLisetrner10
            , OnButtonClickLisetrner11 onButtonClickLisetrner11, OnButtonClickLisetrner12 onButtonClickLisetrner12, OnButtonClickLisetrner13 onButtonClickLisetrner13) {
        this.list = list;
        this.context = context;
        this.onTestClickLisetener = onTestClickLisetener;
        this.onButtonClickLisetrner3 = onButtonClickLisetrner3;
        this.onButtonClickLisetrner4 = onButtonClickLisetrner4;
        this.onButtonClickLisetrner5 = onButtonClickLisetrner5;
        this.onButtonClickLisetrner6 = onButtonClickLisetrner6;
        this.onButtonClickLisetrner7 = onButtonClickLisetrner7;
        this.onButtonClickLisetrner8 = onButtonClickLisetrner8;
        this.onButtonClickLisetrner9 = onButtonClickLisetrner9;
        this.onButtonClickLisetrner10 = onButtonClickLisetrner10;
        this.onButtonClickLisetrner11 = onButtonClickLisetrner11;
        this.onButtonClickLisetrner12 = onButtonClickLisetrner12;
        this.onButtonClickLisetrner13 = onButtonClickLisetrner13;
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

            convertView = LayoutInflater.from(context).inflate(R.layout.item_personal_loan, null);
            viewHolder = new ViewHolder(convertView);
            viewHolder.button4.setVisibility(View.GONE);
            viewHolder.button5.setVisibility(View.GONE);
            viewHolder.button6.setVisibility(View.GONE);
            viewHolder.button3.setVisibility(View.GONE);
            viewHolder.button7.setVisibility(View.GONE);
            viewHolder.tvFuyi.setVisibility(View.GONE);
            viewHolder.tvTiche.setVisibility(View.GONE);
            viewHolder.tvLuohu.setVisibility(View.GONE);
            viewHolder.tvDiya.setVisibility(View.GONE);
            viewHolder.tvFangkuan.setVisibility(View.GONE);
            viewHolder.tvFanyong.setVisibility(View.GONE);

            convertView.setTag(viewHolder);

        } else {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_personal_loan, null);
                viewHolder = new ViewHolder(convertView);
                viewHolder.button4.setVisibility(View.GONE);
                viewHolder.button5.setVisibility(View.GONE);
                viewHolder.button6.setVisibility(View.GONE);
                viewHolder.button3.setVisibility(View.GONE);
                viewHolder.button7.setVisibility(View.GONE);
                viewHolder.tvFuyi.setVisibility(View.GONE);
                viewHolder.tvTiche.setVisibility(View.GONE);
                viewHolder.tvLuohu.setVisibility(View.GONE);
                viewHolder.tvDiya.setVisibility(View.GONE);
                viewHolder.tvFangkuan.setVisibility(View.GONE);
                viewHolder.tvFanyong.setVisibility(View.GONE);
                convertView.setTag(viewHolder);

            } else {

                viewHolder = (ViewHolder) convertView.getTag();

            }
        }

        viewHolder.button4.setVisibility(View.GONE);
        viewHolder.button6.setVisibility(View.GONE);
        viewHolder.button5.setVisibility(View.GONE);
        viewHolder.button3.setVisibility(View.GONE);
        viewHolder.button7.setVisibility(View.GONE);
        viewHolder.tvFuyi.setVisibility(View.GONE);
        viewHolder.tvTiche.setVisibility(View.GONE);
        viewHolder.tvLuohu.setVisibility(View.GONE);
        viewHolder.tvDiya.setVisibility(View.GONE);
        viewHolder.tvFangkuan.setVisibility(View.GONE);
        viewHolder.tvFanyong.setVisibility(View.GONE);
        viewHolder.textView2.setText(list.get(position).getTelphone());
        viewHolder.textView9.setText(list.get(position).getUsername());
        viewHolder.tvCarname.setText(list.get(position).getCarSize());
        viewHolder.tvCarname.setSelected(true);

        if (list.get(position).getIsnew().equals("1")) {

            viewHolder.ll_new.setVisibility(View.GONE);

        } else if (list.get(position).getIsnew().equals("2")) {

            viewHolder.tv_chepai.setText(list.get(position).getLicenseNum());

            if (list.get(position).getVprice().equals("-999")) {

                if (list.get(position).getEstate().equals("-1")) {
                    viewHolder.tv_vprice.setText("评估师驳回");
                } else {
                    viewHolder.tv_vprice.setText("暂未评估");
                }

            } else {
                viewHolder.tv_vprice.setText(list.get(position).getVprice());

            }
        }

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
        hashMap.put("5", "终审-初审通过");
        hashMap.put("6", "放款中");
        hashMap.put("7", "还款中");
        hashMap.put("8", "已驳回,请重新提交");//已驳回,已评估  已驳回,待评估
        hashMap.put("9", "作废");
        hashMap.put("10", "销售作废");
        hashMap.put("11", "已还清");
        hashMap.put("-1", "评估师驳回");

        String msg = hashMap.get(list.get(position).getFlag());

//        viewHolder.tvState.setText(msg);
//        if (!list.get(position).getFlag().equals("8")) {
//            viewHolder.tvState.setText(msg);
//        } else {
//            if (list.get(position).getVprice().equals("0")) {
//                viewHolder.tvState.setText("已驳回,待评估");
//            } else {
//                viewHolder.tvState.setText("已驳回,已评估");
//            }
//        }

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
                viewHolder.button7.setVisibility(View.GONE);


            } else {
//                viewHolder.button4.setVisibility(View.VISIBLE);
                viewHolder.button3.setBackgroundResource(R.drawable.wanshan);
                viewHolder.button5.setVisibility(View.VISIBLE);

            }

            if (list.get(position).getFlag().equals("0") || list.get(position).getFlag().equals("-1") || list.get(position).getFlag().equals("-999")) {

                viewHolder.button6.setVisibility(View.GONE);

                viewHolder.button4.setVisibility(View.GONE);

                viewHolder.button7.setVisibility(View.GONE);

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

                    if (list.get(position).getFlag().equals("6") && list.get(position).getGps().equals("2")) {
                        viewHolder.button6.setVisibility(View.VISIBLE);
                        viewHolder.button7.setVisibility(View.GONE);

                    }

                    if (list.get(position).getLiftcar().equals("2")) {
                        viewHolder.tvTiche.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.tvTiche.setVisibility(View.GONE);
                    }

                    if (list.get(position).getFlag().equals("6") && list.get(position).getSettle().equals("2")) {
                        viewHolder.tvLuohu.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.tvLuohu.setVisibility(View.GONE);
                    }

//                    if (list.get(position).getMortgage().equals("2")) {
//                        viewHolder.tvDiya.setVisibility(View.VISIBLE);
//                    } else {
                        viewHolder.tvDiya.setVisibility(View.GONE);
//                    }

                    if (list.get(position).getPay().equals("2")) {
                        viewHolder.tvFangkuan.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.tvFangkuan.setVisibility(View.GONE);
                    }

                    if (list.get(position).getFlag().equals("6") && list.get(position).getCert().equals("1")) {
                        viewHolder.button6.setVisibility(View.GONE);
                        viewHolder.button7.setVisibility(View.VISIBLE);
                    }

                } else if (list.get(position).getIsnew().equals("2")) {
                    if (list.get(position).getFlag().equals("6") && list.get(position).getGps().equals("2")) {
                        viewHolder.button6.setVisibility(View.VISIBLE);
                    }
                    if (list.get(position).getFlag().equals("6") && list.get(position).getLiftcar().equals("2")) {
                        viewHolder.tvTiche.setVisibility(View.VISIBLE);
                    }
                    if (list.get(position).getFlag().equals("6") && list.get(position).getSettle().equals("2")) {
                        viewHolder.tvLuohu.setVisibility(View.VISIBLE);
                    }
                }
                viewHolder.button5.setVisibility(View.GONE);

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

        viewHolder.textView2.setOnClickListener(this);

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

        final View finalConvertView2 = convertView;
        viewHolder.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner6.onButtonClick(position, finalConvertView2);
            }
        });

        final View finalConvertView3 = convertView;
        viewHolder.button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner10.onButtonClick(position, finalConvertView3);
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

        viewHolder.tvTiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner11.onButtonClick(position);
            }
        });

        viewHolder.tvLuohu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner12.onButtonClick(position);
            }
        });
        viewHolder.tvDiya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickLisetrner13.onButtonClick(position);
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

    public interface OnTestClickLisetener {
        void onTestClick(View view);
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
        void onButtonClick(int positon, View view);

    }

    public interface OnButtonClickLisetrner10 {
        void onButtonClick(int positon, View view);

    }

    public interface OnButtonClickLisetrner7 {
        void onButtonClick(int positon);
    }

    public interface OnButtonClickLisetrner11 {
        void onButtonClick(int positon);
    }

    public interface OnButtonClickLisetrner12 {
        void onButtonClick(int positon);
    }

    public interface OnButtonClickLisetrner13 {
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
        @InjectView(R.id.textView9)
        TextView textView9;
        @InjectView(R.id.tv_chepai)
        TextView tv_chepai;
        @InjectView(R.id.tv_vprice)
        TextView tv_vprice;
        @InjectView(R.id.textView2)
        TextView textView2;
        @InjectView(R.id.tv_carname)
        TextView tvCarname;
        @InjectView(R.id.fuyi)
        TextView tvFuyi;
        @InjectView(R.id.fangkuan)
        TextView tvFangkuan;
        @InjectView(R.id.tiche)
        TextView tvTiche;
        @InjectView(R.id.luohu)
        TextView tvLuohu;
        @InjectView(R.id.diya)
        TextView tvDiya;
        @InjectView(R.id.fanyong)
        TextView tvFanyong;
        @InjectView(R.id.button3)
        Button button3;
        @InjectView(R.id.button4)
        Button button4;
        @InjectView(R.id.button5)
        Button button5;
        @InjectView(R.id.button6)
        Button button6;
        @InjectView(R.id.button7)
        Button button7;
        @InjectView(R.id.ll_new)
        RelativeLayout ll_new;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView2:
                onTestClickLisetener.onTestClick(v);
                break;
        }
    }


}

