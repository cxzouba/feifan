package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2017/11/7.
 */

public class NoticeBean {

    /**
     * code : 1
     * list : [{"id":"68","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"69","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"70","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"71","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"72","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"73","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"74","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"75","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"76","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"77","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"78","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"79","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"80","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"81","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"82","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"},{"id":"83","content":"您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全","riqi":"2017-11-02"}]
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
         * id : 68
         * content : 您的账号在其他设备上登录，如不是本人操作，请及时修改密码保证账号安全
         * riqi : 2017-11-02
         */

        private String id;
        private String content;
        private String riqi;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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
