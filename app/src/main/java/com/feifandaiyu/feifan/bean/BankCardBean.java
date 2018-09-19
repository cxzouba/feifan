package com.feifandaiyu.feifan.bean;

/**
 * Created by houdaichang on 2017/7/13.
 */

public class BankCardBean {

    /**
     * code : 1
     * msg : 添加成功
     */

    private int code;
    private String msg;
    /**
     * list : {"cardHolder":"测试户口","cardNum":"62222222343233","bankName":"建设银行","openingBank":"建设银行1111","mobile":"13222345675","unionPay":"-1","id":"295"}
     */

    private ListBean list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * cardHolder : 测试户口
         * cardNum : 62222222343233
         * bankName : 建设银行
         * openingBank : 建设银行1111
         * mobile : 13222345675
         * unionPay : -1
         * id : 295
         */

        private String cardHolder;
        private String cardNum;
        private String bankName;
        private String openingBank;
        private String mobile;
        private String unionPay;
        private String id;

        public String getCardHolder() {
            return cardHolder;
        }

        public void setCardHolder(String cardHolder) {
            this.cardHolder = cardHolder;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getOpeningBank() {
            return openingBank;
        }

        public void setOpeningBank(String openingBank) {
            this.openingBank = openingBank;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUnionPay() {
            return unionPay;
        }

        public void setUnionPay(String unionPay) {
            this.unionPay = unionPay;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
