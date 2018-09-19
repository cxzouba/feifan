package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2017/6/2.
 */

public class CarLoanBean {


    /**
     * code : 1
     * list : [{"id":"597","carId":"597","car_size":"宝马5系 2014款 528Li xDrive豪华设计套装","username":"未选定","flag":"-999","valuation_price":null,"appenhance_pic":null,"userId":"-999","vprice":"-999","estate":"0","carpicFlag":"1","costFlag":"-999","telephone":"****","price":"-999","orf":0,"rf":0,"reconsider":1},{"id":"596","carId":"596","car_size":"宝马5系 2014款 525Li 豪华设计套装","username":"未选定","flag":"-999","valuation_price":null,"appenhance_pic":null,"userId":"-999","vprice":"-999","estate":"0","carpicFlag":"1","costFlag":"-999","telephone":"****","price":"-999","orf":0,"rf":0,"reconsider":1},{"id":"595","carId":"595","car_size":"奥迪A6L 2016款 TFSI 运动型","username":"未选定","flag":"-999","valuation_price":null,"appenhance_pic":"http://oupz05j2b.bkt.clouddn.com/fcb59c5a-20e5-467c-a010-2ed0c8b6f7322455820171211.png?e=1513324272&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:WSItIuKTA6wBQu-iX_7r3pwENm0=","userId":"-999","vprice":"-999","estate":"1","carpicFlag":"1","costFlag":"-999","telephone":"****","price":"-999","orf":1,"rf":0,"reconsider":1}]
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
         * id : 597
         * carId : 597
         * car_size : 宝马5系 2014款 528Li xDrive豪华设计套装
         * username : 未选定
         * flag : -999
         * valuation_price : null
         * appenhance_pic : null
         * userId : -999
         * vprice : -999
         * estate : 0
         * carpicFlag : 1
         * costFlag : -999
         * telephone : ****
         * price : -999
         * orf : 0
         * rf : 0
         * reconsider : 1
         */

        private String id;
        private String carId;
        private String car_size;
        private String username;
        private String flag;
        private String valuation_price;
        private String appenhance_pic;
        private String userId;
        private String vprice;
        private String estate;
        private String carpicFlag;
        private String costFlag;
        private String telephone;
        private String price;//(贷款额，未填写费用时，贷款额默认值：-999)
        private int orf;//（评估师驳回次数)
        private int rf;//(风控驳回次数)
        private String reconsider;
        private String gps;
        private String isnew;//1-新车，2-二手车
        private String dismissal;//(复议驳回原因)
        private String licenseNum;//车牌号
        private String mid;//抵押信息id
        private String contract;//(合同id，无合同时：-999)
        private String visits_lat;
        private String visits_lng;
        private String pay;//(申请放款，1-不可申请，2-可申请)
        private String dealer;//(申请经销商返佣 1-不可申请，2-可申请)
        private String reasons;//驳回原因
        private String rentType;//驳回原因
        private String ismarry;
        private String isguarantee;

        public String getIsmarry() {
            return ismarry;
        }

        public void setIsmarry(String ismarry) {
            this.ismarry = ismarry;
        }

        public String getIsguarantee() {
            return isguarantee;
        }

        public void setIsguarantee(String isguarantee) {
            this.isguarantee = isguarantee;
        }

        public String getRentType() {
            return rentType;
        }

        public void setRentType(String rentType) {
            this.rentType = rentType;
        }

        public String getReasons() {
            return reasons;
        }

        public void setReasons(String reasons) {
            this.reasons = reasons;
        }

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }

        public String getDealer() {
            return dealer;
        }

        public void setDealer(String dealer) {
            this.dealer = dealer;
        }

        public String getVisits_lat() {
            return visits_lat;
        }

        public void setVisits_lat(String visits_lat) {
            this.visits_lat = visits_lat;
        }

        public String getVisits_lng() {
            return visits_lng;
        }

        public void setVisits_lng(String visits_lng) {
            this.visits_lng = visits_lng;
        }

        public String getContract() {
            return contract;
        }

        public void setContract(String contract) {
            this.contract = contract;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getDismissal() {
            return dismissal;
        }

        public void setDismissal(String dismissal) {
            this.dismissal = dismissal;
        }

        public String getLicenseNum() {
            return licenseNum;
        }

        public void setLicenseNum(String licenseNum) {
            this.licenseNum = licenseNum;
        }

        public String getGps() {
            return gps;
        }

        public void setGps(String gps) {
            this.gps = gps;
        }

        public String getIsnew() {
            return isnew;
        }

        public void setIsnew(String isnew) {
            this.isnew = isnew;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getCar_size() {
            return car_size;
        }

        public void setCar_size(String car_size) {
            this.car_size = car_size;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public Object getValuation_price() {
            return valuation_price;
        }

        public void setValuation_price(String valuation_price) {
            this.valuation_price = valuation_price;
        }

        public String getAppenhance_pic() {
            return appenhance_pic;
        }

        public void setAppenhance_pic(String appenhance_pic) {
            this.appenhance_pic = appenhance_pic;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getVprice() {
            return vprice;
        }

        public void setVprice(String vprice) {
            this.vprice = vprice;
        }

        public String getEstate() {
            return estate;
        }

        public void setEstate(String estate) {
            this.estate = estate;
        }

        public String getCarpicFlag() {
            return carpicFlag;
        }

        public void setCarpicFlag(String carpicFlag) {
            this.carpicFlag = carpicFlag;
        }

        public String getCostFlag() {
            return costFlag;
        }

        public void setCostFlag(String costFlag) {
            this.costFlag = costFlag;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getOrf() {
            return orf;
        }

        public void setOrf(int orf) {
            this.orf = orf;
        }

        public int getRf() {
            return rf;
        }

        public void setRf(int rf) {
            this.rf = rf;
        }

        public String getReconsider() {
            return reconsider;
        }

        public void setReconsider(String reconsider) {
            this.reconsider = reconsider;
        }
    }
}
