package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2018/1/18.
 */

public class ContractListBean {

    /**
     * code : 1
     * list : [{"id":"4","times":"2018-01-17","number":"1","contractType":"个人","distribute":1,"sign":0}]
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
         * id : 4
         * times : 2018-01-17
         * number : 1
         * contractType : 个人
         * distribute : 1
         * sign : 0
         */

        private String id;
        private String times;
        private String number;
        private String contractType;
        private String distribute;
        private String sign;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getContractType() {
            return contractType;
        }

        public void setContractType(String contractType) {
            this.contractType = contractType;
        }

        public String getDistribute() {
            return distribute;
        }

        public void setDistribute(String distribute) {
            this.distribute = distribute;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
