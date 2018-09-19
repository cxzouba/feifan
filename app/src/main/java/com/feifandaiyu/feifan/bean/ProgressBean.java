package com.feifandaiyu.feifan.bean;

/**
 * Created by houdaichang on 2017/6/9.
 */

public class ProgressBean {

    /**
     * userName : chengquan
     * code : 1
     * stage : 11
     * msg : 添加二手车评估信息
     * list : {"brand":"奥迪","car_series":"奥迪A4","car_size":"奥迪A4 2008款 2.0T 自动豪华型","engine_code":"dfdwef","newcar_price":343400,"car_color":"dsdfwefd","license_num":"dfwfew","oldcar_price":2144,"gearbox":1}
     */

    private String userName;
    private int code;
    private int stage;
    private String msg;
    private String reason;
    private ListBean list;

    public String getReasons() {
        return reason;
    }

    public void setReasons(String reasons) {
        this.reason = reasons;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
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
         * brand : 奥迪
         * car_series : 奥迪A4
         * car_size : 奥迪A4 2008款 2.0T 自动豪华型
         * engine_code : dfdwef
         * newcar_price : 343400
         * car_color : dsdfwefd
         * license_num : dfwfew
         * oldcar_price : 2144
         * gearbox : 1
         */

        private String id;
        private String brand;
        private String car_series;
        private String car_size;
        private String engine_code;
        private String newcar_price;
        private String car_color;
        private String license_num;
        private String oldcar_price;
        private String gearbox;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getNewcar_price() {
            return newcar_price;
        }

        public void setNewcar_price(String newcar_price) {
            this.newcar_price = newcar_price;
        }

        public String getCar_color() {
            return car_color;
        }

        public void setCar_color(String car_color) {
            this.car_color = car_color;
        }

        public String getLicense_num() {
            return license_num;
        }

        public void setLicense_num(String license_num) {
            this.license_num = license_num;
        }

        public String getOldcar_price() {
            return oldcar_price;
        }

        public void setOldcar_price(String oldcar_price) {
            this.oldcar_price = oldcar_price;
        }

        public String getGearbox() {
            return gearbox;
        }

        public void setGearbox(String gearbox) {
            this.gearbox = gearbox;
        }
    }
}
