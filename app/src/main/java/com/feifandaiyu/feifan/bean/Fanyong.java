package com.feifandaiyu.feifan.bean;

/**
 * Created by houdaichang on 2018/1/16.
 */

public class Fanyong {

    /**
     * list : {"loanAmount":"100000.00","rebate":"2.0%","username":"ceshi","bankcode":"6212263500001388244","money":200000,"userId":"1504","carId":"591","dealerId":"38"}
     * code : 1
     */

    private ListBean list;
    private int code;
    private String msg;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ListBean {
        /**
         * loanAmount : 100000.00
         * rebate : 2.0%
         * username : ceshi
         * bankcode : 6212263500001388244
         * money : 200000
         * userId : 1504
         * carId : 591
         * dealerId : 38
         */

        private String loanAmount;
        private String rebate;
        private String username;
        private String bankcode;
        private String money;
        private String userId;
        private String carId;
        private String dealerId;
        private String loanId;
        private String gps;

        public String getGps() {
            return gps;
        }

        public void setGps(String gps) {
            this.gps = gps;
        }

        public String getLoanId() {
            return loanId;
        }

        public void setLoanId(String loanId) {
            this.loanId = loanId;
        }

        public String getLoanAmount() {
            return loanAmount;
        }

        public void setLoanAmount(String loanAmount) {
            this.loanAmount = loanAmount;
        }

        public String getRebate() {
            return rebate;
        }

        public void setRebate(String rebate) {
            this.rebate = rebate;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getBankcode() {
            return bankcode;
        }

        public void setBankcode(String bankcode) {
            this.bankcode = bankcode;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getDealerId() {
            return dealerId;
        }

        public void setDealerId(String dealerId) {
            this.dealerId = dealerId;
        }
    }
}
