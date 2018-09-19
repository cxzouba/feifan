package com.feifandaiyu.feifan.bean;

/**
 * Created by davidzhao on 2017/6/20.
 */

public class CarTypeActivityBean {

    /**
     * list : {"carType":0,"guidePrice":"55800.00","salePrice":"50000","vinCode":"586765748987","carDealer":"-1","vehOperator":"123","bcc":"宝骏宝骏330 2016款 1.5L 手动舒适型宝骏330","carColor":"1","gearbox":"0","licenseNum":"58698"}
     * code : 1
     */

    private ListBean list;
    private int code;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    private String carId;

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ListBean {
        /**
         * carType : 0
         * guidePrice : 55800.00
         * salePrice : 50000
         * vinCode : 586765748987
         * carDealer : -1
         * vehOperator : 123
         * bcc : 宝骏宝骏330 2016款 1.5L 手动舒适型宝骏330
         * carColor : 1
         * gearbox : 0
         * licenseNum : 58698
         */

        private int carType;
        private String guidePrice;
        private String salePrice;
        private String vinCode;
        private String carDealer;
        private String vehOperator;
        private String bcc;
        private String carColor;
        private String gearbox;
        private String licenseNum;
        private String fcardTime;
        private String mileage;
        private String location;
        private String brand;
        private String carSize;
        private String carSeries;
        private String delerId;

        public String getDelerId() {
            return delerId;
        }

        public void setDelerId(String delerId) {
            this.delerId = delerId;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCarSize() {
            return carSize;
        }

        public void setCarSize(String carSize) {
            this.carSize = carSize;
        }

        public String getCarSeries() {
            return carSeries;
        }

        public void setCarSeries(String carSeries) {
            this.carSeries = carSeries;
        }

        public String getFcardTime() {
            return fcardTime;
        }

        public void setFcardTime(String fcardTime) {
            this.fcardTime = fcardTime;
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

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        private String remarks;

        public int getCarType() {
            return carType;
        }

        public void setCarType(int carType) {
            this.carType = carType;
        }

        public String getGuidePrice() {
            return guidePrice;
        }

        public void setGuidePrice(String guidePrice) {
            this.guidePrice = guidePrice;
        }

        public String getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(String salePrice) {
            this.salePrice = salePrice;
        }

        public String getVinCode() {
            return vinCode;
        }

        public void setVinCode(String vinCode) {
            this.vinCode = vinCode;
        }

        public String getCarDealer() {
            return carDealer;
        }

        public void setCarDealer(String carDealer) {
            this.carDealer = carDealer;
        }

        public String getVehOperator() {
            return vehOperator;
        }

        public void setVehOperator(String vehOperator) {
            this.vehOperator = vehOperator;
        }

        public String getBcc() {
            return bcc;
        }

        public void setBcc(String bcc) {
            this.bcc = bcc;
        }

        public String getCarColor() {
            return carColor;
        }

        public void setCarColor(String carColor) {
            this.carColor = carColor;
        }

        public String getGearbox() {
            return gearbox;
        }

        public void setGearbox(String gearbox) {
            this.gearbox = gearbox;
        }

        public String getLicenseNum() {
            return licenseNum;
        }

        public void setLicenseNum(String licenseNum) {
            this.licenseNum = licenseNum;
        }
    }
}
