package com.feifandaiyu.feifan.bean;

/**
 * Created by davidzhao on 2017/5/23.
 */

public class UserBean {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * list : {"role":2,"saleID":5}
     */

    private String code;
    private ListBean list;

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * role : 2
         * saleID : 5
         */

        private int role;
        private int saleID;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        private String userName;
        private String city;

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public int getSaleID() {
            return saleID;
        }

        public void setSaleID(int saleID) {
            this.saleID = saleID;
        }
    }


}
