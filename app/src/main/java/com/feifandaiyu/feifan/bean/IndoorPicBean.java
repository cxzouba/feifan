package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2018/1/17.
 */

public class IndoorPicBean {

    /**
     * code : 1
     * show : {"cards":["http://oupz05j2b.bkt.clouddn.com/babyShow/1515736621-0?e=1516174343&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:VIdfjCm2E44ACyXKoDC9TbFeTAw="],"housePic":["http://oupz05j2b.bkt.clouddn.com/image_20180112135701_958.jpg?e=1516174343&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:aQteBeLuoyb5MCtiScpLaM4Zmkk="],"indoor":["http://oupz05j2b.bkt.clouddn.com/image_20180112135701_393.jpg?e=1516174343&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:Q5asVudQzpn263vLEUNypYJkZ3A="],"cardi":["http://oupz05j2b.bkt.clouddn.com/babyShow/1515736621-1?e=1516174343&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:hASc0ZPFrJHymxLxBapF3npXleY="],"drivers":["http://oupz05j2b.bkt.clouddn.com/image_20180112135701_565.jpg?e=1516174343&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:9ovpu5hhp6MDiWuh02zwUu54SMs="],"household":["http://oupz05j2b.bkt.clouddn.com/image_20180112135701_812.jpg?e=1516174343&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:LBVny-ZpXEKciLX2Ek5Zaq1K6Jw="],"marriage":["http://oupz05j2b.bkt.clouddn.com/20180111/3653276a9016d6c569c50c994933b42c.png?e=1516174343&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:8aI5WjB1nj9a4KqUZffFyO3bjag="],"bankFlow":["http://oupz05j2b.bkt.clouddn.com/image_20180112135701_131.jpg?e=1516174343&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:Bf52s7G9fiWJCIv3U5_FdgkPRSg="],"bankcardFront":["http://oupz05j2b.bkt.clouddn.com/babyShow/1515736621-2?e=1516174343&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:M-TYcg-7MQ4hHItpb387ZxVoc8E="],"bankcardCounter":["http://oupz05j2b.bkt.clouddn.com/babyShow/1515736621-3?e=1516174343&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:fGcBemkDBZtyy6zy8KwAwWjLUQ8="],"workProof":["http://oupz05j2b.bkt.clouddn.com/image_20180112135701_710.jpg?e=1516174343&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:7b4SxBSpvfKPfJ65xTV8QCWk_g8="],"screenshot":["http://oupz05j2b.bkt.clouddn.com/image_20180112135701_139.jpg?e=1516174343&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:1iW6AQ1jjUE2e9menDkJaEfVS78="],"appAgreement":["http://oupz05j2b.bkt.clouddn.com/image_20180112135701_60.jpg?e=1516174343&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:q4un2aKy5UAjU0pt9EQ-C_EvoWA="]}
     * list : {"cards":["babyShow/1515736621-0"],"housePic":["image_20180112135701_958.jpg"],"indoor":["image_20180112135701_393.jpg"],"cardi":["babyShow/1515736621-1"],"drivers":["image_20180112135701_565.jpg"],"household":["image_20180112135701_812.jpg"],"marriage":["20180111/3653276a9016d6c569c50c994933b42c.png"],"bankFlow":["image_20180112135701_131.jpg"],"bankcardFront":["babyShow/1515736621-2"],"bankcardCounter":["babyShow/1515736621-3"],"workProof":["image_20180112135701_710.jpg"],"screenshot":["image_20180112135701_139.jpg"],"appAgreement":["image_20180112135701_60.jpg"]}
     */

    private int code;
    private ShowBean show;
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
        private List<String> cards;
        private List<String> housePic;
        private List<String> indoor;
        private List<String> cardi;
        private List<String> drivers;
        private List<String> household;
        private List<String> marriage;
        private List<String> bankFlow;
        private List<String> bankcardFront;
        private List<String> bankcardCounter;
        private List<String> workProof;
        private List<String> screenshot;
        private List<String> appAgreement;

        public List<String> getCards() {
            return cards;
        }

        public void setCards(List<String> cards) {
            this.cards = cards;
        }

        public List<String> getHousePic() {
            return housePic;
        }

        public void setHousePic(List<String> housePic) {
            this.housePic = housePic;
        }

