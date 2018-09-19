package com.feifandaiyu.feifan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by houdaichang on 2017/6/12.
 */

public class ReportBean implements Serializable{

    /**
     * code : 1
     * list : {"user":{"username":"赵欻欻","telphone":"13263661109","userid":"80"},"car":{"brand":"北汽幻速","car_size":"北汽幻速H3 2015款 1.5L 手动舒适型","car_series":"北汽幻速H3","vin_code":"1233213","car_color":null,"newcar_price":"12321.00","license_num":null,"mileage":"213213"},"certificates":[{"dataname":"原始发票"},{"dataname":"机动车登记证书"},{"dataname":"机动车行驶证"},{"dataname":"身份证/组织机构代码证"},{"dataname":"其他"}],"tax":[{"dataname":"购置附加税"},{"dataname":"车船使用税"},{"dataname":"交强险"}]}
     */

    private int code;
    private ListBean list;
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

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * user : {"username":"赵欻欻","telphone":"13263661109","userid":"80"}
         * car : {"brand":"北汽幻速","car_size":"北汽幻速H3 2015款 1.5L 手动舒适型","car_series":"北汽幻速H3","vin_code":"1233213","car_color":null,"newcar_price":"12321.00","license_num":null,"mileage":"213213"}
         * certificates : [{"dataname":"原始发票"},{"dataname":"机动车登记证书"},{"dataname":"机动车行驶证"},{"dataname":"身份证/组织机构代码证"},{"dataname":"其他"}]
         * tax : [{"dataname":"购置附加税"},{"dataname":"车船使用税"},{"dataname":"交强险"}]
         */

        private UserBean user;
        private CarBean car;
        private List<CertificatesBean> certificates;
        private List<TaxBean> tax;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public CarBean getCar() {
            return car;
        }

        public void setCar(CarBean car) {
            this.car = car;
        }

        public List<CertificatesBean> getCertificates() {
            return certificates;
        }

        public void setCertificates(List<CertificatesBean> certificates) {
            this.certificates = certificates;
        }

        public List<TaxBean> getTax() {
            return tax;
        }

        public void setTax(List<TaxBean> tax) {
            this.tax = tax;
        }

        public static class UserBean implements Serializable{
            /**
             * username : 赵欻欻
             * telphone : 13263661109
             * userid : 80
             */

            private String username;
            private String telphone;
            private String userid;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getTelphone() {
                return telphone;
            }

            public void setTelphone(String telphone) {
                this.telphone = telphone;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }
        }

        public static class CarBean implements Serializable {
            /**
             * brand : 北汽幻速
             * car_size : 北汽幻速H3 2015款 1.5L 手动舒适型
             * car_series : 北汽幻速H3
             * vin_code : 1233213
             * car_color : null
             * newcar_price : 12321.00
             * license_num : null
             * mileage : 213213
             */

            private String brand;
            private String car_size;
            private String car_series;
            private String vin_code;
            private String car_color;
            private String newcar_price;
            private String license_num;
            private String mileage;

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getCar_size() {
                return car_size;
            }

            public void setCar_size(String car_size) {
                this.car_size = car_size;
            }

            public String getCar_series() {
                return car_series;
            }

            public void setCar_series(String car_series) {
                this.car_series = car_series;
            }

            public String getVin_code() {
                return vin_code;
            }

            public void setVin_code(String vin_code) {
                this.vin_code = vin_code;
            }

            public String getCar_color() {
                return car_color;
            }

            public void setCar_color(String car_color) {
                this.car_color = car_color;
            }

            public String getNewcar_price() {
                return newcar_price;
            }

            public void setNewcar_price(String newcar_price) {
                this.newcar_price = newcar_price;
            }

            public String getLicense_num() {
                return license_num;
            }

            public void setLicense_num(String license_num) {
                this.license_num = license_num;
            }

            public String getMileage() {
                return mileage;
            }

            public void setMileage(String mileage) {
                this.mileage = mileage;
            }
        }

        public static class CertificatesBean implements Serializable {
            /**
             * dataname : 原始发票
             */

            private String dataname;

            public String getDataname() {
                return dataname;
            }

            public void setDataname(String dataname) {
                this.dataname = dataname;
            }
        }

        public static class TaxBean implements Serializable {
            /**
             * dataname : 购置附加税
             */

            private String dataname;

            public String getDataname() {
                return dataname;
            }

            public void setDataname(String dataname) {
                this.dataname = dataname;
            }
        }
    }
}
