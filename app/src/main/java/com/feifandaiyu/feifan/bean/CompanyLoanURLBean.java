package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2017/6/2.
 */

public class CompanyLoanURLBean {

    /**
     * code : 1
     * list : [{"id":"13","company_name":"特土科技","flag":"1","telphone":"13888888888"},{"id":"14","company_name":"计算机设计","flag":"1","telphone":"18900001111"},{"id":"15","company_name":"觉得就像爸爸","flag":"4","telphone":"18900003333"}]
     */

    private int code;
    private List<ListBean> list;
    private int role;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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
         * id : 13
         * company_name : 特土科技
         * flag : 1
         * telphone : 13888888888
         */

        private String id;
        private String company_name;
        private String flag;
        private String telphone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }
    }
}
