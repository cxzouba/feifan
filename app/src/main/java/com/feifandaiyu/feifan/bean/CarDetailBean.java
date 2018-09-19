package com.feifandaiyu.feifan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by houdaichang on 2017/6/3.
 */

public class CarDetailBean implements Serializable {


    /**
     * code : 1
     * list : [{"id":620,"times":"2013-04-19","mileage":"130000","location":"哈尔滨","structure_pic":["http://oupz05j2b.bkt.clouddn.com/0c1a21b2-2d7f-408b-98b9-4daef4355c1d7980020171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:xuHciqIKJ8CM0H0YewQQV-LfX5E=,出厂铭牌","http://oupz05j2b.bkt.clouddn.com/cca72007-de59-4805-9172-4de6dfcd09f02397120171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:PiEyvJ0GoKiaW_-3sYrObUIFjso=,发动机","http://oupz05j2b.bkt.clouddn.com/3b3a315b-9b9b-4044-b9f5-cecf846814df3140920171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:U76OTv6rOicbTPUi3aferqq25_M=,后备箱"],"appenhance_pic":["http://oupz05j2b.bkt.clouddn.com/993c5c0e-b110-4d0d-ab9f-00af608742de8825620171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:iMzeivdJBByn47cGDQuRs49dFMg=,正前方","http://oupz05j2b.bkt.clouddn.com/9fc13a29-5db9-4c04-89b4-0c2b10fbf9cd9917320171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:rgGSeduiQNGOFP767oGm-OgrBB4=,正后方","http://oupz05j2b.bkt.clouddn.com/0ef50854-8905-4201-a8eb-c14ed67ebcca8397320171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:bTVnF4GKieX64lbdAi1ybqEiQtg=,左前45度","http://oupz05j2b.bkt.clouddn.com/92c384e4-9568-4b70-b02d-dddc17a338bc6864820171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:OyqeEjZq8FUYdXX0H9srFM5KH4k=,右前45度","http://oupz05j2b.bkt.clouddn.com/0456017b-b761-436f-a1a3-15c6e3e61fe44133920171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:sEHWrtshVogs_ZyCbox2sHu7Z4M=,左后45度","http://oupz05j2b.bkt.clouddn.com/ad964c45-32c6-425c-97ab-e211d8c2a86c8874720171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:3o2y_vCqR91CLbMu2KVDACHHAq0=,右后45度","http://oupz05j2b.bkt.clouddn.com/3f644158-8dc4-4d07-a55f-2b078a77ea737620620171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:hoHyqbcie7I9m7NT4FumhPrGLH4=,风挡17位串号"],"control_pic":["http://oupz05j2b.bkt.clouddn.com/b639d99e-6a0c-41e8-af38-d2e059383e2e8303820171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:P2RfsZT5LLOm6C9vbgsfJU1aFzo=,里程"],"chassis_pic":["http://oupz05j2b.bkt.clouddn.com/77df6529-b58c-41f4-8326-9bb6f07f8fad7268720171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:FAN-G6d0Kkm9vUyCbaMeXZUkN6g=,大本1","http://oupz05j2b.bkt.clouddn.com/e1fe6732-30df-47e8-9e02-668e0069dafa2198320171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:L3iERmUrsVYYKjAOBsv3CotDOXQ=,大本2","http://oupz05j2b.bkt.clouddn.com/8789f29c-0e99-4115-a759-bc8a42cf8af81656420171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:YZTiUz9Olo-a8LmWzKw4EcAzzDo=,大本3","http://oupz05j2b.bkt.clouddn.com/dad5a38e-8727-49ff-9126-536977cbefcf9182420171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:Kfqr2XBTvR-EZqS9X5TFgNidAzM=,大本4","http://oupz05j2b.bkt.clouddn.com/22982492-8ac9-43ea-bee7-fece4b649f353738020171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:41KUndN9tWVIrwqznqstZguZ8vQ=,大本5","http://oupz05j2b.bkt.clouddn.com/197f2c2d-02c2-4a3a-bb41-0092d3e9a8809371720171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:LpQeVt9DyMgH8qUTuqbymcCRx3g=,大本6","http://oupz05j2b.bkt.clouddn.com/94577fa6-6541-4a7f-b6e6-7144d62d3a9a1446420171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:U9g07m9Swl1cXmuJJYiD4jXKeA8=,大本7","http://oupz05j2b.bkt.clouddn.com/b15fc742-4f9e-4eb6-aa26-544a8af2f78f4660420171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:hQi30tlobw04W-mZYpQx9icEyA0=,大本8","http://oupz05j2b.bkt.clouddn.com/0260bfdc-0030-4020-a566-77a72ee8d5fa2271020171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:BmESOy4CsRhxuG9cjmExikBYxAM=,大本9","http://oupz05j2b.bkt.clouddn.com/59789a85-5f63-4e4f-b1bb-bd4ae397784e1896920171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:GyAsojR_CDgHayAqc7NPhTQNLKc=,行驶证正本","http://oupz05j2b.bkt.clouddn.com/a273c85f-7e26-4567-8bf9-0c55af6c117e1393420171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:WEjvk5Jkk2MGTzaBpKuqyoZ9Uko=,行驶证副本","http://oupz05j2b.bkt.clouddn.com/de6403e3-a660-40d0-8f99-108eaded00d34161820171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:EsgnydlY6VCO4UfTpTzoiCODoD8=,机动车检车页","http://oupz05j2b.bkt.clouddn.com/6e0d5517-e363-4809-aeb2-25bc4efd7e7b3033720171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:yVvXwKBhdh3KtCet4Snltfqxv6w=,商险","http://oupz05j2b.bkt.clouddn.com/de50ac15-cd0e-483d-a365-d91c88d28f5f9399520171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:-yCoBVHiuWQhvXnrPrLcmICD89w=,强险"],"other_pic":["http://oupz05j2b.bkt.clouddn.com/05e5e2be-e1e1-422d-8ea6-01943d481feb6240220171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:FL3oKCS0tA0KmVnFx1JoAXiGPHc=","http://oupz05j2b.bkt.clouddn.com/f3940277-284f-4ac2-93f2-a0dd3c2ccc897271520171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:za-M7nXdcJdp1V8CAu5Lzarxeow="],"brand":"福特","car_series":"翼虎","car_size":"翼虎 2013款 1.6L GTDi 两驱舒适型","engine_code":"LVSHJCABXDE205886","gearbox":0,"car_color":"黄","license_num":"黑A8J520","guidePrice":190000,"car_dealer":"无","valuation_price":"110000.00","remarks":"未填写","appraiser_remark":"haha"}]
     */

