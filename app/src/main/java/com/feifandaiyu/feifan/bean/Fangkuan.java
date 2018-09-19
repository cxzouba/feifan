package com.feifandaiyu.feifan.bean;

/**
 * Created by houdaichang on 2018/1/12.
 */

public class Fangkuan {


    /**
     * list : {"username":"[董博]13624615515","carSize":"宝马7系 2016款 740Li 尊享型","loanPrice":"1000000.00","interestTotal":88100,"gps":2988,"loanId":"388","loanMoney":908912}
     * code : 1
     * rejects : {"title":"风控驳回","dismissal":"3","remainder":2,"description":"您申请的放款已被风控驳回3次，还可以申请2次","reason":"驳回"}
     */

    private ListBean list;
    private int code;
    private RejectsBean rejects;
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

    public RejectsBean getRejects() {
        return rejects;
    }

    public void setRejects(RejectsBean rejects) {
        this.rejects = rejects;
    }

    public static class ListBean {
        /**
         * username : [董博]13624615515
         * carSize : 宝马7系 2016款 740Li 尊享型
         * loanPrice : 1000000.00
         * interestTotal : 88100
         * gps : 2988
         * loanId : 388
         * loanMoney : 908912
         */

        private String username;
        private String carSize;
        private String loanPrice;
        private String interestTotal;
        private String gps;
        private String loanId;
        private String loanMoney;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCarSize() {
            return carSize;
        }

        public void setCarSize(String carSize) {
            this.carSize = carSize;
        }

        public String getLoanPrice() {
            return loanPrice;
        }

        public void setLoanPrice(String loanPrice) {
            this.loanPrice = loanPrice;
        }

        public String getInterestTotal() {
            return interestTotal;
        }

        public void setInterestTotal(String interestTotal) {
            this.interestTotal = interestTotal;
        }

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

        public String getLoanMoney() {
            return loanMoney;
        }

        public void setLoanMoney(String loanMoney) {
            this.loanMoney = loanMoney;
        }
    }

    public static class RejectsBean {
        /**
         * title : 风控驳回
         * dismissal : 3
         * remainder : 2
         * description : 您申请的放款已被风控驳回3次，还可以申请2次
         * reason : 驳回
         */

        private String title;
        private String dismissal;
        private String remainder;
        private String description;
        private String reason;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDismissal() {
            return dismissal;
        }

        public void setDismissal(String dismissal) {
            this.dismissal = dismissal;
        }

        public String getRemainder() {
            return remainder;
        }

        public void setRemainder(String remainder) {
            this.remainder = remainder;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
