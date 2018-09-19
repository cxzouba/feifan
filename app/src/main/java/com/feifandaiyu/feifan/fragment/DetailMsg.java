package com.feifandaiyu.feifan.fragment;

/**
 * Created by houdaichang on 2017/11/7.
 */

class DetailMsg {

    /**
     * code : 1
     * list : {"content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-07"}
     */

    private int code;
    private ListBean list;

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
         * content : 您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全
         * riqi : 2017-11-07
         */

        private String content;
        private String riqi;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRiqi() {
            return riqi;
        }

        public void setRiqi(String riqi) {
            this.riqi = riqi;
        }
    }
}