    private int code;
    private String msg;
    private List<ListBean> list;

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

    public static class ListBean implements Serializable{
        /**
         * id : 620
         * times : 2013-04-19
         * mileage : 130000
         * location : 哈尔滨
         * structure_pic : ["http://oupz05j2b.bkt.clouddn.com/0c1a21b2-2d7f-408b-98b9-4daef4355c1d7980020171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:xuHciqIKJ8CM0H0YewQQV-LfX5E=,出厂铭牌","http://oupz05j2b.bkt.clouddn.com/cca72007-de59-4805-9172-4de6dfcd09f02397120171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:PiEyvJ0GoKiaW_-3sYrObUIFjso=,发动机","http://oupz05j2b.bkt.clouddn.com/3b3a315b-9b9b-4044-b9f5-cecf846814df3140920171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:U76OTv6rOicbTPUi3aferqq25_M=,后备箱"]
         * appenhance_pic : ["http://oupz05j2b.bkt.clouddn.com/993c5c0e-b110-4d0d-ab9f-00af608742de8825620171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:iMzeivdJBByn47cGDQuRs49dFMg=,正前方","http://oupz05j2b.bkt.clouddn.com/9fc13a29-5db9-4c04-89b4-0c2b10fbf9cd9917320171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:rgGSeduiQNGOFP767oGm-OgrBB4=,正后方","http://oupz05j2b.bkt.clouddn.com/0ef50854-8905-4201-a8eb-c14ed67ebcca8397320171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:bTVnF4GKieX64lbdAi1ybqEiQtg=,左前45度","http://oupz05j2b.bkt.clouddn.com/92c384e4-9568-4b70-b02d-dddc17a338bc6864820171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:OyqeEjZq8FUYdXX0H9srFM5KH4k=,右前45度","http://oupz05j2b.bkt.clouddn.com/0456017b-b761-436f-a1a3-15c6e3e61fe44133920171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:sEHWrtshVogs_ZyCbox2sHu7Z4M=,左后45度","http://oupz05j2b.bkt.clouddn.com/ad964c45-32c6-425c-97ab-e211d8c2a86c8874720171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:3o2y_vCqR91CLbMu2KVDACHHAq0=,右后45度","http://oupz05j2b.bkt.clouddn.com/3f644158-8dc4-4d07-a55f-2b078a77ea737620620171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:hoHyqbcie7I9m7NT4FumhPrGLH4=,风挡17位串号"]
         * control_pic : ["http://oupz05j2b.bkt.clouddn.com/b639d99e-6a0c-41e8-af38-d2e059383e2e8303820171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:P2RfsZT5LLOm6C9vbgsfJU1aFzo=,里程"]
         * chassis_pic : ["http://oupz05j2b.bkt.clouddn.com/77df6529-b58c-41f4-8326-9bb6f07f8fad7268720171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:FAN-G6d0Kkm9vUyCbaMeXZUkN6g=,大本1","http://oupz05j2b.bkt.clouddn.com/e1fe6732-30df-47e8-9e02-668e0069dafa2198320171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:L3iERmUrsVYYKjAOBsv3CotDOXQ=,大本2","http://oupz05j2b.bkt.clouddn.com/8789f29c-0e99-4115-a759-bc8a42cf8af81656420171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:YZTiUz9Olo-a8LmWzKw4EcAzzDo=,大本3","http://oupz05j2b.bkt.clouddn.com/dad5a38e-8727-49ff-9126-536977cbefcf9182420171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:Kfqr2XBTvR-EZqS9X5TFgNidAzM=,大本4","http://oupz05j2b.bkt.clouddn.com/22982492-8ac9-43ea-bee7-fece4b649f353738020171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:41KUndN9tWVIrwqznqstZguZ8vQ=,大本5","http://oupz05j2b.bkt.clouddn.com/197f2c2d-02c2-4a3a-bb41-0092d3e9a8809371720171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:LpQeVt9DyMgH8qUTuqbymcCRx3g=,大本6","http://oupz05j2b.bkt.clouddn.com/94577fa6-6541-4a7f-b6e6-7144d62d3a9a1446420171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:U9g07m9Swl1cXmuJJYiD4jXKeA8=,大本7","http://oupz05j2b.bkt.clouddn.com/b15fc742-4f9e-4eb6-aa26-544a8af2f78f4660420171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:hQi30tlobw04W-mZYpQx9icEyA0=,大本8","http://oupz05j2b.bkt.clouddn.com/0260bfdc-0030-4020-a566-77a72ee8d5fa2271020171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:BmESOy4CsRhxuG9cjmExikBYxAM=,大本9","http://oupz05j2b.bkt.clouddn.com/59789a85-5f63-4e4f-b1bb-bd4ae397784e1896920171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:GyAsojR_CDgHayAqc7NPhTQNLKc=,行驶证正本","http://oupz05j2b.bkt.clouddn.com/a273c85f-7e26-4567-8bf9-0c55af6c117e1393420171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:WEjvk5Jkk2MGTzaBpKuqyoZ9Uko=,行驶证副本","http://oupz05j2b.bkt.clouddn.com/de6403e3-a660-40d0-8f99-108eaded00d34161820171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:EsgnydlY6VCO4UfTpTzoiCODoD8=,机动车检车页","http://oupz05j2b.bkt.clouddn.com/6e0d5517-e363-4809-aeb2-25bc4efd7e7b3033720171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:yVvXwKBhdh3KtCet4Snltfqxv6w=,商险","http://oupz05j2b.bkt.clouddn.com/de50ac15-cd0e-483d-a365-d91c88d28f5f9399520171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:-yCoBVHiuWQhvXnrPrLcmICD89w=,强险"]
         * other_pic : ["http://oupz05j2b.bkt.clouddn.com/05e5e2be-e1e1-422d-8ea6-01943d481feb6240220171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:FL3oKCS0tA0KmVnFx1JoAXiGPHc=","http://oupz05j2b.bkt.clouddn.com/f3940277-284f-4ac2-93f2-a0dd3c2ccc897271520171228.png?e=1515484536&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:za-M7nXdcJdp1V8CAu5Lzarxeow="]
         * brand : 福特
         * car_series : 翼虎
         * car_size : 翼虎 2013款 1.6L GTDi 两驱舒适型
         * engine_code : LVSHJCABXDE205886
         * gearbox : 0
         * car_color : 黄
         * license_num : 黑A8J520
         * guidePrice : 190000
         * car_dealer : 无
         * valuation_price : 110000.00
         * remarks : 未填写
         * appraiser_remark : haha
         */

