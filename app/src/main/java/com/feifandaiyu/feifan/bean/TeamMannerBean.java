package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by davidzhao on 2017/6/2.
 */

public class TeamMannerBean {

    /**
     * code : 1
     * list : [{"admin_id":"4","role":"[\"3\"]","name":"admin","rolename":"风控\u2014内勤组"}]
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
         * admin_id : 4
         * admin_name : ["3"]
         * name : admin
         * rolename : 风控—内勤组
         */

        private String admin_id;
        private String admin_name;
        private String name;
        private String rolename;

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }

        public String getAdminName() {
            return admin_name;
        }

        public void setAdminName(String admin_name) {
            this.admin_name = admin_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRolename() {
            return rolename;
        }

        public void setRolename(String rolename) {
            this.rolename = rolename;
        }
    }
}
