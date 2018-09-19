package com.feifandaiyu.feifan.bean;

import android.media.Image;

/**
 * Created by davidzhao on 2017/5/8.
 */

public class CompanyLoanBean {
    public CompanyLoanBean(String companyName, String phoneNumber, Image icon) {
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.icon = icon;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    private String companyName;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    private String phoneNumber;
    private Image icon;

}
