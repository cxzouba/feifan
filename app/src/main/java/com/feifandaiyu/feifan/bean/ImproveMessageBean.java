package com.feifandaiyu.feifan.bean;

/**
 * Created by houdaichang on 2017/6/3.
 */

public class ImproveMessageBean {

    /**
     * code : 1
     * list : {"userId":"18"}
     */

    private int code;
    private ListBean list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * userId : 18
         */

        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
