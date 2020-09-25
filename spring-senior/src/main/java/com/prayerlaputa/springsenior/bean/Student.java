package com.prayerlaputa.springsenior.bean;

/**
 * @author chenglong.yu
 * created on 2020/9/25
 */
public class Student {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
