package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by davidzhao on 2017/6/2.
 */

public class GuoChengBean {

    /**
     * code : 1
     * list : [{"times":"2017-05-22 14:05:47","name":"风控\u2014内勤组","id":"104"},{"times":"2017-06-01 09:06:09","name":"风控\u2014合同专员","id":"106"},{"times":"2017-06-01 15:06:26","name":"销售组","id":"110"}]
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
         * times : 2017-05-22 14:05:47
         * name : 风控—内勤组
         * id : 104
         */

        private String times;
        private String name;
        private String id;

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}