package com.feifandaiyu.feifan.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by houdaichang on 2017/5/8.
 */

public class UploadImageAdapter extends BaseAdapter {

    String[] photoStyles = new String[]{
            "客户身份证-正面",
            "客户身份证-背面",
            "配偶身份证-正面",
            "配偶身份证-背面",
            "客户资产住房信息",
            "客户合同信息"
    };

    @Override
    public int getCount() {
        return photoStyles.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
