package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by davidzhao on 2017/6/14.
 */

public class CarDetailBean1 {

    /**
     * code : 1
     * list : [{"username":"Qqqqq","cardname":"身份证","cardnum":"4678","telphone":"13199809890","province":"黑龙江省","city":"哈尔滨市","areaname":"道里区","address":"黑龙江省哈尔滨市道里区","brand":"奥迪","car_series":"奥迪A6","car_size":"奥迪A6 2004款 2.4L 手动基本","engine_code":"67545","times":"2017-06-14","mileage":"7544","location":"Ggh","appenhance_pic":"","control_pic":"","chassis_pic":"","structure_pic":null,"valuation_price":null,"remarks":"Tyu","appraiser_remark":null}]
     */

    private int code;
    private List<ListBean> list;

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
         * username : Qqqqq
         * cardname : 身份证
         * cardnum : 4678
         * telphone : 13199809890
         * province : 黑龙江省
         * city : 哈尔滨市
         * areaname : 道里区
         * address : 黑龙江省哈尔滨市道里区
         * brand : 奥迪
         * car_series : 奥迪A6
         * car_size : 奥迪A6 2004款 2.4L 手动基本
         * engine_code : 67545
         * times : 2017-06-14
         * mileage : 7544
         * location : Ggh
         * appenhance_pic :
         * control_pic :
         * chassis_pic :
         * structure_pic : null
         * valuation_price : null
         * remarks : Tyu
         * appraiser_remark : null
         */

        private String username;
        private String cardname;
        private String cardnum;
        private String telphone;
        private String province;
        private String city;
        private String areaname;
        private String address;
        private String brand;
        private String car_series;
        private String car_size;
        private String engine_code;
        private String times;
        private String mileage;
        private String location;
        private String appenhance_pic;
        private String control_pic;
        private String chassis_pic;
        private Object structure_pic;
        private Object valuation_price;
        private String remarks;
        private Object appraiser_remark;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCardname() {
            return cardname;
        }

        public void setCardname(String cardname) {
            this.cardname = cardname;
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

        public String getAreaname() {
            return areaname;
        }

        public void setAreaname(String areaname) {
            this.areaname = areaname;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCar_series() {
            return car_series;
        }

        public void setCar_series(String car_series) {
            this.car_series = car_series;
        }

        public String getCar_size() {
            return car_size;
        }

        public void setCar_size(String car_size) {
            this.car_size = car_size;
        }

        public String getEngine_code() {
            return engine_code;
        }

        public void setEngine_code(String engine_code) {
            this.engine_code = engine_code;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getMileage() {
            return mileage;
        }

        public void setMileage(String mileage) {
            this.mileage = mileage;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getAppenhance_pic() {
            return appenhance_pic;
        }

        public void setAppenhance_pic(String appenhance_pic) {
            this.appenhance_pic = appenhance_pic;
        }

        public String getControl_pic() {
            return control_pic;
        }

        public void setControl_pic(String control_pic) {
            this.control_pic = control_pic;
        }

        public String getChassis_pic() {
            return chassis_pic;
        }

        public void setChassis_pic(String chassis_pic) {
            this.chassis_pic = chassis_pic;
        }

        public Object getStructure_pic() {
            return structure_pic;
        }

        public void setStructure_pic(Object structure_pic) {
            this.structure_pic = structure_pic;
        }

        public Object getValuation_price() {
            return valuation_price;
        }

        public void setValuation_price(Object valuation_price) {
            this.valuation_price = valuation_price;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public Object getAppraiser_remark() {
            return appraiser_remark;
        }

        public void setAppraiser_remark(Object appraiser_remark) {
            this.appraiser_remark = appraiser_remark;
        }
    }
}
