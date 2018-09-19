package com.feifandaiyu.feifan.bean;

/**
 * Created by houdaichang on 2017/6/2.
 */

public class ChuLiBean {

    /**
     * code : 1
     * list : {"times":"2017-06-01 15:06:26","remark":"ccc"}
     */

    private int code;
    private ListBean list;

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
         * times : 2017-06-01 15:06:26
         * remark : ccc
         */

        private String times;
        private String remark;

        public String getAuditors() {
            return auditors;
        }

        public void setAuditors(String auditors) {
            this.auditors = auditors;
        }

        private String auditors;

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
