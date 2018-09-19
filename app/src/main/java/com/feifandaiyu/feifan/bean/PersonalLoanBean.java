package com.feifandaiyu.feifan.bean;

/**
 * Created by davidzhao on 2017/5/17.
 */

public class PersonalLoanBean {
    private String personname;
    private String phonenumber;
    private boolean state;

    public PersonalLoanBean() {
    }

    public PersonalLoanBean(String personname, String phonenumber, boolean state) {
        this.personname = personname;
        this.phonenumber = phonenumber;
        this.state = state;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
