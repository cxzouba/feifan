package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2018/1/10.
 */

public class ContractBean {

    /**
     * contractCode : qq1234567890
     * signedPlace : 中国黑龙江省哈尔滨市南岗区中兴大道
     * code : 1
     * interview : {"show":["http://oupz05j2b.bkt.clouddn.com/36871c24-7f8c-42f9-a4ba-482a657024a09199020180110.png?e=1515578371&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:xWoXodIcRqqhRNyzkfF3goDzxJ4="],"list":["36871c24-7f8c-42f9-a4ba-482a657024a09199020180110.png"]}
     * hold : {"show":["http://oupz05j2b.bkt.clouddn.com/87cadf6f-9c42-49ad-ab96-e59e5ad9e20f2540420180110.png?e=1515578371&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:ZYqfNHKFYtQP3IfUzBs295V4vo0="],"list":["87cadf6f-9c42-49ad-ab96-e59e5ad9e20f2540420180110.png"]}
     * contractPic : {"show":["http://oupz05j2b.bkt.clouddn.com/78d9326b-d2fa-4989-864f-e9bf9fd1df604572820180110.png?e=1515578371&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:mv_TmluYTvAshkAOPjjLlRNMXy0="],"list":["78d9326b-d2fa-4989-864f-e9bf9fd1df604572820180110.png"]}
     */

    private String contractCode;
    private String signedPlace;
    private String tlContractCode;
    private String msg;
    private int code;
    private InterviewBean interview;
    private HoldBean hold;
    private ContractPicBean contractPic;

    public String getTlContractCode() {
        return tlContractCode;
    }

    public void setTlContractCode(String tlContractCode) {
        this.tlContractCode = tlContractCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getSignedPlace() {
        return signedPlace;
    }

    public void setSignedPlace(String signedPlace) {
        this.signedPlace = signedPlace;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InterviewBean getInterview() {
        return interview;
    }

    public void setInterview(InterviewBean interview) {
        this.interview = interview;
    }

    public HoldBean getHold() {
        return hold;
    }

    public void setHold(HoldBean hold) {
        this.hold = hold;
    }

    public ContractPicBean getContractPic() {
        return contractPic;
    }

    public void setContractPic(ContractPicBean contractPic) {
        this.contractPic = contractPic;
    }

    public static class InterviewBean {
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

    public static class HoldBean {
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

    public static class ContractPicBean {
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
