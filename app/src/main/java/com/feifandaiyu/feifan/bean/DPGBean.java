package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by davidzhao on 2017/5/15.
 */

public class DPGBean {


    /**
     * list : [{"id":"138","valuation_price":null,"brand":"奥迪","car_series":"奥迪A3","car_size":"奥迪A3 2014款 Sportback 35 TFSI 自动进取型","username":"小形形填二手车","appenhance_pic":"image_20170626151742_779.jpg","flag":"未评估"}]
     * list_end : [{"id":"136","valuation_price":"22233.00","brand":"Arash","car_series":"宝马3系","car_size":"宝马3系 2016款 320i M运动型","username":"苏悠悠","order_code":"","appenhance_pic":"2f9e0423-8351-411f-b2c2-1f2f9f72fe0f1932620170626.png","flag":"已评估","report":"0","isover":0},{"id":"134","valuation_price":"100.00","brand":"奥迪","car_series":"奥迪A6L","car_size":"奥迪A6L 2016款 TFSI 运动型","username":"剑指江湖","order_code":"23","appenhance_pic":"45941c39-c0e8-4f28-a27b-68a61d67f5c35588820170623.png","flag":"已评估","report":"1","isover":1}]
     * code : 1
     */

    private int code;
    private List<ListBean> list;
    private List<ListEndBean> list_end;

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

    public List<ListEndBean> getList_end() {
        return list_end;
    }

    public void setList_end(List<ListEndBean> list_end) {
        this.list_end = list_end;
    }

    public static class ListBean {
        /**
         * id : 138
         * valuation_price : null
         * brand : 奥迪
         * car_series : 奥迪A3
         * car_size : 奥迪A3 2014款 Sportback 35 TFSI 自动进取型
         * username : 小形形填二手车
         * appenhance_pic : image_20170626151742_779.jpg
         * flag : 未评估
         */

        private String carId;
        private Object valuation_price;
        private String brand;
        private String car_series;
        private String car_size;
        private String username;
        private String appenhance_pic;
        private String flag;

        public String getLicenseNum() {
            return licenseNum;
        }

        public void setLicenseNum(String licenseNum) {
            this.licenseNum = licenseNum;
        }

        private String licenseNum;

        public String getId() {
            return carId;
        }

        public void setId(String id) {
            this.carId = id;
        }

        public Object getValuation_price() {
            return valuation_price;
        }

        public void setValuation_price(Object valuation_price) {
            this.valuation_price = valuation_price;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAppenhance_pic() {
            return appenhance_pic;
        }

        public void setAppenhance_pic(String appenhance_pic) {
            this.appenhance_pic = appenhance_pic;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }

    public static class ListEndBean {
        /**
         * id : 136
         * valuation_price : 22233.00
         * brand : Arash
         * car_series : 宝马3系
         * car_size : 宝马3系 2016款 320i M运动型
         * username : 苏悠悠
         * order_code :
         * appenhance_pic : 2f9e0423-8351-411f-b2c2-1f2f9f72fe0f1932620170626.png
         * flag : 已评估
         * report : 0
         * isover : 0
         */

        private String carId;
        private String valuation_price;
        private String brand;
        private String car_series;
        private String car_size;
        private String username;
        private String order_code;
        private String appenhance_pic;
        private String flag;
        private String report;
        private String licenseNum;
        private int isover;
        private String pid;//评估报告，无评估报告时：无报告，有评估报告时：评估报告id
        private String apic;//评估师签名图片地址，无签名时：未签名，已完成签名时：签名图片地址
        private String userId;//车辆对应客户id 无客户时，值为-999，此时不应填写评估报告，应提示，请等待业务员完善客户信息

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getLicenseNum() {
            return licenseNum;
        }

        public void setLicenseNum(String licenseNum) {
            this.licenseNum = licenseNum;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getApic() {
            return apic;
        }

        public void setApic(String apic) {
            this.apic = apic;
        }

        public String getId() {
            return carId;
        }

        public void setId(String id) {
            this.carId = id;
        }

        public String getValuation_price() {
            return valuation_price;
        }

        public void setValuation_price(String valuation_price) {
            this.valuation_price = valuation_price;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getAppenhance_pic() {
            return appenhance_pic;
        }

        public void setAppenhance_pic(String appenhance_pic) {
            this.appenhance_pic = appenhance_pic;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getReport() {
            return report;
        }

        public void setReport(String report) {
            this.report = report;
        }

        public int getIsover() {
            return isover;
        }

        public void setIsover(int isover) {
            this.isover = isover;
        }
    }
}
