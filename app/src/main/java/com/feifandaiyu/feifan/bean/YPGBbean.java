package com.feifandaiyu.feifan.bean;

import android.media.Image;

/**
 * Created by davidzhao on 2017/5/18.
 */

public class YPGBbean {
    private String carName;
    private String owenrName;
    private Image image;


    public YPGBbean(String carName, String owenrName, Image image) {
        this.carName = carName;
        this.owenrName = owenrName;
        this.image = image;

    }



    public String getCarName() {

        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getOwenrName() {
        return owenrName;
    }

    public void setOwenrName(String owenrName) {
        this.owenrName = owenrName;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


}
