package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2018/9/10.
 */

public class NewClass {


    /**
     * code : 1
     * list : [{"carSize":"V12 Vantage 2016款 6.0L S Roadster","appenhance_pic":"a:1:{i:0;s:53:\"fe1ae918-d196-4aef-9735-59c9e03058ce6108320180910.png\";}","carId":690,"ismarry":2,"isguarantee":1,"userName":"董博","telephone":"13633352536","flag":"6","userId":"1326","costFlag":"1","price":"210000.00","rentType":"直租","visits_lat":"45.707","visits_lng":"126.600","dealerId":"-999","isnew":1,"vprice":0,"carpicFlag":1,"orf":0,"rf":0,"reconsider":1,"contract":62,"carpic":1,"pay":1,"cert":2,"insurance":["74320177-ad8f-473f-bfa9-32697e3cc3c84618520180905.png"],"insuranceshow":["https://img.hrbffdy.com/74320177-ad8f-473f-bfa9-32697e3cc3c84618520180905.png?e=1536660923&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:cD7JvtG4BOQgZCQAFJ45wI3xNb0="],"invoice":["bbe91f46-d97e-4f8e-8fe6-b5a8624689c31398920180905.png"],"invoiceshow":["https://img.hrbffdy.com/bbe91f46-d97e-4f8e-8fe6-b5a8624689c31398920180905.png?e=1536660923&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:AKJSBmkZgP7rTyfN1rh_maXcwDQ="],"mid":49,"gps":2,"dealer":1,"liftcar":1,"settle":1,"mortgage":1}]
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
         * carSize : V12 Vantage 2016款 6.0L S Roadster
         * appenhance_pic : a:1:{i:0;s:53:"fe1ae918-d196-4aef-9735-59c9e03058ce6108320180910.png";}
         * carId : 690
         * ismarry : 2
         * isguarantee : 1
         * userName : 董博
         * telephone : 13633352536
         * flag : 6
         * userId : 1326
         * costFlag : 1
         * price : 210000.00
         * rentType : 直租
         * visits_lat : 45.707
         * visits_lng : 126.600
         * dealerId : -999
         * isnew : 1
         * vprice : 0
         * carpicFlag : 1
         * orf : 0
         * rf : 0
         * reconsider : 1
         * contract : 62
         * carpic : 1
         * pay : 1
         * cert : 2
         * insurance : ["74320177-ad8f-473f-bfa9-32697e3cc3c84618520180905.png"]
         * insuranceshow : ["https://img.hrbffdy.com/74320177-ad8f-473f-bfa9-32697e3cc3c84618520180905.png?e=1536660923&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:cD7JvtG4BOQgZCQAFJ45wI3xNb0="]
         * invoice : ["bbe91f46-d97e-4f8e-8fe6-b5a8624689c31398920180905.png"]
         * invoiceshow : ["https://img.hrbffdy.com/bbe91f46-d97e-4f8e-8fe6-b5a8624689c31398920180905.png?e=1536660923&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:AKJSBmkZgP7rTyfN1rh_maXcwDQ="]
         * mid : 49
         * gps : 2
         * dealer : 1
         * liftcar : 1
         * settle : 1
         * mortgage : 1
         */

        private String carSize;
        private String appenhance_pic;
        private int carId;
        private int ismarry;
        private int isguarantee;
        private String userName;
        private String telephone;
        private String flag;
        private String userId;
        private String costFlag;
        private String price;
        private String rentType;
        private String visits_lat;
        private String visits_lng;
        private String dealerId;
        private int isnew;
        private int vprice;
        private int carpicFlag;
        private int orf;
        private int rf;
        private int reconsider;
        private int contract;
        private int carpic;
        private int pay;
        private int cert;
        private int mid;
        private int gps;
        private int dealer;
        private int liftcar;
        private int settle;
        private int mortgage;
        private List<String> insurance;
        private List<String> insuranceshow;
        private List<String> invoice;
        private List<String> invoiceshow;

        public String getCarSize() {
            return carSize;
        }

        public void setCarSize(String carSize) {
            this.carSize = carSize;
        }

        public String getAppenhance_pic() {
            return appenhance_pic;
        }

        public void setAppenhance_pic(String appenhance_pic) {
            this.appenhance_pic = appenhance_pic;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public int getIsmarry() {
            return ismarry;
        }

        public void setIsmarry(int ismarry) {
            this.ismarry = ismarry;
        }

        public int getIsguarantee() {
            return isguarantee;
        }

        public void setIsguarantee(int isguarantee) {
            this.isguarantee = isguarantee;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCostFlag() {
            return costFlag;
        }

        public void setCostFlag(String costFlag) {
            this.costFlag = costFlag;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRentType() {
            return rentType;
        }

        public void setRentType(String rentType) {
            this.rentType = rentType;
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

        public String getDealerId() {
            return dealerId;
        }

        public void setDealerId(String dealerId) {
            this.dealerId = dealerId;
        }

        public int getIsnew() {
            return isnew;
        }

        public void setIsnew(int isnew) {
            this.isnew = isnew;
        }

        public int getVprice() {
            return vprice;
        }

        public void setVprice(int vprice) {
            this.vprice = vprice;
        }

        public int getCarpicFlag() {
            return carpicFlag;
        }

        public void setCarpicFlag(int carpicFlag) {
            this.carpicFlag = carpicFlag;
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

        public int getReconsider() {
            return reconsider;
        }

        public void setReconsider(int reconsider) {
            this.reconsider = reconsider;
        }

        public int getContract() {
            return contract;
        }

        public void setContract(int contract) {
            this.contract = contract;
        }

        public int getCarpic() {
            return carpic;
        }

        public void setCarpic(int carpic) {
            this.carpic = carpic;
        }

        public int getPay() {
            return pay;
        }

        public void setPay(int pay) {
            this.pay = pay;
        }

        public int getCert() {
            return cert;
        }

        public void setCert(int cert) {
            this.cert = cert;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public int getGps() {
            return gps;
        }

        public void setGps(int gps) {
            this.gps = gps;
        }

        public int getDealer() {
            return dealer;
        }

        public void setDealer(int dealer) {
            this.dealer = dealer;
        }

        public int getLiftcar() {
            return liftcar;
        }

        public void setLiftcar(int liftcar) {
            this.liftcar = liftcar;
        }

        public int getSettle() {
            return settle;
        }

        public void setSettle(int settle) {
            this.settle = settle;
        }

        public int getMortgage() {
            return mortgage;
        }

        public void setMortgage(int mortgage) {
            this.mortgage = mortgage;
        }

        public List<String> getInsurance() {
            return insurance;
        }

        public void setInsurance(List<String> insurance) {
            this.insurance = insurance;
        }

        public List<String> getInsuranceshow() {
            return insuranceshow;
        }

        public void setInsuranceshow(List<String> insuranceshow) {
            this.insuranceshow = insuranceshow;
        }

        public List<String> getInvoice() {
            return invoice;
        }

        public void setInvoice(List<String> invoice) {
            this.invoice = invoice;
        }

        public List<String> getInvoiceshow() {
            return invoiceshow;
        }

        public void setInvoiceshow(List<String> invoiceshow) {
            this.invoiceshow = invoiceshow;
        }
    }
}
