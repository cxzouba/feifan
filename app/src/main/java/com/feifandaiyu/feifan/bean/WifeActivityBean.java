package com.feifandaiyu.feifan.bean;

/**
 * Created by davidzhao on 2017/6/21.
 */

public class WifeActivityBean {
    private int code;
    private String msg;
    /**
     * list : {"isfund":"本地","userName":"拍摄了","cardType":"身份证","cardnum":"377282228282","telphone":"18646411436","birthday":"2017-08-17","monthlyIncome":"1000元/月以下","defray":"7383","corporateName":"第四","workProvince":"河北省","workCity":"邯郸市","workArea":"邯山区","workAddress":"邯郸","areaCode":"-1","corporateMobile":"88796441","inductionTime":"2017-08-17","unitProperty":"个体","remarks":"年薪","id":"135","flag":"0"}
     */

    private ListBean list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
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
         * isfund : 本地
         * userName : 拍摄了
         * cardType : 身份证
         * cardnum : 377282228282
         * telphone : 18646411436
         * birthday : 2017-08-17
         * monthlyIncome : 1000元/月以下
         * defray : 7383
         * corporateName : 第四
         * workProvince : 河北省
         * workCity : 邯郸市
         * workArea : 邯山区
         * workAddress : 邯郸
         * areaCode : -1
         * corporateMobile : 88796441
         * inductionTime : 2017-08-17
         * unitProperty : 个体
         * remarks : 年薪
         * id : 135
         * flag : 0
         */

        private String isfund;
        private String userName;
        private String cardType;
        private String cardnum;
        private String telphone;
        private String birthday;
        private String monthlyIncome;
        private String defray;
        private String corporateName;
        private String workProvince;
        private String workCity;
        private String workArea;
        private String workAddress;
        private String areaCode;
        private String corporateMobile;
        private String inductionTime;
        private String unitProperty;
        private String remarks;
        private String id;
        private String flag;

        public String getIsfund() {
            return isfund;
        }

        public void setIsfund(String isfund) {
            this.isfund = isfund;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCardnum() {
            return cardnum;
        }

        public void setCardnum(String cardnum) {
            this.cardnum = cardnum;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getMonthlyIncome() {
            return monthlyIncome;
        }

        public void setMonthlyIncome(String monthlyIncome) {
            this.monthlyIncome = monthlyIncome;
        }

        public String getDefray() {
            return defray;
        }

        public void setDefray(String defray) {
            this.defray = defray;
        }

        public String getCorporateName() {
            return corporateName;
        }

        public void setCorporateName(String corporateName) {
            this.corporateName = corporateName;
        }

        public String getWorkProvince() {
            return workProvince;
        }

        public void setWorkProvince(String workProvince) {
            this.workProvince = workProvince;
        }

        public String getWorkCity() {
            return workCity;
        }

        public void setWorkCity(String workCity) {
            this.workCity = workCity;
        }

        public String getWorkArea() {
            return workArea;
        }

        public void setWorkArea(String workArea) {
            this.workArea = workArea;
        }

        public String getWorkAddress() {
            return workAddress;
        }

        public void setWorkAddress(String workAddress) {
            this.workAddress = workAddress;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getCorporateMobile() {
            return corporateMobile;
        }

        public void setCorporateMobile(String corporateMobile) {
            this.corporateMobile = corporateMobile;
        }

        public String getInductionTime() {
            return inductionTime;
        }

        public void setInductionTime(String inductionTime) {
            this.inductionTime = inductionTime;
        }

        public String getUnitProperty() {
            return unitProperty;
        }

        public void setUnitProperty(String unitProperty) {
            this.unitProperty = unitProperty;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
}
