package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2017/6/22.
 */

public class BankListBean {

    /**
     * code : 1
     * list : [{"bankName":"泰隆银行"},{"bankName":"建设银行"},{"bankName":"中信银行"},{"bankName":"光大银行"},{"bankName":"民生银行"},{"bankName":"广发银行"},{"bankName":"兴业银行"},{"bankName":"中国银行"},{"bankName":"工商银行"},{"bankName":"农业银行"},{"bankName":"交通银行"},{"bankName":"邮储银行"},{"bankName":"招商银行"},{"bankName":"华夏银行"}]
     */

    private int code;
    private List<ListBean> list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * bankName : 泰隆银行
         */

        private String bankName;

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }
    }
}
