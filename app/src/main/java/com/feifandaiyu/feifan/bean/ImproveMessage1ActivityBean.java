package com.feifandaiyu.feifan.bean;

/**
 * Created by davidzhao on 2017/7/4.
 */

public class ImproveMessage1ActivityBean {

    /**
     * code : 1
     * list : {"userId":"124"}
     */

    private int code;
    private ListBean list;
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


    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * userId : 124
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
