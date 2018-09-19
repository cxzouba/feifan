package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2018/9/17.
 */

public class OcrBean {

    /**
     * log_id : 8582664246232281394
     * direction : 3
     * words_result_num : 5
     * words_result : [{"words":"1.凡因本合同引起的或与本合同有关的任何争任何一方均有权将争议提交哈尔滨仲裁委员会进行"},{"words":"仲裁"},{"words":"2.本保单第一受益人为(非货遇杭州资租赁有限公司黑江分公司),非经第一受益人书面"},{"words":"同意,本保单不得撤销/减保或批改.车辆单次事故赔付金额超过5000元以上的,需第一受益人书面"},{"words":"同意,方可将款项赔付给被保险人"}]
     */

    private long log_id;
    private int direction;
    private int words_result_num;
    private List<WordsResultBean> words_result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public List<WordsResultBean> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WordsResultBean> words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {
        /**
         * words : 1.凡因本合同引起的或与本合同有关的任何争任何一方均有权将争议提交哈尔滨仲裁委员会进行
         */

        private String words;

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }

    @Override
    public String toString() {
        return "OcrBean{" +
                "words_result=" + words_result +
                '}';
    }
}
