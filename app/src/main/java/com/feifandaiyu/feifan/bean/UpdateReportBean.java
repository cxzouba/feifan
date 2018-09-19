package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2017/8/31.
 */

public class UpdateReportBean {

    /**
     * code : 1
     * msg : 已上传征信报告
     * credit : 0
     * show : ["http://oupz05j2b.bkt.clouddn.com/6b2b8f06-6ddf-417d-8dc6-4a9524b088c73294220170830.png?e=1504143301&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:OOQgjPF_URmy8tOTugOU4T338v8="]
     * list : ["6b2b8f06-6ddf-417d-8dc6-4a9524b088c73294220170830.png"]
     */

    private int code;
    private String msg;
    private int credit;
    public List<String> show;
    public List<String> list;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String gid;

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

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public List<String> getShow() {
        return show;
    }

    public void setShow(List<String> show) {
        this.show = show;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
