package com.feifandaiyu.feifan.bean;

/**
 * Created by houdaichang on 2017/7/13.
 */

public class UploadVideoBean {


    /**
     * code : 1
     * show : {"video":"http://oupz05j2b.bkt.clouddn.com/20170824/45bc4582ae8eb9e83096ec0acd4aab74.mp4?e=1503627947&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:kpgftI2x8SbCvx21X57hiKACrrk="}
     * list : {"video":"20170824/45bc4582ae8eb9e83096ec0acd4aab74.mp4"}
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
        /**
         * video : http://oupz05j2b.bkt.clouddn.com/20170824/45bc4582ae8eb9e83096ec0acd4aab74.mp4?e=1503627947&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:kpgftI2x8SbCvx21X57hiKACrrk=
         */

        private String video;

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }
    }

    public static class ListBean {
        /**
         * video : 20170824/45bc4582ae8eb9e83096ec0acd4aab74.mp4
         */

        private String video;

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }
    }
}
