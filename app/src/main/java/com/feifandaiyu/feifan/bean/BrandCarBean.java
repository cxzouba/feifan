package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by davidzhao on 2017/5/26.
 */

public class BrandCarBean {

    /**
     * code : 1
     * list : {"series":[{"cars":"宝骏330"},{"cars":"宝骏560"},{"cars":"宝骏610"},{"cars":"宝骏630"},{"cars":"宝骏730"},{"cars":"乐驰"}]}
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
        private List<SeriesBean> series;

        public List<SeriesBean> getSeries() {
            return series;
        }

        public void setSeries(List<SeriesBean> series) {
            this.series = series;
        }

        public static class SeriesBean {
            /**
             * cars : 宝骏330
             */

            private String cars;

            public String getCars() {
                return cars;
            }

            public void setCars(String cars) {
                this.cars = cars;
            }
        }
    }
}
