package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by davidzhao on 2017/6/1.
 */

public class PersonalLoanURLBean {

    /**
     * code : 1
     * role : 2
     * list : [{"id":"191","username":"aaaaaaaa32525","telphone":"13111144334","flag":"0","carloan":"2"},{"id":"190","username":"houdaiou","telphone":"13245677890","flag":"0","carloan":"2"},{"id":"189","username":"test123456","telphone":"13712348765","flag":"0","carloan":"2"},{"id":"188","username":"sujinping","telphone":"13965843650","flag":"0","carloan":"1"},{"id":"187","username":"zhaochengquan","telphone":"15648806943","flag":"0","carloan":"0"},{"id":"186","username":"ggggg123","telphone":"13622152176","flag":"0","carloan":"2"},{"id":"185","username":"asdfasdfas","telphone":"13712340987","flag":"0","carloan":"1"},{"id":"184","username":"sujinpin","telphone":"1325455667","flag":"0","carloan":"1"},{"id":"183","username":"asdfasd","telphone":"13622152673","flag":"0","carloan":"1"},{"id":"182","username":"dddd","telphone":"13333334555","flag":"0","carloan":"1"},{"id":"181","username":"asdfasdf","telphone":"13733142876","flag":"0","carloan":"1"},{"id":"180","username":"asdfasdf","telphone":"13622163216","flag":"0","carloan":"1"},{"id":"179","username":"asdfadsf","telphone":"13721651567","flag":"0","carloan":"1"},{"id":"178","username":"asfasdf","telphone":"13722153216","flag":"0","carloan":"1"},{"id":"177","username":"asdfads","telphone":"13733142165","flag":"0","carloan":"1"},{"id":"176","username":"asdfasdfasdfasdfas","telphone":"13722152754","flag":"0","carloan":"1"},{"id":"175","username":"qwqwqwqw","telphone":"13122233221","flag":"0","carloan":"1"},{"id":"174","username":"asdfadsfasdf","telphone":"13621734574","flag":"0","carloan":"1"},{"id":"173","username":"2swdfd","telphone":"13833173121","flag":"0","carloan":"1"},{"id":"172","username":"asdfads","telphone":"13622163187","flag":"0","carloan":"1"},{"id":"171","username":"asdfasdf","telphone":"13833102358","flag":"0","carloan":"1"},{"id":"170","username":"rrrrrrrrrrrr","telphone":"18722163218","flag":"0","carloan":"1"},{"id":"169","username":"uiuiui","telphone":"13544455555","flag":"0","carloan":"1"},{"id":"168","username":"asdfads","telphone":"13633145296","flag":"0","carloan":"1"},{"id":"167","username":"afdasd","telphone":"13122167321","flag":"0","carloan":"1"},{"id":"166","username":"ididid","telphone":"13111122221","flag":"0","carloan":"1"},{"id":"165","username":"ididid","telphone":"13144433221","flag":"0","carloan":"1"},{"id":"164","username":"23eedd","telphone":"15822164198","flag":"0","carloan":"1"},{"id":"163","username":"dididi","telphone":"13111134567","flag":"0","carloan":"1"},{"id":"162","username":"asdfads","telphone":"13633142165","flag":"0","carloan":"1"},{"id":"161","username":"1212","telphone":"13622132157","flag":"0","carloan":"1"},{"id":"160","username":"1212","telphone":"13622132156","flag":"0","carloan":"1"},{"id":"159","username":"mmmmm","telphone":"13122211111","flag":"0","carloan":"1"},{"id":"158","username":"nnnnnn","telphone":"15233344553","flag":"0","carloan":"1"},{"id":"157","username":"zhaochengquan","telphone":"13359916200","flag":"0","carloan":"1"},{"id":"156","username":"d","telphone":"13323412341","flag":"0","carloan":"1"},{"id":"155","username":"22222","telphone":"22222","flag":"0","carloan":"1"},{"id":"154","username":"zhaochengquan","telphone":"13763661100","flag":"0","carloan":"1"},{"id":"153","username":"ccccccc","telphone":"13988877889","flag":"0","carloan":"1"},{"id":"152","username":"eeeeee","telphone":"13122211113","flag":"0","carloan":"1"},{"id":"151","username":"ttttttttt","telphone":"13244455556","flag":"0","carloan":"1"},{"id":"150","username":"vvvvvv","telphone":"13455544445","flag":"0","carloan":"1"},{"id":"149","username":"ce","telphone":"132223333555","flag":"0","carloan":"1"},{"id":"148","username":"pppppp","telphone":"13455544444","flag":"0","carloan":"1"},{"id":"147","username":"ee","telphone":"12334222222","flag":"0","carloan":"1"},{"id":"146","username":"dfgh","telphone":"13122233445","flag":"0","carloan":"1"},{"id":"145","username":"yutiyutiy","telphone":"13122233332","flag":"0","carloan":"1"}]
     */

    private int code;
    private int role;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 191
         * username : aaaaaaaa32525
         * telphone : 13111144334
         * flag : 0
         * carloan : 2
         */

