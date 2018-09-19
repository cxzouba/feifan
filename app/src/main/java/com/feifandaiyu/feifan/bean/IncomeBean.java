package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2017/6/22.
 */

public class IncomeBean {

    /**
     * code : 1
     * list : [{"wagesName":"1000元/月以下"},{"wagesName":"1001-2000元/月"},{"wagesName":"2001-4000元/月"},{"wagesName":"4001-6000元/月"},{"wagesName":"6001-8000元/月"},{"wagesName":"8001-10000元/月"},{"wagesName":"10001-15000元/月"},{"wagesName":"15001-20000元/月"},{"wagesName":"20001-30000元/月"},{"wagesName":"30001-50000元/月"},{"wagesName":"50000元/月以上"}]
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
         * wagesName : 1000元/月以下
         */

        private String wagesName;

        public String getWagesName() {
            return wagesName;
        }

        public void setWagesName(String wagesName) {
            this.wagesName = wagesName;
        }
    }
}
