package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2018/1/29.
 */

public class ContractInfoBean {

    /**
     * code : 1
     * list : [{"contractCode":"FFC20180123587059","userName":"未选定","flag":"已派发"}]
     */

    private int code;
    private List<ListBean> list;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * contractCode : FFC20180123587059
         * userName : 未选定
         * flag : 已派发
         */

        private String contractCode;
        private String userName;
        private String flag;

        public String getContractCode() {
            return contractCode;
        }

        public void setContractCode(String contractCode) {
            this.contractCode = contractCode;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
}
