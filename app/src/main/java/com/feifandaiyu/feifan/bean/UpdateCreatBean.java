package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2017/12/20.
 */

public class UpdateCreatBean {


    /**
     * code : 1
     * list : {"credit":"0","userName":"侯代昌","cardNum":"230606199203175738","overduepic":["http://oupz05j2b.bkt.clouddn.com/20171218/1d6981280f3611e09a941d47efa2a3a0.png?e=1513741308&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:jjWAPgoNSIQsqPmPHChMvFePJfI="],"overdue":"nasha","pic":["http://oupz05j2b.bkt.clouddn.com/63a9b1a0-1821-4d3e-8800-d400017c2c6c4867720171219.png?e=1513741308&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:j0wDZYV6_-g9mvXsGBqX9AqxDyA=","http://oupz05j2b.bkt.clouddn.com/ad62ac45-5381-47d1-b726-7e2a28dc951c6514720171219.png?e=1513741308&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:YAFh8hUTpMi5y4wI9MgnU4zl_7s=","http://oupz05j2b.bkt.clouddn.com/0d7aaad9-2551-43e5-be2e-386271ae0ea14734120171219.png?e=1513741308&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:tNIXVYX2i6-IqDBqjra4ANFKukE=","http://oupz05j2b.bkt.clouddn.com/ece358fe-f73e-43d3-9504-491a5099d94c4058120171219.png?e=1513741308&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:DO88CZpt28gpIvpql8f5ucPB2xU="]}
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

    public static class ListBean {
        /**
         * credit : 0
         * userName : 侯代昌
         * cardNum : 230606199203175738
         * overduepic : ["http://oupz05j2b.bkt.clouddn.com/20171218/1d6981280f3611e09a941d47efa2a3a0.png?e=1513741308&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:jjWAPgoNSIQsqPmPHChMvFePJfI="]
         * overdue : nasha
         * pic : ["http://oupz05j2b.bkt.clouddn.com/63a9b1a0-1821-4d3e-8800-d400017c2c6c4867720171219.png?e=1513741308&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:j0wDZYV6_-g9mvXsGBqX9AqxDyA=","http://oupz05j2b.bkt.clouddn.com/ad62ac45-5381-47d1-b726-7e2a28dc951c6514720171219.png?e=1513741308&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:YAFh8hUTpMi5y4wI9MgnU4zl_7s=","http://oupz05j2b.bkt.clouddn.com/0d7aaad9-2551-43e5-be2e-386271ae0ea14734120171219.png?e=1513741308&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:tNIXVYX2i6-IqDBqjra4ANFKukE=","http://oupz05j2b.bkt.clouddn.com/ece358fe-f73e-43d3-9504-491a5099d94c4058120171219.png?e=1513741308&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:DO88CZpt28gpIvpql8f5ucPB2xU="]
         */

        private int credit;
        private String userName;
        private String cardNum;
        private String overdue;
        private String drivingLicence;
        private List<String> overduepic;
        private List<String> pic;

        public String getDrivingLicence() {
            return drivingLicence == null ? "" : drivingLicence;
        }

        public void setDrivingLicence(String drivingLicence) {
            this.drivingLicence = drivingLicence;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getOverdue() {
            return overdue;
        }

        public void setOverdue(String overdue) {
            this.overdue = overdue;
        }

        public List<String> getOverduepic() {
            return overduepic;
        }

        public void setOverduepic(List<String> overduepic) {
            this.overduepic = overduepic;
        }

        public List<String> getPic() {
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }
    }
}
