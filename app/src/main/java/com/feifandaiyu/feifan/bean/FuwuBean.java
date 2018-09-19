package com.feifandaiyu.feifan.bean;

/**
 * Created by houdaichang on 2018/2/2.
 */

public class FuwuBean {

    /**
     * success : true
     * data : {"type":"CONTROL","content":"输入动态密码","process_code":10002,"req_msg_tpl":{"type":"SUBMIT_CAPTCHA","method":"AUTO"},"finish":false}
     * code : 1
     * website : chinamobilehl
     * token : 2b20cd2f24ab499eb202d55168d48615
     * account : 13624615515
     * password : 199184
     * userId : 1233
     * process_code : 10002
     * msg : 输入动态密码
     */

    private boolean success;
    private DataBean data;
    private int code;
    private String website;
    private String token;
    private String account;
    private String password;
    private String userId;
    private int process_code;
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getProcess_code() {
        return process_code;
    }

    public void setProcess_code(int process_code) {
        this.process_code = process_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * type : CONTROL
         * content : 输入动态密码
         * process_code : 10002
         * req_msg_tpl : {"type":"SUBMIT_CAPTCHA","method":"AUTO"}
         * finish : false
         */

        private String type;
        private String content;
        private int process_code;
        private ReqMsgTplBean req_msg_tpl;
        private boolean finish;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getProcess_code() {
            return process_code;
        }

        public void setProcess_code(int process_code) {
            this.process_code = process_code;
        }

        public ReqMsgTplBean getReq_msg_tpl() {
            return req_msg_tpl;
        }

        public void setReq_msg_tpl(ReqMsgTplBean req_msg_tpl) {
            this.req_msg_tpl = req_msg_tpl;
        }

        public boolean isFinish() {
            return finish;
        }

        public void setFinish(boolean finish) {
            this.finish = finish;
        }

        public static class ReqMsgTplBean {
            /**
             * type : SUBMIT_CAPTCHA
             * method : AUTO
             */

            private String type;
            private String method;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getMethod() {
                return method;
            }

            public void setMethod(String method) {
                this.method = method;
            }
        }
    }
}
