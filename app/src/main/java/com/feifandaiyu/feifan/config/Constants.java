package com.feifandaiyu.feifan.config;

import com.feifandaiyu.feifan.utils.LogUtils;

/**
 * @author houdaichang
 * @date 2017/5/2
 */

public class Constants {

    /**
     * LogUtils.LEVEL_ALL:打开日志(显示所有的日志输出)
     * LogUtils.LEVEL_OFF:关闭日志(屏蔽所有的日志输出)
     */

    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;
    public static final long PROTOCOLTIMEOUT = 5 * 60 * 1000;//5分钟

    // 正式
    public static String token = "1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:_L1fCWx2hEWmiPcKYOkLriRQlcs=:eyJzY29wZSI6ImZlaWZhbmRhaXl1IiwiZGVhZGxpbmUiOjE0OTY2Mjk2MjUwfQ==";
    // 测试

//    public static String token = "1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:aG6qAk-5ZEoYWSxd8q5-C7vcJfw=:eyJzY29wZSI6ImNlc2hpZmVpZmFuY2hlZGFpIiwiZGVhZGxpbmUiOjE1MDM2MjU0MDkwMH0=";

    public static final class URLS {

        //http://official.hrbffdy.com/public/index.php/info/
        //正式https://www.hrbffdy.com/info/
        //测试http://192.168.3.32/feifancar/public/index.php/info/
        public static final String BASEURL = "http://192.168.1.189/feifancar/public/index.php/info/";
        public static final String IMGBASEURL = BASEURL + "image?name=";

    }

    public static final class REQ {

    }

    public static final class RES {

    }

    public static final class PAY {
        //public static final int PAYTYPE_ZHIFUBAO = 0;
        //public static final int PAYTYPE_WEIXIN = 1;
    }

}