        public List<String> getIndoor() {
            return indoor;
        }

        public void setIndoor(List<String> indoor) {
            this.indoor = indoor;
        }

        public List<String> getCardi() {
            return cardi;
        }

        public void setCardi(List<String> cardi) {
            this.cardi = cardi;
        }

        public List<String> getDrivers() {
            return drivers;
        }

        public void setDrivers(List<String> drivers) {
            this.drivers = drivers;
        }

        public List<String> getHousehold() {
            return household;
        }

        public void setHousehold(List<String> household) {
            this.household = household;
        }

        public List<String> getMarriage() {
            return marriage;
        }

        public void setMarriage(List<String> marriage) {
            this.marriage = marriage;
        }

        public List<String> getBankFlow() {
            return bankFlow;
        }

        public void setBankFlow(List<String> bankFlow) {
            this.bankFlow = bankFlow;
        }

        public List<String> getBankcardFront() {
            return bankcardFront;
        }

        public void setBankcardFront(List<String> bankcardFront) {
            this.bankcardFront = bankcardFront;
        }

        public List<String> getBankcardCounter() {
            return bankcardCounter;
        }

        public void setBankcardCounter(List<String> bankcardCounter) {
            this.bankcardCounter = bankcardCounter;
        }

        public List<String> getWorkProof() {
            return workProof;
        }

        public void setWorkProof(List<String> workProof) {
            this.workProof = workProof;
        }

        public List<String> getScreenshot() {
            return screenshot;
        }

        public void setScreenshot(List<String> screenshot) {
            this.screenshot = screenshot;
        }

        public List<String> getAppAgreement() {
            return appAgreement;
        }

        public void setAppAgreement(List<String> appAgreement) {
            this.appAgreement = appAgreement;
        }
    }

    public static class ListBean {
        private List<String> cards;
        private List<String> housePic;
        private List<String> indoor;
        private List<String> cardi;
        private List<String> drivers;
        private List<String> household;
        private List<String> marriage;
        private List<String> bankFlow;
        private List<String> bankcardFront;
        private List<String> bankcardCounter;
        private List<String> workProof;
        private List<String> screenshot;
        private List<String> appAgreement;

        public List<String> getCards() {
            return cards;
        }

        public void setCards(List<String> cards) {
            this.cards = cards;
        }

        public List<String> getHousePic() {
            return housePic;
        }

        public void setHousePic(List<String> housePic) {
            this.housePic = housePic;
        }

        public List<String> getIndoor() {
            return indoor;
        }

        public void setIndoor(List<String> indoor) {
            this.indoor = indoor;
        }

        public List<String> getCardi() {
            return cardi;
        }

        public void setCardi(List<String> cardi) {
            this.cardi = cardi;
        }

        public List<String> getDrivers() {
            return drivers;
        }

        public void setDrivers(List<String> drivers) {
            this.drivers = drivers;
        }

        public List<String> getHousehold() {
            return household;
        }

        public void setHousehold(List<String> household) {
            this.household = household;
        }

        public List<String> getMarriage() {
            return marriage;
        }

        public void setMarriage(List<String> marriage) {
            this.marriage = marriage;
        }

        public List<String> getBankFlow() {
            return bankFlow;
        }

        public void setBankFlow(List<String> bankFlow) {
            this.bankFlow = bankFlow;
        }

        public List<String> getBankcardFront() {
            return bankcardFront;
        }

        public void setBankcardFront(List<String> bankcardFront) {
            this.bankcardFront = bankcardFront;
        }

        public List<String> getBankcardCounter() {
            return bankcardCounter;
        }

        public void setBankcardCounter(List<String> bankcardCounter) {
            this.bankcardCounter = bankcardCounter;
        }

        public List<String> getWorkProof() {
            return workProof;
        }

        public void setWorkProof(List<String> workProof) {
            this.workProof = workProof;
        }

        public List<String> getScreenshot() {
            return screenshot;
        }

        public void setScreenshot(List<String> screenshot) {
            this.screenshot = screenshot;
        }

        public List<String> getAppAgreement() {
            return appAgreement;
        }

        public void setAppAgreement(List<String> appAgreement) {
            this.appAgreement = appAgreement;
        }
    }
}
