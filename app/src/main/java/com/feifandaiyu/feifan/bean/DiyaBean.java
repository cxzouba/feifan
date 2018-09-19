package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2018/1/19.
 */

public class DiyaBean {

    /**
     * code : 1
     * mid : 21
     * flag : 1
     * carType : 2
     * rid : 28
     * imei : 1
     * list : {"afterMortgage":["image_20180118200436_197.jpg"],"insurance":["image_20180118200436_197.jpg"],"commercial":["image_20180118200436_197.jpg"],"vincode":["image_20180118200436_197.jpg"],"licensenum":["image_20180118200436_197.jpg"],"position":["image_20180118200436_197.jpg"],"groupPhoto":["image_20180118200436_197.jpg"],"saleAgreement":["image_20180118200436_197.jpg"],"drivingLicense":["-1"],"invoice":["-1"],"certificate":["-1"]}
     * show : {"afterMortgage":["http://oupz05j2b.bkt.clouddn.com/image_20180118200436_197.jpg?e=1516333550&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:gO7VStUuQ2T8gewRv-JvOZHO73k="],"insurance":["http://oupz05j2b.bkt.clouddn.com/image_20180118200436_197.jpg?e=1516333550&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:gO7VStUuQ2T8gewRv-JvOZHO73k="],"commercial":["http://oupz05j2b.bkt.clouddn.com/image_20180118200436_197.jpg?e=1516333550&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:gO7VStUuQ2T8gewRv-JvOZHO73k="],"vincode":["http://oupz05j2b.bkt.clouddn.com/image_20180118200436_197.jpg?e=1516333550&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:gO7VStUuQ2T8gewRv-JvOZHO73k="],"licensenum":["http://oupz05j2b.bkt.clouddn.com/image_20180118200436_197.jpg?e=1516333550&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:gO7VStUuQ2T8gewRv-JvOZHO73k="],"position":["http://oupz05j2b.bkt.clouddn.com/image_20180118200436_197.jpg?e=1516333550&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:gO7VStUuQ2T8gewRv-JvOZHO73k="],"groupPhoto":["http://oupz05j2b.bkt.clouddn.com/image_20180118200436_197.jpg?e=1516333550&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:gO7VStUuQ2T8gewRv-JvOZHO73k="],"saleAgreement":["http://oupz05j2b.bkt.clouddn.com/image_20180118200436_197.jpg?e=1516333550&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:gO7VStUuQ2T8gewRv-JvOZHO73k="],"drivingLicense":["-1"],"invoice":["-1"],"certificate":["-1"]}
     */

    private int code;
    private String mid;
    private String chepaihao;
    private String chejiahao;
    private String flag;
    private int carType;
    private String rid;
    private String rname;
    private String msg;
    private String imei;
    private ListBean list;
    private ShowBean show;

    public String getChepaihao() {
        return chepaihao;
    }

    public void setChepaihao(String chepaihao) {
        this.chepaihao = chepaihao;
    }

    public String getChejiahao() {
        return chejiahao;
    }

    public void setChejiahao(String chejiahao) {
        this.chejiahao = chejiahao;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

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

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getCarType() {
        return carType;
    }

    public void setCarType(int carType) {
        this.carType = carType;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public ShowBean getShow() {
        return show;
    }

    public void setShow(ShowBean show) {
        this.show = show;
    }

    public static class ListBean {
        private List<String> afterMortgage;
        private List<String> insurance;
        private List<String> commercial;
        private List<String> vincode;
        private List<String> licensenum;
        private List<String> position;
        private List<String> groupPhoto;
        private List<String> saleAgreement;
        private List<String> drivingLicense;
        private List<String> invoice;
        private List<String> certificate;

        public List<String> getAfterMortgage() {
            return afterMortgage;
        }

        public void setAfterMortgage(List<String> afterMortgage) {
            this.afterMortgage = afterMortgage;
        }

        public List<String> getInsurance() {
            return insurance;
        }

        public void setInsurance(List<String> insurance) {
            this.insurance = insurance;
        }

        public List<String> getCommercial() {
            return commercial;
        }

        public void setCommercial(List<String> commercial) {
            this.commercial = commercial;
        }

        public List<String> getVincode() {
            return vincode;
        }

        public void setVincode(List<String> vincode) {
            this.vincode = vincode;
        }

        public List<String> getLicensenum() {
            return licensenum;
        }

        public void setLicensenum(List<String> licensenum) {
            this.licensenum = licensenum;
        }

        public List<String> getPosition() {
            return position;
        }

        public void setPosition(List<String> position) {
            this.position = position;
        }

        public List<String> getGroupPhoto() {
            return groupPhoto;
        }

        public void setGroupPhoto(List<String> groupPhoto) {
            this.groupPhoto = groupPhoto;
        }

        public List<String> getSaleAgreement() {
            return saleAgreement;
        }

        public void setSaleAgreement(List<String> saleAgreement) {
            this.saleAgreement = saleAgreement;
        }

        public List<String> getDrivingLicense() {
            return drivingLicense;
        }

        public void setDrivingLicense(List<String> drivingLicense) {
            this.drivingLicense = drivingLicense;
        }

        public List<String> getInvoice() {
            return invoice;
        }

        public void setInvoice(List<String> invoice) {
            this.invoice = invoice;
        }

        public List<String> getCertificate() {
            return certificate;
        }

        public void setCertificate(List<String> certificate) {
            this.certificate = certificate;
        }
    }

    public static class ShowBean {
        private List<String> afterMortgage;
        private List<String> insurance;
        private List<String> commercial;
        private List<String> vincode;
        private List<String> licensenum;
        private List<String> position;
        private List<String> groupPhoto;
        private List<String> saleAgreement;
        private List<String> drivingLicense;
        private List<String> invoice;
        private List<String> certificate;

        public List<String> getAfterMortgage() {
            return afterMortgage;
        }

        public void setAfterMortgage(List<String> afterMortgage) {
            this.afterMortgage = afterMortgage;
        }

        public List<String> getInsurance() {
            return insurance;
        }

        public void setInsurance(List<String> insurance) {
            this.insurance = insurance;
        }

        public List<String> getCommercial() {
            return commercial;
        }

        public void setCommercial(List<String> commercial) {
            this.commercial = commercial;
        }

        public List<String> getVincode() {
            return vincode;
        }

        public void setVincode(List<String> vincode) {
            this.vincode = vincode;
        }

        public List<String> getLicensenum() {
            return licensenum;
        }

        public void setLicensenum(List<String> licensenum) {
            this.licensenum = licensenum;
        }

        public List<String> getPosition() {
            return position;
        }

        public void setPosition(List<String> position) {
            this.position = position;
        }

        public List<String> getGroupPhoto() {
            return groupPhoto;
        }

        public void setGroupPhoto(List<String> groupPhoto) {
            this.groupPhoto = groupPhoto;
        }

        public List<String> getSaleAgreement() {
            return saleAgreement;
        }

        public void setSaleAgreement(List<String> saleAgreement) {
            this.saleAgreement = saleAgreement;
        }

        public List<String> getDrivingLicense() {
            return drivingLicense;
        }

        public void setDrivingLicense(List<String> drivingLicense) {
            this.drivingLicense = drivingLicense;
        }

        public List<String> getInvoice() {
            return invoice;
        }

        public void setInvoice(List<String> invoice) {
            this.invoice = invoice;
        }

        public List<String> getCertificate() {
            return certificate;
        }

        public void setCertificate(List<String> certificate) {
            this.certificate = certificate;
        }
    }
}
