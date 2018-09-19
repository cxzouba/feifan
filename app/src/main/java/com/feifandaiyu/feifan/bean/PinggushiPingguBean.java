package com.feifandaiyu.feifan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by houdaichang on 2017/6/3.
 */

public class PinggushiPingguBean implements Serializable{

    /**
     * code : 1
     * list : [{"username":"z","cardname":"驾驶证","cardnum":"222332199511096219","telphone":"13359616619","province":"黑龙江省","city":"哈尔滨市","areaname":"道里区","address":"nullnullnull","brand":"宝马","car_series":"宝马3系","car_size":"310","engine_code":"1111111111111","times":"2017-06-02","mileage":"1000","location":"1234312","appenhance_pic":["http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,车身外观","http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,车身外观"],"control_pic":["http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,中控内饰","http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,中控内饰"],"chassis_pic":["http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,附加图片","http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,附加图片"],"structure_pic":["http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg","http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg"],"valuation_price":"0.00","remarks":"备注","appraiser_remark":"11111asfdfasdf"}]
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

    public static class ListBean implements Serializable {
        /**
         * username : z
         * cardname : 驾驶证
         * cardnum : 222332199511096219
         * telphone : 13359616619
         * province : 黑龙江省
         * city : 哈尔滨市
         * areaname : 道里区
         * address : nullnullnull
         * brand : 宝马
         * car_series : 宝马3系
         * car_size : 310
         * engine_code : 1111111111111
         * times : 2017-06-02
         * mileage : 1000
         * location : 1234312
         * appenhance_pic : ["http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,车身外观","http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,车身外观"]
         * control_pic : ["http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,中控内饰","http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,中控内饰"]
         * chassis_pic : ["http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,附加图片","http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,附加图片"]
         * structure_pic : ["http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg","http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg,http://pic.58pic.com/58pic/14/27/45/71r58PICmDM_1024.jpg"]
         * valuation_price : 0.00
         * remarks : 备注
         * appraiser_remark : 11111asfdfasdf
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
        private String valuation_price;
        private String remarks;
        private String appraiser_remark;
        private List<String> appenhance_pic;
        private List<String> control_pic;
        private List<String> chassis_pic;
        private List<String> structure_pic;

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

        public String getValuation_price() {
            return valuation_price;
        }

        public void setValuation_price(String valuation_price) {
            this.valuation_price = valuation_price;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getAppraiser_remark() {
            return appraiser_remark;
        }

        public void setAppraiser_remark(String appraiser_remark) {
            this.appraiser_remark = appraiser_remark;
        }

        public List<String> getAppenhance_pic() {
            return appenhance_pic;
        }

        public void setAppenhance_pic(List<String> appenhance_pic) {
            this.appenhance_pic = appenhance_pic;
        }

        public List<String> getControl_pic() {
            return control_pic;
        }

        public void setControl_pic(List<String> control_pic) {
            this.control_pic = control_pic;
        }

        public List<String> getChassis_pic() {
            return chassis_pic;
        }

        public void setChassis_pic(List<String> chassis_pic) {
            this.chassis_pic = chassis_pic;
        }

        public List<String> getStructure_pic() {
            return structure_pic;
        }

        public void setStructure_pic(List<String> structure_pic) {
            this.structure_pic = structure_pic;
        }
    }
}
