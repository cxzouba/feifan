package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2018/2/2.
 */

public class ZhengxinBean {

    /**
     * code : 1
     * user : [{"userId":"1233","username":"董博","idCode":"232325199109111038","mobile":"13624615515"}]
     * contact : [{"username":"董董董","mobile":"13645678965","relation":"同事"},{"username":"董董","mobile":"13625874567","relation":"同事"}]
     */

    private int code;
    private List<UserBean> user;
    private List<ContactBean> contact;
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

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public List<ContactBean> getContact() {
        return contact;
    }

    public void setContact(List<ContactBean> contact) {
        this.contact = contact;
    }

    public static class UserBean {
        /**
         * userId : 1233
         * username : 董博
         * idCode : 232325199109111038
         * mobile : 13624615515
         */

        private String userId;
        private String username;
        private String idCode;
        private String mobile;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getIdCode() {
            return idCode;
        }

        public void setIdCode(String idCode) {
            this.idCode = idCode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    public static class ContactBean {
        /**
         * username : 董董董
         * mobile : 13645678965
         * relation : 同事
         */

        private String username;
        private String mobile;
        private String relation;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }
    }
}
