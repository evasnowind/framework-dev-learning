package com.prayerlaputa.classloader.demo2;

public class Foo {

    private String name;

    public Foo(String name) {
        this.name = name;
    }

    public String hello() {
        return "hello " + name;
    }

}