        private String userId;//无用户时：-999
        private String userName;//无客户时值为：未选定
        private String telephone;//无手机号码时值为：****
        private String flag;//用户状态 无用户时 状态：-999
        private String carloan;
        private String carId;
        private String credit;
        private String visits_lat;
        private String visits_lng;
        private String price;//贷款额
        private String gps;
        private String isnew;//1-新车，2-二手车
        private String ismarry;
        private String isguarantee;
        private String vprice;//（评估价，仅二手车，未评估时，价格为-999）
        private String sprice;
        private String carSize;
        private String estate;//评估师驳回状态，-1为驳回，仅状态为-1时，在列表显示即可
        private String rebate;
        private String car_carloan;
        private int rf;//风控驳回次数
        private int orf;//评估师驳回次数，仅二手车
        private int carinfochange;
        private int carpicchange;
        private String assessment;
        private String carpicFlag;//车辆图片上传 -999：未上传，1-已上传
        private String costFlag;//费用添加状态：-999：未添加，1-已添加
        private String reconsider;//（复议状态：0-可复议，1-不可复议，2-复议通过,-1:复议被驳回，3-正在申请复议）
        private String rentType;//贷款类型 未添加费用信息时：-999
        private String dismissal;//(复议驳回原因)
        private String licenseNum;//车牌号
        private String mid;//抵押信息id
        private String reasons;//驳回原因
        private String contract;//(合同id，无合同时：-999)
        private String pay;//(申请放款，1-不可申请，2-可申请)
        private String dealer;//(申请经销商返佣 1-不可申请，2-可申请)
        private String carpic; //（新车：抵押需要上传车辆照片，-999:未上传新车照片，1：已上传新车照片）
        private String cert; //1-合格证未上传，2-合格证已上传   新车：申请放款前，增加上传合格证(必传)，交强险保单（非必传），发票（非必传）3张照片,cert=1时，不会出现pay=2情况，即 未上传合格证时，不可以申请放款。

        private List<String> insurance;// 交强险保单（新车）
        private List<String> insuranceshow;
        private List<String> invoice;//发票（新车）
        private List<String> invoiceshow;

        private String liftcar;//提车 1-不可申请提车 2-可申请提车 （放款通过，补充材料后，可申请提车）
        private String settle;//落户  1-不可落户，2-可落户
        private String mortgage;//抵押 1-不可抵押  2-可抵押

        public String getSettle() {
            return settle == null ? "" : settle;
        }

        public void setSettle(String settle) {
            this.settle = settle;
        }

        public String getMortgage() {
            return mortgage == null ? "" : mortgage;
        }

        public void setMortgage(String mortgage) {
            this.mortgage = mortgage;
        }

        public String getLiftcar() {
            return liftcar == null ? "" : liftcar;
        }

        public void setLiftcar(String liftcar) {
            this.liftcar = liftcar;
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

        public String getCert() {
            return cert == null ? "" : cert;
        }

        public void setCert(String cert) {
            this.cert = cert;
        }

        public String getCarpic() {
            return carpic;
        }

        public void setCarpic(String carpic) {
            this.carpic = carpic;
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

        public String getContract() {
            return contract;
        }

        public void setContract(String contract) {
            this.contract = contract;
        }

        public String getReasons() {
            return reasons;
        }

        public void setReasons(String reasons) {
            this.reasons = reasons;
        }

        public String getLicenseNum() {
            return licenseNum;
        }

        public void setLicenseNum(String licenseNum) {
            this.licenseNum = licenseNum;
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

        public String getRentType() {
            return rentType;
        }

        public void setRentType(String rentType) {
            this.rentType = rentType;
        }

        public String getReconsider() {
            return reconsider;
        }

        public void setReconsider(String reconsider) {
            this.reconsider = reconsider;
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
        //        assessment   评估状态 0-已建档  1-未评估 2-已评估

        public String getAssessment() {
            return assessment;
        }

        public void setAssessment(String assessment) {
            this.assessment = assessment;
        }

        public int getCarinfochange() {
            return carinfochange;
        }

        public void setCarinfochange(int carinfochange) {
            this.carinfochange = carinfochange;
        }

        public int getCarpicchange() {
            return carpicchange;
        }

        public void setCarpicchange(int carpicchange) {
            this.carpicchange = carpicchange;
        }

        public int getRf() {
            return rf;
        }

        public void setRf(int rf) {
            this.rf = rf;
        }

        public int getOrf() {
            return orf;
        }

        public void setOrf(int orf) {
            this.orf = orf;
        }

        public String getCar_carloan() {
            return car_carloan;
        }

        public void setCar_carloan(String car_carloan) {
            this.car_carloan = car_carloan;
        }

        public String getRebate() {
            return rebate;
        }

        public void setRebate(String rebate) {
            this.rebate = rebate;
        }

        public String getIsguarantee() {
            return isguarantee;
        }

        public void setIsguarantee(String isguarantee) {
            this.isguarantee = isguarantee;
        }

        public String getSprice() {
            return sprice;
        }

        public void setSprice(String sprice) {
            this.sprice = sprice;
        }

        public String getVprice() {
            return vprice;
        }

        public void setVprice(String vprice) {
            this.vprice = vprice;
        }

        public String getIsmarry() {
            return ismarry;
        }

        public void setIsmarry(String ismarry) {
            this.ismarry = ismarry;
        }

        public String getIsnew() {
            return isnew;
        }

        public void setIsnew(String isnew) {
            this.isnew = isnew;
        }

        public String getGps() {
            return gps;
        }

        public void setGps(String gps) {
            this.gps = gps;
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

        public String getId() {
            return userId;
        }

        public void setId(String id) {
            this.userId = id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getUsername() {
            return userName;
        }

        public void setUsername(String username) {
            this.userName = username;
        }

        public String getTelphone() {
            return telephone;
        }

        public void setTelphone(String telphone) {
            this.telephone = telphone;
        }

        public String getCarSize() {
            return carSize;
        }

        public void setCarSize(String carSize) {
            this.carSize = carSize;
        }

        public String getEstate() {
            return estate;
        }

        public void setEstate(String estate) {
            this.estate = estate;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getCarloan() {
            return carloan;
        }

        public void setCarloan(String carloan) {
            this.carloan = carloan;
        }

        public String getCarid() {
            return carId;
        }

        public void setCarid(String carid) {
            this.carId = carid;
        }
    }
}
