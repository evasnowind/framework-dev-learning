package com.prayerlaputa.assemblebean.xmlconfig;

/**
 * chenglong.yu
 */
public class UserDao {

    private String curName;

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public void printUserDao() {
        System.out.println("user dao curName=" + curName);
    }
}
