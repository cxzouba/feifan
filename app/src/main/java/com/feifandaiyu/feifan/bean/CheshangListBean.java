package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2017/12/4.
 */

public class CheshangListBean {

    /**
     * code : 1
     * list : [{"Id":"5","username":"haha","telphone":"13244456768","status":0},{"Id":"6","username":"432345","telphone":"13544467678","status":0},{"Id":"7","username":"gdg","telphone":"13422234545","status":0}]
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
         * Id : 5
         * username : haha
         * telphone : 13244456768
         * status : 0 无照片
         */

        private String Id;
        private String username;
        private String telphone;

        public String getRebate() {
            return rebate;
        }

        public void setRebate(String rebate) {
            this.rebate = rebate;
        }

        private String rebate;
        private int status;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
