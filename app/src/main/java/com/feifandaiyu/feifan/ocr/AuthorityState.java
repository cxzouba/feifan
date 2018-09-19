package com.feifandaiyu.feifan.ocr;

/**
 * Created by houdaichang on 2017/10/13.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.util.HashMap;

public class AuthorityState {
    public static final int STATE_SUCCESS = 0;
    public static final int STATE_WARNING_VALIDITY_COMING = 16;
    public static final int STATE_ERROR_BEGIN = 48;
    public static final int STATE_ERROR_NOT_FIND_LICENSE = 49;
    public static final int STATE_ERROR_EXPIRED = 50;
    public static final int STATE_ERROR_AUTHORIZED = 51;
    public static final int STATE_ERROR_NETWORK = 240;
    public static final int STATE_NOT_INIT = 256;
    public static final int STATE_INIT_ING = 272;
    private static HashMap<Integer, String> sStateName = new HashMap();

    public AuthorityState() {
    }

    public static String getStateName(int aState) {
        String ret = null;
        if (null != sStateName) {
            ret = (String) sStateName.get(Integer.valueOf(aState));
        }

        return ret;
    }

    static {
        sStateName.put(Integer.valueOf(0), "STATE_SUCCESS");
        sStateName.put(Integer.valueOf(16), "STATE_WARNING_VALIDITY_COMING");
        sStateName.put(Integer.valueOf(49), "STATE_ERROR_NOT_FIND_LICENSE");
        sStateName.put(Integer.valueOf(50), "STATE_ERROR_EXPIRED");
        sStateName.put(Integer.valueOf(51), "STATE_ERROR_AUTHORIZED");
        sStateName.put(Integer.valueOf(240), "STATE_ERROR_NETWORK");
        sStateName.put(Integer.valueOf(256), "STATE_NOT_INIT");
        sStateName.put(Integer.valueOf(272), "STATE_INIT_ING");
    }
}

