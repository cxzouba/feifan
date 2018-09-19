package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2017/7/25.
 */

public class CostBean {

    /**
     * code : 1
     * periods24 : [{"periods":"24","interest":"0.143","bank_interest":"0.0549"},{"periods":"24","interest":"0.24","bank_interest":"0.21"}]
     * periods12 : [{"periods":"12","interest":"0.54","bank_interest":"0.25"},{"periods":"12","interest":"0.18","bank_interest":"0.16"}]
     * periods36 : [{"periods":"36","interest":"0.36","bank_interest":"0.33"},{"periods":"36","interest":"0.63","bank_interest":"0.55"}]
     */

    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;
    private String rebate;//车商返点
    private String gps;//车商gps返佣金额
    private String sprice;//发票价
    private String vprice;//（仅二手车）

    public String getSprice() {
        return sprice;
    }

    public void setSprice(String sprice) {
        this.sprice = sprice;
    }

    public String getVprice() {
        return vprice;
    }

    public void setVprice(String vprice) {
        this.vprice = vprice;
    }

    public String getRebate() {
        return rebate;
    }

    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    private List<Periods24Bean> periods24;
    private List<Periods12Bean> periods12;
    private List<Periods36Bean> periods36;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Periods24Bean> getPeriods24() {
        return periods24;
    }

    public void setPeriods24(List<Periods24Bean> periods24) {
        this.periods24 = periods24;
    }

    public List<Periods12Bean> getPeriods12() {
        return periods12;
    }

    public void setPeriods12(List<Periods12Bean> periods12) {
        this.periods12 = periods12;
    }

    public List<Periods36Bean> getPeriods36() {
        return periods36;
    }

    public void setPeriods36(List<Periods36Bean> periods36) {
        this.periods36 = periods36;
    }

    public static class Periods24Bean {
        /**
         * periods : 24
         * interest : 0.143
         * bank_interest : 0.0549
         */

        private String periods;
        private String interest;
        private String bank_interest;

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

        public String getBank_interest() {
            return bank_interest;
        }

        public void setBank_interest(String bank_interest) {
            this.bank_interest = bank_interest;
        }
    }

    public static class Periods12Bean {
        /**
         * periods : 12
         * interest : 0.54
         * bank_interest : 0.25
         */

        private String periods;
        private String interest;
        private String bank_interest;

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

        public String getBank_interest() {
            return bank_interest;
        }

        public void setBank_interest(String bank_interest) {
            this.bank_interest = bank_interest;
        }
    }

    public static class Periods36Bean {
        /**
         * periods : 36
         * interest : 0.36
         * bank_interest : 0.33
         */

        private String periods;
        private String interest;
        private String bank_interest;

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

        public String getBank_interest() {
            return bank_interest;
        }

        public void setBank_interest(String bank_interest) {
            this.bank_interest = bank_interest;
        }
    }
}