        private int id;
        private String times;
        private String mileage;
        private String location;
        private String brand;
        private String car_series;
        private String car_size;
        private String engine_code;
        private String gearbox;
        private String car_color;
        private String license_num;
        private String guidePrice;
        private String car_dealer;
        private String valuation_price;
        private String remarks;
        private String appraiser_remark;
        private List<String> structure_pic;
        private List<String> appenhance_pic;
        private List<String> control_pic;
        private List<String> chassis_pic;
        private List<String> other_pic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getGearbox() {
            return gearbox;
        }

        public void setGearbox(String gearbox) {
            this.gearbox = gearbox;
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

        public String getGuidePrice() {
            return guidePrice;
        }

        public void setGuidePrice(String guidePrice) {
            this.guidePrice = guidePrice;
        }

        public String getCar_dealer() {
            return car_dealer;
        }

        public void setCar_dealer(String car_dealer) {
            this.car_dealer = car_dealer;
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

        public List<String> getStructure_pic() {
            return structure_pic;
        }

        public void setStructure_pic(List<String> structure_pic) {
            this.structure_pic = structure_pic;
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

        public List<String> getOther_pic() {
            return other_pic;
        }

        public void setOther_pic(List<String> other_pic) {
            this.other_pic = other_pic;
        }
    }
}
