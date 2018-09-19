package com.feifandaiyu.feifan.bean;

/**
 * Created by houdaichang on 2017/6/1.
 */

public class ChangePasswordBean {

    /**
     * code : 0
     * msg : 密码与之前密码一致
     */

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
