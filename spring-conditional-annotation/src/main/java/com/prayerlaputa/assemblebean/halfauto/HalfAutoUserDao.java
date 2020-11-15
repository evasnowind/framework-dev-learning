package com.prayerlaputa.assemblebean.halfauto;

import org.springframework.stereotype.Repository;

/**
 * chenglong.yu
 */
@Repository
public class HalfAutoUserDao {

    private String curName = "halfAutoUserDAO";

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public void printUserDao() {
        System.out.println("user dao curName=" + curName);
    }
}
