package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2017/8/29.
 */

public class UpdateDiyaBean {


    /**
     * code : 1
     * list : {"imei":"1234567","equipmentPic":["6a9df6c8-1282-484e-bda3-87cb45315ebd2885520170823.png"]}
     * show : {"equipmentPic":["http://oupz05j2b.bkt.clouddn.com/6a9df6c8-1282-484e-bda3-87cb45315ebd2885520170823.png?e=1504167537&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:XlOdgvCsk06fVgpudCIgjThrBYs="]}
     */

    private int code;
    private ListBean list;
    private ShowBean show;
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

    public ShowBean getShow() {
        return show;
    }

    public void setShow(ShowBean show) {
        this.show = show;
    }

    public static class ListBean {
        /**
         * imei : 1234567
         * equipmentPic : ["6a9df6c8-1282-484e-bda3-87cb45315ebd2885520170823.png"]
         */

        private String imei;
        private List<String> equipmentPic;

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public List<String> getEquipmentPic() {
            return equipmentPic;
        }

        public void setEquipmentPic(List<String> equipmentPic) {
            this.equipmentPic = equipmentPic;
        }
    }

    public static class ShowBean {
        private List<String> equipmentPic;

        public List<String> getEquipmentPic() {
            return equipmentPic;
        }

        public void setEquipmentPic(List<String> equipmentPic) {
            this.equipmentPic = equipmentPic;
        }
    }
}
