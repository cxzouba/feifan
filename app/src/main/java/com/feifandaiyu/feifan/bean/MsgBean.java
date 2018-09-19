package com.feifandaiyu.feifan.bean;

/**
 * Created by houdaichang on 2017/10/16.
 */

public class MsgBean {

    /**
     * code : 1
     * msg : 修改成功
     */

    private int code;
    private String msg;
    private String token;

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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
