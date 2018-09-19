package com.feifandaiyu.feifan.utils;

/**
 * Created by davidzhao on 2017/7/24.
 */

public class BirthdayUtils {
    public static String getBirhtday(String id){
        String bornday = id.substring(6, 14);
        return bornday.substring(0,4)+"-"+bornday.substring(4,6)+"-"+bornday.substring(6,8);
    }
}
