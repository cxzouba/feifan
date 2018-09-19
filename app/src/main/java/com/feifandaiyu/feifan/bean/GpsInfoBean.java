package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2018/1/22.
 */

public class GpsInfoBean {


    /**
     * code : 1
     * list : [{"model":"GPS1","number":"GPS1-2","userName":"安方近"},{"model":"GPS1","number":"GPS1-1","userName":"杨贵君"}]
     */

    private int code;
    private List<ListBean> list;
    private String msg;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * model : GPS1
         * number : GPS1-2
         * userName : 安方近
         */

        private String model;
        private String number;
        private String userName;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
