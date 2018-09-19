package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2018/1/18.
 */

public class GpsListBean {

    /**
     * code : 1
     * list : [{"number":"2","createtime":"2018-01-31","id":"51","state":"申请成功","distribute":2,"sign":1},{"number":"2","createtime":"2018-01-31","id":"50","state":"申请成功","distribute":2,"sign":0},{"number":"4","createtime":"2018-01-22","id":"49","state":"申请成功","distribute":4,"sign":0},{"number":"1","createtime":"2018-01-22","id":"48","state":"申请成功","distribute":0,"sign":0},{"number":"3","createtime":"2018-01-22","id":"47","state":"申请中","distribute":0,"sign":0},{"number":"1","createtime":"2018-01-22","id":"46","state":"申请中","distribute":0,"sign":0},{"number":"3","createtime":"2018-01-22","id":"45","state":"申请成功","distribute":3,"sign":2},{"number":"1","createtime":"2018-01-22","id":"33","state":"申请中","distribute":0,"sign":0},{"number":"1","createtime":"2018-01-22","id":"32","state":"申请中","distribute":0,"sign":0},{"number":"1","createtime":"2018-01-22","id":"31","state":"已拒绝","distribute":0,"sign":0},{"number":"1","createtime":"2018-01-22","id":"30","state":"已拒绝","distribute":0,"sign":0},{"number":"1","createtime":"2018-01-22","id":"29","state":"申请中","distribute":0,"sign":0},{"number":"1","createtime":"2018-01-21","id":"25","state":"申请成功","distribute":0,"sign":0},{"number":"1","createtime":"2018-01-21","id":"24","state":"申请成功","distribute":0,"sign":0},{"number":"1","createtime":"2018-01-21","id":"23","state":"申请成功","distribute":0,"sign":0},{"number":"1","createtime":"2018-01-21","id":"22","state":"申请中","distribute":0,"sign":0},{"number":"2","createtime":"2018-01-20","id":"21","state":"申请成功","distribute":2,"sign":2},{"number":"0","createtime":"2018-01-20","id":"20","state":"已拒绝","distribute":0,"sign":0},{"number":"0","createtime":"2018-01-20","id":"19","state":"已拒绝","distribute":0,"sign":0},{"number":"0","createtime":"2018-01-20","id":"18","state":"已拒绝","distribute":0,"sign":0}]
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
         * number : 2
         * createtime : 2018-01-31
         * id : 51
         * state : 申请成功
         * distribute : 2
         * sign : 1
         */

        private String number;
        private String createtime;
        private String id;
        private String state;
        private int distribute;
        private int sign;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getDistribute() {
            return distribute;
        }

        public void setDistribute(int distribute) {
            this.distribute = distribute;
        }

        public int getSign() {
            return sign;
        }

        public void setSign(int sign) {
            this.sign = sign;
        }
    }
}
