package com.feifandaiyu.feifan.bean;

/**
 * Created by houdaichang on 2017/7/26.
 */

public class PostCostBean {

    /**
     * code : 1
     */

    private int code;
    public String msg;

    /**
     * list : {"fapiao":"200000","purchaseTax":"1000","carLoan":1,"rentType":"直租","invoicePrice":"100000.00","gpsCost":"1988","commercial":"1000","compulsory":"1000","periods":"12","interest":"0.143","loanPrice":"114237","remarks":"斗"}
     */

    private ListBean list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * fapiao : 200000
         * purchaseTax : 1000
         * carLoan : 1
         * rentType : 直租
         * invoicePrice : 100000.00
         * gpsCost : 1988
         * commercial : 1000
         * compulsory : 1000
         * periods : 12
         * interest : 0.143
         * loanPrice : 114237
         * remarks : 斗
         */

        private String fapiao;
        private String purchaseTax;
        private String carLoan;
        private String rentType;
        private String invoicePrice;
        private String gpsCost;
        private String commercial;
        private String compulsory;
        private String periods;
        private String interest;
        private String loanPrice;
        private String emtotal;
        private String interest_total;

        public String getGps() {
            return gps;
        }

        public void setGps(String gps) {
            this.gps = gps;
        }

        private String gps;//gps返佣(无：0)

        public String getEmtotal() {
            return emtotal;
        }

        public void setEmtotal(String emtotal) {
            this.emtotal = emtotal;
        }

        public String getInterest_total() {
            return interest_total;
        }

        public void setInterest_total(String interest_total) {
            this.interest_total = interest_total;
        }

        public String getDealer() {
            return dealer;
        }

        public void setDealer(String dealer) {
            this.dealer = dealer;
        }

        private String dealer;

        public String getBankInterest() {
            return bankInterest;
        }

        public void setBankInterest(String bankInterest) {
            this.bankInterest = bankInterest;
        }

        private String remarks;
        private String bankInterest;

        public String getFapiao() {
            return fapiao;
        }

        public void setFapiao(String fapiao) {
            this.fapiao = fapiao;
        }

        public String getPurchaseTax() {
            return purchaseTax;
        }

        public void setPurchaseTax(String purchaseTax) {
            this.purchaseTax = purchaseTax;
        }

        public String getCarLoan() {
            return carLoan;
        }

        public void setCarLoan(String carLoan) {
            this.carLoan = carLoan;
        }

        public String getRentType() {
            return rentType;
        }

        public void setRentType(String rentType) {
            this.rentType = rentType;
        }

        public String getInvoicePrice() {
            return invoicePrice;
        }

        public void setInvoicePrice(String invoicePrice) {
            this.invoicePrice = invoicePrice;
        }

        public String getGpsCost() {
            return gpsCost;
        }

        public void setGpsCost(String gpsCost) {
            this.gpsCost = gpsCost;
        }

        public String getCommercial() {
            return commercial;
        }

        public void setCommercial(String commercial) {
            this.commercial = commercial;
        }

        public String getCompulsory() {
            return compulsory;
        }

        public void setCompulsory(String compulsory) {
            this.compulsory = compulsory;
        }

        public String getPeriods() {
            return periods;
        }

        public void setPeriods(String periods) {
            this.periods = periods;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getLoanPrice() {
            return loanPrice;
        }

        public void setLoanPrice(String loanPrice) {
            this.loanPrice = loanPrice;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }
}
