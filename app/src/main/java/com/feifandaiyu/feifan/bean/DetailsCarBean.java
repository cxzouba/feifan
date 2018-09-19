package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by davidzhao on 2017/5/27.
 */

public class DetailsCarBean {

    /**
     * code : 1
     * list : {"series":[{"Models":"宝骏330 2016款 1.5L 手动舒适型","price":"5.58万"},{"Models":"宝骏330 2016款 1.5L 手动精英型","price":"5.98万"},{"Models":"宝骏330 2016款 1.5L 手动舒适型","price":"5.58万"},{"Models":"宝骏330 2016款 1.5L 手动精英型","price":"5.98万"},{"Models":"宝骏330 2016款 1.5L 手动舒适型","price":"5.58万"},{"Models":"宝骏330 2016款 1.5L 手动精英型","price":"5.98万"}]}
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
             * Models : 宝骏330 2016款 1.5L 手动舒适型
             * price : 5.58万
             */

            private String Models;
            private String price;

            public String getModels() {
                return Models;
            }

            public void setModels(String Models) {
                this.Models = Models;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
