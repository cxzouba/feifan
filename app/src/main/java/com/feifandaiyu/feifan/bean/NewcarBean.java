package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2018/1/10.
 */

public class NewcarBean {


    /**
     * code : 1
     * appenhance_pic : {"show":["https://img.hrbffdy.com/8098eb43-ee12-46b2-9990-7bbbdbb1d2d85876520180423.png?e=1524481494&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:cRKFBG2RuhxpgauAdvNY1JIOqNg=","https://img.hrbffdy.com/8d8bfcfd-7b46-4fc3-8225-c7d30455ee0e7237720180423.png?e=1524481494&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:vMksEJ_Sopw9ijS2gmKONLfcj8c=","https://img.hrbffdy.com/49580a0e-9aef-4ec8-a9db-837b335db7cb9180920180423.png?e=1524481494&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:gMfOxITtB5EFF7M2gchCxzsPr3I=","https://img.hrbffdy.com/d5f301e6-e54f-4bb4-ae6a-5e96e74df92d3676620180423.png?e=1524481494&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:_UBVJE_pD1w9w8y1En7O6dtfg04="],"list":["8098eb43-ee12-46b2-9990-7bbbdbb1d2d85876520180423.png","8d8bfcfd-7b46-4fc3-8225-c7d30455ee0e7237720180423.png","49580a0e-9aef-4ec8-a9db-837b335db7cb9180920180423.png","d5f301e6-e54f-4bb4-ae6a-5e96e74df92d3676620180423.png"]}
     * control_pic : {"show":["https://img.hrbffdy.com/5e01a263-69fc-4d93-9a9f-eb6c850b3b229539820180423.png?e=1524481494&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:8-HQ_xA0u7SmtB5god_tqe8_JOE=","https://img.hrbffdy.com/197b2119-2e9e-4b82-af44-224678fa7fdc2441520180423.png?e=1524481494&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:VROYsDDfNUhxPuweEleWLhkzX9Y=","https://img.hrbffdy.com/a22d6be1-a946-4868-9865-1454fed371117951420180423.png?e=1524481494&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:hgMFTb6i08kofgOcwRdK_GUXIAo="],"list":["5e01a263-69fc-4d93-9a9f-eb6c850b3b229539820180423.png","197b2119-2e9e-4b82-af44-224678fa7fdc2441520180423.png","a22d6be1-a946-4868-9865-1454fed371117951420180423.png"]}
     * structure_pic : {"show":["https://img.hrbffdy.com/e730c460-eae1-4945-9056-ff80908d3a4b1133520180423.png?e=1524481494&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:vHz7x_g58BZJUnnDXRKtX3_uDwY=","https://img.hrbffdy.com/17e828cc-c6a5-490e-b33b-2fb6649d86fe1476020180423.png?e=1524481494&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:bW5zesWJHzSigX1eh6CYloaasFQ="],"list":["e730c460-eae1-4945-9056-ff80908d3a4b1133520180423.png","17e828cc-c6a5-490e-b33b-2fb6649d86fe1476020180423.png"]}
     * other_pic : {"show":["https://img.hrbffdy.com/5e01a263-69fc-4d93-9a9f-eb6c850b3b229539820180423.png?e=1524481494&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:8-HQ_xA0u7SmtB5god_tqe8_JOE=","https://img.hrbffdy.com/197b2119-2e9e-4b82-af44-224678fa7fdc2441520180423.png?e=1524481494&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:VROYsDDfNUhxPuweEleWLhkzX9Y=","https://img.hrbffdy.com/a22d6be1-a946-4868-9865-1454fed371117951420180423.png?e=1524481494&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:hgMFTb6i08kofgOcwRdK_GUXIAo="],"list":["5e01a263-69fc-4d93-9a9f-eb6c850b3b229539820180423.png","197b2119-2e9e-4b82-af44-224678fa7fdc2441520180423.png","a22d6be1-a946-4868-9865-1454fed371117951420180423.png"]}
     */

    private String msg;
    private int code;
    private AppenhancePicBean appenhance_pic;
    private ControlPicBean control_pic;
    private StructurePicBean structure_pic;
    private OtherPicBean other_pic;

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

    public AppenhancePicBean getAppenhance_pic() {
        return appenhance_pic;
    }

    public void setAppenhance_pic(AppenhancePicBean appenhance_pic) {
        this.appenhance_pic = appenhance_pic;
    }

    public ControlPicBean getControl_pic() {
        return control_pic;
    }

    public void setControl_pic(ControlPicBean control_pic) {
        this.control_pic = control_pic;
    }

    public StructurePicBean getStructure_pic() {
        return structure_pic;
    }

    public void setStructure_pic(StructurePicBean structure_pic) {
        this.structure_pic = structure_pic;
    }

    public OtherPicBean getOther_pic() {
        return other_pic;
    }

    public void setOther_pic(OtherPicBean other_pic) {
        this.other_pic = other_pic;
    }

    public static class AppenhancePicBean {
        private List<String> show;
        private List<String> list;

        public List<String> getShow() {
            return show;
        }

        public void setShow(List<String> show) {
            this.show = show;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }

    public static class ControlPicBean {
        private List<String> show;
        private List<String> list;

        public List<String> getShow() {
            return show;
        }

        public void setShow(List<String> show) {
            this.show = show;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }

    public static class StructurePicBean {
        private List<String> show;
        private List<String> list;

        public List<String> getShow() {
            return show;
        }

        public void setShow(List<String> show) {
            this.show = show;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }

    public static class OtherPicBean {
        private List<String> show;
        private List<String> list;

        public List<String> getShow() {
            return show;
        }

        public void setShow(List<String> show) {
            this.show = show;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }
}
