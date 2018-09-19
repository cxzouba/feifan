package com.feifandaiyu.feifan.bean;

/**
 * Created by davidzhao on 2017/5/31.
 */

public class CreditActivityBean {

    /**
     * code : 1
     * list : {"loanType":"2","credit":"0","userName":"123","cardType":"身份证","cardNum":"232326198009061056","telphone":"13625465342","province":"河北省","city":"邯郸市","area":"邯山区","address":"1"}
     */

    private int code;
    private String msg;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private ListBean list;

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
         * loanType : 2
         * credit : 0
         * userName : 123
         * cardType : 身份证
         * cardNum : 232326198009061056
         * telphone : 13625465342
         * province : 河北省
         * city : 邯郸市
         * area : 邯山区
         * address : 1
         */

        private String loanType;
        private String credit;
        private String userName;
        private String cardType;
        private String cardNum;
        private String telphone;
        private String province;
        private String city;
        private String area;
        private String address;
        private String userId;
        private String cardAddress;

        public String getCardAddress() {
            return cardAddress;
        }

        public void setCardAddress(String cardAddress) {
            this.cardAddress = cardAddress;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getLoanType() {
            return loanType;
        }

        public void setLoanType(String loanType) {
            this.loanType = loanType;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
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

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
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
    }
}
