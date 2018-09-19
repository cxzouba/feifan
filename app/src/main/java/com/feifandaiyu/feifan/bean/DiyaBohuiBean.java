package com.feifandaiyu.feifan.bean;

/**
 * Created by houdaichang on 2018/1/17.
 */

public class DiyaBohuiBean {

    /**
     * code : 1
     * rejects : {"title":"未驳回","dismissal":"0","remainder":"5","description":"未被驳回，请提交抵押信息","reason":"无"}
     */

    private int code;
    private RejectsBean rejects;
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

    public RejectsBean getRejects() {
        return rejects;
    }

    public void setRejects(RejectsBean rejects) {
        this.rejects = rejects;
    }

    public static class RejectsBean {
        /**
         * title : 未驳回
         * dismissal : 0
         * remainder : 5
         * description : 未被驳回，请提交抵押信息
         * reason : 无
         */

        private String title;
        private String dismissal;
        private String remainder;
        private String description;
        private String reason;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDismissal() {
            return dismissal;
        }

        public void setDismissal(String dismissal) {
            this.dismissal = dismissal;
        }

        public String getRemainder() {
            return remainder;
        }

        public void setRemainder(String remainder) {
            this.remainder = remainder;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
