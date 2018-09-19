package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2017/8/29.
 */

public class UpdatePersonalPicBean {

    /**
     * code : 1
     * show : {"groupPhoto":["http://oupz05j2b.bkt.clouddn.com/image_20170814093140_228.jpg?e=1503996479&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:SIGywrmVQp0Pth3yIX8kBFbjYXI="],"contractPic":["http://oupz05j2b.bkt.clouddn.com/image_20170814093140_845.jpg?e=1503996479&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:gk_YY-NlP6NKReznBnho0JkWWTE=","http://oupz05j2b.bkt.clouddn.com/image_20170814093141_980.jpg?e=1503996479&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:RAI3tOl86XIyS7Re1w79bnDKUvk="],"housePic":["http://oupz05j2b.bkt.clouddn.com/image_20170814093141_3.jpg?e=1503996479&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:y8vnHq9KZc-Hv9sePMB1okPK0fA=","http://oupz05j2b.bkt.clouddn.com/image_20170814093141_914.jpg?e=1503996479&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:BhA5hmJdGrmBlZSq0Z5fIJxHk0g=","http://oupz05j2b.bkt.clouddn.com/image_20170814093141_804.jpg?e=1503996479&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:Ruv8aNja99mCI1UchfahdfzExfY=","http://oupz05j2b.bkt.clouddn.com/image_20170814093140_890.jpg?e=1503996479&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:9kbASgdVi_HR29r-_rTCjvgX4A0="],"cards":["http://oupz05j2b.bkt.clouddn.com/image_20170814093140_997.jpg?e=1503996479&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:-DsMUriOuXut0ihHPg1vyIQe_a0=","http://oupz05j2b.bkt.clouddn.com/image_20170814093140_464.jpg?e=1503996479&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:53PBZwQtYlhr15NEn7ijuTLO6sw="]}
     * list : {"groupPhoto":["image_20170814093140_228.jpg"],"contractPic":["image_20170814093140_845.jpg","image_20170814093141_980.jpg"],"housePic":["image_20170814093141_3.jpg","image_20170814093141_914.jpg","image_20170814093141_804.jpg","image_20170814093140_890.jpg"],"cards":["image_20170814093140_997.jpg","image_20170814093140_464.jpg"]}
     */

    private int code;
    private ShowBean show;
    private ListBean list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ShowBean getShow() {
        return show;
    }

    public void setShow(ShowBean show) {
        this.show = show;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ShowBean {
        private List<String> groupPhoto;
        private List<String> contractPic;
        private List<String> housePic;
        private List<String> cards;

        public List<String> getGroupPhoto() {
            return groupPhoto;
        }

        public void setGroupPhoto(List<String> groupPhoto) {
            this.groupPhoto = groupPhoto;
        }

        public List<String> getContractPic() {
            return contractPic;
        }

        public void setContractPic(List<String> contractPic) {
            this.contractPic = contractPic;
        }

        public List<String> getHousePic() {
            return housePic;
        }

        public void setHousePic(List<String> housePic) {
            this.housePic = housePic;
        }

        public List<String> getCards() {
            return cards;
        }

        public void setCards(List<String> cards) {
            this.cards = cards;
        }
    }

    public static class ListBean {
        private List<String> groupPhoto;
        private List<String> contractPic;
        private List<String> housePic;
        private List<String> cards;

        public List<String> getGroupPhoto() {
            return groupPhoto;
        }

        public void setGroupPhoto(List<String> groupPhoto) {
            this.groupPhoto = groupPhoto;
        }

        public List<String> getContractPic() {
            return contractPic;
        }

        public void setContractPic(List<String> contractPic) {
            this.contractPic = contractPic;
        }

        public List<String> getHousePic() {
            return housePic;
        }

        public void setHousePic(List<String> housePic) {
            this.housePic = housePic;
        }

        public List<String> getCards() {
            return cards;
        }

        public void setCards(List<String> cards) {
            this.cards = cards;
        }
    }
}
