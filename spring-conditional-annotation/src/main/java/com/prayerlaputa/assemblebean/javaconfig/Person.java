package com.prayerlaputa.assemblebean.javaconfig;

/**
 * @author chenglong.yu
 * created on 2020/11/13
 */
public class Person {
    private Integer id;
    private String name;
    private Car car;

    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public Person() {
    }
    public Person(Integer id, String name, Car car) {
        this.id = id;
        this.name = name;
        this.car = car;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Car getCar() {
        return car;
    }
    public void setCar(Car car) {
        this.car = car;
    }
    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", car=" + car + "]";
    }

}
