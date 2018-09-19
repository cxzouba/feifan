package com.feifandaiyu.feifan.bean;

/**
 * Created by davidzhao on 2017/6/20.
 */

public class CautionerMessageActivityBean {


    /**
     * code : 1
     * msg : 联系人信息添加成功
     */

    private int code;
    private String msg;
    /**
     * list : {"isfund":"非本地","guaranteeName":"共借人","telphone":"1812345678","cardType":"身份证","cardnum":"64765491964346","homeProvince":"黑龙江省","homeCity":"哈尔滨市","homeArea":"南岗区","homeAddress":"哈西","workName":"非凡","province":"黑龙江省","city":"哈尔滨市","area":"松北区","address":"松北","areaCode":"-1","workPhone":"888863363","relations":"父亲","flag":"0","id":"162"}
     */

    private ListBean list;

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

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * isfund : 非本地
         * guaranteeName : 共借人
         * telphone : 1812345678
         * cardType : 身份证
         * cardnum : 64765491964346
         * homeProvince : 黑龙江省
         * homeCity : 哈尔滨市
         * homeArea : 南岗区
         * homeAddress : 哈西
         * workName : 非凡
         * province : 黑龙江省
         * city : 哈尔滨市
         * area : 松北区
         * address : 松北
         * areaCode : -1
         * workPhone : 888863363
         * relations : 父亲
         * flag : 0
         * id : 162
         */

        private String isfund;
        private String guaranteeName;
        private String telphone;
        private String cardType;
        private String cardnum;
        private String homeProvince;
        private String homeCity;
        private String homeArea;
        private String homeAddress;
        private String workName;
        private String province;
        private String city;
        private String area;
        private String address;
        private String areaCode;
        private String workPhone;
        private String relations;
        private String flag;
        private String id;
        private String sex;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIsfund() {
            return isfund;
        }

        public void setIsfund(String isfund) {
            this.isfund = isfund;
        }

        public String getGuaranteeName() {
            return guaranteeName;
        }

        public void setGuaranteeName(String guaranteeName) {
            this.guaranteeName = guaranteeName;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
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

        public String getHomeProvince() {
            return homeProvince;
        }

        public void setHomeProvince(String homeProvince) {
            this.homeProvince = homeProvince;
        }

        public String getHomeCity() {
            return homeCity;
        }

        public void setHomeCity(String homeCity) {
            this.homeCity = homeCity;
        }

        public String getHomeArea() {
            return homeArea;
        }

        public void setHomeArea(String homeArea) {
            this.homeArea = homeArea;
        }

        public String getHomeAddress() {
            return homeAddress;
        }

        public void setHomeAddress(String homeAddress) {
            this.homeAddress = homeAddress;
        }

        public String getWorkName() {
            return workName;
        }

        public void setWorkName(String workName) {
            this.workName = workName;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getWorkPhone() {
            return workPhone;
        }

        public void setWorkPhone(String workPhone) {
            this.workPhone = workPhone;
        }

        public String getRelations() {
            return relations;
        }

        public void setRelations(String relations) {
            this.relations = relations;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
