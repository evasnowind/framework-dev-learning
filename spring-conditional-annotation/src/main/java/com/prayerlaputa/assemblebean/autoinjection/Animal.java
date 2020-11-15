package com.prayerlaputa.assemblebean.autoinjection;

/**
 * @author chenglong.yu
 * created on 2020/11/13
 */
public class Animal {
    private String name;

    public String getName() {
        return getClass().getName();
    }

    public void setName(String name) {
        this.name = name;
    }
}
