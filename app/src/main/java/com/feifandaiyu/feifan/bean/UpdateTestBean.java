package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2017/8/29.
 */

public class UpdateTestBean {

    /**
     * code : 1
     * msg : 存在共借人征信照片
     * data : {"show":["http://oupz05j2b.bkt.clouddn.com/W020150703567080584862.jpg?e=1503980354&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:qtIjhSs2JP8_soQdxqQFP-U9ork="],"list":["W020150703567080584862.jpg"]}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> show;
        private List<String> list;

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
}
