package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2017/7/26.
 */

public class CarCostBean {


    /**
     * sprice : 550000.00
     * vprice : 550000
     * rebate : 0.5%
     * gps : 1000
     * code : 1
     * periods3 : [{"periods":"3","interest":"0.0099","bank_interest":"0.008"}]
     * periods12 : [{"periods":"12","interest":"0.0088","bank_interest":"0.00645"}]
     * periods24 : [{"periods":"24","interest":"0.0128","bank_interest":"0.00635"},{"periods":"24","interest":"0.0118","bank_interest":"0.00635"}]
     * periods36 : [{"periods":"36","interest":"0.0128","bank_interest":"0.0064"},{"periods":"36","interest":"0.0118","bank_interest":"0.0064"}]
     */

    private String sprice;
    private String vprice;
    private String rebate;
    private String gps;
    private String msg;
    private int code;
    private List<Periods3Bean> periods3;
    private List<Periods12Bean> periods12;
    private List<Periods24Bean> periods24;
    private List<Periods36Bean> periods36;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Periods3Bean> getPeriods3() {
        return periods3;
    }

    public void setPeriods3(List<Periods3Bean> periods3) {
        this.periods3 = periods3;
    }

    public List<Periods12Bean> getPeriods12() {
        return periods12;
    }

    public void setPeriods12(List<Periods12Bean> periods12) {
        this.periods12 = periods12;
    }

    public List<Periods24Bean> getPeriods24() {
        return periods24;
    }

    public void setPeriods24(List<Periods24Bean> periods24) {
        this.periods24 = periods24;
    }

    public List<Periods36Bean> getPeriods36() {
        return periods36;
    }

    public void setPeriods36(List<Periods36Bean> periods36) {
        this.periods36 = periods36;
    }

    public static class Periods3Bean {
        /**
         * periods : 3
         * interest : 0.0099
         * bank_interest : 0.008
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
         * interest : 0.0088
         * bank_interest : 0.00645
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

    public static class Periods24Bean {
        /**
         * periods : 24
         * interest : 0.0128
         * bank_interest : 0.00635
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
         * interest : 0.0128
         * bank_interest : 0.0064
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
