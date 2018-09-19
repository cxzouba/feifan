package com.feifandaiyu.feifan.utils;

import java.util.Random;

/**
 * Created by davidzhao on 2017/7/28.
 */

public class StringCreateUtils {
    public static String createString() {
        String[] strings = {
                "亲，您能返回的次数不多了哦！是否返回？",
                "返回是会呼吸的痛！是否返回？",
                "再返回奖金就没啦！是否返回？",
                "一次完成更畅爽！是否返回？",
                "老板让我问你为啥总返回！是否返回？",
                "返回记得从完善信息进来哈！是否返回？",
                "返回这页数据就没啦！是否返回？",
                "有没有试过一次填完！是否返回？",
                "你返回的机会要没啦！是否返回？"
        };
        Random random = new Random();
        int number = random.nextInt(9);

        return strings[number];
    }
}

